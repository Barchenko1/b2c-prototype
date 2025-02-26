package com.b2c.prototype.manager.userprofile.basic;

import com.b2c.prototype.modal.dto.payload.RegistrationUserProfileDto;
import com.b2c.prototype.modal.dto.payload.UserProfileDto;
import com.b2c.prototype.modal.entity.user.UserDetails;
import com.b2c.prototype.dao.user.IUserProfileDao;

import com.b2c.prototype.service.common.EntityOperationManager;
import com.b2c.prototype.service.common.IEntityOperationManager;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.userprofile.IUserDetailsManager;
import com.b2c.prototype.service.supplier.ISupplierService;
import com.tm.core.finder.factory.IParameterFactory;

import java.util.List;

import static com.b2c.prototype.util.Constant.USER_ID;

public class UserDetailsManager implements IUserDetailsManager {

    private final IEntityOperationManager entityOperationDao;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;
    private final IParameterFactory parameterFactory;

    public UserDetailsManager(IUserProfileDao userProfileDao,
                              ITransformationFunctionService transformationFunctionService,
                              ISupplierService supplierService,
                              IParameterFactory parameterFactory) {
        this.entityOperationDao = new EntityOperationManager(userProfileDao);
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void createNewUser(RegistrationUserProfileDto registrationUserProfileDto) {
        // to do need call to auth service
        entityOperationDao.updateEntity(
                transformationFunctionService.getEntity(UserDetails.class, registrationUserProfileDto));
    }

    @Override
    public void updateUserDetailsByUserId(String userId, UserProfileDto userProfileDto) {

    }

    @Override
    public void updateUserStatusByUserId(String userId, boolean status) {
        entityOperationDao.executeConsumer(session -> {
            UserDetails existingUser = entityOperationDao.getEntity(
                    parameterFactory.createStringParameter(USER_ID, userId));
            existingUser.setActive(status);
            session.merge(existingUser);
        });
    }

    @Override
    public void deleteUserProfileByUserId(String userId) {
        entityOperationDao.deleteEntityByParameter(
                supplierService.parameterStringSupplier(USER_ID, userId));
    }

    @Override
    public UserProfileDto getUserProfileByUserId(String userId) {
        return entityOperationDao.getEntityGraphDto(
                "",
                parameterFactory.createStringParameter(USER_ID, userId),
                transformationFunctionService.getTransformationFunction(UserDetails.class, UserProfileDto.class));
    }

    @Override
    public List<UserProfileDto> getUserProfiles() {
        return entityOperationDao.getEntityGraphDtoList("",
                transformationFunctionService.getTransformationFunction(UserDetails.class, UserProfileDto.class));
    }

}
