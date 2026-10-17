// =====================================================================
// CodeQuest: Shared App Framework
// Dynamic navigation, Theme toggle, Toasts, Backend connectivity badge
// =====================================================================

document.addEventListener("DOMContentLoaded", () => {
  initTheme();
  renderNavbar();
  renderFooter();
  checkBackendStatus();
});

// Theme Management
function initTheme() {
  const savedTheme = localStorage.getItem("cq_theme") || "light";
  document.documentElement.setAttribute("data-theme", savedTheme);
}

function toggleTheme() {
  const current = document.documentElement.getAttribute("data-theme") || "light";
  const next = current === "dark" ? "light" : "dark";
  document.documentElement.setAttribute("data-theme", next);
  localStorage.setItem("cq_theme", next);
  updateThemeIcon(next);
}

function updateThemeIcon(theme) {
  const btn = document.getElementById("theme-toggle-btn");
  if (btn) {
    btn.innerHTML = theme === "dark" ? "☀️" : "🌙";
    btn.setAttribute("title", theme === "dark" ? "Switch to Light Mode" : "Switch to Dark Mode");
  }
}

// Check Backend Connection Status
async function checkBackendStatus() {
  const isLive = await CodeQuestAPI.checkBackendHealth();
  const indicator = document.getElementById("backend-status-badge");
  if (indicator) {
    if (isLive) {
      indicator.innerHTML = `<span style="display:inline-block;width:8px;height:8px;border-radius:50%;background:#10B981;margin-right:5px;"></span> Live API Connected`;
      indicator.className = "badge bg-success-subtle text-success border border-success-subtle";
    } else {
      indicator.innerHTML = `<span style="display:inline-block;width:8px;height:8px;border-radius:50%;background:#06B6D4;margin-right:5px;"></span> Standalone / Preview Mode`;
      indicator.className = "badge bg-info-subtle text-info border border-info-subtle";
    }
  }
}

// Dynamic Navigation Header
function renderNavbar() {
  const navContainer = document.getElementById("navbar-placeholder");
  if (!navContainer) return;

  const user = Auth.getCurrentUser();
  const isLoggedIn = Auth.isLoggedIn();
  const isAdmin = Auth.isAdmin();
  const currentPath = window.location.pathname.split("/").pop() || "index.html";

  const publicLinks = `
    <li><a href="index.html" class="nav-link-item ${currentPath === 'index.html' ? 'active' : ''}">Home</a></li>
    <li><a href="learn.html" class="nav-link-item ${currentPath === 'learn.html' ? 'active' : ''}">Learn Java</a></li>
    <li><a href="leaderboard.html" class="nav-link-item ${currentPath === 'leaderboard.html' ? 'active' : ''}">Leaderboard</a></li>
  `;

  const studentLinks = `
    <li><a href="dashboard.html" class="nav-link-item ${currentPath === 'dashboard.html' ? 'active' : ''}">Dashboard</a></li>
    <li><a href="learn.html" class="nav-link-item ${currentPath === 'learn.html' || currentPath === 'lesson.html' ? 'active' : ''}">Syllabus</a></li>
    <li><a href="streaks.html" class="nav-link-item ${currentPath === 'streaks.html' ? 'active' : ''}">Streaks</a></li>
    <li><a href="mistakes.html" class="nav-link-item ${currentPath === 'mistakes.html' ? 'active' : ''}">Mistake Notebook</a></li>
    <li><a href="challenges.html" class="nav-link-item ${currentPath === 'challenges.html' ? 'active' : ''}">Challenges</a></li>
    <li><a href="leaderboard.html" class="nav-link-item ${currentPath === 'leaderboard.html' ? 'active' : ''}">Rankings</a></li>
    <li><a href="doubts.html" class="nav-link-item ${currentPath === 'doubts.html' ? 'active' : ''}">Doubts</a></li>
  `;

  const adminLinks = `
    <li><a href="admin.html" class="nav-link-item ${currentPath === 'admin.html' ? 'active' : ''}">Admin Control Center</a></li>
    <li><a href="learn.html" class="nav-link-item ${currentPath === 'learn.html' ? 'active' : ''}">Course Syllabus</a></li>
    <li><a href="leaderboard.html" class="nav-link-item ${currentPath === 'leaderboard.html' ? 'active' : ''}">Leaderboard</a></li>
  `;

  let userSection = "";
  if (isLoggedIn) {
    const streakVal = (user && typeof user.currentStreak === 'number') ? user.currentStreak : 0;
    const pointsVal = (user && typeof user.totalPoints === 'number') ? user.totalPoints : 0;
    userSection = `
      <div class="d-flex align-items-center gap-2">
        ${!isAdmin ? `
          <a href="streaks.html" class="streak-badge text-decoration-none" title="Current Active Streak">
            🔥 <span>${streakVal}d</span>
          </a>
          <a href="profile.html" class="points-badge text-decoration-none" title="Total Experience Points">
            ⚡ <span>${pointsVal.toLocaleString()} XP</span>
          </a>
        ` : `
          <span class="badge bg-danger text-white px-2 py-1">ADMINISTRATOR</span>
        `}

        <div class="dropdown ms-2">
          <button class="btn btn-sm btn-outline-secondary dropdown-toggle d-flex align-items-center gap-2 rounded-pill px-3 py-1" type="button" data-bs-toggle="dropdown">
            <span class="rounded-circle bg-primary text-white d-flex align-items-center justify-content-center" style="width:26px;height:26px;font-size:0.8rem;font-weight:700;">
              ${(user.fullName || 'User').charAt(0)}
            </span>
            <span class="fw-semibold d-none d-md-inline">${user.fullName || 'User'}</span>
          </button>
          <ul class="dropdown-menu dropdown-menu-end shadow border-0" style="min-width: 200px;">
            <li class="px-3 py-2 border-bottom">
              <div class="fw-bold">${user.fullName || 'User'}</div>
              <div class="text-muted small">${user.role === 'ROLE_ADMIN' ? 'Professor / Administrator' : (user.collegeName || 'Student')}</div>
            </li>
            ${!isAdmin ? `
              <li><a class="dropdown-item py-2" href="profile.html">👤 My Profile & Badges</a></li>
              <li><a class="dropdown-item py-2" href="streaks.html">🔥 My Daily Streak</a></li>
              <li><a class="dropdown-item py-2" href="mistakes.html">📖 Mistake Notebook</a></li>
              <li><a class="dropdown-item py-2" href="friends.html">👥 Friends</a></li>
              <li><a class="dropdown-item py-2" href="feedback.html">💬 Submit Feedback</a></li>
            ` : `
              <li><a class="dropdown-item py-2" href="admin.html">⚙️ Admin Dashboard</a></li>
            `}
            <li><hr class="dropdown-divider my-1"></li>
            <li><a class="dropdown-item text-danger py-2" href="#" onclick="Auth.logout()">🚪 Sign Out</a></li>
          </ul>
        </div>
      </div>
    `;
  } else {
    userSection = `
      <div class="d-flex align-items-center gap-2">
        <a href="admin-setup.html" class="btn btn-outline-secondary btn-sm d-none d-lg-inline-block rounded-pill px-3">Admin Setup</a>
        <a href="login.html" class="btn-cq btn-cq-secondary btn-sm">Log In</a>
        <a href="register.html" class="btn-cq btn-cq-primary btn-sm">Sign Up Free</a>
      </div>
    `;
  }

  const currentTheme = document.documentElement.getAttribute("data-theme") || "light";

  navContainer.innerHTML = `
    <nav class="navbar-custom">
      <div class="container-fluid d-flex align-items-center justify-content-between px-md-4">
        <a href="index.html" class="brand-logo">
          <div class="brand-icon">&lt;/&gt;</div>
          <span>CodeQuest</span>
        </a>

        <ul class="nav-links mb-0">
          ${isLoggedIn ? (isAdmin ? adminLinks : studentLinks) : publicLinks}
        </ul>

        <div class="d-flex align-items-center gap-2">
          <span id="backend-status-badge" class="d-none d-xl-inline-block small"></span>
          <button id="theme-toggle-btn" class="btn btn-sm btn-light border rounded-circle p-2" onclick="toggleTheme()" title="Toggle Dark/Light Mode" style="width:36px;height:36px;display:flex;align-items:center;justify-content:center;">
            ${currentTheme === 'dark' ? '☀️' : '🌙'}
          </button>
          ${userSection}
        </div>
      </div>
    </nav>
  `;
}

