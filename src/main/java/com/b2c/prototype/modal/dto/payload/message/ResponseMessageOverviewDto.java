package com.b2c.prototype.modal.dto.payload.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessageOverviewDto {
    private String sender;
    private String title;
    private long dateOfSend;
    private String sendSystem;
    private String subscribe;
    private String type;
    private String status;
}
