package com.b2c.prototype.transform.order;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.payload.constant.CountryDto;
import com.b2c.prototype.modal.dto.payload.constant.CurrencyDto;
import com.b2c.prototype.modal.dto.payload.item.PriceDto;
import com.b2c.prototype.modal.dto.payload.option.group.TimeDurationOptionGroupDto;
import com.b2c.prototype.modal.dto.payload.option.group.ZoneOptionGroupDto;
import com.b2c.prototype.modal.dto.payload.option.item.TimeDurationOptionDto;
import com.b2c.prototype.modal.dto.payload.option.item.ZoneOptionDto;
import com.b2c.prototype.modal.dto.payload.order.CurrencyConvertDateDto;
import com.b2c.prototype.modal.dto.payload.order.single.ResponseCustomerOrderDetails;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.TimeDurationOption;
import com.b2c.prototype.modal.entity.option.ZoneOption;
import com.b2c.prototype.modal.entity.option.ZoneOptionGroup;
import com.b2c.prototype.modal.entity.order.CustomerSingleDeliveryOrder;
import com.b2c.prototype.modal.entity.payment.CurrencyCoefficient;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.transform.constant.IGeneralEntityTransformService;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.stream.Collectors;

import static com.b2c.prototype.util.Constant.KEY;

@Service
public class OrderTransformService implements IOrderTransformService {

    private final IGeneralEntityDao generalEntityDao;
    private final IGeneralEntityTransformService generalEntityTransformService;

    public OrderTransformService(IGeneralEntityDao generalEntityDao,
                                 IGeneralEntityTransformService generalEntityTransformService) {
        this.generalEntityDao = generalEntityDao;
        this.generalEntityTransformService = generalEntityTransformService;
    }

    @Override
    public OptionGroup mapTimeDurationOptionGroupDtoToOptionGroup(TimeDurationOptionGroupDto timeDurationOptionGroupDto) {
        OptionGroup optionGroup =  OptionGroup.builder()
                .value(timeDurationOptionGroupDto.getValue())
                .key(timeDurationOptionGroupDto.getKey())
                .timeDurationOptions(timeDurationOptionGroupDto.getTimeDurationOptions().stream()
                        .map(timeDurationOptionDto -> TimeDurationOption.builder()
                                .value(timeDurationOptionDto.getValue())
                                .key(timeDurationOptionDto.getKey())
                                .startTime(timeDurationOptionDto.getStartTime())
                                .endTime(timeDurationOptionDto.getEndTime())
                                .timeZone(timeDurationOptionDto.getTimeZone())
                                .durationInMin(Duration.between(
                                                timeDurationOptionDto.getStartTime(),
                                                timeDurationOptionDto.getEndTime())
                                        .toMinutes())
                                .price(Price.builder()
                                        .amount(timeDurationOptionDto.getPrice().getAmount())
                                        .currency(
                                                generalEntityTransformService.mapCurrencyDtoToCurrency(timeDurationOptionDto.getPrice().getCurrency()))
                                        .build())
                                .build())
                        .collect(Collectors.toSet())
                )
                .build();

        optionGroup.getTimeDurationOptions().forEach(optionGroup::addTimeDurationOption);
        return optionGroup;
    }

    @Override
    public TimeDurationOptionGroupDto mapOptionGroupToTimeDurationOptionGroupDto(OptionGroup optionGroup) {
        return TimeDurationOptionGroupDto.builder()
                .value(optionGroup.getValue())
                .key(optionGroup.getKey())
                .timeDurationOptions(optionGroup.getTimeDurationOptions().stream()
                        .map(timeDurationOption -> TimeDurationOptionDto.builder()
//                                .searchKey(timeDurationOption.getKey())
                                .value(timeDurationOption.getValue())
                                .key(timeDurationOption.getKey())
                                .startTime(timeDurationOption.getStartTime())
                                .endTime(timeDurationOption.getEndTime())
                                .timeZone(timeDurationOption.getTimeZone())
                                .price(PriceDto.builder()
                                        .amount(timeDurationOption.getPrice().getAmount())
                                        .currency(CurrencyDto.builder()
                                                .value(timeDurationOption.getPrice().getCurrency().getValue())
                                                .key(timeDurationOption.getPrice().getCurrency().getKey())
                                                .build())
                                        .build())
                                .build())
                        .collect(Collectors.toList())
                )
                .build();
    }

