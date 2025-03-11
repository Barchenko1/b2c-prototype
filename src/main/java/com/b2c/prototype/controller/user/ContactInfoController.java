package com.b2c.prototype.controller.user;

import com.b2c.prototype.modal.dto.payload.ContactInfoDto;
import com.b2c.prototype.processor.user.IContactInfoProcess;
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

import java.util.Map;

@RestController
@RequestMapping("/api/v1/contactInfo")
public class ContactInfoController {
    private final IContactInfoProcess contactInfoProcess;

    public ContactInfoController(IContactInfoProcess contactInfoProcess) {
        this.contactInfoProcess = contactInfoProcess;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveUserDetails(@RequestParam final Map<String, String> requestParams,
                                                @RequestBody final ContactInfoDto contactInfoDto) {
        contactInfoProcess.saveUpdateContactInfo(requestParams, contactInfoDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Void> deleteContactInfo(@RequestParam final Map<String, String> requestParams) {
        contactInfoProcess.deleteContactInfo(requestParams);
        return ResponseEntity.ok().build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ContactInfoDto> getContactInfo(@RequestParam final Map<String, String> requestParams) {
        return new ResponseEntity<>(contactInfoProcess.getResponseContactInfoByUserId(requestParams), HttpStatus.OK);
    }
}
