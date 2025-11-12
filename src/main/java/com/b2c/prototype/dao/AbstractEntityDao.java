package com.b2c.prototype.dao;

import com.nimbusds.jose.util.Pair;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnitUtil;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public abstract class AbstractEntityDao implements IGeneralEntityDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public <E> E persistEntity(E entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    @Transactional
    public <E> E mergeEntity(E entity) {
        return entityManager.merge(entity);
    }

    @Override
    @Transactional
    public <E> void removeEntity(E entity) {
        E ref = getRef(entity);
        entityManager.remove(ref);
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public <E> void findAndRemoveEntity(String namedQuery, Pair<String, ?> pair) {
        Query query = getQuery(namedQuery, pair);
        E result = (E) query.getSingleResult();
        entityManager.remove(result);
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public <E> void findAndRemoveEntity(String namedQuery, List<Pair<String, ?>> pairs) {
        Query query = getQuery(namedQuery, pairs);
        E result = (E) query.getSingleResult();
        entityManager.remove(result);
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public <E> void findAndRemoveEntityList(String namedQuery, Pair<String, ?> pair) {
        Query query = getQuery(namedQuery, pair);
        List<E> resultList = query.getResultList();
        resultList.forEach(entity -> entityManager.remove(entity));
    }

    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public <E> E findEntity(String namedQuery, Pair<String, ?> pair) {
        Query query = getQuery(namedQuery, pair);
        E result = (E) query.getSingleResult();
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public <E> Optional<E> findOptionEntity(String namedQuery, Pair<String, ?> pair) {
        try {
            TypedQuery<E> q = (TypedQuery<E>) getQuery(namedQuery, pair);
            return Optional.ofNullable(q.getSingleResult());
        } catch (NoResultException | EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public <E> List<E> findEntityList(String namedQuery, Pair<String, ?> pair) {
        Query query = getQuery(namedQuery, pair);
        List<E> resultList = query.getResultList();
        return resultList;
    }

    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public <E> E findEntity(String namedQuery, List<Pair<String, ?>> pairs) {
        Query query = getQuery(namedQuery, pairs);
        E result = (E) query.getSingleResult();
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public <E> Optional<E> findOptionEntity(String namedQuery, List<Pair<String, ?>> pairs) {
        try {
            TypedQuery<E> q = (TypedQuery<E>) getQuery(namedQuery, pairs);
            return Optional.ofNullable(q.getSingleResult());
        } catch (NoResultException | EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public <E> List<E> findEntityList(String namedQuery, List<Pair<String, ?>> pairs) {
        Query query = getQuery(namedQuery, pairs);
        List<E> resultList = query.getResultList();
        return resultList;
    }

    private Query getQuery(String namedQuery, Pair<String, ?> pair) {
        Query query = entityManager.createNamedQuery(namedQuery);
        if (pair != null) {
            if (pair.getLeft() != null && pair.getRight() != null) {
                query.setParameter(pair.getLeft(), pair.getRight());
            } else {
                throw new IllegalArgumentException("Parameter '" + pair.getLeft() + "' is null or empty.");
            }
        }

        return query;
    }

    private Query getQuery(String namedQuery, List<Pair<String, ?>> pairs) {
        Query query = entityManager.createNamedQuery(namedQuery);
        if (pairs != null) {
            pairs.forEach((pair) -> {
                if (pair.getLeft() != null && pair.getRight() != null) {
                    query.setParameter(pair.getLeft(), pair.getRight());
                } else {
                    throw new IllegalArgumentException("Parameter '" + pair.getLeft() + "' is null or empty.");
                }
            });
        }

        return query;
    }

    private <E> E getRef(E entity) {
        PersistenceUnitUtil util = entityManager.getEntityManagerFactory().getPersistenceUnitUtil();
        Object id = util.getIdentifier(entity);
        if (id == null) {
            throw new IllegalArgumentException("Cannot delete transient entity (null id).");
        }
        @SuppressWarnings("unchecked")
        Class<E> type = (Class<E>) entity.getClass();
        return entityManager.getReference(type, id);
    }


}
