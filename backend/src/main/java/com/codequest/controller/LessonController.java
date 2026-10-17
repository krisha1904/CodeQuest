package com.codequest.controller;

import com.codequest.entity.Lesson;
import com.codequest.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @GetMapping("/{id}")
    public ResponseEntity<Lesson> getLessonById(@PathVariable Long id) {
        return ResponseEntity.ok(lessonService.getLessonById(id));
    }

    @GetMapping("/module/{moduleId}")
    public ResponseEntity<List<Lesson>> getLessonsByModule(@PathVariable Long moduleId) {
        return ResponseEntity.ok(lessonService.getLessonsByModule(moduleId));
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<?> markCompleted(@PathVariable Long id, Authentication authentication) {
        boolean firstTimeCompleted = lessonService.markLessonCompleted(id, authentication.getName());
        return ResponseEntity.ok(Map.of("success", true, "firstTime", firstTimeCompleted));
    }
}
