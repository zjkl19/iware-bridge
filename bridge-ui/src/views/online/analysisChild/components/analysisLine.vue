<template>
  <div
    :id="'analysisLine' + echartData.id"
    class="analysisLine"
    style="width: 100%; height: 100%"
  ></div>
</template>

<script>
import erd from 'element-resize-detector';
export default {
  name: 'analysisLine-dzl',
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
          title: {
            text: this.echartData.name,
            // textAlign: 'center',
            left: 'center',
            textStyle: {
              color: '#000',
              fontSize: 7 * this.size,
              lineHeight: 11 * this.size
            }
          },
          tooltip: {
            trigger: 'axis',
            confine: true
            // axisPointer: {
            //   type: 'none'
            // }
          },
          grid: {
            top: '15%', //与上面的距离
            left: '2%', //与左边的距离
            right: '3%', //与右边的距离
            bottom: '3%', //与下面的距离
            containLabel: true
          },
          xAxis: {
            type: 'category',
            boundaryGap: false,
            data: this.echartData.xData,
            axisLabel: { color: '#595959', fontSize: 6 * this.size },
            axisLine: { lineStyle: { color: '#BFBFBF' } },
            axisTick: { lineStyle: { color: '#BFBFBF' } },
            splitArea: { show: false }
          },
          yAxis: {
            type: 'value',
            max: () => {
              let dataList = _this.echartData.yData;
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
              let dataList = _this.echartData.yData;
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
            name: this.echartData.unit,
            nameTextStyle: { color: '#595959' },
            axisTick: { show: false },
            axisLine: { lineStyle: { color: '#BFBFBF' } },
            axisLabel: { color: '#595959', fontSize: 7 * this.size },
            splitArea: { show: false },
            splitLine: { lineStyle: { color: '#DEDEDE', type: 'dashed' } }
          },
          series: [
            {
              type: 'line',
              smooth: true,
              symbol: 'none',
              itemStyle: {
                color: '#4EA7F9'
              },
              data: this.echartData.yData
            }
          ]
        },
        false
      );
    }
  },
  mounted() {
    let _this = this;
    let id = 'analysisLine' + this.echartData.id;
    this.myChart = this.$echarts.init(document.getElementById(id));
    this.setOption();
    window.addEventListener('resize', () => {
      this.myChart.resize();
    });
    erd().listenTo(document.getElementById(id), () => {
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
.analysisLine {
}
</style>
