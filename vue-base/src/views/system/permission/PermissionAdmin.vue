<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="12">
        <el-tree
            v-loading="loading"
            :data="treeData"
            :props="{label: 'permissionName'}"
            node-key="id"
            default-expand-all
            highlight-current
            :expand-on-click-node="false"
            @current-change="handleTreeClick"
        >
          <template v-slot="{ node, data }">
            <div style="width: 100%; font-size: .8rem; line-height: 1.6rem;">
              <i v-if="data.type === 0" class="el-icon-folder"></i>
              <i v-else class="el-icon-document"></i>
              <span style="margin-left: .5rem;">{{ node.label }}</span>
              <span style="float: right;">
                <el-button
                    type="text"
                    size="mini"
                    icon="el-icon-circle-plus"
                    v-if="data.type !== 1"
                    @click="() => showAddForm(data.id)">
                  新增子权限
                </el-button>
                <el-button
                    type="text"
                    size="mini"
                    icon="el-icon-remove"
                    v-if="data.id !== '0'"
                    @click="() => handleDelete(data.id)">
                  删除
                </el-button>
              </span>
            </div>
          </template>
        </el-tree>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template v-slot:header>
            <span>权限详情</span>
          </template>
          <FormUpdatePermission
              @submit="handleUpdate"
              :id="currentId"
              v-if="currentId && currentId !== '0'"
          />
          <div v-else style="margin: 4rem; text-align: center;">
            <span>请选择一个权限</span>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <DialogAddPermission
        @submit="handleAdd"
        :default-parent-id="currentId"
        :visible.sync="addDialogFormVisible"
        v-if="addDialogFormVisible"
    />
  </div>
</template>

<script>
import { addPermission, removePermission, updatePermission } from '@/api/permission';
import DialogAddPermission from './components/DialogAddPermission.vue';
import FormUpdatePermission from './components/FormUpdatePermission.vue';

export default {
  name: 'PermissionAdmin',
  components: {
    DialogAddPermission,
    FormUpdatePermission,
  },
  data() {
    return {
      loading: true,
      addDialogFormVisible: false,
      currentId: null,
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
  mounted() {
    this.loadData();
  },
  methods: {
    loadData() {
      this.loading = true;
      this.$store.dispatch('permission/loadAll').then(() => {
        this.loading = false;
      }).catch((err) => {
        this.$message.error(`查询权限失败：${err}`);
        this.loading = false;
      });
    },
    handleTreeClick(data) {
      this.currentId = data.id;
    },
    showAddForm(id) {
      this.currentId = id;
      this.addDialogFormVisible = true;
    },
    handleAdd(form) {
      addPermission(form).then(() => {
        this.$message.success('新增成功');
        this.addDialogFormVisible = false;
        this.loadData();
      }).catch((err) => {
        this.$message.error(`新增失败：${err}`);
      });
    },
    handleUpdate(form) {
      updatePermission(form).then(() => {
        this.$message.success('修改成功');
        this.loadData();
      }).catch((err) => {
        this.$message.error(`修改失败：${err}`);
      });
    },
    handleDelete(id) {
      this.$confirm('确定删除该权限与其子权限?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        removePermission(id).then(() => {
          this.$message.success('删除成功');
          this.loadData();
        }).catch((err) => {
          this.$message.error(`删除失败：${err}`);
        });
      });
    },
  },
};
</script>

<style scoped>

</style>
