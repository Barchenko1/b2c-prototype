package com.b2c.prototype.controller.option;

import com.b2c.prototype.modal.dto.payload.TimeDurationOptionDto;
import com.b2c.prototype.modal.dto.response.ResponseTimeDurationOptionDto;
import com.b2c.prototype.processor.option.ITimeDurationOptionProcess;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/timeduration")
public class TimeDurationOptionController {
    private final ITimeDurationOptionProcess timeDurationOptionProcess;


    public TimeDurationOptionController(ITimeDurationOptionProcess timeDurationOptionProcess) {
        this.timeDurationOptionProcess = timeDurationOptionProcess;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveUpdateTimeDurationOption(@RequestParam final Map<String, String> requestParams,
                                                             @RequestBody final TimeDurationOptionDto timeDurationOptionDto) {
        timeDurationOptionProcess.saveUpdateTimeDurationOption(requestParams, timeDurationOptionDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Void> deleteTimeDurationOption(@RequestParam final Map<String, String> requestParams) {
        timeDurationOptionProcess.deleteTimeDurationOption(requestParams);
        return ResponseEntity.ok().build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseTimeDurationOptionDto> getTimeDurationOption(@RequestParam final Map<String, String> requestParams) {
        return new ResponseEntity<>(timeDurationOptionProcess.getTimeDurationOptionDto(requestParams), HttpStatus.OK);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponseTimeDurationOptionDto> getTimeDurationOptionList(@RequestParam final Map<String, String> requestParams) {
        return timeDurationOptionProcess.getTimeDurationOptionDtoList(requestParams);
    }
}
