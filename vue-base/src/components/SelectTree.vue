<template>
  <el-popover
      ref="popover"
      placement="bottom-start"
      trigger="click"
      v-model="popoverVisible"
  >
    <el-tree
        ref="tree"
        class="select-tree"
        node-key="id"
        highlight-current
        :style="`min-width: ${treeWidth}`"
        :data="data"
        :props="nodeProps"
        :expand-on-click-node="false"
        default-expand-all
        @node-click="onClickNode"
    />
    <el-input
        slot="reference"
        ref="input"
        class="select-tree-input"
        v-model="labelModel"
        :style="`width: ${width}px; cursor: pointer;`"
        suffix-icon="el-icon-arrow-down"
        :placeholder="placeholder"
        readonly
    />
  </el-popover>
</template>

<script>
export default {
  name: 'SelectTree',
  props: {
    // 接收绑定参数
    value: String,
    // 输入框宽度
    width: String,
    // 选项数据
    data: {
      type: Array,
      required: true,
    },
    // 输入框占位符
    placeholder: {
      type: String,
      required: false,
      default: '请选择',
    },
    // 树节点配置选项
    props: {
      type: Object,
      required: false,
    },
  },
  // 设置绑定参数
  model: {
    prop: 'value',
    event: 'selected',
  },
  computed: {
    nodeProps() {
      return {
        value: 'id',
        label: 'name',
        children: 'children',
        ...this.props,
      };
    },
    labelModel() {
      return this.queryTree(this.data, this.value);
    },
  },
  watch: {
    value(val) {
      this.$refs.tree.setCurrentKey(val);
    },
  },
  data() {
    return {
      // 树状菜单显示状态
      showStatus: false,
      // 菜单宽度
      treeWidth: 'auto',
      popoverVisible: false,
    };
  },
  created() {
    // 获取输入框宽度同步至树状菜单宽度
    this.$nextTick(() => {
      this.treeWidth = `${(this.width || this.$refs.input.$refs.input.clientWidth) - 24}px`;
    });
  },
  methods: {
    // 单击节点
    onClickNode(node) {
      this.$emit('selected', node[this.nodeProps.value]);
      this.onCloseTree();
    },
    // 隐藏树状菜单
    onCloseTree() {
      this.popoverVisible = false;
    },
    // 搜索树状数据中的 ID
    queryTree(tree, id) {
      let stark = [];
      stark = stark.concat(tree);
      while (stark.length) {
        const temp = stark.shift();
        if (temp[this.nodeProps.children]) {
          stark = stark.concat(temp[this.nodeProps.children]);
        }
        if (temp[this.nodeProps.value] === id) {
          return temp[this.nodeProps.label];
        }
      }
      return '';
    },
  },
};
</script>

<style scoped>
.select-tree {
  max-height: 350px;
  overflow-y: scroll;
}
/* 菜单滚动条 */
.select-tree::-webkit-scrollbar {
  z-index: 11;
  width: 6px;
}
.select-tree::-webkit-scrollbar-track,
.select-tree::-webkit-scrollbar-corner {
  background: #fff;
}
.select-tree::-webkit-scrollbar-thumb {
  border-radius: 5px;
  width: 6px;
  background: #b4bccc;
}
.select-tree::-webkit-scrollbar-track-piece {
  background: #fff;
  width: 6px;
}
</style>
