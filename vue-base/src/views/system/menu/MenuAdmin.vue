<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="12">
        <el-tree
            v-loading="loading"
            :data="treeData"
            :props="{label: 'menuName'}"
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
                  新增子菜单
                </el-button>
                 <el-button
                     type="text"
                     size="mini"
                     icon="el-icon-edit"
                     v-if="data.type === 1"
                     @click="() => showPermissionDialog(data.id)">
                  接口权限
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
            <span>菜单详情</span>
          </template>
          <FormUpdateMenu
              @submit="handleUpdate"
              :id="currentId"
              v-if="currentId && currentId !== '0'"
          />
          <div v-else style="margin: 4rem; text-align: center;">
            <span>请选择一个菜单</span>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <DialogAddMenu
        @submit="handleAdd"
        :default-parent-id="currentId"
        :visible.sync="addDialogFormVisible"
        v-if="addDialogFormVisible"
    />
    <DialogMenuPermission
        @submit="handleMenuPermissionUpdate"
        :id="currentId"
        :visible.sync="dialogMenuPermissionVisible"
        v-if="dialogMenuPermissionVisible"
    />
  </div>
</template>

<script>
import {
  addMenu,
  removeMenu,
  updateMenu,
  updatePermissionIdsByRoleId,
} from '@/api/menu';
import DialogAddMenu from './components/DialogAddMenu.vue';
import FormUpdateMenu from './components/FormUpdateMenu.vue';
import DialogMenuPermission from './components/DialogMenuPermission.vue';

export default {
  name: 'MenuAdmin',
  components: {
    DialogAddMenu,
    FormUpdateMenu,
    DialogMenuPermission,
  },
  data() {
    return {
      loading: true,
      addDialogFormVisible: false,
      dialogMenuPermissionVisible: false,
      currentId: null,
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
  mounted() {
    this.loadData();
  },
  methods: {
    loadData() {
      this.loading = true;
      this.$store.dispatch('menu/loadAll').then(() => {
        this.loading = false;
      }).catch(({ msg }) => {
        this.$message.error(`查询菜单失败：${msg}`);
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
      addMenu(form).then(() => {
        this.$message.success('新增成功');
        this.addDialogFormVisible = false;
        this.loadData();
      }).catch(({ msg }) => {
        this.$message.error(`新增失败：${msg}`);
      });
    },
    handleUpdate(form) {
      updateMenu(form).then(() => {
        this.$message.success('修改成功');
        this.loadData();
      }).catch(({ msg }) => {
        this.$message.error(`修改失败：${msg}`);
      });
    },
    handleDelete(id) {
      this.$confirm('确定删除该菜单与其子菜单?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        removeMenu(id).then(() => {
          this.$message.success('删除成功');
          this.loadData();
        }).catch(({ msg }) => {
          this.$message.error(`删除失败：${msg}`);
        });
      });
    },
    showPermissionDialog(id) {
      this.dialogMenuPermissionVisible = true;
      this.currentId = id;
    },
    handleMenuPermissionUpdate(menuId, permissionIds) {
      updatePermissionIdsByRoleId(menuId, permissionIds).then(() => {
        this.$message.success('修改成功');
        this.dialogMenuPermissionVisible = false;
      }).catch(({ msg }) => {
        this.$message.error(`修改失败：${msg}`);
      });
    },
  },
};
</script>

<style scoped>

</style>
