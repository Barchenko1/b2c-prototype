package com.b2c.prototype.controller.basic;

import com.b2c.prototype.modal.dto.payload.AddressDto;
import com.b2c.prototype.modal.dto.payload.ContactInfoDto;
import com.b2c.prototype.modal.dto.response.ResponseUserAddressDto;
import com.b2c.prototype.processor.address.IAddressProcess;
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
@RequestMapping("/api/v1/address")
public class AddressController {
    private final IAddressProcess addressProcess;

    public AddressController(IAddressProcess addressProcess) {
        this.addressProcess = addressProcess;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveUpdateAddress(@RequestParam final Map<String, String> requestParams,
                                                @RequestBody final AddressDto addressDto) {
        addressProcess.saveUpdateAddress(requestParams, addressDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Void> deleteAddress(@RequestParam final Map<String, String> requestParams) {
        addressProcess.deleteAddress(requestParams);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AddressDto> getAddresses(@RequestParam final Map<String, String> requestParams) {
        return addressProcess.getAddresses(requestParams);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AddressDto> getAddressByOrderId(@RequestParam final Map<String, String> requestParams) {
        return new ResponseEntity<>(addressProcess.getAddressByOrderId(requestParams), HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponseUserAddressDto> getAddressesByUserId(@RequestParam final Map<String, String> requestParams) {
        return addressProcess.getAddressesByUserId(requestParams);
    }
}
