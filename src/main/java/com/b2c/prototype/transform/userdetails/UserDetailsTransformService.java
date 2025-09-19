package com.b2c.prototype.transform.userdetails;

import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
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
import com.b2c.prototype.modal.entity.message.Message;
import com.b2c.prototype.modal.entity.message.MessageStatus;
import com.b2c.prototype.modal.entity.message.MessageType;
import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.b2c.prototype.modal.entity.user.Device;
import com.b2c.prototype.modal.entity.user.UserCreditCard;
import com.b2c.prototype.modal.entity.user.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsTransformService implements IUserDetailsTransformService {

    @Override
    public MessageType mapConstantPayloadDtoToMessageType(ConstantPayloadDto constantPayloadDto) {
        return null;
    }

    @Override
    public ConstantPayloadDto mapMessageTypeToConstantPayloadDto(MessageType messageType) {
        return null;
    }

    @Override
    public MessageStatus mapConstantPayloadDtoToMessageStatus(ConstantPayloadDto constantPayloadDto) {
        return null;
    }

    @Override
    public ConstantPayloadDto mapMessageStatusToConstantPayloadDto(MessageStatus messageStatus) {
        return null;
    }

    @Override
    public Message mapMessageDtoToMessage(MessageDto messageDto) {
        return null;
    }

    @Override
    public ResponseMessageOverviewDto mapMessageToResponseMessageOverviewDto(Message message) {
        return null;
    }

    @Override
    public ResponseMessagePayloadDto mapResponseMessagePayloadDtoToMessage(Message message) {
        return null;
    }

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

    @Override
    public Device mapDeviceDtoToDevice(DeviceDto deviceDto) {
        return null;
    }

    @Override
    public ResponseDeviceDto mapDeviceToResponseDeviceDto(Device device) {
        return null;
    }

    @Override
    public UserCreditCard mapUserCreditCardToUserCreditCardDto(UserCreditCardDto userCreditCardDto) {
        return null;
    }

    @Override
    public ResponseUserCreditCardDto mapUserCreditCardToResponseUserCreditCard(UserCreditCard userCreditCard) {
        return null;
    }

    @Override
    public ResponseCreditCardDto mapUserCreditCardToResponseCreditCard(CreditCard creditCard) {
        return null;
    }
}
