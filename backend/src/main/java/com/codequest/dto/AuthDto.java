package com.codequest.dto;

public class AuthDto {

    public static class LoginRequest {
        private String username;
        private String password;

        public LoginRequest() {}
        public LoginRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class RegisterRequest {
        private String username;
        private String email;
        private String password;
        private String fullName;
        private String collegeName;

        public RegisterRequest() {}

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }

        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }

        public String getCollegeName() { return collegeName; }
        public void setCollegeName(String collegeName) { this.collegeName = collegeName; }
    }

    public static class AdminSetupRequest {
        private String setupToken;
        private String username;
        private String email;
        private String password;
        private String fullName;
        private String collegeName;

        public AdminSetupRequest() {}

        public String getSetupToken() { return setupToken; }
        public void setSetupToken(String setupToken) { this.setupToken = setupToken; }

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }

        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }

        public String getCollegeName() { return collegeName; }
        public void setCollegeName(String collegeName) { this.collegeName = collegeName; }
    }

    public static class AuthResponse {
        private String token;
        private Long id;
        private String username;
        private String email;
        private String fullName;
        private String collegeName;
        private String role;
        private int currentStreak;
        private int longestStreak;
        private int totalPoints;
        private String levelTitle;

        public AuthResponse(String token, Long id, String username, String email, String fullName, String collegeName, String role, int currentStreak, int longestStreak, int totalPoints, String levelTitle) {
            this.token = token;
            this.id = id;
            this.username = username;
            this.email = email;
            this.fullName = fullName;
            this.collegeName = collegeName;
            this.role = role;
            this.currentStreak = currentStreak;
            this.longestStreak = longestStreak;
            this.totalPoints = totalPoints;
            this.levelTitle = levelTitle;
        }

        public String getToken() { return token; }
        public Long getId() { return id; }
        public String getUsername() { return username; }
        public String getEmail() { return email; }
        public String getFullName() { return fullName; }
        public String getCollegeName() { return collegeName; }
        public String getRole() { return role; }
        public int getCurrentStreak() { return currentStreak; }
        public int getLongestStreak() { return longestStreak; }
        public int getTotalPoints() { return totalPoints; }
        public String getLevelTitle() { return levelTitle; }
    }
}
