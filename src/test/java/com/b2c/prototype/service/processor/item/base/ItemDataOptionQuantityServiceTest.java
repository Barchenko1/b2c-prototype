package com.b2c.prototype.service.processor.item.base;

import com.b2c.prototype.dao.item.IItemDataOptionQuantityDao;
import com.b2c.prototype.modal.dto.request.ItemDataOptionOneQuantityDto;
import com.b2c.prototype.modal.dto.request.ItemDataOptionQuantityDto;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.item.ItemDataOption;
import com.b2c.prototype.modal.entity.item.ItemDataOptionQuantity;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.modal.entity.order.OrderItemData;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.modal.entity.store.CountType;
import com.b2c.prototype.modal.entity.store.Store;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.query.IQueryService;
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

class ItemDataOptionQuantityServiceTest {

    @Mock
    private IItemDataOptionQuantityDao itemDataOptionQuantityDao;
    @Mock
    private IQueryService queryService;
    @Mock
    private ITransformationFunctionService transformationFunctionService;
    @Mock
    private ISupplierService supplierService;
    @InjectMocks
    private ItemDataOptionQuantityService itemDataOptionQuantityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIncreaseOneItemDataOptionQuantityCount() {
        ItemDataOptionOneQuantityDto dto = getItemDataOptionOneQuantityDto();

        OrderItemData orderItemData = mock(OrderItemData.class);
        ItemDataOptionQuantity itemDataOptionQuantity = getItemDataOptionQuantity();

        when(orderItemData.getItemDataOptionQuantities())
                .thenReturn(List.of(itemDataOptionQuantity));

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        when(supplierService.parameterStringSupplier(ORDER_ID, dto.getOrderId()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(OrderItemData.class, parameterSupplier)).thenReturn(orderItemData);

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(itemDataOptionQuantity);
            return null;
        }).when(itemDataOptionQuantityDao).executeConsumer(any(Consumer.class));

        itemDataOptionQuantityService.increaseOneItemDataOptionQuantityCount(dto);

