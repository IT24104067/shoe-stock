import { api } from "./api.js";
import { showAlert } from "./utils.js";

/**
 * Load all offers for current user
 */
export async function loadOffers() {
    try {
        return await api.get("/offers");
    } catch(err) {
        console.error(err);
        showAlert("Failed to load offers", "danger");
        return [];
    }
}

/**
 * Load pending offers (manager only)
 */
export async function loadPendingOffers() {
    try {
        return await api.get("/offers/pending");
    } catch (err) {
        showAlert("Failed to load pending approvals", "danger");
    }
}

/**
 * Create new offer (Assistant only)
 * body: { shoeId, size, discountPercent, validUntil }
 */
export async function createOffer(body) {
    try {
        return await api.post("/offers", body);
    } catch(err) {
        console.error(err);
        showAlert("Failed to create offer", "danger");
        throw err;
    }
}

/**
 * Approve or reject offer (Manager only)
 * body: { approved: true/false }
 */
export async function approveOffer(offerId, approved) {
    try {
        return await api.put(`/offers/${offerId}/approve`, { approved });
    } catch(err) {
        console.error(err);
        showAlert("Failed to update offer", "danger");
        throw err;
    }
}
/**
 * Assistant deletes own offer
 */
export async function deleteOffer(offerId) {
    return api.delete(`/offers/${offerId}`);
}
