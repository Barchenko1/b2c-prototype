package com.b2c.prototype.processor.user;

import com.b2c.prototype.manager.userdetails.IContactInfoManager;
import com.b2c.prototype.modal.dto.payload.order.ContactInfoDto;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ContactInfoProcess implements IContactInfoProcess {

    private final IContactInfoManager contactInfoManager;

    public ContactInfoProcess(IContactInfoManager contactInfoManager) {
        this.contactInfoManager = contactInfoManager;
    }

    @Override
    public void saveUpdateContactInfo(Map<String, String> requestParams, ContactInfoDto contactInfoDto) {
        String userId = requestParams.get("userId");
        contactInfoManager.saveUpdateContactInfoByUserId(userId, contactInfoDto);
    }

    @Override
    public void deleteContactInfo(Map<String, String> requestParams) {
        String userId = requestParams.get("userId");
        contactInfoManager.deleteContactInfoByUserId(userId);
    }

    @Override
    public ContactInfoDto getResponseContactInfoByUserId(Map<String, String> requestParams) {
        String userId = requestParams.get("userId");
        return contactInfoManager.getContactInfoByUserId(userId);
    }
}
