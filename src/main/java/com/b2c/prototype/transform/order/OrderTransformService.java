package com.b2c.prototype.transform.order;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.payload.constant.CountryDto;
import com.b2c.prototype.modal.dto.payload.item.PriceDto;
import com.b2c.prototype.modal.dto.payload.option.TimeDurationOptionDto;
import com.b2c.prototype.modal.dto.payload.option.ZoneOptionDto;
import com.b2c.prototype.modal.dto.payload.order.single.ResponseCustomerOrderDetails;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.modal.entity.option.TimeDurationOption;
import com.b2c.prototype.modal.entity.option.ZoneOption;
import com.b2c.prototype.modal.entity.order.CustomerSingleDeliveryOrder;
import com.b2c.prototype.modal.entity.price.Price;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;

import java.time.Duration;

import static com.b2c.prototype.util.Constant.VALUE;

@Service
public class OrderTransformService implements IOrderTransformService {

    private final IGeneralEntityDao generalEntityDao;

    public OrderTransformService(IGeneralEntityDao generalEntityDao) {
        this.generalEntityDao = generalEntityDao;
    }

    @Override
    public ZoneOptionDto mapZoneOptionToZoneOptionDto(ZoneOption zoneOption) {
        return ZoneOptionDto.builder()
                .label(zoneOption.getLabel())
                .value(zoneOption.getValue())
//                .city(zoneOption.getCity())
                .price(PriceDto.builder()
                        .amount(zoneOption.getPrice().getAmount())
                        .currency(zoneOption.getPrice().getCurrency().getLabel())
                        .build())
                .build();
    }

    @Override
    public ZoneOption mapZoneOptionDtoToZoneOption(ZoneOptionDto zoneOptionDto) {
        return ZoneOption.builder()
                .label(zoneOptionDto.getLabel())
                .value(zoneOptionDto.getValue())

//                .city(zoneOptionDto.getCity())
                .price(Price.builder()
                        .amount(zoneOptionDto.getPrice().getAmount())
                        .currency(generalEntityDao.findEntity("Currency.findByValue", Pair.of(VALUE, zoneOptionDto.getPrice().getCurrency())))
                        .build())
                .build();
    }

    @Override
    public TimeDurationOption mapTimeDurationOptionDtoToTimeDurationOption(TimeDurationOptionDto timeDurationOptionDto) {
        return TimeDurationOption.builder()
                .label(timeDurationOptionDto.getLabel())
                .value(timeDurationOptionDto.getValue())
                .timeZone(timeDurationOptionDto.getTimeZone())
                .startTime(timeDurationOptionDto.getStartTime())
                .endTime(timeDurationOptionDto.getEndTime())
                .durationInMin(Duration.between(
                        timeDurationOptionDto.getStartTime(),
                        timeDurationOptionDto.getEndTime())
                        .toMinutes())
                .price(Price.builder()
                        .amount(timeDurationOptionDto.getPrice().getAmount())
                        .currency(generalEntityDao.findEntity("Currency.findByValue", Pair.of(VALUE, timeDurationOptionDto.getPrice().getCurrency())))
                        .build())
                .build();
    }

    @Override
    public TimeDurationOptionDto mapTimeDurationOptionToTimeDurationOptionDto(TimeDurationOption timeDurationOption) {
        return TimeDurationOptionDto.builder()
                .label(timeDurationOption.getLabel())
                .value(timeDurationOption.getValue())
                .timeZone(timeDurationOption.getTimeZone())
                .startTime(timeDurationOption.getStartTime())
                .endTime(timeDurationOption.getEndTime())
                .price(PriceDto.builder()
                        .amount(timeDurationOption.getPrice().getAmount())
                        .currency(timeDurationOption.getPrice().getCurrency().getLabel())
                        .build())
                .build();
    }

    @Override
    public ResponseCustomerOrderDetails mapCustomerSingleDeliveryOrderToResponseCustomerOrderDetails(CustomerSingleDeliveryOrder customerSingleDeliveryOrder) {
        return null;
    }
}
