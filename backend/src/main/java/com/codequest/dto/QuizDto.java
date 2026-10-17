package com.codequest.dto;

import java.util.List;
import java.util.Map;

public class QuizDto {

    public static class QuestionDto {
        private Long id;
        private String questionText;
        private String optionA;
        private String optionB;
        private String optionC;
        private String optionD;
        private String difficulty;

        public QuestionDto() {}

        public QuestionDto(Long id, String questionText, String optionA, String optionB, String optionC, String optionD, String difficulty) {
            this.id = id;
            this.questionText = questionText;
            this.optionA = optionA;
            this.optionB = optionB;
            this.optionC = optionC;
            this.optionD = optionD;
            this.difficulty = difficulty;
        }

        public Long getId() { return id; }
        public String getQuestionText() { return questionText; }
        public String getOptionA() { return optionA; }
        public String getOptionB() { return optionB; }
        public String getOptionC() { return optionC; }
        public String getOptionD() { return optionD; }
        public String getDifficulty() { return difficulty; }
    }

    public static class SubmissionRequest {
        private Map<Long, String> answers; // questionId -> selectedOption ('A','B','C','D')

        public SubmissionRequest() {}
        public SubmissionRequest(Map<Long, String> answers) {
            this.answers = answers;
        }

        public Map<Long, String> getAnswers() { return answers; }
        public void setAnswers(Map<Long, String> answers) { this.answers = answers; }
    }

    public static class AnswerReview {
        private Long questionId;
        private String questionText;
        private String selectedOption;
        private String correctOption;
        private boolean isCorrect;
        private String explanation;

        public AnswerReview(Long questionId, String questionText, String selectedOption, String correctOption, boolean isCorrect, String explanation) {
            this.questionId = questionId;
            this.questionText = questionText;
            this.selectedOption = selectedOption;
            this.correctOption = correctOption;
            this.isCorrect = isCorrect;
            this.explanation = explanation;
        }

        public Long getQuestionId() { return questionId; }
        public String getQuestionText() { return questionText; }
        public String getSelectedOption() { return selectedOption; }
        public String getCorrectOption() { return correctOption; }
        public boolean isCorrect() { return isCorrect; }
        public String getExplanation() { return explanation; }
    }

    public static class ResultResponse {
        private int totalQuestions;
        private int correctCount;
        private int percentage;
        private boolean passed;
        private int pointsAwarded;
        private int newStreak;
        private boolean newStreakEarnedToday;
        private List<AnswerReview> reviews;

        public ResultResponse(int totalQuestions, int correctCount, int percentage, boolean passed, int pointsAwarded, int newStreak, boolean newStreakEarnedToday, List<AnswerReview> reviews) {
            this.totalQuestions = totalQuestions;
            this.correctCount = correctCount;
            this.percentage = percentage;
            this.passed = passed;
            this.pointsAwarded = pointsAwarded;
            this.newStreak = newStreak;
            this.newStreakEarnedToday = newStreakEarnedToday;
            this.reviews = reviews;
        }

        public int getTotalQuestions() { return totalQuestions; }
        public int getCorrectCount() { return correctCount; }
        public int getPercentage() { return percentage; }
        public boolean isPassed() { return passed; }
        public int getPointsAwarded() { return pointsAwarded; }
        public int getNewStreak() { return newStreak; }
        public boolean isNewStreakEarnedToday() { return newStreakEarnedToday; }
        public List<AnswerReview> getReviews() { return reviews; }
    }
}
