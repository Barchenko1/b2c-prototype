package com.b2c.prototype.manager.userdetails;

import com.b2c.prototype.modal.dto.payload.order.ContactInfoDto;

public interface IContactInfoManager {
    void saveUpdateContactInfoByUserId(String userId, ContactInfoDto contactInfoDto);
    void deleteContactInfoByUserId(String userId);

    ContactInfoDto getContactInfoByUserId(String userId);

    void saveUpdateContactInfoByOrderId(String orderId, ContactInfoDto contactInfoDto);
    void deleteContactInfoByOrderId(String orderId);

    ContactInfoDto getContactInfoByOrderId(String orderId);
}
