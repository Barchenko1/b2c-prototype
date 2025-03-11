package com.b2c.prototype.manager.order.base;

import com.b2c.prototype.dao.order.IOrderItemDataDao;
import com.b2c.prototype.modal.dto.payload.OrderArticularItemQuantityDto;
import com.b2c.prototype.modal.dto.response.ResponseOrderDetails;
import com.b2c.prototype.modal.entity.order.DeliveryArticularItemQuantity;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.order.IOrderArticularItemQuantityManager;
import com.b2c.prototype.service.supplier.ISupplierService;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.manager.common.EntityOperationManager;
import com.tm.core.process.manager.common.IEntityOperationManager;

import java.util.List;

import static com.b2c.prototype.util.Constant.ORDER_ID;

public class OrderArticularItemQuantityManager implements IOrderArticularItemQuantityManager {

    private final IEntityOperationManager entityOperationDao;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;
    private final IParameterFactory parameterFactory;

    public OrderArticularItemQuantityManager(IOrderItemDataDao orderItemDao,
                                             ITransformationFunctionService transformationFunctionService,
                                             ISupplierService supplierService,
                                             IParameterFactory parameterFactory) {
        this.entityOperationDao = new EntityOperationManager(orderItemDao);
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void saveOrderArticularItemQuantity(OrderArticularItemQuantityDto orderArticularItemQuantityDto) {
        entityOperationDao.executeConsumer(session -> {
            DeliveryArticularItemQuantity orderItemDataOption =
                    transformationFunctionService.getEntity(session, DeliveryArticularItemQuantity.class, orderArticularItemQuantityDto);
            session.merge(orderItemDataOption);
        });
    }

    @Override
    public void updateOrderArticularItemQuantity(String orderId, OrderArticularItemQuantityDto orderArticularItemQuantityDto) {
        entityOperationDao.executeConsumer(session -> {
            DeliveryArticularItemQuantity orderItemDataOption = entityOperationDao.getNamedQueryEntity(
                    "",
                    parameterFactory.createStringParameter(ORDER_ID, orderId));
            DeliveryArticularItemQuantity newOrderItemDataOption =
                    transformationFunctionService.getEntity(DeliveryArticularItemQuantity.class, orderArticularItemQuantityDto);
            newOrderItemDataOption.setId(orderItemDataOption.getId());
            session.merge(orderItemDataOption);
        });
    }

    @Override
    public void deleteOrder(String orderId) {
        entityOperationDao.deleteEntityByParameter(
                parameterFactory.createStringParameter(ORDER_ID, orderId));
    }

    @Override
    public ResponseOrderDetails getResponseOrderDetails(String orderId) {
        return entityOperationDao.getGraphEntityDto(
                "",
                parameterFactory.createStringParameter(ORDER_ID, orderId),
                transformationFunctionService.getTransformationFunction(DeliveryArticularItemQuantity.class, ResponseOrderDetails.class));
    }

    @Override
    public List<ResponseOrderDetails> getResponseOrderDetailsList() {
        return entityOperationDao.getGraphEntityDtoList(
                "",
                transformationFunctionService.getTransformationFunction(DeliveryArticularItemQuantity.class, ResponseOrderDetails.class));
    }
}
