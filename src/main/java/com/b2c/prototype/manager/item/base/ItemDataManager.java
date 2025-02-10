package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.dao.item.IItemDataDao;
import com.b2c.prototype.modal.dto.payload.ItemDataDto;
import com.b2c.prototype.modal.dto.payload.OptionItemDto;
import com.b2c.prototype.modal.dto.response.ResponseItemDataDto;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.item.ItemData;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.item.IItemDataManager;
import com.b2c.prototype.service.common.EntityOperationManager;
import com.b2c.prototype.service.common.IEntityOperationManager;
import com.b2c.prototype.service.query.IQueryService;
import com.b2c.prototype.service.supplier.ISupplierService;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.b2c.prototype.util.Constant.ITEM_ID;
import static com.b2c.prototype.util.Constant.VALUE;

public class ItemDataManager implements IItemDataManager {

    private final IEntityOperationManager entityOperationDao;
    private final IQueryService queryService;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;

    public ItemDataManager(IItemDataDao itemDataDao,
                           IQueryService queryService,
                           ITransformationFunctionService transformationFunctionService,
                           ISupplierService supplierService) {
        this.entityOperationDao = new EntityOperationManager(itemDataDao);
        this.queryService = queryService;
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
    }

    @Override
    public void saveItemData(ItemDataDto itemDataDto) {
        entityOperationDao.executeConsumer(session -> {
            ItemData itemData = transformationFunctionService.getEntity(ItemData.class, itemDataDto);
            session.merge(itemData);
        });
    }

    @Override
    public void updateItemData(String itemId, ItemDataDto itemDataDto) {
        entityOperationDao.executeConsumer(session -> {
            ItemData existingItemData = entityOperationDao.getEntityGraph(
                    "itemData.full",
                    supplierService.parameterStringSupplier("itemId", itemId));
            ItemData itemData = transformationFunctionService.getEntity(
                    ItemData.class,
                    itemDataDto);

            itemData.setId(existingItemData.getId());
            itemData.setItemId(existingItemData.getItemId());

            itemData.getArticularItemSet().forEach(articularItem -> {
                if (articularItem.getFullPrice() != null) {
                    articularItem.getFullPrice().setCurrency(session.merge(articularItem.getFullPrice().getCurrency()));
                }
                if (articularItem.getTotalPrice() != null) {
                    articularItem.getTotalPrice().setCurrency(session.merge(articularItem.getTotalPrice().getCurrency()));
                }
                if (articularItem.getDiscount() != null) {
                    articularItem.getDiscount().setCurrency(session.merge(articularItem.getDiscount().getCurrency()));
                }
                if (articularItem.getStatus() != null) {
                    articularItem.setStatus(session.merge(articularItem.getStatus()));
                }
            });

            session.merge(itemData);
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

}
