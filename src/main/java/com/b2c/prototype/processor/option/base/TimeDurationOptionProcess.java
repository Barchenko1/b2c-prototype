package com.b2c.prototype.processor.option.base;

import com.b2c.prototype.manager.option.ITimeDurationOptionManager;
import com.b2c.prototype.modal.dto.payload.option.TimeDurationOptionDto;
import com.b2c.prototype.processor.option.ITimeDurationOptionProcess;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class TimeDurationOptionProcess implements ITimeDurationOptionProcess {

    private final ITimeDurationOptionManager timeDurationOptionManager;

    public TimeDurationOptionProcess(ITimeDurationOptionManager timeDurationOptionManager) {
        this.timeDurationOptionManager = timeDurationOptionManager;
    }

    @Override
    public void persistEntity(TimeDurationOptionDto timeDurationOptionDto) {
        timeDurationOptionManager.persistEntity(timeDurationOptionDto);
    }

    @Override
    public void mergeEntity(Map<String, String> requestParams, TimeDurationOptionDto timeDurationOptionDto) {
        LocalDateTime start = getLocalDateTime(requestParams.get("start"));
        LocalDateTime end = getLocalDateTime(requestParams.get("end"));
        timeDurationOptionManager.mergeEntity(start, end, timeDurationOptionDto);
    }

    @Override
    public void removeEntity(Map<String, String> requestParams) {
        LocalDateTime start = getLocalDateTime(requestParams.get("start"));
        LocalDateTime end = getLocalDateTime(requestParams.get("end"));
        timeDurationOptionManager.removeEntity(start, end);
    }

    @Override
    public List<TimeDurationOptionDto> getEntities(Map<String, String> requestParams) {
        return timeDurationOptionManager.getEntities();
    }

    @Override
    public TimeDurationOptionDto getEntity(Map<String, String> requestParams) {
        LocalDateTime start = getLocalDateTime(requestParams.get("start"));
        LocalDateTime end = getLocalDateTime(requestParams.get("end"));
        return timeDurationOptionManager.getEntity(start, end);
    }

    public static LocalDateTime getLocalDateTime(String date) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(date, f);
    }
}
