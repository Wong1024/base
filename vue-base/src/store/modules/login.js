import { fetchLoginUser, fetchLoginMenuTree } from '@/api/login';

export default {
  namespaced: true,
  state: () => ({
    loginUser: {
      id: '',
      username: '',
      nickname: '未登录',
    },
    menuTree: [],
  }),
  getters: {
  },
  actions: {
    loadLoginUser({ commit }) {
      fetchLoginUser().then((data) => {
        commit('setLoginUser', data);
      });
    },
    loadLoginMenuTree({ commit }) {
      fetchLoginMenuTree().then((data) => {
        commit('setMenuTree', data);
      });
    },
  },
  mutations: {
    setLoginUser(state, data) {
      state.loginUser = data;
    },
    setMenuTree(state, data) {
      state.menuTree = data;
    },
  },
};
