<template>
  <div class="onBridge">
    <div class="above">
      <el-select
        class="above_select"
        v-model="structureId"
        placeholder="请选择"
      >
        <el-option
          v-for="item in structureList"
          :key="item.id"
          :label="item.name"
          :value="item.id"
          class="above_select_opton"
        >
        </el-option>
      </el-select>

      <el-button type="primary" class="above_button" @click="getData"
        >查询</el-button
      >
    </div>

    <div class="middle">
      <div class="middle_left">
        <div class="middle_left_top animate__animated animate__fadeInDown">
          <div
            v-for="item in itemList"
            :key="item.id"
            class="warnItem boxShadow"
          >
            <div class="text">{{ item.text }}</div>
            <div class="content">
              <div class="txt">
                <span>{{ item.value }}</span>
                <span
                  v-if="item.id == 3 && item.warningTend != 0"
                  :class="item.warningTend > 1 ? 'up' : 'down'"
                >
                  <i class="iconfont icon-shangsheng"></i>
                </span>
              </div>
              <span class="icon" @click="moreReturn(item.name)"
                ><i
                  :class="'iconfont icon-' + item.icon"
                  :style="{ color: item.color }"
                ></i
              ></span>
            </div>
          </div>
        </div>

        <div
          class="
            boxShadow
            middle_left_follow
            animate__animated animate__fadeInLeft
          "
        >
          <div class="top">
            <span class="title">预警统计</span>
            <div class="tapItem">
              <span
                :class="{ activeItem: activeWarnIndex == 1 }"
                @click="warnClick(1)"
                >今日</span
              >
              <span
                :class="{ activeItem: activeWarnIndex == 2 }"
                @click="warnClick(2)"
                >本月</span
              >
              <span
                :class="{ activeItem: activeWarnIndex == 3 }"
                @click="warnClick(3)"
                >今年</span
              >
              <span
                :class="{ activeItem: activeWarnIndex == 0 }"
                @click="warnClick(0)"
                >全部</span
              >
            </div>
          </div>
          <div class="btm">
            <monitorPie ref="monitorPie" :echartData="warnData"></monitorPie>
          </div>
        </div>
      </div>

      <div class="boxShadow middle_middle animate__animated animate__fadeIn">
        <el-tabs v-model="activeName" @tab-click="handleClick" id="tabs">
          <el-tab-pane class="middle_middle2_tab2" label="照片" name="second">
            <div v-if="imgList.length == 0" class="noData">暂无图片</div>
            <el-carousel
              v-else
              :interval="5000"
              arrow="always"
              height="100%"
              autoplay
            >
              <el-carousel-item v-for="item in imgList" :key="item.id">
                <img :src="item.path" />
              </el-carousel-item>
            </el-carousel>
          </el-tab-pane>
          <el-tab-pane
            class="middle_middle2_tab3"
            label="监控视频"
            name="third"
          >
            <div v-if="videoList.length == 0" class="noData">暂无视频</div>
            <el-carousel
              v-else
              :interval="60000"
              arrow="always"
              height="100%"
              autoplay
            >
              <el-carousel-item v-for="item in videoList" :key="item.id">
                <EZUIKit
                  v-if="activeName == 'third'"
                  class="middle_middle2_tab3"
                  :ref="'video' + item.id"
                  :id="item.id"
                  :videoUrl="item.ezopenUrl"
                  :accessToken="item.accessToken"
                ></EZUIKit>
              </el-carousel-item>
            </el-carousel>
          </el-tab-pane>
        </el-tabs>
      </div>

      <div class="middle_right">
        <div
          class="
            boxShadow
            middle_right_top
            animate__animated animate__fadeInDown
          "
        >
          <div class="middle_right_top_top">
            <span>基本信息</span>
            <span
              v-if="structureId != ''"
              class="btn"
              @click="moreReturn('信息管理')"
            >
              更多></span
            >
          </div>
          <div class="middle_right_top_follow">
            <div class="infoItem">
              <span class="title">桥隧名称：</span
              ><span class="text">{{ baseInformation.name || '/' }}</span>
            </div>
            <div class="infoItem">
              <span class="title">管理单位：</span
              ><span class="text">{{
                baseInformation.runningDepartment || '/'
              }}</span>
            </div>
            <div class="infoItem">
              <span class="title">{{
                baseInformation.structureType == 1 ? '桥梁类型：' : '隧道类型：'
              }}</span
              ><span class="text">{{
                baseInformation.bridgeTypeName || '/'
              }}</span>
            </div>
            <div class="infoItem">
              <span class="title">所在路名：</span
              ><span class="text">{{ baseInformation.roadName || '/' }}</span>
            </div>
          </div>
        </div>

        <div
          class="
            boxShadow
            middle_right_follow
            animate__animated animate__fadeInRight
          "
        >
          <div class="middle_right_follow_top">
            <span> 传感器类型 </span>
            <span
              v-if="structureId != ''"
              class="btn"
              @click="moreReturn('传感器设置')"
              >详情</span
            >
          </div>
          <div class="typeEchart">
            <span v-if="typeData.data.length == 0" class="noData"
              >暂无数据</span
            >
            <typePie v-else ref="typePie" :echartData="typeData"></typePie>
          </div>
        </div>
      </div>
    </div>

    <div class="follow animate__animated animate__fadeInUp">
      <div class="boxShadow follow_left">
        <div class="top">
          <span class="title">测点预警排行</span>
          <div class="tapItem">
            <span
              :class="{ activeItem: activeRankingIndex == 1 }"
              @click="rankingClick(1)"
              >今日</span
            >
            <span
              :class="{ activeItem: activeRankingIndex == 2 }"
              @click="rankingClick(2)"
              >本月</span
            >
            <span
              :class="{ activeItem: activeRankingIndex == 3 }"
              @click="rankingClick(3)"
              >今年</span
            >
            <span
              :class="{ activeItem: activeRankingIndex == 0 }"
              @click="rankingClick(0)"
              >全部</span
            >
          </div>
        </div>
        <div class="rankEchart">
          <span v-if="rankData.noData" class="noData">暂无数据</span>
          <rankingBar
            v-else
            ref="rankingBar"
            :echartData="rankData"
          ></rankingBar>
        </div>
      </div>

      <div class="boxShadow follow_right">
        <div class="follow_right_top">
          <span class="title">预警变化</span>
          <div class="right">
            <div class="tapItem">
              <span
                :class="{ activeItem: activeChangeIndex == 1 }"
                @click="changeClick(1)"
                >今日</span
              >
              <span
                :class="{ activeItem: activeChangeIndex == 2 }"
                @click="changeClick(2)"
                >本月</span
              >
              <span
                :class="{ activeItem: activeChangeIndex == 3 }"
                @click="changeClick(3)"
                >今年</span
              >
              <!-- <span
                :class="{ activeItem: activeChangeIndex == 0 }"
                @click="changeClick(0)"
                >全部</span
              > -->
            </div>
            <span
              v-if="structureId != ''"
              class="btn"
              @click="moreReturn('预警管理')"
              >详情</span
            >
          </div>
        </div>
        <div class="follow_right_ec">
          <span v-if="changeData.xName.length == 0" class="noData"
            >暂无数据</span
          >
          <changeLine
            v-else
            ref="changeLine"
            :echartData="changeData"
          ></changeLine>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {
  getCount,
  getSensorList,
  getStatistics,
  getWarningRank,
  getWarningTend
} from '@/api/online/onebridgeMonitoring';
import { getYingshiyunAccessToken } from '@/api/video/video';
import { getStructureListByModel, getStructureById } from '@/api/common';
import monitorGauge from './components/monitorGauge';
import monitorPie from './components/monitorPie';
import typePie from './components/typePie';
import rankingBar from './components/rankingBar';
import changeLine from './components/changeLine';
import EZUIKit from '@/components/video/EZUIKit';
export default {
  name: 'onebridgeMonitoring',
  components: {
    monitorGauge,
    monitorPie,
    typePie,
    rankingBar,
    changeLine,
    EZUIKit
  },
  data() {
    return {
      itemList: [
        {
          id: 1,
          text: '在线传感器',
          value: 0,
          icon: 'chuanganqi1',
          color: '#419AFF',
          name: '传感器设置'
        },
        {
          id: 2,
          text: '离线传感器数',
          value: 0,
          icon: 'guzhang',
          color: '#FF5F5F',
          name: '传感器设置'
        },
        {
          id: 3,
          text: '今日预警',
          value: 0,
          icon: 'yujing',
          color: '#FF7F2F',
          warningTend: 0,
          name: '预警管理'
        },
        {
          id: 4,
          text: '今日待处理',
          value: 0,
          icon: 'daichuli1',
          color: '#595959',
          name: '预警管理'
        }
      ], //统计标签
      activeWarnIndex: 1,
      videoList: [],
      //预警统计图数据
      warnData: {
        total1: 0,
        value1: [
          { name: '未处理', value: 0 },
          { name: '已处理', value: 0 }
        ],
        total2: 0,
        value2: [
          { name: '未处理', value: 0 },
          { name: '已处理', value: 0 }
        ],
        total3: 0,
        value3: [
          { name: '未处理', value: 0 },
          { name: '已处理', value: 0 }
        ]
      },

      //测点预警排行
      activeRankingIndex: 1,
      rankData: {
        xName: [],
        value1: [],
        value2: [],
        value3: [],
        noData: true
      },

      //预警变化
      activeChangeIndex: 1,
      changeData: {
        unit: '时',
        text: '',
        xName: [],
        data1: [],
        data2: [],
        data3: []
      },

      structureId: '',
      structureList: [],
      //传感器类型ec图数据
      typeData: {
        data: []
      },
      //年月日
      radio1: '月',
      //基本信息
      baseInformation: {},
      activeName: 'second',
      //走马灯照片
      imgList: [],
      multiple: window.innerHeight / 70 / 7
    };
  },
  beforeMount() {
    this.$utils.getAuthPage(this); //获取权限
  },
  mounted() {
    this.getStructureListByModel(); //获取结构物
  },
  beforeRouteLeave(to, from, next) {
    window.addEventListener('message', () => {});
    if (to.name === '时程数据') {
      sessionStorage.setItem('structureId', this.structureId);
    } else {
      if (sessionStorage.getItem('structureId')) {
        sessionStorage.removeItem('structureId');
      }
    }
    next();
  },
  beforeRouteEnter(to, from, next) {
    if (from.name === '时程数据') {
      let structureId = sessionStorage.getItem('structureId');
      next((vm) => {
        if (structureId) {
          vm.structureId = Number(structureId);
        }
      });
    } else {
      next();
    }
  },
  methods: {
    //查询数据
    getData() {
      if (this.structureId == '') {
        this.$message({
          type: 'warning',
          message: '请选择结构物',
          showClose: true,
          duration: 2000
        });
        return;
      }
      this.getCount(); //查询结构物在线、离线传感器数/今日预警数/今日待处理
      this.getSensorList(); //传感器类型
      this.warnClick(1); //预警统计
      this.rankingClick(1); //测点预警排行
      this.changeClick(1); //预警变化趋势
      this.getStructureById(); //查询基础信息
    },
    //查询结构物在线、离线传感器数/今日预警数/今日待处理
    async getCount() {
      let { code, data } = await getCount(this.structureId);
      if (code == '0000') {
        this.itemList[0].value = data.online;
        this.itemList[1].value = data.offline;
        this.itemList[2].value = data.todayWarning;
        this.itemList[2].warningTend = data.warningTend;
        this.itemList[3].value = data.todayPending;
      }
    },
    //传感器类型
    async getSensorList() {
      let { code, data } = await getSensorList(this.structureId);
      if (code == '0000') {
        let arry = [];
        data.map((item) => {
          let obj = {
            name: item.name,
            value: item.count
          };
          arry.push(obj);
        });
        this.typeData.data = arry;
        this.$nextTick(() => {
          if (arry.length > 0) {
            this.$refs.typePie.setOption();
          }
        });
      }
    },
    //预警统计选择
    async warnClick(index) {
      this.activeWarnIndex = index;
      let { code, data } = await getStatistics(this.structureId, index);
      if (code == '0000') {
        let list1 = [
          { name: '未处理', value: 0 },
          { name: '已处理', value: 0 }
        ];
        let list2 = [
          { name: '未处理', value: 0 },
          { name: '已处理', value: 0 }
        ];
        let list3 = [
          { name: '未处理', value: 0 },
          { name: '已处理', value: 0 }
        ];
        let total1 = 0;
        let total2 = 0;
        let total3 = 0;
        data.map((item) => {
          let obj = { name: item.status, value: item.count };
          if (item.warningLevel == '一级预警') {
            if (item.status == '未处理') {
              list1[0] = obj;
            } else {
              list1[1] = obj;
            }
            total1 += item.count;
          }
          if (item.warningLevel == '二级预警') {
            if (item.status == '未处理') {
              list2[0] = obj;
            } else {
              list2[1] = obj;
            }
            total2 += item.count;
          }
          if (item.warningLevel == '三级预警') {
            if (item.status == '未处理') {
              list3[0] = obj;
            } else {
              list3[1] = obj;
            }
            total3 += item.count;
          }
        });
        this.warnData.total1 = total1;
        this.warnData.value1 = list1;
        this.warnData.total2 = total2;
        this.warnData.value2 = list2;
        this.warnData.total3 = total3;
        this.warnData.value3 = list3;
        this.$nextTick(() => {
          this.$refs.monitorPie.setOption();
        });
      }
    },
    //测点预警排行选择
    async rankingClick(index) {
      this.activeRankingIndex = index;
      let { code, data } = await getWarningRank(this.structureId, index);
      if (code == '0000') {
        let xName = [];
        let list1 = [];
        let list2 = [];
        let list3 = [];
        data.map((item) => {
          xName.push(item.name);
          if (item.status == '1') {
            list1.push(item.count);
          }
          if (item.status == '2') {
            list2.push(item.count);
          }
          if (item.status == '3') {
            list3.push(item.count);
          }
        });

        this.rankData.xName = [...new Set(xName)];
        this.rankData.value1 = list1;
        this.rankData.value2 = list2;
        this.rankData.value3 = list3;
        if (list1.length == 0 && list2.length == 0 && list3.length == 0) {
          this.rankData.noData = true;
        } else {
          this.rankData.noData = false;
        }
        this.$nextTick(() => {
          if (xName.length > 0) {
            this.$refs.rankingBar.setOption();
          }
        });
      }
    },
    //预警变化选择
    async changeClick(index) {
      this.activeChangeIndex = index;
      let { code, data } = await getWarningTend(index, this.structureId);
      if (code == '0000') {
        let xName = [];
        let list1 = [];
        let list2 = [];
        let list3 = [];
        data.map((item) => {
          xName.push(item.sensorTime);
          list1.push(item.warningLevelOne);
          list2.push(item.warningLevelTwo);
          list3.push(item.warningLevelThree);
        });
        this.changeData.text = index;
        this.changeData.xName = xName;
        this.changeData.data1 = list1;
        this.changeData.data2 = list2;
        this.changeData.data3 = list3;
        this.changeData.unit = index == 1 ? '时' : index == 2 ? '日' : '月';
        this.$nextTick(() => {
          if (xName.length > 0) {
            this.$refs.changeLine.setOption();
          }
        });
      }
    },
    //查询基础信息
    async getStructureById() {
      let Info = await getStructureById(this.structureId, 1);
      if (Info.code == '0000') {
        Info.data.photoList.map((item) => {
          item.path = this.$basePath + item.path;
        });
        this.baseInformation = Info.data;
        this.imgList = Info.data.photoList;
        //获取萤石云token
        let { code, data } = await getYingshiyunAccessToken();
        if (code == '0000') {
          Info.data.videoList.map((item) => {
            item.accessToken = data;
          });
          this.videoList = Info.data.videoList;
        }
      }
    },
    //获取结构物
    async getStructureListByModel() {
      let params = { powerId: this.$store.getters.getActiveIndex };
      let { code, data } = await getStructureListByModel(params);
      if (code == '0000') {
        this.structureList = data;
        if (this.$route.params.id) {
          this.structureId = Number(this.$route.params.id);
        } else if (this.structureId == '') {
          this.structureId = data[0] ? data[0].id : '';
        }
        this.$nextTick(() => {
          if (data.length > 0) {
            this.getData();
          }
        });
      }
    },
    moreReturn(name) {
      if (name == '信息管理') {
        this.$router.push({
          path:
            this.baseInformation.structureType == 1
              ? '/infoManage/bridgeManageDetial'
              : '/infoManage/tunnelManageDetial',
          query: { id: this.structureId, name: '结构物监测' }
        });
      } else {
        this.$router.push({
          name,
          params: {
            id: this.structureId
          }
        });
      }
    },
    handleClick(tab, event) {}
  }
};
</script>
<style lang="scss" scoped>
.onBridge {
  height: 100%;
  width: 100%;
  background: transparent !important;
}

