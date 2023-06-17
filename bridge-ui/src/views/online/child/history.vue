<template>
  <div class="history">
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
      >
      </el-date-picker>
      <el-button type="primary" @click="getHistory">查询</el-button>
    </div>
    <div v-if="sensorList.length == 0" class="historyBox noData">
      请先选择测点
    </div>
    <div v-else class="historyBox">
      <div
        v-for="(item, index) in dataList"
        :key="item.id"
        class="historyItem"
        :style="{ height: dataList.length > 1 ? '50%' : '100%' }"
      >
        <div class="codeName">{{ item.codeName }}</div>
        <div v-if="item.noData" class="itemBox">
          <div v-if="item.echartType == 1" class="tip">
            <span>最大值：{{ item.maxValue }}</span>
            <span>最小值：{{ item.minValue }}</span>
            <span>平均值：{{ item.avgValue }}</span>
            <span>方差：{{ item.varValue }}</span>
          </div>
          <div class="historyEchart">
            <div
              v-if="item.echartData.xData.length > 0"
              style="width: 100%; height: 100%"
            >
              <dataHistory
                v-if="
                  (item.sensorTypeId != 7 && item.companyId != 2) ||
                  (item.echartType == 2 &&
                    (item.sensorTypeId == 1 || item.sensorTypeId == 3))
                "
                :ref="'hisEchart' + item.id"
                :echartData="item.echartData"
              ></dataHistory>
              <envelope
                v-else-if="
                  item.sensorTypeId != 7 &&
                  item.companyId == 2 &&
                  item.echartType == 1
                "
                :ref="'hisEchart' + item.id"
                :echartData="item.echartData"
              ></envelope>
              <dataScatter
                v-else
                :ref="'hisEchart' + item.id"
                :echartData="item.echartData"
              ></dataScatter>
            </div>
            <span v-else>暂无数据</span>
          </div>
          <el-select
            v-if="item.haveFrequency"
            class="select"
            v-model="item.echartType"
            placeholder="请选择"
            @change="typeChange(item, index)"
          >
            <el-option
              v-for="item in echartTypeList"
              :key="item.id"
              :label="item.label"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </div>
        <div v-else class="noData">暂无数据</div>
      </div>
    </div>
  </div>
</template>

