package com.b2c.prototype.transform.order;

import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.dto.payload.order.single.ResponseCustomerOrderDetails;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.b2c.prototype.modal.entity.order.CustomerSingleDeliveryOrder;
import com.b2c.prototype.modal.entity.order.OrderStatus;
import com.b2c.prototype.modal.entity.payment.PaymentMethod;
import org.springframework.stereotype.Service;

@Service
public class OrderTransformService implements IOrderTransformService {

    @Override
    public OrderStatus mapConstantPayloadDtoToOrderStatus(ConstantPayloadDto constantPayloadDto) {
        return null;
    }

    @Override
    public ConstantPayloadDto mapOrderStatusToConstantPayloadDto(OrderStatus orderStatus) {
        return null;
    }

    @Override
    public PaymentMethod mapConstantPayloadDtoToPaymentMethod(ConstantPayloadDto constantPayloadDto) {
        return null;
    }

    @Override
    public ConstantPayloadDto mapPaymentMethodToConstantPayloadDto(PaymentMethod paymentMethod) {
        return null;
    }

    @Override
    public DeliveryType mapConstantPayloadDtoToDeliveryType(ConstantPayloadDto constantPayloadDto) {
        return null;
    }

    @Override
    public ConstantPayloadDto mapDeliveryTypeToConstantPayloadDto(DeliveryType deliveryType) {
        return null;
    }

    @Override
    public ResponseCustomerOrderDetails mapCustomerSingleDeliveryOrderToResponseCustomerOrderDetails(CustomerSingleDeliveryOrder customerSingleDeliveryOrder) {
        return null;
    }
}
