package com.b2c.prototype.manager.userdetails.basic;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.b2c.prototype.manager.userdetails.ICountryPhoneCodeManager;
import com.b2c.prototype.transform.userdetails.IUserDetailsTransformService;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.VALUE;

@Service
public class CountryPhoneCodeManager implements ICountryPhoneCodeManager {
    
    private final IGeneralEntityDao generalEntityDao;
    private final IUserDetailsTransformService userDetailsTransformService;

    public CountryPhoneCodeManager(IGeneralEntityDao generalEntityDao, IUserDetailsTransformService userDetailsTransformService) {
        this.generalEntityDao = generalEntityDao;
        this.userDetailsTransformService = userDetailsTransformService;
    }

    public void saveEntity(ConstantPayloadDto payload) {
        CountryPhoneCode entity = userDetailsTransformService.mapConstantPayloadDtoToCountryPhoneCode(payload);
        generalEntityDao.persistEntity(entity);
    }

    public void updateEntity(String searchValue, ConstantPayloadDto payload) {
        CountryPhoneCode fetchedEntity =
                generalEntityDao.findEntity("CountryPhoneCode.findByValue", Pair.of(VALUE, searchValue));
        fetchedEntity.setValue(payload.getValue());
        fetchedEntity.setLabel(payload.getLabel());
        generalEntityDao.mergeEntity(fetchedEntity);
    }

    public void deleteEntity(String value) {
        CountryPhoneCode fetchedEntity = generalEntityDao.findEntity("CountryPhoneCode.findByValue", Pair.of(VALUE, value));
        generalEntityDao.removeEntity(fetchedEntity);
    }

    public ConstantPayloadDto getEntity(String value) {
        CountryPhoneCode entity = generalEntityDao.findEntity("CountryPhoneCode.findByValue", Pair.of(VALUE, value));
        return userDetailsTransformService.mapCountryPhoneCodeDtoToConstantPayloadDto(entity);
    }

    public Optional<ConstantPayloadDto> getEntityOptional(String value) {
        CountryPhoneCode entity = generalEntityDao.findEntity("CountryPhoneCode.findByValue", Pair.of(VALUE, value));
        return Optional.of(userDetailsTransformService.mapCountryPhoneCodeDtoToConstantPayloadDto(entity));
    }


    public List<ConstantPayloadDto> getEntities() {
        return generalEntityDao.findEntityList("CountryPhoneCode.all", (Pair<String, ?>) null).stream()
                .map(e -> userDetailsTransformService.mapCountryPhoneCodeDtoToConstantPayloadDto((CountryPhoneCode) e))
                .toList();
    }
}
