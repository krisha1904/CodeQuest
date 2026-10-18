package com.codequest.dto;

public class AdminDto {

    public static class MetricsResponse {
        private long totalStudents;
        private long totalLessons;
        private long totalQuestions;
        private long totalQuizAttempts;
        private double averagePassRate;
        private long openDoubts;
        private long openFeedback;
        private long totalUsers;
        private long totalLogins;
        private long activeUsersToday;

        public MetricsResponse(long totalStudents, long totalLessons, long totalQuestions, long totalQuizAttempts, double averagePassRate, long openDoubts, long openFeedback) {
            this(totalStudents, totalLessons, totalQuestions, totalQuizAttempts, averagePassRate, openDoubts, openFeedback, totalStudents, 0, 0);
        }

        public MetricsResponse(long totalStudents, long totalLessons, long totalQuestions, long totalQuizAttempts, double averagePassRate, long openDoubts, long openFeedback, long totalUsers, long totalLogins, long activeUsersToday) {
            this.totalStudents = totalStudents;
            this.totalLessons = totalLessons;
            this.totalQuestions = totalQuestions;
            this.totalQuizAttempts = totalQuizAttempts;
            this.averagePassRate = averagePassRate;
            this.openDoubts = openDoubts;
            this.openFeedback = openFeedback;
            this.totalUsers = totalUsers;
            this.totalLogins = totalLogins;
            this.activeUsersToday = activeUsersToday;
        }

        public long getTotalStudents() { return totalStudents; }
        public long getTotalLessons() { return totalLessons; }
        public long getTotalQuestions() { return totalQuestions; }
        public long getTotalQuizAttempts() { return totalQuizAttempts; }
        public double getAveragePassRate() { return averagePassRate; }
        public long getOpenDoubts() { return openDoubts; }
        public long getOpenFeedback() { return openFeedback; }
        public long getTotalUsers() { return totalUsers; }
        public long getTotalLogins() { return totalLogins; }
        public long getActiveUsersToday() { return activeUsersToday; }
    }

    public static class UserDetailResponse {
        private Long id;
        private String username;
        private String email;
        private String fullName;
        private String collegeName;
        private String role;
        private int totalPoints;
        private int currentStreak;
        private int longestStreak;
        private String levelTitle;
        private java.time.LocalDateTime createdAt;
        private java.time.LocalDateTime lastLoginAt;
        private int loginCount;

        public UserDetailResponse() {}

        public UserDetailResponse(Long id, String username, String email, String fullName, String collegeName, String role, int totalPoints, int currentStreak, int longestStreak, String levelTitle, java.time.LocalDateTime createdAt, java.time.LocalDateTime lastLoginAt, int loginCount) {
            this.id = id;
            this.username = username;
            this.email = email;
            this.fullName = fullName;
            this.collegeName = collegeName;
            this.role = role;
            this.totalPoints = totalPoints;
            this.currentStreak = currentStreak;
            this.longestStreak = longestStreak;
            this.levelTitle = levelTitle;
            this.createdAt = createdAt;
            this.lastLoginAt = lastLoginAt;
            this.loginCount = loginCount;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }
        public String getCollegeName() { return collegeName; }
        public void setCollegeName(String collegeName) { this.collegeName = collegeName; }
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
        public int getTotalPoints() { return totalPoints; }
        public void setTotalPoints(int totalPoints) { this.totalPoints = totalPoints; }
        public int getCurrentStreak() { return currentStreak; }
        public void setCurrentStreak(int currentStreak) { this.currentStreak = currentStreak; }
        public int getLongestStreak() { return longestStreak; }
        public void setLongestStreak(int longestStreak) { this.longestStreak = longestStreak; }
        public String getLevelTitle() { return levelTitle; }
        public void setLevelTitle(String levelTitle) { this.levelTitle = levelTitle; }
        public java.time.LocalDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(java.time.LocalDateTime createdAt) { this.createdAt = createdAt; }
        public java.time.LocalDateTime getLastLoginAt() { return lastLoginAt; }
        public void setLastLoginAt(java.time.LocalDateTime lastLoginAt) { this.lastLoginAt = lastLoginAt; }
        public int getLoginCount() { return loginCount; }
        public void setLoginCount(int loginCount) { this.loginCount = loginCount; }
    }

    public static class LoginActivityResponse {
        private Long id;
        private Long userId;
        private String username;
        private String email;
        private String fullName;
        private String collegeName;
        private String role;
        private String activityType;
        private String description;
        private java.time.LocalDateTime timestamp;

