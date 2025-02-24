package com.b2c.prototype.manager.userprofile;

import com.b2c.prototype.modal.dto.payload.BeneficiaryDto;
import com.b2c.prototype.modal.dto.payload.ContactPhoneDto;

import java.util.List;

public interface IContactPhoneManager {
    void saveUpdateContactPhoneByUserId(String userId, ContactPhoneDto contactPhoneDto);
    void saveUpdateContactPhoneByOrderId(String orderId, BeneficiaryDto beneficiaryDto);
    void deleteContactPhoneByUserId(String userId);
    void deleteContactPhoneByOrderId(String orderId, int beneficiaryNumber);
    ContactPhoneDto getContactPhoneByUserId(String userId);
    List<ContactPhoneDto> getContactPhoneByOrderId(String orderId);
    List<ContactPhoneDto> getContactPhoneList();
}
