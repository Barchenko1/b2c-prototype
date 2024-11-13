package com.b2c.prototype.service.processor;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDtoUpdate;

import java.util.List;
import java.util.Optional;

public interface IOneFieldEntityService<E> {
    void saveEntity(OneFieldEntityDto oneFieldEntityDto);
    void updateEntity(OneFieldEntityDtoUpdate oneFieldEntityDtoUpdate);
    void deleteEntity(OneFieldEntityDto oneFieldEntityDto);
    E getEntity(OneFieldEntityDto oneFieldEntityDto);
    Optional<E> getEntityOptional(OneFieldEntityDto oneFieldEntityDto);
    List<E> getEntities();

}
