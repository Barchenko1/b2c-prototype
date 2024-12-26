package com.b2c.prototype.service.processor.userprofile;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.ContactInfoSearchFieldOrderNumberDto;
import com.b2c.prototype.modal.dto.request.ContactPhoneDto;
import com.b2c.prototype.modal.dto.update.ContactPhoneDtoUpdate;

import java.util.List;

public interface IContactPhoneService {
    void saveUpdateContactPhoneByUserId(ContactPhoneDtoUpdate contactPhoneDtoUpdate);
    void saveUpdateContactPhoneByOrderId(ContactPhoneDtoUpdate contactPhoneDtoUpdate);
    void deleteContactPhoneByUserId(OneFieldEntityDto oneFieldEntityDto);
    void deleteContactPhoneByOrderId(ContactInfoSearchFieldOrderNumberDto contactInfoSearchFieldOrderNumberDto);
    ContactPhoneDto getContactPhoneByUserId(OneFieldEntityDto oneFieldEntityDto);
    List<ContactPhoneDto> getContactPhoneByOrderId(OneFieldEntityDto oneFieldEntityDto);
    List<ContactPhoneDto> getContactPhoneList();
}
