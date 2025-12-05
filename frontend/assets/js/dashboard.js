import { api } from "./api.js";
import { showAlert } from "./utils.js";

/**
 * Dashboard Cards Loader
 */
export async function loadDashboard() {
    try {
        const res = await api.get("/dashboard/stats");
        return res;
    } catch (err) {
        console.error("Dashboard Load Error", err);
        showAlert("Unable to load dashboard stats!", "danger");
    }
}
