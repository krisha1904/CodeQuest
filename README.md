# CodeQuest: A Gamified Java Learning and Student Engagement Platform

> **Tagline:** Learn. Practice. Compete. Repeat.  
> **Course Syllabus:** College Core Java Programming (12 Modules, 10-Section Lessons)  
> **Technology Stack:** HTML5, CSS3, JavaScript, Bootstrap 5, Chart.js, Spring Boot 3.3.4, Spring Security, Spring Data JPA, MySQL 8.0, Maven.

---

## 🌟 Executive Overview

**CodeQuest** is a comprehensive educational platform designed specifically for college students learning Java programming. Built to match how Java is taught in university lecture halls and laboratories, CodeQuest bridges academic theory with gamified habits, Duolingo-style daily learning streaks, Mistake Notebooks, peer quiz challenges, and an instructor control center.

---

## 🏗️ Project Architecture & File Structure

```text
java_clg_proj/
├── backend/                               # Spring Boot 3 Backend Application
│   ├── pom.xml                            # Maven dependencies (Web, JPA, Security, MySQL, H2, JWT)
│   ├── src/main/java/com/codequest/
│   │   ├── CodeQuestApplication.java      # Application entry point
│   │   ├── config/
│   │   │   ├── SecurityConfig.java        # Spring Security (RBAC, JWT, stateless)
│   │   │   ├── CorsConfig.java            # Cross-Origin resource sharing
│   │   │   └── DataInitializer.java       # Automatic database seeder for courses & quizzes
│   │   ├── entity/                        # JPA Entities
│   │   │   ├── User.java, Role.java
│   │   │   ├── Course.java, CourseModule.java, Lesson.java
│   │   │   ├── Question.java, QuizAttempt.java, QuizAttemptAnswer.java
│   │   │   ├── LessonProgress.java, StreakRecord.java, StudentActivity.java
│   │   │   ├── Badge.java, StudentBadge.java, PointTransaction.java
│   │   │   ├── Friendship.java, Challenge.java, ChallengeParticipant.java
│   │   │   ├── MistakeRecord.java, Doubt.java, DoubtReply.java, Feedback.java, Notification.java
│   │   ├── repository/                    # Spring Data JPA Repositories
│   │   ├── service/                       # Business logic services
│   │   ├── controller/                    # REST API Controllers (/api/...)
│   │   ├── dto/                           # Request & Response data transfer objects
│   │   ├── exception/                     # Global exception handlers
│   │   └── security/                      # JWT token utilities & filters
│   └── src/main/resources/
│       ├── application.properties         # Main properties
│       ├── application-dev.properties     # Embedded H2 in-memory dev profile
│       └── application-mysql.properties   # MySQL 8.0 production profile
├── database/
│   ├── codequest_schema.sql               # Pure MySQL 8.0 DDL schema script
│   └── codequest_seed_data.sql            # Comprehensive SQL seed data
├── frontend/                              # Responsive Web Application
│   ├── index.html                         # Platform landing page
│   ├── login.html                         # Student & Admin login (instant demo accounts)
│   ├── register.html                      # Student registration
│   ├── dashboard.html                     # Personalized student dashboard & Chart.js activity
│   ├── learn.html                         # 12-Module University Java Roadmap & Syllabus
│   ├── lesson.html                        # 10-Section classroom lesson viewer
│   ├── quiz.html                          # Interactive MCQ quiz runner & score validator
│   ├── mistakes.html                      # Mistake Notebook (review & resolve errors)
│   ├── streaks.html                       # Daily streak tracker & 7-day calendar
│   ├── challenges.html                    # Peer quiz challenges (asynchronous head-to-head)
│   ├── friends.html                       # Student study circle & peer search
│   ├── leaderboard.html                   # All-time XP, streaks, and friends leaderboards
│   ├── doubts.html                        # Ticket-based "Ask a Doubt" discussions
│   ├── feedback.html                      # Course & platform feedback submission
│   ├── profile.html                       # Student profile, level status, & earned badges
│   ├── admin.html                         # Full Administrator Control Center (6 CMS tabs)
│   ├── css/
│   │   └── style.css                      # Custom design tokens, dark mode, code formatting
│   └── js/
│       ├── api.js                         # Unified REST client with offline preview fallback
│       ├── auth.js                        # Authentication state & session manager
│       ├── data.js                        # Complete college curriculum dataset
│       └── app.js                         # Dynamic navbar, footer, and toast notifications
├── tools/
│   └── apache-maven-3.9.9/                # Pre-configured portable Apache Maven
└── README.md
```

