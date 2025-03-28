//package com.b2c.prototype.manager.payment.base;
//
//import com.b2c.prototype.dao.payment.IPaymentDao;
//import com.b2c.prototype.modal.constant.PaymentMethodEnum;
//import com.b2c.prototype.modal.dto.payload.CreditCardDto;
//import com.b2c.prototype.modal.dto.payload.discount.DiscountDto;
//import com.b2c.prototype.modal.dto.payload.PaymentDto;
//import com.b2c.prototype.modal.dto.payload.PriceDto;
//import com.b2c.prototype.modal.entity.item.Discount;
//import com.b2c.prototype.modal.entity.order.DeliveryArticularItemQuantity;
//import com.b2c.prototype.modal.entity.payment.CreditCard;
//import com.b2c.prototype.modal.entity.payment.Payment;
//import com.b2c.prototype.modal.entity.payment.PaymentMethod;
//import com.b2c.prototype.modal.entity.price.Currency;
//import com.b2c.prototype.modal.entity.price.Price;
//import com.b2c.prototype.service.function.ITransformationFunctionService;
//import com.b2c.prototype.service.query.ISearchService;
//import com.b2c.prototype.service.supplier.ISupplierService;
//import com.tm.core.finder.parameter.Parameter;
//import org.hibernate.Session;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.List;
//import java.util.function.Consumer;
//import java.util.function.Function;
//import java.util.function.Supplier;
//
//import static com.b2c.prototype.util.Constant.ORDER_ID;
//import static com.b2c.prototype.util.Constant.PAYMENT_ID;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.Mockito.any;
//import static org.mockito.Mockito.doAnswer;
//import static org.mockito.Mockito.eq;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//class PaymentManagerTest {
//
//    @Mock
//    private IPaymentDao paymentDao;
//    @Mock
//    private ISearchService queryService;
//    @Mock
//    private ITransformationFunctionService transformationFunctionService;
//    @Mock
//    private ISupplierService supplierService;
//    @InjectMocks
//    private PaymentManager paymentManager;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testSaveUpdatePayment() {
//        PaymentDto paymentDto = getPaymentDto();
//        String orderId = "123";
//        Parameter parameter = mock(Parameter.class);
//        Supplier<Parameter> parameterSupplier = () -> parameter;
//
//        DeliveryArticularItemQuantity orderItemDataOption = mock(DeliveryArticularItemQuantity.class);
//        Payment payment = mock(Payment.class);
//        Payment newPayment = getPayment();
//
//        when(supplierService.parameterStringSupplier(ORDER_ID, orderId))
//                .thenReturn(parameterSupplier);
////        when(queryService.getNamedQueryEntity(DeliveryArticularItemQuantity.class, parameterSupplier))
////                .thenReturn(orderItemDataOption);
////        when(orderItemDataOption.getPayment()).thenReturn(payment);
//        when(transformationFunctionService.getEntity(eq(Payment.class), eq(paymentDto)))
//                .thenReturn(newPayment);
//        doAnswer(invocation -> {
//            Consumer<Session> consumer = invocation.getArgument(0);
//            Session session = mock(Session.class);
//            consumer.accept(session);
//            verify(session).merge(orderItemDataOption);
//            return null;
//        }).when(paymentDao).executeConsumer(any(Consumer.class));
//
//        paymentManager.saveUpdatePayment(orderId, paymentDto);
//
////        verify(orderItemDataOption).setPayment(newPayment);
//        verify(paymentDao).executeConsumer(any(Consumer.class));
//    }
//
//    @Test
//    void testSaveUpdatePaymentNull() {
//        PaymentDto paymentDto = getPaymentDto();
//        String orderId = "123";
//        Parameter parameter = mock(Parameter.class);
//        Supplier<Parameter> parameterSupplier = () -> parameter;
//
//        DeliveryArticularItemQuantity orderItemDataOption = mock(DeliveryArticularItemQuantity.class);
//        Payment payment = null;
//        Payment newPayment = getPayment();
//
//        when(supplierService.parameterStringSupplier(ORDER_ID, orderId))
//                .thenReturn(parameterSupplier);
////        when(queryService.getEntity(DeliveryArticularItemQuantity.class, parameterSupplier))
////                .thenReturn(orderItemDataOption);
////        when(orderItemDataOption.getPayment()).thenReturn(payment);
//        when(transformationFunctionService.getEntity(eq(Payment.class), eq(paymentDto)))
//                .thenReturn(newPayment);
//        doAnswer(invocation -> {
//            Consumer<Session> consumer = invocation.getArgument(0);
//            Session session = mock(Session.class);
//            consumer.accept(session);
//            verify(session).merge(orderItemDataOption);
//            return null;
//        }).when(paymentDao).executeConsumer(any(Consumer.class));
//
//        paymentManager.saveUpdatePayment(orderId, paymentDto);
//
////        verify(orderItemDataOption).setPayment(newPayment);
//        verify(paymentDao).executeConsumer(any());
//    }
//
//    @Test
//    void testDeletePaymentByOrderId() {
//        String orderId = "123";
//        Payment payment = getPayment();
//
//        Parameter parameter = mock(Parameter.class);
//        Supplier<Parameter> parameterSupplier = () -> parameter;
//        when(supplierService.parameterStringSupplier(ORDER_ID, orderId))
//                .thenReturn(parameterSupplier);
//        Supplier<Payment> paymentSupplier = () -> payment;
//        Function<DeliveryArticularItemQuantity, Payment> function = mock(Function.class);
//        when(transformationFunctionService.getTransformationFunction(eq(DeliveryArticularItemQuantity.class), eq(Payment.class)))
//                .thenReturn(function);
////        when(supplierService.entityFieldSupplier(
////                DeliveryArticularItemQuantity.class,
////                parameterSupplier,
////                function)
////        ).thenReturn(paymentSupplier);
//
//        paymentManager.deletePaymentByOrderId(orderId);
//
//        verify(paymentDao).deleteEntity(paymentSupplier);
//    }
//
//    @Test
//    void testDeletePaymentByPaymentId() {
//        String paymentId = "123";
//        Payment payment = getPayment();
//        Supplier<Payment> paymentSupplier = () -> payment;
//
//        Parameter parameter = mock(Parameter.class);
//        Supplier<Parameter> parameterSupplier = () -> parameter;
//
//        when(supplierService.parameterStringSupplier(PAYMENT_ID, paymentId))
//                .thenReturn(parameterSupplier);
////        when(supplierService.entityFieldSupplier(Payment.class, parameterSupplier))
////                .thenReturn(paymentSupplier);
//
//        paymentManager.deletePaymentByPaymentId(paymentId);
//
//        verify(paymentDao).deleteEntity(paymentSupplier);
//    }
//
//    @Test
//    void testGetPaymentByOrderId() {
//        String orderId = "123";
//        Parameter parameter = mock(Parameter.class);
//        Supplier<Parameter> parameterSupplier = () -> parameter;
//        Function<DeliveryArticularItemQuantity, PaymentDto> function = mock(Function.class);
//
//        PaymentDto paymentDto = getPaymentDto();
//        when(supplierService.parameterStringSupplier(ORDER_ID, orderId))
//                .thenReturn(parameterSupplier);
//        when(transformationFunctionService.getTransformationFunction(eq(DeliveryArticularItemQuantity.class), eq(PaymentDto.class)))
//                .thenReturn(function);
////        when(queryService.getEntityDto(DeliveryArticularItemQuantity.class, parameterSupplier, function))
////                .thenReturn(paymentDto);
//
//        PaymentDto result = paymentManager.getPaymentByOrderId(orderId);
//
//        assertNotNull(result);
//    }
//
//    @Test
//    void testGetPaymentByPaymentId() {
//        String paymentId = "123";
//        Parameter parameter = mock(Parameter.class);
//        Supplier<Parameter> parameterSupplier = () -> parameter;
//        Function<Payment, PaymentDto> function = mock(Function.class);
//
//        Payment payment = getPayment();
//        PaymentDto paymentDto = getPaymentDto();
//        when(supplierService.parameterStringSupplier(PAYMENT_ID, paymentId))
//                .thenReturn(parameterSupplier);
//        when(transformationFunctionService.getTransformationFunction(eq(Payment.class), eq(PaymentDto.class)))
//                .thenReturn(function);
////        when(paymentDao.getGraphEntity(anyString(), eq(parameter)))
////                .thenReturn(payment);
//        when(function.apply(payment)).thenReturn(paymentDto);
//
//        PaymentDto result = paymentManager.getPaymentByPaymentId(paymentId);
//
//        assertNotNull(result);
//    }
//
//    @Test
//    void testGetAllPayments() {
//        Payment payment = getPayment();
//        PaymentDto paymentDto = getPaymentDto();
//        Function<Payment, PaymentDto> function = mock(Function.class);
//        when(transformationFunctionService.getTransformationFunction(eq(Payment.class), eq(PaymentDto.class)))
//                .thenReturn(function);
//        when(function.apply(payment)).thenReturn(paymentDto);
////        when(paymentDao.getEntityList())
////                .thenReturn(List.of(payment));
//
//        List<PaymentDto> allPayments = paymentManager.getAllPayments();
//
//        assertNotNull(allPayments);
//        assertEquals(1, allPayments.size());
//        allPayments.forEach(result -> {
//            assertEquals(paymentDto, result);
//        });
//    }
//
//    private Payment getPayment() {
//        PaymentMethod paymentMethod = PaymentMethod.builder()
//                .id(1L)
//                .value(PaymentMethodEnum.CARD.getValue())
//                .build();
//        return Payment.builder()
//                .id(1L)
//                .paymentId("123")
//                .paymentMethod(paymentMethod)
//                .creditCard(getCreditCard())
//                .commissionPrice(getPrice(120))
//                .totalPrice(getPrice(100))
//                .discount(getDiscount())
//                .build();
//    }
//
//    private CreditCardDto getCreditCardDto() {
//        return CreditCardDto.builder()
//                .cardNumber("1234-5678-9012-3456")
//                .monthOfExpire(6)
//                .yearOfExpire(28)
//                .cvv("123")
//                .ownerName("John")
//                .ownerSecondName("Doe")
//                .build();
//    }
//
//    private CreditCard getCreditCard() {
//        return CreditCard.builder()
//                .id(1L)
//                .cardNumber("1234-5678-9012-3456")
//                .monthOfExpire(6)
//                .yearOfExpire(28)
//                .cvv("123")
//                .ownerName("John")
//                .ownerSecondName("Doe")
//                .isActive(true)
//                .build();
//    }
//
//    private Currency getCurrency() {
//        return Currency.builder()
//                .id(1L)
//                .value("USD")
//                .build();
//    }
//
//    private Price getPrice(double amount) {
//        return Price.builder()
//                .amount(amount)
//                .currency(getCurrency())
//                .build();
//    }
//
//    private PriceDto getPriceDto(double amount) {
//        return PriceDto.builder()
//                .amount(amount)
//                .currency(getCurrency().getValue())
//                .build();
//    }
//
//    private DiscountDto getDiscountDto() {
//        return DiscountDto.builder()
//                .charSequenceCode("CODE123")
//                .amount(20)
//                .currency("USA")
//                .build();
//    }
//
//    private Discount getDiscount() {
//        return Discount.builder()
//                .charSequenceCode("CODE123")
//                .amount(20)
//                .currency(getCurrency())
//                .build();
//    }
//
//    private PaymentDto getPaymentDto() {
//        return PaymentDto.builder()
//                .paymentMethod(PaymentMethodEnum.CARD.getValue())
//                .creditCard(getCreditCardDto())
//                .fullPrice(getPriceDto(120))
//                .totalPrice(getPriceDto(100))
//                .discount(getDiscountDto())
//                .build();
//    }
//}
