package com.b2c.prototype.controller.order;

import com.b2c.prototype.modal.dto.payload.order.CurrencyConvertDateDto;
import com.b2c.prototype.processor.order.ICurrencyCoefficientProcessor;
import org.springframework.http.HttpStatus;
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

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/currency/coefficient")
public class CurrencyCoefficientController {
    private final ICurrencyCoefficientProcessor currencyCoefficientProcessor;

    public CurrencyCoefficientController(ICurrencyCoefficientProcessor currencyCoefficientProcessor) {
        this.currencyCoefficientProcessor = currencyCoefficientProcessor;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveCurrencyCoefficient(@RequestParam final Map<String, String> requestParams,
                                                        @RequestBody final CurrencyConvertDateDto currencyConvertDtoList) {
        currencyCoefficientProcessor.saveCurrencyCoefficient(requestParams, currencyConvertDtoList);
        return ResponseEntity.ok().build();
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateCurrencyCoefficient(@RequestParam final Map<String, String> requestParams,
                                                          @RequestBody final CurrencyConvertDateDto currencyConvertDtoList) {
        currencyCoefficientProcessor.updateCurrencyCoefficient(requestParams, currencyConvertDtoList);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Void> deleteCurrencyCoefficient(@RequestParam final Map<String, String> requestParams) {
        currencyCoefficientProcessor.deleteCurrencyCoefficient(requestParams);
        return ResponseEntity.ok().build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/all")
    public List<CurrencyConvertDateDto> getResponseCurrencyCoefficient(@RequestParam final Map<String, String> requestParams) {
        return currencyCoefficientProcessor.getResponseCurrencyCoefficientList(requestParams);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CurrencyConvertDateDto> getResponseCurrencyCoefficientList(@RequestParam final Map<String, String> requestParams) {
        return new ResponseEntity<>(currencyCoefficientProcessor.getResponseCurrencyCoefficient(requestParams), HttpStatus.OK);
    }

}
