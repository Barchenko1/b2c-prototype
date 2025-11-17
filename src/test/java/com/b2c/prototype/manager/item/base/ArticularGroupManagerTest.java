package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.modal.dto.payload.item.ArticularGroupDto;
import com.b2c.prototype.modal.dto.payload.constant.ItemTypeDto;
import com.b2c.prototype.modal.entity.item.ArticularGroup;
import com.b2c.prototype.modal.entity.item.ArticularItem;


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

class ArticularGroupManagerTest {
    @InjectMocks
    private ArticularGroupManager itemDataManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveArticularGroup() {
        ArticularGroupDto articularGroupDto = getArticularGroupDto();
        ArticularGroup articularGroup = getArticularGroup();

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(articularGroup);
            return null;
        });

        itemDataManager.saveArticularGroup(articularGroupDto);

    }

    @Test
    void testUpdateArticularGroup() {
        String itemId = "itemId";
        ArticularGroupDto articularGroupDto = getArticularGroupDto();
        ArticularGroup articularGroup = getArticularGroup();

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(articularGroup);
            return null;
        });

        itemDataManager.updateArticularGroup(itemId, articularGroupDto);

    }

    @Test
    void testgetArticularGroup() {
        String itemId = "itemId";
        ArticularGroup articularGroup = getArticularGroup();
        ArticularGroupDto responseDto = getResponseItemDataDto();


        Function<ArticularGroup, ArticularGroupDto> function = mock(Function.class);

        when(function.apply(articularGroup)).thenReturn(responseDto);
        ArticularGroupDto result = itemDataManager.getArticularGroup(itemId);

        assertEquals(responseDto, result);
    }

    @Test
    void testgetArticularGroupList() {
        ArticularGroupDto ArticularGroupDto = getResponseItemDataDto();
        List<ArticularGroupDto> responseDtoList = List.of(ArticularGroupDto);
        ArticularGroup articularGroup = getArticularGroup();
        ArticularGroupDto responseDto = getResponseItemDataDto();

        Function<ArticularGroup, ArticularGroupDto> function = mock(Function.class);

//        when(itemDataDao.getEntityList()).thenReturn(List.of(metaData));
        when(function.apply(articularGroup)).thenReturn(responseDto);

        List<ArticularGroupDto> result = itemDataManager.getArticularGroupList();

        assertEquals(responseDtoList, result);
    }

    private ArticularGroupDto getResponseItemDataDto() {
        return ArticularGroupDto.builder()
                .category(null)
//                .itemType(ItemTypeDto.builder()
//                        .value("itemTypeLabel")
//                        .key("itemTypeValue")
//                        .build())
//                .description()
                .build();
    }

    private ArticularGroup getArticularGroup() {
        return ArticularGroup.builder()
//                .category(Category.builder()
//                        .label("categoryLabel")
//                        .value("categoryValue")
//                        .build())
//                .itemType(ItemType.builder()
//                        .label("itemTypeLabel")
//                        .value("itemTypeValue")
//                        .build())
                .build();
    }

    private ArticularGroupDto getArticularGroupDto() {
        return ArticularGroupDto.builder()
                .category(null)
//                .itemType(ItemTypeDto.builder()
//                        .value("itemTypeLabel")
//                        .key("itemTypeValue")
//                        .build())
                .build();
    }

    private ArticularItem getArticularGroupOption() {
        return ArticularItem.builder()
                .articularUniqId("articularIdValue")
                .build();
    }
}
