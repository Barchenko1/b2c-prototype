package com.b2c.prototype.service.processor.item.base;

import com.b2c.prototype.dao.item.IItemDataDao;
import com.b2c.prototype.dao.item.IItemDataOptionDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.DiscountDto;
import com.b2c.prototype.modal.dto.request.ItemDataDto;
import com.b2c.prototype.modal.dto.request.ItemDataOptionDto;
import com.b2c.prototype.modal.dto.request.ItemDataQuantityDto;
import com.b2c.prototype.modal.dto.request.OptionItemDto;
import com.b2c.prototype.modal.dto.request.PriceDto;
import com.b2c.prototype.modal.dto.response.ResponseItemDataOptionDto;
import com.b2c.prototype.modal.dto.update.ItemDataOptionDtoUpdate;
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
import com.b2c.prototype.service.common.IEntityOperationDao;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.supplier.ISupplierService;
import com.tm.core.processor.finder.parameter.Parameter;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

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
    void saveItemDataOption_ShouldSaveEntity() {
        ItemDataOptionDto dto = mock(ItemDataOptionDto.class);
        ItemDataOption itemDataOption = itemDataOption();
        when(transformationFunctionService.getEntity(ItemDataOption.class, dto))
                .thenReturn(itemDataOption);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(itemDataOption);
            return null;
        }).when(itemDataOptionDao).executeConsumer(any(Consumer.class));

        itemDataOptionService.saveItemDataOption(dto);

        verify(itemDataOptionDao).executeConsumer(any());
    }

    @Test
    void updateItemDataOption_ShouldUpdateEntity() {
        ItemDataOptionDtoUpdate itemDataOptionDtoUpdate = ItemDataOptionDtoUpdate.builder()
                .newEntity(getItemDataOptionDto())
                .searchField("searchField")
                .build();
        ItemDataOption existingEntity = itemDataOption();
        ItemDataOption newItemDataOption = updateItemDataOption();

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        when(supplierService.parameterStringSupplier("articularId", itemDataOptionDtoUpdate.getSearchField()))
                .thenReturn(parameterSupplier);
        when(itemDataOptionDao.getEntity(parameter)).thenReturn(existingEntity);
        when(transformationFunctionService.getEntity(ItemDataOption.class, itemDataOptionDtoUpdate.getNewEntity()))
                .thenReturn(newItemDataOption);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(newItemDataOption);
            return null;
        }).when(itemDataOptionDao).executeConsumer(any(Consumer.class));

        itemDataOptionService.updateItemDataOption(itemDataOptionDtoUpdate);

        verify(itemDataOptionDao).executeConsumer(any());
    }

    @Test
    void deleteItemDataOption_ShouldDeleteEntityByParameter() {
        OneFieldEntityDto dto = new OneFieldEntityDto("testValue");
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier("articularId", dto.getValue()))
                .thenReturn(parameterSupplier);

        itemDataOptionService.deleteItemDataOption(dto);

        verify(itemDataOptionDao).findEntityAndDelete(parameter);
    }

    @Test
    void getResponseItemDataOptionDto_ShouldReturnDto() {
        OneFieldEntityDto dto = new OneFieldEntityDto("testValue");
        ItemDataOption itemDataOption = itemDataOption();
        ResponseItemDataOptionDto responseDto = responseItemDataOptionDto();

        Function<ItemDataOption, ResponseItemDataOptionDto> function = mock(Function.class);
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier("articularId", dto.getValue()))
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
        ItemDataOption itemDataOption = itemDataOption();
        ResponseItemDataOptionDto responseDto = responseItemDataOptionDto();
        List<ResponseItemDataOptionDto> responseDtoList = List.of(responseDto);
        Function<ItemDataOption, ResponseItemDataOptionDto> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(ItemDataOption.class, ResponseItemDataOptionDto.class))
                .thenReturn(function);
        when(itemDataOptionDao.getEntityList()).thenReturn(List.of(itemDataOption()));
        when(function.apply(itemDataOption)).thenReturn(responseDto);

        List<ResponseItemDataOptionDto> result = itemDataOptionService.getResponseItemDataOptionDtoList();

        assertEquals(responseDtoList, result);
    }

    @Test
    void getResponseItemDataOptionDtoFiltered_ShouldReturnDtoList() {
        ItemDataOption itemDataOption = itemDataOption();
        ResponseItemDataOptionDto responseDto = responseItemDataOptionDto();
        List<ResponseItemDataOptionDto> responseDtoList = List.of(responseDto);
        Function<ItemDataOption, ResponseItemDataOptionDto> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(ItemDataOption.class, ResponseItemDataOptionDto.class))
                .thenReturn(function);
        when(itemDataOptionDao.getEntityList()).thenReturn(List.of(itemDataOption()));
        when(function.apply(itemDataOption)).thenReturn(responseDto);

        List<ResponseItemDataOptionDto> result = itemDataOptionService.getResponseItemDataOptionDtoFiltered();

        assertEquals(responseDtoList, result);
    }

    @Test
    void getResponseItemDataOptionDtoSorted_ShouldReturnSortedList() {
        String sortType = "asc";
        ItemDataOption itemDataOption = itemDataOption();
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
        Map<OneFieldEntityDto, Set<OneFieldEntityDto>> map = new HashMap<>(){{
            put(new OneFieldEntityDto("Size"), Set.of(new OneFieldEntityDto("L")));
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

    private ItemDataOption itemDataOption() {
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
