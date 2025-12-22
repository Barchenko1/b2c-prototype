package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.manager.item.IStoreArticularGroupManager;
import com.b2c.prototype.modal.dto.payload.item.request.StoreArticularGroupRequestDto;
import com.b2c.prototype.modal.dto.payload.item.response.ArticularGroupResponseDto;
import com.b2c.prototype.modal.dto.payload.item.response.StoreArticularGroupResponseDto;
import com.b2c.prototype.modal.dto.payload.store.StoreArticularStockDto;
import com.b2c.prototype.modal.dto.payload.store.StoreGeneralBoardDto;
import com.b2c.prototype.modal.entity.item.ArticularGroup;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.item.ArticularItemQuantity;
import com.b2c.prototype.modal.entity.item.DiscountGroup;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.modal.entity.option.OptionItemCost;
import com.b2c.prototype.modal.entity.store.ArticularStock;
import com.b2c.prototype.modal.entity.store.Store;
import com.b2c.prototype.modal.entity.store.StoreGeneralBoard;
import com.b2c.prototype.service.generator.IKeyGeneratorService;
import com.b2c.prototype.transform.item.ItemTransformService;
import com.b2c.prototype.transform.modal.StoreArticularGroupTransform;
import com.b2c.prototype.transform.store.IStoreArticularGroupTransformService;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        generalEntityDao.persistEntity(storeArticularGroupTransform.getArticularGroup());
        storeArticularGroupTransform.getStores().forEach(generalEntityDao::persistEntity);
        generalEntityDao.persistEntity(storeArticularGroupTransform.getStoreGeneralBoard());
    }

    @Override
    @Transactional
    public void updateStoreArticularGroup(String tenantId, String articularGroupId, StoreArticularGroupRequestDto storeArticularGroupRequestDto) {
        StoreArticularGroupTransform storeArticularGroupTransform = storeArticularGroupTransformService
                .mapStoreArticularGroupRequestDtoToStoreArticularGroupTransform(storeArticularGroupRequestDto);

        generalEntityDao.mergeEntity(storeArticularGroupTransform.getArticularGroup());
        storeArticularGroupTransform.getStores().forEach(generalEntityDao::mergeEntity);
        generalEntityDao.mergeEntity(storeArticularGroupTransform.getStoreGeneralBoard());
    }

    @Override
    @Transactional
    public void deleteStoreArticularGroup(String tenantId, String articularGroupId, boolean isForced) {
        List<Pair<String, ?>> params = List.of(Pair.of(CODE, tenantId),
                Pair.of(ARTICULAR_GROUP_ID, articularGroupId));
        ArticularGroup articularGroup = generalEntityDao.findEntity(
                "ArticularGroup.findByRegionAndKey",
                params);
        List<String> articularIds = articularGroup.getItems().stream()
                .map(item -> item.getArticularItem().getArticularUniqId())
                .toList();
        StoreGeneralBoard storeGeneralBoard = generalEntityDao.findEntity(
                "StoreGeneralBoard.findByArticularUniqIds",
                Pair.of("articularIds", articularIds));
        List<Store> stores = generalEntityDao.findEntityList(
                "Store.findAllStoresByRegionAndArticularIds",
                List.of(
                        Pair.of(CODE, tenantId),
                        Pair.of("articularIds", articularIds)
                )
        );

        stores.forEach(generalEntityDao::removeEntity);
        generalEntityDao.removeEntity(storeGeneralBoard);

        Set<OptionItemCost> allOptionItemCosts = new HashSet<>();
        Set<OptionItem> allOptionItems = new HashSet<>();
        Set<OptionGroup> allOptionGroups = new HashSet<>();
        Set<DiscountGroup> allDiscountGroups = new HashSet<>();

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
        List<Pair<String, ?>> params = List.of(Pair.of(CODE, tenantId),
                Pair.of(ARTICULAR_GROUP_ID, articularGroupId));
        ArticularGroup articularGroup = generalEntityDao.findEntity(
                "ArticularGroup.findByRegionAndKey",
                params);

        List<String> articularIds = articularGroup.getItems().stream()
                .map(item -> item.getArticularItem().getArticularUniqId())
                .toList();
        StoreGeneralBoard storeGeneralBoard = generalEntityDao.findEntity(
                "StoreGeneralBoard.findByArticularUniqIds",
                Pair.of("articularIds", articularIds));
        List<Store> stores = generalEntityDao.findEntityList(
                "Store.findAllStoresByRegionAndArticularIds",
                List.of(
                        Pair.of(CODE, tenantId),
                        Pair.of("articularIds", articularIds)
                )
        );

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
        // 1) groups
        List<ArticularGroup> groups = generalEntityDao.findEntityList(
                "ArticularGroup.findAllByRegion",
                List.of(Pair.of(CODE, tenantId))
        );

        // groupId -> articularIds
        Map<String, List<String>> groupToArticularIds = groups.stream()
                .collect(Collectors.toMap(
                        g -> String.valueOf(g.getArticularGroupId()),
                        g -> g.getItems().stream()
                                .map(it -> it.getArticularItem().getArticularUniqId())
                                .toList()
                ));

        List<String> allArticularIds = groupToArticularIds.values().stream()
                .flatMap(Collection::stream)
                .distinct()
                .toList();

        if (allArticularIds.isEmpty()) {
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

        // 2) One call for boards/stocks for all articularIds
        // You likely need a "findByArticularUniqIds" that returns ALL matching boards/stocks (not single entity)
        List<StoreGeneralBoard> boards = generalEntityDao.findEntityList(
                "StoreGeneralBoard.findAllByArticularUniqIds",
                List.of(Pair.of("articularIds", allArticularIds))
        );

        // 3) One call for stores for all articularIds
        List<Store> stores = generalEntityDao.findEntityList(
                "Store.findAllStoresByRegionAndArticularIds",
                List.of(Pair.of(CODE, tenantId), Pair.of("articularIds", allArticularIds))
        );

        // ---- Build lookup maps ----
        // IMPORTANT: you need a way to know which articularIds each board/store relates to.
        // If entity graph contains stocks -> articularItem -> articularUniqId, you can extract it.
        Map<String, List<StoreGeneralBoard>> articularIdToBoards = boards.stream()
                .flatMap(b -> b.getArticularStocks().stream()
                        .map(stock -> Map.entry(stock.getArticularItemQuantity()
                                .getArticularItem()
                                .getArticularUniqId(), b)))
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())
                ));

        Map<String, List<Store>> articularIdToStores = stores.stream()
                .flatMap(store -> store.getArticularStocks().stream()
                        .map(stock -> Map.entry(stock.getArticularItemQuantity()
                                .getArticularItem()
                                .getArticularUniqId(), store)))
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())
                ));

        // ---- Response per group ----
        return groups.stream()
                .map(g -> {
                    List<String> ids = groupToArticularIds.getOrDefault(
                            String.valueOf(g.getArticularGroupId()), List.of()
                    );

                    // boards/stores for this group = union by articularId
                    List<StoreGeneralBoard> groupBoards = ids.stream()
                            .flatMap(id -> articularIdToBoards.getOrDefault(id, List.of()).stream())
                            .distinct()
                            .toList();

                    List<Store> groupStores = ids.stream()
                            .flatMap(id -> articularIdToStores.getOrDefault(id, List.of()).stream())
                            .distinct()
                            .toList();

                    // If you expect exactly ONE board per tenant, you can pick first; otherwise you may need a merge DTO.
                    StoreGeneralBoardDto boardDto = groupBoards.isEmpty()
                            ? null
                            : itemTransformService.mapStoreGeneralBoardToStoreGeneralBoardDto(groupBoards.get(0));

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
}
