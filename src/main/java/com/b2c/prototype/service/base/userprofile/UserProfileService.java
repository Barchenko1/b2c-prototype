package com.b2c.prototype.service.base.userprofile;

import com.b2c.prototype.modal.dto.request.RegistrationUserProfileDto;
import com.b2c.prototype.modal.dto.response.ResponseUserDataDto;
import com.b2c.prototype.modal.constant.RoleEnum;
import com.b2c.prototype.modal.entity.user.UserProfile;
import com.b2c.prototype.dao.user.IUserProfileDao;
import com.b2c.prototype.gateway.IRestClient;
import com.b2c.prototype.service.general.AbstractGeneralEntityService;
import com.tm.core.dao.general.IGeneralEntityDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import com.tm.core.modal.GeneralEntity;
import com.tm.core.processor.finder.parameter.Parameter;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

@Slf4j
public class UserProfileService extends AbstractGeneralEntityService implements IUserProfileService {

    private final IRestClient restClient;
    private final IUserProfileDao UserProfileDao;
    private final IEntityIdentifierDao entityIdentifierDao;

    public UserProfileService(IRestClient restClient,
                          IUserProfileDao UserProfileDao,
                          IEntityIdentifierDao entityIdentifierDao) {
        this.restClient = restClient;
        this.UserProfileDao = UserProfileDao;
        this.entityIdentifierDao = entityIdentifierDao;
    }

    @Override
    protected IGeneralEntityDao getEntityDao() {
        return this.UserProfileDao;
    }

    @Override
    public void createNewUser(RegistrationUserProfileDto registrationUserProfileDto, RoleEnum roleEnum) {
        UserProfile newUser = UserProfile.builder()
                .dateOfCreate(System.currentTimeMillis())
                .build();

        // to do need call to auth service
        GeneralEntity generalEntity = new GeneralEntity();
        generalEntity.addEntityPriority(1, newUser);
        super.saveEntity(generalEntity);
    }

    @Override
    public void changeUserStatusByLogin(String username, boolean isActive) {
        Supplier<UserProfile> UserProfileSupplier = () -> {
            Parameter parameter = parameterFactory.createStringParameter("username", username);
            UserProfile existingUser = entityIdentifierDao.getEntity(UserProfile.class, parameter);
            existingUser.setActive(isActive);
            return existingUser;
        };
        super.updateEntity(UserProfileSupplier);
    }

    @Override
    public void changeUserStatusByEmail(String email, boolean isActive) {
        Supplier<UserProfile> UserProfileSupplier = () -> {
            Parameter parameter = parameterFactory.createStringParameter("email", email);
            UserProfile existingUser = entityIdentifierDao.getEntity(UserProfile.class, parameter);
            existingUser.setActive(isActive);
            return existingUser;
        };
        super.updateEntity(UserProfileSupplier);
    }

    @Override
    public void deleteUserByLogin(String username) {
        Parameter parameter = parameterFactory.createStringParameter("username", username);
        super.deleteEntity(parameter);
    }

    @Override
    public void deleteUserByEmail(String email) {
        Parameter parameter = parameterFactory.createStringParameter("email", email);
        super.deleteEntity(parameter);
    }

    @Override
    public ResponseUserDataDto getUserByEmail(String email) {
        Parameter parameter = new Parameter("email", email);
        UserProfile UserProfile =
                (UserProfile) UserProfileDao.getOptionalGeneralEntity(parameter)
                        .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        ResponseUserDataDto responseUserDataDto = ResponseUserDataDto.builder()
                .username(UserProfile.getUsername())
                .build();
        return responseUserDataDto;
    }


}
