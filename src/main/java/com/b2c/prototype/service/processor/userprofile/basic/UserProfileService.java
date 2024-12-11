package com.b2c.prototype.service.processor.userprofile.basic;

import com.b2c.prototype.modal.dto.request.AddressDto;
import com.b2c.prototype.modal.dto.request.ContactPhoneDto;
import com.b2c.prototype.modal.dto.request.RegistrationUserProfileDto;
import com.b2c.prototype.modal.constant.RoleEnum;
import com.b2c.prototype.modal.dto.request.UserDetailsDto;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.b2c.prototype.modal.entity.user.ContactPhone;
import com.b2c.prototype.modal.entity.user.UserProfile;
import com.b2c.prototype.dao.user.IUserProfileDao;

import com.b2c.prototype.service.processor.userprofile.IUserProfileService;
import com.tm.core.processor.finder.factory.IParameterFactory;
import com.tm.core.processor.finder.parameter.Parameter;
import org.hibernate.Session;

import java.util.List;
import java.util.function.Consumer;

public class UserProfileService implements IUserProfileService {

    private final IParameterFactory parameterFactory;
    private final IUserProfileDao userProfileDao;

    public UserProfileService(IParameterFactory parameterFactory,
                              IUserProfileDao userProfileDao) {
        this.parameterFactory = parameterFactory;
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
        userProfileDao.persistEntity(newUserProfile);
    }

    @Override
    public void changeUserStatusByEmail(String email, boolean isActive) {
        Consumer<Session> consumer = session -> {
            Parameter parameter = parameterFactory.createStringParameter("email", email);
            UserProfile existingUser = userProfileDao.getEntity(parameter);
            existingUser.setActive(isActive);
            session.merge(existingUser);
        };
        userProfileDao.updateEntity(consumer);
    }

    @Override
    public void deleteUserProfileByEmail(String email) {
        Parameter parameter = parameterFactory.createStringParameter("email", email);
        userProfileDao.findEntityAndDelete(parameter);
    }

    @Override
    public UserDetailsDto getUserProfileByUsername(String username) {
        Parameter parameter = new Parameter("username", username);
        UserProfile userProfile =
                (UserProfile) userProfileDao.getOptionalEntity(parameter)
                        .orElseThrow(() -> new RuntimeException("User not found with username: " + username));

        return getResponseUserDetails(userProfile);
    }

    @Override
    public UserDetailsDto getUserByEmail(String email) {
        Parameter parameter = new Parameter("email", email);
        UserProfile userProfile =
                (UserProfile) userProfileDao.getOptionalEntity(parameter)
                        .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        return getResponseUserDetails(userProfile);
    }

    private UserDetailsDto getResponseUserDetails(UserProfile userProfile) {
        ContactInfo contactInfo = userProfile.getContactInfo();
        ContactPhone contactPhone = contactInfo.getContactPhone();
        ContactPhoneDto responseContactPhoneDto = ContactPhoneDto.builder()
                .phoneNumber(contactPhone.getPhoneNumber())
                .countryPhoneCode(contactPhone.getCountryPhoneCode().getCode())
                .build();
        AddressDto addressDto = getResponseAddressDto(userProfile.getAddress());
        return UserDetailsDto.builder()
                .email(userProfile.getEmail())
                .name(contactInfo.getName())
                .secondName(contactInfo.getSecondName())
                .contactPhone(responseContactPhoneDto)
                .creditCardDtoList(List.of())
                .addressDto(addressDto)
                .build();
    }

    private AddressDto getResponseAddressDto(Address address) {
        return AddressDto.builder()
                .country(address.getCountry().getValue())
                .city(address.getCity())
                .street(address.getStreet())
                .street2(address.getStreet2())
                .florNumber(address.getFlorNumber())
                .apartmentNumber(address.getApartmentNumber())
                .zipCode(address.getZipCode())
                .build();
    }




}
