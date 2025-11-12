package com.b2c.prototype.manager.option.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.manager.option.ITimeDurationGroupOptionManager;
import com.b2c.prototype.modal.dto.payload.option.group.TimeDurationOptionGroupDto;
import com.b2c.prototype.modal.dto.payload.option.item.TimeDurationOptionDto;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.TimeDurationOption;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.transform.constant.IGeneralEntityTransformService;
import com.b2c.prototype.transform.order.IOrderTransformService;
import com.nimbusds.jose.util.Pair;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import static com.b2c.prototype.util.Constant.KEY;
import static java.util.stream.Collectors.toMap;

@Service
public class TimeDurationGroupOptionManager implements ITimeDurationGroupOptionManager {

    private final IGeneralEntityDao generalEntityDao;
    private final IOrderTransformService orderTransformService;
    private final IGeneralEntityTransformService generalEntityTransformService;

    public TimeDurationGroupOptionManager(IGeneralEntityDao generalEntityDao,
                                          IOrderTransformService orderTransformService,
                                          IGeneralEntityTransformService generalEntityTransformService) {
        this.generalEntityDao = generalEntityDao;
        this.orderTransformService = orderTransformService;
        this.generalEntityTransformService = generalEntityTransformService;
    }

    @Transactional
    @Override
    public void persistEntity(TimeDurationOptionGroupDto dto) {
        OptionGroup entity = orderTransformService.mapTimeDurationOptionGroupDtoToOptionGroup(dto);
        generalEntityDao.persistEntity(entity);
    }

    @Transactional
    @Override
    public void mergeEntity(String searchValue, TimeDurationOptionGroupDto timeDurationOptionGroupDto) {
        OptionGroup group = generalEntityDao.findEntity(
                "OptionGroup.findByValueWithOptionItems",
                Pair.of(KEY, searchValue)
        );
        OptionGroup entity = syncItemsAllowingKeyChange(searchValue, group, timeDurationOptionGroupDto);
        generalEntityDao.mergeEntity(entity);
    }

    @Transactional
    @Override
    public void removeEntity(String value) {
        OptionGroup optionGroup = generalEntityDao.findEntity("OptionGroup.findByKey", Pair.of(KEY, value));
        generalEntityDao.removeEntity(optionGroup);
    }

    @Override
    public OptionGroup getEntity(String value) {
        return generalEntityDao.findEntity(
                "OptionGroup.findByValueWithOptionItems",
                List.of(Pair.of(KEY, value)));
    }

    @Override
    public List<OptionGroup> getEntities() {
        return generalEntityDao.findEntityList(
                "OptionGroup.withOptionItems", (Pair<String, ?>) null);
    }

    private OptionGroup syncItemsAllowingKeyChange(String searchValue, OptionGroup group, TimeDurationOptionGroupDto dto) {
        if (group == null) {
            throw new IllegalArgumentException("OptionGroup not found by old value: " + searchValue);
        }

        copyUpdatableFields(group, dto);

        final Map<String, TimeDurationOption> currentByValue = group.getTimeDurationOptions().stream()
                .filter(Objects::nonNull)
                .filter(t -> t.getKey() != null)
                .collect(toMap(TimeDurationOption::getKey, Function.identity(), (a, b) -> a, LinkedHashMap::new));

        final Set<TimeDurationOption> matchedExisting = Collections.newSetFromMap(new IdentityHashMap<>());
        final List<TimeDurationOptionDto> incoming = Optional.ofNullable(dto.getTimeDurationOptions())
                .orElseGet(Collections::emptyList);

        incoming.forEach(itemDto -> {
            if (itemDto == null) return;
            final String lookupKey = itemDto.getSearchKey() != null ? itemDto.getSearchKey() : itemDto.getKey();
            TimeDurationOption existing = lookupKey == null ? null : currentByValue.get(lookupKey);

            if (existing != null) {
                if (itemDto.getKey() != null && !itemDto.getKey().equals(existing.getKey())) {
                    if (currentByValue.containsKey(itemDto.getKey()) && currentByValue.get(itemDto.getKey()) != existing) {
                        throw new IllegalStateException("TimeDurationOption value collision on rename: " + itemDto.getKey());
                    }
                    currentByValue.remove(existing.getKey());
                    existing.setKey(itemDto.getKey());
                    currentByValue.put(existing.getKey(), existing);
                }

                if (itemDto.getValue() != null) existing.setValue(itemDto.getValue());
                if (itemDto.getKey() != null) existing.setKey(itemDto.getKey());

                if (itemDto.getStartTime() != null) existing.setStartTime(itemDto.getStartTime());
                if (itemDto.getEndTime() != null) existing.setEndTime(itemDto.getEndTime());
                if (itemDto.getTimeZone() != null) existing.setTimeZone(itemDto.getTimeZone());
                if (existing.getStartTime() != null && existing.getEndTime() != null) {
                    existing.setDurationInMin(
                            Duration.between(existing.getStartTime(), existing.getEndTime()).toMinutes()
                    );
                }

                if (itemDto.getPrice() != null) {
                    Price p = existing.getPrice();
                    if (p == null) {
                        p = Price.builder().build();
                        existing.setPrice(p);
                    }
                    if (itemDto.getPrice().getAmount() != null) {
                        p.setAmount(itemDto.getPrice().getAmount());
                    }
                    if (itemDto.getPrice().getCurrency() != null && itemDto.getPrice().getCurrency().getKey() != null) {
                        p.setCurrency(
                                generalEntityTransformService.mapCurrencyDtoToCurrency(itemDto.getPrice().getCurrency())
                        );
                    }
                }

                matchedExisting.add(existing);
            }
        });

        incoming.stream()
                .filter(Objects::nonNull)
                .filter(d -> d.getSearchKey() == null)
                .forEach(d -> {
                    final String newKey = d.getKey();
                    if (newKey == null || newKey.trim().isEmpty()) {
                        throw new IllegalArgumentException("New TimeDurationOption must have non-null 'value'.");
                    }
                    if (!currentByValue.containsKey(newKey)) {
                        TimeDurationOption created = TimeDurationOption.builder()
                                .value(d.getValue())
                                .key(newKey)
                                .startTime(d.getStartTime())
                                .endTime(d.getEndTime())
                                .timeZone(d.getTimeZone())
                                .durationInMin(
                                        d.getStartTime() != null && d.getEndTime() != null
                                                ? Duration.between(d.getStartTime(), d.getEndTime()).toMinutes()
                                                : 0
                                )
                                .price(d.getPrice() != null
                                        ? Price.builder()
                                        .amount(d.getPrice().getAmount())
                                        .currency(generalEntityTransformService.mapCurrencyDtoToCurrency(d.getPrice().getCurrency()))
                                        .build()
                                        : null)
                                .build();
                        group.addTimeDurationOption(created);
                        currentByValue.put(newKey, created);
                        matchedExisting.add(created);
                    }
                });

        // REMOVE ORPHANS (unchanged)
        group.getTimeDurationOptions().stream()
                .filter(Objects::nonNull)
                .filter(t -> !matchedExisting.contains(t))
                .toList()
                .forEach(group::removeTimeDurationOption);

        return group;
    }

    private void copyUpdatableFields(OptionGroup target, TimeDurationOptionGroupDto source) {
        if (source.getValue() != null) {
            target.setValue(source.getValue());
        }
        if (source.getKey() != null) {
            target.setKey(source.getKey());
        }
    }
}
