import axios from 'axios';

const API_URL = 'http://localhost:8080/api/tags';


export const getAllTags = () => axios.get(`${API_URL}`);

