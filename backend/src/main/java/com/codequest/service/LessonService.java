package com.codequest.service;

import com.codequest.entity.Lesson;
import com.codequest.entity.LessonProgress;
import com.codequest.entity.User;
import com.codequest.exception.ResourceNotFoundException;
import com.codequest.repository.LessonProgressRepository;
import com.codequest.repository.LessonRepository;
import com.codequest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private LessonProgressRepository lessonProgressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StreakService streakService;

    @Autowired
    private GamificationService gamificationService;

    @Transactional(readOnly = true)
    public Lesson getLessonById(Long id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Lesson> getLessonsByModule(Long moduleId) {
        return lessonRepository.findByModuleIdOrderBySequenceOrderAsc(moduleId);
    }

    @Transactional
    public boolean markLessonCompleted(Long lessonId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Lesson lesson = getLessonById(lessonId);

        LessonProgress progress = lessonProgressRepository.findByUserIdAndLessonId(user.getId(), lesson.getId())
                .orElse(new LessonProgress(user, lesson, false));

        if (!progress.isCompleted()) {
            progress.setCompleted(true);
            lessonProgressRepository.save(progress);

            streakService.recordLearningActivity(user, "LESSON_COMPLETION");
            gamificationService.awardPoints(user, 50, "Completed Lesson: " + lesson.getTitle());
            gamificationService.checkAndAwardBadge(user, "FIRST_STEP");

            if (user.getCurrentStreak() >= 7) {
                gamificationService.checkAndAwardBadge(user, "SEVEN_DAY_STREAK");
            } else if (user.getCurrentStreak() >= 3) {
                gamificationService.checkAndAwardBadge(user, "THREE_DAY_STREAK");
            }

            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getStudentProgressSummary(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        long totalPublishedLessons = lessonRepository.countByIsPublishedTrue();
        long completedLessons = lessonProgressRepository.countByUserIdAndIsCompletedTrue(user.getId());
        double percentage = totalPublishedLessons > 0 ? ((double) completedLessons / totalPublishedLessons) * 100 : 0.0;

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalLessons", totalPublishedLessons);
        summary.put("completedLessons", completedLessons);
        summary.put("percentage", Math.round(percentage));
        return summary;
    }
}
