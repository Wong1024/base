import axios from 'axios';

const instance = axios.create({
});

instance.interceptors.request.use(
  (config) => config,
  (err) => Promise.reject(err),
);

instance.interceptors.response.use((response) => {
  const { data } = response;
  return data;
}, (error) => {
  if (error.response.status === 401) {
    window.location.href = '/login';
  }
  // 请求错误时做些事
  if (error.response && error.response.data && error.response.data.error) {
    return Promise.reject(error.response.data.error);
  }
  return Promise.reject(error.message);
});

export default instance;