    @Override
    public ZoneOptionGroup mapZoneOptionGroupDtoToZoneOptionGroup(ZoneOptionGroupDto zoneOptionGroupDto) {
        ZoneOptionGroup zoneOptionGroup = ZoneOptionGroup.builder()
                .value(zoneOptionGroupDto.getValue())
                .key(zoneOptionGroupDto.getKey())
                .city(zoneOptionGroupDto.getCity())
                .country(generalEntityTransformService.mapCountryDtoToCountry(zoneOptionGroupDto.getCountry()))
                .zoneOptions(zoneOptionGroupDto.getZoneOptions().stream()
                        .map(zoneOptionDto -> ZoneOption.builder()
                                .value(zoneOptionDto.getValue())
                                .key(zoneOptionDto.getKey())
                                .price(Price.builder()
                                        .amount(zoneOptionDto.getPrice().getAmount())
                                        .currency(
                                                generalEntityTransformService.mapCurrencyDtoToCurrency(zoneOptionDto.getPrice().getCurrency()))
                                        .build())
                                .build())
                        .collect(Collectors.toSet())
                )
                .build();

        zoneOptionGroup.getZoneOptions().forEach(zoneOptionGroup::addZoneOption);
        return zoneOptionGroup;
    }

    @Override
    public ZoneOptionGroupDto mapZoneOptionGroupToZoneOptionGroupDto(ZoneOptionGroup zoneOptionGroup) {
        return ZoneOptionGroupDto.builder()
                .value(zoneOptionGroup.getValue())
                .key(zoneOptionGroup.getKey())
                .country(CountryDto.builder()
                        .value(zoneOptionGroup.getCountry().getValue())
                        .key(zoneOptionGroup.getCountry().getKey())
                        .build())
                .city(zoneOptionGroup.getCity())
                .zoneOptions(zoneOptionGroup.getZoneOptions().stream()
                        .map(zoneOption -> ZoneOptionDto.builder()
//                                .searchKey(zoneOption.getKey())
                                .key(zoneOption.getKey())
                                .value(zoneOption.getValue())
                                .price(PriceDto.builder()
                                        .amount(zoneOption.getPrice().getAmount())
                                        .currency(CurrencyDto.builder()
                                                .value(zoneOption.getPrice().getCurrency().getValue())
                                                .key(zoneOption.getPrice().getCurrency().getKey())
                                                .build())
                                        .build())
                                .build())
                        .collect(Collectors.toList())
                )
                .build();
    }

    @Override
    public CurrencyCoefficient mapCurrencyConvertDateDtoToCurrencyCoefficient(CurrencyConvertDateDto currencyConvertDateDto) {
        return CurrencyCoefficient.builder()
                .currencyFrom(generalEntityTransformService.mapCurrencyDtoToCurrency(currencyConvertDateDto.getCurrencyFrom()))
                .currencyTo(generalEntityTransformService.mapCurrencyDtoToCurrency(currencyConvertDateDto.getCurrencyTo()))
                .coefficient(currencyConvertDateDto.getCoefficient())
                .dateOfCreate(LocalDate.now())
                .build();
    }

    @Override
    public CurrencyConvertDateDto mapCurrencyCoefficientToCurrencyConvertDateDto(CurrencyCoefficient currencyCoefficient) {
        return CurrencyConvertDateDto.builder()
                .currencyFrom(CurrencyDto.builder()
                        .key(currencyCoefficient.getCurrencyFrom().getKey())
                        .value(currencyCoefficient.getCurrencyFrom().getValue())
                        .build())
                .currencyTo(CurrencyDto.builder()
                        .key(currencyCoefficient.getCurrencyTo().getKey())
                        .value(currencyCoefficient.getCurrencyTo().getValue())
                        .build())
                .coefficient(currencyCoefficient.getCoefficient())
                .dateOfCreate(currencyCoefficient.getDateOfCreate())
                .build();
    }

    @Override
    public ResponseCustomerOrderDetails mapCustomerSingleDeliveryOrderToResponseCustomerOrderDetails(CustomerSingleDeliveryOrder customerSingleDeliveryOrder) {
        return null;
    }
}
