package com.b2c.prototype.processor.commission;

import com.b2c.prototype.manager.payment.ICommissionManager;
import com.b2c.prototype.modal.dto.payload.commission.MinMaxCommissionDto;
import com.b2c.prototype.modal.dto.payload.commission.ResponseBuyerCommissionInfoDto;
import com.b2c.prototype.modal.dto.payload.commission.ResponseMinMaxCommissionDto;
import com.b2c.prototype.modal.dto.payload.order.ArticularItemQuantityDto;

import java.util.List;
import java.util.Map;

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
    public void updateCommission(MinMaxCommissionDto minMaxCommissionDto) {
        commissionManager.updateCommission(minMaxCommissionDto);
    }

    @Override
    public void deleteCommission(Map<String, String> requestParams) {
        String commissionType = requestParams.get("commissionType");
        commissionManager.deleteCommissionByTime(commissionType);
    }

    @Override
    public List<ResponseMinMaxCommissionDto> getCommissions(Map<String, String> requestParams) {
        return commissionManager.getCommissionList();
    }

    @Override
    public ResponseMinMaxCommissionDto getCommission(Map<String, String> requestParams) {
        String commissionType = requestParams.get("commissionType");
        return commissionManager.getCommissionByCommissionType(commissionType);
    }

    @Override
    public ResponseBuyerCommissionInfoDto getBuyerCommission(Map<String, String> requestParams, List<ArticularItemQuantityDto> articularItemQuantityDtoList) {
        return commissionManager.getBuyerCommission(articularItemQuantityDtoList);
    }
}
