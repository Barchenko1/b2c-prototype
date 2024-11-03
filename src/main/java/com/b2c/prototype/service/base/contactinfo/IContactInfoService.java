package com.b2c.prototype.service.base.contactinfo;

import com.b2c.prototype.modal.dto.common.RequestOneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.RequestContactInfoDto;
import com.b2c.prototype.modal.dto.update.RequestContactInfoDtoUpdate;

public interface IContactInfoService {
    void saveContactInfo(RequestContactInfoDto requestContactInfoDto);
    void saveContactInfoWithResponse(RequestContactInfoDto requestContactInfoDto);
    void updateContactInfoByOrderId(RequestContactInfoDtoUpdate requestContactInfoDtoUpdate);
    void deleteContactInfoByOrderId(RequestOneFieldEntityDto requestOneFieldEntityDto);
    void updateContactInfoByUsername(RequestContactInfoDtoUpdate requestContactInfoDtoUpdate);
    void deleteContactInfoByUsername(RequestOneFieldEntityDto requestOneFieldEntityDto);
}
