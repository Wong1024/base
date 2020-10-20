<template>
  <el-dialog title="新增部门" :visible.sync="visible" :before-close="handleDialogClose">
    <el-form :model="form" :rules="rules" ref="form">
      <el-form-item label="部门名称" prop="departName" :label-width="formLabelWidth">
        <el-input v-model="form.departName" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="上级部门" prop="parentId" :label-width="formLabelWidth">
        <el-input v-model="parentName" readonly/>
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
  name: 'DialogAddDepart',
  props: {
    defaultParentId: String,
    visible: Boolean,
  },
  data() {
    return {
      form: {
        departName: '',
        parentId: this.defaultParentId,
      },
      rules: {
        departName: [
          { required: true, message: '请输入部门名称', trigger: 'blur' },
        ],
      },
      formLabelWidth: '120px',
    };
  },
  computed: {
    ...mapState({
      parentName(state) {
        return this.defaultParentId === '0'
          ? '无上级部门'
          : state.depart.all[this.defaultParentId].departName;
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
