package com.b2c.prototype.service.processor.address.base;

import com.b2c.prototype.modal.dto.payload.CountryDto;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.service.processor.AbstractConstantEntityServiceTest;
import com.tm.core.processor.finder.parameter.Parameter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static com.b2c.prototype.util.Constant.VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class CountryServiceTest extends AbstractConstantEntityServiceTest<Country> {

    @Mock
    private Function<Country, CountryDto> mapEntityToDtoFunction;
    @Mock
    private Function<CountryDto, Country> mapDtoToEntityFunction;
    private CountryService countryService;

    @BeforeEach
    void setUp() {
        when(transformationFunctionService.getTransformationFunction(CountryDto.class, Country.class))
                .thenReturn(mapDtoToEntityFunction);
        when(transformationFunctionService.getTransformationFunction(Country.class, CountryDto.class))
                .thenReturn(mapEntityToDtoFunction);

        countryService = new CountryService(
                parameterFactory,
                dao,
                transformationFunctionService,
                singleValueMap
        );
    }

    @Test
    public void testSaveEntity() {
        CountryDto countryDto = getCountryDto();
        Country testValue = createTestValue();

        when(mapDtoToEntityFunction.apply(countryDto)).thenReturn(testValue);
        when(dao.getEntityClass()).thenAnswer(invocation -> Country.class);

        countryService.saveEntity(countryDto);

        verifySaveEntity(testValue);
    }

    @Test
    public void testUpdateEntity() {
        CountryDto newDto = CountryDto.builder()
                .label("newLabel")
                .value("newValue")
                .build();

        Country testValue = Country.builder()
                .value("newValue")
                .build();

        when(mapDtoToEntityFunction.apply(newDto)).thenReturn(testValue);
        when(dao.getEntityClass()).thenAnswer(invocation -> Country.class);

        countryService.updateEntity("testValue", newDto);

        verifyUpdateEntity(testValue, newDto.getValue());
    }

    @Test
    public void testDeleteEntity() {
        countryService.deleteEntity("testValue");

        verifyDeleteEntity("testValue");
    }

    @Test
    public void testGetEntity() {
        Parameter parameter = parameterFactory.createStringParameter(VALUE, "testValue");
        Country testValue = createTestValue();
        CountryDto countryDto = getCountryDto();

        when(mapEntityToDtoFunction.apply(testValue)).thenReturn(countryDto);
        when(parameterFactory.createStringParameter(VALUE, "testValue")).thenReturn(parameter);
        when(dao.getEntity(parameter)).thenReturn(testValue);

        CountryDto result = countryService.getEntity("testValue");

        assertEquals(countryDto, result);
    }

    @Test
    public void testGetEntityOptional() {
        Parameter parameter = parameterFactory.createStringParameter(VALUE, "testValue");
        Country testValue = createTestValue();
        CountryDto countryDto = getCountryDto();

        when(mapEntityToDtoFunction.apply(testValue)).thenReturn(countryDto);
        when(parameterFactory.createStringParameter(VALUE, "testValue")).thenReturn(parameter);
        when(dao.getEntity(parameter)).thenReturn(testValue);

        Optional<CountryDto> result = countryService.getEntityOptional("testValue");

        assertTrue(result.isPresent());
        assertEquals(Optional.of(countryDto), result);
    }

    @Test
    public void testGetEntities() {
        Country testValue = createTestValue();
        CountryDto countryDto = getCountryDto();

        when(mapEntityToDtoFunction.apply(testValue)).thenReturn(countryDto);
        when(dao.getEntityList()).thenReturn(List.of(testValue));

        List<CountryDto> list = countryService.getEntities();

        assertEquals(1, list.size());
        assertEquals(countryDto, list.get(0));
    }

    private Country createTestValue() {
        return Country.builder()
                .value("testValue")
                .label("testLabel")
                .build();
    }

    private CountryDto getCountryDto() {
        return CountryDto.builder()
                .value("testValue")
                .label("testLabel")
                .build();
    }
}