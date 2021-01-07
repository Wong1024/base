<template>
  <el-dialog
      title="角色菜单管理"
      :visible.sync="visible"
      :before-close="handleDialogClose"
      v-loading="loading"
  >
    <el-tree
        ref="tree"
        :data="treeData"
        :props="{label: 'menuName'}"
        node-key="id"
        default-expand-all
        :expand-on-click-node="false"
        show-checkbox
        :default-checked-keys="menuIds"
        check-on-click-node
    />
    <div slot="footer" class="dialog-footer">
      <el-button @click="handleDialogClose">取消</el-button>
      <el-button type="primary" @click="submitForm">提交</el-button>
    </div>
  </el-dialog>
</template>

<script>

import { fetchMenuIdsByRoleId } from '@/api/role';

export default {
  name: 'DialogRoleMenu',
  props: {
    id: {
      type: String,
      required: true,
    },
    visible: Boolean,
  },
  data() {
    return {
      loading: true, // 数据未加载完成禁止编辑内容
      menuIds: [],
    };
  },
  computed: {
    treeData() {
      return [{
        id: '0',
        type: 0,
        menuName: '全部菜单',
        children: this.$store.getters['menu/menuTree'],
      }];
    },
  },
  methods: {
    handleDialogClose() {
      this.$emit('update:visible', false);
    },
    submitForm() {
      this.$emit('submit', this.id, this.$refs.tree.getCheckedKeys(true));
    },
  },
  async mounted() {
    this.loading = true;
    await this.$store.dispatch('menu/loadAll').catch(({ msg }) => {
      this.$message.error(`查询菜单失败：${msg}`);
    });
    this.menuIds = await fetchMenuIdsByRoleId(this.id).catch(({ msg }) => {
      this.$message.error(`查询失败：${msg}`);
    });
    this.loading = false;
  },
};
</script>

<style scoped>

</style>
