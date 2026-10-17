// =====================================================================
// CodeQuest: Authentication & Session State Manager
// Clean user database: real registration, secure admin setup & login
// =====================================================================

const Auth = {
  getCurrentUser() {
    const raw = localStorage.getItem("cq_current_user");
    if (!raw) return null;
    try {
      return JSON.parse(raw);
    } catch (e) {
      return null;
    }
  },

  isLoggedIn() {
    return !!localStorage.getItem("cq_token") && !!this.getCurrentUser();
  },

  isAdmin() {
    const user = this.getCurrentUser();
    return !!(user && user.role === "ROLE_ADMIN");
  },

  async login(username, password) {
    try {
      const data = await CodeQuestAPI.request("/auth/login", {
        method: "POST",
        body: JSON.stringify({ username, password })
      });

      localStorage.setItem("cq_token", data.token);
      localStorage.setItem("cq_current_user", JSON.stringify({
        id: data.id,
        username: data.username,
        email: data.email,
        fullName: data.fullName,
        collegeName: data.collegeName || "Engineering College",
        role: data.role,
        currentStreak: typeof data.currentStreak === 'number' ? data.currentStreak : 0,
        longestStreak: typeof data.longestStreak === 'number' ? data.longestStreak : 0,
        totalPoints: typeof data.totalPoints === 'number' ? data.totalPoints : 0,
        levelTitle: data.levelTitle || (data.role === 'ROLE_ADMIN' ? 'CodeQuest Administrator' : 'Java Novice')
      }));

      return { success: true, user: data };
    } catch (err) {
      return { success: false, message: err.message || "Invalid username or password" };
    }
  },

  async register(fullName, username, email, collegeName, password) {
    try {
      const data = await CodeQuestAPI.request("/auth/register", {
        method: "POST",
        body: JSON.stringify({ fullName, username, email, collegeName, password })
      });

      localStorage.setItem("cq_token", data.token);
      localStorage.setItem("cq_current_user", JSON.stringify({
        id: data.id,
        username: data.username,
        email: data.email,
        fullName: data.fullName,
        collegeName: data.collegeName || "Engineering College",
        role: "ROLE_STUDENT",
        currentStreak: typeof data.currentStreak === 'number' ? data.currentStreak : 0,
        longestStreak: typeof data.longestStreak === 'number' ? data.longestStreak : 0,
        totalPoints: typeof data.totalPoints === 'number' ? data.totalPoints : 0,
        levelTitle: data.levelTitle || "Java Novice"
      }));

      return { success: true, user: data };
    } catch (err) {
      return { success: false, message: err.message || "Registration failed. Username or email may already be taken." };
    }
  },

  async setupAdmin(setupToken, fullName, username, email, collegeName, password) {
    try {
      const data = await CodeQuestAPI.request("/auth/admin-setup", {
        method: "POST",
        body: JSON.stringify({ setupToken, fullName, username, email, collegeName, password })
      });

      localStorage.setItem("cq_token", data.token);
      localStorage.setItem("cq_current_user", JSON.stringify({
        id: data.id,
        username: data.username,
        email: data.email,
        fullName: data.fullName,
        collegeName: data.collegeName || "CodeQuest Administration",
        role: "ROLE_ADMIN",
        currentStreak: 0,
        longestStreak: 0,
        totalPoints: 0,
        levelTitle: "CodeQuest Administrator"
      }));

      return { success: true, user: data };
    } catch (err) {
      return { success: false, message: err.message || "Administrator setup failed. Please check your setup token." };
    }
  },

  logout() {
    localStorage.removeItem("cq_token");
    localStorage.removeItem("cq_current_user");
    window.location.href = "login.html";
  },

  requireAuth() {
    if (!this.isLoggedIn()) {
      window.location.href = "login.html";
    }
  },

  requireAdmin() {
    this.requireAuth();
    if (!this.isAdmin()) {
      window.location.href = "dashboard.html";
    }
  }
};
