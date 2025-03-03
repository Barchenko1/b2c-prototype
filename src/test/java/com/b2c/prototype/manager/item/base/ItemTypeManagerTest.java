package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.item.ItemType;
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

class ItemTypeManagerTest extends AbstractConstantEntityManagerTest<ItemType> {

    @Mock
    private Function<ItemType, ConstantPayloadDto> mapEntityToDtoFunction;
    @Mock
    private Function<ConstantPayloadDto, ItemType> mapDtoToEntityFunction;
    private ItemTypeManager itemTypeManager;

    @BeforeEach
    void setUp() {
        when(transformationFunctionService.getTransformationFunction(ConstantPayloadDto.class, ItemType.class))
                .thenReturn(mapDtoToEntityFunction);
        when(transformationFunctionService.getTransformationFunction(ItemType.class, ConstantPayloadDto.class))
                .thenReturn(mapEntityToDtoFunction);

        itemTypeManager = new ItemTypeManager(
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
        ItemType testValue = createTestValue();

        when(mapDtoToEntityFunction.apply(dto)).thenReturn(testValue);
        when(dao.getEntityClass()).thenAnswer(invocation -> ItemType.class);

        itemTypeManager.saveEntity(dto);

        verifySaveEntity(testValue);
    }

    @Test
    public void testUpdateEntity() {
        ConstantPayloadDto newDto = ConstantPayloadDto.builder()
                .label("newLabel")
                .value("newValue")
                .build();

        ItemType testValue = ItemType.builder()
                .value("newValue")
                .build();

        when(mapDtoToEntityFunction.apply(newDto)).thenReturn(testValue);
        when(dao.getEntityClass()).thenAnswer(invocation -> ItemType.class);

        itemTypeManager.updateEntity("testValue", newDto);

        verifyUpdateEntity(testValue, newDto.getValue());
    }

    @Test
    public void testDeleteEntity() {
        itemTypeManager.deleteEntity("testValue");

        verifyDeleteEntity("testValue");
    }

    @Test
    public void testGetEntity() {
        Parameter parameter = parameterFactory.createStringParameter(VALUE, "testValue");
        ItemType testValue = createTestValue();
        ConstantPayloadDto constantPayloadDto = getResponseOneFieldEntityDto();

        when(mapEntityToDtoFunction.apply(testValue)).thenReturn(constantPayloadDto);
        when(parameterFactory.createStringParameter(VALUE, "testValue")).thenReturn(parameter);
        //        when(dao.getNamedQueryEntity("", parameter)).thenReturn(testValue);

        ConstantPayloadDto result = itemTypeManager.getEntity("testValue");

        assertEquals(constantPayloadDto, result);
    }

    @Test
    public void testGetEntityOptional() {
        Parameter parameter = parameterFactory.createStringParameter(VALUE, "testValue");
        ItemType testValue = createTestValue();
        ConstantPayloadDto constantPayloadDto = getResponseOneFieldEntityDto();

        when(mapEntityToDtoFunction.apply(testValue)).thenReturn(constantPayloadDto);
        when(parameterFactory.createStringParameter(VALUE, "testValue")).thenReturn(parameter);
        //        when(dao.getNamedQueryEntity("", parameter)).thenReturn(testValue);

        Optional<ConstantPayloadDto> result = itemTypeManager.getEntityOptional("testValue");

        assertEquals(Optional.of(constantPayloadDto), result);
    }

    @Test
    public void testGetEntities() {
        ItemType testValue = createTestValue();
        ConstantPayloadDto constantPayloadDto = getResponseOneFieldEntityDto();

        when(mapEntityToDtoFunction.apply(testValue)).thenReturn(constantPayloadDto);
//        when(dao.getEntityList()).thenReturn(List.of(testValue));

        List<ConstantPayloadDto> list = itemTypeManager.getEntities();

        assertEquals(1, list.size());
        assertEquals(constantPayloadDto, list.get(0));
    }

    private ItemType createTestValue() {
        return ItemType.builder()
                .value("testValue")
                .build();
    }
}