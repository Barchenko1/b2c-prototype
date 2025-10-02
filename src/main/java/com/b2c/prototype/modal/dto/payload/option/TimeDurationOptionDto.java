package com.b2c.prototype.modal.dto.payload.option;

import com.b2c.prototype.modal.dto.payload.item.PriceDto;
import com.b2c.prototype.util.DateTimeDeserializer;
import com.b2c.prototype.util.DateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TimeDurationOptionDto {
    private String label;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private LocalDateTime startTime;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private LocalDateTime endTime;
    private String clientTimezone;
    private PriceDto price;
}
