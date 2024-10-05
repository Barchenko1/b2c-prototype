package com.b2c.prototype.service.single;

import com.tm.core.dao.single.ISingleEntityDao;
import com.tm.core.processor.finder.factory.ParameterFactory;
import com.tm.core.processor.finder.parameter.Parameter;
import com.tm.core.processor.finder.factory.IParameterFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
public abstract class AbstractSingleEntityService implements ISingleEntityService {

    protected IParameterFactory parameterFactory;

    public AbstractSingleEntityService() {
        this.parameterFactory = new ParameterFactory();
    }

    protected abstract ISingleEntityDao getEntityDao();

    @Override
    public <E> void saveEntity(E entity) {
        log.info("Saving entity: {}", entity);
        getEntityDao().saveEntity(entity);
    }

    @Override
    public <E> void updateEntity(E entity, Parameter... parameters) {
        log.info("Updating entity: {}", entity);
        getEntityDao().findEntityAndUpdate(entity, parameters);
    }

    @Override
    public <E> void deleteEntity(Parameter... parameters) {
        StringBuilder sb = new StringBuilder();
        sb.append("Deleting entity: ");
        for (Parameter parameter : parameters) {
            sb.append(parameter.getName()).append(", ");
            sb.append(parameter.getValue());
        }
        log.info("Deleting entity: {}", sb);
        getEntityDao().findEntityAndDelete(parameters);
    }

    @Override
    public <E> List<E> getEntityList(Parameter... parameters) {
        return getEntityDao().getEntityList(parameters);
    }

    @Override
    public <E> E getEntity(Parameter... parameters) {
        return getEntityDao().getEntity(parameters);
    }

    @Override
    public <E> Optional<E> getOptionalEntity(Parameter... parameters) {
        return getEntityDao().getOptionalEntity(parameters);
    }
}
