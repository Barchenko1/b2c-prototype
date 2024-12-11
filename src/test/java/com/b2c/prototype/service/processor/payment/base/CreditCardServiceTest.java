package com.b2c.prototype.service.processor.payment.base;

import com.b2c.prototype.dao.payment.ICreditCardDao;
import com.b2c.prototype.dao.user.IUserProfileDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.CreditCardDto;
import com.b2c.prototype.modal.dto.response.ResponseCardDto;
import com.b2c.prototype.modal.dto.update.CreditCardDtoUpdate;
import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.b2c.prototype.modal.entity.user.UserProfile;
import com.tm.core.processor.finder.factory.IParameterFactory;
import com.tm.core.processor.finder.parameter.Parameter;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreditCardServiceTest {

    @Mock
    private IParameterFactory parameterFactory;

    @Mock
    private ICreditCardDao creditCardDao;
    @Mock
    private IUserProfileDao userProfileDao;

    @InjectMocks
    private CreditCardService creditCardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveCard_shouldSaveCreditCreditCard() {
        CreditCardDto creditCardDto = getTestCreditCardDto();

        creditCardService.saveCreditCard(creditCardDto);

        ArgumentCaptor<Supplier<CreditCard>> captor = ArgumentCaptor.forClass(Supplier.class);
        verify(creditCardDao).saveEntity(captor.capture());
        CreditCard capturedEntity = captor.getValue().get();

        assertEquals(creditCardDto.getCardNumber(), capturedEntity.getCardNumber());
        assertEquals(creditCardDto.getDateOfExpire(), capturedEntity.getDateOfExpire());
        assertTrue(capturedEntity.isActive());
        assertEquals(creditCardDto.getOwnerName(), capturedEntity.getOwnerName());
        assertEquals(creditCardDto.getOwnerSecondName(), capturedEntity.getOwnerSecondName());
    }

    @Test
    void updateCard_shouldUpdateCreditCreditCard() {
        CreditCardDtoUpdate creditCardDtoUpdate = CreditCardDtoUpdate.builder()
                .oldCardNumber("1234567890123456")
                .searchField("search")
                .newCreditCard(CreditCardDto.builder()
                        .cardNumber("0123456789012345")
                        .dateOfExpire("06/29")
                        .cvv("789")
                        .ownerName("John")
                        .ownerSecondName("Doe")
                        .build())
                .build();
        UserProfile userProfile = mock(UserProfile.class);
        CreditCard creditCard = getTestCreditCard();
        List<CreditCard> creditCardList = List.of(creditCard);
        CreditCard expectedCard = CreditCard.builder()
                .id(1L)
                .cardNumber("0123456789012345")
                .dateOfExpire("06/29")
                .cvv("789")
                .ownerName("John")
                .ownerSecondName("Doe")
                .isActive(true)
                .build();

        when(parameterFactory.createStringParameter("search", creditCardDtoUpdate.getSearchField()))
                .thenReturn(mock(Parameter.class));
        when(userProfileDao.getEntity(any())).thenReturn(userProfile);
        when(userProfile.getCreditCardList()).thenReturn(creditCardList);

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(expectedCard);
            return null;
        }).when(creditCardDao).updateEntity(any(Consumer.class));

        creditCardService.updateCreditCard(creditCardDtoUpdate);

        verify(creditCardDao).updateEntity(any(Consumer.class));
    }

    @Test
    void deleteCardByEmail_shouldDeleteCreditCard() {
        String email = "my@email.com";
        Parameter parameter = new Parameter("email", email);
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto(email);
        when(parameterFactory.createStringParameter("email", email)).thenReturn(parameter);

        creditCardService.deleteCreditCardByEmail(oneFieldEntityDto);

        verify(creditCardDao).findEntityAndDelete(parameter);
    }

    @Test
    void deleteCardByOrderId_shouldDeleteCreditCard() {
        String orderId = "123";
        Parameter parameter = new Parameter("order_id", orderId);
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto(orderId);

        when(parameterFactory.createStringParameter("order_id", orderId)).thenReturn(parameter);

        creditCardService.deleteCreditCardByOrderId(oneFieldEntityDto);

        verify(creditCardDao).findEntityAndDelete(parameter);
    }


    @Test
    void getCardByEmail_shouldReturnResponseCardDto() {
        String email = "my@email.com";
        Parameter parameter = mock(Parameter.class);
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto(email);
        when(parameterFactory.createStringParameter("email", email)).thenReturn(parameter);

        CreditCard creditCard = getTestCreditCard();

        when(creditCardDao.getEntity(parameter)).thenReturn(creditCard);

        ResponseCardDto result = creditCardService.getCardByEmail(oneFieldEntityDto);

        assertNotNull(result);
        assertEquals(creditCard.getCardNumber(), result.getCardNumber());
        assertEquals(creditCard.getDateOfExpire(), result.getDateOfExpire());
        assertTrue(result.isActive());
        assertEquals(creditCard.getOwnerName(), result.getOwnerName());
        assertEquals(creditCard.getOwnerSecondName(), result.getOwnerSecondName());
    }

    @Test
    void getCardByOrderId_shouldReturnResponseCardDto() {
        String orderId = "123";
        Parameter parameter = mock(Parameter.class);
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto(orderId);
        when(parameterFactory.createStringParameter("order_id", orderId)).thenReturn(parameter);

        CreditCard creditCard = getTestCreditCard();

        when(creditCardDao.getEntity(parameter)).thenReturn(creditCard);

        ResponseCardDto result = creditCardService.getCardByOrderId(oneFieldEntityDto);

        assertNotNull(result);
        assertEquals(creditCard.getCardNumber(), result.getCardNumber());
        assertEquals(creditCard.getDateOfExpire(), result.getDateOfExpire());
        assertTrue(result.isActive());
        assertEquals(creditCard.getOwnerName(), result.getOwnerName());
        assertEquals(creditCard.getOwnerSecondName(), result.getOwnerSecondName());
    }

    @Test
    void getResponseCardDto_shouldReturnNull() {
        Parameter parameter = mock(Parameter.class);
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto("nonexistent");
        when(creditCardDao.getOptionalEntity(parameter)).thenReturn(Optional.empty());

        assertTrue(() -> creditCardService.getCardByEmail(oneFieldEntityDto) == null);
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
}
