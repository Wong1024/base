<template>
  <div>
    <div>
      <el-button @click="addDialogFormVisible=true">新增角色</el-button>
    </div>
    <el-divider/>
    <el-table
        v-loading="loading"
        :data="tableData"
        style="width: 100%">
      <el-table-column
          prop="roleName"
          label="角色名"
      />
      <el-table-column
          fixed="right"
          label="操作"
          width="200">
        <template v-slot="{row}">
          <el-button @click="handleMenuAdmin(row)" type="text" size="small">角色菜单管理</el-button>
          <el-button @click="handleClick(row)" type="text" size="small">编辑</el-button>
          <el-button @click="handleDelete(row)" type="text" size="small">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
        background
        layout="prev, pager, next"
        :current-page.sync="currentPage"
        @current-change="loadData()"
        :page-size="pageSize"
        :total="total"
        style="margin-top: 10px; text-align: right;"
    />
<!--新增用户表单-->
    <DialogAddRole
        @submit="handleAdd"
        :visible.sync="addDialogFormVisible"
        v-if="addDialogFormVisible"
    />
    <DialogUpdateRole
        @submit="handleUpdate"
        :id="currentId"
        :visible.sync="updateDialogFormVisible"
        v-if="updateDialogFormVisible"
    />
    <DialogRoleMenu
        @submit="handleRoleMenuUpdate"
        :id="currentId"
        :visible.sync="dialogRoleMenuVisible"
        v-if="dialogRoleMenuVisible"
    />
  </div>
</template>

<script>
import {
  addRole,
  fetchRolePage,
  removeRole,
  updateRole,
  updateRoleMenusByRoleId,
} from '@/api/role';
import DialogAddRole from './components/DialogAddRole.vue';
import DialogUpdateRole from './components/DialogUpdateRole.vue';
import DialogRoleMenu from './components/DialogRoleMenu.vue';

export default {
  name: 'RoleAdmin',
  components: { DialogAddRole, DialogUpdateRole, DialogRoleMenu },
  data() {
    return {
      loading: true,
      addDialogFormVisible: false,
      updateDialogFormVisible: false,
      dialogRoleMenuVisible: false,
      tableData: [],
      total: 0,
      currentPage: 1,
      pageSize: 10,
      currentId: null,
    };
  },
  mounted() {
    this.loadData();
  },
  methods: {
    loadData() {
      this.loading = true;
      fetchRolePage(1, 10).then((data) => {
        this.tableData = data.content;
        this.total = data.totalElements;
        this.loading = false;
      });
    },
    handleClick(row) {
      this.currentId = row.id;
      this.updateDialogFormVisible = true;
    },
    handleAdd(form) {
      addRole(form).then(() => {
        this.$message.success('新增成功');
        this.addDialogFormVisible = false;
        this.loadData();
      }).catch(({ msg }) => {
        this.$message.error(`新增失败：${msg}`);
      });
    },
    handleUpdate(form) {
      updateRole(form).then(() => {
        this.$message.success('修改成功');
        this.updateDialogFormVisible = false;
        this.loadData();
      }).catch(({ msg }) => {
        this.$message.error(`修改失败：${msg}`);
      });
    },
    handleDelete(row) {
      removeRole(row.id).then(() => {
        this.$message.success('删除成功');
        this.loadData();
      }).catch(({ msg }) => {
        this.$message.error(`删除失败：${msg}`);
      });
    },
    handleMenuAdmin(row) {
      this.currentId = row.id;
      this.dialogRoleMenuVisible = true;
    },
    handleRoleMenuUpdate(roleId, menuIds) {
      updateRoleMenusByRoleId(roleId, menuIds).then(() => {
        this.$message.success('修改成功');
        this.dialogRoleMenuVisible = false;
      }).catch(({ msg }) => {
        this.$message.error(`修改失败：${msg}`);
      });
    },
  },
};
</script>

<style scoped>

</style>
