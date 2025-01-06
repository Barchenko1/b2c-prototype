package com.b2c.prototype.modal.dto.searchfield;

import com.b2c.prototype.modal.dto.common.AbstractSearchFieldEntityDto;
import com.b2c.prototype.modal.dto.request.CreditCardDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class CreditCardSearchFieldEntityDto extends AbstractSearchFieldEntityDto<CreditCardDto> {
}
