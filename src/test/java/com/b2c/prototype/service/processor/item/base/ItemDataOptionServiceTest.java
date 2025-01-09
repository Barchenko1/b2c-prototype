package com.b2c.prototype.service.processor.item.base;

import com.b2c.prototype.dao.item.IItemDataOptionDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.payload.DiscountDto;
import com.b2c.prototype.modal.dto.payload.ItemDataDto;
import com.b2c.prototype.modal.dto.searchfield.ItemDataOptionArraySearchFieldEntityDto;
import com.b2c.prototype.modal.dto.payload.ItemDataOptionDto;
import com.b2c.prototype.modal.dto.payload.PriceDto;
import com.b2c.prototype.modal.dto.response.ResponseItemDataOptionDto;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.item.ItemData;
import com.b2c.prototype.modal.entity.item.ItemDataOption;
import com.b2c.prototype.modal.entity.item.ItemStatus;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.query.IQueryService;
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
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ItemDataOptionServiceTest {

    @Mock
    private IItemDataOptionDao itemDataOptionDao;
    @Mock
    private IQueryService queryService;
    @Mock
    private ITransformationFunctionService transformationFunctionService;
    @Mock
    private ISupplierService supplierService;
    @InjectMocks
    private ItemDataOptionService itemDataOptionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateItemDataOption_ShouldUpdateEntity() {
        ItemDataOptionArraySearchFieldEntityDto itemDataOptionArraySearchFieldEntityDto = ItemDataOptionArraySearchFieldEntityDto.builder()
                .searchField("searchField")
                .newEntityArray(new ItemDataOptionDto[]{getItemDataOptionDto()})
                .build();
        ItemData itemData = getItemData();
        ItemDataOption newItemDataOption = updateItemDataOption();

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        when(supplierService.parameterStringSupplier(ITEM_ID, itemDataOptionArraySearchFieldEntityDto.getSearchField()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(ItemData.class, parameterSupplier))
                .thenReturn(itemData);
        when(transformationFunctionService.getEntity(ItemDataOption.class, itemDataOptionArraySearchFieldEntityDto.getNewEntityArray()[0]))
                .thenReturn(newItemDataOption);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(newItemDataOption);
            return null;
        }).when(itemDataOptionDao).executeConsumer(any(Consumer.class));

        itemDataOptionService.saveUpdateItemDataOption(itemDataOptionArraySearchFieldEntityDto);

        verify(itemDataOptionDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void updateItemDataOption_ShouldUpdateEntityNoExisingItemDataOptions() {
        ItemDataOptionArraySearchFieldEntityDto itemDataOptionArraySearchFieldEntityDto = ItemDataOptionArraySearchFieldEntityDto.builder()
                .searchField("searchField")
                .newEntityArray(new ItemDataOptionDto[]{getItemDataOptionDto()})
                .build();
        ItemData itemData = getItemData();
        itemData.setItemDataOptionList(new ArrayList<>());
        ItemDataOption newItemDataOption = updateItemDataOption();

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        when(supplierService.parameterStringSupplier(ITEM_ID, itemDataOptionArraySearchFieldEntityDto.getSearchField()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(ItemData.class, parameterSupplier))
                .thenReturn(itemData);
        when(transformationFunctionService.getEntity(ItemDataOption.class, itemDataOptionArraySearchFieldEntityDto.getNewEntityArray()[0]))
                .thenReturn(newItemDataOption);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(newItemDataOption);
            return null;
        }).when(itemDataOptionDao).executeConsumer(any(Consumer.class));

        itemDataOptionService.saveUpdateItemDataOption(itemDataOptionArraySearchFieldEntityDto);

        verify(itemDataOptionDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void deleteItemDataOption_ShouldDeleteEntityByParameter() {
        OneFieldEntityDto dto = new OneFieldEntityDto("testValue");
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier(ARTICULAR_ID, dto.getValue()))
                .thenReturn(parameterSupplier);

        itemDataOptionService.deleteItemDataOption(dto);

        verify(itemDataOptionDao).findEntityAndDelete(parameter);
    }

    @Test
    void getResponseItemDataOptionDto_ShouldReturnDto() {
        OneFieldEntityDto dto = new OneFieldEntityDto("testValue");
        ItemDataOption itemDataOption = getItemDataOption();
        ResponseItemDataOptionDto responseDto = responseItemDataOptionDto();

        Function<ItemDataOption, ResponseItemDataOptionDto> function = mock(Function.class);
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier(ARTICULAR_ID, dto.getValue()))
                .thenReturn(parameterSupplier);
        when(itemDataOptionDao.getEntity(parameter)).thenReturn(itemDataOption);
        when(transformationFunctionService.getTransformationFunction(ItemDataOption.class, ResponseItemDataOptionDto.class))
                .thenReturn(function);
        when(function.apply(itemDataOption)).thenReturn(responseDto);

        ResponseItemDataOptionDto result = itemDataOptionService.getResponseItemDataOptionDto(dto);

        assertEquals(responseDto, result);
    }

    @Test
    void getResponseItemDataOptionDtoList_ShouldReturnDtoList() {
        ItemDataOption itemDataOption = getItemDataOption();
        ResponseItemDataOptionDto responseDto = responseItemDataOptionDto();
        List<ResponseItemDataOptionDto> responseDtoList = List.of(responseDto);
        Function<ItemDataOption, ResponseItemDataOptionDto> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(ItemDataOption.class, ResponseItemDataOptionDto.class))
                .thenReturn(function);
        when(itemDataOptionDao.getEntityList()).thenReturn(List.of(getItemDataOption()));
        when(function.apply(itemDataOption)).thenReturn(responseDto);

        List<ResponseItemDataOptionDto> result = itemDataOptionService.getResponseItemDataOptionDtoList();

        assertEquals(responseDtoList, result);
    }

    @Test
    void getResponseItemDataOptionDtoFiltered_ShouldReturnDtoList() {
        ItemDataOption itemDataOption = getItemDataOption();
        ResponseItemDataOptionDto responseDto = responseItemDataOptionDto();
        List<ResponseItemDataOptionDto> responseDtoList = List.of(responseDto);
        Function<ItemDataOption, ResponseItemDataOptionDto> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(ItemDataOption.class, ResponseItemDataOptionDto.class))
                .thenReturn(function);
        when(itemDataOptionDao.getEntityList()).thenReturn(List.of(getItemDataOption()));
        when(function.apply(itemDataOption)).thenReturn(responseDto);

        List<ResponseItemDataOptionDto> result = itemDataOptionService.getResponseItemDataOptionDtoFiltered();

        assertEquals(responseDtoList, result);
    }

    @Test
    void getResponseItemDataOptionDtoSorted_ShouldReturnSortedList() {
        String sortType = "asc";
        ItemDataOption itemDataOption = getItemDataOption();
        ResponseItemDataOptionDto responseDto = responseItemDataOptionDto();
        List<ResponseItemDataOptionDto> responseDtoList = List.of(responseDto);
        Function<ItemDataOption, ResponseItemDataOptionDto> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(ItemDataOption.class, ResponseItemDataOptionDto.class))
                .thenReturn(function);
        when(itemDataOptionDao.getEntityList()).thenReturn(List.of(itemDataOption));
        when(function.apply(itemDataOption)).thenReturn(responseDto);

        List<ResponseItemDataOptionDto> result = itemDataOptionService.getResponseItemDataOptionDtoSorted(sortType);

        assertEquals(responseDtoList, result);
    }

    private ItemDataOptionDto getItemDataOptionDto() {
        Map<OneFieldEntityDto, OneFieldEntityDto> map = new HashMap<>(){{
            put(new OneFieldEntityDto("Size"), new OneFieldEntityDto("L"));
        }};
        ItemDataDto itemDataDto = ItemDataDto.builder()
                .categoryName("categoryNameValue")
                .itemTypeName("itemTypeNameValue")
                .brandName("brandNameValue")
                .itemStatus("itemStatusValue")
                .build();
        DiscountDto discountDto = DiscountDto.builder()
                .amount(200)
                .currency("EUR")
                .charSequenceCode("CODE124")
                .build();
        return ItemDataOptionDto.builder()
                .itemData(itemDataDto)
                .fullPrice(getPriceDto(10))
                .totalPrice(getPriceDto(8))
                .discount(discountDto)
                .optionGroupOptionItemMap(map)
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

    private ItemDataOption updateItemDataOption() {
        OptionGroup optionGroup = OptionGroup.builder()
                .id(1L)
                .value("Size")
                .build();
        OptionItem optionItem = OptionItem.builder()
                .optionName("L")
                .optionGroup(optionGroup)
                .build();
        return ItemDataOption.builder()
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
                .optionItem(optionItem)
                .build();
    }

    private ItemData getItemData() {
        return ItemData.builder()
                .category(Category.builder().name("categoryNameValue").build())
                .itemType(ItemType.builder().value("itemTypeNameValue").build())
                .brand(Brand.builder().value("brandNameValue").build())
                .status(ItemStatus.builder().value("itemStatusValue").build())
                .itemDataOptionList(List.of(getItemDataOption()))
                .build();
    }

    private ItemDataOption getItemDataOption() {
        OptionGroup optionGroup = OptionGroup.builder()
                .id(1L)
                .value("Size")
                .build();
        OptionItem optionItem = OptionItem.builder()
                .optionName("L")
                .optionGroup(optionGroup)
                .build();
        return ItemDataOption.builder()
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
                .optionItem(optionItem)
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
