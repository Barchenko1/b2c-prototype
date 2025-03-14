package com.b2c.prototype.processor.commission;

import com.b2c.prototype.manager.payment.ISellerCommissionManager;
import com.b2c.prototype.modal.dto.payload.CommissionDto;
import com.b2c.prototype.modal.dto.response.ResponseCommissionDto;

import java.util.List;
import java.util.Map;

public class SellerCommissionProcess implements ISellerCommissionProcess {
    private final ISellerCommissionManager sellerCommissionManager;

    public SellerCommissionProcess(ISellerCommissionManager sellerCommissionManager) {
        this.sellerCommissionManager = sellerCommissionManager;
    }

    @Override
    public void saveLastCommission(Map<String, String> requestParams, CommissionDto commissionDto) {
        sellerCommissionManager.saveLastCommission(commissionDto);
    }

    @Override
    public void deleteCommission(Map<String, String> requestParams) {
        String effectiveDate = requestParams.get("effectiveDate");
        sellerCommissionManager.deleteCommissionByTime(effectiveDate);
    }

    @Override
    public List<ResponseCommissionDto> getCommissions(Map<String, String> requestParams) {
        return sellerCommissionManager.getCommissionList();
    }

    @Override
    public ResponseCommissionDto getCommission(Map<String, String> requestParams) {
        return sellerCommissionManager.getLastCommission();
    }
}
