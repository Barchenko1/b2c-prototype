package com.b2c.prototype.manager.option.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.manager.option.IZoneOptionManager;
import com.b2c.prototype.modal.dto.payload.option.group.ZoneOptionGroupDto;
import com.b2c.prototype.modal.dto.payload.option.item.ZoneOptionDto;
import com.b2c.prototype.modal.entity.option.ZoneOption;
import com.b2c.prototype.modal.entity.option.ZoneOptionGroup;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.transform.order.IOrderTransformService;
import com.nimbusds.jose.util.Pair;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import static com.b2c.prototype.util.Constant.VALUE;
import static java.util.stream.Collectors.toMap;

@Service
public class ZoneOptionManager implements IZoneOptionManager {

    private final IGeneralEntityDao generalEntityDao;
    private final IOrderTransformService orderTransformService;

    public ZoneOptionManager(IGeneralEntityDao generalEntityDao,
                             IOrderTransformService orderTransformService) {
        this.generalEntityDao = generalEntityDao;
        this.orderTransformService = orderTransformService;
    }

    @Transactional
    @Override
    public void persistEntity(ZoneOptionGroupDto zoneOptionGroupDto) {
        ZoneOptionGroup zoneOptionGroup = orderTransformService.mapZoneOptionGroupDtoToZoneOptionGroup(zoneOptionGroupDto);
        generalEntityDao.persistEntity(zoneOptionGroup);
    }

    @Transactional
    @Override
    public void mergeEntity(String searchValue, ZoneOptionGroupDto zoneOptionGroupDto) {
        ZoneOptionGroup group = generalEntityDao.findEntity(
                "ZoneOptionGroup.findByValue",
                Pair.of(VALUE, searchValue)
        );
        ZoneOptionGroup entity = syncItemsAllowingValueChange(searchValue, group, zoneOptionGroupDto);
        generalEntityDao.mergeEntity(entity);
    }

    @Transactional
    @Override
    public void removeEntity(String value) {
        ZoneOptionGroup zoneOptionGroup = generalEntityDao.findEntity("ZoneOptionGroup.findByValue", Pair.of(VALUE, value));
        generalEntityDao.removeEntity(zoneOptionGroup);
    }

    @Override
    public ZoneOptionGroup getEntity(String value) {
        return generalEntityDao.findEntity("ZoneOptionGroup.findByValue", Pair.of(VALUE, value));
    }

    @Override
    public Optional<ZoneOptionGroup> getEntityOptional(String value) {
        return generalEntityDao.findOptionEntity("ZoneOptionGroup.findByValue", Pair.of(VALUE, value));
    }

    @Override
    public List<ZoneOptionGroup> getEntities() {
        return generalEntityDao.findEntityList("ZoneOptionGroup.all", (Pair<String, ?>) null);
    }

    private ZoneOptionGroup syncItemsAllowingValueChange(String searchValue, ZoneOptionGroup group, ZoneOptionGroupDto dto) {
        if (group == null) {
            throw new IllegalArgumentException("OptionGroup not found by old value: " + searchValue);
        }

        copyUpdatableFields(group, dto);

        final Map<String, ZoneOption> currentByValue = group.getZoneOptions().stream()
                .filter(Objects::nonNull)
                .filter(t -> t.getValue() != null)
                .collect(toMap(ZoneOption::getValue, Function.identity(), (a, b) -> a, LinkedHashMap::new));

        final Set<ZoneOption> matchedExisting = new HashSet<>();
        final List<ZoneOptionDto> incoming = Optional.ofNullable(dto.getZoneOptions())
                .orElseGet(Collections::emptyList);

        // UPDATE EXISTING
        incoming.forEach(itemDto -> {
            if (itemDto == null) return;
            final String lookupKey = itemDto.getSearchValue() != null ? itemDto.getSearchValue() : itemDto.getValue();
            ZoneOption existing = lookupKey == null ? null : currentByValue.get(lookupKey);

            if (existing != null) {
                if (itemDto.getLabel() != null) existing.setLabel(itemDto.getLabel());

                // rename (guard against collision)
                if (itemDto.getValue() != null && !itemDto.getValue().equals(existing.getValue())) {
                    if (currentByValue.containsKey(itemDto.getValue()) && currentByValue.get(itemDto.getValue()) != existing) {
                        throw new IllegalStateException("TimeDurationOption value collision on rename: " + itemDto.getValue());
                    }
                    currentByValue.remove(existing.getValue());
                    existing.setValue(itemDto.getValue());
                    currentByValue.put(existing.getValue(), existing);
                }

                // times + duration
//                if (itemDto.getStartTime() != null) existing.setStartTime(itemDto.getStartTime());
//                if (itemDto.getEndTime() != null) existing.setEndTime(itemDto.getEndTime());
//                if (itemDto.getTimeZone() != null) existing.setTimeZone(itemDto.getTimeZone());
//                if (existing.getStartTime() != null && existing.getEndTime() != null) {
//                    existing.setDurationInMin(
//                            Duration.between(existing.getStartTime(), existing.getEndTime()).toMinutes()
//                    );
//                }

                // price update (no manual IDs; cascade handles insert/update)
                if (itemDto.getPrice() != null) {
                    Price p = existing.getPrice();
                    if (p == null) {
                        p = Price.builder().build();
                        existing.setPrice(p);
                    }
                    if (itemDto.getPrice().getAmount() != null) {
                        p.setAmount(itemDto.getPrice().getAmount());
                    }
                    if (itemDto.getPrice().getCurrency() != null && itemDto.getPrice().getCurrency().getValue() != null) {
                        p.setCurrency(
                                generalEntityDao.findEntity("Currency.findByValue",
                                        Pair.of(VALUE, itemDto.getPrice().getCurrency().getValue()))
                        );
                    }
                }

                matchedExisting.add(existing);
            }
        });

        // CREATE NEW (unchanged from your code)
        incoming.stream()
                .filter(Objects::nonNull)
                .filter(d -> d.getSearchValue() == null)
                .forEach(d -> {
                    final String newVal = d.getValue();
                    if (newVal == null || newVal.trim().isEmpty()) {
                        throw new IllegalArgumentException("New TimeDurationOption must have non-null 'value'.");
                    }
                    if (!currentByValue.containsKey(newVal)) {
                        ZoneOption created = ZoneOption.builder()
                                .label(d.getLabel())
                                .value(newVal)
                                .price(d.getPrice() != null
                                        ? Price.builder()
                                        .amount(d.getPrice().getAmount())
                                        .currency(generalEntityDao.findEntity("Currency.findByValue",
                                                Pair.of(VALUE, d.getPrice().getCurrency().getValue())))
                                        .build()
                                        : null)
                                .build();
                        group.addZoneOption(created);
                        currentByValue.put(newVal, created);
                        matchedExisting.add(created);
                    }
                });

        // REMOVE ORPHANS (unchanged)
        group.getZoneOptions().stream()
                .filter(Objects::nonNull)
                .filter(t -> !matchedExisting.contains(t))
                .toList()
                .forEach(group::removeZoneOption);

        return group;
    }

    private void copyUpdatableFields(ZoneOptionGroup target, ZoneOptionGroupDto source) {
        if (source.getLabel() != null) {
            target.setLabel(source.getLabel());
        }
        if (source.getValue() != null) {
            target.setValue(source.getValue());
        }
        if (source.getCity() != null) {
            target.setCity(source.getCity());
        }
        if (source.getCountry() != null) {
            target.setCountry(generalEntityDao.findEntity("Country.findByValue", Pair.of(VALUE, source.getCountry().getValue())));
        }
    }
}
