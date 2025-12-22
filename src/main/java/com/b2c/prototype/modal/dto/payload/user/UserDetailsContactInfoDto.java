package com.b2c.prototype.modal.dto.payload.user;

import com.b2c.prototype.modal.dto.payload.order.ContactInfoDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsContactInfoDto {
    private String userId;
    private String username;
    @JsonProperty("isActive")
    private boolean isActive;
    private ContactInfoDto contactInfo;
}
