package com.b2c.prototype.modal.dto.request;

import lombok.Data;

@Data
public class RequestCardDto {
    private String cartNumber;
    private String dateOfExpire;
    private int cvv;
    private String ownerName;
    private String ownerSecondName;
}
