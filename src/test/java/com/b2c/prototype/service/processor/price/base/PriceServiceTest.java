package com.b2c.prototype.service.processor.price.base;

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
        PriceSearchFieldEntityDto priceSearchFieldEntityDto = getPriceDtoSearchField();

        ItemDataOption itemDataOption = mock(ItemDataOption.class);

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        PriceTypeEnum priceType = PriceTypeEnum.FULL_PRICE;

        when(supplierService.parameterStringSupplier(ARTICULAR_ID, priceSearchFieldEntityDto.getSearchField()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(ItemDataOption.class, parameterSupplier))
                .thenReturn(itemDataOption);
        when(transformationFunctionService.getEntity(Price.class, priceSearchFieldEntityDto.getNewEntity(), priceType.getValue()))
                .thenReturn(getPrice(100.0));
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(itemDataOption);
            return null;
        }).when(priceDao).executeConsumer(any(Consumer.class));

        priceService.saveUpdatePriceByArticularId(priceSearchFieldEntityDto, priceType);

        verify(priceDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testSaveUpdateTotalPriceByArticularId() {
        PriceSearchFieldEntityDto priceSearchFieldEntityDto = getPriceDtoSearchField();

        ItemDataOption itemDataOption = mock(ItemDataOption.class);

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        PriceTypeEnum priceType = PriceTypeEnum.TOTAL_PRICE;

        when(supplierService.parameterStringSupplier(ARTICULAR_ID, priceSearchFieldEntityDto.getSearchField()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(ItemDataOption.class, parameterSupplier))
                .thenReturn(itemDataOption);
        when(transformationFunctionService.getEntity(Price.class, priceSearchFieldEntityDto.getNewEntity(), priceType.getValue()))
                .thenReturn(getPrice(100.0));
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(itemDataOption);
            return null;
        }).when(priceDao).executeConsumer(any(Consumer.class));

        priceService.saveUpdatePriceByArticularId(priceSearchFieldEntityDto, priceType);

        verify(priceDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testSaveUpdateFullPriceByArticularIdExistingPriceNull() {
        PriceSearchFieldEntityDto priceSearchFieldEntityDto = getPriceDtoSearchField();

        ItemDataOption itemDataOption = mock(ItemDataOption.class);
        when(itemDataOption.getFullPrice()).thenReturn(getPrice(100.0));
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        PriceTypeEnum priceType = PriceTypeEnum.FULL_PRICE;

        Function<Price, PriceDto> function = mock(Function.class);
        when(supplierService.parameterStringSupplier(ARTICULAR_ID, priceSearchFieldEntityDto.getSearchField()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(ItemDataOption.class, parameterSupplier))
                .thenReturn(itemDataOption);
        when(transformationFunctionService.getEntity(Price.class, priceSearchFieldEntityDto.getNewEntity(), priceType.getValue()))
                .thenReturn(getPrice(100.0));
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(itemDataOption);
            return null;
        }).when(priceDao).executeConsumer(any(Consumer.class));

        priceService.saveUpdatePriceByArticularId(priceSearchFieldEntityDto, priceType);

        verify(priceDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testSaveUpdateTotalPriceByArticularIdExistingPriceNull() {
        PriceSearchFieldEntityDto priceSearchFieldEntityDto = getPriceDtoSearchField();

        ItemDataOption itemDataOption = mock(ItemDataOption.class);
        when(itemDataOption.getTotalPrice()).thenReturn(getPrice(100.0));
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        PriceTypeEnum priceType = PriceTypeEnum.TOTAL_PRICE;

        when(supplierService.parameterStringSupplier(ARTICULAR_ID, priceSearchFieldEntityDto.getSearchField()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(ItemDataOption.class, parameterSupplier))
                .thenReturn(itemDataOption);
        when(transformationFunctionService.getEntity(Price.class, priceSearchFieldEntityDto.getNewEntity(), priceType.getValue()))
                .thenReturn(getPrice(100.0));
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(itemDataOption);
            return null;
        }).when(priceDao).executeConsumer(any(Consumer.class));

        priceService.saveUpdatePriceByArticularId(priceSearchFieldEntityDto, priceType);

        verify(priceDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testSaveUpdateTotalPriceByOrderId() {
        PriceSearchFieldEntityDto priceSearchFieldEntityDto = getPriceDtoSearchField();

        OrderItemData orderItemData = mock(OrderItemData.class);
        Payment payment = mock(Payment.class);
        when(orderItemData.getPayment()).thenReturn(payment);

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        PriceTypeEnum priceType = PriceTypeEnum.TOTAL_PRICE;

        when(supplierService.parameterStringSupplier(ORDER_ID, priceSearchFieldEntityDto.getSearchField()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(OrderItemData.class, parameterSupplier))
                .thenReturn(orderItemData);
        when(transformationFunctionService.getEntity(Price.class, priceSearchFieldEntityDto.getNewEntity(), priceType.getValue()))
                .thenReturn(getPrice(100.0));
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(payment);
            return null;
        }).when(priceDao).executeConsumer(any(Consumer.class));

        priceService.saveUpdatePriceByOrderId(priceSearchFieldEntityDto, priceType);

        verify(priceDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testSaveUpdateFullPriceByOrderId() {
        PriceSearchFieldEntityDto priceSearchFieldEntityDto = getPriceDtoSearchField();

        OrderItemData orderItemData = mock(OrderItemData.class);
        Payment payment = mock(Payment.class);
        when(orderItemData.getPayment()).thenReturn(payment);

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        PriceTypeEnum priceType = PriceTypeEnum.FULL_PRICE;

        when(supplierService.parameterStringSupplier(ORDER_ID, priceSearchFieldEntityDto.getSearchField()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(OrderItemData.class, parameterSupplier))
                .thenReturn(orderItemData);
        when(transformationFunctionService.getEntity(Price.class, priceSearchFieldEntityDto.getNewEntity(), priceType.getValue()))
                .thenReturn(getPrice(100.0));
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(payment);
            return null;
        }).when(priceDao).executeConsumer(any(Consumer.class));

        priceService.saveUpdatePriceByOrderId(priceSearchFieldEntityDto, priceType);

        verify(priceDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testSaveUpdateTotalPriceByOrderIdExistingPriceNull() {
        PriceSearchFieldEntityDto priceSearchFieldEntityDto = getPriceDtoSearchField();

        OrderItemData orderItemData = mock(OrderItemData.class);
        Payment payment = mock(Payment.class);
        when(orderItemData.getPayment()).thenReturn(payment);
        when(payment.getTotalPrice()).thenReturn(getPrice(100.0));
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        PriceTypeEnum priceType = PriceTypeEnum.TOTAL_PRICE;

        when(supplierService.parameterStringSupplier(ORDER_ID, priceSearchFieldEntityDto.getSearchField()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(OrderItemData.class, parameterSupplier))
                .thenReturn(orderItemData);
        when(transformationFunctionService.getEntity(Price.class, priceSearchFieldEntityDto.getNewEntity(), priceType.getValue()))
                .thenReturn(getPrice(100.0));
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(payment);
            return null;
        }).when(priceDao).executeConsumer(any(Consumer.class));

        priceService.saveUpdatePriceByOrderId(priceSearchFieldEntityDto, priceType);

        verify(priceDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testSaveUpdateFullPriceByOrderIdExistingPriceNull() {
        PriceSearchFieldEntityDto priceSearchFieldEntityDto = getPriceDtoSearchField();

        OrderItemData orderItemData = mock(OrderItemData.class);
        Payment payment = mock(Payment.class);
        when(orderItemData.getPayment()).thenReturn(payment);
        when(payment.getFullPrice()).thenReturn(getPrice(100.0));
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        PriceTypeEnum priceType = PriceTypeEnum.FULL_PRICE;

        when(supplierService.parameterStringSupplier(ORDER_ID, priceSearchFieldEntityDto.getSearchField()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(OrderItemData.class, parameterSupplier))
                .thenReturn(orderItemData);
        when(transformationFunctionService.getEntity(Price.class, priceSearchFieldEntityDto.getNewEntity(), priceType.getValue()))
                .thenReturn(getPrice(100.0));
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(payment);
            return null;
        }).when(priceDao).executeConsumer(any(Consumer.class));

        priceService.saveUpdatePriceByOrderId(priceSearchFieldEntityDto, priceType);

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
        Function<OrderItemData, Price> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(OrderItemData.class, Price.class, priceType.getValue()))
                .thenReturn(function);
        when(supplierService.entityFieldSupplier(
                OrderItemData.class,
                parameterSupplier,
                function
        )).thenReturn(priceSupplier);
        priceService.deletePriceByOrderId(oneFieldEntityDto, priceType);

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

        Function<ItemDataOption, Price> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(ItemDataOption.class, Price.class, priceType.getValue()))
                .thenReturn(function);
        when(supplierService.entityFieldSupplier(
                ItemDataOption.class,
                parameterSupplier,
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
        PriceTypeEnum priceType = PriceTypeEnum.FULL_PRICE;
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        PriceDto priceDto = getPriceDto(150.0);
        Function<OrderItemData, PriceDto> function = mock(Function.class);
        when(supplierService.parameterStringSupplier(ORDER_ID, oneFieldEntityDto.getValue()))
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
        PriceTypeEnum priceType = PriceTypeEnum.FULL_PRICE;
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        PriceDto priceDto = getPriceDto(150.0);
        Function<ItemDataOption, PriceDto> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(ItemDataOption.class, PriceDto.class, priceType.getValue()))
                .thenReturn(function);
        when(supplierService.parameterStringSupplier(ARTICULAR_ID, oneFieldEntityDto.getValue()))
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
        when(supplierService.parameterStringSupplier(ORDER_ID, oneFieldEntityDto.getValue()))
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
        when(supplierService.parameterStringSupplier(ARTICULAR_ID, oneFieldEntityDto.getValue()))
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