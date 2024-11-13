package com.b2c.prototype.modal.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseContactInfoDto {
    private String name;
    private String secondName;
    private ResponseContactPhoneDto contactPhoneDto;
}
