package com.b2c.prototype.modal.dto.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactPhoneDto {
    private String countryPhoneCode;
    private String phoneNumber;
}
