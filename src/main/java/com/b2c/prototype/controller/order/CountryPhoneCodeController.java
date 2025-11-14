package com.b2c.prototype.controller.order;

import com.b2c.prototype.processor.item.ICountryPhoneCodeProcess;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user/phone/country-code")
public class CountryPhoneCodeController {
    private final ICountryPhoneCodeProcess countryPhoneCodeProcess;

    public CountryPhoneCodeController(ICountryPhoneCodeProcess countryPhoneCodeProcess) {
        this.countryPhoneCodeProcess = countryPhoneCodeProcess;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveConstantEntity(@RequestBody Map<String, Object> payload) {
        countryPhoneCodeProcess.persistEntity(payload);
        return ResponseEntity.ok().build();
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> putConstantEntity(@RequestBody Map<String, Object> payload,
                                                    @RequestParam(value = "key") final String key) {
        countryPhoneCodeProcess.mergeEntity(payload, key);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> deleteConstantEntity(@RequestParam(value = "key") final String key) {
        countryPhoneCodeProcess.removeEntity(key);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getConstantEntities(@RequestHeader(name = "Accept-Language", defaultValue = "en") String location) {
        return ResponseEntity.ok(countryPhoneCodeProcess.getEntityList(location));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getConstantEntity(@RequestHeader(name = "Accept-Language", defaultValue = "en") String location,
                                               @RequestParam(value = "key") final String key) {
        return ResponseEntity.ok(countryPhoneCodeProcess.getEntity(location, key));
    }
}
