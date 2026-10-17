package com.codequest.controller;

import com.codequest.dto.StreakDto;
import com.codequest.entity.User;
import com.codequest.exception.ResourceNotFoundException;
import com.codequest.repository.UserRepository;
import com.codequest.service.StreakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/streaks")
public class StreakController {

    @Autowired
    private StreakService streakService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/summary")
    public ResponseEntity<StreakDto.StreakSummaryResponse> getStreakSummary(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return ResponseEntity.ok(streakService.getStreakSummary(user.getId()));
    }
}
