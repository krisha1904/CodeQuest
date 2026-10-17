package com.codequest.controller;

import com.codequest.dto.AdminDto;
import com.codequest.entity.Feedback;
import com.codequest.entity.Lesson;
import com.codequest.entity.Question;
import com.codequest.entity.User;
import com.codequest.exception.ResourceNotFoundException;
import com.codequest.repository.FeedbackRepository;
import com.codequest.service.AdminService;
import com.codequest.service.DoubtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private DoubtService doubtService;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @GetMapping("/metrics")
    public ResponseEntity<AdminDto.MetricsResponse> getMetrics() {
        return ResponseEntity.ok(adminService.getPlatformMetrics());
    }

    @GetMapping("/students")
    public ResponseEntity<List<User>> getAllStudents() {
        return ResponseEntity.ok(adminService.getAllStudents());
    }

    @PostMapping("/lessons")
    public ResponseEntity<Lesson> createLesson(@RequestBody AdminDto.LessonSaveRequest req) {
        return ResponseEntity.ok(adminService.saveLesson(req));
    }

    @PostMapping("/questions")
    public ResponseEntity<Question> createQuestion(@RequestBody AdminDto.QuestionSaveRequest req) {
        return ResponseEntity.ok(adminService.saveQuestion(req));
    }

    @GetMapping("/doubts")
    public ResponseEntity<?> getAllDoubts() {
        return ResponseEntity.ok(doubtService.getAllDoubts().stream().map(d -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", d.getId());
            map.put("topic", d.getTopic());
            map.put("title", d.getTitle());
            map.put("description", d.getDescription());
            map.put("status", d.getStatus());
            map.put("studentName", d.getUser().getFullName());
            map.put("collegeName", d.getUser().getCollegeName());
            map.put("createdAt", d.getCreatedAt());
            map.put("repliesCount", d.getReplies().size());
            return map;
        }).collect(Collectors.toList()));
    }

    @GetMapping("/feedback")
    public ResponseEntity<List<Feedback>> getAllFeedback() {
        return ResponseEntity.ok(feedbackRepository.findAllByOrderByCreatedAtDesc());
    }

    @PutMapping("/feedback/{id}/reply")
    public ResponseEntity<?> replyFeedback(@PathVariable Long id, @RequestBody Map<String, String> body) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback not found"));
        feedback.setAdminResponse(body.get("response"));
        feedback.setStatus("RESOLVED");
        feedbackRepository.save(feedback);
        return ResponseEntity.ok(Map.of("success", true, "message", "Feedback response recorded."));
    }
}
