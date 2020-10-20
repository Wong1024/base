<template>
  <div>
    <div>
      <el-button @click="addDialogFormVisible=true">新增用户</el-button>
    </div>
    <el-divider/>
    <el-table
        v-loading="loading"
        :data="tableData"
        style="width: 100%">
      <el-table-column prop="username" label="用户名"/>
      <el-table-column prop="nickname" label="姓名"/>
      <el-table-column prop="departName" label="部门"/>
      <el-table-column prop="state" label="状态">
        <template v-slot="{row}">
          <el-tag type="success" v-if="row.state === 0">启用</el-tag>
          <el-tag type="danger" v-else>禁用</el-tag>
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="200">
        <template v-slot="{row}">
          <el-button @click="handleClick(row)" type="text" size="small">编辑</el-button>
          <el-button
              v-if="row.state === 0"
              @click="handleDeActive(row)"
              type="text"
              size="small"
          >禁用</el-button>
          <el-button v-else @click="handleActive(row)" type="text" size="small">启用</el-button>
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
    <DialogAddUser
        @submit="handleAdd"
        :visible.sync="addDialogFormVisible"
        v-if="addDialogFormVisible"
    />
    <DialogUpdateUser
        @submit="handleUpdate"
        :id="currentId"
        :visible.sync="updateDialogFormVisible"
        v-if="updateDialogFormVisible"
    />
  </div>
</template>

<script>
import {
  addUser,
  removeUser,
  updateUser,
  updateUserState,
} from '@/api/user';
import { mapState, mapGetters } from 'vuex';
import DialogAddUser from './components/DialogAddUser.vue';
import DialogUpdateUser from './components/DialogUpdateUser.vue';

export default {
  name: 'UserAdmin',
  components: { DialogAddUser, DialogUpdateUser },
  data() {
    return {
      loading: true,
      addDialogFormVisible: false,
      updateDialogFormVisible: false,
      currentPage: 1,
      pageSize: 10,
      currentId: null,
    };
  },
  computed: {
    ...mapState({
      total: (state) => state.user.totalCount,
    }),
    ...mapGetters({
      tableData: 'user/currentPageUsers',
    }),
  },
  mounted() {
    this.$store.dispatch('role/loadAll').catch((err) => {
      this.$message.error(`查询角色失败：${err}`);
    });
    this.$store.dispatch('depart/loadAll').catch((err) => {
      this.$message.error(`查询部门失败：${err}`);
    });
    this.loadData();
  },
  methods: {
    loadData() {
      this.loading = true;
      const payload = {
        page: this.currentPage,
        size: this.pageSize,
      };
      this.$store.dispatch('user/loadPage', payload).then(() => {
        this.loading = false;
      });
    },
    handleClick(row) {
      this.currentId = row.id;
      this.updateDialogFormVisible = true;
    },
    handleAdd(form) {
      addUser(form).then(() => {
        this.$message.success('新增成功');
        this.addDialogFormVisible = false;
        this.loadData();
      }).catch((err) => {
        this.$message.error(`新增失败：${err}`);
      });
    },
    handleUpdate(form) {
      updateUser(form).then(() => {
        this.$message.success('修改成功');
        this.updateDialogFormVisible = false;
        this.loadData();
      }).catch((err) => {
        this.$message.error(`修改失败：${err}`);
      });
    },
    handleDelete(row) {
      this.$confirm('确定删除该用户?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        removeUser(row.id).then(() => {
          this.$message.success('删除成功');
          this.loadData();
        }).catch((err) => {
          this.$message.error(`删除失败：${err}`);
        });
      });
    },
    handleActive(row) {
      updateUserState(row.id, 0).then(() => {
        this.$message.success('启用成功');
        this.loadData();
      }).catch((err) => {
        this.$message.error(`启用失败：${err}`);
      });
    },
    handleDeActive(row) {
      updateUserState(row.id, 1).then(() => {
        this.$message.success('禁用成功');
        this.loadData();
      }).catch((err) => {
        this.$message.error(`禁用失败：${err}`);
      });
    },
  },
};
</script>

<style scoped>

</style>