        public LoginActivityResponse() {}

        public LoginActivityResponse(Long id, Long userId, String username, String email, String fullName, String collegeName, String role, String activityType, String description, java.time.LocalDateTime timestamp) {
            this.id = id;
            this.userId = userId;
            this.username = username;
            this.email = email;
            this.fullName = fullName;
            this.collegeName = collegeName;
            this.role = role;
            this.activityType = activityType;
            this.description = description;
            this.timestamp = timestamp;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }
        public String getCollegeName() { return collegeName; }
        public void setCollegeName(String collegeName) { this.collegeName = collegeName; }
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
        public String getActivityType() { return activityType; }
        public void setActivityType(String activityType) { this.activityType = activityType; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public java.time.LocalDateTime getTimestamp() { return timestamp; }
        public void setTimestamp(java.time.LocalDateTime timestamp) { this.timestamp = timestamp; }
    }

    public static class LessonSaveRequest {
        private Long moduleId;
        private String title;
        private int sequenceOrder;
        private int estimatedMinutes;
        private String definition;
        private String conceptExplanation;
        private String syntaxBlock;
        private String javaProgram;
        private String programOutput;
        private String programExplanation;
        private String importantPoints;
        private String commonMistakes;
        private String practiceQuestions;
        private boolean isPublished = true;

        public LessonSaveRequest() {}

        public Long getModuleId() { return moduleId; }
        public void setModuleId(Long moduleId) { this.moduleId = moduleId; }

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public int getSequenceOrder() { return sequenceOrder; }
        public void setSequenceOrder(int sequenceOrder) { this.sequenceOrder = sequenceOrder; }

        public int getEstimatedMinutes() { return estimatedMinutes; }
        public void setEstimatedMinutes(int estimatedMinutes) { this.estimatedMinutes = estimatedMinutes; }

        public String getDefinition() { return definition; }
        public void setDefinition(String definition) { this.definition = definition; }

        public String getConceptExplanation() { return conceptExplanation; }
        public void setConceptExplanation(String conceptExplanation) { this.conceptExplanation = conceptExplanation; }

        public String getSyntaxBlock() { return syntaxBlock; }
        public void setSyntaxBlock(String syntaxBlock) { this.syntaxBlock = syntaxBlock; }

        public String getJavaProgram() { return javaProgram; }
        public void setJavaProgram(String javaProgram) { this.javaProgram = javaProgram; }

        public String getProgramOutput() { return programOutput; }
        public void setProgramOutput(String programOutput) { this.programOutput = programOutput; }

        public String getProgramExplanation() { return programExplanation; }
        public void setProgramExplanation(String programExplanation) { this.programExplanation = programExplanation; }

        public String getImportantPoints() { return importantPoints; }
        public void setImportantPoints(String importantPoints) { this.importantPoints = importantPoints; }

        public String getCommonMistakes() { return commonMistakes; }
        public void setCommonMistakes(String commonMistakes) { this.commonMistakes = commonMistakes; }

        public String getPracticeQuestions() { return practiceQuestions; }
        public void setPracticeQuestions(String practiceQuestions) { this.practiceQuestions = practiceQuestions; }

        public boolean isPublished() { return isPublished; }
        public void setPublished(boolean published) { isPublished = published; }
    }

    public static class QuestionSaveRequest {
        private Long lessonId;
        private String questionText;
        private String optionA;
        private String optionB;
        private String optionC;
        private String optionD;
        private String correctOption;
        private String explanation;
        private String difficulty;

        public QuestionSaveRequest() {}

        public Long getLessonId() { return lessonId; }
        public void setLessonId(Long lessonId) { this.lessonId = lessonId; }

        public String getQuestionText() { return questionText; }
        public void setQuestionText(String questionText) { this.questionText = questionText; }

        public String getOptionA() { return optionA; }
        public void setOptionA(String optionA) { this.optionA = optionA; }

        public String getOptionB() { return optionB; }
        public void setOptionB(String optionB) { this.optionB = optionB; }

        public String getOptionC() { return optionC; }
        public void setOptionC(String optionC) { this.optionC = optionC; }

        public String getOptionD() { return optionD; }
        public void setOptionD(String optionD) { this.optionD = optionD; }

        public String getCorrectOption() { return correctOption; }
        public void setCorrectOption(String correctOption) { this.correctOption = correctOption; }

        public String getExplanation() { return explanation; }
        public void setExplanation(String explanation) { this.explanation = explanation; }

        public String getDifficulty() { return difficulty; }
        public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
    }
}
