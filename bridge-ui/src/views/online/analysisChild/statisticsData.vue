<template>
  <div class="statisticsData">
    <div class="statisticsDataBox">
      <div
        class="statisticsBox statisticsFirst animate__animated animate__fadeIn"
      >
        <el-main
          v-loading="loading1"
          element-loading-background="rgba(255, 255,255, 1)"
          element-loading-text="加载中..."
          class="boxShadow"
        >
          <div class="ecTop">
            <span class="title">车流量时程</span>
            <!-- <div class="selItem">
              <span :class="{ activeItem: index1 == 1 }" @click="itemChange1(1)"
                >时</span
              ><span
                :class="{ activeItem: index1 == 2 }"
                @click="itemChange1(2)"
                >日</span
              >
            </div> -->
          </div>
          <div v-if="lineBarData1.xData.length == 0" class="ecBtm">
            暂无数据
          </div>
          <div v-else class="ecBtm">
            <statisticsBarLine
              ref="statisticsEchart1"
              :echartData="lineBarData1"
            ></statisticsBarLine>
          </div>
        </el-main>
        <el-main
          v-loading="loading2"
          element-loading-background="rgba(255, 255,255, 1)"
          element-loading-text="加载中..."
          class="boxShadow"
        >
          <div class="ecTop">
            <span class="title">车型、车速分布</span>
          </div>
          <div class="ecBtm">
            <statisticsPie
              ref="statisticsEchart2"
              class="pieItem"
              :echartData="pieData1"
            ></statisticsPie>
            <statisticsPie
              ref="statisticsEchart3"
              class="pieItem"
              :echartData="pieData2"
            ></statisticsPie>
          </div>
        </el-main>
      </div>
      <div
        class="statisticsBox statisticsFirst animate__animated animate__fadeIn"
      >
        <el-main
          v-loading="loading3"
          element-loading-background="rgba(255, 255,255, 1)"
          element-loading-text="加载中..."
          class="boxShadow"
        >
          <div class="ecTop">
            <span class="title">最大车重</span>
            <!-- <div class="selItem">
              <span :class="{ activeItem: index2 == 1 }" @click="itemChange2(1)"
                >时</span
              ><span
                :class="{ activeItem: index2 == 2 }"
                @click="itemChange2(2)"
                >日</span
              >
            </div> -->
          </div>
          <div v-if="lineData.date.length == 0" class="ecBtm">暂无数据</div>
          <div v-else class="ecBtm">
            <statisticsLine
              ref="statisticsEchart4"
              :echartData="lineData"
            ></statisticsLine>
          </div>
        </el-main>
        <el-main
          v-loading="loading4"
          element-loading-background="rgba(255, 255,255, 1)"
          element-loading-text="加载中..."
          class="boxShadow"
        >
          <div class="ecTop">
            <span class="title">车重分布</span>
          </div>
          <div v-if="lineBarData2.xData.length == 0" class="ecBtm">
            暂无数据
          </div>
          <div v-else class="ecBtm">
            <statisticsBarLine
              ref="statisticsEchart5"
              :echartData="lineBarData2"
            ></statisticsBarLine>
          </div>
        </el-main>
      </div>
      <div
        class="statisticsBox statisticsSecond animate__animated animate__fadeIn"
      >
        <el-main
          v-loading="loading5"
          element-loading-background="rgba(255, 255,255, 1)"
          element-loading-text="加载中..."
          class="boxShadow left"
        >
          <div class="ecTop">
            <span class="title">重车统计</span>
            <div class="selItem">
              <el-select
                v-model="weight"
                placeholder="请选择重量"
                @change="getCountCarWeight(true)"
              >
                <el-option
                  v-for="item in weightList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                >
                </el-option>
              </el-select>
            </div>
          </div>
          <div v-if="lineBarData3.xData.length == 0" class="ecBtm">
            暂无数据
          </div>
          <div v-else class="ecBtm">
            <statisticsBarLine
              ref="statisticsEchart6"
              :echartData="lineBarData3"
            ></statisticsBarLine>
          </div>
        </el-main>
        <el-main
          v-loading="loading6"
          element-loading-background="rgba(255, 255,255, 1)"
          element-loading-text="加载中..."
          class="boxShadow right"
        >
          <div class="ecTop">
            <span class="title">超重车分布</span>
            <div class="selItem">
              <el-select
                v-model="axleId"
                placeholder="请选择车道"
                @change="getTransfiniteCar(true)"
              >
                <el-option
                  v-for="item in axleList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                >
                </el-option>
              </el-select>
            </div>
          </div>
          <div class="ecBtm">
            <statisticsPie2
              ref="statisticsEchart7"
              :echartData="pieData3"
            ></statisticsPie2>
          </div>
        </el-main>
      </div>
    </div>
  </div>