.onBridge .above {
  display: flex;
  height: 3.705vh;
  .above_select {
    width: 14.68vw;
    height: 100%;
    .above_select_opton {
      height: 100%;
      font-size: 0.729vw;
    }
    /deep/ .el-input {
      height: 100%;
    }
    /deep/ .el-input__inner {
      height: 100%;
    }
    /deep/ .el-input__suffix,
    /deep/ .el-input__suffix-inner {
      display: flex;
      align-items: center;
      justify-content: center;
    }
  }
  .above_button {
    width: 3.125vw;
    height: 100%;
    margin-left: 20px;
    font-size: 14px;
  }
  .above_button2 {
    width: 4.583333vw;
    height: 100%;
    margin-left: 20px;
    font-size: 14px;
  }
  /deep/ .el-button {
    padding: 0;
  }
}

.onBridge .middle {
  height: 49.445vh;
  display: flex;
  align-items: center;
  justify-content: space-between;
  .middle_left {
    width: 494px;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    .middle_left_top {
      width: 100%;
      height: 19.63vh;
      background: transparent;
      display: flex;
      flex-wrap: wrap;
      justify-content: space-between;
      align-content: space-between;
      .warnItem {
        width: 238px;
        height: 8.89vh;
        padding: 1.484vh 24px;
        display: flex;
        flex-direction: column;
        justify-content: space-evenly;
        .text {
          font-size: 16px;
          color: #595959;
        }
        .content {
          font-size: 32px;
          color: #333333;
          display: flex;
          align-items: center;
          justify-content: space-between;
          .txt {
            display: flex;
            align-items: center;
            span {
              display: flex;
              align-items: center;
            }
            i {
              font-size: 24px;
              padding: 0 8px;
            }
          }
          .icon {
            cursor: pointer;
          }
          .up {
            color: #ff4747;
          }
          .down {
            color: #27ae60;
            transform: rotate(180deg);
          }
          i {
            font-size: 32px;
          }
        }
      }
    }
    .middle_left_follow {
      width: 100%;
      height: 27.778vh;
      padding: 2.348vh 24px;
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      .top {
        display: flex;
        justify-content: space-between;
        .title {
          color: #262626;
          font-size: 16px;
        }
      }
      .btm {
        flex: 1;
      }
    }
  }
  .middle_middle {
    width: 832px;
    height: 100%;
    padding: 1.112vh 1.25vw 2.135vh;
    background: #fff;
    .middle_middle2_tab2 {
      height: 100%;
      width: 100%;
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
        width: 8px;
        height: 8px;
        border-radius: 50%;
        background: #e5e5e5;
        opacity: 1;
      }
      /deep/ .el-carousel__indicator.is-active button {
        background: #419aff;
      }
    }
    .middle_middle2_tab3 {
      height: 100%;
      width: 100%;
      video {
        height: 100%;
        width: 100%;
      }
      /deep/ .el-carousel {
        height: 100%;
      }
      /deep/ .el-carousel__item {
        background: transparent;
      }
      /deep/ .el-carousel__indicators {
        display: none;
      }
    }
    /deep/ .el-tabs {
      height: 100%;
      width: 100%;
      display: flex;
      flex-direction: column;
      justify-content: space-between;
    }
    /deep/ .el-tabs__header {
      height: 3.334vh;
      margin: 0;
    }
    /deep/ .el-tabs__content {
      height: 40.557vh;
    }
    /deep/ .el-tabs__nav-wrap::after {
      height: 1px;
    }
  }
  .middle_right {
    width: 496px;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    .middle_right_top {
      width: 100%;
      height: 23.334vh;
      padding: 2.223vh 24px;
      display: flex;
      flex-direction: column;
      .middle_right_top_top {
        font-size: 16px;
        color: #262626;
        display: flex;
        justify-content: space-between;
        .btn {
          color: #419aff;
          cursor: pointer;
        }
      }
      .middle_right_top_follow {
        flex: 1;
        display: flex;
        flex-direction: column;
        justify-content: space-evenly;
        .infoItem {
          font-size: 16px;
          padding: 0 32px;
          display: flex;
          // align-items: center;
          .title {
            color: #595959;
            white-space: nowrap;
          }
          .text {
            color: #333;
          }
        }
      }
    }
    .middle_right_follow {
      height: 24.26vh;
      width: 100%;
      padding: 2.223vh 24px;
      display: flex;
      flex-direction: column;
      .middle_right_follow_top {
        font-size: 16px;
        color: #262626;
        display: flex;
        justify-content: space-between;
        .btn {
          color: #419aff;
          cursor: pointer;
        }
      }
      .typeEchart {
        flex: 1;
      }
    }
  }
}

