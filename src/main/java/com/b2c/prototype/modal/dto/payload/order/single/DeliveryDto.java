package com.b2c.prototype.modal.dto.payload.order.single;

import com.b2c.prototype.modal.dto.payload.order.AddressDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeliveryDto {
    private AddressDto deliveryAddress;
    private String deliveryType;
    private String deliveryDate;
    private String timeDurationOption;
}