</template>

<script>
import {
  getDistribute,
  getTrafficFlow,
  getMaxAlexWeight,
  getCarWeight,
  getCountCarWeight,
  getTransfiniteCar
} from '@/api/online/analysisData';
import statisticsLine from './components/statisticsLine';
import statisticsPie from './components/statisticsPie';
import statisticsPie2 from './components/statisticsPie2';
import statisticsBarLine from './components/statisticsBarLine';
export default {
  name: 'statisticsData',
  components: {
    statisticsLine,
    statisticsPie,
    statisticsPie2,
    statisticsBarLine
  },
  props: ['structureId', 'lane', 'time'],
  data() {
    return {
      axleList: [],
      axleId: '',
      weightList: [
        { id: 50, name: '超50t' },
        { id: 60, name: '超60t' },
        { id: 70, name: '超70t' },
        { id: 80, name: '超80t' },
        { id: 90, name: '超90t' },
        { id: 100, name: '超100t' }
      ],
      weight: 50,
      index1: 1,
      index2: 1,
      index3: 1,
      lineBarData1: {
        type: 1,
        name: 'barlineData1',
        yName1: '单车道/辆',
        yName2: '车辆总计/辆',
        xData: [],
        data: []
      },
      lineBarData2: {
        type: 2,
        name: 'barlineData2',
        yName1: '单车道/辆',
        yName2: '车辆总计/辆',
        xData: [],
        data: []
      },
      lineBarData3: {
        type: 1,
        name: 'barlineData2',
        yName1: '单车道/辆',
        yName2: '车辆总计/辆',
        xData: [],
        data: []
      },
      lineData: {
        name: 'lineData',
        date: [],
        data: []
      },
      pieData1: {
        name: 'pieData1',
        text1: '车型',
        text2: '',
        type: 1,
        data: [
          { value: 0, name: '<20 km/h' },
          { value: 0, name: '20-40 km/h' },
          { value: 0, name: '40-60 km/h' },
          { value: 0, name: '60-80 km/h' },
          { value: 0, name: '80-100 km/h' }
        ]
      },
      pieData2: {
        name: 'pieData2',
        text1: '车速',
        text2: '单位(km/h)',
        type: 2,
        data: [
          { value: 0, name: '二轴车' },
          { value: 0, name: '三轴车' },
          { value: 0, name: '四轴车' },
          { value: 0, name: '五轴车' },
          { value: 0, name: '六轴车' },
          { value: 0, name: '六轴以上车' }
        ]
      },
      pieData3: {
        name: 'pieData4',
        data: [
          { value: 0, name: '未超重' },
          { value: 0, name: '超重' }
        ]
      },
      loading1: false,
      loading2: false,
      loading3: false,
      loading4: false,
      loading5: false,
      loading6: false
    };
  },
  methods: {
    //获取图形数据
    async getData() {
      if (this.structureId == '') {
        this.$message({
          message: '请选择结构物！',
          type: 'warning',
          duration: 2000,
          showClose: true
        });
        return;
      }
      this.loading1 = true;
      this.loading2 = true;
      this.loading3 = true;
      this.loading4 = true;
      this.loading5 = true;
      this.loading6 = true;
      this.lineBarData1.data = [];
      this.lineBarData2.data = [];
      this.lineBarData3.data = [];
      this.lineData.data = [];
      this.getTrafficFlow();
      this.getMaxAlexWeight();
      this.getCarWeight();
      this.getCountCarWeight();
      this.getDistribute();
      this.getTransfiniteCar();
    },
    //获取车型、车速分布
    async getDistribute() {
      let params = {
        carNo: this.lane,
        endTime: this.time[1],
        startTime: this.time[0],
        structureId: this.structureId
      };
      let { code, data } = await getDistribute(params);
      if (code == '0000') {
        this.loading2 = false;
        let data1 = [];
        let data2 = [];
        data.model.map((item) => {
          let obj = {
            id: item.axleId,
            name: item.name,
            value: item.count
          };
          data1.push(obj);
        });
        data.carSpeed.map((item) => {
          let obj = {
            name: item.name,
            value: item.count
          };
          data2.push(obj);
        });
        this.axleList = [...data1];
        this.axleId = data1[0].id;
        this.pieData1.data = data1;
        this.pieData2.data = data2;
        this.$nextTick(() => {
          if (data1.length > 0) {
            this.$refs.statisticsEchart2.setOption();
          }
          if (data1.length > 0) {
            this.$refs.statisticsEchart3.setOption();
          }
        });
      }
    },
    //获取车流量时程
    async getTrafficFlow() {
      let params = {
        carNo: this.lane,
        endTime: this.time[1],
        startTime: this.time[0],
        structureId: this.structureId
      };
      let { code, data } = await getTrafficFlow(params);
      if (code == '0000') {
        this.loading1 = false;
        let { arry, xData, total } = this.dataChange(data, 'time');
        // arry.reverse();
        arry.push({ type: 'bar', name: '车辆总计', data: total });
        this.lineBarData1.data = arry;
        this.lineBarData1.xData = xData;
        this.$nextTick(() => {
          if (xData.length > 0) {
            this.$refs.statisticsEchart1.getData();
          }
        });
      }
    },
    //获取最大车重
    async getMaxAlexWeight() {
      let params = {
        carNo: this.lane,
        endTime: this.time[1],
        startTime: this.time[0],
        structureId: this.structureId
      };
      let { code, data } = await getMaxAlexWeight(params);
      if (code == '0000') {
        this.loading3 = false;
        let { arry, xData } = this.dataChange(data, 'time');
        // arry.reverse();
        this.lineData.data = arry;
        this.lineData.date = xData;
        this.$nextTick(() => {
          if (xData.length > 0) {
            this.$refs.statisticsEchart4.getData();
          }
        });
      }
    },
    //获取车重分布
    async getCarWeight() {
      let params = {
        carNo: this.lane,
        endTime: this.time[1],
        startTime: this.time[0],
        structureId: this.structureId
      };
      let { code, data } = await getCarWeight(params);
      if (code == '0000') {
        this.loading4 = false;
        let { arry, xData, total } = this.dataChange(data, 'name');
        // arry.reverse();
        arry.push({ type: 'bar', name: '车辆总计', data: total });
        this.lineBarData2.data = arry;
        this.lineBarData2.xData = xData;
        this.$nextTick(() => {
          if (xData.length > 0) {
            this.$refs.statisticsEchart5.getData();
          }
        });
      }
    },
    //重车统计
    async getCountCarWeight(state) {
      if (state) this.loading5 = true;
      let params = {
        carNo: this.lane,
        endTime: this.time[1],
        startTime: this.time[0],
        structureId: this.structureId,
        weight: this.weight
      };
      let { code, data } = await getCountCarWeight(params);
      if (code == '0000') {
        this.loading5 = false;
        let { arry, xData, total } = this.dataChange(data, 'time');
        // arry.reverse();
        arry.push({ type: 'bar', name: '车辆总计', data: total });
        this.lineBarData3.data = arry;
        this.lineBarData3.xData = xData;
        this.$nextTick(() => {
          if (xData.length > 0) {
            this.$refs.statisticsEchart6.getData();
          }
        });
      }
    },
    //超重车统计
    async getTransfiniteCar(state) {
      if (state) this.loading6 = true;
      let params = {
        axleId: this.axleId,
        carNo: this.lane,
        endTime: this.time[1],
        startTime: this.time[0],
        structureId: this.structureId
      };
      let { code, data } = await getTransfiniteCar(params);
      if (code == '0000') {
        this.loading6 = false;
        let arry = [];
        for (let key in data) {
          let obj = {
            name: key,
            value: data[key]
          };
          arry.push(obj);
        }
        this.pieData3.data = arry;
        this.$nextTick(() => {
          if (arry.length > 0) {
            this.$refs.statisticsEchart7.setOption();
          }
        });
      }
    },
    //将数据转换
    dataChange(data, xName) {
      let arry = [];
      let xData = [];
      let total = [];
      for (let key in data) {
        let laneData = [];
        data[key].map((item, i) => {
          laneData.push(item.count);
          xData.push(item[xName]);
          if (total.length > i) {
            total[i] += item.count;
          } else {
            total.push(item.count);
          }
        });
        let obj = {
          type: 'line',
          name: key,
          data: laneData
        };
        arry.push(obj);
      }
      xData = [...new Set(xData)];
      return { arry, xData, total };
    },
    //时日选择
    itemChange1(e) {
      this.index1 = e;
      this.$refs.statisticsEchart1.setOption();
    },
    itemChange2(e) {
      this.index2 = e;
      this.$refs.statisticsEchart4.setOption();
    },
    itemChange3(e) {
      this.index3 = e;
      this.$refs.statisticsEchart7.setOption();
    },
    echartReflash() {
      this.$refs.statisticsEchart1.clear();
      this.$refs.statisticsEchart2.clear();
      this.$refs.statisticsEchart3.clear();
      this.$refs.statisticsEchart4.clear();
      this.$refs.statisticsEchart5.clear();
      this.$refs.statisticsEchart6.clear();
      this.$refs.statisticsEchart7.clear();
    }
  }
};
</script>
<style lang="scss" scoped>
.statisticsData {
  .statisticsDataBox {
    width: 100%;
  }
  .statisticsBox {
    width: 100%;
    height: 360px;
    // margin-top: 1.855vh;
    margin-top: 1.855vh;
    display: flex;
    justify-content: space-between;
    .boxShadow {
      padding: 1.854vh 24px;
      display: flex;
      flex-direction: column;
      .ecTop {
        height: 2.967vh;
        display: flex;
        align-items: center;
        justify-content: space-between;
        .title {
          font-size: 16px;
          color: #262626;
        }
        .selItem {
          width: 120px;
          height: 100%;
          display: flex;
          align-items: center;
          span {
            // height: 100%;
            padding: 0.464vh 16px;
            border: 1px solid #d9d9d9;
            cursor: pointer;
            &:first-child {
              border-right: 0;
              border-top-left-radius: 2px;
              border-bottom-left-radius: 2px;
            }
            &:last-child {
              border-left: 0;
              border-top-right-radius: 2px;
              border-bottom-right-radius: 2px;
            }
          }
          .activeItem {
            color: #2f80ed;
            border: 1px solid #1890ff !important;
          }
        }
      }
      .ecBtm {
        width: 100%;
        flex: 1;
        font-size: 28px;
        color: #333;
        display: flex;
        align-items: center;
        justify-content: center;
        .pieItem {
          height: 100%;
          width: 50%;
        }
      }
    }
    /deep/ .el-main {
      flex: unset;
    }
  }
  .statisticsFirst {
    .boxShadow {
      width: 886px;
      height: 100%;
    }
  }
  .statisticsSecond {
    .left {
      width: 1188px;
      height: 100%;
    }
    .right {
      width: 584px;
      height: 100%;
    }
  }
}
</style>
