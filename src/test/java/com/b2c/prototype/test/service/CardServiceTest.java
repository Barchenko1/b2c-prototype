package com.b2c.prototype.test.service;

import com.b2c.prototype.dao.payment.ICardDao;
import com.b2c.prototype.modal.dto.request.RequestCardDto;
import com.b2c.prototype.modal.dto.response.ResponseCardDto;
import com.b2c.prototype.modal.entity.payment.Card;
import com.b2c.prototype.service.card.CardService;
import com.b2c.prototype.service.card.ICardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static com.b2c.prototype.util.Query.DELETE_CARD_BY_CARD_NUMBER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CardServiceTest {

    @Mock
    private ICardDao cardDao;

    private ICardService cardService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        cardService = new CardService(cardDao);
    }

    @Test
    public void testSaveCard() {
        // Create a mock RequestCardDto
        RequestCardDto requestCardDto = new RequestCardDto();
        requestCardDto.setCartNumber("1234567890123456");
        requestCardDto.setDateOfExpire("12/25");
        requestCardDto.setCvv("123");
        requestCardDto.setOwnerName("test");
        requestCardDto.setOwnerSecondName("test");

        // Call the method
        cardService.saveCard(requestCardDto);

        // Verify that mutateEntityBySQLQueryWithParams was called with the correct parameters
//        verify(cardDao).mutateEntityBySQLQueryWithParams(INSERT_INTO_CARD, "1234567890123456", "12/25", "123", true, "test", "test");
    }

    @Test
    public void testDeleteCardByCardNumber() {
        // Mock behavior of cardDao.mutateEntityBySQLQueryWithParams
        doNothing().when(cardDao).mutateEntityBySQLQueryWithParams(anyString(), anyString());

        // Call the method
        cardService.deleteCardByCardNumber("1234567890123456");

        // Verify that mutateEntityBySQLQueryWithParams was called with the correct parameters
        verify(cardDao).mutateEntityBySQLQueryWithParams(DELETE_CARD_BY_CARD_NUMBER, "1234567890123456");
    }

    @Test
    public void testGetCardByCardOwner() {
        // Mock behavior of cardDao.getOptionalEntityBySQLQueryWithStringParam
        Card mockCard = new Card(1L, "1234567890123456", "12/25", "123", true, "test", "test");
        when(cardDao.getOptionalEntityBySQLQueryWithParams(anyString(), anyString())).thenReturn(Optional.of(mockCard));

        // Call the method
        ResponseCardDto responseCardDto = cardService.getCardByCardOwnerUserName("username1");

        // Verify that the returned CardDataDto matches the mocked card
        assertNotNull(responseCardDto);
        assertEquals("1234567890123456", responseCardDto.getCartNumber());
        assertEquals("12/25", responseCardDto.getDateOfExpire());
        assertEquals("123", responseCardDto.getCvv());
        assertTrue(responseCardDto.isActive());
    }

    @Test
    public void testGetCardByCardNumber() {
        // Mock behavior of cardDao.getOptionalEntityBySQLQueryWithStringParam
        Card mockCard = new Card(1L, "1234567890123456", "12/25", "123", true, "test", "test");
        when(cardDao.getOptionalEntityBySQLQueryWithParams(anyString(), anyString())).thenReturn(Optional.of(mockCard));

        // Call the method
        ResponseCardDto responseCardDto = cardService.getCardByCardNumber("1234567890123456");

        // Verify that the returned CardDataDto matches the mocked card
        assertNotNull(responseCardDto);
        assertEquals("1234567890123456", responseCardDto.getCartNumber());
        assertEquals("12/25", responseCardDto.getDateOfExpire());
        assertEquals("123", responseCardDto.getCvv());
        assertTrue(responseCardDto.isActive());
    }
}