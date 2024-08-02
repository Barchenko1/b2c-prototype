package com.b2c.prototype.service.client.userinfo;

import com.b2c.prototype.modal.client.dto.common.RequestOneFieldEntityDto;
import com.b2c.prototype.modal.client.dto.request.RequestUserInfoDto;
import com.b2c.prototype.modal.client.dto.update.RequestUserInfoDtoUpdate;

public interface IUserInfoService {
    void saveUserInfo(RequestUserInfoDto requestUserInfoDto);
    void saveUserInfoWithResponse(RequestUserInfoDto requestUserInfoDto);
    void updateUserInfo(RequestUserInfoDtoUpdate requestUserInfoDtoUpdate);
    void deleteUserInfo(RequestOneFieldEntityDto requestOneFieldEntityDto);

}
