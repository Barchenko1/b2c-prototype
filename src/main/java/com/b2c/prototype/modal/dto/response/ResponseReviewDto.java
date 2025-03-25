package com.b2c.prototype.modal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseReviewDto {
    private String title;
    private String message;
    private int ratingValue;
    private Date dateOfCreate;
}
