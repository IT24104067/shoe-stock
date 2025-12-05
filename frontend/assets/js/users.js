import { api } from "./api.js";
import { showAlert } from "./utils.js";

/**
 * Load all users
 * IT Technician sees all
 * Manager/Assistant/Salesman/User may see limited info if backend allows
 */
export async function loadUsers() {
    try {
        return await api.get("/users");
    } catch (err) {
        console.error(err);
        showAlert("Failed to load users", "danger");
        return [];
    }
}

/**
 * Create a new user (IT Technician only)
 * body = { username, password, roles: [] }
 */
export async function createUser(body) {
    try {
        return await api.post("/users", body);
    } catch (err) {
        console.error(err);
        showAlert("Failed to create user", "danger");
        throw err;
    }
}

/**
 * Edit existing user
 * body = { username?, roles? }  // password can be changed separately
 */
export async function editUser(userId, body) {
    try {
        return await api.put(`/users/${userId}`, body);
    } catch (err) {
        console.error(err);
        showAlert("Failed to update user", "danger");
        throw err;
    }
}

/**
 * Delete user (IT Technician only)
 */
export async function deleteUser(userId) {
    try {
        return await api.delete(`/users/${userId}`);
    } catch (err) {
        console.error(err);
        showAlert("Failed to delete user", "danger");
        throw err;
    }
}
