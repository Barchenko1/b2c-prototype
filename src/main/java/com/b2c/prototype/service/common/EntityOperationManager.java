package com.b2c.prototype.service.common;

import com.tm.core.process.dao.common.IEntityDao;
import com.tm.core.finder.parameter.Parameter;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class EntityOperationManager implements IEntityOperationManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(EntityOperationManager.class);

    private final IEntityDao dao;

    public EntityOperationManager(IEntityDao dao) {
        this.dao = dao;
    }

    @Override
    public <E> void saveEntity(Supplier<E> entitySupplier) {
        LOGGER.info("Saving entity");
        dao.saveEntity(entitySupplier);
    }

    @Override
    public <E> void updateEntity(Supplier<E> entitySupplier) {
        LOGGER.info("Updating entity");
        dao.updateEntity(entitySupplier);
    }

    @Override
    public <E> void deleteEntity(Supplier<E> entitySupplier) {
        LOGGER.info("Deleting entity");
        dao.deleteEntity(entitySupplier);
    }

    @Override
    public void executeConsumer(Consumer<Session> consumer) {
        LOGGER.info("Saving entity");
        dao.executeConsumer(consumer);
    }

    @Override
    public void deleteEntityByParameter(Supplier<Parameter> parameterSupplier) {
        StringBuilder sb = new StringBuilder();
        Parameter parameter = parameterSupplier.get();
        sb.append("Deleting entity: ");
        sb.append(parameter.getName()).append(", ");
        sb.append(parameter.getValue());
        LOGGER.info("Deleting entity: {}", sb);
        dao.findEntityAndDelete(parameter);
    }

    @Override
    public <E> void saveEntity(E entity) {
        LOGGER.info("Saving entity");
        dao.persistEntity(entity);
    }

    @Override
    public <E> void updateEntity(E entity) {
        LOGGER.info("Updating entity");
        dao.mergeEntity(entity);
    }

    @Override
    public <E> void deleteEntity(E entity) {
        LOGGER.info("Deleting entity");
        dao.deleteEntity(entity);
    }

    @Override
    public <E> E getEntity(Supplier<Parameter> parameterSupplier) {
        LOGGER.info("Getting entity");
        return dao.getEntity(parameterSupplier.get());
    }

    @Override
    public <E> Optional<E> getOptionalEntity(Supplier<Parameter> parameterSupplier) {
        LOGGER.info("Getting entity");
        return dao.getOptionalEntity(parameterSupplier.get());
    }

    @Override
    public <E> List<E> getEntityList() {
        LOGGER.info("Getting entity list");
        return dao.getEntityList();
    }

    @Override
    public <E> E getEntityGraph(String graph, Supplier<Parameter> parameterSupplier) {
        LOGGER.info("Getting entity");
        return dao.getEntityGraph(graph, parameterSupplier.get());
    }

    @Override
    public <E> Optional<E> getOptionalEntityGraph(String graph, Supplier<Parameter> parameterSupplier) {
        LOGGER.info("Getting entity");
        return dao.getOptionalEntityGraph(graph, parameterSupplier.get());
    }

    @Override
    public <E> List<E> getEntityGraphList(String graph) {
        LOGGER.info("Getting entity list");
        return dao.getEntityGraphList(graph);
    }

    @Override
    public <E> E getEntityNamedQuery(String namedQuery, Supplier<Parameter> parameterSupplier) {
        LOGGER.info("Getting entity");
        return dao.getEntityNamedQuery(namedQuery, parameterSupplier.get());
    }

    @Override
    public <E> Optional<E> getOptionalEntityNamedQuery(String namedQuery, Supplier<Parameter> parameterSupplier) {
        LOGGER.info("Getting entity");
        return dao.getOptionalEntityNamedQuery(namedQuery, parameterSupplier.get());
    }

    @Override
    public <E> List<E> getEntityNamedQueryList(String namedQuery) {
        LOGGER.info("Getting entity list");
        return dao.getEntityNamedQueryList(namedQuery);
    }

    @Override
    public <E, R> R getEntityGraphDto(String graph, Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction) {
        LOGGER.info("Getting entity dto");
        E entity = dao.getEntityGraph(graph, parameterSupplier.get());
        return Optional.ofNullable(entity).map(mapToDtoFunction).orElse(null);
    }

    @Override
    public <E, R> Optional<R> getOptionalEntityGraphDto(String graph, Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction) {
        LOGGER.info("Getting entity dto");
        E entity = dao.getEntityGraph(graph, parameterSupplier.get());
        return Optional.ofNullable(entity).map(mapToDtoFunction);
    }

    @Override
    public <E, R> List<R> getEntityGraphDtoList(String graph, Function<E, R> mapToDtoFunction) {
        LOGGER.info("Getting entity dto list");
        List<E> entityList = dao.getEntityList();
        return entityList.stream()
                .map(entity -> transformEntityToDto(entity, mapToDtoFunction))
                .toList();
    }

    @Override
    public <E, R> List<R> getSubEntityGraphDtoList(String graphNamedQuery, Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction) {
        LOGGER.info("Getting entity dto list");
        List<E> entityList = dao.getEntityList(parameterSupplier.get());
        return entityList.stream()
                .map(entity -> transformEntityToDto(entity, mapToDtoFunction))
                .toList();
    }

    private <E, R> R transformEntityToDto(E entity, Function<E, R> mapToDtoFunction) {
        LOGGER.info("Entity: {}", entity);
        R dto = mapToDtoFunction.apply(entity);
        LOGGER.info("Entity Dto: {}", dto);
        return dto;
    }
}
