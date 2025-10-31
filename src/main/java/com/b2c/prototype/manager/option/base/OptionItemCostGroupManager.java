package com.b2c.prototype.manager.option.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.manager.option.IOptionItemCostGroupManager;
import com.b2c.prototype.modal.dto.payload.option.group.OptionItemCostGroupDto;
import com.b2c.prototype.modal.dto.payload.option.item.OptionItemCostDto;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItemCost;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.transform.item.IItemTransformService;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class OptionItemCostGroupManager implements IOptionItemCostGroupManager {

    private final IGeneralEntityDao generalEntityDao;
    private final IItemTransformService itemTransformService;

    public OptionItemCostGroupManager(IGeneralEntityDao generalEntityDao, IItemTransformService itemTransformService) {
        this.generalEntityDao = generalEntityDao;
        this.itemTransformService = itemTransformService;
    }

    @Transactional
    @Override
    public void persistEntity(OptionItemCostGroupDto optionItemCostGroupDto) {
        OptionGroup entity = itemTransformService.mapOptionItemCostGroupDtoToOptionGroupDto(optionItemCostGroupDto);
        generalEntityDao.persistEntity(entity);
    }

    @Transactional
    @Override
    public void mergeEntity(String searchValue, OptionItemCostGroupDto optionItemCostGroupDto) {
        OptionGroup group = generalEntityDao.findEntity(
                "OptionGroup.findByValueWithOptionItems",
                Pair.of("value", searchValue)
        );
        OptionGroup entity = syncItemsAllowingValueChange(searchValue, group, optionItemCostGroupDto);
        generalEntityDao.mergeEntity(entity);
    }

    @Transactional
    @Override
    public void removeEntity(String value) {
        OptionGroup fetchedEntity = generalEntityDao.findEntity("OptionGroup.findByValue", Pair.of(VALUE, value));
        generalEntityDao.removeEntity(fetchedEntity);
    }

    @Override
    public OptionGroup getEntity(String value) {
        return generalEntityDao.findEntity("OptionGroup.findByValueWithOptionItems", Pair.of(VALUE, value));
    }

    @Override
    public Optional<OptionGroup> getEntityOptional(String value) {
        OptionGroup entity = generalEntityDao.findEntity("OptionGroup.findByValueWithOptionItems", Pair.of(VALUE, value));
        return Optional.of(entity);
    }

    @Override
    public List<OptionGroup> getEntities() {
        return generalEntityDao.findEntityList("OptionGroup.withOptionItems", (Pair<String, ?>) null);
    }

    private OptionGroup syncItemsAllowingValueChange(String searchValue, OptionGroup group, OptionItemCostGroupDto dto) {
        if (group == null) {
            throw new IllegalArgumentException("OptionGroup not found by old value: " + searchValue);
        }

        copyUpdatableFields(group, dto);

        final Map<String, OptionItemCost> currentByValue = group.getOptionItemCosts().stream()
                .filter(Objects::nonNull)
                .filter(t -> t.getValue() != null)
                .collect(toMap(OptionItemCost::getValue, Function.identity(), (a, b) -> a, LinkedHashMap::new));

        final Set<OptionItemCost> matchedExisting = new HashSet<>();
        final List<OptionItemCostDto> incoming = Optional.ofNullable(dto.getOptionItemCosts())
                .orElseGet(Collections::emptyList);

        // UPDATE EXISTING
        incoming.forEach(itemDto -> {
            if (itemDto == null) return;
            final String lookupKey = itemDto.getSearchValue() != null ? itemDto.getSearchValue() : itemDto.getValue();
            OptionItemCost existing = lookupKey == null ? null : currentByValue.get(lookupKey);

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
                        OptionItemCost created = OptionItemCost.builder()
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
                        group.addOptionItemCost(created);
                        currentByValue.put(newVal, created);
                        matchedExisting.add(created);
                    }
                });

        // REMOVE ORPHANS (unchanged)
        group.getOptionItemCosts().stream()
                .filter(Objects::nonNull)
                .filter(t -> !matchedExisting.contains(t))
                .toList()
                .forEach(group::removeOptionItemCost);

        return group;
    }

    private void copyUpdatableFields(OptionGroup target, OptionItemCostGroupDto source) {
        if (source.getLabel() != null) {
            target.setLabel(source.getLabel());
        }
        if (source.getValue() != null) {
            target.setValue(source.getValue());
        }
    }
}
