package com.b2c.prototype.modal.dto.payload.tenant;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LanguageDto {
    private String key;
    private String languageCode;
    private String name;
    @JsonProperty("isActive")
    private boolean isActive;
}
