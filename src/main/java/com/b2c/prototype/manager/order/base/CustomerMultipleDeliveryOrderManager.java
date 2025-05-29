package com.b2c.prototype.manager.order.base;

import com.b2c.prototype.dao.order.ICustomerOrderDao;
import com.b2c.prototype.manager.order.ICustomerMultipleDeliveryOrderManager;
import com.b2c.prototype.modal.dto.payload.order.multi.CustomerMultiDeliveryOrderDto;
import com.b2c.prototype.modal.dto.payload.order.single.ResponseCustomerOrderDetails;
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.dao.identifier.IQueryService;
import com.tm.core.process.manager.common.EntityOperationManager;
import com.tm.core.process.manager.common.IEntityOperationManager;

import java.util.List;

public class CustomerMultipleDeliveryOrderManager implements ICustomerMultipleDeliveryOrderManager {

    private final IEntityOperationManager entityOperationManager;
    private final IQueryService queryService;
    private final ITransformationFunctionService transformationFunctionService;
    private final IParameterFactory parameterFactory;

    public CustomerMultipleDeliveryOrderManager(ICustomerOrderDao orderItemDao, IQueryService queryService,
                                                ITransformationFunctionService transformationFunctionService,
                                                IParameterFactory parameterFactory) {
        this.entityOperationManager = new EntityOperationManager(orderItemDao);
        this.queryService = queryService;
        this.transformationFunctionService = transformationFunctionService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void saveCustomerOrder(CustomerMultiDeliveryOrderDto customerMultiDeliveryOrderDto) {

    }

    @Override
    public void updateCustomerOrder(String orderId, CustomerMultiDeliveryOrderDto customerMultiDeliveryOrderDto) {

    }

    @Override
    public void deleteCustomerOrder(String orderId) {

    }

    @Override
    public void updateCustomerOrderStatus(String orderId, String statusValue) {

    }

    @Override
    public ResponseCustomerOrderDetails getResponseCustomerOrderDetails(String orderId) {
        return null;
    }

    @Override
    public List<ResponseCustomerOrderDetails> getResponseCustomerOrderDetailsList() {
        return List.of();
    }
}
