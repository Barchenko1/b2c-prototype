package com.b2c.prototype.service.base.userinfo;

import com.b2c.prototype.dao.user.IUserInfoDao;
import com.b2c.prototype.modal.dto.common.RequestOneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.RequestUserInfoDto;
import com.b2c.prototype.modal.dto.update.RequestUserInfoDtoUpdate;
import com.b2c.prototype.modal.entity.user.UserInfo;
import com.b2c.prototype.service.single.AbstractSingleEntityService;
import com.tm.core.dao.single.ISingleEntityDao;
import com.tm.core.processor.finder.parameter.Parameter;
import com.tm.core.processor.finder.factory.IParameterFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserInfoService extends AbstractSingleEntityService implements IUserInfoService {

    private final IUserInfoDao userInfoDao;

    public UserInfoService(IUserInfoDao userInfoDao) {
        this.userInfoDao = userInfoDao;
    }

    @Override
    protected ISingleEntityDao getEntityDao() {
        return this.userInfoDao;
    }

    @Override
    public void saveUserInfo(RequestUserInfoDto requestUserInfoDto) {
        UserInfo userInfo = UserInfo.builder()
                .name(requestUserInfoDto.getName())
                .secondName(requestUserInfoDto.getSecondName())
                .phoneNumber(requestUserInfoDto.getPhoneNumber())
                .build();

        super.saveEntity(userInfo);
    }

    @Override
    public void saveUserInfoWithResponse(RequestUserInfoDto requestUserInfoDto) {
        UserInfo userInfo = UserInfo.builder()
                .name(requestUserInfoDto.getName())
                .secondName(requestUserInfoDto.getSecondName())
                .phoneNumber(requestUserInfoDto.getPhoneNumber())
                .build();

        super.saveEntity(userInfo);
    }

    @Override
    public void updateUserInfoByOrderId(RequestUserInfoDtoUpdate requestUserInfoDtoUpdate) {
        RequestUserInfoDto requestUserInfoDto = requestUserInfoDtoUpdate.getNewEntityDto();
        UserInfo userInfo = UserInfo.builder()
                .name(requestUserInfoDto.getName())
                .secondName(requestUserInfoDto.getSecondName())
                .phoneNumber(requestUserInfoDto.getPhoneNumber())
                .build();

        Parameter parameter =
                parameterFactory.createStringParameter("orderId", requestUserInfoDtoUpdate.getSearchField());
        super.updateEntity(userInfo, parameter);
    }

    @Override
    public void updateUserInfoByUsername(RequestUserInfoDtoUpdate requestUserInfoDtoUpdate) {
        RequestUserInfoDto requestUserInfoDto = requestUserInfoDtoUpdate.getNewEntityDto();
        UserInfo userInfo = UserInfo.builder()
                .name(requestUserInfoDto.getName())
                .secondName(requestUserInfoDto.getSecondName())
                .phoneNumber(requestUserInfoDto.getPhoneNumber())
                .build();

        Parameter parameter =
                parameterFactory.createStringParameter("username", requestUserInfoDtoUpdate.getSearchField());
        super.updateEntity(userInfo, parameter);
    }

    @Override
    public void deleteUserInfoByOrderId(RequestOneFieldEntityDto requestOneFieldEntityDto) {
        Parameter parameter =
                parameterFactory.createStringParameter("orderId", requestOneFieldEntityDto.getRequestValue());
        super.deleteEntity(parameter);
    }

    @Override
    public void deleteUserInfoByUsername(RequestOneFieldEntityDto requestOneFieldEntityDto) {
        Parameter parameter =
                parameterFactory.createStringParameter("username", requestOneFieldEntityDto.getRequestValue());
        super.deleteEntity(parameter);
    }

}
