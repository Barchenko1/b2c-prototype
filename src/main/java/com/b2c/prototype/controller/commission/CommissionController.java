package com.b2c.prototype.controller.commission;

import com.b2c.prototype.modal.dto.payload.commission.MinMaxCommissionDto;
import com.b2c.prototype.modal.dto.payload.commission.ResponseBuyerCommissionInfoDto;
import com.b2c.prototype.modal.dto.payload.commission.ResponseMinMaxCommissionDto;
import com.b2c.prototype.modal.dto.payload.order.ArticularItemQuantityDto;
import com.b2c.prototype.processor.commission.ICommissionProcess;
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
@RequestMapping("/api/v1/commission")
public class CommissionController {
    private final ICommissionProcess commissionProcess;

    public CommissionController(ICommissionProcess commissionProcess) {
        this.commissionProcess = commissionProcess;
    }

    @PostMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Void> saveLastCommission(@RequestParam final Map<String, String> requestParams,
                                                   @RequestBody final MinMaxCommissionDto minMaxCommissionDto) {
        commissionProcess.saveCommission(requestParams, minMaxCommissionDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Void> updateCommission(@RequestParam final Map<String, String> requestParams,
                                                 @RequestBody final MinMaxCommissionDto minMaxCommissionDto) {
        commissionProcess.updateCommission(minMaxCommissionDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Void> deleteCommission(@RequestParam final Map<String, String> requestParams) {
        commissionProcess.deleteCommission(requestParams);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponseMinMaxCommissionDto> getCommissions(@RequestParam final Map<String, String> requestParams) {
        return commissionProcess.getCommissions(requestParams);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMinMaxCommissionDto> getCommission(@RequestParam final Map<String, String> requestParams) {
        return new ResponseEntity<>(commissionProcess.getCommission(requestParams), HttpStatus.OK);
    }

    // client

    @PostMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<ResponseBuyerCommissionInfoDto> getBuyerCommission(@RequestParam final Map<String, String> requestParams,
                                                                             @RequestBody final List<ArticularItemQuantityDto> articularItemQuantityList) {
        return new ResponseEntity<>(commissionProcess.getBuyerCommission(requestParams, articularItemQuantityList), HttpStatus.OK);
    }
}
