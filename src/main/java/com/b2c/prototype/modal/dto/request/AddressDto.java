package com.b2c.prototype.modal.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressDto {
    private String country;
    private String street;
    private String street2;
    private int buildingNumber;
    private int flor;
    private int apartmentNumber;
}
