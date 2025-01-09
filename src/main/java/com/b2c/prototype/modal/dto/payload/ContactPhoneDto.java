package com.b2c.prototype.modal.dto.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactPhoneDto {
    private String countryPhoneCode;
    private String phoneNumber;
}
