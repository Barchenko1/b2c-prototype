package com.b2c.prototype.controller.discount;

import com.b2c.prototype.modal.dto.payload.discount.DiscountGroupDto;
import com.b2c.prototype.modal.dto.payload.discount.DiscountStatusDto;
import com.b2c.prototype.processor.item.IDiscountGroupProcess;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/group/discount")
public class DiscountGroupController {
    private final IDiscountGroupProcess discountGroupProcess;

    public DiscountGroupController(IDiscountGroupProcess discountGroupProcess) {
        this.discountGroupProcess = discountGroupProcess;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveDiscountGroup(@RequestParam final Map<String, String> requestParams,
                                                  @RequestBody DiscountGroupDto payload) {
        discountGroupProcess.saveDiscountGroup(requestParams, payload);
        return ResponseEntity.ok().build();
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> putDiscountGroup(@RequestParam final Map<String, String> requestParams,
                                                 @RequestBody final DiscountGroupDto discountGroupDto) {
        discountGroupProcess.updateDiscountGroup(requestParams, discountGroupDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> changeDiscountStatus(@RequestBody DiscountStatusDto payload) {
        discountGroupProcess.changeDiscountStatus(payload);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Void> deleteDiscountGroup(@RequestParam final Map<String, String> requestParams) {
        discountGroupProcess.removeDiscountGroup(requestParams);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DiscountGroupDto> getDiscountGroups(@RequestParam final Map<String, String> requestParams) {
        return discountGroupProcess.getDiscountGroups(requestParams);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DiscountGroupDto> getDiscountGroup(@RequestParam final Map<String, String> requestParams) {
        return new ResponseEntity<>(discountGroupProcess.getDiscountGroup(requestParams), HttpStatus.OK);
    }
}
