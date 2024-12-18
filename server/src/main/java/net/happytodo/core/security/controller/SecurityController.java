package net.happytodo.core.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/security")
public class SecurityController {
    @GetMapping("/login-user")
    public ResponseEntity<Authentication> getLoginUser() {
        return ResponseEntity.ok(SecurityContextHolder.getContext()
                .getAuthentication());
    }
}
