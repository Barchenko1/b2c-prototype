package com.b2c.prototype.processor.option.base;

import com.b2c.prototype.manager.option.ITimeDurationOptionManager;
import com.b2c.prototype.modal.dto.payload.option.TimeDurationOptionDto;
import com.b2c.prototype.processor.option.ITimeDurationOptionProcess;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static com.b2c.prototype.util.Constant.VALUE;

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
        timeDurationOptionManager.mergeEntity(requestParams.get(VALUE), timeDurationOptionDto);
    }

    @Override
    public void removeEntity(Map<String, String> requestParams) {
        timeDurationOptionManager.removeEntity(requestParams.get(VALUE));
    }

    @Override
    public List<TimeDurationOptionDto> getEntities(Map<String, String> requestParams) {
        return timeDurationOptionManager.getEntities();
    }

    @Override
    public TimeDurationOptionDto getEntity(Map<String, String> requestParams) {
        return timeDurationOptionManager.getEntity(requestParams.get(VALUE));
    }

}