        verify(itemDataOptionQuantityDao).executeConsumer(any(Consumer.class));
        assertEquals(6, itemDataOptionQuantity.getQuantity());
    }

    @Test
    void testDecreaseOneItemDataOptionQuantityCount() {
        ItemDataOptionOneQuantityDto dto = getItemDataOptionOneQuantityDto();

        OrderItemData orderItemData = mock(OrderItemData.class);
        ItemDataOptionQuantity itemDataOptionQuantity = getItemDataOptionQuantity();

        when(orderItemData.getItemDataOptionQuantities())
                .thenReturn(List.of(itemDataOptionQuantity));

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        when(supplierService.parameterStringSupplier(ORDER_ID, dto.getOrderId()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(OrderItemData.class, parameterSupplier)).thenReturn(orderItemData);

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(itemDataOptionQuantity);
            return null;
        }).when(itemDataOptionQuantityDao).executeConsumer(any(Consumer.class));

        itemDataOptionQuantityService.decreaseOneItemDataOptionQuantityCount(dto);

        verify(itemDataOptionQuantityDao).executeConsumer(any(Consumer.class));
        assertEquals(4, itemDataOptionQuantity.getQuantity());
    }

    @Test
    void testIncreaseOneItemDataOptionQuantityCountAndStore() {
        ItemDataOptionOneQuantityDto dto = getItemDataOptionOneQuantityDto();
        Session session = mock(Session.class);
        ItemDataOptionQuantity itemDataOptionQuantity = getItemDataOptionQuantity();

        Store store = getStore();

        OrderItemData orderItemData = mock(OrderItemData.class);
        when(orderItemData.getItemDataOptionQuantities())
                .thenReturn(List.of(itemDataOptionQuantity));

        NativeQuery<Store> query = mock(NativeQuery.class);

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        when(supplierService.parameterStringSupplier(ORDER_ID, dto.getOrderId()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(OrderItemData.class, parameterSupplier))
                .thenReturn(orderItemData);
        when(session.createNativeQuery(anyString(), eq(Store.class)))
                .thenReturn(query);
        when(queryService.getQueryEntity(query, parameterSupplier)).thenReturn(store);
        when(supplierService.parameterStringSupplier(ARTICULAR_ID, dto.getArticularId()))
                .thenReturn(parameterSupplier);

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            consumer.accept(session);
            verify(session).merge(itemDataOptionQuantity);
            return null;
        }).when(itemDataOptionQuantityDao).executeConsumer(any(Consumer.class));

        itemDataOptionQuantityService.increaseOneItemDataOptionQuantityCountAndStore(dto);

        assertEquals(9, store.getCount());
        assertEquals(6, itemDataOptionQuantity.getQuantity());
    }

    @Test
    void testDecreaseOneItemDataCountAndStore() {
        ItemDataOptionOneQuantityDto dto = getItemDataOptionOneQuantityDto();
        Session session = mock(Session.class);
        ItemDataOptionQuantity itemDataOptionQuantity = getItemDataOptionQuantity();
        Store store = new Store();
        store.setCount(10);

        OrderItemData orderItemData = mock(OrderItemData.class);
        when(orderItemData.getItemDataOptionQuantities())
                .thenReturn(List.of(itemDataOptionQuantity));

        NativeQuery<Store> query = mock(NativeQuery.class);

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        when(supplierService.parameterStringSupplier(ORDER_ID, dto.getOrderId()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(OrderItemData.class, parameterSupplier))
                .thenReturn(orderItemData);
        when(session.createNativeQuery(anyString(), eq(Store.class)))
                .thenReturn(query);
        when(queryService.getQueryEntity(query, parameterSupplier)).thenReturn(store);
        when(supplierService.parameterStringSupplier(ARTICULAR_ID, dto.getArticularId()))
                .thenReturn(parameterSupplier);

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            consumer.accept(session);
            verify(session).merge(itemDataOptionQuantity);
            return null;
        }).when(itemDataOptionQuantityDao).executeConsumer(any(Consumer.class));

        itemDataOptionQuantityService.decreaseOneItemDataOptionQuantityCountAndStore(dto);

        assertEquals(11, store.getCount());
        assertEquals(4, itemDataOptionQuantity.getQuantity());
    }

    @Test
    void testIncreaseItemDataOptionQuantityCount() {
        ItemDataOptionQuantityDto dto = getItemDataOptionQuantityDto();
        dto.setQuantity(3);
        OrderItemData orderItemData = mock(OrderItemData.class);
        ItemDataOptionQuantity itemDataOptionQuantity = getItemDataOptionQuantity();

        when(orderItemData.getItemDataOptionQuantities())
                .thenReturn(List.of(itemDataOptionQuantity));

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        when(supplierService.parameterStringSupplier(ORDER_ID, dto.getOrderId()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(OrderItemData.class, parameterSupplier)).thenReturn(orderItemData);

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(itemDataOptionQuantity);
            return null;
        }).when(itemDataOptionQuantityDao).executeConsumer(any(Consumer.class));

        itemDataOptionQuantityService.increaseItemDataOptionQuantityCount(dto);

        verify(itemDataOptionQuantityDao).executeConsumer(any(Consumer.class));
        assertEquals(8, itemDataOptionQuantity.getQuantity());
    }

    @Test
    void testDecreaseItemDataOptionQuantityCount() {
        ItemDataOptionQuantityDto dto = getItemDataOptionQuantityDto();
        dto.setQuantity(3);
        OrderItemData orderItemData = mock(OrderItemData.class);
        ItemDataOptionQuantity itemDataOptionQuantity = getItemDataOptionQuantity();

        when(orderItemData.getItemDataOptionQuantities())
                .thenReturn(List.of(itemDataOptionQuantity));

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        when(supplierService.parameterStringSupplier(ORDER_ID, dto.getOrderId()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(OrderItemData.class, parameterSupplier)).thenReturn(orderItemData);

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(itemDataOptionQuantity);
            return null;
        }).when(itemDataOptionQuantityDao).executeConsumer(any(Consumer.class));

        itemDataOptionQuantityService.decreaseItemDataOptionQuantityCount(dto);

        verify(itemDataOptionQuantityDao).executeConsumer(any(Consumer.class));
        assertEquals(2, itemDataOptionQuantity.getQuantity());
    }

    @Test
    void testIncreaseItemDataOptionQuantityCountAndStore() {
        ItemDataOptionQuantityDto dto = getItemDataOptionQuantityDto();
        dto.setQuantity(3);
        Session session = mock(Session.class);
        ItemDataOptionQuantity itemDataOptionQuantity = getItemDataOptionQuantity();
        Store store = getStore();

        OrderItemData orderItemData = mock(OrderItemData.class);
        when(orderItemData.getItemDataOptionQuantities())
                .thenReturn(List.of(itemDataOptionQuantity));

        NativeQuery<Store> query = mock(NativeQuery.class);

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        when(supplierService.parameterStringSupplier(ORDER_ID, dto.getOrderId()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(OrderItemData.class, parameterSupplier))
                .thenReturn(orderItemData);
        when(session.createNativeQuery(anyString(), eq(Store.class)))
                .thenReturn(query);
        when(queryService.getQueryEntity(query, parameterSupplier)).thenReturn(store);
        when(supplierService.parameterStringSupplier(ARTICULAR_ID, dto.getArticularId()))
                .thenReturn(parameterSupplier);

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            consumer.accept(session);
            verify(session).merge(itemDataOptionQuantity);
            return null;
        }).when(itemDataOptionQuantityDao).executeConsumer(any(Consumer.class));

        itemDataOptionQuantityService.increaseItemDataOptionQuantityCountAndStore(dto);

        assertEquals(2, store.getCount());
        assertEquals(8, itemDataOptionQuantity.getQuantity());
    }

    @Test
    void testDecreaseItemDataCountAndStore() {
        ItemDataOptionQuantityDto dto = getItemDataOptionQuantityDto();
        dto.setQuantity(3);
        Session session = mock(Session.class);
        ItemDataOptionQuantity itemDataOptionQuantity = getItemDataOptionQuantity();
        Store store = getStore();

        OrderItemData orderItemData = mock(OrderItemData.class);
        when(orderItemData.getItemDataOptionQuantities())
                .thenReturn(List.of(itemDataOptionQuantity));

        NativeQuery<Store> query = mock(NativeQuery.class);

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        when(supplierService.parameterStringSupplier(ORDER_ID, dto.getOrderId()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(OrderItemData.class, parameterSupplier))
                .thenReturn(orderItemData);
        when(session.createNativeQuery(anyString(), eq(Store.class)))
                .thenReturn(query);
        when(queryService.getQueryEntity(query, parameterSupplier)).thenReturn(store);
        when(supplierService.parameterStringSupplier(ARTICULAR_ID, dto.getArticularId()))
                .thenReturn(parameterSupplier);

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            consumer.accept(session);
            verify(session).merge(itemDataOptionQuantity);
            return null;
        }).when(itemDataOptionQuantityDao).executeConsumer(any(Consumer.class));

        itemDataOptionQuantityService.decreaseItemDataOptionQuantityCountAndStore(dto);

        assertEquals(12, store.getCount());
        assertEquals(2, itemDataOptionQuantity.getQuantity());
    }

    @Test
    void testIncreaseItemDataOptionQuantityCountAndStore_exception() {
        ItemDataOptionQuantityDto dto = getItemDataOptionQuantityDto();
        dto.setQuantity(20);
        Session session = mock(Session.class);
        ItemDataOptionQuantity itemDataOptionQuantity = getItemDataOptionQuantity();
        Store store = getStore();

        OrderItemData orderItemData = mock(OrderItemData.class);
        when(orderItemData.getItemDataOptionQuantities())
                .thenReturn(List.of(itemDataOptionQuantity));

        NativeQuery<Store> query = mock(NativeQuery.class);

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        when(supplierService.parameterStringSupplier(ORDER_ID, dto.getOrderId()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(OrderItemData.class, parameterSupplier))
                .thenReturn(orderItemData);
        when(session.createNativeQuery(anyString(), eq(Store.class)))
                .thenReturn(query);
        when(queryService.getQueryEntity(query, parameterSupplier)).thenReturn(store);
        when(supplierService.parameterStringSupplier(ARTICULAR_ID, dto.getArticularId()))
                .thenReturn(parameterSupplier);

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            consumer.accept(session);
            verify(session).merge(itemDataOptionQuantity);
            return null;
        }).when(itemDataOptionQuantityDao).executeConsumer(any(Consumer.class));

        assertThrows(RuntimeException.class,
                () -> itemDataOptionQuantityService.increaseItemDataOptionQuantityCountAndStore(dto));
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

    private ItemDataOptionQuantity getItemDataOptionQuantity() {
        return ItemDataOptionQuantity.builder()
                .itemDataOption(getItemDataOption())
                .quantity(5)
                .build();
    }

    private ItemDataOption getItemDataOption() {
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
