package com.b2c.prototype.modal.dto.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressDto {
    private String country;
    private String city;
    private String street;
    private String street2;
    private int buildingNumber;
    private int florNumber;
    private int apartmentNumber;
    private String zipCode;
}
