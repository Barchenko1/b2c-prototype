package com.b2c.prototype.manager.price.base;

import static com.b2c.prototype.util.Constant.ORDER_ID;
import static com.b2c.prototype.util.Constant.ARTICULAR_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import com.b2c.prototype.dao.price.IPriceDao;
import com.b2c.prototype.modal.constant.PriceTypeEnum;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.payload.PriceDto;
import com.b2c.prototype.modal.dto.searchfield.PriceSearchFieldEntityDto;
import com.b2c.prototype.modal.dto.response.ResponsePriceDto;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.order.OrderArticularItem;
import com.b2c.prototype.modal.entity.payment.Payment;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.query.ISearchService;
import com.b2c.prototype.service.supplier.ISupplierService;
import com.tm.core.finder.parameter.Parameter;
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

class PriceManagerTest {

    @Mock
    private IPriceDao priceDao;
    @Mock
    private ISearchService queryService;
    @Mock
    private ITransformationFunctionService transformationFunctionService;
    @Mock
    private ISupplierService supplierService;
    @InjectMocks
    private PriceManager priceManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUpdateFullPriceByArticularId() {
        PriceSearchFieldEntityDto priceSearchFieldEntityDto = getPriceDtoSearchField();

        ArticularItem articularItem = mock(ArticularItem.class);

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        PriceTypeEnum priceType = PriceTypeEnum.FULL_PRICE;

        when(supplierService.parameterStringSupplier(ARTICULAR_ID, priceSearchFieldEntityDto.getSearchField()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(ArticularItem.class, parameterSupplier))
                .thenReturn(articularItem);
        when(transformationFunctionService.getEntity(Price.class, priceSearchFieldEntityDto.getNewEntity(), priceType.getValue()))
                .thenReturn(getPrice(100.0));
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(articularItem);
            return null;
        }).when(priceDao).executeConsumer(any(Consumer.class));

        priceManager.saveUpdatePriceByArticularId(priceSearchFieldEntityDto, priceType);

