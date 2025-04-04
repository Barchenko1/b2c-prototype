package com.b2c.prototype.processor.commission;

import com.b2c.prototype.modal.dto.payload.commission.CommissionDto;
import com.b2c.prototype.modal.dto.payload.order.ResponseCommissionDto;

import java.util.List;
import java.util.Map;

public interface ICommissionProcess {
    void saveLastCommission(Map<String, String> requestParams, CommissionDto commissionDto);
    void deleteCommission(Map<String, String> requestParams);

    List<ResponseCommissionDto> getCommissions(Map<String, String> requestParams);
    ResponseCommissionDto getCommission(Map<String, String> requestParams);
}
