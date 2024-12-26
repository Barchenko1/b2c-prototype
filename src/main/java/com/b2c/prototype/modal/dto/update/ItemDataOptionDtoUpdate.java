package com.b2c.prototype.modal.dto.update;

import com.b2c.prototype.modal.dto.common.AbstractSearchFieldEntityDto;
import com.b2c.prototype.modal.dto.request.ItemDataOptionDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class ItemDataOptionDtoUpdate extends AbstractSearchFieldEntityDto<ItemDataOptionDto> {
}
