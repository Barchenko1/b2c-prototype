package com.b2c.prototype.manager;

import com.tm.core.process.dao.transitive.ITransitiveSelfEntityDao;
import com.tm.core.modal.TransitiveSelfEntity;
import com.tm.core.finder.factory.ParameterFactory;
import com.tm.core.finder.parameter.Parameter;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.util.TransitiveSelfEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
public abstract class AbstractTransitiveSelfEntityManager implements ITransitiveSelfEntityManager {

    protected IParameterFactory parameterFactory;

    public AbstractTransitiveSelfEntityManager() {
        this.parameterFactory = new ParameterFactory();
    }

    protected abstract ITransitiveSelfEntityDao getEntityDao();

    @Override
    public <E extends TransitiveSelfEntity> void saveEntityTree(E entity) {
        log.info("Saving entity: {}", entity);
        getEntityDao().saveEntityTree(entity);
    }

    @Override
    public <E extends TransitiveSelfEntity> void updateEntityTreeOldMain(E entity, Parameter... parameters) {
        log.info("Updating entity: {}", entity);
        getEntityDao().updateEntityTreeOldMain(entity, parameters);
    }

    @Override
    public void deleteEntityTree(Parameter... parameters) {
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
