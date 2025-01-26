package com.b2c.prototype.service.manager.option.base;

import com.b2c.prototype.dao.cashed.ISingleValueMap;
import com.b2c.prototype.dao.option.IOptionItemDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.delete.MultipleFieldsSearchDtoDelete;
import com.b2c.prototype.modal.dto.payload.OptionItemDto;
import com.b2c.prototype.modal.dto.payload.SingleOptionItem;
import com.b2c.prototype.modal.dto.searchfield.OptionItemSearchFieldEntityDto;
import com.b2c.prototype.modal.entity.item.ItemDataOption;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.query.IQueryService;
import com.b2c.prototype.service.supplier.ISupplierService;
import com.tm.core.processor.finder.parameter.Parameter;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.b2c.prototype.util.Constant.ARTICULAR_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OptionItemManagerTest {

    @Mock
    private IOptionItemDao optionItemDao;
    @Mock
    private IQueryService queryService;
    @Mock
    private ITransformationFunctionService transformationFunctionService;
    @Mock
    private ISupplierService supplierService;
    @Mock
    private ISingleValueMap singleValueMap;
    @InjectMocks
    private OptionItemManager optionItemManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUpdateOptionItemSetByArticularId() {
        OptionItemSearchFieldEntityDto dtoUpdate = OptionItemSearchFieldEntityDto.builder()
                .searchField("searchField")
                .newEntity(getSingleOptionItem())
                .build();
        OptionItem optionItem = getOptionItem();
        ItemDataOption itemDataOption = mock(ItemDataOption.class);
        when(itemDataOption.getOptionItem()).thenReturn(optionItem);

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        when(transformationFunctionService.getEntity(OptionItem.class, dtoUpdate.getNewEntity()))
                .thenReturn(optionItem);
        when(supplierService.parameterStringSupplier(ARTICULAR_ID, dtoUpdate.getSearchField()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(ItemDataOption.class, parameterSupplier))
                .thenReturn(itemDataOption);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(itemDataOption);
            return null;
        }).when(optionItemDao).executeConsumer(any(Consumer.class));

        optionItemManager.saveUpdateOptionItemSetByArticularId(dtoUpdate);

        verify(singleValueMap).putEntity(OptionGroup.class, "value", optionItem.getOptionGroup());
        verify(singleValueMap).putEntity(OptionItem.class, "optionName", optionItem.getOptionName());
    }

    @Test
    void testSaveUpdateOptionItemSetByOptionGroupName() {
        OptionItemDto optionItemDto = getOptionItemDto();
        OptionItem newOptionItem = mock(OptionItem.class);

        when(transformationFunctionService.getEntityCollection(OptionItem.class, optionItemDto))
                .thenReturn(Set.of(newOptionItem));
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(newOptionItem);
            return null;
        }).when(optionItemDao).executeConsumer(any(Consumer.class));

        optionItemManager.saveUpdateOptionItemSetByOptionGroupName(optionItemDto);

        verify(singleValueMap).putEntity(OptionGroup.class, "value", newOptionItem.getOptionGroup());
        verify(singleValueMap).putEntity(OptionItem.class, "optionName", newOptionItem);
    }

    @Test
    void testDeleteOptionItemByArticularId() {
        OneFieldEntityDto dto = new OneFieldEntityDto("123");

        OptionItem optionItem = getOptionItem();
        Supplier<OptionItem> optionItemSupplier = () -> optionItem;
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        when(supplierService.parameterStringSupplier(ARTICULAR_ID, dto.getValue()))
                .thenReturn(parameterSupplier);
        Function<ItemDataOption, OptionItem> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(ItemDataOption.class, OptionItem.class))
                .thenReturn(function);
        when(supplierService.entityFieldSupplier(
                ItemDataOption.class,
                parameterSupplier,
                function
        ))
                .thenReturn(optionItemSupplier);
        optionItemManager.deleteOptionItemByArticularId(dto);

        verify(optionItemDao).deleteEntity(optionItemSupplier);
    }

    @Test
    void testDeleteOptionItemByOptionGroupName() {
        Session session = mock(Session.class);
        MultipleFieldsSearchDtoDelete multipleFieldsSearchDtoDelete = MultipleFieldsSearchDtoDelete.builder()
                .mainSearchField("mainSearchField")
                .innerSearchField("innerSearchField")
                .build();
        OptionItem optionItem = getOptionItem();

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        NativeQuery<OptionItem> query = mock(NativeQuery.class);

        when(session.createNativeQuery(anyString(), eq(OptionItem.class)))
                .thenReturn(query);
        when(supplierService.parameterStringSupplier("value", multipleFieldsSearchDtoDelete.getMainSearchField()))
                .thenReturn(parameterSupplier);
        when(supplierService.parameterStringSupplier("optionName", multipleFieldsSearchDtoDelete.getInnerSearchField()))
                .thenReturn(parameterSupplier);
        when(queryService.getQueryEntityParameterArray(eq(query), any(Supplier.class)))
                .thenReturn(optionItem);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            consumer.accept(session);
            verify(session).remove(optionItem);
            return null;
        }).when(optionItemDao).executeConsumer(any(Consumer.class));

        optionItemManager.deleteOptionItemByOptionGroupName(multipleFieldsSearchDtoDelete);

        verify(optionItemDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testGetOptionItemByItemArticularId() {
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto("Size");
        ItemDataOption itemDataOption = mock(ItemDataOption.class);
        OptionItemDto optionItemDto = getOptionItemDto();

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        when(supplierService.parameterStringSupplier(ARTICULAR_ID, oneFieldEntityDto.getValue()))
                .thenReturn(parameterSupplier);
        Function<ItemDataOption, OptionItemDto> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(ItemDataOption.class, OptionItemDto.class))
                .thenReturn(function);
        when(function.apply(itemDataOption)).thenReturn(optionItemDto);
        when(queryService.getEntityDto(ItemDataOption.class, parameterSupplier, function))
                .thenReturn(optionItemDto);

        OptionItemDto result = optionItemManager.getOptionItemByItemArticularId(oneFieldEntityDto);

        assertEquals(optionItemDto, result);
    }

    @Test
    void testGetOptionItemListByOptionGroup() {
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto("Size");
        OptionItem optionItem = getOptionItem();
        OptionItemDto optionItemDto = getOptionItemDto();

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        when(supplierService.parameterStringSupplier("value", oneFieldEntityDto.getValue()))
                .thenReturn(parameterSupplier);
        when(queryService.getSubEntityList(OptionItem.class, parameterSupplier))
                .thenReturn(List.of(optionItem));
        Function<OptionItem, OptionItemDto> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(OptionItem.class, OptionItemDto.class))
                .thenReturn(function);
        when(function.apply(optionItem)).thenReturn(optionItemDto);
        List<OptionItemDto> result = optionItemManager.getOptionItemListByOptionGroup(oneFieldEntityDto);

        assertEquals(1, result.size());
        assertEquals(optionItemDto, result.get(0));
    }

    @Test
    void testGetOptionItemList() {
        OptionItemDto dto = mock(OptionItemDto.class);
        Function<OptionItem, OptionItemDto> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(OptionItem.class, OptionItemDto.class, "set"))
                .thenReturn(function);
        when(queryService.getEntityDtoList(OptionItem.class, function)).thenReturn(List.of(dto));

        List<OptionItemDto> result = optionItemManager.getOptionItemList();

        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }

    private OptionGroup getOptionGroup() {
        return OptionGroup.builder()
                .id(1L)
                .value("Size")
                .build();
    }

    private OptionItem getOptionItem() {
        return OptionItem.builder()
                .id(1L)
                .optionName("XL")
                .optionGroup(getOptionGroup())
                .build();
    }

    private SingleOptionItem getSingleOptionItem() {
        return SingleOptionItem.builder()
                .optionItemValue("L")
                .optionGroupValue("Size")
                .build();
    }

    private OptionItemDto getOptionItemDto() {
        return OptionItemDto.builder()
                .optionGroupOptionItemsMap(new HashMap<>() {{
                    put("Size", Set.of("L", "XL", "XXL"));
                }})
                .build();
    }
}
