package com.b2c.prototype.transform.userdetails;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.payload.message.MessageDto;
import com.b2c.prototype.modal.dto.payload.message.ResponseMessageOverviewDto;
import com.b2c.prototype.modal.dto.payload.message.ResponseMessagePayloadDto;
import com.b2c.prototype.modal.dto.payload.order.CreditCardDto;
import com.b2c.prototype.modal.dto.payload.user.DeviceDto;
import com.b2c.prototype.modal.dto.payload.user.RegistrationUserDetailsDto;
import com.b2c.prototype.modal.dto.payload.user.UserAddressDto;
import com.b2c.prototype.modal.dto.payload.user.UserCreditCardDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsContactInfoDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsDto;
import com.b2c.prototype.modal.entity.address.UserAddress;
import com.b2c.prototype.modal.entity.message.Message;
import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.b2c.prototype.modal.entity.user.Device;
import com.b2c.prototype.modal.entity.user.UserCreditCard;
import com.b2c.prototype.modal.entity.user.UserDetails;
import com.b2c.prototype.transform.constant.IGeneralEntityTransformService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import static com.b2c.prototype.util.Util.getUUID;

@Service
public class UserDetailsTransformService implements IUserDetailsTransformService {

    private final IGeneralEntityDao generalEntityDao;
    private final IGeneralEntityTransformService generalEntityTransformService;

    public UserDetailsTransformService(IGeneralEntityDao generalEntityDao,
                                       IGeneralEntityTransformService generalEntityTransformService) {
        this.generalEntityDao = generalEntityDao;
        this.generalEntityTransformService = generalEntityTransformService;
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
                .contactInfo(generalEntityTransformService
                        .mapContactInfoDtoToContactInfo(userDetailsDto.getContactInfo()))
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
                .contactInfo(generalEntityTransformService
                        .mapContactInfoDtoToContactInfo(userDetailsContactInfoDto.getContactInfo()))
                .build();
    }

    @Override
    public UserDetailsDto mapUserDetailsToResponseUserDetailsDto(UserDetails userDetails) {
        return UserDetailsDto.builder()
                .userId(userDetails.getUserId())
                .isActive(userDetails.isActive())
                .username(userDetails.getUsername())
                .dateOfCreate(userDetails.getDateOfCreate())
                .contactInfo(generalEntityTransformService
                        .mapContactInfoToContactInfoDto(userDetails.getContactInfo()))
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
    public UserAddressDto mapUserAddressToUserAddressDto(UserAddress userAddress) {
        return UserAddressDto.builder()
                .address(generalEntityTransformService
                        .mapAddressToAddressDto(userAddress.getAddress()))
                .isDefault(userAddress.isDefault())
                .build();
    }

    @Override
    public UserAddress mapUserAddressDtoToUserAddress(UserAddressDto userAddressDto) {
        return UserAddress.builder()
                .address(generalEntityTransformService
                        .mapAddressDtoToAddress(userAddressDto.getAddress()))
                .isDefault(userAddressDto.isDefault())
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
