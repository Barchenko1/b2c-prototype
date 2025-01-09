package com.b2c.prototype.service.processor.userprofile.basic;

import com.b2c.prototype.modal.base.AbstractConstantEntity;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.common.ConstantEntityPayloadSearchFieldDto;
import com.b2c.prototype.modal.dto.payload.ConstantEntityPayloadDto;
import com.b2c.prototype.modal.entity.store.CountType;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.AbstractConstantEntityServiceTest;
import com.tm.core.processor.finder.parameter.Parameter;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.VALUE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class CountryPhoneCodeServiceTest extends AbstractConstantEntityServiceTest<CountryPhoneCode> {
    @Mock
    private ITransformationFunctionService transformationFunctionService;
    @InjectMocks
    private CountryPhoneCodeService countryPhoneCodeService;

    @Test
    public void testSaveEntity() {
        ConstantEntityPayloadDto dto = ConstantEntityPayloadDto.builder()
                .label("testLabel")
                .value("testValue")
                .build();
        CountryPhoneCode testValue = createTestValue();
        when(dao.getEntityClass()).thenAnswer(invocation -> CountryPhoneCode.class);
        when(transformationFunctionService.getEntity(CountryPhoneCode.class, dto))
                .thenReturn(testValue);

        countryPhoneCodeService.saveEntity(dto);

        verifySaveEntity(testValue);
    }

    @Test
    public void testUpdateEntity() {
        ConstantEntityPayloadDto newDto = ConstantEntityPayloadDto.builder()
                .label("newLabel")
                .value("newValue")
                .build();
        ConstantEntityPayloadSearchFieldDto dtoUpdate = ConstantEntityPayloadSearchFieldDto.builder()
                .searchField("searchField")
                .newEntity(newDto)
                .build();

        CountryPhoneCode testValue = CountryPhoneCode.builder()
                .value("newValue")
                .build();
        when(dao.getEntityClass()).thenAnswer(invocation -> CountryPhoneCode.class);
        when(transformationFunctionService.getEntity(CountryPhoneCode.class, newDto))
                .thenReturn(testValue);

        countryPhoneCodeService.updateEntity(dtoUpdate);

        verifyUpdateEntity(testValue, dtoUpdate);
    }

    @Test
    public void testDeleteEntity() {
        OneFieldEntityDto dto = new OneFieldEntityDto("testValue");

        countryPhoneCodeService.deleteEntity(dto);

        verifyDeleteEntity(dto);
    }

    @Test
    public void testGetEntity() {
        OneFieldEntityDto dto = new OneFieldEntityDto("testValue");
        Parameter parameter = parameterFactory.createStringParameter(VALUE, dto.getValue());
        CountryPhoneCode testValue = createTestValue();
        ConstantEntityPayloadDto constantEntityPayloadDto = getResponseOneFieldEntityDto();

        when(parameterFactory.createStringParameter(VALUE, dto.getValue())).thenReturn(parameter);
        when(dao.getEntity(parameter)).thenReturn(testValue);
        when(transformationFunctionService.getEntity(ConstantEntityPayloadDto.class, testValue))
                .thenReturn(constantEntityPayloadDto);

        ConstantEntityPayloadDto result = countryPhoneCodeService.getEntity(dto);

        assertEquals(constantEntityPayloadDto, result);
    }

    @Test
    public void testGetEntityOptional() {
        OneFieldEntityDto dto = new OneFieldEntityDto("testValue");
        Parameter parameter = parameterFactory.createStringParameter(VALUE, dto.getValue());
        CountryPhoneCode testValue = createTestValue();
        ConstantEntityPayloadDto constantEntityPayloadDto = getResponseOneFieldEntityDto();

        when(parameterFactory.createStringParameter(VALUE, dto.getValue())).thenReturn(parameter);
        when(dao.getEntity(parameter)).thenReturn(testValue);
        when(transformationFunctionService.getEntity(ConstantEntityPayloadDto.class, testValue))
                .thenReturn(constantEntityPayloadDto);

        Optional<ConstantEntityPayloadDto> result = countryPhoneCodeService.getEntityOptional(dto);

        assertEquals(Optional.of(constantEntityPayloadDto), result);
    }

    @Test
    public void testGetAllEntity() {
        CountryPhoneCode testValue = createTestValue();
        ConstantEntityPayloadDto constantEntityPayloadDto = getResponseOneFieldEntityDto();

        when(dao.getEntityList()).thenReturn(List.of(testValue));
        when(transformationFunctionService.getEntity(ConstantEntityPayloadDto.class, testValue))
                .thenReturn(constantEntityPayloadDto);

        List<ConstantEntityPayloadDto> list = countryPhoneCodeService.getEntities();

        assertEquals(1, list.size());
        assertEquals(constantEntityPayloadDto, list.get(0));
    }

    private CountryPhoneCode createTestValue() {
        return CountryPhoneCode.builder()
                .value("testValue")
                .build();
    }
}