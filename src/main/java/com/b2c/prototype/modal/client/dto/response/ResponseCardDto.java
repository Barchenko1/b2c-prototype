package com.b2c.prototype.modal.client.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseCardDto {
    private String cartNumber;
    private String dateOfExpire;
    private String cvv;
    private boolean isActive;
    private String ownerName;
    private String ownerSecondName;
}
