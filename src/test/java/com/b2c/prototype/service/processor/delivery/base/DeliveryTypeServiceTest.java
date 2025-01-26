package com.b2c.prototype.service.processor.delivery.base;

import com.b2c.prototype.modal.dto.payload.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.b2c.prototype.service.processor.AbstractConstantEntityServiceTest;
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

class DeliveryTypeServiceTest extends AbstractConstantEntityServiceTest<DeliveryType> {

    @Mock
    private Function<DeliveryType, ConstantPayloadDto> mapEntityToDtoFunction;
    @Mock
    private Function<ConstantPayloadDto, DeliveryType> mapDtoToEntityFunction;
    private DeliveryTypeService deliveryTypeService;

    @BeforeEach
    void setUp() {
        when(transformationFunctionService.getTransformationFunction(ConstantPayloadDto.class, DeliveryType.class))
                .thenReturn(mapDtoToEntityFunction);
        when(transformationFunctionService.getTransformationFunction(DeliveryType.class, ConstantPayloadDto.class))
                .thenReturn(mapEntityToDtoFunction);

        deliveryTypeService = new DeliveryTypeService(
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
        DeliveryType testValue = createTestValue();

        when(mapDtoToEntityFunction.apply(dto)).thenReturn(testValue);
        when(dao.getEntityClass()).thenAnswer(invocation -> DeliveryType.class);

        deliveryTypeService.saveEntity(dto);

        verifySaveEntity(testValue);
    }

    @Test
    public void testUpdateEntity() {
        ConstantPayloadDto newDto = ConstantPayloadDto.builder()
                .label("newLabel")
                .value("newValue")
                .build();

        DeliveryType testValue = DeliveryType.builder()
                .value("newValue")
                .build();

        when(mapDtoToEntityFunction.apply(newDto)).thenReturn(testValue);
        when(dao.getEntityClass()).thenAnswer(invocation -> DeliveryType.class);

        deliveryTypeService.updateEntity("testValue", newDto);

        verifyUpdateEntity(testValue, newDto.getValue());
    }

    @Test
    public void testDeleteEntity() {
        
        deliveryTypeService.deleteEntity("testValue");
        verifyDeleteEntity("testValue");
    }

    @Test
    public void testGetEntity() {
        Parameter parameter = parameterFactory.createStringParameter(VALUE, "testValue");
        DeliveryType testValue = createTestValue();
        ConstantPayloadDto constantPayloadDto = getResponseOneFieldEntityDto();

        when(mapEntityToDtoFunction.apply(testValue)).thenReturn(constantPayloadDto);
        when(parameterFactory.createStringParameter(VALUE, "testValue")).thenReturn(parameter);
        when(dao.getEntity(parameter)).thenReturn(testValue);

        ConstantPayloadDto result = deliveryTypeService.getEntity("testValue");

        assertEquals(constantPayloadDto, result);
    }

    @Test
    public void testGetEntityOptional() {
        
        Parameter parameter = parameterFactory.createStringParameter(VALUE, "testValue");
        DeliveryType testValue = createTestValue();
        ConstantPayloadDto constantPayloadDto = getResponseOneFieldEntityDto();

        when(mapEntityToDtoFunction.apply(testValue)).thenReturn(constantPayloadDto);
        when(parameterFactory.createStringParameter(VALUE, "testValue")).thenReturn(parameter);
        when(dao.getEntity(parameter)).thenReturn(testValue);

        Optional<ConstantPayloadDto> result = deliveryTypeService.getEntityOptional("testValue");

        assertEquals(Optional.of(constantPayloadDto), result);
    }

    @Test
    public void testGetEntities() {
        DeliveryType testValue = createTestValue();
        ConstantPayloadDto constantPayloadDto = getResponseOneFieldEntityDto();

        when(mapEntityToDtoFunction.apply(testValue)).thenReturn(constantPayloadDto);
        when(dao.getEntityList()).thenReturn(List.of(testValue));

        List<ConstantPayloadDto> list = deliveryTypeService.getEntities();

        assertEquals(1, list.size());
        assertEquals(constantPayloadDto, list.get(0));
    }

    private DeliveryType createTestValue() {
        return DeliveryType.builder()
                .value("testValue")
                .build();
    }
}