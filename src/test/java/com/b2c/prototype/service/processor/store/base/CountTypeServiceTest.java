package com.b2c.prototype.service.processor.store.base;

import com.b2c.prototype.modal.dto.payload.ConstantEntityPayloadDto;
import com.b2c.prototype.modal.entity.store.CountType;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.AbstractConstantEntityServiceTest;
import com.tm.core.processor.finder.parameter.Parameter;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CountTypeServiceTest extends AbstractConstantEntityServiceTest<CountType> {
    @Mock
    private ITransformationFunctionService transformationFunctionService;
    @InjectMocks
    private CountTypeService countTypeService;

    @Test
    public void testSaveEntity() {
        ConstantEntityPayloadDto dto = ConstantEntityPayloadDto.builder()
                .label("testLabel")
                .value("testValue")
                .build();
        CountType testValue = createTestValue();
        when(dao.getEntityClass()).thenAnswer(invocation -> CountType.class);
        when(transformationFunctionService.getEntity(CountType.class, dto))
                .thenReturn(testValue);

        countTypeService.saveEntity(dto);

        verifySaveEntity(testValue);
    }

    @Test
    public void testUpdateEntity() {
        ConstantEntityPayloadDto newDto = ConstantEntityPayloadDto.builder()
                .label("newLabel")
                .value("newValue")
                .build();

        CountType testValue = CountType.builder()
                .value("newValue")
                .build();
        when(dao.getEntityClass()).thenAnswer(invocation -> CountType.class);
        when(transformationFunctionService.getEntity(CountType.class, newDto))
                .thenReturn(testValue);

        countTypeService.updateEntity("testValue", newDto);

        verifyUpdateEntity(testValue, newDto);
    }

    @Test
    public void testDeleteEntity() {
        

        countTypeService.deleteEntity("testValue");

        verifyDeleteEntity("testValue");
    }

    @Test
    public void testGetEntity() {
        
        Parameter parameter = parameterFactory.createStringParameter(VALUE, "testValue");
        CountType testValue = createTestValue();
        ConstantEntityPayloadDto constantEntityPayloadDto = getResponseOneFieldEntityDto();

        when(parameterFactory.createStringParameter(VALUE, "testValue")).thenReturn(parameter);
        when(dao.getEntity(parameter)).thenReturn(testValue);
        when(transformationFunctionService.getEntity(ConstantEntityPayloadDto.class, testValue))
                .thenReturn(constantEntityPayloadDto);

        ConstantEntityPayloadDto result = countTypeService.getEntity("testValue");

        assertEquals(constantEntityPayloadDto, result);
    }

    @Test
    public void testGetEntityOptional() {
        
        Parameter parameter = parameterFactory.createStringParameter(VALUE, "testValue");
        CountType testValue = createTestValue();
        ConstantEntityPayloadDto constantEntityPayloadDto = getResponseOneFieldEntityDto();

        when(parameterFactory.createStringParameter(VALUE, "testValue")).thenReturn(parameter);
        when(dao.getEntity(parameter)).thenReturn(testValue);
        when(transformationFunctionService.getEntity(ConstantEntityPayloadDto.class, testValue))
                .thenReturn(constantEntityPayloadDto);

        Optional<ConstantEntityPayloadDto> result = countTypeService.getEntityOptional("testValue");

        assertEquals(Optional.of(constantEntityPayloadDto), result);
    }

    @Test
    public void testGetAllEntity() {
        CountType testValue = createTestValue();
        ConstantEntityPayloadDto constantEntityPayloadDto = getResponseOneFieldEntityDto();

        when(dao.getEntityList()).thenReturn(List.of(testValue));
        when(transformationFunctionService.getEntity(ConstantEntityPayloadDto.class, testValue))
                .thenReturn(constantEntityPayloadDto);

        List<ConstantEntityPayloadDto> list = countTypeService.getEntities();

        assertEquals(1, list.size());
        assertEquals(constantEntityPayloadDto, list.get(0));
    }

    private CountType createTestValue() {
        return CountType.builder()
                .value("testValue")
                .build();
    }
}
