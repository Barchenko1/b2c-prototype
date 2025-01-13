package com.b2c.prototype.controller.advanse;

import com.b2c.prototype.modal.dto.payload.DiscountDto;
import com.b2c.prototype.service.processor.item.IDiscountService;
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
@RequestMapping("/api/v1/discount")
public class DiscountController {
    private final IDiscountService discountService;

    public DiscountController(IDiscountService discountService) {
        this.discountService = discountService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveDiscount(@RequestBody final DiscountDto discountDto) {
        discountService.saveDiscount(discountDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping("/1")
    public ResponseEntity<Void> updateDiscountByArticularId(@RequestBody final DiscountDto discountDto,
                                                            @RequestParam(value = "articularId") final String articularId) {

        return ResponseEntity.ok().build();
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping("/2")
    public ResponseEntity<Void> updateDiscountByCharSequenceCode(@RequestBody final DiscountDto discountDto,
                                                                 @RequestParam(value = "charSequenceCode") final String charSequenceCode) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Void> deleteDiscount(@RequestParam(value = "value") final String value) {

        return ResponseEntity.ok().build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping("/all")
    public ResponseEntity<?> getDiscounts(@RequestHeader(name = "Accept-Language", defaultValue = "en") String location) {

        return ResponseEntity.badRequest().body("Invalid serviceId.");
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDiscount(@RequestHeader(name = "Accept-Language", defaultValue = "en") String location,
                                               @RequestParam(value = "value") final String value) {

        return ResponseEntity.badRequest().body("Invalid serviceId.");
    }
}
