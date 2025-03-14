package com.b2c.prototype.controller.store;

import com.b2c.prototype.modal.dto.payload.AddressDto;
import com.b2c.prototype.processor.store.IStoreAddressProcess;
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

import java.util.Map;

@RestController
@RequestMapping("/api/v1/store/address")
public class StoreAddressController {

    private final IStoreAddressProcess storeAddressProcess;

    public StoreAddressController(IStoreAddressProcess storeAddressProcess) {
        this.storeAddressProcess = storeAddressProcess;
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateStoreAddress(@RequestParam final Map<String, String> requestParams,
                                                   @RequestBody final AddressDto addressDto) {
        storeAddressProcess.saveUpdateStoreAddress(requestParams, addressDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AddressDto> getStoreAddress(@RequestParam final Map<String, String> requestParams) {
        return new ResponseEntity<>(storeAddressProcess.getResponseStoreAddress(requestParams), HttpStatus.OK);
    }

}
