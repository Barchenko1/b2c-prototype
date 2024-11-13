package com.b2c.prototype.service.processor.userprofile.basic;

import com.b2c.prototype.modal.dto.request.RegistrationUserProfileDto;
import com.b2c.prototype.modal.dto.response.ResponseAddressDto;
import com.b2c.prototype.modal.dto.response.ResponseContactPhoneDto;
import com.b2c.prototype.modal.dto.response.ResponseUserDetailsDto;
import com.b2c.prototype.modal.constant.RoleEnum;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.b2c.prototype.modal.entity.user.ContactPhone;
import com.b2c.prototype.modal.entity.user.UserProfile;
import com.b2c.prototype.dao.user.IUserProfileDao;

import com.b2c.prototype.service.processor.userprofile.IUserProfileService;
import com.tm.core.dao.factory.IGeneralEntityFactory;
import com.tm.core.modal.GeneralEntity;
import com.tm.core.processor.finder.factory.IParameterFactory;
import com.tm.core.processor.finder.parameter.Parameter;
import org.hibernate.Session;

import java.util.List;
import java.util.function.Consumer;

public class UserProfileService implements IUserProfileService {

    private final IParameterFactory parameterFactory;
    private final IGeneralEntityFactory generalEntityFactory;
    private final IUserProfileDao userProfileDao;

    public UserProfileService(IParameterFactory parameterFactory,
                              IGeneralEntityFactory generalEntityFactory,
                              IUserProfileDao userProfileDao) {
        this.parameterFactory = parameterFactory;
        this.generalEntityFactory = generalEntityFactory;
        this.userProfileDao = userProfileDao;
    }

    @Override
    public void createNewUser(RegistrationUserProfileDto registrationUserProfileDto, RoleEnum roleEnum) {
        UserProfile newUserProfile = UserProfile.builder()
                .username(registrationUserProfileDto.getUsername())
                .email(registrationUserProfileDto.getEmail())
                .dateOfCreate(System.currentTimeMillis())
                .build();

        // to do need call to auth service
        GeneralEntity generalEntity = generalEntityFactory.getOneGeneralEntity(newUserProfile);
        userProfileDao.saveGeneralEntity(generalEntity);
    }

    @Override
    public void changeUserStatusByLogin(String username, boolean isActive) {
        Consumer<Session> consumer = session -> {
            Parameter parameter = parameterFactory.createStringParameter("username", username);
            UserProfile existingUser = userProfileDao.getGeneralEntity(UserProfile.class, parameter);
            existingUser.setActive(isActive);
            session.merge(existingUser);
        };
        userProfileDao.updateGeneralEntity(consumer);
    }

    @Override
    public void changeUserStatusByEmail(String email, boolean isActive) {
        Consumer<Session> consumer = session -> {
            Parameter parameter = parameterFactory.createStringParameter("email", email);
            UserProfile existingUser = userProfileDao.getGeneralEntity(UserProfile.class, parameter);
            existingUser.setActive(isActive);
            session.merge(existingUser);
        };
        userProfileDao.updateGeneralEntity(consumer);
    }

    @Override
    public void deleteUserByLogin(String username) {
        Parameter parameter = parameterFactory.createStringParameter("username", username);
        userProfileDao.deleteGeneralEntity(parameter);
    }

    @Override
    public void deleteUserByEmail(String email) {
        Parameter parameter = parameterFactory.createStringParameter("email", email);
        userProfileDao.deleteGeneralEntity(parameter);
    }

    @Override
    public ResponseUserDetailsDto getUserByUsername(String username) {
        Parameter parameter = new Parameter("username", username);
        UserProfile userProfile =
                (UserProfile) userProfileDao.getOptionalGeneralEntity(UserProfile.class, parameter)
                        .orElseThrow(() -> new RuntimeException("User not found with username: " + username));

        return getResponseUserDetails(userProfile);
    }

    @Override
    public ResponseUserDetailsDto getUserByEmail(String email) {
        Parameter parameter = new Parameter("email", email);
        UserProfile userProfile =
                (UserProfile) userProfileDao.getOptionalGeneralEntity(UserProfile.class, parameter)
                        .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        return getResponseUserDetails(userProfile);
    }

    private ResponseUserDetailsDto getResponseUserDetails(UserProfile userProfile) {
        ContactInfo contactInfo = userProfile.getContactInfo();
        ContactPhone contactPhone = contactInfo.getContactPhone();
        ResponseContactPhoneDto responseContactPhoneDto = ResponseContactPhoneDto.builder()
                .phoneNumber(contactPhone.getPhoneNumber())
                .countryPhoneCode(contactPhone.getCountryPhoneCode().getCode())
                .build();
        ResponseAddressDto responseAddressDto = getResponseAddressDto(userProfile.getAddress());
        return ResponseUserDetailsDto.builder()
                .username(userProfile.getUsername())
                .email(userProfile.getEmail())
                .name(contactInfo.getName())
                .secondName(contactInfo.getSecondName())
                .contactPhone(responseContactPhoneDto)
                .creditCardDtoList(List.of())
                .addressDto(responseAddressDto)
                .build();
    }

    private ResponseAddressDto getResponseAddressDto(Address address) {
        return ResponseAddressDto.builder()
                .country(address.getCountry().getValue())
                .city(address.getCity())
                .street(address.getStreet())
                .street2(address.getStreet2())
                .flor(address.getFlor())
                .apartmentNumber(address.getApartmentNumber())
                .zipCode(address.getZipCode())
                .build();
    }


}
