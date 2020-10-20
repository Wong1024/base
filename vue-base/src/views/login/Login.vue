<template>
  <div style="width: 400px; margin-top: 100px; margin-right: auto; margin-left: auto;">
    <LoginForm @submit="handleSubmit" :errorMsg="errorMessage"/>
  </div>
</template>

<script>
import { doLogin } from '@/api/login';
import LoginForm from './LoginForm.vue';

export default {
  name: 'Login',
  components: { LoginForm },
  data() {
    return {
      errorMessage: '',
    };
  },
  methods: {
    handleSubmit({ username, password }) {
      doLogin(username, password).then(({ url }) => {
        if (url) {
          this.$router.push(url);
        } else {
          this.$router.push('/');
        }
      }).catch((err) => {
        this.errorMessage = err;
      });
    },
  },
};
</script>

<style scoped>

</style>
