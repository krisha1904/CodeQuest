package com.codequest.service;

import com.codequest.dto.AuthDto;
import com.codequest.entity.Role;
import com.codequest.entity.User;
import com.codequest.exception.BadRequestException;
import com.codequest.repository.UserRepository;
import com.codequest.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AdminSetupService adminSetupService;

    @Transactional
    public AuthDto.AuthResponse register(AuthDto.RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Username is already taken.");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email is already registered.");
        }

        User user = new User(
                request.getUsername(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getFullName(),
                request.getCollegeName(),
                Role.ROLE_STUDENT
        );

        userRepository.save(user);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);

        return new AuthDto.AuthResponse(
                jwt,
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFullName(),
                user.getCollegeName(),
                user.getRole().name(),
                user.getCurrentStreak(),
                user.getLongestStreak(),
                user.getTotalPoints(),
                user.getLevelTitle()
        );
    }

    public AuthDto.AuthResponse login(AuthDto.LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);
        User user = userRepository.findByUsername(request.getUsername())
                .or(() -> userRepository.findByEmail(request.getUsername()))
                .orElseThrow(() -> new BadRequestException("User not found"));

        return new AuthDto.AuthResponse(
                jwt,
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFullName(),
                user.getCollegeName(),
                user.getRole().name(),
                user.getCurrentStreak(),
                user.getLongestStreak(),
                user.getTotalPoints(),
                user.getLevelTitle()
        );
    }

    @Transactional
    public AuthDto.AuthResponse setupFirstAdmin(AuthDto.AdminSetupRequest request) {
        if (!adminSetupService.isSetupAvailable()) {
            throw new BadRequestException("Administrator setup is already completed and permanently disabled.");
        }
        if (!adminSetupService.validateToken(request.getSetupToken())) {
            throw new BadRequestException("Invalid administrator setup token.");
        }
        if (request.getUsername() == null || request.getUsername().trim().length() < 3) {
            throw new BadRequestException("Username must be at least 3 characters.");
        }
        if (request.getEmail() == null || !request.getEmail().contains("@")) {
            throw new BadRequestException("A valid email address is required.");
        }
        if (request.getPassword() == null || request.getPassword().length() < 6) {
            throw new BadRequestException("Password must be at least 6 characters.");
        }
        if (userRepository.existsByUsername(request.getUsername().trim())) {
            throw new BadRequestException("Username is already taken.");
        }
        if (userRepository.existsByEmail(request.getEmail().trim())) {
            throw new BadRequestException("Email is already registered.");
        }

        User admin = new User(
                request.getUsername().trim(),
                request.getEmail().trim(),
                passwordEncoder.encode(request.getPassword()),
                request.getFullName() != null && !request.getFullName().isBlank() ? request.getFullName().trim() : "Administrator",
                request.getCollegeName() != null && !request.getCollegeName().isBlank() ? request.getCollegeName().trim() : "CodeQuest",
                Role.ROLE_ADMIN
        );
        userRepository.save(admin);

        adminSetupService.completeSetup();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername().trim(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);

        return new AuthDto.AuthResponse(
                jwt,
                admin.getId(),
                admin.getUsername(),
                admin.getEmail(),
                admin.getFullName(),
                admin.getCollegeName(),
                admin.getRole().name(),
                admin.getCurrentStreak(),
                admin.getLongestStreak(),
                admin.getTotalPoints(),
                admin.getLevelTitle()
        );
    }
}
