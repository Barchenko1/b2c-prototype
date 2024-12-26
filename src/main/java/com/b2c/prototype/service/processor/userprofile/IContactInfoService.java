package com.b2c.prototype.service.processor.userprofile;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.ContactInfoArrayDtoSearchField;
import com.b2c.prototype.modal.dto.request.ContactInfoDto;
import com.b2c.prototype.modal.dto.request.ContactInfoDtoSearchField;
import com.b2c.prototype.modal.dto.request.ContactInfoSearchFieldOrderNumberDto;

import java.util.List;

public interface IContactInfoService {
    void saveUpdateContactInfoByUserId(ContactInfoDtoSearchField contactInfoDtoSearchField);
    void saveUpdateContactInfoByOrderId(ContactInfoArrayDtoSearchField contactInfoArrayDtoSearchField);
    void deleteContactInfoByUserId(OneFieldEntityDto oneFieldEntityDto);
    void deleteContactInfoByOrderId(ContactInfoSearchFieldOrderNumberDto contactInfoSearchFieldOrderNumberDto);

    ContactInfoDto getContactInfoByUserId(OneFieldEntityDto oneFieldEntityDto);
    List<ContactInfoDto> getContactInfoListByOrderId(OneFieldEntityDto oneFieldEntityDto);

}
