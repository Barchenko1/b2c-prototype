package com.b2c.prototype.manager.payment.base;

import com.b2c.prototype.dao.payment.ICreditCardDao;
import com.b2c.prototype.modal.dto.delete.MultipleFieldsSearchDtoDelete;
import com.b2c.prototype.modal.dto.payload.CreditCardDto;
import com.b2c.prototype.modal.dto.response.ResponseCreditCardDto;
import com.b2c.prototype.modal.entity.order.OrderArticularItem;
import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.b2c.prototype.modal.entity.payment.Payment;
import com.b2c.prototype.modal.entity.user.UserDetails;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.query.ISearchService;
import com.b2c.prototype.service.supplier.ISupplierService;
import com.tm.core.finder.parameter.Parameter;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.b2c.prototype.util.Constant.ORDER_ID;
import static com.b2c.prototype.util.Constant.USER_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreditCardManagerTest {

    @Mock
    private ICreditCardDao creditCardDao;
    @Mock
    private ISearchService queryService;
    @Mock
    private ITransformationFunctionService transformationFunctionService;
    @Mock
    private ISupplierService supplierService;
    @InjectMocks
    private CreditCardManager creditCardManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveCreditCardByUserId_shouldSaveCreditCreditCard() {
        CreditCardDto creditCardDto = getTestCreditCardDto();
        String userId = "abc";

        UserDetails userProfile = mock(UserDetails.class);
        CreditCard newCreditCard = mock(CreditCard.class);
        List<CreditCard> existingCreditCards = new ArrayList<>();
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> supplier = () -> parameter;

        when(supplierService.parameterStringSupplier(USER_ID, userId))
                .thenReturn(supplier);
        when(queryService.getEntity(UserDetails.class, supplier))
                .thenReturn(userProfile);
        when(transformationFunctionService.getEntity(CreditCard.class, creditCardDto))
                .thenReturn(newCreditCard);
        when(userProfile.getCreditCardList()).thenReturn(existingCreditCards);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(userProfile);
            return null;
        }).when(creditCardDao).executeConsumer(any(Consumer.class));

        creditCardManager.saveCreditCardByUserId(userId, creditCardDto);

        verify(creditCardDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void saveCreditCardByUserId_shouldThrowException() {
        CreditCardDto creditCardDto = getTestCreditCardDto();
        String userId = "abc";

        UserDetails userProfile = mock(UserDetails.class);
        CreditCard newCreditCard = mock(CreditCard.class);
        List<CreditCard> existingCreditCards = new ArrayList<>(){{
            add(newCreditCard);
        }};
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> supplier = () -> parameter;

        when(supplierService.parameterStringSupplier(USER_ID, userId))
                .thenReturn(supplier);
        when(queryService.getEntity(UserDetails.class, supplier))
                .thenReturn(userProfile);
        when(transformationFunctionService.getEntity(CreditCard.class, creditCardDto))
                .thenReturn(newCreditCard);
        when(userProfile.getCreditCardList()).thenReturn(existingCreditCards);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(userProfile);
            return null;
        }).when(creditCardDao).executeConsumer(any(Consumer.class));

        assertThrows(RuntimeException.class, () -> creditCardManager.saveCreditCardByUserId(userId, creditCardDto));

        verify(creditCardDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void saveCreditCardByOrderId_shouldSaveCreditCreditCard() {
        CreditCardDto creditCardDto = getTestCreditCardDto();
        String orderId = "abc";

        OrderArticularItem orderItemDataOption = mock(OrderArticularItem.class);
        Payment payment = mock(Payment.class);
        CreditCard creditCard = mock(CreditCard.class);
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> supplier = () -> parameter;

        when(supplierService.parameterStringSupplier(ORDER_ID, orderId))
                .thenReturn(supplier);
        when(queryService.getEntity(OrderArticularItem.class, supplier))
                .thenReturn(orderItemDataOption);
        when(transformationFunctionService.getEntity(CreditCard.class, creditCardDto))
                .thenReturn(creditCard);
        when(orderItemDataOption.getPayment()).thenReturn(payment);
        when(payment.getCreditCard()).thenReturn(creditCard);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(payment);
            return null;
        }).when(creditCardDao).executeConsumer(any(Consumer.class));

        creditCardManager.saveCreditCardByOrderId(orderId, creditCardDto);

        verify(creditCardDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void updateCreditCardByUserId_shouldUpdateCreditCreditCard() {
        String userId = "abc";
        CreditCardDto creditCardDto = getTestCreditCardDto();
        UserDetails userProfile = mock(UserDetails.class);
        CreditCard creditCard = getTestCreditCard();
        List<CreditCard> creditCardList = List.of(creditCard);
        Supplier<Parameter> parameterSupplier = mock(Supplier.class);
        when(supplierService.parameterStringSupplier(USER_ID, userId))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(eq(UserDetails.class), any(Supplier.class)))
                .thenReturn(userProfile);
        when(transformationFunctionService.getEntity(CreditCard.class, creditCardDto))
                .thenReturn(creditCard);
        when(userProfile.getCreditCardList()).thenReturn(creditCardList);

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(creditCard);
            return null;
        }).when(creditCardDao).executeConsumer(any(Consumer.class));

        creditCardManager.updateCreditCardByUserId(userId, creditCardDto);

        verify(creditCardDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void updateCreditCardByOrderId_shouldUpdateCreditCreditCard() {
        String orderId = "abc";
        CreditCardDto creditCardDto = getTestCreditCardDto();
        OrderArticularItem orderItemDataOption = mock(OrderArticularItem.class);
        Payment payment = mock(Payment.class);
        CreditCard creditCard = getTestCreditCard();
        Supplier<Parameter> parameterSupplier = mock(Supplier.class);
        when(supplierService.parameterStringSupplier(ORDER_ID, orderId))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(eq(OrderArticularItem.class), any(Supplier.class)))
                .thenReturn(orderItemDataOption);
        when(transformationFunctionService.getEntity(CreditCard.class, creditCardDto))
                .thenReturn(creditCard);
        when(orderItemDataOption.getPayment()).thenReturn(payment);
        when(payment.getCreditCard()).thenReturn(creditCard);

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(payment);
            return null;
        }).when(creditCardDao).executeConsumer(any(Consumer.class));

        creditCardManager.updateCreditCardByOrderId(orderId, creditCardDto);

        verify(creditCardDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void updateCreditCardByOrderId_shouldThrowException() {
        String orderId = "abc";
        CreditCardDto creditCardDto = getTestCreditCardDto();
        OrderArticularItem orderItemDataOption = mock(OrderArticularItem.class);
        Payment payment = mock(Payment.class);
        CreditCard creditCard = getTestCreditCard();
        Supplier<Parameter> parameterSupplier = mock(Supplier.class);
        when(supplierService.parameterStringSupplier(ORDER_ID, orderId))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(eq(OrderArticularItem.class), any(Supplier.class)))
                .thenReturn(orderItemDataOption);
        when(transformationFunctionService.getEntity(CreditCard.class, creditCardDto))
                .thenReturn(creditCard);
        when(orderItemDataOption.getPayment()).thenReturn(payment);
        when(payment.getCreditCard()).thenReturn(CreditCard.builder()
                .cardNumber("111")
                .build());

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(payment);
            return null;
        }).when(creditCardDao).executeConsumer(any(Consumer.class));

        assertThrows(RuntimeException.class, () -> creditCardManager.updateCreditCardByOrderId(orderId, creditCardDto));

        verify(creditCardDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void deleteCreditCardByUserId_shouldDeleteCreditCard() {
        String userId = "123";
        MultipleFieldsSearchDtoDelete multipleFieldsSearchDtoDelete = MultipleFieldsSearchDtoDelete.builder()
                .mainSearchField(userId)
                .innerSearchField("123")
                .build();

        UserDetails userProfile = mock(UserDetails.class);
        CreditCard creditCard = mock(CreditCard.class);
        List<CreditCard> existingCreditCards = new ArrayList<>(){{
            add(creditCard);
        }};
        when(creditCard.getCardNumber()).thenReturn("123");
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> supplier = () -> parameter;

        when(supplierService.parameterStringSupplier(USER_ID, multipleFieldsSearchDtoDelete.getMainSearchField()))
                .thenReturn(supplier);
        when(queryService.getEntity(UserDetails.class, supplier))
                .thenReturn(userProfile);
        when(userProfile.getCreditCardList()).thenReturn(existingCreditCards);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).remove(creditCard);
            return null;
        }).when(creditCardDao).executeConsumer(any(Consumer.class));

        creditCardManager.deleteCreditCardByUserId(multipleFieldsSearchDtoDelete);

        verify(creditCardDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void deleteCardByOrderId_shouldDeleteCreditCard() {
        String order_id = "123";
        MultipleFieldsSearchDtoDelete multipleFieldsSearchDtoDelete = MultipleFieldsSearchDtoDelete.builder()
                .mainSearchField(order_id)
                .innerSearchField("123")
                .build();

        OrderArticularItem orderItemDataOption = mock(OrderArticularItem.class);
        Payment payment = mock(Payment.class);
        when(orderItemDataOption.getPayment()).thenReturn(payment);
        CreditCard creditCard = mock(CreditCard.class);
        when(payment.getCreditCard()).thenReturn(creditCard);
        when(creditCard.getCardNumber()).thenReturn("111");
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> supplier = () -> parameter;

        when(supplierService.parameterStringSupplier(ORDER_ID, multipleFieldsSearchDtoDelete.getMainSearchField()))
                .thenReturn(supplier);
        when(queryService.getEntity(OrderArticularItem.class, supplier))
                .thenReturn(orderItemDataOption);
        when(payment.getCreditCard()).thenReturn(creditCard);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).remove(creditCard);
            return null;
        }).when(creditCardDao).executeConsumer(any(Consumer.class));

        assertThrows(RuntimeException.class, () -> creditCardManager.deleteCreditCardByOrderId(multipleFieldsSearchDtoDelete));

        verify(creditCardDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void getCardListByUserId_shouldReturnResponseCardDtoList() {
        String userId = "123";
        CreditCard creditCard = getTestCreditCard();
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        when(creditCardDao.getEntityList(parameter))
                .thenReturn(List.of(creditCard));
        when(supplierService.parameterStringSupplier(USER_ID, userId))
                .thenReturn(parameterSupplier);
        when(transformationFunctionService.getTransformationFunction(CreditCard.class, ResponseCreditCardDto.class))
                .thenReturn(creditCard1 -> ResponseCreditCardDto.builder()
                        .isActive(creditCard1.isActive())
                        .cardNumber(creditCard1.getCardNumber())
                        .monthOfExpire(creditCard1.getMonthOfExpire())
                        .yearOfExpire(creditCard1.getYearOfExpire())
                        .ownerName(creditCard1.getOwnerName())
                        .ownerSecondName(creditCard1.getOwnerSecondName())
                        .build());

        List<ResponseCreditCardDto> responseCreditCardDtoList = creditCardManager.getCardListByUserId(userId);

        assertEquals(1, responseCreditCardDtoList.size());
        responseCreditCardDtoList.forEach(result -> {
            assertEquals(creditCard.getCardNumber(), result.getCardNumber());
            assertEquals(creditCard.getMonthOfExpire(), result.getMonthOfExpire());
            assertEquals(creditCard.getYearOfExpire(), result.getYearOfExpire());
            assertTrue(result.isActive());
            assertEquals(creditCard.getOwnerName(), result.getOwnerName());
            assertEquals(creditCard.getOwnerSecondName(), result.getOwnerSecondName());
        });
    }

    @Test
    void getCardByUserId_shouldReturnResponseCardDto() {
        String order_id = "123";
        MultipleFieldsSearchDtoDelete multipleFieldsSearchDtoDelete = MultipleFieldsSearchDtoDelete.builder()
                .mainSearchField(order_id)
                .innerSearchField("123")
                .build();

        OrderArticularItem orderItemDataOption = mock(OrderArticularItem.class);
        Payment payment = mock(Payment.class);
        when(orderItemDataOption.getPayment()).thenReturn(payment);
        CreditCard creditCard = mock(CreditCard.class);
        when(payment.getCreditCard()).thenReturn(creditCard);
        when(creditCard.getCardNumber()).thenReturn("123");
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> supplier = () -> parameter;

        when(supplierService.parameterStringSupplier(ORDER_ID, multipleFieldsSearchDtoDelete.getMainSearchField()))
                .thenReturn(supplier);
        when(queryService.getEntity(OrderArticularItem.class, supplier))
                .thenReturn(orderItemDataOption);
        when(payment.getCreditCard()).thenReturn(creditCard);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).remove(creditCard);
            return null;
        }).when(creditCardDao).executeConsumer(any(Consumer.class));

        creditCardManager.deleteCreditCardByOrderId(multipleFieldsSearchDtoDelete);

        verify(creditCardDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void getCardByOrderId_shouldReturnResponseCardDto() {
        String orderId = "123";
        Parameter parameter = mock(Parameter.class);

        CreditCard creditCard = getTestCreditCard();
        ResponseCreditCardDto responseCreditCardDto = getTestResponseCardDto();

        Function<CreditCard, ResponseCreditCardDto> function = creditCard1 -> ResponseCreditCardDto.builder()
                .isActive(creditCard1.isActive())
                .cardNumber(creditCard1.getCardNumber())
                .monthOfExpire(creditCard1.getMonthOfExpire())
                .yearOfExpire(creditCard1.getYearOfExpire())
                .ownerName(creditCard1.getOwnerName())
                .ownerSecondName(creditCard1.getOwnerSecondName())
                .build();

        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(creditCardDao.getEntityGraph(anyString(), eq(parameter))).thenReturn(creditCard);
        when(supplierService.parameterStringSupplier(ORDER_ID, orderId))
                .thenReturn(parameterSupplier);
        when(transformationFunctionService.getTransformationFunction(CreditCard.class, ResponseCreditCardDto.class))
                .thenReturn(function);

        ResponseCreditCardDto result = creditCardManager.getCardByOrderId(orderId);

        assertNotNull(result);
        assertEquals(creditCard.getCardNumber(), result.getCardNumber());
        assertEquals(creditCard.getMonthOfExpire(), result.getMonthOfExpire());
        assertEquals(creditCard.getYearOfExpire(), result.getYearOfExpire());
        assertTrue(result.isActive());
        assertEquals(creditCard.getOwnerName(), result.getOwnerName());
        assertEquals(creditCard.getOwnerSecondName(), result.getOwnerSecondName());
    }

    @Test
    void getCardByOrderId_shouldReturnNull() {
        Parameter parameter = mock(Parameter.class);
        String orderId = "nonexistent";

        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier(ORDER_ID, orderId))
                .thenReturn(parameterSupplier);
        when(creditCardDao.getOptionalEntity(parameter)).thenReturn(Optional.empty());
        when(transformationFunctionService.getTransformationFunction(CreditCard.class, ResponseCreditCardDto.class))
                .thenReturn(creditCard1 -> ResponseCreditCardDto.builder()
                        .isActive(creditCard1.isActive())
                        .cardNumber(creditCard1.getCardNumber())
                        .monthOfExpire(creditCard1.getMonthOfExpire())
                        .yearOfExpire(creditCard1.getYearOfExpire())
                        .ownerName(creditCard1.getOwnerName())
                        .ownerSecondName(creditCard1.getOwnerSecondName())
                        .build());
        assertTrue(() -> creditCardManager.getCardByOrderId(orderId) == null);
    }

    @Test
    void getAllCards_shouldReturnResponseCardDtoList() {
        CreditCard creditCard = getTestCreditCard();
        Parameter parameter = mock(Parameter.class);

        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(creditCardDao.getEntityList()).thenReturn(List.of(creditCard));
        when(supplierService.parameterStringSupplier(ORDER_ID, "123"))
                .thenReturn(parameterSupplier);
        when(transformationFunctionService.getTransformationFunction(CreditCard.class, ResponseCreditCardDto.class))
                .thenReturn(creditCard1 -> ResponseCreditCardDto.builder()
                        .isActive(creditCard1.isActive())
                        .cardNumber(creditCard1.getCardNumber())
                        .monthOfExpire(creditCard1.getMonthOfExpire())
                        .yearOfExpire(creditCard1.getYearOfExpire())
                        .ownerName(creditCard1.getOwnerName())
                        .ownerSecondName(creditCard1.getOwnerSecondName())
                        .build());

        List<ResponseCreditCardDto> responseCreditCardDtoList = creditCardManager.getAllCards();

        assertEquals(1, responseCreditCardDtoList.size());
        responseCreditCardDtoList.forEach(result -> {
            assertEquals(creditCard.getCardNumber(), result.getCardNumber());
            assertEquals(creditCard.getMonthOfExpire(), result.getMonthOfExpire());
            assertEquals(creditCard.getYearOfExpire(), result.getYearOfExpire());
            assertTrue(result.isActive());
            assertEquals(creditCard.getOwnerName(), result.getOwnerName());
            assertEquals(creditCard.getOwnerSecondName(), result.getOwnerSecondName());
        });
    }

    private ResponseCreditCardDto getTestResponseCardDto() {
        return ResponseCreditCardDto.builder()
                .cardNumber("1234567890123456")
                .monthOfExpire(6)
                .yearOfExpire(28)
                .isActive(true)
                .ownerName("John")
                .ownerSecondName("Doe")
                .build();
    }

    private CreditCardDto getTestCreditCardDto() {
        return CreditCardDto.builder()
                .cardNumber("1234567890123456")
                .monthOfExpire(6)
                .yearOfExpire(28)
                .cvv("123")
                .ownerName("John")
                .ownerSecondName("Doe")
                .build();
    }

    private CreditCard getTestCreditCard() {
        return CreditCard.builder()
                .id(1L)
                .cardNumber("1234567890123456")
                .monthOfExpire(6)
                .yearOfExpire(28)
                .cvv("123")
                .ownerName("John")
                .ownerSecondName("Doe")
                .isActive(true)
                .build();
    }

}
