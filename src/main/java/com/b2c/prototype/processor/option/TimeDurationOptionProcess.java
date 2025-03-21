package com.b2c.prototype.processor.option;

import com.b2c.prototype.manager.option.ITimeDurationOptionManager;
import com.b2c.prototype.modal.dto.payload.option.TimeDurationOptionDto;
import com.b2c.prototype.modal.dto.response.ResponseTimeDurationOptionDto;

import java.util.List;
import java.util.Map;

public class TimeDurationOptionProcess implements ITimeDurationOptionProcess {

    private final ITimeDurationOptionManager timeDurationOptionManager;

    public TimeDurationOptionProcess(ITimeDurationOptionManager timeDurationOptionManager) {
        this.timeDurationOptionManager = timeDurationOptionManager;
    }

    @Override
    public void saveUpdateTimeDurationOption(Map<String, String> requestParams, TimeDurationOptionDto timeDurationOptionDto) {
        String value = requestParams.get("value");
        timeDurationOptionManager.saveUpdateTimeDurationOption(value, timeDurationOptionDto);
    }

    @Override
    public void deleteTimeDurationOption(Map<String, String> requestParams) {
        String value = requestParams.get("value");
        timeDurationOptionManager.deleteTimeDurationOption(value);
    }

    @Override
    public List<ResponseTimeDurationOptionDto> getTimeDurationOptionDtoList(Map<String, String> requestParams) {
        return timeDurationOptionManager.getTimeDurationOptionDtoList();
    }

    @Override
    public ResponseTimeDurationOptionDto getTimeDurationOptionDto(Map<String, String> requestParams) {
        String value = requestParams.get("value");
        return timeDurationOptionManager.getTimeDurationOptionDto(value);
    }

}
