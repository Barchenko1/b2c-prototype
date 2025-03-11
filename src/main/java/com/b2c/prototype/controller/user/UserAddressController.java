package com.b2c.prototype.controller.user;

import com.b2c.prototype.modal.dto.payload.AddressDto;
import com.b2c.prototype.modal.dto.payload.UserAddressDto;
import com.b2c.prototype.modal.dto.response.ResponseUserAddressDto;
import com.b2c.prototype.processor.address.IUserAddressProcess;
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
@RequestMapping("/api/v1/address")
public class UserAddressController {
    private final IUserAddressProcess userAddressProcess;

    public UserAddressController(IUserAddressProcess userAddressProcess) {
        this.userAddressProcess = userAddressProcess;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveUpdateUserAddressByUserId(@RequestParam final Map<String, String> requestParams,
                                                              @RequestBody final UserAddressDto userAddressDto) {
        userAddressProcess.saveUpdateUserAddressByUserId(requestParams, userAddressDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Void> setDefaultAddress(@RequestParam final Map<String, String> requestParams) {
        userAddressProcess.setDefaultAddress(requestParams);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Void> deleteUserAddress(@RequestParam final Map<String, String> requestParams) {
        userAddressProcess.deleteUserAddress(requestParams);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AddressDto> getAllAddressesByAddress(@RequestParam final Map<String, String> requestParams) {
        return userAddressProcess.getAllAddressesByAddress(requestParams);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponseUserAddressDto> getUserAddressListByUserId(@RequestParam final Map<String, String> requestParams) {
        return userAddressProcess.getUserAddressListByUserId(requestParams);
    }

    @GetMapping(value = "/default", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUserAddressDto getDefaultUserAddress(@RequestParam final Map<String, String> requestParams) {
        return userAddressProcess.getDefaultUserAddress(requestParams);
    }
}
