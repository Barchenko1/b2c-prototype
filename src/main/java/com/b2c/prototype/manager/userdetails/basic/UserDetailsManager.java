package com.b2c.prototype.manager.userdetails.basic;

import com.b2c.prototype.modal.dto.payload.RegistrationUserDetailsDto;
import com.b2c.prototype.modal.dto.payload.UserDetailsDto;
import com.b2c.prototype.modal.dto.response.ResponseUserDetailsDto;
import com.b2c.prototype.modal.entity.user.UserDetails;
import com.b2c.prototype.dao.user.IUserDetailsDao;

import com.b2c.prototype.service.common.EntityOperationManager;
import com.b2c.prototype.service.common.IEntityOperationManager;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.userdetails.IUserDetailsManager;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.dao.identifier.IQueryService;

import java.util.List;

import static com.b2c.prototype.util.Constant.USER_ID;

public class UserDetailsManager implements IUserDetailsManager {

    private final IEntityOperationManager entityOperationDao;
    private final ITransformationFunctionService transformationFunctionService;
    private final IQueryService queryService;
    private final IParameterFactory parameterFactory;

    public UserDetailsManager(IUserDetailsDao userDetailsDao,
                              ITransformationFunctionService transformationFunctionService,
                              IQueryService queryService,
                              IParameterFactory parameterFactory) {
        this.entityOperationDao = new EntityOperationManager(userDetailsDao);
        this.transformationFunctionService = transformationFunctionService;
        this.queryService = queryService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void createNewUser(RegistrationUserDetailsDto registrationUserDetailsDto) {
        entityOperationDao.updateEntity(
                transformationFunctionService.getEntity(UserDetails.class, registrationUserDetailsDto));
    }

    @Override
    public void saveUserDetails(UserDetailsDto userDetailsDto) {
        entityOperationDao.executeConsumer(session -> {
            UserDetails userDetails =
                    transformationFunctionService.getEntity(session, UserDetails.class, userDetailsDto);
            session.merge(userDetails);
        });
    }

    @Override
    public void updateUserDetailsByUserId(String userId, UserDetailsDto userDetailsDto) {
        entityOperationDao.executeConsumer(session -> {
            UserDetails existingUserDetails = entityOperationDao.getNamedQueryEntity(
                    "UserDetails.findByUserId",
                    parameterFactory.createStringParameter(USER_ID, userId));
            UserDetails userDetails =
                    transformationFunctionService.getEntity(session, UserDetails.class, userDetailsDto);
            userDetails.setId(existingUserDetails.getId());
            userDetails.getContactInfo().setId(existingUserDetails.getContactInfo().getId());
            existingUserDetails.getAddresses().forEach(session::remove);
            existingUserDetails.getUserCreditCardList().forEach(session::remove);
            session.merge(userDetails);
        });
    }

    @Override
    public void updateUserStatusByUserId(String userId, boolean status) {
        entityOperationDao.executeConsumer(session -> {
            UserDetails existingUser = entityOperationDao.getNamedQueryEntity(
                    "UserDetails.findByUserId",
                    parameterFactory.createStringParameter(USER_ID, userId));
            existingUser.setActive(status);
            session.merge(existingUser);
        });
    }

    @Override
    public void deleteUserDetailsByUserId(String userId) {
        entityOperationDao.executeConsumer(session -> {
            UserDetails existingUserDetails = queryService.getNamedQueryEntity(
                    session,
                    UserDetails.class,
                    "UserDetails.findByUserId",
                    parameterFactory.createStringParameter(USER_ID, userId));
            session.remove(existingUserDetails);
        });
    }

    @Override
    public ResponseUserDetailsDto getUserDetailsByUserId(String userId) {
        return entityOperationDao.getNamedQueryEntityDto(
                "UserDetails.findFullUserDetailsByUserId",
                parameterFactory.createStringParameter(USER_ID, userId),
                transformationFunctionService.getTransformationFunction(UserDetails.class, ResponseUserDetailsDto.class));
    }

    @Override
    public List<ResponseUserDetailsDto> getResponseUserDetails() {
        return entityOperationDao.getNamedQueryEntityDtoList(
                "UserDetails.findAllFullUserDetailsByUserId",
                transformationFunctionService.getTransformationFunction(UserDetails.class, ResponseUserDetailsDto.class));
    }

}
