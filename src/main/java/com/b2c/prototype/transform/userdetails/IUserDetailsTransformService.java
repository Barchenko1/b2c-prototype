package com.b2c.prototype.transform.userdetails;

import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.dto.payload.order.AddressDto;
import com.b2c.prototype.modal.dto.payload.user.RegistrationUserDetailsDto;
import com.b2c.prototype.modal.dto.payload.user.ResponseUserAddressDto;
import com.b2c.prototype.modal.dto.payload.user.ResponseUserDetailsDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsDto;
import com.b2c.prototype.modal.entity.address.UserAddress;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.b2c.prototype.modal.entity.user.UserDetails;

public interface IUserDetailsTransformService {
//        transformationFunctionService.addTransformationFunction(RegistrationUserDetailsDto .class, UserDetails .class, mapRegistrationUserDetailsDtoToUserDetailsFunction());
//        transformationFunctionService.addTransformationFunction(UserDetailsDto .class, UserDetails.class, mapUserDetailsDtoToUserDetailsFunction());
//        transformationFunctionService.addTransformationFunction(SearchFieldUpdateEntityDto .class, UserDetails.class, mapSearchFieldUpdateEntityDtoToUserDetailsFunction());
//        transformationFunctionService.addTransformationFunction(UserDetails.class, ResponseUserDetailsDto .class, mapUserDetailsToUserResponseUserDetailsDtoFunction());

    UserDetails mapRegistrationUserDetailsDtoToUserDetails(RegistrationUserDetailsDto registrationUserDetailsDto);
    UserDetails mapUserDetailsDtoToUserDetails(UserDetailsDto userDetailsDto);

    ResponseUserDetailsDto mapUserDetailsToResponseUserDetailsDto(UserDetails userDetails);

    CountryPhoneCode mapConstantPayloadDtoToCountryPhoneCode(ConstantPayloadDto constantPayloadDto);
    ConstantPayloadDto mapCountryPhoneCodeDtoToConstantPayloadDto(CountryPhoneCode userDetailsDto);

    ResponseUserAddressDto mapUserAddressToResponseUserAddressDto(UserAddress userAddress);
    AddressDto mapUserAddressToAddressDto(UserAddress userAddress);

}
