package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.modal.dto.payload.item.MetaDataDto;
import com.b2c.prototype.modal.dto.payload.constant.BrandDto;
import com.b2c.prototype.modal.dto.payload.constant.CategoryValueDto;
import com.b2c.prototype.modal.dto.payload.constant.ItemTypeDto;
import com.b2c.prototype.modal.dto.payload.item.ResponseMetaDataDto;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.modal.entity.item.MetaData;
import com.b2c.prototype.modal.entity.item.ItemType;


import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MetaDataManagerTest {
    @InjectMocks
    private MetaDataManager itemDataManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveMetaData() {
        MetaDataDto metaDataDto = getMetaDataDto();
        MetaData metaData = getMetaData();

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(metaData);
            return null;
        });

        itemDataManager.saveMetaData(metaDataDto);

    }

    @Test
    void testUpdateMetaData() {
        String itemId = "itemId";
        MetaDataDto metaDataDto = getMetaDataDto();
        MetaData metaData = getMetaData();

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(metaData);
            return null;
        });

        itemDataManager.updateMetaData(itemId, metaDataDto);

    }

    @Test
    void testGetMetaData() {
        String itemId = "itemId";
        MetaData metaData = getMetaData();
        ResponseMetaDataDto responseDto = getResponseItemDataDto();


        Function<MetaData, ResponseMetaDataDto> function = mock(Function.class);

        when(function.apply(metaData)).thenReturn(responseDto);
        ResponseMetaDataDto result = itemDataManager.getMetaData(itemId);

        assertEquals(responseDto, result);
    }

    @Test
    void testGetMetaDataList() {
        ResponseMetaDataDto responseMetaDataDto = getResponseItemDataDto();
        List<ResponseMetaDataDto> responseDtoList = List.of(responseMetaDataDto);
        MetaData metaData = getMetaData();
        ResponseMetaDataDto responseDto = getResponseItemDataDto();

        Function<MetaData, ResponseMetaDataDto> function = mock(Function.class);

//        when(itemDataDao.getEntityList()).thenReturn(List.of(metaData));
        when(function.apply(metaData)).thenReturn(responseDto);

        List<ResponseMetaDataDto> result = itemDataManager.getMetaDataList();

        assertEquals(responseDtoList, result);
    }

    private ResponseMetaDataDto getResponseItemDataDto() {
        return ResponseMetaDataDto.builder()
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

    private MetaData getMetaData() {
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

    private MetaDataDto getMetaDataDto() {
        return MetaDataDto.builder()
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

    private ArticularItem getMetaDataOption() {
        return ArticularItem.builder()
                .articularUniqId("articularIdValue")
                .build();
    }
}
