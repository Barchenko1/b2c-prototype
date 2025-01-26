package com.b2c.prototype.service.processor.order.base;

import com.b2c.prototype.modal.dto.payload.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.order.OrderStatus;
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

class OrderStatusServiceTest extends AbstractConstantEntityServiceTest<OrderStatus> {

    @Mock
    private Function<OrderStatus, ConstantPayloadDto> mapEntityToDtoFunction;
    @Mock
    private Function<ConstantPayloadDto, OrderStatus> mapDtoToEntityFunction;
    private OrderStatusService service;

    @BeforeEach
    void setUp() {
        when(transformationFunctionService.getTransformationFunction(ConstantPayloadDto.class, OrderStatus.class))
                .thenReturn(mapDtoToEntityFunction);
        when(transformationFunctionService.getTransformationFunction(OrderStatus.class, ConstantPayloadDto.class))
                .thenReturn(mapEntityToDtoFunction);

        service = new OrderStatusService(
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
        OrderStatus testValue = createTestValue();

        when(mapDtoToEntityFunction.apply(dto)).thenReturn(testValue);
        when(dao.getEntityClass()).thenAnswer(invocation -> OrderStatus.class);

        service.saveEntity(dto);

        verifySaveEntity(testValue);
    }

    @Test
    public void testUpdateEntity() {
        ConstantPayloadDto newDto = ConstantPayloadDto.builder()
                .label("newLabel")
                .value("newValue")
                .build();

        OrderStatus testValue = OrderStatus.builder()
                .value("newValue")
                .build();

        when(mapDtoToEntityFunction.apply(newDto)).thenReturn(testValue);
        when(dao.getEntityClass()).thenAnswer(invocation -> OrderStatus.class);

        service.updateEntity("testValue", newDto);

        verifyUpdateEntity(testValue, newDto.getValue());
    }

    @Test
    public void testDeleteEntity() {
        service.deleteEntity("testValue");

        verifyDeleteEntity("testValue");
    }

    @Test
    public void testGetEntity() {
        Parameter parameter = parameterFactory.createStringParameter(VALUE, "testValue");
        OrderStatus testValue = createTestValue();
        ConstantPayloadDto constantPayloadDto = getResponseOneFieldEntityDto();

        when(mapEntityToDtoFunction.apply(testValue)).thenReturn(constantPayloadDto);
        when(parameterFactory.createStringParameter(VALUE, "testValue")).thenReturn(parameter);
        when(dao.getEntity(parameter)).thenReturn(testValue);

        ConstantPayloadDto result = service.getEntity("testValue");

        assertEquals(constantPayloadDto, result);
    }

    @Test
    public void testGetEntityOptional() {
        Parameter parameter = parameterFactory.createStringParameter(VALUE, "testValue");
        OrderStatus testValue = createTestValue();
        ConstantPayloadDto constantPayloadDto = getResponseOneFieldEntityDto();

        when(mapEntityToDtoFunction.apply(testValue)).thenReturn(constantPayloadDto);
        when(parameterFactory.createStringParameter(VALUE, "testValue")).thenReturn(parameter);
        when(dao.getEntity(parameter)).thenReturn(testValue);

        Optional<ConstantPayloadDto> result = service.getEntityOptional("testValue");

        assertEquals(Optional.of(constantPayloadDto), result);
    }

    @Test
    public void testGetAllEntity() {
        OrderStatus testValue = createTestValue();
        ConstantPayloadDto constantPayloadDto = getResponseOneFieldEntityDto();

        when(mapEntityToDtoFunction.apply(testValue)).thenReturn(constantPayloadDto);
        when(dao.getEntityList()).thenReturn(List.of(testValue));

        List<ConstantPayloadDto> list = service.getEntities();

        assertEquals(1, list.size());
        assertEquals(constantPayloadDto, list.get(0));
    }

    private OrderStatus createTestValue() {
        return OrderStatus.builder()
                .value("testValue")
                .build();
    }
}