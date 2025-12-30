package com.b2c.prototype.controller.region;


import com.b2c.prototype.modal.dto.payload.tenant.TenantDto;
import com.b2c.prototype.processor.region.ITenantProcess;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/tenant")
public class TenantController {
    private final ITenantProcess regionProcess;

    public TenantController(ITenantProcess regionProcess) {
        this.regionProcess = regionProcess;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveEntity(@RequestBody TenantDto tenantDto) {
        regionProcess.persistEntity(tenantDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> putEntity(@RequestParam(value = "code") final String code, @RequestBody TenantDto tenantDto) {
        regionProcess.mergeEntity(code, tenantDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> deleteEntity(@RequestParam(value = "code") final String code) {
        regionProcess.removeEntity(code);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getEntities() {
        return ResponseEntity.ok(regionProcess.getEntityList());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getEntity(@RequestParam(value = "code") final String code) {
        return ResponseEntity.ok(regionProcess.getEntity(code));
    }
}
