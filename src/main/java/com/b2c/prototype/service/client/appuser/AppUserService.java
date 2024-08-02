package com.b2c.prototype.service.client.appuser;

import com.b2c.prototype.modal.client.dto.response.ResponseUserDataDto;
import com.b2c.prototype.modal.constant.RoleEnum;
import com.b2c.prototype.modal.client.dto.request.RegistrationAppUserDto;
import com.b2c.prototype.modal.client.entity.user.AppUser;
import com.b2c.prototype.dao.user.IAppUserDao;
import com.b2c.prototype.service.gateway.IRestClient;
import lombok.extern.slf4j.Slf4j;

import static com.b2c.prototype.util.Query.DELETE_APP_USER_BY_EMAIL;
import static com.b2c.prototype.util.Query.DELETE_APP_USER_BY_USERNAME;
import static com.b2c.prototype.util.Query.SELECT_APP_USER_BY_EMAIL;
import static com.b2c.prototype.util.Query.SELECT_APP_USER_BY_USERNAME;

@Slf4j
public class AppUserService implements IAppUserService {

    private final IRestClient restClient;
    private final IAppUserDao appUserDao;

    public AppUserService(IRestClient restClient,
                          IAppUserDao appUserDao) {
        this.restClient = restClient;
        this.appUserDao = appUserDao;
    }

    @Override
    public void createNewUser(RegistrationAppUserDto registrationAppUserDto, RoleEnum roleEnum) {
        AppUser createNewUser = AppUser.builder()
                .dateOfCreate(System.currentTimeMillis())
                .build();

        // to do need call to auth service
        appUserDao.saveEntity(createNewUser);
    }

    @Override
    public void changeUserStatusByLogin(String login, boolean isActive) {
//        Optional<AppUser> optionalAppUser = appUserDao.getOptionalEntityBySQLQueryWithParams(
//                SELECT_APP_USER_BY_USERNAME, login);
//        optionalAppUser.ifPresent(appUser -> {
//            appUser.setActive(isActive);
//            appUserDao.updateEntity(appUser);
//        });

        appUserDao.mutateEntityBySQLQueryWithParams(SELECT_APP_USER_BY_USERNAME, login, isActive);
    }

    @Override
    public void changeUserStatusByEmail(String email, boolean isActive) {
//        Optional<AppUser> optionalAppUser = appUserDao.getOptionalEntityBySQLQueryWithParams(
//                SELECT_APP_USER_BY_EMAIL, email);
//        optionalAppUser.ifPresent(appUser -> {
//            appUser.setActive(isActive);
//            appUserDao.updateEntity(appUser);
//        });
        appUserDao.mutateEntityBySQLQueryWithParams(SELECT_APP_USER_BY_EMAIL, email, isActive);

    }

    @Override
    public void deleteUserByLogin(String username) {
        appUserDao.mutateEntityBySQLQueryWithParams(DELETE_APP_USER_BY_USERNAME, username);
    }

    @Override
    public void deleteUserByEmail(String email) {
        appUserDao.mutateEntityBySQLQueryWithParams(DELETE_APP_USER_BY_EMAIL, email);
    }

    @Override
    public ResponseUserDataDto getUserByEmail(String email) {
        AppUser appUser =
                (AppUser) appUserDao.getOptionalEntityBySQLQueryWithParams(SELECT_APP_USER_BY_EMAIL, email)
                        .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        ResponseUserDataDto responseUserDataDto = ResponseUserDataDto.builder()
                .username(appUser.getUsername())
                .build();
        return responseUserDataDto;
    }


}
