package com.b2c.prototype.manager.payment;

import com.b2c.prototype.modal.dto.payload.commission.MinMaxCommissionDto;

import java.util.List;

public interface ICommissionManager {
    void saveCommission(MinMaxCommissionDto minMaxCommissionDto);
    void updateCommission(String tenantId, String key, MinMaxCommissionDto minMaxCommissionDto);
    void deleteCommission(String tenantId, String key);

    List<MinMaxCommissionDto> getCommissionList(String regionCode);
    MinMaxCommissionDto getCommission(String tenantId, String key);
}
