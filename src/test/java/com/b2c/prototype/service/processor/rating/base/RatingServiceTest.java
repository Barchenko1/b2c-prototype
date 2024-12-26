package com.b2c.prototype.service.processor.rating.base;

import com.b2c.prototype.modal.dto.common.OneIntegerFieldEntityDto;
import com.b2c.prototype.modal.dto.common.OneIntegerFieldEntityDtoUpdate;
import com.b2c.prototype.modal.entity.item.Rating;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.AbstractOneFieldEntityServiceTest;
import com.tm.core.processor.finder.parameter.Parameter;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RatingServiceTest extends AbstractOneFieldEntityServiceTest<Rating> {

    @Mock
    private ITransformationFunctionService transformationFunctionService;
    @InjectMocks
    private RatingService ratingService;

    @Override
    protected String getFieldName() {
        return "value";
    }

    @Test
    public void testSaveEntity() {
        OneIntegerFieldEntityDto dto = new OneIntegerFieldEntityDto(1);
        Rating testValue = createTestValue();
        Function<OneIntegerFieldEntityDto, Rating> mockFunction = input -> testValue;
        when(transformationFunctionService.getTransformationFunction(OneIntegerFieldEntityDto.class, Rating.class))
                .thenReturn(mockFunction);
        ratingService.saveEntity(dto);

        verify(dao).persistEntity(testValue);
        verify(singleValueMap).putEntity(testValue.getClass(), getFieldName(), testValue);
    }

    @Test
    public void testUpdateEntity() {
        OneIntegerFieldEntityDto oldDto = new OneIntegerFieldEntityDto(1);
        OneIntegerFieldEntityDto newDto = new OneIntegerFieldEntityDto(2);
        OneIntegerFieldEntityDtoUpdate dtoUpdate = OneIntegerFieldEntityDtoUpdate.builder()
                .oldEntity(oldDto)
                .newEntity(newDto)
                .build();

        Rating testValue = Rating.builder()
                .value(2)
                .build();
        Function<OneIntegerFieldEntityDto, Rating> mockFunction = input -> testValue;
        when(transformationFunctionService.getTransformationFunction(OneIntegerFieldEntityDto.class, Rating.class))
                .thenReturn(mockFunction);
        ratingService.updateEntity(dtoUpdate);

        Integer searchParameter = dtoUpdate.getOldEntity().getValue();
        Parameter parameter = parameterFactory.createIntegerParameter(getFieldName(), searchParameter);
        verify(dao).findEntityAndUpdate(testValue, parameter);
        verify(singleValueMap).putRemoveEntity(
                testValue.getClass(),
                searchParameter,
                dtoUpdate.getNewEntity().getValue(),
                testValue
        );
    }

    @Test
    public void testDeleteEntity() {
        OneIntegerFieldEntityDto dto = new OneIntegerFieldEntityDto(1);
        Rating testValue = createTestValue();
        Function<OneIntegerFieldEntityDto, Rating> mockFunction = input -> testValue;
        when(transformationFunctionService.getTransformationFunction(OneIntegerFieldEntityDto.class, Rating.class))
                .thenReturn(mockFunction);
        ratingService.deleteEntity(dto);

        verify(dao).deleteEntity(testValue);
        verify(singleValueMap).removeEntity(testValue.getClass(), dto.getValue());
    }

    @Test
    public void testGetEntity() {
        OneIntegerFieldEntityDto dto = new OneIntegerFieldEntityDto(1);
        Parameter parameter = parameterFactory.createIntegerParameter(getFieldName(), dto.getValue());
        Rating testValue = createTestValue();

        when(parameterFactory.createIntegerParameter(getFieldName(), dto.getValue())).thenReturn(parameter);
        when(dao.getEntity(parameter)).thenReturn(testValue);

        Rating result = ratingService.getEntity(dto);

        assertEquals(testValue, result);
    }

    @Test
    public void testGetEntityOptional() {
        OneIntegerFieldEntityDto dto = new OneIntegerFieldEntityDto(1);
        Parameter parameter = parameterFactory.createIntegerParameter(getFieldName(), dto.getValue());
        Rating testValue = createTestValue();

        when(parameterFactory.createIntegerParameter(getFieldName(), dto.getValue())).thenReturn(parameter);
        when(dao.getOptionalEntity(parameter)).thenReturn(Optional.of(testValue));

        Optional<Rating> result = ratingService.getEntityOptional(dto);

        assertEquals(Optional.of(testValue), result);
    }

    private Rating createTestValue() {
        return Rating.builder()
                .value(1)
                .build();
    }
}