package com.b2c.prototype.controller.constant;

import com.b2c.prototype.service.orchestrator.IConstantOrchestratorService;
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
@RequestMapping("/api/v1/singlevalue")
public class ConstantController {

    private final IConstantOrchestratorService constantOrchestratorService;

    public ConstantController(IConstantOrchestratorService constantOrchestratorService) {
        this.constantOrchestratorService = constantOrchestratorService;
    }


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveConstantEntity(@RequestBody Map<String, Object> payload,
                                                     @RequestHeader(value = "serviceId") final String serviceId) {
        constantOrchestratorService.saveConstantEntity(payload, serviceId);
        return ResponseEntity.ok().build();
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> putConstantEntity(@RequestBody Map<String, Object> payload,
                                                    @RequestHeader(value = "serviceId") final String serviceId,
                                                    @RequestParam(value = "value") final String value) {
        constantOrchestratorService.putConstantEntity(payload, serviceId, value);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> patchConstantEntity(@RequestBody Map<String, Object> payload,
                                                      @RequestHeader(value = "serviceId") final String serviceId,
                                                      @RequestParam(value = "value") final String value) {
        constantOrchestratorService.patchConstantEntity(payload, serviceId, value);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> deleteConstantEntity(@RequestHeader(value = "serviceId") final String serviceId,
                                                       @RequestParam(value = "value") final String value) {
        constantOrchestratorService.deleteConstantEntity(serviceId, value);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getConstantEntities(@RequestHeader(name = "Accept-Language", defaultValue = "en") String location,
                                                 @RequestHeader(value = "serviceId") final String serviceId) {
        return ResponseEntity.ok(constantOrchestratorService.getConstantEntities(location, serviceId));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getConstantEntity(@RequestHeader(name = "Accept-Language", defaultValue = "en") String location,
                                               @RequestHeader(name = "serviceId") final String serviceId,
                                               @RequestParam(value = "value") final String value) {
        return ResponseEntity.ok(constantOrchestratorService.getConstantEntity(location, serviceId, value));
    }
}
