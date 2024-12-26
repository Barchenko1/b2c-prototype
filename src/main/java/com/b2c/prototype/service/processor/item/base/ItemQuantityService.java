package com.b2c.prototype.service.processor.item.base;

import com.b2c.prototype.dao.item.IItemDataDao;
import com.b2c.prototype.modal.dto.request.ItemDataQuantityDto;
import com.b2c.prototype.modal.entity.item.ItemDataOptionQuantity;
import com.b2c.prototype.modal.entity.order.OrderItemData;
import com.b2c.prototype.service.common.EntityOperationDao;
import com.b2c.prototype.service.common.IEntityOperationDao;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.item.IItemQuantityService;
import com.b2c.prototype.service.processor.query.IQueryService;
import com.b2c.prototype.service.supplier.ISupplierService;

public class ItemQuantityService implements IItemQuantityService {

    private final IEntityOperationDao entityOperationDao;
    private final IQueryService queryService;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;

    public ItemQuantityService(IItemDataDao itemDataDao,
                               IQueryService queryService,
                               ITransformationFunctionService transformationFunctionService,
                               ISupplierService supplierService) {
        this.entityOperationDao = new EntityOperationDao(itemDataDao);
        this.queryService = queryService;
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
    }

    @Override
    public void increaseOneItemDataCount(ItemDataQuantityDto itemDataQuantityDto) {
        entityOperationDao.executeConsumer(session -> {
            ItemDataOptionQuantity itemDataOptionQuantity = updateOneIncrementCounter(itemDataQuantityDto, true);
            session.merge(itemDataOptionQuantity);
        });
    }

    @Override
    public void decreaseOneItemDataCount(ItemDataQuantityDto itemDataQuantityDto) {
        entityOperationDao.executeConsumer(session -> {
            ItemDataOptionQuantity itemDataOptionQuantity = updateOneIncrementCounter(itemDataQuantityDto, false);
            session.merge(itemDataOptionQuantity);
        });
    }

    @Override
    public void increaseOneItemDataCountAndStore(ItemDataQuantityDto itemDataQuantityDto) {

    }

    @Override
    public void decreaseOneItemDataCountAndStore(ItemDataQuantityDto itemDataQuantityDto) {

    }

    @Override
    public void updateItemQuantity(ItemDataQuantityDto itemDataQuantityDto) {
        entityOperationDao.executeConsumer(session -> {
            ItemDataOptionQuantity itemDataOptionQuantity = updateCounter(itemDataQuantityDto, true);
            session.merge(itemDataOptionQuantity);
        });
    }

    @Override
    public void updateItemQuantityAndStore(ItemDataQuantityDto itemDataQuantityDto) {
        entityOperationDao.executeConsumer(session -> {
            ItemDataOptionQuantity itemDataOptionQuantity = updateCounter(itemDataQuantityDto, false);
            session.merge(itemDataOptionQuantity);
        });
    }

    private ItemDataOptionQuantity updateOneIncrementCounter(ItemDataQuantityDto itemDataQuantityDto, boolean increase) {
        OrderItemData orderItemData = queryService.getEntity(
                OrderItemData.class,
                supplierService.parameterStringSupplier("orderId", itemDataQuantityDto.getOrderId()));
        ItemDataOptionQuantity existingItemDataOptionQuantity = orderItemData.getItemDataOptionQuantitySet().stream()
                .filter(idq ->
                        idq.getItemDataOption().getArticularId().equals(itemDataQuantityDto.getArticularId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException());
        int existingItemDataQuantityQuantityCount = existingItemDataOptionQuantity.getQuantity();
        int count = increase
                ? existingItemDataQuantityQuantityCount + 1
                : existingItemDataQuantityQuantityCount - 1;
        existingItemDataOptionQuantity.setQuantity(count);
        return existingItemDataOptionQuantity;
    }

    private ItemDataOptionQuantity updateCounter(ItemDataQuantityDto itemDataQuantityDto, boolean increase) {
        OrderItemData orderItemData = queryService.getEntity(
                OrderItemData.class,
                supplierService.parameterStringSupplier("orderId", itemDataQuantityDto.getOrderId()));
        ItemDataOptionQuantity existingItemDataOptionQuantity = orderItemData.getItemDataOptionQuantitySet().stream()
                .filter(idq ->
                        idq.getItemDataOption().getArticularId().equals(itemDataQuantityDto.getArticularId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException());
        int existingItemDataQuantityQuantityCount = existingItemDataOptionQuantity.getQuantity();
        int counter = increase
                ? existingItemDataQuantityQuantityCount + itemDataQuantityDto.getQuantity()
                : existingItemDataQuantityQuantityCount - itemDataQuantityDto.getQuantity();
        existingItemDataOptionQuantity.setQuantity(counter);
        return existingItemDataOptionQuantity;
    }
}
