package com.b2c.prototype.processor.commission;

import com.b2c.prototype.manager.payment.IBuyerCommissionManager;
import com.b2c.prototype.modal.dto.payload.CommissionDto;
import com.b2c.prototype.modal.dto.response.ResponseDeviceDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class BuyerCommissionProcess implements IBuyerCommissionProcess {
    private final IBuyerCommissionManager buyerCommissionManager;

    public BuyerCommissionProcess(IBuyerCommissionManager buyerCommissionManager) {
        this.buyerCommissionManager = buyerCommissionManager;
    }

    @Override
    public void putCommission(Map<String, String> requestParams, CommissionDto commissionDto) {

    }

    @Override
    public void deleteCommission(Map<String, String> requestParams) {
        LocalDateTime effectiveDate = LocalDateTime.now();
        buyerCommissionManager.deleteBuyerCommission(effectiveDate);
    }

    @Override
    public List<ResponseDeviceDto> getCommissions(Map<String, String> requestParams) {
        return List.of();
    }
}
