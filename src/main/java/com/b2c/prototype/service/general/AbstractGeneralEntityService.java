package com.b2c.prototype.service.general;

import com.tm.core.dao.general.IGeneralEntityDao;
import com.tm.core.modal.GeneralEntity;
import com.tm.core.processor.finder.factory.ParameterFactory;
import com.tm.core.processor.finder.parameter.Parameter;
import com.tm.core.processor.finder.factory.IParameterFactory;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Slf4j
public abstract class AbstractGeneralEntityService implements IGeneralEntityService {

    protected IParameterFactory parameterFactory;

    public AbstractGeneralEntityService() {
        this.parameterFactory = new ParameterFactory();
    }

    protected abstract IGeneralEntityDao getEntityDao();

    @Override
    public void saveEntity(GeneralEntity generalEntity) {
        log.info("Saving entity: {}", generalEntity);
        getEntityDao().saveGeneralEntity(generalEntity);
    }

    @Override
    public <E> void updateEntity(Supplier<E> supplier) {
        getEntityDao().updateGeneralEntity(supplier);
    }

    @Override
    public void updateEntity(Consumer<Session> consumer) {
        getEntityDao().updateGeneralEntity(consumer);
    }

    @Override
    public <E> void deleteEntity(Class<?> clazz, Parameter... parameters) {
        log.info("Deleting entity: {}", clazz);
        getEntityDao().deleteGeneralEntity(clazz, parameters);
    }

    @Override
    public <E> void deleteEntity(Parameter... parameters) {
        log.info("Deleting entity");
        getEntityDao().deleteGeneralEntity(parameters);
    }

    @Override
    public <E> List<E> getEntityList(Class<?> clazz, Parameter... parameters) {
        return getEntityDao().getGeneralEntityList(clazz, parameters);
    }

    @Override
    public <E> E getEntity(Class<?> clazz, Parameter... parameters) {
        return getEntityDao().getGeneralEntity(clazz, parameters);
    }

    @Override
    public <E> Optional<E> getOptionalEntity(Class<?> clazz, Parameter... parameters) {
        return getEntityDao().getOptionalGeneralEntity(clazz, parameters);
    }
}
