package com.b2c.prototype.processor.commission;

import com.b2c.prototype.manager.payment.ICommissionManager;
import com.b2c.prototype.modal.dto.payload.commission.MinMaxCommissionDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CommissionProcess implements ICommissionProcess {
    private final ICommissionManager commissionManager;

    public CommissionProcess(ICommissionManager commissionManager) {
        this.commissionManager = commissionManager;
    }

    @Override
    public void saveCommission(Map<String, String> requestParams, MinMaxCommissionDto minMaxCommissionDto) {
        commissionManager.saveCommission(minMaxCommissionDto);
    }

    @Override
    public void updateCommission(Map<String, String> requestParams, MinMaxCommissionDto minMaxCommissionDto) {
        String regionCode = requestParams.get("tenant");
        String key = requestParams.get("key");
        commissionManager.updateCommission(regionCode, key, minMaxCommissionDto);
    }

    @Override
    public void deleteCommission(Map<String, String> requestParams) {
        String regionCode = requestParams.get("tenant");
        String key = requestParams.get("key");
        commissionManager.deleteCommission(regionCode, key);
    }

    @Override
    public List<MinMaxCommissionDto> getCommissions(Map<String, String> requestParams) {
        String regionCode = requestParams.get("tenant");
        return commissionManager.getCommissionList(regionCode);
    }

    @Override
    public MinMaxCommissionDto getCommission(Map<String, String> requestParams) {
        String regionCode = requestParams.get("tenant");
        String key = requestParams.get("key");
        return commissionManager.getCommission(regionCode, key);
    }
}
