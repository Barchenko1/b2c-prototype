package com.b2c.prototype.modal.dto.request;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class ContactInfoSearchFieldOrderNumberDto extends OneFieldEntityDto {
    private int orderNumber;
}
