package com.b2c.prototype.processor.user;

import com.b2c.prototype.modal.dto.payload.ContactInfoDto;

import java.util.Map;


public interface IContactInfoProcess {
    void saveUpdateContactInfo(Map<String, String> requestParams, ContactInfoDto contactInfoDto);
    void deleteContactInfo(Map<String, String> requestParams);

    ContactInfoDto getResponseContactInfoByUserId(Map<String, String> requestParams);
}
