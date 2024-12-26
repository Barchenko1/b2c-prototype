package com.b2c.prototype.service.processor.price.base;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.b2c.prototype.dao.price.IPriceDao;
import com.b2c.prototype.modal.constant.PriceType;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.PriceDto;
import com.b2c.prototype.modal.dto.request.PriceDtoSearchField;
import com.b2c.prototype.modal.dto.response.ResponsePriceDto;
import com.b2c.prototype.modal.entity.item.ItemDataOption;
import com.b2c.prototype.modal.entity.order.OrderItemData;
import com.b2c.prototype.modal.entity.payment.Payment;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.query.IQueryService;
import com.b2c.prototype.service.supplier.ISupplierService;
import com.tm.core.processor.finder.parameter.Parameter;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

class PriceServiceTest {

    @Mock
    private IPriceDao priceDao;
    @Mock
    private IQueryService queryService;
    @Mock
    private ITransformationFunctionService transformationFunctionService;
    @Mock
    private ISupplierService supplierService;
    @InjectMocks
    private PriceService priceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUpdateFullPriceByArticularId() {
        PriceDtoSearchField priceDtoSearchField = getPriceDtoSearchField();

        ItemDataOption itemDataOption = mock(ItemDataOption.class);

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        PriceType priceType = PriceType.FULL_PRICE;

        when(supplierService.parameterStringSupplier("articularId", priceDtoSearchField.getSearchField()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(ItemDataOption.class, parameterSupplier))
                .thenReturn(itemDataOption);
        when(transformationFunctionService.getEntity(Price.class, priceDtoSearchField.getNewEntity(), priceType.getValue()))
                .thenReturn(getPrice(100.0));
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(itemDataOption);
            return null;
        }).when(priceDao).executeConsumer(any(Consumer.class));

        priceService.saveUpdatePriceByArticularId(priceDtoSearchField, priceType);

