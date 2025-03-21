package com.b2c.prototype.modal.dto.response;

import com.b2c.prototype.modal.dto.payload.ContactInfoDto;
import com.b2c.prototype.modal.dto.payload.DeliveryDto;
import com.b2c.prototype.modal.dto.payload.ArticularItemQuantityDto;
import com.b2c.prototype.modal.dto.payload.PaymentDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsDto;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class ResponseOrderDetails {
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
