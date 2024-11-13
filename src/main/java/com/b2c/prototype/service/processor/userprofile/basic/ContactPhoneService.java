package com.b2c.prototype.service.processor.userprofile.basic;

import com.b2c.prototype.dao.cashed.IEntityCachedMap;
import com.b2c.prototype.dao.user.IContactPhoneDao;
import com.b2c.prototype.modal.dto.request.ContactPhoneDto;
import com.b2c.prototype.modal.dto.update.ContactPhoneDtoUpdate;
import com.b2c.prototype.modal.entity.user.ContactPhone;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.b2c.prototype.service.processor.userprofile.IContactPhoneService;
import com.b2c.prototype.service.single.CommonSingleEntityService;
import com.b2c.prototype.service.single.ICommonSingleEntityService;
import com.tm.core.processor.finder.factory.IParameterFactory;
import com.tm.core.processor.finder.parameter.Parameter;

import java.util.List;
import java.util.Optional;

public class ContactPhoneService implements IContactPhoneService {

    private final IParameterFactory parameterFactory;
    private final ICommonSingleEntityService commonEntityService;
    private final IEntityCachedMap entityCachedMap;

    public ContactPhoneService(IParameterFactory parameterFactory,
                               IContactPhoneDao contactPhoneDao,
                               IEntityCachedMap entityCachedMap) {
        this.parameterFactory = parameterFactory;
        this.commonEntityService = new CommonSingleEntityService(contactPhoneDao);
        this.entityCachedMap = entityCachedMap;
    }

    @Override
    public void saveContactPhone(ContactPhoneDto contactPhoneDto) {
        ContactPhone contactPhone = buildCountryPhone(contactPhoneDto);
        commonEntityService.saveEntity(contactPhone);
    }

    @Override
    public void updateContactPhone(ContactPhoneDtoUpdate contactPhoneDtoUpdate) {
        ContactPhoneDto newCountryPhoneCode = contactPhoneDtoUpdate.getNewEntityDto();
        ContactPhone contactPhone = buildCountryPhone(newCountryPhoneCode);
        String searchParameter = contactPhoneDtoUpdate.getSearchField();
        Parameter parameter = parameterFactory.createStringParameter("code",
                searchParameter);
        commonEntityService.updateEntity(contactPhone, parameter);
    }

    @Override
    public void deleteContactPhone(ContactPhoneDto contactPhoneDto) {
        Parameter parameter = parameterFactory.createStringParameter("phoneNumber",
                contactPhoneDto.getPhoneNumber());
        commonEntityService.deleteEntityByParameter(parameter);
    }

    @Override
    public ContactPhone getContactPhone(ContactPhoneDto contactPhoneDto) {
        Parameter parameter = parameterFactory
                .createStringParameter("phoneNumber", contactPhoneDto.getPhoneNumber());
        return commonEntityService.getEntity(parameter);
    }

    @Override
    public Optional<ContactPhone> getOptionalContactPhone(ContactPhoneDto contactPhoneDto) {
        Parameter parameter = parameterFactory
                .createStringParameter("phoneNumber", contactPhoneDto.getPhoneNumber());
        return commonEntityService.getOptionalEntity(parameter);
    }

    @Override
    public List<ContactPhone> getContactPhoneList() {
        return commonEntityService.getEntityList();
    }

    private ContactPhone buildCountryPhone(ContactPhoneDto contactPhoneDto) {
        CountryPhoneCode countryPhoneCode = entityCachedMap
                .getEntity(CountryPhoneCode.class, "code", contactPhoneDto.getCountryCode());
        return ContactPhone.builder()
                .phoneNumber(contactPhoneDto.getPhoneNumber())
                .countryPhoneCode(countryPhoneCode)
                .build();
    }
}