<script>
import { getHistory, getFrequency } from '@/api/online/warnDetails';
import dataHistory from './components/dataHistory';
import envelope from './components/envelope';
import dataScatter from './components/dataScatter';
export default {
  name: 'history-dzl',
  components: { dataHistory, dataScatter, envelope },
  props: {
    sensorList: {
      type: Array,
      default: () => []
    }
  },
  watch: {
    sensorList() {
      this.getHistory();
    }
  },
  data() {
    return {
      pickerOptions: {
        shortcuts: [
          {
            text: '最近一周',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', [start, end]);
            }
          },
          {
            text: '最近一个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit('pick', [start, end]);
            }
          },
          {
            text: '最近三个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit('pick', [start, end]);
            }
          }
        ],
        disabledDate(time) {
          return time.getTime() > Date.now();
        }
      },
      echartTypeList: [
        { id: 1, label: '时程图' },
        { id: 2, label: '频谱图' }
      ],
      timeValue: [
        this.$utils.Dateformat1(new Date()) + ' 00:00:00',
        this.$utils.Dateformat1(new Date()) + ' 23:59:59'
      ],
      dataList: []
    };
  },
  methods: {
    //获取历史值
    async getHistory() {
      if (this.sensorList.length == 0) {
        return;
      }
      let params = {
        sensorList: this.sensorList,
        startTime: this.timeValue[0],
        endTime: this.timeValue[1]
      };

      let { code, data } = await getHistory(params);
      if (code == '0000') {
        let arry = [];
        data.map((item, index) => {
          let xData = [];
          let yData = [];
          let maxValueData = [];
          let minValueData = [];
          item.list.map((child) => {
            xData.push(child.samplingTime);
            let obj = {
              samplingTime: child.samplingTime,
              value: '',
              sensorCoding: child.sensorCoding,
              singleShaftNuber: child.singleShaftNuber,
              modelName: child.modelName,
              licensePlate:
                child.licensePlate !== null ? child.licensePlate.trim() : ''
            };
            if (item.sensorTypeId == 7) {
              obj.value = [child.samplingTime, child.value];
              yData.push(obj);
            } else {
              obj.value = child.value;
              yData.push(child.value);
              if (child.maxValue !== null && child.minValue !== null) {
                maxValueData.push(child.maxValue);
                // maxValueData.push(
                //   this.getValue(child.maxValue, child.minValue)
                // );
                minValueData.push(child.minValue);
              }
            }
          });
          let obj = {
            id: item.id,
            codeName:
              this.sensorList[index].name +
              '(' +
              this.sensorList[index].sensorCoding +
              ')',
            companyId: this.sensorList[index].companyId,
            maxValue: item.max,
            minValue: item.min,
            avgValue: item.avg,
            varValue: item.variance,
            sensorTypeId: item.sensorTypeId,
            // haveFrequency: [1, 3].includes(item.sensorTypeId) ? true : false,
            echartType: 1,
            echartData: {
              id: item.id,
              name: '平均值',
              maxValue: item.max,
              minValue: item.min,
              unit: item.unit,
              xData,
              data: yData,
              maxValueData,
              minValueData
            },
            noData: yData.length > 0 ? true : false
          };
          arry.push(obj);
        });
        this.dataList = arry;
        this.$nextTick(() => {
          arry.map((item) => {
            if (item.echartData.xData.length > 0) {
              let refName = 'hisEchart' + item.id;
              this.$refs[refName][0].setOption();
            }
          });
        });
      }
    },
    //获取单个历史值
    async getHistorySingle(item, index) {
      if (this.sensorList.length == 0) {
        return;
      }
      let params = {
        sensorList: [item],
        startTime: this.timeValue[0],
        endTime: this.timeValue[1]
      };
      let { code, data } = await getHistory(params);
      if (code == '0000') {
        let xData = [];
        let yData = [];
        let maxValueData = [];
        let minValueData = [];
        data[0].list.map((item) => {
          xData.push(item.samplingTime);
          let obj = {
            samplingTime: item.samplingTime,
            value: '',
            sensorCoding: item.sensorCoding,
            singleShaftNuber: item.singleShaftNuber,
            modelName: item.modelName,
            licensePlate:
              item.licensePlate !== null ? item.licensePlate.trim() : ''
          };
          if (data[0].sensorTypeId == 7) {
            obj.value = [item.samplingTime, item.value];
            yData.push(obj);
          } else {
            obj.value = item.value;
            yData.push(item.value);
            if (item.maxValue !== null && item.minValue !== null) {
              maxValueData.push(item.maxValue);
              // maxValueData.push(this.getValue(item.maxValue, item.minValue));
              minValueData.push(item.minValue);
            }
          }
        });
        this.dataList[index].id = data[0].id;
        this.dataList[index].maxValue = data[0].max;
        this.dataList[index].minValue = data[0].min;
        this.dataList[index].avgValue = data[0].avg;
        this.dataList[index].varValue = data[0].variance;
        // this.dataList[index].haveFrequency = [1, 3].includes(
        //   data[0].sensorTypeId
        // )
        //   ? true
        //   : false;
        this.dataList[index].echartData = {
          id: index,
          name: '平均值',
          maxValue: data[0].max,
          minValue: data[0].min,
          unit: data[0].unit,
          xData,
          data: yData,
          maxValueData,
          minValueData
        };
        this.dataList[index].noData = yData.length > 0 ? true : false;
        this.$nextTick(() => {
          if (xData.length > 0) {
            let refName = 'hisEchart' + data[0].id;
            this.$refs[refName][0].setOption();
          }
        });
      }
    },
    //获取频谱数据
    async getFrequency(item, index) {
      if (this.sensorList.length == 0) {
        return;
      }
      let params = {
        sensorCoding: item.sensorCoding,
        sensorDetailsId: item.sensorDetailsId,
        startTime: this.timeValue[0],
        endTime: this.timeValue[1]
      };
      let { code, data } = await getFrequency(params);
      if (code == '0000') {
        let xData = [];
        let yData = [];
        data.map((child, i) => {
          xData.push(child.calculateTime);
          if (
            i - 1 >= 0 &&
            i + 1 <= data.length - 1 &&
            data[i - 1].frequency == null &&
            data[i + 1].frequency == null &&
            child.frequency != null
          ) {
            let obj = {
              value: child.frequency,
              symbol: 'circle',
              symbolSize: 8
            };
            yData.push(obj);
          } else {
            yData.push(child.frequency);
          }
        });
        let echartData = {
          id: item.id,
          name: '频率值',
          maxValue: Math.max(...yData),
          minValue: Math.min(...yData),
          unit: 'Hz',
          xData,
          data: yData,
          minValueData: [],
          maxValueData: []
        };
        this.dataList[index].echartData = echartData;
        this.$nextTick(() => {
          if (xData.length > 0) {
            let refName = 'hisEchart' + echartData.id;
            this.$refs[refName][0].setOption();
          }
        });
      }
    },
    getValue(num1, num2) {
      if (num1 >= 0 && num2 >= 0) {
        return Number((num1 - num2).toFixed(6));
      }
      if (num1 >= 0 && num2 < 0) {
        return Number((num1 - num2).toFixed(6));
      }
      if (num1 < 0 && num2 < 0) {
        return num2 - num1;
      }
    },
    //切换图
    typeChange(item, index) {
      this.sensorList.map((child) => {
        if (child.id == item.id) {
          if (item.echartType == 1) {
            this.getHistorySingle(child, index);
          } else {
            this.getFrequency(child, index);
          }
        }
      });
    }
  },
  mounted() {
    this.getHistory();
  }
};
</script>
<style lang="scss" scoped>
.history {
  height: 100%;
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
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
  .historyBox {
    // height: 65.742vh;
    height: 67.742vh;
    width: 100%;
    overflow: auto;
    .historyItem {
      position: relative;
      padding: 10px 0 0;
      border-bottom: 1px solid #e5e5e5;
      .codeName {
        position: absolute;
        left: 0;
        top: 0;
        font-size: 16px;
        color: #000;
      }
      .itemBox {
        height: 100%;
        width: 100%;
        display: flex;
        flex-direction: column;
      }
      .tip {
        padding: 0 230px;
        display: flex;
        align-items: center;
        justify-content: space-evenly;
      }
      .historyEchart {
        flex: 1;
        width: 100%;
        display: flex;
        align-items: center;
        justify-content: center;
        span {
          color: #333;
          font-size: 32px;
        }
      }
      .select {
        position: absolute;
        right: 40px;
        top: 10px;
        width: 100px;
      }
    }
    &::-webkit-scrollbar {
      width: 8px;
    }
    &::-webkit-scrollbar-thumb {
      border-radius: 8px;
      background: #c4c4c4;
    }
  }
}
.noData {
  width: 100%;
  height: 100%;
  color: #333;
  font-size: 32px;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
<style lang="scss">
//时间下拉框
.timeSelect {
  .el-button--text {
    display: none;
  }
}
</style>
