package com.b2c.prototype.modal.dto.payload.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationUserDetailsDto {
    private String email;
    private String password;
}
