import axios from 'axios';
import {getToken} from '../utils/Token'

const API_URL = 'http://localhost:8080/api/notes';

const token = getToken();

const headers = {
    headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
    }
}

export const createNote = (note) => axios.post(API_URL, note, headers);//
export const getNoteById = (id) => axios.get(`${API_URL}/${id}`, headers);
export const updateNote = (id, note) => axios.put(`${API_URL}/${id}`, note, headers);//
export const deleteNote = (id) => axios.delete(`${API_URL}/${id}`, headers);//
export const getActiveNotes = () => axios.get(`${API_URL}/active`, headers);//
export const getArchivedNotes = () => axios.get(`${API_URL}/archived`, headers);//
export const archiveNote = (id) => axios.patch(`${API_URL}/archive/${id}`, {}, headers);//** *
export const unarchiveNote = (id) => axios.patch(`${API_URL}/unarchive/${id}`, {}, headers);
export const assignTagToNote = (noteId, tagId) => axios.patch(`${API_URL}/${noteId}/addTag/${tagId}`, {}, headers);//** *
export const removeTagToNote = (noteId, tagId) => axios.patch(`${API_URL}/${noteId}/removeTag/${tagId}`, {}, headers);
export const activeNotesByTagId = ( tagId) => axios.get(`${API_URL}/activesByTag/${tagId}`, headers);

