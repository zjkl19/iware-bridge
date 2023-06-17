<template>
  <div id="app">
    <router-view />
  </div>
</template>
<script>
export default {
  created() {
    //页面刷新store数据备份
    if (sessionStorage.getItem('store')) {
      //在页面加载时读取sessionStorage里的状态信息
      this.$store.replaceState(
        Object.assign(
          {},
          this.$store.state,
          JSON.parse(sessionStorage.getItem('store'))
        )
      );
    }
    window.addEventListener('beforeunload', () => {
      //在页面刷新时将vuex里的信息保存到sessionStorage里
      sessionStorage.setItem('store', JSON.stringify(this.$store.state));
    });
  }
};
</script>
