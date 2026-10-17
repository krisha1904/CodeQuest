package com.codequest.controller;

import com.codequest.dto.QuizDto;
import com.codequest.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity<List<QuizDto.QuestionDto>> getQuestionsForLesson(@PathVariable Long lessonId) {
        return ResponseEntity.ok(quizService.getQuestionsForLesson(lessonId));
    }

    @PostMapping("/lesson/{lessonId}/submit")
    public ResponseEntity<QuizDto.ResultResponse> submitQuiz(
            @PathVariable Long lessonId,
            @RequestBody QuizDto.SubmissionRequest request,
            Authentication authentication) {
        return ResponseEntity.ok(quizService.submitQuiz(lessonId, request, authentication.getName()));
    }
}
