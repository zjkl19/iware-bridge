<template>
  <div id="changeLine" style="width: 100%; height: 100%"></div>
</template>

<script>
import echarts from 'echarts';
require('echarts/theme/macarons'); // echarts theme
import erd from 'element-resize-detector';
export default {
  name: 'changeLine',
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
              type: 'none'
            },
            formatter: function (params) {
              // console.log(params);
              let html = `<div style="padding:0 10px">`;
              if (_this.echartData.text == 1) {
                html += `<div>${params[0].name}时</div>`;
              } else if (_this.echartData.text == 2) {
                let month = new Date().getMonth() + 1;
                html += `<div>${month}月${params[0].name}日</div>`;
              } else {
                html += `<div>${params[0].name}月</div>`;
              }
              for (let i = 0; i < params.length; i++) {
                html += `<div style="display:flex;align-items:center;">`;
                if (params[i].seriesName == '一级') {
                  html += `<div style="width:8px;height:8px;border-radius:50%;background:#FF5F5F;margin-right:10px;"></div>`;
                } else if (params[i].seriesName == '二级') {
                  html += `<div style="width:8px;height:8px;border-radius:50%;background:#FFD45F;margin-right:10px;"></div>`;
                } else {
                  html += `<div style="width:8px;height:8px;border-radius:50%;background:#419AFF;margin-right:10px;"></div>`;
                }
                html += `<div>${params[i].seriesName}：${params[i].value}</div>`;
                html += `</div>`;
              }
              html += `</div>`;
              return html;
            }
          },
          legend: {
            itemGap: 15 * this.size,
            itemWidth: 15 * this.size,
            itemHeight: 8,
            textStyle: {
              color: '#595959',
              fontSize: 7 * this.size
            }
          },
          grid: {
            left: '2%',
            right: '2%',
            bottom: '0%',
            top: '15%',
            containLabel: true
          },
          xAxis: {
            type: 'category',
            name: this.echartData.unit,
            boundaryGap: false,
            splitLine: { show: false },
            axisLabel: { color: '#595959', fontSize: 6 * this.size },
            axisTick: { inside: true, lineStyle: { color: '#BFBFBF' } },
            axisLine: { lineStyle: { color: '#BFBFBF' } },
            splitArea: { show: false },
            nameTextStyle: { color: '#595959' },
            data: this.echartData.xName
          },
          yAxis: {
            type: 'value',
            name: '次',
            splitNumber: 4,
            splitLine: {
              lineStyle: { width: 1, type: 'dashed', color: '#dedede' }
            },
            axisLabel: { color: '#595959', fontSize: 7 * this.size },
            axisTick: { show: false },
            axisLine: { show: false },
            splitArea: { show: false },
            nameTextStyle: { color: '#595959' }
          },
          series: [
            {
              name: '一级',
              type: 'line',
              z: 3,
              smooth: false,
              itemStyle: {
                color: '#FF5F5F'
              },
              areaStyle: {
                color: 'rgba(255, 95, 95, 0.25)'
              },
              data: this.echartData.data1
            },
            {
              name: '二级',
              type: 'line',
              z: 2,
              smooth: false,
              itemStyle: {
                color: '#FFD45F'
              },
              areaStyle: {
                color: 'rgba(255, 212, 95, 0.35)'
              },
              data: this.echartData.data2
            },
            {
              name: '三级',
              type: 'line',
              z: 1,
              smooth: false,
              itemStyle: {
                color: '#419AFF'
              },
              areaStyle: {
                color: 'rgba(65, 154, 255, 0.1)'
              },
              data: this.echartData.data3
            }
          ]
        },
        false
      );
      window.addEventListener('resize', () => {
        this.myChart.resize();
      });
      // setTimeout(() => {
      erd().listenTo(document.getElementById('changeLine'), () => {
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
