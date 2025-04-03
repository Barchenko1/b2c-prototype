package com.b2c.prototype.manager.userdetails.basic;

import com.b2c.prototype.modal.dto.payload.user.RegistrationUserDetailsDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsDto;
import com.b2c.prototype.modal.dto.response.ResponseUserDetailsDto;
import com.b2c.prototype.modal.entity.user.UserDetails;
import com.b2c.prototype.dao.user.IUserDetailsDao;

import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.userdetails.IUserDetailsManager;
import com.b2c.prototype.service.query.ISearchService;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.dao.query.IFetchHandler;
import com.tm.core.process.manager.common.EntityOperationManager;
import com.tm.core.process.manager.common.IEntityOperationManager;

import java.util.List;

import static com.b2c.prototype.util.Constant.USER_ID;

public class UserDetailsManager implements IUserDetailsManager {

    private final IEntityOperationManager entityOperationManager;
    private final ITransformationFunctionService transformationFunctionService;
    private final IFetchHandler fetchHandler;
    private final IParameterFactory parameterFactory;

    public UserDetailsManager(IUserDetailsDao userDetailsDao,
                              ITransformationFunctionService transformationFunctionService,
                              IFetchHandler fetchHandler,
                              IParameterFactory parameterFactory) {
        this.entityOperationManager = new EntityOperationManager(userDetailsDao);
        this.transformationFunctionService = transformationFunctionService;
        this.fetchHandler = fetchHandler;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void createNewUser(RegistrationUserDetailsDto registrationUserDetailsDto) {
        entityOperationManager.updateEntity(
                transformationFunctionService.getEntity(UserDetails.class, registrationUserDetailsDto));
    }

    @Override
    public void saveUserDetails(UserDetailsDto userDetailsDto) {
        entityOperationManager.executeConsumer(session -> {
            UserDetails userDetails =
                    transformationFunctionService.getEntity(session, UserDetails.class, userDetailsDto);
            session.merge(userDetails);
        });
    }

    @Override
    public void updateUserDetailsByUserId(String userId, UserDetailsDto userDetailsDto) {
        entityOperationManager.executeConsumer(session -> {
            UserDetails existingUserDetails = entityOperationManager.getNamedQueryEntity(
                    "UserDetails.findByUserId",
                    parameterFactory.createStringParameter(USER_ID, userId));
            UserDetails userDetails =
                    transformationFunctionService.getEntity(session, UserDetails.class, userDetailsDto);
            userDetails.setId(existingUserDetails.getId());
            userDetails.getContactInfo().setId(existingUserDetails.getContactInfo().getId());
            existingUserDetails.getUserAddresses().forEach(session::remove);
            existingUserDetails.getUserCreditCards().forEach(session::remove);
            session.merge(userDetails);
        });
    }

    @Override
    public void updateUserStatusByUserId(String userId, boolean status) {
        entityOperationManager.executeConsumer(session -> {
            UserDetails existingUser = entityOperationManager.getNamedQueryEntity(
                    "UserDetails.findByUserId",
                    parameterFactory.createStringParameter(USER_ID, userId));
            existingUser.setActive(status);
            session.merge(existingUser);
        });
    }

    @Override
    public void updateUserVerifyEmailByUserId(String userId, boolean verifyEmail) {
        entityOperationManager.executeConsumer(session -> {
            UserDetails existingUser = entityOperationManager.getNamedQueryEntity(
                    "UserDetails.findByUserId",
                    parameterFactory.createStringParameter(USER_ID, userId));
            existingUser.setEmailVerified(verifyEmail);
            session.merge(existingUser);
        });
    }

    @Override
    public void updateUserVerifyPhoneByUserId(String userId, boolean verifyPhone) {
        entityOperationManager.executeConsumer(session -> {
            UserDetails existingUser = entityOperationManager.getNamedQueryEntity(
                    "UserDetails.findByUserId",
                    parameterFactory.createStringParameter(USER_ID, userId));
            existingUser.setContactPhoneVerified(verifyPhone);
            session.merge(existingUser);
        });
    }

    @Override
    public void deleteUserDetailsByUserId(String userId) {
        entityOperationManager.executeConsumer(session -> {
            UserDetails existingUserDetails = fetchHandler.getNamedQueryEntity(
                    UserDetails.class,
                    "UserDetails.findByUserId",
                    parameterFactory.createStringParameter(USER_ID, userId));
            session.remove(existingUserDetails);
        });
    }

    @Override
    public ResponseUserDetailsDto getUserDetailsByUserId(String userId) {
        return entityOperationManager.getNamedQueryEntityDto(
                "UserDetails.findFullUserDetailsByUserId",
                parameterFactory.createStringParameter(USER_ID, userId),
                transformationFunctionService.getTransformationFunction(UserDetails.class, ResponseUserDetailsDto.class));
    }

    @Override
    public List<ResponseUserDetailsDto> getResponseUserDetails() {
        return entityOperationManager.getNamedQueryEntityDtoList(
                "UserDetails.findAllFullUserDetailsByUserId",
                transformationFunctionService.getTransformationFunction(UserDetails.class, ResponseUserDetailsDto.class));
    }

}
