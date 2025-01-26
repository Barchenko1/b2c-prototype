package com.b2c.prototype.service.manager.item.base;

import com.b2c.prototype.dao.item.IDiscountDao;
import com.b2c.prototype.modal.dto.payload.DiscountDto;
import com.b2c.prototype.modal.dto.payload.DiscountStatusDto;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.item.ItemDataOption;
import com.b2c.prototype.service.common.EntityOperationDao;
import com.b2c.prototype.service.common.IEntityOperationDao;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.manager.item.IDiscountManager;
import com.b2c.prototype.service.query.IQueryService;
import com.b2c.prototype.service.supplier.ISupplierService;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.ARTICULAR_ID;
import static com.b2c.prototype.util.Constant.CHAR_SEQUENCE_CODE;
import static com.b2c.prototype.util.Constant.ITEM_DATA_OPTION_BY_DISCOUNT;
import static com.b2c.prototype.util.Constant.ITEM_DATA_OPTION_BY_DISCOUNT_CHAR_SEQUENCE_CODE;

public class DiscountManager implements IDiscountManager {

    private final IEntityOperationDao entityOperationDao;
    private final IQueryService queryService;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;

    public DiscountManager(IDiscountDao discountDao,
                           IQueryService queryService,
                           ITransformationFunctionService transformationFunctionService,
                           ISupplierService supplierService) {
        this.entityOperationDao = new EntityOperationDao(discountDao);
        this.queryService = queryService;
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
    }

    @Override
    public void saveDiscount(DiscountDto discountDto) {
        entityOperationDao.saveEntity(
                supplierService.getSupplier(Discount.class, discountDto));
    }

    @Override
    public void updateItemDataDiscount(String articularId, DiscountDto discountDto) {
        entityOperationDao.executeConsumer(session -> {
            if (discountDto.getCharSequenceCode() == null) {
                throw new RuntimeException("Discount code is null");
            }
            ItemDataOption itemDataOption = queryService.getEntity(
                    ItemDataOption.class,
                    supplierService.parameterStringSupplier(ARTICULAR_ID, articularId)
            );

            Discount discount = (Discount) Optional.ofNullable(discountDto.getCharSequenceCode())
                    .flatMap(code -> entityOperationDao.getOptionalEntity(
                            supplierService.parameterStringSupplier(CHAR_SEQUENCE_CODE, code)))
                    .orElseGet(() -> transformationFunctionService.getEntity(Discount.class, discountDto));

            if (itemDataOption.getDiscount() != null) {
                discount.setId(itemDataOption.getDiscount().getId());
            }

            itemDataOption.setDiscount(discount);
            session.merge(itemDataOption);
        });
    }


    @Override
    public void updateDiscount(String charSequenceCode, DiscountDto discountDto) {
        entityOperationDao.executeConsumer(session -> {
            if (discountDto.getCharSequenceCode() == null) {
                throw new RuntimeException("Discount code is null");
            }
            Discount newDiscount = transformationFunctionService.getEntity(Discount.class, discountDto);
            Discount oldDiscount = entityOperationDao.getEntity(
                    supplierService.parameterStringSupplier(CHAR_SEQUENCE_CODE, charSequenceCode));
            newDiscount.setId(oldDiscount.getId());
            session.merge(newDiscount);
        });
    }

    @Override
    public void changeDiscountStatus(DiscountStatusDto discountStatusDto) {
        entityOperationDao.executeConsumer(session -> {
            Discount currencyDiscount = queryService.getEntity(
                    Discount.class,
                    supplierService.parameterStringSupplier(CHAR_SEQUENCE_CODE, discountStatusDto.getCharSequenceCode()));
            currencyDiscount.setActive(discountStatusDto.isActive());
            session.merge(currencyDiscount);
        });
    }

    @Override
    public void deleteDiscount(String charSequenceCode) {
        entityOperationDao.executeConsumer(session -> {
            List<ItemDataOption> itemDataOptionList = queryService.getEntityList(
                    ItemDataOption.class,
                    ITEM_DATA_OPTION_BY_DISCOUNT_CHAR_SEQUENCE_CODE,
                    supplierService.parameterStringSupplier(CHAR_SEQUENCE_CODE, charSequenceCode));
            Discount discount = itemDataOptionList.get(0).getDiscount();
            itemDataOptionList.forEach(itemDataOption -> {
                itemDataOption.setDiscount(null);
                session.merge(itemDataOption);
            });

            session.remove(session.merge(discount));
        });
    }

    @Override
    public DiscountDto getDiscount(String charSequenceCode) {
        return queryService.getEntityListNamedQueryDto(
                ItemDataOption.class,
                ITEM_DATA_OPTION_BY_DISCOUNT_CHAR_SEQUENCE_CODE,
                supplierService.parameterStringSupplier(CHAR_SEQUENCE_CODE, charSequenceCode),
                transformationFunctionService.getCollectionTransformationFunction(ItemDataOption.class, DiscountDto.class));
    }

    @Override
    public Optional<DiscountDto> getOptionalDiscount(String charSequenceCode) {
        return queryService.getOptionalEntityNamedQueryDto(
                ItemDataOption.class,
                ITEM_DATA_OPTION_BY_DISCOUNT_CHAR_SEQUENCE_CODE,
                supplierService.parameterStringSupplier(CHAR_SEQUENCE_CODE, charSequenceCode),
                transformationFunctionService.getCollectionTransformationFunction(Discount.class, DiscountDto.class));
    }

    @Override
    public List<DiscountDto> getDiscounts() {
        return queryService.getEntityListNamedQueryDtoList(
                ItemDataOption.class,
                ITEM_DATA_OPTION_BY_DISCOUNT,
                transformationFunctionService.getCollectionTransformationCollectionFunction(ItemDataOption.class, DiscountDto.class, "list"));
    }

}
