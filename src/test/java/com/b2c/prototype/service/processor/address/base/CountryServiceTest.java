package com.b2c.prototype.service.processor.address.base;

import com.b2c.prototype.modal.dto.payload.ConstantEntityPayloadDto;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.AbstractConstantEntityServiceTest;
import com.tm.core.processor.finder.factory.IParameterFactory;
import com.tm.core.processor.finder.parameter.Parameter;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class CountryServiceTest extends AbstractConstantEntityServiceTest<Country> {

    @Mock
    private ITransformationFunctionService transformationFunctionService;
    @Mock
    private IParameterFactory parameterFactory;
    @InjectMocks
    private CountryService countryService;

    @Test
    public void testSaveEntity() {
        ConstantEntityPayloadDto dto = ConstantEntityPayloadDto.builder()
                .label("testLabel")
                .value("testValue")
                .build();
        Country testValue = createTestValue();

        when(dao.getEntityClass()).thenAnswer(invocation -> Country.class);
        when(transformationFunctionService.getEntity(Country.class, dto))
                .thenReturn(testValue);

        countryService.saveEntity(dto);

        verifySaveEntity(testValue);
    }

    @Test
    public void testUpdateEntity() {
        ConstantEntityPayloadDto newDto = ConstantEntityPayloadDto.builder()
                .label("newLabel")
                .value("newValue")
                .build();

        Country testValue = Country.builder()
                .value("newValue")
                .build();
        when(dao.getEntityClass()).thenAnswer(invocation -> Country.class);
        when(transformationFunctionService.getEntity(Country.class, newDto))
                .thenReturn(testValue);


        countryService.updateEntity("testValue", newDto);

        verifyUpdateEntity(testValue, newDto);
    }

    @Test
    public void testDeleteEntity() {
        

        countryService.deleteEntity("testValue");

        verifyDeleteEntity("testValue");
    }

    @Test
    public void testGetEntity() {
        
        Parameter parameter = parameterFactory.createStringParameter(VALUE, "testValue");
        Country testValue = createTestValue();
        ConstantEntityPayloadDto constantEntityPayloadDto = getResponseOneFieldEntityDto();

        when(parameterFactory.createStringParameter(VALUE, "testValue")).thenReturn(parameter);
        when(dao.getEntity(parameter)).thenReturn(testValue);
        when(transformationFunctionService.getEntity(ConstantEntityPayloadDto.class, testValue))
                .thenReturn(constantEntityPayloadDto);

        ConstantEntityPayloadDto result = countryService.getEntity("testValue");

        assertEquals(constantEntityPayloadDto, result);
    }

    @Test
    public void testGetEntityOptional() {
        
        Parameter parameter = parameterFactory.createStringParameter(VALUE, "testValue");
        Country testValue = createTestValue();
        ConstantEntityPayloadDto constantEntityPayloadDto = getResponseOneFieldEntityDto();

        when(parameterFactory.createStringParameter(VALUE, "testValue")).thenReturn(parameter);
        when(dao.getEntity(parameter)).thenReturn(testValue);
        when(transformationFunctionService.getEntity(ConstantEntityPayloadDto.class, testValue))
                .thenReturn(constantEntityPayloadDto);

        Optional<ConstantEntityPayloadDto> result = countryService.getEntityOptional("testValue");

        assertEquals(Optional.of(constantEntityPayloadDto), result);
    }

    @Test
    public void testGetEntities() {
        Country testValue = createTestValue();
        ConstantEntityPayloadDto constantEntityPayloadDto = getResponseOneFieldEntityDto();

        when(dao.getEntityList()).thenReturn(List.of(testValue));
        when(transformationFunctionService.getEntity(ConstantEntityPayloadDto.class, testValue))
                .thenReturn(constantEntityPayloadDto);

        List<ConstantEntityPayloadDto> list = countryService.getEntities();

        assertEquals(1, list.size());
        assertEquals(constantEntityPayloadDto, list.get(0));
    }

    private Country createTestValue() {
        return Country.builder()
                .value("testValue")
                .label("label")
                .build();
    }
}