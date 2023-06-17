<template>
  <div id="surveryEchart" style="width: 100%; height: 100%"></div>
</template>

<script>
import echarts from 'echarts';
require('echarts/theme/macarons'); // echarts theme
import erd from 'element-resize-detector';
export default {
  name: 'surveryEchart',
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
      let _this = this;
      this.myChart = echarts.init(this.$el, 'macarons');
      this.myChart.clear();
      this.myChart.setOption(
        {
          tooltip: {
            show: true,
            trigger: 'axis',
            axisPointer: {
              type: 'none'
            }
          },
          grid: {
            top: '15%', //与上面的距离
            left: '5%', //与左边的距离
            right: '5%', //与右边的距离
            bottom: '5%', //与下面的距离
            containLabel: true
          },
          xAxis: {
            type: 'category',
            data: this.echartData.xData,
            axisLine: {
              lineStyle: {
                color: '#58657c'
              }
            },
            axisLabel: {
              margin: 8,
              color: 'rgba(255,255,255,0.6)',
              fontSize: 6 * this.size
            },
            axisTick: {
              show: false
            },
            splitArea: {
              show: false
            }
          },
          yAxis: {
            type: 'value',
            axisLine: {
              show: false
            },
            axisLabel: {
              margin: 8,
              color: 'rgba(255,255,255,0.6)',
              fontSize: 6 * this.size
            },
            axisTick: {
              show: false
            },
            splitArea: {
              show: false
            },
            splitLine: {
              lineStyle: {
                type: 'dashed',
                color: '#58657c'
              }
            }
          },
          // dataZoom: [
          //   {
          //     show: true,
          //     type: 'inside',
          //     realtime: true,
          //     startValue: 0,
          //     endValue: 4,
          //     zoomLock: true
          //   }
          // ],
          series: {
            name: '桥隧概况',
            type: 'bar',
            label: {
              show: true,
              position: 'top',
              fontSize: 6 * this.size,
              color: '#fff'
            },
            barWidth: 8 * this.size,
            itemStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                { offset: 0, color: '#6eeeff' },
                { offset: 1, color: '#03a9f2' }
              ])
            },
            data: this.echartData.data
          }
        },
        false
      );
      window.addEventListener('resize', () => {
        this.myChart.resize();
      });
      // setTimeout(() => {
      erd().listenTo(document.getElementById('surveryEchart'), () => {
        //监听到事件后执行的业务逻辑
        _this.myChart.resize();
      });
      // }, 5000);
    }
  },
  mounted() {
    // this.setOption();
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
