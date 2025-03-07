package com.b2c.prototype.manager.delivery.base;

import com.b2c.prototype.dao.delivery.IDeliveryDao;
import com.b2c.prototype.modal.dto.payload.DeliveryDto;
import com.b2c.prototype.modal.entity.delivery.Delivery;
import com.b2c.prototype.modal.entity.order.OrderArticularItemQuantity;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.delivery.IDeliveryManager;
import com.b2c.prototype.service.query.ISearchService;
import com.b2c.prototype.service.supplier.ISupplierService;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.manager.common.EntityOperationManager;
import com.tm.core.process.manager.common.IEntityOperationManager;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.b2c.prototype.util.Constant.ORDER_ID;

@Slf4j
public class DeliveryManager implements IDeliveryManager {

    private final IEntityOperationManager entityOperationDao;
    private final ISearchService searchService;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;
    private final IParameterFactory parameterFactory;

    public DeliveryManager(IDeliveryDao deliveryDao,
                           ISearchService searchService,
                           ITransformationFunctionService transformationFunctionService,
                           ISupplierService supplierService,
                           IParameterFactory parameterFactory) {
        this.entityOperationDao = new EntityOperationManager(deliveryDao);
        this.searchService = searchService;
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void saveUpdateDelivery(String orderId, DeliveryDto deliveryDto) {
        entityOperationDao.executeConsumer(session -> {
            OrderArticularItemQuantity orderItemDataOption = searchService.getNamedQueryEntity(
                    OrderArticularItemQuantity.class,
                    "",
                    parameterFactory.createStringParameter(ORDER_ID, orderId));
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
                        OrderArticularItemQuantity.class,
                        "",
                        supplierService.parameterStringSupplier(ORDER_ID, orderId),
                        transformationFunctionService.getTransformationFunction(OrderArticularItemQuantity.class, Delivery.class)));
    }

    @Override
    public DeliveryDto getDelivery(String orderId) {
        return searchService.getNamedQueryEntityDto(
                OrderArticularItemQuantity.class,
                "",
                parameterFactory.createStringParameter(ORDER_ID, orderId),
                transformationFunctionService.getTransformationFunction(OrderArticularItemQuantity.class, DeliveryDto.class));
    }

    @Override
    public List<DeliveryDto> getDeliveries() {
        return entityOperationDao.getGraphEntityDtoList("",
                transformationFunctionService.getTransformationFunction(Delivery.class, DeliveryDto.class));
    }
}
