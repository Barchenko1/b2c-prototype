package com.b2c.prototype.modal.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResponseMessageOverviewDto {
    private String sender;
    private List<String> receivers;
    private String title;
    private String u_id;
    private long dateOfSend;
    private String sendSystem;
    private String subscribe;
    private String type;
    private String status;
}
