package com.b2c.prototype.transform.store;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.constant.CountType;
import com.b2c.prototype.modal.dto.payload.item.request.ArticularGroupRequestDto;
import com.b2c.prototype.modal.dto.payload.item.request.ArticularItemAssignmentDto;
import com.b2c.prototype.modal.dto.payload.item.request.StoreArticularItemRequestDto;
import com.b2c.prototype.modal.dto.payload.item.response.GroupOptionKeys;
import com.b2c.prototype.modal.dto.payload.item.request.StoreArticularGroupRequestDto;
import com.b2c.prototype.modal.dto.payload.item.StoreDiscount;
import com.b2c.prototype.modal.dto.payload.item.StoreDiscountGroup;
import com.b2c.prototype.modal.dto.payload.item.StoreOptionItemCostGroup;
import com.b2c.prototype.modal.dto.payload.item.StoreOptionItemGroup;
import com.b2c.prototype.modal.dto.payload.item.StoreOptionItemGroupTransfer;
import com.b2c.prototype.modal.dto.payload.item.response.StoreRequestDto;
import com.b2c.prototype.modal.dto.payload.option.item.OptionItemCostDto;
import com.b2c.prototype.modal.dto.payload.option.item.OptionItemDto;
import com.b2c.prototype.modal.entity.item.ArticularGroup;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.item.ArticularItemQuantity;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.item.DiscountGroup;
import com.b2c.prototype.modal.entity.item.Item;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.modal.entity.option.OptionItemCost;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.modal.entity.tenant.Tenant;
import com.b2c.prototype.modal.entity.store.ArticularStock;
import com.b2c.prototype.modal.entity.store.AvailabilityStatus;
import com.b2c.prototype.modal.entity.store.Store;
import com.b2c.prototype.modal.entity.store.StoreGeneralBoard;
import com.b2c.prototype.service.generator.IKeyGeneratorService;
import com.b2c.prototype.transform.constant.IGeneralEntityTransformService;
import com.b2c.prototype.transform.transform.StoreArticularGroupTransform;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.b2c.prototype.util.Constant.ARTICULAR_GROUP_ID;
import static com.b2c.prototype.util.Constant.CODE;
import static com.b2c.prototype.util.Constant.KEY;

@Service
public class StoreArticularGroupTransformService implements IStoreArticularGroupTransformService {

    private final IGeneralEntityDao generalEntityDao;
    private final IGeneralEntityTransformService generalEntityTransformService;
    private final IKeyGeneratorService keyGeneratorService;

    public StoreArticularGroupTransformService(IGeneralEntityDao generalEntityDao,
                                               IGeneralEntityTransformService generalEntityTransformService,
                                               IKeyGeneratorService keyGeneratorService) {
        this.generalEntityDao = generalEntityDao;
        this.generalEntityTransformService = generalEntityTransformService;
        this.keyGeneratorService = keyGeneratorService;
    }


    @Override
    public Discount mapStoreDiscountToDiscount(StoreDiscount storeDiscount) {
        return Discount.builder()
                .charSequenceCode(storeDiscount.getCharSequenceCode())
                .amount(storeDiscount.getAmount())
                .isPercent(storeDiscount.isPercent())
                .isActive(storeDiscount.isActive())
                .currency(storeDiscount.getCurrency() != null
                        ? generalEntityTransformService.mapCurrencyDtoToCurrency(storeDiscount.getCurrency())
                        : null)
                .build();
    }

    @Override
    public DiscountGroup mapStoreDiscountGroupDtoToDiscountGroup(Tenant tenant, StoreDiscountGroup dto) {
        if (dto.getKey() != null) {
            DiscountGroup existing = generalEntityDao.findEntity(
                    "DiscountGroup.findByRegionAndKey",
                    List.of(
                            Pair.of(CODE, tenant.getCode()),
                            Pair.of(KEY, dto.getKey())
                    )
            );

            Map<String, Discount> existingByCode = existing.getDiscounts().stream()
                    .collect(Collectors.toMap(Discount::getCharSequenceCode, Function.identity()));

            dto.getDiscounts().values().stream()
                    .map(sd -> existingByCode.getOrDefault(sd.getCharSequenceCode(), mapStoreDiscountToDiscount(sd)))
                    .sorted(Comparator.comparing(Discount::getCharSequenceCode))
                    .forEach(existing::addDiscount);

            return existing;
        }

        DiscountGroup created = DiscountGroup.builder()
                .key(keyGeneratorService.generateKey("discount_group"))
                .value(dto.getValue())
                .tenant(tenant)
                .build();

        dto.getDiscounts().values().stream()
                .map(this::mapStoreDiscountToDiscount)
                .sorted(Comparator.comparing(Discount::getCharSequenceCode))
                .forEach(created::addDiscount);

        return created;
    }

