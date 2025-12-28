package com.b2c.prototype.manager.rating.base;

import com.b2c.prototype.manager.AbstractConstantEntityManagerTest;
import com.b2c.prototype.modal.dto.common.NumberConstantPayloadDto;
import com.b2c.prototype.modal.entity.review.Rating;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
//        when(transformationFunctionService.getTransformationFunction(NumberConstantPayloadDto.class, Rating.class))
//                .thenReturn(mapDtoToEntityFunction);
//        when(transformationFunctionService.getTransformationFunction(Rating.class, NumberConstantPayloadDto.class))
//                .thenReturn(mapEntityToDtoFunction);

        ratingManager = null;
    }

    @Test
    public void testSaveEntity() {
        NumberConstantPayloadDto dto = new NumberConstantPayloadDto(1);
        Rating testValue = createTestValue();

        when(mapDtoToEntityFunction.apply(dto)).thenReturn(testValue);
//        when(dao.getEntityClass()).thenAnswer(invocation -> Rating.class);

//        ratingManager.saveEntity(transform);

        verify(dao).persistEntity(testValue);
    }

    @Test
    public void testUpdateEntity() {
        NumberConstantPayloadDto newDto = new NumberConstantPayloadDto(2);
        Rating testValue = Rating.builder()
                .value(2)
                .build();

        when(mapDtoToEntityFunction.apply(newDto)).thenReturn(testValue);
//        when(dao.getEntityClass()).thenAnswer(invocation -> Rating.class);

//        ratingManager.updateEntity(1, newDto);

//        Parameter parameter = parameterFactory.createIntegerParameter(VALUE, 1);
//        verify(dao).findEntityAndUpdate(testValue, parameter);
    }

    @Test
    public void testDeleteEntity() {
//        ratingManager.deleteEntity(1);
//        Parameter parameter = parameterFactory.createNumberParameter(VALUE, 1);
//        verify(dao).findEntityAndDelete(parameter);
    }

    @Test
    public void testGetEntity() {
        NumberConstantPayloadDto numberConstantPayloadDto = new NumberConstantPayloadDto(1);
//        Parameter parameter = parameterFactory.createNumberParameter(VALUE, numberConstantPayloadDto.getValue());
        Rating testValue = createTestValue();

        when(mapEntityToDtoFunction.apply(testValue)).thenReturn(numberConstantPayloadDto);
//        when(parameterFactory.createNumberParameter(VALUE, 1)).thenReturn(parameter);
//        when(dao.getNamedQueryEntity(parameter)).thenReturn(testValue);

//        NumberConstantPayloadDto result = ratingManager.getEntity(1);

//        assertEquals(numberConstantPayloadDto, result);
    }

    @Test
    public void testGetEntityOptional() {
        NumberConstantPayloadDto numberConstantPayloadDto = new NumberConstantPayloadDto(1);
//        Parameter parameter = parameterFactory.createNumberParameter(VALUE, numberConstantPayloadDto.getValue());
        Rating testValue = createTestValue();

        when(mapEntityToDtoFunction.apply(testValue)).thenReturn(numberConstantPayloadDto);
//        when(parameterFactory.createNumberParameter(VALUE, 1)).thenReturn(parameter);
        //        when(dao.getNamedQueryEntity("", parameter)).thenReturn(testValue);

//        Optional<NumberConstantPayloadDto> result = ratingManager.getEntityOptional(1);

//        assertTrue(result.isPresent());
//        assertEquals(numberConstantPayloadDto, result.get());
    }

    @Test
    public void testGetEntities() {
        NumberConstantPayloadDto numberConstantPayloadDto = new NumberConstantPayloadDto(1);
        Rating testValue = createTestValue();

        when(mapEntityToDtoFunction.apply(testValue)).thenReturn(numberConstantPayloadDto);
//        when(dao.getEntityList()).thenReturn(List.of(testValue));

//        List<NumberConstantPayloadDto> list = ratingManager.getEntities();

//        assertEquals(1, list.size());
//        assertEquals(numberConstantPayloadDto, list.get(0));
    }

    private Rating createTestValue() {
        return Rating.builder()
                .value(1)
                .build();
    }
}