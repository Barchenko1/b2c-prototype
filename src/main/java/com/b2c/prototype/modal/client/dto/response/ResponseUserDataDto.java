package com.b2c.prototype.modal.client.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseUserDataDto {
    private String username;
    private String email;
    private String name;
    private String secondName;
    private String phone;

}
