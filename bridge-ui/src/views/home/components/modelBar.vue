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
            trigger: 'item',
            confine: true
          },
          legend: {
            top: '5%',
            itemWidth: 8,
            itemHeight: 8,
            textStyle: {
              color: 'rgba(255,255,255,0.85)'
            }
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
              lineStyle: {
                color: '#145357'
              }
            },
            axisLabel: {
              margin: 8,
              color: 'rgba(255,255,255,0.85)',
              fontSize: 12,
              interval: 1,
              formatter: function (text) {
                if (text.length > 5) {
                  return text.slice(0, 5) + '...';
                } else {
                  return text;
                }
              }
            },
            axisTick: {
              show: false
            },
            splitArea: {
              show: false
            }
          },
          yAxis: {
            name: '病害数',
            type: 'value',
            max: 20,
            nameTextStyle: {
              color: 'rgba(255,255,255,0.85)'
            },
            axisLine: {
              show: false
            },
            axisLabel: {
              margin: 8,
              color: 'rgba(255,255,255,0.85)',
              fontSize: 12
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
                color: '#145357'
              }
            }
          },
          series: [
            {
              name: '已修复',
              type: 'bar',
              stack: 'total',
              barWidth: 16,
              itemStyle: {
                color: '#43d9d7'
              },
              data: this.echartData.data[0]
            },
            {
              name: '未修复',
              type: 'bar',
              stack: 'total',
              barWidth: 16,
              itemStyle: {
                color: '#f2e01d'
              },
              data: this.echartData.data[1]
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
