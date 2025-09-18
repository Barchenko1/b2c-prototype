package com.b2c.prototype.manager.option.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.manager.option.ITimeDurationOptionManager;
import com.b2c.prototype.modal.dto.payload.option.TimeDurationOptionDto;
import com.b2c.prototype.modal.dto.payload.option.ResponseTimeDurationOptionDto;
import com.b2c.prototype.modal.entity.option.TimeDurationOption;
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.manager.common.ITransactionEntityOperationManager;
import com.tm.core.process.manager.common.operator.TransactionEntityOperationManager;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.b2c.prototype.util.Constant.VALUE;

@Service
public class TimeDurationOptionManager implements ITimeDurationOptionManager {

    private final ITransactionEntityOperationManager entityOperationManager;
    private final ITransformationFunctionService transformationFunctionService;
    private final IParameterFactory parameterFactory;

    public TimeDurationOptionManager(IGeneralEntityDao generalEntityDao,
                                     ITransformationFunctionService transformationFunctionService,
                                     IParameterFactory parameterFactory) {
        this.entityOperationManager = new TransactionEntityOperationManager(null);
        this.transformationFunctionService = transformationFunctionService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void saveUpdateTimeDurationOption(String timeDurationValue, TimeDurationOptionDto timeDurationOptionDto) {
        entityOperationManager.executeConsumer(session -> {
            TimeDurationOption timeDurationOption = transformationFunctionService.getEntity((Session) session, TimeDurationOption.class, timeDurationOptionDto);
            if (timeDurationValue != null) {
                TimeDurationOption existingZoneOption = entityOperationManager.getNamedQueryEntityClose(
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
        entityOperationManager.executeConsumer(session -> {
            TimeDurationOption timeDurationOption = entityOperationManager.getNamedQueryEntityClose(
                    "TimeDurationOption.findAllWithPriceAndCurrency",
                    parameterFactory.createStringParameter(VALUE, timeDurationValue));
            entityOperationManager.deleteEntity(timeDurationOption);
        });
    }

    @Override
    public ResponseTimeDurationOptionDto getTimeDurationOptionDto(String timeDurationValue) {
        TimeDurationOption timeDurationOption = entityOperationManager.getNamedQueryEntityClose(
                "TimeDurationOption.findAllWithPriceAndCurrency",
                parameterFactory.createStringParameter(VALUE, timeDurationValue));

        return transformationFunctionService.getTransformationFunction(TimeDurationOption.class, ResponseTimeDurationOptionDto.class)
                .apply(timeDurationOption);
    }

    @Override
    public List<ResponseTimeDurationOptionDto> getTimeDurationOptionDtoList() {
        List<TimeDurationOption> timeDurationOptionList = entityOperationManager.getNamedQueryEntityListClose(
                "TimeDurationOption.all");

        return timeDurationOptionList.stream()
                .map(transformationFunctionService.getTransformationFunction(TimeDurationOption.class, ResponseTimeDurationOptionDto.class))
                .toList();
    }
}
