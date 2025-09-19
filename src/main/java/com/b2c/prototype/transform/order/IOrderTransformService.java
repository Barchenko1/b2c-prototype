package com.b2c.prototype.transform.order;

import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.b2c.prototype.modal.entity.order.OrderStatus;
import com.b2c.prototype.modal.entity.payment.PaymentMethod;

public interface IOrderTransformService {
    OrderStatus mapConstantPayloadDtoToOrderStatus(ConstantPayloadDto constantPayloadDto);
    ConstantPayloadDto mapOrderStatusToConstantPayloadDto(OrderStatus orderStatus);

    PaymentMethod mapConstantPayloadDtoToPaymentMethod(ConstantPayloadDto constantPayloadDto);
    ConstantPayloadDto mapPaymentMethodToConstantPayloadDto(PaymentMethod paymentMethod);

    DeliveryType mapConstantPayloadDtoToDeliveryType(ConstantPayloadDto constantPayloadDto);
    ConstantPayloadDto mapDeliveryTypeToConstantPayloadDto(DeliveryType deliveryType);
}
