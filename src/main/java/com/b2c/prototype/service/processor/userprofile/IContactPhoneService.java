package com.b2c.prototype.service.processor.userprofile;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.ContactPhoneDto;
import com.b2c.prototype.modal.dto.update.ContactPhoneDtoUpdate;

import java.util.List;
import java.util.Optional;

public interface IContactPhoneService {
    void saveContactPhone(ContactPhoneDto contactPhoneDto);
    void updateContactPhoneByEmail(ContactPhoneDtoUpdate contactPhoneDtoUpdate);
    void updateContactPhoneByOrderId(ContactPhoneDtoUpdate contactPhoneDtoUpdate);
    void deleteContactPhoneByEmail(OneFieldEntityDto oneFieldEntityDto);
    void deleteContactPhoneByOrderId(OneFieldEntityDto oneFieldEntityDto);
    ContactPhoneDto getContactPhoneByEmail(OneFieldEntityDto oneFieldEntityDto);
    ContactPhoneDto getContactPhoneByOrderId(OneFieldEntityDto oneFieldEntityDto);
    List<ContactPhoneDto> getContactPhoneList();
}
