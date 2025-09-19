package com.b2c.prototype.manager.option.base;

import com.b2c.prototype.modal.dto.payload.option.OptionGroupOptionItemSetDto;
import com.b2c.prototype.modal.entity.item.ArticularItem;

import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;


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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OptionItemManagerTest {
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
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(articularItem);
            return null;
        });

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
        });

//        optionItemManager.saveOptionItemSet("", optionGroupOptionItemSetDtoList);

    }

    @Test
    void testDeleteOptionItemByArticularId() {
        String articularId = "123";
        String optionValue = "innerSearchField";
        OptionItem optionItem = getOptionItem();
        Supplier<OptionItem> optionItemSupplier = () -> optionItem;



        Function<ArticularItem, OptionItem> function = mock(Function.class);

        optionItemManager.deleteOptionItemByArticularId(articularId, optionValue);

    }

    @Test
    void testDeleteOptionItemByOptionGroupName() {
        Session session = mock(Session.class);
        String optionGroupName = "mainSearchField";
        String optionValue = "innerSearchField";
        OptionItem optionItem = getOptionItem();


        NativeQuery<OptionItem> query = mock(NativeQuery.class);

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            consumer.accept(session);
            verify(session).remove(optionItem);
            return null;
        });

        optionItemManager.deleteOptionItemByOptionGroup(optionGroupName, optionValue);

    }

    @Test
    void testGetOptionItemByItemArticularId() {
        String articularId = "123";
        ArticularItem articularItem = mock(ArticularItem.class);
        OptionGroupOptionItemSetDto optionGroupOptionItemSetDto = getOptionItemDto();



        
        Function<ArticularItem, OptionGroupOptionItemSetDto> function = mock(Function.class);
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

        

//        when(queryService.getSubEntityList(OptionItem.class, parameter))
//                .thenReturn(List.of(optionItem));
        Function<OptionItem, OptionGroupOptionItemSetDto> function = mock(Function.class);

        when(function.apply(optionItem)).thenReturn(optionGroupOptionItemSetDto);
        OptionGroupOptionItemSetDto result = optionItemManager.getOptionItemListByOptionGroup(optionGroupName);

//        assertEquals(1, result.getOptionGroupOptionItemsMap().size());
//        assertEquals(optionGroupOptionItemSetDto, result.getOptionGroupOptionItemsMap().get(0));
    }

    @Test
    void testGetOptionItemList() {
        OptionGroupOptionItemSetDto dto = mock(OptionGroupOptionItemSetDto.class);
        Function<OptionItem, OptionGroupOptionItemSetDto> function = mock(Function.class);

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
