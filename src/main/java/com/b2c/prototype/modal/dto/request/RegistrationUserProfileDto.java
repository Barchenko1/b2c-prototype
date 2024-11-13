package com.b2c.prototype.modal.dto.request;

import lombok.Data;

@Data
public class RegistrationUserProfileDto {
    private String username;
    private String email;
    private String password;
}
