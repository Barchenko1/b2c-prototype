package com.b2c.prototype.service.processor.userprofile.basic;

import com.b2c.prototype.modal.dto.payload.RegistrationUserProfileDto;
import com.b2c.prototype.modal.dto.payload.UserProfileDto;
import com.b2c.prototype.modal.entity.user.UserProfile;
import com.b2c.prototype.dao.user.IUserProfileDao;

import com.b2c.prototype.service.common.EntityOperationDao;
import com.b2c.prototype.service.common.IEntityOperationDao;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.userprofile.IUserProfileService;
import com.b2c.prototype.service.supplier.ISupplierService;

import java.util.List;

import static com.b2c.prototype.util.Constant.USER_ID;

public class UserProfileService implements IUserProfileService {

    private final IEntityOperationDao entityOperationDao;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;

    public UserProfileService(IUserProfileDao userProfileDao,
                              ITransformationFunctionService transformationFunctionService,
                              ISupplierService supplierService) {
        this.entityOperationDao = new EntityOperationDao(userProfileDao);
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
    }

    @Override
    public void createNewUser(RegistrationUserProfileDto registrationUserProfileDto) {
        // to do need call to auth service
        entityOperationDao.updateEntity(
                transformationFunctionService.getEntity(UserProfile.class, registrationUserProfileDto));
    }

    @Override
    public void updateUserStatusByUserId(String userId, boolean isActive) {
        entityOperationDao.executeConsumer(session -> {
            UserProfile existingUser = entityOperationDao.getEntity(
                    supplierService.parameterStringSupplier(USER_ID, userId));
            existingUser.setActive(isActive);
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
        return entityOperationDao.getEntityDto("",
                supplierService.parameterStringSupplier(USER_ID, userId),
                transformationFunctionService.getTransformationFunction(UserProfile.class, UserProfileDto.class));
    }

    @Override
    public List<UserProfileDto> getUserProfiles() {
        return entityOperationDao.getEntityDtoList("",
                transformationFunctionService.getTransformationFunction(UserProfile.class, UserProfileDto.class));
    }

}
