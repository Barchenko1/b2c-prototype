package com.b2c.prototype.service.processor.rating.base;

import com.b2c.prototype.modal.dto.common.ConstantNumberEntityPayloadDto;
import com.b2c.prototype.modal.dto.common.ConstantNumberSearchEntityPayloadDtoUpdate;
import com.b2c.prototype.modal.entity.item.Rating;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RatingServiceTest extends AbstractConstantEntityServiceTest<Rating> {

    @Mock
    private ITransformationFunctionService transformationFunctionService;
    @InjectMocks
    private RatingService ratingService;

    @Test
    public void testSaveEntity() {
        ConstantNumberEntityPayloadDto dto = new ConstantNumberEntityPayloadDto(1);
        Rating testValue = createTestValue();

        when(dao.getEntityClass()).thenAnswer(invocation -> Rating.class);
        when(transformationFunctionService.getEntity(Rating.class, dto))
                .thenReturn(testValue);

        ratingService.saveEntity(dto);

        verify(dao).persistEntity(testValue);
        verify(singleValueMap).putEntity(testValue.getClass(), VALUE, testValue);
    }

    @Test
    public void testUpdateEntity() {
        ConstantNumberEntityPayloadDto newDto = new ConstantNumberEntityPayloadDto(2);
        ConstantNumberSearchEntityPayloadDtoUpdate dtoUpdate = ConstantNumberSearchEntityPayloadDtoUpdate.builder()
                .searchField(1)
                .newEntity(newDto)
                .build();

        Rating testValue = Rating.builder()
                .value(2)
                .build();
        when(dao.getEntityClass()).thenAnswer(invocation -> Rating.class);
        when(transformationFunctionService.getEntity(Rating.class, newDto))
                .thenReturn(testValue);

        ratingService.updateEntity(dtoUpdate);

        Integer searchParameter = dtoUpdate.getSearchField();
        Parameter parameter = parameterFactory.createIntegerParameter(VALUE, searchParameter);
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
        ConstantNumberEntityPayloadDto dto = new ConstantNumberEntityPayloadDto(1);

        ratingService.deleteEntity(dto);

        Parameter parameter = parameterFactory.createNumberParameter(VALUE, dto.getValue());
        verify(dao).findEntityAndDelete(parameter);
        verify(singleValueMap).removeEntity(any(), eq(dto.getValue()));
    }

    @Test
    public void testGetEntity() {
        ConstantNumberEntityPayloadDto constantNumberEntityPayloadDto = new ConstantNumberEntityPayloadDto(1);
        Parameter parameter = parameterFactory.createNumberParameter(VALUE, constantNumberEntityPayloadDto.getValue());
        Rating testValue = createTestValue();

        when(parameterFactory.createNumberParameter(VALUE, constantNumberEntityPayloadDto.getValue())).thenReturn(parameter);
        when(dao.getEntity(parameter)).thenReturn(testValue);
        when(transformationFunctionService.getEntity(ConstantNumberEntityPayloadDto.class, testValue))
                .thenReturn(constantNumberEntityPayloadDto);

        ConstantNumberEntityPayloadDto result = ratingService.getEntity(constantNumberEntityPayloadDto);

        assertEquals(constantNumberEntityPayloadDto, result);
    }

    @Test
    public void testGetEntityOptional() {
        ConstantNumberEntityPayloadDto constantNumberEntityPayloadDto = new ConstantNumberEntityPayloadDto(1);
        Parameter parameter = parameterFactory.createNumberParameter(VALUE, constantNumberEntityPayloadDto.getValue());
        Rating testValue = createTestValue();

        when(parameterFactory.createNumberParameter(VALUE, constantNumberEntityPayloadDto.getValue())).thenReturn(parameter);
        when(dao.getEntity(parameter)).thenReturn(testValue);
        when(transformationFunctionService.getEntity(ConstantNumberEntityPayloadDto.class, testValue))
                .thenReturn(constantNumberEntityPayloadDto);

        Optional<ConstantNumberEntityPayloadDto> result = ratingService.getEntityOptional(constantNumberEntityPayloadDto);

        assertTrue(result.isPresent());
        assertEquals(constantNumberEntityPayloadDto, result.get());
    }

    @Test
    public void testGetEntities() {
        ConstantNumberEntityPayloadDto constantEntityPayloadDto = new ConstantNumberEntityPayloadDto(1);
        Rating testValue = createTestValue();

        when(dao.getEntityList()).thenReturn(List.of(testValue));
        when(transformationFunctionService.getEntity(ConstantNumberEntityPayloadDto.class, testValue))
                .thenReturn(constantEntityPayloadDto);

        List<ConstantNumberEntityPayloadDto> list = ratingService.getEntities();

        assertEquals(1, list.size());
        assertEquals(constantEntityPayloadDto, list.get(0));
    }

    private Rating createTestValue() {
        return Rating.builder()
                .value(1)
                .build();
    }
}