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
import org.springframework.stereotype.Service;

@Service
public class UserDetailsTransformService implements IUserDetailsTransformService {

    @Override
    public UserDetails mapRegistrationUserDetailsDtoToUserDetails(RegistrationUserDetailsDto registrationUserDetailsDto) {
        return null;
    }

    @Override
    public UserDetails mapUserDetailsDtoToUserDetails(UserDetailsDto userDetailsDto) {
        return null;
    }

    @Override
    public ResponseUserDetailsDto mapUserDetailsToResponseUserDetailsDto(UserDetails userDetails) {
        return null;
    }

    @Override
    public CountryPhoneCode mapConstantPayloadDtoToCountryPhoneCode(ConstantPayloadDto constantPayloadDto) {
        return null;
    }

    @Override
    public ConstantPayloadDto mapCountryPhoneCodeDtoToConstantPayloadDto(CountryPhoneCode userDetailsDto) {
        return null;
    }

    @Override
    public ResponseUserAddressDto mapUserAddressToResponseUserAddressDto(UserAddress userAddress) {
        return null;
    }

    @Override
    public AddressDto mapUserAddressToAddressDto(UserAddress userAddress) {
        return null;
    }
}
