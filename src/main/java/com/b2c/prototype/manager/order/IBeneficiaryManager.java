package com.b2c.prototype.manager.order;

import com.b2c.prototype.modal.dto.payload.BeneficiaryDto;

import java.util.List;

public interface IBeneficiaryManager {
    void saveUpdateContactInfoByOrderId(String orderId, List<BeneficiaryDto> beneficiaryDtoList);
    void deleteContactInfoByOrderId(String orderId, int beneficiaryNumber);

    List<BeneficiaryDto> getContactInfoListByOrderId(String orderId);
}
