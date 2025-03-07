package com.b2c.prototype.manager.price.base;

import com.b2c.prototype.dao.price.IPriceDao;
import com.b2c.prototype.modal.constant.PriceTypeEnum;
import com.b2c.prototype.modal.dto.payload.PriceDto;
import com.b2c.prototype.modal.dto.response.ResponsePriceDto;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.order.OrderArticularItemQuantity;
import com.b2c.prototype.modal.entity.payment.Payment;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.price.IPriceManager;
import com.b2c.prototype.service.query.ISearchService;
import com.b2c.prototype.service.supplier.ISupplierService;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.manager.common.EntityOperationManager;
import com.tm.core.process.manager.common.IEntityOperationManager;

import java.util.List;

import static com.b2c.prototype.util.Constant.ORDER_ID;
import static com.b2c.prototype.util.Constant.ARTICULAR_ID;

public class PriceManager implements IPriceManager {

    private final IEntityOperationManager entityOperationDao;
    private final ISearchService searchService;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;
    private final IParameterFactory parameterFactory;

    public PriceManager(IPriceDao priceDao,
                        ISearchService searchService,
                        ITransformationFunctionService transformationFunctionService,
                        ISupplierService supplierService,
                        IParameterFactory parameterFactory) {
        this.entityOperationDao = new EntityOperationManager(priceDao);
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
        this.searchService = searchService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void saveUpdatePriceByOrderId(String orderId, PriceDto priceDto, PriceTypeEnum priceType) {
        entityOperationDao.executeConsumer(session -> {
            OrderArticularItemQuantity orderItemDataOption = searchService.getNamedQueryEntity(
                    OrderArticularItemQuantity.class,
                    "",
                    parameterFactory.createStringParameter(ORDER_ID, orderId));
            Price price = transformationFunctionService.getEntity(
                    Price.class, priceDto, priceType.getValue());
//            Payment payment = orderItemDataOption.getPayment();
//
//            if (priceType == PriceTypeEnum.FULL_PRICE) {
//                updateFullPrice(payment, price);
//            }
//            if (priceType == PriceTypeEnum.TOTAL_PRICE) {
//                updateTotalPrice(payment, price);
//            }
//            session.merge(payment);
        });
    }

    @Override
    public void saveUpdatePriceByArticularId(String articularId, PriceDto priceDto, PriceTypeEnum priceType) {
        entityOperationDao.executeConsumer(session -> {
            ArticularItem articularItem = searchService.getNamedQueryEntity(
                    ArticularItem.class,
                    "",
                    parameterFactory.createStringParameter(ARTICULAR_ID, articularId));
            Price price = transformationFunctionService.getEntity(
                    Price.class, priceDto, priceType.getValue());

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
    public void deletePriceByOrderId(String orderId, PriceTypeEnum priceType) {
        entityOperationDao.deleteEntity(
                supplierService.entityFieldSupplier(
                        OrderArticularItemQuantity.class,
                        "",
                        supplierService.parameterStringSupplier(ORDER_ID, orderId),
                        transformationFunctionService.getTransformationFunction(
                                OrderArticularItemQuantity.class, Price.class, priceType.getValue())
                )
        );
    }

    @Override
    public void deletePriceByArticularId(String articularId, PriceTypeEnum priceType) {
        entityOperationDao.deleteEntity(
                supplierService.entityFieldSupplier(
                        ArticularItem.class,
                        "",
                        supplierService.parameterStringSupplier(ARTICULAR_ID, articularId),
                        transformationFunctionService.getTransformationFunction(
                                ArticularItem.class, Price.class, priceType.getValue())
                )
        );
    }

    @Override
    public PriceDto getPriceByOrderId(String orderId, PriceTypeEnum priceType) {
        return searchService.getNamedQueryEntityDto(
                OrderArticularItemQuantity.class,
                "",
                parameterFactory.createStringParameter(ORDER_ID, orderId),
                transformationFunctionService.getTransformationFunction(
                        OrderArticularItemQuantity.class, PriceDto.class, priceType.getValue()));
    }

    @Override
    public PriceDto getPriceByArticularId(String articularId, PriceTypeEnum priceType) {
        return searchService.getNamedQueryEntityDto(
                ArticularItem.class,
                "",
                parameterFactory.createStringParameter(ARTICULAR_ID, articularId),
                transformationFunctionService.getTransformationFunction(
                        ArticularItem.class, PriceDto.class, priceType.getValue()));
    }

    @Override
    public ResponsePriceDto getResponsePriceDtoByArticularId(String articularId) {
        return searchService.getGraphEntityDto(
                ArticularItem.class,
                "",
                parameterFactory.createStringParameter(ARTICULAR_ID, articularId),
                transformationFunctionService.getTransformationFunction(ArticularItem.class, ResponsePriceDto.class));
    }

    @Override
    public ResponsePriceDto getResponsePriceDtoByOrderId(String orderId) {
        return searchService.getNamedQueryEntityDto(
                OrderArticularItemQuantity.class,
                "",
                parameterFactory.createStringParameter(ORDER_ID, orderId),
                transformationFunctionService.getTransformationFunction(OrderArticularItemQuantity.class, ResponsePriceDto.class));
    }

    @Override
    public List<PriceDto> getPrices() {
        return entityOperationDao.getGraphEntityDtoList(
                "",
                transformationFunctionService.getTransformationFunction(Price.class, PriceDto.class));
    }

    private void updateFullPrice(Payment payment, Price price) {
        Price existingPrice = payment.getCommissionPrice();
        if (existingPrice != null) {
            price.setId(existingPrice.getId());
        }
        payment.setCommissionPrice(price);
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
