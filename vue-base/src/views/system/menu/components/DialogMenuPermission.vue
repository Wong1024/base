<template>
  <el-dialog
      title="菜单权限管理"
      :visible.sync="visible"
      :before-close="handleDialogClose"
      v-loading="loading"
  >
    <el-tree
        ref="tree"
        :data="treeData"
        :props="{label: 'permissionName'}"
        node-key="id"
        default-expand-all
        :expand-on-click-node="false"
        show-checkbox
        :default-checked-keys="permissionIds"
        check-on-click-node
    />
    <div slot="footer" class="dialog-footer">
      <el-button @click="handleDialogClose">取消</el-button>
      <el-button type="primary" @click="submitForm">提交</el-button>
    </div>
  </el-dialog>
</template>

<script>

import { fetchPermissionIdsByRoleId } from '@/api/menu';

export default {
  name: 'DialogMenuPermission',
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
      permissionIds: [],
    };
  },
  computed: {
    treeData() {
      return [{
        id: '0',
        type: 0,
        permissionName: '全部权限',
        children: this.$store.getters['permission/permissionTree'],
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
    await this.$store.dispatch('permission/loadAll').catch(({ msg }) => {
      this.$message.error(`查询菜单失败：${msg}`);
    });
    this.permissionIds = await fetchPermissionIdsByRoleId(this.id).catch(({ msg }) => {
      this.$message.error(`查询失败：${msg}`);
    });
    this.loading = false;
  },
};
</script>

<style scoped>

</style>
