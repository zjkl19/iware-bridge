<template>
  <div :id="'envelope' + echartData.id" class="envelope"></div>
</template>

<script>
import Highcharts from 'highcharts/highstock';
import highchartsMore from 'highcharts/highcharts-more';
highchartsMore(Highcharts);
export default {
  name: 'envelope-dzl',
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
      let data = [];
      this.echartData.minValueData.map((item, i) => {
        data.push([this.echartData.maxValueData[i], item]);
      });
      this.$nextTick(() => {
        this.showEchart(data);
      });
    },
    showEchart(areaData) {
      // console.log(areaData);
      let _this = this;
      let max = Math.max(...this.echartData.maxValueData);
      let min = Math.min(...this.echartData.minValueData);
      let elementId = 'envelope' + this.echartData.id;
      this.myChart = Highcharts.chart(elementId, {
        chart: {
          marginTop: 50,
          marginLeft: 80,
          marginRight: 50,
          marginBottom: 50
        },
        title: { text: '' },
        legend: {
          enabled: false
        },
        xAxis: {
          // type: 'category',
          categories: this.echartData.xData,
          labels: {
            formatter: function (params) {
              // console.log(params);
              let title =
                params.value.split(' ')[0] + ' ' + params.value.split(' ')[1];
              let html = `<span class="x-labels" title="${title}">${title}</span>`;
              return html;
            },
            rotation: 0,
            style: {
              color: '#595959',
              fontSize: 6 * this.size
            }
          },
          lineColor: '#BFBFBF',
          tickLength: 6,
          tickWidth: 1,
          tickInterval:
            areaData.length > 100 ? 5 : areaData.length > 20 ? 4 : 2,
          tickColor: '#BFBFBF',
          tickPosition: 'inside',
          tickmarkPlacement: 'on'
        },
        yAxis: {
          max,
          min,
          title: {
            text: this.echartData.unit || '',
            align: 'high',
            offset: -10,
            rotation: 0,
            y: -20,
            style: {
              color: '#595959',
              fontSize: 8 * this.size
            }
          },
          labels: {
            style: {
              color: '#595959',
              fontSize: 7 * this.size
            }
          },
          lineColor: '#BFBFBF',
          lineWidth: 1,
          gridLineColor: '#dedede',
          gridLineDashStyle: 'LongDash'
        },
        tooltip: {
          shared: true,
          backgroundColor: 'rgba(50, 50, 50, 0.5)',
          borderColor: 'transparent',
          borderRadius: 4,
          style: {
            color: '#fff',
            fontSize: 14,
            lineHeight: 24
          },
          headerFormat: '<b style="font-size:14px;">{point.key}</b><br/>',
          pointFormatter: function () {
            // let unit = _this.echartData.unit || '';
            let value = _this.echartData.data[this.index];
            let maxValue = _this.echartData.maxValueData[this.index];
            let minValue = _this.echartData.minValueData[this.index];
            let html = `<b style="font-size:14px;>平均值: <span>${value}</span></b><br/>
                        <b style="font-size:14px;>最大值: <span>${maxValue}</span></b><br/>
                        <b style="font-size:14px;>最小值：<span>${minValue}</span></b><br/>`;
            return html;
          },
          valueSuffix: this.echartData.unit || ''
        },
        series: [
          {
            name: '平均值',
            type: 'line',
            zIndex: 10, // 控制显示层叠
            color: '#419aff',
            lineWidth: 2,
            marker: {
              enabled: false
            },
            data: this.echartData.data
          },
          {
            name: '最大值、最小值',
            type: 'arearange',
            color: '#419aff',
            fillOpacity: 0.1,
            dashStyle: 'LongDash',
            lineWidth: 1,
            marker: {
              enabled: false
            },
            data: areaData
          }
        ]
      });

      window.addEventListener('resize', () => {
        this.setOption();
      });
    }
  },
  mounted() {
    this.setOption();
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
.envelope {
  height: 100%;
  width: 100%;
  /deep/ .x-labels {
    color: red;
  }
  /deep/ .highcharts-credits {
    display: none;
  }
}
</style>
