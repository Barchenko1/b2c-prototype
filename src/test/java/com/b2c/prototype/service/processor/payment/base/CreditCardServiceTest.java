package com.b2c.prototype.service.processor.payment.base;

import com.b2c.prototype.dao.payment.ICreditCardDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.delete.MultipleFieldsSearchDtoDelete;
import com.b2c.prototype.modal.dto.request.CreditCardDto;
import com.b2c.prototype.modal.dto.request.CreditCardDtoSearchField;
import com.b2c.prototype.modal.dto.response.ResponseCreditCardDto;
import com.b2c.prototype.modal.dto.update.CreditCardDtoUpdate;
import com.b2c.prototype.modal.entity.order.OrderItemData;
import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.b2c.prototype.modal.entity.payment.Payment;
import com.b2c.prototype.modal.entity.user.UserProfile;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreditCardServiceTest {

    @Mock
    private ICreditCardDao creditCardDao;
    @Mock
    private IQueryService queryService;
    @Mock
    private ITransformationFunctionService transformationFunctionService;
    @Mock
    private ISupplierService supplierService;
    @InjectMocks
    private CreditCardService creditCardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveCreditCardByUserId_shouldSaveCreditCreditCard() {
        CreditCardDtoSearchField creditCardDtoSearchField = CreditCardDtoSearchField.builder()
                .newEntity(getTestCreditCardDto())
                .searchField("abc")
                .build();

        UserProfile userProfile = mock(UserProfile.class);
        CreditCard newCreditCard = mock(CreditCard.class);
        List<CreditCard> existingCreditCards = new ArrayList<>();
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> supplier = () -> parameter;

        when(supplierService.parameterStringSupplier("user_id", creditCardDtoSearchField.getSearchField()))
                .thenReturn(supplier);
        when(queryService.getEntity(UserProfile.class, supplier))
                .thenReturn(userProfile);
        when(transformationFunctionService.getEntity(CreditCard.class, creditCardDtoSearchField.getNewEntity()))
                .thenReturn(newCreditCard);
        when(userProfile.getCreditCardList()).thenReturn(existingCreditCards);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(userProfile);
            return null;
        }).when(creditCardDao).executeConsumer(any(Consumer.class));

        creditCardService.saveCreditCardByUserId(creditCardDtoSearchField);

        verify(creditCardDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void saveCreditCardByUserId_shouldThrowException() {
        CreditCardDtoSearchField creditCardDtoSearchField = CreditCardDtoSearchField.builder()
                .newEntity(getTestCreditCardDto())
                .searchField("abc")
                .build();

        UserProfile userProfile = mock(UserProfile.class);
        CreditCard newCreditCard = mock(CreditCard.class);
        List<CreditCard> existingCreditCards = new ArrayList<>(){{
            add(newCreditCard);
        }};
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> supplier = () -> parameter;

        when(supplierService.parameterStringSupplier("user_id", creditCardDtoSearchField.getSearchField()))
                .thenReturn(supplier);
        when(queryService.getEntity(UserProfile.class, supplier))
                .thenReturn(userProfile);
        when(transformationFunctionService.getEntity(CreditCard.class, creditCardDtoSearchField.getNewEntity()))
                .thenReturn(newCreditCard);
        when(userProfile.getCreditCardList()).thenReturn(existingCreditCards);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(userProfile);
            return null;
        }).when(creditCardDao).executeConsumer(any(Consumer.class));

        assertThrows(RuntimeException.class, () -> creditCardService.saveCreditCardByUserId(creditCardDtoSearchField));

        verify(creditCardDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void saveCreditCardByOrderId_shouldSaveCreditCreditCard() {
        CreditCardDtoSearchField creditCardDtoSearchField = CreditCardDtoSearchField.builder()
                .newEntity(getTestCreditCardDto())
                .searchField("abc")
                .build();

        OrderItemData orderItemData = mock(OrderItemData.class);
        Payment payment = mock(Payment.class);
        CreditCard creditCard = mock(CreditCard.class);
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> supplier = () -> parameter;

        when(supplierService.parameterStringSupplier("order_id", creditCardDtoSearchField.getSearchField()))
                .thenReturn(supplier);
        when(queryService.getEntity(OrderItemData.class, supplier))
                .thenReturn(orderItemData);
        when(transformationFunctionService.getEntity(CreditCard.class, creditCardDtoSearchField.getNewEntity()))
                .thenReturn(creditCard);
        when(orderItemData.getPayment()).thenReturn(payment);
        when(payment.getCreditCard()).thenReturn(creditCard);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(payment);
            return null;
        }).when(creditCardDao).executeConsumer(any(Consumer.class));

        creditCardService.saveCreditCardByOrderId(creditCardDtoSearchField);

        verify(creditCardDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void updateCreditCardByUserId_shouldUpdateCreditCreditCard() {
        CreditCardDtoUpdate creditCardDtoUpdate = createTestCreditCardDtoUpdate();
        UserProfile userProfile = mock(UserProfile.class);
        CreditCard creditCard = getTestCreditCard();
        List<CreditCard> creditCardList = List.of(creditCard);
        Supplier<Parameter> parameterSupplier = mock(Supplier.class);
        when(supplierService.parameterStringSupplier("user_id", creditCardDtoUpdate.getSearchField()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(eq(UserProfile.class), any(Supplier.class)))
                .thenReturn(userProfile);
        when(transformationFunctionService.getEntity(CreditCard.class, creditCardDtoUpdate.getNewEntity()))
                .thenReturn(creditCard);
        when(userProfile.getCreditCardList()).thenReturn(creditCardList);

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(creditCard);
            return null;
        }).when(creditCardDao).executeConsumer(any(Consumer.class));

        creditCardService.updateCreditCardByUserId(creditCardDtoUpdate);

        verify(creditCardDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void updateCreditCardByOrderId_shouldUpdateCreditCreditCard() {
        CreditCardDtoUpdate creditCardDtoUpdate = createTestCreditCardDtoUpdate();
        OrderItemData orderItemData = mock(OrderItemData.class);
        Payment payment = mock(Payment.class);
        CreditCard creditCard = getTestCreditCard();
        Supplier<Parameter> parameterSupplier = mock(Supplier.class);
        when(supplierService.parameterStringSupplier("order_id", creditCardDtoUpdate.getSearchField()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(eq(OrderItemData.class), any(Supplier.class)))
                .thenReturn(orderItemData);
        when(transformationFunctionService.getEntity(CreditCard.class, creditCardDtoUpdate.getNewEntity()))
                .thenReturn(creditCard);
        when(orderItemData.getPayment()).thenReturn(payment);
        when(payment.getCreditCard()).thenReturn(creditCard);

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(payment);
            return null;
        }).when(creditCardDao).executeConsumer(any(Consumer.class));

        creditCardService.updateCreditCardByOrderId(creditCardDtoUpdate);

        verify(creditCardDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void updateCreditCardByOrderId_shouldThrowException() {
        CreditCardDtoUpdate creditCardDtoUpdate = createTestCreditCardDtoUpdate();
        OrderItemData orderItemData = mock(OrderItemData.class);
        Payment payment = mock(Payment.class);
        CreditCard creditCard = getTestCreditCard();
        Supplier<Parameter> parameterSupplier = mock(Supplier.class);
        when(supplierService.parameterStringSupplier("order_id", creditCardDtoUpdate.getSearchField()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(eq(OrderItemData.class), any(Supplier.class)))
                .thenReturn(orderItemData);
        when(transformationFunctionService.getEntity(CreditCard.class, creditCardDtoUpdate.getNewEntity()))
                .thenReturn(creditCard);
        when(orderItemData.getPayment()).thenReturn(payment);
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

        assertThrows(RuntimeException.class, () -> creditCardService.updateCreditCardByOrderId(creditCardDtoUpdate));

        verify(creditCardDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void deleteCreditCardByUserId_shouldDeleteCreditCard() {
        String userId = "123";
        MultipleFieldsSearchDtoDelete multipleFieldsSearchDtoDelete = MultipleFieldsSearchDtoDelete.builder()
                .mainSearchField(userId)
                .innerSearchField("123")
                .build();

        UserProfile userProfile = mock(UserProfile.class);
        CreditCard creditCard = mock(CreditCard.class);
        List<CreditCard> existingCreditCards = new ArrayList<>(){{
            add(creditCard);
        }};
        when(creditCard.getCardNumber()).thenReturn("123");
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> supplier = () -> parameter;

        when(supplierService.parameterStringSupplier("user_id", multipleFieldsSearchDtoDelete.getMainSearchField()))
                .thenReturn(supplier);
        when(queryService.getEntity(UserProfile.class, supplier))
                .thenReturn(userProfile);
        when(userProfile.getCreditCardList()).thenReturn(existingCreditCards);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).remove(creditCard);
            return null;
        }).when(creditCardDao).executeConsumer(any(Consumer.class));

        creditCardService.deleteCreditCardByUserId(multipleFieldsSearchDtoDelete);

        verify(creditCardDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void deleteCardByOrderId_shouldDeleteCreditCard() {
        String order_id = "123";
        MultipleFieldsSearchDtoDelete multipleFieldsSearchDtoDelete = MultipleFieldsSearchDtoDelete.builder()
                .mainSearchField(order_id)
                .innerSearchField("123")
                .build();

        OrderItemData orderItemData = mock(OrderItemData.class);
        Payment payment = mock(Payment.class);
        when(orderItemData.getPayment()).thenReturn(payment);
        CreditCard creditCard = mock(CreditCard.class);
        when(payment.getCreditCard()).thenReturn(creditCard);
        when(creditCard.getCardNumber()).thenReturn("111");
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> supplier = () -> parameter;

        when(supplierService.parameterStringSupplier("order_id", multipleFieldsSearchDtoDelete.getMainSearchField()))
                .thenReturn(supplier);
        when(queryService.getEntity(OrderItemData.class, supplier))
                .thenReturn(orderItemData);
        when(payment.getCreditCard()).thenReturn(creditCard);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).remove(creditCard);
            return null;
        }).when(creditCardDao).executeConsumer(any(Consumer.class));

        assertThrows(RuntimeException.class, () -> creditCardService.deleteCreditCardByOrderId(multipleFieldsSearchDtoDelete));

        verify(creditCardDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void getCardListByUserId_shouldReturnResponseCardDtoList() {
        String userId = "123";
        OneFieldEntityDto oneFieldEntityDto = OneFieldEntityDto.builder()
                .value(userId)
                .build();
        CreditCard creditCard = getTestCreditCard();
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        when(creditCardDao.getEntityList(parameter))
                .thenReturn(List.of(creditCard));
        when(supplierService.parameterStringSupplier("user_id", userId))
                .thenReturn(parameterSupplier);
        when(transformationFunctionService.getTransformationFunction(CreditCard.class, ResponseCreditCardDto.class))
                .thenReturn(creditCard1 -> ResponseCreditCardDto.builder()
                        .isActive(creditCard1.isActive())
                        .cardNumber(creditCard1.getCardNumber())
                        .dateOfExpire(creditCard1.getDateOfExpire())
                        .ownerName(creditCard1.getOwnerName())
                        .ownerSecondName(creditCard1.getOwnerSecondName())
                        .build());

        List<ResponseCreditCardDto> responseCreditCardDtoList = creditCardService.getCardListByUserId(oneFieldEntityDto);

        assertEquals(1, responseCreditCardDtoList.size());
        responseCreditCardDtoList.forEach(result -> {
            assertEquals(creditCard.getCardNumber(), result.getCardNumber());
            assertEquals(creditCard.getDateOfExpire(), result.getDateOfExpire());
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

        OrderItemData orderItemData = mock(OrderItemData.class);
        Payment payment = mock(Payment.class);
        when(orderItemData.getPayment()).thenReturn(payment);
        CreditCard creditCard = mock(CreditCard.class);
        when(payment.getCreditCard()).thenReturn(creditCard);
        when(creditCard.getCardNumber()).thenReturn("123");
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> supplier = () -> parameter;

        when(supplierService.parameterStringSupplier("order_id", multipleFieldsSearchDtoDelete.getMainSearchField()))
                .thenReturn(supplier);
        when(queryService.getEntity(OrderItemData.class, supplier))
                .thenReturn(orderItemData);
        when(payment.getCreditCard()).thenReturn(creditCard);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).remove(creditCard);
            return null;
        }).when(creditCardDao).executeConsumer(any(Consumer.class));

        creditCardService.deleteCreditCardByOrderId(multipleFieldsSearchDtoDelete);

        verify(creditCardDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void getCardByOrderId_shouldReturnResponseCardDto() {
        String orderId = "123";
        Parameter parameter = mock(Parameter.class);
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto(orderId);

        CreditCard creditCard = getTestCreditCard();

        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(creditCardDao.getEntity(parameter)).thenReturn(creditCard);
        when(supplierService.parameterStringSupplier("order_id", orderId))
                .thenReturn(parameterSupplier);
        when(transformationFunctionService.getTransformationFunction(CreditCard.class, ResponseCreditCardDto.class))
                .thenReturn(creditCard1 -> ResponseCreditCardDto.builder()
                        .isActive(creditCard1.isActive())
                        .cardNumber(creditCard1.getCardNumber())
                        .dateOfExpire(creditCard1.getDateOfExpire())
                        .ownerName(creditCard1.getOwnerName())
                        .ownerSecondName(creditCard1.getOwnerSecondName())
                        .build());

        ResponseCreditCardDto result = creditCardService.getCardByOrderId(oneFieldEntityDto);

        assertNotNull(result);
        assertEquals(creditCard.getCardNumber(), result.getCardNumber());
        assertEquals(creditCard.getDateOfExpire(), result.getDateOfExpire());
        assertTrue(result.isActive());
        assertEquals(creditCard.getOwnerName(), result.getOwnerName());
        assertEquals(creditCard.getOwnerSecondName(), result.getOwnerSecondName());
    }

    @Test
    void getCardByOrderId_shouldReturnNull() {
        Parameter parameter = mock(Parameter.class);
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto("nonexistent");

        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier("order_id", oneFieldEntityDto.getValue()))
                .thenReturn(parameterSupplier);
        when(creditCardDao.getOptionalEntity(parameter)).thenReturn(Optional.empty());
        when(transformationFunctionService.getTransformationFunction(CreditCard.class, ResponseCreditCardDto.class))
                .thenReturn(creditCard1 -> ResponseCreditCardDto.builder()
                        .isActive(creditCard1.isActive())
                        .cardNumber(creditCard1.getCardNumber())
                        .dateOfExpire(creditCard1.getDateOfExpire())
                        .ownerName(creditCard1.getOwnerName())
                        .ownerSecondName(creditCard1.getOwnerSecondName())
                        .build());
        assertTrue(() -> creditCardService.getCardByOrderId(oneFieldEntityDto) == null);
    }

    @Test
    void getAllCards_shouldReturnResponseCardDtoList() {
        CreditCard creditCard = getTestCreditCard();
        Parameter parameter = mock(Parameter.class);

        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(creditCardDao.getEntityList()).thenReturn(List.of(creditCard));
        when(supplierService.parameterStringSupplier("order_id", "123"))
                .thenReturn(parameterSupplier);
        when(transformationFunctionService.getTransformationFunction(CreditCard.class, ResponseCreditCardDto.class))
                .thenReturn(creditCard1 -> ResponseCreditCardDto.builder()
                        .isActive(creditCard1.isActive())
                        .cardNumber(creditCard1.getCardNumber())
                        .dateOfExpire(creditCard1.getDateOfExpire())
                        .ownerName(creditCard1.getOwnerName())
                        .ownerSecondName(creditCard1.getOwnerSecondName())
                        .build());

        List<ResponseCreditCardDto> responseCreditCardDtoList = creditCardService.getAllCards();

        assertEquals(1, responseCreditCardDtoList.size());
        responseCreditCardDtoList.forEach(result -> {
            assertEquals(creditCard.getCardNumber(), result.getCardNumber());
            assertEquals(creditCard.getDateOfExpire(), result.getDateOfExpire());
            assertTrue(result.isActive());
            assertEquals(creditCard.getOwnerName(), result.getOwnerName());
            assertEquals(creditCard.getOwnerSecondName(), result.getOwnerSecondName());
        });
    }

    private ResponseCreditCardDto getTestResponseCardDto() {
        return ResponseCreditCardDto.builder()
                .cardNumber("1234567890123456")
                .dateOfExpire("06/28")
                .isActive(true)
                .ownerName("John")
                .ownerSecondName("Doe")
                .build();
    }

    private CreditCardDto getTestCreditCardDto() {
        return CreditCardDto.builder()
                .cardNumber("1234567890123456")
                .dateOfExpire("06/28")
                .cvv("123")
                .ownerName("John")
                .ownerSecondName("Doe")
                .build();
    }

    private CreditCard getTestCreditCard() {
        return CreditCard.builder()
                .id(1L)
                .cardNumber("1234567890123456")
                .dateOfExpire("06/28")
                .cvv("123")
                .ownerName("John")
                .ownerSecondName("Doe")
                .isActive(true)
                .build();
    }

    private CreditCardDtoUpdate createTestCreditCardDtoUpdate() {
        return CreditCardDtoUpdate.builder()
                .oldEntity(CreditCardDto.builder()
                        .cardNumber("1234567890123456")
                        .dateOfExpire("06/29")
                        .cvv("789")
                        .ownerName("John")
                        .ownerSecondName("Doe")
                        .build())
                .searchField("search")
                .newEntity(CreditCardDto.builder()
                        .cardNumber("0123456789012345")
                        .dateOfExpire("06/29")
                        .cvv("789")
                        .ownerName("John")
                        .ownerSecondName("Doe")
                        .build())
                .build();
    }
}
