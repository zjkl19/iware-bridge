<template>
  <div class="daily__empty-info-container">
    <div class="empty-info"><slot>暂无数据</slot></div>
  </div>
</template>

<script>
export default {};
</script>

<style lang="scss">
.daily__empty-info-container {
  position: relative;
  top: 50%;
  transform: translateY(-50%);

  .empty-info {
    text-align: center;
    font-size: 30px;
  }
}
</style>
