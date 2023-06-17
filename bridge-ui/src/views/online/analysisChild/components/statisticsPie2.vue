<template>
  <div :id="this.echartData.name" style="width: 100%; height: 100%"></div>
</template>

<script>
import echarts from 'echarts';
require('echarts/theme/macarons'); // echarts theme
import erd from 'element-resize-detector';
export default {
  name: 'statisticPie',
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
          color: ['#419AFF', '#FF5F5F'],
          tooltip: {
            trigger: 'item'
          },
          series: {
            type: 'pie',
            radius: ['0%', '80%'],
            center: ['50%', '50%'],
            // hoverAnimation: false,
            label: { fontSize: 12, color: '#595959' },
            labelLine: {},
            itemStyle: { borderColor: '#fff', borderWidth: 2 },
            data: this.echartData.data
          }
        },
        false
      );
      window.addEventListener('resize', () => {
        this.myChart.resize();
      });
      // setTimeout(() => {
      erd().listenTo(document.getElementById(this.echartData.name), () => {
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