        verify(priceDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testSaveUpdateTotalPriceByArticularId() {
        PriceSearchFieldEntityDto priceSearchFieldEntityDto = getPriceDtoSearchField();

        ArticularItem articularItem = mock(ArticularItem.class);

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        PriceTypeEnum priceType = PriceTypeEnum.TOTAL_PRICE;

        when(supplierService.parameterStringSupplier(ARTICULAR_ID, priceSearchFieldEntityDto.getSearchField()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(ArticularItem.class, parameterSupplier))
                .thenReturn(articularItem);
        when(transformationFunctionService.getEntity(Price.class, priceSearchFieldEntityDto.getNewEntity(), priceType.getValue()))
                .thenReturn(getPrice(100.0));
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(articularItem);
            return null;
        }).when(priceDao).executeConsumer(any(Consumer.class));

        priceManager.saveUpdatePriceByArticularId(priceSearchFieldEntityDto, priceType);

        verify(priceDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testSaveUpdateFullPriceByArticularIdExistingPriceNull() {
        PriceSearchFieldEntityDto priceSearchFieldEntityDto = getPriceDtoSearchField();

        ArticularItem articularItem = mock(ArticularItem.class);
        when(articularItem.getFullPrice()).thenReturn(getPrice(100.0));
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        PriceTypeEnum priceType = PriceTypeEnum.FULL_PRICE;

        Function<Price, PriceDto> function = mock(Function.class);
        when(supplierService.parameterStringSupplier(ARTICULAR_ID, priceSearchFieldEntityDto.getSearchField()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(ArticularItem.class, parameterSupplier))
                .thenReturn(articularItem);
        when(transformationFunctionService.getEntity(Price.class, priceSearchFieldEntityDto.getNewEntity(), priceType.getValue()))
                .thenReturn(getPrice(100.0));
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(articularItem);
            return null;
        }).when(priceDao).executeConsumer(any(Consumer.class));

        priceManager.saveUpdatePriceByArticularId(priceSearchFieldEntityDto, priceType);

        verify(priceDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testSaveUpdateTotalPriceByArticularIdExistingPriceNull() {
        PriceSearchFieldEntityDto priceSearchFieldEntityDto = getPriceDtoSearchField();

        ArticularItem articularItem = mock(ArticularItem.class);
        when(articularItem.getTotalPrice()).thenReturn(getPrice(100.0));
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        PriceTypeEnum priceType = PriceTypeEnum.TOTAL_PRICE;

        when(supplierService.parameterStringSupplier(ARTICULAR_ID, priceSearchFieldEntityDto.getSearchField()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(ArticularItem.class, parameterSupplier))
                .thenReturn(articularItem);
        when(transformationFunctionService.getEntity(Price.class, priceSearchFieldEntityDto.getNewEntity(), priceType.getValue()))
                .thenReturn(getPrice(100.0));
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(articularItem);
            return null;
        }).when(priceDao).executeConsumer(any(Consumer.class));

        priceManager.saveUpdatePriceByArticularId(priceSearchFieldEntityDto, priceType);

        verify(priceDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testSaveUpdateTotalPriceByOrderId() {
        PriceSearchFieldEntityDto priceSearchFieldEntityDto = getPriceDtoSearchField();

        OrderArticularItem orderItemDataOption = mock(OrderArticularItem.class);
        Payment payment = mock(Payment.class);
        when(orderItemDataOption.getPayment()).thenReturn(payment);

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        PriceTypeEnum priceType = PriceTypeEnum.TOTAL_PRICE;

        when(supplierService.parameterStringSupplier(ORDER_ID, priceSearchFieldEntityDto.getSearchField()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(OrderArticularItem.class, parameterSupplier))
                .thenReturn(orderItemDataOption);
        when(transformationFunctionService.getEntity(Price.class, priceSearchFieldEntityDto.getNewEntity(), priceType.getValue()))
                .thenReturn(getPrice(100.0));
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(payment);
            return null;
        }).when(priceDao).executeConsumer(any(Consumer.class));

        priceManager.saveUpdatePriceByOrderId(priceSearchFieldEntityDto, priceType);

        verify(priceDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testSaveUpdateFullPriceByOrderId() {
        PriceSearchFieldEntityDto priceSearchFieldEntityDto = getPriceDtoSearchField();

        OrderArticularItem orderItemDataOption = mock(OrderArticularItem.class);
        Payment payment = mock(Payment.class);
        when(orderItemDataOption.getPayment()).thenReturn(payment);

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        PriceTypeEnum priceType = PriceTypeEnum.FULL_PRICE;

        when(supplierService.parameterStringSupplier(ORDER_ID, priceSearchFieldEntityDto.getSearchField()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(OrderArticularItem.class, parameterSupplier))
                .thenReturn(orderItemDataOption);
        when(transformationFunctionService.getEntity(Price.class, priceSearchFieldEntityDto.getNewEntity(), priceType.getValue()))
                .thenReturn(getPrice(100.0));
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(payment);
            return null;
        }).when(priceDao).executeConsumer(any(Consumer.class));

        priceManager.saveUpdatePriceByOrderId(priceSearchFieldEntityDto, priceType);

        verify(priceDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testSaveUpdateTotalPriceByOrderIdExistingPriceNull() {
        PriceSearchFieldEntityDto priceSearchFieldEntityDto = getPriceDtoSearchField();

        OrderArticularItem orderItemDataOption = mock(OrderArticularItem.class);
        Payment payment = mock(Payment.class);
        when(orderItemDataOption.getPayment()).thenReturn(payment);
        when(payment.getTotalPrice()).thenReturn(getPrice(100.0));
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        PriceTypeEnum priceType = PriceTypeEnum.TOTAL_PRICE;

        when(supplierService.parameterStringSupplier(ORDER_ID, priceSearchFieldEntityDto.getSearchField()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(OrderArticularItem.class, parameterSupplier))
                .thenReturn(orderItemDataOption);
        when(transformationFunctionService.getEntity(Price.class, priceSearchFieldEntityDto.getNewEntity(), priceType.getValue()))
                .thenReturn(getPrice(100.0));
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(payment);
            return null;
        }).when(priceDao).executeConsumer(any(Consumer.class));

        priceManager.saveUpdatePriceByOrderId(priceSearchFieldEntityDto, priceType);

        verify(priceDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testSaveUpdateFullPriceByOrderIdExistingPriceNull() {
        PriceSearchFieldEntityDto priceSearchFieldEntityDto = getPriceDtoSearchField();

        OrderArticularItem orderItemDataOption = mock(OrderArticularItem.class);
        Payment payment = mock(Payment.class);
        when(orderItemDataOption.getPayment()).thenReturn(payment);
        when(payment.getFullPrice()).thenReturn(getPrice(100.0));
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        PriceTypeEnum priceType = PriceTypeEnum.FULL_PRICE;

        when(supplierService.parameterStringSupplier(ORDER_ID, priceSearchFieldEntityDto.getSearchField()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(OrderArticularItem.class, parameterSupplier))
                .thenReturn(orderItemDataOption);
        when(transformationFunctionService.getEntity(Price.class, priceSearchFieldEntityDto.getNewEntity(), priceType.getValue()))
                .thenReturn(getPrice(100.0));
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(payment);
            return null;
        }).when(priceDao).executeConsumer(any(Consumer.class));

        priceManager.saveUpdatePriceByOrderId(priceSearchFieldEntityDto, priceType);

        verify(priceDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testDeleteFullPriceByOrderId() {
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto();
        oneFieldEntityDto.setValue("123");
        PriceTypeEnum priceType = PriceTypeEnum.FULL_PRICE;
        Price price = getPrice(100.0);
        Supplier<Price> priceSupplier = () -> price;

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier(ORDER_ID, oneFieldEntityDto.getValue()))
                .thenReturn(parameterSupplier);
        Function<OrderArticularItem, Price> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(OrderArticularItem.class, Price.class, priceType.getValue()))
                .thenReturn(function);
        when(supplierService.entityFieldSupplier(
                OrderArticularItem.class,
                parameterSupplier,
                function
        )).thenReturn(priceSupplier);
        priceManager.deletePriceByOrderId(oneFieldEntityDto, priceType);

        verify(priceDao).deleteEntity(priceSupplier);
    }

    @Test
    void testDeleteTotalPriceByArticularId() {
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto();
        oneFieldEntityDto.setValue("123");
        PriceTypeEnum priceType = PriceTypeEnum.TOTAL_PRICE;
        Price price = getPrice(100.0);
        Supplier<Price> priceSupplier = () -> price;

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier(ARTICULAR_ID, oneFieldEntityDto.getValue()))
                .thenReturn(parameterSupplier);

        Function<ArticularItem, Price> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(ArticularItem.class, Price.class, priceType.getValue()))
                .thenReturn(function);
        when(supplierService.entityFieldSupplier(
                ArticularItem.class,
                parameterSupplier,
                function
        )).thenReturn(priceSupplier);
        priceManager.deletePriceByArticularId(oneFieldEntityDto, priceType);

        verify(priceDao).deleteEntity(any(Supplier.class));
    }

    @Test
    void testGetFullPriceByOrderId() {
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto();
        oneFieldEntityDto.setValue("123");
        OrderArticularItem orderItemDataOption = mock(OrderArticularItem.class);
        PriceTypeEnum priceType = PriceTypeEnum.FULL_PRICE;
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        PriceDto priceDto = getPriceDto(150.0);
        Function<OrderArticularItem, PriceDto> function = mock(Function.class);
        when(supplierService.parameterStringSupplier(ORDER_ID, oneFieldEntityDto.getValue()))
                .thenReturn(parameterSupplier);
        when(transformationFunctionService.getTransformationFunction(OrderArticularItem.class, PriceDto.class, priceType.getValue()))
                .thenReturn(function);
        when(function.apply(orderItemDataOption)).thenReturn(priceDto);
        when(queryService.getEntityDto(OrderArticularItem.class, parameterSupplier, function))
                .thenAnswer(invocation -> {
                    Supplier<Parameter> supplierArg = invocation.getArgument(1);
                    Function<OrderArticularItem, PriceDto> functionArg = invocation.getArgument(2);
                    assertEquals(parameterSupplier.get(), supplierArg.get());
                    return functionArg.apply(orderItemDataOption);
                });

        PriceDto result = priceManager.getPriceByOrderId(oneFieldEntityDto, priceType);

        assertNotNull(result);
        assertEquals(150.0, result.getAmount());
    }

    @Test
    void testGetFullPriceByArticularId() {
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto();
        oneFieldEntityDto.setValue("123");
        ArticularItem articularItem = mock(ArticularItem.class);
        PriceTypeEnum priceType = PriceTypeEnum.FULL_PRICE;
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        PriceDto priceDto = getPriceDto(150.0);
        Function<ArticularItem, PriceDto> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(ArticularItem.class, PriceDto.class, priceType.getValue()))
                .thenReturn(function);
        when(supplierService.parameterStringSupplier(ARTICULAR_ID, oneFieldEntityDto.getValue()))
                .thenReturn(parameterSupplier);
        when(function.apply(articularItem)).thenReturn(priceDto);
        when(queryService.getEntityDto(ArticularItem.class, parameterSupplier, function))
                .thenAnswer(invocation -> {
                    Supplier<Parameter> supplierArg = invocation.getArgument(1);
                    Function<ArticularItem, PriceDto> functionArg = invocation.getArgument(2);
                    assertEquals(parameterSupplier.get(), supplierArg.get());
                    return functionArg.apply(articularItem);
                });

        PriceDto result = priceManager.getPriceByArticularId(oneFieldEntityDto, priceType);

        assertNotNull(result);
        assertEquals(150.0, result.getAmount());
    }

    @Test
    void testGetResponsePriceDtoByOrderId() {
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto();
        oneFieldEntityDto.setValue("123");
        OrderArticularItem orderItemDataOption = mock(OrderArticularItem.class);
        Payment payment = mock(Payment.class);
        when(orderItemDataOption.getPayment()).thenReturn(payment);

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        ResponsePriceDto responsePriceDto = getResponsePriceDto(150.0);
        Function<OrderArticularItem, ResponsePriceDto> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(OrderArticularItem.class, ResponsePriceDto.class))
                .thenReturn(function);
        when(supplierService.parameterStringSupplier(ORDER_ID, oneFieldEntityDto.getValue()))
                .thenReturn(parameterSupplier);
        when(function.apply(orderItemDataOption)).thenReturn(responsePriceDto);
        when(queryService.getEntityDto(OrderArticularItem.class, parameterSupplier, function))
                .thenAnswer(invocation -> {
                    Supplier<Parameter> supplierArg = invocation.getArgument(1);
                    Function<OrderArticularItem, ResponsePriceDto> functionArg = invocation.getArgument(2);
                    assertEquals(parameterSupplier.get(), supplierArg.get());
                    return functionArg.apply(orderItemDataOption);
                });

        ResponsePriceDto result = priceManager.getResponsePriceDtoByOrderId(oneFieldEntityDto);

        assertNotNull(result);
        assertEquals(150.0, result.getFullPrice().getAmount());
        assertEquals(150.0, result.getTotalPrice().getAmount());
    }

    @Test
    void testGetResponsePriceDtoByArticularId() {
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto();
        oneFieldEntityDto.setValue("123");
        ArticularItem articularItem = mock(ArticularItem.class);
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        ResponsePriceDto responsePriceDto = getResponsePriceDto(150.0);
        Function<ArticularItem, ResponsePriceDto> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(ArticularItem.class, ResponsePriceDto.class))
                .thenReturn(function);
        when(supplierService.parameterStringSupplier(ARTICULAR_ID, oneFieldEntityDto.getValue()))
                .thenReturn(parameterSupplier);
        when(function.apply(articularItem)).thenReturn(responsePriceDto);
        when(queryService.getEntityDto(ArticularItem.class, parameterSupplier, function))
                .thenAnswer(invocation -> {
                    Supplier<Parameter> supplierArg = invocation.getArgument(1);
                    Function<ArticularItem, ResponsePriceDto> functionArg = invocation.getArgument(2);
                    assertEquals(parameterSupplier.get(), supplierArg.get());
                    return functionArg.apply(articularItem);
                });

        ResponsePriceDto result = priceManager.getResponsePriceDtoByArticularId(oneFieldEntityDto);

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

        List<PriceDto> priceDtoList = priceManager.getPrices();

        priceDtoList.forEach(result -> {
            assertEquals(150.0, result.getAmount());
        });
    }

    private PriceSearchFieldEntityDto getPriceDtoSearchField() {
        return PriceSearchFieldEntityDto.builder()
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