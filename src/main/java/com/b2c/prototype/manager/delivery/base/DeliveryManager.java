package com.b2c.prototype.manager.delivery.base;

import com.b2c.prototype.dao.delivery.IDeliveryDao;
import com.b2c.prototype.modal.dto.payload.DeliveryDto;
import com.b2c.prototype.modal.entity.delivery.Delivery;
import com.b2c.prototype.modal.entity.order.OrderArticularItem;
import com.b2c.prototype.service.common.EntityOperationManager;
import com.b2c.prototype.service.common.IEntityOperationManager;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.delivery.IDeliveryManager;
import com.b2c.prototype.service.query.ISearchService;
import com.b2c.prototype.service.supplier.ISupplierService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.b2c.prototype.util.Constant.ORDER_ID;

@Slf4j
public class DeliveryManager implements IDeliveryManager {

    private final IEntityOperationManager entityOperationDao;
    private final ISearchService searchService;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;

    public DeliveryManager(IDeliveryDao deliveryDao,
                           ISearchService searchService,
                           ITransformationFunctionService transformationFunctionService,
                           ISupplierService supplierService) {
        this.entityOperationDao = new EntityOperationManager(deliveryDao);
        this.searchService = searchService;
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
    }

    @Override
    public void saveUpdateDelivery(String orderId, DeliveryDto deliveryDto) {
        entityOperationDao.executeConsumer(session -> {
            OrderArticularItem orderItemDataOption = searchService.getEntity(
                    OrderArticularItem.class,
                    supplierService.parameterStringSupplier(ORDER_ID, orderId));
            Delivery newDelivery = transformationFunctionService.getEntity(Delivery.class, deliveryDto);
            Delivery delivery = orderItemDataOption.getDelivery();
            if (delivery != null) {
                newDelivery.setId(delivery.getId());
            }
            orderItemDataOption.setDelivery(newDelivery);
            session.merge(orderItemDataOption);
        });
    }

    @Override
    public void deleteDelivery(String orderId) {
        entityOperationDao.deleteEntity(
                supplierService.entityFieldSupplier(
                        OrderArticularItem.class,
                        supplierService.parameterStringSupplier(ORDER_ID, orderId),
                        transformationFunctionService.getTransformationFunction(OrderArticularItem.class, Delivery.class)));
    }

    @Override
    public DeliveryDto getDelivery(String orderId) {
        return searchService.getEntityDto(
                OrderArticularItem.class,
                supplierService.parameterStringSupplier(ORDER_ID, orderId),
                transformationFunctionService.getTransformationFunction(OrderArticularItem.class, DeliveryDto.class));
    }

    @Override
    public List<DeliveryDto> getDeliveries() {
        return entityOperationDao.getEntityGraphDtoList("",
                transformationFunctionService.getTransformationFunction(Delivery.class, DeliveryDto.class));
    }
}
