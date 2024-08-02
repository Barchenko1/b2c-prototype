package com.b2c.prototype.modal.client.dto.request;

import lombok.Data;

@Data
public class RequestAddressDto {
    private String country;
    private String street;
    private int building;
    private int flor;
    private int apartmentNumber;
}
