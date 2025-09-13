package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.dao.IEntityDao;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.payload.discount.DiscountDto;
import com.b2c.prototype.modal.dto.payload.discount.DiscountStatusDto;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.b2c.prototype.manager.item.IDiscountManager;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.dao.IFetchHandler;
import com.tm.core.process.manager.common.ITransactionEntityOperationManager;
import com.tm.core.process.manager.common.operator.TransactionEntityOperationManager;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.ARTICULAR_ID;
import static com.b2c.prototype.util.Constant.CHAR_SEQUENCE_CODE;

public class DiscountManager implements IDiscountManager {

    private final ITransactionEntityOperationManager entityOperationManager;
    private final IFetchHandler fetchHandler;
    private final ITransformationFunctionService transformationFunctionService;
    private final IParameterFactory parameterFactory;

    public DiscountManager(IGeneralEntityDao discountDao,
                           IFetchHandler fetchHandler,
                           ITransformationFunctionService transformationFunctionService,
                           IParameterFactory parameterFactory) {
        this.entityOperationManager = new TransactionEntityOperationManager(null);
        this.fetchHandler = fetchHandler;
        this.transformationFunctionService = transformationFunctionService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void saveDiscount(DiscountDto discountDto) {
        entityOperationManager.executeConsumer(session -> {
            Discount discount = transformationFunctionService.getEntity((Session) session, Discount.class, discountDto);
            session.merge(discount);
        });
    }

    @Override
    public void updateItemDataDiscount(String articularId, DiscountDto discountDto) {
        entityOperationManager.executeConsumer(session -> {
            if (discountDto.getCharSequenceCode() == null) {
                throw new RuntimeException("Discount code is null");
            }
            ArticularItem articularItem = fetchHandler.getNamedQueryEntityClose(
                    ArticularItem.class,
                    "ArticularItem.discount.currency",
                    parameterFactory.createStringParameter(ARTICULAR_ID, articularId));

            Discount discount = (Discount) Optional.ofNullable(discountDto.getCharSequenceCode())
                    .flatMap(code -> entityOperationManager.getNamedQueryOptionalEntityClose(
                            "Discount.currency",
                            parameterFactory.createStringParameter(CHAR_SEQUENCE_CODE, code)))
                    .orElseGet(() -> transformationFunctionService.getEntity((Session) session, Discount.class, discountDto));

            if (articularItem.getDiscount() != null) {
                discount.setId(articularItem.getDiscount().getId());
            }

            articularItem.setDiscount(discount);
            session.merge(articularItem);
        });
    }

    @Override
    public void updateDiscount(String charSequenceCode, DiscountDto discountDto) {
        entityOperationManager.executeConsumer(session -> {
            if (discountDto.getCharSequenceCode() == null) {
                throw new RuntimeException("Discount code is null");
            }
            Discount newDiscount = transformationFunctionService.getEntity((Session) session, Discount.class, discountDto);
            Discount oldDiscount = entityOperationManager.getNamedQueryEntityClose(
                    "Discount.currency",
                    parameterFactory.createStringParameter(CHAR_SEQUENCE_CODE, charSequenceCode));
            newDiscount.setId(oldDiscount.getId());
            session.merge(newDiscount);
        });
    }

    @Override
    public void changeDiscountStatus(DiscountStatusDto discountStatusDto) {
        entityOperationManager.executeConsumer(session -> {
            Discount currencyDiscount = entityOperationManager.getNamedQueryEntityClose(
                    "Discount.currency",
                    parameterFactory.createStringParameter(CHAR_SEQUENCE_CODE, discountStatusDto.getCharSequenceCode()));
            currencyDiscount.setActive(discountStatusDto.isActive());
            session.merge(currencyDiscount);
        });
    }

    @Override
    public void deleteDiscount(String charSequenceCode) {
        entityOperationManager.executeConsumer(session -> {
            List<ArticularItem> articularItemList = fetchHandler.getNamedQueryEntityListClose(
                    ArticularItem.class,
                    "ArticularItem.findByDiscountCharSequenceCode",
                    parameterFactory.createStringParameter(CHAR_SEQUENCE_CODE, charSequenceCode));
            Discount discount = articularItemList.get(0).getDiscount();
            articularItemList.forEach(itemDataOption -> {
                itemDataOption.setDiscount(null);
                session.merge(itemDataOption);
            });
            session.remove(session.merge(discount));
        });
    }

    @Override
    public DiscountDto getDiscount(String charSequenceCode) {
        List<ArticularItem> articularItemList = fetchHandler.getNamedQueryEntityListClose(
                ArticularItem.class,
                "ArticularItem.findByDiscountCharSequenceCode",
                parameterFactory.createStringParameter(CHAR_SEQUENCE_CODE, charSequenceCode));
//        return articularItemList.stream()
//                .map(e-> new DiscountDto())
//                .findFirst()
//                .orElseThrow(() -> new RuntimeException(""));
        return transformationFunctionService.getCollectionTransformationFunction(ArticularItem.class, DiscountDto.class)
                .apply(articularItemList);
    }

    @Override
    public List<DiscountDto> getDiscounts() {
        List<ArticularItem> articularItemList = fetchHandler.getNamedQueryEntityListClose(
                ArticularItem.class,
                "ArticularItem.findByDiscountNotNull");

        return (List<DiscountDto>) transformationFunctionService.getCollectionTransformationCollectionFunction(ArticularItem.class, DiscountDto.class, "list")
                .apply(articularItemList);
    }

}
