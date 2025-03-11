package com.b2c.prototype.processor.commission;

import com.b2c.prototype.manager.payment.ISellerCommissionManager;
import com.b2c.prototype.modal.dto.payload.CommissionDto;
import com.b2c.prototype.modal.dto.response.ResponseDeviceDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class SellerCommissionProcess implements ISellerCommissionProcess {
    private final ISellerCommissionManager sellerCommissionManager;

    public SellerCommissionProcess(ISellerCommissionManager sellerCommissionManager) {
        this.sellerCommissionManager = sellerCommissionManager;
    }

    @Override
    public void putCommission(Map<String, String> requestParams, CommissionDto commissionDto) {
        sellerCommissionManager.putBuyerCommission(commissionDto);
    }

    @Override
    public void deleteCommission(Map<String, String> requestParams) {
        LocalDateTime effectiveDate = LocalDateTime.now();
        sellerCommissionManager.deleteBuyerCommission(effectiveDate);
    }

    @Override
    public List<ResponseDeviceDto> getCommissions(Map<String, String> requestParams) {
        return List.of();
    }
}
