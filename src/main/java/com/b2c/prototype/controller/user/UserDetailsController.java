package com.b2c.prototype.controller.user;

import com.b2c.prototype.modal.dto.payload.user.RegistrationUserDetailsDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsDto;
import com.b2c.prototype.modal.dto.response.ResponseUserDetailsDto;
import com.b2c.prototype.processor.user.IUserDetailsProcess;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
public class UserDetailsController {
    private final IUserDetailsProcess userDetailsProcess;

    public UserDetailsController(IUserDetailsProcess userDetailsProcess) {
        this.userDetailsProcess = userDetailsProcess;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveUserDetails(@RequestParam final Map<String, String> requestParams,
                                                @RequestBody final RegistrationUserDetailsDto registrationUserDetailsDto) {
        userDetailsProcess.createNewUser(requestParams, registrationUserDetailsDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/full", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveUserDetails(@RequestParam final Map<String, String> requestParams,
                                                @RequestBody final UserDetailsDto userDetailsDto) {
        userDetailsProcess.saveUserDetails(requestParams, userDetailsDto);
        return ResponseEntity.ok().build();
    }


    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> putIUserDetails(@RequestParam final Map<String, String> requestParams,
                                                @RequestBody final UserDetailsDto userDetailsDto) {
        userDetailsProcess.updateUserDetailsByUserId(requestParams, userDetailsDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> patchUserDetails(@RequestParam final Map<String, String> requestParams,
                                                 @RequestBody final UserDetailsDto userDetailsDto) {
        userDetailsProcess.updateUserDetailsByUserId(requestParams, userDetailsDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> patchUserDetailsStatus(@RequestParam final Map<String, String> requestParams) {
        userDetailsProcess.updateUserStatusByUserId(requestParams);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/verifyEmail", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> patchUserDetailsEmail(@RequestParam final Map<String, String> requestParams) {
        userDetailsProcess.updateUserVerifyEmailByUserId(requestParams);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/verifyPhone", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> patchUserDetailsPhone(@RequestParam final Map<String, String> requestParams) {
        userDetailsProcess.updateUserVerifyPhoneByUserId(requestParams);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Void> deleteUserDetails(@RequestParam final Map<String, String> requestParams) {
        userDetailsProcess.deleteUserDetailsByUserId(requestParams);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponseUserDetailsDto> getUserDetailsList(@RequestParam final Map<String, String> requestParams) {
        return userDetailsProcess.getUserDetails(requestParams);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseUserDetailsDto> getUserDetails(@RequestParam final Map<String, String> requestParams) {
        return new ResponseEntity<>(userDetailsProcess.getUserDetailsByUserId(requestParams), HttpStatus.OK);
    }
}
