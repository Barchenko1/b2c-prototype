package com.b2c.prototype.modal.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseContactPhoneDto {
    private String countryPhoneCode;
    private String phoneNumber;
}
