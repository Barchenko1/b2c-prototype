package com.b2c.prototype.manager.payment;

import com.b2c.prototype.modal.dto.payload.CommissionDto;
import com.b2c.prototype.modal.dto.response.ResponseCommissionDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ICommissionManager {
    void saveLastCommission(CommissionDto commissionDto);
    void deleteCommissionByTime(String effectiveDate);

    List<ResponseCommissionDto> getCommissionList();
    ResponseCommissionDto getLastCommission();
}
