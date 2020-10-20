<template>
  <div>用户未登录，将自动跳转至登录页</div>
</template>

<script>
export default {
  name: 'Error401',
  mounted() {
    setTimeout(() => {
      window.location.href = '/login';
    }, 3000);
  },
};
</script>

<style scoped>

</style>
