package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.modal.dto.payload.item.ArticularGroupDto;
import com.b2c.prototype.modal.dto.payload.constant.BrandDto;
import com.b2c.prototype.modal.dto.payload.constant.CategoryValueDto;
import com.b2c.prototype.modal.dto.payload.constant.ItemTypeDto;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.item.MetaData;


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
    private ArticularGroupManager itemDataManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveMetaData() {
        ArticularGroupDto articularGroupDto = getMetaDataDto();
        MetaData metaData = getMetaData();

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(metaData);
            return null;
        });

        itemDataManager.saveArticularGroup(articularGroupDto);

    }

    @Test
    void testUpdateMetaData() {
        String itemId = "itemId";
        ArticularGroupDto articularGroupDto = getMetaDataDto();
        MetaData metaData = getMetaData();

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(metaData);
            return null;
        });

        itemDataManager.updateArticularGroup(itemId, articularGroupDto);

    }

    @Test
    void testGetMetaData() {
        String itemId = "itemId";
        MetaData metaData = getMetaData();
        ArticularGroupDto responseDto = getResponseItemDataDto();


        Function<MetaData, ArticularGroupDto> function = mock(Function.class);

        when(function.apply(metaData)).thenReturn(responseDto);
        ArticularGroupDto result = itemDataManager.getArticularGroup(itemId);

        assertEquals(responseDto, result);
    }

    @Test
    void testGetMetaDataList() {
        ArticularGroupDto ArticularGroupDto = getResponseItemDataDto();
        List<ArticularGroupDto> responseDtoList = List.of(ArticularGroupDto);
        MetaData metaData = getMetaData();
        ArticularGroupDto responseDto = getResponseItemDataDto();

        Function<MetaData, ArticularGroupDto> function = mock(Function.class);

//        when(itemDataDao.getEntityList()).thenReturn(List.of(metaData));
        when(function.apply(metaData)).thenReturn(responseDto);

        List<ArticularGroupDto> result = itemDataManager.getArticularGroupList();

        assertEquals(responseDtoList, result);
    }

    private ArticularGroupDto getResponseItemDataDto() {
        return ArticularGroupDto.builder()
                .category(CategoryValueDto.builder()
                        .value("categoryLabel")
                        .key("categoryValue")
                        .build())
                .itemType(ItemTypeDto.builder()
                        .value("itemTypeLabel")
                        .key("itemTypeValue")
                        .build())
                .brand(BrandDto.builder()
                        .value("brandLabel")
                        .key("brandValue")
                        .build())
//                .description()
                .build();
    }

    private MetaData getMetaData() {
        return MetaData.builder()
//                .category(Category.builder()
//                        .label("categoryLabel")
//                        .value("categoryValue")
//                        .build())
//                .itemType(ItemType.builder()
//                        .label("itemTypeLabel")
//                        .value("itemTypeValue")
//                        .build())
//                .brand(Brand.builder()
//                        .label("brandLabel")
//                        .value("brandValue")
//                        .build())
                .build();
    }

    private ArticularGroupDto getMetaDataDto() {
        return ArticularGroupDto.builder()
                .category(CategoryValueDto.builder()
                        .value("categoryLabel")
                        .key("categoryValue")
                        .build())
                .itemType(ItemTypeDto.builder()
                        .value("itemTypeLabel")
                        .key("itemTypeValue")
                        .build())
                .brand(BrandDto.builder()
                        .value("brandLabel")
                        .key("brandValue")
                        .build())
                .build();
    }

    private ArticularItem getMetaDataOption() {
        return ArticularItem.builder()
                .articularUniqId("articularIdValue")
                .build();
    }
}
