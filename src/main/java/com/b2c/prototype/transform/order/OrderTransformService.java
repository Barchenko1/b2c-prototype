package com.b2c.prototype.transform.order;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.payload.constant.CountryDto;
import com.b2c.prototype.modal.dto.payload.constant.CurrencyDto;
import com.b2c.prototype.modal.dto.payload.item.PriceDto;
import com.b2c.prototype.modal.dto.payload.option.group.OptionItemCostGroupDto;
import com.b2c.prototype.modal.dto.payload.option.group.OptionItemGroupDto;
import com.b2c.prototype.modal.dto.payload.option.group.TimeDurationOptionGroupDto;
import com.b2c.prototype.modal.dto.payload.option.group.ZoneOptionGroupDto;
import com.b2c.prototype.modal.dto.payload.option.item.OptionItemCostDto;
import com.b2c.prototype.modal.dto.payload.option.item.OptionItemDto;
import com.b2c.prototype.modal.dto.payload.option.item.TimeDurationOptionDto;
import com.b2c.prototype.modal.dto.payload.option.item.ZoneOptionDto;
import com.b2c.prototype.modal.dto.payload.order.single.ResponseCustomerOrderDetails;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItemCost;
import com.b2c.prototype.modal.entity.option.TimeDurationOption;
import com.b2c.prototype.modal.entity.option.ZoneOption;
import com.b2c.prototype.modal.entity.option.ZoneOptionGroup;
import com.b2c.prototype.modal.entity.order.CustomerSingleDeliveryOrder;
import com.b2c.prototype.modal.entity.price.Price;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static com.b2c.prototype.util.Constant.VALUE;

@Service
public class OrderTransformService implements IOrderTransformService {

    private final IGeneralEntityDao generalEntityDao;

    public OrderTransformService(IGeneralEntityDao generalEntityDao) {
        this.generalEntityDao = generalEntityDao;
    }

    @Override
    public OptionGroup mapTimeDurationOptionGroupDtoToOptionGroup(TimeDurationOptionGroupDto timeDurationOptionGroupDto) {
        OptionGroup optionGroup =  OptionGroup.builder()
                .label(timeDurationOptionGroupDto.getLabel())
                .value(timeDurationOptionGroupDto.getValue())
                .timeDurationOptions(timeDurationOptionGroupDto.getTimeDurationOptions().stream()
                        .map(timeDurationOptionDto -> TimeDurationOption.builder()
                                .label(timeDurationOptionDto.getLabel())
                                .value(timeDurationOptionDto.getValue())
                                .startTime(timeDurationOptionDto.getStartTime())
                                .endTime(timeDurationOptionDto.getEndTime())
                                .timeZone(timeDurationOptionDto.getTimeZone())
                                .durationInMin(Duration.between(
                                                timeDurationOptionDto.getStartTime(),
                                                timeDurationOptionDto.getEndTime())
                                        .toMinutes())
                                .price(Price.builder()
                                        .amount(timeDurationOptionDto.getPrice().getAmount())
                                        .currency(generalEntityDao.findEntity("Currency.findByValue",
                                                Pair.of(VALUE, timeDurationOptionDto.getPrice().getCurrency().getValue())))
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
                .label(optionGroup.getLabel())
                .value(optionGroup.getValue())
                .timeDurationOptions(optionGroup.getTimeDurationOptions().stream()
                        .map(timeDurationOption -> TimeDurationOptionDto.builder()
                                .searchValue(timeDurationOption.getValue())
                                .label(timeDurationOption.getLabel())
                                .value(timeDurationOption.getValue())
                                .startTime(timeDurationOption.getStartTime())
                                .endTime(timeDurationOption.getEndTime())
                                .timeZone(timeDurationOption.getTimeZone())
                                .price(PriceDto.builder()
                                        .amount(timeDurationOption.getPrice().getAmount())
                                        .currency(CurrencyDto.builder()
                                                .label(timeDurationOption.getPrice().getCurrency().getLabel())
                                                .value(timeDurationOption.getPrice().getCurrency().getValue())
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
                .label(zoneOptionGroupDto.getLabel())
                .value(zoneOptionGroupDto.getValue())
                .city(zoneOptionGroupDto.getCity())
                .country(generalEntityDao.findEntity("Country.findByValue",
                        Pair.of(VALUE, zoneOptionGroupDto.getCountry().getValue())))
                .zoneOptions(zoneOptionGroupDto.getZoneOptions().stream()
                        .map(zoneOptionDto -> ZoneOption.builder()
                                .label(zoneOptionDto.getLabel())
                                .value(zoneOptionDto.getValue())
                                .price(Price.builder()
                                        .amount(zoneOptionDto.getPrice().getAmount())
                                        .currency(generalEntityDao.findEntity("Currency.findByValue",
                                                Pair.of(VALUE, zoneOptionDto.getPrice().getCurrency().getValue())))
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
                .label(zoneOptionGroup.getLabel())
                .value(zoneOptionGroup.getValue())
                .country(CountryDto.builder()
                        .label(zoneOptionGroup.getCountry().getLabel())
                        .value(zoneOptionGroup.getCountry().getValue())
                        .build())
                .city(zoneOptionGroup.getCity())
                .zoneOptions(zoneOptionGroup.getZoneOptions().stream()
                        .map(zoneOption -> ZoneOptionDto.builder()
                                .searchValue(zoneOption.getValue())
                                .label(zoneOption.getLabel())
                                .value(zoneOption.getValue())
                                .price(PriceDto.builder()
                                        .amount(zoneOption.getPrice().getAmount())
                                        .currency(CurrencyDto.builder()
                                                .label(zoneOption.getPrice().getCurrency().getLabel())
                                                .value(zoneOption.getPrice().getCurrency().getValue())
                                                .build())
                                        .build())
                                .build())
                        .collect(Collectors.toList())
                )
                .build();
    }

    @Override
    public ResponseCustomerOrderDetails mapCustomerSingleDeliveryOrderToResponseCustomerOrderDetails(CustomerSingleDeliveryOrder customerSingleDeliveryOrder) {
        return null;
    }
}
