package com.b2c.prototype.processor.item.base;

import com.b2c.prototype.manager.rating.IRatingManager;
import com.b2c.prototype.modal.dto.payload.constant.RatingDto;
import com.b2c.prototype.modal.entity.review.Rating;
import com.b2c.prototype.processor.item.IRatingProcess;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RatingProcess implements IRatingProcess {
    private final ObjectMapper objectMapper;
    private final IRatingManager ratingManager;

    public RatingProcess(ObjectMapper objectMapper, IRatingManager ratingManager) {
        this.objectMapper = objectMapper;
        this.ratingManager = ratingManager;
    }

    @Override
    public void persistEntity(Map<String, Object> payload) {
        Rating entity = objectMapper.convertValue(payload, Rating.class);
        ratingManager.persistEntity(entity);
    }

    @Override
    public void mergeEntity(Map<String, Object> payload, String value) {
        Rating entity = objectMapper.convertValue(payload, Rating.class);
        ratingManager.mergeEntity(getIntValue(value), entity);
    }

    @Override
    public void removeEntity(String value) {
        ratingManager.removeEntity(getIntValue(value));
    }

    @Override
    public List<RatingDto> getEntityList(String location) {
        return ratingManager.getEntities().stream()
                .map(entity -> objectMapper.convertValue(entity, RatingDto.class))
                .toList();
    }

    @Override
    public RatingDto getEntity(String location, String value) {
        return Optional.of(ratingManager.getEntity(getIntValue(value)))
                .map(entity -> objectMapper.convertValue(entity, RatingDto.class))
                .orElseThrow(() -> new RuntimeException(""));
    }

    private Integer getIntValue(String value) {
        return Integer.valueOf(value);
    }
}
