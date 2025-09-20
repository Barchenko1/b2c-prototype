package com.b2c.prototype.controller.item;

import com.b2c.prototype.modal.dto.payload.option.ZoneOptionDto;
import com.b2c.prototype.processor.option.IZoneOptionProcess;
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
@RequestMapping("/api/v1/zone")
public class ZoneOptionController {
    private final IZoneOptionProcess zoneOptionProcess;

    public ZoneOptionController(IZoneOptionProcess zoneOptionProcess) {
        this.zoneOptionProcess = zoneOptionProcess;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveUpdateTimeDurationOption(@RequestParam final Map<String, String> requestParams,
                                                             @RequestBody final ZoneOptionDto zoneOptionDto) {
        zoneOptionProcess.saveUpdateZoneOption(requestParams, zoneOptionDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Void> deleteTimeDurationOption(@RequestParam final Map<String, String> requestParams) {
        zoneOptionProcess.deleteZoneOption(requestParams);
        return ResponseEntity.ok().build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ZoneOptionDto> getTimeDurationOption(@RequestParam final Map<String, String> requestParams) {
        return new ResponseEntity<>(zoneOptionProcess.getZoneOption(requestParams), HttpStatus.OK);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<?> getTimeDurationOptionList(@RequestParam final Map<String, String> requestParams) {
        return zoneOptionProcess.getZoneOptionList(requestParams);
    }
}
