<template>
  <div id="warnLine" style="width: 100%; height: 100%"></div>
</template>

<script>
import echarts from 'echarts';
require('echarts/theme/macarons'); // echarts theme
import erd from 'element-resize-detector';
export default {
  name: 'warnLine',
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
            trigger: 'axis',
            axisPointer: {
              // type: 'none'
            }
          },
          xAxis: {
            type: 'category',
            boundaryGap: false,
            data: this.echartData.date,
            axisTick: { inside: true },
            axisLine: { lineStyle: { color: '#BFBFBF' } },
            splitArea: { show: false },
            axisLabel: { color: '#595959' }
          },
          yAxis: {
            name: this.echartData.unit,
            type: 'value',
            nameTextStyle: { color: '#595959' },
            axisTick: { show: false },
            axisLine: { show: false },
            axisLabel: { color: '#595959' },
            splitArea: { show: false },
            splitLine: { lineStyle: { type: 'dashed' } }
          },
          grid: {
            left: '5%',
            right: '10%',
            bottom: '10%',
            top: '12%',
            containLabel: true
          },
          series: [
            //时程数据
            {
              data: this.echartData.data,
              type: 'line',
              itemStyle: {
                color: '#27AE60'
              },
              areaStyle: {
                color: 'rgba(39, 174, 96, 0.1)'
              }
            },
            //一级预警上限
            {
              data: [],
              type: 'line',
              areaStyle: {
                show: false
              },
              markLine: {
                //数值为0的Y轴样式
                silent: true,
                symbol: 'none',
                data: [
                  {
                    yAxis: this.echartData.upOne,
                    name: '一级预警上限',
                    lineStyle: {
                      type: 'solid',
                      color: '#EB5757',
                      width: 1
                    }
                  }
                ],
                label: {
                  show: true,
                  position: 'insideEndTop',
                  fontSize: 12,
                  fontWeight: 'blod',
                  formatter: function (parmas) {
                    return parmas.name + '(' + parmas.value + ')';
                  }
                }
              }
            },
            //一级预警下限
            {
              data: [],
              type: 'line',
              areaStyle: {
                show: false
              },
              markLine: {
                //数值为0的Y轴样式
                silent: true,
                symbol: 'none',
                data: [
                  {
                    yAxis: this.echartData.downOne,
                    name: '一级预警下限',
                    lineStyle: {
                      type: 'solid',
                      color: '#EB5757',
                      width: 1
                    }
                  }
                ],
                label: {
                  show: true,
                  position: 'insideEndTop',
                  fontSize: 12,
                  fontWeight: 'blod',
                  formatter: function (parmas) {
                    return parmas.name + '(' + parmas.value + ')';
                  }
                }
              }
            },
            //二级预警上限
            {
              data: [],
              type: 'line',
              areaStyle: {
                show: false
              },
              markLine: {
                //数值为0的Y轴样式
                silent: true,
                symbol: 'none',
                data: [
                  {
                    yAxis: this.echartData.upTwo,
                    name: '二级预警上限',
                    lineStyle: {
                      type: 'solid',
                      color: '#F2C94C',
                      width: 1
                    }
                  }
                ],
                label: {
                  show: true,
                  position: 'insideMiddleTop',
                  fontSize: 12,
                  fontWeight: 'blod',
                  formatter: function (parmas) {
                    return parmas.name + '(' + parmas.value + ')';
                  }
                }
              }
            },
            //二级预警下限
            {
              data: [],
              type: 'line',
              areaStyle: {
                show: false
              },
              markLine: {
                //数值为0的Y轴样式
                silent: true,
                symbol: 'none',
                data: [
                  {
                    yAxis: this.echartData.downTwo,
                    name: '二级预警下限',
                    lineStyle: {
                      type: 'solid',
                      color: '#F2C94C',
                      width: 1
                    }
                  }
                ],
                label: {
                  show: true,
                  position: 'insideMiddleTop',
                  fontSize: 12,
                  fontWeight: 'blod',
                  formatter: function (parmas) {
                    return parmas.name + '(' + parmas.value + ')';
                  }
                }
              }
            },
            //三级预警上限
            {
              data: [],
              type: 'line',
              areaStyle: {
                show: false
              },
              markLine: {
                //数值为0的Y轴样式
                silent: true,
                symbol: 'none',
                data: [
                  {
                    yAxis: this.echartData.upThree,
                    name: '三级预警上限',
                    lineStyle: {
                      type: 'solid',
                      color: '#419aff',
                      width: 1
                    }
                  }
                ],
                label: {
                  show: true,
                  position: 'insideStartTop',
                  fontSize: 12,
                  fontWeight: 'blod',
                  formatter: function (parmas) {
                    return parmas.name + '(' + parmas.value + ')';
                  }
                }
              }
            },
            //三级预警下限
            {
              data: [],
              type: 'line',
              areaStyle: {
                show: false
              },
              markLine: {
                //数值为0的Y轴样式
                silent: true,
                symbol: 'none',
                data: [
                  {
                    yAxis: this.echartData.downThree,
                    name: '三级预警下限',
                    lineStyle: {
                      type: 'solid',
                      color: '#419aff',
                      width: 1
                    }
                  }
                ],
                label: {
                  show: true,
                  position: 'insideStartTop',
                  fontSize: 12,
                  fontWeight: 'blod',
                  formatter: function (parmas) {
                    return parmas.name + '(' + parmas.value + ')';
                  }
                }
              }
            }
          ]
        },
        false
      );
      window.addEventListener('resize', () => {
        this.myChart.resize();
      });
      // setTimeout(() => {
      erd().listenTo(document.getElementById('warnLine'), () => {
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
