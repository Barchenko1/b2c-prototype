package com.b2c.prototype.service.processor;

import com.b2c.prototype.modal.dto.common.NumberConstantPayloadDto;

import java.util.List;
import java.util.Optional;

public interface IIntegerConstantEntityService {
    void saveEntity(NumberConstantPayloadDto numberConstantPayloadDto);
    void updateEntity(Integer searchValue, NumberConstantPayloadDto numberConstantPayloadDto);
    void deleteEntity(int ratingValue);
    NumberConstantPayloadDto getEntity(int ratingValue);
    Optional<NumberConstantPayloadDto> getEntityOptional(int ratingValue);
    List<NumberConstantPayloadDto> getEntities();

}
