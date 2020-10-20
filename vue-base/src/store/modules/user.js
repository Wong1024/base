import { fetchAllUser, fetchUserPage } from '@/api/user';

export default {
  namespaced: true,
  state: () => ({
    all: {},
    ids: [],
    currentPageIds: [],
    totalCount: 0,
  }),
  getters: {
    currentPageUsers(state) {
      return state.currentPageIds.map((id) => state.all[id]);
    },
    allUsers(state) {
      return state.ids.map((id) => state.all[id]);
    },
  },
  actions: {
    loadPage({ commit }, { page, size }) {
      fetchUserPage(page, size).then((data) => {
        commit('setUserPage', data);
      });
    },
    loadAll({ commit }) {
      fetchAllUser().then((data) => {
        commit('setUsers', data);
      });
    },
  },
  mutations: {
    setUserPage(state, { content, totalElements }) {
      content.forEach((item) => {
        state.all[item.id] = item;
      });
      state.currentPageIds = content.map((item) => item.id);
      state.totalCount = totalElements;
    },
    setUsers(state, data) {
      data.forEach((item) => {
        state.all[item.id] = item;
      });
      state.ids = data.map((item) => item.id);
    },
  },
};
