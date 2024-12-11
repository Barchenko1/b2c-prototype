//package com.b2c.prototype.service.common;
//
//import com.tm.core.dao.general.IGeneralEntityDao;
//import com.tm.core.processor.finder.parameter.Parameter;
//import org.hibernate.Session;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.function.Consumer;
//import java.util.function.Function;
//import java.util.function.Supplier;
//
//public class CommandGeneralEntityService implements IEntityOperationDao {
//    private static final Logger LOGGER = LoggerFactory.getLogger(CommandGeneralEntityService.class);
//
//    private final IGeneralEntityDao dao;
//
//    public CommandGeneralEntityService(IGeneralEntityDao dao) {
//        this.dao = dao;
//    }
//
////    @Override
////    public void saveEntity(Supplier<GeneralEntity> entitySupplier) {
////        GeneralEntity generalEntity = entitySupplier.get();
////        LOGGER.info("Saving entity: {}", generalEntity);
////        dao.saveEntity(generalEntity);
////    }
//
//    @Override
//    public <E> void saveEntity(Supplier<E> entitySupplier) {
//
//    }
//
//    @Override
//    public void saveEntity(Consumer<Session> consumer) {
//        LOGGER.info("Saving entity");
//        dao.saveEntity(consumer);
//    }
//
//    @Override
//    public void updateEntity(Consumer<Session> consumer) {
//        dao.updateEntity(consumer);
//    }
//
//    @Override
//    public void deleteEntityByParameter(Supplier<Parameter> parameterSupplier) {
//        StringBuilder sb = new StringBuilder();
//        Parameter parameter = parameterSupplier.get();
//        sb.append("Deleting entity: ");
//        sb.append(parameter.getName()).append(", ");
//        sb.append(parameter.getValue());
//        LOGGER.info("Deleting entity: {}", sb);
//        dao.findEntityAndDelete(parameter);
//    }
//
//    @Override
//    public <E> void deleteEntity(Supplier<E> supplier) {
//
//    }
//
//    @Override
//    public void deleteEntity(Consumer<Session> consumer) {
//
//    }
//
//    @Override
//    public <R, E> List<R> getEntityDtoList(Function<E, R> mapToDtoFunction) {
//        List<E> entityList = dao.getEntityList();
//        return entityList.stream()
//                .map(entity -> transformEntityToDto(entity, mapToDtoFunction))
//                .toList();
//    }
//
//    @Override
//    public <R, E> List<R> getSubEntityDtoList(Supplier<Parameter[]> parameterSupplier, Function<E, R> mapToDtoFunction) {
//        Parameter[] parametersArray = parameterSupplier.get();
//        List<E> entityList = dao.getEntityList(parametersArray);
//        return entityList.stream()
//                .map(entity -> transformEntityToDto(entity, mapToDtoFunction))
//                .toList();
//    }
//
//    @Override
//    public <R, E> R getEntityDto(Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction) {
//        E entity = dao.getEntity(parameterSupplier.get());
//        return Optional.ofNullable(entity)
//                .map(mapToDtoFunction)
//                .orElse(null);
//    }
//
//    @Override
//    public <R, E> Optional<R> getOptionalEntityDto(Supplier<Parameter> parameterSupplier, Function<E, R> mapToDtoFunction) {
//        E entity = dao.getEntity(parameterSupplier.get());
//        return Optional.ofNullable(entity).map(mapToDtoFunction);
//    }
//
//    private <R, E> R transformEntityToDto(E entity, Function<E, R> mapToDtoFunction) {
//        LOGGER.info("Entity: {}", entity);
//        R dto = mapToDtoFunction.apply(entity);
//        LOGGER.info("Entity Dto: {}", dto);
//        return dto;
//    }
//}
