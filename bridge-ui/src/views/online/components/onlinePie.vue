<template>
  <div id="onlinePie" style="width: 100%; height: 100%"></div>
</template>

<script>
import echarts from 'echarts';
require('echarts/theme/macarons'); // echarts theme
import erd from 'element-resize-detector';
export default {
  name: 'onlinePie',
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
      this.echartData.data.map((item) => {
        total += item.value;
      });
      this.myChart = echarts.init(this.$el, 'macarons');
      this.myChart.clear();
      this.myChart.setOption(
        {
          color: ['#FF5F5F', '#419AFF', '#38ECD9', '#FFD45F', '#FF9B5F'],
          tooltip: {
            show: true,
            trigger: 'item'
          },
          title: {
            left: '23%',
            top: '41%',
            text: '{text|传感器总数}\n{value|' + total + '}',
            textStyle: {
              rich: {
                value: {
                  fontSize: 16 * this.size,
                  color: '#333333',
                  lineHeight: 22.5 * this.size,
                  align: 'center'
                  // padding: [0, 15]
                },
                text: {
                  fontSize: 7 * this.size,
                  color: '#595959'
                }
              }
            }
          },
          legend: {
            icon: 'circle',
            // top: '5%',
            top: 'center',
            left: '60%',
            orient: 'vertical',
            type: 'scroll',
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
                  color: '#595959',
                  fontWeight: 'bold'
                },
                left: {
                  fontSize: 7 * this.size,
                  color: '#595959',
                  width: 70 * this.size
                }
              }
            }
          },
          series: [
            {
              name: '传感器统计',
              type: 'pie',
              radius: ['52%', '60%'],
              center: ['30%', '50%'],
              hoverAnimation: false,
              itemStyle: {
                normal: {
                  borderWidth: 2,
                  borderColor: '#fff'
                }
              },
              label: { show: false },
              data: this.echartData.data,
              labelLine: {
                show: false
              }
            }
          ]
        },
        false
      );
    }
  },
  mounted() {
    this.setOption();
    window.addEventListener('resize', () => {
      this.myChart.resize();
    });
    // setTimeout(() => {
    erd().listenTo(document.getElementById('onlinePie'), () => {
      //监听到事件后执行的业务逻辑
      this.myChart.resize();
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
