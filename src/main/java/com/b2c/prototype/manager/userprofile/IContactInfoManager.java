package com.b2c.prototype.manager.userprofile;

import com.b2c.prototype.modal.dto.payload.ContactInfoDto;

public interface IContactInfoManager {
    void saveUpdateContactInfoByUserId(String userId, ContactInfoDto contactInfoDto);
    void deleteContactInfoByUserId(String userId);

    ContactInfoDto getContactInfoByUserId(String userId);
}
