import axios from 'axios';

const API_URL = 'http://localhost:8080/api/auth';

export const login = (credentials) => axios.post(`${API_URL}/login`, credentials);

