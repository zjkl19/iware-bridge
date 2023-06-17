<template>
  <div :id="'dataLine' + echartData.id" style="width: 100%; height: 100%"></div>
</template>

<script>
import echarts from 'echarts';
require('echarts/theme/macarons'); // echarts theme
import erd from 'element-resize-detector';
export default {
  name: 'dataLine',
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
            show: true,
            trigger: 'axis',
            formatter: function (params) {
              let data = params[0].data;
              let html = '<div>';
              html += `<div>${data.samplingTime}</div>`;
              html += `<div>测点编号：${data.sensorCoding}</div>`;
              html += `<div>总重：${data.value[1]}吨</div>`;
              if (data.singleShaftNuber && !!data.singleShaftNuber)
                html += `<div>轴数：${data.singleShaftNuber}</div>`;
              if (data.modelName && !!data.modelName)
                html += `<div>车型：${data.modelName}</div>`;
              if (data.licensePlate && !!data.licensePlate)
                html += `<div>车牌号：${data.licensePlate}</div>`;
              html += '</div>';
              return html;
            }
          },
          grid: {
            top: '18%', //与上面的距离
            left: '5%', //与左边的距离
            right: '5%', //与右边的距离
            bottom: '5%', //与下面的距离
            containLabel: true
          },
          toolbox: {
            top: '3%',
            left: 'center',
            feature: {
              dataZoom: {
                // yAxisIndex: "none"
                type: 'inside' //使鼠标在图表中时滚轮可用
              },
              restore: {},
              brushStyle: {
                color: 'rgba(65, 154, 255, 0.1)'
              }
              // saveAsImage: {}
            },
            iconStyle: {
              borderColor: '#595959'
            },
            emphasis: {
              iconStyle: {
                borderColor: '#1890ff'
              }
            }
          },
          xAxis: {
            type: 'category',
            data: this.echartData.date,
            splitLine: { show: false },
            axisLabel: { color: '#595959', fontSize: 6 * this.size },
            axisTick: { inside: true, lineStyle: { color: '#BFBFBF' } },
            axisLine: { lineStyle: { color: '#BFBFBF' } },
            splitArea: { show: false }
          },
          yAxis: {
            name: this.echartData.unit,
            nameTextStyle: { color: '#595959', fontSize: 7 * this.size },
            type: 'value',
            max: () => {
              let dataArry = _this.echartData.data.map((item) => {
                return item.value[1];
              });
              let maxValue = Math.max(...dataArry);
              return maxValue;
            },
            min: () => {
              let dataArry = _this.echartData.data.map((item) => {
                return item.value[1];
              });
              let minVal = Math.min(...dataArry);
              return minVal;
            },
            splitLine: {
              lineStyle: { width: 1, type: 'dashed', color: '#dedede' }
            },
            axisLabel: { color: '#595959', fontSize: 7 * this.size },
            axisTick: { show: false },
            axisLine: { lineStyle: { color: '#BFBFBF' } },
            splitArea: { show: false }
          },
          series: [
            {
              type: 'scatter',
              symbolSize: 7 * this.size,
              itemStyle: {
                color: '#419aff'
              },
              data: this.echartData.data
            }
          ]
        },
        false
      );
    },
    setOptionPush() {
      let _this = this;
      this.myChart.setOption(
        {
          tooltip: {
            show: true,
            trigger: 'axis',
            formatter: function (params) {
              let data = params[0].data;
              let html = '<div>';
              html += `<div>${data.samplingTime}</div>`;
              html += `<div>测点编号：${data.sensorCoding}</div>`;
              html += `<div>总重：${data.value[1]}吨</div>`;
              if (data.singleShaftNuber && !!data.singleShaftNuber)
                html += `<div>轴数：${data.singleShaftNuber}</div>`;
              if (data.modelName && !!data.modelName)
                html += `<div>车型：${data.modelName}</div>`;
              if (data.licensePlate && !!data.licensePlate)
                html += `<div>车牌号：${data.licensePlate}</div>`;
              html += '</div>';
              return html;
            }
          },
          grid: {
            top: '18%', //与上面的距离
            left: '5%', //与左边的距离
            right: '5%', //与右边的距离
            bottom: '5%', //与下面的距离
            containLabel: true
          },
          toolbox: {
            top: '3%',
            left: 'center',
            feature: {
              dataZoom: {
                // yAxisIndex: "none"
                type: 'inside' //使鼠标在图表中时滚轮可用
              },
              restore: {},
              brushStyle: {
                color: 'rgba(65, 154, 255, 0.1)'
              }
              // saveAsImage: {}
            },
            iconStyle: {
              borderColor: '#595959'
            },
            emphasis: {
              iconStyle: {
                borderColor: '#1890ff'
              }
            }
          },
          xAxis: {
            type: 'category',
            data: this.echartData.date,
            splitLine: { show: false },
            axisLabel: {
              color: '#595959',
              fontSize: 6 * this.size,
              formatter: function (time) {
                if (time.split('-').length > 2) {
                  return time;
                } else {
                  return _this.$utils.Dateformat(new Date(Number(time)));
                }
              }
            },
            axisTick: { inside: true, lineStyle: { color: '#BFBFBF' } },
            axisLine: { lineStyle: { color: '#BFBFBF' } },
            splitArea: { show: false }
          },
          yAxis: {
            name: this.echartData.unit,
            nameTextStyle: { color: '#595959', fontSize: 7 * this.size },
            type: 'value',
            max: () => {
              let dataArry = _this.echartData.data.map((item) => {
                return item.value[1];
              });
              let maxValue = Math.max(...dataArry);
              return maxValue;
            },
            min: () => {
              let dataArry = _this.echartData.data.map((item) => {
                return item.value[1];
              });
              let minVal = Math.min(...dataArry);
              return minVal;
            },
            splitLine: {
              lineStyle: { width: 1, type: 'dashed', color: '#dedede' }
            },
            axisLabel: { color: '#595959', fontSize: 7 * this.size },
            axisTick: { show: false },
            axisLine: { show: false },
            splitArea: { show: false }
          },
          series: [
            {
              type: 'scatter',
              symbolSize: 7 * this.size,
              itemStyle: {
                color: '#419aff'
              },
              data: this.echartData.data
            }
          ]
        },
        false
      );
    }
  },
  mounted() {
    let _this = this;
    this.setOption();

    window.addEventListener('resize', () => {
      this.myChart.resize();
    });
    // setTimeout(() => {
    let erdId = 'dataLine' + this.echartData.id;
    erd().listenTo(document.getElementById(erdId), () => {
      //监听到事件后执行的业务逻辑
      _this.myChart.resize();
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
