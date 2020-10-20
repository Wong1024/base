<template>
  <div>
    <template v-for="nodeItem in nodeList">
      <el-submenu
          :index='nodeItem.id'
          v-if="nodeItem.children && nodeItem.children.length>0"
          :key="nodeItem.id">
        <template v-slot:title>
          <i v-if="nodeItem.menuIcon" :class="nodeItem.menuIcon"></i>
          <span>{{ nodeItem.menuName }}</span>
        </template>
        <MenuTree :node-list="nodeItem.children"/>
      </el-submenu>
      <el-menu-item
          v-else-if="nodeItem.newWindow"
          index=''
          :key='nodeItem.id'
      >
        <template v-slot:title>
          <a :href="nodeItem.path" target="_blank">
            <i v-if="nodeItem.menuIcon" :class="nodeItem.menuIcon"></i>
            <span>{{ nodeItem.menuName }}</span>
          </a>
        </template>
      </el-menu-item>
      <el-menu-item
          v-else
          :index='nodeItem.path'
          :key='nodeItem.id'
      >
        <template v-slot:title>
          <i v-if="nodeItem.menuIcon" :class="nodeItem.menuIcon"></i>
          <span>{{ nodeItem.menuName }}</span>
        </template>
      </el-menu-item>
    </template>
  </div>
</template>

<script>
export default {
  name: 'MenuTree',
  props: {
    nodeList: {
      type: Array,
      default() {
        return [];
      },
    },
  },
};
</script>

<style scoped>
a {
  text-decoration: none;
  color: black;
}
</style>
