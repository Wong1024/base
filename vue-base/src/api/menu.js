import axios from './axios';

export function fetchMenuById(id) {
  return axios.get(`/api/menus/${id}`);
}

export function fetchMenus(data) {
  return axios.get('/api/menus', data);
}

export function addMenu(data) {
  return axios.post('/api/menus', data);
}

export function updateMenu(data) {
  return axios.put(`/api/menus/${data.id}`, data);
}

export function removeMenu(id) {
  return axios.delete(`/api/menus/${id}`);
}

export function fetchPermissionIdsByRoleId(menuId) {
  return axios.get(`/api/menus/${menuId}/permissionIds`);
}

export function updatePermissionIdsByRoleId(menuId, permissionIds) {
  return axios.put(`/api/menus/${menuId}/permissionIds`, permissionIds);
}
