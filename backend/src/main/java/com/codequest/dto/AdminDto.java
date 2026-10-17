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

        public MetricsResponse(long totalStudents, long totalLessons, long totalQuestions, long totalQuizAttempts, double averagePassRate, long openDoubts, long openFeedback) {
            this.totalStudents = totalStudents;
            this.totalLessons = totalLessons;
            this.totalQuestions = totalQuestions;
            this.totalQuizAttempts = totalQuizAttempts;
            this.averagePassRate = averagePassRate;
            this.openDoubts = openDoubts;
            this.openFeedback = openFeedback;
        }

        public long getTotalStudents() { return totalStudents; }
        public long getTotalLessons() { return totalLessons; }
        public long getTotalQuestions() { return totalQuestions; }
        public long getTotalQuizAttempts() { return totalQuizAttempts; }
        public double getAveragePassRate() { return averagePassRate; }
        public long getOpenDoubts() { return openDoubts; }
        public long getOpenFeedback() { return openFeedback; }
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
