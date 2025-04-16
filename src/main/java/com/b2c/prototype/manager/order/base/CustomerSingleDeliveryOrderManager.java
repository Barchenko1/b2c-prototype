package com.b2c.prototype.manager.order.base;

import com.b2c.prototype.dao.order.ICustomerOrderDao;
import com.b2c.prototype.modal.dto.payload.order.single.CustomerSingleDeliveryOrderDto;
import com.b2c.prototype.modal.dto.payload.order.single.ResponseCustomerOrderDetails;
import com.b2c.prototype.modal.entity.order.CustomerSingleDeliveryOrder;
import com.b2c.prototype.modal.entity.order.OrderStatus;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.order.ICustomerSingleDeliveryOrderManager;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.dao.identifier.IQueryService;
import com.tm.core.process.dao.query.IFetchHandler;
import com.tm.core.process.manager.common.EntityOperationManager;
import com.tm.core.process.manager.common.IEntityOperationManager;

import java.util.List;

import static com.b2c.prototype.util.Constant.ORDER_ID;

public class CustomerSingleDeliveryOrderManager implements ICustomerSingleDeliveryOrderManager {

    private final IEntityOperationManager entityOperationManager;
    private final IQueryService queryService;
    private final IFetchHandler fetchHandler;
    private final ITransformationFunctionService transformationFunctionService;
    private final IParameterFactory parameterFactory;

    public CustomerSingleDeliveryOrderManager(ICustomerOrderDao orderItemDao,
                                              IQueryService queryService,
                                              IFetchHandler fetchHandler,
                                              ITransformationFunctionService transformationFunctionService,
                                              IParameterFactory parameterFactory) {
        this.entityOperationManager = new EntityOperationManager(orderItemDao);
        this.queryService = queryService;
        this.fetchHandler = fetchHandler;
        this.transformationFunctionService = transformationFunctionService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void saveCustomerOrder(CustomerSingleDeliveryOrderDto customerSingleDeliveryOrderDto) {
        entityOperationManager.executeConsumer(session -> {
            CustomerSingleDeliveryOrder customerSingleDeliveryOrder =
                    transformationFunctionService.getEntity(session, CustomerSingleDeliveryOrder.class, customerSingleDeliveryOrderDto);
            session.merge(customerSingleDeliveryOrder);
        });
    }

    @Override
    public void updateCustomerOrder(String orderId, CustomerSingleDeliveryOrderDto customerSingleDeliveryOrderDto) {
        entityOperationManager.executeConsumer(session -> {
            CustomerSingleDeliveryOrder existingCustomerSingleDeliveryOrder = entityOperationManager.getNamedQueryEntity(
                    "CustomerSingleDeliveryOrder.findByOrderIdWithPayment",
                    parameterFactory.createStringParameter(ORDER_ID, orderId));
            CustomerSingleDeliveryOrder newCustomerSingleDeliveryOrder =
                    transformationFunctionService.getEntity(session, CustomerSingleDeliveryOrder.class, customerSingleDeliveryOrderDto);
            existingCustomerSingleDeliveryOrder.setId(newCustomerSingleDeliveryOrder.getId());
            session.merge(existingCustomerSingleDeliveryOrder);
        });
    }

    @Override
    public void deleteCustomerOrder(String orderId) {
        entityOperationManager.deleteEntityByParameter(
                parameterFactory.createStringParameter(ORDER_ID, orderId));
    }

    @Override
    public void updateCustomerOrderStatus(String orderId, String statusValue) {
        entityOperationManager.executeConsumer(session -> {
            CustomerSingleDeliveryOrder existingCustomerSingleDeliveryOrder = entityOperationManager.getNamedQueryEntity(
                    "CustomerSingleDeliveryOrder.findByOrderIdWithPayment",
                    parameterFactory.createStringParameter(ORDER_ID, orderId));
            OrderStatus orderStatus = transformationFunctionService.getEntity(session, OrderStatus.class, orderId);
            existingCustomerSingleDeliveryOrder.setStatus(orderStatus);
            session.merge(existingCustomerSingleDeliveryOrder);
        });
    }

    @Override
    public ResponseCustomerOrderDetails getResponseCustomerOrderDetails(String orderId) {
        return entityOperationManager.getNamedQueryOptionalEntity(
                        "CustomerSingleDeliveryOrder.findByOrderIdWithPayment",
                        parameterFactory.createStringParameter(ORDER_ID, orderId))
                .map(o -> transformationFunctionService.getTransformationFunction(CustomerSingleDeliveryOrder.class, ResponseCustomerOrderDetails.class)
                        .apply((CustomerSingleDeliveryOrder) o))
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public List<ResponseCustomerOrderDetails> getResponseCustomerOrderDetailsList() {
        List<CustomerSingleDeliveryOrder> customerSingleDeliveryOrders = entityOperationManager.getNamedQueryEntityList("CustomerSingleDeliveryOrder.all");

        return customerSingleDeliveryOrders.stream()
                .map(transformationFunctionService.getTransformationFunction(
                        CustomerSingleDeliveryOrder.class, ResponseCustomerOrderDetails.class))
                .toList();
    }
}
