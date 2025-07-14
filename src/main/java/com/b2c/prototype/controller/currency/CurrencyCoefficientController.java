package com.b2c.prototype.controller.currency;

import com.b2c.prototype.modal.dto.payload.order.CurrencyConvertDateDto;
import com.b2c.prototype.modal.dto.payload.order.CurrencyConvertDto;
import com.b2c.prototype.modal.dto.payload.order.ResponseCurrencyCoefficientDto;
import com.b2c.prototype.processor.payment.ICurrencyCoefficientProcessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController(value = "/api/v1/currency/coefficient")
public class CurrencyCoefficientController {
    private final ICurrencyCoefficientProcessor currencyCoefficientProcessor;

    public CurrencyCoefficientController(ICurrencyCoefficientProcessor currencyCoefficientProcessor) {
        this.currencyCoefficientProcessor = currencyCoefficientProcessor;
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveUpdateStoreAddress(@RequestParam final Map<String, String> requestParams,
                                                       @RequestBody final List<CurrencyConvertDto> currencyConvertDtoList) {
        currencyCoefficientProcessor.saveCurrencyCoefficient(requestParams, currencyConvertDtoList);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteCurrencyCoefficient(@RequestParam final Map<String, String> requestParams,
                                                          @RequestBody final List<CurrencyConvertDateDto> currencyConvertDateDtoList) {
        currencyCoefficientProcessor.deleteCurrencyCoefficient(requestParams, currencyConvertDateDtoList);
        return ResponseEntity.ok().build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/all")
    public ResponseEntity<ResponseCurrencyCoefficientDto> getResponseCurrencyCoefficient(@RequestParam final Map<String, String> requestParams) {
        return new ResponseEntity<>(currencyCoefficientProcessor.getResponseCurrencyCoefficient(requestParams), HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponseCurrencyCoefficientDto> getResponseCurrencyCoefficientList(@RequestParam final Map<String, String> requestParams) {
        return currencyCoefficientProcessor.getResponseCurrencyCoefficientList(requestParams);
    }

}
