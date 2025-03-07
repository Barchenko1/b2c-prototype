package com.b2c.prototype.manager.userdetails;

import com.b2c.prototype.modal.dto.payload.ContactInfoDto;
import com.b2c.prototype.modal.dto.payload.ContactPhoneDto;

import java.util.List;

public interface IContactPhoneManager {
    void saveUpdateContactPhoneByUserId(String userId, ContactPhoneDto contactPhoneDto);
    void saveUpdateContactPhoneByOrderId(String orderId, ContactInfoDto contactInfoDto);
    void deleteContactPhoneByUserId(String userId);
    void deleteContactPhoneByOrderId(String orderId);
    ContactPhoneDto getContactPhoneByUserId(String userId);
    List<ContactPhoneDto> getContactPhoneByOrderId(String orderId);
    List<ContactPhoneDto> getContactPhoneList();
}
