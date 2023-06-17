<template>
  <div class="analysis">
    <div class="top">
      <div class="border_select">
        <el-select
          v-model="projectId"
          placeholder="全部项目"
          @change="getStructureList(true)"
        >
          <el-option
            v-for="item in projectList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          >
          </el-option>
        </el-select>
      </div>

      <div class="border_select">
        <el-select v-model="structureId" placeholder="全部结构物">
          <el-option
            v-for="item in structureList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          >
          </el-option>
        </el-select>
      </div>

      <div>
        <el-button type="primary" @click="getStructureTimeList" size="medium"
          >查询</el-button
        >
      </div>
    </div>

    <div class="middle animate__animated animate__fadeInDown">
      <div class="middle_left">
        <div class="middle_left_top">
          <div class="boxShadow middle_left_top_div">
            <div class="middle_left_top_div_top">检测次数</div>
            <div class="middle_left_top_div_follow">
              <div class="middle_left_top_div_follow_left">{{ number }}</div>
              <i class="iconfont icon-fangdajing color color-blue"> </i>
            </div>
          </div>
          <div class="boxShadow border_div middle_left_top_div">
            <div class="middle_left_top_div_top">检测病害数</div>

            <div class="middle_left_top_div_follow">
              <div class="middle_left_top_div_follow_left">
                {{ diseaseNumber }}
              </div>
              <i class="iconfont icon-version color color-orange"> </i>
            </div>
          </div>
        </div>

        <div class="boxShadow middle_left_follow">
          <div class="title">
            <span class="middle_left_follow_span">结构病害数量统计</span>
            <el-select
              class="selTime"
              v-model="time"
              placeholder="时间"
              @change="getPageInfo"
            >
              <el-option
                v-for="item in timeList"
                :key="item"
                :label="item"
                :value="item"
              >
              </el-option>
            </el-select>
          </div>
          <div v-if="pieData.data.length == 0" class="noData">暂无数据</div>
          <analysisPie
            v-else
            ref="analysisPie"
            class="border_ec"
            :echartData="pieData"
          ></analysisPie>
        </div>
      </div>

      <div class="boxShadow middle_right">
        <div class="title">
          <div class="middle_right_span">构件病害数量统计</div>
          <el-select
            class="selTime"
            v-model="time"
            placeholder="时间"
            @change="getPageInfo"
          >
            <el-option
              v-for="item in timeList"
              :key="item"
              :label="item"
              :value="item"
            >
            </el-option>
          </el-select>
        </div>
        <div v-if="lineData1.xData.length == 0" class="noData">暂无数据</div>
        <analysisLine1
          v-else
          ref="analysisLine1"
          class="border_ec"
          :echartData="lineData1"
        ></analysisLine1>
      </div>
    </div>

    <div class="follow animate__animated animate__fadeInUp">
      <div class="boxShadow follow_left">
        <div class="title">
          <span class="follow_left_span">历年评分对比</span>
          <div class="changItem">
            <span
              :class="{ activeTxt: typeIndex == 1 }"
              @click="changeTypeIndex(1)"
              >BCI</span
            >
            <span
              :class="{ activeTxt: typeIndex == 2 }"
              @click="changeTypeIndex(2)"
              >BSI</span
            >
          </div>
        </div>
        <div v-if="barData1.xData.length == 0" class="noData">暂无数据</div>
        <analysisBar
          v-else
          ref="analysisBar"
          class="border_ec"
          :echartData="barData1"
        ></analysisBar>
      </div>

      <div class="boxShadow follow_right">
        <div class="follow_right_div">
          <div class="title">
            <span class="follow_right_div_span">构件病害类型统计</span>
            <div>
              <el-select
                class="selTime"
                v-model="time"
                placeholder="时间"
                @change="getPageInfo"
              >
                <el-option
                  v-for="item in timeList"
                  :key="item"
                  :label="item"
                  :value="item"
                >
                </el-option>
              </el-select>
              <el-select
                v-model="typeName"
                clearable
                placeholder="全部病害"
                @change="getComponentDiseaseType"
                class="follow_right_div_select"
              >
                <el-option
                  v-for="item in typeList"
                  :key="item"
                  :label="item"
                  :value="item"
                >
                </el-option>
              </el-select>
            </div>
          </div>
        </div>
        <div v-if="barData2.xData.length == 0" class="noData">暂无数据</div>
        <analysisBar2
          v-else
          ref="analysisBar2"
          class="border_ec"
          :echartData="barData2"
        ></analysisBar2>
      </div>
    </div>
  </div>
