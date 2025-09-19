package com.b2c.prototype.manager.order.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.manager.order.ICustomerMultipleDeliveryOrderManager;
import com.b2c.prototype.modal.dto.payload.order.multi.CustomerMultiDeliveryOrderDto;
import com.b2c.prototype.modal.dto.payload.order.single.ResponseCustomerOrderDetails;
import com.b2c.prototype.transform.order.IOrderTransformService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerMultipleDeliveryOrderManager implements ICustomerMultipleDeliveryOrderManager {

    private final IGeneralEntityDao generalEntityDao;
    private final IOrderTransformService orderTransformService;

    public CustomerMultipleDeliveryOrderManager(IGeneralEntityDao generalEntityDao, IOrderTransformService orderTransformService){
        this.generalEntityDao = generalEntityDao;
        this.orderTransformService = orderTransformService;
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
