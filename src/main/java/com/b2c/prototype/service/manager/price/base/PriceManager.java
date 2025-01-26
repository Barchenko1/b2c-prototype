package com.b2c.prototype.service.manager.price.base;

import com.b2c.prototype.dao.price.IPriceDao;
import com.b2c.prototype.modal.constant.PriceTypeEnum;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.payload.PriceDto;
import com.b2c.prototype.modal.dto.searchfield.PriceSearchFieldEntityDto;
import com.b2c.prototype.modal.dto.response.ResponsePriceDto;
import com.b2c.prototype.modal.entity.item.ItemDataOption;
import com.b2c.prototype.modal.entity.order.OrderItemData;
import com.b2c.prototype.modal.entity.payment.Payment;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.manager.price.IPriceManager;
import com.b2c.prototype.service.query.IQueryService;
import com.b2c.prototype.service.common.EntityOperationDao;
import com.b2c.prototype.service.common.IEntityOperationDao;
import com.b2c.prototype.service.supplier.ISupplierService;

import java.util.List;

import static com.b2c.prototype.util.Constant.ORDER_ID;
import static com.b2c.prototype.util.Constant.ARTICULAR_ID;

public class PriceManager implements IPriceManager {

    private final IEntityOperationDao entityOperationDao;
    private final IQueryService queryService;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;

    public PriceManager(IPriceDao priceDao,
                        IQueryService queryService,
                        ITransformationFunctionService transformationFunctionService,
                        ISupplierService supplierService) {
        this.entityOperationDao = new EntityOperationDao(priceDao);
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
        this.queryService = queryService;
    }

    @Override
    public void saveUpdatePriceByOrderId(PriceSearchFieldEntityDto priceSearchFieldEntityDto, PriceTypeEnum priceType) {
        entityOperationDao.executeConsumer(session -> {
            OrderItemData orderItemData = queryService.getEntity(
                    OrderItemData.class,
                    supplierService.parameterStringSupplier(ORDER_ID, priceSearchFieldEntityDto.getSearchField()));
            Price price = transformationFunctionService.getEntity(
                    Price.class, priceSearchFieldEntityDto.getNewEntity(), priceType.getValue());
            Payment payment = orderItemData.getPayment();

            if (priceType == PriceTypeEnum.FULL_PRICE) {
                updateFullPrice(payment, price);
            }
            if (priceType == PriceTypeEnum.TOTAL_PRICE) {
                updateTotalPrice(payment, price);
            }
            session.merge(payment);
        });
    }

    @Override
    public void saveUpdatePriceByArticularId(PriceSearchFieldEntityDto priceSearchFieldEntityDto, PriceTypeEnum priceType) {
        entityOperationDao.executeConsumer(session -> {
            ItemDataOption itemDataOption = queryService.getEntity(
                    ItemDataOption.class,
                    supplierService.parameterStringSupplier(ARTICULAR_ID, priceSearchFieldEntityDto.getSearchField()));
            Price price = transformationFunctionService.getEntity(
                    Price.class, priceSearchFieldEntityDto.getNewEntity(), priceType.getValue());

            if (priceType == PriceTypeEnum.FULL_PRICE) {
                updateFullPrice(itemDataOption, price);
            }
            if (priceType == PriceTypeEnum.TOTAL_PRICE) {
                updateTotalPrice(itemDataOption, price);
            }
            session.merge(itemDataOption);
        });
    }

    @Override
    public void deletePriceByOrderId(OneFieldEntityDto oneFieldEntityDto, PriceTypeEnum priceType) {
        entityOperationDao.deleteEntity(
                supplierService.entityFieldSupplier(
                        OrderItemData.class,
                        supplierService.parameterStringSupplier(ORDER_ID, oneFieldEntityDto.getValue()),
                        transformationFunctionService.getTransformationFunction(
                                OrderItemData.class, Price.class, priceType.getValue())
                )
        );
    }

    @Override
    public void deletePriceByArticularId(OneFieldEntityDto oneFieldEntityDto, PriceTypeEnum priceType) {
        entityOperationDao.deleteEntity(
                supplierService.entityFieldSupplier(
                        ItemDataOption.class,
                        supplierService.parameterStringSupplier(ARTICULAR_ID, oneFieldEntityDto.getValue()),
                        transformationFunctionService.getTransformationFunction(
                                ItemDataOption.class, Price.class, priceType.getValue())
                )
        );
    }

    @Override
    public PriceDto getPriceByOrderId(OneFieldEntityDto oneFieldEntityDto, PriceTypeEnum priceType) {
        return queryService.getEntityDto(
                OrderItemData.class,
                supplierService.parameterStringSupplier(ORDER_ID, oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(
                        OrderItemData.class, PriceDto.class, priceType.getValue()));
    }

    @Override
    public PriceDto getPriceByArticularId(OneFieldEntityDto oneFieldEntityDto, PriceTypeEnum priceType) {
        return queryService.getEntityDto(
                ItemDataOption.class,
                supplierService.parameterStringSupplier(ARTICULAR_ID, oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(
                        ItemDataOption.class, PriceDto.class, priceType.getValue()));
    }

    @Override
    public ResponsePriceDto getResponsePriceDtoByArticularId(OneFieldEntityDto oneFieldEntityDto) {
        return queryService.getEntityDto(
                ItemDataOption.class,
                supplierService.parameterStringSupplier(ARTICULAR_ID, oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(ItemDataOption.class, ResponsePriceDto.class));
    }

    @Override
    public ResponsePriceDto getResponsePriceDtoByOrderId(OneFieldEntityDto oneFieldEntityDto) {
        return queryService.getEntityDto(
                OrderItemData.class,
                supplierService.parameterStringSupplier(ORDER_ID, oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(OrderItemData.class, ResponsePriceDto.class));
    }

    @Override
    public List<PriceDto> getPrices() {
        return entityOperationDao.getEntityDtoList("",
                transformationFunctionService.getTransformationFunction(Price.class, PriceDto.class));
    }

    private void updateFullPrice(Payment payment, Price price) {
        Price existingPrice = payment.getFullPrice();
        if (existingPrice != null) {
            price.setId(existingPrice.getId());
        }
        payment.setFullPrice(price);
    }

    private void updateTotalPrice(Payment payment, Price price) {
        Price existingPrice = payment.getTotalPrice();
        if (existingPrice != null) {
            price.setId(existingPrice.getId());
        }
        payment.setTotalPrice(price);
    }

    private void updateFullPrice(ItemDataOption itemDataOption, Price price) {
        Price existingPrice = itemDataOption.getFullPrice();
        if (existingPrice != null) {
            price.setId(existingPrice.getId());
        }
        itemDataOption.setFullPrice(price);
    }

    private void updateTotalPrice(ItemDataOption itemDataOption, Price price) {
        Price existingPrice = itemDataOption.getTotalPrice();
        if (existingPrice != null) {
            price.setId(existingPrice.getId());
        }
        itemDataOption.setTotalPrice(price);
    }
}
