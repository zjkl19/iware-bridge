import echarts from 'echarts';

export default [
  function 饼图_主要病害占比(data) {
    let _data = data;
    //用于legend显示数字
    const valueMap = new Map(data.map((item) => [item.name, item.value]));
    //用于legend显示数字
    function __LegendFomatter(item0) {
      return valueMap.get(item0);
    }

    function __LegendFomatter2(item) {
      let arr = [];
      while (item) {
        arr.push(item.substr(0, 11));
        item = item.substr(11);
      }
      return arr.join('\n');
    }
    var sumC = 0;
    for (let index = 0; index < data.length; index++) {
      sumC += data[index].value;
    }
    return {
      title: [
        {
          text: sumC,
          textStyle: {
            color: '#595959',
            fontSize: 28
          },

          textAlign: 'center',
          x: '27%',
          y: '42%',
          subtext: '病害数',
          subtextStyle: {
            color: '#595959',
            fontSize: 14
          }
        }
      ],

      tooltip: {
        trigger: 'item',
        formatter(v) {
          return `主要病害统计<br/>${v.marker}${v.data.name}<br/>数量：${v.data.value}<br/>占比：${v.percent}%`;
        }
      },
      // legend: [
      //     { //显示数字的legend
      //         top: "middle",
      //         icon: "none",
      //         orient: "vertical",
      //         right: "10%",
      //         itemGap: 25,
      //         textStyle: {
      //             color: "#d3dce5",
      //             fontSize: 14
      //         },
      //         formatter: __LegendFomatter
      //     },
      //     {//显示名字的legend
      //         top: "middle",
      //         icon: `path://
      //         M100,200
      //         C100,66.6 300,66.6 300,200
      //         S100,333.3 100,200z

      //         M250,200
      //         C250,133.3 150,133.3 150,200
      //         S250,266.6 250,200
      //         `,
      //         itemWidth: 8,
      //         itemHeight: 8,

      //         itemGap: 25,
      //         orient: "vertical",
      //         left: "50%",
      //         textStyle: {
      //             color: "#7f8792",
      //             fontSize: 14
      //         },
      //         formatter: __LegendFomatter2
      //     }
      // ],
      legend: {
        selectedMode: true, //取消图例上的点击事件
        left: '50%',
        align: 'left',
        top: 'middle',
        formatter: function (data) {
          let value;
          let name;
          for (let item of _data) {
            if (item.name == data) {
              name = item.name;
              value = item.value;
            }
          }
          // let value = data.replace(/[^0-9]/ig,"");
          // let name=data.replace(/[0-9]/ig,"");
          let index = '';
          //
          let arry = [];
          if ((index + 1) % 2 == 1) {
            arry = ['{b|' + name + '}{a|' + value + '}{d|}'];
          } else {
            arry = ['{b|' + name + '}{c|' + value + '}'];
          }
          return arry.join();
        },
        textStyle: {
          rich: {
            a: {
              fontSize: 14,
              width: 30,
              align: 'right'
            },
            b: {
              fontSize: 14,
              width: 150
            },
            c: {
              fontSize: 14,
              width: 30,
              align: 'right'
            },
            d: {
              width: 28
            }
          }
        },
        height: 250,
        icon: `path://
                              M100,200
                              C100,66.6 300,66.6 300,200
                              S100,333.3 100,200z
      
                              M250,200
                              C250,133.3 150,133.3 150,200
                              S250,266.6 250,200
                              `, //图例圆形
        orient: 'vertical' //图例显示
      },
      series: [
        {
          name: '主要病害占比',
          type: 'pie',
          radius: ['50%', '58%'],
          center: ['28%', '50%'],
          avoidLabelOverlap: false,
          label: {
            normal: {
              show: false
            }
          },
          labelLine: {
            normal: {
              show: false
            }
          },
          data: data,
          color: ['#328ff1', '#32eaf1', '#bb8d35', '#d13964', '#bd32f1']

          // itemStyle: {
          //     borderWidth: "1",
          //     borderColor: "#141e30"
          // }
        }
      ]
    };
  },
  //
  function 条形_日常巡查趋势(data) {
    //auto generate days
    const today = Date.now();
    const days = [...Array(7).keys()].reverse().map((i) => {
      const thatDay = new Date(today - i * 60 * 60 * 24 * 1000);
      const month = thatDay.getMonth() + 1;
      const day = thatDay.getDate();
      return month + '/' + day;
    });

    return {
      tooltip: {
        trigger: 'axis',
        formatter: '{b}<br/> {c} %'
      },
      xAxis: {
        type: 'category',
        data: days,
        boundaryGap: false,
        axisLabel: {
          margin: 12,
          color: '#626771'
        }
      },
      yAxis: {
        type: 'value',
        axisLine: {
          show: false
        },
        axisLabel: {
          margin: 20,
          color: '#626771'
        },
        splitLine: {
          lineStyle: {
            color: 'rgba(66,74,88,.2)'
          }
        }
      },
      grid: {
        left: 2,
        right: 20,
        top: 8,
        bottom: 5,
        containLabel: true
      },
      series: [
        {
          data, //data here
          type: 'line',
          lineStyle: {
            width: 2
          },
          areaStyle: {
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
              {
                offset: 0,
                color: 'rgba(53,151,253,.25)'
              },
              {
                offset: 1,
                color: 'transparent'
              }
            ])
          },
          color: '#3597fd',
          symbol: 'circle',
          symbolSize: 6
        }
      ]
    };
  }
];
