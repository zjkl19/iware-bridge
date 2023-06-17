<template>
  <div id="monitorEchart2" style="width: 100%; height: 100%"></div>
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
          backgroundColor: 'transparent',
          title: {
            show: true,
            text: '{text|监测评分}\n{val|92.00}',
            subtext: '{left|严重}{right|良好}',
            left: '31%',
            top: '42%',
            textStyle: {
              rich: {
                text: {
                  color: '#595959',
                  fontSize: 5 * this.multiple,
                  align: 'center',
                  padding: [4 * this.multiple, 0]
                },
                val: {
                  color: '#333333',
                  fontSize: 10 * this.multiple,
                  fontWeight: 'bold',
                  align: 'center'
                }
              }
            },
            subtextStyle: {
              color: 'rgba(0,0,0,0.85)',
              rich: {
                left: {
                  // align: "left",
                  fontSize: 6 * this.multiple,
                  padding: [-3 * this.multiple, -28 * this.multiple]
                },
                right: {
                  // align: "right",
                  fontSize: 6 * this.multiple,
                  padding: [-3 * this.multiple, 88 * this.multiple]
                }
              }
            }
          },
          angleAxis: {
            max: 200,
            clockwise: true, // 逆时针
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
              center: ['47%', '70%'], //中心点位置
              radius: '200%' //图形大小
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
                      color: '#FD5F2D'
                    },
                    {
                      offset: 0.5,
                      color: '#EFC739'
                    },
                    {
                      offset: 1,
                      color: '#43D9D7'
                    }
                  ])
                }
              }
            },
            // {
            //   name: "",
            //   type: "gauge",
            //   radius: "90%",
            //   center: ["50%", "60%"],
            //   startAngle: 180,
            //   endAngle: 0,
            //   min: 0,
            //   max: 100,
            //   title: {
            //     show: false
            //   },
            //   detail: {
            //     show: false
            //   },
            //   axisLine: {
            //     show: true,
            //     lineStyle: {
            //       width: 6,
            //       color: [
            //         [
            //           1,
            //           new echarts.graphic.LinearGradient(0, 0, 1, 0, [
            //             {
            //               offset: 0,
            //               color: "#fdbb2d"
            //             },
            //             {
            //               offset: 0.5,
            //               color: "#43d9d7"
            //             },
            //             {
            //               offset: 1,
            //               color: "#38b5b5"
            //             }
            //           ])
            //         ]
            //       ]
            //     }
            //   },
            //   axisTick: {
            //     show: false
            //   },
            //   splitLine: {
            //     show: false
            //   },
            //   axisLabel: {
            //     show: false
            //   },
            //   pointer: {
            //     show: false
            //   },
            //   data: [
            //     {
            //       value: 100,
            //       name: ""
            //     }
            //   ]
            // },
            //刻度样式
            {
              type: 'gauge',
              radius: '85%',
              center: ['47%', '70%'],
              splitNumber: 3,
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
              radius: ['85%', '75%'],
              hoverAnimation: false,
              startAngle: -90,
              data: [
                {
                  name: '',
                  value: 98.86 / 100,
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
                    borderWidth: 1,
                    borderColor: '#43D9D7'
                  }
                },
                {
                  name: '',
                  value: 1.32 - 98.86 / 100,
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
          ]
        },
        false
      );
      window.addEventListener('resize', () => {
        let width = $('#monitorEchart2').width() * 0.52;
        let height = $('#monitorEchart2').height() * 0.30994151;
        $('#monitorEchart2').css(
          'background-size',
          width + 'px ' + height + 'px'
        );
        this.myChart.resize();
      });
      // setTimeout(() => {
      erd().listenTo(document.getElementById('monitorEchart2'), () => {
        let width = $('#monitorEchart2').width() * 0.52;
        let height = $('#monitorEchart2').height() * 0.30994151;
        $('#monitorEchart2').css(
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
<style lang="scss" scoped>
#monitorEchart {
  background: url('~@/assets/images/home/gaugeImg.png') no-repeat;
  background-size: 104px 4.82vh;
  background-position: 44% 57%;
}
</style>
