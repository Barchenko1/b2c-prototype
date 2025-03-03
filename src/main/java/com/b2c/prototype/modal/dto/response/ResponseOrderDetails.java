package com.b2c.prototype.modal.dto.response;

import com.b2c.prototype.modal.dto.payload.BeneficiaryDto;
import com.b2c.prototype.modal.dto.payload.DeliveryDto;
import com.b2c.prototype.modal.dto.payload.ArticularItemQuantityDto;
import com.b2c.prototype.modal.dto.payload.PaymentDto;
import com.b2c.prototype.modal.dto.payload.UserDetailsDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class ResponseOrderDetails {
    private String orderId;
    private long dateOfCreate;
    private UserDetailsDto userDetails;
    private Set<ArticularItemQuantityDto> itemDataOptionQuantities;
    private DeliveryDto delivery;
    private List<BeneficiaryDto> beneficiaries;
    private PaymentDto payment;
    private String orderStatus;
    private String note;
}
