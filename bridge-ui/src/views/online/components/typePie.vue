<template>
  <div id="morPie" style="width: 100%; height: 100%"></div>
</template>

<script>
import echarts from 'echarts';
require('echarts/theme/macarons'); // echarts theme
import erd from 'element-resize-detector';
export default {
  name: 'typePie',
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
      let total = 0;
      this.echartData.data.map((text) => {
        total = total * 1 + text.value * 1;
      });
      this.myChart = echarts.init(this.$el, 'macarons');
      this.myChart.clear();
      this.myChart.setOption(
        {
          tooltip: {
            trigger: 'item'
          },
          legend: {
            top: '2%',
            right: '10%',
            type: 'scroll',
            orient: 'vertical',
            pageButtonGap: 4 * this.size,
            pageIconSize: 6 * this.size,
            icon: 'circle',
            itemGap: 11 * this.size,
            itemWidth: 8,
            itemHeight: 8,
            formatter: function (name) {
              let target;
              _this.echartData.data.map((text, index) => {
                if (name == text.name) {
                  target = text.value;
                }
              });
              let arr = ['{left|' + name + '}{right| ' + target + '}'];
              return arr.join();
            },
            textStyle: {
              rich: {
                right: {
                  fontSize: 7 * this.size,
                  color: '#333',
                  fontWeight: 'bold'
                },
                left: {
                  fontSize: 7 * this.size,
                  color: '#595959',
                  width: 72 * this.size
                }
              }
            }
          },
          color: ['#FF5F5F', '#419AFF', '#38ECD9', '#FFD45F', '#FF9B5F'],
          series: [
            {
              name: '传感器类型',
              type: 'pie',
              center: ['25%', '50%'],
              radius: ['70%', '80%'],
              // avoidLabelOverlap: false,
              // silent: true,
              label: {
                show: false,
                position: 'center'
              },
              itemStyle: {
                normal: {
                  borderWidth: 2,
                  borderColor: '#fff'
                }
              },
              //圆环中间样式
              label: {
                normal: {
                  position: 'center',
                  padding: [20, 0, 0, 0],
                  formatter: `{left|传感器总数}\n{right|${total}}`,
                  textStyle: {
                    rich: {
                      right: {
                        fontSize: 16 * this.size,
                        color: '#333333',
                        lineHeight: 22 * this.size
                      },
                      left: {
                        fontSize: 7 * this.size,
                        color: '#595959',
                        fontWeight: '100'
                      }
                    }
                  }
                }
              },
              labelLine: {
                show: false
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
      erd().listenTo(document.getElementById('morPie'), () => {
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
