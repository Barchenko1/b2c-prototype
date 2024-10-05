package com.b2c.prototype.service.base.appuser;

import com.b2c.prototype.modal.dto.response.ResponseUserDataDto;
import com.b2c.prototype.modal.constant.RoleEnum;
import com.b2c.prototype.modal.dto.request.RegistrationAppUserDto;
import com.b2c.prototype.modal.entity.user.AppUser;
import com.b2c.prototype.dao.user.IAppUserDao;
import com.b2c.prototype.gateway.IRestClient;
import com.b2c.prototype.service.general.AbstractGeneralEntityService;
import com.tm.core.dao.general.IGeneralEntityDao;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import com.tm.core.modal.GeneralEntity;
import com.tm.core.processor.finder.parameter.Parameter;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

@Slf4j
public class AppUserService extends AbstractGeneralEntityService implements IAppUserService {

    private final IRestClient restClient;
    private final IAppUserDao appUserDao;
    private final IEntityIdentifierDao entityIdentifierDao;

    public AppUserService(IRestClient restClient,
                          IAppUserDao appUserDao,
                          IEntityIdentifierDao entityIdentifierDao) {
        this.restClient = restClient;
        this.appUserDao = appUserDao;
        this.entityIdentifierDao = entityIdentifierDao;
    }

    @Override
    protected IGeneralEntityDao getEntityDao() {
        return this.appUserDao;
    }

    @Override
    public void createNewUser(RegistrationAppUserDto registrationAppUserDto, RoleEnum roleEnum) {
        AppUser newUser = AppUser.builder()
                .dateOfCreate(System.currentTimeMillis())
                .build();

        // to do need call to auth service
        GeneralEntity generalEntity = new GeneralEntity();
        generalEntity.addEntityPriority(1, newUser);
        super.saveEntity(generalEntity);
    }

    @Override
    public void changeUserStatusByLogin(String username, boolean isActive) {
        Supplier<AppUser> appUserSupplier = () -> {
            Parameter parameter = parameterFactory.createStringParameter("username", username);
            AppUser existingUser = entityIdentifierDao.getEntity(AppUser.class, parameter);
            existingUser.setActive(isActive);
            return existingUser;
        };
        super.updateEntity(appUserSupplier);
    }

    @Override
    public void changeUserStatusByEmail(String email, boolean isActive) {
        Supplier<AppUser> appUserSupplier = () -> {
            Parameter parameter = parameterFactory.createStringParameter("email", email);
            AppUser existingUser = entityIdentifierDao.getEntity(AppUser.class, parameter);
            existingUser.setActive(isActive);
            return existingUser;
        };
        super.updateEntity(appUserSupplier);
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
        AppUser appUser =
                (AppUser) appUserDao.getOptionalGeneralEntity(parameter)
                        .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        ResponseUserDataDto responseUserDataDto = ResponseUserDataDto.builder()
                .username(appUser.getUsername())
                .build();
        return responseUserDataDto;
    }


}
