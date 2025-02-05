package com.b2c.prototype.manager.order;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.searchfield.BeneficiaryArrayDtoSearchField;
import com.b2c.prototype.modal.dto.payload.BeneficiaryDto;
import com.b2c.prototype.modal.dto.searchfield.BeneficiarySearchFieldOrderNumberDto;

import java.util.List;

public interface IBeneficiaryManager {
    void saveUpdateContactInfoByOrderId(BeneficiaryArrayDtoSearchField beneficiaryArrayDtoSearchField);
    void deleteContactInfoByOrderId(BeneficiarySearchFieldOrderNumberDto beneficiarySearchFieldOrderNumberDto);

    List<BeneficiaryDto> getContactInfoListByOrderId(OneFieldEntityDto oneFieldEntityDto);
}
