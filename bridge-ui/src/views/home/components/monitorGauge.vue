<template>
  <div id="monitorEchart" style="width: 100%; height: 100%"></div>
</template>

<script>
import echarts from 'echarts';
require('echarts/theme/macarons'); // echarts theme
import $ from 'jquery';
import erd from 'element-resize-detector';
export default {
  name: 'monitorEchart',
  props: {
    echartData: {
      type: Object,
      default: () => {}
    }
  },
  data() {
    return {
      myChart: '',
      multiple: window.innerHeight / 70 / 7,
      colorList: [
        [0, '#38b5b5'],
        [0.1, 'transparent'],
        [0.2, '#38b5b5'],
        [0.3, 'transparent'],
        [0.4, '#38b5b5'],
        [0.5, 'transparent'],
        [0.6, '#38b5b5'],
        [0.7, 'transparent'],
        [0.8, '#38b5b5'],
        [0.9, 'transparent'],
        [1, '#38b5b5']
      ]
    };
  },
  methods: {
    setOption() {
      let _this = this;
      var img = new Image();
      img.src = require('@/assets/images/onlineFrame_icon_1.png');
      this.myChart = echarts.init(this.$el, 'macarons');
      this.myChart.clear();
      this.myChart.setOption(
        {
          backgroundColor: 'transparent',
          title: {
            show: true,
            text:
              '{text|今日处警率}\n{val|' +
              this.echartData.processingRate +
              '%}',
            subtext: '{left|0}{right|100}',
            left: 'center',
            top: '48%',
            textStyle: {
              rich: {
                text: {
                  color: 'rgba(255,255,255,0.85)',
                  fontSize: 5 * this.multiple,
                  align: 'center',
                  padding: [6, 0]
                },
                val: {
                  color: '#fff',
                  fontSize: 10 * this.multiple,
                  fontWeight: 'bold',
                  align: 'center'
                }
              }
            },
            subtextStyle: {
              color: 'rgba(255,255,255,0.85)',
              rich: {
                left: {
                  // align: "left",
                  fontSize: 5 * this.multiple,
                  padding: [0, 30 * this.multiple, -5 * this.multiple, 0]
                },
                right: {
                  // align: "right",
                  fontSize: 5 * this.multiple,
                  padding: [0, 0, -5 * this.multiple, 30 * this.multiple]
                }
              }
            }
          },
          angleAxis: {
            max: 200,
            // clockwise: true, // 逆时针
            // 隐藏刻度线
            show: false,
            startAngle: 180
          },
          radiusAxis: {
            type: 'category',
            show: true,
            axisLabel: {
              show: false
            },
            axisLine: {
              show: false
            },
            axisTick: {
              show: false
            }
          },
          polar: [
            {
              center: ['50%', '70%'], //中心点位置
              radius: '180%' //图形大小
            }
          ],
          series: [
            {
              type: 'bar',
              data: [100],
              coordinateSystem: 'polar',
              roundCap: true,
              barWidth: 6,
              itemStyle: {
                normal: {
                  opacity: 1,
                  color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [
                    {
                      offset: 0,
                      color: '#fdbb2d'
                    },
                    {
                      offset: 0.5,
                      color: '#43d9d7'
                    },
                    {
                      offset: 1,
                      color: '#38b5b5'
                    }
                  ])
                }
              }
            },
            //刻度样式
            {
              type: 'gauge',
              radius: '78%',
              center: ['50%', '70%'],
              splitNumber: 4,
              max: 100,
              startAngle: 180,
              endAngle: 0,
              detail: {
                show: false
              },
              axisLine: {
                // 坐标轴线
                lineStyle: {
                  opacity: 0 //刻度背景宽度
                }
              },
              data: [
                {
                  name: '',
                  value: 100
                }
              ],
              splitLine: {
                show: false
              },
              axisTick: {
                show: true,
                lineStyle: {
                  color: '#062843',
                  width: 1
                },
                length: 6,
                splitNumber: 20
              },
              axisLabel: {
                show: false
              },
              pointer: {
                show: false
              }
            },
            {
              type: 'pie',
              radius: ['87%', '87%'],
              center: ['50%', '70%'], //中心点位置
              hoverAnimation: false,
              startAngle: 180,
              data: [
                {
                  name: '',
                  value: this.echartData.processingRate,
                  label: {
                    show: false
                  },
                  labelLine: {
                    show: false
                  },
                  itemStyle: {
                    color: 'rgba(0,0,0,0)'
                  }
                },
                {
                  //画中间的图标
                  name: '',
                  value: 0,
                  label: {
                    position: 'inside',
                    backgroundColor: '#fff',
                    borderRadius: 7,
                    padding: 7,
                    borderWidth: 0,
                    borderColor: 'blue'
                  }
                },
                {
                  name: '',
                  value: 200 - this.echartData.processingRate,
                  label: {
                    show: false
                  },
                  labelLine: {
                    show: false
                  },
                  itemStyle: {
                    color: 'rgba(255,255,255,0)'
                  }
                }
              ]
            }
            //水滴样式
            // {
            //   name: 'process',
            //   type: 'gauge',
            //   radius: '62%',
            //   center: ['50%', '70%'],
            //   max: 100,
            //   startAngle: 180,
            //   endAngle: 0,
            //   splitNumber: 12,
            //   data: [
            //     {
            //       value: 50
            //     }
            //   ],
            //   detail: {
            //     show: false
            //   },
            //   axisLine: {
            //     // 坐标轴线
            //     lineStyle: {
            //       color: [
            //         [0, '#062843'],
            //         [80 / 100, '#38b5b5'],
            //         [1, '#062843']
            //       ],
            //       width: 8
            //     }
            //   },
            //   splitLine: {
            //     length: 8,
            //     lineStyle: {
            //       color: '#062843',
            //       width: 4
            //     }
            //   },
            //   axisTick: {
            //     show: false
            //   },
            //   axisLabel: {
            //     show: false
            //   },
            //   pointer: {
            //     show: false
            //   }
            // }
          ]
        },
        false
      );
      // setTimeout(() => {
      erd().listenTo(document.getElementById('monitorEchart'), () => {
        let width = $('#monitorEchart').width() * 0.50485908;
        let height = $('#monitorEchart').height() * 0.30994151;
        $('#monitorEchart').css(
          'background-size',
          width + 'px ' + height + 'px'
        );
        //监听到事件后执行的业务逻辑
        _this.myChart.resize();
      });
      // }, 5000);
    }
  },
  mounted() {
    // this.setOption();
    window.addEventListener('resize', () => {
      this.multiple = window.innerHeight / 70 / 7;
      let width = $('#monitorEchart').width() * 0.50485908;
      let height = $('#monitorEchart').height() * 0.30994151;
      $('#monitorEchart').css('background-size', width + 'px ' + height + 'px');
      // this.myChart.resize();
      this.setOption();
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
<style lang="scss" scoped>
#monitorEchart {
  background: url('~@/assets/images/home/gaugeImg.png') no-repeat;
  background-size: 104px 4.82vh;
  background-position: 50% 57%;
}
</style>
