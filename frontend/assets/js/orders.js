import { api } from "./api.js";
import { showAlert } from "./utils.js";

/**
 * Load all orders
 * (Manager sees all, Salesman sees his orders, User sees his orders)
 */
export async function loadOrders() {
    try {
        return await api.get("/orders");
    } catch (err) {
        console.error(err);
        showAlert("Failed to load orders", "danger");
        return [];
    }
}

/**
 * Get single order details
 */
export async function getOrderById(id) {
    return api.get(`/orders/${id}`);
}

/**
 * Place new order
 */
export async function placeOrder(orderBody) {
    return api.post("/orders", orderBody);
}

/**
 * Update order status
 * (Manager + Salesman only)
 */
export async function updateOrderStatus(id, status) {
    return api.put(`/orders/${id}/status`, { status });
}

/**
 * Return items (stock increases)
 */
export async function returnOrder(id) {
    return api.post(`/orders/${id}/return`, {});
}

/**
 * Create new order
 * body: { shoeId, size, quantity }
 */
export async function createOrder(body) {
    try {
        return await api.post("/orders", body);
    } catch(err) {
        console.error(err);
        showAlert("Failed to create order", "danger");
        throw err;
    }
}

/**
 * Return order → increment stock
 */
export async function returnOrder(orderId) {
    try {
        return await api.post(`/orders/${orderId}/return`);
    } catch(err) {
        console.error(err);
        showAlert("Failed to return order", "danger");
        throw err;
    }
}

/**
 * Load low-stock shoes (≤10)
 */
export async function loadLowStockShoes() {
    try {
        return await api.get("/shoes/low-stock");
    } catch(err) {
        console.error(err);
        return [];
    }
}
