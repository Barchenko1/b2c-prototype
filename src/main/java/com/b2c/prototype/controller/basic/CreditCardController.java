package com.b2c.prototype.controller.basic;

import com.b2c.prototype.modal.dto.payload.CreditCardDto;
import com.b2c.prototype.modal.dto.response.ResponseCreditCardDto;
import com.b2c.prototype.modal.dto.response.ResponseUserCreditCardDto;
import com.b2c.prototype.processor.creditcard.ICreditCardProcess;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/creditcard")
public class CreditCardController {
    private final ICreditCardProcess creditCardProcess;

    public CreditCardController(ICreditCardProcess creditCardProcess) {
        this.creditCardProcess = creditCardProcess;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveUpdateAddress(@RequestParam final Map<String, String> requestParams,
                                                  @RequestBody final CreditCardDto creditCardDto) {
        creditCardProcess.saveUpdateCreditCard(requestParams, creditCardDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Void> deleteAddress(@RequestParam final Map<String, String> requestParams) {
        creditCardProcess.deleteCreditCard(requestParams);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponseCreditCardDto> getAddresses(@RequestParam final Map<String, String> requestParams) {
        return creditCardProcess.getAllCreditCards(requestParams);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseCreditCardDto> getAddressByOrderId(@RequestParam final Map<String, String> requestParams) {
        return new ResponseEntity<>(creditCardProcess.getCreditCardByOrderId(requestParams), HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponseUserCreditCardDto> getAddressesByUserId(@RequestParam final Map<String, String> requestParams) {
        return creditCardProcess.getCreditCardListByUserId(requestParams);
    }
}
