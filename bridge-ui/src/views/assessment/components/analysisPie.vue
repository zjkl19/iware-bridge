<template>
  <div id="analysisPie" style="width: 100%; height: 100%"></div>
</template>

<script>
import echarts from 'echarts';
require('echarts/theme/macarons'); // echarts theme
import erd from 'element-resize-detector';
export default {
  name: 'analysisPie',
  props: {
    echartData: {
      type: Object,
      default: () => {}
    }
  },
  data() {
    return {
      myChart: '',
      multiple: window.innerHeight / 70 / 7
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
            //鼠标停放显示数据
            trigger: 'item',
            formatter: function (parms) {
              var str =
                parms.marker +
                '' +
                parms.data.name +
                '</br>' +
                '数量：' +
                parms.data.value +
                '</br>' +
                '占比：' +
                parms.percent +
                '%';
              return str;
            },
            extraCssText:
              'width:8vw;height:8vh;font-size:1.4vh;line-height:2.5vh'
          },
          legend: {
            orient: 'vertical', //图例横向显示
            top: 'center', //图例位置
            // left: "center",
            left: '60%',
            itemGap: 12 * this.multiple, //图例间隔
            icon: 'circle', //图例前图标
            itemWidth: 4 * this.multiple,
            itemHeight: 4 * this.multiple,
            //图例显示方式
            formatter: function (name) {
              let target;
              _this.echartData.data.map((text, index) => {
                if (name == text.name) {
                  target = text.value;
                }
              });
              let arr = ['{b|' + name + '}{c| ' + target + '}'];
              return arr.join();
            },
            //图例样式
            textStyle: {
              rich: {
                c: {
                  //verticalAlign: "right",
                  fontSize: 40 * this.multiple + '%',
                  color: '#595959'
                },
                b: {
                  fontSize: 40 * this.multiple + '%',
                  color: '#595959',
                  width: 60 * this.multiple
                }
              }
            }
          },
          series: [
            {
              name: '结构病害数量统计',
              type: 'pie',
              center: ['30%', '50%'],
              radius: ['55%', '63%'], //圆环大小
              avoidLabelOverlap: false, //圆环提示
              label: {
                show: false,
                position: 'center'
              },
              emphasis: {
                //圆环中间样式
                label: {
                  show: true,
                  fontSize: '10',
                  color: 'black',
                  fontStyle: 'normal',
                  fontWeight: 'normal',
                  formatter: function (parms) {
                    let target;
                    _this.echartData.data.map((text, index) => {
                      if (parms.data.name == text.name) {
                        target = text.value;
                      }
                    });
                    let arr = ['{b|' + parms.name + '}\n{c| ' + target + '}'];
                    return arr.join();
                  },
                  textStyle: {
                    rich: {
                      c: {
                        //verticalAlign: "right",
                        fontSize: 12 * this.multiple,
                        color: '#595959',
                        fontWeight: 'bold',
                        padding: [-5 * this.multiple, 10, 0, 0]
                      },
                      b: {
                        fontSize: 8 * this.multiple,
                        color: '#333333',
                        padding: [5 * this.multiple, 0, 0, 0]
                      }
                    }
                  }
                }
              },
              labelLine: {
                show: false
              },

              //圆环颜色循环
              itemStyle: {
                normal: {
                  color: function (params) {
                    var colorList = [
                      '#FFD45F',
                      '#FF9B5F',
                      '#FF5F5F',
                      '#419AFF'
                    ];
                    return colorList[params.dataIndex % colorList.length];
                  }
                }
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
    this.setOption();
    window.addEventListener('resize', () => {
      this.myChart.resize();
    });
    // setTimeout(() => {
    erd().listenTo(document.getElementById('analysisPie'), () => {
      //监听到事件后执行的业务逻辑
      this.myChart.resize();
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
