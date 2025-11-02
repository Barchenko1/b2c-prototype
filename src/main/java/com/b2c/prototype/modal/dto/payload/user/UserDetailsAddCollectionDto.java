package com.b2c.prototype.modal.dto.payload.user;

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
public class UserDetailsAddCollectionDto {
    private String userId;
    private UserAddressDto address;
    private UserCreditCardDto creditCard;
    private DeviceDto device;
}
