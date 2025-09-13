package com.b2c.prototype.dao;

import com.nimbusds.jose.util.Pair;

import java.util.List;
import java.util.Optional;

public interface IEntityDao {
    <E> E persistEntity(E entity);
    <E> E mergeEntity(E entity);
    <E> void removeEntity(E entity);

    <E> E findEntity(String namedQuery, List<Pair<String, ?>> pairs);
    <E> Optional<E> findOptionEntity(String namedQuery, List<Pair<String, ?>> pairs);
    <E> List<E> findEntityList(String namedQuery, List<Pair<String, ?>> pairs);
}
