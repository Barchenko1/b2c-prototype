package com.b2c.prototype.modal.dto.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationUserProfileDto {
    private String username;
    private String email;
    private String password;
}
