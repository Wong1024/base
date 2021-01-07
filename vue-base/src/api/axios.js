import axios from 'axios';

const instance = axios.create({
});

instance.interceptors.request.use(
  (config) => config,
  (err) => Promise.reject(err),
);

instance.interceptors.response.use(({ data }) => {
  if (!data.success) {
    return Promise.reject(data);
  }
  return data.data;
}, (error) => {
  if (error.response.data.code === 4010) {
    window.location.href = '/login';
  }
  // 请求错误时做些事
  if (error.response && error.response.data) {
    return Promise.reject(error.response.data);
  }
  return Promise.reject(error.message);
});

export default instance;
