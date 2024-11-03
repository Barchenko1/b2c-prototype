package com.b2c.prototype.service.base.contactinfo;

import com.b2c.prototype.dao.user.IContactInfoDao;
import com.b2c.prototype.modal.dto.common.RequestOneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.RequestContactInfoDto;
import com.b2c.prototype.modal.dto.update.RequestContactInfoDtoUpdate;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.b2c.prototype.service.general.AbstractGeneralEntityService;
import com.b2c.prototype.service.single.AbstractSingleEntityService;
import com.tm.core.dao.general.IGeneralEntityDao;
import com.tm.core.dao.single.ISingleEntityDao;
import com.tm.core.processor.finder.parameter.Parameter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContactInfoService extends AbstractGeneralEntityService implements IContactInfoService {

    private final IContactInfoDao contactInfoDao;

    public ContactInfoService(IContactInfoDao contactInfoDao) {
        this.contactInfoDao = contactInfoDao;
    }

    @Override
    protected IGeneralEntityDao getEntityDao() {
        return this.contactInfoDao;
    }

    @Override
    public void saveContactInfo(RequestContactInfoDto requestContactInfoDto) {
        ContactInfo contactInfo = ContactInfo.builder()
                .name(requestContactInfoDto.getName())
                .secondName(requestContactInfoDto.getSecondName())
//                .contactPhone(requestContactInfoDto.getPhoneNumber())
                .build();

//        super.saveEntity(contactInfo);
    }

    @Override
    public void saveContactInfoWithResponse(RequestContactInfoDto requestContactInfoDto) {
        ContactInfo contactInfo = ContactInfo.builder()
                .name(requestContactInfoDto.getName())
                .secondName(requestContactInfoDto.getSecondName())
//                .phoneNumber(requestContactInfoDto.getPhoneNumber())
                .build();

//        super.saveEntity(contactInfo);
    }

    @Override
    public void updateContactInfoByOrderId(RequestContactInfoDtoUpdate requestContactInfoDtoUpdate) {
        RequestContactInfoDto requestContactInfoDto = requestContactInfoDtoUpdate.getNewEntityDto();
        ContactInfo contactInfo = ContactInfo.builder()
                .name(requestContactInfoDto.getName())
                .secondName(requestContactInfoDto.getSecondName())
//                .phoneNumber(requestContactInfoDto.getPhoneNumber())
                .build();

        Parameter parameter =
                parameterFactory.createStringParameter("orderId", requestContactInfoDtoUpdate.getSearchField());
//        super.updateEntity(contactInfo, parameter);
    }

    @Override
    public void updateContactInfoByUsername(RequestContactInfoDtoUpdate requestContactInfoDtoUpdate) {
        RequestContactInfoDto requestContactInfoDto = requestContactInfoDtoUpdate.getNewEntityDto();
        ContactInfo contactInfo = ContactInfo.builder()
                .name(requestContactInfoDto.getName())
                .secondName(requestContactInfoDto.getSecondName())
//                .phoneNumber(requestContactInfoDto.getPhoneNumber())
                .build();

        Parameter parameter =
                parameterFactory.createStringParameter("username", requestContactInfoDtoUpdate.getSearchField());
//        super.updateEntity(contactInfo, parameter);
    }

    @Override
    public void deleteContactInfoByOrderId(RequestOneFieldEntityDto requestOneFieldEntityDto) {
        Parameter parameter =
                parameterFactory.createStringParameter("orderId", requestOneFieldEntityDto.getRequestValue());
        super.deleteEntity(parameter);
    }

    @Override
    public void deleteContactInfoByUsername(RequestOneFieldEntityDto requestOneFieldEntityDto) {
        Parameter parameter =
                parameterFactory.createStringParameter("username", requestOneFieldEntityDto.getRequestValue());
        super.deleteEntity(parameter);
    }

}
