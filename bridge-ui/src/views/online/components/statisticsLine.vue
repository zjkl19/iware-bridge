<template>
  <div :id="this.echartData.name" style="width: 100%; height: 100%"></div>
</template>

<script>
import echarts from 'echarts';
require('echarts/theme/macarons'); // echarts theme
import erd from 'element-resize-detector';
export default {
  name: 'statisticLine',
  props: {
    echartData: {
      type: Object,
      default: () => {}
    }
  },
  data() {
    return {
      myChart: '',
      dataList: []
    };
  },
  methods: {
    getData() {
      let arry = [];
      let colors = [
        '#419AFF',
        '#FF5F5F',
        '#61DDAA',
        '#78D3F8',
        '#F6BD16',
        '#7262fd',
        '#F08BB4',
        '#9661BC',
        '#F6903D',
        '#65789B'
      ];
      this.echartData.data.map((item, i) => {
        let obj = {
          name: item.name,
          data: item.data,
          type: 'line',
          smooth: false,
          itemStyle: {
            color: colors[i]
          }
        };
        arry.push(obj);
      });
      this.dataList = arry;
      this.$nextTick(() => {
        this.setOption();
      });
    },
    setOption() {
      let _this = this;
      this.myChart = echarts.init(this.$el, 'macarons');
      this.myChart.clear();
      this.myChart.setOption(
        {
          tooltip: {
            trigger: 'axis',
            confine: true
            // axisPointer: {
            //   type: 'none'
            // }
          },
          legend: {
            top: '3%',
            type: 'scroll',
            width: '60%',
            itemHeight: 10,
            textStyle: {
              color: '#595959',
              fontSize: 14
            }
          },
          grid: {
            top: '15%', //与上面的距离
            left: '3%', //与左边的距离
            right: '4%', //与右边的距离
            bottom: '3%', //与下面的距离
            containLabel: true
          },
          xAxis: {
            type: 'category',
            // boundaryGap: false,
            data: this.echartData.date,
            axisLine: { lineStyle: { color: '#BFBFBF' } },
            axisLabel: {
              margin: 10,
              color: '#595959',
              fontSize: 12,
              formatter: function (text) {
                return text.split(' ').join('\n');
              }
            },
            axisTick: { show: false },
            splitArea: { show: false }
          },
          yAxis: {
            type: 'value',
            name: '重量/吨',
            nameTextStyle: { color: '#595959' },
            axisTick: { show: false },
            axisLine: { show: false },
            axisLabel: { color: '#595959', fontSize: 14 },
            splitArea: { show: false },
            splitLine: { lineStyle: { color: '#DEDEDE', type: 'dashed' } }
          },
          // dataZoom: [
          //   {
          //     show: true,
          //     type: 'inside',
          //     realtime: true,
          //     startValue: 0,
          //     endValue: 19,
          //     zoomLock: true
          //   }
          // ],
          series: this.dataList
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
