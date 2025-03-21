package com.b2c.prototype.modal.dto.payload.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceDto {
    private String userAgent;
    private int screenWidth;
    private int screenHeight;
    private String timezone;
    private String language;
    private String platform;
}
