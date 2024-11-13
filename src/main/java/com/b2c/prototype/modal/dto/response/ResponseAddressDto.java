package com.b2c.prototype.modal.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseAddressDto {
    private String country;
    private String city;
    private String street;
    private String street2;
    private int buildingNumber;
    private int flor;
    private int apartmentNumber;
    private String zipCode;
}
