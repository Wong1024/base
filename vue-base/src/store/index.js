import Vue from 'vue';
import Vuex from 'vuex';
import user from './modules/user';
import role from './modules/role';
import depart from './modules/depart';
import menu from './modules/menu';
import permission from './modules/permission';
import login from './modules/login';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    user,
    role,
    depart,
    menu,
    permission,
    login,
  },
});
