package com.b2c.prototype.manager.payment;

import com.b2c.prototype.modal.dto.payload.commission.MinMaxCommissionDto;
import com.b2c.prototype.modal.dto.payload.commission.ResponseBuyerCommissionInfoDto;
import com.b2c.prototype.modal.dto.payload.commission.ResponseMinMaxCommissionDto;
import com.b2c.prototype.modal.dto.payload.order.ArticularItemQuantityDto;

import java.util.List;

public interface ICommissionManager {
    void saveCommission(MinMaxCommissionDto minMaxCommissionDto);
    void updateCommission(MinMaxCommissionDto minMaxCommissionDto);
    void deleteCommissionByTime(String effectiveDate);

    List<ResponseMinMaxCommissionDto> getCommissionList();
    ResponseMinMaxCommissionDto getCommissionByCommissionType(String commissionType);

    ResponseBuyerCommissionInfoDto getBuyerCommission(List<ArticularItemQuantityDto> articularItemQuantityDtoList)
}
