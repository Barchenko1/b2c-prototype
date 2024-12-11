package com.b2c.prototype.service.processor.price.base;

import com.b2c.prototype.dao.cashed.IEntityCachedMap;
import com.b2c.prototype.dao.price.IPriceDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.PriceDto;
import com.b2c.prototype.modal.dto.request.PriceDtoSearchField;
import com.b2c.prototype.modal.entity.item.ItemData;
import com.b2c.prototype.modal.entity.order.OrderItem;
import com.b2c.prototype.modal.entity.payment.Payment;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.service.processor.price.IPriceService;
import com.b2c.prototype.service.processor.query.IQueryService;
import com.b2c.prototype.service.common.EntityOperationDao;
import com.b2c.prototype.service.common.IEntityOperationDao;
import com.tm.core.processor.finder.factory.IParameterFactory;
import com.tm.core.processor.finder.parameter.Parameter;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class PriceService implements IPriceService {

    private final IParameterFactory parameterFactory;
    private final IEntityOperationDao entityOperationDao;
    private final IQueryService queryService;
    private final IEntityCachedMap entityCachedMap;

    public PriceService(IParameterFactory parameterFactory,
                        IPriceDao priceDao,
                        IQueryService queryService,
                        IEntityCachedMap entityCachedMap) {
        this.parameterFactory = parameterFactory;
        this.entityOperationDao = new EntityOperationDao(priceDao);
        this.queryService = queryService;
        this.entityCachedMap = entityCachedMap;
    }

    @Override
    public void saveFullPriceByOrderId(PriceDtoSearchField priceDtoSearchField) {
        entityOperationDao.saveEntity(session -> {
            OrderItem orderItem = queryService.getEntity(
                    OrderItem.class,
                    orderIdParameterSupplier(priceDtoSearchField.getSearchField()));
            Price price = mapToEntityFunction().apply(priceDtoSearchField.getNewEntityDto());
            Payment payment = orderItem.getPayment();
            payment.setFullPrice(price);
            session.persist(price);
            session.merge(payment);
        });
    }

    @Override
    public void saveTotalPriceByOrderId(PriceDtoSearchField priceDtoSearchField) {
        entityOperationDao.saveEntity(session -> {
            OrderItem orderItem = queryService.getEntity(
                    OrderItem.class,
                    orderIdParameterSupplier(priceDtoSearchField.getSearchField()));
            Price price = mapToEntityFunction().apply(priceDtoSearchField.getNewEntityDto());
            Payment payment = orderItem.getPayment();
            payment.setTotalPrice(price);
            session.persist(price);
            session.merge(payment);
        });
    }

    @Override
    public void saveFullPriceByArticularId(PriceDtoSearchField priceDtoSearchField) {
        entityOperationDao.saveEntity(session -> {
            ItemData itemData = queryService.getEntity(
                    ItemData.class,
                    articularIdParameterSupplier(priceDtoSearchField.getSearchField()));
            Price price = mapToEntityFunction().apply(priceDtoSearchField.getNewEntityDto());
            itemData.setFullPrice(price);
            session.persist(price);
            session.merge(itemData);
        });
    }

    @Override
    public void saveTotalPriceByArticularId(PriceDtoSearchField priceDtoSearchField) {
        entityOperationDao.saveEntity(session -> {
            ItemData itemData = queryService.getEntity(
                    ItemData.class,
                    articularIdParameterSupplier(priceDtoSearchField.getSearchField()));
            Price price = mapToEntityFunction().apply(priceDtoSearchField.getNewEntityDto());
            itemData.setTotalPrice(price);
            session.persist(price);
            session.merge(itemData);
        });
    }

    @Override
    public void updateFullPriceByOrderId(PriceDtoSearchField priceDtoSearchField) {
        entityOperationDao.updateEntity(session -> {
            OrderItem orderItem = queryService.getEntity(
                    OrderItem.class,
                    orderIdParameterSupplier(priceDtoSearchField.getSearchField()));
            Price price = mapToEntityFunction().apply(priceDtoSearchField.getNewEntityDto());
            Payment payment = orderItem.getPayment();
            Price existingFullPrice = payment.getFullPrice();
            existingFullPrice.setAmount(price.getAmount());
            existingFullPrice.setCurrency(price.getCurrency());
            session.merge(existingFullPrice);
        });
    }

    @Override
    public void updateTotalPriceByOrderId(PriceDtoSearchField priceDtoSearchField) {
        entityOperationDao.updateEntity(session -> {
            OrderItem orderItem = queryService.getEntity(
                    OrderItem.class,
                    orderIdParameterSupplier(priceDtoSearchField.getSearchField()));
            Price price = mapToEntityFunction().apply(priceDtoSearchField.getNewEntityDto());
            Payment payment = orderItem.getPayment();
            Price existingFullPrice = payment.getTotalPrice();
            existingFullPrice.setAmount(price.getAmount());
            existingFullPrice.setCurrency(price.getCurrency());
            session.merge(existingFullPrice);
        });
    }

    @Override
    public void updateFullPriceByArticularId(PriceDtoSearchField priceDtoSearchField) {
        entityOperationDao.updateEntity(session -> {
            ItemData itemData = queryService.getEntity(
                    ItemData.class,
                    articularIdParameterSupplier(priceDtoSearchField.getSearchField()));
            Price price = mapToEntityFunction().apply(priceDtoSearchField.getNewEntityDto());
            Price existingFullPrice = itemData.getFullPrice();
            existingFullPrice.setAmount(price.getAmount());
            existingFullPrice.setCurrency(price.getCurrency());
            session.merge(existingFullPrice);
        });
    }

    @Override
    public void updateTotalPriceByArticularId(PriceDtoSearchField priceDtoSearchField) {
        entityOperationDao.updateEntity(session -> {
            ItemData itemData = queryService.getEntity(
                    ItemData.class,
                    articularIdParameterSupplier(priceDtoSearchField.getSearchField()));
            Price price = mapToEntityFunction().apply(priceDtoSearchField.getNewEntityDto());
            Price existingFullPrice = itemData.getTotalPrice();
            existingFullPrice.setAmount(price.getAmount());
            existingFullPrice.setCurrency(price.getCurrency());
            session.merge(existingFullPrice);
        });
    }


    @Override
    public void deleteFullPriceByOrderId(OneFieldEntityDto oneFieldEntityDto) {
        entityOperationDao.deleteEntity(orderItemFullPriceSupplier(oneFieldEntityDto.getValue()));
    }

    @Override
    public void deleteTotalPriceByOrderId(OneFieldEntityDto oneFieldEntityDto) {
        entityOperationDao.deleteEntity(orderItemTotalPriceSupplier(oneFieldEntityDto.getValue()));
    }

    @Override
    public void deleteFullPriceByArticularId(OneFieldEntityDto oneFieldEntityDto) {
        entityOperationDao.deleteEntity(articularIdFullPriceSupplier(oneFieldEntityDto.getValue()));
    }

    @Override
    public void deleteTotalPriceByArticularId(OneFieldEntityDto oneFieldEntityDto) {
        entityOperationDao.deleteEntity(articularIdTotalPriceSupplier(oneFieldEntityDto.getValue()));
    }

    @Override
    public PriceDto getFullPriceByOrderId(OneFieldEntityDto oneFieldEntityDto) {
        return queryService.getEntityDto(
                OrderItem.class,
                orderIdParameterSupplier(oneFieldEntityDto.getValue()),
                mapOrderItemToFullPriceDtoFunction());
    }

    @Override
    public PriceDto getTotalPriceByOrderId(OneFieldEntityDto oneFieldEntityDto) {
        return queryService.getEntityDto(
                OrderItem.class,
                orderIdParameterSupplier(oneFieldEntityDto.getValue()),
                mapOrderItemToTotalPriceDtoFunction());
    }

    @Override
    public PriceDto getFullPriceByArticularId(OneFieldEntityDto oneFieldEntityDto) {
        return queryService.getEntityDto(
                ItemData.class,
                articularIdParameterSupplier(oneFieldEntityDto.getValue()),
                mapItemToFullPriceDtoFunction());
    }

    @Override
    public PriceDto getTotalPriceByArticularId(OneFieldEntityDto oneFieldEntityDto) {
        return queryService.getEntityDto(
                ItemData.class,
                articularIdParameterSupplier(oneFieldEntityDto.getValue()),
                mapItemToTotalPriceDtoFunction());
    }

    @Override
    public List<PriceDto> getPrices() {
        return entityOperationDao.getEntityDtoList(mapPriceToPriceDtoFunction());
    }

    private Supplier<Parameter> orderIdParameterSupplier(String value) {
        return () -> parameterFactory.createStringParameter("orderId", value);
    }

    private Supplier<Parameter> articularIdParameterSupplier(String value) {
        return () -> parameterFactory.createStringParameter("articularId", value);
    }

    private Supplier<Price> orderItemFullPriceSupplier(String value) {
        return () -> {
            OrderItem orderItem = queryService.getEntity(
                    OrderItem.class,
                    orderIdParameterSupplier(value));
            return orderItem.getPayment().getFullPrice();
        };
    }

    private Supplier<Price> orderItemTotalPriceSupplier(String value) {
        return () -> {
            OrderItem orderItem = queryService.getEntity(
                    OrderItem.class,
                    orderIdParameterSupplier(value));
            return orderItem.getPayment().getTotalPrice();
        };
    }

    private Supplier<Price> articularIdFullPriceSupplier(String value) {
        return () -> {
            ItemData itemData = queryService.getEntity(
                    ItemData.class,
                    articularIdParameterSupplier(value));
            return itemData.getFullPrice();
        };
    }

    private Supplier<Price> articularIdTotalPriceSupplier(String value) {
        return () -> {
            ItemData itemData = queryService.getEntity(
                    ItemData.class,
                    articularIdParameterSupplier(value));
            return itemData.getTotalPrice();
        };
    }

    private Function<PriceDto, Price> mapToEntityFunction() {
        return priceDto -> {
            Currency currency = entityCachedMap.getEntity(
                    Currency.class,
                    "value",
                    priceDto.getCurrency());
            return Price.builder()
                    .amount(priceDto.getAmount())
                    .currency(currency)
                    .build();
        };
    }

    private Function<OrderItem, PriceDto> mapOrderItemToFullPriceDtoFunction() {
        return orderItem -> {
            Price fullPrice = orderItem.getPayment().getFullPrice();
            return PriceDto.builder()
                    .amount(fullPrice.getAmount())
                    .currency(fullPrice.getCurrency().getValue())
                    .build();
        };
    }

    private Function<OrderItem, PriceDto> mapOrderItemToTotalPriceDtoFunction() {
        return orderItem -> {
            Price fullPrice = orderItem.getPayment().getTotalPrice();
            return PriceDto.builder()
                    .amount(fullPrice.getAmount())
                    .currency(fullPrice.getCurrency().getValue())
                    .build();
        };
    }

    private Function<ItemData, PriceDto> mapItemToFullPriceDtoFunction() {
        return itemData -> {
            Price fullPrice = itemData.getFullPrice();
            return PriceDto.builder()
                    .amount(fullPrice.getAmount())
                    .currency(fullPrice.getCurrency().getValue())
                    .build();
        };
    }

    private Function<ItemData, PriceDto> mapItemToTotalPriceDtoFunction() {
        return itemData -> {
            Price fullPrice = itemData.getTotalPrice();
            return PriceDto.builder()
                    .amount(fullPrice.getAmount())
                    .currency(fullPrice.getCurrency().getValue())
                    .build();
        };
    }

    private Function<Price, PriceDto> mapPriceToPriceDtoFunction() {
        return price -> PriceDto.builder()
                .amount(price.getAmount())
                .currency(price.getCurrency().getValue())
                .build();
    }
}
