package com.b2c.prototype.service.function;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.entity.price.Currency;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class TransformationFunctionService implements ITransformationFunctionService {

    private final Map<String, Function<?, ?>> functionMap = new HashMap<>();

    public TransformationFunctionService() {
        addOneFieldEntityDtoTransformationFunction(Currency.class, null, getOneFieldEntityDtoFunction());
    }

    @Override
    public <E, R> Function<E, R> getTransformationFunction(Class<E> classFrom, Class<R> classTo, String sol) {
        return (Function<E, R>) this.functionMap.get(createKey(classFrom, classTo, sol));
    }

    @Override
    public <E, R> R getEntity(Class<R> classTo, E dataEntity, String sol) {
        @SuppressWarnings("unchecked")
        Class<E> classFrom = (Class<E>) dataEntity.getClass();
        return getTransformationFunction(classFrom, classTo, sol)
                .apply(dataEntity);
    }

    private <E, R> void addOneFieldEntityDtoTransformationFunction(Class<R> classTo, String sol, Function<E, R> function) {
        this.functionMap.put(createKey(OneFieldEntityDto.class, classTo, sol), function);
    }

    private <E, R> void addTransformationFunction(Class<E> classFrom, Class<R> classTo, String sol, Function<?, ?> function) {
        this.functionMap.put(createKey(classFrom, classTo, sol), function);
    }

    private <E, R> String createKey(Class<E> classFrom, Class<R> classTo, String sol) {
        return sol != null && !sol.isEmpty()
                ? classFrom.getName() + "->" + classTo.getName() + "[" + sol+ "]"
                : classFrom.getName() + "->" + classTo.getName();
    }

    private Function<OneFieldEntityDto, Currency> getOneFieldEntityDtoFunction() {
        return oneFieldEntityDto -> Currency.builder()
                .value(oneFieldEntityDto.getValue())
                .build();
    }
}
