package com.b2c.prototype.modal.dto.response;

import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.dto.payload.review.ResponseReviewCommentDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseReviewDto {
    private String title;
    private String message;
    private int ratingValue;
    private Date dateOfCreate;
    private ConstantPayloadDto status;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private List<ResponseReviewCommentDto> comments;
}
