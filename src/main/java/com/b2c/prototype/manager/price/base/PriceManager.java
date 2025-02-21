package com.b2c.prototype.manager.price.base;

import com.b2c.prototype.dao.price.IPriceDao;
import com.b2c.prototype.modal.constant.PriceTypeEnum;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.payload.PriceDto;
import com.b2c.prototype.modal.dto.searchfield.PriceSearchFieldEntityDto;
import com.b2c.prototype.modal.dto.response.ResponsePriceDto;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.order.OrderArticularItem;
import com.b2c.prototype.modal.entity.payment.Payment;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.price.IPriceManager;
import com.b2c.prototype.service.query.ISearchService;
import com.b2c.prototype.service.common.EntityOperationManager;
import com.b2c.prototype.service.common.IEntityOperationManager;
import com.b2c.prototype.service.supplier.ISupplierService;

import java.util.List;

import static com.b2c.prototype.util.Constant.ORDER_ID;
import static com.b2c.prototype.util.Constant.ARTICULAR_ID;

public class PriceManager implements IPriceManager {

    private final IEntityOperationManager entityOperationDao;
    private final ISearchService searchService;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;

    public PriceManager(IPriceDao priceDao,
                        ISearchService searchService,
                        ITransformationFunctionService transformationFunctionService,
                        ISupplierService supplierService) {
        this.entityOperationDao = new EntityOperationManager(priceDao);
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
        this.searchService = searchService;
    }

    @Override
    public void saveUpdatePriceByOrderId(PriceSearchFieldEntityDto priceSearchFieldEntityDto, PriceTypeEnum priceType) {
        entityOperationDao.executeConsumer(session -> {
            OrderArticularItem orderItemDataOption = searchService.getEntity(
                    OrderArticularItem.class,
                    supplierService.parameterStringSupplier(ORDER_ID, priceSearchFieldEntityDto.getSearchField()));
            Price price = transformationFunctionService.getEntity(
                    Price.class, priceSearchFieldEntityDto.getNewEntity(), priceType.getValue());
            Payment payment = orderItemDataOption.getPayment();

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
            ArticularItem articularItem = searchService.getEntity(
                    ArticularItem.class,
                    supplierService.parameterStringSupplier(ARTICULAR_ID, priceSearchFieldEntityDto.getSearchField()));
            Price price = transformationFunctionService.getEntity(
                    Price.class, priceSearchFieldEntityDto.getNewEntity(), priceType.getValue());

            if (priceType == PriceTypeEnum.FULL_PRICE) {
                updateFullPrice(articularItem, price);
            }
            if (priceType == PriceTypeEnum.TOTAL_PRICE) {
                updateTotalPrice(articularItem, price);
            }
            session.merge(articularItem);
        });
    }

    @Override
    public void deletePriceByOrderId(OneFieldEntityDto oneFieldEntityDto, PriceTypeEnum priceType) {
        entityOperationDao.deleteEntity(
                supplierService.entityFieldSupplier(
                        OrderArticularItem.class,
                        supplierService.parameterStringSupplier(ORDER_ID, oneFieldEntityDto.getValue()),
                        transformationFunctionService.getTransformationFunction(
                                OrderArticularItem.class, Price.class, priceType.getValue())
                )
        );
    }

    @Override
    public void deletePriceByArticularId(OneFieldEntityDto oneFieldEntityDto, PriceTypeEnum priceType) {
        entityOperationDao.deleteEntity(
                supplierService.entityFieldSupplier(
                        ArticularItem.class,
                        supplierService.parameterStringSupplier(ARTICULAR_ID, oneFieldEntityDto.getValue()),
                        transformationFunctionService.getTransformationFunction(
                                ArticularItem.class, Price.class, priceType.getValue())
                )
        );
    }

    @Override
    public PriceDto getPriceByOrderId(OneFieldEntityDto oneFieldEntityDto, PriceTypeEnum priceType) {
        return searchService.getEntityDto(
                OrderArticularItem.class,
                supplierService.parameterStringSupplier(ORDER_ID, oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(
                        OrderArticularItem.class, PriceDto.class, priceType.getValue()));
    }

    @Override
    public PriceDto getPriceByArticularId(OneFieldEntityDto oneFieldEntityDto, PriceTypeEnum priceType) {
        return searchService.getEntityDto(
                ArticularItem.class,
                supplierService.parameterStringSupplier(ARTICULAR_ID, oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(
                        ArticularItem.class, PriceDto.class, priceType.getValue()));
    }

    @Override
    public ResponsePriceDto getResponsePriceDtoByArticularId(OneFieldEntityDto oneFieldEntityDto) {
        return searchService.getEntityDto(
                ArticularItem.class,
                supplierService.parameterStringSupplier(ARTICULAR_ID, oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(ArticularItem.class, ResponsePriceDto.class));
    }

    @Override
    public ResponsePriceDto getResponsePriceDtoByOrderId(OneFieldEntityDto oneFieldEntityDto) {
        return searchService.getEntityDto(
                OrderArticularItem.class,
                supplierService.parameterStringSupplier(ORDER_ID, oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(OrderArticularItem.class, ResponsePriceDto.class));
    }

    @Override
    public List<PriceDto> getPrices() {
        return entityOperationDao.getEntityGraphDtoList("",
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

    private void updateFullPrice(ArticularItem articularItem, Price price) {
        Price existingPrice = articularItem.getFullPrice();
        if (existingPrice != null) {
            price.setId(existingPrice.getId());
        }
        articularItem.setFullPrice(price);
    }

    private void updateTotalPrice(ArticularItem articularItem, Price price) {
        Price existingPrice = articularItem.getTotalPrice();
        if (existingPrice != null) {
            price.setId(existingPrice.getId());
        }
        articularItem.setTotalPrice(price);
    }
}
