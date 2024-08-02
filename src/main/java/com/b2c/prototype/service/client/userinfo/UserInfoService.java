package com.b2c.prototype.service.client.userinfo;

import com.b2c.prototype.dao.userinfo.IUserInfoDao;
import com.b2c.prototype.modal.client.dto.common.RequestOneFieldEntityDto;
import com.b2c.prototype.modal.client.dto.request.RequestUserInfoDto;
import com.b2c.prototype.modal.client.dto.update.RequestUserInfoDtoUpdate;
import com.b2c.prototype.modal.client.entity.user.UserInfo;
import com.b2c.prototype.service.client.AbstractService;
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

//        RequestEntityWrapper<UserInfo> requestEntityWrapper = new RequestEntityWrapper<>(
//                userInfoDao,
//                requestUserInfoDto,
//                userInfo
//        );
//
//        super.saveEntity(requestEntityWrapper);
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
    public void updateUserInfo(RequestUserInfoDtoUpdate requestUserInfoDtoUpdate) {
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
    public void deleteUserInfo(RequestOneFieldEntityDto requestOneFieldEntityDto) {
        userInfoDao.mutateEntityBySQLQueryWithParams(DELETE_USER_INFO_BY_USERNAME,
                requestOneFieldEntityDto.getRequestValue());
    }
}
