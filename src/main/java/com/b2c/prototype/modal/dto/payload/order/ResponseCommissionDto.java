package com.b2c.prototype.modal.dto.payload.order;

import com.b2c.prototype.util.ZonedDateTimeDeserializer;
import com.b2c.prototype.util.ZonedDateTimeSerializer;
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
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseCommissionDto {
    private double amount;
    private String feeType;
    private String currency;
    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    @JsonDeserialize(using = ZonedDateTimeDeserializer.class)
    private ZonedDateTime effectiveDate;
}
