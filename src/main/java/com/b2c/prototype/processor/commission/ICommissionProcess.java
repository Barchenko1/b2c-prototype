package com.b2c.prototype.processor.commission;

import com.b2c.prototype.modal.dto.payload.commission.MinMaxCommissionDto;

import java.util.List;
import java.util.Map;

public interface ICommissionProcess {
    void saveCommission(Map<String, String> requestParams, MinMaxCommissionDto minMaxCommissionDto);
    void updateCommission(Map<String, String> requestParams, MinMaxCommissionDto minMaxCommissionDto);
    void deleteCommission(Map<String, String> requestParams);

    List<MinMaxCommissionDto> getCommissions(Map<String, String> requestParams);
    MinMaxCommissionDto getCommission(Map<String, String> requestParams);
}
