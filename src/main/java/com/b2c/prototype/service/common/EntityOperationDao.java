package com.b2c.prototype.service.common;

import com.tm.core.dao.common.IEntityDao;
import com.tm.core.processor.finder.parameter.Parameter;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class EntityOperationDao implements IEntityOperationDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(EntityOperationDao.class);

    private final IEntityDao dao;

    public EntityOperationDao(IEntityDao dao) {
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
    public <E> void updateEntityByParameter(Supplier<E> entitySupplier, Supplier<Parameter> parameterSupplier) {
        dao.findEntityAndUpdate(entitySupplier.get(), parameterSupplier.get());
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
        return Optional.ofNullable(dao.getEntity(parameterSupplier.get()));
    }

    @Override
    public <E> List<E> getEntityList() {
        LOGGER.info("Getting entity list");
        return dao.getEntityList();
    }

    @Override
    public <E> List<E> getSubEntityList(Supplier<Parameter[]> parameterSupplier) {
        LOGGER.info("Getting entity list");
        Parameter[] parametersArray = parameterSupplier.get();
        return dao.getEntityList(parametersArray);
    }

    @Override
    public <R, E> R getEntityDto(Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction) {
        LOGGER.info("Getting entity dto");
        E entity = dao.getEntity(parameterSupplier.get());
        return Optional.ofNullable(entity)
                .map(mapToDtoFunction)
                .orElse(null);
    }

    @Override
    public <R, E> Optional<R> getOptionalEntityDto(Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction) {
        LOGGER.info("Getting entity dto");
        E entity = dao.getEntity(parameterSupplier.get());
        return Optional.ofNullable(entity).map(mapToDtoFunction);
    }

    @Override
    public <R, E> List<R> getEntityDtoList(Function<E, R> mapToDtoFunction) {
        LOGGER.info("Getting entity dto list");
        List<E> entityList = dao.getEntityList();
        return entityList.stream()
                .map(entity -> transformEntityToDto(entity, mapToDtoFunction))
                .toList();
    }

    @Override
    public <R, E> List<R> getSubEntityDtoList(Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction) {
        LOGGER.info("Getting entity dto list");
        List<E> entityList = dao.getEntityList(parameterSupplier.get());
        return entityList.stream()
                .map(entity -> transformEntityToDto(entity, mapToDtoFunction))
                .toList();
    }

    private <R, E> R transformEntityToDto(E entity, Function<E, R> mapToDtoFunction) {
        LOGGER.info("Entity: {}", entity);
        R dto = mapToDtoFunction.apply(entity);
        LOGGER.info("Entity Dto: {}", dto);
        return dto;
    }
}
