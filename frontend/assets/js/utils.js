// Utility helpers used across frontend

/**
 * Decode JWT payload safely (no signature verification)
 * returns payload object or null
 */
export function parseJwt(token) {
    if (!token) return null;
    try {
        const payload = token.split('.')[1];
        const decoded = atob(payload.replace(/-/g,'+').replace(/_/g,'/'));
        // decodeURIComponent + escape to handle UTF-8 chars safely
        return JSON.parse(decodeURIComponent(escape(decoded)));
    } catch (e) {
        console.warn("Failed to parse JWT", e);
        return null;
    }
}

/**
 * Simple alert (bootstrap) inserted into target element
 */
export function showAlert(message, type = "info", target = document.body, timeout = 4000) {
    const div = document.createElement("div");
    div.className = `alert alert-${type} alert-dismissible fade show`;
    div.role = "alert";
    div.innerHTML = `
    ${message}
    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
  `;
    target.prepend(div);
    if (timeout > 0) {
        setTimeout(() => {
            try { bootstrap.Alert.getOrCreateInstance(div).close(); } catch {}
        }, timeout);
    }
}

/**
 * Save JWT and optional user info to localStorage
 */
export function saveAuthToken(token) {
    localStorage.setItem("jwtToken", token);
    const payload = parseJwt(token);
    if (payload && payload.sub) {
        localStorage.setItem("username", payload.sub);
        // Keep roles for quick check (server uses 'roles' claim as comma-separated)
        if (payload.roles) localStorage.setItem("roles", payload.roles);
    }
}

/**
 * Clear auth
 */
export function clearAuth() {
    localStorage.removeItem("jwtToken");
    localStorage.removeItem("username");
    localStorage.removeItem("roles");
}

/**
 * Get role array from stored token
 */
export function getRolesFromStorage() {
    const rolesRaw = localStorage.getItem("roles");
    if (!rolesRaw) return [];
    return rolesRaw.split(",").map(r => r.trim()).filter(Boolean);
}