    private OptionItem mapOptionItemDtoToOptionItem(OptionItemDto dto) {
        return OptionItem.builder()
                .value(dto.getValue())
                .key(keyGeneratorService.generateKey("option_item"))
                .build();
    }

    private OptionItemCost mapOptionItemDtoToOptionItemCost(OptionItemCostDto dto) {
        return OptionItemCost.builder()
                .value(dto.getValue())
                .key(keyGeneratorService.generateKey("option_item"))
                .price(generalEntityTransformService.mapPriceDtoToPrice(dto.getPrice()))
                .build();
    }

    @Override
    public OptionGroup mapStoreOptionItemGroupDtoToOptionGroup(Tenant tenant, StoreOptionItemGroup optionItemGroupDto) {
        if (optionItemGroupDto.getKey() != null) {
            OptionGroup existing = generalEntityDao.findEntity(
                    "OptionGroup.findByRegionAndKey",
                    List.of(
                            Pair.of(CODE, tenant.getCode()),
                            Pair.of(KEY, optionItemGroupDto.getKey())));

            Map<String, OptionItem> existingByCode = existing.getOptionItems().stream()
                    .collect(Collectors.toMap(OptionItem::getKey, Function.identity()));

            optionItemGroupDto.getOptionItems().values().stream()
                    .map(sd -> existingByCode.getOrDefault(sd.getKey(), mapOptionItemDtoToOptionItem(sd)))
                    .sorted(Comparator.comparing(OptionItem::getKey))
                    .forEach(existing::addOptionItem);

            return existing;
        }

        OptionGroup created = OptionGroup.builder()
                .key(keyGeneratorService.generateKey("discount_group"))
                .value(optionItemGroupDto.getValue())
                .tenant(tenant)
                .build();

        optionItemGroupDto.getOptionItems().values().stream()
                .map(this::mapOptionItemDtoToOptionItem)
                .sorted(Comparator.comparing(OptionItem::getKey))
                .forEach(created::addOptionItem);

        return created;
    }

