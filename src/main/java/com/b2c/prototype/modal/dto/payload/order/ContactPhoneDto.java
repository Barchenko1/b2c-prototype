package com.b2c.prototype.modal.dto.payload.order;

import com.b2c.prototype.modal.dto.payload.constant.CountryPhoneCodeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactPhoneDto {
    private CountryPhoneCodeDto countryPhoneCode;
    private String phoneNumber;
}
