// Centralized API helper for ShoeStock frontend
// Usage: api.get('/shoes'), api.post('/auth/login', body), api.upload('/shoes/{id}/image', file)
// Build API base depending on how the page is opened
let API_BASE = "/api";
if (typeof window !== "undefined") {
    const isFile = window.location.protocol === "file:";
    if (isFile) {
        // If opened from file://, default to local backend port
        API_BASE = "http://localhost:8081/api";
    } else if (window.location.origin) {
        API_BASE = window.location.origin + "/api";
    }
}

function getAuthToken() {
    return localStorage.getItem("jwtToken") || null;
}

function buildHeaders(isJson = true) {
    const headers = {};
    if (isJson) headers["Content-Type"] = "application/json";
    const token = getAuthToken();
    if (token) headers["Authorization"] = "Bearer " + token;
    return headers;
}

async function handleFetch(response) {
    const contentType = response.headers.get("content-type") || "";
    let data = null;
    if (contentType.includes("application/json")) {
        data = await response.json();
    } else {
        data = await response.text();
    }

    if (!response.ok) {
        const err = new Error(data?.message || response.statusText || "Request failed");
        err.status = response.status;
        err.payload = data;
        throw err;
    }
    return data;
}

export const api = {
    get: async (path) => {
        const res = await fetch(API_BASE + path, {
            method: "GET",
            headers: buildHeaders(true),
            credentials: "include"
        });
        return handleFetch(res);
    },

    post: async (path, body) => {
        const res = await fetch(API_BASE + path, {
            method: "POST",
            headers: buildHeaders(true),
            credentials: "include",
            body: JSON.stringify(body)
        });
        return handleFetch(res);
    },

    put: async (path, body) => {
        const res = await fetch(API_BASE + path, {
            method: "PUT",
            headers: buildHeaders(true),
            credentials: "include",
            body: JSON.stringify(body)
        });
        return handleFetch(res);
    },

    delete: async (path) => {
        const res = await fetch(API_BASE + path, {
            method: "DELETE",
            headers: buildHeaders(true),
            credentials: "include"
        });
        return handleFetch(res);
    },

    // For form-data file uploads (images)
    upload: async (path, formData) => {
        const token = getAuthToken();
        const headers = token ? { "Authorization": "Bearer " + token } : {};
        const res = await fetch(API_BASE + path, {
            method: "POST",
            headers,
            credentials: "include",
            body: formData
        });
        return handleFetch(res);
    },

    // Download binary resource (like image) -> returns blob
    downloadBlob: async (path) => {
        const token = getAuthToken();
        const res = await fetch(API_BASE + path, {
            method: "GET",
            headers: token ? { "Authorization": "Bearer " + token } : {},
            credentials: "include"
        });
        if (!res.ok) {
            const txt = await res.text();
            throw new Error(txt || "Download failed");
        }
        return res.blob();
    }
};
