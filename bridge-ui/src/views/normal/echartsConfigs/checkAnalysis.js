//legend 默认值
const defaultLegendConfigs = {
  top: 'middle',
  orient: 'vertical',
  icon: 'circle',
  itemWidth: 8,
  itemHeight: 8,
  textStyle: {
    color: '#595959',
    lineHeight: 20
  },
  itemGap: 23
};

//axisConfig 默认值
const defaultxAxisConfigs = {
  nameTextStyle: { color: '#595959' },
  axisTick: { inside: true, lineStyle: { color: '#BFBFBF' } },
  axisLine: { lineStyle: { color: '#BFBFBF' } },
  splitArea: { show: false }
};
const defaultyAxisConfigs = {
  splitLine: {
    lineStyle: { width: 1, type: 'dashed', color: '#dedede' }
  },
  axisTick: { show: false },
  axisLine: { show: false },
  splitArea: { show: false }
};

const echartConfigs0 = (data) => {
  function _reduceStringLength(str) {
    let arr = [];
    while (str) {
      arr.push(str.substr(0, 10));
      str = str.substr(10);
    }
    return arr.join('\n');
  }
  const leftOfLegend =
    window.innerWidth > 1440 ? ['63%', '78%', '55%'] : ['55%', '69%', '35%'];
  let legend;
  // 此处原本为'data.length <= 6'，但是后期修改样式，认为不分列显示效果好，使用把6改为60
  if (data.length <= 60) {
    legend = {
      left: leftOfLegend[0],
      type: 'scroll',
      pageButtonGap: 8,
      pageIconSize: 12,
      data: data.map((v) => v[0]),
      formatter: _reduceStringLength,

      ...defaultLegendConfigs
    };
  } else {
    legend = [
      {
        left: leftOfLegend[1],
        type: 'scroll',
        pageButtonGap: 8,
        pageIconSize: 12,
        data: Array.from(data)
          .splice(6, 6)
          .map((v) => v[0]),
        formatter: _reduceStringLength,
        ...defaultLegendConfigs
      },
      {
        left: leftOfLegend[2],
        type: 'scroll',
        pageButtonGap: 8,
        pageIconSize: 12,
        data: Array.from(data)
          .splice(0, 6)
          .map((v) => v[0]),
        formatter: _reduceStringLength,
        ...defaultLegendConfigs
      }
    ];
  }

  return {
    dataset: {
      source: [['病害名', '值'], ...data] //data here
    },
    tooltip: {
      trigger: 'item',
      confine: true,
      formatter: function ({ marker, data, percent }) {
        return `${data[0]}<br />${marker}&nbsp;${data[1]}&nbsp;&nbsp;&nbsp;(${percent}%)`;
      }
    },
    legend, //data here
    series: [
      {
        name: '病害类型统计',
        type: 'pie',
        radius: ['15%', window.innerWidth < 1440 ? '25%' : '65%'],
        center: ['25%', '50%'],
        roseType: 'area',
        label: {
          show: false
        },
        itemStyle: {
          // borderWidth: 1.5,
          // borderColor: "#141e30",
        }
      }
    ],
    color: [
      '#4d38ec',
      '#3873ec',
      '#38bbec',
      '#38ecd9',
      '#38ec88',
      '#c3ff5a',
      '#ffd45f',
      '#ff9b5f',
      '#ff5f5f',
      '#ff5fa7',
      '#f25fff',
      '#9f5fff'
    ]
  };
};

const echartConfigs1 = (source) => {
  return {
    dataset: {
      source //data here
    },
    color: ['#3873ec'],
    tooltip: {
      trigger: 'axis',
      confine: true,
      axisPointer: {
        type: 'shadow'
      }
    },
    grid: {
      left: '3%',
      right: '12%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: [
      {
        type: 'category',
        axisTick: {
          alignWithLabel: true
        },
        name: '\n\n构件',
        axisLabel: {
          color: '#595959',
          interval: 0,
          formatter: function (value) {
            // let a = '',
            //   i = '';
            // let charLenEachLine = window.innerWidth > 1440 ? 4 : 3;
            // value.split('').forEach((char, idx, thisArg) => {
            //   i += char;
            //   if (i.length == charLenEachLine || thisArg.length - 1 == idx)
            //     (a += i + '\n'), (i = '');
            // });
            // return a;
            let name = '';
            if (value.length > 6) {
              name = value.slice(0, 6) + '...';
            } else {
              name = value;
            }
            return name;
          }
        },
        ...defaultxAxisConfigs
      }
    ],
    yAxis: [
      {
        type: 'value',
        splitLine: {
          show: false
        },
        nameGap: 25,
        name: source[0][1] + '    ',
        axisLabel: {
          color: '#595959'
        },
        ...defaultyAxisConfigs
      }
    ],
    series: [
      {
        type: 'bar',
        barWidth: 20
      }
    ]
  };
};

