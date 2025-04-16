package com.b2c.prototype.manager.option.base;

import com.b2c.prototype.modal.dto.payload.option.OptionGroupOptionItemSetDto;
import com.b2c.prototype.modal.entity.item.ArticularItem;

import com.b2c.prototype.dao.option.IOptionItemDao;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.service.function.ITransformationFunctionService;

import com.tm.core.finder.parameter.Parameter;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
    private ITransformationFunctionService transformationFunctionService;
    @InjectMocks
    private OptionItemManager optionItemManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUpdateOptionItemByArticularId() {
        String articularId = "searchField";
        OptionGroupOptionItemSetDto optionGroupOptionItemSetDto = getOptionItemDto();
        OptionItem optionItem = getOptionItem();
        ArticularItem articularItem = mock(ArticularItem.class);
        when(articularItem.getOptionItems()).thenReturn(Set.of(optionItem));

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        when(transformationFunctionService.getEntity(OptionItem.class, optionGroupOptionItemSetDto))
                .thenReturn(optionItem);
        
//        when(queryService.getEntity(ArticularItem.class, parameterSupplier))
//                .thenReturn(articularItem);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(articularItem);
            return null;
        }).when(optionItemDao).executeConsumer(any(Consumer.class));

        optionItemManager.saveUpdateOptionItemByArticularId(articularId, "", null);

    }

    @Test
    void testSaveUpdateOptionItemSetByOptionGroupName() {
        OptionGroupOptionItemSetDto optionGroupOptionItemSetDto = getOptionItemDto();
        OptionItem newOptionItem = mock(OptionItem.class);
        List<OptionGroupOptionItemSetDto> optionGroupOptionItemSetDtoList = List.of(optionGroupOptionItemSetDto);

//        when(transformationFunctionService.getEntityCollection(OptionItem.class, optionGroupOptionItemSetDto))
//                .thenReturn(Set.of(newOptionItem));
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(newOptionItem);
            return null;
        }).when(optionItemDao).executeConsumer(any(Consumer.class));

//        optionItemManager.saveOptionItemSet("", optionGroupOptionItemSetDtoList);

    }

    @Test
    void testDeleteOptionItemByArticularId() {
        String articularId = "123";
        String optionValue = "innerSearchField";
        OptionItem optionItem = getOptionItem();
        Supplier<OptionItem> optionItemSupplier = () -> optionItem;
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;


        Function<ArticularItem, OptionItem> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(ArticularItem.class, OptionItem.class))
                .thenReturn(function);
//        when(supplierService.entityFieldSupplier(
//                ArticularItem.class,
//                parameterSupplier,
//                function
//        ))
//                .thenReturn(optionItemSupplier);
        optionItemManager.deleteOptionItemByArticularId(articularId, optionValue);

        verify(optionItemDao).deleteEntity(optionItemSupplier);
    }

    @Test
    void testDeleteOptionItemByOptionGroupName() {
        Session session = mock(Session.class);
        String optionGroupName = "mainSearchField";
        String optionValue = "innerSearchField";
        OptionItem optionItem = getOptionItem();

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        NativeQuery<OptionItem> query = mock(NativeQuery.class);

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            consumer.accept(session);
            verify(session).remove(optionItem);
            return null;
        }).when(optionItemDao).executeConsumer(any(Consumer.class));

        optionItemManager.deleteOptionItemByOptionGroup(optionGroupName, optionValue);

        verify(optionItemDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testGetOptionItemByItemArticularId() {
        String articularId = "123";
        ArticularItem articularItem = mock(ArticularItem.class);
        OptionGroupOptionItemSetDto optionGroupOptionItemSetDto = getOptionItemDto();

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        
        Function<ArticularItem, OptionGroupOptionItemSetDto> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(ArticularItem.class, OptionGroupOptionItemSetDto.class))
                .thenReturn(function);
        when(function.apply(articularItem)).thenReturn(optionGroupOptionItemSetDto);
//        when(queryService.getEntityDto(ArticularItem.class, parameterSupplier, function))
//                .thenReturn(optionGroupOptionItemSetDto);

        List<OptionGroupOptionItemSetDto> result = optionItemManager.getOptionItemByItemArticularId(articularId);

        assertEquals(optionGroupOptionItemSetDto, result);
    }

    @Test
    void testGetOptionItemListByOptionGroup() {
        String optionGroupName = "Size";
        OptionItem optionItem = getOptionItem();
        OptionGroupOptionItemSetDto optionGroupOptionItemSetDto = getOptionItemDto();

        Parameter parameter = mock(Parameter.class);

//        when(queryService.getSubEntityList(OptionItem.class, parameter))
//                .thenReturn(List.of(optionItem));
        Function<OptionItem, OptionGroupOptionItemSetDto> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(OptionItem.class, OptionGroupOptionItemSetDto.class))
                .thenReturn(function);
        when(function.apply(optionItem)).thenReturn(optionGroupOptionItemSetDto);
        OptionGroupOptionItemSetDto result = optionItemManager.getOptionItemListByOptionGroup(optionGroupName);

//        assertEquals(1, result.getOptionGroupOptionItemsMap().size());
//        assertEquals(optionGroupOptionItemSetDto, result.getOptionGroupOptionItemsMap().get(0));
    }

    @Test
    void testGetOptionItemList() {
        OptionGroupOptionItemSetDto dto = mock(OptionGroupOptionItemSetDto.class);
        Function<OptionItem, OptionGroupOptionItemSetDto> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(OptionItem.class, OptionGroupOptionItemSetDto.class, "set"))
                .thenReturn(function);

        List<OptionGroupOptionItemSetDto> list = optionItemManager.getOptionItemList();

        assertEquals(1, list.size());
        assertEquals(dto, list.get(0));
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
                .value("XL")
//                .optionGroup(getOptionGroup())
                .build();
    }

    private OptionGroupOptionItemSetDto getOptionItemDto() {
        return OptionGroupOptionItemSetDto.builder()
//                .optionGroupOptionItemsMap(new HashMap<>() {{
//                    put("Size", Set.of("L", "XL", "XXL"));
//                }})
                .build();
    }
}
