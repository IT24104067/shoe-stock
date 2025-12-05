import { api } from "./api.js";
import { saveAuthToken, parseJwt, showAlert, clearAuth, getRolesFromStorage } from "./utils.js";

/**
 * login(username, password)
 * expects backend /api/auth/login return { token } OR {token: '...'}
 */
export async function login(username, password) {
    try {
        const res = await api.post("/auth/login", { username, password });
        // backend in previous design returned {"token": "..."} or {"token": "..."}
        const token = res.token || res?.token || res?.accessToken || res;
        if (!token) throw new Error("No token returned from server");
        saveAuthToken(token);

        // role based redirect
        const payload = parseJwt(token);
        const rolesRaw = payload?.roles || payload?.role || "";
        const roles = typeof rolesRaw === "string" ? rolesRaw.split(",") : (Array.isArray(rolesRaw) ? rolesRaw : []);
        // priority: MANAGER, IT, ASSISTANT, SALESMAN, USER
        if (roles.includes("ROLE_MANAGER") || roles.includes("ROLE_IT")) {
            window.location.href = "../views/dashboard-manager.html";
        } else if (roles.includes("ROLE_ASSISTANT")) {
            window.location.href = "../views/dashboard-assistant.html";
        } else if (roles.includes("ROLE_SALESMAN")) {
            window.location.href = "../views/dashboard-salesman.html";
        } else {
            window.location.href = "../views/dashboard-user.html";
        }
    } catch (err) {
        const message = err?.payload?.message || err.message || "Login failed";
        showAlert(message, "danger", document.querySelector(".card") || document.body);
        throw err;
    }
}

/**
 * registerUser(username, password, displayName)
 * calls /api/auth/register
 */
export async function registerUser(username, password, displayName) {
    try {
        const res = await api.post("/auth/register", { username, password, displayName });
        showAlert("Account created. Please login.", "success", document.querySelector(".card") || document.body);
        return res;
    } catch (err) {
        const message = err?.payload?.message || err.message || "Register failed";
        showAlert(message, "danger", document.querySelector(".card") || document.body);
        throw err;
    }
}

/**
 * logout
 */
export function logoutAndRedirect() {
    clearAuth();
    window.location.href = "../index.html";
}

/**
 * quick helper: redirect to login if not auth
 */
export function requireAuth(redirectToLogin = true) {
    const token = localStorage.getItem("jwtToken");
    if (!token) {
        if (redirectToLogin) window.location.href = "../views/login.html";
        return false;
    }
    return true;
}
