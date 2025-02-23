package com.b2c.prototype.modal.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchFieldUpdateCollectionEntityDto<T> {
    private String searchField;
    private List<T> updateDtoSet;
}
