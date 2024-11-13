package com.b2c.prototype.service.processor.item.base;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDtoUpdate;
import com.b2c.prototype.modal.entity.item.ItemStatus;
import com.b2c.prototype.service.processor.AbstractOneFieldEntityServiceTest;
import com.tm.core.processor.finder.parameter.Parameter;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ItemStatusServiceTest extends AbstractOneFieldEntityServiceTest<ItemStatus> {
    
    @InjectMocks
    private ItemStatusService itemStatusService;

    @Override
    protected String getFieldName() {
        return "value";
    }

    @Test
    public void testSaveEntity() {
        OneFieldEntityDto dto = new OneFieldEntityDto("testValue");
        ItemStatus testValue = createTestValue();

        itemStatusService.saveEntity(dto);

        verifySaveEntity(testValue);
    }

    @Test
    public void testUpdateEntity() {
        OneFieldEntityDto oldDto = new OneFieldEntityDto("oldValue");
        OneFieldEntityDto newDto = new OneFieldEntityDto("newValue");
        OneFieldEntityDtoUpdate dtoUpdate = new OneFieldEntityDtoUpdate();
        dtoUpdate.setOldEntityDto(oldDto);
        dtoUpdate.setNewEntityDto(newDto);

        ItemStatus testValue = ItemStatus.builder()
                .value("newValue")
                .build();

        itemStatusService.updateEntity(dtoUpdate);

        verifyUpdateEntity(testValue, dtoUpdate);
    }

    @Test
    public void testDeleteEntity() {
        OneFieldEntityDto dto = new OneFieldEntityDto("testValue");
        ItemStatus testValue = createTestValue();

        itemStatusService.deleteEntity(dto);

        verifyDeleteEntity(testValue, dto);
    }

    @Test
    public void testGetEntity() {
        OneFieldEntityDto dto = new OneFieldEntityDto("testValue");
        Parameter parameter = parameterFactory.createStringParameter(getFieldName(), dto.getValue());
        ItemStatus testValue = createTestValue();

        when(parameterFactory.createStringParameter(getFieldName(), dto.getValue())).thenReturn(parameter);
        when(dao.getEntity(parameter)).thenReturn(testValue);

        ItemStatus result = itemStatusService.getEntity(dto);

        assertEquals(testValue, result);
    }

    @Test
    public void testGetEntityOptional() {
        OneFieldEntityDto dto = new OneFieldEntityDto("testValue");
        Parameter parameter = parameterFactory.createStringParameter(getFieldName(), dto.getValue());
        ItemStatus testValue = createTestValue();

        when(parameterFactory.createStringParameter(getFieldName(), dto.getValue())).thenReturn(parameter);
        when(dao.getOptionalEntity(parameter)).thenReturn(Optional.of(testValue));

        Optional<ItemStatus> result = itemStatusService.getEntityOptional(dto);

        assertEquals(Optional.of(testValue), result);
    }

    private ItemStatus createTestValue() {
        return ItemStatus.builder()
                .value("testValue")
                .build();
    }
}