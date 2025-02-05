package com.b2c.prototype.manager.userprofile;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.searchfield.BeneficiarySearchFieldOrderNumberDto;
import com.b2c.prototype.modal.dto.payload.ContactPhoneDto;
import com.b2c.prototype.modal.dto.searchfield.ContactPhoneSearchFieldEntityDto;

import java.util.List;

public interface IContactPhoneManager {
    void saveUpdateContactPhoneByUserId(ContactPhoneSearchFieldEntityDto contactPhoneSearchFieldEntityDto);
    void saveUpdateContactPhoneByOrderId(ContactPhoneSearchFieldEntityDto contactPhoneSearchFieldEntityDto);
    void deleteContactPhoneByUserId(OneFieldEntityDto oneFieldEntityDto);
    void deleteContactPhoneByOrderId(BeneficiarySearchFieldOrderNumberDto beneficiarySearchFieldOrderNumberDto);
    ContactPhoneDto getContactPhoneByUserId(OneFieldEntityDto oneFieldEntityDto);
    List<ContactPhoneDto> getContactPhoneByOrderId(OneFieldEntityDto oneFieldEntityDto);
    List<ContactPhoneDto> getContactPhoneList();
}
