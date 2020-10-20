import axios from './axios';

export function fetchRoleById(id) {
  return axios.get(`/api/roles/${id}`);
}

export function fetchRolePage(page, size) {
  return axios.get('/api/rolesPage', { params: { page, size } });
}

export function fetchRoles() {
  return axios.get('/api/roles');
}

export function addRole(data) {
  return axios.post('/api/roles', data);
}

export function updateRole(data) {
  return axios.put(`/api/roles/${data.id}`, data);
}

export function removeRole(id) {
  return axios.delete(`/api/roles/${id}`);
}

export function fetchMenuIdsByRoleId(roleId) {
  return axios.get(`/api/roles/${roleId}/menuIds`);
}

export function updateRoleMenusByRoleId(roleId, menuIds) {
  return axios.put(`/api/roles/${roleId}/menuIds`, menuIds);
}
