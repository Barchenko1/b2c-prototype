package com.b2c.prototype.manager.rating.base;

import com.b2c.prototype.modal.dto.common.NumberConstantPayloadDto;
import com.b2c.prototype.modal.entity.item.Rating;
import com.b2c.prototype.manager.AbstractConstantEntityManagerTest;
import com.tm.core.finder.parameter.Parameter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static com.b2c.prototype.util.Constant.VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RatingManagerTest extends AbstractConstantEntityManagerTest<Rating> {

    @Mock
    private Function<Rating, NumberConstantPayloadDto> mapEntityToDtoFunction;
    @Mock
    private Function<NumberConstantPayloadDto, Rating> mapDtoToEntityFunction;
    private RatingManager ratingManager;

    @BeforeEach
    void setUp() {
        when(transformationFunctionService.getTransformationFunction(NumberConstantPayloadDto.class, Rating.class))
                .thenReturn(mapDtoToEntityFunction);
        when(transformationFunctionService.getTransformationFunction(Rating.class, NumberConstantPayloadDto.class))
                .thenReturn(mapEntityToDtoFunction);

        ratingManager = new RatingManager(
                parameterFactory,
                dao,
                transformationFunctionService,
                singleValueMap
        );
    }

    @Test
    public void testSaveEntity() {
        NumberConstantPayloadDto dto = new NumberConstantPayloadDto(1);
        Rating testValue = createTestValue();

        when(mapDtoToEntityFunction.apply(dto)).thenReturn(testValue);
        when(dao.getEntityClass()).thenAnswer(invocation -> Rating.class);

        ratingManager.saveEntity(dto);

        verify(dao).persistEntity(testValue);
        verify(singleValueMap).putEntity(testValue.getClass(), VALUE, testValue);
    }

    @Test
    public void testUpdateEntity() {
        NumberConstantPayloadDto newDto = new NumberConstantPayloadDto(2);
        Rating testValue = Rating.builder()
                .value(2)
                .build();

        when(mapDtoToEntityFunction.apply(newDto)).thenReturn(testValue);
        when(dao.getEntityClass()).thenAnswer(invocation -> Rating.class);

        ratingManager.updateEntity(1, newDto);

        Parameter parameter = parameterFactory.createIntegerParameter(VALUE, 1);
        verify(dao).findEntityAndUpdate(testValue, parameter);
        verify(singleValueMap).putRemoveEntity(
                testValue.getClass(),
                1,
                newDto.getValue(),
                testValue
        );
    }

    @Test
    public void testDeleteEntity() {
        ratingManager.deleteEntity(1);
        Parameter parameter = parameterFactory.createNumberParameter(VALUE, 1);
        verify(dao).findEntityAndDelete(parameter);
        verify(singleValueMap).removeEntity(any(), eq(1));
    }

    @Test
    public void testGetEntity() {
        NumberConstantPayloadDto numberConstantPayloadDto = new NumberConstantPayloadDto(1);
        Parameter parameter = parameterFactory.createNumberParameter(VALUE, numberConstantPayloadDto.getValue());
        Rating testValue = createTestValue();

        when(mapEntityToDtoFunction.apply(testValue)).thenReturn(numberConstantPayloadDto);
        when(parameterFactory.createNumberParameter(VALUE, 1)).thenReturn(parameter);
        when(dao.getEntity(parameter)).thenReturn(testValue);

        NumberConstantPayloadDto result = ratingManager.getEntity(1);

        assertEquals(numberConstantPayloadDto, result);
    }

    @Test
    public void testGetEntityOptional() {
        NumberConstantPayloadDto numberConstantPayloadDto = new NumberConstantPayloadDto(1);
        Parameter parameter = parameterFactory.createNumberParameter(VALUE, numberConstantPayloadDto.getValue());
        Rating testValue = createTestValue();

        when(mapEntityToDtoFunction.apply(testValue)).thenReturn(numberConstantPayloadDto);
        when(parameterFactory.createNumberParameter(VALUE, 1)).thenReturn(parameter);
        when(dao.getEntity(parameter)).thenReturn(testValue);

        Optional<NumberConstantPayloadDto> result = ratingManager.getEntityOptional(1);

        assertTrue(result.isPresent());
        assertEquals(numberConstantPayloadDto, result.get());
    }

    @Test
    public void testGetEntities() {
        NumberConstantPayloadDto numberConstantPayloadDto = new NumberConstantPayloadDto(1);
        Rating testValue = createTestValue();

        when(mapEntityToDtoFunction.apply(testValue)).thenReturn(numberConstantPayloadDto);
        when(dao.getEntityList()).thenReturn(List.of(testValue));

        List<NumberConstantPayloadDto> list = ratingManager.getEntities();

        assertEquals(1, list.size());
        assertEquals(numberConstantPayloadDto, list.get(0));
    }

    private Rating createTestValue() {
        return Rating.builder()
                .value(1)
                .build();
    }
}