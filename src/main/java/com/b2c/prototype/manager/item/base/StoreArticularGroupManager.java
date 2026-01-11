package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.manager.item.IStoreArticularGroupManager;
import com.b2c.prototype.modal.dto.payload.item.request.StoreArticularGroupRequestDto;
import com.b2c.prototype.modal.dto.payload.item.request.StoreArticularItemRequestDto;
import com.b2c.prototype.modal.dto.payload.item.response.ArticularGroupResponseDto;
import com.b2c.prototype.modal.dto.payload.item.response.StoreArticularGroupResponseDto;
import com.b2c.prototype.modal.dto.payload.store.StoreArticularStockDto;
import com.b2c.prototype.modal.dto.payload.store.StoreGeneralBoardDto;
import com.b2c.prototype.modal.entity.item.ArticularGroup;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.item.DiscountGroup;
import com.b2c.prototype.modal.entity.item.Item;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.modal.entity.option.OptionItemCost;
import com.b2c.prototype.modal.entity.store.ArticularStock;
import com.b2c.prototype.modal.entity.store.Store;
import com.b2c.prototype.modal.entity.store.StoreGeneralBoard;
import com.b2c.prototype.service.generator.IKeyGeneratorService;
import com.b2c.prototype.transform.item.ItemTransformService;
import com.b2c.prototype.transform.transform.StoreArticularGroupTransform;
import com.b2c.prototype.transform.transform.StoreArticularItemTransform;
import com.b2c.prototype.transform.store.IStoreArticularGroupTransformService;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.b2c.prototype.util.Constant.ARTICULAR_GROUP_ID;
import static com.b2c.prototype.util.Constant.CODE;

@Service
public class StoreArticularGroupManager implements IStoreArticularGroupManager {

    private final IGeneralEntityDao generalEntityDao;
    private final IStoreArticularGroupTransformService storeArticularGroupTransformService;
    private final IKeyGeneratorService keyGeneratorService;
    private final ItemTransformService itemTransformService;

    public StoreArticularGroupManager(IGeneralEntityDao generalEntityDao,
                                      IStoreArticularGroupTransformService storeArticularGroupTransformService,
                                      IKeyGeneratorService keyGeneratorService, ItemTransformService itemTransformService) {
        this.generalEntityDao = generalEntityDao;
        this.storeArticularGroupTransformService = storeArticularGroupTransformService;
        this.keyGeneratorService = keyGeneratorService;
        this.itemTransformService = itemTransformService;
    }

    @Override
    @Transactional
    public void saveStoreArticularGroup(StoreArticularGroupRequestDto storeArticularGroupRequestDto) {
        StoreArticularGroupTransform storeArticularGroupTransform = storeArticularGroupTransformService
                .mapStoreArticularGroupRequestDtoToStoreArticularGroupTransform(storeArticularGroupRequestDto);

        Set<OptionGroup> ogs = storeArticularGroupTransform.getArticularGroup().getItems().stream()
                .flatMap(item -> item.getArticularItem().getOptionItems().stream())
                .map(OptionItem::getOptionGroup)
                .collect(Collectors.toSet());
        ogs.forEach(generalEntityDao::persistEntity);
        generalEntityDao.persistEntity(storeArticularGroupTransform.getArticularGroup());
        storeArticularGroupTransform.getStores().forEach(generalEntityDao::persistEntity);
        generalEntityDao.mergeEntity(storeArticularGroupTransform.getStoreGeneralBoard());
    }

    @Override
    @Transactional
    public void addStoreArticularItem(StoreArticularItemRequestDto storeArticularItemRequestDto) {
        StoreArticularGroupTransform storeArticularGroupTransform = storeArticularGroupTransformService
                .mapStoreArticularItemRequestDtoToStoreArticularGroupTransform(storeArticularItemRequestDto);

        generalEntityDao.persistEntity(storeArticularGroupTransform.getArticularGroup());
        storeArticularGroupTransform.getStores().forEach(generalEntityDao::persistEntity);
        generalEntityDao.mergeEntity(storeArticularGroupTransform.getStoreGeneralBoard());
    }

