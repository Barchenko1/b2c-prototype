package com.b2c.prototype.service.base.card;

import com.b2c.prototype.modal.dto.request.RequestCardDto;
import com.b2c.prototype.modal.dto.update.RequestCardDtoUpdate;
import com.b2c.prototype.modal.dto.response.ResponseCardDto;
import com.b2c.prototype.modal.entity.payment.AbstractCreditCard;
import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.b2c.prototype.dao.payment.ICreditCardDao;
import com.b2c.prototype.service.single.AbstractSingleEntityService;
import com.b2c.prototype.util.CardUtil;
import com.tm.core.processor.finder.parameter.Parameter;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class CreditCardService extends AbstractSingleEntityService implements ICardService {
    private final ICreditCardDao cardDao;

    public CreditCardService(ICreditCardDao cardDao) {
        this.cardDao = cardDao;
    }

    @Override
    protected ICreditCardDao getEntityDao() {
        return this.cardDao;
    }

    @Override
    public void saveCard(RequestCardDto requestCardDto) {
        CreditCard creditCard = CreditCard.builder()
                .cardNumber(requestCardDto.getCartNumber())
                .dateOfExpire(requestCardDto.getDateOfExpire())
                .cvv(requestCardDto.getCvv())
                .isActive(CardUtil.isCardActive(requestCardDto.getDateOfExpire()))
                .ownerName(requestCardDto.getOwnerName())
                .ownerSecondName(requestCardDto.getOwnerSecondName())
                .build();

        super.saveEntity(creditCard);
    }

    @Override
    public void updateCard(RequestCardDtoUpdate requestCardDtoUpdate) {
        RequestCardDto newCardDto = requestCardDtoUpdate.getNewEntityDto();
        CreditCard newCreditCard = CreditCard.builder()
                .ownerName(newCardDto.getOwnerName())
                .ownerSecondName(newCardDto.getOwnerSecondName())
                .cardNumber(newCardDto.getCartNumber())
                .dateOfExpire(newCardDto.getDateOfExpire())
                .cvv(newCardDto.getCvv())
                .isActive(CardUtil.isCardActive(newCardDto.getDateOfExpire()))
                .build();

        Parameter parameter =
                parameterFactory.createStringParameter("cardNumber", requestCardDtoUpdate.getSearchField());
        super.updateEntity(newCreditCard, parameter);
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
        Optional<CreditCard> optionalCard = cardDao.getOptionalEntity(parameter);

        if (optionalCard.isEmpty()) {
            throw new RuntimeException();
        }

        CreditCard creditCard = optionalCard.get();

        return ResponseCardDto.builder()
                .cartNumber(creditCard.getCardNumber())
                .dateOfExpire(creditCard.getDateOfExpire())
                .cvv(creditCard.getCvv())
                .isActive(creditCard.isActive())
                .ownerName(creditCard.getOwnerName())
                .ownerSecondName(creditCard.getOwnerSecondName())
                .build();

    }

}
