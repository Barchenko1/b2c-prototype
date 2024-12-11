package com.b2c.prototype.modal.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ResponseReviewDto {
    private String title;
    private String message;
    private int ratingValue;
    private Date dateOfCreate;
}
