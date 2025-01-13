package com.b2c.prototype.service.processor;

import com.b2c.prototype.modal.dto.payload.ConstantEntityPayloadDto;

import java.util.List;
import java.util.Optional;

public interface IConstantEntityService {
    void saveEntity(ConstantEntityPayloadDto constantEntityPayloadDto);
    void updateEntity(String searchValue, ConstantEntityPayloadDto constantEntityPayloadDto);
    void deleteEntity(String value);
    ConstantEntityPayloadDto getEntity(String value);
    Optional<ConstantEntityPayloadDto> getEntityOptional(String value);
    List<ConstantEntityPayloadDto> getEntities();

}
