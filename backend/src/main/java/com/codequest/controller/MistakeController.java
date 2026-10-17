package com.codequest.controller;

import com.codequest.entity.MistakeRecord;
import com.codequest.entity.User;
import com.codequest.exception.ResourceNotFoundException;
import com.codequest.repository.MistakeRecordRepository;
import com.codequest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/mistakes")
public class MistakeController {

    @Autowired
    private MistakeRecordRepository mistakeRecordRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<?> getMyMistakes(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<MistakeRecord> mistakes = mistakeRecordRepository.findByUserIdOrderByLastAttemptedAtDesc(user.getId());

        List<Map<String, Object>> response = mistakes.stream().map(m -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", m.getId());
            map.put("questionId", m.getQuestion().getId());
            map.put("lessonTitle", m.getQuestion().getLesson().getTitle());
            map.put("questionText", m.getQuestion().getQuestionText());
            map.put("optionA", m.getQuestion().getOptionA());
            map.put("optionB", m.getQuestion().getOptionB());
            map.put("optionC", m.getQuestion().getOptionC());
            map.put("optionD", m.getQuestion().getOptionD());
            map.put("userAnswer", m.getUserAnswer());
            map.put("correctOption", m.getQuestion().getCorrectOption());
            map.put("explanation", m.getQuestion().getExplanation());
            map.put("isResolved", m.isResolved());
            map.put("attemptsCount", m.getAttemptsCount());
            map.put("lastAttemptedAt", m.getLastAttemptedAt());
            return map;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/resolve")
    public ResponseEntity<?> resolveMistake(@PathVariable Long id, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        MistakeRecord mistake = mistakeRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mistake record not found"));

        if (!mistake.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(403).body(Map.of("error", "Unauthorized"));
        }

        mistake.setResolved(true);
        mistakeRecordRepository.save(mistake);

        return ResponseEntity.ok(Map.of("success", true, "message", "Marked as understood."));
    }
}
