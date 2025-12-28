//package com.b2c.prototype.manager.item.base;
//
//import com.b2c.prototype.transform.transform.payload.discount.DiscountDto;
//import com.b2c.prototype.transform.transform.payload.discount.DiscountStatusDto;
//import com.b2c.prototype.transform.entity.item.ArticularItem;
//import com.b2c.prototype.transform.entity.item.Discount;
//import com.b2c.prototype.transform.entity.price.Currency;
//
//
//import org.hibernate.Session;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InjectMocks;
//import org.mockito.MockitoAnnotations;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.function.Consumer;
//import java.util.function.Function;
//import java.util.function.Supplier;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.doAnswer;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//class DiscountManagerTest {
//    @InjectMocks
//    private DiscountManager discountManager;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void savePercentDiscount_shouldSaveDiscount() {
//        DiscountDto transform = createTestDto();
//        Currency currency = creatTestCurrency();
//
////        discountManager.saveDiscountGroup(transform);
//
//        ArgumentCaptor<Supplier<Discount>> captor = ArgumentCaptor.forClass(Supplier.class);
////        verify(gen).persistEntity(captor.capture());
//        Discount capturedEntity = captor.getValue().get();
//        assertEquals(transform.getCharSequenceCode(), capturedEntity.getCharSequenceCode());
//        assertEquals(transform.getAmount(), capturedEntity.getAmount());
//        assertEquals(currency, capturedEntity.getCurrency());
//    }
//
//    @Test
//    void updateItemDataDiscount_shouldUpdateDiscount() {
//        String articularId = "CODE123";
//        DiscountDto transform = createTestDto();
//        ArticularItem mockArticularItem = mock(ArticularItem.class);
//        Discount discount = createTestDiscount();
//
//        when(mockArticularItem.getDiscount()).thenReturn(discount);
//
//        doAnswer(invocation -> {
//            Consumer<Session> consumer = invocation.getArgument(0);
//            Session session = mock(Session.class);
//            consumer.accept(session);
//            verify(session).merge(mockArticularItem);
//            return null;
//        });
//
////        discountManager.updateArticularDiscount(articularId, transform);
//
//    }
//
//    @Test
//    void updateDiscount_shouldUpdateDiscount() {
//        String charSequenceCode = "CODE123";
//        DiscountDto transform = createTestDto();
//        ArticularItem mockArticularItem = mock(ArticularItem.class);
//        Discount discount = createTestDiscount();
//
//        when(mockArticularItem.getDiscount()).thenReturn(discount);
//
//        doAnswer(invocation -> {
//            Consumer<Session> consumer = invocation.getArgument(0);
//            Session session = mock(Session.class);
//            consumer.accept(session);
//            verify(session).merge(discount);
//            return null;
//        });
//
////        discountManager.updateDiscount(charSequenceCode, transform);
//
//    }
//
//    @Test
//    void changeDiscountStatus_shouldUpdateDiscount() {
//        DiscountStatusDto discountStatusDto = DiscountStatusDto.builder()
//                .charSequenceCode("abc")
//                .isActive(false)
//                .build();
//        Discount discount = createTestDiscount();
//        discount.setActive(false);
//
//        doAnswer(invocation -> {
//            Consumer<Session> consumer = invocation.getArgument(0);
//            Session session = mock(Session.class);
//            consumer.accept(session);
//            verify(session).merge(discount);
//            return null;
//        });
//
//        discountManager.changeDiscountStatus(discountStatusDto);
//
//    }
//
//    @Test
//    void deleteDiscount_shouldDeleteDiscount() {
//        DiscountDto transform = createTestDto();
//        ArticularItem mockArticularItem = mock(ArticularItem.class);
//        Discount discount = createTestDiscount();
//
////        when(queryService.getNamedQueryEntity(
////                eq(ArticularItem.class),
////                anyString(),
////                eq(parameterSupplier))
////        ).thenReturn(List.of(mockArticularItem));
//        when(mockArticularItem.getDiscount()).thenReturn(discount);
//
//        doAnswer(invocation -> {
//            Consumer<Session> consumer = invocation.getArgument(0);
//            Session session = mock(Session.class);
//            consumer.accept(session);
//            verify(session).merge(mockArticularItem);
//            return null;
//        });
//
//        discountManager.deleteDiscount(transform.getCharSequenceCode());
//
//    }
//
//    @Test
//    void getDiscount_shouldReturnDiscount() {
//        String code = "CODE123";
//        Function<Collection<ArticularItem>, DiscountDto> function = mock(Function.class);
//        DiscountDto expectedDto = createTestDto();
////        when(queryService.getSubNamedQueryEntityDtoList(
////                eq(ArticularItem.class),
////                anyString(),
////                eq(mockParameter),
////                eq(function)
////        )).thenReturn(expectedDto);
//
////        DiscountDto result = discountManager.getDiscount(code);
////        assertEquals(expectedDto.getCurrency(), result.getCurrency());
////        assertEquals(expectedDto.getArticularIdSet(), result.getArticularIdSet());
////        assertEquals(expectedDto.getAmount(), result.getAmount());
////        assertEquals(expectedDto.getIsActive(), result.getIsActive());
//    }
//
//    @Test
//    void getDiscounts_shouldReturnListOfDiscounts() {
//        String code = "CODE123";
//        Function<Collection<ArticularItem>, Collection<DiscountDto>> function = mock(Function.class);
//        List<Discount> discounts = List.of(createTestDiscount(),
//                Discount.builder()
//                        .amount(200)
//                        .currency(Currency.builder()
//                                .value("EUR")
//                                .build())
//                        .charSequenceCode("CODE124")
//                        .isActive(true)
//                        .build());
//        List<DiscountDto> expectedList = List.of(
//                createTestDto(),
//                DiscountDto.builder()
//                        .amount(200)
//                        .currency("EUR")
//                        .charSequenceCode("CODE124")
//                        .isActive(true)
//                        .build()
//        );
////        when(queryService.getNamedQueryEntityDtoList(
////                eq(ArticularItem.class),
////                anyString(),
////                eq(function)
////        )).thenReturn(expectedList);
////        when(transformationFunctionService.getCollectionTransformationCollectionFunction(ArticularItem.class, DiscountDto.class, "list"))
////                .thenReturn(function);
//
////        List<DiscountDto> result = discountManager.getDiscounts();
////        assertEquals(expectedList, result);
//    }
//
//    private DiscountDto createTestDto() {
//        return DiscountDto.builder()
//                .charSequenceCode("CODE123")
//                .amount(100)
//                .currency("USA")
//                .isActive(true)
//                .build();
//    }
//
//    private Discount createTestDiscount() {
//        return Discount.builder()
//                .charSequenceCode("CODE123")
//                .amount(100)
//                .isActive(true)
//                .currency(creatTestCurrency())
//                .build();
//    }
//
//    private Currency creatTestCurrency() {
//        return Currency.builder()
//                .value("USA")
//                .build();
//    }
//}