        verify(priceDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testSaveUpdateTotalPriceByArticularId() {
        PriceDtoSearchField priceDtoSearchField = getPriceDtoSearchField();

        ItemDataOption itemDataOption = mock(ItemDataOption.class);

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        PriceType priceType = PriceType.TOTAL_PRICE;

        when(supplierService.parameterStringSupplier("articularId", priceDtoSearchField.getSearchField()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(ItemDataOption.class, parameterSupplier))
                .thenReturn(itemDataOption);
        when(transformationFunctionService.getEntity(Price.class, priceDtoSearchField.getNewEntity(), priceType.getValue()))
                .thenReturn(getPrice(100.0));
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(itemDataOption);
            return null;
        }).when(priceDao).executeConsumer(any(Consumer.class));

        priceService.saveUpdatePriceByArticularId(priceDtoSearchField, priceType);

        verify(priceDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testSaveUpdateFullPriceByArticularIdExistingPriceNull() {
        PriceDtoSearchField priceDtoSearchField = getPriceDtoSearchField();

        ItemDataOption itemDataOption = mock(ItemDataOption.class);
        when(itemDataOption.getFullPrice()).thenReturn(getPrice(100.0));
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        PriceType priceType = PriceType.FULL_PRICE;

        Function<Price, PriceDto> function = mock(Function.class);
        when(supplierService.parameterStringSupplier("articularId", priceDtoSearchField.getSearchField()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(ItemDataOption.class, parameterSupplier))
                .thenReturn(itemDataOption);
        when(transformationFunctionService.getEntity(Price.class, priceDtoSearchField.getNewEntity(), priceType.getValue()))
                .thenReturn(getPrice(100.0));
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(itemDataOption);
            return null;
        }).when(priceDao).executeConsumer(any(Consumer.class));

        priceService.saveUpdatePriceByArticularId(priceDtoSearchField, priceType);

        verify(priceDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testSaveUpdateTotalPriceByArticularIdExistingPriceNull() {
        PriceDtoSearchField priceDtoSearchField = getPriceDtoSearchField();

        ItemDataOption itemDataOption = mock(ItemDataOption.class);
        when(itemDataOption.getTotalPrice()).thenReturn(getPrice(100.0));
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        PriceType priceType = PriceType.TOTAL_PRICE;

        when(supplierService.parameterStringSupplier("articularId", priceDtoSearchField.getSearchField()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(ItemDataOption.class, parameterSupplier))
                .thenReturn(itemDataOption);
        when(transformationFunctionService.getEntity(Price.class, priceDtoSearchField.getNewEntity(), priceType.getValue()))
                .thenReturn(getPrice(100.0));
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(itemDataOption);
            return null;
        }).when(priceDao).executeConsumer(any(Consumer.class));

        priceService.saveUpdatePriceByArticularId(priceDtoSearchField, priceType);

        verify(priceDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testSaveUpdateTotalPriceByOrderId() {
        PriceDtoSearchField priceDtoSearchField = getPriceDtoSearchField();

        OrderItemData orderItemData = mock(OrderItemData.class);
        Payment payment = mock(Payment.class);
        when(orderItemData.getPayment()).thenReturn(payment);

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        PriceType priceType = PriceType.TOTAL_PRICE;

        when(supplierService.parameterStringSupplier("order_id", priceDtoSearchField.getSearchField()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(OrderItemData.class, parameterSupplier))
                .thenReturn(orderItemData);
        when(transformationFunctionService.getEntity(Price.class, priceDtoSearchField.getNewEntity(), priceType.getValue()))
                .thenReturn(getPrice(100.0));
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(payment);
            return null;
        }).when(priceDao).executeConsumer(any(Consumer.class));

        priceService.saveUpdatePriceByOrderId(priceDtoSearchField, priceType);

        verify(priceDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testSaveUpdateFullPriceByOrderId() {
        PriceDtoSearchField priceDtoSearchField = getPriceDtoSearchField();

        OrderItemData orderItemData = mock(OrderItemData.class);
        Payment payment = mock(Payment.class);
        when(orderItemData.getPayment()).thenReturn(payment);

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        PriceType priceType = PriceType.FULL_PRICE;

        when(supplierService.parameterStringSupplier("order_id", priceDtoSearchField.getSearchField()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(OrderItemData.class, parameterSupplier))
                .thenReturn(orderItemData);
        when(transformationFunctionService.getEntity(Price.class, priceDtoSearchField.getNewEntity(), priceType.getValue()))
                .thenReturn(getPrice(100.0));
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(payment);
            return null;
        }).when(priceDao).executeConsumer(any(Consumer.class));

        priceService.saveUpdatePriceByOrderId(priceDtoSearchField, priceType);

        verify(priceDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testSaveUpdateTotalPriceByOrderIdExistingPriceNull() {
        PriceDtoSearchField priceDtoSearchField = getPriceDtoSearchField();

        OrderItemData orderItemData = mock(OrderItemData.class);
        Payment payment = mock(Payment.class);
        when(orderItemData.getPayment()).thenReturn(payment);
        when(payment.getTotalPrice()).thenReturn(getPrice(100.0));
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        PriceType priceType = PriceType.TOTAL_PRICE;

        when(supplierService.parameterStringSupplier("order_id", priceDtoSearchField.getSearchField()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(OrderItemData.class, parameterSupplier))
                .thenReturn(orderItemData);
        when(transformationFunctionService.getEntity(Price.class, priceDtoSearchField.getNewEntity(), priceType.getValue()))
                .thenReturn(getPrice(100.0));
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(payment);
            return null;
        }).when(priceDao).executeConsumer(any(Consumer.class));

        priceService.saveUpdatePriceByOrderId(priceDtoSearchField, priceType);

        verify(priceDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testSaveUpdateFullPriceByOrderIdExistingPriceNull() {
        PriceDtoSearchField priceDtoSearchField = getPriceDtoSearchField();

        OrderItemData orderItemData = mock(OrderItemData.class);
        Payment payment = mock(Payment.class);
        when(orderItemData.getPayment()).thenReturn(payment);
        when(payment.getFullPrice()).thenReturn(getPrice(100.0));
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        PriceType priceType = PriceType.FULL_PRICE;

        when(supplierService.parameterStringSupplier("order_id", priceDtoSearchField.getSearchField()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(OrderItemData.class, parameterSupplier))
                .thenReturn(orderItemData);
        when(transformationFunctionService.getEntity(Price.class, priceDtoSearchField.getNewEntity(), priceType.getValue()))
                .thenReturn(getPrice(100.0));
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(payment);
            return null;
        }).when(priceDao).executeConsumer(any(Consumer.class));

        priceService.saveUpdatePriceByOrderId(priceDtoSearchField, priceType);

        verify(priceDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testDeleteFullPriceByOrderId() {
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto();
        oneFieldEntityDto.setValue("123");
        PriceType priceType = PriceType.FULL_PRICE;
        Price price = getPrice(100.0);
        Supplier<Price> priceSupplier = () -> price;

        Function<OrderItemData, Price> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(OrderItemData.class, Price.class, priceType.getValue()))
                .thenReturn(function);
        when(supplierService.entityFieldSupplier(
                OrderItemData.class,
                "order_id",
                oneFieldEntityDto.getValue(),
                function
        )).thenReturn(priceSupplier);
        priceService.deletePriceByOrderId(oneFieldEntityDto, priceType);

        verify(priceDao).deleteEntity(priceSupplier);
    }

    @Test
    void testDeleteTotalPriceByArticularId() {
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto();
        oneFieldEntityDto.setValue("123");
        PriceType priceType = PriceType.TOTAL_PRICE;
        Price price = getPrice(100.0);
        Supplier<Price> priceSupplier = () -> price;

        Function<ItemDataOption, Price> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(ItemDataOption.class, Price.class, priceType.getValue()))
                .thenReturn(function);
        when(supplierService.entityFieldSupplier(
                ItemDataOption.class,
                "articularId",
                oneFieldEntityDto.getValue(),
                function
        )).thenReturn(priceSupplier);
        priceService.deletePriceByArticularId(oneFieldEntityDto, priceType);

        verify(priceDao).deleteEntity(any(Supplier.class));
    }

    @Test
    void testGetFullPriceByOrderId() {
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto();
        oneFieldEntityDto.setValue("123");
        OrderItemData orderItemData = mock(OrderItemData.class);
        PriceType priceType = PriceType.FULL_PRICE;
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        PriceDto priceDto = getPriceDto(150.0);
        Function<OrderItemData, PriceDto> function = mock(Function.class);
        when(supplierService.parameterStringSupplier("order_id", oneFieldEntityDto.getValue()))
                .thenReturn(parameterSupplier);
        when(transformationFunctionService.getTransformationFunction(OrderItemData.class, PriceDto.class, priceType.getValue()))
                .thenReturn(function);
        when(function.apply(orderItemData)).thenReturn(priceDto);
        when(queryService.getEntityDto(OrderItemData.class, parameterSupplier, function))
                .thenAnswer(invocation -> {
                    Supplier<Parameter> supplierArg = invocation.getArgument(1);
                    Function<OrderItemData, PriceDto> functionArg = invocation.getArgument(2);
                    assertEquals(parameterSupplier.get(), supplierArg.get());
                    return functionArg.apply(orderItemData);
                });

        PriceDto result = priceService.getPriceByOrderId(oneFieldEntityDto, priceType);

        assertNotNull(result);
        assertEquals(150.0, result.getAmount());
    }

    @Test
    void testGetFullPriceByArticularId() {
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto();
        oneFieldEntityDto.setValue("123");
        ItemDataOption itemDataOption = mock(ItemDataOption.class);
        PriceType priceType = PriceType.FULL_PRICE;
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        PriceDto priceDto = getPriceDto(150.0);
        Function<ItemDataOption, PriceDto> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(ItemDataOption.class, PriceDto.class, priceType.getValue()))
                .thenReturn(function);
        when(supplierService.parameterStringSupplier("articularId", oneFieldEntityDto.getValue()))
                .thenReturn(parameterSupplier);
        when(function.apply(itemDataOption)).thenReturn(priceDto);
        when(queryService.getEntityDto(ItemDataOption.class, parameterSupplier, function))
                .thenAnswer(invocation -> {
                    Supplier<Parameter> supplierArg = invocation.getArgument(1);
                    Function<ItemDataOption, PriceDto> functionArg = invocation.getArgument(2);
                    assertEquals(parameterSupplier.get(), supplierArg.get());
                    return functionArg.apply(itemDataOption);
                });

        PriceDto result = priceService.getPriceByArticularId(oneFieldEntityDto, priceType);

        assertNotNull(result);
        assertEquals(150.0, result.getAmount());
    }

    @Test
    void testGetResponsePriceDtoByOrderId() {
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto();
        oneFieldEntityDto.setValue("123");
        OrderItemData orderItemData = mock(OrderItemData.class);
        Payment payment = mock(Payment.class);
        when(orderItemData.getPayment()).thenReturn(payment);

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        ResponsePriceDto responsePriceDto = getResponsePriceDto(150.0);
        Function<OrderItemData, ResponsePriceDto> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(OrderItemData.class, ResponsePriceDto.class))
                .thenReturn(function);
        when(supplierService.parameterStringSupplier("order_id", oneFieldEntityDto.getValue()))
                .thenReturn(parameterSupplier);
        when(function.apply(orderItemData)).thenReturn(responsePriceDto);
        when(queryService.getEntityDto(OrderItemData.class, parameterSupplier, function))
                .thenAnswer(invocation -> {
                    Supplier<Parameter> supplierArg = invocation.getArgument(1);
                    Function<OrderItemData, ResponsePriceDto> functionArg = invocation.getArgument(2);
                    assertEquals(parameterSupplier.get(), supplierArg.get());
                    return functionArg.apply(orderItemData);
                });

        ResponsePriceDto result = priceService.getResponsePriceDtoByOrderId(oneFieldEntityDto);

        assertNotNull(result);
        assertEquals(150.0, result.getFullPrice().getAmount());
        assertEquals(150.0, result.getTotalPrice().getAmount());
    }

    @Test
    void testGetResponsePriceDtoByArticularId() {
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto();
        oneFieldEntityDto.setValue("123");
        ItemDataOption itemDataOption = mock(ItemDataOption.class);
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        ResponsePriceDto responsePriceDto = getResponsePriceDto(150.0);
        Function<ItemDataOption, ResponsePriceDto> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(ItemDataOption.class, ResponsePriceDto.class))
                .thenReturn(function);
        when(supplierService.parameterStringSupplier("articularId", oneFieldEntityDto.getValue()))
                .thenReturn(parameterSupplier);
        when(function.apply(itemDataOption)).thenReturn(responsePriceDto);
        when(queryService.getEntityDto(ItemDataOption.class, parameterSupplier, function))
                .thenAnswer(invocation -> {
                    Supplier<Parameter> supplierArg = invocation.getArgument(1);
                    Function<ItemDataOption, ResponsePriceDto> functionArg = invocation.getArgument(2);
                    assertEquals(parameterSupplier.get(), supplierArg.get());
                    return functionArg.apply(itemDataOption);
                });

        ResponsePriceDto result = priceService.getResponsePriceDtoByArticularId(oneFieldEntityDto);

        assertNotNull(result);
        assertEquals(150.0, result.getFullPrice().getAmount());
        assertEquals(150.0, result.getTotalPrice().getAmount());
    }

    @Test
    void testGetPrices() {
        Price price = getPrice(150.0);
        PriceDto priceDto = getPriceDto(150.0);
        Function<Price, PriceDto> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(Price.class, PriceDto.class))
                .thenReturn(function);
        when(function.apply(any(Price.class))).thenReturn(priceDto);
        when(priceDao.getEntityList()).thenReturn(List.of(price));

        List<PriceDto> priceDtoList = priceService.getPrices();

        priceDtoList.forEach(result -> {
            assertEquals(150.0, result.getAmount());
        });
    }

    private PriceDtoSearchField getPriceDtoSearchField() {
        return PriceDtoSearchField.builder()
                .searchField("ART123")
                .newEntity(getPriceDto(10))
                .build();
    }

    private Currency getCurrency() {
        return Currency.builder()
                .id(1L)
                .value("USD")
                .build();
    }

    private ResponsePriceDto getResponsePriceDto(double amount) {
        return ResponsePriceDto.builder()
                .fullPrice(getPriceDto(amount))
                .totalPrice(getPriceDto(amount))
                .build();
    }

    private Price getPrice(double price) {
        return Price.builder()
                .currency(getCurrency())
                .amount(price)
                .build();
    }

    private PriceDto getPriceDto(double price) {
        return PriceDto.builder()
                .currency(getCurrency().getValue())
                .amount(price)
                .build();
    }

}