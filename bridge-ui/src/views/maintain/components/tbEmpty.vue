<template>
  <div class="empty">
    <div class="title">{{ title }}</div>
    <div class="empty_info">暂无数据</div>
  </div>
</template>

<script>
export default {
  props: {
    title: String
  }
};
</script>

<style scope lang="scss">

.empty {
  width: 100%;
  height: 100%;
}
.empty_info {
  color: #464f60;
  font-size: 30px;
  text-align: center;
  position: relative;
  top: 50%;
  transform: translateY(-50%);
}
.title {
  padding-left: 12px;
  // color: $YY_SPAN_DIVTITLE;
  font-size: 16px;
}
</style>