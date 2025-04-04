package com.b2c.prototype.controller.user;

import com.b2c.prototype.modal.dto.payload.user.UserCreditCardDto;
import com.b2c.prototype.modal.dto.payload.order.ResponseCreditCardDto;
import com.b2c.prototype.modal.dto.payload.user.ResponseUserCreditCardDto;
import com.b2c.prototype.processor.creditcard.IUserCreditCardProcess;
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
@RequestMapping("/api/v1/usercreditcard")
public class UserCreditCardController {
    private final IUserCreditCardProcess userCreditCardProcess;

    public UserCreditCardController(IUserCreditCardProcess userCreditCardProcess) {
        this.userCreditCardProcess = userCreditCardProcess;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveUpdateUserCreditCard(@RequestParam final Map<String, String> requestParams,
                                                         @RequestBody final UserCreditCardDto userCreditCardDto) {
        userCreditCardProcess.saveUpdateUserCreditCardByUserId(requestParams, userCreditCardDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Void> setDefaultUserCreditCard(@RequestParam final Map<String, String> requestParams) {
        userCreditCardProcess.setDefaultUserCreditCard(requestParams);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Void> deleteUserCreditCard(@RequestParam final Map<String, String> requestParams) {
        userCreditCardProcess.deleteUserCreditCard(requestParams);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponseCreditCardDto> getAddresses(@RequestParam final Map<String, String> requestParams) {
        return userCreditCardProcess.getAllCreditCardByCardNumber(requestParams);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponseUserCreditCardDto> getCreditCardListByUserId(@RequestParam final Map<String, String> requestParams) {
        return userCreditCardProcess.getUserCreditCardListByUserId(requestParams);
    }

    @GetMapping(value = "/default", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUserCreditCardDto getDefaultUserCreditCard(@RequestParam final Map<String, String> requestParams) {
        return userCreditCardProcess.getDefaultUserCreditCard(requestParams);
    }
}
