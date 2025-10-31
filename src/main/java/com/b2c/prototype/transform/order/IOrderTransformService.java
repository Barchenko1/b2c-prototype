package com.b2c.prototype.transform.order;

import com.b2c.prototype.modal.dto.payload.option.group.TimeDurationOptionGroupDto;
import com.b2c.prototype.modal.dto.payload.option.group.ZoneOptionGroupDto;
import com.b2c.prototype.modal.dto.payload.order.single.ResponseCustomerOrderDetails;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.ZoneOptionGroup;
import com.b2c.prototype.modal.entity.order.CustomerSingleDeliveryOrder;

public interface IOrderTransformService {
    OptionGroup mapTimeDurationOptionGroupDtoToOptionGroup(TimeDurationOptionGroupDto timeDurationOptionGroupDto);
    TimeDurationOptionGroupDto mapOptionGroupToTimeDurationOptionGroupDto(OptionGroup optionGroup);

    ZoneOptionGroup mapZoneOptionGroupDtoToZoneOptionGroup(ZoneOptionGroupDto zoneOptionGroupDto);
    ZoneOptionGroupDto mapZoneOptionGroupToZoneOptionGroupDto(ZoneOptionGroup zoneOptionGroup);

    ResponseCustomerOrderDetails mapCustomerSingleDeliveryOrderToResponseCustomerOrderDetails(CustomerSingleDeliveryOrder customerSingleDeliveryOrder);
}
