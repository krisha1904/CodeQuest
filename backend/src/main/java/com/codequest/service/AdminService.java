package com.codequest.service;

import com.codequest.dto.AdminDto;
import com.codequest.entity.*;
import com.codequest.exception.ResourceNotFoundException;
import com.codequest.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizAttemptRepository quizAttemptRepository;

    @Autowired
    private DoubtRepository doubtRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private CourseModuleRepository courseModuleRepository;

    @Transactional(readOnly = true)
    public AdminDto.MetricsResponse getPlatformMetrics() {
        long totalStudents = userRepository.countByRole(Role.ROLE_STUDENT);
        long totalLessons = lessonRepository.count();
        long totalQuestions = questionRepository.count();
        long totalQuizAttempts = quizAttemptRepository.count();
        Double avgRate = quizAttemptRepository.getOverallPlatformAverage();
        double averagePassRate = avgRate != null ? Math.round(avgRate * 10.0) / 10.0 : 0.0;
        long openDoubts = doubtRepository.countByStatus("OPEN");
        long openFeedback = feedbackRepository.countByStatus("NEW");

        return new AdminDto.MetricsResponse(
                totalStudents,
                totalLessons,
                totalQuestions,
                totalQuizAttempts,
                averagePassRate,
                openDoubts,
                openFeedback
        );
    }

    @Transactional
    public Lesson saveLesson(AdminDto.LessonSaveRequest req) {
        CourseModule module = courseModuleRepository.findById(req.getModuleId())
                .orElseThrow(() -> new ResourceNotFoundException("Module not found"));

        Lesson lesson = new Lesson();
        lesson.setModule(module);
        lesson.setTitle(req.getTitle());
        lesson.setSequenceOrder(req.getSequenceOrder());
        lesson.setEstimatedMinutes(req.getEstimatedMinutes() > 0 ? req.getEstimatedMinutes() : 15);
        lesson.setDefinition(req.getDefinition());
        lesson.setConceptExplanation(req.getConceptExplanation());
        lesson.setSyntaxBlock(req.getSyntaxBlock());
        lesson.setJavaProgram(req.getJavaProgram());
        lesson.setProgramOutput(req.getProgramOutput());
        lesson.setProgramExplanation(req.getProgramExplanation());
        lesson.setImportantPoints(req.getImportantPoints());
        lesson.setCommonMistakes(req.getCommonMistakes());
        lesson.setPracticeQuestions(req.getPracticeQuestions());
        lesson.setPublished(req.isPublished());

        return lessonRepository.save(lesson);
    }

    @Transactional
    public Question saveQuestion(AdminDto.QuestionSaveRequest req) {
        Lesson lesson = lessonRepository.findById(req.getLessonId())
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));

        Question q = new Question(
                lesson,
                req.getQuestionText(),
                req.getOptionA(),
                req.getOptionB(),
                req.getOptionC(),
                req.getOptionD(),
                req.getCorrectOption().toUpperCase(),
                req.getExplanation(),
                req.getDifficulty() != null ? req.getDifficulty() : "EASY"
        );

        return questionRepository.save(q);
    }

    @Transactional(readOnly = true)
    public List<User> getAllStudents() {
        return userRepository.findByRole(Role.ROLE_STUDENT);
    }
}
