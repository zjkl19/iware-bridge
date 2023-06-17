<template>
  <div :id="this.echartData.name" style="width: 100%; height: 100%"></div>
</template>

<script>
import echarts from 'echarts';
require('echarts/theme/macarons'); // echarts theme
import erd from 'element-resize-detector';
export default {
  name: 'statisticPie',
  props: {
    echartData: {
      type: Object,
      default: () => {}
    }
  },
  data() {
    return {
      myChart: ''
    };
  },
  methods: {
    setOption() {
      let _this = this;
      let colors =
        this.echartData.type == 1
          ? ['#419AFF', '#4541FF', '#FF5F5F', '#FF7C5F', '#FFAC5F']
          : ['#419AFF', '#4176FF', '#4541FF', '#FF5F5F', '#FF7C5F', '#FFAC5F'];
      this.myChart = echarts.init(this.$el, 'macarons');
      this.myChart.clear();
      this.myChart.setOption(
        {
          color: colors,
          tooltip: {
            show: true,
            trigger: 'item',
            formatter: function (params) {
              let html = '<div style="padding:0 5px">';
              html +=
                '<div style="margin-bottom:5px">' +
                _this.echartData.text1 +
                '：' +
                _this.echartData.text2 +
                '</div>';
              html += '<div style="display:flex;align-items:center;">';
              html +=
                '<span style="width:8px;height:8px;border-radius:50%;margin-right:5px;background:' +
                colors[params.dataIndex] +
                '"></span>';
              html +=
                '<span style="color:#fff">' +
                params.data.name +
                '：' +
                params.data.value +
                ' ' +
                params.percent +
                '%</span>';
              html += '</div>';
              html += '</div>';
              return html;
            }
          },
          title: {
            text:
              '{text1|' +
              this.echartData.text1 +
              '}' +
              (this.echartData.text2 == '' ? '' : '\n') +
              '{text2|' +
              this.echartData.text2 +
              '}',
            top: 'center',
            left: 'center',
            textStyle: {
              rich: {
                text1: {
                  fontSize: 14,
                  color: '#333',
                  padding: [7, 0]
                },
                text2: {
                  fontSize: 14,
                  color: '#666'
                }
              }
            }
          },
          series: {
            type: 'pie',
            radius: ['40%', '60%'],
            center: ['50%', '50%'],
            hoverAnimation: false,
            label: {
              // show: false
              fontSize: 12,
              color: '#595959',
              formatter: function (data) {
                if (_this.echartData.type == 1) {
                  return data.name + '：' + data.percent + '%';
                } else {
                  return data.name;
                }
              }
            },
            labelLine: {
              // show: false
            },
            itemStyle: {
              borderColor: '#fff',
              borderWidth: 2
            },
            data: this.echartData.data
          }
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
