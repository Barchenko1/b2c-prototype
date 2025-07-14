package com.b2c.prototype.manager;

import com.b2c.prototype.modal.dto.common.NumberConstantPayloadDto;

import java.util.List;
import java.util.Optional;

public interface IIntegerConstantEntityManager<T> {
    void saveEntity(T numberConstantPayloadDto);
    void updateEntity(Integer searchValue, T numberConstantPayloadDto);
    void deleteEntity(int ratingValue);
    NumberConstantPayloadDto getEntity(int ratingValue);
    Optional<NumberConstantPayloadDto> getEntityOptional(int ratingValue);
    List<NumberConstantPayloadDto> getEntities();

}
