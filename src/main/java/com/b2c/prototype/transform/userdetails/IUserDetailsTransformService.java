package com.b2c.prototype.transform.userdetails;

import com.b2c.prototype.modal.dto.payload.message.MessageDto;
import com.b2c.prototype.modal.dto.payload.message.ResponseMessageOverviewDto;
import com.b2c.prototype.modal.dto.payload.message.ResponseMessagePayloadDto;
import com.b2c.prototype.modal.dto.payload.order.AddressDto;
import com.b2c.prototype.modal.dto.payload.order.ContactInfoDto;
import com.b2c.prototype.modal.dto.payload.order.CreditCardDto;
import com.b2c.prototype.modal.dto.payload.user.DeviceDto;
import com.b2c.prototype.modal.dto.payload.user.RegistrationUserDetailsDto;
import com.b2c.prototype.modal.dto.payload.user.UserAddressDto;
import com.b2c.prototype.modal.dto.payload.user.UserCreditCardDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsContactInfoDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsDto;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.address.UserAddress;
import com.b2c.prototype.modal.entity.message.Message;
import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.b2c.prototype.modal.entity.user.Device;
import com.b2c.prototype.modal.entity.user.UserCreditCard;
import com.b2c.prototype.modal.entity.user.UserDetails;

public interface IUserDetailsTransformService {

    Message mapMessageDtoToMessage(MessageDto messageDto);
    ResponseMessageOverviewDto mapMessageToResponseMessageOverviewDto(Message message);
    ResponseMessagePayloadDto mapResponseMessagePayloadDtoToMessage(Message message);

    UserDetails mapRegistrationUserDetailsDtoToUserDetails(RegistrationUserDetailsDto registrationUserDetailsDto);
    UserDetails mapUserDetailsDtoToUserDetails(UserDetailsDto userDetailsDto);
    UserDetails mapUserDetailsContactInfoDtoToUserDetails(UserDetailsContactInfoDto userDetailsContactInfoDto);
    UserDetailsDto mapUserDetailsToResponseUserDetailsDto(UserDetails userDetails);

    ContactInfo mapContactInfoDtoToContactInfo(ContactInfoDto contactInfoDto);
    ContactInfoDto mapContactInfoToContactInfoDto(ContactInfo contactInfo);

    UserAddressDto mapUserAddressToUserAddressDto(UserAddress userAddress);
    UserAddress mapUserAddressDtoToUserAddress(UserAddressDto userAddress);

    AddressDto mapAddressToAddressDto(Address address);
    Address mapAddressDtoToAddress(AddressDto addressDto);

    Device mapDeviceDtoToDevice(DeviceDto deviceDto);
    DeviceDto mapDeviceToDeviceDto(Device device);

    UserCreditCard mapUserCreditCardDtoToUserCreditCard(UserCreditCardDto userCreditCardDto);
    UserCreditCardDto mapUserCreditCardToUserCreditCardDto(UserCreditCard userCreditCard);

    CreditCard mapCreditCardDtoToCreditCard(CreditCardDto creditCardDto);
    CreditCardDto mapCreditCardToCreditCardDto(CreditCard creditCard);
}
