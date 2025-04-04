package com.b2c.prototype.modal.dto.payload.order;

import com.b2c.prototype.modal.dto.payload.user.UserDetailsDto;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class ResponseCustomerOrderDetails {
    private String orderId;
    private long dateOfCreate;
    private UserDetailsDto userDetails;
    private Set<ArticularItemQuantityDto> itemDataOptionQuantities;
    private DeliveryDto delivery;
    private ContactInfoDto beneficiary;
    private PaymentDto payment;
    private String orderStatus;
    private String note;
}
