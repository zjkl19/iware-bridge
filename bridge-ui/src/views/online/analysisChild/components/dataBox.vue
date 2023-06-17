<template>
  <div id="dataBox" style="width: 100%; height: 100%"></div>
</template>

<script>
import echarts from 'echarts';
require('echarts/theme/macarons'); // echarts theme
import erd from 'element-resize-detector';
export default {
  name: 'dataBox',
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
      this.myChart.clear();
      this.myChart.setOption({
        tooltip: {
          show: true,
          trigger: 'item',
          axisPointer: {
            type: 'line',
            label: {
              show: true,
              color: '#1890ff',
              backgroundColor: '#fff',
              borderColor: '#1890ff',
              borderWidth: 1,
              shadowColor: 'transparent',
              formatter: function (name) {
                name.value = name.value.split('-').join('\n');
                return name.value;
              }
            },
            lineStyle: {
              color: 'transparent'
            }
          }
          // formatter: function (params) {
          //   // console.log(params);
          //   let html = `<div>`;
          //   html += `<div>${params[0].name}</div>`;
          //   html += `<div>max：${params[0].data[5]}</div>`;
          //   html += `<div>Q3：${params[0].data[4]}</div>`;
          //   html += `<div>median：${params[0].data[3]}</div>`;
          //   html += `<div>Q1：${params[0].data[2]}</div>`;
          //   html += `<div>min：${params[0].data[1]}</div>`;
          //   // let arry = [];
          //   // params.map((item, i) => {
          //   //   if (i > 0 && item.value[1]) {
          //   //     arry.push(item.value[1]);
          //   //   }
          //   // });
          //   // if (arry.length == 0) arry = '-';
          //   // else arry = arry.join(',');
          //   // html += `<div>异常值：${arry}</div>`;
          //   html += `</div>`;
          //   return html;
          // }
        },
        grid: {
          top: '12%', //与上面的距离
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
            restore: {}
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
        dataZoom: [
          {
            type: 'inside'
          }
        ],
        xAxis: {
          type: 'category',
          data: this.echartData.xData,
          splitLine: { show: false },
          axisLabel: {
            interval: 0,
            color: '#262626',
            fontSize: 7 * this.size,
            formatter: function (params) {
              var newParamsName = ''; // 最终拼接成的字符串
              var paramsNameNumber = params.length; // 实际标签的个数
              var provideNumber = 12; // 每行能显示的字的个数
              var rowNumber = Math.ceil(paramsNameNumber / provideNumber); // 换行的话，需要显示几行，向上取整
              if (paramsNameNumber > provideNumber) {
                // 条件等同于rowNumber>1
                /** 循环每一行,p表示行 */
                for (var p = 0; p < rowNumber; p++) {
                  var tempStr = ''; // 表示每一次截取的字符串
                  var start = p * provideNumber; // 开始截取的位置
                  var end = start + provideNumber; // 结束截取的位置
                  // 此处特殊处理最后一行的索引值
                  if (p == rowNumber - 1) {
                    // 最后一次不换行
                    tempStr = params.substring(start, paramsNameNumber);
                  } else {
                    // 每一次拼接字符串并换行
                    tempStr = params.substring(start, end) + '\n';
                  }
                  newParamsName += tempStr; // 最终拼成的字符串
                }
              } else {
                // 将旧标签的值赋给新标签
                newParamsName = params;
              }
              //将最终的字符串返回
              return newParamsName;
            }
          },
          axisTick: { lineStyle: { color: '#BFBFBF' } },
          axisLine: { lineStyle: { color: '#BFBFBF' } },
          splitArea: { show: false }
        },
        yAxis: {
          type: 'value',
          max: function () {
            return Math.ceil(Math.max(..._this.echartData.maxValList));
          },
          min: function () {
            return Math.floor(Math.min(..._this.echartData.minValList));
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
            name: 'boxplot',
            type: 'boxplot',
            data: this.echartData.data,
            itemStyle: {
              normal: {
                borderColor: '#1890ff',
                borderWidth: 2,
                color: 'rgba(65, 154, 255, 0.1)'
              }
            },
            tooltip: {
              formatter: function (param) {
                return [
                  'Upper: ' + param.data[5] + ' 分',
                  'Q3: ' + param.data[4] + ' 分',
                  'Median: ' + param.data[3] + ' 分',
                  'Q1: ' + param.data[2] + ' 分',
                  'Lower: ' + param.data[1] + ' 分'
                ].join('<br/>');
              }
            }
          },
          {
            name: '异常',
            type: 'scatter',
            symbolSize: 10,
            data: this.echartData.warnData,
            itemStyle: {
              normal: {
                color: '#FF5F5F'
              }
            },
            label: {
              show: false
            }
          }
        ]
      });
      // }, 5000);
    }
  },
  mounted() {
    let _this = this;
    this.myChart = echarts.init(this.$el, 'macarons');
    this.setOption();
    window.addEventListener('resize', () => {
      this.myChart.resize();
    });
    // setTimeout(() => {
    erd().listenTo(document.getElementById('dataBox'), () => {
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
