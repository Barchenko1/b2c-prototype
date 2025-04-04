package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.dao.item.IItemDataOptionDao;
import com.b2c.prototype.modal.dto.payload.discount.InitDiscountDto;
import com.b2c.prototype.modal.dto.payload.item.ArticularItemDto;
import com.b2c.prototype.modal.dto.payload.option.OptionGroupOptionItemSetDto;
import com.b2c.prototype.modal.dto.payload.item.PriceDto;
import com.b2c.prototype.modal.dto.payload.item.ResponseArticularItemDto;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.item.ItemData;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.query.ISearchService;
import com.tm.core.finder.parameter.Parameter;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ArticularItemManagerTest {

    @Mock
    private IItemDataOptionDao itemDataOptionDao;
    @Mock
    private ISearchService queryService;
    @Mock
    private ITransformationFunctionService transformationFunctionService;
    @InjectMocks
    private ArticularItemManager articularItemManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateItemDataOption_ShouldUpdateEntity() {
        String itemId = "itemId";
        ArticularItemDto articularItemDto = getItemDataOptionDto();
        List<ArticularItemDto> articularItemDtoList = List.of(articularItemDto);
        ItemData itemData = getItemData();
        ArticularItem newArticularItem = updateItemDataOption();

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

//        when(queryService.getEntity(ItemData.class, parameterSupplier))
//                .thenReturn(itemData);
        when(transformationFunctionService.getEntity(ArticularItem.class, articularItemDto))
                .thenReturn(newArticularItem);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(newArticularItem);
            return null;
        }).when(itemDataOptionDao).executeConsumer(any(Consumer.class));

        articularItemManager.saveUpdateArticularItem(itemId, articularItemDtoList);

        verify(itemDataOptionDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void updateItemDataOption_ShouldUpdateEntityNoExisingItemDataOptions() {
        String itemId = "itemId";
        ArticularItemDto articularItemDto = getItemDataOptionDto();
        List<ArticularItemDto> articularItemDtoList = List.of(articularItemDto);
        ItemData itemData = getItemData();
        itemData.setArticularItemSet(new HashSet<>());
        ArticularItem newArticularItem = updateItemDataOption();

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

//        when(queryService.getEntity(ItemData.class, parameterSupplier))
//                .thenReturn(itemData);
        when(transformationFunctionService.getEntity(ArticularItem.class, articularItemDto))
                .thenReturn(newArticularItem);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(newArticularItem);
            return null;
        }).when(itemDataOptionDao).executeConsumer(any(Consumer.class));

        articularItemManager.saveUpdateArticularItem(itemId, articularItemDtoList);

        verify(itemDataOptionDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void deleteItemDataOption_ShouldDeleteEntityByParameter() {
        String articularId = "testValue";
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        

        articularItemManager.deleteArticularItem(articularId);

        verify(itemDataOptionDao).findEntityAndDelete(parameter);
    }

    @Test
    void getResponseItemDataOptionDto_ShouldReturnDto() {
        String value = "testValue";
        ArticularItem articularItem = getItemDataOption();
        ResponseArticularItemDto responseDto = responseItemDataOptionDto();

        Function<ArticularItem, ResponseArticularItemDto> function = mock(Function.class);
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(itemDataOptionDao.getGraphEntity(anyString(), eq(parameter))).thenReturn(articularItem);
        when(transformationFunctionService.getTransformationFunction(ArticularItem.class, ResponseArticularItemDto.class))
                .thenReturn(function);
        when(function.apply(articularItem)).thenReturn(responseDto);

        ResponseArticularItemDto result = articularItemManager.getResponseArticularItemDto(value);

        assertEquals(responseDto, result);
    }

    @Test
    void getResponseItemDataOptionDtoList_ShouldReturnDtoList() {
        ArticularItem articularItem = getItemDataOption();
        ResponseArticularItemDto responseDto = responseItemDataOptionDto();
        List<ResponseArticularItemDto> responseDtoList = List.of(responseDto);
        Function<ArticularItem, ResponseArticularItemDto> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(ArticularItem.class, ResponseArticularItemDto.class))
                .thenReturn(function);
//        when(itemDataOptionDao.getEntityList()).thenReturn(List.of(getItemDataOption()));
        when(function.apply(articularItem)).thenReturn(responseDto);

        List<ResponseArticularItemDto> result = articularItemManager.getResponseArticularItemDtoList();

        assertEquals(responseDtoList, result);
    }

    @Test
    void getResponseItemDataOptionDtoFiltered_ShouldReturnDtoList() {
        ArticularItem articularItem = getItemDataOption();
        ResponseArticularItemDto responseDto = responseItemDataOptionDto();
        List<ResponseArticularItemDto> responseDtoList = List.of(responseDto);
        Function<ArticularItem, ResponseArticularItemDto> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(ArticularItem.class, ResponseArticularItemDto.class))
                .thenReturn(function);
