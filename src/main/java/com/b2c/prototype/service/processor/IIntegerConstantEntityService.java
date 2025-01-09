package com.b2c.prototype.service.processor;

import com.b2c.prototype.modal.dto.common.ConstantNumberEntityPayloadDto;
import com.b2c.prototype.modal.dto.common.ConstantNumberSearchEntityPayloadDtoUpdate;

import java.util.List;
import java.util.Optional;

public interface IIntegerConstantEntityService {
    void saveEntity(ConstantNumberEntityPayloadDto constantNumberEntityPayloadDto);
    void updateEntity(ConstantNumberSearchEntityPayloadDtoUpdate constantNumberSearchEntityPayloadDtoUpdate);
    void deleteEntity(ConstantNumberEntityPayloadDto constantNumberEntityPayloadDto);
    ConstantNumberEntityPayloadDto getEntity(ConstantNumberEntityPayloadDto constantNumberEntityPayloadDto);
    Optional<ConstantNumberEntityPayloadDto> getEntityOptional(ConstantNumberEntityPayloadDto constantNumberEntityPayloadDto);
    List<ConstantNumberEntityPayloadDto> getEntities();

}
