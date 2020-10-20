import { fetchPermissions } from '@/api/permission';

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
    permissionTree(state) {
      return buildTreeChildren('0', state.ids.map((id) => state.all[id]));
    },
    allPermissions(state) {
      return state.ids.map((id) => state.all[id]);
    },
  },
  actions: {
    loadAll({ commit }) {
      return fetchPermissions().then((data) => {
        commit('setPermissions', data);
      });
    },
  },
  mutations: {
    setPermissions(state, data) {
      data.forEach((item) => {
        state.all[item.id] = item;
      });
      state.ids = data.map((item) => item.id);
    },
  },
};
