package com.b2c.prototype.processor.option.base;

import com.b2c.prototype.manager.option.ITimeDurationOptionManager;
import com.b2c.prototype.modal.dto.payload.option.group.TimeDurationOptionGroupDto;
import com.b2c.prototype.processor.option.ITimeDurationOptionProcess;
import com.b2c.prototype.transform.order.IOrderTransformService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TimeDurationOptionProcess implements ITimeDurationOptionProcess {

    private final ObjectMapper objectMapper;
    private final ITimeDurationOptionManager timeDurationOptionManager;
    private final IOrderTransformService orderTransformService;

    public TimeDurationOptionProcess(ObjectMapper objectMapper,
                                     ITimeDurationOptionManager timeDurationOptionManager,
                                     IOrderTransformService orderTransformService) {
        this.objectMapper = objectMapper;
        this.timeDurationOptionManager = timeDurationOptionManager;
        this.orderTransformService = orderTransformService;
    }

    @Override
    public void persistEntity(Map<String, Object> payload) {
        TimeDurationOptionGroupDto dto = objectMapper.convertValue(payload, TimeDurationOptionGroupDto.class);
        timeDurationOptionManager.persistEntity(dto);
    }

    @Override
    public void mergeEntity(Map<String, Object> payload, String value) {
        TimeDurationOptionGroupDto dto = objectMapper.convertValue(payload, TimeDurationOptionGroupDto.class);
        timeDurationOptionManager.mergeEntity(value, dto);
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
