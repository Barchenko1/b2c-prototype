package com.b2c.prototype.manager.payment.base;

import com.b2c.prototype.manager.userdetails.basic.UserCreditCardManager;
import com.b2c.prototype.modal.dto.payload.order.CreditCardDto;
import com.b2c.prototype.modal.dto.payload.order.ResponseCreditCardDto;
import com.b2c.prototype.modal.dto.payload.user.ResponseUserCreditCardDto;
import com.b2c.prototype.modal.entity.order.DeliveryArticularItemQuantity;
import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.b2c.prototype.modal.entity.payment.Payment;
import com.b2c.prototype.modal.entity.user.UserCreditCard;
import com.b2c.prototype.modal.entity.user.UserDetails;
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.tm.core.finder.parameter.Parameter;
import com.tm.core.process.dao.common.ITransactionEntityDao;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreditCardManagerTest {

    @Mock
    private ITransactionEntityDao creditCardDao;
    @Mock
    private ITransformationFunctionService transformationFunctionService;
    @InjectMocks
    private UserCreditCardManager creditCardManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveCreditCardByUserId_shouldSaveCreditCreditCard() {
        CreditCardDto creditCardDto = getTestCreditCardDto();
        String userId = "abc";

        UserDetails userDetails = mock(UserDetails.class);
        CreditCard newCreditCard = mock(CreditCard.class);
        Set<UserCreditCard> existingUserCreditCards = new HashSet<>();
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> supplier = () -> parameter;

        
//        when(queryService.getEntity(UserDetails.class, supplier))
//                .thenReturn(userDetails);
        when(transformationFunctionService.getEntity(CreditCard.class, creditCardDto))
                .thenReturn(newCreditCard);
        when(userDetails.getUserCreditCards()).thenReturn(existingUserCreditCards);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(userDetails);
            return null;
        }).when(creditCardDao).executeConsumer(any(Consumer.class));

//        creditCardManager.saveCreditCardByUserId(userId, creditCardDto);

        verify(creditCardDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void saveCreditCardByUserId_shouldThrowException() {
        CreditCardDto creditCardDto = getTestCreditCardDto();
        String userId = "abc";

        UserDetails userDetails = mock(UserDetails.class);
        CreditCard newCreditCard = mock(CreditCard.class);
        UserCreditCard userCreditCard = mock(UserCreditCard.class);
        Set<UserCreditCard> existingUserCreditCards = new HashSet<>(){{
            add(userCreditCard);
        }};
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> supplier = () -> parameter;

        
//        when(queryService.getEntity(UserDetails.class, supplier))
//                .thenReturn(userDetails);
        when(transformationFunctionService.getEntity(CreditCard.class, creditCardDto))
                .thenReturn(newCreditCard);
        when(userDetails.getUserCreditCards()).thenReturn(existingUserCreditCards);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(userDetails);
            return null;
        }).when(creditCardDao).executeConsumer(any(Consumer.class));

//        assertThrows(RuntimeException.class, () -> creditCardManager.saveUpdateCreditCardByOrderId(userId, creditCardDto));

        verify(creditCardDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void saveCreditCardByOrderId_shouldSaveCreditCreditCard() {
        CreditCardDto creditCardDto = getTestCreditCardDto();
        String orderId = "abc";

        DeliveryArticularItemQuantity orderItemDataOption = mock(DeliveryArticularItemQuantity.class);
        Payment payment = mock(Payment.class);
        CreditCard creditCard = mock(CreditCard.class);
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> supplier = () -> parameter;


//        when(queryService.getEntity(DeliveryArticularItemQuantity.class, supplier))
//                .thenReturn(orderItemDataOption);
        when(transformationFunctionService.getEntity(CreditCard.class, creditCardDto))
                .thenReturn(creditCard);
//        when(orderItemDataOption.getPayment()).thenReturn(payment);
        when(payment.getCreditCard()).thenReturn(creditCard);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(payment);
            return null;
        }).when(creditCardDao).executeConsumer(any(Consumer.class));

//        creditCardManager.saveUpdateCreditCardByOrderId(orderId, creditCardDto);

        verify(creditCardDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void updateCreditCardByUserId_shouldUpdateCreditCreditCard() {
        String userId = "abc";
        CreditCardDto creditCardDto = getTestCreditCardDto();
        UserDetails userDetails = mock(UserDetails.class);
        CreditCard creditCard = getTestCreditCard();
        UserCreditCard userCreditCard = UserCreditCard.builder()
                .creditCard(creditCard)
                .isDefault(false)
                .build();
        Set<UserCreditCard> creditCardList = Set.of(userCreditCard);
        Supplier<Parameter> parameterSupplier = mock(Supplier.class);
//        when(queryService.getEntity(eq(UserDetails.class), any(Supplier.class)))
//                .thenReturn(userDetails);
        when(transformationFunctionService.getEntity(CreditCard.class, userCreditCard))
                .thenReturn(creditCard);
        when(userDetails.getUserCreditCards()).thenReturn(creditCardList);

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(creditCard);
            return null;
        }).when(creditCardDao).executeConsumer(any(Consumer.class));

//        creditCardManager.saveUpdateCreditCardByOrderId(userId, creditCardDto);

        verify(creditCardDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void updateCreditCardByOrderId_shouldUpdateCreditCreditCard() {
        String orderId = "abc";
        CreditCardDto creditCardDto = getTestCreditCardDto();
        DeliveryArticularItemQuantity orderItemDataOption = mock(DeliveryArticularItemQuantity.class);
        Payment payment = mock(Payment.class);
        CreditCard creditCard = getTestCreditCard();
        Supplier<Parameter> parameterSupplier = mock(Supplier.class);
        
//        when(queryService.getEntity(eq(DeliveryArticularItemQuantity.class), any(Supplier.class)))
//                .thenReturn(orderItemDataOption);
        when(transformationFunctionService.getEntity(CreditCard.class, creditCardDto))
                .thenReturn(creditCard);
//        when(orderItemDataOption.getPayment()).thenReturn(payment);
        when(payment.getCreditCard()).thenReturn(creditCard);

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(payment);
            return null;
        }).when(creditCardDao).executeConsumer(any(Consumer.class));

