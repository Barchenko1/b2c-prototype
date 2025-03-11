package com.b2c.prototype.processor.commission;

import com.b2c.prototype.modal.dto.payload.CommissionDto;
import com.b2c.prototype.modal.dto.response.ResponseDeviceDto;

import java.util.List;
import java.util.Map;

public interface ICommissionProcess {
    void putCommission(Map<String, String> requestParams, CommissionDto commissionDto);
    void deleteCommission(Map<String, String> requestParams);

    List<ResponseDeviceDto> getCommissions(Map<String, String> requestParams);
}
