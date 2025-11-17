package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.payload.discount.DiscountDto;
import com.b2c.prototype.modal.dto.payload.discount.DiscountGroupDto;
import com.b2c.prototype.modal.dto.payload.discount.DiscountStatusDto;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.manager.item.IDiscountGroupManager;
import com.b2c.prototype.modal.entity.item.DiscountGroup;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.region.Region;
import com.b2c.prototype.transform.item.IItemTransformService;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import java.util.stream.Collectors;

import static com.b2c.prototype.util.Constant.ARTICULAR_ID;
import static com.b2c.prototype.util.Constant.CODE;
import static com.b2c.prototype.util.Constant.KEY;

@Service
public class DiscountGroupManager implements IDiscountGroupManager {

    private final IGeneralEntityDao generalEntityDao;
    private final IItemTransformService itemTransformService;

    public DiscountGroupManager(IGeneralEntityDao generalEntityDao,
                                IItemTransformService itemTransformService) {
        this.generalEntityDao = generalEntityDao;
        this.itemTransformService = itemTransformService;
    }

    @Override
    @Transactional
    public void saveDiscountGroup(DiscountGroupDto discountGroupDto) {
        DiscountGroup discountGroup = itemTransformService.mapDiscountGroupDtoToDiscountGroup(discountGroupDto);
        generalEntityDao.persistEntity(discountGroup);
    }

    @Override
    @Transactional
    public void updateArticularDiscount(String articularId, DiscountGroupDto discountGroupDto) {
        ArticularItem articularItem = generalEntityDao.findEntity(
                "ArticularItem.discount.currency",
                Pair.of(ARTICULAR_ID, articularId));

//        Discount discount = (Discount) Optional.ofNullable(discountGroupDto.ge())
//                .flatMap(code -> generalEntityDao.findOptionEntity(
//                        "Discount.currency",
//                        Pair.of(CHAR_SEQUENCE_CODE, code)))
//                .orElseGet(() -> itemTransformService.mapDiscountGroupDtoToDiscountGroup(discountDto));

//        if (articularItem.getDiscount() != null) {
//            discount.setId(articularItem.getDiscount().getId());
//        }

//        articularItem.setDiscount(discount);
        generalEntityDao.mergeEntity(articularItem);
    }

    @Override
    @Transactional
    public void updateDiscountGroup(String region, String key, DiscountGroupDto discountGroupDto) {
        DiscountGroup discountGroup = generalEntityDao.findEntity(
                "DiscountGroup.findByRegionAndKey",
                List.of(Pair.of(CODE, region), Pair.of(KEY, key)));
        DiscountGroup entity = syncItemsAllowingKeyChange(key, discountGroup, discountGroupDto);
        generalEntityDao.mergeEntity(entity);
    }

    @Override
    @Transactional
    public void changeDiscountStatus(DiscountStatusDto discountStatusDto) {
        DiscountGroup discountGroup = generalEntityDao.findEntity(
                "DiscountGroup.findByRegionAndKey",
                List.of(Pair.of(KEY, discountStatusDto.getGroupCode()),
                        Pair.of(CODE, discountStatusDto.getRegion())));

        discountGroup.getDiscounts().stream()
                .filter(d -> d.getCharSequenceCode().equals(discountStatusDto.getCharSequenceCode()))
                .findFirst()
                .ifPresent(d -> d.setActive(discountStatusDto.isActive()));

        generalEntityDao.mergeEntity(discountGroup);
    }

    @Override
    @Transactional
    public void removeDiscountGroup(String region, String key) {
        generalEntityDao.findAndRemoveEntity("DiscountGroup.findByRegionAndKey",
                List.of(Pair.of(CODE, region), Pair.of(KEY, key)));
    }

