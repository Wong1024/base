<template>
  <el-form ref="form" :model="form" :rules="rules" label-width="80px">
    <el-alert
        v-if="errorMsg"
        :title="errorMsg"
        type="error"
        show-icon/>
    <el-form-item label="用户名" prop="username">
      <el-input v-model="form.username"/>
    </el-form-item>
    <el-form-item label="密码" prop="password">
      <el-input v-model="form.password" show-password/>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">登录</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
export default {
  props: {
    errorMsg: String,
  },
  name: 'LoginForm',
  data() {
    return {
      form: {
        username: '',
        password: '',
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
        ],
      },
    };
  },
  methods: {
    onSubmit() {
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
