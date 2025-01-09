package com.b2c.prototype.modal.dto.update;

import com.b2c.prototype.modal.dto.common.AbstractTwoFieldEntitySearchEntityDto;
import com.b2c.prototype.modal.dto.payload.MessageDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class MessageDtoUpdate extends AbstractTwoFieldEntitySearchEntityDto<MessageDto> {
}
