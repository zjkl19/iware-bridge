<template>
  <div id="surveryEchart" style="width: 100%; height: 100%"></div>
</template>

<script>
import echarts from 'echarts';
require('echarts/theme/macarons'); // echarts theme
import erd from 'element-resize-detector';
import 'echarts-liquidfill';
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
      angle: 0
    };
  },
  methods: {
    setOption() {
      let _this = this;
      this.myChart = echarts.init(this.$el, 'macarons');
      this.myChart.clear();
      this.myChart.setOption(
        {
          title: {
            text:
              '{value|' +
              this.echartData.percent +
              '%}\n{text|' +
              this.echartData.text +
              '}',
            textStyle: {
              rich: {
                value: {
                  fontSize: 26,
                  color: '#fafafb',
                  padding: [5, 0]
                },
                text: {
                  fontSize: 12,
                  color: '#fff'
                }
              }
            },
            x: 'center',
            y: 'center'
          },
          series: [
            {
              name: '小环',
              type: 'gauge',
              splitNumber: 12,
              radius: '65%',
              center: ['50%', '50%'],
              startAngle: 0,
              endAngle: 360,
              axisLine: {
                show: false
              },
              axisTick: {
                show: true,
                lineStyle: {
                  color: this.echartData.color,
                  width: 5
                },
                length: 1,
                splitNumber: 3
              },
              splitLine: {
                show: false
              },
              axisLabel: {
                show: false
              },
              detail: {
                show: false
              }
            },
            {
              type: 'liquidFill',
              radius: '55%',
              center: ['50%', '50%'],
              backgroundStyle: {
                color: this.echartData.bgColor
              },
              color: [
                //水波的颜色
                this.echartData.color
              ],
              label: {
                normal: {
                  formatter: ''
                }
              },
              outline: {
                show: false,
                borderDistance: 5,
                itemStyle: {
                  type: 'dotted',
                  borderWidth: 1,
                  borderColor: 'rgba(255,255,255,0.6)'
                }
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
      erd().listenTo(document.getElementById('surveryEchart'), () => {
        //监听到事件后执行的业务逻辑
        _this.myChart.resize();
      });
      // }, 5000);
    }
  },
  mounted() {
    // let _this = this;
    this.setOption();
    // this.timer = setInterval(function () {
    //   //用setInterval做动画感觉有问题
    //   _this.draw();
    // }, 100);
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
