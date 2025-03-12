package com.b2c.prototype.processor.commission;

import com.b2c.prototype.manager.payment.IBuyerCommissionManager;
import com.b2c.prototype.modal.dto.payload.CommissionDto;
import com.b2c.prototype.modal.dto.response.ResponseCommissionDto;
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
    public void saveLastCommission(Map<String, String> requestParams, CommissionDto commissionDto) {
        buyerCommissionManager.saveLastCommission(commissionDto);
    }

    @Override
    public void deleteCommission(Map<String, String> requestParams) {
        String effectiveDate = requestParams.get("effectiveDate");
        buyerCommissionManager.deleteCommissionByTime(effectiveDate);
    }

    @Override
    public List<ResponseCommissionDto> getCommissions(Map<String, String> requestParams) {
        return buyerCommissionManager.getCommissionList();
    }

    @Override
    public ResponseCommissionDto getCommission(Map<String, String> requestParams) {
        return buyerCommissionManager.getLastCommission();
    }
}
