package com.b2c.prototype.modal.dto.update;

import com.b2c.prototype.modal.dto.common.AbstractSearchFieldEntityDtoUpdate;
import com.b2c.prototype.modal.dto.payload.CreditCardDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class CreditCardSearchFieldEntityUpdateDto extends AbstractSearchFieldEntityDtoUpdate<CreditCardDto> {
}
