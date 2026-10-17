package com.codequest.service;

import com.codequest.dto.StreakDto;
import com.codequest.entity.StreakRecord;
import com.codequest.entity.User;
import com.codequest.repository.StreakRecordRepository;
import com.codequest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class StreakService {

    @Autowired
    private StreakRecordRepository streakRecordRepository;

    @Autowired
    private UserRepository userRepository;

    public ZoneId getUserZoneId(User user) {
        if (user == null || user.getTimezone() == null || user.getTimezone().isBlank()) {
            return ZoneId.systemDefault();
        }
        try {
            return ZoneId.of(user.getTimezone());
        } catch (Exception e) {
            return ZoneId.systemDefault();
        }
    }

    @Transactional
    public void syncUserStreak(User user) {
        ZoneId zoneId = getUserZoneId(user);
        LocalDate today = LocalDate.now(zoneId);
        LocalDate yesterday = today.minusDays(1);

        boolean hasToday = streakRecordRepository.findByUserIdAndActivityDate(user.getId(), today).isPresent();
        boolean hasYesterday = streakRecordRepository.findByUserIdAndActivityDate(user.getId(), yesterday).isPresent();

        if (!hasToday && !hasYesterday) {
            if (user.getCurrentStreak() != 0) {
                user.setCurrentStreak(0);
                userRepository.save(user);
            }
        } else {
            LocalDate checkDate = hasToday ? today : yesterday;
            int count = 0;
            while (streakRecordRepository.findByUserIdAndActivityDate(user.getId(), checkDate).isPresent()) {
                count++;
                checkDate = checkDate.minusDays(1);
            }
            if (user.getCurrentStreak() != count) {
                user.setCurrentStreak(count);
                if (count > user.getLongestStreak()) {
                    user.setLongestStreak(count);
                }
                userRepository.save(user);
            }
        }
    }

    @Transactional
    public boolean recordLearningActivity(User user, String activityType) {
        ZoneId zoneId = getUserZoneId(user);
        LocalDate today = LocalDate.now(zoneId);

        // Check if today is already credited
        Optional<StreakRecord> existingToday = streakRecordRepository.findByUserIdAndActivityDate(user.getId(), today);
        if (existingToday.isPresent()) {
            return false; // Already credited today, do not duplicate streak or awards
        }

        // Record streak record for today
        StreakRecord record = new StreakRecord(user, today, activityType);
        streakRecordRepository.save(record);

        // Sync streak based on consecutive activity records
        syncUserStreak(user);
        return true;
    }

    @Transactional
    public StreakDto.StreakSummaryResponse getStreakSummary(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        syncUserStreak(user);

        ZoneId zoneId = getUserZoneId(user);
        LocalDate today = LocalDate.now(zoneId);

        boolean completedToday = streakRecordRepository.findByUserIdAndActivityDate(userId, today).isPresent();

        // Generate 7-day visual calendar (last 6 days + today)
        List<StreakDto.DayStatus> calendar = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            boolean completed = streakRecordRepository.findByUserIdAndActivityDate(userId, date).isPresent();
            String dayName = date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
            calendar.add(new StreakDto.DayStatus(date, dayName, completed, date.isEqual(today)));
        }

        String motivationalMessage = getMotivationalMessage(user.getCurrentStreak(), completedToday);

        return new StreakDto.StreakSummaryResponse(
                user.getCurrentStreak(),
                user.getLongestStreak(),
                completedToday,
                calendar,
                motivationalMessage
        );
    }

    private String getMotivationalMessage(int streak, boolean completedToday) {
        if (!completedToday) {
            return "Keep your streak alive! Complete a Java lesson or pass a quiz today.";
        }
        if (streak >= 7) {
            return "Incredible dedication! You are on a " + streak + "-day learning streak. Keep inspiring!";
        } else if (streak >= 3) {
            return "Awesome momentum! " + streak + " consecutive days of Java mastery.";
        } else {
            return "Great job learning today! Consistency builds programming excellence.";
        }
    }
}
