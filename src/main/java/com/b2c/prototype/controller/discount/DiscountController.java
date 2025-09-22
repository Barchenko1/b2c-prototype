package com.b2c.prototype.controller.discount;

import com.b2c.prototype.modal.dto.payload.discount.DiscountDto;
import com.b2c.prototype.modal.dto.payload.discount.DiscountStatusDto;
import com.b2c.prototype.processor.item.IDiscountProcess;
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
@RequestMapping("/api/v1/discount")
public class DiscountController {
    private final IDiscountProcess discountProcess;

    public DiscountController(IDiscountProcess discountProcess) {
        this.discountProcess = discountProcess;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveDiscount(@RequestParam final Map<String, String> requestParams,
                                             @RequestBody final DiscountDto discountDto) {
        discountProcess.saveDiscount(requestParams, discountDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> putDiscount(@RequestParam final Map<String, String> requestParams,
                                            @RequestBody final DiscountDto discountDto) {
        discountProcess.updateDiscount(requestParams, discountDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> patchDiscount(@RequestParam final Map<String, String> requestParams,
                                              @RequestBody final DiscountDto discountDto) {
        discountProcess.updateDiscount(requestParams, discountDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> changeDiscountStatus(@RequestBody final DiscountStatusDto discountStatusDto) {
        discountProcess.changeDiscountStatus(discountStatusDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> changeDiscountStatus(@RequestParam final Map<String, String> requestParams,
                                                     @RequestBody Map<String, Object> updates) {
        discountProcess.changeDiscountStatus(requestParams, updates);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Void> deleteDiscount(@RequestParam final Map<String, String> requestParams) {
        discountProcess.deleteDiscount(requestParams);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DiscountDto> getDiscounts(@RequestParam final Map<String, String> requestParams) {
        return discountProcess.getDiscounts(requestParams);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DiscountDto> getDiscount(@RequestParam final Map<String, String> requestParams) {
        return new ResponseEntity<>(discountProcess.getDiscount(requestParams), HttpStatus.OK);
    }
}
