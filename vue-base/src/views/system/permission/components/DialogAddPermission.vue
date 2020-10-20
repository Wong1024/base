<template>
  <el-dialog title="新增部门" :visible.sync="visible" :before-close="handleDialogClose">
    <el-form :model="form" :rules="rules" ref="form">
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
        <el-input v-model="parentName" readonly/>
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
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="handleDialogClose">取消</el-button>
      <el-button type="primary" @click="submitForm">提交</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { mapState } from 'vuex';

export default {
  name: 'DialogAddPermission',
  props: {
    defaultParentId: String,
    visible: Boolean,
  },
  data() {
    return {
      form: {
        permissionName: '',
        parentId: this.defaultParentId,
        type: 0,
        path: '',
        method: '',
        sort: '',
      },
      rules: {
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
  computed: {
    ...mapState({
      parentName(state) {
        return this.defaultParentId === '0'
          ? '无上级菜单'
          : state.permission.all[this.defaultParentId].permissionName;
      },
    }),
  },
  methods: {
    handleDialogClose() {
      this.$emit('update:visible', false);
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
