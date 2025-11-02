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
import com.b2c.prototype.modal.dto.payload.user.UserDetailsContactInfoDto;
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
                .contactInfo(mapContactInfoDtoToContactInfo(userDetailsDto.getContactInfo()))
                .userCreditCards(userDetailsDto.getCreditCards().stream()
                        .map(this::mapUserCreditCardDtoToUserCreditCard)
                        .collect(Collectors.toSet()))
                .userAddresses(userDetailsDto.getAddresses().stream()
                        .map(this::mapUserAddressDtoToUserAddress)
                        .collect(Collectors.toSet()))
                .devices(userDetailsDto.getDevices().stream()
                        .map(this::mapDeviceDtoToDevice)
                        .collect(Collectors.toSet()))
                .build();
    }

    @Override
    public UserDetails mapUserDetailsContactInfoDtoToUserDetails(UserDetailsContactInfoDto userDetailsContactInfoDto) {
        return UserDetails.builder()
                .userId(userDetailsContactInfoDto.getUserId())
                .isActive(userDetailsContactInfoDto.isActive())
                .username(userDetailsContactInfoDto.getUsername())
                .contactInfo(mapContactInfoDtoToContactInfo(userDetailsContactInfoDto.getContactInfo()))
                .build();
    }

    @Override
    public UserDetailsDto mapUserDetailsToResponseUserDetailsDto(UserDetails userDetails) {
        return UserDetailsDto.builder()
                .userId(userDetails.getUserId())
                .isActive(userDetails.isActive())
                .username(userDetails.getUsername())
                .dateOfCreate(userDetails.getDateOfCreate())
                .contactInfo(mapContactInfoToContactInfoDto(userDetails.getContactInfo()))
                .creditCards(userDetails.getUserCreditCards().stream()
                        .map(this::mapUserCreditCardToUserCreditCardDto)
                        .toList())
                .addresses(userDetails.getUserAddresses().stream()
                        .map(this::mapUserAddressToUserAddressDto)
                        .toList())
                .devices(userDetails.getDevices().stream()
                        .map(this::mapDeviceToDeviceDto)
                        .toList())
                .build();
    }

    @Override
    public ContactInfo mapContactInfoDtoToContactInfo(ContactInfoDto contactInfoDto) {
        return ContactInfo.builder()
                .firstName(contactInfoDto.getFirstName())
                .lastName(contactInfoDto.getLastName())
                .email(contactInfoDto.getEmail())
                .isEmailVerified(contactInfoDto.isEmailVerified())
                .isContactPhoneVerified(contactInfoDto.isPhoneVerified())
                .contactPhone(ContactPhone.builder()
                        .countryPhoneCode(generalEntityDao.findEntity("CountryPhoneCode.findByValue",
                                Pair.of(VALUE, contactInfoDto.getContactPhone().getCountryPhoneCode().getValue())))
                        .phoneNumber(contactInfoDto.getContactPhone().getPhoneNumber())
                        .build())
                .birthdayDate(contactInfoDto.getBirthdayDate())
                .build();
    }

    @Override
    public ContactInfoDto mapContactInfoToContactInfoDto(ContactInfo contactInfo) {
        return ContactInfoDto.builder()
                .firstName(contactInfo.getFirstName())
                .lastName(contactInfo.getLastName())
                .email(contactInfo.getEmail())
                .isEmailVerified(contactInfo.isEmailVerified())
                .isPhoneVerified(contactInfo.isContactPhoneVerified())
                .contactPhone(ContactPhoneDto.builder()
                        .countryPhoneCode(CountryPhoneCodeDto.builder()
                                .label(contactInfo.getContactPhone().getCountryPhoneCode().getLabel())
                                .value(contactInfo.getContactPhone().getCountryPhoneCode().getValue())
                                .build())
                        .phoneNumber(contactInfo.getContactPhone().getPhoneNumber())
                        .build())
                .birthdayDate(contactInfo.getBirthdayDate())
                .build();
    }

    @Override
    public UserAddressDto mapUserAddressToUserAddressDto(UserAddress userAddress) {
        return UserAddressDto.builder()
                .address(mapAddressToAddressDto(userAddress.getAddress()))
                .isDefault(userAddress.isDefault())
                .build();
    }

    @Override
    public UserAddress mapUserAddressDtoToUserAddress(UserAddressDto userAddressDto) {
        return UserAddress.builder()
                .address(mapAddressDtoToAddress(userAddressDto.getAddress()))
                .isDefault(userAddressDto.isDefault())
                .build();
    }

    @Override
    public AddressDto mapAddressToAddressDto(Address address) {
        return AddressDto.builder()
                .country(CountryDto.builder()
                        .label(address.getCountry().getLabel())
                        .value(address.getCountry().getValue())
                        .build())
                .city(address.getCity())
                .street(address.getStreet())
                .buildingNumber(address.getBuildingNumber())
                .florNumber(address.getFlorNumber())
                .apartmentNumber(address.getApartmentNumber())
                .zipCode(address.getZipCode())
                .build();
    }

    @Override
    public Address mapAddressDtoToAddress(AddressDto addressDto) {
        return Address.builder()
                .country(generalEntityDao.findEntity("Country.findByValue",
                        Pair.of(VALUE, addressDto.getCountry().getValue())))
                .city(addressDto.getCity())
                .street(addressDto.getStreet())
                .buildingNumber(addressDto.getBuildingNumber())
                .florNumber(addressDto.getFlorNumber())
                .apartmentNumber(addressDto.getApartmentNumber())
                .zipCode(addressDto.getZipCode())
                .build();
    }

    @Override
    public Device mapDeviceDtoToDevice(DeviceDto deviceDto) {
        return Device.builder()
                .ipAddress(deviceDto.getIpAddress())
                .loginTime(deviceDto.getLoginTime())
                .userAgent(deviceDto.getUserAgent())
                .screenWidth(deviceDto.getScreenWidth())
                .screenHeight(deviceDto.getScreenHeight())
                .timezone(deviceDto.getTimezone())
                .language(deviceDto.getLanguage())
                .platform(deviceDto.getPlatform())
                .isThisDevice(deviceDto.isThisDevice())
                .build();
    }

    @Override
    public DeviceDto mapDeviceToDeviceDto(Device device) {
        return DeviceDto.builder()
                .ipAddress(device.getIpAddress())
                .loginTime(device.getLoginTime())
                .userAgent(device.getUserAgent())
                .screenWidth(device.getScreenWidth())
                .screenHeight(device.getScreenHeight())
                .timezone(device.getTimezone())
                .language(device.getLanguage())
                .platform(device.getPlatform())
                .isThisDevice(device.isThisDevice())
                .build();
    }

    @Override
    public UserCreditCard mapUserCreditCardDtoToUserCreditCard(UserCreditCardDto userCreditCardDto) {
        return UserCreditCard.builder()
                .creditCard(mapCreditCardDtoToCreditCard(userCreditCardDto.getCreditCard()))
                .isDefault(userCreditCardDto.isDefault())
                .build();
    }

    @Override
    public UserCreditCardDto mapUserCreditCardToUserCreditCardDto(UserCreditCard userCreditCard) {
        return UserCreditCardDto.builder()
                .creditCard(mapCreditCardToCreditCardDto(userCreditCard.getCreditCard()))
                .isDefault(userCreditCard.isDefault())
                .build();
    }

    @Override
    public CreditCard mapCreditCardDtoToCreditCard(CreditCardDto creditCardDto) {
        return CreditCard.builder()
                .cardNumber(creditCardDto.getCardNumber())
                .monthOfExpire(creditCardDto.getMonthOfExpire())
                .yearOfExpire(creditCardDto.getYearOfExpire())
                .cvv(creditCardDto.getCvv())
                .isActive(creditCardDto.isActive())
                .ownerName(creditCardDto.getOwnerName())
                .ownerSecondName(creditCardDto.getOwnerSecondName())
                .build();
    }

    @Override
    public CreditCardDto mapCreditCardToCreditCardDto(CreditCard creditCard) {
        return CreditCardDto.builder()
                .cardNumber(creditCard.getCardNumber())
                .monthOfExpire(creditCard.getMonthOfExpire())
                .yearOfExpire(creditCard.getYearOfExpire())
                .cvv(creditCard.getCvv())
                .isActive(creditCard.isActive())
                .ownerName(creditCard.getOwnerName())
                .ownerSecondName(creditCard.getOwnerSecondName())
                .build();
    }

}
