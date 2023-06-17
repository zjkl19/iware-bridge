<template>
  <div class="daily__pic-container-wrap">
    <div
      :tabindex="!disabled ? 0 : undefined"
      @keydown.delete="handleDelete(i.id)"
      class="daily__pic-container"
      v-for="i in imgs"
      :key="i.id"
    >
      <el-image
        :src="i.photoUrl"
        fit="contain"
        style="width: 270px; height: 180px"
        lazy
        :preview-src-list="urls"
      />
      <p :title="i.remarks">{{ i.remarks }}</p>
      <i class="el-icon-close" v-if="!disabled" @click="handleDelete(i.id)"></i>
      <i class="daily__tooltip" v-if="!disabled">按delete键可删除</i>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    imgs: {
      //[{id,photoUrl,remarks}...]
      type: Array,
      required: true
    },
    //是否支持删除
    disabled: {
      type: Boolean,
      default: false
    }
  },
  methods: {
    handleDelete(id) {
      if (!this.disabled) this.$emit('delete', id);
    }
  },
  computed: {
    urls() {
      return this.imgs.map((i) => i.photoUrl);
    }
  }
};
</script>

<style lang="scss">
.daily__pic-container-wrap {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
}

.daily__pic-container {
  overflow: hidden;
  text-align: center;
  display: inline-block;
  padding: 10px;
  width: 300px;
  height: 250px;
  border: 1px solid transparent;
  transition: border 0.2s ease;

  > img {
    margin: 0 auto;
    display: block;
  }
  > p {
    font-size: 16px;
    max-height: 32px;
    overflow: hidden;
  }
}
</style>