    @Override
    public StoreOptionItemGroupTransfer mapStoreOptionItemGroupDtoToOptionItemSet(Tenant tenant, Map.Entry<String, StoreOptionItemGroup> storeOptionItemGroup) {
        OptionGroup optionGroup;
        Map<String, OptionItem> optionItems;

        if (storeOptionItemGroup.getValue().getKey() != null) {
            OptionGroup existing = generalEntityDao.findEntity(
                    "OptionGroup.findByRegionAndKey",
                    List.of(
                            Pair.of(CODE, tenant.getCode()),
                            Pair.of(KEY, storeOptionItemGroup.getValue().getKey())));

            Map<String, OptionItem> existingByKey = existing.getOptionItems().stream()
                    .collect(Collectors.toMap(OptionItem::getKey, Function.identity()));

            storeOptionItemGroup.getValue().getOptionItems().values().stream()
                    .map(sd -> existingByKey.getOrDefault(sd.getKey(), mapOptionItemDtoToOptionItem(sd)))
                    .sorted(Comparator.comparing(OptionItem::getKey))
                    .forEach(existing::addOptionItem);

            optionGroup = existing;
            optionItems = existing.getOptionItems().stream().collect(Collectors.toMap(
                    OptionItem::getKey,
                    Function.identity()
            ));
        } else {
            OptionGroup created = mapStoreOptionItemGroupDtoToOptionGroup(tenant, storeOptionItemGroup.getValue());

            Map<String, OptionItem> newItems = Optional.ofNullable(storeOptionItemGroup.getValue().getOptionItems())
                    .map(items -> items.entrySet().stream()
                            .collect(Collectors.toMap(
                                    Map.Entry::getKey,
                                    e -> mapOptionItemDtoToOptionItem(e.getValue())
                            )))
                    .orElseGet(Collections::emptyMap);

            newItems.values().stream()
                    .sorted(Comparator.comparing(OptionItem::getKey))
                    .forEach(created::addOptionItem);

            optionGroup = created;
            optionItems = newItems;
        }

        return StoreOptionItemGroupTransfer.builder()
                .optionGroup(Map.of(storeOptionItemGroup.getKey(), optionGroup))
                .optionItems(optionItems)
                .build();
    }

//    @Override
//    public StoreOptionItemGroupTransfer mapStoreOptionItemGroupDtoToOptionItemSet(Tenant tenant, Map.Entry<String, StoreOptionItemGroup> storeOptionItemGroup) {
//        Map<String, OptionItem> optionItems =
//                Optional.ofNullable(storeOptionItemGroup.getValue().getOptionItems())
//                        .map(items -> items.entrySet()
//                                .stream()
//                                .collect(Collectors.toMap(
//                                        Map.Entry::getKey,
//                                        e -> (OptionItem) OptionItem.builder()
//                                                .value(e.getValue().getValue())
//                                                .key(keyGeneratorService.generateKey("option_item"))
//                                                .build()
//                                )))
//                        .orElseGet(Collections::emptyMap);
//
//        Map<String, OptionGroup> optionGroup = Map.of(
//                storeOptionItemGroup.getKey(),
//                OptionGroup.builder()
//                        .value(storeOptionItemGroup.getValue().getValue())
//                        .key(keyGeneratorService.generateKey("option_group"))
//                        .tenant(tenant)
//                        .build());
//
//        optionGroup.values().forEach((og) -> {
//            optionItems.values().forEach(og::addOptionItem);
//        });
//
//        return StoreOptionItemGroupTransfer.builder()
//                .optionGroup(optionGroup)
//                .optionItems(optionItems)
//                .build();
//    }