const echartConfigs3 = (source, totoalBarNumber) => {
  console.log(source, totoalBarNumber);
  return {
    dataset: {
      source //data here
    },
    legend: {
      ...defaultLegendConfigs,
      orient: 'horizontal',
      top: '5%'
    },
    color: ['#3873ec', '#38bbec', '#38ec88', '#ffd45f', '#ff5f5f', '#f25fff'],
    tooltip: {
      trigger: 'axis',
      confine: true,
      axisPointer: {
        type: 'shadow'
      }
    },
    grid: {
      left: '3%',
      right: '12%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: [
      {
        type: 'category',
        axisTick: {
          alignWithLabel: true
        },
        name: '\n\n构件',
        axisLabel: {
          color: '#595959',
          formatter: function (value) {
            // let a = '',
            //   i = '';
            // let charLenEachLine = window.innerWidth > 1440 ? 4 : 3;
            // value.split('').forEach((char, idx, thisArg) => {
            //   i += char;
            //   if (i.length == charLenEachLine || thisArg.length - 1 == idx)
            //     (a += i + '\n'), (i = '');
            // });
            // return a;
            let name = '';
            if (value.length > 6) {
              name = value.slice(0, 6) + '...';
            } else {
              name = value;
            }
            return name;
          }
        },
        ...defaultxAxisConfigs
      }
    ],
    yAxis: [
      {
        type: 'value',
        splitLine: {
          show: false
        },
        nameGap: 25,
        axisLabel: {
          color: '#595959'
        },
        ...defaultyAxisConfigs
      }
    ],
    series: [
      { type: 'bar', stack: 'a', barWidth: 20 },
      { type: 'bar', stack: 'a', barWidth: 20 },
      { type: 'bar', stack: 'a', barWidth: 20 },
      { type: 'bar', stack: 'a', barWidth: 20 },
      { type: 'bar', stack: 'a', barWidth: 20 },
      { type: 'bar', stack: 'a', barWidth: 20 },
      { type: 'bar', stack: 'a', barWidth: 20 },
      { type: 'bar', stack: 'a', barWidth: 20 },
      { type: 'bar', stack: 'a', barWidth: 20 }
    ].splice(0, totoalBarNumber)
  };
};

//这是   echarts   dataset  source   的样例数据
// ["product", "2015", "2016", "2017", "2018", "2019"],
// ["Matcha Latte", 43.3, 85.8, 93.7, 66, 69],
// ["Milk Tea", 83.1, 73.4, 55.1, 66, 69],
// ["Cheese Cocoa", 86.4, 65.2, 82.5, 66, 69],
const echartConfigs4 = (source) => {
  return {
    color: ['#3873ec'],
    tooltip: {
      trigger: 'axis',
      confine: true,
      axisPointer: {
        type: 'shadow'
      }
    },
    legend: {
      ...defaultLegendConfigs,
      orient: 'horizontal',
      right: 0,
      top: 0
    },
    dataset: {
      source //data here
    },
    grid: {
      left: '3%',
      right: '8%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: [
      {
        type: 'category',
        axisTick: {
          alignWithLabel: true
        },
        name: '\n\n构件',
        axisLabel: {
          color: '#595959',
          formatter: function (value) {
            //4个字符自动换行
            // let a = '',
            //   i = '';
            // let charLenEachLine = window.innerWidth > 1440 ? 4 : 3;
            // value.split('').forEach((char, idx, thisArg) => {
            //   i += char;
            //   if (i.length == charLenEachLine || thisArg.length - 1 == idx)
            //     (a += i + '\n'), (i = '');
            // });
            // return a;
            let name = '';
            if (value.length > 8) {
              name = value.slice(0, 8) + '...';
            } else {
              name = value;
            }
            return name;
          },
          fontSize: window.innerWidth > 1440 ? 12 : 10
        },
        ...defaultxAxisConfigs
      }
    ],
    yAxis: [
      {
        type: 'value',
        splitLine: {
          show: false
        },
        nameGap: 25,
        name: '病害数    ',
        axisLabel: {
          color: '#595959'
        },
        ...defaultyAxisConfigs
      }
    ],
    series: [
      {
        type: 'bar',
        barWidth: 12,
        barGap: 0.5
      },
      {
        type: 'bar',
        barWidth: 12
      },
      {
        type: 'bar',
        barWidth: 12
      },
      {
        type: 'bar',
        barWidth: 12
      },
      {
        type: 'bar',
        barWidth: 12
      }
    ],
    color: ['#ff5fa7', '#ffd45f', '#c3ff5a', '#38ec88', '#38bbec']
  };
};

const checkAnalysis = [
  echartConfigs0, //玫瑰图
  echartConfigs1, //蓝柱图
  echartConfigs1, //蓝柱图
  echartConfigs3, //堆叠柱状图
  echartConfigs4 //彩虹柱图
];
export default checkAnalysis;
