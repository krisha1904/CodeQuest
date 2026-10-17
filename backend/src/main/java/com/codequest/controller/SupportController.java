package com.codequest.controller;

import com.codequest.dto.SupportDto;
import com.codequest.entity.Doubt;
import com.codequest.entity.Feedback;
import com.codequest.entity.User;
import com.codequest.repository.FeedbackRepository;
import com.codequest.repository.UserRepository;
import com.codequest.service.DoubtService;
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
public class SupportController {

    @Autowired
    private DoubtService doubtService;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/doubts")
    public ResponseEntity<?> getDoubts(Authentication authentication) {
        List<Doubt> doubts = doubtService.getDoubtsForUser(authentication.getName());
        return ResponseEntity.ok(mapDoubts(doubts));
    }

    @PostMapping("/doubts")
    public ResponseEntity<?> createDoubt(@RequestBody SupportDto.CreateDoubtDto dto, Authentication authentication) {
        Doubt doubt = doubtService.createDoubt(dto, authentication.getName());
        return ResponseEntity.ok(Map.of("success", true, "id", doubt.getId()));
    }

    @PostMapping("/doubts/{id}/reply")
    public ResponseEntity<?> replyDoubt(
            @PathVariable Long id,
            @RequestBody SupportDto.DoubtReplyDto dto,
            Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        Doubt doubt = doubtService.replyToDoubt(id, dto.getReplyText(), authentication.getName(), isAdmin);
        return ResponseEntity.ok(Map.of("success", true, "status", doubt.getStatus()));
    }

    @PostMapping("/feedback")
    public ResponseEntity<?> submitFeedback(@RequestBody SupportDto.FeedbackRequestDto dto, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow();
        Feedback feedback = new Feedback(user, dto.getCategory(), dto.getSubject(), dto.getMessage());
        feedbackRepository.save(feedback);
        return ResponseEntity.ok(Map.of("success", true, "message", "Thank you for your feedback!"));
    }

    @GetMapping("/feedback/my")
    public ResponseEntity<?> getMyFeedback(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow();
        List<Feedback> list = feedbackRepository.findByUserIdOrderByCreatedAtDesc(user.getId());
        return ResponseEntity.ok(list);
    }

    private List<Map<String, Object>> mapDoubts(List<Doubt> doubts) {
        return doubts.stream().map(d -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", d.getId());
            map.put("topic", d.getTopic());
            map.put("title", d.getTitle());
            map.put("description", d.getDescription());
            map.put("status", d.getStatus());
            map.put("authorName", d.getUser().getFullName());
            map.put("createdAt", d.getCreatedAt());
            map.put("replies", d.getReplies().stream().map(r -> Map.of(
                    "authorName", r.getAuthor().getFullName(),
                    "replyText", r.getReplyText(),
                    "isAdminReply", r.isAdminReply(),
                    "createdAt", r.getCreatedAt()
            )).collect(Collectors.toList()));
            return map;
        }).collect(Collectors.toList());
    }
}
