package com.b2c.prototype.transform.userdetails;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.payload.constant.CountryDto;
import com.b2c.prototype.modal.dto.payload.constant.CountryPhoneCodeDto;
import com.b2c.prototype.modal.dto.payload.message.MessageDto;
import com.b2c.prototype.modal.dto.payload.message.ResponseMessageOverviewDto;
import com.b2c.prototype.modal.dto.payload.message.ResponseMessagePayloadDto;
import com.b2c.prototype.modal.dto.payload.order.AddressDto;
import com.b2c.prototype.modal.dto.payload.order.ContactInfoDto;
import com.b2c.prototype.modal.dto.payload.order.ContactPhoneDto;
import com.b2c.prototype.modal.dto.payload.order.CreditCardDto;
import com.b2c.prototype.modal.dto.payload.user.DeviceDto;
import com.b2c.prototype.modal.dto.payload.user.RegistrationUserDetailsDto;
import com.b2c.prototype.modal.dto.payload.user.UserAddressDto;
import com.b2c.prototype.modal.dto.payload.user.UserCreditCardDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsDto;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.modal.entity.address.UserAddress;
import com.b2c.prototype.modal.entity.message.Message;
import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.b2c.prototype.modal.entity.user.ContactPhone;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.b2c.prototype.modal.entity.user.Device;
import com.b2c.prototype.modal.entity.user.UserCreditCard;
import com.b2c.prototype.modal.entity.user.UserDetails;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.nimbusds.jose.util.Pair;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.b2c.prototype.util.Constant.VALUE;
import static com.b2c.prototype.util.Util.getUUID;

@Service
public class UserDetailsTransformService implements IUserDetailsTransformService {

    private final IGeneralEntityDao generalEntityDao;

    public UserDetailsTransformService(IGeneralEntityDao generalEntityDao) {
        this.generalEntityDao = generalEntityDao;
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
        return UserDetails.builder()
                .contactInfo(ContactInfo.builder()
                        .email(registrationUserDetailsDto.getEmail())
                        .isEmailVerified(false)
                        .isContactPhoneVerified(false)
                        .build())
                .userId(getUUID())
                .isActive(false)
                .dateOfCreate(LocalDateTime.now())
                .build();
    }

    @Override
    public UserDetails mapUserDetailsDtoToUserDetails(UserDetailsDto userDetailsDto) {
        return UserDetails.builder()
                .userId(userDetailsDto.getUserId())
                .isActive(userDetailsDto.isActive())
                .username(userDetailsDto.getUsername())
                .dateOfCreate(userDetailsDto.getDateOfCreate())
                .contactInfo(ContactInfo.builder()
                        .firstName(userDetailsDto.getContactInfo().getFirstName())
                        .lastName(userDetailsDto.getContactInfo().getLastName())
                        .email(userDetailsDto.getContactInfo().getEmail())
                        .isEmailVerified(userDetailsDto.getContactInfo().isEmailVerified())
                        .isContactPhoneVerified(userDetailsDto.getContactInfo().isPhoneVerified())
                        .contactPhone(ContactPhone.builder()
                                .countryPhoneCode(generalEntityDao.findEntity("CountryPhoneCode.findByValue",
                                        Pair.of(VALUE, userDetailsDto.getContactInfo().getContactPhone().getCountryPhoneCode().getValue())))
                                .phoneNumber(userDetailsDto.getContactInfo().getContactPhone().getPhoneNumber())
                                .build())
                        .birthdayDate(userDetailsDto.getContactInfo().getBirthdayDate())
                        .build())
                .userCreditCards(userDetailsDto.getCreditCards().stream()
                        .map(userCreditCard -> UserCreditCard.builder()
                                .creditCard(CreditCard.builder()
                                        .cardNumber(userCreditCard.getCreditCard().getCardNumber())
                                        .monthOfExpire(userCreditCard.getCreditCard().getMonthOfExpire())
                                        .yearOfExpire(userCreditCard.getCreditCard().getYearOfExpire())
                                        .cvv(userCreditCard.getCreditCard().getCvv())
                                        .isActive(userCreditCard.getCreditCard().isActive())
                                        .ownerName(userCreditCard.getCreditCard().getOwnerName())
                                        .ownerSecondName(userCreditCard.getCreditCard().getOwnerSecondName())
                                        .build())
                                .isDefault(userCreditCard.isDefault())
                                .build())
                        .collect(Collectors.toSet()))
                .userAddresses(userDetailsDto.getAddresses().stream()
                        .map(userAddressDto -> UserAddress.builder()
                                .address(Address.builder()
                                        .country(generalEntityDao.findEntity("Country.findByValue",
                                                Pair.of(VALUE, userAddressDto.getAddress().getCountry().getValue())))
                                        .city(userAddressDto.getAddress().getCity())
                                        .street(userAddressDto.getAddress().getStreet())
                                        .buildingNumber(userAddressDto.getAddress().getBuildingNumber())
                                        .florNumber(userAddressDto.getAddress().getFlorNumber())
                                        .apartmentNumber(userAddressDto.getAddress().getApartmentNumber())
                                        .zipCode(userAddressDto.getAddress().getZipCode())
                                        .build())
                                .isDefault(userAddressDto.isDefault())
                                .build())
                        .collect(Collectors.toSet()))
                .devices(userDetailsDto.getDevices().stream()
                        .map(deviceDto -> Device.builder()
                                .ipAddress(deviceDto.getIpAddress())
                                .loginTime(deviceDto.getLoginTime())
                                .userAgent(deviceDto.getUserAgent())
                                .screenWidth(deviceDto.getScreenWidth())
                                .screenHeight(deviceDto.getScreenHeight())
                                .timezone(deviceDto.getTimezone())
                                .language(deviceDto.getLanguage())
                                .platform(deviceDto.getPlatform())
                                .isThisDevice(deviceDto.isThisDevice())
                                .build())
                        .collect(Collectors.toSet()))
                .build();
    }

