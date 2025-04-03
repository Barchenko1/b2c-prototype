package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.dao.item.IDiscountDao;
import com.b2c.prototype.modal.dto.payload.discount.DiscountDto;
import com.b2c.prototype.modal.dto.payload.discount.DiscountStatusDto;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.item.IDiscountManager;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.dao.query.IFetchHandler;
import com.tm.core.process.manager.common.EntityOperationManager;
import com.tm.core.process.manager.common.IEntityOperationManager;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.ARTICULAR_ID;
import static com.b2c.prototype.util.Constant.CHAR_SEQUENCE_CODE;
import static com.b2c.prototype.util.Constant.ARTICULAR_ITEM_FIND_BY_DISCOUNT_NOT_NULL;
import static com.b2c.prototype.util.Constant.ARTICULAR_ITEM_FIND_BY_DISCOUNT_CHAR_SEQUENCE_CODE;

public class DiscountManager implements IDiscountManager {

    private final IEntityOperationManager entityOperationManager;
    private final IFetchHandler fetchHandler;
    private final ITransformationFunctionService transformationFunctionService;
    private final IParameterFactory parameterFactory;

    public DiscountManager(IDiscountDao discountDao,
                           IFetchHandler fetchHandler,
                           ITransformationFunctionService transformationFunctionService,
                           IParameterFactory parameterFactory) {
        this.entityOperationManager = new EntityOperationManager(discountDao);
        this.fetchHandler = fetchHandler;
        this.transformationFunctionService = transformationFunctionService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void saveDiscount(DiscountDto discountDto) {
        entityOperationManager.executeConsumer(session -> {
            Discount discount = transformationFunctionService.getEntity(session, Discount.class, discountDto);
            session.merge(discount);
        });
    }

    @Override
    public void updateItemDataDiscount(String articularId, DiscountDto discountDto) {
        entityOperationManager.executeConsumer(session -> {
            if (discountDto.getCharSequenceCode() == null) {
                throw new RuntimeException("Discount code is null");
            }
            ArticularItem articularItem = fetchHandler.getGraphEntity(
                    ArticularItem.class,
                    "articularItem.discount.currency",
                    parameterFactory.createStringParameter(ARTICULAR_ID, articularId));

            Discount discount = (Discount) Optional.ofNullable(discountDto.getCharSequenceCode())
                    .flatMap(code -> entityOperationManager.getGraphOptionalEntity(
                            "Discount.currency",
                            parameterFactory.createStringParameter(CHAR_SEQUENCE_CODE, code)))
                    .orElseGet(() -> transformationFunctionService.getEntity(session, Discount.class, discountDto));

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
            Discount newDiscount = transformationFunctionService.getEntity(session, Discount.class, discountDto);
            Discount oldDiscount = entityOperationManager.getGraphEntity(
                    "Discount.currency",
                    parameterFactory.createStringParameter(CHAR_SEQUENCE_CODE, charSequenceCode));
            newDiscount.setId(oldDiscount.getId());
            session.merge(newDiscount);
        });
    }

    @Override
    public void changeDiscountStatus(DiscountStatusDto discountStatusDto) {
        entityOperationManager.executeConsumer(session -> {
            Discount currencyDiscount = entityOperationManager.getGraphEntity(
                    "Discount.currency",
                    parameterFactory.createStringParameter(CHAR_SEQUENCE_CODE, discountStatusDto.getCharSequenceCode()));
            currencyDiscount.setActive(discountStatusDto.isActive());
            session.merge(currencyDiscount);
        });
    }

    @Override
    public void deleteDiscount(String charSequenceCode) {
        entityOperationManager.executeConsumer(session -> {
            List<ArticularItem> articularItemList = fetchHandler.getNamedQueryEntityList(
                    ArticularItem.class,
                    ARTICULAR_ITEM_FIND_BY_DISCOUNT_CHAR_SEQUENCE_CODE,
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
        List<ArticularItem> articularItemList = fetchHandler.getNamedQueryEntityList(
                ArticularItem.class,
                ARTICULAR_ITEM_FIND_BY_DISCOUNT_CHAR_SEQUENCE_CODE,
                parameterFactory.createStringParameter(CHAR_SEQUENCE_CODE, charSequenceCode));
        return transformationFunctionService.getCollectionTransformationFunction(ArticularItem.class, DiscountDto.class)
                .apply(articularItemList);
    }

    @Override
    public List<DiscountDto> getDiscounts() {
        List<ArticularItem> articularItemList = fetchHandler.getNamedQueryEntityList(
                ArticularItem.class,
                ARTICULAR_ITEM_FIND_BY_DISCOUNT_NOT_NULL);
        return (List<DiscountDto>) transformationFunctionService.getCollectionTransformationCollectionFunction(ArticularItem.class, DiscountDto.class, "list")
                .apply(articularItemList);
    }

}
