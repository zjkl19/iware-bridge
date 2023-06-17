<template>
  <div
    id="relevanceLine"
    class="relevanceLine"
    style="width: 100%; height: 100%"
  ></div>
</template>

<script>
import erd from 'element-resize-detector';
export default {
  name: 'relevanceLine-dzl',
  components: {},
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
            itemGap: 40,
            itemWidth: 5 * this.size,
            itemHeight: 5 * this.size,
            textStyle: {
              color: '#595959',
              fontSize: 7 * this.size
            },
            data: [
              {
                name: '实测值',
                icon: 'rect'
              },
              {
                name: '拟合值',
                icon: 'line'
              },
              {
                name: '3δ上限',
                icon: 'line'
              },
              {
                name: '3δ下限',
                icon: 'line'
              }
            ]
          },
          grid: {
            top: '5%', //与上面的距离
            left: '3%', //与左边的距离
            right: '3%', //与右边的距离
            bottom: '6%', //与下面的距离
            containLabel: true
          },
          xAxis: {
            type: 'value',
            name: this.echartData.name1,
            nameLocation: 'middle',
            nameTextStyle: { color: '#262626', fontSize: 8 * this.size },
            nameGap: 15 * this.size,
            max: () => {
              let dataList = _this.echartData.xValueList;
              let maxValue = Math.max(...dataList);
              maxValue = Math.ceil(maxValue * 100) / 100;
              return maxValue + 5;
            },
            min: () => {
              let dataList = _this.echartData.xValueList;
              let minValue = Math.min(...dataList);
              minValue = Math.floor(minValue * 100) / 100;
              return minValue - 5;
            },
            axisLabel: { color: '#595959', fontSize: 6 * this.size },
            axisLine: { lineStyle: { color: '#BFBFBF' } },
            axisTick: { lineStyle: { color: '#BFBFBF' } },
            splitArea: { show: false },
            splitLine: { show: false }
          },
          yAxis: {
            type: 'value',
            name: this.echartData.name2,
            nameLocation: 'middle',
            nameTextStyle: { color: '#262626', fontSize: 8 * this.size },
            nameGap: 20 * this.size,
            max: () => {
              let dataList = _this.echartData.yValueList;
              let maxValue = Math.max(...dataList);
              maxValue = Math.ceil(maxValue * 100) / 100;
              return maxValue + 5;
            },
            min: () => {
              let dataList = _this.echartData.yValueList;
              let minValue = Math.min(...dataList);
              minValue = Math.floor(minValue * 100) / 100;
              return minValue - 5;
            },
            axisTick: { show: false },
            axisLine: { lineStyle: { color: '#BFBFBF' } },
            axisLabel: { color: '#595959', fontSize: 7 * this.size },
            splitArea: { show: false },
            splitLine: { lineStyle: { color: '#DEDEDE', type: 'dashed' } }
          },
          series: [
            {
              //实测值
              name: '实测值',
              type: 'scatter',
              symbol: 'rect',
              symbolSize: 6,
              hoverAnimation: false,
              itemStyle: {
                color: '#4541FF'
              },
              data: this.echartData.yData1
            },
            {
              //拟合值
              name: '拟合值',
              type: 'line',
              smooth: false,
              symbol: 'none',
              itemStyle: {
                color: '#4EA7F9'
              },
              data: this.echartData.yData2
            },
            {
              //3δ上限
              name: '3δ上限',
              type: 'line',
              smooth: false,
              symbol: 'none',
              itemStyle: {
                color: '#FF5F5F'
              },
              data: this.echartData.yData3
            },
            {
              //3δ下限
              name: '3δ下限',
              type: 'line',
              smooth: false,
              symbol: 'none',
              itemStyle: {
                color: '#FF5F5F'
              },
              data: this.echartData.yData4
            }
          ]
        },
        false
      );
    }
  },
  mounted() {
    let _this = this;
    this.myChart = this.$echarts.init(document.getElementById('relevanceLine'));
    this.setOption();
    window.addEventListener('resize', () => {
      this.myChart.resize();
    });
    erd().listenTo(document.getElementById('relevanceLine'), () => {
      //监听到事件后执行的业务逻辑
      _this.myChart.resize();
    });
  },
  beforeDestroy() {
    if (!this.myChart) {
      return;
    }
    window.removeEventListener('resize', {});
  }
};
</script>
<style lang="scss" scoped>
.relevanceLine {
}
</style>
