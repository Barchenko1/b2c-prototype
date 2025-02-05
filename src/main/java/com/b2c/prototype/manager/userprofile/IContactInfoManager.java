package com.b2c.prototype.manager.userprofile;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.payload.ContactInfoDto;
import com.b2c.prototype.modal.dto.searchfield.ContactInfoSearchFieldEntityDto;

public interface IContactInfoManager {
    void saveUpdateContactInfoByUserId(ContactInfoSearchFieldEntityDto contactInfoSearchFieldEntityDto);
    void deleteContactInfoByUserId(OneFieldEntityDto oneFieldEntityDto);

    ContactInfoDto getContactInfoByUserId(OneFieldEntityDto oneFieldEntityDto);
}
