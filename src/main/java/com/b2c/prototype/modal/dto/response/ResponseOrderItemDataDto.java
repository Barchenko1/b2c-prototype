package com.b2c.prototype.modal.dto.response;

import com.b2c.prototype.modal.dto.payload.BeneficiaryDto;
import com.b2c.prototype.modal.dto.payload.DeliveryDto;
import com.b2c.prototype.modal.dto.payload.ItemDataOptionQuantityDto;
import com.b2c.prototype.modal.dto.payload.PaymentDto;
import com.b2c.prototype.modal.dto.payload.UserProfileDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class ResponseOrderItemDataDto {
    private String orderId;
    private long dateOfCreate;
    private UserProfileDto userProfile;
    private Set<ItemDataOptionQuantityDto> itemDataOptionQuantities;
    private DeliveryDto delivery;
    private List<BeneficiaryDto> beneficiaries;
    private PaymentDto payment;
    private String orderStatus;
    private String note;
}
