<template>
  <el-dialog title="新增用户" :visible.sync="visible" :before-close="handleDialogClose">
    <el-form :model="form" :rules="rules" ref="form">
      <el-form-item label="用户名" prop="username" :label-width="formLabelWidth">
        <el-input v-model="form.username" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="姓名" prop="nickname" :label-width="formLabelWidth">
        <el-input v-model="form.nickname" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="所属部门" prop="parentId" :label-width="formLabelWidth">
        <SelectTree
            :data="departTree"
            :props="{label: 'departName'}"
            v-model="form.departId"
        />
      </el-form-item>
      <el-form-item label="状态" prop="state" :label-width="formLabelWidth">
        <el-radio-group v-model="form.state">
          <el-radio :label="0">启用</el-radio>
          <el-radio :label="1">禁用</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="密码" prop="password" :label-width="formLabelWidth">
        <el-input v-model="form.password" show-password autocomplete="off"/>
      </el-form-item>
      <el-form-item label="确认密码" prop="checkPass" :label-width="formLabelWidth">
        <el-input v-model="form.checkPass" show-password autocomplete="off"/>
      </el-form-item>
      <el-form-item label="角色" prop="roles" :label-width="formLabelWidth">
        <el-checkbox-group v-model="form.roles">
          <el-checkbox v-for="role in roles" :key="role.id" :label="role.roleName"/>
        </el-checkbox-group>
      </el-form-item>
      <!--解决自动填充问题-->
      <el-form-item style="position: absolute; z-index: -1;">
        <el-input autocomplete="off" tabindex="-1"/>
      </el-form-item>
      <el-form-item style="position: absolute; z-index: -1;">
        <el-input show-password autocomplete="off" tabindex="-1"/>
      </el-form-item>
      <!--end 解决自动填充问题-->
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="handleDialogClose">取消</el-button>
      <el-button type="primary" @click="submitForm">提交</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { mapGetters } from 'vuex';
import SelectTree from '@/components/SelectTree.vue';

export default {
  name: 'DialogAddUser',
  components: { SelectTree },
  props: {
    visible: Boolean,
  },
  data() {
    return {
      form: {
        name: '',
        nickname: '',
        departId: '0',
        password: '',
        checkPass: '',
        roles: '',
        state: 0,
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
        ],
        nickname: [
          { required: true, message: '请输入姓名', trigger: 'blur' },
        ],
        password: [
          {
            validator: (rule, value, callback) => {
              if (value === '') {
                callback(new Error('请输入密码'));
              } else {
                if (this.form.checkPass !== '') {
                  this.$refs.form.validateField('checkPass');
                }
                callback();
              }
            },
            message: '请输入密码',
            trigger: 'blur',
          },
        ],
        checkPass: [
          {
            validator: (rule, value, callback) => {
              if (value === '') {
                callback(new Error('请再次输入密码'));
              } else if (value !== this.form.password) {
                callback(new Error('两次输入密码不一致!'));
              } else {
                callback();
              }
            },
            trigger: 'blur',
          },
        ],
      },
      formLabelWidth: '120px',
    };
  },
  computed: {
    departTree() {
      return [{
        id: '0',
        departName: '无所属部门',
        children: this.$store.getters['depart/departTree'],
      }];
    },
    ...mapGetters({
      roles: 'role/allRoles',
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
