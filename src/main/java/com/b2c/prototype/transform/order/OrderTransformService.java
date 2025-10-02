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
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;

import javax.management.ValueExp;

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
                .country(CountryDto.builder()
                        .label(zoneOption.getCountry().getLabel())
                        .value(zoneOption.getCountry().getValue())
                        .build())
                .city(zoneOption.getCity())
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
                .country(generalEntityDao.findEntity("Country.findByValue", Pair.of(VALUE, zoneOptionDto.getCountry().getValue())))
                .city(zoneOptionDto.getCity())
                .price(Price.builder()
                        .amount(zoneOptionDto.getPrice().getAmount())
                        .currency(generalEntityDao.findEntity("Currency.findByValue", Pair.of(VALUE, zoneOptionDto.getPrice().getCurrency())))
                        .build())
                .build();
    }

    @Override
    public TimeDurationOption mapTimeDurationOptionDtoToTimeDurationOption(TimeDurationOptionDto timeDurationOptionDto) {
        return null;
    }

    @Override
    public TimeDurationOptionDto mapTimeDurationOptionToTimeDurationOptionDto(TimeDurationOption timeDurationOption) {
        return null;
    }

    @Override
    public ResponseCustomerOrderDetails mapCustomerSingleDeliveryOrderToResponseCustomerOrderDetails(CustomerSingleDeliveryOrder customerSingleDeliveryOrder) {
        return null;
    }
}
