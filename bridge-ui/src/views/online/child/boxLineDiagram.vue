<template>
  <div class="boxLineDiagram">
    <div class="tool">
      <span>历史值时间段：</span>
      <el-date-picker
        class="time"
        popper-class="timeSelect"
        v-model="timeValue"
        type="datetimerange"
        :picker-options="pickerOptions"
        range-separator="至"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
        value-format="yyyy-MM-dd HH:mm:ss"
        :default-time="['00:00:00', '23:59:59']"
        align="center"
        :clearable="false"
        @blur="pickerBlur"
      >
      </el-date-picker>
      <el-button type="primary" @click="boxAnalyse">查询</el-button>
    </div>
    <div v-if="sensorList.length == 0 && !noData" class="noData">
      请先选择测点
    </div>
    <div v-else-if="sensorList.length > 0 && noData" class="noData">
      暂无数据
    </div>
    <!-- <dataBox v-else ref="boxEchart" :echartData="echartData"></dataBox> -->
  </div>
</template>

<script>
// import { boxAnalyse } from '@/api/online/warnDetails';
// import dataBox from './components/dataBox';
export default {
  name: 'boxLineDiagram-dzl',
  // components: { dataBox },
  props: {
    sensorList: {
      type: Array,
      default: () => []
    }
  },
  watch: {
    sensorList() {
      this.boxAnalyse();
    }
  },
  data() {
    const _this = this;
    return {
      minDate: new Date(),
      pickerOptions: {
        shortcuts: [
          {
            text: '最近一周',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 6);
              picker.$emit('pick', [start, end]);
            }
          },
          {
            text: '最近一个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 29);
              picker.$emit('pick', [start, end]);
            }
          },
          {
            text: '最近三个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 89);
              picker.$emit('pick', [start, end]);
            }
          }
        ],
        onPick: ({ minDate }) => {
          _this.minDate = minDate;
        },
        disabledDate(time) {
          let range = 90 * 24 * 3600 * 1000;
          let newTime = time.getTime();
          let minTime = _this.minDate.getTime();
          let nowTime = new Date(
            _this.$utils.Dateformat1(new Date()) + ' 23:59:59'
          );
          let endTime = new Date(
            _this.$utils.Dateformat1(new Date(minTime + range)) + ' 23:59:59'
          );
          let beginTime = new Date(
            _this.$utils.Dateformat1(new Date(minTime - range)) + ' 00:00:00'
          );
          return (
            newTime > nowTime.getTime() ||
            newTime > endTime ||
            newTime < beginTime
          );
        }
      },
      timeValue: [
        this.$utils.Dateformat1(new Date()) + ' 00:00:00',
        this.$utils.Dateformat1(new Date()) + ' 23:59:59'
      ],
      echartData: {
        xData: [''],
        data: [],
        warnData: []
      },
      noData: false
    };
  },
  methods: {
    //箱型图分析
    async boxAnalyse() {
      if (this.sensorList.length == 0) {
        this.noData = false;
        return;
      }
      for (let i = 0; i < this.sensorList.length; i++) {
        if (this.sensorList[i].companyId == 2) {
          this.$message({
            message: '仅基康设备展示箱线图！',
            type: 'warning',
            showClose: true,
            duration: 2000
          });
          this.noData = true;
          return;
        }
        if (this.sensorList[i].sensorTypeId == 7) {
          this.$message({
            message: '称重传感器不展示箱线图！',
            type: 'warning',
            showClose: true,
            duration: 2000
          });
          this.noData = true;
          return;
        }
      }
      if (this.noData) return;
      let params = {
        sensorList: this.sensorList,
        startTime: this.timeValue[0],
        endTime: this.timeValue[1]
      };
      let { code, data } = await boxAnalyse(params);
      if (code == '0000') {
        let xData = [];
        let dataList = [];
        let warnData = [];
        data.map((item, index) => {
          let warnList = [index, item.warningList[0], '异常'];
          xData.push(item.name);
          dataList.push(item.calculate);
          warnData.push(warnList);
        });
        this.echartData.xData = xData;
        this.echartData.data = dataList;
        this.echartData.warnData = warnData;
        this.$nextTick(() => {
          this.noData = false;
          if (xData.length > 0) {
            // this.$refs.boxEchart.setOption();
          }
        });
      }
    },
    //计算最大值、最小值、第一四分位数、中位数、第三四分位数
    compute(arry) {
      // let arry = list.map((o) => o.actual).sort();
      var q1 = this.quantile(arry, 0.25); //第一四分位数
      var q3 = this.quantile(arry, 0.75); //第三四分位数
      var warnList = [];
      var midVal = 0; //中位数
      if (arry.length % 2 == 0) {
        midVal = (arry[arry.length / 2] + arry[arry.length / 2 + 1]) / 2;
      }
      if (arry.length % 2 != 0) {
        midVal = arry[(arry.length + 1) / 2];
      }
      let bound = 1.5 * (q3 - q1);
      var min = arry[0];
      var max = arry[arry.length - 1];
      var minVal = Math.max(min, q1 - bound);
      var maxVal = Math.min(max, q3 + bound);
      arry.map((val) => {
        if (val < minVal || val > maxVal) {
          let outlier = [0, val, '异常'];
          warnList.push(outlier);
        }
      });
      return { maxVal, minVal, midVal, q1, q3, warnList };
    },
    quantile(ascArr, p) {
      var H = (ascArr.length - 1) * p + 1;
      var h = Math.floor(H);
      var v = +ascArr[h - 1];
      var e = H - h;
      return e ? v + e * (ascArr[h] - v) : v;
    },
    pickerBlur() {
      // console.log(1111, val);
      this.minDate = new Date(this.timeValue[0]);
    }
  },
  mounted() {
    this.boxAnalyse();
  }
};
</script>
<style lang="scss" scoped>
.boxLineDiagram {
  height: 100%;
  width: 100%;
  .tool {
    display: flex;
    align-items: center;
    .time {
      margin: 0 20px;
      display: flex;
      align-items: center;
      justify-content: space-between;
      /deep/ .el-range-separator {
        display: flex;
        align-items: center;
        justify-content: center;
      }
    }
  }
  .noData {
    height: 100%;
    width: 100%;
    font-size: 32px;
    color: #333;
    display: flex;
    align-items: center;
    justify-content: center;
  }
}
</style>
