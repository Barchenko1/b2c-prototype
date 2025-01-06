package com.b2c.prototype.modal.dto.searchfield;

import com.b2c.prototype.modal.dto.common.AbstractSearchFieldEntityDto;
import com.b2c.prototype.modal.dto.request.ItemDataDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class ItemDataSearchFieldEntityDto extends AbstractSearchFieldEntityDto<ItemDataDto> {
}
