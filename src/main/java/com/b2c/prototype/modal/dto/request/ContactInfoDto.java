package com.b2c.prototype.modal.dto.request;

import lombok.Data;

@Data
public class ContactInfoDto {
    private String name;
    private String secondName;
    private String phoneNumber;
    private String countryPhoneCode;
}
