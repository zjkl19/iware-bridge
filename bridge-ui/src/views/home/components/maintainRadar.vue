<template>
  <div id="maintainEchart" style="width: 100%; height: 100%"></div>
</template>

<script>
import echarts from 'echarts';
require('echarts/theme/macarons'); // echarts theme
import erd from 'element-resize-detector';
export default {
  name: 'maintainEchart',
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
      let titleList = [
        '技术状况（BCI）',
        '预警处理率',
        '巡查完成率',
        '维养完成率',
        '检测完成率'
      ];
      this.myChart = echarts.init(this.$el, 'macarons');
      this.myChart.clear();
      this.myChart.setOption(
        {
          tooltip: {
            trigger: 'item',
            confine: true,
            formatter: function (data) {
              let html = data.name;
              data.data.value.map((item, i) => {
                if (item != null) {
                  html += '</br>' + titleList[i] + '：' + item;
                }
              });
              return html;
            }
          },
          radar: {
            // shape: 'circle',
            splitArea: {
              areaStyle: {
                color: ['rgba(250,250,250,0)', 'rgba(255,255,255,0.16)']
              }
            },
            center: ['50%', '55%'],
            indicator: this.echartData.indicator
          },
          series: {
            name: '桥隧综合评价指数',
            type: 'radar',
            itemStyle: {
              color: '#03a9f2'
            },
            areaStyle: {
              color: '#03a9f2'
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
      erd().listenTo(document.getElementById('maintainEchart'), () => {
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
