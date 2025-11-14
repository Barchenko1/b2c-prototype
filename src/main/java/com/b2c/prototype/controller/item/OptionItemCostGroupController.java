package com.b2c.prototype.controller.item;

import com.b2c.prototype.modal.dto.payload.option.group.OptionItemCostGroupDto;
import com.b2c.prototype.processor.option.IOptionItemCostGroupProcess;
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

@RestController
@RequestMapping("/api/v1/option/group/item/cost")
public class OptionItemCostGroupController {

    private final IOptionItemCostGroupProcess optionItemCostGroupProcess;

    public OptionItemCostGroupController(IOptionItemCostGroupProcess optionItemCostGroupProcess) {
        this.optionItemCostGroupProcess = optionItemCostGroupProcess;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveConstantEntity(@RequestBody OptionItemCostGroupDto payload) {
        optionItemCostGroupProcess.persistEntity(payload);
        return ResponseEntity.ok().build();
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> putConstantEntity(@RequestBody OptionItemCostGroupDto payload,
                                                    @RequestParam(value = "key") final String key) {
        optionItemCostGroupProcess.mergeEntity(payload, key);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> deleteConstantEntity(@RequestParam(value = "key") final String key) {
        optionItemCostGroupProcess.removeEntity(key);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getConstantEntities(@RequestHeader(name = "Accept-Language", defaultValue = "en") String location) {
        return ResponseEntity.ok(optionItemCostGroupProcess.getEntityList(location));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getConstantEntity(@RequestHeader(name = "Accept-Language", defaultValue = "en") String location,
                                               @RequestParam(value = "key") final String key) {
        return ResponseEntity.ok(optionItemCostGroupProcess.getEntity(location, key));
    }

}
