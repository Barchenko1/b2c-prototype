package com.b2c.prototype.modal.dto.request;

import lombok.Data;

@Data
public class PostDto {
    private String authorUserName;
    private String authorEmail;
    private String title;
    private String message;
    private String uniquePostId;

    private PostDto parent;
}
