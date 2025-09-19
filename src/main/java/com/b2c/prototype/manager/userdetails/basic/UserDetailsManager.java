package com.b2c.prototype.manager.userdetails.basic;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.payload.user.RegistrationUserDetailsDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsDto;
import com.b2c.prototype.modal.dto.payload.user.ResponseUserDetailsDto;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.b2c.prototype.modal.entity.user.UserDetails;

import com.b2c.prototype.manager.userdetails.IUserDetailsManager;
import com.b2c.prototype.transform.userdetails.IUserDetailsTransformService;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    public void saveUserDetails(UserDetailsDto userDetailsDto) {
        UserDetails userDetails =
                userDetailsTransformService.mapUserDetailsDtoToUserDetails(userDetailsDto);
        generalEntityDao.persistEntity(userDetails);
    }

    @Override
    @Transactional
    public void updateUserDetailsByUserId(String userId, UserDetailsDto userDetailsDto) {
        UserDetails existingUserDetails = generalEntityDao.findEntity(
                "UserDetails.findByUserId",
                Pair.of(USER_ID, userId));
        UserDetails userDetails = userDetailsTransformService.mapUserDetailsDtoToUserDetails(userDetailsDto);
        userDetails.setId(existingUserDetails.getId());
        userDetails.getContactInfo().setId(existingUserDetails.getContactInfo().getId());
        generalEntityDao.mergeEntity(userDetails);
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
    public ResponseUserDetailsDto getUserDetailsByUserId(String userId) {
        UserDetails userDetail = generalEntityDao.findEntity(
                "UserDetails.findFullUserDetailsByUserId", Pair.of(USER_ID, userId));

        return Optional.of(userDetail)
                .map(userDetailsTransformService::mapUserDetailsToResponseUserDetailsDto)
                .orElseThrow(() -> new RuntimeException(""));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponseUserDetailsDto> getResponseUserDetails() {
        List<UserDetails> userDetailsList =
                generalEntityDao.findEntityList("UserDetails.findAllFullUserDetailsByUserId", (Pair<String, ?>) null);

        return userDetailsList.stream()
                .map(userDetailsTransformService::mapUserDetailsToResponseUserDetailsDto)
                .toList();
    }

}
