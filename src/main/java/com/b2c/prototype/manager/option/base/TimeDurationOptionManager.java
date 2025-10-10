package com.b2c.prototype.manager.option.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.manager.option.ITimeDurationOptionManager;
import com.b2c.prototype.modal.dto.payload.option.TimeDurationOptionDto;
import com.b2c.prototype.modal.entity.option.TimeDurationOption;
import com.b2c.prototype.transform.order.IOrderTransformService;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.b2c.prototype.util.Constant.VALUE;

@Service
public class TimeDurationOptionManager implements ITimeDurationOptionManager {

    private final IGeneralEntityDao generalEntityDao;
    private final IOrderTransformService orderTransformService;

    public TimeDurationOptionManager(IGeneralEntityDao generalEntityDao,
                                     IOrderTransformService orderTransformService) {
        this.generalEntityDao = generalEntityDao;
        this.orderTransformService = orderTransformService;
    }

    @Override
    public void persistEntity(TimeDurationOptionDto timeDurationOptionDto) {
        TimeDurationOption timeDurationOption = orderTransformService.mapTimeDurationOptionDtoToTimeDurationOption(timeDurationOptionDto);
        generalEntityDao.persistEntity(timeDurationOption);
    }

    @Override
    public void mergeEntity(String value, TimeDurationOptionDto timeDurationOptionDto) {
        TimeDurationOption timeDurationOption = orderTransformService.mapTimeDurationOptionDtoToTimeDurationOption(timeDurationOptionDto);
        TimeDurationOption existingZoneOption = generalEntityDao.findEntity(
                "TimeDurationOption.findTimeDurationByValue",
                Pair.of(VALUE, value));
        timeDurationOption.setId(existingZoneOption.getId());
        timeDurationOption.getPrice().setId(existingZoneOption.getPrice().getId());
        timeDurationOption.getPrice().getCurrency().setId(existingZoneOption.getPrice().getCurrency().getId());
        generalEntityDao.mergeEntity(timeDurationOption);
    }

    @Override
    public void removeEntity(String value) {
        TimeDurationOption timeDurationOption = generalEntityDao.findEntity(
                "TimeDurationOption.findTimeDurationByValue",
                Pair.of(VALUE, value));
        generalEntityDao.removeEntity(timeDurationOption);
    }

    @Override
    public TimeDurationOptionDto getEntity(String value) {
        TimeDurationOption timeDurationOption = generalEntityDao.findEntity(
                "TimeDurationOption.findTimeDurationByValue",
                List.of(Pair.of(VALUE, value)));

        return orderTransformService.mapTimeDurationOptionToTimeDurationOptionDto(timeDurationOption);
    }

    @Override
    public List<TimeDurationOptionDto> getEntities() {
        List<TimeDurationOption> timeDurationOptionList = generalEntityDao.findEntityList(
                "TimeDurationOption.all", (Pair<String, ?>) null);

        return timeDurationOptionList.stream()
                .map(orderTransformService::mapTimeDurationOptionToTimeDurationOptionDto)
                .toList();
    }
}
