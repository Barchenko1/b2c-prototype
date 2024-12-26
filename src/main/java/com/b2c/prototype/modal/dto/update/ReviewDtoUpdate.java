package com.b2c.prototype.modal.dto.update;

import com.b2c.prototype.modal.dto.common.AbstractEntityDtoUpdate;
import com.b2c.prototype.modal.dto.request.ReviewDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class ReviewDtoUpdate extends AbstractEntityDtoUpdate<ReviewDto> {
}