//        when(itemDataOptionDao.getEntityList()).thenReturn(List.of(getItemDataOption()));
        when(function.apply(articularItem)).thenReturn(responseDto);

        List<ResponseArticularItemDto> result = articularItemManager.getResponseArticularItemDtoFiltered();

        assertEquals(responseDtoList, result);
    }

    @Test
    void getResponseItemDataOptionDtoSorted_ShouldReturnSortedList() {
        String sortType = "asc";
        ArticularItem articularItem = getItemDataOption();
        ResponseArticularItemDto responseDto = responseItemDataOptionDto();
        List<ResponseArticularItemDto> responseDtoList = List.of(responseDto);
        Function<ArticularItem, ResponseArticularItemDto> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(ArticularItem.class, ResponseArticularItemDto.class))
                .thenReturn(function);
//        when(itemDataOptionDao.getEntityList()).thenReturn(List.of(articularItem));
        when(function.apply(articularItem)).thenReturn(responseDto);

        List<ResponseArticularItemDto> result = articularItemManager.getResponseArticularItemDtoSorted(sortType);

        assertEquals(responseDtoList, result);
    }

    private ArticularItemDto getItemDataOptionDto() {
        InitDiscountDto discountDto = InitDiscountDto.builder()
                .amount(200)
                .currency("EUR")
                .charSequenceCode("CODE124")
                .build();
        OptionGroupOptionItemSetDto optionGroupOptionItemSetDto = null;
        return ArticularItemDto.builder()
                .fullPrice(getPriceDto(10))
                .totalPrice(getPriceDto(8))
                .discount(discountDto)
                .options(null)
                .build();
    }

    private ResponseArticularItemDto responseItemDataOptionDto() {
        return ResponseArticularItemDto.builder()
                .articularId("articularId")
                .dateOfCreate(100)
                .fullPrice(getPriceDto(10))
                .totalPrice(getPriceDto(8))
//                .optionGroupOptionItemMap(map)
                .build();
    }

    private ArticularItem updateItemDataOption() {
        OptionGroup optionGroup = OptionGroup.builder()
                .id(1L)
                .value("Size")
                .build();
        OptionItem optionItem = OptionItem.builder()
                .value("L")
                .label("L")
                .optionGroup(optionGroup)
                .build();
        return ArticularItem.builder()
                .articularId("articularId")
                .dateOfCreate(100)
                .fullPrice(getPrice(10))
                .totalPrice(getPrice(8))
                .discount(Discount.builder()
                        .id(1L)
                        .charSequenceCode("Update abc")
                        .amount(2)
                        .isPercent(false)
                        .isActive(true)
                        .build())
                .optionItems(Set.of(optionItem))
                .build();
    }

    private ItemData getItemData() {
        return ItemData.builder()
                .category(Category.builder()
                        .label("categoryLabel")
                        .value("categoryValue").build())
                .itemType(ItemType.builder()
                        .label("itemTypeLabel")
                        .value("itemTypeValue")
                        .build())
                .brand(Brand.builder()
                        .label("brandLabel")
                        .value("brandValue")
                        .build())
                .articularItemSet(Set.of(getItemDataOption()))
                .build();
    }

    private ArticularItem getItemDataOption() {
        OptionGroup optionGroup = OptionGroup.builder()
                .id(1L)
                .value("Size")
                .build();
        OptionItem optionItem = OptionItem.builder()
                .value("L")
                .label("L")
                .optionGroup(optionGroup)
                .build();
        return ArticularItem.builder()
                .articularId("articularId")
                .dateOfCreate(100)
                .fullPrice(getPrice(10))
                .totalPrice(getPrice(8))
                .discount(Discount.builder()
                        .id(1L)
                        .charSequenceCode("abc")
                        .amount(2)
                        .isPercent(false)
                        .isActive(true)
                        .build())
                .optionItems(Set.of(optionItem))
                .build();
    }

    private Price getPrice(double amount) {
        Currency currency = Currency.builder()
                .id(1L)
                .label("USD")
                .value("USD")
                .build();
        return Price.builder()
                .amount(amount)
                .currency(currency)
                .build();
    }

    private PriceDto getPriceDto(double amount) {
        return PriceDto.builder()
                .amount(amount)
                .currency("USD")
                .build();
    }
}
