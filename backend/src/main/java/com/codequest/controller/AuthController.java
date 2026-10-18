package com.codequest.controller;

import com.codequest.dto.AuthDto;
import com.codequest.service.AdminSetupService;
import com.codequest.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AdminSetupService adminSetupService;

    @PostMapping("/register")
    public ResponseEntity<AuthDto.AuthResponse> register(@RequestBody AuthDto.RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthDto.AuthResponse> login(@RequestBody AuthDto.LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/admin-setup/status")
    public ResponseEntity<?> getAdminSetupStatus() {
        boolean available = adminSetupService.isSetupAvailable();
        boolean resetAvailable = adminSetupService.isResetAvailable();
        return ResponseEntity.ok(Map.of(
            "setupAvailable", available,
            "resetAvailable", resetAvailable,
            "message", available ? "Administrator setup is available." : "Administrator account exists. Use admin recovery if locked out."
        ));
    }

    @PostMapping("/admin-setup")
    public ResponseEntity<AuthDto.AuthResponse> setupAdmin(@RequestBody AuthDto.AdminSetupRequest request) {
        return ResponseEntity.ok(authService.setupFirstAdmin(request));
    }

    @PostMapping("/admin-reset")
    public ResponseEntity<AuthDto.AuthResponse> resetAdmin(@RequestBody AuthDto.AdminResetRequest request) {
        return ResponseEntity.ok(authService.resetAdmin(request));
    }
}
