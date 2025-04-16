package com.b2c.prototype.modal.dto.payload.order.multi;

import com.b2c.prototype.modal.dto.payload.order.PaymentDto;
import com.b2c.prototype.modal.dto.payload.user.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
//for future
public class CustomerMultiDeliveryOrderDto {
    private UserDto user;
    private List<DeliveryArticularQuantityDto> deliveryArticularQuantityDtoList;
    private PaymentDto payment;
    private String note;
}