    @Override
    public StoreOptionItemGroupTransfer mapStoreOptionItemCostGroupDtoToOptionItemSet(Tenant tenant, Map.Entry<String, StoreOptionItemCostGroup> storeOptionItemCostGroup) {
        OptionGroup optionGroup;
        Map<String, OptionItemCost> optionItemCost;

        if (storeOptionItemCostGroup.getValue().getKey() != null) {
            OptionGroup existing = generalEntityDao.findEntity(
                    "OptionGroup.findByRegionAndKey",
                    List.of(
                            Pair.of(CODE, tenant.getCode()),
                            Pair.of(KEY, storeOptionItemCostGroup.getValue().getKey())));

            Map<String, OptionItemCost> existingByKey = existing.getOptionItemCosts().stream()
                    .collect(Collectors.toMap(OptionItemCost::getKey, Function.identity()));

            storeOptionItemCostGroup.getValue().getCostOptions().values().stream()
                    .map(sd -> existingByKey.getOrDefault(sd.getKey(), mapOptionItemDtoToOptionItemCost(sd)))
                    .sorted(Comparator.comparing(OptionItemCost::getKey))
                    .forEach(existing::addOptionItemCost);

            optionGroup = existing;
            optionItemCost = existing.getOptionItemCosts().stream().collect(Collectors.toMap(
                    OptionItemCost::getKey,
                    Function.identity()
            ));
        } else {
            OptionGroup created = mapStoreOptionItemCostGroupDtoToOptionGroup(tenant, storeOptionItemCostGroup.getValue());

            Map<String, OptionItemCost> newItems = Optional.ofNullable(storeOptionItemCostGroup.getValue().getCostOptions())
                    .map(items -> items.entrySet().stream()
                            .collect(Collectors.toMap(
                                    Map.Entry::getKey,
                                    e -> mapOptionItemDtoToOptionItemCost(e.getValue())
                            )))
                    .orElseGet(Collections::emptyMap);

            newItems.values().stream()
                    .sorted(Comparator.comparing(OptionItemCost::getKey))
                    .forEach(created::addOptionItemCost);

            optionGroup = created;
            optionItemCost = newItems;
        }

        return StoreOptionItemGroupTransfer.builder()
                .optionGroup(Map.of(storeOptionItemCostGroup.getKey(), optionGroup))
                .optionItemCosts(optionItemCost)
                .build();
    }

//    @Override
//    public StoreOptionItemGroupTransfer mapStoreOptionItemCostGroupDtoToOptionItemSet(Tenant tenant, Map.Entry<String, StoreOptionItemCostGroup> storeOptionItemCostGroup) {
//        Map<String, OptionItemCost> optionItemCosts =
//                Optional.ofNullable(storeOptionItemCostGroup.getValue().getCostOptions())
//                                .map(costOptions -> costOptions.entrySet()
//                                        .stream()
//                                        .sorted(Comparator.comparing(e -> e.getValue().getValue()))
//                                        .collect(Collectors.toMap(
//                                                Map.Entry::getKey,
//                                                entry -> (OptionItemCost) OptionItemCost.builder()
//                                                        .value(entry.getValue().getValue())
//                                                        .key(keyGeneratorService.generateKey("option_item_cost"))
//                                                        .price(generalEntityTransformService.mapPriceDtoToPrice(entry.getValue().getPrice()))
//                                                        .build(),
//                                                (v1, v2) -> v1
//                                        )))
//                                .orElseGet(Collections::emptyMap);
//
//        Map<String, OptionGroup> optionGroup = Map.of(
//                storeOptionItemCostGroup.getKey(),
//                OptionGroup.builder()
//                        .value(storeOptionItemCostGroup.getValue().getValue())
//                        .key(keyGeneratorService.generateKey("option_group"))
//                        .tenant(tenant)
//                        .build());
//
//        optionGroup.values().forEach((og) -> {
//            optionItemCosts.values().forEach(og::addOptionItemCost);
//        });
//
//        return StoreOptionItemGroupTransfer.builder()
//                .optionGroup(optionGroup)
//                .optionItemCosts(optionItemCosts)
//                .build();
//    }

    @Override
    public OptionGroup mapStoreOptionItemCostGroupDtoToOptionGroup(Tenant tenant, StoreOptionItemCostGroup storeOptionItemCostGroup) {
        OptionGroup optionGroup = OptionGroup.builder()
                .value(storeOptionItemCostGroup.getValue())
                .key(keyGeneratorService.generateKey("option_group"))
                .tenant(tenant)
                .optionItemCosts(storeOptionItemCostGroup.getCostOptions().values().stream()
                        .map(optionItemCostDto -> OptionItemCost.builder()
                                .value(optionItemCostDto.getValue())
                                .key(keyGeneratorService.generateKey("option_item_cost"))
                                .price(Price.builder()
                                        .amount(optionItemCostDto.getPrice().getAmount())
                                        .currency(generalEntityTransformService.mapCurrencyDtoToCurrency(
                                                optionItemCostDto.getPrice().getCurrency()))
                                        .build())
                                .build())
                        .collect(Collectors.toSet())
                )
                .build();

        optionGroup.getOptionItemCosts().forEach(optionGroup::addOptionItemCost);
        return optionGroup;
    }

