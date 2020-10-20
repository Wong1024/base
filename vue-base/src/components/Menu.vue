<template>
  <el-menu
      router
      :default-active="activePath"
  >
    <MenuTree :node-list="menuData" />
  </el-menu>
</template>

<script>
import { mapState } from 'vuex';
import MenuTree from './MenuTree.vue';

export default {
  name: 'Menu',
  components: { MenuTree },
  data() {
    return {
      activePath: '/',
    };
  },
  computed: {
    ...mapState('login', {
      menuData: 'menuTree',
    }),
  },
  mounted() {
    this.activePath = window.location.pathname;
  },
};
</script>

<style scoped>

</style>
