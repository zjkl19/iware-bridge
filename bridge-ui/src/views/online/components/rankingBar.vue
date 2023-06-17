<template>
  <div id="rankBar" style="width: 100%; height: 100%"></div>
</template>

<script>
import echarts from 'echarts';
require('echarts/theme/macarons'); // echarts theme
import erd from 'element-resize-detector';
export default {
  name: 'rankingBar',
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
      let max = 0;
      let oneWarnData = []; //一级预警右侧显示总数
      for (let i = 0; i < this.echartData.xName.length; i++) {
        let total =
          this.echartData.value1[i] +
          this.echartData.value2[i] +
          this.echartData.value3[i];
        if (total > max) {
          max = total;
        }
        let obj = {
          value: this.echartData.value1[i],
          total
        };
        oneWarnData.push(obj);
      }
      this.myChart = echarts.init(this.$el, 'macarons');
      this.myChart.clear();
      this.myChart.setOption(
        {
          color: ['#419AFF', '#FFD45F', '#FF5F5F'],
          tooltip: {
            show: true,
            trigger: 'axis',
            axisPointer: {
              type: 'shadow'
            }
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
          grid: {
            left: '2%',
            right: '15%',
            bottom: '18%',
            top: '5%',
            containLabel: true
          },
          xAxis: {
            type: 'value',
            max: max,
            splitLine: { show: false },
            axisLabel: { show: false },
            axisTick: { show: false },
            axisLine: { show: false },
            splitArea: { show: false }
          },
          yAxis: {
            type: 'category',
            inverse: true,
            axisLine: { show: false },
            axisLabel: {
              color: '#595959',
              fontSize: 6 * this.size
            },
            axisTick: { show: false },
            splitArea: { show: false },
            data: this.echartData.xName
          },
          dataZoom: [
            {
              show: this.echartData.xName.length > 3,
              type: 'slider',
              yAxisIndex: [0],
              right: 0,
              width: 15,
              labelFormatter: '',
              realtime: true,
              startValue: 0,
              endValue: 4,
              zoomLock: true,
              handleSize: 0,
              moveHandleSize: 0,
              dataBackground: {
                lineStyle: { opacity: 0 },
                areaStyle: { opacity: 0 }
              },
              fillerColor: '#ebeef5',
              borderColor: 'rgba(255,255,255,0)'
            },
            {
              show: true,
              type: 'inside',
              yAxisIndex: [0],
              realtime: true,
              startValue: 0,
              endValue: 2,
              zoomLock: true
            }
          ],
          series: [
            {
              name: '三级预警',
              type: 'bar',
              stack: 'warn',
              barWidth: 6 * this.size,
              label: {
                show: false
              },
              data: this.echartData.value3
            },
            {
              name: '二级预警',
              type: 'bar',
              stack: 'warn',
              barWidth: 6 * this.size,
              label: {
                show: false
              },
              data: this.echartData.value2
            },
            {
              name: '一级预警',
              type: 'bar',
              stack: 'warn',
              barWidth: 6 * this.size,
              label: {
                show: true,
                color: '#333',
                fontSize: 6 * this.size,
                position: 'right',
                distance: 4 * this.size,
                offset: [0, 2],
                formatter: function (params) {
                  // console.log(params);
                  // if (params.data != 0) {
                  //   return params.data;
                  // } else {
                  //   return '';
                  // }
                  return params.data.total;
                }
              },
              data: oneWarnData
            }
          ]
        },
        false
      );
      window.addEventListener('resize', () => {
        this.myChart.resize();
      });
      // setTimeout(() => {
      erd().listenTo(document.getElementById('rankBar'), () => {
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
