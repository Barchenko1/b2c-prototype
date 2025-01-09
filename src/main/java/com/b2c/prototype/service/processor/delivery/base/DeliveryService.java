package com.b2c.prototype.service.processor.delivery.base;

import com.b2c.prototype.dao.delivery.IDeliveryDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.payload.DeliveryDto;
import com.b2c.prototype.modal.dto.searchfield.DeliverySearchFieldEntityDto;
import com.b2c.prototype.modal.entity.delivery.Delivery;
import com.b2c.prototype.modal.entity.order.OrderItemData;
import com.b2c.prototype.service.common.EntityOperationDao;
import com.b2c.prototype.service.common.IEntityOperationDao;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.delivery.IDeliveryService;
import com.b2c.prototype.service.processor.query.IQueryService;
import com.b2c.prototype.service.supplier.ISupplierService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.b2c.prototype.util.Constant.ORDER_ID;

@Slf4j
public class DeliveryService implements IDeliveryService {

    private final IEntityOperationDao entityOperationDao;
    private final IQueryService queryService;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;

    public DeliveryService(IDeliveryDao deliveryDao,
                           IQueryService queryService,
                           ITransformationFunctionService transformationFunctionService,
                           ISupplierService supplierService) {
        this.entityOperationDao = new EntityOperationDao(deliveryDao);
        this.queryService = queryService;
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
    }

    @Override
    public void saveUpdateDelivery(DeliverySearchFieldEntityDto deliverySearchFieldEntityDto) {
        entityOperationDao.executeConsumer(session -> {
            OrderItemData orderItemData = queryService.getEntity(
                    OrderItemData.class,
                    supplierService.parameterStringSupplier(ORDER_ID, deliverySearchFieldEntityDto.getSearchField()));
            DeliveryDto deliveryDto = deliverySearchFieldEntityDto.getNewEntity();
            Delivery newDelivery = transformationFunctionService.getEntity(Delivery.class, deliveryDto);
            Delivery delivery = orderItemData.getDelivery();
            if (delivery != null) {
                newDelivery.setId(delivery.getId());
            }
            orderItemData.setDelivery(newDelivery);
            session.merge(orderItemData);
        });
    }

    @Override
    public void deleteDelivery(OneFieldEntityDto oneFieldEntityDto) {
        entityOperationDao.deleteEntity(
                supplierService.entityFieldSupplier(
                        OrderItemData.class,
                        supplierService.parameterStringSupplier(ORDER_ID, oneFieldEntityDto.getValue()),
                        transformationFunctionService.getTransformationFunction(OrderItemData.class, Delivery.class)));
    }

    @Override
    public DeliveryDto getDelivery(OneFieldEntityDto oneFieldEntityDto) {
        return queryService.getEntityDto(
                OrderItemData.class,
                supplierService.parameterStringSupplier(ORDER_ID, oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(OrderItemData.class, DeliveryDto.class));
    }

    @Override
    public List<DeliveryDto> getDeliveries() {
        return entityOperationDao.getEntityDtoList(
                transformationFunctionService.getTransformationFunction(Delivery.class, DeliveryDto.class));
    }
}
