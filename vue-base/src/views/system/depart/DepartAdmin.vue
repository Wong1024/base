<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="12">
        <el-tree
            v-loading="loading"
            :data="treeData"
            :props="{label: 'departName'}"
            node-key="id"
            default-expand-all
            highlight-current
            :expand-on-click-node="false"
            @current-change="handleTreeClick"
        >
          <template v-slot="{ node, data }">
            <div style="width: 100%; font-size: .8rem; line-height: 1.6rem;">
              <span>{{ node.label }}</span>
              <span style="float: right;">
                <el-button
                    type="text"
                    size="mini"
                    icon="el-icon-circle-plus"
                    @click="() => showAddForm(data.id)">
                  新增子部门
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
            <span>部门详情</span>
          </template>
          <FormUpdateDepart
              @submit="handleUpdate"
              :id="currentId"
              v-if="currentId && currentId !== '0'"
          />
          <div v-else style="margin: 4rem; text-align: center;">
            <span>请选择一个部门</span>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <DialogAddDepart
        @submit="handleAdd"
        :default-parent-id="currentId"
        :visible.sync="addDialogFormVisible"
        v-if="addDialogFormVisible"
    />
  </div>
</template>

<script>
import {
  addDepart,
  removeDepart,
  updateDepart,
} from '@/api/depart';
import DialogAddDepart from './components/DialogAddDepart.vue';
import FormUpdateDepart from './components/FormUpdateDepart.vue';

export default {
  name: 'DepartAdmin',
  components: { DialogAddDepart, FormUpdateDepart },
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
        departName: '全部部门',
        children: this.$store.getters['depart/departTree'],
      }];
    },
  },
  mounted() {
    this.loadData();
  },
  methods: {
    loadData() {
      this.loading = true;
      this.$store.dispatch('depart/loadAll').then(() => {
        this.loading = false;
      }).catch((err) => {
        this.$message.error(`查询部门失败：${err}`);
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
      addDepart(form).then(() => {
        this.$message.success('新增成功');
        this.addDialogFormVisible = false;
        this.loadData();
      }).catch((err) => {
        this.$message.error(`新增失败：${err}`);
      });
    },
    handleUpdate(form) {
      updateDepart(form).then(() => {
        this.$message.success('修改成功');
        this.loadData();
      }).catch((err) => {
        this.$message.error(`修改失败：${err}`);
      });
    },
    handleDelete(id) {
      this.$confirm('确定删除该部门与其子部门?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        removeDepart(id).then(() => {
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
