package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.dao.item.IItemDataOptionDao;
import com.b2c.prototype.manager.item.base.ItemDataOptionManager;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.payload.DiscountDto;
import com.b2c.prototype.modal.dto.payload.ItemDataDto;
import com.b2c.prototype.modal.dto.payload.ItemDataOptionDto;
import com.b2c.prototype.modal.dto.payload.OptionItemDto;
import com.b2c.prototype.modal.dto.payload.PriceDto;
import com.b2c.prototype.modal.dto.response.ResponseItemDataOptionDto;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.item.ItemData;
import com.b2c.prototype.modal.entity.item.ItemStatus;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.query.IQueryService;
import com.b2c.prototype.service.supplier.ISupplierService;
import com.tm.core.processor.finder.parameter.Parameter;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.b2c.prototype.util.Constant.ARTICULAR_ID;
import static com.b2c.prototype.util.Constant.ITEM_ID;
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
    private IQueryService queryService;
    @Mock
    private ITransformationFunctionService transformationFunctionService;
    @Mock
    private ISupplierService supplierService;
    @InjectMocks
    private ItemDataOptionManager itemDataOptionManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateItemDataOption_ShouldUpdateEntity() {
        String itemId = "itemId";
        ItemDataOptionDto itemDataOptionDto = getItemDataOptionDto();
        List<ItemDataOptionDto> itemDataOptionDtoList = List.of(itemDataOptionDto);
        ItemData itemData = getItemData();
        ArticularItem newArticularItem = updateItemDataOption();

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        when(supplierService.parameterStringSupplier(ITEM_ID, itemId))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(ItemData.class, parameterSupplier))
                .thenReturn(itemData);
        when(transformationFunctionService.getEntity(ArticularItem.class, itemDataOptionDto))
                .thenReturn(newArticularItem);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(newArticularItem);
            return null;
        }).when(itemDataOptionDao).executeConsumer(any(Consumer.class));

        itemDataOptionManager.saveUpdateItemDataOption(itemId, itemDataOptionDtoList);

        verify(itemDataOptionDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void updateItemDataOption_ShouldUpdateEntityNoExisingItemDataOptions() {
        String itemId = "itemId";
        ItemDataOptionDto itemDataOptionDto = getItemDataOptionDto();
        List<ItemDataOptionDto> itemDataOptionDtoList = List.of(itemDataOptionDto);
        ItemData itemData = getItemData();
        itemData.setArticularItemList(new ArrayList<>());
        ArticularItem newArticularItem = updateItemDataOption();

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        when(supplierService.parameterStringSupplier(ITEM_ID, itemId))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(ItemData.class, parameterSupplier))
                .thenReturn(itemData);
        when(transformationFunctionService.getEntity(ArticularItem.class, itemDataOptionDto))
                .thenReturn(newArticularItem);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(newArticularItem);
            return null;
        }).when(itemDataOptionDao).executeConsumer(any(Consumer.class));

        itemDataOptionManager.saveUpdateItemDataOption(itemId, itemDataOptionDtoList);

        verify(itemDataOptionDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void deleteItemDataOption_ShouldDeleteEntityByParameter() {
        String articularId = "testValue";
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier(ARTICULAR_ID, articularId))
                .thenReturn(parameterSupplier);

        itemDataOptionManager.deleteItemDataOption(articularId);

        verify(itemDataOptionDao).findEntityAndDelete(parameter);
    }

    @Test
    void getResponseItemDataOptionDto_ShouldReturnDto() {
        String value = "testValue";
        ArticularItem articularItem = getItemDataOption();
        ResponseItemDataOptionDto responseDto = responseItemDataOptionDto();

        Function<ArticularItem, ResponseItemDataOptionDto> function = mock(Function.class);
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier(ARTICULAR_ID, value))
                .thenReturn(parameterSupplier);
        when(itemDataOptionDao.getEntityGraph(anyString(), eq(parameter))).thenReturn(articularItem);
        when(transformationFunctionService.getTransformationFunction(ArticularItem.class, ResponseItemDataOptionDto.class))
                .thenReturn(function);
        when(function.apply(articularItem)).thenReturn(responseDto);

        ResponseItemDataOptionDto result = itemDataOptionManager.getResponseItemDataOptionDto(value);

        assertEquals(responseDto, result);
    }

    @Test
    void getResponseItemDataOptionDtoList_ShouldReturnDtoList() {
        ArticularItem articularItem = getItemDataOption();
        ResponseItemDataOptionDto responseDto = responseItemDataOptionDto();
        List<ResponseItemDataOptionDto> responseDtoList = List.of(responseDto);
        Function<ArticularItem, ResponseItemDataOptionDto> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(ArticularItem.class, ResponseItemDataOptionDto.class))
                .thenReturn(function);
        when(itemDataOptionDao.getEntityList()).thenReturn(List.of(getItemDataOption()));
        when(function.apply(articularItem)).thenReturn(responseDto);

        List<ResponseItemDataOptionDto> result = itemDataOptionManager.getResponseItemDataOptionDtoList();

        assertEquals(responseDtoList, result);
    }

    @Test
    void getResponseItemDataOptionDtoFiltered_ShouldReturnDtoList() {
        ArticularItem articularItem = getItemDataOption();
        ResponseItemDataOptionDto responseDto = responseItemDataOptionDto();
        List<ResponseItemDataOptionDto> responseDtoList = List.of(responseDto);
        Function<ArticularItem, ResponseItemDataOptionDto> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(ArticularItem.class, ResponseItemDataOptionDto.class))
                .thenReturn(function);
        when(itemDataOptionDao.getEntityList()).thenReturn(List.of(getItemDataOption()));
        when(function.apply(articularItem)).thenReturn(responseDto);

        List<ResponseItemDataOptionDto> result = itemDataOptionManager.getResponseItemDataOptionDtoFiltered();

        assertEquals(responseDtoList, result);
    }

    @Test
    void getResponseItemDataOptionDtoSorted_ShouldReturnSortedList() {
        String sortType = "asc";
        ArticularItem articularItem = getItemDataOption();
        ResponseItemDataOptionDto responseDto = responseItemDataOptionDto();
        List<ResponseItemDataOptionDto> responseDtoList = List.of(responseDto);
        Function<ArticularItem, ResponseItemDataOptionDto> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(ArticularItem.class, ResponseItemDataOptionDto.class))
                .thenReturn(function);
        when(itemDataOptionDao.getEntityList()).thenReturn(List.of(articularItem));
        when(function.apply(articularItem)).thenReturn(responseDto);

        List<ResponseItemDataOptionDto> result = itemDataOptionManager.getResponseItemDataOptionDtoSorted(sortType);

        assertEquals(responseDtoList, result);
    }

    private ItemDataOptionDto getItemDataOptionDto() {
        Map<OneFieldEntityDto, OneFieldEntityDto> map = new HashMap<>(){{
            put(new OneFieldEntityDto("Size"), new OneFieldEntityDto("L"));
        }};
        ItemDataDto itemDataDto = ItemDataDto.builder()
                .category("categoryNameValue")
                .itemType("itemTypeNameValue")
                .brand("brandNameValue")
                .itemStatus("itemStatusValue")
                .build();
        DiscountDto discountDto = DiscountDto.builder()
                .amount(200)
                .currency("EUR")
                .charSequenceCode("CODE124")
                .build();
        OptionItemDto optionItemDto = null;
        return ItemDataOptionDto.builder()
                .fullPrice(getPriceDto(10))
                .totalPrice(getPriceDto(8))
                .discount(discountDto)
                .optionItem(optionItemDto)
                .build();
    }

    private ResponseItemDataOptionDto responseItemDataOptionDto() {
        Map<OneFieldEntityDto, Set<OneFieldEntityDto>> map = new HashMap<>(){{
            put(new OneFieldEntityDto("Size"), Set.of(new OneFieldEntityDto("L")));
        }};
        return ResponseItemDataOptionDto.builder()
                .articularId("articularId")
                .dateOfCreate(100)
                .fullPrice(getPriceDto(10))
                .currentPrice(getPriceDto(8))
                .discountPrice(getPriceDto(2))
                .optionGroupOptionItemMap(map)
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
                .category(Category.builder().name("categoryNameValue").build())
                .itemType(ItemType.builder().value("itemTypeNameValue").build())
                .brand(Brand.builder().value("brandNameValue").build())
                .status(ItemStatus.builder().value("itemStatusValue").build())
                .articularItemList(List.of(getItemDataOption()))
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
