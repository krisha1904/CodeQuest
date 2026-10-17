package com.codequest.service;

import com.codequest.dto.QuizDto;
import com.codequest.entity.*;
import com.codequest.exception.ResourceNotFoundException;
import com.codequest.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuizService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private QuizAttemptRepository quizAttemptRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MistakeRecordRepository mistakeRecordRepository;

    @Autowired
    private StreakService streakService;

    @Autowired
    private GamificationService gamificationService;

    @Autowired
    private LessonService lessonService;

    @Transactional(readOnly = true)
    public List<QuizDto.QuestionDto> getQuestionsForLesson(Long lessonId) {
        List<Question> questions = questionRepository.findByLessonId(lessonId);
        return questions.stream().map(q -> new QuizDto.QuestionDto(
                q.getId(),
                q.getQuestionText(),
                q.getOptionA(),
                q.getOptionB(),
                q.getOptionC(),
                q.getOptionD(),
                q.getDifficulty()
        )).collect(Collectors.toList());
    }

    @Transactional
    public QuizDto.ResultResponse submitQuiz(Long lessonId, QuizDto.SubmissionRequest request, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));

        List<Question> questions = questionRepository.findByLessonId(lessonId);
        Map<Long, String> userAnswers = request.getAnswers() != null ? request.getAnswers() : Map.of();

        int correctCount = 0;
        List<QuizDto.AnswerReview> reviews = new ArrayList<>();
        List<QuizAttemptAnswer> attemptAnswers = new ArrayList<>();

        for (Question q : questions) {
            String selected = userAnswers.get(q.getId());
            boolean isCorrect = selected != null && selected.equalsIgnoreCase(q.getCorrectOption());

            if (isCorrect) {
                correctCount++;
            } else {
                // Record in Mistake Notebook
                recordMistake(user, q, selected != null ? selected : "NONE");
            }

            reviews.add(new QuizDto.AnswerReview(
                    q.getId(),
                    q.getQuestionText(),
                    selected,
                    q.getCorrectOption(),
                    isCorrect,
                    q.getExplanation()
            ));
        }

        int total = questions.size();
        int percentage = total > 0 ? (int) Math.round(((double) correctCount / total) * 100) : 0;
        boolean passed = percentage >= 70;
        int pointsAwarded = 0;

        if (passed) {
            pointsAwarded = percentage == 100 ? 100 : 50;
            gamificationService.awardPoints(user, pointsAwarded, "Passed Quiz: " + lesson.getTitle() + " (" + percentage + "%)");
            lessonService.markLessonCompleted(lesson.getId(), username);

            gamificationService.checkAndAwardBadge(user, "QUIZ_STARTER");
            if (percentage == 100) {
                gamificationService.checkAndAwardBadge(user, "PERFECT_SCORE");
            }
        }

        // Streak credit
        boolean newStreakEarnedToday = false;
        if (passed) {
            newStreakEarnedToday = streakService.recordLearningActivity(user, "QUIZ_PASSED");
            if (user.getCurrentStreak() >= 7) {
                gamificationService.checkAndAwardBadge(user, "SEVEN_DAY_STREAK");
            } else if (user.getCurrentStreak() >= 3) {
                gamificationService.checkAndAwardBadge(user, "THREE_DAY_STREAK");
            }
        }

        // Save Attempt to Database
        QuizAttempt attempt = new QuizAttempt(user, lesson, total, correctCount, percentage, pointsAwarded, passed);
        for (QuizDto.AnswerReview r : reviews) {
            Question q = questions.stream().filter(item -> item.getId().equals(r.getQuestionId())).findFirst().orElse(null);
            if (q != null) {
                attemptAnswers.add(new QuizAttemptAnswer(attempt, q, r.getSelectedOption() != null ? r.getSelectedOption() : "X", r.isCorrect()));
            }
        }
        attempt.setAnswers(attemptAnswers);
        quizAttemptRepository.save(attempt);

        return new QuizDto.ResultResponse(
                total,
                correctCount,
                percentage,
                passed,
                pointsAwarded,
                user.getCurrentStreak(),
                newStreakEarnedToday,
                reviews
        );
    }

    private void recordMistake(User user, Question question, String selectedOption) {
        mistakeRecordRepository.findByUserIdAndQuestionId(user.getId(), question.getId())
                .ifPresentOrElse(
                        mistake -> mistake.incrementAttempts(selectedOption),
                        () -> {
                            MistakeRecord mistake = new MistakeRecord(user, question, selectedOption);
                            mistakeRecordRepository.save(mistake);
                        }
                );
    }
}