    @Override
    @Transactional
    public void deleteStoreArticularItem(String tenantId, String articularGroupId, String articularId, boolean isForced) {
        ArticularGroup articularGroup = fetchArticularGroup(tenantId, articularGroupId);
        List<String> articularIds = List.of(articularId);
        StoreGeneralBoard storeGeneralBoard = fetchStoreGeneralBoard(tenantId, articularIds);
        List<Store> stores = fetchStores(tenantId, articularIds);

        Item item = articularGroup.getItems().stream()
                .filter(e -> articularIds.contains(e.getArticularItem().getArticularUniqId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Articular Item not found in Articular Group"));

        ArticularItem articularItem = item.getArticularItem();

        List<ArticularStock> stocksToRemove = getStoresArticularStocks(stores, articularIds);
        stocksToRemove.forEach(stock -> {
            stores.forEach(store -> store.getArticularStocks().remove(stock));
            generalEntityDao.removeEntity(stock);
        });

        List<ArticularStock> generalBoardStocksToRemove = getGeneralBoardArticularStocks(storeGeneralBoard, articularIds);
        generalBoardStocksToRemove.forEach(storeGeneralBoard.getArticularStocks()::remove);
        generalBoardStocksToRemove.forEach(generalEntityDao::removeEntity);

        articularGroup.getItems().removeIf(e ->
                articularIds.contains(e.getArticularItem().getArticularUniqId())
        );

        articularItem.setDiscount(null);
        generalEntityDao.mergeEntity(articularGroup);
        if (isForced) {
            Set<OptionItemCost> allOptionItemCosts = new HashSet<>(articularItem.getOptionItemCosts());
            Set<OptionItem> allOptionItems = new HashSet<>(articularItem.getOptionItems());
            Set<OptionGroup> allOptionGroups = new HashSet<>();
            Set<DiscountGroup> allDiscountGroups = new HashSet<>();

            allOptionItemCosts.forEach(cost -> {
                if (cost.getOptionGroup() != null) {
                    allOptionGroups.add(cost.getOptionGroup());
                }
            });

            allOptionItems.forEach(optionItem -> {
                if (optionItem.getOptionGroup() != null) {
                    allOptionGroups.add(optionItem.getOptionGroup());
                }
            });

            if (articularItem.getDiscount() != null && articularItem.getDiscount().getDiscountGroup() != null) {
                allDiscountGroups.add(articularItem.getDiscount().getDiscountGroup());
            }

            allOptionItems.forEach(optionItem -> optionItem.removeArticularItem(articularItem));
            allOptionItemCosts.forEach(optionItemCost -> optionItemCost.removeArticularItem(articularItem));

            if (!storesContainOtherArticularIds(stores, articularIds)) {
                stores.forEach(generalEntityDao::removeEntity);
            }

            allOptionItemCosts.forEach(optionItemCost -> {
                if (!optionItemCostContainsOtherArticularIds(optionItemCost, articularIds)) {
                    generalEntityDao.removeEntity(optionItemCost);
                }
            });

            allOptionItems.forEach(optionItem -> {
                if (!optionItemContainsOtherArticularIds(optionItem, articularIds)) {
                    generalEntityDao.removeEntity(optionItem);
                }
            });

            allDiscountGroups.forEach(discountGroup -> {
                if (!discountContainsOtherArticularIds(discountGroup, articularIds)) {
                    generalEntityDao.removeEntity(discountGroup);
                }
            });

            allOptionGroups.forEach(generalEntityDao::removeEntity);

            allDiscountGroups.forEach(discountGroup -> {
                if (discountGroup.getDiscounts() == null || !discountGroup.getDiscounts().isEmpty()) {
                    generalEntityDao.removeEntity(discountGroup);
                }
            });
        }
    }


    @Override
    @Transactional
    public void updateStoreArticularGroup(String tenantId, String articularGroupId, StoreArticularGroupRequestDto storeArticularGroupRequestDto) {
        // 1. Fetch existing ArticularGroup
        ArticularGroup existingGroup = generalEntityDao.findEntity(
                "ArticularGroup.findByRegionAndKey",
                List.of(Pair.of(CODE, tenantId), Pair.of(ARTICULAR_GROUP_ID, articularGroupId))
        );

        // 2. Get existing articularIds and create registries (similar to CategoryManager)
        Set<String> oldArticularIds = existingGroup.getItems().stream()
                .map(item -> item.getArticularItem().getArticularUniqId())
                .collect(Collectors.toSet());

        Map<String, ArticularItem> existingArticularItemMap = existingGroup.getItems().stream()
                .collect(Collectors.toMap(
                        item -> item.getArticularItem().getArticularUniqId(),
                        Item::getArticularItem,
                        (a, b) -> a
                ));

        // 3. Transform request to entities
        StoreArticularGroupTransform transform = storeArticularGroupTransformService
                .mapStoreArticularGroupRequestDtoToStoreArticularGroupTransform(storeArticularGroupRequestDto);

        ArticularGroup updatedGroup = transform.getArticularGroup();

        // 4. Apply ArticularGroup update (preserve ID like CategoryManager does)
        existingGroup.setTenant(updatedGroup.getTenant());
        existingGroup.setCategory(updatedGroup.getCategory());
        existingGroup.setDescription(updatedGroup.getDescription());

        // 5. Update items - preserve existing ArticularItem IDs
        updatedGroup.getItems().forEach(groupItem -> {
            String articularUniqId = groupItem.getArticularItem().getArticularUniqId();
            ArticularItem existingItem = existingArticularItemMap.get(articularUniqId);
            if (existingItem != null) {
                groupItem.getArticularItem().setId(existingItem.getId());
            }
        });

        existingGroup.setItems(updatedGroup.getItems());
        generalEntityDao.mergeEntity(existingGroup);

        // 6. Get new articularIds after update
        Set<String> newArticularIds = updatedGroup.getItems().stream()
                .map(item -> item.getArticularItem().getArticularUniqId())
                .collect(Collectors.toSet());

        // 7. Update StoreGeneralBoard (preserve ID)
        StoreGeneralBoard existingBoard = generalEntityDao.findEntity(
                "StoreGeneralBoard.findByTenantId",
                Pair.of(CODE, tenantId)
        );

        StoreGeneralBoard updatedBoard = transform.getStoreGeneralBoard();
        updatedBoard.setId(existingBoard.getId());
//        updatedBoard.setTenantId(existingBoard.getTenantId());

        // Preserve nested ArticularItemQuantity IDs
        Map<String, Long> existingBoardStockIds = existingBoard.getArticularStocks().stream()
                .collect(Collectors.toMap(
                        stock -> stock.getArticularItemQuantity().getArticularItem().getArticularUniqId(),
                        stock -> stock.getId(),
                        (a, b) -> a
                ));

        updatedBoard.getArticularStocks().forEach(stock -> {
            String articularUniqId = stock.getArticularItemQuantity().getArticularItem().getArticularUniqId();
            Long existingId = existingBoardStockIds.get(articularUniqId);
            if (existingId != null) {
                stock.setId(existingId);
            }
            ArticularItem existingItem = existingArticularItemMap.get(articularUniqId);
            if (existingItem != null) {
                stock.getArticularItemQuantity().getArticularItem().setId(existingItem.getId());
            }
        });

        generalEntityDao.mergeEntity(updatedBoard);

        // 8. Update Stores (preserve IDs)
        List<Store> existingStores = generalEntityDao.findEntityList(
                "Store.findAllStoresByRegionAndArticularIds",
                List.of(Pair.of(CODE, tenantId), Pair.of("articularIds", new ArrayList<>(oldArticularIds)))
        );

        Map<String, Store> existingStoreMap = existingStores.stream()
                .collect(Collectors.toMap(Store::getStoreUniqId, s -> s, (a, b) -> a));

        transform.getStores().forEach(newStore -> {
            Store existing = existingStoreMap.get(newStore.getStoreUniqId());
            if (existing != null) {
                newStore.setId(existing.getId());
                newStore.setStoreUniqId(existing.getStoreUniqId());

                // Preserve stock IDs
                Map<String, Long> existingStockIds = existing.getArticularStocks().stream()
                        .collect(Collectors.toMap(
                                stock -> stock.getArticularItemQuantity().getArticularItem().getArticularUniqId(),
                                stock -> stock.getId(),
                                (a, b) -> a
                        ));

                newStore.getArticularStocks().forEach(stock -> {
                    String articularUniqId = stock.getArticularItemQuantity().getArticularItem().getArticularUniqId();
                    Long existingId = existingStockIds.get(articularUniqId);
                    if (existingId != null) {
                        stock.setId(existingId);
                    }
                    ArticularItem existingItem = existingArticularItemMap.get(articularUniqId);
                    if (existingItem != null) {
                        stock.getArticularItemQuantity().getArticularItem().setId(existingItem.getId());
                    }
                });

                generalEntityDao.mergeEntity(newStore);
            } else {
                generalEntityDao.persistEntity(newStore);
            }
        });

        // 9. Clean up orphaned entities
        Set<String> removedArticularIds = new HashSet<>(oldArticularIds);
        removedArticularIds.removeAll(newArticularIds);

        if (!removedArticularIds.isEmpty()) {
            cleanupOrphanedStores(tenantId, removedArticularIds);
        }
    }

    private void cleanupOrphanedStores(String tenantId, Set<String> removedArticularIds) {
        List<Store> orphanedStores = generalEntityDao.findEntityList(
                "Store.findAllStoresByRegionAndArticularIds",
                List.of(Pair.of(CODE, tenantId), Pair.of("articularIds", new ArrayList<>(removedArticularIds)))
        );

        orphanedStores.forEach(store -> {
            store.getArticularStocks().removeIf(stock ->
                    removedArticularIds.contains(stock.getArticularItemQuantity().getArticularItem().getArticularUniqId())
            );

            if (store.getArticularStocks().isEmpty()) {
                generalEntityDao.removeEntity(store);
            } else {
                generalEntityDao.mergeEntity(store);
            }
        });
    }

    private void updateArticularGroup(ArticularGroup existingGroup, ArticularGroup newUpdateGroup) {
        existingGroup.setTenant(newUpdateGroup.getTenant());
        existingGroup.setCategory(newUpdateGroup.getCategory());
        existingGroup.setDescription(newUpdateGroup.getDescription());

        existingGroup.setItems(newUpdateGroup.getItems());
    }

    @Override
    @Transactional
    public void deleteStoreArticularGroup(String tenantId, String articularGroupId, boolean isForced) {
        ArticularGroup articularGroup = fetchArticularGroup(tenantId, articularGroupId);
        List<String> articularIds = getArticularItemIds(articularGroup);
        StoreGeneralBoard storeGeneralBoard = fetchStoreGeneralBoard(tenantId, articularIds);
        List<Store> stores = fetchStores(tenantId, articularIds);

        stores.forEach(generalEntityDao::removeEntity);

        Set<OptionItemCost> allOptionItemCosts = new HashSet<>();
        Set<OptionItem> allOptionItems = new HashSet<>();
        Set<OptionGroup> allOptionGroups = new HashSet<>();
        Set<DiscountGroup> allDiscountGroups = new HashSet<>();

        if (isForced) {

        }

        articularGroup.getItems().forEach(groupItem -> {
            ArticularItem articularItem = groupItem.getArticularItem();

            articularItem.getOptionItemCosts().forEach(cost -> {
                if (cost.getOptionGroup() != null) {
                    allOptionGroups.add(cost.getOptionGroup());
                }
                allOptionItemCosts.add(cost);
            });

            articularItem.getOptionItems().forEach(item -> {
                if (item.getOptionGroup() != null) {
                    allOptionGroups.add(item.getOptionGroup());
                }
                allOptionItems.add(item);
            });

            if (articularItem.getDiscount() != null && articularItem.getDiscount().getDiscountGroup() != null) {
                allDiscountGroups.add(articularItem.getDiscount().getDiscountGroup());
            }

            articularItem.getOptionItemCosts().clear();
            articularItem.getOptionItems().clear();
        });

        allOptionItemCosts.forEach(generalEntityDao::removeEntity);
        allOptionItems.forEach(generalEntityDao::removeEntity);
        allOptionGroups.forEach(generalEntityDao::removeEntity);

        generalEntityDao.removeEntity(articularGroup);

        allDiscountGroups.forEach(discountGroup -> {
            if (discountGroup.getDiscounts() == null || !discountGroup.getDiscounts().isEmpty()) {
                generalEntityDao.removeEntity(discountGroup);
            }
        });
    }

    @Override
    @Transactional(readOnly = true)
    public StoreArticularGroupResponseDto getStoreArticularGroup(String tenantId, String articularGroupId) {
        ArticularGroup articularGroup = fetchArticularGroup(tenantId, articularGroupId);
        List<String> articularIds = getArticularItemIds(articularGroup);
        StoreGeneralBoard storeGeneralBoard = fetchStoreGeneralBoard(tenantId, articularIds);
        List<Store> stores = fetchStores(tenantId, articularIds);

        ArticularGroupResponseDto articularGroupResponseDto = itemTransformService.mapArticularGroupDtoToArticularGroupDto(articularGroup);
        StoreGeneralBoardDto storeGeneralBoardDto = itemTransformService.mapStoreGeneralBoardToStoreGeneralBoardDto(storeGeneralBoard);
        List<StoreArticularStockDto> storeDtoList = stores.stream()
                .map(itemTransformService::mapStoreToStoreArticularStockDto)
                .toList();

        return StoreArticularGroupResponseDto.builder()
                .tenantId(tenantId)
                .articularGroup(articularGroupResponseDto)
                .storeGeneralBoard(storeGeneralBoardDto)
                .stores(storeDtoList)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StoreArticularGroupResponseDto> getStoreArticularGroups(String tenantId) {
        List<ArticularGroup> groups = generalEntityDao.findEntityList(
                "ArticularGroup.findAllByRegion",
                List.of(Pair.of(CODE, tenantId))
        );

        Map<String, List<String>> groupToArticularIds = groups.stream()
                .collect(Collectors.toMap(
                        g -> String.valueOf(g.getArticularGroupId()),
                        g -> g.getItems().stream()
                                .map(it -> it.getArticularItem().getArticularUniqId())
                                .toList()
                ));

        List<String> articularIds = groupToArticularIds.values().stream()
                .flatMap(Collection::stream)
                .distinct()
                .toList();

        if (articularIds.isEmpty()) {
            return groups.stream()
                    .map(g -> StoreArticularGroupResponseDto.builder()
                            .tenantId(tenantId)
                            .articularGroup(itemTransformService.mapArticularGroupDtoToArticularGroupDto(g))
                            .storeGeneralBoard(null)
                            .stores(List.of())
                            .build()
                    )
                    .toList();
        }

        StoreGeneralBoard storeGeneralBoard = generalEntityDao.findEntity(
                "StoreGeneralBoard.findByTenantAndArticularUniqIds",
                List.of(Pair.of(CODE, tenantId),
                        Pair.of("articularIds", articularIds)));

        StoreGeneralBoardDto boardDto = itemTransformService.mapStoreGeneralBoardToStoreGeneralBoardDto(storeGeneralBoard);

        List<Store> stores = generalEntityDao.findEntityList(
                "Store.findAllStoresByRegionAndArticularIds",
                List.of(Pair.of(CODE, tenantId),
                        Pair.of("articularIds", articularIds))
        );

        Map<String, List<Store>> articularIdToStores = stores.stream()
                .flatMap(store -> store.getArticularStocks().stream()
                        .map(stock -> Map.entry(stock.getArticularItemQuantity()
                                .getArticularItem()
                                .getArticularUniqId(), store)))
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())
                ));

        return groups.stream()
                .map(g -> {
                    List<String> ids = groupToArticularIds.getOrDefault(
                            String.valueOf(g.getArticularGroupId()), List.of()
                    );

                    List<Store> groupStores = ids.stream()
                            .flatMap(id -> articularIdToStores.getOrDefault(id, List.of()).stream())
                            .distinct()
                            .toList();

                    List<StoreArticularStockDto> storeDtos = groupStores.stream()
                            .map(itemTransformService::mapStoreToStoreArticularStockDto)
                            .toList();

                    return StoreArticularGroupResponseDto.builder()
                            .tenantId(tenantId)
                            .articularGroup(itemTransformService.mapArticularGroupDtoToArticularGroupDto(g))
                            .storeGeneralBoard(boardDto)
                            .stores(storeDtos)
                            .build();
                })
                .toList();
    }

