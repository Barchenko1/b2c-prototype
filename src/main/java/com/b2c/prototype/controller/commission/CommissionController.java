package com.b2c.prototype.controller.commission;

import com.b2c.prototype.modal.dto.payload.CommissionDto;
import com.b2c.prototype.modal.dto.response.ResponseDeviceDto;
import com.b2c.prototype.processor.commission.IBuyerCommissionProcess;
import com.b2c.prototype.processor.commission.ISellerCommissionProcess;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
    private final IBuyerCommissionProcess buyerCommissionProcess;
    private final ISellerCommissionProcess sellerCommissionProcess;

    public CommissionController(IBuyerCommissionProcess buyerCommissionProcess, ISellerCommissionProcess sellerCommissionProcess) {
        this.buyerCommissionProcess = buyerCommissionProcess;
        this.sellerCommissionProcess = sellerCommissionProcess;
    }

    @PutMapping(value = "/buyer", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Void> putBuyerCommission(@RequestParam final Map<String, String> requestParams,
                                                   @RequestBody final CommissionDto commissionDto) {
        buyerCommissionProcess.putCommission(requestParams, commissionDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/buyer", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Void> deleteBuyerCommission(@RequestParam final Map<String, String> requestParams) {
        buyerCommissionProcess.deleteCommission(requestParams);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/buyer", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponseDeviceDto> getBuyerCommissions(@RequestParam final Map<String, String> requestParams) {
        return buyerCommissionProcess.getCommissions(requestParams);
    }

    @PutMapping(value = "/seller", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Void> putSellerCommission(@RequestParam final Map<String, String> requestParams,
                                                    @RequestBody final CommissionDto commissionDto) {
        sellerCommissionProcess.putCommission(requestParams, commissionDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/seller", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Void> deleteSellerCommission(@RequestParam final Map<String, String> requestParams) {
        sellerCommissionProcess.deleteCommission(requestParams);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/seller", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponseDeviceDto> getSellerCommissions(@RequestParam final Map<String, String> requestParams) {
        return sellerCommissionProcess.getCommissions(requestParams);
    }
}
