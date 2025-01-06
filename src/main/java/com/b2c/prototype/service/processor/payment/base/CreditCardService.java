package com.b2c.prototype.service.processor.payment.base;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.delete.MultipleFieldsSearchDtoDelete;
import com.b2c.prototype.modal.dto.searchfield.CreditCardSearchFieldEntityDto;
import com.b2c.prototype.modal.dto.response.ResponseCreditCardDto;
import com.b2c.prototype.modal.dto.update.CreditCardSearchFieldEntityUpdateDto;
import com.b2c.prototype.modal.entity.order.OrderItemData;
import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.b2c.prototype.dao.payment.ICreditCardDao;
import com.b2c.prototype.modal.entity.payment.Payment;
import com.b2c.prototype.modal.entity.user.UserProfile;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.payment.ICreditCardService;
import com.b2c.prototype.service.common.EntityOperationDao;
import com.b2c.prototype.service.common.IEntityOperationDao;
import com.b2c.prototype.service.processor.query.IQueryService;
import com.b2c.prototype.service.supplier.ISupplierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.ORDER_ID;
import static com.b2c.prototype.util.Constant.USER_ID;

public class CreditCardService implements ICreditCardService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreditCardService.class);

    private final IEntityOperationDao entityOperationDao;
    private final IQueryService queryService;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;

    public CreditCardService(ICreditCardDao creditCardDao,
                             IQueryService queryService,
                             ITransformationFunctionService transformationFunctionService,
                             ISupplierService supplierService) {
        this.entityOperationDao = new EntityOperationDao(creditCardDao);
        this.queryService = queryService;
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
    }

    @Override
    public void saveCreditCardByUserId(CreditCardSearchFieldEntityDto creditCardSearchFieldEntityDto) {
        entityOperationDao.executeConsumer(session -> {
            UserProfile userProfile = queryService.getEntity(
                    UserProfile.class,
                    supplierService.parameterStringSupplier(USER_ID, creditCardSearchFieldEntityDto.getSearchField()));
            CreditCard creditCard = transformationFunctionService.getEntity(
                    CreditCard.class,
                    creditCardSearchFieldEntityDto.getNewEntity());
            List<CreditCard> creditCardList = Optional.ofNullable(userProfile.getCreditCardList())
                    .orElseThrow(() -> new RuntimeException("User has no credit card list"));
            if (creditCardList.contains(creditCard)) {
                throw new RuntimeException("User already has this credit card");
            }
            creditCardList.add(creditCard);
            session.merge(userProfile);
        });
    }

    @Override
    public void updateCreditCardByUserId(CreditCardSearchFieldEntityUpdateDto creditCardSearchFieldEntityUpdateDto) {
        entityOperationDao.executeConsumer(session -> {
            UserProfile userProfile = queryService.getEntity(
                    UserProfile.class,
                    supplierService.parameterStringSupplier(USER_ID, creditCardSearchFieldEntityUpdateDto.getSearchField()));
            CreditCard newCreditCard = transformationFunctionService.getEntity(
                    CreditCard.class,
                    creditCardSearchFieldEntityUpdateDto.getNewEntity());
            CreditCard existingCreditCard = userProfile.getCreditCardList().stream()
                    .filter(card -> card.getCardNumber().equals(creditCardSearchFieldEntityUpdateDto.getOldEntity().getCardNumber()))
                    .findFirst()
                    .orElseThrow();
            newCreditCard.setId(existingCreditCard.getId());
            session.merge(newCreditCard);
        });
    }

    @Override
    public void saveCreditCardByOrderId(CreditCardSearchFieldEntityDto creditCardSearchFieldEntityDto) {
        entityOperationDao.executeConsumer(session -> {
            OrderItemData orderItemData = queryService.getEntity(
                    OrderItemData.class,
                    supplierService.parameterStringSupplier(ORDER_ID, creditCardSearchFieldEntityDto.getSearchField()));
            CreditCard newCreditCard = transformationFunctionService.getEntity(
                    CreditCard.class,
                    creditCardSearchFieldEntityDto.getNewEntity());
            Payment payment = orderItemData.getPayment();
            payment.setCreditCard(newCreditCard);
            session.merge(payment);
        });
    }

    @Override
    public void updateCreditCardByOrderId(CreditCardSearchFieldEntityUpdateDto creditCardSearchFieldEntityUpdateDto) {
        entityOperationDao.executeConsumer(session -> {
            OrderItemData orderItemData = queryService.getEntity(
                    OrderItemData.class,
                    supplierService.parameterStringSupplier(ORDER_ID, creditCardSearchFieldEntityUpdateDto.getSearchField()));
            CreditCard newCreditCard = transformationFunctionService.getEntity(
                    CreditCard.class,
                    creditCardSearchFieldEntityUpdateDto.getNewEntity());
            Payment payment = orderItemData.getPayment();
            CreditCard existingCreditCard = payment.getCreditCard();
            if (!existingCreditCard.getCardNumber().equals(newCreditCard.getCardNumber())) {
                throw new RuntimeException("Card number mismatch");
            }
            payment.setCreditCard(newCreditCard);
            session.merge(payment);
        });
    }

    @Override
    public void deleteCreditCardByUserId(MultipleFieldsSearchDtoDelete multipleFieldsSearchDtoDelete) {
        entityOperationDao.executeConsumer(session -> {
            UserProfile userProfile = queryService.getEntity(
                    UserProfile.class,
                    supplierService.parameterStringSupplier(USER_ID, multipleFieldsSearchDtoDelete.getMainSearchField()));
            CreditCard creditCard = userProfile.getCreditCardList().stream()
                    .filter(existingCreditCard ->
                            existingCreditCard.getCardNumber().equals(multipleFieldsSearchDtoDelete.getInnerSearchField()))
                    .findFirst()
                    .orElseThrow(() ->new RuntimeException("User has no credit card"));
            session.remove(creditCard);
        });
    }

    @Override
    public void deleteCreditCardByOrderId(MultipleFieldsSearchDtoDelete multipleFieldsSearchDtoDelete) {
        entityOperationDao.executeConsumer(session -> {
            OrderItemData orderItemData = queryService.getEntity(
                    OrderItemData.class,
                    supplierService.parameterStringSupplier(ORDER_ID, multipleFieldsSearchDtoDelete.getMainSearchField()));
            CreditCard creditCard = orderItemData.getPayment().getCreditCard();
            if (!creditCard.getCardNumber().equals(multipleFieldsSearchDtoDelete.getInnerSearchField())) {
                throw new RuntimeException("User has no credit card");
            }
            session.remove(creditCard);
        });
    }

    @Override
    public List<ResponseCreditCardDto> getCardListByUserId(OneFieldEntityDto oneFieldEntityDto) {
        return entityOperationDao.getSubEntityDtoList(
                supplierService.parameterStringSupplier(USER_ID, oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(CreditCard.class, ResponseCreditCardDto.class));
    }

    @Override
    public ResponseCreditCardDto getCardByOrderId(OneFieldEntityDto oneFieldEntityDto) {
        return entityOperationDao.getEntityDto(
                supplierService.parameterStringSupplier(ORDER_ID, oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(CreditCard.class, ResponseCreditCardDto.class));
    }

    @Override
    public List<ResponseCreditCardDto> getAllCards() {
        return entityOperationDao.getEntityDtoList(
                transformationFunctionService.getTransformationFunction(CreditCard.class, ResponseCreditCardDto.class));
    }

}