    @Override
    public StoreArticularGroupTransform mapStoreArticularGroupRequestDtoToStoreArticularGroupTransform(StoreArticularGroupRequestDto storeArticularGroupRequestDto) {
        ArticularGroupRequestDto articularGroupRequestDto = storeArticularGroupRequestDto.getArticularGroup();
        Map<String, ArticularItemAssignmentDto> articularItemAssignmentDtoMap = storeArticularGroupRequestDto.getArticularGroup().getArticularItems();
        Tenant tenant = generalEntityDao.findEntity("Tenant.findByCode",
                Pair.of(CODE, storeArticularGroupRequestDto.getTenantId()));
        ArticularGroup articularGroup = ArticularGroup.builder()
                .tenant(tenant)
                .articularGroupId(keyGeneratorService.generateKey("articular_group"))
                .description(articularGroupRequestDto.getDescription())
                .category((Category) generalEntityDao.findOptionEntity("Category.findByKeyAndRegion",
                                List.of(Pair.of(CODE, storeArticularGroupRequestDto.getTenantId()), Pair.of(KEY, articularGroupRequestDto.getCategory().getKey())))
                        .orElse(Category.builder()
                                .key(articularGroupRequestDto.getCategory().getKey())
                                .value(articularGroupRequestDto.getCategory().getValue())
                                .build()))
                .build();

        return setupStoreArticularGroupTransform(
                articularItemAssignmentDtoMap,
                tenant,
                articularGroup,
                storeArticularGroupRequestDto.getDiscountGroups(),
                storeArticularGroupRequestDto.getOptionItemGroups(),
                storeArticularGroupRequestDto.getOptionItemCostGroups(),
                storeArticularGroupRequestDto.getStoreGroup()
        );
    }

    @Override
    public StoreArticularGroupTransform mapStoreArticularItemRequestDtoToStoreArticularGroupTransform(StoreArticularItemRequestDto storeArticularItemRequestDto) {
        Map<String, ArticularItemAssignmentDto> articularItemAssignmentDtoMap = storeArticularItemRequestDto.getArticularItems();
        Tenant tenant = generalEntityDao.findEntity("Tenant.findByCode",
                Pair.of(CODE, storeArticularItemRequestDto.getTenantId()));
        ArticularGroup articularGroup = generalEntityDao.findEntity(
                "ArticularGroup.findByRegionAndKey",
                List.of(Pair.of(CODE, tenant.getCode()),
                        Pair.of(ARTICULAR_GROUP_ID, storeArticularItemRequestDto.getArticularGroupId())));
        return setupStoreArticularGroupTransform(
                articularItemAssignmentDtoMap,
                tenant,
                articularGroup,
                storeArticularItemRequestDto.getDiscountGroups(),
                storeArticularItemRequestDto.getOptionItemGroups(),
                storeArticularItemRequestDto.getOptionItemCostGroups(),
                storeArticularItemRequestDto.getStoreGroup()
        );
    }

    private Discount getDiscount(Map<String, StoreDiscountGroup> discountGroups,
                                 GroupOptionKeys groupOptionKeys,
                                 Tenant tenant,
                                 Map<String, DiscountGroup> discountGroupMap) {

        if (groupOptionKeys == null
                || groupOptionKeys.getGroupKey() == null
                || groupOptionKeys.getOptionKeys() == null
                || groupOptionKeys.getOptionKeys().isEmpty()) {
            return null;
        }

        String groupKey = groupOptionKeys.getGroupKey();
        String wantedCode = groupOptionKeys.getOptionKeys().get(0);

        StoreDiscountGroup storeDiscountGroup = discountGroups.get(groupKey);
        if (storeDiscountGroup == null) {
            return null;
        }

        return Optional.ofNullable(storeDiscountGroup.getDiscounts().get(wantedCode))
                .map(StoreDiscount::getCharSequenceCode)
                .flatMap(code -> discountGroupMap.values().stream()
                        .flatMap(dg -> dg.getDiscounts().stream())
                        .filter(d -> code.equals(d.getCharSequenceCode()))
                        .findFirst())
                .orElse(null);
    }

    private Set<OptionItem> getOptionItemList(Set<StoreOptionItemGroupTransfer> storeOptionItemGroupTransferSet,
                                               List<GroupOptionKeys> groupOptionKeysList) {

        if (storeOptionItemGroupTransferSet == null
                || storeOptionItemGroupTransferSet.isEmpty()
                || groupOptionKeysList == null
                || groupOptionKeysList.isEmpty()) {
            return Collections.emptySet();
        }

        return groupOptionKeysList.stream()
                .filter(Objects::nonNull)
                .filter(groupOptionKeys ->
                        groupOptionKeys.getGroupKey() != null
                                && groupOptionKeys.getOptionKeys() != null
                                && !groupOptionKeys.getOptionKeys().isEmpty()
                )
                .flatMap(groupOptionKeys -> {
                    String groupKey = groupOptionKeys.getGroupKey();
                    Set<String> wantedCodes = new HashSet<>(groupOptionKeys.getOptionKeys());

                    return storeOptionItemGroupTransferSet.stream()
                            .filter(transfer -> transfer.getOptionGroup().containsKey(groupKey))
                            .flatMap(transfer -> transfer.getOptionItems().entrySet().stream())
                            .filter(entry -> wantedCodes.contains(entry.getKey()))
                            .map(Map.Entry::getValue);
                })
                .collect(Collectors.toSet());
    }

