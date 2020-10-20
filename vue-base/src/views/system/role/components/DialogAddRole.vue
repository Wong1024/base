<template>
  <el-dialog title="新增用户" :visible.sync="visible" :before-close="handleDialogClose">
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
export default {
  name: 'DialogAddRole',
  props: {
    visible: Boolean,
  },
  data() {
    return {
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
};
</script>

<style scoped>

</style>
