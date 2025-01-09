package com.b2c.prototype.modal.dto.update;

import com.b2c.prototype.modal.dto.common.AbstractEntityDtoUpdate;
import com.b2c.prototype.modal.dto.payload.PostDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class PostDtoUpdate extends AbstractEntityDtoUpdate<PostDto> {
}
