<template>
  <div id="morPie" style="width: 100%; height: 100%"></div>
</template>

<script>
import echarts from 'echarts';
require('echarts/theme/macarons'); // echarts theme
import erd from 'element-resize-detector';
export default {
  name: 'monitorPie',
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
          color: ['#FF5F5F', '#419AFF'],
          tooltip: {
            show: true,
            trigger: 'item'
          },
          legend: {
            icon: 'circle',
            bottom: 0,
            itemGap: 15 * this.size,
            itemWidth: 8,
            itemHeight: 8,
            textStyle: {
              color: '#595959',
              fontSize: 7 * this.size
            }
          },
          series: [
            {
              name: '三级预警',
              type: 'pie',
              radius: ['48%', '55%'],
              center: ['15%', '45%'],
              // hoverAnimation: false,
              itemStyle: {
                normal: {
                  borderWidth: 2,
                  borderColor: '#fff'
                }
              },
              label: {
                position: 'center',
                formatter: `{text|三级预警}\n{num|${this.echartData.total3}}`,
                padding: [20, 0, 0, 0],
                rich: {
                  text: {
                    color: '#595959',
                    fontSize: 7 * this.size
                  },
                  num: {
                    color: '#333333',
                    fontSize: 14 * this.size,
                    lineHeight: 20 * this.size
                  }
                }
              },
              data: this.echartData.value3,
              labelLine: {
                show: false
              }
            },
            {
              name: '二级预警',
              type: 'pie',
              radius: ['48%', '55%'],
              center: ['50%', '45%'],
              // hoverAnimation: false,
              itemStyle: {
                normal: {
                  borderWidth: 2,
                  borderColor: '#fff'
                }
              },
              label: {
                position: 'center',
                formatter: `{text|二级预警}\n{num|${this.echartData.total2}}`,
                padding: [20, 0, 0, 0],
                rich: {
                  text: {
                    color: '#595959',
                    fontSize: 7 * this.size
                  },
                  num: {
                    color: '#333333',
                    fontSize: 14 * this.size,
                    lineHeight: 20 * this.size
                  }
                }
              },
              data: this.echartData.value2,
              labelLine: {
                show: false
              }
            },
            {
              name: '一级预警',
              type: 'pie',
              radius: ['48%', '55%'],
              center: ['85%', '45%'],
              // hoverAnimation: false,
              itemStyle: {
                normal: {
                  borderWidth: 2,
                  borderColor: '#fff'
                }
              },
              label: {
                position: 'center',
                formatter: `{text|一级预警}\n{num|${this.echartData.total1}}`,
                padding: [20, 0, 0, 0],
                rich: {
                  text: {
                    color: '#595959',
                    fontSize: 7 * this.size
                  },
                  num: {
                    color: '#333333',
                    fontSize: 14 * this.size,
                    lineHeight: 20 * this.size
                  }
                }
              },
              data: this.echartData.value1,
              labelLine: {
                show: false
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
      erd().listenTo(document.getElementById('morPie'), () => {
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
