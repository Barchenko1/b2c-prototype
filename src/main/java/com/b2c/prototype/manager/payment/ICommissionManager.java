package com.b2c.prototype.manager.payment;

import com.b2c.prototype.modal.dto.payload.CommissionDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ICommissionManager {
    void putBuyerCommission(CommissionDto commissionDto);
    void deleteBuyerCommission(LocalDateTime effectiveDate);

    List<CommissionDto> getBuyerCommissions();
}
