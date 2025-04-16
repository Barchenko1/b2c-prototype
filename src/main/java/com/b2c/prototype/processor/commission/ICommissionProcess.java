package com.b2c.prototype.processor.commission;

import com.b2c.prototype.modal.dto.payload.commission.MinMaxCommissionDto;
import com.b2c.prototype.modal.dto.payload.commission.ResponseBuyerCommissionInfoDto;
import com.b2c.prototype.modal.dto.payload.commission.ResponseMinMaxCommissionDto;
import com.b2c.prototype.modal.dto.payload.order.ArticularItemQuantityDto;

import java.util.List;
import java.util.Map;

public interface ICommissionProcess {
    void saveCommission(Map<String, String> requestParams, MinMaxCommissionDto minMaxCommissionDto);
    void updateCommission(MinMaxCommissionDto minMaxCommissionDto);
    void deleteCommission(Map<String, String> requestParams);

    List<ResponseMinMaxCommissionDto> getCommissions(Map<String, String> requestParams);
    ResponseMinMaxCommissionDto getCommission(Map<String, String> requestParams);

    ResponseBuyerCommissionInfoDto getBuyerCommission(Map<String, String> requestParams, List<ArticularItemQuantityDto> articularItemQuantityDtoList);
}
