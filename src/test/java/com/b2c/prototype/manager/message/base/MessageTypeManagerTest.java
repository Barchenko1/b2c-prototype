package com.b2c.prototype.manager.message.base;

import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.message.MessageType;
import com.b2c.prototype.manager.AbstractConstantEntityManagerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class MessageTypeManagerTest extends AbstractConstantEntityManagerTest<MessageType> {
    @Mock
    private Function<MessageType, ConstantPayloadDto> mapEntityToDtoFunction;
    @Mock
    private Function<ConstantPayloadDto, MessageType> mapDtoToEntityFunction;
    private MessageTypeManager messageTypeManager;

    @BeforeEach
    void setUp() {
//        when(transformationFunctionService.getTransformationFunction(ConstantPayloadDto.class, MessageType.class))
//                .thenReturn(mapDtoToEntityFunction);
//        when(transformationFunctionService.getTransformationFunction(MessageType.class, ConstantPayloadDto.class))
//                .thenReturn(mapEntityToDtoFunction);

        messageTypeManager =null;
    }

    @Test
    public void testPersistEntity() {
        ConstantPayloadDto dto = ConstantPayloadDto.builder()
                .value("testLabel")
                .key("testValue")
                .build();
        MessageType testValue = createTestValue();

        when(mapDtoToEntityFunction.apply(dto)).thenReturn(testValue);
//        when(dao.getEntityClass()).thenAnswer(invocation -> MessageType.class);

//        messageTypeManager.persistEntity(dto);

        verifySaveEntity(testValue);
    }

    @Test
    public void testMergeEntity() {
        ConstantPayloadDto newDto = ConstantPayloadDto.builder()
                .value("newLabel")
                .key("newValue")
                .build();

        MessageType testValue = MessageType.builder()
                .value("newValue")
                .build();

        when(mapDtoToEntityFunction.apply(newDto)).thenReturn(testValue);
//        when(dao.getEntityClass()).thenAnswer(invocation -> MessageType.class);

//        messageTypeManager.mergeEntity("testValue", newDto);

        verifyUpdateEntity(testValue, newDto.getKey());
    }

    @Test
    public void testRemoveEntity() {
        

        messageTypeManager.removeEntity("testValue");

        verifyDeleteEntity("testValue");
    }

    @Test
    public void testGetEntity() {
        
        
        MessageType testValue = createTestValue();
        ConstantPayloadDto constantPayloadDto = getResponseOneFieldEntityDto();

        when(mapEntityToDtoFunction.apply(testValue)).thenReturn(constantPayloadDto);
        
        //        when(dao.getNamedQueryEntity("", parameter)).thenReturn(testValue);

//        ConstantPayloadDto result = messageTypeManager.getEntity("testValue");

//        assertEquals(constantPayloadDto, result);
    }

    @Test
    public void testGetEntityOptional() {
        
        
        MessageType testValue = createTestValue();
        ConstantPayloadDto constantPayloadDto = getResponseOneFieldEntityDto();

        when(mapEntityToDtoFunction.apply(testValue)).thenReturn(constantPayloadDto);
        
        //        when(dao.getNamedQueryEntity("", parameter)).thenReturn(testValue);

//        Optional<ConstantPayloadDto> result = messageTypeManager.getEntityOptional("testValue");

//        assertEquals(Optional.of(constantPayloadDto), result);
    }

    @Test
    public void testGetEntities() {
        MessageType testValue = createTestValue();
        ConstantPayloadDto constantPayloadDto = getResponseOneFieldEntityDto();

        when(mapEntityToDtoFunction.apply(testValue)).thenReturn(constantPayloadDto);
//        when(dao.getEntityList()).thenReturn(List.of(testValue));

//        List<ConstantPayloadDto> list = messageTypeManager.getEntities();

//        assertEquals(1, list.size());
//        assertEquals(constantPayloadDto, list.get(0));
    }

    private MessageType createTestValue() {
        return MessageType.builder()
                .value("testValue")
                .build();
    }
}