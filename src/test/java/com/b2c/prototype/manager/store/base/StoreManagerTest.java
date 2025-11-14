package com.b2c.prototype.manager.store.base;

import com.b2c.prototype.modal.entity.item.ArticularItem;

import com.b2c.prototype.modal.dto.payload.store.StoreDto;
import com.b2c.prototype.modal.entity.store.Store;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StoreManagerTest {
    @InjectMocks
    private StoreManager storeManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveStore() {
        StoreDto storeDto = getStoreDto();
        Store store = getStore();

        ArticularItem articularItem = mock(ArticularItem.class);



//        when(supplierService.parameterStringSupplier(ARTICULAR_ID, storeDto.getArticularId()))
//                .thenReturn(parameterSupplier);
//        when(queryService.getEntity(ArticularItem.class, parameterSupplier))
//                .thenReturn(articularItem);

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(store);
            return null;
        });

        storeManager.saveStore(storeDto);

    }

    @Test
    void testUpdateStore() {
        StoreDto storeDto = getStoreDto();
        Store store = getStore();

        Session session = mock(Session.class);
        NativeQuery<Store> query = mock(NativeQuery.class);



//        when(session.createNativeQuery(anyString(), eq(Store.class)))
//                .thenReturn(query);
//        when(supplierService.parameterStringSupplier(ARTICULAR_ID, storeDto.getArticularId()))
//                .thenReturn(parameterSupplier);

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            consumer.accept(session);
            verify(session).merge(store);
            return null;
        });

        storeManager.updateStore("", "", storeDto);

    }

    @Test
    void testDeleteStore() {
        String articularId = "articularId";
        
        Function<ArticularItem, Store> function = mock(Function.class);
//        when(supplierService.entityFieldSupplier(
//                ArticularItem.class,
//                parameterSupplier,
//                function
//        )).thenReturn(storeSupplier);

        storeManager.deleteStore("", articularId);

    }

    @Test
    void testGetAllResponseStoresByArticularId() {
        String articularId = "articularId";
        Store store = getStore();
        StoreDto storeDto = mock(StoreDto.class);


        
        Function<Store, StoreDto> function = mock(Function.class);
        when(function.apply(store)).thenReturn(storeDto);

        List<StoreDto> result = storeManager.getAllStoresByArticularId("", articularId);

        assertEquals(storeDto, result);
    }

    @Test
    void testGetAllStoreResponse() {
        StoreDto storeDto = mock(StoreDto.class);
        List<StoreDto> singletonList = Collections.singletonList(storeDto);

        Store store = getStore();

        Function<Store, StoreDto> function = mock(Function.class);
        when(function.apply(store)).thenReturn(storeDto);

//        List<StoreDto> result = storeManager.getAllResponseStore();

//        assertEquals(singletonList, result);
    }

    private Store getStore() {
        return Store.builder()
                .build();
    }

    private StoreDto getStoreDto() {
        return StoreDto.builder()
                .build();
    }
}
