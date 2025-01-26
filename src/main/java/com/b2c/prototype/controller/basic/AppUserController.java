package com.b2c.prototype.controller.basic;

import com.b2c.prototype.service.processor.userprofile.IUserProfileService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AppUserController {

    private final IUserProfileService userProfileService;

    public AppUserController(IUserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUser() {

        return ResponseEntity.ok(200);
    }
}
