<template>
  <div class="home">
    <div class="homeLeft">
      <div class="itemBox animate__animated animate__bounceInDown">
        <div class="itemTop">
          <span class="title">基础情况</span>
        </div>
        <div class="basics">
          <div class="basicsItem">
            <div class="title">项目数</div>
            <div class="numBox">
              <span class="num">{{ projectNum }}</span
              ><span>个</span>
            </div>
            <div v-if="projectChangeNum >= 0" class="text">
              <span>较去年 +{{ projectChangeNum }}</span
              ><span v-if="projectChangeNum != 0" class="up"
                ><i class="el-icon-top"></i
              ></span>
            </div>
            <div v-else class="text">
              <span>较去年 {{ projectChangeNum }}</span>
              <div class="down">
                <i class="el-icon-top"></i>
              </div>
            </div>
          </div>
          <div class="basicsItem">
            <div class="title">桥梁数</div>
            <div class="numBox">
              <span class="num">{{ bridgeNum }}</span
              ><span>座</span>
            </div>
            <div v-if="bridgeChangeNum >= 0" class="text">
              <span>较去年 +{{ bridgeChangeNum }}</span
              ><span v-if="bridgeChangeNum != 0" class="up"
                ><i class="el-icon-top"></i
              ></span>
            </div>
            <div v-else class="text">
              <span>较去年 {{ bridgeChangeNum }}</span
              ><span class="down"><i class="el-icon-top"></i></span>
            </div>
          </div>
          <div class="basicsItem">
            <div class="title">隧道数</div>
            <div class="numBox">
              <span class="num">{{ tunnelNum }}</span
              ><span>座</span>
            </div>
            <div v-if="tunnelChangeNum >= 0" class="text">
              <span>较去年 +{{ tunnelChangeNum }}</span
              ><span v-if="tunnelChangeNum != 0" class="up"
                ><i class="el-icon-top"></i
              ></span>
            </div>
            <div v-else class="text">
              <span>较去年 {{ tunnelChangeNum }}</span
              ><span class="down"><i class="el-icon-top"></i></span>
            </div>
          </div>
        </div>
      </div>
      <div class="itemBox animate__animated animate__bounceInLeft">
        <div class="itemTop">
          <span class="title">桥隧概况</span>
          <div class="condition">
            <div
              :class="{ activeCondition: surveyIndex == 1 }"
              @click="changeSurvey(1, 'sortByType')"
            >
              按分类
            </div>
            <div
              :class="{ activeCondition: surveyIndex == 2 }"
              @click="changeSurvey(2, 'sortByBridgeType')"
            >
              按桥型
            </div>
            <div
              :class="{ activeCondition: surveyIndex == 3 }"
              @click="changeSurvey(3, 'sortByArea')"
            >
              按区域
            </div>
          </div>
        </div>
        <div class="survey">
          <SurveyBar
            v-if="surveryData.data.length > 0"
            ref="surveyEchart"
            :echartData="surveryData"
          ></SurveyBar>
          <div v-else class="empty">暂无数据</div>
        </div>
      </div>
      <div class="itemBox animate__animated animate__bounceInLeft">
        <div class="itemTop">
          <span class="title">公路桥隧技术状况</span>
          <div class="condition">
            <div
              :class="{ activeCondition: roadTechnologyIndex == 1 }"
              @click="changeCityTechnology(1)"
            >
              桥梁
            </div>
            <div
              :class="{ activeCondition: roadTechnologyIndex == 2 }"
              @click="changeCityTechnology(2)"
            >
              隧道
            </div>
          </div>
        </div>
        <div class="roadTechnology">
          <div v-for="item in roadList" :key="item.name" class="transItem">
            <div class="transTop">
              <span class="text">{{ item.name }}</span
              ><span class="value">{{ item.count }}</span>
            </div>
            <div class="transBtm">
              <div
                :style="{
                  width:
                    tecMaxValue > 0 ? (item.count / tecMaxValue) * 100 + '%' : 0
                }"
              ></div>
            </div>
          </div>
        </div>
      </div>
      <div class="itemBox animate__animated animate__bounceInUp">
        <div class="itemTop">
          <span class="title">城市桥隧技术状况</span>
          <div class="condition">
            <div
              :class="{ activeCondition: cityTechnologyIndex == 1 }"
              @click="changeCityTechnology(1)"
            >
              桥梁
            </div>
            <div
              :class="{ activeCondition: cityTechnologyIndex == 2 }"
              @click="changeCityTechnology(2)"
            >
              隧道
            </div>
          </div>
        </div>
        <div class="cityTechnology">
          <div class="cityTechnologyBox">
            <div ref="transBox" class="transBox">
              <div v-for="item in cityList" :key="item.name" class="transItem">
                <div class="transTop">
                  <span class="text">{{ item.name }}</span
                  ><span class="value">{{ item.count }}</span>
                </div>
                <div class="transBtm">
                  <div
                    :style="{
                      width:
                        tecMaxValue > 0
                          ? (item.count / tecMaxValue) * 100 + '%'
                          : 0
                    }"
                  ></div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="homeMid animate__animated animate__fadeIn animate__delay-1s">
      <Map ref="bMap" :structureList="structureList"></Map>
      <div class="selectItem">
        <div class="selectArea">
          <el-cascader
            ref="cascaderHandle"
            v-model="areaValue"
            :options="areaList"
            placeholder="全部地区"
            :props="areaProps"
            :show-all-levels="false"
            popper-class="cascaderArea"
            clearable
            @change="selectChange('area')"
          ></el-cascader>
        </div>
        <div v-if="roleId != 2" class="selectNormal">
          <el-select
            v-model="userId"
            :popper-append-to-body="false"
            placeholder="全部业主"
            @change="selectChange"
            clearable
            filterable
          >
            <el-option
              v-for="item in userList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </div>
        <div v-if="roleId != 1 && roleId != 0" class="selectNormal">
          <el-select
            v-model="undertakeId"
            :popper-append-to-body="false"
            placeholder="全部承接单位"
            @change="selectChange"
            clearable
            filterable
          >
            <el-option
              v-for="item in undertakeList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </div>
        <div class="selectNormal">
          <el-select
            v-model="projectId"
            :popper-append-to-body="false"
            placeholder="全部项目"
            @change="selectChange"
            clearable
            filterable
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
        <div class="selectNormal">
          <el-select
            v-model="structureId"
            :popper-append-to-body="false"
            placeholder="选择结构物"
            @change="selectStructure"
            clearable
            filterable
          >
            <el-option
              v-for="item in structureList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </div>
      </div>
      <div class="checkItem">
        <div class="checkRow">
          <div class="checkLeft">
            <el-checkbox v-model="checked1" @change="checkCondition"
              >监测</el-checkbox
            >
          </div>
          <div class="checkRight">
            <span class="num">{{ checkDataList[0].length }}</span
            ><span class="text">座</span>
          </div>
        </div>
        <div class="checkRow">
          <div class="checkLeft">
            <el-checkbox v-model="checked2" @change="checkCondition"
              >巡查</el-checkbox
            >
          </div>
          <div class="checkRight">
            <span class="num">{{ checkDataList[1].length }}</span
            ><span class="text">座</span>
          </div>
        </div>
        <div class="checkRow">
          <div class="checkLeft">
            <el-checkbox v-model="checked3" @change="checkCondition"
              >维养</el-checkbox
            >
          </div>
          <div class="checkRight">
            <span class="num">{{ checkDataList[2].length }}</span
            ><span class="text">座</span>
          </div>
        </div>
        <div class="checkRow">
          <div class="checkLeft">
            <el-checkbox v-model="checked4" @change="checkCondition"
              >检测</el-checkbox
            >
          </div>
          <div class="checkRight">
            <span class="num">{{ checkDataList[3].length }}</span
            ><span class="text">座</span>
          </div>
        </div>
      </div>
    </div>
    <div class="homeRight">
      <div class="itemBox animate__animated animate__bounceInDown">
        <div class="itemTop">
          <span class="title">桥隧综合评价指数</span>
        </div>
        <div class="maintain">
          <div class="maintainBox">
            <maintainRadar
              ref="maintainEchart"
              :echartData="maintainData"
            ></maintainRadar>
          </div>
          <div class="maintainValue">{{ maintainData.total }}分</div>
        </div>
      </div>
      <div class="itemBox animate__animated animate__bounceInRight">
        <div class="itemTop">
          <span class="title">预警统计</span>
          <div class="condition">
            <div
              :class="{ activeCondition: warningIndex == 1 }"
              @click="changeWarning(1)"
            >
              今日
            </div>
            <div
              :class="{ activeCondition: warningIndex == 2 }"
              @click="changeWarning(2)"
            >
              本月
            </div>
            <div
              :class="{ activeCondition: warningIndex == 3 }"
              @click="changeWarning(3)"
            >
              今年
            </div>
            <div
              :class="{ activeCondition: warningIndex == 0 }"
              @click="changeWarning(0)"
            >
              全部
            </div>
          </div>
        </div>
        <div class="warning">
          <div class="warningTabs">
            <div class="tabItem">
              <div class="tabTop">
                <span class="stateBlue"></span><span>已处理</span>
              </div>
              <div class="tabBtm">{{ warningData.data[0].value }}</div>
            </div>
            <div class="tabItem">
              <div class="tabTop">
                <span class="stateYellow"></span><span>未处理</span>
              </div>
              <div class="tabBtm" style="color: rgba(255, 129, 0, 0.9)">
                {{ warningData.data[1].value }}
              </div>
            </div>
          </div>
          <div class="warningBox">
            <warningPie
              ref="warningEchart"
              :echartData="warningData"
            ></warningPie>
          </div>
        </div>
      </div>
      <div class="itemBox animate__animated animate__bounceInUp">
        <div class="itemTop">
          <span class="title">维养概况</span>
          <div class="condition">
            <div
              :class="{ activeCondition: maintenanceIndex == 1 }"
              @click="changeMaintenance(1)"
            >
              今日
            </div>
            <div
              :class="{ activeCondition: maintenanceIndex == 2 }"
              @click="changeMaintenance(2)"
            >
              本月
            </div>
            <div
              :class="{ activeCondition: maintenanceIndex == 3 }"
              @click="changeMaintenance(3)"
            >
              今年
            </div>
            <div
              :class="{ activeCondition: maintenanceIndex == 0 }"
              @click="changeMaintenance(0)"
            >
              全部
            </div>
          </div>
        </div>
        <div class="maintenance">
          <div class="maintenanceItem">
            <div class="maintenanceBox">
              <maintenanceLiquid
                ref="maintenanceXCEchart"
                :echartData="maintainDataXC"
              ></maintenanceLiquid>
            </div>
            <div class="maintenanceInfo">
              <div class="infoItem">
                <span class="text">已完成</span
                ><span class="num">{{ maintainDataXC.deal }}</span>
              </div>
              <div class="infoItem">
                <span class="text">未完成</span
                ><span class="num" style="color: #ff8100">{{
                  maintainDataXC.undeal
                }}</span>
              </div>
            </div>
          </div>
          <div class="maintenanceItem">
            <div class="maintenanceBox">
              <maintenanceLiquid
                ref="maintenanceWYEchart"
                :echartData="maintainDataWY"
              ></maintenanceLiquid>
            </div>
            <div class="maintenanceInfo">
              <div class="infoItem">
                <span class="text">已完成</span
                ><span class="num">{{ maintainDataWY.deal }}</span>
              </div>
              <div class="infoItem">
                <span class="text">未完成</span
                ><span class="num" style="color: #03a9f2">{{
                  maintainDataWY.undeal
                }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {
  getBridgeList,
  getBaseInfo,
  getWarningInfo,
  getInspMainInfo,
  getComprehensive
} from '@/api/home/home';
import { getProjectList, getListByRoleHome, getArea } from '@/api/common';
import Map from '@/components/map/map';
import SurveyBar from './components/surveyBar';
import maintainRadar from './components/maintainRadar';
import warningPie from './components/warningPie';
import maintenanceLiquid from './components/maintenanceLiquid';
export default {
  name: 'Home-dzl',
  components: {
    Map,
    SurveyBar,
    maintainRadar,
    warningPie,
    maintenanceLiquid
  },
  props: {},
  data() {
    return {
      roleId: this.$store.getters.getRoleInfo.id,
      //查询条件筛选
      userId: '',
      undertakeId: '',
      areaValue: [],
      areaList: [],
      areaProps: {
        value: 'id',
        label: 'name',
        checkStrictly: true
      }, //所属区域动态加载
      userList: [],
      undertakeList: [],
      projectList: [],
      projectId: '',
      structureList: [], //  结构物列表
      structureId: '',
      //四类条件选择
      checked1: true,
      checked2: true,
      checked3: true,
      checked4: true,
      checkDataList: [[], [], [], []],
      //基础情况
      projectNum: 0,
      projectChangeNum: 0,
      bridgeNum: 0,
      bridgeChangeNum: 0,
      tunnelNum: 0,
      tunnelChangeNum: 0,
      //各模块类型选择
      surveyIndex: 1,
      roadTechnologyIndex: 1,
      cityTechnologyIndex: 1,
      warningIndex: 2,
      maintenanceIndex: 2,
      //桥隧概况数据
      surveryObj: {
        sortByType: [],
        sortByBridgeType: [],
        sortByArea: []
      },
      surveryData: {
        xData: [],
        data: []
      },
      //公路桥隧数据
      roadList: [],
      //城市桥隧数据
      cityList: [],
      //桥梁、隧道技术状况对象
      technicalObj: {
        bridgeCityTechnology: [],
        bridgeRoadTechnology: [],
        tunnelCityTechnology: [],
        tunnelRoadTechnology: []
      },
      tecMaxValue: 0,
      //桥隧综合评价指数数据
      maintainData: {
        total: 0,
        indicator: [
          {
            name: '技术状况（BCI）',
            max: 100,
            color: 'rgba(255,255,255,0.85)'
          },
          { name: '预警处理率', max: 100, color: 'rgba(255,255,255,0.85)' },
          { name: '巡查完成率', max: 100, color: 'rgba(255,255,255,0.85)' },
          { name: '维养完成率', max: 100, color: 'rgba(255,255,255,0.85)' },
          { name: '检测完成率', max: 100, color: 'rgba(255,255,255,0.85)' }
        ],
        data: [{ name: '桥隧综合评价指数', value: [] }]
      },
      //预警统计数据
      warningData: {
        total: 0,
        data: [
          {
            name: '已处理',
            value: 0
          },
          {
            name: '未处理',
            value: 0
          }
        ]
      },
      //维养概况数据
      maintainDataXC: {
        bgColor: 'rgba(255,129,0,0.3)',
        color: '#ff8100',
        percent: 83,
        deal: 10,
        undeal: 2,
        text: '巡查计划',
        data: [0.83]
      },
      maintainDataWY: {
        bgColor: 'rgba(3,169,242,0.3)',
        color: '#03a9f2',
        percent: 78,
        deal: 7,
        undeal: 2,
        text: '维养计划',
        data: [0.78]
      }
    };
  },
  mounted() {
    this.getBaseInfo(); //获取基础情况(左侧)
    this.getBridgeList(); //获取地图桥隧列表
    this.getProjectList(); //获取项目列表
    this.getWarningInfo(); //获取预警统计
    this.getInspMainInfo(); //获取巡查维养信息
    this.getComprehensive(); //获取桥隧综合评价指数
    this.getArea(); //获取区域
    this.getListByRoleHome(2); //获取业主
    this.getListByRoleHome(1); //承接单位列表
    if (this.roleId != 2) {
    }
    if (this.technicalObj.bridgeCityTechnology.length > 0) {
      this.rankTime();
    }
  },
  methods: {
    //获取地图桥隧列表
    async getBridgeList() {
      let params = {
        provinceId: this.areaValue[0] || '',
        cityId: this.areaValue[1] || '',
        countyId: this.areaValue[2] || '',
        unitId: this.userId,
        undertakeId: this.undertakeId,
        projectId: this.projectId
      };
      let { code, data } = await getBridgeList(params);
      if (code == '0000') {
        this.structureId = '';
        data.structureList.map((item) => {
          item.lnglat = [item.longitude, item.latitude];
        });
        this.getStructureList = data.structureList;
        this.checkDataList = [
          data.onlineList,
          data.inspectionList,
          data.maintainList,
          data.evaluationList
        ];
        this.$nextTick(() => {
          this.checkCondition();
          this.$refs.bMap.loadList(data.structureList);
        });
      }
    },
    //获取项目列表
    async getProjectList() {
      let { code, data } = await getProjectList();
      if (code == '0000') {
        this.projectList = data;
      }
    },
    //获取区域
    async getArea() {
      let { code, data } = await getArea();
      if (code == '0000') {
        this.areaList = data;
      }
    },
    //获取业主、承接单位列表
    async getListByRoleHome(index) {
      if (index == this.roleId) {
        return;
      }
      let { code, data } = await getListByRoleHome(index);
      if (code == '0000') {
        if (index == 2) {
          this.userList = data;
        } else {
          this.undertakeList = data;
        }
      }
    },
    //获取基础情况(左侧)
    async getBaseInfo() {
      let { code, data } = await getBaseInfo();
      if (code == '0000') {
        //基础情况
        this.projectNum = data.projectNum || 0;
        this.projectChangeNum = data.projectChangeNum || 0;
        this.bridgeNum = data.bridgeNum || 0;
        this.bridgeChangeNum = data.bridgeChangeNum || 0;
        this.tunnelNum = data.tunnelNum || 0;
        this.tunnelChangeNum = data.tunnelChangeNum || 0;
        //桥隧概况对象
        this.surveryObj = {
          sortByType: data.sortByType || [],
          sortByBridgeType: data.sortByBridgeType || [],
          sortByArea: data.sortByArea || []
        };
        //公路、城市桥隧技术状况对象
        this.technicalObj = {
          bridgeCityTechnology: data.bridgeCityTechnology || [],
          bridgeRoadTechnology: data.bridgeRoadTechnology || [],
          tunnelCityTechnology: data.tunnelCityTechnology || [],
          tunnelRoadTechnology: data.tunnelRoadTechnology || []
        };
        this.$nextTick(() => {
          this.changeSurvey(1, 'sortByType');
          this.changeCityTechnology(1);
        });
      }
    },
    //获取预警统计
    async getWarningInfo() {
      let { code, data } = await getWarningInfo(this.warningIndex);
      if (code == '0000') {
        let val1 = 0;
        let val2 = 0;
        data.map((item) => {
          if (item.status == '已处理') val1 += item.count;
          else val2 += item.count;
        });
        this.warningData.data[0].value = val1;
        this.warningData.data[1].value = val2;
        this.warningData.total = val1 + val2;
        this.$nextTick(() => {
          this.$refs.warningEchart.setOption();
        });
      }
    },
    //获取巡查维养信息
    async getInspMainInfo() {
      let { code, data } = await getInspMainInfo(this.maintenanceIndex);
      if (code == '0000') {
        //巡查计划
        this.maintainDataXC.deal = data.inspectionFinish;
        this.maintainDataXC.undeal = data.inspectionIncomplete;
        let percentDotXC =
          Math.round(
            (data.inspectionFinish /
              (data.inspectionFinish + data.inspectionIncomplete)) *
              100
          ) || 0;
        this.maintainDataXC.data = [percentDotXC / 100];
        this.maintainDataXC.percent = percentDotXC;
        //维养计划
        this.maintainDataWY.deal = data.maintainFinish;
        this.maintainDataWY.undeal = data.maintainIncomplete;
        let percentDotWY =
          Math.round(
            (data.maintainFinish /
              (data.maintainFinish + data.maintainIncomplete)) *
              100
          ) || 0;
        this.maintainDataWY.data = [percentDotWY / 100];
        this.maintainDataWY.percent = percentDotWY;
        this.$nextTick(() => {
          this.$refs.maintenanceXCEchart.setOption();
          this.$refs.maintenanceWYEchart.setOption();
        });
      }
    },
    //获取桥隧综合评价指数
    async getComprehensive() {
      let { code, data } = await getComprehensive();
      if (code == '0000') {
        let arry = data.concat();
        let length = 1;
        let avgNum = 0;
        if (arry.length > 0) {
          avgNum = arry.reduce((num, item, index) => {
            if (item != null) {
              length++;
              if (index != arry.length - 1) {
                return num + item;
              } else {
                return (num + item) / length;
              }
            } else {
              if (index != arry.length - 1) {
                return num;
              } else {
                return num / length;
              }
            }
          });
        }
        this.maintainData.total = Math.round(avgNum);
        this.maintainData.data[0].value = data;
        this.$nextTick(() => {
          this.$refs.maintainEchart.setOption();
        });
      }
    },
    //桥隧概况切换
    changeSurvey(index, name) {
      this.surveyIndex = index;
      let arry = this.surveryObj[name];
      let xData = [];
      let data = [];
      arry.map((item) => {
        if (item.count > 0) {
          xData.push(item.name);
          data.push(item.count);
        }
      });
      this.surveryData.xData = xData;
      this.surveryData.data = data;
      this.$nextTick(() => {
        if (this.$refs.surveyEchart) {
          this.$refs.surveyEchart.setOption();
        }
      });
    },
    //公路桥隧技术切换
    changeRoadTechnology() {},
    //城市、公路桥隧技术切换
    changeCityTechnology(index) {
      if (this.cityTimer && this.cityTimer != null) {
        clearInterval(this.cityTimer);
        this.cityTimer = null;
        if (this.$refs.transBox) {
          this.$refs.transBox.className = 'transBox';
        }
      }
      this.roadTechnologyIndex = index;
      this.cityTechnologyIndex = index;
      this.roadList = [];
      this.cityList = [];
      if (index == 1) {
        this.roadList = this.technicalObj.bridgeRoadTechnology.concat();
        this.cityList = this.technicalObj.bridgeCityTechnology.concat();
      } else {
        this.roadList = this.technicalObj.tunnelRoadTechnology.concat();
        this.cityList = this.technicalObj.tunnelCityTechnology.concat();
      }
      let newArry = [...this.roadList, ...this.cityList];
      this.tecMaxValue = Math.max(...newArry.map((item) => item.count));
      if (this.cityList.length > 5) {
        this.rankTime();
      }
    },
    //预警统计切换
    changeWarning(index) {
      this.warningIndex = index;
      this.getWarningInfo();
    },
    //维养概况切换
    changeMaintenance(index) {
      this.maintenanceIndex = index;
      this.getInspMainInfo();
    },
    //地图条件筛选
    selectChange(name) {
      if (name == 'area') {
        this.$refs.cascaderHandle.dropDownVisible = false; //监听值发生变化就关闭它
        if (this.areaValue.length > 1) {
          let cityName = this.getCityName(this.areaList, this.areaValue, 0); //获取选中城市名
          let level = this.areaValue.length == 2 ? 10 : 12;
          this.$refs.bMap.cityGo(cityName, level);
        } else {
          this.$refs.bMap.cityBack();
        }
      }
      this.$nextTick(async () => {
        await this.getBridgeList();
      });
    },
    //获取城市名
    getCityName(arry, valList, i) {
      let cityName = '';
      arry.map((item) => {
        if (item.id == valList[i]) {
          cityName = item.name;
          if (valList[i + 1]) {
            let index = i + 1;
            cityName = this.getCityName(item.children, valList, index);
          }
        }
      });
      return cityName;
    },
    //选择结构物
    selectStructure(id) {
      if (id == '') {
        return;
      }
      let cityList = [];
      let cityName = '';
      let obj = '';
      this.structureList.map((item) => {
        if (item.id == id) {
          obj = item;
          cityList = [item.provinceId, item.cityId, item.countyId];
          cityName = this.getCityName(this.areaList, cityList, 0); //获取选中城市名
        }
      });
      this.$nextTick(() => {
        this.$refs.bMap.cityGo(cityName);
        this.$refs.bMap.onMarkerClick(obj);
      });
    },
    //地图四类选择筛选
    checkCondition() {
      //获取勾选结果
      let checkList = [
        this.checked1,
        this.checked2,
        this.checked3,
        this.checked4
      ];
      //获取不展示桥隧id
      let disList = [];
      let showList = [];
      checkList.map((state, i) => {
        if (state) showList = showList.concat(this.checkDataList[i]);
        else disList = disList.concat(this.checkDataList[i]);
      });
      disList = [...new Set(disList)];
      showList = [...new Set(showList)];
      disList.map((id, i) => {
        if (showList.includes(id)) disList.splice(i, 1);
      });
      //筛选出地图上展示的列表
      let arry = JSON.parse(JSON.stringify(this.getStructureList));
      disList.map((noId) => {
        arry.map((item, i) => {
          if (item.id === noId) {
            arry.splice(i, 1);
          }
        });
      });
      this.$nextTick(() => {
        this.structureList = arry;
        // this.$refs.bMap.mapDataList = arry;
      });
    },
    //城市技术状况轮播
    rankTime() {
      // cityRemove
      this.cityTimer = setInterval(() => {
        this.$refs.transBox.className = 'transBox cityRemove';
        setTimeout(() => {
          if (this.cityTimer != null) {
            if (this.$refs.transBox) {
              this.$refs.transBox.className = 'transBox';
            }
            let arry = this.cityList[0];
            this.cityList.splice(0, 1);
            this.cityList.push(arry);
          }
        }, 2000);
      }, 3000);
    }
  },
  beforeDestroy() {
    if (this.cityTimer && this.cityTimer != null) {
      clearInterval(this.cityTimer);
    }
  },
  beforeRouteEnter(to, from, next) {
    //判断是否从模型页返回
    if (from.name === 'bridgeModel') {
      next((vm) => {
        vm.$store.dispatch('asyncUpdateActiveIndex', 1);
      });
    } else {
      next();
    }
  }
};
</script>
<style lang="scss" scoped>
.home {
  background: #020512;
  background-image: linear-gradient(#060c1b 1px, transparent 0),
    linear-gradient(90deg, #060c1b 1px, transparent 0),
    linear-gradient(#151827 2px, transparent 0),
    linear-gradient(90deg, #151827 2px, transparent 0);
  background-size: 40px 40px, 40px 40px, 80px 80px, 80px 80px;
  padding: 1.484vh 16px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  .empty {
    height: 100%;
    width: 100%;
    color: rgba(255, 255, 255, 0.6);
    font-size: 30px;
    display: flex;
    align-items: center;
    justify-content: center;
  }
}
.homeLeft,
.homeRight {
  width: 408px;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
.homeLeft {
  .basics {
    height: 10.752vh;
    padding: 1.2vh 12px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    .basicsItem {
      height: 100%;
      width: 120px;
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      .title {
        font-size: 12px;
        color: #fff;
      }
      .numBox {
        height: 3.708vh;
        padding: 0 11px;
        font-weight: bold;
        background-color: #040c1e;
        border: solid 1px #2f4970;
        padding-bottom: 0.742vh;
        display: flex;
        align-items: baseline;
        .num {
          font-size: 26px;
          color: #00bdff;
          padding-right: 9px;
        }
        span {
          font-size: 14px;
          color: rgba(255, 255, 255, 0.6);
        }
      }
      .text {
        font-size: 14px;
        padding: 0 11px;
        color: rgba(255, 255, 255, 0.85);
        display: flex;
        align-items: center;
        justify-content: space-between;
        span {
          white-space: nowrap;
        }
        .up {
          color: #00bdff;
          i {
            font-weight: bold;
          }
        }
        .down {
          color: #ff0f0f;
          transform: rotate(180deg);
          i {
            font-weight: bold;
          }
        }
      }
    }
  }
  .survey {
    height: 19.093vh;
  }
  .roadTechnology {
    // height: 20.502vh;
    height: 20.4vh;
    padding: 1.708vh 24px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
  }
  .cityTechnology {
    height: 21.502vh;
    padding: 1.708vh 24px;
    // display: flex;
    // flex-direction: column;
    // justify-content: space-between;
    .cityTechnologyBox {
      height: 100%;
      width: 100%;
      overflow: auto;
      .transBox {
        position: relative;
        height: 100%;
        width: 100%;
        opacity: 1;
      }
    }
  }
  .transItem {
    height: 3.62vh;
    width: 100%;
    display: flex;
    flex-direction: column;
    justify-content: space-evenly;
    .transTop {
      width: 100%;
      display: flex;
      justify-content: space-between;
      .text {
        font-size: 14px;
        color: rgba(255, 255, 255, 0.6);
      }
      .value {
        font-size: 16px;
        color: #fafafb;
      }
    }
    .transBtm {
      width: 100%;
      height: 8px;
      background-color: rgba(255, 255, 255, 0.16);
      border-radius: 4px;
      div {
        width: 0;
        height: 100%;
        background-image: linear-gradient(90deg, #03a9f2 0%, #6eeeff 100%);
        border-radius: 4px;
        transition: width 1s;
      }
    }
  }
}
.homeRight {
  .maintain {
    height: 27.619vh;
    padding: 1.708vh 0;
    display: flex;
    align-items: center;
    flex-direction: column;
    .maintainBox {
      width: 100%;
      flex: 1;
    }
    .maintainValue {
      width: 120px;
      height: 3.337vh;
      font-weight: bold;
      font-size: 26px;
      color: #00acea;
      background-color: #050f26;
      border: solid 1px #2f4970;
      display: flex;
      align-items: center;
      justify-content: center;
    }
  }
  .warning {
    height: 23.541vh;
    padding-top: 1.5vh;
    display: flex;
    align-items: center;
    flex-direction: column;
    .warningTabs {
      width: 336px;
      height: 5.098vh;
      display: flex;
      justify-content: space-between;
      .tabItem {
        width: 160px;
        height: 100%;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        .tabTop {
          color: #fff;
          font-size: 12px;
          display: flex;
          align-items: center;
          .stateBlue {
            width: 10px;
            height: 10px;
            background: #03a9f2;
            margin-right: 4px;
          }
          .stateYellow {
            width: 10px;
            height: 10px;
            background: #ee7902;
            margin-right: 4px;
          }
        }
        .tabBtm {
          width: 100%;
          height: 3.337vh;
          line-height: 3.337vh;
          text-align: center;
          background-color: #050f26;
          border: solid 1px #2f4970;
          color: rgba(0, 189, 255, 0.9);
          font-size: 26px;
          font-weight: bold;
        }
      }
    }
    .warningBox {
      width: 100%;
      flex: 1;
    }
  }
  .maintenance {
    height: 26.507vh;
    padding: 1.708vh 40px 1.708vh 0;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    .maintenanceItem {
      width: 100%;
      height: 10.195vh;
      display: flex;
      align-items: center;
      justify-content: space-between;
      .maintenanceBox {
        height: 100%;
        flex: 1;
      }
      .maintenanceInfo {
        width: 140px;
        height: 100%;
        display: flex;
        flex-direction: column;
        justify-content: space-around;
        .infoItem {
          width: 100%;
          height: 3.337vh;
          display: flex;
          align-items: center;
          justify-content: space-between;
          .text {
            font-size: 12px;
            color: rgba(255, 255, 255, 0.85);
          }
          .num {
            width: 94px;
            height: 3.337vh;
            line-height: 3.337vh;
            text-align: center;
            background-color: #050f26;
            border: solid 1px #2f4970;
            font-size: 26px;
            color: #fff;
          }
        }
      }
    }
  }
}
.homeMid {
  position: relative;
  width: 1040px;
  height: 100%;
  z-index: 10;
  .selectItem {
    position: absolute;
    top: 1.484vh;
    right: 0;
    height: 3.708vh;
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: flex-end;
    .selectArea {
      width: 120px;
      height: 100%;
      margin-right: 20px;
    }
    .selectNormal {
      width: 200px;
      height: 100%;
      margin-right: 20px;
    }
    /deep/ .el-select {
      height: 100%;
      width: 100%;
    }
    /deep/ .el-input__inner {
      color: #fff;
      background: #1a3d67;
      border: 0;
      &::placeholder {
        color: rgba(255, 255, 255, 0.6);
      }
      &:focus {
        border: solid 1px #03a9f2;
      }
    }
    /deep/ .el-select-dropdown {
      max-width: 250px;
      background: #1a3d67;
      border: solid 1px #1a3d67;
      box-shadow: 0px 2px 16px 0px rgba(15, 15, 15, 0.36);
    }
    /deep/ .el-select-dropdown__list {
      background: #1a3d67;
    }
    /deep/ .el-popper .popper__arrow {
      border-bottom-color: #1a3d67;
      &::after {
        border-bottom-color: #1a3d67;
      }
    }
    /deep/ .el-select-dropdown__item {
      color: rgba(255, 255, 255, 0.6);
      background-color: #1a3d67;
      &:hover {
        color: #fff;
        background: #03a9f2;
      }
    }
    /deep/ .el-select-dropdown__empty {
      color: rgba(255, 255, 255, 0.6);
      background-color: #1a3d67;
    }
  }
  .checkItem {
    position: absolute;
    right: 12px;
    bottom: 1.484vh;
    width: 148px;
    height: 13.717vh;
    padding: 0 12px;
    background-color: #17365c;
    border-radius: 4px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    .checkRow {
      height: 25%;
      border-bottom: 1px solid #2f4970;
      display: flex;
      justify-content: space-between;
      .checkLeft {
        display: flex;
        align-items: center;
        /deep/ .el-checkbox__label {
          color: rgba(255, 255, 255, 0.85);
        }
      }
      .checkRight {
        display: flex;
        align-items: center;
        .num {
          font-size: 20px;
          color: #03a9f2;
        }
        .text {
          font-size: 14px;
          color: rgba(255, 255, 255, 0.6);
          padding-left: 8px;
        }
      }
      &:last-child {
        border: none;
      }
    }
  }
}
//首页模块公用样式
.itemBox {
  width: 100%;
  border: solid 1px #42669d;
  display: flex;
  flex-direction: column;
  .itemTop {
    height: 3.337vh;
    background-color: #001d3a;
    border-bottom: solid 1px #42669d;
    padding: 0 12px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    .title {
      font-size: 20px;
      color: #fff;
      font-weight: bold;
    }
    .condition {
      // border: solid 1px #03a9f2;
      display: flex;
      align-items: center;
      div {
        width: 52px;
        height: 2.225vh;
        font-size: 12px;
        color: rgba(255, 255, 255, 0.6);
        background-color: #002f5e;
        border: solid 1px #03a9f2;
        white-space: nowrap;
        display: flex;
        align-items: center;
        justify-content: center;
        cursor: pointer;
        &:last-child {
          border-left: 0;
        }
      }
      .activeCondition {
        color: #fff;
        background-color: #03a9f2;
      }
    }
  }
}
.cityRemove {
  animation: cityOut 1s linear forwards;
  .transItem:first-child {
    opacity: 0;
    transition: opacity 1s;
  }
}

//城市技术状况轮播动画
@keyframes cityOut {
  from {
    top: 0;
  }
  to {
    top: -3.62vh;
  }
}
</style>
<style lang="scss">
/* 地区选择联级下拉样式 */
.cascaderArea {
  border: solid 1px #1a3d67;
  box-shadow: 0px 2px 16px 0px rgba(15, 15, 15, 0.36);
  .el-cascader-menu {
    border-right: 0;
    &:nth-child(n + 2) {
      border-left: solid 1px #419aff;
    }
  }
  .el-cascader-menu__list {
    background: #1a3d67;
  }
  .popper__arrow {
    border-bottom-color: #1a3d67 !important;
    &::after {
      border-bottom-color: #1a3d67 !important;
    }
  }
  .el-cascader-node__label {
    font-weight: normal;
  }
  .el-cascader-node {
    color: rgba(255, 255, 255, 0.6);
    background: #1a3d67;
    .el-radio__inner {
      border: 1px solid #419aff;
      background: transparent;
      &::after {
        width: 8px;
        height: 8px;
        background: #419aff;
      }
    }
    &:hover {
      color: #fff;
      background: #419aff;
      .el-radio__inner {
        border: 1px solid #fff;
        background: transparent;
        &::after {
          width: 8px;
          height: 8px;
          background: #fff;
        }
      }
    }
  }
  .el-cascader-node:not(.is-disabled):focus {
    color: rgba(255, 255, 255, 0.6);
    background: #1a3d67;
    .el-radio__inner {
      border: 1px solid #419aff;
      background: transparent;
      &::after {
        width: 8px;
        height: 8px;
        background: #419aff;
      }
    }
    &:hover {
      color: #fff !important;
      background: #419aff;
      .el-radio__inner {
        border: 1px solid #fff;
        background: transparent;
        &::after {
          width: 8px;
          height: 8px;
          background: #fff;
        }
      }
    }
  }
  .el-cascader-node.is-selectable.in-active-path {
    color: rgba(255, 255, 255, 0.6);
    &:hover {
      color: #fff !important;
    }
  }
  .el-cascader-node.in-active-path,
  .el-cascader-node.is-active,
  .el-cascader-node.is-selectable.in-checked-path {
    color: rgba(255, 255, 255, 0.6);
  }
}
</style>
