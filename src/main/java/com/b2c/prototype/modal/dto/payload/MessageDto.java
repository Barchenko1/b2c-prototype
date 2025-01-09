package com.b2c.prototype.modal.dto.payload;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MessageDto {
    private String sender;
    private List<String> receivers;
    private String title;
    private String message;
    private String sendSystem;
    private String type;
}
