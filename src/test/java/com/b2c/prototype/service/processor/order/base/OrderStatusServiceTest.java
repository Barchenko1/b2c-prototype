package com.b2c.prototype.service.processor.order.base;

import com.b2c.prototype.modal.dto.payload.ConstantEntityPayloadDto;
import com.b2c.prototype.modal.entity.order.OrderStatus;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.AbstractConstantEntityServiceTest;
import com.tm.core.processor.finder.parameter.Parameter;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class OrderStatusServiceTest extends AbstractConstantEntityServiceTest<OrderStatus> {
    @Mock
    private ITransformationFunctionService transformationFunctionService;
    @InjectMocks
    private OrderStatusService service;

    @Test
    public void testSaveEntity() {
        ConstantEntityPayloadDto dto = ConstantEntityPayloadDto.builder()
                .label("testLabel")
                .value("testValue")
                .build();
        OrderStatus testValue = createTestValue();
        when(dao.getEntityClass()).thenAnswer(invocation -> OrderStatus.class);
        when(transformationFunctionService.getEntity(OrderStatus.class, dto))
                .thenReturn(testValue);

        service.saveEntity(dto);

        verifySaveEntity(testValue);
    }

    @Test
    public void testUpdateEntity() {
        ConstantEntityPayloadDto newDto = ConstantEntityPayloadDto.builder()
                .label("newLabel")
                .value("newValue")
                .build();

        OrderStatus testValue = OrderStatus.builder()
                .value("newValue")
                .build();
        when(dao.getEntityClass()).thenAnswer(invocation -> OrderStatus.class);
        when(transformationFunctionService.getEntity(OrderStatus.class, newDto))
                .thenReturn(testValue);

        service.updateEntity("testValue", newDto);

        verifyUpdateEntity(testValue, newDto);
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
        ConstantEntityPayloadDto constantEntityPayloadDto = getResponseOneFieldEntityDto();

        when(parameterFactory.createStringParameter(VALUE, "testValue")).thenReturn(parameter);
        when(dao.getEntity(parameter)).thenReturn(testValue);
        when(transformationFunctionService.getEntity(ConstantEntityPayloadDto.class, testValue))
                .thenReturn(constantEntityPayloadDto);

        ConstantEntityPayloadDto result = service.getEntity("testValue");

        assertEquals(constantEntityPayloadDto, result);
    }

    @Test
    public void testGetEntityOptional() {
        Parameter parameter = parameterFactory.createStringParameter(VALUE, "testValue");
        OrderStatus testValue = createTestValue();
        ConstantEntityPayloadDto constantEntityPayloadDto = getResponseOneFieldEntityDto();

        when(parameterFactory.createStringParameter(VALUE, "testValue")).thenReturn(parameter);
        when(dao.getEntity(parameter)).thenReturn(testValue);
        when(transformationFunctionService.getEntity(ConstantEntityPayloadDto.class, testValue))
                .thenReturn(constantEntityPayloadDto);

        Optional<ConstantEntityPayloadDto> result = service.getEntityOptional("testValue");

        assertEquals(Optional.of(constantEntityPayloadDto), result);
    }

    @Test
    public void testGetAllEntity() {
        OrderStatus testValue = createTestValue();
        ConstantEntityPayloadDto constantEntityPayloadDto = getResponseOneFieldEntityDto();

        when(dao.getEntityList()).thenReturn(List.of(testValue));
        when(transformationFunctionService.getEntity(ConstantEntityPayloadDto.class, testValue))
                .thenReturn(constantEntityPayloadDto);

        List<ConstantEntityPayloadDto> list = service.getEntities();

        assertEquals(1, list.size());
        assertEquals(constantEntityPayloadDto, list.get(0));
    }

    private OrderStatus createTestValue() {
        return OrderStatus.builder()
                .value("testValue")
                .build();
    }
}