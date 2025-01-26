package com.b2c.prototype.service.processor.message;

import com.b2c.prototype.modal.dto.payload.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.message.MessageType;
import com.b2c.prototype.service.processor.AbstractConstantEntityServiceTest;
import com.b2c.prototype.service.processor.message.base.MessageTypeService;
import com.tm.core.processor.finder.parameter.Parameter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static com.b2c.prototype.util.Constant.VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class MessageTypeServiceTest extends AbstractConstantEntityServiceTest<MessageType> {
    @Mock
    private Function<MessageType, ConstantPayloadDto> mapEntityToDtoFunction;
    @Mock
    private Function<ConstantPayloadDto, MessageType> mapDtoToEntityFunction;
    private MessageTypeService messageTypeService;

    @BeforeEach
    void setUp() {
        when(transformationFunctionService.getTransformationFunction(ConstantPayloadDto.class, MessageType.class))
                .thenReturn(mapDtoToEntityFunction);
        when(transformationFunctionService.getTransformationFunction(MessageType.class, ConstantPayloadDto.class))
                .thenReturn(mapEntityToDtoFunction);

        messageTypeService = new MessageTypeService(
                parameterFactory,
                dao,
                transformationFunctionService,
                singleValueMap
        );
    }

    @Test
    public void testSaveEntity() {
        ConstantPayloadDto dto = ConstantPayloadDto.builder()
                .label("testLabel")
                .value("testValue")
                .build();
        MessageType testValue = createTestValue();

        when(mapDtoToEntityFunction.apply(dto)).thenReturn(testValue);
        when(dao.getEntityClass()).thenAnswer(invocation -> MessageType.class);

        messageTypeService.saveEntity(dto);

        verifySaveEntity(testValue);
    }

    @Test
    public void testUpdateEntity() {
        ConstantPayloadDto newDto = ConstantPayloadDto.builder()
                .label("newLabel")
                .value("newValue")
                .build();

        MessageType testValue = MessageType.builder()
                .value("newValue")
                .build();

        when(mapDtoToEntityFunction.apply(newDto)).thenReturn(testValue);
        when(dao.getEntityClass()).thenAnswer(invocation -> MessageType.class);

        messageTypeService.updateEntity("testValue", newDto);

        verifyUpdateEntity(testValue, newDto.getValue());
    }

    @Test
    public void testDeleteEntity() {
        

        messageTypeService.deleteEntity("testValue");

        verifyDeleteEntity("testValue");
    }

    @Test
    public void testGetEntity() {
        
        Parameter parameter = parameterFactory.createStringParameter(VALUE, "testValue");
        MessageType testValue = createTestValue();
        ConstantPayloadDto constantPayloadDto = getResponseOneFieldEntityDto();

        when(mapEntityToDtoFunction.apply(testValue)).thenReturn(constantPayloadDto);
        when(parameterFactory.createStringParameter(VALUE, "testValue")).thenReturn(parameter);
        when(dao.getEntity(parameter)).thenReturn(testValue);

        ConstantPayloadDto result = messageTypeService.getEntity("testValue");

        assertEquals(constantPayloadDto, result);
    }

    @Test
    public void testGetEntityOptional() {
        
        Parameter parameter = parameterFactory.createStringParameter(VALUE, "testValue");
        MessageType testValue = createTestValue();
        ConstantPayloadDto constantPayloadDto = getResponseOneFieldEntityDto();

        when(mapEntityToDtoFunction.apply(testValue)).thenReturn(constantPayloadDto);
        when(parameterFactory.createStringParameter(VALUE, "testValue")).thenReturn(parameter);
        when(dao.getEntity(parameter)).thenReturn(testValue);

        Optional<ConstantPayloadDto> result = messageTypeService.getEntityOptional("testValue");

        assertEquals(Optional.of(constantPayloadDto), result);
    }

    @Test
    public void testGetEntities() {
        MessageType testValue = createTestValue();
        ConstantPayloadDto constantPayloadDto = getResponseOneFieldEntityDto();

        when(mapEntityToDtoFunction.apply(testValue)).thenReturn(constantPayloadDto);
        when(dao.getEntityList()).thenReturn(List.of(testValue));

        List<ConstantPayloadDto> list = messageTypeService.getEntities();

        assertEquals(1, list.size());
        assertEquals(constantPayloadDto, list.get(0));
    }

    private MessageType createTestValue() {
        return MessageType.builder()
                .value("testValue")
                .build();
    }
}