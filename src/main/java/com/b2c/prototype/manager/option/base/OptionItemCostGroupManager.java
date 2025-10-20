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

        // 2) Update group's own basic fields (assuming AbstractConstantDto carries label/value)
        copyUpdatableFields(group, dto);

        // 3) Build lookup for existing items by their CURRENT value
        final Map<String, OptionItemCost> currentByValue = group.getOptionItemCosts().stream()
                .filter(Objects::nonNull)
                .filter(oi -> oi.getValue() != null)
                .collect(toMap(OptionItemCost::getValue, Function.identity(), (a, b) -> a, LinkedHashMap::new));

        // 4) Track which existing items we kept/updated
        final Set<OptionItemCost> matchedExisting = new HashSet<>();

        // Defensive: normalize incoming list
        final List<OptionItemCostDto> incoming = Optional.ofNullable(dto.getOptionItemCosts())
                .orElseGet(Collections::emptyList);

        // 5) First pass: update existing items (identified by searchValue if present, otherwise by value)
        incoming.forEach(itemDto -> {
            if (itemDto == null) return;

            final String lookupKey = itemDto.getSearchValue() != null
                    ? itemDto.getSearchValue()
                    : itemDto.getValue(); // fall back to its current value

            OptionItemCost existing = null;
            if (lookupKey != null) {
                existing = currentByValue.get(lookupKey);
            }

            if (existing != null) {
                // Update fields
                if (itemDto.getLabel() != null) existing.setLabel(itemDto.getLabel());
                if (itemDto.getValue() != null && !itemDto.getValue().equals(existing.getValue())) {
                    // Guard: avoid collision after rename
                    if (currentByValue.containsKey(itemDto.getValue()) && currentByValue.get(itemDto.getValue()) != existing) {
                        throw new IllegalStateException("OptionItem value collision on rename: " + itemDto.getValue());
                    }
                    // Update maps to keep them consistent for later lookups
                    currentByValue.remove(existing.getValue());
                    existing.setValue(itemDto.getValue());
                    currentByValue.put(existing.getValue(), existing);
                }

                matchedExisting.add(existing);
            }
        });

        // 6) Second pass: create brand-new items (searchValue == null AND no existing with that value)
        incoming.stream()
                .filter(Objects::nonNull)
                .filter(d -> d.getSearchValue() == null)           // explicit "new"
                .forEach(d -> {
                    final String newVal = d.getValue();
                    if (newVal == null || newVal.trim().isEmpty()) {
                        throw new IllegalArgumentException("New OptionItem must have non-null 'value'.");
                    }
                    // If already updated an existing to this value in step 5, skip creating duplicate
                    if (!currentByValue.containsKey(newVal)) {
                        OptionItemCost created = OptionItemCost.builder()
                                .label(d.getLabel())
                                .value(newVal)
                                .price(Price.builder()
                                        .amount(d.getPrice().getAmount())
                                        .currency(generalEntityDao.findEntity("Currency.findByValue", Pair.of("value", d.getPrice().getCurrency().getValue())))
                                        .build())
                                .build();
                        group.addOptionItemCost(created);               // maintains both sides
                        currentByValue.put(newVal, created);
                        matchedExisting.add(created);
                    }
                });

        // 7) Remove items that are no longer present in the DTO (orphanRemoval=true will delete)
        final List<OptionItemCost> toRemove = group.getOptionItemCosts().stream()
                .filter(Objects::nonNull)
                .filter(oi -> !matchedExisting.contains(oi))
                .toList();

        toRemove.forEach(group::removeOptionItemCost);

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