    private ArticularGroup fetchArticularGroup(String tenantId, String articularGroupId) {
        List<Pair<String, ?>> params = List.of(
                Pair.of(CODE, tenantId),
                Pair.of(ARTICULAR_GROUP_ID, articularGroupId));

        return generalEntityDao.findEntity(
                "ArticularGroup.findByRegionAndKey",
                params);
    }

    private List<String> getArticularItemIds(ArticularGroup articularGroup) {
        return articularGroup.getItems().stream()
                .map(item -> item.getArticularItem().getArticularUniqId())
                .toList();
    }

    private StoreGeneralBoard fetchStoreGeneralBoard(String tenantId, List<String> articularIds) {
        return generalEntityDao.findEntity(
                "StoreGeneralBoard.findByTenant",
                Pair.of(CODE, tenantId));
    }

    private List<Store> fetchStores(String tenantId, List<String> articularIds) {
        return generalEntityDao.findEntityList(
                "Store.findAllStoresByRegionAndArticularIds",
                List.of(
                        Pair.of(CODE, tenantId),
                        Pair.of("articularIds", articularIds)
                )
        );
    }

    private boolean storesContainOtherArticularIds(List<Store> stores, List<String> articularIds) {
        Set<String> allowedIds = articularIds == null
                ? Set.of()
                : new HashSet<>(articularIds);

        return stores.stream()
                .flatMap(store -> store.getArticularStocks().stream())
                .map(stock -> stock.getArticularItemQuantity()
                        .getArticularItem()
                        .getArticularUniqId())
                .anyMatch(id -> !allowedIds.contains(id));
    }

