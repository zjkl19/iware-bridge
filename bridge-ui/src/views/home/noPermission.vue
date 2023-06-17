<template>
  <div class="noPermission">
    <div v-if="type == 1" class="content">
      <img src="@/assets/images/404.png" alt="" />
      <span class="text1">404</span>
      <span class="text2">抱歉，你访问的页面不存在</span>
      <span class="btn" @click="toHome">返回首页</span>
    </div>
    <div v-else class="content">
      <img src="@/assets/images/noPer.png" alt="" />
      <span class="text2">抱歉，当前用户暂无权限，请联系主账户开通</span>
      <span class="btn" @click="toHome">返回首页</span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'noPermission-dzl',
  components: {},
  props: {},
  data() {
    return {
      type: 1 //1:404页面，2：没有权限
    };
  },
  methods: {
    toHome() {
      this.$router.replace('/home');
    }
  },
  beforeMount() {
    if (this.$route.params.noPer) this.type = 2;
  }
};
</script>
<style lang="scss" scoped>
.noPermission {
  display: flex;
  align-items: center;
  justify-content: center;
  .content {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    img {
      margin-bottom: 28px;
    }
    .text1 {
      line-height: 61px;
      font-size: 48px;
      color: #2f2e41;
    }
    .text2 {
      line-height: 30px;
      font-size: 20px;
      color: #333;
      margin: 8px 0 48px;
    }
    .btn {
      width: 88px;
      height: 40px;
      font-size: 14px;
      color: #fff;
      background: #419aff;
      border-radius: 4px;
      display: flex;
      align-items: center;
      justify-content: center;
      cursor: pointer;
    }
  }
}
</style>
