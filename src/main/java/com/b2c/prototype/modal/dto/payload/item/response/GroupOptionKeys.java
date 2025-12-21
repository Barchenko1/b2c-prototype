package com.b2c.prototype.modal.dto.payload.item.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupOptionKeys {
    private String groupKey;
    private List<String> optionKeys;
}
