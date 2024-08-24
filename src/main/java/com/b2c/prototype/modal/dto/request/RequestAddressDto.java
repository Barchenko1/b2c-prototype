package com.b2c.prototype.modal.dto.request;

import lombok.Data;

@Data
public class RequestAddressDto {
    private String country;
    private String street;
    private int buildingNumber;
    private int flor;
    private int apartmentNumber;
}
