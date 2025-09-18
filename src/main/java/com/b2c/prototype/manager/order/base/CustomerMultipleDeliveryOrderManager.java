package com.b2c.prototype.manager.order.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.manager.order.ICustomerMultipleDeliveryOrderManager;
import com.b2c.prototype.modal.dto.payload.order.multi.CustomerMultiDeliveryOrderDto;
import com.b2c.prototype.modal.dto.payload.order.single.ResponseCustomerOrderDetails;
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.dao.query.IQueryService;
import com.tm.core.process.manager.common.operator.EntityOperationManager;
import com.tm.core.process.manager.common.IEntityOperationManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerMultipleDeliveryOrderManager implements ICustomerMultipleDeliveryOrderManager {

    private final IEntityOperationManager entityOperationManager;
    private final ITransformationFunctionService transformationFunctionService;
    private final IParameterFactory parameterFactory;

    public CustomerMultipleDeliveryOrderManager(IGeneralEntityDao orderItemDao,
                                                IQueryService queryService,
                                                ITransformationFunctionService transformationFunctionService,
                                                IParameterFactory parameterFactory) {
        this.entityOperationManager = new EntityOperationManager(null);
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
