package com.b2c.prototype.manager.option.base;

import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.manager.AbstractConstantEntityManagerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class OptionGroupManagerTest extends AbstractConstantEntityManagerTest<OptionGroup> {

    @Mock
    private Function<OptionGroup, ConstantPayloadDto> mapEntityToDtoFunction;
    @Mock
    private Function<ConstantPayloadDto, OptionGroup> mapDtoToEntityFunction;
    private OptionGroupManager optionGroupManager;

    @BeforeEach
    void setUp() {
//        when(transformationFunctionService.getTransformationFunction(ConstantPayloadDto.class, OptionGroup.class))
//                .thenReturn(mapDtoToEntityFunction);
//        when(transformationFunctionService.getTransformationFunction(OptionGroup.class, ConstantPayloadDto.class))
//                .thenReturn(mapEntityToDtoFunction);

        optionGroupManager = null;
    }

    @Test
    public void testPersistEntity() {
        ConstantPayloadDto dto = ConstantPayloadDto.builder()
                .label("testLabel")
                .value("testValue")
                .build();
        OptionGroup testValue = createTestValue();

        when(mapDtoToEntityFunction.apply(dto)).thenReturn(testValue);
//        when(dao.getEntityClass()).thenAnswer(invocation -> OptionGroup.class);

//        optionGroupManager.persistEntity(dto);

        verifySaveEntity(testValue);
    }

    @Test
    public void testMergeEntity() {
        ConstantPayloadDto newDto = ConstantPayloadDto.builder()
                .label("newLabel")
                .value("newValue")
                .build();

        OptionGroup testValue = OptionGroup.builder()
                .value("newValue")
                .build();

        when(mapDtoToEntityFunction.apply(newDto)).thenReturn(testValue);
//        when(dao.getEntityClass()).thenAnswer(invocation -> OptionGroup.class);

//        optionGroupManager.mergeEntity("testValue", newDto);

        verifyUpdateEntity(testValue, newDto.getValue());
    }

    @Test
    public void testRemoveEntity() {
        optionGroupManager.removeEntity("testValue");

        verifyDeleteEntity("testValue");
    }

    @Test
    public void testGetEntity() {
        
        OptionGroup testValue = createTestValue();
        ConstantPayloadDto constantPayloadDto = getResponseOneFieldEntityDto();

        when(mapEntityToDtoFunction.apply(testValue)).thenReturn(constantPayloadDto);
        
        //        when(dao.getNamedQueryEntity("", parameter)).thenReturn(testValue);

//        ConstantPayloadDto result = optionGroupManager.getEntity("testValue");

//        assertEquals(constantPayloadDto, result);
    }

    @Test
    public void testGetEntityOptional() {
        
        OptionGroup testValue = createTestValue();
        ConstantPayloadDto constantPayloadDto = getResponseOneFieldEntityDto();

        when(mapEntityToDtoFunction.apply(testValue)).thenReturn(constantPayloadDto);
        
        //        when(dao.getNamedQueryEntity("", parameter)).thenReturn(testValue);

//        Optional<ConstantPayloadDto> result = optionGroupManager.getEntityOptional("testValue");

//        assertEquals(Optional.of(constantPayloadDto), result);
    }

    @Test
    public void testGetEntities() {
        OptionGroup testValue = createTestValue();
        ConstantPayloadDto constantPayloadDto = getResponseOneFieldEntityDto();

        when(mapEntityToDtoFunction.apply(testValue)).thenReturn(constantPayloadDto);
//        when(dao.getEntityList()).thenReturn(List.of(testValue));

//        List<ConstantPayloadDto> list = optionGroupManager.getEntities();
//
//        assertEquals(1, list.size());
//        assertEquals(constantPayloadDto, list.get(0));
    }

    private OptionGroup createTestValue() {
        return OptionGroup.builder()
                .value("testValue")
                .build();
    }
}