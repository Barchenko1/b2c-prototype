package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.manager.AbstractConstantEntityManagerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class BrandManagerTest extends AbstractConstantEntityManagerTest<Brand> {

    @Mock
    private Function<Brand, ConstantPayloadDto> mapEntityToDtoFunction;
    @Mock
    private Function<ConstantPayloadDto, Brand> mapDtoToEntityFunction;
    private BrandManager brandManager;

    @BeforeEach
    void setUp() {
//        when(transformationFunctionService.getTransformationFunction(ConstantPayloadDto.class, Brand.class))
//                .thenReturn(mapDtoToEntityFunction);
//        when(transformationFunctionService.getTransformationFunction(Brand.class, ConstantPayloadDto.class))
//                .thenReturn(mapEntityToDtoFunction);

        brandManager = null;
    }

    @Test
    public void testPersistEntity() {
        ConstantPayloadDto dto = ConstantPayloadDto.builder()
                .value("testLabel")
                .key("testValue")
                .build();
        Brand testValue = createTestValue();

        when(mapDtoToEntityFunction.apply(dto)).thenReturn(testValue);
//        when(dao.getEntityClass()).thenAnswer(invocation -> Brand.class);

//        brandManager.persistEntity(dto);

        verifySaveEntity(testValue);
    }

    @Test
    public void testMergeEntity() {
        ConstantPayloadDto newDto = ConstantPayloadDto.builder()
                .value("newLabel")
                .key("newValue")
                .build();

        Brand testValue = Brand.builder()
                .value("newValue")
                .build();

        when(mapDtoToEntityFunction.apply(newDto)).thenReturn(testValue);
//        when(dao.getEntityClass()).thenAnswer(invocation -> Brand.class);

//        brandManager.mergeEntity("testValue", newDto);

        verifyUpdateEntity(testValue, newDto.getKey());
    }

    @Test
    public void testRemoveEntity() {
        brandManager.removeEntity("testValue");

        verifyDeleteEntity("testValue");
    }

    @Test
    public void testGetEntity() {
        
        
        Brand testValue = createTestValue();
        ConstantPayloadDto constantPayloadDto = getResponseOneFieldEntityDto();

        when(mapEntityToDtoFunction.apply(testValue)).thenReturn(constantPayloadDto);
        
        //        when(dao.getNamedQueryEntity("", parameter)).thenReturn(testValue);

//        ConstantPayloadDto result = brandManager.getEntity("testValue");

//        assertEquals(constantPayloadDto, result);
    }

    @Test
    public void testGetEntityOptional() {
        
        
        Brand testValue = createTestValue();
        ConstantPayloadDto constantPayloadDto = getResponseOneFieldEntityDto();

        when(mapEntityToDtoFunction.apply(testValue)).thenReturn(constantPayloadDto);
        
        //        when(dao.getNamedQueryEntity("", parameter)).thenReturn(testValue);

//        Optional<ConstantPayloadDto> result = brandManager.getEntityOptional("testValue");

//        assertEquals(Optional.of(constantPayloadDto), result);
    }

    @Test
    public void testGetEntities() {
        Brand testValue = createTestValue();
        ConstantPayloadDto constantPayloadDto = getResponseOneFieldEntityDto();

        when(mapEntityToDtoFunction.apply(testValue)).thenReturn(constantPayloadDto);
//        when(dao.getEntityList()).thenReturn(List.of(testValue));

//        List<ConstantPayloadDto> list = brandManager.getEntities();

//        assertEquals(1, list.size());
//        assertEquals(constantPayloadDto, list.get(0));
    }

    private Brand createTestValue() {
        return Brand.builder()
                .value("testValue")
                .build();
    }
}