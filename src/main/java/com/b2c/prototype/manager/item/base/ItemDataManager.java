package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.dao.item.IItemDataDao;
import com.b2c.prototype.modal.dto.payload.ItemDataDto;
import com.b2c.prototype.modal.dto.response.ResponseItemDataDto;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.item.ItemData;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.item.IItemDataManager;
import com.b2c.prototype.service.common.EntityOperationManager;
import com.b2c.prototype.service.common.IEntityOperationManager;
import com.b2c.prototype.service.query.ISearchService;
import com.b2c.prototype.service.supplier.ISupplierService;
import com.tm.core.process.dao.identifier.IQueryService;
import com.tm.core.finder.parameter.Parameter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.b2c.prototype.util.Constant.ITEM_ID;
import static com.b2c.prototype.util.Util.getUUID;

public class ItemDataManager implements IItemDataManager {

    private final IEntityOperationManager entityOperationDao;
    private final IQueryService queryService;
    private final ISearchService searchService;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;

    public ItemDataManager(IItemDataDao itemDataDao, 
                           IQueryService queryService,
                           ISearchService searchService,
                           ITransformationFunctionService transformationFunctionService,
                           ISupplierService supplierService) {
        this.entityOperationDao = new EntityOperationManager(itemDataDao);
        this.queryService = queryService;
        this.searchService = searchService;
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
    }

    @Override
    public void saveItemData(ItemDataDto itemDataDto) {
        entityOperationDao.executeConsumer(session -> {
            ItemData itemData = transformationFunctionService.getEntity(session, ItemData.class, itemDataDto);
            itemData.setItemId(getUUID());
            itemData.getArticularItemSet().forEach(articularItem ->
                    articularItem.setArticularId(getUUID()));
            session.merge(itemData);
        });
    }

    @Override
    public void updateItemData(String itemId, ItemDataDto itemDataDto) {
        entityOperationDao.executeConsumer(session -> {
            ItemData existingItemData = queryService.getEntityGraph(
                    session,
                    ItemData.class,
                    "itemData.full",
                    new Parameter("itemId", itemId));
            ItemData itemData = transformationFunctionService.getEntity(
                    session,
                    ItemData.class,
                    itemDataDto);

            existingItemData.setDescription(itemData.getDescription());
            existingItemData.setCategory(itemData.getCategory());
            existingItemData.setItemType(itemData.getItemType());
            existingItemData.setBrand(itemData.getBrand());

            List<ArticularItem> result = mergeArticularItems(existingItemData.getArticularItemSet(), itemData.getArticularItemSet());
            existingItemData.setArticularItemSet(new HashSet<>(result));
            session.merge(existingItemData);
        });
    }

    @Override
    public void deleteItemData(String itemId) {
        entityOperationDao.deleteEntityByParameter(
                supplierService.parameterStringSupplier(ITEM_ID, itemId));
    }

    @Override
    public ResponseItemDataDto getItemData(String itemId) {
        return entityOperationDao.getEntityGraphDto(
                "",
                supplierService.parameterStringSupplier(ITEM_ID, itemId),
                transformationFunctionService.getTransformationFunction(ItemData.class, ResponseItemDataDto.class));
    }

    @Override
    public List<ResponseItemDataDto> getItemDataList() {
        return entityOperationDao.getEntityGraphDtoList(
                "",
                transformationFunctionService.getTransformationFunction(ItemData.class, ResponseItemDataDto.class));
    }

    private List<ArticularItem> mergeArticularItems(Set<ArticularItem> aSet, Set<ArticularItem> bSet) {
        List<ArticularItem> a = aSet.stream().toList();
        List<ArticularItem> b = bSet.stream().toList();
        List<ArticularItem> articularItemList = new ArrayList<>();
        int i = 0;
        if (a.size() >= b.size()) {
            for (; i < b.size(); i++) {
                ArticularItem articularItem = copyArticularItemValues(a.get(i), b.get(i));
                articularItemList.add(articularItem);
            }
            for (; i < a.size(); i++) {
                ArticularItem articularItem = createArticularItem(a.get(i));
                articularItemList.add(articularItem);
            }
        } else {
            for (i = 0; i < a.size(); i++) {
                ArticularItem articularItem = copyArticularItemValues(b.get(i), a.get(i));
                articularItemList.add(articularItem);
            }
            for (; i < b.size(); i++) {
                ArticularItem articularItem = createArticularItem(b.get(i));
                articularItemList.add(articularItem);
            }
        }


        return articularItemList;
    }

    private ArticularItem copyArticularItemValues(ArticularItem target, ArticularItem source) {
        if (target.getArticularId() == null) {
            target.setArticularId(source.getArticularId());
        }
        target.setDateOfCreate(source.getDateOfCreate());
        target.setProductName(source.getProductName());

        target.getFullPrice().setAmount(source.getFullPrice().getAmount());
        target.getFullPrice().setCurrency(source.getFullPrice().getCurrency());
        target.getTotalPrice().setAmount(source.getTotalPrice().getAmount());
        target.getTotalPrice().setCurrency(source.getTotalPrice().getCurrency());

        target.setStatus(source.getStatus());

        // Update discount fields without replacing the identifier
        if (target.getDiscount() != null && source.getDiscount() != null) {
            target.getDiscount().setAmount(source.getDiscount().getAmount());
            target.getDiscount().setCurrency(source.getDiscount().getCurrency());
            target.getDiscount().setPercent(source.getDiscount().isPercent());
            target.getDiscount().setActive(source.getDiscount().isActive());
            // Only update charSequenceCode if needed, but ensure uniqueness check if required
            if (!target.getDiscount().getCharSequenceCode().equals(source.getDiscount().getCharSequenceCode())) {
                target.getDiscount().setCharSequenceCode(source.getDiscount().getCharSequenceCode());
            }
        } else if (source.getDiscount() != null) {
            target.setDiscount(source.getDiscount());
        }

        new HashSet<>(target.getOptionItems()).forEach(source::removeOptionItem);
        source.getOptionItems().forEach(source::addOptionItem);
        return target;
    }

    private ArticularItem createArticularItem(ArticularItem sourceItem) {
        ArticularItem articularItem = ArticularItem.builder()
                .articularId(sourceItem.getArticularId())
                .dateOfCreate(sourceItem.getDateOfCreate())
                .productName(sourceItem.getProductName())
                .fullPrice(sourceItem.getFullPrice())
                .totalPrice(sourceItem.getTotalPrice())
                .status(sourceItem.getStatus())
                .discount(sourceItem.getDiscount())
                .build();
        sourceItem.getOptionItems().forEach(articularItem::addOptionItem);
        return articularItem;
    }



}
