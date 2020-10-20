<template>
    <el-form :model="form" :rules="rules" ref="form" v-loading="loading">
      <el-form-item label="部门名称" prop="departName" :label-width="formLabelWidth">
        <el-input v-model="form.departName" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="上级部门" prop="parentId" :label-width="formLabelWidth">
        <SelectTree
            :data="departTree"
            :props="{label: 'departName'}"
            v-model="form.parentId"
        />
      </el-form-item>
      <el-form-item>
        <el-button @click="handleReset">重置</el-button>
        <el-button type="primary" @click="submitForm">提交</el-button>
      </el-form-item>
    </el-form>
</template>

<script>
import { fetchDepartById } from '@/api/depart';
import SelectTree from '@/components/SelectTree.vue';

export default {
  name: 'FormUpdateDepart',
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
        departName: '',
        parentId: '',
      },
      initForm: {},
      rules: {
        departName: [
          { required: true, message: '请输入部门名称', trigger: 'blur' },
        ],
      },
      formLabelWidth: '120px',
    };
  },
  watch: {
    id(newId) {
      this.loading = true;
      fetchDepartById(newId).then((data) => {
        this.form = { ...data };
        this.initForm = { ...data };
        this.loading = false;
      }).catch((err) => {
        this.$message.error(`查询失败：${err}`);
      });
    },
  },
  computed: {
    departTree() { // 选择上级部门时需要去除自身以及自身的子部门，以防止出现循环
      const departs = this.$store.getters['depart/allDeparts'];
      const map = {};
      const tree = [];
      const ids = [];
      departs.forEach((item) => {
        map[item.id] = { ...item };
        ids.push(item.id);
      });
      delete map[this.id];
      ids.forEach((id) => {
        const depart = map[id];
        if (!depart) {
          return;
        }
        if (depart.parentId === '0') {
          tree.push(depart);
        } else if (map[depart.parentId]) {
          if (!map[depart.parentId].children) {
            map[depart.parentId].children = [];
          }
          map[depart.parentId].children.push(depart);
        }
      });
      return [{
        id: '0',
        departName: '无上级部门',
        children: tree,
      }];
    },
  },
  mounted() {
    this.loading = true;
    fetchDepartById(this.id).then((data) => {
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
