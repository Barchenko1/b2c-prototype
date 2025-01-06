package com.b2c.prototype.service.processor.userprofile;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.ContactInfoDto;
import com.b2c.prototype.modal.dto.searchfield.ContactInfoSearchFieldEntityDto;

public interface IContactInfoService {
    void saveUpdateContactInfoByUserId(ContactInfoSearchFieldEntityDto contactInfoSearchFieldEntityDto);
    void deleteContactInfoByUserId(OneFieldEntityDto oneFieldEntityDto);

    ContactInfoDto getContactInfoByUserId(OneFieldEntityDto oneFieldEntityDto);
}
