package com.b2c.prototype.service.processor.review;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.response.ResponseReviewDto;
import com.b2c.prototype.modal.dto.searchfield.ReviewSearchFieldEntityDto;

import java.util.List;

public interface IReviewService {
    void saveUpdateReview(ReviewSearchFieldEntityDto reviewSearchFieldEntityDto);
    void deleteReview(OneFieldEntityDto oneFieldEntityDto);

    List<ResponseReviewDto> getReviewListByItemId(OneFieldEntityDto oneFieldEntityDto);

}
