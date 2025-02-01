package net.happytodo.core.security.controller;

import lombok.RequiredArgsConstructor;
import net.happytodo.core.security.dto.User;
import net.happytodo.core.security.service.SecurityService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/security")
@RequiredArgsConstructor
public class SecurityController {
    private final SecurityService securityService;
    @GetMapping("/login-user")
    public ResponseEntity<User.UserResponse> getLoginUser() {
        return ResponseEntity.ok(securityService.getLoginUser()
                .map(User.UserAccount::toResponse).orElse(null));
    }
}