    private List<ArticularStock> getStoresArticularStocks(List<Store> stores, List<String> articularIds) {
        return stores.stream()
                .flatMap(store -> store.getArticularStocks().stream())
                .filter(stock -> {
                    String articularUniqId = stock.getArticularItemQuantity()
                            .getArticularItem()
                            .getArticularUniqId();
                    return articularIds.contains(articularUniqId);
                })
                .toList();
    }

    private boolean storesGeneralBoardContainOtherArticularIds(StoreGeneralBoard storeGeneralBoard, List<String> articularIds) {
        Set<String> allowedIds = articularIds == null
                ? Set.of()
                : new HashSet<>(articularIds);

        return storeGeneralBoard.getArticularStocks().stream()
                .map(stock -> stock.getArticularItemQuantity()
                        .getArticularItem()
                        .getArticularUniqId())
                .anyMatch(id -> !allowedIds.contains(id));
    }

    private List<ArticularStock> getGeneralBoardArticularStocks(StoreGeneralBoard storeGeneralBoard, List<String> articularIds) {
        return storeGeneralBoard.getArticularStocks().stream()
                .filter(stock -> {
                    String articularUniqId = stock.getArticularItemQuantity()
                            .getArticularItem()
                            .getArticularUniqId();
                    return articularIds.contains(articularUniqId);
                })
                .toList();
    }

