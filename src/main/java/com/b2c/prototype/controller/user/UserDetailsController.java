package com.b2c.prototype.controller.user;

import com.b2c.prototype.modal.dto.payload.user.RegistrationUserDetailsDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsAddCollectionDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsContactInfoDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsRemoveCollectionDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsStatusDto;
import com.b2c.prototype.processor.user.IUserDetailsProcess;
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
@RequestMapping("/api/v1/user/details")
public class UserDetailsController {
    private final IUserDetailsProcess userDetailsProcess;

    public UserDetailsController(IUserDetailsProcess userDetailsProcess) {
        this.userDetailsProcess = userDetailsProcess;
    }

    @PostMapping(value = "/new",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveUserDetails(@RequestParam final Map<String, String> requestParams,
                                                @RequestBody final RegistrationUserDetailsDto registrationUserDetailsDto) {
        userDetailsProcess.createNewUser(requestParams, registrationUserDetailsDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> postUserDetails(@RequestParam final Map<String, String> requestParams,
                                                @RequestBody final UserDetailsDto userDetailsDto) {
        userDetailsProcess.updateUserDetailsByUserId(requestParams, userDetailsDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> postUserDetails(@RequestBody final UserDetailsContactInfoDto userDetailsContactInfoDto) {
        userDetailsProcess.updateUserDetailsContactInfo(userDetailsContactInfoDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> postUserDetailsStatus(@RequestBody final UserDetailsStatusDto userDetailsStatusDto) {
        userDetailsProcess.updateUserDetailsStatus(userDetailsStatusDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/verifyEmail", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> postUserDetailsEmail(@RequestBody final UserDetailsStatusDto userDetailsStatusDto) {
        userDetailsProcess.updateUserDetailsVerifyEmail(userDetailsStatusDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/verifyPhone", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> postUserDetailsPhone(@RequestBody final UserDetailsStatusDto userDetailsStatusDto) {
        userDetailsProcess.updateUserDetailsVerifyPhone(userDetailsStatusDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Void> deleteUserDetails(@RequestParam final Map<String, String> requestParams) {
        userDetailsProcess.deleteUserDetailsByUserId(requestParams);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/add/address", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addUserDetailsAddress(@RequestBody final UserDetailsAddCollectionDto userDetailsAddCollectionDto) {
        userDetailsProcess.addUserDetailsAddress(userDetailsAddCollectionDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/add/creditcard", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addUserDetailsCreditCard(@RequestBody final UserDetailsAddCollectionDto userDetailsAddCollectionDto) {
        userDetailsProcess.addUserDetailsCreditCard(userDetailsAddCollectionDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/add/device", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addUserDetailsDevice(@RequestBody final UserDetailsAddCollectionDto userDetailsAddCollectionDto) {
        userDetailsProcess.addUserDetailsDevice(userDetailsAddCollectionDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/remove/address", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteUserDetailsAddress(@RequestBody final UserDetailsRemoveCollectionDto userDetailsAddCollectionDto) {
        userDetailsProcess.deleteUserDetailsAddress(userDetailsAddCollectionDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/remove/creditcard", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteUserDetailsCreditCard(@RequestBody final UserDetailsRemoveCollectionDto userDetailsAddCollectionDto) {
        userDetailsProcess.deleteUserDetailsCreditCard(userDetailsAddCollectionDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDetailsDto> getUserDetailsList(@RequestParam final Map<String, String> requestParams) {
        return userDetailsProcess.getUserDetails(requestParams);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDetailsDto> getUserDetails(@RequestParam final Map<String, String> requestParams) {
        return new ResponseEntity<>(userDetailsProcess.getUserDetailsByUserId(requestParams), HttpStatus.OK);
    }
}
