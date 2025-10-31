package com.b2c.prototype.manager.userdetails.basic;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.payload.user.RegistrationUserDetailsDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsDto;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.modal.entity.address.UserAddress;
import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.b2c.prototype.modal.entity.user.ContactPhone;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.b2c.prototype.modal.entity.user.Device;
import com.b2c.prototype.modal.entity.user.UserCreditCard;
import com.b2c.prototype.modal.entity.user.UserDetails;

import com.b2c.prototype.manager.userdetails.IUserDetailsManager;
import com.b2c.prototype.transform.userdetails.IUserDetailsTransformService;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.b2c.prototype.util.Constant.USER_ID;

@Service
public class UserDetailsManager implements IUserDetailsManager {

    private final IGeneralEntityDao generalEntityDao;
    private final IUserDetailsTransformService userDetailsTransformService;

    public UserDetailsManager(IGeneralEntityDao generalEntityDao,
                              IUserDetailsTransformService userDetailsTransformService) {
        this.generalEntityDao = generalEntityDao;
        this.userDetailsTransformService = userDetailsTransformService;
    }

    @Override
    @Transactional
    public void createNewUser(RegistrationUserDetailsDto registrationUserDetailsDto) {
        UserDetails userDetails =
                userDetailsTransformService.mapRegistrationUserDetailsDtoToUserDetails(registrationUserDetailsDto);
        generalEntityDao.persistEntity(userDetails);
    }

    @Override
    @Transactional
    public void updateUserDetailsByUserId(String userId, UserDetailsDto userDetailsDto) {
        UserDetails existingUserDetails = generalEntityDao.findEntity(
                "UserDetails.findByUserId",
                Pair.of(USER_ID, userId));
        UserDetails userDetails = userDetailsTransformService.mapUserDetailsDtoToUserDetails(userDetailsDto);

        existingUserDetails.setUserId(userDetails.getUserId());
        existingUserDetails.setUsername(userDetails.getUsername());
        existingUserDetails.setActive(userDetails.isActive());
        existingUserDetails.setDateOfCreate(userDetails.getDateOfCreate());

        updateContactInfo(existingUserDetails.getContactInfo(), userDetails.getContactInfo());

        List<UserAddress> existingUserAddressList = new ArrayList<>(existingUserDetails.getUserAddresses());
        List<UserAddress> newUserAddressList = new ArrayList<>(userDetails.getUserAddresses());
        updateUserAddresses(existingUserAddressList, newUserAddressList);
        existingUserDetails.setUserAddresses(new HashSet<>(existingUserAddressList));

        List<UserCreditCard> existingUserCreditCardList = new ArrayList<>(existingUserDetails.getUserCreditCards());
        List<UserCreditCard> newUserUserCreditCardList = new ArrayList<>(userDetails.getUserCreditCards());
        updateUserCreditCards(existingUserCreditCardList, newUserUserCreditCardList);
        existingUserDetails.setUserCreditCards(new HashSet<>(existingUserCreditCardList));

        List<Device> existingDeviceList = new ArrayList<>(existingUserDetails.getDevices());
        List<Device> newDeviceList = new ArrayList<>(userDetails.getDevices());
        updateDevices(existingDeviceList, newDeviceList);
        existingUserDetails.setDevices(new HashSet<>(existingDeviceList));

        generalEntityDao.mergeEntity(existingUserDetails);
    }

    @Override
    @Transactional
    public void updateUserStatusByUserId(String userId, boolean status) {
        UserDetails existingUser = generalEntityDao.findEntity("UserDetails.findByUserId", Pair.of(USER_ID, userId));
        existingUser.setActive(status);
        generalEntityDao.mergeEntity(existingUser);
    }

    @Override
    @Transactional
    public void updateUserVerifyEmailByUserId(String userId, boolean verifyEmail) {
        UserDetails existingUser = generalEntityDao.findEntity("UserDetails.findByUserId", Pair.of(USER_ID, userId));
        ContactInfo contactInfo = existingUser.getContactInfo();
        contactInfo.setEmailVerified(verifyEmail);
        generalEntityDao.mergeEntity(existingUser);
    }

    @Override
    @Transactional
    public void updateUserVerifyPhoneByUserId(String userId, boolean verifyPhone) {
        UserDetails existingUser = generalEntityDao.findEntity("UserDetails.findByUserId", Pair.of(USER_ID, userId));
        ContactInfo contactInfo = existingUser.getContactInfo();
        contactInfo.setContactPhoneVerified(verifyPhone);
        generalEntityDao.mergeEntity(existingUser);
    }

