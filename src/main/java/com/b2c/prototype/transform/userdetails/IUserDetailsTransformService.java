package com.b2c.prototype.transform.userdetails;

import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.dto.payload.item.MetaDataDto;
import com.b2c.prototype.modal.dto.payload.message.MessageDto;
import com.b2c.prototype.modal.dto.payload.message.ResponseMessageOverviewDto;
import com.b2c.prototype.modal.dto.payload.message.ResponseMessagePayloadDto;
import com.b2c.prototype.modal.dto.payload.order.AddressDto;
import com.b2c.prototype.modal.dto.payload.order.ResponseCreditCardDto;
import com.b2c.prototype.modal.dto.payload.user.DeviceDto;
import com.b2c.prototype.modal.dto.payload.user.RegistrationUserDetailsDto;
import com.b2c.prototype.modal.dto.payload.user.ResponseDeviceDto;
import com.b2c.prototype.modal.dto.payload.user.ResponseUserAddressDto;
import com.b2c.prototype.modal.dto.payload.user.ResponseUserCreditCardDto;
import com.b2c.prototype.modal.dto.payload.user.ResponseUserDetailsDto;
import com.b2c.prototype.modal.dto.payload.user.UserCreditCardDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsDto;
import com.b2c.prototype.modal.entity.address.UserAddress;
import com.b2c.prototype.modal.entity.item.MetaData;
import com.b2c.prototype.modal.entity.message.Message;
import com.b2c.prototype.modal.entity.message.MessageStatus;
import com.b2c.prototype.modal.entity.message.MessageType;
import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.b2c.prototype.modal.entity.user.Device;
import com.b2c.prototype.modal.entity.user.UserCreditCard;
import com.b2c.prototype.modal.entity.user.UserDetails;

public interface IUserDetailsTransformService {
    MessageType mapConstantPayloadDtoToMessageType(ConstantPayloadDto constantPayloadDto);
    ConstantPayloadDto mapMessageTypeToConstantPayloadDto(MessageType messageType);

    MessageStatus mapConstantPayloadDtoToMessageStatus(ConstantPayloadDto constantPayloadDto);
    ConstantPayloadDto mapMessageStatusToConstantPayloadDto(MessageStatus messageStatus);

    Message mapMessageDtoToMessage(MessageDto messageDto);
    ResponseMessageOverviewDto mapMessageToResponseMessageOverviewDto(Message message);
    ResponseMessagePayloadDto mapResponseMessagePayloadDtoToMessage(Message message);

    UserDetails mapRegistrationUserDetailsDtoToUserDetails(RegistrationUserDetailsDto registrationUserDetailsDto);
    UserDetails mapUserDetailsDtoToUserDetails(UserDetailsDto userDetailsDto);

    ResponseUserDetailsDto mapUserDetailsToResponseUserDetailsDto(UserDetails userDetails);

    CountryPhoneCode mapConstantPayloadDtoToCountryPhoneCode(ConstantPayloadDto constantPayloadDto);
    ConstantPayloadDto mapCountryPhoneCodeDtoToConstantPayloadDto(CountryPhoneCode userDetailsDto);

    ResponseUserAddressDto mapUserAddressToResponseUserAddressDto(UserAddress userAddress);
    AddressDto mapUserAddressToAddressDto(UserAddress userAddress);

    Device mapDeviceDtoToDevice(DeviceDto deviceDto);
    ResponseDeviceDto mapDeviceToResponseDeviceDto(Device device);

    UserCreditCard mapUserCreditCardToUserCreditCardDto(UserCreditCardDto userCreditCardDto);
    ResponseUserCreditCardDto mapUserCreditCardToResponseUserCreditCard(UserCreditCard userCreditCard);
    ResponseCreditCardDto mapUserCreditCardToResponseCreditCard(CreditCard creditCard);


}
