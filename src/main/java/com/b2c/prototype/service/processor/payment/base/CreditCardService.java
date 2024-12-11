package com.b2c.prototype.service.processor.payment.base;

import com.b2c.prototype.dao.user.IUserProfileDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.CreditCardDto;
import com.b2c.prototype.modal.dto.response.ResponseCardDto;
import com.b2c.prototype.modal.dto.update.CreditCardDtoUpdate;
import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.b2c.prototype.dao.payment.ICreditCardDao;
import com.b2c.prototype.modal.entity.user.UserProfile;
import com.b2c.prototype.service.processor.payment.ICreditCardService;
import com.b2c.prototype.service.common.EntityOperationDao;
import com.b2c.prototype.service.common.IEntityOperationDao;
import com.b2c.prototype.util.CardUtil;
import com.tm.core.processor.finder.factory.IParameterFactory;
import com.tm.core.processor.finder.parameter.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class CreditCardService implements ICreditCardService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreditCardService.class);
    private final IParameterFactory parameterFactory;
    private final IEntityOperationDao entityOperationDao;
    private final IUserProfileDao userProfileDao;

    public CreditCardService(IParameterFactory parameterFactory,
                             ICreditCardDao creditCardDao,
                             IUserProfileDao userProfileDao) {
        this.parameterFactory = parameterFactory;
        this.entityOperationDao = new EntityOperationDao(creditCardDao);
        this.userProfileDao = userProfileDao;
    }

    @Override
    public void saveCreditCard(CreditCardDto creditCardDto) {
        entityOperationDao.saveEntity(creditCardSupplier(creditCardDto));
    }

    @Override
    public void updateCreditCard(CreditCardDtoUpdate creditCardDtoUpdate) {
        entityOperationDao.updateEntity(session -> {
            UserProfile userProfile = userProfileDao.getEntity(
                    emailParameterSupplier(creditCardDtoUpdate.getSearchField()).get());
//            UserProfile userProfile = commonSingleEntityService.getEntity(
//                    UserProfile.class,
//                    emailParameterSupplier(creditCardDtoUpdate.getSearchField())
//            );
            CreditCard newCreditCard = mapToEntityFunction().apply(creditCardDtoUpdate.getNewCreditCard());
            CreditCard existingCreditCard = userProfile.getCreditCardList().stream()
                    .filter(card -> card.getCardNumber().equals(creditCardDtoUpdate.getOldCardNumber()))
                    .findFirst()
                    .orElseThrow();
            newCreditCard.setId(existingCreditCard.getId());
            session.merge(newCreditCard);
        });
    }

    @Override
    public void deleteCreditCardByEmail(OneFieldEntityDto oneFieldEntityDto) {
        entityOperationDao.deleteEntityByParameter(emailParameterSupplier(oneFieldEntityDto.getValue()));
    }

    @Override
    public void deleteCreditCardByOrderId(OneFieldEntityDto oneFieldEntityDto) {
        entityOperationDao.deleteEntityByParameter(orderIdParameterSupplier(oneFieldEntityDto.getValue()));
    }

    @Override
    public ResponseCardDto getCardByEmail(OneFieldEntityDto oneFieldEntityDto) {
        return entityOperationDao.getEntityDto(
                emailParameterSupplier(oneFieldEntityDto.getValue()),
                mapToDtoFunction());
    }

    @Override
    public ResponseCardDto getCardByOrderId(OneFieldEntityDto oneFieldEntityDto) {
        return entityOperationDao.getEntityDto(
                orderIdParameterSupplier(oneFieldEntityDto.getValue()),
                mapToDtoFunction());
    }

    @Override
    public List<ResponseCardDto> getAllCards() {
        return entityOperationDao.getEntityDtoList(mapToDtoFunction());
    }

    private Function<CreditCardDto, CreditCard> mapToEntityFunction() {
        return (creditCardDto) -> CreditCard.builder()
                .cardNumber(creditCardDto.getCardNumber())
                .dateOfExpire(creditCardDto.getDateOfExpire())
                .isActive(CardUtil.isCardActive(creditCardDto.getDateOfExpire()))
                .cvv(creditCardDto.getCvv())
                .ownerName(creditCardDto.getOwnerName())
                .ownerSecondName(creditCardDto.getOwnerSecondName())
                .build();
    }

    private Function<CreditCard, ResponseCardDto> mapToDtoFunction() {
        return (creditCard) -> ResponseCardDto.builder()
                .cardNumber(creditCard.getCardNumber())
                .dateOfExpire(creditCard.getDateOfExpire())
                .isActive(CardUtil.isCardActive(creditCard.getDateOfExpire()))
                .ownerName(creditCard.getOwnerName())
                .ownerSecondName(creditCard.getOwnerSecondName())
                .build();
    }

    private Supplier<CreditCard> creditCardSupplier(CreditCardDto creditCardDto) {
        return () -> mapToEntityFunction().apply(creditCardDto);
    }

    private Supplier<Parameter> orderIdParameterSupplier(String value) {
        return () -> parameterFactory.createStringParameter(
                "order_id", value
        );
    }

    private Supplier<Parameter> emailParameterSupplier(String value) {
        return () -> parameterFactory.createStringParameter(
                "email", value
        );
    }


}
