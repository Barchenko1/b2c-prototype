package com.b2c.prototype.manager.option.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.payload.option.group.OptionItemGroupDto;
import com.b2c.prototype.modal.dto.payload.option.item.OptionItemDto;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.manager.option.IOptionItemGroupManager;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.transform.item.IItemTransformService;
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

import static com.b2c.prototype.util.Constant.KEY;
import static java.util.stream.Collectors.toMap;

@Service
public class OptionItemGroupManager implements IOptionItemGroupManager {

    private final IGeneralEntityDao generalEntityDao;
    private final IItemTransformService itemTransformService;

    public OptionItemGroupManager(IGeneralEntityDao generalEntityDao, IItemTransformService itemTransformService) {
        this.generalEntityDao = generalEntityDao;
        this.itemTransformService = itemTransformService;
    }

    public void persistEntity(OptionItemGroupDto optionItemGroupDto) {
        OptionGroup entity = itemTransformService.mapOptionItemGroupDtoToOptionGroupDto(optionItemGroupDto);
        generalEntityDao.persistEntity(entity);
    }

    @Transactional
    @Override
    public void mergeEntity(String searchValue, OptionItemGroupDto optionItemGroupDto) {
        OptionGroup group = generalEntityDao.findEntity(
                "OptionGroup.findByValueWithOptionItems",
                Pair.of(KEY, searchValue)
        );
        OptionGroup entity = syncItemsAllowingValueChange(searchValue, group, optionItemGroupDto);
        generalEntityDao.mergeEntity(entity);
    }

    @Transactional
    @Override
    public void removeEntity(String value) {
        OptionGroup fetchedEntity = generalEntityDao.findEntity("OptionGroup.findByKey", Pair.of(KEY, value));
        generalEntityDao.removeEntity(fetchedEntity);
    }

    public OptionGroup getEntity(String value) {
        return generalEntityDao.findEntity("OptionGroup.findByValueWithOptionItems", Pair.of(KEY, value));
    }

    public Optional<OptionGroup> getEntityOptional(String value) {
        OptionGroup entity = generalEntityDao.findEntity("OptionGroup.findByValueWithOptionItems", Pair.of(KEY, value));
        return Optional.of(entity);
    }


    public List<OptionGroup> getEntities() {
        return generalEntityDao.findEntityList("OptionGroup.withOptionItems", (Pair<String, ?>) null);
    }

    private OptionGroup syncItemsAllowingValueChange(String searchValue, OptionGroup group, OptionItemGroupDto dto) {
        if (group == null) {
            throw new IllegalArgumentException("OptionGroup not found by old value: " + searchValue);
        }

        // 2) Update group's own basic fields (assuming AbstractConstantDto carries label/value)
        copyUpdatableFields(group, dto);

        // 3) Build lookup for existing items by their CURRENT value
        final Map<String, OptionItem> currentByValue = group.getOptionItems().stream()
                .filter(Objects::nonNull)
                .filter(oi -> oi.getKey() != null)
                .collect(toMap(OptionItem::getKey, Function.identity(), (a, b) -> a, LinkedHashMap::new));

        // 4) Track which existing items we kept/updated
        final Set<OptionItem> matchedExisting = new HashSet<>();

        // Defensive: normalize incoming list
        final List<OptionItemDto> incoming = Optional.ofNullable(dto.getOptionItems())
                .orElseGet(Collections::emptyList);

        // 5) First pass: update existing items (identified by searchValue if present, otherwise by value)
        incoming.forEach(itemDto -> {
            if (itemDto == null) return;

            final String lookupKey = itemDto.getSearchValue() != null
                    ? itemDto.getSearchValue()
                    : itemDto.getKey(); // fall back to its current value

            OptionItem existing = null;
            if (lookupKey != null) {
                existing = currentByValue.get(lookupKey);
            }

            if (existing != null) {
                // Update fields
                if (itemDto.getValue() != null) existing.setValue(itemDto.getValue());
                if (itemDto.getKey() != null && !itemDto.getKey().equals(existing.getKey())) {
                    // Guard: avoid collision after rename
                    if (currentByValue.containsKey(itemDto.getKey()) && currentByValue.get(itemDto.getKey()) != existing) {
                        throw new IllegalStateException("OptionItem value collision on rename: " + itemDto.getKey());
                    }
                    // Update maps to keep them consistent for later lookups
                    currentByValue.remove(existing.getKey());
                    existing.setKey(itemDto.getKey());
                    currentByValue.put(existing.getKey(), existing);
                }

                matchedExisting.add(existing);
            }
        });

        // 6) Second pass: create brand-new items (searchValue == null AND no existing with that value)
        incoming.stream()
                .filter(Objects::nonNull)
                .filter(d -> d.getSearchValue() == null)           // explicit "new"
                .forEach(d -> {
                    final String newKey = d.getKey();
                    if (newKey == null || newKey.trim().isEmpty()) {
                        throw new IllegalArgumentException("New OptionItem must have non-null 'value'.");
                    }
                    // If already updated an existing to this value in step 5, skip creating duplicate
                    if (!currentByValue.containsKey(newKey)) {
                        OptionItem created = OptionItem.builder()
                                .value(d.getValue())
                                .key(newKey)
                                .build();
                        group.addOptionItem(created);               // maintains both sides
                        currentByValue.put(newKey, created);
                        matchedExisting.add(created);
                    }
                });

        // 7) Remove items that are no longer present in the DTO (orphanRemoval=true will delete)
        final List<OptionItem> toRemove = group.getOptionItems().stream()
                .filter(Objects::nonNull)
                .filter(oi -> !matchedExisting.contains(oi))
                .toList();

        toRemove.forEach(group::removeOptionItem);

        return group;
    }

    private void copyUpdatableFields(OptionGroup target, OptionItemGroupDto source) {
        if (source.getValue() != null) {
            target.setValue(source.getValue());
        }
        if (source.getKey() != null) {
            target.setKey(source.getKey());
        }
    }
}
