package com.b2c.prototype.service.base.card;

import com.b2c.prototype.modal.dto.request.RequestCardDto;
import com.b2c.prototype.modal.dto.update.RequestCardDtoUpdate;
import com.b2c.prototype.modal.dto.response.ResponseCardDto;
import com.b2c.prototype.modal.entity.payment.Card;
import com.b2c.prototype.dao.payment.ICardDao;
import com.b2c.prototype.service.single.AbstractSingleEntityService;
import com.b2c.prototype.util.CardUtil;
import com.tm.core.processor.finder.parameter.Parameter;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class CreditCardService extends AbstractSingleEntityService implements ICardService {
    private final ICardDao cardDao;

    public CreditCardService(ICardDao cardDao) {
        this.cardDao = cardDao;
    }

    @Override
    protected ICardDao getEntityDao() {
        return this.cardDao;
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

        super.saveEntity(card);
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

        Parameter parameter =
                parameterFactory.createStringParameter("cardNumber", requestCardDtoUpdate.getSearchField());
        super.updateEntity(newCard, parameter);
    }

    @Override
    public void deleteCardByCardNumber(String cardNumber) {
        Parameter parameter = new Parameter("cardNumber", cardNumber);
        super.deleteEntity(parameter);
    }

    @Override
    public ResponseCardDto getCardByCardOwnerUserName(String cardOwnerUsername) {
        Parameter parameter = parameterFactory.createStringParameter("cardOwnerUsername", cardOwnerUsername);
        return getResponseCardDto(parameter);
    }

    @Override
    public ResponseCardDto getCardByCardNumber(String cardNumber) {
        Parameter parameter = parameterFactory.createStringParameter("cardNumber", cardNumber);
        return getResponseCardDto(parameter);
    }

    private ResponseCardDto getResponseCardDto(Parameter parameter) {
        Optional<Card> optionalCard = cardDao.getOptionalEntity(parameter);

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
