package com.b2c.prototype.service.processor.item.base;

import com.b2c.prototype.dao.item.IDiscountDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.payload.DiscountDto;
import com.b2c.prototype.modal.dto.payload.DiscountStatusDto;
import com.b2c.prototype.modal.dto.response.ResponseDiscountDto;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.item.ItemDataOption;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.query.IQueryService;
import com.b2c.prototype.service.supplier.ISupplierService;
import com.tm.core.processor.finder.parameter.Parameter;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.b2c.prototype.util.Constant.ARTICULAR_ID;
import static com.b2c.prototype.util.Constant.CHAR_SEQUENCE_CODE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DiscountServiceTest {

    @Mock
    private IDiscountDao discountDao;
    @Mock
    private IQueryService queryService;
    @Mock
    private ITransformationFunctionService transformationFunctionService;
    @Mock
    private ISupplierService supplierService;
    @InjectMocks
    private DiscountService discountService;

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
        when(supplierService.getSupplier(Discount.class, dto))
                .thenReturn(supplier);

        discountService.saveDiscount(dto);

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
        Currency currency = creatTestCurrency();
        Parameter mockParameter = mock(Parameter.class);
        ItemDataOption mockItemDataOption = mock(ItemDataOption.class);
        Discount Discount = createTestDiscount();

        Supplier<Parameter> parameterSupplier = () -> mockParameter;
        when(supplierService.parameterStringSupplier(ARTICULAR_ID, articularId))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(ItemDataOption.class, parameterSupplier))
                .thenReturn(mockItemDataOption);
        when(transformationFunctionService.getEntity(Discount.class, dto))
                .thenReturn(Discount);

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(mockItemDataOption);
            return null;
        }).when(discountDao).executeConsumer(any(Consumer.class));

        discountService.updateItemDataDiscount(articularId, dto);

        verify(discountDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void updateDiscount_shouldUpdateDiscount() {
        String code = "CODE123";
        DiscountDto dto = createTestDto();
        Currency currency = creatTestCurrency();
        Parameter mockParameter = mock(Parameter.class);

        Discount discount = createTestDiscount();
        Supplier<Discount> discountSupplier = () -> discount;
        Supplier<Parameter> parameterSupplier = () -> mockParameter;
        when(supplierService.getSupplier(Discount.class, dto))
                .thenReturn(discountSupplier);
        when(supplierService.parameterStringSupplier(CHAR_SEQUENCE_CODE, code))
                .thenReturn(parameterSupplier);

        discountService.updateDiscount(code, dto);

        ArgumentCaptor<Discount> captor = ArgumentCaptor.forClass(Discount.class);
        verify(discountDao).findEntityAndUpdate(captor.capture(), eq(mockParameter));
        Discount capturedEntity = captor.getValue();
        assertEquals(dto.getCharSequenceCode(), capturedEntity.getCharSequenceCode());
        assertEquals(dto.getAmount(), capturedEntity.getAmount());
        assertEquals(currency, capturedEntity.getCurrency());
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

        when(supplierService.parameterStringSupplier(CHAR_SEQUENCE_CODE, discountStatusDto.getCharSequenceCode()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(Discount.class, parameterSupplier))
                .thenReturn(discount);

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(discount);
            return null;
        }).when(discountDao).executeConsumer(any(Consumer.class));

        discountService.changeDiscountStatus(discountStatusDto);

        verify(discountDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void deleteDiscount_shouldDeleteDiscount() {
        DiscountDto dto = createTestDto();
        Parameter mockParameter = mock(Parameter.class);
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto(dto.getCharSequenceCode());
        Supplier<Parameter> parameterSupplier = () -> mockParameter;
        when(supplierService.parameterStringSupplier(CHAR_SEQUENCE_CODE, dto.getCharSequenceCode()))
                .thenReturn(parameterSupplier);

        discountService.deleteDiscount(oneFieldEntityDto);

        verify(discountDao).findEntityAndDelete(mockParameter);
    }

    @Test
    void getDiscount_shouldReturnDiscount() {
        String code = "CODE123";
        Parameter mockParameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> mockParameter;
        Function<Discount, ResponseDiscountDto> mapFunction = discount -> ResponseDiscountDto.builder()
                .amount(discount.getAmount())
                .isActive(discount.isActive())
                .currency(discount.getCurrency().getValue())
                .build();
        Discount entity = createTestDiscount();
        when(discountDao.getEntity(mockParameter)).thenReturn(entity);

        when(supplierService.parameterStringSupplier(CHAR_SEQUENCE_CODE, code))
                .thenReturn(parameterSupplier);
        when(transformationFunctionService.getTransformationFunction(Discount.class, ResponseDiscountDto.class))
                .thenReturn(mapFunction);

        ResponseDiscountDto result = discountService.getDiscount(code);
        ResponseDiscountDto expectedDto = createResponseTestDto();
        assertEquals(expectedDto.getCurrency(), result.getCurrency());
        assertEquals(expectedDto.getArticularId(), result.getArticularId());
        assertEquals(expectedDto.getAmount(), result.getAmount());
        assertEquals(expectedDto.isActive(), result.isActive());
    }

    @Test
    void getOptionalDiscount_shouldReturnOptionalDiscount() {
        String code = "CODE123";
        Parameter mockParameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> mockParameter;
        Function<Discount, ResponseDiscountDto> mapFunction = discount -> ResponseDiscountDto.builder()
                .amount(discount.getAmount())
                .isActive(discount.isActive())
                .currency(discount.getCurrency().getValue())
                .build();
        Discount entity = createTestDiscount();
        when(discountDao.getEntity(mockParameter)).thenReturn(entity);
        when(supplierService.parameterStringSupplier(CHAR_SEQUENCE_CODE, code))
                .thenReturn(parameterSupplier);
        when(transformationFunctionService.getTransformationFunction(Discount.class, ResponseDiscountDto.class))
                .thenReturn(mapFunction);
        Optional<ResponseDiscountDto> optional = discountService.getOptionalDiscount(code);

        assertTrue(optional.isPresent());
        ResponseDiscountDto result = optional.get();
        ResponseDiscountDto expectedDto = createResponseTestDto();
        assertEquals(expectedDto.getCurrency(), result.getCurrency());
        assertEquals(expectedDto.getArticularId(), result.getArticularId());
        assertEquals(expectedDto.getAmount(), result.getAmount());
        assertEquals(expectedDto.isActive(), result.isActive());
    }

    @Test
    void getOptionalDiscount_ShouldReturnEmptyOptional() {
        String code = "CODE123";
        Parameter mockParameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> mockParameter;
        Function<Discount, ResponseDiscountDto> mapFunction = discount -> ResponseDiscountDto.builder()
                .amount(discount.getAmount())
                .isActive(discount.isActive())
                .currency(discount.getCurrency().getValue())
                .build();
        when(discountDao.getEntity(mockParameter)).thenReturn(null);
        when(supplierService.parameterStringSupplier(CHAR_SEQUENCE_CODE, code))
                .thenReturn(parameterSupplier);
        when(transformationFunctionService.getTransformationFunction(Discount.class, ResponseDiscountDto.class))
                .thenReturn(mapFunction);
        Optional<ResponseDiscountDto> result = discountService.getOptionalDiscount(code);

        assertFalse(result.isPresent());
    }

    @Test
    void getDiscounts_shouldReturnListOfDiscounts() {
        String code = "CODE123";
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto(code);
        Parameter mockParameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> mockParameter;
        Function<Discount, DiscountDto> mapFunction = Discount -> DiscountDto.builder()
                .amount(Discount.getAmount())
                .charSequenceCode(Discount.getCharSequenceCode())
                .currency(Discount.getCurrency().getValue())
                .build();
        when(discountDao.getEntityList()).thenReturn(List.of(
                createTestDiscount(),
                Discount.builder()
                        .amount(200)
                        .currency(Currency.builder()
                                .value("EUR")
                                .build())
                        .charSequenceCode("CODE124")
                        .build()
        ));
        when(supplierService.parameterStringSupplier(CHAR_SEQUENCE_CODE, oneFieldEntityDto.getValue()))
                .thenReturn(parameterSupplier);
        when(transformationFunctionService.getTransformationFunction(Discount.class, DiscountDto.class))
                .thenReturn(mapFunction);

        List<DiscountDto> result = discountService.getDiscounts();

        List<DiscountDto> expectedList = List.of(
                createTestDto(),
                DiscountDto.builder()
                        .amount(200)
                        .currency("EUR")
                        .charSequenceCode("CODE124")
                        .build()
        );
        assertEquals(expectedList, result);
    }

    private DiscountDto createTestDto() {
        return DiscountDto.builder()
                .charSequenceCode("CODE123")
                .amount(100)
                .currency("USA")
                .build();
    }

    private ResponseDiscountDto createResponseTestDto() {
        return ResponseDiscountDto.builder()
                .isActive(true)
                .amount(100)
                .currency("USA")
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
