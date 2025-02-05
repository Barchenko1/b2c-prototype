package com.b2c.prototype.manager.store.base;

import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.service.scope.IConstantsScope;
import com.b2c.prototype.dao.store.IStoreDao;
import com.b2c.prototype.manager.store.base.StoreManager;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.payload.StoreDto;
import com.b2c.prototype.modal.dto.response.ResponseStoreDto;
import com.b2c.prototype.modal.entity.store.CountType;
import com.b2c.prototype.modal.entity.store.Store;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.query.IQueryService;
import com.b2c.prototype.service.supplier.ISupplierService;
import com.tm.core.processor.finder.parameter.Parameter;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.b2c.prototype.modal.constant.CountTypeEnum.LIMITED;
import static com.b2c.prototype.util.Constant.ARTICULAR_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StoreManagerTest {

    @Mock
    private IStoreDao storeDao;
    @Mock
    private IQueryService queryService;
    @Mock
    private ITransformationFunctionService transformationFunctionService;
    @Mock
    private ISupplierService supplierService;
    @Mock
    private IConstantsScope singleValueMap;
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

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        when(supplierService.parameterStringSupplier(ARTICULAR_ID, storeDto.getArticularId()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(ArticularItem.class, parameterSupplier))
                .thenReturn(articularItem);
        when(singleValueMap.getEntity(CountType.class, "value", storeDto.getCountType()))
                .thenReturn(getCountType());

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(store);
            return null;
        }).when(storeDao).executeConsumer(any(Consumer.class));

        storeManager.saveStore(storeDto);

        verify(storeDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testUpdateStore() {
        StoreDto storeDto = getStoreDto();
        Store store = getStore();

        Session session = mock(Session.class);
        NativeQuery<Store> query = mock(NativeQuery.class);

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        when(session.createNativeQuery(anyString(), eq(Store.class)))
                .thenReturn(query);
        when(supplierService.parameterStringSupplier(ARTICULAR_ID, storeDto.getArticularId()))
                .thenReturn(parameterSupplier);
        when(queryService.getQueryEntity(query, parameterSupplier))
                .thenReturn(store);
        when(singleValueMap.getEntity(CountType.class, "value", storeDto.getCountType()))
                .thenReturn(getCountType());

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            consumer.accept(session);
            verify(session).merge(store);
            return null;
        }).when(storeDao).executeConsumer(any(Consumer.class));

        storeManager.updateStore(storeDto);

        verify(storeDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testDeleteStore() {
        OneFieldEntityDto oneFieldEntityDto = OneFieldEntityDto.builder()
                .value("articularId")
                .build();

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        Supplier<Store> storeSupplier = () -> getStore();

        when(supplierService.parameterStringSupplier(ARTICULAR_ID, oneFieldEntityDto.getValue()))
                .thenReturn(parameterSupplier);
        Function<ArticularItem, Store> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(ArticularItem.class, Store.class))
                .thenReturn(function);
        when(supplierService.entityFieldSupplier(
                ArticularItem.class,
                parameterSupplier,
                function
        )).thenReturn(storeSupplier);

        storeManager.deleteStore(oneFieldEntityDto);

        verify(storeDao).deleteEntity(storeSupplier);
    }

    @Test
    void testGetStoreResponse() {
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto();
        oneFieldEntityDto.setValue("articular1");
        Store store = getStore();
        ResponseStoreDto responseStoreDto = mock(ResponseStoreDto.class);

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier(ARTICULAR_ID, oneFieldEntityDto.getValue()))
                .thenReturn(parameterSupplier);
        Function<Store, ResponseStoreDto> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(Store.class, ResponseStoreDto.class))
                .thenReturn(function);
        when(storeDao.getEntityGraph(anyString(), eq(parameter))).thenReturn(store);
        when(function.apply(store)).thenReturn(responseStoreDto);

        ResponseStoreDto result = storeManager.getStoreResponse(oneFieldEntityDto);

        assertEquals(responseStoreDto, result);
    }

    @Test
    void testGetAllStoreResponse() {
        ResponseStoreDto responseStoreDto = mock(ResponseStoreDto.class);
        List<ResponseStoreDto> responseStoreDtoList = Collections.singletonList(responseStoreDto);

        Store store = getStore();

        Function<Store, ResponseStoreDto> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(Store.class, ResponseStoreDto.class))
                .thenReturn(function);
        when(storeDao.getEntityList()).thenReturn(List.of(store));
        when(function.apply(store)).thenReturn(responseStoreDto);

        List<ResponseStoreDto> result = storeManager.getAllStoreResponse();

        assertEquals(responseStoreDtoList, result);
    }

    private CountType getCountType() {
        return CountType.builder()
                .id(1L)
                .value("LIMITED")
                .build();
    }

    private Store getStore() {
        return Store.builder()
                .countType(getCountType())
                .count(10)
                .build();
    }

    private StoreDto getStoreDto() {
        return StoreDto.builder()
                .articularId("articularId")
                .count(10)
                .countType(LIMITED)
                .build();
    }
}
