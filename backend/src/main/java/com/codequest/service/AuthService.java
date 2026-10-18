package com.codequest.service;

import com.codequest.dto.AuthDto;
import com.codequest.entity.Role;
import com.codequest.entity.StudentActivity;
import com.codequest.entity.User;
import com.codequest.exception.BadRequestException;
import com.codequest.repository.StudentActivityRepository;
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

import java.time.LocalDateTime;

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

    @Autowired
    private StudentActivityRepository studentActivityRepository;

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
        user.setLastLoginAt(LocalDateTime.now());
        user.setLoginCount(1);
        userRepository.save(user);

        try {
            studentActivityRepository.save(new StudentActivity(
                    user,
                    "USER_REGISTRATION",
                    "New student account '" + user.getUsername() + "' registered (" + (user.getCollegeName() != null ? user.getCollegeName() : "University") + ")"
            ));
        } catch (Exception ignored) {}

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

    @Transactional
    public AuthDto.AuthResponse login(AuthDto.LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);
        User user = userRepository.findByUsername(request.getUsername())
                .or(() -> userRepository.findByEmail(request.getUsername()))
                .orElseThrow(() -> new BadRequestException("User not found"));

        // Record live user login
        user.setLastLoginAt(LocalDateTime.now());
        user.incrementLoginCount();
        userRepository.save(user);

        try {
            studentActivityRepository.save(new StudentActivity(
                    user,
                    "USER_LOGIN",
                    "User '" + user.getUsername() + "' (" + (user.getRole() == Role.ROLE_ADMIN ? "Administrator" : "Student") + ") logged in from web client"
            ));
        } catch (Exception ignored) {}

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
            throw new BadRequestException("Administrator setup is already completed. Use admin recovery if needed.");
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
        admin.setLastLoginAt(LocalDateTime.now());
        admin.setLoginCount(1);
        userRepository.save(admin);

        adminSetupService.completeSetup();

        try {
            studentActivityRepository.save(new StudentActivity(
                    admin,
                    "ADMIN_SETUP",
                    "Primary administrator account '" + admin.getUsername() + "' initialized"
            ));
        } catch (Exception ignored) {}

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

    @Transactional
    public AuthDto.AuthResponse resetAdmin(AuthDto.AdminResetRequest request) {
        if (request.getSetupToken() == null || request.getSetupToken().isBlank()) {
            throw new BadRequestException("Setup/Recovery token is required.");
        }
        if (request.getNewPassword() == null || request.getNewPassword().length() < 6) {
            throw new BadRequestException("New password must be at least 6 characters.");
        }

        String username = (request.getUsername() != null && !request.getUsername().isBlank()) ? request.getUsername().trim() : "Krisha_1904";
        User admin = adminSetupService.resetAdminPassword(
                request.getSetupToken(),
                username,
                request.getNewPassword(),
                request.getEmail(),
                request.getFullName()
        );

        admin.setLastLoginAt(LocalDateTime.now());
        admin.incrementLoginCount();
        userRepository.save(admin);

        try {
            studentActivityRepository.save(new StudentActivity(
                    admin,
                    "ADMIN_RESET",
                    "Administrator account '" + admin.getUsername() + "' recovered and password reset"
            ));
        } catch (Exception ignored) {}

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(admin.getUsername(), request.getNewPassword())
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
