<template>
  <div class="detection">
    <div class="top">
      <el-select
        v-model="structureId"
        placeholder="请选择结构物"
        class="top_select"
      >
        <el-option
          v-for="item in structureList"
          :key="item.id"
          :label="item.name"
          :value="item.id"
        >
        </el-option>
      </el-select>

      <el-button type="primary" size="medium" @click="selectProject"
        ><span class="top_button_span">查询</span></el-button
      >
    </div>

    <div class="middle animate__animated animate__fadeInDown">
      <div class="middle_left">
        <div class="middle_left_top boxShadow">
          <div class="middle_left_top_div_p">
            <span class="one_p">BCI</span>
            <span class="border_span_background one_span">{{ bci }}</span>
          </div>
          <div class="middle_left_top_div_p">
            <span class="one_p">BSIm</span>
            <span class="border_span_background2 one_span">{{ bsim }}</span>
          </div>
          <div class="middle_left_top_div_p">
            <span class="one_p">BSIs</span>
            <span class="border_span_background3 one_span">{{ bsis }}</span>
          </div>
          <div class="middle_left_top_div_p">
            <span class="one_p">BSIx</span>
            <span class="border_span_background4 one_span">{{ bsix }}</span>
          </div>
        </div>

        <div class="boxShadow middle_left_follow">
          <div class="tool">
            <span class="middle_left_follow_span">历年评分变化</span>
            <el-select
              v-model="partType"
              placeholder="请选择"
              class="middle_left_follow_span_select"
              @change="getScoreTrend"
            >
              <el-option
                v-for="item in partTypeList"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              >
              </el-option>
            </el-select>
          </div>
          <span v-if="yearData.y1.length == 0" class="noData">暂无数据</span>
          <div v-else class="border_ec2" id="yearEchart"></div>
        </div>
      </div>

      <div class="boxShadow middle_middle">
        <div v-if="imgList.length == 0" class="noData">
          <span class="icon"><i class="el-icon-picture-outline"></i></span>
          <span>暂无图片</span>
        </div>
        <div v-else class="middle_middle_div">
          <el-carousel :interval="3000" arrow="always" height="39.7vh" autoplay>
            <el-carousel-item v-for="item in imgList" :key="item.id">
              <img :src="item.path" class="el_img" />
            </el-carousel-item>
          </el-carousel>
        </div>
      </div>

      <div class="boxShadow middle_right">
        <div class="middle_right_top">
          <span class="middle_right_top_span">基本信息</span>
          <span v-if="structureId != ''" class="more" @click="routerGo({}, 1)"
            >更多</span
          >
        </div>
        <div class="middle_right_follow">
          <span class="my_p">
            <span class="my_span1">桥隧名称：</span
            ><span class="my_span2">{{ structureInfo.name || '/' }}</span>
          </span>
          <span class="my_p">
            <span class="my_span1">桥梁类型：</span
            ><span class="my_span2">{{
              structureInfo.bridgeTypeName || '/'
            }}</span>
          </span>
          <span class="my_p">
            <span class="my_span1">建成年月：</span
            ><span class="my_span2">{{ structureInfo.buildTime || '/' }}</span>
          </span>
          <span class="my_p">
            <span class="my_span1">桥梁全长：</span
            ><span class="my_span2">{{
              structureInfo.bridgeInfo.totalLength || '/'
            }}</span>
          </span>
          <span class="my_p">
            <span class="my_span1">桥梁跨数：</span
            ><span class="my_span2">{{
              structureInfo.bridgeInfo.spanNum || '/'
            }}</span>
          </span>
          <span class="my_p">
            <span class="my_span1">跨径组合(m)：</span
            ><span class="my_span2">{{
              structureInfo.bridgeInfo.spanCombination || '/'
            }}</span>
          </span>
          <span class="my_p">
            <span class="my_span1">设计荷载：</span
            ><span class="my_span2">{{ structureInfo.designLoad || '/' }}</span>
          </span>
          <span class="my_p">
            <span class="my_span1">养护类别：</span
            ><span class="my_span2">{{
              structureInfo.maintainCategory || '/'
            }}</span>
          </span>
          <span class="my_p">
            <span class="my_span1">管养单位：</span
            ><span class="my_span2">{{
              structureInfo.runningDepartment || '/'
            }}</span>
          </span>
        </div>
      </div>
    </div>

    <div class="follow animate__animated animate__fadeInUp">
      <div class="boxShadow follow_left">
        <div class="tool">
          <span class="follow_left_span">构件病害数排行</span>
          <el-select
            v-model="componentId"
            placeholder="全部构件"
            class="follow_left_select"
            popper-class="componentStyle"
            @change="getDiseaseByComponent"
            clearable
          >
            <el-option
              v-for="item in componentList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </div>
        <span v-if="diseaseNumberData.x.length == 0" class="noData">
          暂无数据
        </span>
        <div v-else class="border_ec" id="diseaseNumberEchart"></div>
      </div>
      <div class="boxShadow follow_right">
        <span class="follow_right_span">检测概况</span>
        <div class="follow_right_table">
          <el-table
            class="tableBox"
            :data="tableData"
            :row-class-name="tableRowClassName"
          >
            <el-table-column
              type="index"
              label="序号"
              width="80"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="evaluationUnit"
              label="检测单位"
              align="center"
              show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column prop="type" label="检测类型" align="center">
            </el-table-column>
            <el-table-column
              prop="evaluationTime"
              label="检测时间"
              align="center"
            >
            </el-table-column>
            <el-table-column prop="ratingLevel" label="评定等级" align="center">
            </el-table-column>
            <el-table-column
              label="BCI评分"
              prop="bridgeConditionIndex"
              sortable
              align="center"
            >
              <template slot-scope="scope">
                <div
                  class="perBox"
                  :class="
                    scope.row.bridgeConditionIndex >= 80
                      ? 'success'
                      : scope.row.bridgeConditionIndex < 80 &&
                        scope.row.bridgeConditionIndex >= 70
                      ? 'yellow'
                      : scope.row.bridgeConditionIndex < 70 &&
                        scope.row.bridgeConditionIndex >= 60
                      ? 'warning'
                      : scope.row.bridgeConditionIndex < 60 &&
                        scope.row.bridgeConditionIndex >= 0
                      ? 'danger'
                      : ''
                  "
                >
                  {{ scope.row.bridgeConditionIndex }}
                </div>
              </template>
            </el-table-column>

            <el-table-column
              label="BSIm"
              prop="deckSystemBSIm"
              sortable
              align="center"
            >
              <template slot-scope="scope">
                <div
                  class="perBox"
                  :class="
                    scope.row.deckSystemBSIm >= 80
                      ? 'success'
                      : scope.row.deckSystemBSIm < 80 &&
                        scope.row.deckSystemBSIm >= 70
                      ? 'yellow'
                      : scope.row.deckSystemBSIm < 70 &&
                        scope.row.deckSystemBSIm >= 60
                      ? 'warning'
                      : scope.row.deckSystemBSIm < 60 &&
                        scope.row.deckSystemBSIm >= 0
                      ? 'danger'
                      : ''
                  "
                >
                  {{ scope.row.deckSystemBSIm }}
                </div>
              </template>
            </el-table-column>

            <el-table-column
              label="BSIs评分"
              prop="upperStructureBSIs"
              sortable
              align="center"
            >
              <template slot-scope="scope">
                <div
                  class="perBox"
                  :class="
                    scope.row.upperStructureBSIs >= 80
                      ? 'success'
                      : scope.row.upperStructureBSIs < 80 &&
                        scope.row.upperStructureBSIs >= 70
                      ? 'yellow'
                      : scope.row.upperStructureBSIs < 70 &&
                        scope.row.upperStructureBSIs >= 60
                      ? 'warning'
                      : scope.row.upperStructureBSIs < 60 &&
                        scope.row.upperStructureBSIs >= 0
                      ? 'danger'
                      : ''
                  "
                >
                  {{ scope.row.upperStructureBSIs }}
                </div>
              </template>
            </el-table-column>

            <el-table-column
              label="BSIx评分"
              prop="lowerStructureBSIx"
              sortable
              align="center"
            >
              <template slot-scope="scope">
                <div
                  class="perBox"
                  :class="
                    scope.row.lowerStructureBSIx >= 80
                      ? 'success'
                      : scope.row.lowerStructureBSIx < 80 &&
                        scope.row.lowerStructureBSIx >= 70
                      ? 'yellow'
                      : scope.row.lowerStructureBSIx < 70 &&
                        scope.row.lowerStructureBSIx >= 60
                      ? 'warning'
                      : scope.row.lowerStructureBSIx < 60 &&
                        scope.row.lowerStructureBSIx >= 0
                      ? 'danger'
                      : ''
                  "
                >
                  {{ scope.row.lowerStructureBSIx }}
                </div>
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center">
              <template slot-scope="scope">
                <el-link type="primary" @click="routerGo(scope.row)"
                  >评价结果</el-link
                >
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {
  getDiseaseByComponent,
  getEvaluationInfo,
  getNewestScore,
  getScoreTrend,
  getComponent
} from '@/api/assessment/detection';
import { getStructureListByModel, getStructureById } from '@/api/common';
import * as echarts from 'echarts';
export default {
  name: 'detection',
  components: {},
  props: {},
  data() {
    return {
      imgList: [],
      bci: 0,
      bsim: 0,
      bsis: 0,
      bsix: 0,
      tableData: [],
      structureInfo: { bridgeInfo: {} },
      structureId: '', //项目值
      structureList: [],
      componentId: '',
      componentList: [],

      partType: 2,
      partTypeList: [
        {
          id: 2,
          name: '全桥'
        },
        {
          id: 3,
          name: '桥面系'
        },
        {
          id: 4,
          name: '上部结构'
        },
        {
          id: 5,
          name: '下部结构'
        }
      ],
      yearEcharts: null,
      yearData: {
        x: [],
        y1: []
      },
      diseaseNumberEcharts: null,
      diseaseNumberData: {
        x: [],
        y: []
      }
    };
  },
  beforeMount() {
    this.$utils.getAuthPage(this); //获取权限
  },
  mounted() {
    this.getComponent(); //获取构建病害数排行列表
    this.getStructureListByModel(); //获取结构物列表
  },
  methods: {
    //获取结构物列表
    async getStructureListByModel() {
      let params = {
        powerId: this.$store.getters.getActiveIndex
      };
      let { code, data } = await getStructureListByModel(params);
      if (code == '0000') {
        this.structureList = data;
        if (this.$route.query.structureId) {
          this.structureId = Number(this.$route.query.structureId);
        } else {
          this.structureId = data[0] ? data[0].id : '';
        }
        this.$nextTick(() => {
          if (data.length > 0) {
            this.selectProject();
          }
        });
      }
    },
    //获取构建病害数排行列表
    async getComponent() {
      let { code, data } = await getComponent();
      if (code == '0000') {
        this.componentList = data;
      }
    },
    //获取基本信息
    async getStructureById() {
      let id = !!this.structureId ? this.structureId : 0;
      let { code, data } = await getStructureById(id, 2);
      if (code == '0000') {
        data.photoList.map((item) => {
          item.path = this.$basePath + item.path;
        });
        data.maintainCategory = this.getType(data.maintainCategory);
        this.structureInfo = data;
        if (data.bridgeInfo === null) {
          this.structureInfo.bridgeInfo = {};
        }
        this.imgList = data.photoList;
      }
    },
    getType(id) {
      let type = '';
      if (id == 1) {
        type = 'Ⅰ类养护';
      } else if (id == 2) {
        type = 'Ⅱ类养护';
      } else if (id == 3) {
        type = 'Ⅲ类养护';
      } else if (id == 4) {
        type = 'Ⅳ类养护';
      } else if (id == 5) {
        type = 'Ⅴ类养护';
      } else {
        type = '/';
      }
      return type;
    },
    //构建病害数排行
    async getDiseaseByComponent() {
      let id = !!this.structureId ? this.structureId : 0;
      let componentId = this.componentId != '' ? this.componentId : 0;
      let { code, data } = await getDiseaseByComponent(id, componentId);
      if (code == '0000') {
        let xData = [];
        let yData = [];
        data.map((item) => {
          xData.push(item.name);
          yData.push(item.count);
        });
        this.diseaseNumberData.x = xData;
        this.diseaseNumberData.y = yData;
        this.$nextTick(() => {
          if (yData.length > 0) {
            this.diseaseNumberEchartPoint(this.diseaseNumberData);
          }
        });
      }
    },
    //检测概况
    async getEvaluationInfo() {
      let id = !!this.structureId ? this.structureId : 0;
      let { code, data } = await getEvaluationInfo(id);
      if (code == '0000') {
        this.tableData = data;
      }
    },
    //最近一次评分情况
    async getNewestScore() {
      let id = !!this.structureId ? this.structureId : 0;
      let { code, data } = await getNewestScore(id);
      if (code == '0000') {
        this.bci = data.bci;
        this.bsim = data.bsim;
        this.bsis = data.bsis;
        this.bsix = data.bsix;
      }
    },
    //历年评分变化
    async getScoreTrend() {
      let id = !!this.structureId ? this.structureId : 0;
      let { code, data } = await getScoreTrend(id, this.partType);
      if (code == '0000') {
        let xData = [];
        let yData = [];
        data.map((item) => {
          xData.push(item.evaluationTime);
          yData.push(item.bridgeConditionIndex);
        });
        this.yearData.x = xData;
        this.yearData.y1 = yData;
        this.$nextTick(() => {
          if (yData.length > 0) {
            this.yearEchartPoint(this.yearData);
          }
        });
      }
    },
    selectProject() {
      if (this.structureId == '') {
        this.$message({
          message: '请选择结构物！',
          type: 'warning',
          showClose: true,
          duration: 2000
        });
        return;
      }
      this.getDiseaseByComponent();
      this.getEvaluationInfo();
      this.getNewestScore();
      this.getScoreTrend();
      this.getStructureById();
    },
    diseaseNumberEchartPoint(diseaseNumberData) {
      this.diseaseNumberEcharts = echarts.init(
        document.getElementById('diseaseNumberEchart')
      );
      let option = {
        tooltip: {
          trigger: 'axis'
        },
        xAxis: {
          type: 'category',
          splitLine: { show: false },
          axisLabel: { color: '#595959' },
          axisTick: { show: false },
          axisLine: { lineStyle: { color: '#BFBFBF' } },
          splitArea: { show: false },
          data: diseaseNumberData.x
        },
        color: '#58AFFF',
        yAxis: {
          type: 'value',
          splitLine: {
            lineStyle: { width: 1, type: 'dashed', color: '#dedede' }
          },
          axisLabel: { color: '#595959' },
          axisTick: { show: false },
          axisLine: { show: false },
          splitArea: { show: false }
        },
        grid: {
          top: '10%',
          left: '3%',
          right: '5%',
          bottom: '5%',
          containLabel: true
        },
        series: [
          {
            barWidth: 20,
            data: diseaseNumberData.y,
            type: 'bar'
          }
        ]
      };
      this.diseaseNumberEcharts.setOption(option);
      window.addEventListener('resize', () => {
        this.diseaseNumberEcharts.resize();
      });
    },
    yearEchartPoint(yearData) {
      this.yearEcharts = echarts.init(document.getElementById('yearEchart'));
      let option = {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            label: {
              backgroundColor: '#6a7985'
            }
          }
        },
        legend: {
          data: ['BCI'],
          left: 'center',
          itemGap: 48,
          itemWidth: 32,
          itemHeight: 8
        },
        grid: {
          top: '20%',
          left: '3%',
          right: '5%',
          bottom: '5%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            boundaryGap: false,
            splitLine: { show: false },
            axisLabel: { color: '#595959' },
            axisTick: { inside: true, lineStyle: { color: '#BFBFBF' } },
            axisLine: { lineStyle: { color: '#BFBFBF' } },
            splitArea: { show: false },
            data: yearData.x
          }
        ],
        yAxis: [
          {
            type: 'value',
            splitLine: {
              lineStyle: { width: 1, type: 'dashed', color: '#dedede' }
            },
            axisLabel: { color: '#595959' },
            axisTick: { show: false },
            axisLine: { show: false },
            splitArea: { show: false }
          }
        ],
        color: ['#419AFF'],
        series: [
          {
            name: 'BCI',
            type: 'line',
            areaStyle: {
              color: 'rgba(65, 154, 255, 0.1)'
            },
            data: yearData.y1
          }
        ]
      };
      this.yearEcharts.setOption(option);
      window.addEventListener('resize', () => {
        this.yearEcharts.resize();
      });
    },
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex % 2 == 1) {
        return 'first-row';
      } else {
        return 'second-row';
      }
    },
    routerGo(data, index) {
      if (index && index == 1) {
        this.$store.dispatch('asyncUpdateActiveIndex', 91);
        this.$router.push({
          path: '/infoManage/bridgeManageDetial',
          query: {
            id: this.structureId,
            structureName: this.structureInfo.name,
            name: '结构物检测'
          }
        });
      } else {
        this.$router.push({
          name: '检测记录',
          params: {
            structureInfoId: this.structureId,
            monitorStructureId: data.monitorStructureId,
            activeName: 'BCIeva',
            endTime: data.endTime,
            firstNav: 'evaluation',
            projectId: data.projectId
          }
        });
      }
    }
  }
};
</script>
<style lang="scss" scoped>
.detection {
  background: transparent !important;
  box-shadow: unset;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  .top {
    display: flex;
    align-items: center;
    width: 16%;
    justify-content: space-between;
  }
  .middle {
    height: 43.519vh;
    display: flex;
    align-items: center;
    justify-content: space-between;
    .middle_left {
      height: 100%;
      width: 592px;
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      .middle_left_top {
        height: 8.89vh;
        width: 100%;
        padding: 18px 0;
        display: flex;
        align-items: center;
        justify-content: space-between;
        .middle_left_top_div_p {
          width: 25%;
          height: 100%;
          border-right: 1px solid #dedede;
          padding: 0 36px;
          display: flex;
          flex-direction: column;
          justify-content: space-evenly;
          .one_p {
            font-size: 16px;
            color: #595959;
          }
          .one_span {
            font-size: 28px;
          }
          &:last-child {
            border: 0;
          }
        }
      }
      .middle_left_follow {
        height: 32.778vh;
        padding: 24px;
        display: flex;
        flex-direction: column;
        .tool {
          display: flex;
          justify-content: space-between;
        }
        .border_ec2 {
          flex: 1;
        }
      }
    }
    .middle_middle {
      width: 792px;
      height: 100%;
      padding: 24px;
      display: flex;
      .middle_middle_div {
        width: 100%;
        height: 100%;
        img {
          height: 100%;
          width: 100%;
        }
        /deep/ .el-carousel {
          height: 100%;
        }
        /deep/ .el-carousel__item {
          background: transparent;
        }
        /deep/ .el-carousel__button {
          width: 12px;
          height: 12px;
          border-radius: 50%;
          background: #e5e5e5;
          opacity: 1;
        }
        /deep/ .el-carousel__indicator.is-active button {
          background: #419aff;
        }
      }
    }
    .middle_right {
      height: 100%;
      width: 440px;
      padding: 24px;
      display: flex;
      flex-direction: column;
      .middle_right_top {
        font-size: 16px;
        display: flex;
        align-items: center;
        justify-content: space-between;
        .middle_right_top_span {
          color: #262626;
        }
        .more {
          color: #419aff;
          cursor: pointer;
        }
      }
      .middle_right_follow {
        flex: 1;
        padding: 20px 32px;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        .my_p {
          font-size: 16px;
          display: flex;
          align-items: center;
          .my_span1 {
            color: #595959;
            white-space: nowrap;
          }
          .my_span2 {
            color: #333;
          }
        }
      }
    }
  }
  .follow {
    height: 33.705vh;
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;
    .follow_left {
      height: 100%;
      width: 592px;
      padding: 24px;
      display: flex;
      flex-direction: column;
      .tool {
        display: flex;
        justify-content: space-between;
      }
    }
    .follow_right {
      height: 100%;
      width: 1252px;
      padding: 24px;
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      .follow_right_span {
        // padding-bottom: 10px;
      }
      .follow_right_table {
        height: 25vh;
        .tableBox {
          .perBox {
            width: 56px;
            height: 24px;
            border-radius: 4px;
            margin: 0 auto;
            display: flex;
            align-items: center;
            justify-content: center;
          }
        }
        .success {
          background: #4ecb73;
          color: #fff;
        }
        .yellow {
          background: #f2c94c;
          color: #fff;
        }
        .warning {
          background: #fa8c16;
          color: #fff;
        }
        .danger {
          background: #f5222d;
          color: #fff;
        }
        /deep/ .el-table th {
          font-size: 14px;
          color: #333;
          font-weight: bold;
          padding: 0.4644vh 0;
        }
        /deep/ .el-table td {
          font-size: 14px;
          color: #333;
          padding: 0.88vh 0;
        }
        /deep/ .el-table__body-wrapper {
          max-height: 20.371vh;
          overflow: overlay;
          &::-webkit-scrollbar {
            width: 6px;
            height: 6px;
            border-radius: 6px;
          }
          &:hover {
            &::-webkit-scrollbar-thumb {
              border-radius: 6px;
              background: #c4c4c4;
            }
          }
        }
      }
    }
  }
}

//空数据样式
.noData {
  width: 100%;
  flex: 1;
  font-size: 28px;
  color: #333;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  .icon {
    font-size: 38px;
  }
}

.border_span_background {
  color: #419aff;
}
.border_span_background2 {
  color: #eb5757;
}
.border_span_background3 {
  color: #f2994a;
}
.border_span_background4 {
  color: #219653;
}
.el_img {
  width: 100%;
  height: 100%;
}
.border_ec {
  width: 100%;
  height: 27.7778vh;
}

//斑马纹样式
/deep/ .first-row {
  background: #ffffff;
}
/deep/ .second-row {
  background: #f2f4f6;
}
</style>
<style lang="scss">
//构建病害数排行下拉框
.componentStyle {
  .el-select-dropdown__list {
    max-height: 180px;
  }
}
</style>