    private Set<OptionItemCost> getOptionItemCostList(Set<StoreOptionItemGroupTransfer> storeOptionItemGroupTransferSet,
                                                       List<GroupOptionKeys> groupOptionKeysList) {

        if (storeOptionItemGroupTransferSet == null
                || storeOptionItemGroupTransferSet.isEmpty()
                || groupOptionKeysList == null
                || groupOptionKeysList.isEmpty()) {
            return Collections.emptySet();
        }

        return groupOptionKeysList.stream()
                .filter(Objects::nonNull)
                .filter(groupOptionKeys ->
                        groupOptionKeys.getGroupKey() != null
                                && groupOptionKeys.getOptionKeys() != null
                                && !groupOptionKeys.getOptionKeys().isEmpty()
                )
                .flatMap(groupOptionKeys -> {
                    String groupKey = groupOptionKeys.getGroupKey();
                    Set<String> wantedCodes = new HashSet<>(groupOptionKeys.getOptionKeys());

                    return storeOptionItemGroupTransferSet.stream()
                            .filter(transfer -> transfer.getOptionGroup().containsKey(groupKey))
                            .flatMap(transfer -> transfer.getOptionItemCosts().entrySet().stream())
                            .filter(entry -> wantedCodes.contains(entry.getKey()))
                            .map(Map.Entry::getValue);
                })
                .collect(Collectors.toSet());
    }

