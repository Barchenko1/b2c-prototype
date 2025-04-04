package com.b2c.prototype.manager.order.base;

import com.b2c.prototype.dao.order.ICustomerOrderDao;
import com.b2c.prototype.modal.dto.payload.order.CustomerOrderDto;
import com.b2c.prototype.modal.dto.payload.order.ResponseCustomerOrderDetails;
import com.b2c.prototype.modal.entity.order.DeliveryArticularItemQuantity;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.order.ICustomerOrderManager;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.manager.common.EntityOperationManager;
import com.tm.core.process.manager.common.IEntityOperationManager;

import java.util.List;

import static com.b2c.prototype.util.Constant.ORDER_ID;

public class CustomerOrderManager implements ICustomerOrderManager {

    private final IEntityOperationManager entityOperationManager;
    private final ITransformationFunctionService transformationFunctionService;
    private final IParameterFactory parameterFactory;

    public CustomerOrderManager(ICustomerOrderDao orderItemDao,
                                ITransformationFunctionService transformationFunctionService,
                                IParameterFactory parameterFactory) {
        this.entityOperationManager = new EntityOperationManager(orderItemDao);
        this.transformationFunctionService = transformationFunctionService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void saveCustomerOrder(CustomerOrderDto customerOrderDto) {
        entityOperationManager.executeConsumer(session -> {
            DeliveryArticularItemQuantity orderItemDataOption =
                    transformationFunctionService.getEntity(session, DeliveryArticularItemQuantity.class, customerOrderDto);
            session.merge(orderItemDataOption);
        });
    }

    @Override
    public void updateCustomerOrder(String orderId, CustomerOrderDto customerOrderDto) {
        entityOperationManager.executeConsumer(session -> {
            DeliveryArticularItemQuantity orderItemDataOption = entityOperationManager.getNamedQueryEntity(
                    "",
                    parameterFactory.createStringParameter(ORDER_ID, orderId));
            DeliveryArticularItemQuantity newOrderItemDataOption =
                    transformationFunctionService.getEntity(DeliveryArticularItemQuantity.class, customerOrderDto);
            newOrderItemDataOption.setId(orderItemDataOption.getId());
            session.merge(orderItemDataOption);
        });
    }

    @Override
    public void deleteCustomerOrder(String orderId) {
        entityOperationManager.deleteEntityByParameter(
                parameterFactory.createStringParameter(ORDER_ID, orderId));
    }

    @Override
    public ResponseCustomerOrderDetails getResponseCustomerOrderDetails(String orderId) {
        return entityOperationManager.getGraphEntityDto(
                "",
                parameterFactory.createStringParameter(ORDER_ID, orderId),
                transformationFunctionService.getTransformationFunction(DeliveryArticularItemQuantity.class, ResponseCustomerOrderDetails.class));
    }

    @Override
    public List<ResponseCustomerOrderDetails> getResponseCustomerOrderDetailsList() {
        return entityOperationManager.getGraphEntityDtoList(
                "",
                transformationFunctionService.getTransformationFunction(DeliveryArticularItemQuantity.class, ResponseCustomerOrderDetails.class));
    }
}
