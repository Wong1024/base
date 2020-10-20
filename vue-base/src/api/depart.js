import axios from './axios';

export function fetchDepartById(id) {
  return axios.get(`/api/departs/${id}`);
}

export function fetchDeparts(data) {
  return axios.get('/api/departs', data);
}

export function addDepart(data) {
  return axios.post('/api/departs', data);
}

export function updateDepart(data) {
  return axios.put(`/api/departs/${data.id}`, data);
}

export function removeDepart(id) {
  return axios.delete(`/api/departs/${id}`);
}
