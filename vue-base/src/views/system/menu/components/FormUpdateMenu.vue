<template>
    <el-form :model="form" :rules="rules" ref="form" v-loading="loading">
      <el-form-item label="菜单名称" prop="menuName" :label-width="formLabelWidth">
        <el-input v-model="form.menuName" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="上级菜单" prop="parentId" :label-width="formLabelWidth">
        <SelectTree
            :data="menuTree"
            :props="{label: 'menuName'}"
            v-model="form.parentId"
        />
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
      <el-form-item
          v-if="form.type===1"
          label="路径"
          prop="path"
          :label-width="formLabelWidth"
      >
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
          :label-width="formLabelWidth">
        <el-radio-group v-model="form.newWindow">
          <el-radio :label="true">是</el-radio>
          <el-radio :label="false">否</el-radio>
        </el-radio-group>
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
import { fetchMenuById } from '@/api/menu';
import SelectTree from '@/components/SelectTree.vue';

export default {
  name: 'FormUpdateMenu',
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
        menuName: '',
        parentId: '0',
        type: 0,
        description: '',
        path: '',
        extraPath: '',
        menuIcon: '',
        newWindow: false,
        sort: '',
      },
      initForm: {},
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
  watch: {
    id(newId) {
      this.loading = true;
      fetchMenuById(newId).then((data) => {
        this.form = { ...data };
        this.initForm = { ...data };
        this.loading = false;
      }).catch((err) => {
        this.$message.error(`查询失败：${err}`);
      });
    },
  },
  computed: {
    menuTree() { // 选择上级菜单时需要去除自身以及自身的子菜单，以防止出现循环；无法选择功能菜单作为上级菜单
      const menus = this.$store.getters['menu/allMenus'];
      const map = {};
      const tree = [];
      const ids = [];
      menus.forEach((item) => {
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
      return [{
        id: '0',
        menuName: '无上级菜单',
        children: tree,
      }];
    },
  },
  mounted() {
    this.loading = true;
    fetchMenuById(this.id).then((data) => {
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
