package com.b2c.prototype.service.general;

import com.tm.core.dao.general.IGeneralEntityDao;
import com.tm.core.modal.GeneralEntity;
import com.tm.core.processor.finder.parameter.Parameter;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class CommonGeneralEntityService implements ICommonGeneralEntityService {
    private static final Logger LOG = LoggerFactory.getLogger(CommonGeneralEntityService.class);

    private final IGeneralEntityDao dao;

    public CommonGeneralEntityService(IGeneralEntityDao dao) {
        this.dao = dao;
    }

    @Override
    public void saveEntity(GeneralEntity generalEntity) {
        LOG.info("Saving entity: {}", generalEntity);
        dao.saveGeneralEntity(generalEntity);
    }

    @Override
    public void updateEntity(Consumer<Session> consumer) {
        LOG.info("Updating entity");
        dao.updateGeneralEntity(consumer);
    }

    @Override
    public void deleteEntity(Class<?> clazz, Parameter... parameters) {
        LOG.info("Deleting entity: {}", clazz);
        dao.deleteGeneralEntity(clazz, parameters);
    }

    @Override
    public void deleteEntity(Parameter... parameters) {
        LOG.info("Deleting entity by parameter: {}", (Object[]) parameters);
        dao.deleteGeneralEntity(parameters);
    }

    @Override
    public <E> List<E> getEntityList(Class<?> clazz, Parameter... parameters) {
        return dao.getGeneralEntityList(clazz, parameters);
    }

    @Override
    public <E> E getEntity(Class<?> clazz, Parameter... parameters) {
        return dao.getGeneralEntity(clazz, parameters);
    }

    @Override
    public <E> Optional<E> getOptionalEntity(Class<?> clazz, Parameter... parameters) {
        return dao.getOptionalGeneralEntity(clazz, parameters);
    }
}
