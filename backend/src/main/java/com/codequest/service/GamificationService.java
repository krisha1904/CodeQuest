package com.codequest.service;

import com.codequest.entity.*;
import com.codequest.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GamificationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BadgeRepository badgeRepository;

    @Autowired
    private StudentBadgeRepository studentBadgeRepository;

    @Autowired
    private PointTransactionRepository pointTransactionRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Transactional
    public void awardPoints(User user, int points, String reason) {
        user.addPoints(points);
        userRepository.save(user);

        PointTransaction tx = new PointTransaction(user, points, reason);
        pointTransactionRepository.save(tx);
    }

    @Transactional
    public void checkAndAwardBadge(User user, String badgeCode) {
        badgeRepository.findByCode(badgeCode).ifPresent(badge -> {
            if (!studentBadgeRepository.existsByUserIdAndBadgeId(user.getId(), badge.getId())) {
                StudentBadge sb = new StudentBadge(user, badge);
                studentBadgeRepository.save(sb);

                awardPoints(user, badge.getXpBonus(), "Badge Earned: " + badge.getTitle());

                Notification notification = new Notification(
                        user,
                        "New Badge Unlocked!",
                        "Congratulations! You earned the \"" + badge.getTitle() + "\" badge (+ " + badge.getXpBonus() + " XP).",
                        "/profile.html"
                );
                notificationRepository.save(notification);
            }
        });
    }

    @Transactional(readOnly = true)
    public List<Badge> getAllBadges() {
        return badgeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<StudentBadge> getStudentBadges(Long userId) {
        return studentBadgeRepository.findByUserIdOrderByEarnedAtDesc(userId);
    }
}
