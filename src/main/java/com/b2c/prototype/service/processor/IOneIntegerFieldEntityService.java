package com.b2c.prototype.service.processor;

import com.b2c.prototype.modal.dto.common.OneIntegerFieldEntityDto;
import com.b2c.prototype.modal.dto.common.OneIntegerFieldEntityDtoUpdate;

import java.util.List;
import java.util.Optional;

public interface IOneIntegerFieldEntityService<E> {
    void saveEntity(OneIntegerFieldEntityDto oneIntegerFieldEntityDto);
    void updateEntity(OneIntegerFieldEntityDtoUpdate oneIntegerFieldEntityDtoUpdate);
    void deleteEntity(OneIntegerFieldEntityDto oneIntegerFieldEntityDto);
    E getEntity(OneIntegerFieldEntityDto oneIntegerFieldEntityDto);
    Optional<E> getEntityOptional(OneIntegerFieldEntityDto oneIntegerFieldEntityDto);
    List<E> getEntities();

}
