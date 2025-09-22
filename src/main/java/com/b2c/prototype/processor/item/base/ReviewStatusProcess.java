package com.b2c.prototype.processor.item.base;

import com.b2c.prototype.manager.message.IReviewStatusManager;
import com.b2c.prototype.modal.dto.payload.constant.ReviewStatusDto;
import com.b2c.prototype.modal.entity.review.ReviewStatus;
import com.b2c.prototype.processor.item.IReviewStatusProcess;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ReviewStatusProcess implements IReviewStatusProcess {
    private final ObjectMapper objectMapper;
    private final IReviewStatusManager reviewStatusManager;

    public ReviewStatusProcess(ObjectMapper objectMapper,
                               IReviewStatusManager reviewStatusManager) {
        this.objectMapper = objectMapper;
        this.reviewStatusManager = reviewStatusManager;
    }

    @Override
    public void persistEntity(Map<String, Object> payload) {
        ReviewStatus entity = objectMapper.convertValue(payload, ReviewStatus.class);
        reviewStatusManager.persistEntity(entity);
    }

    @Override
    public void mergeEntity(Map<String, Object> payload, String value) {
        ReviewStatus entity = objectMapper.convertValue(payload, ReviewStatus.class);
        reviewStatusManager.mergeEntity(value, entity);
    }

    @Override
    public void removeEntity(String value) {
        reviewStatusManager.removeEntity(value);
    }

    @Override
    public List<ReviewStatusDto> getEntityList(String location) {
        return reviewStatusManager.getEntities().stream()
                .map(entity -> objectMapper.convertValue(entity, ReviewStatusDto.class))
                .toList();
    }

    @Override
    public ReviewStatusDto getEntity(String location, String value) {
        return Optional.of(reviewStatusManager.getEntity(value))
                .map(entity -> objectMapper.convertValue(entity, ReviewStatusDto.class))
                .orElseThrow(() -> new RuntimeException(""));
    }
}
