package org.example.blogproject.controller;

import org.example.blogproject.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/check-username")
    public ResponseEntity<?> checkUsername(@RequestParam String username) {
        boolean exists = userService.findByUsername(username).isPresent();
        return ResponseEntity.ok().body(Collections.singletonMap("exists", exists));
    }

    @GetMapping("/users/check-email")
    public ResponseEntity<?> checkEmail(@RequestParam String email) {
        boolean exists = userService.findByEmail(email).isPresent();
        return ResponseEntity.ok().body(Collections.singletonMap("exists", exists));
    }
}
