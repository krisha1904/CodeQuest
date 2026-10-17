package com.codequest.dto;

import java.time.LocalDate;
import java.util.List;

public class StreakDto {

    public static class DayStatus {
        private LocalDate date;
        private String dayOfWeek;
        private boolean completed;
        private boolean isToday;

        public DayStatus(LocalDate date, String dayOfWeek, boolean completed, boolean isToday) {
            this.date = date;
            this.dayOfWeek = dayOfWeek;
            this.completed = completed;
            this.isToday = isToday;
        }

        public LocalDate getDate() { return date; }
        public String getDayOfWeek() { return dayOfWeek; }
        public boolean isCompleted() { return completed; }
        public boolean isToday() { return isToday; }
    }

    public static class StreakSummaryResponse {
        private int currentStreak;
        private int longestStreak;
        private boolean completedToday;
        private List<DayStatus> weeklyCalendar;
        private String motivationalMessage;

        public StreakSummaryResponse(int currentStreak, int longestStreak, boolean completedToday, List<DayStatus> weeklyCalendar, String motivationalMessage) {
            this.currentStreak = currentStreak;
            this.longestStreak = longestStreak;
            this.completedToday = completedToday;
            this.weeklyCalendar = weeklyCalendar;
            this.motivationalMessage = motivationalMessage;
        }

        public int getCurrentStreak() { return currentStreak; }
        public int getLongestStreak() { return longestStreak; }
        public boolean isCompletedToday() { return completedToday; }
        public List<DayStatus> getWeeklyCalendar() { return weeklyCalendar; }
        public String getMotivationalMessage() { return motivationalMessage; }
    }
}
