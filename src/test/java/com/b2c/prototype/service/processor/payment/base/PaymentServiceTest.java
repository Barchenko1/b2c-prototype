package com.b2c.prototype.service.processor.payment.base;

import com.b2c.prototype.dao.payment.IPaymentDao;
import com.b2c.prototype.modal.constant.PaymentMethodEnum;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.CreditCardDto;
import com.b2c.prototype.modal.dto.request.DiscountDto;
import com.b2c.prototype.modal.dto.request.PaymentDto;
import com.b2c.prototype.modal.dto.request.PriceDto;
import com.b2c.prototype.modal.dto.update.PaymentSearchFieldEntityDto;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.order.OrderItemData;
import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.b2c.prototype.modal.entity.payment.Payment;
import com.b2c.prototype.modal.entity.payment.PaymentMethod;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.query.IQueryService;
import com.b2c.prototype.service.supplier.ISupplierService;
import com.tm.core.processor.finder.parameter.Parameter;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PaymentServiceTest {

    @Mock
    private IPaymentDao paymentDao;
    @Mock
    private IQueryService queryService;
    @Mock
    private ITransformationFunctionService transformationFunctionService;
    @Mock
    private ISupplierService supplierService;
    @InjectMocks
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUpdatePayment() {
        PaymentDto paymentDto = getPaymentDto();
        PaymentSearchFieldEntityDto paymentSearchFieldEntityDto = PaymentSearchFieldEntityDto.builder()
                .searchField("123")
                .newEntity(paymentDto)
                .build();
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        OrderItemData orderItemData = mock(OrderItemData.class);
        Payment payment = mock(Payment.class);
        Payment newPayment = getPayment();

        when(supplierService.parameterStringSupplier("order_id", paymentSearchFieldEntityDto.getSearchField()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(OrderItemData.class, parameterSupplier))
                .thenReturn(orderItemData);
        when(orderItemData.getPayment()).thenReturn(payment);
        when(transformationFunctionService.getEntity(eq(Payment.class), eq(paymentDto)))
                .thenReturn(newPayment);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(orderItemData);
            return null;
        }).when(paymentDao).executeConsumer(any(Consumer.class));

        paymentService.saveUpdatePayment(paymentSearchFieldEntityDto);

        verify(orderItemData).setPayment(newPayment);
        verify(paymentDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testSaveUpdatePaymentNull() {
        PaymentDto paymentDto = getPaymentDto();
        PaymentSearchFieldEntityDto paymentSearchFieldEntityDto = PaymentSearchFieldEntityDto.builder()
                .searchField("123")
                .newEntity(paymentDto)
                .build();
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        OrderItemData orderItemData = mock(OrderItemData.class);
        Payment payment = null;
        Payment newPayment = getPayment();

        when(supplierService.parameterStringSupplier("order_id", paymentSearchFieldEntityDto.getSearchField()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(OrderItemData.class, parameterSupplier))
                .thenReturn(orderItemData);
        when(orderItemData.getPayment()).thenReturn(payment);
        when(transformationFunctionService.getEntity(eq(Payment.class), eq(paymentDto)))
                .thenReturn(newPayment);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(orderItemData);
            return null;
        }).when(paymentDao).executeConsumer(any(Consumer.class));

        paymentService.saveUpdatePayment(paymentSearchFieldEntityDto);

        verify(orderItemData).setPayment(newPayment);
        verify(paymentDao).executeConsumer(any());
    }

    @Test
    void testDeletePaymentByOrderId() {
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto();
        oneFieldEntityDto.setValue("123");
        Payment payment = getPayment();

        Supplier<Payment> paymentSupplier = () -> payment;
        Function<OrderItemData, Payment> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(eq(OrderItemData.class), eq(Payment.class)))
                .thenReturn(function);
        when(supplierService.entityFieldSupplier(
                OrderItemData.class,
                "order_id",
                oneFieldEntityDto.getValue(),
                function)
        ).thenReturn(paymentSupplier);

        paymentService.deletePaymentByOrderId(oneFieldEntityDto);

        verify(paymentDao).deleteEntity(paymentSupplier);
    }

    @Test
    void testDeletePaymentByPaymentId() {
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto();
        oneFieldEntityDto.setValue("123");
        Payment payment = getPayment();
        Supplier<Payment> paymentSupplier = () -> payment;

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        when(supplierService.parameterStringSupplier("paymentId", oneFieldEntityDto.getValue()))
                .thenReturn(parameterSupplier);
        when(supplierService.entityFieldSupplier(Payment.class, parameterSupplier))
                .thenReturn(paymentSupplier);

        paymentService.deletePaymentByPaymentId(oneFieldEntityDto);

        verify(paymentDao).deleteEntity(paymentSupplier);
    }

    @Test
    void testGetPaymentByOrderId() {
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto();
        oneFieldEntityDto.setValue("123");
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        Function<OrderItemData, PaymentDto> function = mock(Function.class);

        PaymentDto paymentDto = getPaymentDto();
        when(supplierService.parameterStringSupplier("order_id", oneFieldEntityDto.getValue()))
                .thenReturn(parameterSupplier);
        when(transformationFunctionService.getTransformationFunction(eq(OrderItemData.class), eq(PaymentDto.class)))
                .thenReturn(function);
        when(queryService.getEntityDto(OrderItemData.class, parameterSupplier, function))
                .thenReturn(paymentDto);

        PaymentDto result = paymentService.getPaymentByOrderId(oneFieldEntityDto);

        assertNotNull(result);
    }

    @Test
    void testGetPaymentByPaymentId() {
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto();
        oneFieldEntityDto.setValue("123");
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        Function<Payment, PaymentDto> function = mock(Function.class);

        Payment payment = getPayment();
        PaymentDto paymentDto = getPaymentDto();
        when(supplierService.parameterStringSupplier("paymentId", oneFieldEntityDto.getValue()))
                .thenReturn(parameterSupplier);
        when(transformationFunctionService.getTransformationFunction(eq(Payment.class), eq(PaymentDto.class)))
                .thenReturn(function);
        when(paymentDao.getEntity(parameter))
                .thenReturn(payment);
        when(function.apply(payment)).thenReturn(paymentDto);

        PaymentDto result = paymentService.getPaymentByPaymentId(oneFieldEntityDto);

        assertNotNull(result);
    }

    @Test
    void testGetAllPayments() {
        Payment payment = getPayment();
        PaymentDto paymentDto = getPaymentDto();
        Function<Payment, PaymentDto> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(eq(Payment.class), eq(PaymentDto.class)))
                .thenReturn(function);
        when(function.apply(payment)).thenReturn(paymentDto);
        when(paymentDao.getEntityList())
                .thenReturn(List.of(payment));

        List<PaymentDto> allPayments = paymentService.getAllPayments();

        assertNotNull(allPayments);
        assertEquals(1, allPayments.size());
        allPayments.forEach(result -> {
            assertEquals(paymentDto, result);
        });
    }

    private Payment getPayment() {
        PaymentMethod paymentMethod = PaymentMethod.builder()
                .id(1L)
                .value(PaymentMethodEnum.CARD.getValue())
                .build();
        return Payment.builder()
                .id(1L)
                .paymentId("paymentId")
                .paymentMethod(paymentMethod)
                .creditCard(getCreditCard())
                .fullPrice(getPrice(120))
                .totalPrice(getPrice(100))
                .discount(getDiscount())
                .build();
    }

    private CreditCardDto getCreditCardDto() {
        return CreditCardDto.builder()
                .cardNumber("1234-5678-9012-3456")
                .dateOfExpire("06/28")
                .cvv("123")
                .ownerName("John")
                .ownerSecondName("Doe")
                .build();
    }

    private CreditCard getCreditCard() {
        return CreditCard.builder()
                .id(1L)
                .cardNumber("1234-5678-9012-3456")
                .dateOfExpire("06/28")
                .cvv("123")
                .ownerName("John")
                .ownerSecondName("Doe")
                .isActive(true)
                .build();
    }

    private Currency getCurrency() {
        return Currency.builder()
                .id(1L)
                .value("USD")
                .build();
    }

    private Price getPrice(double amount) {
        return Price.builder()
                .amount(amount)
                .currency(getCurrency())
                .build();
    }

    private PriceDto getPriceDto(double amount) {
        return PriceDto.builder()
                .amount(amount)
                .currency(getCurrency().getValue())
                .build();
    }

    private DiscountDto getDiscountDto() {
        return DiscountDto.builder()
                .charSequenceCode("CODE123")
                .amount(20)
                .currency("USA")
                .build();
    }

    private Discount getDiscount() {
        return Discount.builder()
                .charSequenceCode("CODE123")
                .amount(20)
                .currency(getCurrency())
                .build();
    }

    private PaymentDto getPaymentDto() {
        return PaymentDto.builder()
                .orderId("123")
                .paymentMethod(PaymentMethodEnum.CARD.getValue())
                .creditCard(getCreditCardDto())
                .fullPrice(getPriceDto(120))
                .totalPrice(getPriceDto(100))
                .discount(getDiscountDto())
                .build();
    }
}