//        creditCardManager.saveUpdateCreditCardByOrderId(orderId, creditCardDto);

        verify(creditCardDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void updateCreditCardByOrderId_shouldThrowException() {
        String orderId = "abc";
        CreditCardDto creditCardDto = getTestCreditCardDto();
        DeliveryArticularItemQuantity orderItemDataOption = mock(DeliveryArticularItemQuantity.class);
        Payment payment = mock(Payment.class);
        CreditCard creditCard = getTestCreditCard();
        Supplier<Parameter> parameterSupplier = mock(Supplier.class);
        
//        when(queryService.getEntity(eq(DeliveryArticularItemQuantity.class), any(Supplier.class)))
//                .thenReturn(orderItemDataOption);
        when(transformationFunctionService.getEntity(CreditCard.class, creditCardDto))
                .thenReturn(creditCard);
//        when(orderItemDataOption.getPayment()).thenReturn(payment);
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

//        assertThrows(RuntimeException.class, () -> creditCardManager.saveUpdateCreditCardByOrderId(orderId, creditCardDto));

        verify(creditCardDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void deleteCreditCardByUserId_shouldDeleteCreditCard() {
        String userId = "123";

        UserDetails userDetails = mock(UserDetails.class);
        CreditCard creditCard = mock(CreditCard.class);
        UserCreditCard userCreditCard = mock(UserCreditCard.class);
        Set<UserCreditCard> existingUserCreditCard = new HashSet<>(){{
            add(userCreditCard);
        }};
        when(creditCard.getCardNumber()).thenReturn("123");
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> supplier = () -> parameter;

        
//        when(queryService.getEntity(UserDetails.class, supplier))
//                .thenReturn(userDetails);
        when(userDetails.getUserCreditCards()).thenReturn(existingUserCreditCard);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).remove(creditCard);
            return null;
        }).when(creditCardDao).executeConsumer(any(Consumer.class));

        creditCardManager.deleteCreditCardByUserId(userId, "");

        verify(creditCardDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void getCardListByUserId_shouldReturnResponseCreditCardDtoList() {
        String userId = "123";
        CreditCard creditCard = getTestCreditCard();
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        when(transformationFunctionService.getTransformationFunction(CreditCard.class, ResponseCreditCardDto.class))
                .thenReturn(creditCard1 -> ResponseCreditCardDto.builder()
                        .isActive(creditCard1.isActive())
                        .cardNumber(creditCard1.getCardNumber())
                        .monthOfExpire(creditCard1.getMonthOfExpire())
                        .yearOfExpire(creditCard1.getYearOfExpire())
                        .ownerName(creditCard1.getOwnerName())
                        .ownerSecondName(creditCard1.getOwnerSecondName())
                        .build());

        List<ResponseUserCreditCardDto> responseCreditCardDtoList = creditCardManager.getCreditCardListByUserId(userId);

        assertEquals(1, responseCreditCardDtoList.size());
        responseCreditCardDtoList.forEach(result -> {
//            assertEquals(creditCard.getCardNumber(), result.getCardNumber());
//            assertEquals(creditCard.getMonthOfExpire(), result.getMonthOfExpire());
//            assertEquals(creditCard.getYearOfExpire(), result.getYearOfExpire());
//            assertTrue(result.isActive());
//            assertEquals(creditCard.getOwnerName(), result.getOwnerName());
//            assertEquals(creditCard.getOwnerSecondName(), result.getOwnerSecondName());
        });
    }

    @Test
    void getCardByUserId_shouldReturnResponseCardDto() {
        String orderId = "123";

        DeliveryArticularItemQuantity orderItemDataOption = mock(DeliveryArticularItemQuantity.class);
        Payment payment = mock(Payment.class);
//        when(orderItemDataOption.getPayment()).thenReturn(payment);
        CreditCard creditCard = mock(CreditCard.class);
        when(payment.getCreditCard()).thenReturn(creditCard);
        when(creditCard.getCardNumber()).thenReturn("123");
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> supplier = () -> parameter;

        
//        when(queryService.getEntity(DeliveryArticularItemQuantity.class, supplier))
//                .thenReturn(orderItemDataOption);
        when(payment.getCreditCard()).thenReturn(creditCard);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).remove(creditCard);
            return null;
        }).when(creditCardDao).executeConsumer(any(Consumer.class));

//        creditCardManager.deleteCreditCardByOrderId(orderId);

        verify(creditCardDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void getAllCreditCards_shouldReturnResponseCardDtoList() {
        CreditCard creditCard = getTestCreditCard();
        Parameter parameter = mock(Parameter.class);

        Supplier<Parameter> parameterSupplier = () -> parameter;

        when(transformationFunctionService.getTransformationFunction(CreditCard.class, ResponseCreditCardDto.class))
                .thenReturn(creditCard1 -> ResponseCreditCardDto.builder()
                        .isActive(creditCard1.isActive())
                        .cardNumber(creditCard1.getCardNumber())
                        .monthOfExpire(creditCard1.getMonthOfExpire())
                        .yearOfExpire(creditCard1.getYearOfExpire())
                        .ownerName(creditCard1.getOwnerName())
                        .ownerSecondName(creditCard1.getOwnerSecondName())
                        .build());

        List<ResponseCreditCardDto> responseCreditCardDtoList = creditCardManager.getAllCreditCardByCardNumber("");

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
