package com.b2c.prototype.service.processor.price.base;

import com.b2c.prototype.modal.dto.payload.ConstantEntityPayloadDto;
import com.b2c.prototype.modal.entity.price.Currency;
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

class CurrencyServiceTest extends AbstractConstantEntityServiceTest<Currency> {
    @Mock
    private ITransformationFunctionService transformationFunctionService;
    @InjectMocks
    private CurrencyService currencyService;



    @Test
    public void testSaveEntity() {
        ConstantEntityPayloadDto dto = ConstantEntityPayloadDto.builder()
                .label("testLabel")
                .value("testValue")
                .build();
        Currency testValue = createTestValue();
        when(dao.getEntityClass()).thenAnswer(invocation -> Currency.class);
        when(transformationFunctionService.getEntity(Currency.class, dto))
                .thenReturn(testValue);

        currencyService.saveEntity(dto);

        verifySaveEntity(testValue);
    }

    @Test
    public void testUpdateEntity() {
        ConstantEntityPayloadDto newDto = ConstantEntityPayloadDto.builder()
                .label("newLabel")
                .value("newValue")
                .build();

        Currency testValue = Currency.builder()
                .value("newValue")
                .build();
        when(dao.getEntityClass()).thenAnswer(invocation -> Currency.class);
        when(transformationFunctionService.getEntity(Currency.class, newDto))
                .thenReturn(testValue);

        currencyService.updateEntity("testValue", newDto);

        verifyUpdateEntity(testValue, newDto);
    }

    @Test
    public void testDeleteEntity() {
        

        currencyService.deleteEntity("testValue");

        verifyDeleteEntity("testValue");
    }

    @Test
    public void testGetEntity() {
        
        Parameter parameter = parameterFactory.createStringParameter(VALUE, "testValue");
        Currency testValue = createTestValue();
        ConstantEntityPayloadDto constantEntityPayloadDto = getResponseOneFieldEntityDto();

        when(parameterFactory.createStringParameter(VALUE, "testValue")).thenReturn(parameter);
        when(dao.getEntity(parameter)).thenReturn(testValue);
        when(transformationFunctionService.getEntity(ConstantEntityPayloadDto.class, testValue))
                .thenReturn(constantEntityPayloadDto);

        ConstantEntityPayloadDto result = currencyService.getEntity("testValue");

        assertEquals(constantEntityPayloadDto, result);
    }

    @Test
    public void testGetEntityOptional() {
        Parameter parameter = parameterFactory.createStringParameter(VALUE, "testValue");
        Currency testValue = createTestValue();
        ConstantEntityPayloadDto constantEntityPayloadDto = getResponseOneFieldEntityDto();

        when(parameterFactory.createStringParameter(VALUE, "testValue")).thenReturn(parameter);
        when(dao.getEntity(parameter)).thenReturn(testValue);
        when(transformationFunctionService.getEntity(ConstantEntityPayloadDto.class, testValue))
                .thenReturn(constantEntityPayloadDto);

        Optional<ConstantEntityPayloadDto> result = currencyService.getEntityOptional("testValue");

        assertEquals(Optional.of(constantEntityPayloadDto), result);
    }

    @Test
    public void testGetAllEntity() {
        Currency testValue = createTestValue();
        ConstantEntityPayloadDto constantEntityPayloadDto = getResponseOneFieldEntityDto();

        when(dao.getEntityList()).thenReturn(List.of(testValue));
        when(transformationFunctionService.getEntity(ConstantEntityPayloadDto.class, testValue))
                .thenReturn(constantEntityPayloadDto);

        List<ConstantEntityPayloadDto> list = currencyService.getEntities();

        assertEquals(1, list.size());
        assertEquals(constantEntityPayloadDto, list.get(0));
    }

    private Currency createTestValue() {
        return Currency.builder()
                .value("testValue")
                .build();
    }
}