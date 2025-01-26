package com.b2c.prototype.service.manager.item.base;

import com.b2c.prototype.dao.item.IItemDataDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.payload.ItemDataDto;
import com.b2c.prototype.modal.dto.response.ResponseItemDataDto;
import com.b2c.prototype.modal.dto.searchfield.ItemDataSearchFieldEntityDto;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.modal.entity.item.ItemData;
import com.b2c.prototype.modal.entity.item.ItemDataOption;
import com.b2c.prototype.modal.entity.item.ItemStatus;
import com.b2c.prototype.modal.entity.item.ItemType;
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

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.b2c.prototype.util.Constant.ITEM_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ItemDataManagerTest {

    @Mock
    private IItemDataDao itemDataDao;
    @Mock
    private IQueryService queryService;
    @Mock
    private ITransformationFunctionService transformationFunctionService;
    @Mock
    private ISupplierService supplierService;
    @InjectMocks
    private ItemDataManager itemDataManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveItemData() {
        ItemDataDto itemDataDto = getItemDataDto();
        ItemData itemData = getItemData();

        when(transformationFunctionService.getEntity(ItemData.class, itemDataDto))
                .thenReturn(itemData);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(itemData);
            return null;
        }).when(itemDataDao).executeConsumer(any(Consumer.class));

        itemDataManager.saveItemData(itemDataDto);

        verify(itemDataDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testUpdateItemData() {
        String articularId = "articularIdValue";
        ItemDataSearchFieldEntityDto itemDataSearchFieldEntityDto = ItemDataSearchFieldEntityDto.builder()
                .searchField(articularId)
                .newEntity(getItemDataDto())
                .build();
        ItemData itemData = getItemData();
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        when(supplierService.parameterStringSupplier(ITEM_ID, itemDataSearchFieldEntityDto.getSearchField()))
                .thenReturn(parameterSupplier);
        when(itemDataDao.getEntity(parameter)).thenReturn(itemData);
        when(transformationFunctionService.getEntity(ItemData.class, itemDataSearchFieldEntityDto.getNewEntity()))
                .thenReturn(itemData);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(itemData);
            return null;
        }).when(itemDataDao).executeConsumer(any(Consumer.class));

        itemDataManager.updateItemData(itemDataSearchFieldEntityDto);

        verify(itemDataDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testDeleteItemData() {
        String articularId = "articular_id";
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto(articularId);

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier(ITEM_ID, oneFieldEntityDto.getValue()))
                .thenReturn(parameterSupplier);

        itemDataManager.deleteItemData(oneFieldEntityDto);

        verify(itemDataDao).findEntityAndDelete(parameter);
    }

    @Test
    void testGetItemData() {
        String itemId = "itemId";
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto(itemId);
        ItemData itemData = getItemData();
        ResponseItemDataDto responseDto = getResponseItemDataDto();
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        Function<ItemData, ResponseItemDataDto> function = mock(Function.class);
        when(supplierService.parameterStringSupplier(ITEM_ID, itemId))
                .thenReturn(parameterSupplier);
        when(transformationFunctionService.getTransformationFunction(ItemData.class, ResponseItemDataDto.class))
                .thenReturn(function);
        when(itemDataDao.getEntityGraph(anyString(), eq(parameter))).thenReturn(itemData);
        when(function.apply(itemData)).thenReturn(responseDto);
        ResponseItemDataDto result = itemDataManager.getItemData(oneFieldEntityDto);

        assertEquals(responseDto, result);
    }

    @Test
    void testGetItemDataList() {
        ResponseItemDataDto responseItemDataDto = getResponseItemDataDto();
        List<ResponseItemDataDto> responseDtoList = List.of(responseItemDataDto);
        ItemData itemData = getItemData();
        ResponseItemDataDto responseDto = getResponseItemDataDto();

        Function<ItemData, ResponseItemDataDto> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(ItemData.class, ResponseItemDataDto.class))
                .thenReturn(function);
        when(itemDataDao.getEntityList()).thenReturn(List.of(itemData));
        when(function.apply(itemData)).thenReturn(responseDto);

        List<ResponseItemDataDto> result = itemDataManager.getItemDataList();

        assertEquals(responseDtoList, result);
    }

    @Test
    void testGetItemDataListFiltered() {
        List<ResponseItemDataDto> responseDtoList = List.of(getResponseItemDataDto());
        ItemData itemData = getItemData();
        ResponseItemDataDto responseDto = getResponseItemDataDto();

        Function<ItemData, ResponseItemDataDto> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(ItemData.class, ResponseItemDataDto.class))
                .thenReturn(function);
        when(itemDataDao.getEntityList()).thenReturn(List.of(itemData));
        when(function.apply(itemData)).thenReturn(responseDto);

        List<ResponseItemDataDto> result = itemDataManager.getItemDataListFiltered();

        assertEquals(responseDtoList, result);
    }

    @Test
    void testGetItemDataListSorted() {
        String sortType = "asc";
        ResponseItemDataDto responseDto = getResponseItemDataDto();
        List<ResponseItemDataDto> responseDtoList = List.of(responseDto);
        ItemData itemData = getItemData();

        Function<ItemData, ResponseItemDataDto> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(ItemData.class, ResponseItemDataDto.class))
                .thenReturn(function);
        when(itemDataDao.getEntityList()).thenReturn(List.of(itemData));
        when(function.apply(itemData)).thenReturn(responseDto);

        List<ResponseItemDataDto> result = itemDataManager.getItemDataListSorted(sortType);

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
                .build();
    }
}
