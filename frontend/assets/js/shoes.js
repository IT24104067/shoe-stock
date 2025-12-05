import { api } from "./api.js";
import { showAlert } from "./utils.js";

/**
 * Fetch all shoes for display
 */
export async function loadAllShoes() {
    try {
        const shoes = await api.get("/shoes");
        return shoes;
    } catch (err) {
        console.error(err);
        showAlert("Unable to load shoes", "danger");
        return [];
    }
}

/**
 * Fetch one shoe by ID
 */
export async function getShoeById(id) {
    return api.get(`/shoes/${id}`);
}

/**
 * Add new shoe (without image)
 * body = { name, brand, description, sizes: [{size, price, count}] }
 */
export async function addShoe(body) {
    return api.post("/shoes", body);
}

/**
 * Edit shoe
 */
export async function editShoe(id, body) {
    try {
        return await api.put(`/shoes/${id}`, body);
    } catch(err) {
        console.error(err);
        showAlert("Failed to edit shoe", "danger");
        throw err;
    }
}

/**
 * Update shoe info
 */
export async function updateShoe(id, body) {
    return api.put(`/shoes/${id}`, body);
}

/**
 * Delete shoe
 */
export async function deleteShoe(id) {
    try {
        return await api.delete(`/shoes/${id}`);
    } catch(err) {
        console.error(err);
        showAlert("Failed to delete shoe", "danger");
        throw err;
    }
}

/**
 * Upload image for a shoe
 */
export async function uploadShoeImage(id, file) {
    const formData = new FormData();
    formData.append("file", file);
    return api.upload(`/shoes/${id}/image`, formData);
}

/**
 * Create new shoe
 * body: { name, sizes: [{size, price, count}], imageUrl }
 */
export async function createShoe(body) {
    try {
        return await api.post("/shoes", body);
    } catch(err) {
        console.error(err);
        showAlert("Failed to create shoe", "danger");
        throw err;
    }
}
