import axios from './axios';

export function fetchPermissionById(id) {
  return axios.get(`/api/permissions/${id}`);
}

export function fetchPermissions(data) {
  return axios.get('/api/permissions', data);
}

export function addPermission(data) {
  return axios.post('/api/permissions', data);
}

export function updatePermission(data) {
  return axios.put(`/api/permissions/${data.id}`, data);
}

export function removePermission(id) {
  return axios.delete(`/api/permissions/${id}`);
}
