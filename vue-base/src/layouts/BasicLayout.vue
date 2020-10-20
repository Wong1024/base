<template>
  <el-container>
    <el-header>
      <span>基础系统框架</span>
      <el-dropdown style="float: right; color: #fff;" @command="handleCommand">
        <span class="el-dropdown-link">
          {{loginUser.nickname}}<i class="el-icon-arrow-down el-icon--right"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="checkout">登出</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </el-header>
    <el-container class="main-content">
      <el-aside width="240px">
        <Menu />
      </el-aside>
      <el-container class="base-content">
        <el-main>
          <router-view/>
        </el-main>
        <el-footer>南京莱斯信息技术股份有限公司</el-footer>
      </el-container>
    </el-container>
  </el-container>
</template>

<script>
import { mapState } from 'vuex';
import Menu from '@/components/Menu.vue';
import { doLogout } from '@/api/login';

export default {
  name: 'BasicLayout',
  components: { Menu },
  computed: {
    departTree() {
      return [{
        id: '0',
        departName: '无所属部门',
        children: this.$store.getters['depart/departTree'],
      }];
    },
    ...mapState('login', {
      loginUser: 'loginUser',
    }),
  },
  methods: {
    handleCommand(command) {
      if (command === 'checkout') {
        doLogout().then(() => {
          this.$router.push('/login');
        }).catch((err) => {
          this.$message.error(`登出失败：${err}`);
        });
      }
    },
  },
};
</script>

<style scoped>
.el-header {
  width: 100%;
  background-color: #7952b3;
  color: #fff;
  font-size: 1.2rem;
  font-weight: bold;
  line-height: 4rem;
  min-height: 4rem;
}
.el-dropdown-link {
  cursor: pointer;
}
</style>
