package com.b2c.prototype.manager.delivery.base;

import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.b2c.prototype.manager.AbstractConstantEntityManagerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class DeliveryTypeManagerTest extends AbstractConstantEntityManagerTest<DeliveryType> {

    @Mock
    private Function<DeliveryType, ConstantPayloadDto> mapEntityToDtoFunction;
    @Mock
    private Function<ConstantPayloadDto, DeliveryType> mapDtoToEntityFunction;
    private DeliveryTypeManager deliveryTypeManager;

    @BeforeEach
    void setUp() {
//        when(transformationFunctionService.getTransformationFunction(ConstantPayloadDto.class, DeliveryType.class))
//                .thenReturn(mapDtoToEntityFunction);
//        when(transformationFunctionService.getTransformationFunction(DeliveryType.class, ConstantPayloadDto.class))
//                .thenReturn(mapEntityToDtoFunction);

        deliveryTypeManager = null;
    }

    @Test
    public void testPersistEntity() {
        ConstantPayloadDto dto = ConstantPayloadDto.builder()
                .value("testLabel")
                .key("testValue")
                .build();
        DeliveryType testValue = createTestValue();

        when(mapDtoToEntityFunction.apply(dto)).thenReturn(testValue);
//        when(dao.getEntityClass()).thenAnswer(invocation -> DeliveryType.class);

//        deliveryTypeManager.persistEntity(modal);
//
//        verifySaveEntity(testValue);
    }

    @Test
    public void testMergeEntity() {
        ConstantPayloadDto newDto = ConstantPayloadDto.builder()
                .value("newLabel")
                .key("newValue")
                .build();

        DeliveryType testValue = DeliveryType.builder()
                .value("newValue")
                .build();

        when(mapDtoToEntityFunction.apply(newDto)).thenReturn(testValue);
//        when(dao.getEntityClass()).thenAnswer(invocation -> DeliveryType.class);

//        deliveryTypeManager.mergeEntity("testValue", newDto);
//
//        verifyUpdateEntity(testValue, newDto.getValue());
    }

    @Test
    public void testRemoveEntity() {
        
        deliveryTypeManager.removeEntity("testValue");
        verifyDeleteEntity("testValue");
    }

    @Test
    public void testGetEntity() {
        
        DeliveryType testValue = createTestValue();
        ConstantPayloadDto constantPayloadDto = getResponseOneFieldEntityDto();

        when(mapEntityToDtoFunction.apply(testValue)).thenReturn(constantPayloadDto);
        
        //        when(dao.getNamedQueryEntity("", parameter)).thenReturn(testValue);

//        ConstantPayloadDto result = deliveryTypeManager.getEntity("testValue");

//        assertEquals(constantPayloadDto, result);
    }

    @Test
    public void testGetEntityOptional() {
        
        
        DeliveryType testValue = createTestValue();
        ConstantPayloadDto constantPayloadDto = getResponseOneFieldEntityDto();

        when(mapEntityToDtoFunction.apply(testValue)).thenReturn(constantPayloadDto);
        
        //        when(dao.getNamedQueryEntity("", parameter)).thenReturn(testValue);

//        Optional<ConstantPayloadDto> result = deliveryTypeManager.getEntityOptional("testValue");

//        assertEquals(Optional.of(constantPayloadDto), result);
    }

    @Test
    public void testGetEntities() {
        DeliveryType testValue = createTestValue();
        ConstantPayloadDto constantPayloadDto = getResponseOneFieldEntityDto();

        when(mapEntityToDtoFunction.apply(testValue)).thenReturn(constantPayloadDto);
//        when(dao.getEntityList()).thenReturn(List.of(testValue));

//        List<ConstantPayloadDto> list = deliveryTypeManager.getEntities();
//
//        assertEquals(1, list.size());
//        assertEquals(constantPayloadDto, list.get(0));
    }

    private DeliveryType createTestValue() {
        return DeliveryType.builder()
                .value("testValue")
                .build();
    }
}