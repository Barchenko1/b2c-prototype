package com.b2c.prototype.modal.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class OrderItemDataDto {
    private UserProfileDto userProfile;
    private Set<ItemDataOptionQuantityDto> itemDataOptionQuantities;
    private DeliveryDto delivery;
    private List<BeneficiaryDto> beneficiaries;
    private PaymentDto payment;
    private String note;
}
