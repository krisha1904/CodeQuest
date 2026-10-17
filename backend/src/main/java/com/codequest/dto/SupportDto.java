package com.codequest.dto;

public class SupportDto {

    public static class CreateDoubtDto {
        private Long lessonId;
        private String topic;
        private String title;
        private String description;

        public CreateDoubtDto() {}

        public Long getLessonId() { return lessonId; }
        public void setLessonId(Long lessonId) { this.lessonId = lessonId; }

        public String getTopic() { return topic; }
        public void setTopic(String topic) { this.topic = topic; }

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }

    public static class DoubtReplyDto {
        private String replyText;

        public DoubtReplyDto() {}
        public DoubtReplyDto(String replyText) { this.replyText = replyText; }

        public String getReplyText() { return replyText; }
        public void setReplyText(String replyText) { this.replyText = replyText; }
    }

    public static class FeedbackRequestDto {
        private String category;
        private String subject;
        private String message;

        public FeedbackRequestDto() {}

        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }

        public String getSubject() { return subject; }
        public void setSubject(String subject) { this.subject = subject; }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}
