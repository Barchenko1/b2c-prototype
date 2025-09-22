package com.b2c.prototype.processor.item;

import com.b2c.prototype.modal.dto.payload.constant.ReviewStatusDto;

import java.util.List;
import java.util.Map;

public interface IReviewStatusProcess {
    void persistEntity(final Map<String, Object> payload);
    void mergeEntity(final Map<String, Object> payload, final String value);
    void removeEntity(final String value);

    List<ReviewStatusDto> getEntityList(final String location);
    ReviewStatusDto getEntity(final String location, final String value);
}
