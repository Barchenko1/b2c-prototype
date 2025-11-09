package com.b2c.prototype.controller.order;

import com.b2c.prototype.modal.dto.payload.option.group.TimeDurationOptionGroupDto;
import com.b2c.prototype.processor.option.ITimeDurationOptionGroupProcess;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/option/group/duration")
public class TimeDurationOptionGroupController {

    private final ITimeDurationOptionGroupProcess timeDurationOptionProcess;

    public TimeDurationOptionGroupController(ITimeDurationOptionGroupProcess timeDurationOptionProcess) {
        this.timeDurationOptionProcess = timeDurationOptionProcess;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveConstantEntity(@RequestBody TimeDurationOptionGroupDto payload) {
        timeDurationOptionProcess.persistEntity(payload);
        return ResponseEntity.ok().build();
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> putConstantEntity(@RequestBody TimeDurationOptionGroupDto payload,
                                                    @RequestParam(value = "key") final String key) {
        timeDurationOptionProcess.mergeEntity(payload, key);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> deleteConstantEntity(@RequestParam(value = "key") final String key) {
        timeDurationOptionProcess.removeEntity(key);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getConstantEntities(@RequestHeader(name = "Accept-Language", defaultValue = "en") String location) {
        return ResponseEntity.ok(timeDurationOptionProcess.getEntityList(location));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getConstantEntity(@RequestHeader(name = "Accept-Language", defaultValue = "en") String location,
                                               @RequestParam(value = "key") final String key) {
        return ResponseEntity.ok(timeDurationOptionProcess.getEntity(location, key));
    }

}
