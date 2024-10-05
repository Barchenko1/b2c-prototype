package com.b2c.prototype.controller;

import com.b2c.prototype.service.base.appuser.IAppUserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AppUserController {

    private final IAppUserService userService;

    public AppUserController(IAppUserService userService) {
        this.userService = userService;
    }


    @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUser() {

        return ResponseEntity.ok(200);
    }
}
