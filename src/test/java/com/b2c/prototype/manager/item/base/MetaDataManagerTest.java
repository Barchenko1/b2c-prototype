package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.modal.dto.payload.item.ItemDataDto;
import com.b2c.prototype.modal.dto.payload.constant.BrandDto;
import com.b2c.prototype.modal.dto.payload.constant.CategoryValueDto;
import com.b2c.prototype.modal.dto.payload.constant.ItemTypeDto;
import com.b2c.prototype.modal.dto.payload.item.ResponseItemDataDto;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.modal.entity.item.MetaData;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.b2c.prototype.transform.function.ITransformationFunctionService;

import com.tm.core.finder.parameter.Parameter;
import com.tm.core.process.dao.common.ITransactionEntityDao;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MetaDataManagerTest {

    @Mock
    private ITransactionEntityDao itemDataDao;

    @Mock
    private ITransformationFunctionService transformationFunctionService;
    @InjectMocks
    private ItemDataManager itemDataManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveItemData() {
        ItemDataDto itemDataDto = getItemDataDto();
        MetaData metaData = getItemData();

        when(transformationFunctionService.getEntity(MetaData.class, itemDataDto))
                .thenReturn(metaData);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(metaData);
            return null;
        }).when(itemDataDao).executeConsumer(any(Consumer.class));

        itemDataManager.saveItemData(itemDataDto);

        verify(itemDataDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testUpdateItemData() {
        String itemId = "itemId";
        ItemDataDto itemDataDto = getItemDataDto();
        MetaData metaData = getItemData();
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        when(itemDataDao.getNamedQueryEntity("", parameter)).thenReturn(metaData);
        when(transformationFunctionService.getEntity(MetaData.class, itemDataDto))
                .thenReturn(metaData);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(metaData);
            return null;
        }).when(itemDataDao).executeConsumer(any(Consumer.class));

        itemDataManager.updateItemData(itemId, itemDataDto);

        verify(itemDataDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testGetItemData() {
        String itemId = "itemId";
        MetaData metaData = getItemData();
        ResponseItemDataDto responseDto = getResponseItemDataDto();
        Parameter parameter = mock(Parameter.class);

        Function<MetaData, ResponseItemDataDto> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(MetaData.class, ResponseItemDataDto.class))
                .thenReturn(function);
        when(itemDataDao.getGraphEntity(anyString(), eq(parameter))).thenReturn(metaData);
        when(function.apply(metaData)).thenReturn(responseDto);
        ResponseItemDataDto result = itemDataManager.getItemData(itemId);

        assertEquals(responseDto, result);
    }

    @Test
    void testGetItemDataList() {
        ResponseItemDataDto responseItemDataDto = getResponseItemDataDto();
        List<ResponseItemDataDto> responseDtoList = List.of(responseItemDataDto);
        MetaData metaData = getItemData();
        ResponseItemDataDto responseDto = getResponseItemDataDto();

        Function<MetaData, ResponseItemDataDto> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(MetaData.class, ResponseItemDataDto.class))
                .thenReturn(function);
//        when(itemDataDao.getEntityList()).thenReturn(List.of(metaData));
        when(function.apply(metaData)).thenReturn(responseDto);

        List<ResponseItemDataDto> result = itemDataManager.getItemDataList();

        assertEquals(responseDtoList, result);
    }

    private ResponseItemDataDto getResponseItemDataDto() {
        return ResponseItemDataDto.builder()
                .category(CategoryValueDto.builder()
                        .label("categoryLabel")
                        .value("categoryValue")
                        .build())
                .itemType(ItemTypeDto.builder()
                        .label("itemTypeLabel")
                        .value("itemTypeValue")
                        .build())
                .brand(BrandDto.builder()
                        .label("brandLabel")
                        .value("brandValue")
                        .build())
//                .description()
                .build();
    }

    private MetaData getItemData() {
        return MetaData.builder()
                .category(Category.builder()
                        .label("categoryLabel")
                        .value("categoryValue")
                        .build())
                .itemType(ItemType.builder()
                        .label("itemTypeLabel")
                        .value("itemTypeValue")
                        .build())
                .brand(Brand.builder()
                        .label("brandLabel")
                        .value("brandValue")
                        .build())
                .build();
    }

    private ItemDataDto getItemDataDto() {
        return ItemDataDto.builder()
                .category(CategoryValueDto.builder()
                        .label("categoryLabel")
                        .value("categoryValue")
                        .build())
                .itemType(ItemTypeDto.builder()
                        .label("itemTypeLabel")
                        .value("itemTypeValue")
                        .build())
                .brand(BrandDto.builder()
                        .label("brandLabel")
                        .value("brandValue")
                        .build())
                .build();
    }

    private ArticularItem getItemDataOption() {
        return ArticularItem.builder()
                .articularId("articularIdValue")
                .build();
    }
}
