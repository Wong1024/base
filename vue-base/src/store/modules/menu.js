import { fetchMenus } from '@/api/menu';

function buildTreeChildren(parentId, data) {
  const children = [];
  data.forEach((item) => {
    if (parentId === item.parentId) {
      children.push({
        ...item, children: buildTreeChildren(item.id, data),
      });
    }
  });
  return children;
}

export default {
  namespaced: true,
  state: () => ({
    all: {},
    ids: [],
  }),
  getters: {
    menuTree(state) {
      return buildTreeChildren('0', state.ids.map((id) => state.all[id]));
    },
    allMenus(state) {
      return state.ids.map((id) => state.all[id]);
    },
  },
  actions: {
    loadAll({ commit }) {
      return fetchMenus().then((data) => {
        commit('setMenus', data);
      });
    },
  },
  mutations: {
    setMenus(state, data) {
      data.forEach((item) => {
        state.all[item.id] = item;
      });
      state.ids = data.map((item) => item.id);
    },
  },
};
