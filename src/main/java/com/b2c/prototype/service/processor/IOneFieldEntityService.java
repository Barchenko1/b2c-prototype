package com.b2c.prototype.service.processor;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDtoUpdate;
import com.b2c.prototype.modal.dto.response.ResponseOneFieldEntityDto;

import java.util.List;
import java.util.Optional;

public interface IOneFieldEntityService<E> {
    void saveEntity(OneFieldEntityDto oneFieldEntityDto);
    void updateEntity(OneFieldEntityDtoUpdate oneFieldEntityDtoUpdate);
    void deleteEntity(OneFieldEntityDto oneFieldEntityDto);
    ResponseOneFieldEntityDto getEntity(OneFieldEntityDto oneFieldEntityDto);
    Optional<ResponseOneFieldEntityDto> getEntityOptional(OneFieldEntityDto oneFieldEntityDto);
    List<ResponseOneFieldEntityDto> getEntities();

}
