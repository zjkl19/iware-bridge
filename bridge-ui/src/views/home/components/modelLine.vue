<template>
  <div id="modelBarEchart" style="width: 100%; height: 100%"></div>
</template>

<script>
import echarts from 'echarts';
require('echarts/theme/macarons'); // echarts theme
import erd from 'element-resize-detector';
export default {
  name: 'modelBarEchart',
  props: {
    echartData: {
      type: Object,
      default: () => {}
    }
  },
  data() {
    return {
      myChart: ''
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
            trigger: 'item'
          },
          grid: {
            top: '20%', //与上面的距离
            left: '5%', //与左边的距离
            right: '5%', //与右边的距离
            bottom: '5%', //与下面的距离
            containLabel: true
          },
          xAxis: {
            type: 'category',
            data: this.echartData.xData,
            axisLine: {
              show: false
            },
            axisLabel: {
              margin: 12,
              color: '#fff',
              fontSize: 10
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
              color: '#fff',
              fontSize: 10
            },
            axisTick: {
              show: false
            },
            splitArea: {
              show: false
            },
            splitLine: {
              lineStyle: {
                color: 'rgba(77,86,107,0.5)'
              }
            }
          },
          series: [
            {
              type: 'line',
              symbol: 'none',
              smooth: false,
              itemStyle: {
                color: '#1a9f97'
              },
              areaStyle: {
                color: 'rgba(10,92,104,0.8)'
              },
              data: this.echartData.data
            }
          ]
        },
        false
      );
      window.addEventListener('resize', () => {
        this.myChart.resize();
      });
      // setTimeout(() => {
      erd().listenTo(document.getElementById('modelBarEchart'), () => {
        //监听到事件后执行的业务逻辑
        _this.myChart.resize();
      });
      // }, 5000);
    }
  },
  mounted() {
    this.setOption();
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
