package com.codequest.service;

import com.codequest.entity.Role;
import com.codequest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.HexFormat;

@Service
public class AdminSetupService {

    @Autowired
    private UserRepository userRepository;

    private String activeSetupToken;
    private static final String TOKEN_FILE_NAME = "admin-setup-token.txt";

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        checkAndInitializeSetup();
    }

    public synchronized void checkAndInitializeSetup() {
        long adminCount = userRepository.countByRole(Role.ROLE_ADMIN);
        if (adminCount == 0) {
            String envToken = System.getenv("ADMIN_SETUP_TOKEN");
            String sysPropToken = System.getProperty("admin.setup.token");

            if (envToken != null && !envToken.isBlank()) {
                activeSetupToken = envToken.trim();
            } else if (sysPropToken != null && !sysPropToken.isBlank()) {
                activeSetupToken = sysPropToken.trim();
            } else if (activeSetupToken == null) {
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
            activeSetupToken = null;
            cleanUpTokenFile();
        }
    }

    public boolean isSetupAvailable() {
        return userRepository.countByRole(Role.ROLE_ADMIN) == 0 && activeSetupToken != null;
    }

    public boolean validateToken(String token) {
        if (!isSetupAvailable() || token == null) {
            return false;
        }
        return activeSetupToken.equals(token.trim());
    }

    public synchronized void completeSetup() {
        activeSetupToken = null;
        cleanUpTokenFile();
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