// Dynamic Footer
function renderFooter() {
  const footerContainer = document.getElementById("footer-placeholder");
  if (!footerContainer) return;

  footerContainer.innerHTML = `
    <footer class="footer-custom">
      <div class="container">
        <div class="row g-4 align-items-center">
          <div class="col-md-6 text-center text-md-start">
            <div class="d-flex align-items-center gap-2 justify-content-center justify-content-md-start mb-2">
              <span class="brand-icon" style="width:28px;height:28px;font-size:0.75rem;">&lt;/&gt;</span>
              <strong class="fs-5">CodeQuest</strong>
            </div>
            <p class="text-muted small mb-0">
              A Gamified Java Learning and Student Engagement Platform designed for college curriculum, semester exams, and consistent programming practice.
            </p>
          </div>
          <div class="col-md-6 text-center text-md-end">
            <div class="d-flex justify-content-center justify-content-md-end gap-3 small mb-2">
              <a href="learn.html" class="text-muted text-decoration-none">Syllabus</a>
              <a href="leaderboard.html" class="text-muted text-decoration-none">Leaderboard</a>
              <a href="doubts.html" class="text-muted text-decoration-none">Ask a Doubt</a>
              <a href="feedback.html" class="text-muted text-decoration-none">Feedback</a>
            </div>
            <div class="text-muted small">
              &copy; 2026 CodeQuest Platform. Built with Spring Boot & Modern Web Technologies.
            </div>
          </div>
        </div>
      </div>
    </footer>
  `;
}

// Global Toast Notifications
function showToast(message, type = "success") {
  let toastContainer = document.getElementById("cq-toast-container");
  if (!toastContainer) {
    toastContainer = document.createElement("div");
    toastContainer.id = "cq-toast-container";
    toastContainer.className = "toast-container position-fixed bottom-0 end-0 p-3";
    toastContainer.style.zIndex = "9999";
    document.body.appendChild(toastContainer);
  }

  const toastEl = document.createElement("div");
  const bgClass = type === "success" ? "bg-success text-white" : type === "danger" ? "bg-danger text-white" : "bg-primary text-white";
  toastEl.className = `toast align-items-center ${bgClass} border-0 shadow-lg`;
  toastEl.setAttribute("role", "alert");
  toastEl.innerHTML = `
    <div class="d-flex">
      <div class="toast-body fw-medium">${message}</div>
      <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
    </div>
  `;

  toastContainer.appendChild(toastEl);
  const bsToast = new bootstrap.Toast(toastEl, { delay: 4000 });
  bsToast.show();
  toastEl.addEventListener("hidden.bs.toast", () => toastEl.remove());
}