---

## 📚 The 10-Section College Lesson Structure

Every lesson in CodeQuest strictly adheres to the university teaching order:
1. **Definition:** Concise, examinable definition suitable for semester notes.
2. **Concept Explanation:** Beginner-friendly explanation using familiar analogies.
3. **Syntax:** Clean, formatted Java syntax blocks.
4. **Java Program:** Complete, runnable college-level program with a one-click copy button.
5. **Expected Output:** Clearly distinguished terminal output window.
6. **Step-by-Step Explanation:** Line-by-line execution breakdown.
7. **Important Points:** Key takeaways and viva preparation tips.
8. **Common Mistakes:** Frequent beginner traps and compiler error explanations.
9. **Multiple-Choice Questions:** Topic-based conceptual MCQs.
10. **Practice Exercises:** Programming questions for self-study.

---

## 🚀 How to Run the Application

### 1. Running the Frontend
The frontend can be launched using any standard static file server or Node.js tool:
```bash
# Option A: Using npx serve (Port 3000)
npx -y serve frontend -l 3000

# Option B: Directly open any HTML file (e.g., frontend/index.html) in your browser!
```

### 2. Running the Spring Boot Backend

The backend includes a pre-configured portable Maven binary in `./tools/apache-maven-3.9.9`:

#### Option A: Quick Dev Mode (Embedded H2 Database — Zero Setup Required)
```powershell
& ".\tools\apache-maven-3.9.9\bin\mvn.cmd" spring-boot:run -f "backend/pom.xml"
```
* **REST API:** `http://localhost:8080/api`
* **H2 Database Console:** `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:codequest_db`, User: `sa`, Password: *(empty)*)

#### Option B: MySQL 8.0 Mode
1. Open **MySQL Workbench** or command-line client.
2. Execute `database/codequest_schema.sql` and `database/codequest_seed_data.sql`.
3. Update `backend/src/main/resources/application-mysql.properties` with your MySQL `root` password if different.
4. Run the backend with the MySQL profile:
```powershell
& ".\tools\apache-maven-3.9.9\bin\mvn.cmd" spring-boot:run -f "backend/pom.xml" -Dspring-boot.run.profiles=mysql
```

---

## 🔑 Account Creation & First-Time Setup

CodeQuest adheres to standard production security by starting with a clean user database (zero pre-existing demo accounts) and a secure initialization flow:

### 1. Student Registration
Students register their own account directly through the web UI:
- Open `/register.html` in your browser.
- Enter your Full Name, College/University, Username, Email, and Password.
- Newly registered students automatically receive the `ROLE_STUDENT` role with 0 XP, 0 Streak, and clean metrics ready for their learning journey.

### 2. First-Time Administrator Initialization
When launching the backend for the first time without an admin account:
1. Spring Boot automatically generates a secure, one-time 32-character setup token.
2. The token is printed in the terminal console and saved locally to `admin-setup-token.txt`.
3. Open `/admin-setup.html` in your browser.
4. Enter the setup token and create your primary administrator account.
5. Upon successful creation, the token is permanently invalidated, `admin-setup-token.txt` is removed, and `/admin-setup.html` locks down against further setup attempts.

---

## 🎯 Verification & Testing Checkpoints

1. **Student Registration & Login:** Navigate to `/register.html`, create a student account, and sign in via `/login.html`. Verify JWT issuance and session initialization.
2. **Dashboard Analytics:** Observe the 6 metric cards, streak flame counter, and interactive Chart.js 7-day activity graph.
3. **Classroom Lesson View:** Navigate to **Lesson 1** or **Lesson 2**. Verify all 10 college curriculum sections, copy code button, and terminal output.
4. **Interactive Quiz Assessment:** Attempt the 5 MCQs on Lesson 1. Submit the quiz and observe server score calculation, passing threshold (≥ 70%), celebration animation, and answer explanations.
5. **Mistake Notebook:** Intentionally pick an incorrect answer on a quiz. Navigate to `/mistakes.html` to review the logged error, detailed explanation, and click "Mark as Understood".
6. **Duolingo-Style Streaks:** Navigate to `/streaks.html`. Verify the streak flame, 7-day calendar checkmarks, and activity rules.
7. **Peer Challenges & Study Circle:** Connect with registered peers on `/friends.html` and initiate asynchronous quiz challenges on `/challenges.html`.
8. **Administrator Control Center:** Set up your primary admin via `/admin-setup.html` and sign in. Navigate to `/admin.html` to review the CMS tabs, manage questions, and oversee the platform.
