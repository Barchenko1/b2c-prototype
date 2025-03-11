package com.b2c.prototype.modal.dto.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressDto {
    private String country;
    private String city;
    private String street;
    private String buildingNumber;
    private int florNumber;
    private int apartmentNumber;
    private String zipCode;
}
