package com.b2c.prototype.service.processor.userprofile;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.ContactInfoDto;
import com.b2c.prototype.modal.dto.update.ContactInfoDtoUpdate;

public interface IContactInfoService {
    void saveContactInfo(ContactInfoDto contactInfoDto);
    void saveContactInfoWithResponse(ContactInfoDto contactInfoDto);
    void updateContactInfoByOrderId(ContactInfoDtoUpdate requestContactInfoDtoUpdate);
    void deleteContactInfoByOrderId(OneFieldEntityDto oneFieldEntityDto);
    void updateContactInfoByUsername(ContactInfoDtoUpdate requestContactInfoDtoUpdate);
    void deleteContactInfoByUsername(OneFieldEntityDto oneFieldEntityDto);
}