package com.b2c.prototype.modal.dto.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderArticularItemQuantityDto {
    private UserDto user;
    private List<ArticularItemQuantityDto> itemDataOptionQuantities;
    private DeliveryDto delivery;
    private ContactInfoDto beneficiary;
    private PaymentDto payment;
    private String note;
}
