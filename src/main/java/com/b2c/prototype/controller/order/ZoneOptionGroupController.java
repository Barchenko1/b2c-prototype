package com.b2c.prototype.controller.order;

import com.b2c.prototype.modal.dto.payload.option.group.ZoneOptionGroupDto;
import com.b2c.prototype.processor.option.IZoneOptionGroupProcess;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/option/group/zone")
public class ZoneOptionGroupController {

    private final IZoneOptionGroupProcess zoneOptionProcess;

    public ZoneOptionGroupController(IZoneOptionGroupProcess zoneOptionProcess) {
        this.zoneOptionProcess = zoneOptionProcess;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveConstantEntity(@RequestBody ZoneOptionGroupDto payload) {
        zoneOptionProcess.persistEntity(payload);
        return ResponseEntity.ok().build();
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> putConstantEntity(@RequestBody ZoneOptionGroupDto payload,
                                                    @RequestParam(value = "key") final String key) {
        zoneOptionProcess.mergeEntity(payload, key);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> patchConstantEntity(@RequestBody ZoneOptionGroupDto payload,
                                                      @RequestParam(value = "key") final String key) {
        zoneOptionProcess.mergeEntity(payload, key);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> deleteConstantEntity(@RequestParam(value = "key") final String key) {
        zoneOptionProcess.removeEntity(key);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getConstantEntities(@RequestHeader(name = "Accept-Language", defaultValue = "en") String location) {
        return ResponseEntity.ok(zoneOptionProcess.getEntityList(location));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getConstantEntity(@RequestHeader(name = "Accept-Language", defaultValue = "en") String location,
                                               @RequestParam(value = "key") final String key) {
        return ResponseEntity.ok(zoneOptionProcess.getEntity(location, key));
    }

}
