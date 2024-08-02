package com.b2c.prototype.modal.client.dto.request;

import lombok.Data;

@Data
public class RequestCardDto {
    private String cartNumber;
    private String dateOfExpire;
    private String cvv;
    private String ownerName;
    private String ownerSecondName;
}
