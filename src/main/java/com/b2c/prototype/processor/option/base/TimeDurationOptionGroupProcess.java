package com.b2c.prototype.processor.option.base;

import com.b2c.prototype.manager.option.ITimeDurationGroupOptionManager;
import com.b2c.prototype.modal.dto.payload.option.group.TimeDurationOptionGroupDto;
import com.b2c.prototype.processor.option.ITimeDurationOptionGroupProcess;
import com.b2c.prototype.transform.order.IOrderTransformService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TimeDurationOptionGroupProcess implements ITimeDurationOptionGroupProcess {
    private final ITimeDurationGroupOptionManager timeDurationOptionManager;
    private final IOrderTransformService orderTransformService;

    public TimeDurationOptionGroupProcess(ITimeDurationGroupOptionManager timeDurationOptionManager,
                                          IOrderTransformService orderTransformService) {
        this.timeDurationOptionManager = timeDurationOptionManager;
        this.orderTransformService = orderTransformService;
    }

    @Override
    public void persistEntity(TimeDurationOptionGroupDto payload) {
        timeDurationOptionManager.persistEntity(payload);
    }

    @Override
    public void mergeEntity(TimeDurationOptionGroupDto timeDurationOptionGroupDto, String value) {
        timeDurationOptionManager.mergeEntity(value, timeDurationOptionGroupDto);
    }

    @Override
    public void removeEntity(String value) {
        timeDurationOptionManager.removeEntity(value);
    }

    @Override
    public List<TimeDurationOptionGroupDto> getEntityList(String location) {
        return timeDurationOptionManager.getEntities().stream()
                .map(orderTransformService::mapOptionGroupToTimeDurationOptionGroupDto)
                .toList();
    }

    @Override
    public TimeDurationOptionGroupDto getEntity(String location, String value) {
        return Optional.of(timeDurationOptionManager.getEntity(value))
                .map(orderTransformService::mapOptionGroupToTimeDurationOptionGroupDto)
                .orElseThrow(() -> new RuntimeException(""));
    }
}
