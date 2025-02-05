package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.dao.item.IItemDataOptionQuantityDao;
import com.b2c.prototype.manager.item.base.ItemDataOptionQuantityManager;
import com.b2c.prototype.modal.dto.payload.ItemDataOptionOneQuantityDto;
import com.b2c.prototype.modal.dto.payload.ItemDataOptionQuantityDto;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.item.ArticularItemQuantity;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.modal.entity.order.OrderArticularItem;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.modal.entity.store.CountType;
import com.b2c.prototype.modal.entity.store.Store;
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

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.b2c.prototype.util.Constant.ORDER_ID;
import static com.b2c.prototype.util.Constant.ARTICULAR_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ArticularItemQuantityManagerTest {

    @Mock
    private IItemDataOptionQuantityDao itemDataOptionQuantityDao;
    @Mock
    private IQueryService queryService;
    @Mock
    private ISupplierService supplierService;
    @InjectMocks
    private ItemDataOptionQuantityManager itemDataOptionQuantityManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIncreaseOneItemDataOptionQuantityCount() {
        ItemDataOptionOneQuantityDto dto = getItemDataOptionOneQuantityDto();

        OrderArticularItem orderItemDataOption = mock(OrderArticularItem.class);
        ArticularItemQuantity articularItemQuantity = getItemDataOptionQuantity();

        when(orderItemDataOption.getArticularItemQuantityList())
                .thenReturn(List.of(articularItemQuantity));

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        when(supplierService.parameterStringSupplier(ORDER_ID, dto.getOrderId()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(OrderArticularItem.class, parameterSupplier)).thenReturn(orderItemDataOption);

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(articularItemQuantity);
            return null;
        }).when(itemDataOptionQuantityDao).executeConsumer(any(Consumer.class));

        itemDataOptionQuantityManager.increaseOneItemDataOptionQuantityCount(dto);

        verify(itemDataOptionQuantityDao).executeConsumer(any(Consumer.class));
        assertEquals(6, articularItemQuantity.getQuantity());
    }

    @Test
    void testDecreaseOneItemDataOptionQuantityCount() {
        ItemDataOptionOneQuantityDto dto = getItemDataOptionOneQuantityDto();

        OrderArticularItem orderItemDataOption = mock(OrderArticularItem.class);
        ArticularItemQuantity articularItemQuantity = getItemDataOptionQuantity();

        when(orderItemDataOption.getArticularItemQuantityList())
                .thenReturn(List.of(articularItemQuantity));

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        when(supplierService.parameterStringSupplier(ORDER_ID, dto.getOrderId()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(OrderArticularItem.class, parameterSupplier)).thenReturn(orderItemDataOption);

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(articularItemQuantity);
            return null;
        }).when(itemDataOptionQuantityDao).executeConsumer(any(Consumer.class));

        itemDataOptionQuantityManager.decreaseOneItemDataOptionQuantityCount(dto);

        verify(itemDataOptionQuantityDao).executeConsumer(any(Consumer.class));
        assertEquals(4, articularItemQuantity.getQuantity());
    }

    @Test
    void testIncreaseOneItemDataOptionQuantityCountAndStore() {
        ItemDataOptionOneQuantityDto dto = getItemDataOptionOneQuantityDto();
        Session session = mock(Session.class);
        ArticularItemQuantity articularItemQuantity = getItemDataOptionQuantity();

        Store store = getStore();

        OrderArticularItem orderItemDataOption = mock(OrderArticularItem.class);
        when(orderItemDataOption.getArticularItemQuantityList())
                .thenReturn(List.of(articularItemQuantity));

        NativeQuery<Store> query = mock(NativeQuery.class);

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        when(supplierService.parameterStringSupplier(ORDER_ID, dto.getOrderId()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(OrderArticularItem.class, parameterSupplier))
                .thenReturn(orderItemDataOption);
        when(session.createNativeQuery(anyString(), eq(Store.class)))
                .thenReturn(query);
        when(queryService.getQueryEntity(query, parameterSupplier)).thenReturn(store);
        when(supplierService.parameterStringSupplier(ARTICULAR_ID, dto.getArticularId()))
                .thenReturn(parameterSupplier);

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            consumer.accept(session);
            verify(session).merge(articularItemQuantity);
            return null;
        }).when(itemDataOptionQuantityDao).executeConsumer(any(Consumer.class));

        itemDataOptionQuantityManager.increaseOneItemDataOptionQuantityCountAndStore(dto);

        assertEquals(9, store.getCount());
        assertEquals(6, articularItemQuantity.getQuantity());
    }

    @Test
    void testDecreaseOneItemDataCountAndStore() {
        ItemDataOptionOneQuantityDto dto = getItemDataOptionOneQuantityDto();
        Session session = mock(Session.class);
        ArticularItemQuantity articularItemQuantity = getItemDataOptionQuantity();
        Store store = new Store();
        store.setCount(10);

        OrderArticularItem orderItemDataOption = mock(OrderArticularItem.class);
        when(orderItemDataOption.getArticularItemQuantityList())
                .thenReturn(List.of(articularItemQuantity));

        NativeQuery<Store> query = mock(NativeQuery.class);

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        when(supplierService.parameterStringSupplier(ORDER_ID, dto.getOrderId()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(OrderArticularItem.class, parameterSupplier))
                .thenReturn(orderItemDataOption);
        when(session.createNativeQuery(anyString(), eq(Store.class)))
                .thenReturn(query);
        when(queryService.getQueryEntity(query, parameterSupplier)).thenReturn(store);
        when(supplierService.parameterStringSupplier(ARTICULAR_ID, dto.getArticularId()))
                .thenReturn(parameterSupplier);

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            consumer.accept(session);
            verify(session).merge(articularItemQuantity);
            return null;
        }).when(itemDataOptionQuantityDao).executeConsumer(any(Consumer.class));

        itemDataOptionQuantityManager.decreaseOneItemDataOptionQuantityCountAndStore(dto);

        assertEquals(11, store.getCount());
        assertEquals(4, articularItemQuantity.getQuantity());
    }

    @Test
    void testIncreaseItemDataOptionQuantityCount() {
        ItemDataOptionQuantityDto dto = getItemDataOptionQuantityDto();
        dto.setQuantity(3);
        OrderArticularItem orderItemDataOption = mock(OrderArticularItem.class);
        ArticularItemQuantity articularItemQuantity = getItemDataOptionQuantity();

        when(orderItemDataOption.getArticularItemQuantityList())
                .thenReturn(List.of(articularItemQuantity));

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        when(supplierService.parameterStringSupplier(ORDER_ID, dto.getOrderId()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(OrderArticularItem.class, parameterSupplier)).thenReturn(orderItemDataOption);

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(articularItemQuantity);
            return null;
        }).when(itemDataOptionQuantityDao).executeConsumer(any(Consumer.class));

        itemDataOptionQuantityManager.increaseItemDataOptionQuantityCount(dto);

        verify(itemDataOptionQuantityDao).executeConsumer(any(Consumer.class));
        assertEquals(8, articularItemQuantity.getQuantity());
    }

    @Test
    void testDecreaseItemDataOptionQuantityCount() {
        ItemDataOptionQuantityDto dto = getItemDataOptionQuantityDto();
        dto.setQuantity(3);
        OrderArticularItem orderItemDataOption = mock(OrderArticularItem.class);
        ArticularItemQuantity articularItemQuantity = getItemDataOptionQuantity();

        when(orderItemDataOption.getArticularItemQuantityList())
                .thenReturn(List.of(articularItemQuantity));

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        when(supplierService.parameterStringSupplier(ORDER_ID, dto.getOrderId()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(OrderArticularItem.class, parameterSupplier)).thenReturn(orderItemDataOption);

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(articularItemQuantity);
            return null;
        }).when(itemDataOptionQuantityDao).executeConsumer(any(Consumer.class));

        itemDataOptionQuantityManager.decreaseItemDataOptionQuantityCount(dto);

        verify(itemDataOptionQuantityDao).executeConsumer(any(Consumer.class));
        assertEquals(2, articularItemQuantity.getQuantity());
    }

    @Test
    void testIncreaseItemDataOptionQuantityCountAndStore() {
        ItemDataOptionQuantityDto dto = getItemDataOptionQuantityDto();
        dto.setQuantity(3);
        Session session = mock(Session.class);
        ArticularItemQuantity articularItemQuantity = getItemDataOptionQuantity();
        Store store = getStore();

        OrderArticularItem orderItemDataOption = mock(OrderArticularItem.class);
        when(orderItemDataOption.getArticularItemQuantityList())
                .thenReturn(List.of(articularItemQuantity));

        NativeQuery<Store> query = mock(NativeQuery.class);

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        when(supplierService.parameterStringSupplier(ORDER_ID, dto.getOrderId()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(OrderArticularItem.class, parameterSupplier))
                .thenReturn(orderItemDataOption);
        when(session.createNativeQuery(anyString(), eq(Store.class)))
                .thenReturn(query);
        when(queryService.getQueryEntity(query, parameterSupplier)).thenReturn(store);
        when(supplierService.parameterStringSupplier(ARTICULAR_ID, dto.getArticularId()))
                .thenReturn(parameterSupplier);

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            consumer.accept(session);
            verify(session).merge(articularItemQuantity);
            return null;
        }).when(itemDataOptionQuantityDao).executeConsumer(any(Consumer.class));

        itemDataOptionQuantityManager.increaseItemDataOptionQuantityCountAndStore(dto);

        assertEquals(2, store.getCount());
        assertEquals(8, articularItemQuantity.getQuantity());
    }

    @Test
    void testDecreaseItemDataCountAndStore() {
        ItemDataOptionQuantityDto dto = getItemDataOptionQuantityDto();
        dto.setQuantity(3);
        Session session = mock(Session.class);
        ArticularItemQuantity articularItemQuantity = getItemDataOptionQuantity();
        Store store = getStore();

        OrderArticularItem orderItemDataOption = mock(OrderArticularItem.class);
        when(orderItemDataOption.getArticularItemQuantityList())
                .thenReturn(List.of(articularItemQuantity));

        NativeQuery<Store> query = mock(NativeQuery.class);

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        when(supplierService.parameterStringSupplier(ORDER_ID, dto.getOrderId()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(OrderArticularItem.class, parameterSupplier))
                .thenReturn(orderItemDataOption);
        when(session.createNativeQuery(anyString(), eq(Store.class)))
                .thenReturn(query);
        when(queryService.getQueryEntity(query, parameterSupplier)).thenReturn(store);
        when(supplierService.parameterStringSupplier(ARTICULAR_ID, dto.getArticularId()))
                .thenReturn(parameterSupplier);

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            consumer.accept(session);
            verify(session).merge(articularItemQuantity);
            return null;
        }).when(itemDataOptionQuantityDao).executeConsumer(any(Consumer.class));

        itemDataOptionQuantityManager.decreaseItemDataOptionQuantityCountAndStore(dto);

        assertEquals(12, store.getCount());
        assertEquals(2, articularItemQuantity.getQuantity());
    }

    @Test
    void testIncreaseItemDataOptionQuantityCountAndStore_exception() {
        ItemDataOptionQuantityDto dto = getItemDataOptionQuantityDto();
        dto.setQuantity(20);
        Session session = mock(Session.class);
        ArticularItemQuantity articularItemQuantity = getItemDataOptionQuantity();
        Store store = getStore();

        OrderArticularItem orderItemDataOption = mock(OrderArticularItem.class);
        when(orderItemDataOption.getArticularItemQuantityList())
                .thenReturn(List.of(articularItemQuantity));

        NativeQuery<Store> query = mock(NativeQuery.class);

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        when(supplierService.parameterStringSupplier(ORDER_ID, dto.getOrderId()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(OrderArticularItem.class, parameterSupplier))
                .thenReturn(orderItemDataOption);
        when(session.createNativeQuery(anyString(), eq(Store.class)))
                .thenReturn(query);
        when(queryService.getQueryEntity(query, parameterSupplier)).thenReturn(store);
        when(supplierService.parameterStringSupplier(ARTICULAR_ID, dto.getArticularId()))
                .thenReturn(parameterSupplier);

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            consumer.accept(session);
            verify(session).merge(articularItemQuantity);
            return null;
        }).when(itemDataOptionQuantityDao).executeConsumer(any(Consumer.class));

        assertThrows(RuntimeException.class,
                () -> itemDataOptionQuantityManager.increaseItemDataOptionQuantityCountAndStore(dto));
    }

    private ItemDataOptionQuantityDto getItemDataOptionQuantityDto() {
        return ItemDataOptionQuantityDto.builder()
                .orderId("orderId")
                .articularId("articularId")
                .quantity(5)
                .build();
    }

    private ItemDataOptionOneQuantityDto getItemDataOptionOneQuantityDto() {
        return ItemDataOptionOneQuantityDto.builder()
                .orderId("orderId")
                .articularId("articularId")
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

    private ArticularItemQuantity getItemDataOptionQuantity() {
        return ArticularItemQuantity.builder()
                .articularItem(getItemDataOption())
                .quantity(5)
                .build();
    }

    private ArticularItem getItemDataOption() {
        OptionGroup optionGroup = OptionGroup.builder()
                .id(1L)
                .value("Size")
                .build();
        OptionItem optionItem = OptionItem.builder()
                .value("L")
                .label("L")
                .optionGroup(optionGroup)
                .build();
        return ArticularItem.builder()
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
                .optionItems(Set.of(optionItem))
                .build();
    }

    private CountType getCountType() {
        return CountType.builder()
                .id(1L)
                .value("LIMITED")
                .build();
    }

    private Store getStore() {
        return Store.builder()
                .id(1L)
                .countType(getCountType())
                .count(10)
                .build();
    }

}
