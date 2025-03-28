//package com.b2c.prototype.manager.item.base;
//
//import com.b2c.prototype.dao.item.IItemDataOptionQuantityDao;
//import com.b2c.prototype.modal.dto.payload.ItemDataOptionOneQuantityDto;
//import com.b2c.prototype.modal.dto.payload.ArticularItemQuantityDto;
//import com.b2c.prototype.modal.entity.item.ArticularItem;
//import com.b2c.prototype.modal.entity.item.ArticularItemQuantityPrice;
//import com.b2c.prototype.modal.entity.item.Discount;
//import com.b2c.prototype.modal.entity.option.OptionGroup;
//import com.b2c.prototype.modal.entity.option.OptionItem;
//import com.b2c.prototype.modal.entity.order.DeliveryArticularItemQuantity;
//import com.b2c.prototype.modal.entity.price.Currency;
//import com.b2c.prototype.modal.entity.price.Price;
//import com.b2c.prototype.modal.entity.store.CountType;
//import com.b2c.prototype.modal.entity.store.Store;
//import com.b2c.prototype.service.query.ISearchService;
//import com.b2c.prototype.service.supplier.ISupplierService;
//import com.tm.core.finder.parameter.Parameter;
//import org.hibernate.Session;
//import org.hibernate.query.NativeQuery;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.Set;
//import java.util.function.Consumer;
//import java.util.function.Supplier;
//
//import static com.b2c.prototype.util.Constant.ORDER_ID;
//import static com.b2c.prototype.util.Constant.ARTICULAR_ID;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.any;
//import static org.mockito.Mockito.doAnswer;
//import static org.mockito.Mockito.eq;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//class ArticularItemQuantityPriceManagerTest {
//
//    @Mock
//    private IItemDataOptionQuantityDao itemDataOptionQuantityDao;
//    @Mock
//    private ISearchService queryService;
//    @Mock
//    private ISupplierService supplierService;
//    @InjectMocks
//    private ArticularItemQuantityЗкшManager articularItemQuantityManager;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testIncreaseOneItemDataOptionQuantityCount() {
//        ItemDataOptionOneQuantityDto dto = getItemDataOptionOneQuantityDto();
//
//        DeliveryArticularItemQuantity orderItemDataOption = mock(DeliveryArticularItemQuantity.class);
//        ArticularItemQuantityPrice articularItemQuantityPrice = getItemDataOptionQuantity();
//
//        when(orderItemDataOption.getArticularItemQuantityPrice())
//                .thenReturn(articularItemQuantityPrice);
//
//        Parameter parameter = mock(Parameter.class);
//        Supplier<Parameter> parameterSupplier = () -> parameter;
//
//        when(supplierService.parameterStringSupplier(ORDER_ID, dto.getOrderId()))
//                .thenReturn(parameterSupplier);
////        when(queryService.getEntity(DeliveryArticularItemQuantity.class, parameterSupplier)).thenReturn(orderItemDataOption);
//
//        doAnswer(invocation -> {
//            Consumer<Session> consumer = invocation.getArgument(0);
//            Session session = mock(Session.class);
//            consumer.accept(session);
//            verify(session).merge(articularItemQuantityPrice);
//            return null;
//        }).when(itemDataOptionQuantityDao).executeConsumer(any(Consumer.class));
//
//        articularItemQuantityManager.increaseOneItemDataOptionQuantityCount(dto);
//
//        verify(itemDataOptionQuantityDao).executeConsumer(any(Consumer.class));
//        assertEquals(6, articularItemQuantityPrice.getQuantity());
//    }
//
//    @Test
//    void testDecreaseOneItemDataOptionQuantityCount() {
//        ItemDataOptionOneQuantityDto dto = getItemDataOptionOneQuantityDto();
//
//        DeliveryArticularItemQuantity orderItemDataOption = mock(DeliveryArticularItemQuantity.class);
//        ArticularItemQuantityPrice articularItemQuantityPrice = getItemDataOptionQuantity();
//
//        when(orderItemDataOption.getArticularItemQuantityPrice())
//                .thenReturn(articularItemQuantityPrice);
//
//        Parameter parameter = mock(Parameter.class);
//        Supplier<Parameter> parameterSupplier = () -> parameter;
//
//        when(supplierService.parameterStringSupplier(ORDER_ID, dto.getOrderId()))
//                .thenReturn(parameterSupplier);
////        when(queryService.getEntity(DeliveryArticularItemQuantity.class, parameterSupplier)).thenReturn(orderItemDataOption);
//
//        doAnswer(invocation -> {
//            Consumer<Session> consumer = invocation.getArgument(0);
//            Session session = mock(Session.class);
//            consumer.accept(session);
//            verify(session).merge(articularItemQuantityPrice);
//            return null;
//        }).when(itemDataOptionQuantityDao).executeConsumer(any(Consumer.class));
//
//        articularItemQuantityManager.decreaseOneItemDataOptionQuantityCount(dto);
//
//        verify(itemDataOptionQuantityDao).executeConsumer(any(Consumer.class));
//        assertEquals(4, articularItemQuantityPrice.getQuantity());
//    }
//
//    @Test
//    void testIncreaseOneItemDataOptionQuantityCountAndStore() {
//        ItemDataOptionOneQuantityDto dto = getItemDataOptionOneQuantityDto();
//        Session session = mock(Session.class);
//        ArticularItemQuantityPrice articularItemQuantityPrice = getItemDataOptionQuantity();
//
//        Store store = getStore();
//
//        DeliveryArticularItemQuantity orderItemDataOption = mock(DeliveryArticularItemQuantity.class);
//        when(orderItemDataOption.getArticularItemQuantityPrice())
//                .thenReturn(articularItemQuantityPrice);
//
//        NativeQuery<Store> query = mock(NativeQuery.class);
//
//        Parameter parameter = mock(Parameter.class);
//        Supplier<Parameter> parameterSupplier = () -> parameter;
//
//        when(supplierService.parameterStringSupplier(ORDER_ID, dto.getOrderId()))
//                .thenReturn(parameterSupplier);
////        when(queryService.getEntity(DeliveryArticularItemQuantity.class, parameterSupplier))
////                .thenReturn(orderItemDataOption);
//        when(session.createNativeQuery(anyString(), eq(Store.class)))
//                .thenReturn(query);
////        when(queryService.getQueryEntity(query, parameterSupplier)).thenReturn(store);
//        when(supplierService.parameterStringSupplier(ARTICULAR_ID, dto.getArticularId()))
//                .thenReturn(parameterSupplier);
//
//        doAnswer(invocation -> {
//            Consumer<Session> consumer = invocation.getArgument(0);
//            consumer.accept(session);
//            verify(session).merge(articularItemQuantityPrice);
//            return null;
//        }).when(itemDataOptionQuantityDao).executeConsumer(any(Consumer.class));
//
//        articularItemQuantityManager.increaseOneItemDataOptionQuantityCountAndStore(dto);
//
////        assertEquals(9, store.getCount());
//        assertEquals(6, articularItemQuantityPrice.getQuantity());
//    }
//
//    @Test
//    void testDecreaseOneItemDataCountAndStore() {
//        ItemDataOptionOneQuantityDto dto = getItemDataOptionOneQuantityDto();
//        Session session = mock(Session.class);
//        ArticularItemQuantityPrice articularItemQuantityPrice = getItemDataOptionQuantity();
//        Store store = new Store();
////        store.setCount(10);
//
//        DeliveryArticularItemQuantity orderItemDataOption = mock(DeliveryArticularItemQuantity.class);
//        when(orderItemDataOption.getArticularItemQuantityPrice())
//                .thenReturn(articularItemQuantityPrice);
//
//        NativeQuery<Store> query = mock(NativeQuery.class);
//
//        Parameter parameter = mock(Parameter.class);
//        Supplier<Parameter> parameterSupplier = () -> parameter;
//
//        when(supplierService.parameterStringSupplier(ORDER_ID, dto.getOrderId()))
//                .thenReturn(parameterSupplier);
////        when(queryService.getEntity(DeliveryArticularItemQuantity.class, parameterSupplier))
////                .thenReturn(orderItemDataOption);
//        when(session.createNativeQuery(anyString(), eq(Store.class)))
//                .thenReturn(query);
////        when(queryService.getQueryEntity(query, parameterSupplier)).thenReturn(store);
//        when(supplierService.parameterStringSupplier(ARTICULAR_ID, dto.getArticularId()))
//                .thenReturn(parameterSupplier);
//
//        doAnswer(invocation -> {
//            Consumer<Session> consumer = invocation.getArgument(0);
//            consumer.accept(session);
//            verify(session).merge(articularItemQuantityPrice);
//            return null;
//        }).when(itemDataOptionQuantityDao).executeConsumer(any(Consumer.class));
//
//        articularItemQuantityManager.decreaseOneItemDataOptionQuantityCountAndStore(dto);
//
////        assertEquals(11, store.getCount());
//        assertEquals(4, articularItemQuantityPrice.getQuantity());
//    }
//
//    @Test
//    void testIncreaseItemDataOptionQuantityCount() {
//        ArticularItemQuantityDto dto = getItemDataOptionQuantityDto();
//        dto.setQuantity(3);
//        DeliveryArticularItemQuantity orderItemDataOption = mock(DeliveryArticularItemQuantity.class);
//        ArticularItemQuantityPrice articularItemQuantityPrice = getItemDataOptionQuantity();
//
//        when(orderItemDataOption.getArticularItemQuantityPrice())
//                .thenReturn(articularItemQuantityPrice);
//
//        Parameter parameter = mock(Parameter.class);
//        Supplier<Parameter> parameterSupplier = () -> parameter;
//
////        when(queryService.getEntity(DeliveryArticularItemQuantity.class, parameterSupplier)).thenReturn(orderItemDataOption);
//
//        doAnswer(invocation -> {
//            Consumer<Session> consumer = invocation.getArgument(0);
//            Session session = mock(Session.class);
//            consumer.accept(session);
//            verify(session).merge(articularItemQuantityPrice);
//            return null;
//        }).when(itemDataOptionQuantityDao).executeConsumer(any(Consumer.class));
//
//        articularItemQuantityManager.increaseItemDataOptionQuantityCount(dto);
//
//        verify(itemDataOptionQuantityDao).executeConsumer(any(Consumer.class));
//        assertEquals(8, articularItemQuantityPrice.getQuantity());
//    }
//
//    @Test
//    void testDecreaseItemDataOptionQuantityCount() {
//        ArticularItemQuantityDto dto = getItemDataOptionQuantityDto();
//        dto.setQuantity(3);
//        DeliveryArticularItemQuantity orderItemDataOption = mock(DeliveryArticularItemQuantity.class);
//        ArticularItemQuantityPrice articularItemQuantityPrice = getItemDataOptionQuantity();
//
//        when(orderItemDataOption.getArticularItemQuantityPrice())
//                .thenReturn(articularItemQuantityPrice);
//
//        Parameter parameter = mock(Parameter.class);
//        Supplier<Parameter> parameterSupplier = () -> parameter;
//
////        when(queryService.getEntity(DeliveryArticularItemQuantity.class, parameterSupplier)).thenReturn(orderItemDataOption);
//
//        doAnswer(invocation -> {
//            Consumer<Session> consumer = invocation.getArgument(0);
//            Session session = mock(Session.class);
//            consumer.accept(session);
//            verify(session).merge(articularItemQuantityPrice);
//            return null;
//        }).when(itemDataOptionQuantityDao).executeConsumer(any(Consumer.class));
//
//        articularItemQuantityManager.decreaseItemDataOptionQuantityCount(dto);
//
//        verify(itemDataOptionQuantityDao).executeConsumer(any(Consumer.class));
//        assertEquals(2, articularItemQuantityPrice.getQuantity());
//    }
//
//    @Test
//    void testIncreaseItemDataOptionQuantityCountAndStore() {
//        ArticularItemQuantityDto dto = getItemDataOptionQuantityDto();
//        dto.setQuantity(3);
//        Session session = mock(Session.class);
//        ArticularItemQuantityPrice articularItemQuantityPrice = getItemDataOptionQuantity();
//        Store store = getStore();
//
//        DeliveryArticularItemQuantity orderItemDataOption = mock(DeliveryArticularItemQuantity.class);
//        when(orderItemDataOption.getArticularItemQuantityPrice())
//                .thenReturn(articularItemQuantityPrice);
//
//        NativeQuery<Store> query = mock(NativeQuery.class);
//
//        Parameter parameter = mock(Parameter.class);
//        Supplier<Parameter> parameterSupplier = () -> parameter;
//
////        when(queryService.getEntity(DeliveryArticularItemQuantity.class, parameterSupplier))
////                .thenReturn(orderItemDataOption);
//        when(session.createNativeQuery(anyString(), eq(Store.class)))
//                .thenReturn(query);
////        when(queryService.getQueryEntity(query, parameterSupplier)).thenReturn(store);
//        when(supplierService.parameterStringSupplier(ARTICULAR_ID, dto.getArticularId()))
//                .thenReturn(parameterSupplier);
//
//        doAnswer(invocation -> {
//            Consumer<Session> consumer = invocation.getArgument(0);
//            consumer.accept(session);
//            verify(session).merge(articularItemQuantityPrice);
//            return null;
//        }).when(itemDataOptionQuantityDao).executeConsumer(any(Consumer.class));
//
//        articularItemQuantityManager.increaseItemDataOptionQuantityCountAndStore(dto);
//
////        assertEquals(2, store.getCount());
//        assertEquals(8, articularItemQuantityPrice.getQuantity());
//    }
//
//    @Test
//    void testDecreaseItemDataCountAndStore() {
//        ArticularItemQuantityDto dto = getItemDataOptionQuantityDto();
//        dto.setQuantity(3);
//        Session session = mock(Session.class);
//        ArticularItemQuantityPrice articularItemQuantityPrice = getItemDataOptionQuantity();
//        Store store = getStore();
//
//        DeliveryArticularItemQuantity orderItemDataOption = mock(DeliveryArticularItemQuantity.class);
//        when(orderItemDataOption.getArticularItemQuantityPrice())
//                .thenReturn(articularItemQuantityPrice);
//
//        NativeQuery<Store> query = mock(NativeQuery.class);
//
//        Parameter parameter = mock(Parameter.class);
//        Supplier<Parameter> parameterSupplier = () -> parameter;
//
////        when(queryService.getEntity(DeliveryArticularItemQuantity.class, parameterSupplier))
////                .thenReturn(orderItemDataOption);
//        when(session.createNativeQuery(anyString(), eq(Store.class)))
//                .thenReturn(query);
////        when(queryService.getQueryEntity(query, parameterSupplier)).thenReturn(store);
//        when(supplierService.parameterStringSupplier(ARTICULAR_ID, dto.getArticularId()))
//                .thenReturn(parameterSupplier);
//
//        doAnswer(invocation -> {
//            Consumer<Session> consumer = invocation.getArgument(0);
//            consumer.accept(session);
//            verify(session).merge(articularItemQuantityPrice);
//            return null;
//        }).when(itemDataOptionQuantityDao).executeConsumer(any(Consumer.class));
//
//        articularItemQuantityManager.decreaseItemDataOptionQuantityCountAndStore(dto);
//
////        assertEquals(12, store.getCount());
//        assertEquals(2, articularItemQuantityPrice.getQuantity());
//    }
//
//    @Test
//    void testIncreaseItemDataOptionQuantityCountAndStore_exception() {
//        ArticularItemQuantityDto dto = getItemDataOptionQuantityDto();
//        dto.setQuantity(20);
//        Session session = mock(Session.class);
//        ArticularItemQuantityPrice articularItemQuantityPrice = getItemDataOptionQuantity();
//        Store store = getStore();
//
//        DeliveryArticularItemQuantity orderItemDataOption = mock(DeliveryArticularItemQuantity.class);
//        when(orderItemDataOption.getArticularItemQuantityPrice())
//                .thenReturn(articularItemQuantityPrice);
//
//        NativeQuery<Store> query = mock(NativeQuery.class);
//
//        Parameter parameter = mock(Parameter.class);
//        Supplier<Parameter> parameterSupplier = () -> parameter;
//
////        when(queryService.getEntity(DeliveryArticularItemQuantity.class, parameterSupplier))
////                .thenReturn(orderItemDataOption);
//        when(session.createNativeQuery(anyString(), eq(Store.class)))
//                .thenReturn(query);
////        when(queryService.getQueryEntity(query, parameterSupplier)).thenReturn(store);
//        when(supplierService.parameterStringSupplier(ARTICULAR_ID, dto.getArticularId()))
//                .thenReturn(parameterSupplier);
//
//        doAnswer(invocation -> {
//            Consumer<Session> consumer = invocation.getArgument(0);
//            consumer.accept(session);
//            verify(session).merge(articularItemQuantityPrice);
//            return null;
//        }).when(itemDataOptionQuantityDao).executeConsumer(any(Consumer.class));
//
//        assertThrows(RuntimeException.class,
//                () -> articularItemQuantityManager.increaseItemDataOptionQuantityCountAndStore(dto));
//    }
//
//    private ArticularItemQuantityDto getItemDataOptionQuantityDto() {
//        return ArticularItemQuantityDto.builder()
//                .articularId("articularId")
//                .quantity(5)
//                .build();
//    }
//
//    private ItemDataOptionOneQuantityDto getItemDataOptionOneQuantityDto() {
//        return ItemDataOptionOneQuantityDto.builder()
//                .orderId("orderId")
//                .articularId("articularId")
//                .build();
//    }
//
//    private Price getPrice(double amount) {
//        Currency currency = Currency.builder()
//                .id(1L)
//                .label("USD")
//                .value("USD")
//                .build();
//        return Price.builder()
//                .amount(amount)
//                .currency(currency)
//                .build();
//    }
//
//    private ArticularItemQuantityPrice getItemDataOptionQuantity() {
//        return ArticularItemQuantityPrice.builder()
//                .articularItem(getItemDataOption())
//                .quantity(5)
//                .build();
//    }
//
//    private ArticularItem getItemDataOption() {
//        OptionGroup optionGroup = OptionGroup.builder()
//                .id(1L)
//                .value("Size")
//                .build();
//        OptionItem optionItem = OptionItem.builder()
//                .value("L")
//                .label("L")
//                .optionGroup(optionGroup)
//                .build();
//        return ArticularItem.builder()
//                .articularId("articularId")
//                .dateOfCreate(100)
//                .fullPrice(getPrice(10))
//                .totalPrice(getPrice(8))
//                .discount(Discount.builder()
//                        .id(1L)
//                        .charSequenceCode("abc")
//                        .amount(2)
//                        .isPercent(false)
//                        .isActive(true)
//                        .build())
//                .optionItems(Set.of(optionItem))
//                .build();
//    }
//
//    private CountType getCountType() {
//        return CountType.builder()
//                .id(1L)
//                .value("LIMITED")
//                .build();
//    }
//
//    private Store getStore() {
//        return Store.builder()
//                .id(1L)
////                .countType(getCountType())
//                .build();
//    }
//
//}
