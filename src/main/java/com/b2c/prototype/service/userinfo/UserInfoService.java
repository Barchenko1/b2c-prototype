package com.b2c.prototype.service.userinfo;

import com.b2c.prototype.dao.user.IUserInfoDao;
import com.b2c.prototype.modal.dto.common.RequestOneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.RequestUserInfoDto;
import com.b2c.prototype.modal.dto.update.RequestUserInfoDtoUpdate;
import com.b2c.prototype.modal.entity.user.UserInfo;
import com.b2c.prototype.service.AbstractService;
import lombok.extern.slf4j.Slf4j;

import static com.b2c.prototype.util.Query.DELETE_USER_INFO_BY_USERNAME;
import static com.b2c.prototype.util.Query.UPDATE_USER_INFO_BY_USERNAME;

@Slf4j
public class UserInfoService extends AbstractService implements IUserInfoService {

    private final IUserInfoDao userInfoDao;

    public UserInfoService(IUserInfoDao userInfoDao) {
        this.userInfoDao = userInfoDao;
    }

    @Override
    public void saveUserInfo(RequestUserInfoDto requestUserInfoDto) {
        UserInfo userInfo = UserInfo.builder()
                .name(requestUserInfoDto.getName())
                .secondName(requestUserInfoDto.getSecondName())
                .phoneNumber(requestUserInfoDto.getPhoneNumber())
                .build();

        userInfoDao.saveEntity(userInfo);
    }

    @Override
    public void saveUserInfoWithResponse(RequestUserInfoDto requestUserInfoDto) {
        UserInfo userInfo = UserInfo.builder()
                .name(requestUserInfoDto.getName())
                .secondName(requestUserInfoDto.getSecondName())
                .phoneNumber(requestUserInfoDto.getPhoneNumber())
                .build();

        userInfoDao.saveEntity(userInfo);
    }

    @Override
    public void updateUserInfoByOrderId(RequestUserInfoDtoUpdate requestUserInfoDtoUpdate) {
        RequestUserInfoDto requestUserInfoDto = requestUserInfoDtoUpdate.getNewEntityDto();
        UserInfo userInfo = UserInfo.builder()
                .name(requestUserInfoDto.getName())
                .secondName(requestUserInfoDto.getSecondName())
                .phoneNumber(requestUserInfoDto.getPhoneNumber())
                .build();

        userInfoDao.mutateEntityBySQLQueryWithParams("",
                userInfo.getName(),
                userInfo.getSecondName(),
                userInfo.getPhoneNumber(),
                requestUserInfoDtoUpdate.getSearchField());
    }

    @Override
    public void deleteUserInfoByOrderId(RequestOneFieldEntityDto requestOneFieldEntityDto) {
        userInfoDao.mutateEntityBySQLQueryWithParams(DELETE_USER_INFO_BY_USERNAME,
                requestOneFieldEntityDto.getRequestValue());
    }

    @Override
    public void updateUserInfoByUsername(RequestUserInfoDtoUpdate requestUserInfoDtoUpdate) {
        RequestUserInfoDto requestUserInfoDto = requestUserInfoDtoUpdate.getNewEntityDto();
        UserInfo userInfo = UserInfo.builder()
                .name(requestUserInfoDto.getName())
                .secondName(requestUserInfoDto.getSecondName())
                .phoneNumber(requestUserInfoDto.getPhoneNumber())
                .build();

        userInfoDao.mutateEntityBySQLQueryWithParams(UPDATE_USER_INFO_BY_USERNAME,
                userInfo.getName(),
                userInfo.getSecondName(),
                userInfo.getPhoneNumber(),
                requestUserInfoDtoUpdate.getSearchField());
    }

    @Override
    public void deleteUserInfoByUsername(RequestOneFieldEntityDto requestOneFieldEntityDto) {
        userInfoDao.mutateEntityBySQLQueryWithParams(DELETE_USER_INFO_BY_USERNAME,
                requestOneFieldEntityDto.getRequestValue());
    }
}
