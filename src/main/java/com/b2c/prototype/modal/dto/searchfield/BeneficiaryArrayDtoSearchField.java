package com.b2c.prototype.modal.dto.searchfield;

import com.b2c.prototype.modal.dto.common.AbstractSearchFieldEntityArrayDto;
import com.b2c.prototype.modal.dto.request.BeneficiaryDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class BeneficiaryArrayDtoSearchField extends AbstractSearchFieldEntityArrayDto<BeneficiaryDto> {
}
