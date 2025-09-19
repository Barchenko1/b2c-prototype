package com.b2c.prototype.transform.address;

import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.dto.payload.constant.CountryDto;
import com.b2c.prototype.modal.dto.payload.order.AddressDto;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.address.Country;

public interface IAddressTransformService {
//        transformationFunctionService.addTransformationFunction(UserAddressDto .class, UserAddress .class, mapUserAddressDtoToUserAddressFunction());
//        transformationFunctionService.addTransformationFunction(UserAddress.class, ResponseUserAddressDto .class, mapUserAddressToResponseUserAddressDtoFunction());
//        transformationFunctionService.addTransformationFunction(UserAddress.class, AddressDto .class, mapUserAddressToAddressDtoDtoFunction());
//        transformationFunctionService.addTransformationFunction(AddressDto.class, Address .class, mapAddressDtoToAddressFunction());
//        transformationFunctionService.addTransformationFunction(Address.class, AddressDto.class, mapAddressToAddressDtoFunction());

    Country mapConstantPayloadDtoToCountry(CountryDto countryDto);
    CountryDto mapCountryToConstantPayloadDto(Country country);


    Address mapAddressDtoToAddress(AddressDto addressDto);
}
