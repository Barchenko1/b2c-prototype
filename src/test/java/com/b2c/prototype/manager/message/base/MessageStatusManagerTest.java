package com.b2c.prototype.manager.message.base;

import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.message.MessageStatus;
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
import static org.mockito.Mockito.when;

class MessageStatusManagerTest extends AbstractConstantEntityManagerTest<MessageStatus> {

    @Mock
    private Function<MessageStatus, ConstantPayloadDto> mapEntityToDtoFunction;
    @Mock
    private Function<ConstantPayloadDto, MessageStatus> mapDtoToEntityFunction;
    private MessageStatusManager messageStatusManager;

    @BeforeEach
    void setUp() {
        when(transformationFunctionService.getTransformationFunction(ConstantPayloadDto.class, MessageStatus.class))
                .thenReturn(mapDtoToEntityFunction);
        when(transformationFunctionService.getTransformationFunction(MessageStatus.class, ConstantPayloadDto.class))
                .thenReturn(mapEntityToDtoFunction);

        messageStatusManager = new MessageStatusManager(
                parameterFactory,
                dao,
                transformationFunctionService);
    }

    @Test
    public void testSaveEntity() {
        ConstantPayloadDto dto = ConstantPayloadDto.builder()
                .label("testLabel")
                .value("testValue")
                .build();
        MessageStatus testValue = createTestValue();
        when(mapDtoToEntityFunction.apply(dto)).thenReturn(testValue);
        when(dao.getEntityClass()).thenAnswer(invocation -> MessageStatus.class);

        messageStatusManager.saveEntity(dto);

        verifySaveEntity(testValue);
    }

    @Test
    public void testUpdateEntity() {
        ConstantPayloadDto newDto = ConstantPayloadDto.builder()
                .label("newLabel")
                .value("newValue")
                .build();

        MessageStatus testValue = MessageStatus.builder()
                .value("newValue")
                .build();
        when(mapDtoToEntityFunction.apply(newDto)).thenReturn(testValue);
        when(dao.getEntityClass()).thenAnswer(invocation -> MessageStatus.class);

        messageStatusManager.updateEntity("testValue", newDto);

        verifyUpdateEntity(testValue, newDto.getValue());
    }

    @Test
    public void testDeleteEntity() {
        messageStatusManager.deleteEntity("testValue");

        verifyDeleteEntity("testValue");
    }

    @Test
    public void testGetEntity() {
        
        Parameter parameter = parameterFactory.createStringParameter(VALUE, "testValue");
        MessageStatus testValue = createTestValue();
        ConstantPayloadDto constantPayloadDto = getResponseOneFieldEntityDto();

        when(mapEntityToDtoFunction.apply(testValue)).thenReturn(constantPayloadDto);
        when(parameterFactory.createStringParameter(VALUE, "testValue")).thenReturn(parameter);
        //        when(dao.getNamedQueryEntity("", parameter)).thenReturn(testValue);

        ConstantPayloadDto result = messageStatusManager.getEntity("testValue");

        assertEquals(constantPayloadDto, result);
    }

    @Test
    public void testGetEntityOptional() {
        
        Parameter parameter = parameterFactory.createStringParameter(VALUE, "testValue");
        MessageStatus testValue = createTestValue();
        ConstantPayloadDto constantPayloadDto = getResponseOneFieldEntityDto();

        when(mapEntityToDtoFunction.apply(testValue)).thenReturn(constantPayloadDto);
        when(parameterFactory.createStringParameter(VALUE, "testValue")).thenReturn(parameter);
        //        when(dao.getNamedQueryEntity("", parameter)).thenReturn(testValue);

        Optional<ConstantPayloadDto> result = messageStatusManager.getEntityOptional("testValue");

        assertEquals(Optional.of(constantPayloadDto), result);
    }

    @Test
    public void testGetEntities() {
        MessageStatus testValue = createTestValue();
        ConstantPayloadDto constantPayloadDto = getResponseOneFieldEntityDto();

        when(mapEntityToDtoFunction.apply(testValue)).thenReturn(constantPayloadDto);
//        when(dao.getEntityList()).thenReturn(List.of(testValue));

        List<ConstantPayloadDto> list = messageStatusManager.getEntities();

        assertEquals(1, list.size());
        assertEquals(constantPayloadDto, list.get(0));
    }

    private MessageStatus createTestValue() {
        return MessageStatus.builder()
                .value("testValue")
                .build();
    }
}