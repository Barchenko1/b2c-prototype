package com.b2c.prototype.service.transitive;

import com.tm.core.dao.transitive.ITransitiveSelfEntityDao;
import com.tm.core.modal.TransitiveSelfEntity;
import com.tm.core.processor.finder.factory.ParameterFactory;
import com.tm.core.processor.finder.parameter.Parameter;
import com.tm.core.processor.finder.factory.IParameterFactory;
import com.tm.core.util.TransitiveSelfEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
public abstract class AbstractTransitiveSelfEntityService implements ITransitiveSelfEntityService {

    protected IParameterFactory parameterFactory;

    public AbstractTransitiveSelfEntityService() {
        this.parameterFactory = new ParameterFactory();
    }

    protected abstract ITransitiveSelfEntityDao getEntityDao();

    @Override
    public <E extends TransitiveSelfEntity> void saveEntityTree(E entity) {
        log.info("Saving entity: {}", entity);
        getEntityDao().saveEntityTree(entity);
    }

    @Override
    public <E extends TransitiveSelfEntity> void updateEntityTree(E entity, Parameter... parameters) {
        log.info("Updating entity: {}", entity);
        getEntityDao().updateEntityTree(entity, parameters);
    }

    @Override
    public <E extends TransitiveSelfEntity> void deleteEntityTree(Parameter... parameters) {
        StringBuilder sb = new StringBuilder();
        sb.append("Deleting entity: ");
        for (Parameter parameter : parameters) {
            sb.append(parameter.getName()).append(", ");
            sb.append(parameter.getValue());
        }
        log.info("Deleting entity: {}", sb);
        getEntityDao().deleteEntityTree(parameters);
    }

    @Override
    public <E extends TransitiveSelfEntity> List<E> getTransitiveSelfEntityList(Parameter... parameters) {
        return getEntityDao().getTransitiveSelfEntityList(parameters);
    }

    @Override
    public <E extends TransitiveSelfEntity> E getTransitiveSelfEntity(Parameter... parameters) {
        return getEntityDao().getTransitiveSelfEntity(parameters);
    }

    @Override
    public <E extends TransitiveSelfEntity> Optional<E> getOptionalTransitiveSelfEntity(Parameter... parameters) {
        return getEntityDao().getOptionalTransitiveSelfEntity(parameters);
    }

    @Override
    public <E extends TransitiveSelfEntity> Map<TransitiveSelfEnum, List<E>> getTransitiveSelfEntitiesTreeBySQLQuery(String sqlQuery) {
        return getEntityDao().getTransitiveSelfEntitiesTree();
    }
}
