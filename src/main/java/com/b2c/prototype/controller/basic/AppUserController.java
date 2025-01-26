package com.b2c.prototype.controller.basic;

import com.b2c.prototype.service.manager.userprofile.IUserProfileManager;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AppUserController {

    private final IUserProfileManager userProfileService;

    public AppUserController(IUserProfileManager userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUser() {

        return ResponseEntity.ok(200);
    }
}
