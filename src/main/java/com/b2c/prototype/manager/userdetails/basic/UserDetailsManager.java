package com.b2c.prototype.manager.userdetails.basic;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.payload.user.RegistrationUserDetailsDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsAddCollectionDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsContactInfoDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsRemoveCollectionDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsStatusDto;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.address.UserAddress;
import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.b2c.prototype.modal.entity.user.ContactPhone;
import com.b2c.prototype.modal.entity.user.Device;
import com.b2c.prototype.modal.entity.user.UserCreditCard;
import com.b2c.prototype.modal.entity.user.UserDetails;

import com.b2c.prototype.manager.userdetails.IUserDetailsManager;
import com.b2c.prototype.transform.userdetails.IUserDetailsTransformService;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    public void updateUserDetailsByUserId(UserDetailsDto userDetailsDto) {
        UserDetails existingUserDetails = generalEntityDao.findEntity(
                "UserDetails.findByUserId",
                Pair.of(USER_ID, userDetailsDto.getUserId()));
        UserDetails userDetails = userDetailsTransformService.mapUserDetailsDtoToUserDetails(userDetailsDto);

        existingUserDetails.setUsername(userDetails.getUsername());
        existingUserDetails.setActive(userDetails.isActive());

        ContactInfo existingContactInfo = existingUserDetails.getContactInfo();
        ContactInfo contactInfo = userDetails.getContactInfo();
        updateContactInfo(existingContactInfo, contactInfo);

        List<UserAddress> newUserAddressList = new ArrayList<>(userDetails.getUserAddresses());
        updateUserAddresses(existingUserDetails.getUserAddresses(), newUserAddressList);

        List<UserCreditCard> newUserUserCreditCardList = new ArrayList<>(userDetails.getUserCreditCards());
        updateUserCreditCards(existingUserDetails.getUserCreditCards(), newUserUserCreditCardList);

        List<Device> newDeviceList = new ArrayList<>(userDetails.getDevices());
        updateDevices(existingUserDetails.getDevices(), newDeviceList);

        generalEntityDao.mergeEntity(existingUserDetails);
    }

    @Override
    @Transactional
    public void updateUserDetailsContactInfo(UserDetailsContactInfoDto userDetailsContactInfoDto) {
        UserDetails existingUserDetails = generalEntityDao.findEntity(
                "UserDetails.findByUserId",
                Pair.of(USER_ID, userDetailsContactInfoDto.getUserId()));
        UserDetails userDetails = userDetailsTransformService.mapUserDetailsContactInfoDtoToUserDetails(userDetailsContactInfoDto);

        existingUserDetails.setUsername(userDetails.getUsername());
        existingUserDetails.setActive(userDetails.isActive());

        ContactInfo existingContactInfo = existingUserDetails.getContactInfo();
        ContactInfo contactInfo = userDetails.getContactInfo();
        updateContactInfo(existingContactInfo, contactInfo);

        generalEntityDao.mergeEntity(existingUserDetails);
    }

    @Override
    @Transactional
    public void updateUserDetailsStatus(UserDetailsStatusDto userDetailsStatusDto) {
        UserDetails existingUser = generalEntityDao.findEntity("UserDetails.findByUserId",
                Pair.of(USER_ID, userDetailsStatusDto.getUserId()));
        existingUser.setActive(userDetailsStatusDto.isStatus());
        generalEntityDao.mergeEntity(existingUser);
    }

    @Override
    @Transactional
    public void updateUserDetailsVerifyEmail(UserDetailsStatusDto userDetailsStatusDto) {
        UserDetails existingUser = generalEntityDao.findEntity("UserDetails.findByUserId",
                Pair.of(USER_ID, userDetailsStatusDto.getUserId()));
        ContactInfo contactInfo = existingUser.getContactInfo();
        contactInfo.setEmailVerified(userDetailsStatusDto.isStatus());
        generalEntityDao.mergeEntity(existingUser);
    }

    @Override
    @Transactional
    public void updateUserDetailsVerifyPhone(UserDetailsStatusDto userDetailsStatusDto) {
        UserDetails existingUser = generalEntityDao.findEntity("UserDetails.findByUserId",
                Pair.of(USER_ID, userDetailsStatusDto.getUserId()));
        ContactInfo contactInfo = existingUser.getContactInfo();
        contactInfo.setContactPhoneVerified(userDetailsStatusDto.isStatus());
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
    @Transactional
    public void addUserDetailsAddress(UserDetailsAddCollectionDto userDetailsAddCollectionDto) {
        UserDetails userDetail = generalEntityDao.findEntity(
                "UserDetails.findUserAddressesByUserId", Pair.of(USER_ID, userDetailsAddCollectionDto.getUserId()));
        UserAddress userAddress =
                userDetailsTransformService.mapUserAddressDtoToUserAddress(userDetailsAddCollectionDto.getAddress());

        Optional<UserAddress> userAddressOptional = userDetail.getUserAddresses().stream()
                .filter(existingUserAddress ->
                        isAddressExist(existingUserAddress.getAddress(), userAddress.getAddress()))
                .findFirst();
        if (userAddressOptional.isPresent()) {
            userAddressOptional.get().setDefault(true);
        } else {
            userDetail.getUserAddresses().forEach(a -> a.setDefault(false));
            userAddress.setDefault(true);
            userDetail.getUserAddresses().add(userAddress);
        }
        generalEntityDao.mergeEntity(userDetail);
    }

    @Override
    @Transactional
    public void addUserDetailsCreditCard(UserDetailsAddCollectionDto userDetailsAddCollectionDto) {
        UserDetails userDetail = generalEntityDao.findEntity(
                "UserDetails.findByUserId", Pair.of(USER_ID, userDetailsAddCollectionDto.getUserId()));
        UserCreditCard userCreditCard =
                userDetailsTransformService.mapUserCreditCardDtoToUserCreditCard(userDetailsAddCollectionDto.getCreditCard());

        Optional<UserCreditCard> userCreditCardOptional = userDetail.getUserCreditCards().stream()
                .filter(existingUserCreditCard ->
                        isCreditCardExist(existingUserCreditCard.getCreditCard(), userCreditCard.getCreditCard()))
                .findFirst();
        if (userCreditCardOptional.isPresent()) {
            userCreditCardOptional.get().setDefault(true);
        } else {
            userDetail.getUserCreditCards().forEach(c -> c.setDefault(false));
            userCreditCard.setDefault(true);
            userDetail.getUserCreditCards().add(userCreditCard);
        }
        generalEntityDao.mergeEntity(userDetail);
    }

    @Override
    @Transactional
    public void addUserDetailsDevice(UserDetailsAddCollectionDto userDetailsAddCollectionDto) {
        UserDetails userDetail = generalEntityDao.findEntity(
                "UserDetails.findByUserId", Pair.of(USER_ID, userDetailsAddCollectionDto.getUserId()));
        Device newDevice = userDetailsTransformService.mapDeviceDtoToDevice(userDetailsAddCollectionDto.getDevice());

        Optional<Device> deviceOptional = userDetail.getDevices().stream()
                .filter(existingDevice -> isDeviceExist(existingDevice, newDevice))
                .findFirst();
        if (deviceOptional.isPresent()) {
            deviceOptional.get().setThisDevice(true);
            deviceOptional.get().setLoginTime(LocalDateTime.now());
        } else {
            userDetail.getDevices().forEach(d -> d.setThisDevice(false));
            newDevice.setThisDevice(true);
            newDevice.setLoginTime(LocalDateTime.now());
            userDetail.getDevices().add(newDevice);
        }

        generalEntityDao.mergeEntity(userDetail);
    }

    @Override
    @Transactional
    public void deleteUserDetailsAddress(UserDetailsRemoveCollectionDto userDetailsRemoveCollectionDto) {
        UserDetails userDetail = generalEntityDao.findEntity(
                "UserDetails.findByUserId", Pair.of(USER_ID, userDetailsRemoveCollectionDto.getUserId()));
        Address address = userDetailsTransformService.mapAddressDtoToAddress(userDetailsRemoveCollectionDto.getAddress());

        Optional<UserAddress> userAddressOptional = userDetail.getUserAddresses().stream()
                .filter(existingUserCreditCard -> isAddressExist(existingUserCreditCard.getAddress(), address))
                .findFirst();
        if (userAddressOptional.isPresent()) {
            userDetail.getUserAddresses().remove(userAddressOptional.get());
            generalEntityDao.mergeEntity(userDetail);
        } else {
            throw new RuntimeException("");
        }
    }

    @Override
    @Transactional
    public void deleteUserDetailsCreditCard(UserDetailsRemoveCollectionDto userDetailsRemoveCollectionDto) {
        UserDetails userDetail = generalEntityDao.findEntity(
                "UserDetails.findByUserId", Pair.of(USER_ID, userDetailsRemoveCollectionDto.getUserId()));
        CreditCard creditCard =
                userDetailsTransformService.mapCreditCardDtoToCreditCard(userDetailsRemoveCollectionDto.getCreditCard());

        Optional<UserCreditCard> userCreditCardOptional = userDetail.getUserCreditCards().stream()
                .filter(existingUserCreditCard -> isCreditCardExist(existingUserCreditCard.getCreditCard(), creditCard))
                .findFirst();
        if (userCreditCardOptional.isPresent()) {
            userDetail.getUserCreditCards().remove(userCreditCardOptional.get());
            generalEntityDao.mergeEntity(userDetail);
        } else {
            throw new RuntimeException("");
        }
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
                generalEntityDao.findEntityList("UserDetails.findAllUserDetails", (Pair<String, ?>) null);

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

    private void updateUserAddresses(Set<UserAddress> existingUserAddressSet, List<UserAddress> newUserAddressList) {
        int minSize = Math.min(existingUserAddressSet.size(), newUserAddressList.size());
        int maxSize = Math.max(existingUserAddressSet.size(), newUserAddressList.size());
        for (int i = 0; i < minSize; i++) {
            UserAddress existingUserAddress = getByIndexStream(existingUserAddressSet, i);
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
        updateArray(minSize, maxSize, existingUserAddressSet, newUserAddressList);
    }

    private void updateUserCreditCards(Set<UserCreditCard> existingUserCreditCardSet, List<UserCreditCard> newUserCreditCardList) {
        int minSize = Math.min(existingUserCreditCardSet.size(), newUserCreditCardList.size());
        int maxSize = Math.max(existingUserCreditCardSet.size(), newUserCreditCardList.size());
        for (int i = 0; i < minSize; i++) {
            UserCreditCard existingUserCreditCard = getByIndexStream(existingUserCreditCardSet, i);
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
        updateArray(minSize, maxSize, existingUserCreditCardSet, newUserCreditCardList);
    }

    private void updateDevices(Set<Device> existingDeviceSet, List<Device> newDeviceList) {
        int minSize = Math.min(existingDeviceSet.size(), newDeviceList.size());
        int maxSize = Math.max(existingDeviceSet.size(), newDeviceList.size());
        for (int i = 0; i < minSize; i++) {
            Device existingDevice = getByIndexStream(existingDeviceSet, i);
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
        updateArray(minSize, maxSize, existingDeviceSet, newDeviceList);
    }

    private <T> void updateArray(int minSize,
                                 int maxSize,
                                 Set<T> existingList,
                                 List<T> newList) {
        if (existingList.size() > newList.size()) {
            for (int i = minSize; i < maxSize; i++) {
                existingList.remove(getByIndexStream(existingList, i));
            }
        } else if (existingList.size() < newList.size()) {
            for (int i = minSize; i < maxSize; i++) {
                existingList.add(newList.get(i));
            }
        }
    }

    private <T> T getByIndexStream(Set<T> set, int index) {
        return set.stream()
                .skip(index)
                .findFirst()
                .orElseThrow(IndexOutOfBoundsException::new);
    }

    private boolean isAddressExist(Address existingAddress, Address newAddress) {
        return existingAddress.getCountry().getValue().equals(newAddress.getCountry().getValue())
                && existingAddress.getCity().equals(newAddress.getCity())
                && existingAddress.getStreet().equals(newAddress.getStreet())
                && existingAddress.getBuildingNumber().equals(newAddress.getBuildingNumber())
                && existingAddress.getFlorNumber() == newAddress.getFlorNumber()
                && existingAddress.getApartmentNumber() == newAddress.getApartmentNumber()
                && existingAddress.getZipCode().equals(newAddress.getZipCode());
    }

    private boolean isCreditCardExist(CreditCard existingCreditCard, CreditCard newCreditCard) {
        return existingCreditCard.getCardNumber().equals(newCreditCard.getCardNumber())
                && existingCreditCard.getMonthOfExpire() == newCreditCard.getMonthOfExpire()
                && existingCreditCard.getYearOfExpire() == newCreditCard.getYearOfExpire()
                && existingCreditCard.getOwnerName().equals(newCreditCard.getOwnerName())
                && existingCreditCard.getOwnerSecondName().equals(newCreditCard.getOwnerSecondName());
    }

    private boolean isDeviceExist(Device existingDevice, Device newDevice) {
        return existingDevice.getIpAddress().equals(newDevice.getIpAddress())
                && existingDevice.getUserAgent().equals(newDevice.getUserAgent())
                && existingDevice.getScreenWidth() == newDevice.getScreenWidth()
                && existingDevice.getScreenHeight() == newDevice.getScreenHeight()
                && existingDevice.getTimezone().equals(newDevice.getTimezone())
                && existingDevice.getLanguage().equals(newDevice.getLanguage())
                && existingDevice.getPlatform().equals(newDevice.getPlatform());
    }

}
