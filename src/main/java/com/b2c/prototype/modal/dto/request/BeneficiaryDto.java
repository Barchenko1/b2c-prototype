package com.b2c.prototype.modal.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BeneficiaryDto {
    private String firstName;
    private String lastName;
    private ContactPhoneDto contactPhone;
}
