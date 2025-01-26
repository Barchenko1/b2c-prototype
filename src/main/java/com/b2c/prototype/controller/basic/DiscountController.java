package com.b2c.prototype.controller.basic;

import com.b2c.prototype.modal.dto.payload.DiscountDto;
import com.b2c.prototype.modal.dto.payload.DiscountStatusDto;
import com.b2c.prototype.service.manager.item.IDiscountManager;
import org.springframework.http.HttpStatus;
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

import java.util.List;

@RestController
@RequestMapping("/api/v1/discount")
public class DiscountController {
    private final IDiscountManager discountService;

    public DiscountController(IDiscountManager discountService) {
        this.discountService = discountService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveDiscount(@RequestBody final DiscountDto discountDto) {
        discountService.saveDiscount(discountDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/articular", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> putDiscountByArticularId(@RequestBody final DiscountDto discountDto,
                                                         @RequestParam(value = "articularId") final String articularId) {
        discountService.updateItemDataDiscount(articularId, discountDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/sequence", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> putDiscountByCharSequenceCode(@RequestBody final DiscountDto discountDto,
                                                              @RequestParam(value = "charSequenceCode") final String charSequenceCode) {
        discountService.updateDiscount(charSequenceCode, discountDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/articular", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> patchDiscountByArticularId(@RequestBody final DiscountDto discountDto,
                                                           @RequestParam(value = "articularId") final String articularId) {
        discountService.updateItemDataDiscount(articularId, discountDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/sequence", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> patchDiscountByCharSequenceCode(@RequestBody final DiscountDto discountDto,
                                                                @RequestParam(value = "charSequenceCode") final String charSequenceCode) {
        discountService.updateDiscount(charSequenceCode, discountDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> changeDiscountStatus(@RequestBody final DiscountStatusDto discountStatusDto) {
        discountService.changeDiscountStatus(discountStatusDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Void> deleteDiscount(@RequestParam(value = "charSequenceCode") final String value) {

        discountService.deleteDiscount(value);
        return ResponseEntity.ok().build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping("/all")
    public List<DiscountDto> getDiscounts(@RequestHeader(name = "Accept-Language", defaultValue = "en") String location) {

        return discountService.getDiscounts();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DiscountDto> getDiscount(@RequestHeader(name = "Accept-Language", defaultValue = "en") String location,
                                                   @RequestParam(value = "charSequenceCode") final String charSequenceCode) {
        return new ResponseEntity<>(discountService.getDiscount(charSequenceCode), HttpStatus.OK);
    }
}
