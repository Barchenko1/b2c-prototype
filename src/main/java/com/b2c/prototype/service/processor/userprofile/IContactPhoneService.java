package com.b2c.prototype.service.processor.userprofile;

import com.b2c.prototype.modal.dto.request.ContactPhoneDto;
import com.b2c.prototype.modal.dto.update.ContactPhoneDtoUpdate;
import com.b2c.prototype.modal.entity.user.ContactPhone;

import java.util.List;
import java.util.Optional;

public interface IContactPhoneService {
    void saveContactPhone(ContactPhoneDto contactPhoneDto);
    void updateContactPhone(ContactPhoneDtoUpdate contactPhoneDtoUpdate);
    void deleteContactPhone(ContactPhoneDto contactPhoneDto);

    ContactPhone getContactPhone(ContactPhoneDto contactPhoneDto);
    List<ContactPhone> getContactPhoneList();
    Optional<ContactPhone> getOptionalContactPhone(ContactPhoneDto contactPhoneDto);
}
