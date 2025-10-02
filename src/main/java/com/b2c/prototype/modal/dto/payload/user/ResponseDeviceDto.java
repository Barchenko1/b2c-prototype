package com.b2c.prototype.modal.dto.payload.user;

import com.b2c.prototype.util.DateTimeDeserializer;
import com.b2c.prototype.util.DateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDeviceDto {
    private String ipAddress;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private ZonedDateTime loginTime;
    private String userAgent;
    private int screenWidth;
    private int screenHeight;
    private String timezone;
    private String language;
    private String platform;
    private boolean isThisDevice;
}