    @Override
    public UserDetailsDto mapUserDetailsToResponseUserDetailsDto(UserDetails userDetails) {
        return UserDetailsDto.builder()
                .userId(userDetails.getUserId())
                .isActive(userDetails.isActive())
                .username(userDetails.getUsername())
                .dateOfCreate(userDetails.getDateOfCreate())
                .contactInfo(ContactInfoDto.builder()
                        .firstName(userDetails.getContactInfo().getFirstName())
                        .lastName(userDetails.getContactInfo().getLastName())
                        .email(userDetails.getContactInfo().getEmail())
                        .isEmailVerified(userDetails.getContactInfo().isEmailVerified())
                        .isPhoneVerified(userDetails.getContactInfo().isContactPhoneVerified())
                        .contactPhone(ContactPhoneDto.builder()
                                .countryPhoneCode(CountryPhoneCodeDto.builder()
                                        .label(userDetails.getContactInfo().getContactPhone().getCountryPhoneCode().getLabel())
                                        .value(userDetails.getContactInfo().getContactPhone().getCountryPhoneCode().getValue())
                                        .build())
                                .phoneNumber(userDetails.getContactInfo().getContactPhone().getPhoneNumber())
                                .build())
                        .birthdayDate(userDetails.getContactInfo().getBirthdayDate())
                        .build())
                .creditCards(userDetails.getUserCreditCards().stream()
                        .map(userCreditCard -> UserCreditCardDto.builder()
                                .creditCard(CreditCardDto.builder()
                                        .cardNumber(userCreditCard.getCreditCard().getCardNumber())
                                        .monthOfExpire(userCreditCard.getCreditCard().getMonthOfExpire())
                                        .yearOfExpire(userCreditCard.getCreditCard().getYearOfExpire())
                                        .isActive(userCreditCard.getCreditCard().isActive())
                                        .ownerName(userCreditCard.getCreditCard().getOwnerName())
                                        .ownerSecondName(userCreditCard.getCreditCard().getOwnerSecondName())
                                        .build())
                                .isDefault(userCreditCard.isDefault())
                                .build())
                        .toList())
                .addresses(userDetails.getUserAddresses().stream()
                        .map(userAddress -> UserAddressDto.builder()
                                .address(AddressDto.builder()
                                        .country(CountryDto.builder()
                                                .label(userAddress.getAddress().getCountry().getLabel())
                                                .value(userAddress.getAddress().getCountry().getValue())
                                                .build())
                                        .city(userAddress.getAddress().getCity())
                                        .street(userAddress.getAddress().getStreet())
                                        .buildingNumber(userAddress.getAddress().getBuildingNumber())
                                        .florNumber(userAddress.getAddress().getFlorNumber())
                                        .apartmentNumber(userAddress.getAddress().getApartmentNumber())
                                        .zipCode(userAddress.getAddress().getZipCode())
                                        .build())
                                .isDefault(userAddress.isDefault())
                                .build())
                        .toList())
                .devices(userDetails.getDevices().stream()
                        .map(device -> DeviceDto.builder()
                                .ipAddress(device.getIpAddress())
                                .loginTime(device.getLoginTime())
                                .userAgent(device.getUserAgent())
                                .screenWidth(device.getScreenWidth())
                                .screenHeight(device.getScreenHeight())
                                .timezone(device.getTimezone())
                                .language(device.getLanguage())
                                .platform(device.getPlatform())
                                .isThisDevice(device.isThisDevice())
                                .build())
                        .toList())
                .build();
    }

    @Override
    public UserAddressDto mapUserAddressToUserAddressDto(UserAddress userAddress) {
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
    public DeviceDto mapDeviceToDeviceDto(Device device) {
        return null;
    }

    @Override
    public UserCreditCard mapUserCreditCardToUserCreditCardDto(UserCreditCardDto userCreditCardDto) {
        return null;
    }

    @Override
    public UserCreditCardDto mapUserCreditCardToResponseUserCreditCard(UserCreditCard userCreditCard) {
        return null;
    }

    @Override
    public UserCreditCardDto mapUserCreditCardToResponseCreditCard(CreditCard creditCard) {
        return null;
    }
}
