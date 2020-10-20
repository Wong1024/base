<template>
  <el-dialog
      title="修改角色"
      :visible.sync="visible"
      :before-close="handleDialogClose"
      v-loading="loading"
  >
    <el-form :model="form" :rules="rules" ref="form">
      <el-form-item label="角色名" prop="roleName" :label-width="formLabelWidth">
        <el-input v-model="form.roleName" autocomplete="off"/>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="handleDialogClose">取消</el-button>
      <el-button type="primary" @click="submitForm">提交</el-button>
    </div>
  </el-dialog>
</template>

<script>

import { fetchRoleById } from '@/api/role';

export default {
  name: 'DialogUpdateRole',
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
        roleName: '',
      },
      rules: {
        roleName: [
          { required: true, message: '请输入角色名', trigger: 'blur' },
        ],
      },
      formLabelWidth: '120px',
    };
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
  mounted() {
    fetchRoleById(this.id).then((data) => {
      this.form = data;
      this.loading = false;
    }).catch((err) => {
      this.$message.error(`查询失败：${err}`);
    });
  },
};
</script>

<style scoped>

</style>
