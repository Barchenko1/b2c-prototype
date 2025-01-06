package com.b2c.prototype.service.processor.item.base;

import com.b2c.prototype.dao.item.IDiscountDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.DiscountDto;
import com.b2c.prototype.modal.dto.request.DiscountStatusDto;
import com.b2c.prototype.modal.dto.response.ResponseDiscountDto;
import com.b2c.prototype.modal.dto.searchfield.DiscountSearchFieldEntityDto;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.item.ItemDataOption;
import com.b2c.prototype.service.common.EntityOperationDao;
import com.b2c.prototype.service.common.IEntityOperationDao;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.item.IDiscountService;
import com.b2c.prototype.service.processor.query.IQueryService;
import com.b2c.prototype.service.supplier.ISupplierService;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.ARTICULAR_ID;

public class DiscountService implements IDiscountService {

    private final IEntityOperationDao entityOperationDao;
    private final IQueryService queryService;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;

    public DiscountService(IDiscountDao discountDao,
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
    public void updateItemDataDiscount(DiscountSearchFieldEntityDto discountSearchFieldEntityDto) {
        entityOperationDao.executeConsumer(session -> {
            ItemDataOption itemDataOption = queryService.getEntity(
                    ItemDataOption.class,
                    supplierService.parameterStringSupplier(ARTICULAR_ID, discountSearchFieldEntityDto.getSearchField()));
            Discount discount = transformationFunctionService.getEntity(
                    Discount.class,
                    discountSearchFieldEntityDto.getNewEntity());

            itemDataOption.setDiscount(discount);
            session.merge(itemDataOption);
        });

    }

    @Override
    public void updateDiscount(DiscountSearchFieldEntityDto discountSearchFieldEntityDto) {
        entityOperationDao.updateEntityByParameter(
                supplierService.getSupplier(Discount.class, discountSearchFieldEntityDto.getNewEntity()),
                supplierService.parameterStringSupplier("charSequenceCode", discountSearchFieldEntityDto.getSearchField()));
    }

    @Override
    public void changeDiscountStatus(DiscountStatusDto discountStatusDto) {
        entityOperationDao.executeConsumer(session -> {
            Discount currencyDiscount = queryService.getEntity(
                    Discount.class,
                    supplierService.parameterStringSupplier("charSequenceCode", discountStatusDto.getCharSequenceCode()));
            currencyDiscount.setActive(discountStatusDto.isActive());
            session.merge(currencyDiscount);
        });
    }

    @Override
    public void deleteDiscount(OneFieldEntityDto oneFieldEntityDto) {
        entityOperationDao.deleteEntityByParameter(
                supplierService.parameterStringSupplier("charSequenceCode", oneFieldEntityDto.getValue()));
    }

    @Override
    public ResponseDiscountDto getDiscount(OneFieldEntityDto oneFieldEntityDto) {
        return entityOperationDao.getEntityDto(
                supplierService.parameterStringSupplier("charSequenceCode", oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(Discount.class, ResponseDiscountDto.class));
    }

    @Override
    public Optional<ResponseDiscountDto> getOptionalDiscount(OneFieldEntityDto oneFieldEntityDto) {
        return entityOperationDao.getOptionalEntityDto(
                supplierService.parameterStringSupplier("charSequenceCode", oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(Discount.class, ResponseDiscountDto.class));
    }

    @Override
    public List<DiscountDto> getDiscounts() {
        return entityOperationDao.getEntityDtoList(
                transformationFunctionService.getTransformationFunction(Discount.class, DiscountDto.class));
    }

}
