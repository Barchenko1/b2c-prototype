package com.b2c.prototype.manager.option.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.manager.option.ITimeDurationOptionManager;
import com.b2c.prototype.modal.dto.payload.option.TimeDurationOptionDto;
import com.b2c.prototype.modal.dto.payload.option.ResponseTimeDurationOptionDto;
import com.b2c.prototype.modal.entity.option.TimeDurationOption;
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.b2c.prototype.util.Constant.VALUE;

@Service
public class TimeDurationOptionManager implements ITimeDurationOptionManager {

    private final IGeneralEntityDao generalEntityDao;
    private final ITransformationFunctionService transformationFunctionService;

    public TimeDurationOptionManager(IGeneralEntityDao generalEntityDao,
                                     ITransformationFunctionService transformationFunctionService) {
        this.generalEntityDao = generalEntityDao;
        this.transformationFunctionService = transformationFunctionService;
    }

    @Override
    public void saveUpdateTimeDurationOption(String timeDurationValue, TimeDurationOptionDto timeDurationOptionDto) {
        TimeDurationOption timeDurationOption = transformationFunctionService.getEntity(TimeDurationOption.class, timeDurationOptionDto);
        if (timeDurationValue != null) {
            TimeDurationOption existingZoneOption = generalEntityDao.findEntity(
                    "TimeDurationOption.findAllWithPriceAndCurrency",
                    Pair.of(VALUE, timeDurationValue));
            timeDurationOption.setId(existingZoneOption.getId());
            timeDurationOption.getPrice().setId(existingZoneOption.getPrice().getId());
            timeDurationOption.getPrice().getCurrency().setId(existingZoneOption.getPrice().getCurrency().getId());
        }
        generalEntityDao.mergeEntity(timeDurationOption);
    }

    @Override
    public void deleteTimeDurationOption(String timeDurationValue) {
        TimeDurationOption timeDurationOption = generalEntityDao.findEntity(
                "TimeDurationOption.findAllWithPriceAndCurrency",
                Pair.of(VALUE, timeDurationValue));
        generalEntityDao.removeEntity(timeDurationOption);
    }

    @Override
    public ResponseTimeDurationOptionDto getTimeDurationOptionDto(String timeDurationValue) {
        TimeDurationOption timeDurationOption = generalEntityDao.findEntity(
                "TimeDurationOption.findAllWithPriceAndCurrency",
                Pair.of(VALUE, timeDurationValue));

        return transformationFunctionService.getTransformationFunction(TimeDurationOption.class, ResponseTimeDurationOptionDto.class)
                .apply(timeDurationOption);
    }

    @Override
    public List<ResponseTimeDurationOptionDto> getTimeDurationOptionDtoList() {
        List<TimeDurationOption> timeDurationOptionList = generalEntityDao.findEntityList(
                "TimeDurationOption.all", (Pair<String, ?>) null);

        return timeDurationOptionList.stream()
                .map(transformationFunctionService.getTransformationFunction(TimeDurationOption.class, ResponseTimeDurationOptionDto.class))
                .toList();
    }
}
