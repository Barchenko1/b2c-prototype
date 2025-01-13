package com.b2c.prototype.service.processor;

import com.b2c.prototype.modal.dto.common.ConstantNumberEntityPayloadDto;

import java.util.List;
import java.util.Optional;

public interface IIntegerConstantEntityService {
    void saveEntity(ConstantNumberEntityPayloadDto constantNumberEntityPayloadDto);
    void updateEntity(Integer searchValue, ConstantNumberEntityPayloadDto constantNumberEntityPayloadDto);
    void deleteEntity(int ratingValue);
    ConstantNumberEntityPayloadDto getEntity(int ratingValue);
    Optional<ConstantNumberEntityPayloadDto> getEntityOptional(int ratingValue);
    List<ConstantNumberEntityPayloadDto> getEntities();

}
