package com.b2c.prototype.dao.cashed;

import java.util.List;
import java.util.Optional;

public interface IEntityIntegerMapWrapper<E> {

    E getEntity(Integer value);
    Optional<E> getOptionalEntity(Integer value);
    List<E> getEntityList(List<Integer> values);

}
