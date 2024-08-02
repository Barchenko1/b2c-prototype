package com.b2c.prototype.service.client.card;

import com.b2c.prototype.modal.client.dto.request.RequestCardDto;
import com.b2c.prototype.modal.client.dto.update.RequestCardDtoUpdate;
import com.b2c.prototype.modal.client.dto.response.ResponseCardDto;
import com.b2c.prototype.modal.client.entity.payment.Card;
import com.b2c.prototype.dao.payment.ICardDao;
import com.b2c.prototype.util.CardUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import static com.b2c.prototype.util.Query.DELETE_CARD_BY_CARD_NUMBER;
import static com.b2c.prototype.util.Query.SELECT_CARD_BY_CARD_NUMBER;
import static com.b2c.prototype.util.Query.SELECT_CARD_BY_OWNER_USERNAME;
import static com.b2c.prototype.util.Query.UPDATE_CARD_BY_CARD_NUMBER;

@Slf4j
public class CardService implements ICardService {
    private final ICardDao cardDao;

    public CardService(ICardDao cardDao) {
        this.cardDao = cardDao;
    }

    @Override
    public void saveCard(RequestCardDto requestCardDto) {
        Card card = Card.builder()
                .cardNumber(requestCardDto.getCartNumber())
                .dateOfExpire(requestCardDto.getDateOfExpire())
                .cvv(requestCardDto.getCvv())
                .isActive(CardUtil.isCardActive(requestCardDto.getDateOfExpire()))
                .ownerName(requestCardDto.getOwnerName())
                .ownerSecondName(requestCardDto.getOwnerSecondName())
                .build();

        cardDao.saveEntity(card);
    }

    @Override
    public void updateCard(RequestCardDtoUpdate requestCardDtoUpdate) {
        RequestCardDto newCardDto = requestCardDtoUpdate.getNewEntityDto();
        Card newCard = Card.builder()
                .ownerName(newCardDto.getOwnerName())
                .ownerSecondName(newCardDto.getOwnerSecondName())
                .cardNumber(newCardDto.getCartNumber())
                .dateOfExpire(newCardDto.getDateOfExpire())
                .cvv(newCardDto.getCvv())
                .isActive(CardUtil.isCardActive(newCardDto.getDateOfExpire()))
                .build();

        cardDao.mutateEntityBySQLQueryWithParams(UPDATE_CARD_BY_CARD_NUMBER,
                newCard.getCardNumber(),
                newCard.getDateOfExpire(),
                newCard.getCvv(),
                newCard.isActive(),
                newCard.getOwnerName(),
                newCard.getOwnerSecondName(),
                requestCardDtoUpdate.getSearchField());
    }

    @Override
    public void deleteCardByCardNumber(String cardNumber) {
        cardDao.mutateEntityBySQLQueryWithParams(DELETE_CARD_BY_CARD_NUMBER, cardNumber);
    }

    @Override
    public ResponseCardDto getCardByCardOwnerUserName(String cardOwnerUsername) {
        return getResponseCardDto(SELECT_CARD_BY_OWNER_USERNAME, cardOwnerUsername);
    }

    @Override
    public ResponseCardDto getCardByCardNumber(String cardNumber) {
        return getResponseCardDto(SELECT_CARD_BY_CARD_NUMBER, cardNumber);
    }

    private ResponseCardDto getResponseCardDto(String selectQuery, String searchParam) {
        Optional<Card> optionalCard = cardDao.getOptionalEntityBySQLQueryWithParams(selectQuery, searchParam);

        if (optionalCard.isEmpty()) {
            throw new RuntimeException();
        }

        Card card = optionalCard.get();

        return ResponseCardDto.builder()
                .cartNumber(card.getCardNumber())
                .dateOfExpire(card.getDateOfExpire())
                .cvv(card.getCvv())
                .isActive(card.isActive())
                .ownerName(card.getOwnerName())
                .ownerSecondName(card.getOwnerSecondName())
                .build();

    }

}
