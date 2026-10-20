// =====================================================================
// CodeQuest: Unified API Layer
// Live Spring Boot REST integration with clean local persistence fallback
// Zero fabricated users, zero fake student statistics
// =====================================================================

const RENDER_PROD_API = "https://codequest-cx1l.onrender.com/api";
const LOCAL_DEV_API = "http://localhost:8080/api";

function resolveApiBaseUrl() {
  if (window.CODEQUEST_API_URL) return window.CODEQUEST_API_URL;
  const stored = localStorage.getItem("cq_api_url");
  if (stored) {
    if (stored.includes("ngrok") || stored.includes("localhost") || !stored.startsWith("https://codequest-cx1l.onrender.com")) {
      localStorage.removeItem("cq_api_url");
    } else {
      return stored;
    }
  }
  if (window.location.hostname === "localhost" || window.location.hostname === "127.0.0.1") {
    return LOCAL_DEV_API;
  }
  return RENDER_PROD_API;
}
const API_BASE_URL = resolveApiBaseUrl();
console.log("[CodeQuest API] Active API URL:", API_BASE_URL);

const CodeQuestAPI = {
  // Check if live backend is online
  async checkBackendHealth() {
    try {
      const res = await fetch(`${API_BASE_URL}/courses`, { method: "HEAD", signal: AbortSignal.timeout(6000) });
      return res.ok || res.status === 401 || res.status === 403;
    } catch (e) {
      return false;
    }
  },

  // Generic Request Helper
  async request(endpoint, options = {}) {
    const token = localStorage.getItem("cq_token");
    const headers = {
      "Content-Type": "application/json",
      ...(token ? { "Authorization": `Bearer ${token}` } : {}),
      ...(options.headers || {})
    };

    try {
      const response = await fetch(`${API_BASE_URL}${endpoint}`, {
        ...options,
        headers,
        signal: AbortSignal.timeout(60000)
      });
      if (!response.ok) {
        let errMessage = `HTTP error ${response.status}`;
        try {
          const errData = await response.json();
          if (errData && (errData.message || errData.error)) {
            errMessage = errData.message || errData.error;
          }
        } catch (_) {}
        throw new Error(errMessage);
      }
      return await response.json();
    } catch (err) {
      if (err.message && !err.message.startsWith("Failed to fetch") && !err.message.startsWith("HTTP error 5") && !err.name.includes("AbortError")) {
        // Business logic error from live server (e.g. invalid credentials, token error)
        throw err;
      }
      // If live server is truly unreachable, fall back to clean local persistence
      console.warn(`[CodeQuest API] Live backend unreachable at ${endpoint}, falling back to local state engine.`, err);
      return this.fallbackHandler(endpoint, options);
    }
  },

  // Clean local state engine (No fake pre-existing users, no fake leaderboard entries)
  fallbackHandler(endpoint, options) {
    const registeredUsers = JSON.parse(localStorage.getItem("cq_registered_accounts") || "[]");
    let currentUser = JSON.parse(localStorage.getItem("cq_current_user") || "null");

    // Admin setup status
    if (endpoint === "/auth/admin-setup/status") {
      const hasAdmin = registeredUsers.some(u => u.role === "ROLE_ADMIN");
      return {
        setupAvailable: !hasAdmin,
        resetAvailable: true,
        message: !hasAdmin ? "Administrator setup is available." : "Administrator account exists. Use recovery if locked out."
      };
    }

    // Admin password reset / recovery
    if (endpoint === "/auth/admin-reset") {
      const body = JSON.parse(options.body || "{}");
      const token = (body.setupToken || "").trim();
      if (!token) throw new Error("Setup/Recovery token is required.");
      if (!body.newPassword || body.newPassword.length < 6) throw new Error("Password must be at least 6 characters.");

      const username = (body.username || "Krisha_1904").trim();
      let admin = registeredUsers.find(u => u.username.toLowerCase() === username.toLowerCase() || u.role === "ROLE_ADMIN");
      if (!admin) {
        admin = {
          id: Date.now(),
          username: username,
          email: body.email || (username.toLowerCase() + "@codequest.com"),
          fullName: body.fullName || "Administrator (" + username + ")",
          collegeName: "CodeQuest Administration",
          role: "ROLE_ADMIN",
          currentStreak: 0,
          longestStreak: 0,
          totalPoints: 0,
          levelTitle: "CodeQuest Administrator",
          createdAt: new Date().toISOString()
        };
        registeredUsers.push(admin);
      }
      admin.username = username;
      admin.password = body.newPassword;
      admin.role = "ROLE_ADMIN";
      admin.lastLoginAt = new Date().toISOString();
      admin.loginCount = (admin.loginCount || 0) + 1;
      localStorage.setItem("cq_registered_accounts", JSON.stringify(registeredUsers));

      this.logLocalActivity(admin, "ADMIN_RESET", "Admin account '" + username + "' password reset successfully");

      return {
        token: "local-jwt-admin-" + admin.id,
        id: admin.id,
        username: admin.username,
        email: admin.email,
        fullName: admin.fullName,
        collegeName: admin.collegeName,
        role: admin.role,
        currentStreak: 0,
        longestStreak: 0,
        totalPoints: 0,
        levelTitle: admin.levelTitle
      };
    }

    // Admin first-time setup
    if (endpoint === "/auth/admin-setup") {
      const body = JSON.parse(options.body || "{}");

      if (!body.username || body.username.trim().length < 3) {
        throw new Error("Username must be at least 3 characters.");
      }
      if (!body.password || body.password.length < 6) {
        throw new Error("Password must be at least 6 characters.");
      }

      const username = body.username.trim();
      let admin = registeredUsers.find(u => u.username.toLowerCase() === username.toLowerCase() || u.role === "ROLE_ADMIN");
      if (!admin) {
        admin = {
          id: Date.now(),
          username: username,
          email: body.email ? body.email.trim() : (username.toLowerCase() + "@codequest.com"),
          password: body.password,
          fullName: body.fullName || "Administrator (" + username + ")",
          collegeName: body.collegeName || "CodeQuest Academy",
          role: "ROLE_ADMIN",
          currentStreak: 0,
          longestStreak: 0,
          totalPoints: 0,
          levelTitle: "CodeQuest Administrator",
          createdAt: new Date().toISOString()
        };
        registeredUsers.push(admin);
      } else {
        admin.username = username;
        admin.password = body.password;
        if (body.email) admin.email = body.email.trim();
        if (body.fullName) admin.fullName = body.fullName.trim();
        if (body.collegeName) admin.collegeName = body.collegeName.trim();
        admin.role = "ROLE_ADMIN";
      }

      admin.lastLoginAt = new Date().toISOString();
      admin.loginCount = (admin.loginCount || 0) + 1;
      localStorage.setItem("cq_registered_accounts", JSON.stringify(registeredUsers));

      return {
        token: "local-jwt-admin-" + admin.id,
        id: admin.id,
        username: admin.username,
        email: admin.email,
        fullName: admin.fullName,
        collegeName: admin.collegeName,
        role: admin.role,
        currentStreak: 0,
        longestStreak: 0,
        totalPoints: 0,
        levelTitle: admin.levelTitle
      };
    }

    // Student registration
    if (endpoint === "/auth/register") {
      const body = JSON.parse(options.body || "{}");
      if (!body.username || !body.email || !body.password) {
        throw new Error("Missing required registration fields.");
      }

      if (registeredUsers.some(u => u.username.toLowerCase() === body.username.trim().toLowerCase())) {
        throw new Error("Username is already taken.");
      }
      if (registeredUsers.some(u => u.email.toLowerCase() === body.email.trim().toLowerCase())) {
        throw new Error("Email is already registered.");
      }

      const newStudent = {
        id: Date.now(),
        username: body.username.trim(),
        email: body.email.trim(),
        password: body.password,
        fullName: body.fullName || body.username,
        collegeName: body.collegeName || "University",
        role: "ROLE_STUDENT",
        currentStreak: 0,
        longestStreak: 0,
        totalPoints: 0,
        levelTitle: "Java Novice",
        createdAt: new Date().toISOString()
      };

      registeredUsers.push(newStudent);
      localStorage.setItem("cq_registered_accounts", JSON.stringify(registeredUsers));

      return {
        token: "local-jwt-student-" + newStudent.id,
        id: newStudent.id,
        username: newStudent.username,
        email: newStudent.email,
        fullName: newStudent.fullName,
        collegeName: newStudent.collegeName,
        role: newStudent.role,
        currentStreak: 0,
        longestStreak: 0,
        totalPoints: 0,
        levelTitle: newStudent.levelTitle
      };
    }

    // Login
    if (endpoint === "/auth/login") {
      const body = JSON.parse(options.body || "{}");
      const un = (body.username || "").trim().toLowerCase();
      const pw = body.password || "";

      const found = registeredUsers.find(u => 
        (u.username.toLowerCase() === un || u.email.toLowerCase() === un) && u.password === pw
      );

      if (!found) {
        throw new Error("Invalid username or password.");
      }

      found.lastLoginAt = new Date().toISOString();
      found.loginCount = (found.loginCount || 0) + 1;
      localStorage.setItem("cq_registered_accounts", JSON.stringify(registeredUsers));
      this.logLocalActivity(found, "USER_LOGIN", `User '${found.username}' (${found.role === 'ROLE_ADMIN' ? 'Administrator' : 'Student'}) logged in from web client`);

      return {
        token: "local-jwt-" + found.id,
        id: found.id,
        username: found.username,
        email: found.email,
        fullName: found.fullName,
        collegeName: found.collegeName,
        role: found.role,
        currentStreak: found.currentStreak || 0,
        longestStreak: found.longestStreak || 0,
        totalPoints: found.totalPoints || 0,
        levelTitle: found.levelTitle || "Java Novice",
        lastLoginAt: found.lastLoginAt,
        loginCount: found.loginCount
      };
    }

    // Public syllabus & curriculum
    if (endpoint === "/courses") {
      return [CODEQUEST_DATA.course];
    }
    if (endpoint.includes("/modules")) {
      return CODEQUEST_DATA.modules;
    }
    if (endpoint.startsWith("/lessons/module/")) {
      const modId = parseInt(endpoint.split("/").pop());
      return CODEQUEST_DATA.lessons.filter(l => l.moduleId === modId);
    }
    if (endpoint.startsWith("/lessons/") && !endpoint.includes("/complete")) {
      const id = parseInt(endpoint.split("/")[2]);
      return CODEQUEST_DATA.lessons.find(l => l.id === id) || CODEQUEST_DATA.lessons[0];
    }

    // Mark lesson complete
    if (endpoint.startsWith("/lessons/") && endpoint.endsWith("/complete")) {
      const lessonId = parseInt(endpoint.split("/")[2]);
      if (currentUser) {
        this.recordLocalStreak(currentUser, "LESSON_COMPLETION");
        currentUser.totalPoints = (currentUser.totalPoints || 0) + 50;
        localStorage.setItem("cq_current_user", JSON.stringify(currentUser));
      }
      return { success: true, firstTime: true };
    }

    // Quizzes
    if (endpoint.startsWith("/quizzes/lesson/") && !endpoint.includes("/submit")) {
      const lessonId = parseInt(endpoint.split("/")[3]);
      const qs = CODEQUEST_DATA.questions[lessonId] || CODEQUEST_DATA.questions[1] || [];
      return qs.map(q => ({
        id: q.id,
        questionText: q.questionText,
        optionA: q.optionA,
        optionB: q.optionB,
        optionC: q.optionC,
        optionD: q.optionD
      }));
    }

    if (endpoint.includes("/quizzes/lesson/") && endpoint.endsWith("/submit")) {
      const lessonId = parseInt(endpoint.split("/")[3]);
      const body = JSON.parse(options.body || "{}");
      const submitted = body.answers || {};
      const qs = CODEQUEST_DATA.questions[lessonId] || CODEQUEST_DATA.questions[1] || [];

      let correctCount = 0;
      const reviews = qs.map(q => {
        const userChoice = submitted[q.id];
        const isCorrect = userChoice && userChoice.toUpperCase() === q.correctOption.toUpperCase();
        if (isCorrect) correctCount++;
        else {
          this.logLocalMistake(q, userChoice);
        }
        return {
          questionId: q.id,
          questionText: q.questionText,
          selectedOption: userChoice || "None",
          correctOption: q.correctOption,
          isCorrect,
          explanation: q.explanation
        };
      });

      const total = qs.length || 1;
      const percentage = Math.round((correctCount / total) * 100);
      const passed = percentage >= 70;
      const pointsAwarded = passed ? (percentage === 100 ? 100 : 50) : 0;

      let newStreakEarnedToday = false;
      if (passed && currentUser) {
        newStreakEarnedToday = this.recordLocalStreak(currentUser, "QUIZ_PASSED");
        currentUser.totalPoints = (currentUser.totalPoints || 0) + pointsAwarded;
        localStorage.setItem("cq_current_user", JSON.stringify(currentUser));
      }

      return {
        totalQuestions: total,
        correctCount,
        percentage,
        passed,
        pointsAwarded,
        newStreak: currentUser ? currentUser.currentStreak : 0,
        newStreakEarnedToday,
        reviews
      };
    }

    // Streaks summary
    if (endpoint === "/streaks/summary") {
      const u = currentUser || { currentStreak: 0, longestStreak: 0 };
      const streakRecords = JSON.parse(localStorage.getItem("cq_streak_records_" + (u.id || "guest")) || "[]");
      const today = new Date().toISOString().split("T")[0];
      const completedToday = streakRecords.some(r => r.date === today);

      const days = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];
      const calendar = [];
      for (let i = 6; i >= 0; i--) {
        const d = new Date();
        d.setDate(d.getDate() - i);
        const dateStr = d.toISOString().split("T")[0];
        calendar.push({
          date: dateStr,
          dayOfWeek: days[d.getDay()],
          completed: streakRecords.some(r => r.date === dateStr),
          isToday: i === 0
        });
      }

      let msg = "Keep your streak alive! Complete a Java lesson or pass a quiz today.";
      if (completedToday) {
        msg = u.currentStreak >= 3 
          ? `Awesome momentum! ${u.currentStreak} consecutive days of Java mastery.`
          : "Great job learning today! Consistency builds programming excellence.";
      }

      return {
        currentStreak: u.currentStreak || 0,
        longestStreak: u.longestStreak || 0,
        completedToday,
        weeklyCalendar: calendar,
        motivationalMessage: msg
      };
    }

    // Leaderboards - Real registered students ONLY
    if (endpoint.startsWith("/leaderboard")) {
      const students = registeredUsers.filter(u => u.role === "ROLE_STUDENT");
      const isStreak = endpoint.includes("type=streak");

      if (isStreak) {
        students.sort((a, b) => (b.currentStreak || 0) - (a.currentStreak || 0) || (b.longestStreak || 0) - (a.longestStreak || 0));
      } else {
        students.sort((a, b) => (b.totalPoints || 0) - (a.totalPoints || 0) || (b.currentStreak || 0) - (a.currentStreak || 0));
      }

      return students.map((s, idx) => ({
        rank: idx + 1,
        id: s.id,
        username: s.username,
        fullName: s.fullName,
        collegeName: s.collegeName,
        totalPoints: s.totalPoints || 0,
        currentStreak: s.currentStreak || 0,
        longestStreak: s.longestStreak || 0,
        levelTitle: s.levelTitle || "Java Novice"
      }));
    }

    // Badges
    if (endpoint === "/badges") {
      return CODEQUEST_DATA.badges;
    }
    if (endpoint === "/badges/my") {
      const earned = JSON.parse(localStorage.getItem("cq_earned_badges_" + (currentUser?.id || "guest")) || "[]");
      return earned;
    }

    // Mistakes
    if (endpoint === "/mistakes") {
      return JSON.parse(localStorage.getItem("cq_mistakes_" + (currentUser?.id || "guest")) || "[]");
    }
    if (endpoint.startsWith("/mistakes/") && endpoint.endsWith("/resolve")) {
      const id = parseInt(endpoint.split("/")[2]);
      let local = JSON.parse(localStorage.getItem("cq_mistakes_" + (currentUser?.id || "guest")) || "[]");
      local = local.map(m => m.id === id ? { ...m, isResolved: true } : m);
      localStorage.setItem("cq_mistakes_" + (currentUser?.id || "guest"), JSON.stringify(local));
      return { success: true };
    }

    // Friends & challenges
    if (endpoint === "/friends") {
      return JSON.parse(localStorage.getItem("cq_friends_" + (currentUser?.id || "guest")) || "[]");
    }
    if (endpoint === "/challenges") {
      return JSON.parse(localStorage.getItem("cq_challenges_" + (currentUser?.id || "guest")) || "[]");
    }
    if (endpoint === "/doubts") {
      return JSON.parse(localStorage.getItem("cq_doubts") || "[]");
    }
    if (endpoint === "/admin/students") {
      return registeredUsers.filter(u => u.role === "ROLE_STUDENT");
    }
    if (endpoint === "/admin/users") {
      return registeredUsers;
    }
    if (endpoint === "/admin/logins") {
      return JSON.parse(localStorage.getItem("cq_login_activities") || "[]");
    }
    if (endpoint === "/admin/metrics") {
      const students = registeredUsers.filter(u => u.role === "ROLE_STUDENT");
      const logins = JSON.parse(localStorage.getItem("cq_login_activities") || "[]");
      const today = new Date().toISOString().split("T")[0];
      const activeToday = logins.filter(l => (l.timestamp || "").startsWith(today)).length;
      return {
        totalStudents: students.length,
        totalUsers: registeredUsers.length,
        totalLessons: CODEQUEST_DATA.lessons.length,
        totalQuestions: 10,
        totalQuizAttempts: 0,
        averagePassRate: 0.0,
        openDoubts: 0,
        openFeedback: 0,
        totalLogins: logins.length,
        activeUsersToday: activeToday
      };
    }

    return { success: true };
  },

  logLocalActivity(user, type, desc) {
    const activities = JSON.parse(localStorage.getItem("cq_login_activities") || "[]");
    activities.unshift({
      id: Date.now(),
      userId: user.id,
      username: user.username,
      email: user.email,
      fullName: user.fullName,
      collegeName: user.collegeName,
      role: user.role,
      activityType: type,
      description: desc,
      timestamp: new Date().toISOString()
    });
    if (activities.length > 100) activities.length = 100;
    localStorage.setItem("cq_login_activities", JSON.stringify(activities));
  },

  recordLocalStreak(user, activityType) {
    const key = "cq_streak_records_" + (user.id || "guest");
    const records = JSON.parse(localStorage.getItem(key) || "[]");
    const today = new Date().toISOString().split("T")[0];

    if (records.some(r => r.date === today)) {
      return false; // Already recorded today
    }

    records.push({ date: today, type: activityType });
    localStorage.setItem(key, JSON.stringify(records));

    // Calculate consecutive days backwards
    let streak = 0;
    let checkDate = new Date();
    while (true) {
      const str = checkDate.toISOString().split("T")[0];
      if (records.some(r => r.date === str)) {
        streak++;
        checkDate.setDate(checkDate.getDate() - 1);
      } else {
        break;
      }
    }

    user.currentStreak = streak;
    if (user.currentStreak > (user.longestStreak || 0)) {
      user.longestStreak = user.currentStreak;
    }

    // Update in registered accounts
    const accounts = JSON.parse(localStorage.getItem("cq_registered_accounts") || "[]");
    const idx = accounts.findIndex(a => a.id === user.id);
    if (idx !== -1) {
      accounts[idx].currentStreak = user.currentStreak;
      accounts[idx].longestStreak = user.longestStreak;
      accounts[idx].totalPoints = user.totalPoints;
      localStorage.setItem("cq_registered_accounts", JSON.stringify(accounts));
    }
    localStorage.setItem("cq_current_user", JSON.stringify(user));
    return true;
  },

  logLocalMistake(question, userChoice) {
    const user = JSON.parse(localStorage.getItem("cq_current_user") || "null");
    const key = "cq_mistakes_" + (user?.id || "guest");
    let mistakes = JSON.parse(localStorage.getItem(key) || "[]");
    const existing = mistakes.find(m => m.questionId === question.id);
    if (existing) {
      existing.attemptsCount += 1;
      existing.userAnswer = userChoice || "None";
      existing.isResolved = false;
    } else {
      mistakes.unshift({
        id: Date.now(),
        questionId: question.id,
        topic: "Java Quiz Assessment",
        questionText: question.questionText,
        userAnswer: userChoice || "None",
        correctOption: question.correctOption,
        explanation: question.explanation,
        attemptsCount: 1,
        isResolved: false,
        lastAttemptedAt: "Just now"
      });
    }
    localStorage.setItem(key, JSON.stringify(mistakes));
  }
};
