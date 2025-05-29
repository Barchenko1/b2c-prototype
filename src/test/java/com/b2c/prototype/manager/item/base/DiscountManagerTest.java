package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.dao.item.IDiscountDao;
import com.b2c.prototype.modal.dto.payload.discount.DiscountDto;
import com.b2c.prototype.modal.dto.payload.discount.DiscountStatusDto;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.transform.function.ITransformationFunctionService;

import com.tm.core.finder.parameter.Parameter;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DiscountManagerTest {

    @Mock
    private IDiscountDao discountDao;

    @Mock
    private ITransformationFunctionService transformationFunctionService;
    @InjectMocks
    private DiscountManager discountManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void savePercentDiscount_shouldSaveDiscount() {
        DiscountDto dto = createTestDto();
        Currency currency = creatTestCurrency();
        Discount discount = createTestDiscount();
        Supplier<Discount> supplier = () -> discount;

        discountManager.saveDiscount(dto);

        ArgumentCaptor<Supplier<Discount>> captor = ArgumentCaptor.forClass(Supplier.class);
        verify(discountDao).saveEntity(captor.capture());
        Discount capturedEntity = captor.getValue().get();
        assertEquals(dto.getCharSequenceCode(), capturedEntity.getCharSequenceCode());
        assertEquals(dto.getAmount(), capturedEntity.getAmount());
        assertEquals(currency, capturedEntity.getCurrency());
    }

    @Test
    void updateItemDataDiscount_shouldUpdateDiscount() {
        String articularId = "CODE123";
        DiscountDto dto = createTestDto();
        Parameter mockParameter = mock(Parameter.class);
        ArticularItem mockArticularItem = mock(ArticularItem.class);
        Discount discount = createTestDiscount();

        Supplier<Parameter> parameterSupplier = () -> mockParameter;
        
        
//        when(queryService.getEntity(ArticularItem.class, parameterSupplier))
//                .thenReturn(mockArticularItem);
//        when(discountDao.getOptionalEntity(mockParameter)).thenReturn(Optional.of(discount));
        when(transformationFunctionService.getEntity(Discount.class, dto))
                .thenReturn(discount);
        when(mockArticularItem.getDiscount()).thenReturn(discount);

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(mockArticularItem);
            return null;
        }).when(discountDao).executeConsumer(any(Consumer.class));

        discountManager.updateItemDataDiscount(articularId, dto);

        verify(discountDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void updateDiscount_shouldUpdateDiscount() {
        String charSequenceCode = "CODE123";
        DiscountDto dto = createTestDto();
        Parameter mockParameter = mock(Parameter.class);
        ArticularItem mockArticularItem = mock(ArticularItem.class);
        Discount discount = createTestDiscount();

        Supplier<Parameter> parameterSupplier = () -> mockParameter;
        
//        when(discountDao.getEntity(mockParameter)).thenReturn(discount);
        when(transformationFunctionService.getEntity(Discount.class, dto))
                .thenReturn(discount);
        when(mockArticularItem.getDiscount()).thenReturn(discount);

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(discount);
            return null;
        }).when(discountDao).executeConsumer(any(Consumer.class));

        discountManager.updateDiscount(charSequenceCode, dto);

        verify(discountDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void changeDiscountStatus_shouldUpdateDiscount() {
        DiscountStatusDto discountStatusDto = DiscountStatusDto.builder()
                .charSequenceCode("abc")
                .isActive(false)
                .build();
        Discount discount = createTestDiscount();
        discount.setActive(false);

        Parameter mockParameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> mockParameter;

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(discount);
            return null;
        }).when(discountDao).executeConsumer(any(Consumer.class));

        discountManager.changeDiscountStatus(discountStatusDto);

        verify(discountDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void deleteDiscount_shouldDeleteDiscount() {
        DiscountDto dto = createTestDto();
        Parameter mockParameter = mock(Parameter.class);
        ArticularItem mockArticularItem = mock(ArticularItem.class);
        Discount discount = createTestDiscount();

        Supplier<Parameter> parameterSupplier = () -> mockParameter;
        
//        when(queryService.getNamedQueryEntity(
//                eq(ArticularItem.class),
//                anyString(),
//                eq(parameterSupplier))
//        ).thenReturn(List.of(mockArticularItem));
        when(mockArticularItem.getDiscount()).thenReturn(discount);

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(mockArticularItem);
            return null;
        }).when(discountDao).executeConsumer(any(Consumer.class));

        discountManager.deleteDiscount(dto.getCharSequenceCode());

        verify(discountDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void getDiscount_shouldReturnDiscount() {
        String code = "CODE123";
        Parameter mockParameter = mock(Parameter.class);
        Function<Collection<ArticularItem>, DiscountDto> function = mock(Function.class);
        DiscountDto expectedDto = createTestDto();
//        when(queryService.getSubNamedQueryEntityDtoList(
//                eq(ArticularItem.class),
//                anyString(),
//                eq(mockParameter),
//                eq(function)
//        )).thenReturn(expectedDto);
        when(transformationFunctionService.getCollectionTransformationFunction(ArticularItem.class, DiscountDto.class))
                .thenReturn(function);
        when(function.apply(anyList())).thenReturn(expectedDto);

        DiscountDto result = discountManager.getDiscount(code);
        assertEquals(expectedDto.getCurrency(), result.getCurrency());
        assertEquals(expectedDto.getArticularIdSet(), result.getArticularIdSet());
        assertEquals(expectedDto.getAmount(), result.getAmount());
        assertEquals(expectedDto.getIsActive(), result.getIsActive());
    }

    @Test
    void getDiscounts_shouldReturnListOfDiscounts() {
        String code = "CODE123";
        Parameter mockParameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> mockParameter;
        Function<Collection<ArticularItem>, Collection<DiscountDto>> function = mock(Function.class);
        List<Discount> discounts = List.of(createTestDiscount(),
                Discount.builder()
                        .amount(200)
                        .currency(Currency.builder()
                                .value("EUR")
                                .build())
                        .charSequenceCode("CODE124")
                        .isActive(true)
                        .build());
        List<DiscountDto> expectedList = List.of(
                createTestDto(),
                DiscountDto.builder()
                        .amount(200)
                        .currency("EUR")
                        .charSequenceCode("CODE124")
                        .isActive(true)
                        .build()
        );
//        when(queryService.getNamedQueryEntityDtoList(
//                eq(ArticularItem.class),
//                anyString(),
//                eq(function)
//        )).thenReturn(expectedList);
//        when(transformationFunctionService.getCollectionTransformationCollectionFunction(ArticularItem.class, DiscountDto.class, "list"))
//                .thenReturn(function);

        List<DiscountDto> result = discountManager.getDiscounts();
        assertEquals(expectedList, result);
    }

    private DiscountDto createTestDto() {
        return DiscountDto.builder()
                .charSequenceCode("CODE123")
                .amount(100)
                .currency("USA")
                .isActive(true)
                .build();
    }

    private Discount createTestDiscount() {
        return Discount.builder()
                .charSequenceCode("CODE123")
                .amount(100)
                .isActive(true)
                .currency(creatTestCurrency())
                .build();
    }

    private Currency creatTestCurrency() {
        return Currency.builder()
                .value("USA")
                .build();
    }
}
