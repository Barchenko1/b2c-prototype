package com.b2c.prototype.modal.dto.searchfield;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class BeneficiarySearchFieldOrderNumberDto extends OneFieldEntityDto {
    private int orderNumber;
}
