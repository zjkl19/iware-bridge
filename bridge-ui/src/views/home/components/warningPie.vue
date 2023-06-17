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
          color: ['#03a9f2', '#ee7902'],
          tooltip: {
            show: true,
            trigger: 'item'
          },
          title: {
            text: '{value|' + this.echartData.total + '}\n{text|预警总数}',
            top: 'center',
            left: 'center',
            textStyle: {
              rich: {
                value: {
                  fontSize: 26,
                  color: '#fafafb',
                  padding: [7, 0]
                },
                text: {
                  fontSize: 12,
                  color: '#fff'
                }
              }
            }
          },
          series: {
            type: 'pie',
            radius: ['60%', '80%'],
            center: ['50%', '50%'],
            hoverAnimation: false,
            label: {
              show: false
            },
            labelLine: {
              show: false
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
