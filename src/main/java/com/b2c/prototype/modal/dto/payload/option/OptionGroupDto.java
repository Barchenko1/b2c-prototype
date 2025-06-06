package com.b2c.prototype.modal.dto.payload.option;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OptionGroupDto {
    private String label;
    private String value;
}
