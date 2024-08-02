package com.b2c.prototype.modal.client.dto.request;

import lombok.Data;

@Data
public class RequestPostDto {
    private String authorUserName;
    private String authorEmail;
    private String title;
    private String message;
    private String uniquePostId;

    private RequestPostDto parent;
}