    private StoreArticularGroupTransform setupStoreArticularGroupTransform(
            Map<String, ArticularItemAssignmentDto> articularItemAssignmentDtoMap,
            Tenant tenant,
            ArticularGroup articularGroup,
            Map<String, StoreDiscountGroup> storeDiscountGroupMap,
            Map<String, StoreOptionItemGroup> storeOptionItemGroupMap,
            Map<String, StoreOptionItemCostGroup> storeOptionItemCostGroupMap,
            Map<String, StoreRequestDto> storeRequestDtoMap) {
        Map<String, DiscountGroup> discountGroupMap = Optional.ofNullable(storeDiscountGroupMap)
                .orElseGet(Collections::emptyMap)
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> mapStoreDiscountGroupDtoToDiscountGroup(tenant, e.getValue())
                ));
        Set<StoreOptionItemGroupTransfer> storeOptionItemGroupTransferSet = storeOptionItemGroupMap
                        .entrySet()
                        .stream()
                        .map(group -> mapStoreOptionItemGroupDtoToOptionItemSet(tenant, group))
                        .collect(Collectors.toSet());
        Set<StoreOptionItemGroupTransfer> storeOptionItemCostGroupTransferSet = storeOptionItemCostGroupMap
                        .entrySet()
                        .stream()
                        .map(group -> mapStoreOptionItemCostGroupDtoToOptionItemSet(tenant, group))
                        .collect(Collectors.toSet());
        Map<String, ArticularItem> articularItemMap = new HashMap<>();

        articularItemAssignmentDtoMap.forEach((key, value) -> {
            ArticularItem articular = ArticularItem.builder()
                    .articularGroup(articularGroup)
                    .articularUniqId(keyGeneratorService.generateKey("articular_item"))
                    .productName(value.getProductName())
                    .dateOfCreate(LocalDateTime.now())
                    .status(generalEntityDao.findEntity("ArticularStatus.findByKey",
                            Pair.of(KEY, value.getStatus().getKey())))
                    .fullPrice(generalEntityTransformService.mapPriceDtoToPrice(value.getFullPrice()))
                    .totalPrice(generalEntityTransformService.mapPriceDtoToPrice(value.getTotalPrice()))
                    .discount(getDiscount(storeDiscountGroupMap, value.getDiscountKey(), tenant, discountGroupMap))
                    .build();

            Set<OptionItem> optionItems = getOptionItemList(storeOptionItemGroupTransferSet, value.getOptionKeys());
            Set<OptionItemCost> optionItemCosts = getOptionItemCostList(storeOptionItemCostGroupTransferSet, value.getOptionCostKeys());

            optionItems.forEach(articular::addOptionItem);
            optionItemCosts.forEach(articular::addOptionItemCost);
            articularItemMap.put(key, articular);
        });

        articularItemMap.values().stream()
                .sorted(Comparator.comparing(ArticularItem::getProductName))
                .forEach(articular -> articularGroup.addItem(
                        Item.builder()
                                .articularItem(articular)
                                .build()
                ));

        Set<Store> stores = storeRequestDtoMap.values()
                .stream()
                .map(storeRequestDto ->
                        Store.builder()
                                .tenant(tenant)
                                .isActive(storeRequestDto.isActive())
                                .storeName(storeRequestDto.getStoreName())
                                .storeUniqId(keyGeneratorService.generateKey("store"))
                                .address(generalEntityTransformService
                                        .mapAddressDtoToAddress(storeRequestDto.getAddress()))
                                .articularStocks(storeRequestDto.getStock().entrySet().stream()
                                        .map(articularStockQuantityDtoEntry -> ArticularStock.builder()
                                                .articularItemQuantity(ArticularItemQuantity.builder()
                                                        .articularItem(articularItemMap.get(articularStockQuantityDtoEntry.getKey()))
                                                        .quantity(articularStockQuantityDtoEntry.getValue().getQuantity())
                                                        .build())
                                                .availabilityState(
                                                        generalEntityDao.findEntity("AvailabilityStatus.findByKey",
                                                                Pair.of(KEY, articularStockQuantityDtoEntry.getValue().getAvailabilityStatus().getKey())))
                                                .countType(CountType.valueOf(articularStockQuantityDtoEntry.getValue().getCountType()))
                                                .build()
                                        ).collect(Collectors.toSet()))
                                .build()
                )
                .collect(Collectors.toSet());

        Map<ArticularItem, Integer> qtyByItem = stores.stream()
                .filter(s -> s.getArticularStocks() != null)
                .flatMap(s -> s.getArticularStocks().stream())
                .map(ArticularStock::getArticularItemQuantity)
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(
                        ArticularItemQuantity::getArticularItem,
                        Collectors.summingInt(ArticularItemQuantity::getQuantity)
                ));

        AvailabilityStatus defaultStatus = stores.stream()
                .filter(s -> s.getArticularStocks() != null)
                .flatMap(s -> s.getArticularStocks().stream())
                .map(ArticularStock::getAvailabilityState)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);

        CountType defaultCountType = stores.stream()
                .filter(s -> s.getArticularStocks() != null)
                .flatMap(s -> s.getArticularStocks().stream())
                .map(ArticularStock::getCountType)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(CountType.LIMITED);

        Set<ArticularStock> stocks = qtyByItem.entrySet().stream()
                .map(entry -> {
                    ArticularItem item = entry.getKey();
                    Integer quantity = entry.getValue();

                    ArticularItemQuantity aggregatedQty = ArticularItemQuantity.builder()
                            .articularItem(item)
                            .quantity(quantity)
                            .build();

                    return ArticularStock.builder()
                            .articularItemQuantity(aggregatedQty)
                            .availabilityState(defaultStatus)
                            .countType(defaultCountType)
                            .build();
                })
                .collect(Collectors.toSet());

        StoreGeneralBoard storeGeneralBoard = generalEntityDao.findEntity(
                "StoreGeneralBoard.findByRegion",
                Pair.of(CODE, tenant.getCode()));
        stocks.forEach(storeGeneralBoard::addArticularStock);

        return StoreArticularGroupTransform.builder()
                .articularGroup(articularGroup)
                .storeGeneralBoard(storeGeneralBoard)
                .stores(stores)
                .build();
    }
}
