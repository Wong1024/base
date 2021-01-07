<template>
  <div>
    <router-view/>
  </div>
</template>

<script>
import { mapState } from 'vuex';

export default {
  name: 'SecurityLayout',
  computed: {
    ...mapState('login', {
      menus: 'menuTree',
    }),
  },
  watch: {
    menus() {
      this.checkPath();
    },
  },
  mounted() {
    this.$store.dispatch('login/loadLoginUser');
    this.$store.dispatch('login/loadLoginMenuTree').catch(({ msg }) => {
      this.$message.error(`查询菜单失败：${msg}`);
    });
  },
  updated() {
    this.checkPath();
  },
  methods: {
    checkPath() {
      const path = window.location.pathname;
      if (path.indexOf('/error') === 0) {
        return;
      }
      let tmp = [].concat(this.menus);
      while (tmp.length > 0) {
        const item = tmp.shift();
        if (item.type === 1
            && (item.path === path
                || item.extraPath.split(',').indexOf(path) > -1)) {
          return;
        }
        if (item.children) {
          tmp = tmp.concat(item.children);
        }
      }
      this.$router.replace('/error403');
    },
  },
};
</script>

<style scoped>

</style>
