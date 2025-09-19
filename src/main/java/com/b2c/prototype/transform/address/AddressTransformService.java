package com.b2c.prototype.transform.address;

import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.dto.payload.constant.CountryDto;
import com.b2c.prototype.modal.dto.payload.order.AddressDto;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.address.Country;
import org.springframework.stereotype.Service;

@Service
public class AddressTransformService implements IAddressTransformService {

    @Override
    public Country mapConstantPayloadDtoToCountry(CountryDto countryDto) {
        return null;
    }

    @Override
    public CountryDto mapCountryToConstantPayloadDto(Country country) {
        return null;
    }

    @Override
    public Address mapAddressDtoToAddress(AddressDto addressDto) {
        return Address.builder()
                .country(mapCountryDtoToCountry(addressDto.getCountry()))
                .city(addressDto.getCity())
                .street(addressDto.getStreet())
                .buildingNumber(addressDto.getBuildingNumber())
                .florNumber(addressDto.getFlorNumber())
                .apartmentNumber(addressDto.getApartmentNumber())
                .zipCode(addressDto.getZipCode())
                .build();
    }

    private Country mapCountryDtoToCountry(CountryDto countryDto) {
        return Country.builder()
                .value(countryDto.getValue())
                .label(countryDto.getLabel())
                .flagImagePath(countryDto.getFlagImagePath())
                .build();
    }
}
