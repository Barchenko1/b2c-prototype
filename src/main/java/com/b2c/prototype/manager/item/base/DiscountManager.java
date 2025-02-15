package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.dao.item.IDiscountDao;
import com.b2c.prototype.modal.dto.payload.DiscountDto;
import com.b2c.prototype.modal.dto.payload.DiscountStatusDto;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.service.common.EntityOperationManager;
import com.b2c.prototype.service.common.IEntityOperationManager;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.item.IDiscountManager;
import com.b2c.prototype.service.query.ISearchService;
import com.b2c.prototype.service.supplier.ISupplierService;
import com.tm.core.process.dao.identifier.IQueryService;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.ARTICULAR_ID;
import static com.b2c.prototype.util.Constant.CHAR_SEQUENCE_CODE;
import static com.b2c.prototype.util.Constant.ITEM_DATA_OPTION_BY_DISCOUNT;
import static com.b2c.prototype.util.Constant.ITEM_DATA_OPTION_BY_DISCOUNT_CHAR_SEQUENCE_CODE;

public class DiscountManager implements IDiscountManager {

    private final IEntityOperationManager entityOperationDao;
    private final ISearchService searchService;
    private final IQueryService queryService;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;

    public DiscountManager(IDiscountDao discountDao,
                           ISearchService searchService,
                           IQueryService queryService,
                           ITransformationFunctionService transformationFunctionService,
                           ISupplierService supplierService) {
        this.entityOperationDao = new EntityOperationManager(discountDao);
        this.searchService = searchService;
        this.queryService = queryService;
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
    }

    @Override
    public void saveDiscount(DiscountDto discountDto) {
        entityOperationDao.executeConsumer(session -> {
            Discount discount = transformationFunctionService.getEntity(session, Discount.class, discountDto);
            session.merge(discount);
        });
    }

    @Override
    public void updateItemDataDiscount(String articularId, DiscountDto discountDto) {
        entityOperationDao.executeConsumer(session -> {
            if (discountDto.getCharSequenceCode() == null) {
                throw new RuntimeException("Discount code is null");
            }
            ArticularItem articularItem = queryService.getEntity(
                    session,
                    ArticularItem.class,
                    supplierService.parameterStringSupplier(ARTICULAR_ID, articularId).get());

            Discount discount = (Discount) Optional.ofNullable(discountDto.getCharSequenceCode())
                    .flatMap(code -> entityOperationDao.getOptionalEntity(
                            supplierService.parameterStringSupplier(CHAR_SEQUENCE_CODE, code)))
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
        entityOperationDao.executeConsumer(session -> {
            if (discountDto.getCharSequenceCode() == null) {
                throw new RuntimeException("Discount code is null");
            }
            Discount newDiscount = transformationFunctionService.getEntity(session, Discount.class, discountDto);
            Discount oldDiscount = entityOperationDao.getEntity(
                    supplierService.parameterStringSupplier(CHAR_SEQUENCE_CODE, charSequenceCode));
            newDiscount.setId(oldDiscount.getId());
            session.merge(newDiscount);
        });
    }

    @Override
    public void changeDiscountStatus(DiscountStatusDto discountStatusDto) {
        entityOperationDao.executeConsumer(session -> {
            Discount currencyDiscount = searchService.getEntity(
                    Discount.class,
                    supplierService.parameterStringSupplier(CHAR_SEQUENCE_CODE, discountStatusDto.getCharSequenceCode()));
            currencyDiscount.setActive(discountStatusDto.isActive());
            session.merge(currencyDiscount);
        });
    }

    @Override
    public void deleteDiscount(String charSequenceCode) {
        entityOperationDao.executeConsumer(session -> {
            List<ArticularItem> articularItemList = searchService.getEntityListNamedQuery(
                    ArticularItem.class,
                    ITEM_DATA_OPTION_BY_DISCOUNT_CHAR_SEQUENCE_CODE,
                    supplierService.parameterStringSupplier(CHAR_SEQUENCE_CODE, charSequenceCode));
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
        return searchService.getEntityListNamedQueryDto(
                ArticularItem.class,
                ITEM_DATA_OPTION_BY_DISCOUNT_CHAR_SEQUENCE_CODE,
                supplierService.parameterStringSupplier(CHAR_SEQUENCE_CODE, charSequenceCode),
                transformationFunctionService.getCollectionTransformationFunction(ArticularItem.class, DiscountDto.class));
    }

    @Override
    public Optional<DiscountDto> getOptionalDiscount(String charSequenceCode) {
        return searchService.getOptionalEntityNamedQueryDto(
                ArticularItem.class,
                ITEM_DATA_OPTION_BY_DISCOUNT_CHAR_SEQUENCE_CODE,
                supplierService.parameterStringSupplier(CHAR_SEQUENCE_CODE, charSequenceCode),
                transformationFunctionService.getCollectionTransformationFunction(Discount.class, DiscountDto.class));
    }

    @Override
    public List<DiscountDto> getDiscounts() {
        return searchService.getEntityListNamedQueryDtoList(
                ArticularItem.class,
                ITEM_DATA_OPTION_BY_DISCOUNT,
                transformationFunctionService.getCollectionTransformationCollectionFunction(ArticularItem.class, DiscountDto.class, "list"));
    }

}
