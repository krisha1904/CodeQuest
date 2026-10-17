package com.codequest.controller;

import com.codequest.entity.User;
import com.codequest.exception.ResourceNotFoundException;
import com.codequest.repository.UserRepository;
import com.codequest.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LessonService lessonService;

    @GetMapping("/profile")
    public ResponseEntity<?> getCurrentUserProfile(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Map<String, Object> progress = lessonService.getStudentProgressSummary(username);

        Map<String, Object> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("username", user.getUsername());
        response.put("email", user.getEmail());
        response.put("fullName", user.getFullName());
        response.put("collegeName", user.getCollegeName());
        response.put("role", user.getRole().name());
        response.put("currentStreak", user.getCurrentStreak());
        response.put("longestStreak", user.getLongestStreak());
        response.put("totalPoints", user.getTotalPoints());
        response.put("levelTitle", user.getLevelTitle());
        response.put("joinedDate", user.getCreatedAt());
        response.put("progress", progress);

        return ResponseEntity.ok(response);
    }
}
