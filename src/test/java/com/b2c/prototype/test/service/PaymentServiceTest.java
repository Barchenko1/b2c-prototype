//package com.b2c.prototype.test.service;
//
//import com.b2c.prototype.dao.payment.ICardDao;
//import com.b2c.prototype.dao.payment.IPaymentDao;
//import com.b2c.prototype.dao.item.IDiscountDao;
//import com.b2c.prototype.dao.payment.IPaymentMethodDao;
//import com.b2c.prototype.modal.entity.payment.PaymentMethod;
//import com.b2c.prototype.dao.wrapper.IEntityStringMapWrapper;
//import com.b2c.prototype.service.payment.IPaymentService;
//import com.b2c.prototype.service.payment.base.PaymentService;
//import com.tm.core.processor.ThreadLocalSessionManager;
//import org.hibernate.SessionFactory;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.Map;
//
//import static com.b2c.prototype.util.Query.DELETE_PAYMENT_BY_ORDER_ID;
//import static com.b2c.prototype.util.Query.DELETE_PAYMENT_HAVE_NOT_ORDER;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.verify;
//
//class PaymentServiceTest {
//
//    @Mock
//    private ThreadLocalSessionManager sessionManager;
//    @Mock
//    private SessionFactory sessionFactory;
//    @Mock
//    private IPaymentDao paymentDao;
//    @Mock
//    private ICardDao cardDao;
//    @Mock
//    private IDiscountDao discountDao;
//    @Mock
//    private IPaymentMethodDao paymentMethodDao;
//    @Mock
//    private Map<String, PaymentMethod> paymenMethodMap;
//    @Mock
//    private IEntityStringMapWrapper<PaymentMethod> paymentMethodEntityMapWrapper;
//
//    private IPaymentService paymentService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
////        paymentService = new PaymentService(sessionManager, paymentDao, cardDao, discountDao, paymentMethodEntityMapWrapper);
//    }
//
////    @Test
////    void testSavePaymentByCard() {
////        // Mocking input data
////        RequestPaymentDto requestPaymentDto = new RequestPaymentDto();
////        requestPaymentDto.setPaymentMethod(PaymentMethodEnum.CARD.name());
////        requestPaymentDto.setAmount(100.0);
////
////        RequestDiscountDto requestDiscountDto = new RequestDiscountDto();
////        requestDiscountDto.setAmount(100);
////        requestDiscountDto.setCurrency(true);
////        requestPaymentDto.setDiscount(requestDiscountDto);
////
////        RequestCardDto requestCardDto = new RequestCardDto();
////        requestPaymentDto.setCard(requestCardDto);
////
////        PaymentMethod paymentMethod = new PaymentMethod();
////        paymentMethod.setMethod("Card");
////
////        // Mocking paymenMethodMap
////        when(paymenMethodMap.get(anyString())).thenReturn(paymentMethod);
////
////        // Mocking Card entity
////        Card card = new Card();
////        card.setDateOfExpire("12/25");
////        when(cardDao.getOptionalEntityBySQLQueryWithParams(anyString(), anyString())).thenReturn(Optional.of(card));
////
////        // Mocking Discount entity
////        Discount discount = Discount.builder()
////                .amount(requestDiscountDto.getAmount())
////                .isCurrency(requestDiscountDto.isCurrency())
////                .isPercents(requestDiscountDto.isPercents())
////                .build();
////        when(discountDao.getOptionalEntityBySQLQueryWithParams(anyString(), anyInt(), anyBoolean())).thenReturn(Optional.of(discount));
////
////        // Execute the method
////        paymentService.savePayment(requestPaymentDto);
////
////        // Create the expected Payment object
////        Payment payment = Payment.builder()
////                .paymentMethod(paymentMethod)
////                .amount(requestPaymentDto.getAmount())
////                .card(card)
////                .discount(discount)
////                .build();
////
////        // Verify interactions
////        verify(cardDao).saveEntity(card);
////        verify(paymentDao).saveEntity(payment);
////    }
////
////    @Test
////    void testSavePaymentByCash() {
////        // Mocking input data
////        RequestPaymentDto requestPaymentDto = new RequestPaymentDto();
////        requestPaymentDto.setPaymentMethod(PaymentMethodEnum.CARD.name());
////        requestPaymentDto.setAmount(100.0);
////        RequestCardDto requestCardDto = new RequestCardDto();
////        requestPaymentDto.setCard(requestCardDto);
////
////        PaymentMethod paymentMethod = new PaymentMethod();
////        paymentMethod.setMethod("Cash");
////        when(paymenMethodMap.get(any())).thenReturn(paymentMethod);
////
////        Card card = new Card();
////        card.setDateOfExpire("12/25");
////        when(cardDao.getOptionalEntityBySQLQueryWithParams(anyString(), anyString())).thenReturn(Optional.of(card));
////
////        // Execute method
////        paymentService.savePayment(requestPaymentDto);
////
////        Payment payment = Payment.builder()
////                .paymentMethod(paymentMethod)
////                .amount(requestPaymentDto.getAmount())
////                .card(null)
////                .build();
////        // Verify interactions
////        verify(paymentDao).saveEntity(payment);
////    }
//
//    @Test
//    void testDeletePaymentByOrderId() {
//        // Execute method
//        paymentService.deletePaymentByOrderId("some_order_id");
//
//        // Verify interactions
//        verify(paymentDao).mutateEntityBySQLQueryWithParams(eq(DELETE_PAYMENT_BY_ORDER_ID), any());
//    }
//
//    @Test
//    void testDeletePaymentHaveNotOrder() {
//        // Execute method
//        paymentService.deletePaymentHaveNotOrder();
//
//        // Verify interactions
//        verify(paymentDao).mutateEntityBySQLQueryWithParams(eq(DELETE_PAYMENT_HAVE_NOT_ORDER));
//    }
//}