    private boolean optionItemContainsOtherArticularIds(OptionItem optionItem, List<String> articularIds) {
        if (articularIds == null) {
            articularIds = List.of();
        }
        Set<String> allowedIds = new HashSet<>(articularIds);
        return optionItem.getArticularItems().stream()
                .map(ArticularItem::getArticularUniqId)
                .anyMatch(id -> !allowedIds.contains(id));
    }

    private boolean optionItemCostContainsOtherArticularIds(OptionItemCost optionItemCost, List<String> articularIds) {
        if (articularIds == null) {
            articularIds = List.of();
        }
        Set<String> allowedIds = new HashSet<>(articularIds);
        return optionItemCost.getArticularItems().stream()
                .map(ArticularItem::getArticularUniqId)
                .anyMatch(id -> !allowedIds.contains(id));
    }

    private boolean discountContainsOtherArticularIds(DiscountGroup discountGroup, List<String> articularIds) {
        if (articularIds == null) {
            articularIds = List.of();
        }
        Set<String> allowedIds = new HashSet<>(articularIds);
        return discountGroup.getDiscounts().stream()
                .flatMap(discount -> discount.getArticularItemList().stream())
                .map(ArticularItem::getArticularUniqId)
                .anyMatch(id -> !allowedIds.contains(id));
    }

}
