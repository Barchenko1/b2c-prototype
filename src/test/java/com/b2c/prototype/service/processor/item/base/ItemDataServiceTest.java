package com.b2c.prototype.service.processor.item.base;

import com.b2c.prototype.dao.item.IItemDataDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.ItemDataDto;
import com.b2c.prototype.modal.dto.response.ResponseItemDataDto;
import com.b2c.prototype.modal.dto.update.ItemDataDtoUpdate;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.modal.entity.item.ItemData;
import com.b2c.prototype.modal.entity.item.ItemDataOption;
import com.b2c.prototype.modal.entity.item.ItemStatus;
import com.b2c.prototype.modal.entity.item.ItemType;
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

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ItemDataServiceTest {

    @Mock
    private IItemDataDao itemDataDao;

    @Mock
    private IQueryService queryService;

    @Mock
    private ITransformationFunctionService transformationFunctionService;

    @Mock
    private ISupplierService supplierService;

    @InjectMocks
    private ItemDataService itemDataService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUpdateItemData() {
        String articularId = "articularIdValue";
        ItemDataDtoUpdate itemDataDtoUpdate = ItemDataDtoUpdate.builder()
                .searchField(articularId)
                .newEntity(getItemDataDto())
                .build();
        ItemDataOption itemDataOption = getItemDataOption();
        ItemData updateItemData = getItemData();
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        when(supplierService.parameterStringSupplier("articularId", itemDataDtoUpdate.getSearchField()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(ItemDataOption.class, parameterSupplier))
                .thenReturn(itemDataOption);
        when(transformationFunctionService.getEntity(ItemData.class, itemDataDtoUpdate.getNewEntity()))
                .thenReturn(updateItemData);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(itemDataOption);
            return null;
        }).when(itemDataDao).executeConsumer(any(Consumer.class));

        itemDataService.saveUpdateItemData(itemDataDtoUpdate);

        verify(itemDataDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testDeleteItemData() {
        String articularId = "articularIdValue";
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto(articularId);
        ItemData itemData = getItemData();
        Supplier<ItemData> itemDataSupplier = () -> itemData;
        Function<ItemDataOption, ItemData> function = mock(Function.class);

        when(transformationFunctionService.getTransformationFunction(ItemDataOption.class, ItemData.class))
                .thenReturn(function);
        when(supplierService.entityFieldSupplier(
                ItemDataOption.class,
                "articularId",
                oneFieldEntityDto.getValue(),
                function
        )).thenReturn(itemDataSupplier);

        itemDataService.deleteItemData(oneFieldEntityDto);

        verify(itemDataDao).deleteEntity(itemDataSupplier);
    }

    @Test
    void testGetItemData() {
        String articularId = "articularIdValue";
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto(articularId);
        ResponseItemDataDto responseDto = getResponseItemDataDto();
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        Function<ItemDataOption, ItemData> function = mock(Function.class);

        when(supplierService.parameterStringSupplier("articularId", articularId))
                .thenReturn(parameterSupplier);
        when(transformationFunctionService.getTransformationFunction(ItemDataOption.class, ItemData.class))
                .thenReturn(function);
        when(queryService.getEntityDto(eq(ItemDataOption.class), any(), any())).thenReturn(responseDto);

        ResponseItemDataDto result = itemDataService.getItemData(oneFieldEntityDto);

        assertEquals(responseDto, result);
    }

    @Test
    void testGetItemDataList() {
        ResponseItemDataDto responseItemDataDto = getResponseItemDataDto();
        List<ResponseItemDataDto> responseDtoList = List.of(responseItemDataDto);
        Function<ItemData, ResponseItemDataDto> function = mock(Function.class);

        when(transformationFunctionService.getTransformationFunction(eq(ItemData.class), eq(ResponseItemDataDto.class)))
                .thenReturn(function);
        when(queryService.getEntityDtoList(ItemDataOption.class, function))
                .thenReturn(responseDtoList);

        List<ResponseItemDataDto> result = itemDataService.getItemDataList();

        assertEquals(responseDtoList, result);
    }

    @Test
    void testGetItemDataListFiltered() {
        List<ResponseItemDataDto> responseDtoList = List.of(getResponseItemDataDto());

        Function<ItemData, ResponseItemDataDto> function = mock(Function.class);

        when(transformationFunctionService.getTransformationFunction(eq(ItemData.class), eq(ResponseItemDataDto.class)))
                .thenReturn(function);
        when(queryService.getEntityDtoList(ItemDataOption.class, function))
                .thenReturn(responseDtoList);

        List<ResponseItemDataDto> result = itemDataService.getItemDataListFiltered();

        assertEquals(responseDtoList, result);
    }

    @Test
    void testGetItemDataListSorted() {
        String sortType = "asc";
        ResponseItemDataDto responseDto = getResponseItemDataDto();
        List<ResponseItemDataDto> responseDtoList = List.of(responseDto);
        Function<ItemData, ResponseItemDataDto> function = mock(Function.class);

        when(transformationFunctionService.getTransformationFunction(eq(ItemData.class), eq(ResponseItemDataDto.class)))
                .thenReturn(function);
        when(queryService.getEntityDtoList(ItemDataOption.class, function))
                .thenReturn(responseDtoList);

        List<ResponseItemDataDto> result = itemDataService.getItemDataListSorted(sortType);

        assertEquals(responseDtoList, result);
    }

    private ResponseItemDataDto getResponseItemDataDto() {
        return ResponseItemDataDto.builder()
                .categoryName("categoryNameValue")
                .itemTypeName("itemTypeNameValue")
                .brandName("brandNameValue")
                .itemStatus("itemStatusValue")
//                .description()
                .build();
    }

    private ItemData getItemData() {
        return ItemData.builder()
                .category(Category.builder().name("categoryNameValue").build())
                .itemType(ItemType.builder().value("itemTypeNameValue").build())
                .brand(Brand.builder().value("brandNameValue").build())
                .status(ItemStatus.builder().value("itemStatusValue").build())
                .build();
    }

    private ItemDataDto getItemDataDto() {
        return ItemDataDto.builder()
                .categoryName("categoryNameValue")
                .itemTypeName("itemTypeNameValue")
                .brandName("brandNameValue")
                .itemStatus("itemStatusValue")
                .build();
    }

    private ItemDataOption getItemDataOption() {
        return ItemDataOption.builder()
                .articularId("articularIdValue")
                .itemData(getItemData())
                .build();
    }
}
