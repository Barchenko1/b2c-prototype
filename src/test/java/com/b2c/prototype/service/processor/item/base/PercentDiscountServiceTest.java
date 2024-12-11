package com.b2c.prototype.service.processor.item.base;

import com.b2c.prototype.dao.item.IPercentDiscountDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.PercentDiscountDto;
import com.b2c.prototype.modal.dto.update.PercentDiscountDtoUpdate;
import com.b2c.prototype.modal.entity.item.CurrencyDiscount;
import com.b2c.prototype.modal.entity.item.PercentDiscount;
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
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PercentDiscountServiceTest {

    @Mock
    private IParameterFactory parameterFactory;

    @Mock
    private IPercentDiscountDao percentDiscountDao;

    @InjectMocks
    private PercentDiscountService percentDiscountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void savePercentDiscount_ShouldSaveEntity() {
        PercentDiscountDto dto = createTestDto();

        percentDiscountService.savePercentDiscount(dto);

        ArgumentCaptor<Supplier<PercentDiscount>> captor = ArgumentCaptor.forClass(Supplier.class);

        verify(percentDiscountDao).saveEntity(captor.capture());
        PercentDiscount savedEntity = captor.getValue().get();

        assertEquals("DISCOUNT1", savedEntity.getCharSequenceCode());
        assertEquals(10.0, savedEntity.getAmount());
    }

    @Test
    void updatePercentDiscount_ShouldUpdateEntity() {
        PercentDiscountDtoUpdate updateDto = new PercentDiscountDtoUpdate();
        updateDto.setNewEntityDto(PercentDiscountDto.builder()
                        .charSequenceCode("DISCOUNT2")
                        .amount(15)
                        .build());
        updateDto.setSearchField("DISCOUNT1");
        Parameter parameter = mock(Parameter.class);
        when(parameterFactory.createStringParameter("charSequenceCode", "DISCOUNT1")).thenReturn(parameter);

        percentDiscountService.updatePercentDiscount(updateDto);

        ArgumentCaptor<PercentDiscount> entityCaptor = ArgumentCaptor.forClass(PercentDiscount.class);
        verify(percentDiscountDao).findEntityAndUpdate(entityCaptor.capture(), eq(parameter));
        PercentDiscount updatedEntity = entityCaptor.getValue();

        assertEquals("DISCOUNT2", updatedEntity.getCharSequenceCode());
        assertEquals(15.0, updatedEntity.getAmount());
    }

    @Test
    void deletePercentDiscount_ShouldDeleteEntity() {
        Parameter parameter = mock(Parameter.class);
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto("DISCOUNT1");
        when(parameterFactory.createStringParameter("charSequenceCode", "DISCOUNT1")).thenReturn(parameter);

        percentDiscountService.deletePercentDiscount(oneFieldEntityDto);

        verify(percentDiscountDao).findEntityAndDelete(parameter);
    }

    @Test
    void getPercentDiscount_ShouldReturnDto() {
        String charSequenceCode = "DISCOUNT1";
        PercentDiscount entity = createTestEntity();
        Parameter parameter = mock(Parameter.class);
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto(charSequenceCode);

        when(parameterFactory.createStringParameter("charSequenceCode", charSequenceCode))
                .thenReturn(parameter);
        when(percentDiscountDao.getEntity(parameter)).thenReturn(entity);

        PercentDiscountDto result = percentDiscountService.getPercentDiscount(oneFieldEntityDto);

        PercentDiscountDto dto = createTestDto();
        assertEquals(dto, result);
    }

    @Test
    void getOptionalPercentDiscount_ShouldReturnOptionalDto() {
        String charSequenceCode = "DISCOUNT1";
        PercentDiscount entity = createTestEntity();
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto(charSequenceCode);
        Parameter parameter = mock(Parameter.class);
        when(parameterFactory.createStringParameter("charSequenceCode", charSequenceCode))
                .thenReturn(parameter);
        when(percentDiscountDao.getEntity(parameter)).thenReturn(entity);

        Optional<PercentDiscountDto> result = percentDiscountService.getOptionalPercentDiscount(oneFieldEntityDto);

        PercentDiscountDto dto = createTestDto();
        assertTrue(result.isPresent());
        assertEquals(dto, result.get());
    }

    @Test
    void getOptionalPercentDiscount_ShouldReturnEmptyOptional() {
        String charSequenceCode = "DISCOUNT1";
        Parameter parameter = mock(Parameter.class);
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto(charSequenceCode);
        when(parameterFactory.createStringParameter("charSequenceCode", charSequenceCode)).thenReturn(parameter);
        when(percentDiscountDao.getEntity(parameter)).thenReturn(null);

        Optional<PercentDiscountDto> result = percentDiscountService.getOptionalPercentDiscount(oneFieldEntityDto);

        assertFalse(result.isPresent());
    }

    @Test
    void getPercentDiscounts_ShouldReturnDtoList() {
        when(percentDiscountDao.getEntityList()).thenReturn(List.of(
                createTestEntity(),
                PercentDiscount.builder()
                        .charSequenceCode("DISCOUNT2")
                        .amount(15)
                        .build()
        ));

        List<PercentDiscountDto> result = percentDiscountService.getPercentDiscounts();

        List<PercentDiscountDto> expectedDtoList = List.of(
                createTestDto(),
                PercentDiscountDto.builder()
                        .charSequenceCode("DISCOUNT2")
                        .amount(15)
                        .build()
        );

        assertEquals(expectedDtoList, result);
    }

    private PercentDiscountDto createTestDto() {
        return PercentDiscountDto.builder()
                .charSequenceCode("DISCOUNT1")
                .amount(10)
                .build();
    }

    private PercentDiscount createTestEntity() {
        return PercentDiscount.builder()
                .charSequenceCode("DISCOUNT1")
                .amount(10)
                .build();
    }
}
