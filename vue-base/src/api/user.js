import axios from './axios';

export function fetchUserById(id) {
  return axios.get(`/api/users/${id}`);
}

export function fetchUserPage(page, size) {
  return axios.get('/api/usersPage', { params: { page, size } });
}

export function fetchAllUser() {
  return axios.get('/api/users');
}

export function addUser(data) {
  return axios.post('/api/users', data);
}

export function updateUser(data) {
  return axios.put(`/api/users/${data.id}`, data);
}

export function removeUser(id) {
  return axios.delete(`/api/users/${id}`);
}

export function updateUserState(id, state) {
  return axios.patch(`/api/users/${id}/state`, { state });
}
