<template>
  <div :id="this.echartData.name" style="width: 100%; height: 100%"></div>
</template>

<script>
import echarts from 'echarts';
require('echarts/theme/macarons'); // echarts theme
import erd from 'element-resize-detector';
export default {
  name: 'statisticBar',
  props: {
    echartData: {
      type: Object,
      default: () => {}
    }
  },
  data() {
    return {
      myChart: '',
      dataList: [],
      size: window.innerHeight / 70 / 7,
      timer: null
    };
  },
  methods: {
    getData() {
      if (this.timer && !!this.timer) {
        clearInterval(this.timer);
      }
      let arry = [];
      let colors = [
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
        if (item.type == 'line') {
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
        } else {
          let obj = {
            name: item.name,
            type: 'bar',
            yAxisIndex: 1,
            barWidth: 10 * this.size,
            itemStyle: {
              color: '#5B8FF9'
            },
            data: item.data
          };
          arry.push(obj);
        }
      });
      this.dataList = arry;
      this.$nextTick(() => {
        this.setOption();
      });
    },
    setOption() {
      let _this = this;
      let colors = [
        '#61DDAA',
        '#78D3F8',
        '#F6BD16',
        '#7262fd',
        '#F08BB4',
        '#9661BC',
        '#F6903D',
        '#65789B'
      ];
      this.myChart = echarts.init(this.$el, 'macarons');
      this.myChart.clear();
      let option = {
        tooltip: {
          show: true,
          trigger: 'axis',
          confine: true,
          // axisPointer: {
          //   type: 'none'
          // },
          formatter: function (data) {
            let total = 0;
            data.map((item) => {
              if (item.seriesType != 'bar') {
                total += item.value;
              }
            });
            let html = '<div style="padding:0 10px">';
            html += '<div style="margin-bottom:5px">' + data[0].name + '</div>';
            data.map((item) => {
              html +=
                '<div style="display:flex;align-items:center;justify-content: space-between;">';
              html +=
                '<div style="margin-right:10px;display:flex;align-items:center;">';
              if (item.seriesType != 'bar') {
                html +=
                  '<span style="width:8px;height:8px;border-radius:50%;margin-right:5px;background:' +
                  colors[item.seriesIndex] +
                  '"></span>';
              } else {
                html +=
                  '<span style="width:8px;height:8px;border-radius:50%;margin-right:5px;background:#5B8FF9"></span>';
              }

              html +=
                '<span style="color:#fff;">' +
                item.seriesName +
                '：' +
                item.value +
                '<span>';
              html += '</div>';
              html +=
                '<span>' +
                (item.value == 0 ? 0 : Math.round((item.value / total) * 100)) +
                '%</span>';
              html += '</div>';
            });
            html += '</div>';
            return html;
          }
        },
        legend: {
          top: '3%',
          type: 'scroll',
          width: '65%',
          itemHeight: 10,
          textStyle: {
            color: '#595959',
            fontSize: 14
          }
        },
        grid: {
          top: '15%', //与上面的距离
          left: '3%', //与左边的距离
          right: '2%', //与右边的距离
          bottom: '3%', //与下面的距离
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: this.echartData.xData,
          // boundaryGap: false,
          interval: 0,
          axisLine: { lineStyle: { color: '#BFBFBF' } },
          axisLabel: {
            margin: 10,
            color: '595959',
            fontSize: 12,
            formatter: function (text) {
              if (_this.echartData.type == 1) {
                return text.split(' ').join('\n');
              } else {
                return text;
              }
            }
          },
          axisTick: { show: false },
          splitArea: { show: false }
        },
        yAxis: [
          {
            name: this.echartData.yName1,
            type: 'value',
            nameTextStyle: { color: '#595959' },
            axisTick: { show: false },
            axisLine: { show: false },
            axisLabel: { color: '#595959', fontSize: 14 },
            splitArea: { show: false },
            splitLine: { lineStyle: { color: '#DEDEDE', type: 'dashed' } }
          },
          {
            name: this.echartData.yName2,
            type: 'value',
            position: 'right',
            nameTextStyle: { color: '#595959' },
            axisTick: { show: false },
            axisLine: { show: false },
            axisLabel: { color: '#595959', fontSize: 14 },
            splitArea: { show: false },
            splitLine: { lineStyle: { color: '#DEDEDE', type: 'dashed' } }
          }
        ],
        dataZoom: [
          {
            show: false,
            // type: 'inside',
            type: 'slider',
            // realtime: true,
            startValue: 0,
            endValue: 30
            // zoomLock: true
          }
        ],
        series: this.dataList
      };
      this.myChart.setOption(option, false);
      window.addEventListener('resize', () => {
        this.myChart.resize();
      });
      // setTimeout(() => {
      erd().listenTo(document.getElementById(this.echartData.name), () => {
        //监听到事件后执行的业务逻辑
        _this.myChart.resize();
      });
      // }, 5000);
      this.$nextTick(() => {
        if (_this.dataList[0].data.length > 30) {
          _this.timer = setInterval(() => {
            // 每次向后滚动一个，最后一个从头开始。
            if (
              option.dataZoom[0].endValue ==
              _this.dataList[0].data.length - 1
            ) {
              option.dataZoom[0].endValue = 30;
              option.dataZoom[0].startValue = 0;
            } else {
              option.dataZoom[0].endValue = option.dataZoom[0].endValue + 1;
              option.dataZoom[0].startValue = option.dataZoom[0].startValue + 1;
            }
            _this.myChart.setOption(option);
          }, 2000);
        }
      });
    }
  },
  mounted() {
    this.setOption();
  },
  created() {},
  beforeDestroy() {
    if (this.timer && !!this.timer) {
      clearInterval(this.timer);
    }
    if (!this.myChart) {
      return;
    }
    window.removeEventListener('resize', {});
  }
};
</script>
