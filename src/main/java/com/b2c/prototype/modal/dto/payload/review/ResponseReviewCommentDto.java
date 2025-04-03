package com.b2c.prototype.modal.dto.payload.review;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseReviewCommentDto {
    private String commentId;
    private String authorName;
    private String authorLastName;
    private String authorEmail;
    private long dateOfCreate;
    private String title;
    private String message;
    private List<ResponseReviewCommentDto> childList;
}
