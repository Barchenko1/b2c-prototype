package com.b2c.prototype.modal.dto.payload.post;

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
public class ResponsePostDto {
    private String postId;
    private String authorName;
    private String authorEmail;
    private long dateOfCreate;
    private String title;
    private String message;
    private List<ResponsePostDto> childList;
}
