// div带标题卡片
#props
  title

<template>
  <div class="tb-card" >
    <span class="title">{{title}}</span>
    <slot></slot>
  </div>
</template>

<script>
export default {
  props:["title"],
};
</script>

<style lang="scss" scoped>
@import '../styles/default.scss';
.title {
  font-size: 16px;
  // color:$YY_SPAN_DIVTITLE;
  position: absolute;
  top:24px;
  left:24px;
}
.tb-card {
  // background-color: $YY_DIV_BACKGROUND;
  // border: $YY_DIV_BORDER;
  box-sizing: border-box;
  padding:40px 10px 0;
  position:relative;
  min-height:120px;
  height:100%;
  overflow: hidden;
  border-radius: 8px;
}

</style>