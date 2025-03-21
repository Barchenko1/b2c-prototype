package com.b2c.prototype.manager.option.base;

import com.b2c.prototype.dao.option.ITimeDurationOptionDao;
import com.b2c.prototype.manager.option.ITimeDurationOptionManager;
import com.b2c.prototype.modal.dto.payload.option.TimeDurationOptionDto;
import com.b2c.prototype.modal.dto.response.ResponseTimeDurationOptionDto;
import com.b2c.prototype.modal.entity.option.TimeDurationOption;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.manager.common.EntityOperationManager;
import com.tm.core.process.manager.common.IEntityOperationManager;

import java.util.List;

import static com.b2c.prototype.util.Constant.VALUE;

public class TimeDurationOptionManager implements ITimeDurationOptionManager {

    private final IEntityOperationManager entityOperationManager;
    private final ITransformationFunctionService transformationFunctionService;
    private final IParameterFactory parameterFactory;

    public TimeDurationOptionManager(ITimeDurationOptionDao timeDurationOptionDao, ITransformationFunctionService transformationFunctionService, IParameterFactory parameterFactory) {
        this.entityOperationManager = new EntityOperationManager(timeDurationOptionDao);
        this.transformationFunctionService = transformationFunctionService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void saveUpdateTimeDurationOption(String timeDurationValue, TimeDurationOptionDto timeDurationOptionDto) {
        entityOperationManager.executeConsumer(session -> {
            TimeDurationOption timeDurationOption = transformationFunctionService.getEntity(session, TimeDurationOption.class, timeDurationOptionDto);
            if (timeDurationValue != null) {
                TimeDurationOption existingZoneOption = entityOperationManager.getNamedQueryEntity(
                        "TimeDurationOption.findAllWithPriceAndCurrency",
                        parameterFactory.createStringParameter(VALUE, timeDurationValue));
                timeDurationOption.setId(existingZoneOption.getId());
                timeDurationOption.getPrice().setId(existingZoneOption.getPrice().getId());
                timeDurationOption.getPrice().getCurrency().setId(existingZoneOption.getPrice().getCurrency().getId());
            }
            session.merge(timeDurationOption);
        });
    }

    @Override
    public void deleteTimeDurationOption(String timeDurationValue) {
        entityOperationManager.deleteEntityByParameter(
                parameterFactory.createStringParameter(VALUE, timeDurationValue));
    }

    @Override
    public ResponseTimeDurationOptionDto getTimeDurationOptionDto(String timeDurationValue) {
        return entityOperationManager.getNamedQueryEntityDto(
                "TimeDurationOption.findAllWithPriceAndCurrency",
                parameterFactory.createStringParameter(VALUE, timeDurationValue),
                transformationFunctionService.getTransformationFunction(TimeDurationOption.class, ResponseTimeDurationOptionDto.class));
    }

    @Override
    public List<ResponseTimeDurationOptionDto> getTimeDurationOptionDtoList() {
        return entityOperationManager.getNamedQueryEntityDtoList(
                "TimeDurationOption.all",
                transformationFunctionService.getTransformationFunction(TimeDurationOption.class, ResponseTimeDurationOptionDto.class));
    }
}
