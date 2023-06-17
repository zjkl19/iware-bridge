<template>
  <div class="ezuikit">
    <div :id="id" style="width: 100%; height: 100%"></div>
  </div>
</template>

<script>
import EZUIKit from 'ezuikit-js';
export default {
  name: 'EZUIKitJs',
  props: {
    videoUrl: String,
    id: Number,
    accessToken: String
  },
  data() {
    return { player: null };
  },
  watch: {
    videoUrl: {
      handler(oldValue, newValue) {}
    }
  },
  mounted() {
    this.player = new EZUIKit.EZUIKitPlayer({
      autoplay: true,
      id: this.id,
      accessToken: this.accessToken,
      url: this.videoUrl,
      template: 'standard' // simple - 极简版;standard-标准版;security - 安防版(预览回放);voice-语音版；
      // 视频上方头部控件
      //header: ["capturePicture", "save", "zoom"], // 如果templete参数不为simple,该字段将被覆盖
      //plugin: ['talk'],                       // 加载插件，talk-对讲
      // 视频下方底部控件
      // footer: ["talk", "broadcast", "hd", "fullScreen"], // 如果template参数不为simple,该字段将被覆盖
      // audio: 1, // 是否默认开启声音 0 - 关闭 1 - 开启
      // openSoundCallBack: data => console.log("开启声音回调", data),
      // closeSoundCallBack: data => console.log("关闭声音回调", data),
      // startSaveCallBack: data => console.log("开始录像回调", data),
      // stopSaveCallBack: data => console.log("录像回调", data),
      // capturePictureCallBack: data => console.log("截图成功回调", data),
      // fullScreenCallBack: data => console.log("全屏回调", data),
      // getOSDTimeCallBack: data => console.log("获取OSDTime回调", data),
    });
  },
  beforeDestroy() {
    if (!!this.player) {
      // this.player.stop();
      this.player = null;
    }
  }
};
</script>
<style lang="scss" scoped>
.ezuikit {
  width: 100%;
  height: 100%;
  /deep/ iframe {
    width: 100%;
    height: 100%;
  }
}
</style>
