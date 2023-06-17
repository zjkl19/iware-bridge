<template>
  <div id="analysisBar2" style="width: 100%; height: 100%"></div>
</template>

<script>
import echarts from 'echarts';
require('echarts/theme/macarons'); // echarts theme
import erd from 'element-resize-detector';
export default {
  name: 'analysisBar2',
  props: {
    echartData: {
      type: Object,
      default: () => {}
    }
  },
  data() {
    return {
      myChart: '',
      size: window.innerHeight / 70 / 7
    };
  },
  methods: {
    setOption() {
      this.myChart = echarts.init(this.$el, 'macarons');
      this.myChart.clear();
      this.myChart.setOption(
        {
          //鼠标移到柱形上的提示
          tooltip: {
            trigger: 'axis'
          },
          grid: {
            top: '20%',
            left: '2%',
            right: '2%',
            bottom: '0%',
            containLabel: true
          },
          xAxis: [
            {
              type: 'category',
              data: this.echartData.xData,
              splitLine: { show: false },
              axisLabel: {
                color: '#595959',
                fontSize: 6 * this.size,
                interval: 0,
                rotate: 40
              },
              axisTick: { inside: true, lineStyle: { color: '#BFBFBF' } },
              axisLine: { lineStyle: { color: '#BFBFBF' } },
              splitArea: { show: false }
            }
          ],
          yAxis: [
            {
              type: 'value',
              splitLine: {
                lineStyle: { width: 1, type: 'dashed', color: '#dedede' }
              },
              axisLabel: { color: '#595959', fontSize: 7 * this.size },
              axisTick: { show: false },
              axisLine: { show: false },
              splitArea: { show: false }
            }
          ],
          dataZoom: [
            {
              show: true,
              type: 'inside',
              realtime: true,
              startValue: 0,
              endValue: 19,
              zoomLock: true
            }
          ],
          series: [
            {
              type: 'bar',
              barWidth: 10 * this.size, //柱形大小
              color: '#58AFFF',
              data: this.echartData.data
            }
          ]
        },
        false
      );
    }
  },
  mounted() {
    let _this = this;
    this.setOption();

    window.addEventListener('resize', () => {
      this.myChart.resize();
    });
    // setTimeout(() => {
    erd().listenTo(document.getElementById('analysisBar2'), () => {
      //监听到事件后执行的业务逻辑
      _this.myChart.resize();
    });
  },
  created() {},
  beforeDestroy() {
    if (!this.myChart) {
      return;
    }
    window.removeEventListener('resize', {});
  }
};
</script>
