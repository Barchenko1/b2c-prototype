package com.b2c.prototype.service.processor.message;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDtoUpdate;
import com.b2c.prototype.modal.entity.message.MessageType;
import com.b2c.prototype.service.processor.AbstractOneFieldEntityServiceTest;
import com.b2c.prototype.service.processor.message.base.MessageTypeService;
import com.tm.core.processor.finder.parameter.Parameter;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class MessageTypeServiceTest extends AbstractOneFieldEntityServiceTest<MessageType> {

    @InjectMocks
    private MessageTypeService messageTypeService;
    
    @Override
    protected String getFieldName() {
        return "value";
    }

    @Test
    public void testSaveEntity() {
        OneFieldEntityDto dto = new OneFieldEntityDto("testValue");
        MessageType testValue = createTestValue();

        messageTypeService.saveEntity(dto);

        verifySaveEntity(testValue);
    }

    @Test
    public void testUpdateEntity() {
        OneFieldEntityDto oldDto = new OneFieldEntityDto("oldValue");
        OneFieldEntityDto newDto = new OneFieldEntityDto("newValue");
        OneFieldEntityDtoUpdate dtoUpdate = new OneFieldEntityDtoUpdate();
        dtoUpdate.setOldEntityDto(oldDto);
        dtoUpdate.setNewEntityDto(newDto);

        MessageType testValue = MessageType.builder()
                .value("newValue")
                .build();

        messageTypeService.updateEntity(dtoUpdate);

        verifyUpdateEntity(testValue, dtoUpdate);
    }

    @Test
    public void testDeleteEntity() {
        OneFieldEntityDto dto = new OneFieldEntityDto("testValue");
        MessageType testValue = createTestValue();

        messageTypeService.deleteEntity(dto);

        verifyDeleteEntity(testValue, dto);
    }

    @Test
    public void testGetEntity() {
        OneFieldEntityDto dto = new OneFieldEntityDto("testValue");
        Parameter parameter = parameterFactory.createStringParameter(getFieldName(), dto.getValue());
        MessageType testValue = createTestValue();

        when(parameterFactory.createStringParameter(getFieldName(), dto.getValue())).thenReturn(parameter);
        when(dao.getEntity(parameter)).thenReturn(testValue);

        MessageType result = messageTypeService.getEntity(dto);

        assertEquals(testValue, result);
    }

    @Test
    public void testGetEntityOptional() {
        OneFieldEntityDto dto = new OneFieldEntityDto("testValue");
        Parameter parameter = parameterFactory.createStringParameter(getFieldName(), dto.getValue());
        MessageType testValue = createTestValue();

        when(parameterFactory.createStringParameter(getFieldName(), dto.getValue())).thenReturn(parameter);
        when(dao.getOptionalEntity(parameter)).thenReturn(Optional.of(testValue));

        Optional<MessageType> result = messageTypeService.getEntityOptional(dto);

        assertEquals(Optional.of(testValue), result);
    }

    private MessageType createTestValue() {
        return MessageType.builder()
                .value("testValue")
                .build();
    }
}