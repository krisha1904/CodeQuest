package com.codequest.controller;

import com.codequest.entity.Badge;
import com.codequest.entity.StudentBadge;
import com.codequest.entity.User;
import com.codequest.exception.ResourceNotFoundException;
import com.codequest.repository.UserRepository;
import com.codequest.service.GamificationService;
import com.codequest.service.StreakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class GamificationController {

    @Autowired
    private GamificationService gamificationService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StreakService streakService;

    @GetMapping("/badges")
    public ResponseEntity<List<Badge>> getAllBadges() {
        return ResponseEntity.ok(gamificationService.getAllBadges());
    }

    @GetMapping("/badges/my")
    public ResponseEntity<List<StudentBadge>> getMyBadges(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return ResponseEntity.ok(gamificationService.getStudentBadges(user.getId()));
    }

    @GetMapping("/leaderboard")
    public ResponseEntity<?> getLeaderboard(@RequestParam(defaultValue = "all-time") String type) {
        List<User> students = userRepository.findByRole(com.codequest.entity.Role.ROLE_STUDENT);

        // Sync and validate streaks against database activity records and timezone
        for (User u : students) {
            streakService.syncUserStreak(u);
        }

        if ("streak".equalsIgnoreCase(type)) {
            students.sort((a, b) -> {
                int cmp = Integer.compare(b.getCurrentStreak(), a.getCurrentStreak());
                if (cmp != 0) return cmp;
                cmp = Integer.compare(b.getLongestStreak(), a.getLongestStreak());
                if (cmp != 0) return cmp;
                return Integer.compare(b.getTotalPoints(), a.getTotalPoints());
            });
        } else {
            students.sort((a, b) -> {
                int cmp = Integer.compare(b.getTotalPoints(), a.getTotalPoints());
                if (cmp != 0) return cmp;
                return Integer.compare(b.getCurrentStreak(), a.getCurrentStreak());
            });
        }

        List<Map<String, Object>> result = new java.util.ArrayList<>();
        int rank = 1;
        for (User u : students) {
            Map<String, Object> map = new HashMap<>();
            map.put("rank", rank++);
            map.put("id", u.getId());
            map.put("username", u.getUsername());
            map.put("fullName", u.getFullName());
            map.put("collegeName", u.getCollegeName());
            map.put("totalPoints", u.getTotalPoints());
            map.put("currentStreak", u.getCurrentStreak());
            map.put("longestStreak", u.getLongestStreak());
            map.put("levelTitle", u.getLevelTitle());
            result.add(map);
        }

        return ResponseEntity.ok(result);
    }
}
