package com.b2c.prototype.controller.basic;

import com.b2c.prototype.modal.dto.payload.DiscountDto;
import com.b2c.prototype.modal.dto.payload.DiscountStatusDto;
import com.b2c.prototype.processor.discount.IDiscountProcess;
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
    private final IDiscountProcess discountProcess;

    public DiscountController(IDiscountProcess discountProcess) {
        this.discountProcess = discountProcess;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveDiscount(@RequestBody final DiscountDto discountDto) {
        discountProcess.saveDiscount(discountDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/articular", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> putDiscountByArticularId(@RequestBody final DiscountDto discountDto,
                                                         @RequestParam(value = "articularId") final String articularId) {
        discountProcess.updateItemDataDiscount(articularId, discountDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/sequence", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> putDiscountByCharSequenceCode(@RequestBody final DiscountDto discountDto,
                                                              @RequestParam(value = "charSequenceCode") final String charSequenceCode) {
        discountProcess.updateDiscount(charSequenceCode, discountDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/articular", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> patchDiscountByArticularId(@RequestBody final DiscountDto discountDto,
                                                           @RequestParam(value = "articularId") final String articularId) {
        discountProcess.updateItemDataDiscount(articularId, discountDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/sequence", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> patchDiscountByCharSequenceCode(@RequestBody final DiscountDto discountDto,
                                                                @RequestParam(value = "charSequenceCode") final String charSequenceCode) {
        discountProcess.updateDiscount(charSequenceCode, discountDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> changeDiscountStatus(@RequestBody final DiscountStatusDto discountStatusDto) {
        discountProcess.changeDiscountStatus(discountStatusDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Void> deleteDiscount(@RequestParam(value = "charSequenceCode") final String charSequenceCode) {

        discountProcess.deleteDiscount(charSequenceCode);
        return ResponseEntity.ok().build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping("/all")
    public List<DiscountDto> getDiscounts(@RequestHeader(name = "Accept-Language", defaultValue = "en") String location) {

        return discountProcess.getDiscounts();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DiscountDto> getDiscount(@RequestHeader(name = "Accept-Language", defaultValue = "en") String location,
                                                   @RequestParam(value = "charSequenceCode") final String charSequenceCode) {
        return new ResponseEntity<>(discountProcess.getDiscount(charSequenceCode), HttpStatus.OK);
    }
}
