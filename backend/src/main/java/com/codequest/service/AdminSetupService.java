package com.codequest.service;

import com.codequest.entity.Role;
import com.codequest.entity.StudentActivity;
import com.codequest.entity.User;
import com.codequest.exception.BadRequestException;
import com.codequest.repository.StudentActivityRepository;
import com.codequest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.HexFormat;
import java.util.Optional;

@Service
public class AdminSetupService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private StudentActivityRepository studentActivityRepository;

    private String activeSetupToken;
    private static final String TOKEN_FILE_NAME = "admin-setup-token.txt";

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        checkAndInitializeSetup();
    }

    public synchronized void checkAndInitializeSetup() {
        // 1. Check for explicit environment credentials to bootstrap admin
        String envUser = System.getenv("ADMIN_USERNAME");
        String envPass = System.getenv("ADMIN_PASSWORD");
        if (envUser != null && !envUser.isBlank() && envPass != null && !envPass.isBlank()) {
            bootstrapAdminUser(envUser.trim(), envPass.trim(), System.getenv("ADMIN_EMAIL"), System.getenv("ADMIN_FULL_NAME"));
        }

        // 2. Check environment setup token
        String envToken = System.getenv("ADMIN_SETUP_TOKEN");
        String sysPropToken = System.getProperty("admin.setup.token");
        if (envToken != null && !envToken.isBlank()) {
            activeSetupToken = envToken.trim();
        } else if (sysPropToken != null && !sysPropToken.isBlank()) {
            activeSetupToken = sysPropToken.trim();
        }

        long adminCount = userRepository.countByRole(Role.ROLE_ADMIN);
        if (adminCount == 0) {
            if (activeSetupToken == null) {
                byte[] bytes = new byte[16];
                new SecureRandom().nextBytes(bytes);
                activeSetupToken = HexFormat.of().formatHex(bytes);
            }

            writeTokenToFile(activeSetupToken);

            System.out.println("\n" + "=".repeat(80));
            System.out.println(" [CODEQUEST INITIAL SETUP] NO ADMINISTRATOR ACCOUNT EXISTS!");
            System.out.println(" A secure one-time setup token has been generated:");
            System.out.println(" SETUP TOKEN: " + activeSetupToken);
            System.out.println(" Visit /admin-setup.html or POST to /api/auth/admin-setup to initialize.");
            System.out.println(" Token also saved locally in: " + new File(TOKEN_FILE_NAME).getAbsolutePath());
            System.out.println("=".repeat(80) + "\n");
        } else {
            // Admin accounts exist. If no persistent ADMIN_SETUP_TOKEN was set via env/prop, clean up token file
            if (envToken == null && sysPropToken == null) {
                activeSetupToken = null;
                cleanUpTokenFile();
            } else {
                System.out.println(">>> [CODEQUEST ADMIN] Admin account exists. Environment setup/reset token is active for recovery.");
            }
        }
    }

    @Transactional
    public User bootstrapAdminUser(String username, String password, String email, String fullName) {
        String cleanEmail = (email != null && !email.isBlank()) ? email.trim() : username.toLowerCase() + "@codequest.com";
        String cleanFullName = (fullName != null && !fullName.isBlank()) ? fullName.trim() : "Administrator (" + username + ")";

        Optional<User> existing = userRepository.findByUsername(username)
                .or(() -> userRepository.findByEmail(cleanEmail));

        User admin;
        if (existing.isPresent()) {
            admin = existing.get();
            admin.setUsername(username);
            admin.setPasswordHash(passwordEncoder.encode(password));
            admin.setRole(Role.ROLE_ADMIN);
            admin.setFullName(cleanFullName);
            admin.setCollegeName("CodeQuest Administration");
            userRepository.save(admin);
            System.out.println(">>> [CODEQUEST ADMIN BOOTSTRAP] Admin user '" + username + "' password synchronized from environment credentials.");
        } else {
            admin = new User(
                    username,
                    cleanEmail,
                    passwordEncoder.encode(password),
                    cleanFullName,
                    "CodeQuest Administration",
                    Role.ROLE_ADMIN
            );
            userRepository.save(admin);
            System.out.println(">>> [CODEQUEST ADMIN BOOTSTRAP] Primary administrator account '" + username + "' created successfully from environment variables.");
        }

        try {
            studentActivityRepository.save(new StudentActivity(admin, "ADMIN_BOOTSTRAP", "Administrator account provisioned/synchronized via environment"));
        } catch (Exception ignored) {}

        return admin;
    }

    public boolean isSetupAvailable() {
        return userRepository.countByRole(Role.ROLE_ADMIN) == 0 && activeSetupToken != null;
    }

    public boolean isResetAvailable() {
        String envToken = System.getenv("ADMIN_SETUP_TOKEN");
        String sysPropToken = System.getProperty("admin.setup.token");
        return (envToken != null && !envToken.isBlank()) || (sysPropToken != null && !sysPropToken.isBlank()) || activeSetupToken != null;
    }

    public boolean validateToken(String token) {
        if (!isSetupAvailable() || token == null) {
            return false;
        }
        return activeSetupToken.equals(token.trim());
    }

    public boolean validateResetToken(String token) {
        if (token == null || token.isBlank()) return false;
        String trimmed = token.trim();

        String envToken = System.getenv("ADMIN_SETUP_TOKEN");
        if (envToken != null && envToken.trim().equals(trimmed)) return true;

        String sysPropToken = System.getProperty("admin.setup.token");
        if (sysPropToken != null && sysPropToken.trim().equals(trimmed)) return true;

        return activeSetupToken != null && activeSetupToken.equals(trimmed);
    }

    @Transactional
    public User resetAdminPassword(String token, String username, String newPassword, String email, String fullName) {
        if (!validateResetToken(token)) {
            throw new BadRequestException("Invalid administrator setup/reset token.");
        }
        if (newPassword == null || newPassword.length() < 6) {
            throw new BadRequestException("Password must be at least 6 characters.");
        }
        String cleanUser = (username != null && !username.isBlank()) ? username.trim() : "admin_roy";
        return bootstrapAdminUser(cleanUser, newPassword, email, fullName);
    }

    public synchronized void completeSetup() {
        String envToken = System.getenv("ADMIN_SETUP_TOKEN");
        String sysPropToken = System.getProperty("admin.setup.token");
        if (envToken == null && sysPropToken == null) {
            activeSetupToken = null;
            cleanUpTokenFile();
        }
        System.out.println(">>> [CODEQUEST INITIAL SETUP] Primary Administrator account created successfully.");
        System.out.println(">>> [CODEQUEST INITIAL SETUP] Administrator setup mechanism is now permanently disabled.");
    }

    private void writeTokenToFile(String token) {
        try (FileWriter writer = new FileWriter(TOKEN_FILE_NAME)) {
            writer.write("====================================================================\n");
            writer.write(" CodeQuest: Initial Administrator One-Time Setup Token\n");
            writer.write("====================================================================\n\n");
            writer.write("SETUP_TOKEN=" + token + "\n\n");
            writer.write("Instructions:\n");
            writer.write("1. Open your browser to frontend/admin-setup.html (or http://localhost:8080/admin-setup.html)\n");
            writer.write("2. Paste this setup token and choose your administrator credentials.\n");
            writer.write("3. Once created, this token and file will be permanently removed.\n");
        } catch (IOException e) {
            System.err.println("Warning: Could not write admin setup token to file: " + e.getMessage());
        }
    }

    private void cleanUpTokenFile() {
        try {
            File f = new File(TOKEN_FILE_NAME);
            if (f.exists()) {
                f.delete();
            }
        } catch (Exception ignored) {}
    }
}
