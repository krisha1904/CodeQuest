package com.codequest.dto;

public class SocialDto {

    public static class FriendRequestDto {
        private String targetUsernameOrEmail;

        public FriendRequestDto() {}
        public FriendRequestDto(String targetUsernameOrEmail) {
            this.targetUsernameOrEmail = targetUsernameOrEmail;
        }

        public String getTargetUsernameOrEmail() { return targetUsernameOrEmail; }
        public void setTargetUsernameOrEmail(String targetUsernameOrEmail) { this.targetUsernameOrEmail = targetUsernameOrEmail; }
    }

    public static class CreateChallengeDto {
        private Long friendId;
        private Long lessonId;

        public CreateChallengeDto() {}
        public CreateChallengeDto(Long friendId, Long lessonId) {
            this.friendId = friendId;
            this.lessonId = lessonId;
        }

        public Long getFriendId() { return friendId; }
        public void setFriendId(Long friendId) { this.friendId = friendId; }

        public Long getLessonId() { return lessonId; }
        public void setLessonId(Long lessonId) { this.lessonId = lessonId; }
    }

    public static class ChallengeScoreSubmissionDto {
        private int score;

        public ChallengeScoreSubmissionDto() {}
        public ChallengeScoreSubmissionDto(int score) {
            this.score = score;
        }

        public int getScore() { return score; }
        public void setScore(int score) { this.score = score; }
    }
}