    @Override
    @Transactional(readOnly = true)
    public DiscountGroupDto getDiscountGroup(String region, String key) {
        DiscountGroup discountGroup = generalEntityDao.findEntity(
                "DiscountGroup.findByRegionAndKey",
                List.of(Pair.of(CODE, region), Pair.of(KEY, key)));
        return Optional.of(discountGroup)
                .map(itemTransformService::mapDiscountGroupToDiscountGroupDto)
                .orElseThrow(() -> new RuntimeException(""));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiscountGroupDto> getDiscountGroups(String region) {
        List<DiscountGroup> discountGroupList = generalEntityDao.findEntityList(
                "DiscountGroup.findAllByRegion", Pair.of(CODE, region));

        return discountGroupList.stream()
                .map(itemTransformService::mapDiscountGroupToDiscountGroupDto)
                .toList();
    }

    private DiscountGroup syncItemsAllowingKeyChange(String searchKey,
                                                     DiscountGroup group,
                                                     DiscountGroupDto dto) {
        if (group == null) {
            throw new IllegalArgumentException("DiscountGroup not found by key: " + searchKey);
        }

        copyUpdatableFields(group, dto);

        // Map current discounts by charSequenceCode
        final Map<String, Discount> currentByCode = group.getDiscounts().stream()
                .filter(Objects::nonNull)
                .filter(d -> d.getCharSequenceCode() != null)
                .collect(Collectors.toMap(
                        Discount::getCharSequenceCode,
                        Function.identity(),
                        (a, b) -> a,
                        LinkedHashMap::new
                ));

        final Set<Discount> matched = Collections.newSetFromMap(new IdentityHashMap<>());
        final List<DiscountDto> incoming = Optional.ofNullable(dto.getDiscounts())
                .orElseGet(Collections::emptyList);

        // UPDATE EXISTING (with optional rename) + fields
        incoming.forEach(itemDto -> {
            if (itemDto == null) return;
            final String lookup = itemDto.getSearchKey() != null ? itemDto.getSearchKey() : itemDto.getCharSequenceCode();
            Discount existing = (lookup == null) ? null : currentByCode.get(lookup);

            if (existing != null) {
                // Rename if needed (guard collisions)
                if (itemDto.getCharSequenceCode() != null
                        && !itemDto.getCharSequenceCode().equals(existing.getCharSequenceCode())) {

                    final String newCode = itemDto.getCharSequenceCode();
                    final Discount collision = currentByCode.get(newCode);
                    if (collision != null && collision != existing) {
                        throw new IllegalStateException(
                                "Discount code collision on rename: " + newCode);
                    }
                    currentByCode.remove(existing.getCharSequenceCode());
                    existing.setCharSequenceCode(newCode);
                    currentByCode.put(existing.getCharSequenceCode(), existing);
                }

                // Update simple fields
                if (itemDto.getAmount() != 0) {
                    existing.setAmount(itemDto.getAmount());
                }
                existing.setActive(itemDto.isActive());
                existing.setPercent(itemDto.isPercent());

                // Currency according to rule: percent -> null; fixed -> must set
                if (existing.isPercent()) {
                    existing.setCurrency(null);
                } else {
                    // fixed amount discount needs currency
                    if (itemDto.getCurrency() != null && itemDto.getCurrency().getKey() != null) {
                        existing.setCurrency(generalEntityDao.findEntity(
                                "Currency.findByKey", Pair.of(KEY, itemDto.getCurrency().getKey())));
                    } else if (existing.getCurrency() == null) {
                        throw new IllegalStateException(
                                "Fixed-amount discount requires currency");
                    }
                }

                matched.add(existing);
            }
        });

        // CREATE NEW (when there is no searchCharSequenceCode)
        incoming.stream()
                .filter(Objects::nonNull)
                .filter(d -> d.getSearchKey() == null)  // new entities
                .forEach(d -> {
                    final String code = d.getCharSequenceCode();
                    if (code == null || code.trim().isEmpty()) {
                        throw new IllegalArgumentException(
                                "New Discount must have non-null 'charSequenceCode'.");
                    }
                    if (!currentByCode.containsKey(code)) {
                        final Currency currency = d.isPercent()
                                ? null
                                : (d.getCurrency() != null && d.getCurrency().getKey() != null
                                ? generalEntityDao.findEntity(
                                "Currency.findByKey", Pair.of("key", d.getCurrency().getKey()))
                                : null);

                        if (!d.isPercent() && currency == null) {
                            throw new IllegalStateException(
                                    "Fixed-amount discount requires currency");
                        }

                        Discount created = Discount.builder()
                                .charSequenceCode(code)
                                .amount(d.getAmount())
                                .isActive(d.isActive())
                                .isPercent(d.isPercent())
                                .currency(currency)
                                .build();

                        group.addDiscount(created); // maintains both sides
                        currentByCode.put(code, created);
                        matched.add(created);
                    }
                });

        // REMOVE ORPHANS
        group.getDiscounts().stream()
                .filter(Objects::nonNull)
                .filter(d -> !matched.contains(d))
                .toList()
                .forEach(group::removeDiscount);

        return group;
    }

    private void copyUpdatableFields(DiscountGroup target, DiscountGroupDto source) {
        if (source.getValue() != null) {
            target.setValue(source.getValue());
        }
        if (source.getKey() != null) {
            target.setKey(source.getKey());
        }
        // If region is editable:
//        if (source.getRegionCode() != null) {
//            Region region = generalEntityDao.findEntity("Region.findByCode",
//                    Pair.of(CODE, source.getRegionCode()));
//            target.setRegion(region);
//        }
    }


}
