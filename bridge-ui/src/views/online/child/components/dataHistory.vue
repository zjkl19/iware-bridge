<template>
  <div
    :id="'dataHistory' + echartData.id"
    style="width: 100%; height: 100%"
  ></div>
</template>

<script>
import echarts from 'echarts';
require('echarts/theme/macarons'); // echarts theme
import erd from 'element-resize-detector';
export default {
  name: 'dataHistory',
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
      let showArea = this.echartData.minValueData.length == 0 ? true : false;
      this.myChart = echarts.init(this.$el, 'macarons');
      this.myChart.clear();
      this.myChart.setOption(
        {
          background: 'rgba(65, 154, 255, 0.1)',
          tooltip: {
            show: true,
            trigger: 'axis',
            formatter: function (list) {
              // console.log(list);
              let html = `<div style="padding:5px 10px;">`;
              html += `<div>${list[0].name}</div>`;
              if (list.length > 1) {
                list.map((item) => {
                  if (item.seriesName == '最小值')
                    html += `<div>最小值：${item.data}</div>`;
                  if (item.seriesName == '平均值')
                    html += `<div>平均值：${list[0].data + item.data}</div>`;
                  if (item.seriesName == '最大值')
                    html += `<div>最大值：${
                      list[0].data + list[1].data + item.data
                    }</div>`;
                });
              } else {
                // console.log(list);
                if (list[0].seriesName == '频率值') {
                  if (list[0].value)
                    html += `<div>${list[0].seriesName}：${list[0].value}</div>`;
                  else html += `<div>${list[0].seriesName}：-</div>`;
                } else
                  html += `<div>${list[0].seriesName}：${list[0].data}</div>`;
              }
              html += `</div>`;
              return html;
            }
          },
          grid: {
            top: '18%', //与上面的距离
            left: '3%', //与左边的距离
            right: '3%', //与右边的距离
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
            boundaryGap: false,
            data: this.echartData.xData,
            splitLine: { show: false },
            axisLabel: {
              color: '#595959',
              fontSize: 6 * this.size,
              formatter: function (value) {
                let str = value.split(' ')[1] + '\n' + value.split(' ')[0];
                return str;
              }
            },
            axisTick: { inside: true, lineStyle: { color: '#BFBFBF' } },
            axisLine: { lineStyle: { color: '#BFBFBF' } },
            splitArea: { show: false }
          },
          yAxis: {
            type: 'value',
            // max: 'dataMax',
            // min: 'dataMin',
            max: () => {
              let dataList =
                _this.echartData.maxValueData &&
                _this.echartData.maxValueData.length > 0
                  ? _this.echartData.maxValueData
                  : _this.echartData.data;
              let dataArry = [];
              dataList.map((item) => {
                if (item != null) {
                  if (item.value) {
                    dataArry.push(item.value);
                  } else {
                    dataArry.push(item);
                  }
                }
              });

              let maxValue = Math.max(...dataArry);
              maxValue = Math.ceil(maxValue * 100) / 100;
              return maxValue;
            },
            min: () => {
              let dataList =
                _this.echartData.minValueData &&
                _this.echartData.minValueData.length > 0
                  ? _this.echartData.minValueData
                  : _this.echartData.data;
              let dataArry = [];
              dataList.map((item) => {
                if (item != null) {
                  if (item.value) {
                    dataArry.push(item.value);
                  } else {
                    dataArry.push(item);
                  }
                }
              });
              let minValue = Math.min(...dataArry);
              minValue = Math.floor(minValue * 100) / 100;
              return minValue;
            },
            name: this.echartData.unit || '',
            nameTextStyle: { color: '#595959', fontSize: 8 * this.size },
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
              name: this.echartData.name,
              type: 'line',
              symbol: 'none',
              smooth: false,
              itemStyle: {
                color: '#1890FF'
              },
              areaStyle: {
                color: showArea ? 'rgba(65, 154, 255, 0.1)' : 'transparent'
                // color: 'rgba(65, 154, 255, 0.1)'
              },
              data: this.echartData.data
            }
            // {
            //   name: '最小值',
            //   type: 'line',
            //   symbol: 'none',
            //   // stack: 'lb',
            //   smooth: false,
            //   itemStyle: {
            //     color: '#eb5757'
            //   },
            //   lineStyle: {
            //     width: 1,
            //     color: '#1890ff',
            //     type: 'dashed'
            //   },
            //   areaStyle: {
            //     color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            //       {
            //         offset: 0,
            //         color: 'transparent'
            //       },
            //       {
            //         offset: 1,
            //         color: 'rgba(65, 154, 255, 0.1)'
            //       }
            //     ]),
            //     origin: 'end'
            //   },
            //   data: this.echartData.minValueData
            // },
            // {
            //   name: '最大值',
            //   type: 'line',
            //   symbol: 'none',
            //   // stack: 'lb',
            //   smooth: false,
            //   itemStyle: {
            //     color: '#eb5757'
            //   },
            //   lineStyle: {
            //     width: 1,
            //     color: '#1890ff',
            //     type: 'dashed'
            //   },
            //   areaStyle: {
            //     color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            //       {
            //         offset: 0,
            //         color: 'rgba(65, 154, 255, 0.1)'
            //       },
            //       {
            //         offset: 1,
            //         color: 'transparent'
            //       }
            //     ]),
            //     origin: 'start'
            //   },
            //   data: this.echartData.maxValueData
            // }
          ]
        },
        false
      );
      window.addEventListener('resize', () => {
        this.myChart.resize();
      });
      // setTimeout(() => {
      let erdId = 'dataHistory' + this.echartData.id;
      erd().listenTo(document.getElementById(erdId), () => {
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
