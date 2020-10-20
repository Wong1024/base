import qs from 'qs';
import axios from './axios';

export function doLogin(username, password) {
  return axios.post('/api/login', qs.stringify({ username, password }));
}

export function doLogout() {
  return axios.post('/api/logout');
}

export function fetchLoginUser() {
  return axios.get('/api/login/user');
}

export function fetchLoginMenuTree() {
  return axios.get('/api/login/menuTree');
}
