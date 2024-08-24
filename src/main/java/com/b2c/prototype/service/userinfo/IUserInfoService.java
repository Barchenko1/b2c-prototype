package com.b2c.prototype.service.userinfo;

import com.b2c.prototype.modal.dto.common.RequestOneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.RequestUserInfoDto;
import com.b2c.prototype.modal.dto.update.RequestUserInfoDtoUpdate;

public interface IUserInfoService {
    void saveUserInfo(RequestUserInfoDto requestUserInfoDto);
    void saveUserInfoWithResponse(RequestUserInfoDto requestUserInfoDto);
    void updateUserInfoByOrderId(RequestUserInfoDtoUpdate requestUserInfoDtoUpdate);
    void deleteUserInfoByOrderId(RequestOneFieldEntityDto requestOneFieldEntityDto);
    void updateUserInfoByUsername(RequestUserInfoDtoUpdate requestUserInfoDtoUpdate);
    void deleteUserInfoByUsername(RequestOneFieldEntityDto requestOneFieldEntityDto);
}
