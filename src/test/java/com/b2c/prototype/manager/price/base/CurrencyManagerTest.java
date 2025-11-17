package com.b2c.prototype.manager.price.base;

import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.manager.AbstractConstantEntityManagerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class CurrencyManagerTest extends AbstractConstantEntityManagerTest<Currency> {

    @Mock
    private Function<Currency, ConstantPayloadDto> mapEntityToDtoFunction;
    @Mock
    private Function<ConstantPayloadDto, Currency> mapDtoToEntityFunction;
    private CurrencyManager currencyManager;

    @BeforeEach
    void setUp() {
//        when(transformationFunctionService.getTransformationFunction(ConstantPayloadDto.class, Currency.class))
//                .thenReturn(mapDtoToEntityFunction);
//        when(transformationFunctionService.getTransformationFunction(Currency.class, ConstantPayloadDto.class))
//                .thenReturn(mapEntityToDtoFunction);

        currencyManager = null;
    }

    @Test
    public void testPersistEntity() {
        ConstantPayloadDto dto = ConstantPayloadDto.builder()
                .value("testLabel")
                .key("testValue")
                .build();
        Currency testValue = createTestValue();

        when(mapDtoToEntityFunction.apply(dto)).thenReturn(testValue);
//        when(dao.getEntityClass()).thenAnswer(invocation -> Currency.class);

//        currencyManager.persistEntity(modal);

//        verifySaveEntity(testValue);
    }

    @Test
    public void testMergeEntity() {
        ConstantPayloadDto newDto = ConstantPayloadDto.builder()
                .value("newLabel")
                .key("newValue")
                .build();

        Currency testValue = Currency.builder()
                .value("newValue")
                .build();

        when(mapDtoToEntityFunction.apply(newDto)).thenReturn(testValue);
//        when(dao.getEntityClass()).thenAnswer(invocation -> Currency.class);

//        currencyManager.mergeEntity("testValue", newDto);

//        verifyUpdateEntity(testValue, newDto.getValue());
    }

    @Test
    public void testRemoveEntity() {
        currencyManager.removeEntity("testValue");

        verifyDeleteEntity("testValue");
    }

    @Test
    public void testGetEntity() {
        
        Currency testValue = createTestValue();
        ConstantPayloadDto constantPayloadDto = getResponseOneFieldEntityDto();

        when(mapEntityToDtoFunction.apply(testValue)).thenReturn(constantPayloadDto);
        
        //        when(dao.getNamedQueryEntity("", parameter)).thenReturn(testValue);

//        ConstantPayloadDto result = currencyManager.getEntity("testValue");

//        assertEquals(constantPayloadDto, result);
    }

    @Test
    public void testGetEntityOptional() {
        
        Currency testValue = createTestValue();
        ConstantPayloadDto constantPayloadDto = getResponseOneFieldEntityDto();

        when(mapEntityToDtoFunction.apply(testValue)).thenReturn(constantPayloadDto);
        
        //        when(dao.getNamedQueryEntity("", parameter)).thenReturn(testValue);

//        Optional<ConstantPayloadDto> result = currencyManager.getEntityOptional("testValue");

//        assertEquals(Optional.of(constantPayloadDto), result);
    }

    @Test
    public void testGetAllEntity() {
        Currency testValue = createTestValue();
        ConstantPayloadDto constantPayloadDto = getResponseOneFieldEntityDto();

        when(mapEntityToDtoFunction.apply(testValue)).thenReturn(constantPayloadDto);
//        when(dao.getEntityList()).thenReturn(List.of(testValue));

//        List<ConstantPayloadDto> list = currencyManager.getEntities();
//
//        assertEquals(1, list.size());
//        assertEquals(constantPayloadDto, list.get(0));
    }

    private Currency createTestValue() {
        return Currency.builder()
                .value("testValue")
                .build();
    }
}