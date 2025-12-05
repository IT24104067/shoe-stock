// Chat frontend helper (polling-based)
// Exports functions to load threads, open a thread, send messages, and poll for updates.

import { api } from "./api.js";
import { showAlert, parseJwt } from "./utils.js";

const POLL_MS = 5000; // polling interval

// Keep local state
let currentThreadId = null;
let pollHandle = null;
let lastMessageTimestamp = null;

/**
 * Load available threads for the logged-in user (manager sees all)
 * returns array of threads: { id, subject, participants, lastMessageAt, unreadCount }
 */
export async function loadThreads() {
    try {
        const threads = await api.get("/chat/threads");
        return threads;
    } catch (err) {
        console.error("loadThreads failed", err);
        showAlert("Failed to load chat threads", "danger");
        return [];
    }
}

/**
 * Create a new thread (participants: array of userIds)
 * body: { participantIds: [...], subject }
 */
export async function createThread(participantIds = [], subject = "") {
    try {
        const payload = { participantIds, subject };
        return await api.post("/chat/threads", payload);
    } catch (err) {
        console.error("createThread failed", err);
        showAlert("Failed to create thread", "danger");
        throw err;
    }
}

/**
 * Load messages for a given threadId
 * returns array of messages: { id, senderId, senderName, text, createdAt }
 */
export async function loadThreadMessages(threadId) {
    try {
        const msgs = await api.get(`/chat/threads/${threadId}`);
        // update lastMessageTimestamp
        if (msgs && msgs.length) {
            lastMessageTimestamp = msgs[msgs.length - 1].createdAt;
        }
        return msgs;
    } catch (err) {
        console.error("loadThreadMessages failed", err);
        showAlert("Failed to load messages", "danger");
        return [];
    }
}

/**
 * Send a message in a thread
 * body: { text }
 */
export async function sendMessage(threadId, text) {
    if (!text || !text.trim()) throw new Error("Empty message");
    try {
        const res = await api.post(`/chat/threads/${threadId}/messages`, { text });
        return res;
    } catch (err) {
        console.error("sendMessage failed", err);
        showAlert("Failed to send message", "danger");
        throw err;
    }
}

/**
 * Polling: check for new messages for current thread
 * calls onNewMessages callback with array of new*/
