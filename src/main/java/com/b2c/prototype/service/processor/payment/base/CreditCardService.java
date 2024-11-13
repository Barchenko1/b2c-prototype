package com.b2c.prototype.service.processor.payment.base;

import com.b2c.prototype.modal.dto.request.RequestCardDto;
import com.b2c.prototype.modal.dto.update.CardDtoUpdate;
import com.b2c.prototype.modal.dto.response.ResponseCardDto;
import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.b2c.prototype.dao.payment.ICreditCardDao;
import com.b2c.prototype.service.processor.payment.ICardService;
import com.b2c.prototype.util.CardUtil;
import com.tm.core.processor.finder.parameter.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class CreditCardService implements ICardService {
    private static final Logger LOG = LoggerFactory.getLogger(CreditCardService.class);
    private final ICreditCardDao creditCardDao;

    public CreditCardService(ICreditCardDao creditCardDao) {
        this.creditCardDao = creditCardDao;
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

//        super.saveEntity(creditCard);
    }

    @Override
    public void updateCard(CardDtoUpdate requestCardDtoUpdate) {
        RequestCardDto newCardDto = requestCardDtoUpdate.getNewEntityDto();
        CreditCard newCreditCard = CreditCard.builder()
                .ownerName(newCardDto.getOwnerName())
                .ownerSecondName(newCardDto.getOwnerSecondName())
                .cardNumber(newCardDto.getCartNumber())
                .dateOfExpire(newCardDto.getDateOfExpire())
                .cvv(newCardDto.getCvv())
                .isActive(CardUtil.isCardActive(newCardDto.getDateOfExpire()))
                .build();

//        Parameter parameter =
//                parameterFactory.createStringParameter("cardNumber", requestCardDtoUpdate.getSearchField());
//        super.updateEntity(newCreditCard, parameter);
    }

    @Override
    public void deleteCardByCardNumber(String cardNumber) {
        Parameter parameter = new Parameter("cardNumber", cardNumber);
//        super.deleteEntityByParameter(parameter);
    }

    @Override
    public ResponseCardDto getCardByCardOwnerUserName(String cardOwnerUsername) {
//        Parameter parameter = parameterFactory.createStringParameter("cardOwnerUsername", cardOwnerUsername);
//        return getResponseCardDto(parameter);
        return null;
    }

    @Override
    public ResponseCardDto getCardByCardNumber(String cardNumber) {
//        Parameter parameter = parameterFactory.createStringParameter("cardNumber", cardNumber);
//        return getResponseCardDto(parameter);
        return null;
    }

    private ResponseCardDto getResponseCardDto(Parameter parameter) {
        Optional<CreditCard> optionalCard = creditCardDao.getOptionalEntity(parameter);

        if (optionalCard.isEmpty()) {
            throw new RuntimeException();
        }

        CreditCard creditCard = optionalCard.get();

        return ResponseCardDto.builder()
                .cartNumber(creditCard.getCardNumber())
                .dateOfExpire(creditCard.getDateOfExpire())
                .isActive(creditCard.isActive())
                .ownerName(creditCard.getOwnerName())
                .ownerSecondName(creditCard.getOwnerSecondName())
                .build();

    }

}
