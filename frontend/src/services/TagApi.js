import axios from 'axios';
import {getToken} from '../utils/Token'

const API_URL = 'http://localhost:8080/api/tags';

const token = getToken();

const headers = {
    headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
    }
}

export const getAllTags = () => axios.get(`${API_URL}`, headers);

