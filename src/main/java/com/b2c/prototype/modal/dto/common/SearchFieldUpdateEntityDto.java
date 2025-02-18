package com.b2c.prototype.modal.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchFieldUpdateEntityDto<T> {
    private String searchField;
    private T updateDto;
}
