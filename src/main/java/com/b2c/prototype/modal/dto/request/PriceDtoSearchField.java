package com.b2c.prototype.modal.dto.request;

import com.b2c.prototype.modal.dto.common.AbstractSearchFieldEntityDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class PriceDtoSearchField extends AbstractSearchFieldEntityDto<PriceDto> {
}
