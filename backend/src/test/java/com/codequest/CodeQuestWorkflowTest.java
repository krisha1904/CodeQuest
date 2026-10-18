package com.codequest;

import com.codequest.dto.AuthDto;
import com.codequest.entity.Role;
import com.codequest.entity.User;
import com.codequest.repository.UserRepository;
import com.codequest.service.AdminSetupService;
import com.codequest.service.AuthService;
import com.codequest.service.LessonService;
import com.codequest.service.StreakService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("dev")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CodeQuestWorkflowTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminSetupService adminSetupService;

    @Autowired
    private AuthService authService;

    @Autowired
    private StreakService streakService;

    @Autowired
    private LessonService lessonService;

    @Autowired
    private com.codequest.service.AdminService adminService;

    @Test
    @Order(1)
    void testZeroPreExistingUsersOnStart() {
        // Requirement 2: Start with zero pre-existing student or admin accounts
        long totalUsers = userRepository.count();
        assertEquals(0, totalUsers, "Database must start with completely empty users table");
        assertTrue(adminSetupService.isSetupAvailable(), "First-time admin setup must be available when 0 admins exist");
    }

    @Test
    @Order(2)
    void testAdminSetupTokenGenerated() {
        // Token file must exist when no admin has been created
        File tokenFile = new File("admin-setup-token.txt");
        assertTrue(tokenFile.exists(), "Setup token file must be created on startup");
    }

    @Test
    @Order(3)
    void testStudentRegistrationEnforcesStudentRoleAndZeroDefaults() {
        // Requirement 2: Proper registration validates info, sets ROLE_STUDENT
        AuthDto.RegisterRequest req = new AuthDto.RegisterRequest();
        req.setUsername("arjun_mehta");
        req.setEmail("arjun@college.edu");
        req.setPassword("SecurePass123!");
        req.setFullName("Arjun Mehta");
        req.setCollegeName("Delhi Technological University");

        AuthDto.AuthResponse resp = authService.register(req);
        assertNotNull(resp);
        assertEquals("arjun_mehta", resp.getUsername());
        assertEquals("ROLE_STUDENT", resp.getRole());
        assertEquals(0, resp.getCurrentStreak(), "Newly registered student must start with 0 streak");
        assertEquals(0, resp.getLongestStreak(), "Newly registered student must start with 0 longest streak");
        assertEquals(0, resp.getTotalPoints(), "Newly registered student must start with 0 points");

        User user = userRepository.findByUsername("arjun_mehta").orElseThrow();
        assertEquals(Role.ROLE_STUDENT, user.getRole(), "Must never grant admin role to student registration");
    }

    @Test
    @Order(4)
    void testFirstTimeAdminSetupSucceedsAndPermanentlyLocks() {
        // Read token from service validation
        // Retrieve setup token or validate
        assertTrue(adminSetupService.isSetupAvailable());

        // Test with invalid token fails
        AuthDto.AdminSetupRequest badReq = new AuthDto.AdminSetupRequest();
        badReq.setSetupToken("INVALID_TOKEN_12345");
        badReq.setUsername("head_admin");
        badReq.setEmail("admin@codequest.edu");
        badReq.setPassword("SuperAdminPass999!");
        badReq.setFullName("Prof. Head Admin");

        assertThrows(RuntimeException.class, () -> authService.setupFirstAdmin(badReq));

        // Use valid setup mechanism
        // For testing, retrieve active token from token file
        String validToken = null;
        try {
            List<String> lines = java.nio.file.Files.readAllLines(java.nio.file.Paths.get("admin-setup-token.txt"));
            for (String line : lines) {
                if (line.startsWith("SETUP_TOKEN=")) {
                    validToken = line.substring("SETUP_TOKEN=".length()).trim();
                    break;
                }
            }
        } catch (Exception e) {
            fail("Could not read admin-setup-token.txt");
        }

        assertNotNull(validToken);
        badReq.setSetupToken(validToken);
        AuthDto.AuthResponse adminResp = authService.setupFirstAdmin(badReq);
        assertNotNull(adminResp);
        assertEquals("ROLE_ADMIN", adminResp.getRole());

        // Verify setup is now permanently disabled
        assertFalse(adminSetupService.isSetupAvailable(), "Setup must be permanently locked after creation");

        // Attempting second admin setup must throw exception
        assertThrows(RuntimeException.class, () -> authService.setupFirstAdmin(badReq));
    }

    @Test
    @Order(5)
    void testStreakIncrementOnLearningActivity() {
        User student = userRepository.findByUsername("arjun_mehta").orElseThrow();
        assertEquals(0, student.getCurrentStreak());

        // Record a qualifying lesson completion activity
        streakService.recordLearningActivity(student, "LESSON_COMPLETION");

        student = userRepository.findByUsername("arjun_mehta").orElseThrow();
        assertEquals(1, student.getCurrentStreak(), "Current streak must increment to 1 day after learning activity");
        assertEquals(1, student.getLongestStreak(), "Longest streak must update to 1 day");

        // Recording second activity on same calendar day must not duplicate streak
        streakService.recordLearningActivity(student, "QUIZ_PASS");
        student = userRepository.findByUsername("arjun_mehta").orElseThrow();
        assertEquals(1, student.getCurrentStreak(), "Must count at most 1 qualifying streak day per calendar day");
    }

    @Test
    @Order(6)
    void testLeaderboardExcludesAdmins() {
        // Verify userRepository filtering
        List<User> students = userRepository.findByRoleOrderByCurrentStreakDesc(Role.ROLE_STUDENT);
        assertEquals(1, students.size(), "Only registered student must appear in student streak rankings");
        assertEquals("arjun_mehta", students.get(0).getUsername());
        assertFalse(students.stream().anyMatch(u -> u.getRole() == Role.ROLE_ADMIN), "Admin must never appear in student streak rankings");
    }

    @Test
    @Order(7)
    void testLoginTrackingAndAdminMetrics() {
        // Log in as student
        AuthDto.AuthResponse loginResp = authService.login(new AuthDto.LoginRequest("arjun_mehta", "SecurePass123!"));
        assertNotNull(loginResp);
        assertEquals("arjun_mehta", loginResp.getUsername());

        // Verify user tracking fields
        User student = userRepository.findByUsername("arjun_mehta").orElseThrow();
        assertNotNull(student.getLastLoginAt(), "lastLoginAt must be populated on login");
        assertTrue(student.getLoginCount() >= 2, "loginCount must increment on login");

        // Verify admin audit stream
        List<com.codequest.dto.AdminDto.LoginActivityResponse> logins = adminService.getRecentLogins();
        assertFalse(logins.isEmpty(), "Recent logins list must not be empty");
        assertTrue(logins.stream().anyMatch(l -> "arjun_mehta".equals(l.getUsername())), "Must track student login event");

        // Verify admin all-users list
        List<com.codequest.dto.AdminDto.UserDetailResponse> allUsers = adminService.getAllUsers();
        assertTrue(allUsers.size() >= 2, "All users must include student and admin");

        // Verify admin metrics
        com.codequest.dto.AdminDto.MetricsResponse metrics = adminService.getPlatformMetrics();
        assertTrue(metrics.getTotalLogins() >= 1, "Metrics must include total logins");
        assertTrue(metrics.getTotalUsers() >= 2, "Metrics must include total users");
    }
}
