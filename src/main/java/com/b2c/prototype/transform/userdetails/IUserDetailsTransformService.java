package com.b2c.prototype.transform.userdetails;

import com.b2c.prototype.modal.dto.payload.message.MessageDto;
import com.b2c.prototype.modal.dto.payload.message.ResponseMessageOverviewDto;
import com.b2c.prototype.modal.dto.payload.message.ResponseMessagePayloadDto;
import com.b2c.prototype.modal.dto.payload.order.AddressDto;
import com.b2c.prototype.modal.dto.payload.user.DeviceDto;
import com.b2c.prototype.modal.dto.payload.user.RegistrationUserDetailsDto;
import com.b2c.prototype.modal.dto.payload.user.UserAddressDto;
import com.b2c.prototype.modal.dto.payload.user.UserCreditCardDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsDto;
import com.b2c.prototype.modal.entity.address.UserAddress;
import com.b2c.prototype.modal.entity.message.Message;
import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.b2c.prototype.modal.entity.user.Device;
import com.b2c.prototype.modal.entity.user.UserCreditCard;
import com.b2c.prototype.modal.entity.user.UserDetails;

public interface IUserDetailsTransformService {

    Message mapMessageDtoToMessage(MessageDto messageDto);
    ResponseMessageOverviewDto mapMessageToResponseMessageOverviewDto(Message message);
    ResponseMessagePayloadDto mapResponseMessagePayloadDtoToMessage(Message message);

    UserDetails mapRegistrationUserDetailsDtoToUserDetails(RegistrationUserDetailsDto registrationUserDetailsDto);
    UserDetails mapUserDetailsDtoToUserDetails(UserDetailsDto userDetailsDto);
    UserDetailsDto mapUserDetailsToResponseUserDetailsDto(UserDetails userDetails);

    UserAddressDto mapUserAddressToUserAddressDto(UserAddress userAddress);
    AddressDto mapUserAddressToAddressDto(UserAddress userAddress);

    Device mapDeviceDtoToDevice(DeviceDto deviceDto);
    DeviceDto mapDeviceToDeviceDto(Device device);

    UserCreditCard mapUserCreditCardToUserCreditCardDto(UserCreditCardDto userCreditCardDto);
    UserCreditCardDto mapUserCreditCardToResponseUserCreditCard(UserCreditCard userCreditCard);
    UserCreditCardDto mapUserCreditCardToResponseCreditCard(CreditCard creditCard);


}
