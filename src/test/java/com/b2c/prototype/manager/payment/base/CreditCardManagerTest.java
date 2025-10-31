package com.b2c.prototype.manager.payment.base;

import com.b2c.prototype.manager.userdetails.basic.UserCreditCardManager;
import com.b2c.prototype.modal.dto.payload.order.CreditCardDto;
import com.b2c.prototype.modal.dto.payload.user.UserCreditCardDto;
import com.b2c.prototype.modal.entity.order.DeliveryArticularItemQuantity;
import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.b2c.prototype.modal.entity.payment.Payment;
import com.b2c.prototype.modal.entity.user.UserCreditCard;
import com.b2c.prototype.modal.entity.user.UserDetails;

import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreditCardManagerTest {
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

        when(userDetails.getUserCreditCards()).thenReturn(existingUserCreditCards);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(userDetails);
            return null;
        });

//        creditCardManager.saveCreditCardByUserId(userId, creditCardDto);

        
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

        when(userDetails.getUserCreditCards()).thenReturn(existingUserCreditCards);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(userDetails);
            return null;
        });

//        assertThrows(RuntimeException.class, () -> creditCardManager.saveUpdateCreditCardByOrderId(userId, creditCardDto));

        
    }

    @Test
    void saveCreditCardByOrderId_shouldSaveCreditCreditCard() {
        CreditCardDto creditCardDto = getTestCreditCardDto();
        String orderId = "abc";

        DeliveryArticularItemQuantity orderItemDataOption = mock(DeliveryArticularItemQuantity.class);
        Payment payment = mock(Payment.class);
        CreditCard creditCard = mock(CreditCard.class);

//        when(orderItemDataOption.getPayment()).thenReturn(payment);
        when(payment.getCreditCard()).thenReturn(creditCard);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(payment);
            return null;
        });

//        creditCardManager.saveUpdateCreditCardByOrderId(orderId, creditCardDto);

        
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
        when(userDetails.getUserCreditCards()).thenReturn(creditCardList);

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(creditCard);
            return null;
        });

//        creditCardManager.saveUpdateCreditCardByOrderId(userId, creditCardDto);

        
    }

    @Test
    void updateCreditCardByOrderId_shouldUpdateCreditCreditCard() {
        String orderId = "abc";
        CreditCardDto creditCardDto = getTestCreditCardDto();
        DeliveryArticularItemQuantity orderItemDataOption = mock(DeliveryArticularItemQuantity.class);
        Payment payment = mock(Payment.class);
        CreditCard creditCard = getTestCreditCard();

//        when(orderItemDataOption.getPayment()).thenReturn(payment);
        when(payment.getCreditCard()).thenReturn(creditCard);

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(payment);
            return null;
        });

//        creditCardManager.saveUpdateCreditCardByOrderId(orderId, creditCardDto);

        
    }

    @Test
    void updateCreditCardByOrderId_shouldThrowException() {
        String orderId = "abc";
        CreditCardDto creditCardDto = getTestCreditCardDto();
        DeliveryArticularItemQuantity orderItemDataOption = mock(DeliveryArticularItemQuantity.class);
        Payment payment = mock(Payment.class);
        CreditCard creditCard = getTestCreditCard();

        when(payment.getCreditCard()).thenReturn(CreditCard.builder()
                .cardNumber("111")
                .build());

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(payment);
            return null;
        });

//        assertThrows(RuntimeException.class, () -> creditCardManager.saveUpdateCreditCardByOrderId(orderId, creditCardDto));

        
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


        
//        when(queryService.getEntity(UserDetails.class, supplier))
//                .thenReturn(userDetails);
        when(userDetails.getUserCreditCards()).thenReturn(existingUserCreditCard);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).remove(creditCard);
            return null;
        });

        creditCardManager.deleteCreditCardByUserId(userId, "");

        
    }

    @Test
    void getCardListByUserId_shouldReturnResponseCreditCardDtoList() {
        String userId = "123";
        CreditCard creditCard = getTestCreditCard();


//        when(transformationFunctionService.getTransformationFunction(CreditCard.class, ResponseCreditCardDto.class))
//                .thenReturn(creditCard1 -> ResponseCreditCardDto.builder()
//                        .isActive(creditCard1.isActive())
//                        .cardNumber(creditCard1.getCardNumber())
//                        .monthOfExpire(creditCard1.getMonthOfExpire())
//                        .yearOfExpire(creditCard1.getYearOfExpire())
//                        .ownerName(creditCard1.getOwnerName())
//                        .ownerSecondName(creditCard1.getOwnerSecondName())
//                        .build());

        List<UserCreditCardDto> responseCreditCardDtoList = creditCardManager.getCreditCardListByUserId(userId);

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


        
//        when(queryService.getEntity(DeliveryArticularItemQuantity.class, supplier))
//                .thenReturn(orderItemDataOption);
        when(payment.getCreditCard()).thenReturn(creditCard);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).remove(creditCard);
            return null;
        });

//        creditCardManager.deleteCreditCardByOrderId(orderId);

        
    }

    @Test
    void getAllCreditCards_shouldReturnResponseCardDtoList() {
        CreditCard creditCard = getTestCreditCard();

//        when(transformationFunctionService.getTransformationFunction(CreditCard.class, ResponseCreditCardDto.class))
//                .thenReturn(creditCard1 -> ResponseCreditCardDto.builder()
//                        .isActive(creditCard1.isActive())
//                        .cardNumber(creditCard1.getCardNumber())
//                        .monthOfExpire(creditCard1.getMonthOfExpire())
//                        .yearOfExpire(creditCard1.getYearOfExpire())
//                        .ownerName(creditCard1.getOwnerName())
//                        .ownerSecondName(creditCard1.getOwnerSecondName())
//                        .build());

        List<UserCreditCardDto> responseCreditCardDtoList = creditCardManager.getAllCreditCardByCardNumber("");

        assertEquals(1, responseCreditCardDtoList.size());
        responseCreditCardDtoList.forEach(result -> {
//            assertEquals(creditCard.getCardNumber(), result.getCardNumber());
//            assertEquals(creditCard.getMonthOfExpire(), result.getMonthOfExpire());
//            assertEquals(creditCard.getYearOfExpire(), result.getYearOfExpire());
////            assertTrue(result.isActive());
//            assertEquals(creditCard.getOwnerName(), result.getOwnerName());
//            assertEquals(creditCard.getOwnerSecondName(), result.getOwnerSecondName());
        });
    }

//    private ResponseCreditCardDto getTestResponseCardDto() {
//        return ResponseCreditCardDto.builder()
//                .cardNumber("1234567890123456")
//                .monthOfExpire(6)
//                .yearOfExpire(28)
//                .isActive(true)
//                .ownerName("John")
//                .ownerSecondName("Doe")
//                .build();
//    }

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
