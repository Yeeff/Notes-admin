import axios from 'axios';

const API_URL = 'http://localhost:8080/api/tags';


export const getAllTags = () => axios.get(`${API_URL}`);
export const assignTagToNote = (noteId, tagId) => axios.patch(`${API_URL}/${noteId}/tag/${tagId}`);


