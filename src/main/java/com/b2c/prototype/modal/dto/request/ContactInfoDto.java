package com.b2c.prototype.modal.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactInfoDto {
    private String name;
    private String secondName;
    private ContactPhoneDto contactPhone;
}
