package com.b2c.prototype.manager.option.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.manager.option.IOptionItemCostGroupManager;
import com.b2c.prototype.modal.dto.payload.option.group.OptionItemCostGroupDto;
import com.b2c.prototype.modal.dto.payload.option.item.OptionItemCostDto;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItemCost;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.transform.constant.IGeneralEntityTransformService;
import com.b2c.prototype.transform.item.IItemTransformService;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
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
public class OptionItemCostGroupManager implements IOptionItemCostGroupManager {

    private final IGeneralEntityDao generalEntityDao;
    private final IItemTransformService itemTransformService;
    private final IGeneralEntityTransformService generalEntityTransformService;

    public OptionItemCostGroupManager(IGeneralEntityDao generalEntityDao,
                                      IItemTransformService itemTransformService,
                                      IGeneralEntityTransformService generalEntityTransformService) {
        this.generalEntityDao = generalEntityDao;
        this.itemTransformService = itemTransformService;
        this.generalEntityTransformService = generalEntityTransformService;
    }

    @Transactional
    @Override
    public void persistEntity(OptionItemCostGroupDto optionItemCostGroupDto) {
        OptionGroup entity = itemTransformService.mapOptionItemCostGroupDtoToOptionGroup(optionItemCostGroupDto);
        generalEntityDao.persistEntity(entity);
    }

    @Transactional
    @Override
    public void mergeEntity(String searchValue, OptionItemCostGroupDto optionItemCostGroupDto) {
        OptionGroup group = generalEntityDao.findEntity(
                "OptionGroup.findByValueWithOptionItems",
                Pair.of(KEY, searchValue)
        );
        OptionGroup entity = syncItemsAllowingKeyChange(searchValue, group, optionItemCostGroupDto);
        generalEntityDao.mergeEntity(entity);
    }

    @Transactional
    @Override
    public void removeEntity(String value) {
        OptionGroup fetchedEntity = generalEntityDao.findEntity("OptionGroup.findByKey", Pair.of(KEY, value));
        generalEntityDao.removeEntity(fetchedEntity);
    }

    @Override
    public OptionGroup getEntity(String value) {
        return generalEntityDao.findEntity("OptionGroup.findByValueWithOptionItems", Pair.of(KEY, value));
    }

    @Override
    public Optional<OptionGroup> getEntityOptional(String value) {
        OptionGroup entity = generalEntityDao.findEntity("OptionGroup.findByValueWithOptionItems", Pair.of(KEY, value));
        return Optional.of(entity);
    }

    @Override
    public List<OptionGroup> getEntities() {
        return generalEntityDao.findEntityList("OptionGroup.withOptionItems", (Pair<String, ?>) null);
    }

    private OptionGroup syncItemsAllowingKeyChange(String searchValue, OptionGroup group, OptionItemCostGroupDto dto) {
        if (group == null) {
            throw new IllegalArgumentException("OptionGroup not found by old value: " + searchValue);
        }

        copyUpdatableFields(group, dto);

        final Map<String, OptionItemCost> currentByValue = group.getOptionItemCosts().stream()
                .filter(Objects::nonNull)
                .filter(opi -> opi.getKey() != null)
                .collect(toMap(OptionItemCost::getKey, Function.identity(), (a, b) -> a, LinkedHashMap::new));

        final Set<OptionItemCost> matchedExisting = Collections.newSetFromMap(new IdentityHashMap<>());
        final List<OptionItemCostDto> incoming = Optional.ofNullable(dto.getOptionItemCosts())
                .orElseGet(Collections::emptyList);

        // UPDATE EXISTING
        incoming.forEach(itemDto -> {
            if (itemDto == null) return;
            final String lookupKey = itemDto.getSearchKey() != null
                    ? itemDto.getSearchKey()
                    : itemDto.getKey();

            OptionItemCost existing = null;
            if (lookupKey != null) {
                existing = currentByValue.get(lookupKey);
            }

            if (existing != null) {
                if (itemDto.getKey() != null && !itemDto.getKey().equals(existing.getKey())) {
                    if (currentByValue.containsKey(itemDto.getKey()) && currentByValue.get(itemDto.getKey()) != existing) {
                        throw new IllegalStateException("Option item cost value collision on rename: " + itemDto.getKey());
                    }
                    currentByValue.remove(existing.getKey());
                    existing.setKey(itemDto.getKey());
                    currentByValue.put(existing.getKey(), existing);
                }
                if (itemDto.getValue() != null) existing.setValue(itemDto.getValue());
                if (itemDto.getKey() != null) existing.setKey(itemDto.getKey());

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
                        p.setCurrency(generalEntityTransformService.mapCurrencyDtoToCurrency(itemDto.getPrice().getCurrency())
                        );
                    }
                }

                matchedExisting.add(existing);
            }
        });

        // CREATE NEW (unchanged from your code)
        incoming.stream()
                .filter(Objects::nonNull)
                .filter(d -> d.getSearchKey() == null)
                .forEach(d -> {
                    final String newKey = d.getKey();
                    if (newKey == null || newKey.trim().isEmpty()) {
                        throw new IllegalArgumentException("New Option item cost must have non-null 'key'.");
                    }
                    if (!currentByValue.containsKey(newKey)) {
                        Price price = null;
                        if (d.getPrice() != null) {
                            price = Price.builder()
                                    .amount(d.getPrice().getAmount())
                                    .currency(
                                            generalEntityDao.findEntity(
                                                    "Currency.findByKey",
                                                    Pair.of(KEY, d.getPrice().getCurrency().getKey())
                                            )
                                    )
                                    .build();
                        }

                        OptionItemCost created = OptionItemCost.builder()
                                .key(newKey)
                                .value(d.getValue())
                                .price(price)
                                .build();
                        group.addOptionItemCost(created);
                        currentByValue.put(newKey, created);
                        matchedExisting.add(created);
                    }
                });

        group.getOptionItemCosts().stream()
                .filter(Objects::nonNull)
                .filter(t -> !matchedExisting.contains(t))
                .toList()
                .forEach(group::removeOptionItemCost);

        return group;
    }

    private void copyUpdatableFields(OptionGroup target, OptionItemCostGroupDto source) {
        if (source.getValue() != null) {
            target.setValue(source.getValue());
        }
        if (source.getKey() != null) {
            target.setKey(source.getKey());
        }
    }
}
