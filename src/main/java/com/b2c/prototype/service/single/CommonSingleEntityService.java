package com.b2c.prototype.service.single;

import com.tm.core.dao.single.ISingleEntityDao;
import com.tm.core.processor.finder.parameter.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class CommonSingleEntityService implements ICommonSingleEntityService {
    private static final Logger LOG = LoggerFactory.getLogger(CommonSingleEntityService.class);

    private final ISingleEntityDao dao;

    public CommonSingleEntityService(ISingleEntityDao dao) {
        this.dao = dao;
    }

    @Override
    public <E> void saveEntity(E entity) {
        LOG.info("Saving entity: {}", entity);
        dao.saveEntity(entity);
    }

    @Override
    public <E> void updateEntity(E entity, Parameter... parameters) {
        LOG.info("Updating entity: {}", entity);
        dao.findEntityAndUpdate(entity, parameters);
    }

    @Override
    public void deleteEntityByParameter(Parameter... parameters) {
        StringBuilder sb = new StringBuilder();
        sb.append("Deleting entity: ");
        for (Parameter parameter : parameters) {
            sb.append(parameter.getName()).append(", ");
            sb.append(parameter.getValue());
        }
        LOG.info("Deleting entity: {}", sb);
        dao.findEntityAndDelete(parameters);
    }

    @Override
    public <E> List<E> getEntityList(Parameter... parameters) {
        return dao.getEntityList(parameters);
    }

    @Override
    public <E> E getEntity(Parameter... parameters) {
        return dao.getEntity(parameters);
    }

    @Override
    public <E> Optional<E> getOptionalEntity(Parameter... parameters) {
        return dao.getOptionalEntity(parameters);
    }
}
