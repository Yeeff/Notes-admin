import axios from 'axios';

const API_URL = 'http://localhost:8080/api/notes';

export const createNote = (note) => axios.post(API_URL, note);
export const getNoteById = (id) => axios.get(`${API_URL}/${id}`);
export const updateNote = (id, note) => axios.put(`${API_URL}/${id}`, note);
export const deleteNote = (id) => axios.delete(`${API_URL}/${id}`);
export const getActiveNotes = () => axios.get(`${API_URL}/active`);
export const getArchivedNotes = () => axios.get(`${API_URL}/archived`);
export const archiveNote = (id) => axios.patch(`${API_URL}/archive/${id}`);
export const unarchiveNote = (id) => axios.patch(`${API_URL}/unarchive/${id}`);
