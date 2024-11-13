package com.b2c.prototype.service.processor.userprofile.basic;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDtoUpdate;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.b2c.prototype.service.processor.AbstractOneFieldEntityServiceTest;
import com.tm.core.processor.finder.parameter.Parameter;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CountryPhoneCodeServiceTest extends AbstractOneFieldEntityServiceTest<CountryPhoneCode> {

     @InjectMocks
     private CountryPhoneCodeService countryPhoneCodeService;

    @Override
    protected String getFieldName() {
        return "code";
    }

    @Test
    public void testSaveEntity() {
        OneFieldEntityDto dto = new OneFieldEntityDto("testValue");
        CountryPhoneCode testValue = createTestValue();

        countryPhoneCodeService.saveEntity(dto);

        verifySaveEntity(testValue);
    }

    @Test
    public void testUpdateEntity() {
        OneFieldEntityDto oldDto = new OneFieldEntityDto("oldValue");
        OneFieldEntityDto newDto = new OneFieldEntityDto("newValue");
        OneFieldEntityDtoUpdate dtoUpdate = new OneFieldEntityDtoUpdate();
        dtoUpdate.setOldEntityDto(oldDto);
        dtoUpdate.setNewEntityDto(newDto);

        CountryPhoneCode value = CountryPhoneCode.builder()
                .code("newValue")
                .build();

        countryPhoneCodeService.updateEntity(dtoUpdate);

        verifyUpdateEntity(value, dtoUpdate);
    }

    @Test
    public void testDeleteEntity() {
        OneFieldEntityDto dto = new OneFieldEntityDto("testValue");
        CountryPhoneCode testValue = createTestValue();

        countryPhoneCodeService.deleteEntity(dto);

        verifyDeleteEntity(testValue, dto);
    }

    @Test
    public void testGetEntity() {
        OneFieldEntityDto dto = new OneFieldEntityDto("testValue");
        Parameter parameter = parameterFactory.createStringParameter(getFieldName(), dto.getValue());
        CountryPhoneCode testValue = createTestValue();

        when(parameterFactory.createStringParameter(getFieldName(), dto.getValue())).thenReturn(parameter);
        when(dao.getEntity(parameter)).thenReturn(testValue);

        CountryPhoneCode result = countryPhoneCodeService.getEntity(dto);

        assertEquals(testValue, result);
    }

    @Test
    public void testGetEntityOptional() {
        OneFieldEntityDto dto = new OneFieldEntityDto("testValue");
        Parameter parameter = parameterFactory.createStringParameter(getFieldName(), dto.getValue());
        CountryPhoneCode testValue = createTestValue();

        when(parameterFactory.createStringParameter(getFieldName(), dto.getValue())).thenReturn(parameter);
        when(dao.getOptionalEntity(parameter)).thenReturn(Optional.of(testValue));

        Optional<CountryPhoneCode> result = countryPhoneCodeService.getEntityOptional(dto);

        assertEquals(Optional.of(testValue), result);
    }

    private CountryPhoneCode createTestValue() {
        return CountryPhoneCode.builder()
                .code("testValue")
                .build();
    }
}