    @Override
    @Transactional
    public void deleteUserDetailsByUserId(String userId) {
        UserDetails existingUserDetails = generalEntityDao.findEntity(
                "UserDetails.findByUserId",
                Pair.of(USER_ID, userId));
        generalEntityDao.removeEntity(existingUserDetails);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetailsDto getUserDetailsByUserId(String userId) {
        UserDetails userDetail = generalEntityDao.findEntity(
                "UserDetails.findByUserId", Pair.of(USER_ID, userId));

        return Optional.of(userDetail)
                .map(userDetailsTransformService::mapUserDetailsToResponseUserDetailsDto)
                .orElseThrow(() -> new RuntimeException(""));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDetailsDto> getUserDetails() {
        List<UserDetails> userDetailsList =
                generalEntityDao.findEntityList("UserDetails.findAllFullUserDetailsByUserId", (Pair<String, ?>) null);

        return userDetailsList.stream()
                .map(userDetailsTransformService::mapUserDetailsToResponseUserDetailsDto)
                .toList();
    }

    private void updateContactInfo(ContactInfo existingContactInfo, ContactInfo contactInfo) {
        existingContactInfo.setFirstName(contactInfo.getFirstName());
        existingContactInfo.setLastName(contactInfo.getLastName());
        existingContactInfo.setEmail(contactInfo.getEmail());
        existingContactInfo.setBirthdayDate(contactInfo.getBirthdayDate());
        existingContactInfo.setContactPhoneVerified(contactInfo.isContactPhoneVerified());
        existingContactInfo.setEmailVerified(contactInfo.isEmailVerified());

        ContactPhone existingContactPhone = existingContactInfo.getContactPhone();
        ContactPhone contactPhone = contactInfo.getContactPhone();

        existingContactPhone.setPhoneNumber(contactPhone.getPhoneNumber());
        existingContactPhone.setCountryPhoneCode(contactPhone.getCountryPhoneCode());
    }

    private void updateUserAddresses(List<UserAddress> existingUserAddressList, List<UserAddress> newUserAddressList) {
        int minSize = Math.min(existingUserAddressList.size(), newUserAddressList.size());
        int maxSize = Math.max(existingUserAddressList.size(), newUserAddressList.size());
        for (int i = 0; i < minSize; i++) {
            UserAddress existingUserAddress = existingUserAddressList.get(i);
            UserAddress newUserAddress = newUserAddressList.get(i);

            existingUserAddress.setDefault(newUserAddress.isDefault());

            Address existingAddress = existingUserAddress.getAddress();
            Address newAddress = newUserAddress.getAddress();

            existingAddress.setCountry(newAddress.getCountry());
            existingAddress.setCity(newAddress.getCity());
            existingAddress.setStreet(newAddress.getStreet());
            existingAddress.setBuildingNumber(newAddress.getBuildingNumber());
            existingAddress.setApartmentNumber(newAddress.getApartmentNumber());
            existingAddress.setFlorNumber(newAddress.getFlorNumber());
            existingAddress.setZipCode(newAddress.getZipCode());
        }
        updateArray(minSize, maxSize, existingUserAddressList, newUserAddressList);
    }

    private void updateUserCreditCards(List<UserCreditCard> existingUserCreditCardList, List<UserCreditCard> newUserCreditCardList) {
        int minSize = Math.min(existingUserCreditCardList.size(), newUserCreditCardList.size());
        int maxSize = Math.max(existingUserCreditCardList.size(), newUserCreditCardList.size());
        for (int i = 0; i < minSize; i++) {
            UserCreditCard existingUserCreditCard = existingUserCreditCardList.get(i);
            UserCreditCard newUserCreditCard = newUserCreditCardList.get(i);

            existingUserCreditCard.setDefault(newUserCreditCard.isDefault());

            CreditCard existingCreditCard = existingUserCreditCard.getCreditCard();
            CreditCard newCreditCard = newUserCreditCard.getCreditCard();

            existingCreditCard.setCardNumber(newCreditCard.getCardNumber());
            existingCreditCard.setCvv(newCreditCard.getCvv());
            existingCreditCard.setOwnerName(newCreditCard.getOwnerName());
            existingCreditCard.setOwnerSecondName(newCreditCard.getOwnerSecondName());
            existingCreditCard.setMonthOfExpire(newCreditCard.getMonthOfExpire());
            existingCreditCard.setYearOfExpire(newCreditCard.getYearOfExpire());
            existingCreditCard.setActive(newCreditCard.isActive());
        }
        updateArray(minSize, maxSize, existingUserCreditCardList, newUserCreditCardList);
    }

    private void updateDevices(List<Device> existingDeviceList, List<Device> newDeviceList) {
        int minSize = Math.min(existingDeviceList.size(), newDeviceList.size());
        int maxSize = Math.max(existingDeviceList.size(), newDeviceList.size());
        for (int i = 0; i < minSize; i++) {
            Device existingDevice = existingDeviceList.get(i);
            Device newDevice = newDeviceList.get(i);

            existingDevice.setIpAddress(newDevice.getIpAddress());
            existingDevice.setLoginTime(newDevice.getLoginTime());
            existingDevice.setUserAgent(newDevice.getUserAgent());
            existingDevice.setScreenWidth(newDevice.getScreenWidth());
            existingDevice.setScreenHeight(newDevice.getScreenHeight());
            existingDevice.setTimezone(newDevice.getTimezone());
            existingDevice.setLanguage(newDevice.getLanguage());
            existingDevice.setPlatform(newDevice.getPlatform());
            existingDevice.setThisDevice(newDevice.isThisDevice());
        }
        updateArray(minSize, maxSize, existingDeviceList, newDeviceList);
    }

    private <T> void updateArray(int minSize,
                             int maxSize,
                             List<T> existingList,
                             List<T> newList) {
        if (existingList.size() > newList.size()) {
            for (int i = minSize; i < maxSize; i++) {
                existingList.remove(existingList.get(i));
            }
        } else if (existingList.size() < newList.size()) {
            for (int i = minSize; i < maxSize; i++) {
                existingList.add(newList.get(i));
            }
        }
    }

}
