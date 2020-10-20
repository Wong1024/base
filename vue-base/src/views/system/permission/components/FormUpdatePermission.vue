<template>
    <el-form :model="form" :rules="rules" ref="form" v-loading="loading">
      <el-form-item label="类型" prop="type" :label-width="formLabelWidth">
        <el-radio-group v-model="form.type">
          <el-radio :label="0">权限组</el-radio>
          <el-radio :label="1">接口权限</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="权限名称" prop="permissionName" :label-width="formLabelWidth">
        <el-input v-model="form.permissionName" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="所属权限组" prop="parentId" :label-width="formLabelWidth">
        <SelectTree
            :data="permissionTree"
            :props="{label: 'permissionName'}"
            v-model="form.parentId"
        />
      </el-form-item>
      <el-form-item v-if="form.type===1" label="路径" prop="path" :label-width="formLabelWidth">
        <el-input v-model="form.path" autocomplete="off"/>
      </el-form-item>
      <el-form-item v-if="form.type===1" label="请求方式" prop="method" :label-width="formLabelWidth">
        <el-select v-model="form.method">
          <el-option value="GET"/>
          <el-option value="POST"/>
          <el-option value="PUT"/>
          <el-option value="DELETE"/>
        </el-select>
      </el-form-item>
      <el-form-item label="排序顺序" prop="sort" :label-width="formLabelWidth">
        <el-input v-model="form.sort" autocomplete="off"/>
      </el-form-item>
      <el-form-item>
        <el-button @click="handleReset">重置</el-button>
        <el-button type="primary" @click="submitForm">提交</el-button>
      </el-form-item>
    </el-form>
</template>

<script>
import { fetchPermissionById } from '@/api/permission';
import SelectTree from '@/components/SelectTree.vue';

export default {
  name: 'FormUpdatePermission',
  components: { SelectTree },
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
      form: {
        permissionName: '',
        parentId: '0',
        type: 0,
        path: '',
        method: '',
        sort: '',
      },
      initForm: {},
      rules: {
        parentId: [
          { required: true, message: '请选择所属权限组', trigger: 'blur' },
        ],
        permissionName: [
          { required: true, message: '请输入权限名称', trigger: 'blur' },
        ],
        type: [
          { required: true, message: '请选择类型', trigger: 'blur' },
        ],
        path: [
          { required: true, message: '请输入路径', trigger: 'blur' },
        ],
        method: [
          { required: true, message: '请选择请求方式', trigger: 'blur' },
        ],
        sort: [
          { required: true, message: '请输入排序顺序', trigger: 'blur' },
        ],
      },
      formLabelWidth: '120px',
    };
  },
  watch: {
    id(newId) {
      this.loading = true;
      fetchPermissionById(newId).then((data) => {
        this.form = { ...data };
        this.initForm = { ...data };
        this.loading = false;
      }).catch((err) => {
        this.$message.error(`查询失败：${err}`);
      });
    },
  },
  computed: {
    permissionTree() {
      const permissions = this.$store.getters['permission/allPermissions'];
      const map = {};
      const tree = [];
      const ids = [];
      permissions.forEach((item) => {
        map[item.id] = { ...item };
        ids.push(item.id);
      });
      delete map[this.id];
      ids.forEach((id) => {
        const menu = map[id];
        if (!menu || menu.type === 1) {
          return;
        }
        if (menu.parentId === '0') {
          tree.push(menu);
        } else if (map[menu.parentId]) {
          if (!map[menu.parentId].children) {
            map[menu.parentId].children = [];
          }
          map[menu.parentId].children.push(menu);
        }
      });
      console.log(tree);
      return [{
        id: '0',
        permissionName: '无上级菜单',
        children: tree,
      }];
    },
  },
  mounted() {
    this.loading = true;
    fetchPermissionById(this.id).then((data) => {
      this.form = { ...data };
      this.initForm = { ...data };
      this.loading = false;
    }).catch((err) => {
      this.$message.error(`查询失败：${err}`);
    });
  },
  methods: {
    handleReset() {
      this.form = { ...this.initForm };
    },
    submitForm() {
      this.$refs.form.validate((valid) => {
        if (!valid) {
          return;
        }
        this.$emit('submit', this.form);
      });
    },
  },
};
</script>

<style scoped>

</style>
