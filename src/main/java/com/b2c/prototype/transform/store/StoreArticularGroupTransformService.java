package com.b2c.prototype.transform.store;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.constant.CountType;
import com.b2c.prototype.modal.dto.payload.item.ArticularGroupDto;
import com.b2c.prototype.modal.dto.payload.item.ArticularItemAssignmentDto;
import com.b2c.prototype.modal.dto.payload.item.GroupOptionKeys;
import com.b2c.prototype.modal.dto.payload.item.StoreArticularGroupRequestDto;
import com.b2c.prototype.modal.dto.payload.item.StoreDiscount;
import com.b2c.prototype.modal.dto.payload.item.StoreDiscountGroup;
import com.b2c.prototype.modal.dto.payload.item.StoreOptionItemCostGroup;
import com.b2c.prototype.modal.dto.payload.item.StoreOptionItemGroup;
import com.b2c.prototype.modal.dto.payload.item.StoreOptionItemGroupTransfer;
import com.b2c.prototype.modal.dto.payload.option.item.OptionItemDto;
import com.b2c.prototype.modal.entity.item.ArticularGroup;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.item.ArticularItemQuantity;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.item.DiscountGroup;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.modal.entity.option.OptionItemCost;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.modal.entity.region.Region;
import com.b2c.prototype.modal.entity.store.ArticularStock;
import com.b2c.prototype.modal.entity.store.AvailabilityStatus;
import com.b2c.prototype.modal.entity.store.Store;
import com.b2c.prototype.modal.entity.store.StoreGeneralBoard;
import com.b2c.prototype.service.generator.IKeyGeneratorService;
import com.b2c.prototype.transform.constant.IGeneralEntityTransformService;
import com.b2c.prototype.transform.modal.StoreArticularGroupTransform;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public DiscountGroup mapStoreDiscountGroupDtoToDiscountGroup(Region region, StoreDiscountGroup discountGroupDto) {
        DiscountGroup discountGroup = DiscountGroup.builder()
                .key(keyGeneratorService.generateKey("discount_group"))
                .value(discountGroupDto.getValue())
                .region(region)
                .discounts(discountGroupDto.getDiscounts().values().stream()
                        .map(this::mapStoreDiscountToDiscount)
                        .collect(Collectors.toSet())
                )
                .build();

        discountGroup.getDiscounts().forEach(discountGroup::addDiscount);
        return discountGroup;
    }

    @Override
    public OptionGroup mapStoreOptionItemGroupDtoToOptionGroup(Region region, StoreOptionItemGroup optionItemGroupDto) {
        OptionGroup optionGroup = OptionGroup.builder()
                .value(optionItemGroupDto.getValue())
                .key(keyGeneratorService.generateKey("option_group"))
                .region(region)
                .optionItems(optionItemGroupDto.getOptionItems().values().stream()
                        .map(optionItemDto -> OptionItem.builder()
                                .value(optionItemDto.getValue())
                                .key(keyGeneratorService.generateKey("option_item"))
                                .build()
                        )
                        .collect(Collectors.toSet())
                )
                .build();

        optionGroup.getOptionItems().forEach(optionGroup::addOptionItem);
        return optionGroup;
    }

    @Override
    public StoreOptionItemGroupTransfer mapStoreOptionItemGroupDtoToOptionItemSet(Region region, Map.Entry<String, StoreOptionItemGroup> storeOptionItemGroup) {
        Map<String, OptionItem> optionItems =
                Optional.ofNullable(storeOptionItemGroup.getValue().getOptionItems())
                        .map(items -> items.entrySet()
                                .stream()
                                .collect(Collectors.toMap(
                                        Map.Entry::getKey,
                                        e -> (OptionItem) OptionItem.builder()
                                                .value(e.getValue().getValue())
                                                .key(keyGeneratorService.generateKey("option_item"))
                                                .build()
                                )))
                        .orElseGet(Collections::emptyMap);

        Map<String, OptionGroup> optionGroup = Map.of(
                storeOptionItemGroup.getKey(),
                OptionGroup.builder()
                        .value(storeOptionItemGroup.getValue().getValue())
                        .key(keyGeneratorService.generateKey("option_group"))
                        .region(region)
                        .build());

        optionGroup.values().forEach((og) -> {
            optionItems.values().forEach(og::addOptionItem);
        });

        StoreOptionItemGroupTransfer storeOptionItemGroupTransfer = StoreOptionItemGroupTransfer.builder()
//                .value(storeOptionItemGroup.getValue().getValue())
//                .key(keyGeneratorService.generateKey("option_group"))
//                .region(region)
                .optionGroup(optionGroup)
                .optionItems(optionItems)
//                .optionItemCosts(optionItemCosts)
                .build();

        return storeOptionItemGroupTransfer;
    }

    @Override
    public StoreOptionItemGroupTransfer mapStoreOptionItemCostGroupDtoToOptionItemSet(Region region, Map.Entry<String, StoreOptionItemCostGroup> storeOptionItemCostGroup) {
        Map<String, OptionItemCost> optionItemCosts =
                Optional.ofNullable(storeOptionItemCostGroup.getValue().getCostOptions())
                        .map(items -> items.entrySet()
                                .stream()
                                .collect(Collectors.toMap(
                                        Map.Entry::getKey,
                                        entry -> (OptionItemCost) OptionItemCost.builder()
                                                .value(entry.getValue().getValue())
                                                .key(keyGeneratorService.generateKey("option_item_cost"))
                                                .price(generalEntityTransformService.mapPriceDtoToPrice(entry.getValue().getPrice()))
                                                .build()
                                )))
                        .orElseGet(Collections::emptyMap);

        Map<String, OptionGroup> optionGroup = Map.of(
                storeOptionItemCostGroup.getKey(),
                OptionGroup.builder()
                        .value(storeOptionItemCostGroup.getValue().getValue())
                        .key(keyGeneratorService.generateKey("option_group"))
                        .region(region)
                        .build());

        optionGroup.values().forEach((og) -> {
            optionItemCosts.values().forEach(og::addOptionItemCost);
        });

        StoreOptionItemGroupTransfer storeOptionItemGroupTransfer = StoreOptionItemGroupTransfer.builder()
//                .value(storeOptionItemGroup.getValue().getValue())
//                .key(keyGeneratorService.generateKey("option_group"))
//                .region(region)
                .optionGroup(optionGroup)
//                .optionItems(optionItems)
                .optionItemCosts(optionItemCosts)
                .build();

        return storeOptionItemGroupTransfer;
    }

    @Override
    public OptionGroup mapStoreOptionItemCostGroupDtoToOptionGroup(Region region, StoreOptionItemCostGroup storeOptionItemCostGroup) {
        OptionGroup optionGroup = OptionGroup.builder()
                .value(storeOptionItemCostGroup.getValue())
                .key(keyGeneratorService.generateKey("option_group"))
                .region(region)
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
        ArticularGroupDto articularGroupDto = storeArticularGroupRequestDto.getArticularGroup();
        Map<String, ArticularItemAssignmentDto> articularItemAssignmentDtoMap = storeArticularGroupRequestDto.getArticularItems();
        Region region = generalEntityDao.findEntity("Region.findByCode",
                Pair.of(CODE, storeArticularGroupRequestDto.getRegion()));
        Map<String, DiscountGroup> discountGroupMap = Optional.ofNullable(storeArticularGroupRequestDto.getDiscountGroups())
                .orElseGet(Collections::emptyMap)
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> mapStoreDiscountGroupDtoToDiscountGroup(region, e.getValue())
                ));
        Set<StoreOptionItemGroupTransfer> storeOptionItemGroupTransferSet =
                storeArticularGroupRequestDto.getOptionItemGroups()
                        .entrySet()
                        .stream()
                        .map(group -> mapStoreOptionItemGroupDtoToOptionItemSet(region, group))
                        .collect(Collectors.toSet());
        Set<StoreOptionItemGroupTransfer> storeOptionItemCostGroupTransferSet =
                storeArticularGroupRequestDto.getOptionItemCostGroups()
                        .entrySet()
                        .stream()
                        .map(group -> mapStoreOptionItemCostGroupDtoToOptionItemSet(region, group))
                        .collect(Collectors.toSet());
        Map<String, ArticularItem> articularItemMap = new HashMap<>();
        ArticularGroup articularGroup = ArticularGroup.builder()
                .region(region)
                .articularGroupId(keyGeneratorService.generateKey("articular_group"))
                .description(articularGroupDto.getDescription())
                .category((Category) generalEntityDao.findOptionEntity("Category.findByKeyAndRegion",
                                List.of(Pair.of(CODE, storeArticularGroupRequestDto.getRegion()), Pair.of(KEY, articularGroupDto.getCategory().getKey())))
                        .orElse(Category.builder()
                                .key(articularGroupDto.getCategory().getKey())
                                .value(articularGroupDto.getCategory().getValue())
                                .build()))
                .build();
        articularItemAssignmentDtoMap.entrySet().forEach(entry -> {
            ArticularItem articular = ArticularItem.builder()
                    .articularGroup(articularGroup)
                    .articularUniqId(keyGeneratorService.generateKey("articular_item"))
                    .productName(entry.getValue().getProductName())
                    .dateOfCreate(LocalDateTime.now())
                    .status(generalEntityDao.findEntity("ArticularStatus.findByKey",
                            Pair.of(KEY, entry.getValue().getStatus().getKey())))
                    .fullPrice(generalEntityTransformService.mapPriceDtoToPrice(entry.getValue().getFullPrice()))
                    .totalPrice(generalEntityTransformService.mapPriceDtoToPrice(entry.getValue().getTotalPrice()))
                    .discount(getDiscount(storeArticularGroupRequestDto.getDiscountGroups(), entry.getValue().getDiscountKey(), region, discountGroupMap))
                    .build();

            Set<OptionItem> optionItems = getOptionItemList(storeOptionItemGroupTransferSet, entry.getValue().getOptionKeys());
            Set<OptionItemCost> optionItemCosts = getOptionItemCostList(storeOptionItemCostGroupTransferSet, entry.getValue().getOptionCostKeys());

            optionItems.forEach(articular::addOptionItem);
            optionItemCosts.forEach(articular::addOptionItemCost);
            articularGroup.addArticularItem(articular);
            articularItemMap.put(entry.getKey(), articular);
        });

        Set<Store> stores = storeArticularGroupRequestDto.getStores().values()
                .stream()
                .flatMap(List::stream)
                .map(storeRequestDto ->
                        Store.builder()
                                .region(region)
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

        StoreGeneralBoard storeGeneralBoard = StoreGeneralBoard.builder()
                .region(region)
                .articularStocks(stocks)
                .build();

        Set<OptionGroup> optionGroups = new HashSet<>();
//        optionGroups.addAll(optionGroupMap.values());
//        optionGroups.addAll(optionCostGroupMap.values());
        return StoreArticularGroupTransform.builder()
                .optionGroup(optionGroups)
                .discountGroup(new HashSet<>(discountGroupMap.values()))
                .articularGroup(articularGroup)
                .storeGeneralBoard(storeGeneralBoard)
                .stores(stores)
                .build();
    }

    private Discount getDiscount(Map<String, StoreDiscountGroup> discountGroups,
                                 GroupOptionKeys groupOptionKeys,
                                 Region region,
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
}
