<template>
  <el-dialog title="新增部门" :visible.sync="visible" :before-close="handleDialogClose">
    <el-form :model="form" :rules="rules" ref="form">
      <el-form-item label="菜单名称" prop="menuName" :label-width="formLabelWidth">
        <el-input v-model="form.menuName" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="上级菜单" prop="parentId" :label-width="formLabelWidth">
        <el-input v-model="parentName" readonly/>
      </el-form-item>
      <el-form-item label="类型" prop="type" :label-width="formLabelWidth">
        <el-radio-group v-model="form.type">
          <el-radio :label="0">菜单组</el-radio>
          <el-radio :label="1">功能菜单</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="描述" prop="description" :label-width="formLabelWidth">
        <el-input v-model="form.description" autocomplete="off"/>
      </el-form-item>
      <el-form-item v-if="form.type===1" label="路径" prop="path" :label-width="formLabelWidth">
        <el-input v-model="form.path" autocomplete="off"/>
      </el-form-item>
      <el-form-item
          v-if="form.type===1"
          label="附带授权路径"
          prop="extraPath"
          :label-width="formLabelWidth"
      >
        <el-input v-model="form.extraPath" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="图标" prop="menuIcon" :label-width="formLabelWidth">
        <el-input v-model="form.menuIcon" autocomplete="off"/>
      </el-form-item>
      <el-form-item
          v-if="form.type===1"
          label="在新窗口打开"
          prop="newWindow"
          :label-width="formLabelWidth"
      >
        <el-radio-group v-model="form.newWindow">
          <el-radio :label="true">是</el-radio>
          <el-radio :label="false">否</el-radio>
        </el-radio-group>
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
  name: 'DialogAddMenu',
  props: {
    defaultParentId: String,
    visible: Boolean,
  },
  data() {
    return {
      form: {
        menuName: '',
        parentId: this.defaultParentId,
        type: 0,
        description: '',
        path: '',
        extraPath: '',
        menuIcon: '',
        newWindow: false,
        sort: '',
      },
      rules: {
        menuName: [
          { required: true, message: '请输入菜单名称', trigger: 'blur' },
        ],
        type: [
          { required: true, message: '请选择类型', trigger: 'blur' },
        ],
        path: [
          { required: true, message: '请输入路径', trigger: 'blur' },
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
          : state.menu.all[this.defaultParentId].menuName;
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
