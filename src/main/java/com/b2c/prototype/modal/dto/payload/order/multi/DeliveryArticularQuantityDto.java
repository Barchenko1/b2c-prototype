package com.b2c.prototype.modal.dto.payload.order.multi;

import com.b2c.prototype.modal.dto.payload.order.AddressDto;
import com.b2c.prototype.modal.dto.payload.order.ArticularItemQuantityDto;
import com.b2c.prototype.modal.dto.payload.order.ContactInfoDto;
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
public class DeliveryArticularQuantityDto {
    private AddressDto deliveryAddress;
    private ArticularItemQuantityDto articularItemQuantity;
    private ContactInfoDto beneficiary;
    private String deliveryType;
    private String timeDurationOption;
}
