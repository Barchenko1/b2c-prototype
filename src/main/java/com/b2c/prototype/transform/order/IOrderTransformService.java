package com.b2c.prototype.transform.order;

import com.b2c.prototype.modal.dto.payload.option.TimeDurationOptionDto;
import com.b2c.prototype.modal.dto.payload.option.ZoneOptionDto;
import com.b2c.prototype.modal.dto.payload.order.single.ResponseCustomerOrderDetails;
import com.b2c.prototype.modal.entity.option.TimeDurationOption;
import com.b2c.prototype.modal.entity.option.ZoneOption;
import com.b2c.prototype.modal.entity.order.CustomerSingleDeliveryOrder;

public interface IOrderTransformService {
    ZoneOptionDto mapZoneOptionToZoneOptionDto(ZoneOption zoneOption);
    ZoneOption mapZoneOptionDtoToZoneOption(ZoneOptionDto zoneOptionDto);


    TimeDurationOption mapTimeDurationOptionDtoToTimeDurationOption(TimeDurationOptionDto timeDurationOptionDto);
    TimeDurationOptionDto mapTimeDurationOptionToTimeDurationOptionDto(TimeDurationOption timeDurationOption);

    ResponseCustomerOrderDetails mapCustomerSingleDeliveryOrderToResponseCustomerOrderDetails(CustomerSingleDeliveryOrder customerSingleDeliveryOrder);
}
