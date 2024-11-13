package com.b2c.prototype.service.processor.rating.base;

import com.b2c.prototype.modal.dto.common.OneIntegerFieldEntityDto;
import com.b2c.prototype.modal.dto.common.OneIntegerFieldEntityDtoUpdate;
import com.b2c.prototype.modal.entity.item.Rating;
import com.b2c.prototype.service.processor.AbstractOneFieldEntityServiceTest;
import com.tm.core.processor.finder.parameter.Parameter;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RatingServiceTest extends AbstractOneFieldEntityServiceTest<Rating> {

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

        ratingService.saveEntity(dto);

        verify(dao).saveEntity(testValue);
        verify(entityCachedMap).putEntity(testValue.getClass(), getFieldName(), testValue);
    }

    @Test
    public void testUpdateEntity() {
        OneIntegerFieldEntityDto oldDto = new OneIntegerFieldEntityDto(1);
        OneIntegerFieldEntityDto newDto = new OneIntegerFieldEntityDto(2);
        OneIntegerFieldEntityDtoUpdate dtoUpdate = new OneIntegerFieldEntityDtoUpdate();
        dtoUpdate.setOldEntityDto(oldDto);
        dtoUpdate.setNewEntityDto(newDto);

        Rating testValue = Rating.builder()
                .value(2)
                .build();

        ratingService.updateEntity(dtoUpdate);

        Integer searchParameter = dtoUpdate.getOldEntityDto().getValue();
        Parameter parameter = parameterFactory.createIntegerParameter(getFieldName(), searchParameter);
        verify(dao).findEntityAndUpdate(testValue, parameter);
        verify(entityCachedMap).updateEntity(
                testValue.getClass(),
                searchParameter,
                dtoUpdate.getNewEntityDto().getValue(),
                testValue
        );
    }

    @Test
    public void testDeleteEntity() {
        OneIntegerFieldEntityDto dto = new OneIntegerFieldEntityDto(1);
        Rating testValue = createTestValue();

        ratingService.deleteEntity(dto);

        verify(dao).deleteEntity(testValue);
        verify(entityCachedMap).removeEntity(testValue.getClass(), dto.getValue());
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