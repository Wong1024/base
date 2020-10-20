import Vue from 'vue';
import VueRouter from 'vue-router';
import SecurityLayout from '@/layouts/SecurityLayout.vue';
import BasicLayout from '@/layouts/BasicLayout.vue';
import Error404 from '@/views/Error404.vue';
import Error403 from '@/views/Error403.vue';
import Error401 from '@/views/Error401.vue';
import Login from '@/views/login/Login.vue';

Vue.use(VueRouter);

const routes = [
  {
    path: '/login',
    component: Login,
  },
  {
    path: '/',
    component: SecurityLayout,
    children: [
      {
        path: '',
        component: BasicLayout,
        children: [
          {
            path: 'error403',
            component: Error403,
          },
          {
            path: '',
            component: () => import('@/views/home/Home.vue'),
          },
          {
            path: 'system/userAdmin',
            component: () => import('@/views/system/user/UserAdmin.vue'),
          },
          {
            path: 'system/roleAdmin',
            component: () => import('@/views/system/role/RoleAdmin.vue'),
          },
          {
            path: 'system/departAdmin',
            component: () => import('@/views/system/depart/DepartAdmin.vue'),
          },
          {
            path: 'system/menuAdmin',
            component: () => import('@/views/system/menu/MenuAdmin.vue'),
          },
          {
            path: 'system/permissionAdmin',
            component: () => import('@/views/system/permission/PermissionAdmin.vue'),
          },
        ],
      },
    ],
  },
  {
    path: '/error401',
    component: Error401,
  },
  {
    path: '*',
    component: Error404,
  },
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
});

export default router;
