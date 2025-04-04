package com.b2c.prototype.controller.commission;

import com.b2c.prototype.modal.dto.payload.commission.CommissionDto;
import com.b2c.prototype.modal.dto.payload.order.ResponseCommissionDto;
import com.b2c.prototype.processor.commission.IBuyerCommissionProcess;
import com.b2c.prototype.processor.commission.ISellerCommissionProcess;
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
@RequestMapping("/api/v1/commission")
public class CommissionController {
    private final IBuyerCommissionProcess buyerCommissionProcess;
    private final ISellerCommissionProcess sellerCommissionProcess;

    public CommissionController(IBuyerCommissionProcess buyerCommissionProcess, ISellerCommissionProcess sellerCommissionProcess) {
        this.buyerCommissionProcess = buyerCommissionProcess;
        this.sellerCommissionProcess = sellerCommissionProcess;
    }

    @PostMapping(value = "/buyer", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Void> putBuyerCommission(@RequestParam final Map<String, String> requestParams,
                                                   @RequestBody final CommissionDto commissionDto) {
        buyerCommissionProcess.saveLastCommission(requestParams, commissionDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/buyer", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Void> deleteBuyerCommission(@RequestParam final Map<String, String> requestParams) {
        buyerCommissionProcess.deleteCommission(requestParams);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/buyer/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponseCommissionDto> getBuyerCommissions(@RequestParam final Map<String, String> requestParams) {
        return buyerCommissionProcess.getCommissions(requestParams);
    }

    @GetMapping(value = "/buyer", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseCommissionDto> getBuyerCommission(@RequestParam final Map<String, String> requestParams) {
        return new ResponseEntity<>(buyerCommissionProcess.getCommission(requestParams), HttpStatus.OK);
    }

    @PostMapping(value = "/seller", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Void> putSellerCommission(@RequestParam final Map<String, String> requestParams,
                                                    @RequestBody final CommissionDto commissionDto) {
        sellerCommissionProcess.saveLastCommission(requestParams, commissionDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/seller", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Void> deleteSellerCommission(@RequestParam final Map<String, String> requestParams) {
        sellerCommissionProcess.deleteCommission(requestParams);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/seller/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponseCommissionDto> getSellerCommissions(@RequestParam final Map<String, String> requestParams) {
        return sellerCommissionProcess.getCommissions(requestParams);
    }

    @GetMapping(value = "/seller", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseCommissionDto> getSellerCommission(@RequestParam final Map<String, String> requestParams) {
        return new ResponseEntity<>(sellerCommissionProcess.getCommission(requestParams), HttpStatus.OK);
    }
}
