package com.b2c.prototype.manager.payment;

import com.b2c.prototype.modal.dto.payload.commission.MinMaxCommissionDto;

import java.util.List;

public interface ICommissionManager {
    void saveCommission(MinMaxCommissionDto minMaxCommissionDto);
    void updateCommission(String region, String key, MinMaxCommissionDto minMaxCommissionDto);
    void deleteCommission(String region, String key);

    List<MinMaxCommissionDto> getCommissionList(String regionCode);
    MinMaxCommissionDto getCommission(String region, String key);
}
