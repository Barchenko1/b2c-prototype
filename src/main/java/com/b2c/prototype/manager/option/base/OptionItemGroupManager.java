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
        OptionGroup entity = syncItemsAllowingKeyChange(searchValue, group, optionItemGroupDto);
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

    private OptionGroup syncItemsAllowingKeyChange(String searchValue, OptionGroup group, OptionItemGroupDto dto) {
        if (group == null) {
            throw new IllegalArgumentException("OptionGroup not found by old value: " + searchValue);
        }

        copyUpdatableFields(group, dto);

        final Map<String, OptionItem> currentByValue = group.getOptionItems().stream()
                .filter(Objects::nonNull)
                .filter(oi -> oi.getKey() != null)
                .collect(toMap(OptionItem::getKey, Function.identity(), (a, b) -> a, LinkedHashMap::new));

        final Set<OptionItem> matchedExisting = Collections.newSetFromMap(new IdentityHashMap<>());
        final List<OptionItemDto> incoming = Optional.ofNullable(dto.getOptionItems())
                .orElseGet(Collections::emptyList);

        incoming.forEach(itemDto -> {
            if (itemDto == null) return;

            final String lookupKey = itemDto.getSearchKey() != null
                    ? itemDto.getSearchKey()
                    : itemDto.getKey();

            OptionItem existing = null;
            if (lookupKey != null) {
                existing = currentByValue.get(lookupKey);
            }

            if (existing != null) {
                if (itemDto.getKey() != null && !itemDto.getKey().equals(existing.getKey())) {
                    if (currentByValue.containsKey(itemDto.getKey()) && currentByValue.get(itemDto.getKey()) != existing) {
                        throw new IllegalStateException("OptionItem value collision on rename: " + itemDto.getKey());
                    }
                    currentByValue.remove(existing.getKey());
                    existing.setKey(itemDto.getKey());
                    currentByValue.put(existing.getKey(), existing);
                }
                if (itemDto.getValue() != null) existing.setValue(itemDto.getValue());
                if (itemDto.getKey() != null) existing.setKey(itemDto.getKey());

                matchedExisting.add(existing);
            }
        });

        incoming.stream()
                .filter(Objects::nonNull)
                .filter(d -> d.getSearchKey() == null)
                .forEach(d -> {
                    final String newKey = d.getKey();
                    if (newKey == null || newKey.trim().isEmpty()) {
                        throw new IllegalArgumentException("New OptionItem must have non-null 'value'.");
                    }
                    if (!currentByValue.containsKey(newKey)) {
                        OptionItem created = OptionItem.builder()
                                .value(d.getValue())
                                .key(newKey)
                                .build();
                        group.addOptionItem(created);
                        currentByValue.put(newKey, created);
                        matchedExisting.add(created);
                    }
                });

        group.getOptionItems().stream()
                .filter(Objects::nonNull)
                .filter(oi -> !matchedExisting.contains(oi))
                .toList()
                .forEach(group::removeOptionItem);

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
