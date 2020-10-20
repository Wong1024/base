import { fetchRoles, fetchRolePage } from '@/api/role';

export default {
  namespaced: true,
  state: () => ({
    all: {},
    ids: [],
    currentPageIds: [],
    totalCount: 0,
  }),
  getters: {
    currentPageRoles(state) {
      return state.currentPageIds.map((id) => state.all[id]);
    },
    allRoles(state) {
      return state.ids.map((id) => state.all[id]);
    },
  },
  actions: {
    loadPage({ commit }, { page, size }) {
      fetchRolePage(page, size).then((data) => {
        commit('setRolePage', data);
      });
    },
    loadAll({ commit }) {
      fetchRoles().then((data) => {
        commit('setRoles', data);
      });
    },
  },
  mutations: {
    setRolePage(state, { content, totalElements }) {
      content.forEach((item) => {
        state.all[item.id] = item;
      });
      state.currentPageIds = content.map((item) => item.id);
      state.totalCount = totalElements;
    },
    setRoles(state, data) {
      data.forEach((item) => {
        state.all[item.id] = item;
      });
      state.ids = data.map((item) => item.id);
    },
  },
};
