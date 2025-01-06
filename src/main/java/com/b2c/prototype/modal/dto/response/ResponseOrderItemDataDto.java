package com.b2c.prototype.modal.dto.response;

import com.b2c.prototype.modal.dto.request.BeneficiaryDto;
import com.b2c.prototype.modal.dto.request.DeliveryDto;
import com.b2c.prototype.modal.dto.request.ItemDataOptionQuantityDto;
import com.b2c.prototype.modal.dto.request.PaymentDto;
import com.b2c.prototype.modal.dto.request.UserProfileDto;
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