.tapItem {
  height: 3.416vh;
  display: flex;
  align-items: center;
  span {
    width: 60px;
    height: 100%;
    font-size: 14px;
    color: #000;
    border: 1px solid #d9d9d9;
    border-left: 0;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    &:first-child {
      border-left: 1px solid #d9d9d9;
    }
  }
  .activeItem {
    color: #2f80ed;
    border: 1px solid #2f80ed !important;
  }
}

.onBridge .follow {
  height: 27.778vh;
  display: flex;
  align-items: center;
  justify-content: space-between;
  .follow_left {
    width: 495px;
    height: 100%;
    padding: 2.348vh 24px;
    display: flex;
    flex-direction: column;
    .top {
      display: flex;
      justify-content: space-between;
      .title {
        color: #262626;
        font-size: 16px;
      }
    }
    .rankEchart {
      flex: 1;
    }
  }
  .follow_right {
    width: 1348px;
    height: 100%;
    padding: 2.348vh 24px;
    display: flex;
    flex-direction: column;
    .follow_right_top {
      display: flex;
      justify-content: space-between;
      .title {
        font-size: 16px;
        color: #262626;
      }
      .right {
        display: flex;
        align-items: center;
        .btn {
          font-size: 16px;
          color: #419aff;
          margin-left: 36px;
          cursor: pointer;
        }
      }
    }
    .follow_right_ec {
      flex: 1;
    }
  }
}

//无数据样式
.noData {
  width: 100%;
  height: 100%;
  color: #333;
  font-size: 28px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.onBridge {
  box-shadow: none;
  width: 93.3333vw;
  background: transparent;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
</style>
