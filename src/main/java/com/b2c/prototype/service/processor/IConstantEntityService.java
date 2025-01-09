package com.b2c.prototype.service.processor;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.common.ConstantEntityPayloadSearchFieldDto;
import com.b2c.prototype.modal.dto.payload.ConstantEntityPayloadDto;

import java.util.List;
import java.util.Optional;

public interface IConstantEntityService {
    void saveEntity(ConstantEntityPayloadDto constantEntityPayloadDto);
    void updateEntity(ConstantEntityPayloadSearchFieldDto constantEntityPayloadSearchFieldDto);
    void deleteEntity(OneFieldEntityDto oneFieldEntityDto);
    ConstantEntityPayloadDto getEntity(OneFieldEntityDto oneFieldEntityDto);
    Optional<ConstantEntityPayloadDto> getEntityOptional(OneFieldEntityDto oneFieldEntityDto);
    List<ConstantEntityPayloadDto> getEntities();

}
