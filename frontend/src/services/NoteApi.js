import axios from 'axios';
import { getToken } from '../utils/Token';

const API_URL = 'http://localhost:8080/api/notes';

const getHeaders = () => {
    const token = getToken();
    return {
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
        }
    };
}

export const createNote = (note) => axios.post(API_URL, note, getHeaders());
export const getNoteById = (id) => axios.get(`${API_URL}/${id}`, getHeaders());
export const updateNote = (id, note) => axios.put(`${API_URL}/${id}`, note, getHeaders());
export const deleteNote = (id) => axios.delete(`${API_URL}/${id}`, getHeaders());
export const getActiveNotes = () => axios.get(`${API_URL}/active`, getHeaders());
export const getArchivedNotes = () => axios.get(`${API_URL}/archived`, getHeaders());
export const archiveNote = (id) => axios.patch(`${API_URL}/archive/${id}`, {}, getHeaders());
export const unarchiveNote = (id) => axios.patch(`${API_URL}/unarchive/${id}`, {}, getHeaders());
export const assignTagToNote = (noteId, tagId) => axios.patch(`${API_URL}/${noteId}/addTag/${tagId}`, {}, getHeaders());
export const removeTagToNote = (noteId, tagId) => axios.patch(`${API_URL}/${noteId}/removeTag/${tagId}`, {}, getHeaders());
export const activeNotesByTagId = (tagId) => axios.get(`${API_URL}/activesByTag/${tagId}`, getHeaders());
