package com.b2c.prototype.service.processor.item.base;

import com.b2c.prototype.dao.cashed.IEntityCachedMap;
import com.b2c.prototype.dao.item.ICurrencyDiscountDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.CurrencyDiscountDto;
import com.b2c.prototype.modal.dto.update.CurrencyDiscountDtoUpdate;
import com.b2c.prototype.modal.entity.item.CurrencyDiscount;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.service.common.IEntityOperationDao;
import com.tm.core.processor.finder.factory.IParameterFactory;
import com.tm.core.processor.finder.parameter.Parameter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class CurrencyDiscountServiceTest {

    @Mock
    private IParameterFactory parameterFactory;
    @Mock
    private ICurrencyDiscountDao currencyDiscountDao;
    @Mock
    private IEntityCachedMap entityCachedMap;
    @InjectMocks
    private CurrencyDiscountService currencyDiscountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void savePercentDiscount_shouldSaveCurrencyDiscount() {
        CurrencyDiscountDto dto = createTestDto();
        Currency currency = creatTestCurrency();
        when(entityCachedMap.getEntity(Currency.class, "value", dto.getCurrency())).thenReturn(currency);

        currencyDiscountService.saveCurrencyDiscount(dto);

        ArgumentCaptor<Supplier<CurrencyDiscount>> captor = ArgumentCaptor.forClass(Supplier.class);
        verify(currencyDiscountDao).saveEntity(captor.capture());
        CurrencyDiscount capturedEntity = captor.getValue().get();
        assertEquals(dto.getCharSequenceCode(), capturedEntity.getCharSequenceCode());
        assertEquals(dto.getAmount(), capturedEntity.getAmount());
        assertEquals(currency, capturedEntity.getCurrency());
    }

    @Test
    void updatePercentDiscount_shouldUpdateCurrencyDiscount() {
        CurrencyDiscountDto newDto = createTestDto();
        CurrencyDiscountDtoUpdate updateDto = new CurrencyDiscountDtoUpdate();
        updateDto.setNewEntityDto(newDto);
        updateDto.setSearchField("CODE123");
        Currency currency = creatTestCurrency();
        when(entityCachedMap.getEntity(Currency.class, "value", newDto.getCurrency())).thenReturn(currency);
        Parameter mockParameter = mock(Parameter.class);
        when(parameterFactory.createStringParameter("charSequenceCode", updateDto.getSearchField())).thenReturn(mockParameter);

        currencyDiscountService.updateCurrencyDiscount(updateDto);

        ArgumentCaptor<CurrencyDiscount> captor = ArgumentCaptor.forClass(CurrencyDiscount.class);
        verify(currencyDiscountDao).findEntityAndUpdate(captor.capture(), eq(mockParameter));
        CurrencyDiscount capturedEntity = captor.getValue();
        assertEquals(newDto.getCharSequenceCode(), capturedEntity.getCharSequenceCode());
        assertEquals(newDto.getAmount(), capturedEntity.getAmount());
        assertEquals(currency, capturedEntity.getCurrency());
    }

    @Test
    void deletePercentDiscount_shouldDeleteCurrencyDiscount() {
        CurrencyDiscountDto dto = createTestDto();
        Parameter mockParameter = mock(Parameter.class);
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto(dto.getCharSequenceCode());
        when(parameterFactory.createStringParameter("charSequenceCode", dto.getCharSequenceCode())).thenReturn(mockParameter);

        currencyDiscountService.deleteCurrencyDiscount(oneFieldEntityDto);

        verify(currencyDiscountDao).findEntityAndDelete(mockParameter);
    }

    @Test
    void getCurrencyDiscount_shouldReturnCurrencyDiscount() {
        String code = "CODE123";
        Parameter mockParameter = mock(Parameter.class);
        CurrencyDiscount entity = createTestCurrencyDiscount();
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto(code);
        when(parameterFactory.createStringParameter("charSequenceCode", code))
                .thenReturn(mockParameter);
        when(currencyDiscountDao.getEntity(mockParameter)).thenReturn(entity);

        CurrencyDiscountDto result = currencyDiscountService.getCurrencyDiscount(oneFieldEntityDto);
        CurrencyDiscountDto expectedDto = createTestDto();
        assertEquals(expectedDto, result);
    }

    @Test
    void getOptionalCurrencyDiscount_shouldReturnOptionalCurrencyDiscount() {
        String code = "CODE123";
        Parameter mockParameter = mock(Parameter.class);
        CurrencyDiscount entity = createTestCurrencyDiscount();
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto(code);
        when(parameterFactory.createStringParameter("charSequenceCode", code))
                .thenReturn(mockParameter);
        when(currencyDiscountDao.getEntity(mockParameter)).thenReturn(entity);

        Optional<CurrencyDiscountDto> result = currencyDiscountService.getOptionalCurrencyDiscount(oneFieldEntityDto);

        assertTrue(result.isPresent());
        CurrencyDiscountDto expectedDto = createTestDto();
        assertEquals(expectedDto, result.get());
    }

    @Test
    void getOptionalCurrencyDiscount_ShouldReturnEmptyOptional() {
        String code = "CODE123";
        Parameter mockParameter = mock(Parameter.class);
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto(code);
        when(parameterFactory.createStringParameter("charSequenceCode", code))
                .thenReturn(mockParameter);
        when(currencyDiscountDao.getEntity(mockParameter)).thenReturn(null);

        Optional<CurrencyDiscountDto> result = currencyDiscountService.getOptionalCurrencyDiscount(oneFieldEntityDto);

        assertFalse(result.isPresent());
    }

    @Test
    void getPercentDiscounts_shouldReturnListOfCurrencyDiscounts() {
        when(currencyDiscountDao.getEntityList()).thenReturn(List.of(
                createTestCurrencyDiscount(),
                CurrencyDiscount.builder()
                        .amount(200)
                        .currency(Currency.builder()
                                .value("EUR")
                                .build())
                        .charSequenceCode("CODE124")
                        .build()
        ));

        List<CurrencyDiscountDto> result = currencyDiscountService.getCurrencyDiscounts();

        List<CurrencyDiscountDto> expectedList = List.of(
                createTestDto(),
                CurrencyDiscountDto.builder()
                        .amount(200)
                        .currency("EUR")
                        .charSequenceCode("CODE124")
                        .build()
        );
        assertEquals(expectedList, result);
    }

    private CurrencyDiscountDto createTestDto() {
        return CurrencyDiscountDto.builder()
                .charSequenceCode("CODE123")
                .amount(100)
                .currency("USA")
                .build();
    }

    private CurrencyDiscount createTestCurrencyDiscount() {
        return CurrencyDiscount.builder()
                .charSequenceCode("CODE123")
                .amount(100)
                .currency(creatTestCurrency())
                .build();
    }

    private Currency creatTestCurrency() {
        return Currency.builder()
                .value("USA")
                .build();
    }
}
