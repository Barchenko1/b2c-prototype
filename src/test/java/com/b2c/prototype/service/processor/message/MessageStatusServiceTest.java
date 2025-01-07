package com.b2c.prototype.service.processor.message;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDtoUpdate;
import com.b2c.prototype.modal.dto.response.ResponseOneFieldEntityDto;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.b2c.prototype.modal.entity.message.MessageStatus;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.AbstractOneFieldEntityServiceTest;
import com.b2c.prototype.service.processor.message.base.MessageStatusService;
import com.tm.core.processor.finder.parameter.Parameter;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class MessageStatusServiceTest extends AbstractOneFieldEntityServiceTest<MessageStatus> {
    @Mock
    private ITransformationFunctionService transformationFunctionService;
    @InjectMocks
    private MessageStatusService messageStatusService;

    @Override
    protected String getFieldName() {
        return "value";
    }

    @Test
    public void testSaveEntity() {
        OneFieldEntityDto dto = new OneFieldEntityDto("testValue");
        MessageStatus testValue = createTestValue();
        Function<OneFieldEntityDto, MessageStatus> mockFunction = input -> testValue;
        when(transformationFunctionService.getTransformationFunction(OneFieldEntityDto.class, MessageStatus.class))
                .thenReturn(mockFunction);
        messageStatusService.saveEntity(dto);

        verifySaveEntity(testValue);
    }

    @Test
    public void testUpdateEntity() {
        OneFieldEntityDto oldDto = new OneFieldEntityDto("oldValue");
        OneFieldEntityDto newDto = new OneFieldEntityDto("newValue");
        OneFieldEntityDtoUpdate dtoUpdate = OneFieldEntityDtoUpdate.builder()
                .oldEntity(oldDto)
                .newEntity(newDto)
                .build();

        MessageStatus testValue = MessageStatus.builder()
                .value("newValue")
                .build();
        Function<OneFieldEntityDto, MessageStatus> mockFunction = input -> testValue;
        when(transformationFunctionService.getTransformationFunction(OneFieldEntityDto.class, MessageStatus.class))
                .thenReturn(mockFunction);
        messageStatusService.updateEntity(dtoUpdate);

        verifyUpdateEntity(testValue, dtoUpdate);
    }

    @Test
    public void testDeleteEntity() {
        OneFieldEntityDto dto = new OneFieldEntityDto("testValue");
        MessageStatus testValue = createTestValue();
        Function<OneFieldEntityDto, MessageStatus> mockFunction = input -> testValue;
        when(transformationFunctionService.getTransformationFunction(OneFieldEntityDto.class, MessageStatus.class))
                .thenReturn(mockFunction);
        messageStatusService.deleteEntity(dto);

        verifyDeleteEntity(testValue, dto);
    }

    @Test
    public void testGetEntity() {
        OneFieldEntityDto dto = new OneFieldEntityDto("testValue");
        Parameter parameter = parameterFactory.createStringParameter(getFieldName(), dto.getValue());
        MessageStatus testValue = createTestValue();
        ResponseOneFieldEntityDto responseOneFieldEntityDto = getResponseOneFieldEntityDto();

        when(parameterFactory.createStringParameter(getFieldName(), dto.getValue())).thenReturn(parameter);
        when(dao.getEntity(parameter)).thenReturn(testValue);
        when(transformationFunctionService.getEntity(ResponseOneFieldEntityDto.class, testValue))
                .thenReturn(responseOneFieldEntityDto);

        ResponseOneFieldEntityDto result = messageStatusService.getEntity(dto);

        assertEquals(responseOneFieldEntityDto, result);
    }

    @Test
    public void testGetEntityOptional() {
        OneFieldEntityDto dto = new OneFieldEntityDto("testValue");
        Parameter parameter = parameterFactory.createStringParameter(getFieldName(), dto.getValue());
        MessageStatus testValue = createTestValue();
        ResponseOneFieldEntityDto responseOneFieldEntityDto = getResponseOneFieldEntityDto();

        when(parameterFactory.createStringParameter(getFieldName(), dto.getValue())).thenReturn(parameter);
        when(dao.getEntity(parameter)).thenReturn(testValue);
        when(transformationFunctionService.getEntity(ResponseOneFieldEntityDto.class, testValue))
                .thenReturn(responseOneFieldEntityDto);

        Optional<ResponseOneFieldEntityDto> result = messageStatusService.getEntityOptional(dto);

        assertEquals(Optional.of(responseOneFieldEntityDto), result);
    }

    @Test
    public void testGetEntities() {
        MessageStatus testValue = createTestValue();
        ResponseOneFieldEntityDto responseOneFieldEntityDto = getResponseOneFieldEntityDto();

        when(dao.getEntityList()).thenReturn(List.of(testValue));
        when(transformationFunctionService.getEntity(ResponseOneFieldEntityDto.class, testValue))
                .thenReturn(responseOneFieldEntityDto);

        List<ResponseOneFieldEntityDto> list = messageStatusService.getEntities();

        assertEquals(1, list.size());
        assertEquals(responseOneFieldEntityDto, list.get(0));
    }

    private MessageStatus createTestValue() {
        return MessageStatus.builder()
                .value("testValue")
                .build();
    }
}