</template>
<script>
import {
  getBCIBSI,
  getComponentDiseaseType,
  getPageInfo,
  getStructureTimeList
} from '@/api/assessment/analysis';
import { getProjectListByModel, getStructureList } from '@/api/common';
import analysisLine1 from './components/analysisLine1';
import analysisBar2 from './components/analysisBar2';
import analysisBar from './components/analysisBar';
import analysisPie from './components/analysisPie';
import echarts from 'echarts';
export default {
  components: {
    analysisLine1,
    analysisBar2,
    analysisBar,
    analysisPie
  },
  data() {
    return {
      //搜索项目的值
      projectId: '',
      //搜索项目
      projectList: [],
      //搜索的结构物的值
      structureId: '',
      //搜索结构物
      structureList: [],
      //搜索时间
      timeList: [],
      time: '',
      //检测次数
      number: 0,
      //检测病害数
      diseaseNumber: 0,
      //搜索类型的值
      typeName: '',
      //搜索类型
      typeList: [],
      //查询的项目id
      sendProjectId: 0,
      //查询的结构物id
      sendStructureId: 0,
      //查询的时间
      sendTime: '',
      //查询的构建类型
      sendTypeName: '',
      //左上角ec图
      pieData: { data: [] },
      //左下角ec图
      typeIndex: 1,
      barData1: {
        type: 1,
        xData: [],
        data1: [],
        data2: [],
        data3: [],
        data4: [],
        data5: [],
        data6: []
      },
      //右上角ec图
      lineData1: {
        xData: [],
        barData: [],
        lineData: []
      },
      //右下角ec图
      barData2: {
        xData: [],
        data: []
      },
      //结构物计划id
      structurePlanId: 0,
      //自适应倍数
      multiple: window.innerHeight / 70 / 7
    };
  },
  beforeMount() {
    this.$utils.getAuthPage(this); //获取权限
  },
  mounted() {
    this.getProjectListByModel();
    //放大缩小页面监听
    window.addEventListener('resize', () => {
      this.multiple = window.innerHeight / 70 / 7;
    });
  },
  methods: {
    //获取项目
    async getProjectListByModel() {
      let id = this.$store.getters.getActiveIndex;
      let { code, data } = await getProjectListByModel(id);
      if (code == '0000') {
        this.projectList = data;
        if (data.length > 0) {
          this.projectId = data[0].id;
          this.getStructureList();
        }
      }
    },
    //获取结构物
    async getStructureList(state) {
      let params = {
        powerId: this.$store.getters.getActiveIndex,
        projectId: this.projectId
      };
      let { code, data } = await getStructureList(params);
      if (code == '0000') {
        this.structureList = data;
        if (data.length > 0) {
          this.structureId = data[0].id;
          if (!state) {
            this.getStructureTimeList();
          }
        }
      }
    },
    //获取时间
    async getStructureTimeList() {
      let { code, data } = await getStructureTimeList(this.structureId,this.projectId);
      if (code == '0000') {
        this.timeList = data;
        if (data.length > 0) {
          this.time = data[0];
        }
        this.getData();
      }
    },
    //查询数据
    getData() {
      this.getBCIBSI();
      this.getPageInfo();
    },
    //获取页面信息
    async getPageInfo() {
      let params = {
        projectId: this.projectId,
        structureId: this.structureId,
        time: this.time
      };
      let { code, data } = await getPageInfo(params);
      if (code == '0000') {
        this.number = data.detectionNumber;
        this.diseaseNumber = data.detectionDiseaseNumber;
        this.pieData.data = data.diseaseStatistics;
        let arry = [];
        let xData = [];
        let lineData = [];
        let barData = [];
        data.componentDiseaseNumber.map((item) => {
          let name = item.typeName + ':' + item.name;
          arry.push(name);
          xData.push(item.name);
          lineData.push(item.points);
          barData.push(item.count);
        });
        this.typeList = arry;
        this.lineData1 = {
          xData,
          lineData,
          barData
        };
        if (data.componentDiseaseNumber.length > 0) {
          await this.getComponentDiseaseType();
        } else {
          this.barData2.xData = [];
          this.barData2.data = [];
        }
        this.$nextTick(() => {
          if (data.diseaseStatistics.length > 0) {
            this.$refs.analysisPie.setOption();
          }
          if (data.componentDiseaseNumber.length > 0) {
            this.$refs.analysisLine1.setOption();
          }
        });
      }
    },
    //获取构件病害类型统计
    async getComponentDiseaseType() {
      let params = {
        projectId: this.projectId,
        structureId: this.structureId,
        time: this.time,
        typeName: this.typeName
      };
      let { code, data } = await getComponentDiseaseType(params);
      if (code == '0000') {
        let xData = [];
        let dataList = [];
        data.map((item) => {
          xData.push(item.name);
          dataList.push(item.count);
        });
        this.barData2.xData = xData;
        this.barData2.data = dataList;
        this.$nextTick(() => {
          if (xData.length > 0) {
            this.$refs.analysisBar2.setOption();
          }
        });
      }
    },
    //获取bciBsi评分
    async getBCIBSI() {
      let params = {
        projectId: this.projectId,
        structureId: this.structureId,
        time: this.time
      };
      let { code, data } = await getBCIBSI(params);
      if (code == '0000') {
        let xData = [];
        let data1 = [];
        let data2 = [];
        let data3 = [];
        let data4 = [];
        let data5 = [];
        let data6 = [];
        data.map((item) => {
          xData.push(item.time);
          data1.push(item.dkScoreBCI);
          data2.push(item.supScoreBCI);
          data3.push(item.subScoreBCI);
          data4.push(item.dkScoreBSI);
          data5.push(item.supScoreBSI);
          data6.push(item.subScoreBSI);
        });
        this.barData1 = {
          type: this.typeIndex,
          xData,
          data1,
          data2,
          data3,
          data4,
          data5,
          data6
        };
        this.$nextTick(() => {
          if (xData.length > 0) {
            this.$refs.analysisBar.setOption();
          }
        });
      }
    },
    //bci和bsi选择
    changeTypeIndex(index) {
      this.typeIndex = index;
      this.barData1.type = index;
      this.$nextTick(() => {
        if (this.barData1.xData.length > 0) {
          this.$refs.analysisBar.setOption();
        }
      });
    },
    compare(property) {
      return function (a, b) {
        var value1 = a[property];
        var value2 = b[property];
        return value2 - value1;
      };
    }
  }
};
</script>
<style lang="scss" scoped>
.analysis {
  background: transparent !important;
  box-shadow: unset;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  .top {
    height: 40px;
    display: flex;
    align-items: center;
    .border_select {
      margin-right: 20px;
    }
  }
  .middle {
    width: 100%;
    height: 38.149vh;
    display: flex;
    align-items: center;
    justify-content: space-between;
    .middle_left {
      height: 100%;
      width: 584px;
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      .middle_left_top {
        height: 8.89vh;
        width: 100%;
        display: flex;
        align-items: center;
        justify-content: space-between;
        .middle_left_top_div {
          width: 282px;
          height: 100%;
          padding: 1.482vh 24px;
          display: flex;
          flex-direction: column;
          .middle_left_top_div_top {
            font-size: 16px;
            color: #595959;
          }
          .middle_left_top_div_follow {
            width: 100%;
            display: flex;
            align-items: center;
            justify-content: space-between;
            .middle_left_top_div_follow_left {
              font-size: 32px;
              color: #333;
            }
            .border_img {
            }
          }
        }
      }
      .middle_left_follow {
        width: 100%;
        height: 27.408vh;
        padding: 24px;
        display: flex;
        flex-direction: column;
        .middle_left_follow_span {
          font-size: 16px;
          color: #262626;
        }
      }
    }
    .middle_right {
      height: 100%;
      width: 1260px;
      padding: 24px;
      display: flex;
      flex-direction: column;
      .middle_right_span {
        font-size: 16px;
        color: #262626;
      }
    }
  }
  .follow {
    width: 100%;
    height: 38.149vh;
    display: flex;
    align-items: center;
    justify-content: space-between;
    .follow_left {
      height: 100%;
      width: 584px;
      padding: 24px;
      display: flex;
      flex-direction: column;
      .follow_left_span {
        font-size: 16px;
        color: #262626;
      }
      .changItem {
        display: flex;
        align-items: center;
        span {
          border: 1px solid #d9d9d9;
          padding: 4px 12px;
          cursor: pointer;
        }
        .activeTxt {
          color: #fff;
          border: 1px solid #419aff;
          background: #419aff;
        }
      }
    }
    .follow_right {
      height: 100%;
      width: 1260px;
      padding: 24px;
      display: flex;
      flex-direction: column;
      .follow_right_div {
        width: 100%;
        display: flex;
        justify-content: space-between;
        .follow_right_div_span {
          font-size: 16px;
          color: #262626;
        }
        .follow_right_div_select {
          width: 160px;
          margin-left: 20px;
        }
      }
    }
  }
  .border_ec {
    width: 100%;
    flex: 1;
  }
}
.noData {
  width: 100%;
  flex: 1;
  font-size: 30px;
  color: #333;
  display: flex;
  align-items: center;
  justify-content: center;
}
.title {
  width: 100%;
  display: flex;
  justify-content: space-between;
  .selTime {
    width: 120px;
  }
}
.color {
  font-size: 32px;
  &-blue {
    color: #419aff;
  }
  &-orange {
    color: orange;
  }
}
</style>
