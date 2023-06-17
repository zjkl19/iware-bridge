<template>
  <div class="model">
    <!-- 模型 -->
    <iframe
      ref="bridgeModel"
      :src="'static/bridgeModel.html?id=' + structureId"
      frameborder="0"
      style="width: 100%; height: 100%"
    ></iframe>
    <!-- <model ref="bridgeModel"></model> -->
    <!-- 头部 -->
    <div class="modelBack">
      <div class="back" @click="routerBack">
        <img src="@/assets/images/home/icon_back.png" alt="" />
        <span class="textGraGreen">返回</span>
      </div>
      <div class="textGraGreen">{{ bridgeName }}</div>
    </div>
    <div class="Tooltips">
      <span class="textGraGreen">{{ time }} 天气：{{ weather }}</span>
      <div
        class="textGraGreen toolClose"
        @click="showDoubleSide = !showDoubleSide"
      >
        <img
          v-if="showDoubleSide"
          src="@/assets/images/home/icon_close.png"
          alt=""
        />
        <img v-else src="@/assets/images/home/icon_open.png" alt="" />
        <span>{{ showDoubleSide ? '侧边收起' : '侧边展开' }}</span>
      </div>
    </div>
    <!-- 左侧 -->
    <div class="modelLeft" :class="{ hideDoubleSide: !showDoubleSide }">
      <div class="modelItem">
        <div class="tipStyle">
          <div class="tipLeft">桥梁概况</div>
          <span
            class="textGraGreen"
            @click="routerGo('/infoManage/bridgeManageDetial', 91, 1)"
            >详情</span
          >
        </div>
        <div class="bridge">
          <div class="bridgeItem">
            <div class="itemGra">
              <img src="@/assets/images/home/danwei.png" alt="" /><span
                >管理单位</span
              >
            </div>
            <div class="itemGra">
              <div style="width: 100%; overflow: hidden; padding: 0">
                <span
                  class="textGraGreen"
                  :class="{
                    textOver:
                      baseInfo.runningDepartment &&
                      baseInfo.runningDepartment.length > 11
                  }"
                  >{{ baseInfo.runningDepartment || '/' }}</span
                >
              </div>

              <div class="iconBridgeBox">
                <el-tooltip
                  placement="bottom-end"
                  class="iconBridge"
                  popper-class="iconInfo"
                >
                  <div slot="content" class="infoItem">
                    <div>
                      <span>单位电话</span
                      ><span>{{ baseInfo.maintainDepartPhone || '/' }}</span>
                    </div>
                    <div>
                      <span>责任人</span
                      ><span>{{ baseInfo.chargeName || '/' }}</span>
                    </div>
                    <div>
                      <span>责任人电话</span
                      ><span>{{ baseInfo.chargePhone || '/' }}</span>
                    </div>
                  </div>
                  <span><i class="iconfont icon-lianxiren"></i></span>
                </el-tooltip>
              </div>
            </div>
          </div>
          <div class="bridgeItem">
            <div class="itemGra">
              <img src="@/assets/images/home/road.png" alt="" /><span
                >路线等级</span
              >
            </div>
            <div class="itemGra">
              <span class="textGraGreen">{{ baseInfo.spanType || '/' }}</span>
            </div>
          </div>
          <div class="bridgeItem">
            <div class="itemGra">
              <img src="@/assets/images/home/time.png" alt="" /><span
                >建成年限</span
              >
            </div>
            <div class="itemGra">
              <span class="textGraGreen">{{ baseInfo.buildTime || '/' }}</span>
            </div>
          </div>
          <div class="bridgeItem">
            <div class="itemGra">
              <img src="@/assets/images/home/fuhe.png" alt="" /><span
                >设计荷载</span
              >
            </div>
            <div class="itemGra">
              <div style="width: 100%; overflow: hidden; padding: 0">
                <span
                  class="textGraGreen"
                  :class="{
                    textOver:
                      baseInfo.designLoad && baseInfo.designLoad.length > 11
                  }"
                  >{{ baseInfo.designLoad || '/' }}</span
                >
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="modelItem borderGra">
        <div class="tipStyle">
          <div class="tipLeft">综合评价指标</div>
        </div>
        <div class="curing">
          <div class="curingBox">
            <modelRadar ref="modelRadar" :echartData="curingData"></modelRadar>
          </div>
          <div class="curingValue">
            <span class="textGraGreen">{{ curingData.total }}分</span>
          </div>
        </div>
      </div>
      <div class="modelItem borderGra">
        <div class="tipStyle">
          <div class="tipLeft">历史检测维养</div>
        </div>
        <div class="history">
          <div v-if="historyList.length == 0" class="noData textGraGreen">
            暂无数据
          </div>
          <div v-else class="hisBox">
            <div ref="timeBox" class="timeBox">
              <el-timeline :reverse="false">
                <el-timeline-item
                  v-for="(item, index) in historyList"
                  :key="index"
                  :timestamp="item.status"
                >
                  <div class="textGraYellow">{{ item.name }}</div>
                </el-timeline-item>
              </el-timeline>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- 右侧 -->
    <div class="modelRight" :class="{ hideDoubleSide: !showDoubleSide }">
      <div class="modelItem">
        <div class="tipStyle">
          <div class="tipLeft">监测概况</div>
          <span
            v-if="roleId > 1 && monitorData.total != 0"
            class="textGraGreen"
            @click="routerGo('结构物监测', 3)"
            >详情</span
          >
        </div>
        <div class="monitor">
          <div class="monitorEchart">
            <monitorGauge
              ref="monitorGauge"
              :echartData="monitorData"
            ></monitorGauge>
          </div>
          <div class="monitorRight">
            <div class="monitorItem itemGra">
              <img src="@/assets/images/home/icon_monitor.png" alt="" /><span
                class="textGraGreen"
                >传感器数量</span
              ><span class="value">{{ monitorData.total }}</span>
            </div>
            <div class="monitorItem itemGra">
              <img src="@/assets/images/home/icon_monitor.png" alt="" /><span
                class="textGraGreen"
                >离线传感器</span
              ><span class="value" style="color: #ebec38">{{
                monitorData.offline
              }}</span>
            </div>
            <div class="monitorItem itemGra">
              <img src="@/assets/images/home/icon_monitor.png" alt="" /><span
                class="textGraGreen"
                >今日预警数</span
              ><span class="value" style="color: #ebec38">{{
                monitorData.warningCount
              }}</span>
            </div>
            <div class="monitorItem itemGra">
              <img src="@/assets/images/home/icon_monitor.png" alt="" /><span
                class="textGraGreen"
                >今日处警率</span
              ><span class="value">{{ monitorData.processingRate }}%</span>
            </div>
          </div>
        </div>
      </div>
      <div class="modelItem borderGra">
        <div class="tipStyle">
          <div class="tipLeft">检测概况</div>
          <span
            v-if="roleId > 1 && evaluationInfo.evaluationTime != null"
            class="textGraGreen"
            @click="routerGo('结构物检测', 69)"
            >详情</span
          >
        </div>
        <div class="testing">
          <div class="itemGra">
            <span class="title">上次检测时间</span
            ><span class="textGraGreen">{{
              evaluationInfo.evaluationTime || '/'
            }}</span>
          </div>
          <div class="itemGra">
            <span class="title">上次评分等级</span>
            <div class="textGraGreen level">
              <span
                ><p>{{ evaluationInfo.level }}</p>
                {{ evaluationInfo.unit }}</span
              >
              <span
                ><p>{{ evaluationInfo.score || '/' }}</p>
                分</span
              >
            </div>
          </div>
          <div class="itemGra">
            <span class="title">上次检测病害数</span
            ><span class="textGraYellow">{{
              evaluationInfo.diseaseCount || '/'
            }}</span>
          </div>
          <div class="itemGra">
            <span class="title">处置对策</span
            ><span class="textGraYellow">{{
              evaluationInfo.dealMethod || '/'
            }}</span>
          </div>
        </div>
      </div>
      <div class="modelItem borderGra">
        <div class="tipStyle">
          <div class="tipLeft">巡查概况</div>
          <span
            v-if="roleId > 1 && inspectionData.percent > 0"
            class="textGraGreen"
            @click="routerGo('/normal/record', 31, 2)"
            >详情</span
          >
        </div>
        <div class="inspectionBox">
          <div class="inspectionLeft">
            <modelLiquid
              ref="liquidInspection"
              :echartData="inspectionData"
            ></modelLiquid>
          </div>
          <div class="inspectionRight">
            <div class="inspectionItem">
              <span class="text">病害数</span>
              <div class="itemGra">
                <span class="textGraGold">{{ inspectionData.deal }}</span>
              </div>
            </div>
            <div class="inspectionItem">
              <span class="text">巡查时间</span>
              <div class="itemGra">
                <span class="textGraGreen">{{ inspectionData.undeal }}</span>
              </div>
            </div>
          </div>
          <!-- <div
            v-if="inspectionData.xData.length == 0"
            class="noData textGraGreen"
          >
            暂无数据
          </div>
          <modelBar
            v-else
            ref="modelBar"
            :echartData="inspectionData"
          ></modelBar> -->
        </div>
      </div>
      <div class="modelItem borderGra">
        <div class="tipStyle">
          <div class="tipLeft">维养概况</div>
          <span
            v-if="roleId > 1 && maintenanceData.total > 0"
            class="textGraGreen"
            @click="routerGo('维养记录', 50)"
            >详情</span
          >
        </div>
        <div class="maintenance">
          <div class="maintenanceLeft">
            <modelLiquid
              ref="liquidEchart"
              :echartData="maintenanceData"
            ></modelLiquid>
          </div>
          <div class="maintenanceRight">
            <div class="maintenanceItem">
              <span class="text">已修复</span>
              <div class="itemGra">
                <span class="textGraGreen">{{ maintenanceData.deal }}</span>
              </div>
            </div>
            <div class="maintenanceItem">
              <span class="text">未修复</span>
              <div class="itemGra">
                <span class="textGraYellow">{{ maintenanceData.undeal }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- 底部 -->
    <div class="modelBtm">
      <div class="iconItem" title="3D旋转" @click="iconClick(1)">
        <img src="@/assets/images/home/icon_3D旋转.png" alt="" />
      </div>
      <el-tooltip
        placement="top"
        v-for="item in iconList"
        :key="item.id"
        class="iconItem"
        popper-class="iconBtm"
        :title="item.title"
      >
        <div slot="content">{{ item.toolTip }}</div>
        <el-button>Top center</el-button>
        <div @click="iconClick(item)">
          <img :src="item.imgUrl" alt="" />
          <div v-if="item.showBar" class="iconBar"></div>
        </div>
      </el-tooltip>
    </div>
  </div>
</template>

<script>
import {
  getComprehensive,
  getMainEvaHistory,
  getSensorInfo,
  getEvaluationInfo,
  getMaintainInfo
} from '@/api/home/bridgeModel';
import { getStructureById } from '@/api/common';
import model from './components/model';
import monitorGauge from './components/monitorGauge';
// import modelBar from './components/modelBar';
import modelLiquid from './components/modelLiquid';
import modelRadar from './components/modelRadar';
export default {
  name: 'bridgeModel-dzl',
  components: {
    model,
    monitorGauge,
    // modelBar,
    modelLiquid,
    modelRadar
  },
  data() {
    return {
      roleId: this.$store.getters.getRoleInfo.id,
      weather: '',
      time: '',
      timer: null,
      showDoubleSide: true,
      iconList: [
        // {
        //   id: 1,
        //   imgUrl: require("@/assets/images/home/icon_3D旋转.png"),
        //   title: "3D旋转",
        //   showBar: true
        // },
        {
          id: 2,
          imgUrl: require('@/assets/images/home/icon_加速度.png'),
          title: '',
          toolTip: '加速度',
          name: 'jiasudu',
          showBar: true
        },
        {
          id: 3,
          imgUrl: require('@/assets/images/home/icon_索力.png'),
          title: '',
          toolTip: '索力',
          name: 'suoli',
          showBar: true
        },
        {
          id: 4,
          imgUrl: require('@/assets/images/home/icon_应变.png'),
          title: '',
          toolTip: '应变',
          name: 'yingbian',
          showBar: true
        },
        {
          id: 5,
          imgUrl: require('@/assets/images/home/icon_动态.png'),
          title: '',
          toolTip: '动态',
          name: 'dongtai',
          showBar: true
        },
        {
          id: 6,
          imgUrl: require('@/assets/images/home/icon_温度.png'),
          title: '',
          toolTip: '温度',
          name: 'wendu',
          showBar: true
        },
        {
          id: 7,
          imgUrl: require('@/assets/images/home/icon_倾角.png'),
          title: '',
          toolTip: '倾角',
          name: 'qingjiao',
          showBar: true
        },
        {
          id: 8,
          imgUrl: require('@/assets/images/home/icon_称重.png'),
          title: '',
          toolTip: '称重',
          name: 'chengzhong',
          showBar: true
        },
        {
          id: 9,
          imgUrl: require('@/assets/images/home/icon_位移.png'),
          title: '',
          toolTip: '位移',
          name: 'weiyi',
          showBar: true
        },
        {
          id: 10,
          imgUrl: require('@/assets/images/home/icon_气象.png'),
          title: '',
          toolTip: '气象',
          name: 'qixiang',
          showBar: true
        }
      ],
      //桥梁概况信息
      baseInfo: {},
      //监测概况数据
      monitorData: {
        processingRate: 0
      },
      //检测概况
      evaluationInfo: {},
      //巡查概况数据
      inspectionData: {
        bgColor: 'rgba(242,224,29,0.5)',
        color: '#f2e01d',
        percent: 0,
        deal: 0,
        undeal: 0,
        total: 0,
        text: '巡查率',
        data: [0]
      },
      //维养概况数据
      maintenanceData: {
        bgColor: 'rgba(22,116,115,0.5)',
        color: '#1bccc1',
        percent: 0,
        deal: 0,
        undeal: 0,
        total: 0,
        text: '维修率',
        data: [0]
      },
      //桥梁综合评价指标统计数据
      curingData: {
        total: 0,
        indicator: [
          {
            name: '桥梁评分（BCI）',
            max: 100,
            color: 'rgba(255,255,255,0.85)'
          },
          { name: '预警处理率', max: 100, color: 'rgba(255,255,255,0.85)' },
          { name: '巡查完成率', max: 100, color: 'rgba(255,255,255,0.85)' },
          { name: '维养完成率', max: 100, color: 'rgba(255,255,255,0.85)' },
          { name: '检测完成率', max: 100, color: 'rgba(255,255,255,0.85)' }
        ],
        data: [{ name: '桥梁综合评价指标', value: [] }]
      },
      //历史检测维养列表
      historyList: [],
      historyTimer: null
    };
  },
  methods: {
    async getData() {
      await this.getStructureById(); //获取基本信息
      await this.getComprehensive(); //获取综合评价指标
      await this.getMainEvaHistory(); //获取历史检测维养
      await this.getSensorInfo(); //获取监测概况
      await this.getEvaluationInfo(); //获取检测概况
      await this.getMaintainInfo(); //获取巡查维养概况
    },
    //获取基本信息
    async getStructureById() {
      let { code, data } = await getStructureById(this.structureId, 0);
      if (code == '0000') {
        this.projectId = data.projectId;
        this.baseInfo = data;
      }
    },
    //获取综合评价指标
    async getComprehensive() {
      let { code, data } = await getComprehensive(this.structureId);
      if (code == '0000') {
        let arry = data.concat();
        let length = 1;
        let avgNum = arry.reduce((num, item, index) => {
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
        this.curingData.total = Math.round(avgNum);
        this.curingData.data[0].value = data;
        this.$nextTick(() => {
          this.$refs.modelRadar.setOption();
        });
      }
    },
    //获取历史检测维养
    async getMainEvaHistory() {
      let { code, data } = await getMainEvaHistory(this.structureId);
      if (code == '0000') {
        this.historyList = data;
        if (data.length > 5) {
          this.rankTime();
        }
      }
    },
    //获取监测概况
    async getSensorInfo() {
      let { code, data } = await getSensorInfo(this.structureId);
      if (code == '0000') {
        this.monitorData = data;
        this.$nextTick(() => {
          this.$refs.monitorGauge.setOption();
        });
      }
    },
    //获取检测概况
    async getEvaluationInfo() {
      let { code, data } = await getEvaluationInfo(this.structureId);
      if (code == '0000') {
        let deal = {
          A: '日常保养',
          B: '保养小修',
          C: '针对性小修',
          D: '局部中修',
          E: '大修或加固'
        };
        if (data.evaluationTime != null) {
          data.dealMethod = deal[data.rantingLevel];
          data.evaluationTime = data.evaluationTime.split(' ')[0];
        }
        if (data.rantingLevel.includes('类')) {
          data.level = data.rantingLevel.split('类')[0];
          data.unit = '类';
        } else if (data.rantingLevel.includes('级')) {
          data.level = data.rantingLevel.split('级')[0];
          data.unit = '级';
        } else {
          data.level = data.rantingLevel;
          data.unit = '';
        }
        data.score = Number(data.score).toFixed(1);
        this.evaluationInfo = data;
      }
    },
    //获取巡查维养概况
    async getMaintainInfo() {
      let { code, data } = await getMaintainInfo(this.structureId);
      if (code == '0000') {
        //巡查
        let inspectionFinish = Number(data.inspectionFinish);
        let inspectionIncomplete = Number(data.inspectionIncomplete);
        let total1 = inspectionFinish + inspectionIncomplete;
        this.inspectionData.deal = data.diseaseCount;
        this.inspectionData.undeal = data.inspectionTime;
        this.inspectionData.percent =
          total1 > 0 ? ((inspectionFinish / total1) * 100).toFixed() : 0;
        this.inspectionData.total = total1;
        this.inspectionData.data =
          total1 > 0 ? [Number((inspectionFinish / total1).toFixed(2))] : [0];
        //维养
        let maintainFinish = Number(data.maintainFinish);
        let maintainIncomplete = Number(data.maintainIncomplete);
        let total2 = maintainFinish + maintainIncomplete;
        this.maintenanceData.deal = maintainFinish;
        this.maintenanceData.undeal = maintainIncomplete;
        this.maintenanceData.percent =
          total2 > 0 ? ((maintainFinish / total2) * 100).toFixed() : 0;
        this.maintenanceData.total = total2;
        this.maintenanceData.data =
          total2 > 0 ? [Number((maintainFinish / total2).toFixed(2))] : [0];
        this.$nextTick(() => {
          // if (this.inspectionData.xData.length > 0) {
          //   this.$refs.modelBar.setOption();
          // }
          this.$refs.liquidInspection.setOption();
          this.$refs.liquidEchart.setOption();
        });
      }
    },
    //返回
    routerBack() {
      this.$router.go(-1);
    },
    //详情跳转
    routerGo(name, id, type) {
      this.$store.dispatch('asyncUpdateActiveIndex', id);
      if (type == 1) {
        this.$router.push({
          path: name,
          query: {
            id: this.structureId,
            name: this.bridgeName + '模型'
          }
        });
      } else if (type == 2) {
        this.$router.push({
          path: name,
          query: {
            projectId: this.projectId,
            structureId: this.structureId
          }
        });
      } else {
        this.$router.push({
          name,
          params: {
            id: this.structureId,
            projectId: this.projectId,
            structureName: this.bridgeName
          }
        });
      }
    },
    //点击底部图标
    iconClick(data) {
      if (data == 1) {
        let rotationKey = this.$refs.bridgeModel.contentWindow.rotationKey;
        if (!rotationKey) {
          let ifr_document = this.$refs.bridgeModel.contentWindow.tmpClick();
          this.showDoubleSide = false;
        } else {
          this.$message.warning('模型处于旋转中，请双击屏幕终止旋转');
        }
      } else {
        data.showBar = !data.showBar;
        this.$refs.bridgeModel.contentWindow.setIconState(
          data.name,
          data.showBar
        );
        // this.$refs.bridgeModel.setIconState(data.name, data.showBar);
      }
    },
    //历史检测维养轮播
    rankTime() {
      // cityRemove
      this.historyTimer = setInterval(() => {
        this.$refs.timeBox.className = 'timeBox cityRemove';
        setTimeout(() => {
          if (this.historyTimer != null) {
            if (this.$refs.timeBox) {
              this.$refs.timeBox.className = 'timeBox';
            }
            let arry = this.historyList[0];
            this.historyList.splice(0, 1);
            this.historyList.push(arry);
          }
        }, 1000);
      }, 3000);
    }
  },
  beforeMount() {
    this.$utils.getAuthPage(this, 1); //获取权限
    this.structureId = this.$route.query.id;
    this.bridgeName = this.$route.query.bridgeName;
  },
  mounted() {
    let _this = this;
    let token = this.$store.getters.getToken;
    this.getData();
    this.$refs.bridgeModel.onload = () => {
      this.$refs.bridgeModel.contentWindow.getBridgeModel(token);
      this.$refs.bridgeModel.contentWindow.getSensorAndVideo(token);
    };

    let key = 'b5b020ce5380b304261c2405c3143367';
    let addressParams = { key, address: '福建省宁德市福鼎市' };
    //获取定位
    this.$http({
      methods: 'get',
      url: 'https://restapi.amap.com/v3/geocode/geo?parameters',
      params: addressParams
    }).then((res) => {
      let weahParams = { key, city: res.data.geocodes[0].adcode };
      //获取天气
      this.$http({
        methods: 'get',
        url: 'https://restapi.amap.com/v3/weather/weatherInfo?parameters',
        params: weahParams
      }).then((res) => {
        this.weather = res.data.lives[0].weather;
      });
    });

    this.timer = setInterval(() => {
      this.time = this.$utils.Dateformat(new Date());
    }, 1000);

    window.addEventListener('message', (res) => {
      if (res.data == 'models') {
        _this.$store.dispatch('asyncUpdateActiveIndex', 91);
        _this.$router.push({
          path: '/infoManage/bridgeManageDetial',
          query: {
            id: this.structureId,
            name: this.bridgeName + '模型',
            type: 'models'
          }
        });
      }
    });
  },
  beforeDestroy() {
    if (this.historyTimer && this.historyTimer != null) {
      clearInterval(this.historyTimer);
    }
  }
};
</script>
<style lang="scss" scoped>
.model {
  position: relative;
  height: 100%;
  width: 100%;
  .modelBack {
    position: absolute;
    top: 4.27vh;
    left: 48px;
    z-index: 999;
    display: flex;
    align-items: center;
    .back {
      display: flex;
      align-items: center;
      cursor: pointer;
      .textGraGreen {
        font-size: 20px;
        margin-left: 13px;
      }
    }
    .textGraGreen {
      font-size: 36px;
      font-weight: bold;
      margin-left: 36px;
    }
  }
  .Tooltips {
    position: absolute;
    top: 4.27vh;
    right: 48px;
    z-index: 999;
    display: flex;
    align-items: center;
    .textGraGreen {
      margin-left: 30px;
      font-size: 20px;
      font-weight: bold;
      span {
        margin-left: 12px;
      }
    }
    .toolClose {
      cursor: pointer;
    }
  }
  .modelRight {
    position: absolute;
    top: 11.308vh;
    right: 48px;
    width: 400px;
    height: 79.519vh;
    background-color: rgba(14, 44, 39, 0.2);
    border: 2px solid;
    border-image-source: linear-gradient(
      -90deg,
      #10414b 0%,
      rgba(24, 160, 156, 0.91) 25%,
      rgba(31, 255, 236, 0.82) 50%,
      rgba(23, 157, 153, 0.73) 75%,
      rgba(15, 59, 70, 0.64) 100%
    );
    border-image-slice: 1;
    z-index: 999;
    display: flex;
    flex-direction: column;
    transition: width 1s;
    overflow: hidden;
    .monitor {
      height: 16.683vh;
      // padding: 0.835vh 12px;
      padding: 0.835vh 0;
      display: flex;
      align-items: center;
      justify-content: space-between;
      .monitorEchart {
        // flex: 1;
        // width: 180px;
        width: 50%;
        height: 100%;
      }
      .monitorRight {
        flex: 1;
        // width: 180px;
        height: 100%;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        .monitorItem {
          width: 100%;
          height: 3.337vh;
          font-size: 20px;
          color: #fff;
          padding-left: 8px;
          display: flex;
          align-items: center;
          .textGraGreen {
            padding: 0 15px 0 8px;
          }
          .value {
            // font-family: serif;
          }
        }
      }
    }
    .testing {
      width: 100%;
      height: 16.22vh;
      padding: 18px 30px;
      display: flex;
      flex-wrap: wrap;
      justify-content: space-evenly;
      .itemGra {
        height: 5.562vh;
        width: 160px;
        color: #fff;
        font-size: 14px;
        padding-left: 15px;
        display: flex;
        flex-direction: column;
        justify-content: space-evenly;
        .title {
          font-size: 14px;
        }
        .textGraGreen,
        .textGraYellow {
          font-size: 16px;
          font-weight: bold;
          span {
            display: flex;
            align-items: baseline;
            p {
              padding-right: 6px;
            }
          }
        }
        .level {
          font-size: 10px;
          p {
            font-size: 20px;
          }
          span:first-child {
            margin-right: 20px;
          }
        }
      }
    }
    .inspectionBox {
      width: 100%;
      height: 16.668vh;
      padding: 0 24px;
      display: flex;
      .inspectionLeft {
        flex: 1;
        height: 100%;
      }
      .inspectionRight {
        width: 171px;
        height: 100%;
        display: flex;
        flex-direction: column;
        justify-content: space-evenly;
        .inspectionItem {
          width: 100%;
          height: 3.337vh;
          display: flex;
          align-items: center;
          justify-content: flex-end;
          .text {
            font-size: 12px;
            color: #fff;
            margin-right: 10px;
          }
          .itemGra {
            height: 100%;
            width: 110px;
            display: flex;
            align-items: center;
            justify-content: center;
            span {
              font-size: 16px;
              font-weight: bold;
            }
          }
        }
      }
    }
    .maintenance {
      width: 100%;
      height: 16.668vh;
      padding: 0 24px;
      display: flex;
      align-items: center;
      .maintenanceLeft {
        flex: 1;
        height: 100%;
      }
      .maintenanceRight {
        width: 171px;
        height: 100%;
        display: flex;
        flex-direction: column;
        justify-content: space-evenly;
        .maintenanceItem {
          width: 100%;
          height: 3.337vh;
          display: flex;
          align-items: center;
          justify-content: flex-end;
          .text {
            font-size: 12px;
            color: #fff;
            margin-right: 10px;
          }
          .itemGra {
            height: 100%;
            width: 110px;
            display: flex;
            align-items: center;
            justify-content: center;
            span {
              font-size: 26px;
              font-weight: bold;
            }
          }
        }
      }
    }
  }
  .modelLeft {
    position: absolute;
    top: 11.308vh;
    left: 48px;
    width: 400px;
    height: 79.519vh;
    background-color: rgba(14, 44, 39, 0.2);
    border: 2px solid;
    border-image-source: linear-gradient(
      -90deg,
      #10414b 0%,
      rgba(24, 160, 156, 0.91) 25%,
      rgba(31, 255, 236, 0.82) 50%,
      rgba(23, 157, 153, 0.73) 75%,
      rgba(15, 59, 70, 0.64) 100%
    );
    border-image-slice: 1;
    z-index: 999;
    display: flex;
    flex-direction: column;
    transition: width 1s;
    overflow: hidden;
    .bridge {
      width: 100%;
      height: 19.185vh;
      padding: 1.855vh 12px;
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      .bridgeItem {
        height: 3.337vh;
        width: 100%;
        display: flex;
        align-items: center;
        div {
          height: 100%;
          padding: 0 12px;
          display: flex;
          align-items: center;
        }
        .itemGra {
          position: relative;
          width: 114px;
          color: #fff;
          font-size: 14px;
          padding-right: 65px;
          overflow: hidden;
          .textGraGreen {
            padding-left: 0 !important;
          }
          .textOver {
            display: inline-block;
            padding-left: 100% !important;
            animation: textOverRoll 8s linear infinite;
          }
          span:last-child {
            padding-left: 5px;
          }
          &:last-child {
            flex: 1;
          }
        }
        .iconBridgeBox {
          position: absolute;
          right: 37px;
          padding: 0;
          display: flex;
          align-items: center;
          i {
            color: #2fc7c8;
          }
        }
      }
    }
    .curing {
      width: 100%;
      height: 27.619vh;
      padding-bottom: 1.484vh;
      display: flex;
      align-items: center;
      flex-direction: column;
      .curingBox {
        width: 100%;
        flex: 1;
      }
      .curingValue {
        width: 116px;
        height: 3.337vh;
        border: 1px solid #0f5458;
        background: rgba(24, 55, 62, 0.5);
        display: flex;
        align-items: center;
        justify-content: center;
        .textGraGreen {
          font-size: 24px;
          font-weight: bold;
        }
      }
    }
    .history {
      width: 100%;
      height: 22.686vh;
      padding: 1.667vh 32px 0;
      // overflow: hidden;
      display: flex;
      // flex-direction: column;
      // justify-content: space-evenly;
      .hisBox {
        height: 100%;
        width: 100%;
        overflow: auto;
      }
      .timeBox {
        position: relative;
        height: 100%;
        font-size: 14px;
        // overflow: auto;
        /deep/ .el-timeline-item {
          height: 4.63vh;
          padding-bottom: 1.39vh;
          &:first-child .el-timeline-item__node--normal {
            background: #45dddb;
          }
          &:last-child .el-timeline-item__wrapper {
            border-bottom: 0;
          }
        }
        /deep/ .el-timeline-item__tail {
          left: 4px;
          top: 8px;
          height: 3.89vh;
          border-left: 1px solid rgba(38, 145, 131, 0.5);
        }
        /deep/ .el-timeline-item__node--normal {
          left: 0;
          width: 8px;
          height: 8px;
          background: rgba(38, 145, 131, 0.5);
        }
        /deep/ .el-timeline-item__wrapper {
          width: 90%;
          height: 100%;
          left: 1.4583vw;
          border-bottom: 1px solid #134452;
          padding: 0;
          display: flex;
          align-items: baseline;
          justify-content: space-between;
        }
        /deep/ .el-timeline-item__content {
          font-weight: bold;
        }
        /deep/ .el-timeline-item__timestamp {
          font-size: 14px;
          color: #e3e3e3;
          margin: 0;
        }
      }
      .historyItem {
        width: 100%;
        height: 3.059vh;
        font-size: 14px;
        color: #fff;
        border-bottom: 1px solid #134452;
        display: flex;
        justify-content: space-between;
        .text {
          font-family: monospace;
          background: linear-gradient(to top, #ff600a, #fff 80%);
          -webkit-background-clip: text;
          color: transparent;
          font-size: 16px;
        }
        &:last-child {
          border: none;
        }
      }
      .noData {
        padding-bottom: 1.667vh;
      }
    }
  }
  .modelBtm {
    position: absolute;
    bottom: 1.669vh;
    left: 528px;
    width: 864px;
    height: 6.303vh;
    background-color: rgba(14, 44, 39, 0.2);
    border-style: solid;
    border-width: 2px;
    border-image-source: linear-gradient(
      -90deg,
      #10414b 0%,
      rgba(24, 160, 156, 0.91) 25%,
      rgba(31, 255, 236, 0.82) 50%,
      rgba(23, 157, 153, 0.73) 75%,
      rgba(15, 59, 70, 0.64) 100%
    );
    border-image-slice: 1;
    z-index: 999;
    display: flex;
    align-items: center;
    .iconItem {
      width: 96px;
      height: 48px;
      border-right: 2px solid;
      border-image-source: linear-gradient(
        0deg,
        transparent,
        rgba(24, 160, 156, 0.91) 25%,
        rgba(31, 255, 236, 0.82) 50%,
        rgba(23, 157, 153, 0.73) 75%,
        transparent
      );
      border-image-slice: 1;
      display: flex;
      align-items: center;
      justify-content: center;
      cursor: pointer;
      .iconBar {
        position: absolute;
        bottom: 0;
        height: 4px;
        width: 48px;
        background: #36fff0;
      }
      &:last-child {
        border: none;
      }
    }
  }
  .hideDoubleSide {
    width: 0;
    animation: hideBorder 1s linear;
    animation-fill-mode: forwards;
  }
}
//渐变字体样式
.textGraGreen {
  font-size: 16px;
  background: linear-gradient(to top, #25f2f5, #fff 80%);
  -webkit-background-clip: text;
  color: transparent;
  display: flex;
  align-items: center;
}
.textGraYellow {
  font-size: 16px;
  background: linear-gradient(to top, #ff600a, #fff 80%);
  -webkit-background-clip: text;
  color: transparent;
  display: flex;
  align-items: center;
}
.textGraGold {
  font-size: 16px;
  background: linear-gradient(to top, #f2e01d, #fff 80%);
  -webkit-background-clip: text;
  color: transparent;
  display: flex;
  align-items: center;
}
.borderGra {
  border-top: 2px solid;
  border-image-source: linear-gradient(
    -90deg,
    rgba(15, 59, 70, 0.64) 0%,
    rgba(31, 255, 236, 0.96) 90%,
    #10414b 100%
  );
  border-image-slice: 1;
}
.itemGra {
  background: linear-gradient(
    to right,
    rgba(16, 65, 75, 0.4),
    rgba(16, 65, 75, 0)
  );
  border: 1px solid;
  border-right: 0;
  border-image-source: linear-gradient(
    -90deg,
    rgba(16, 65, 75, 0) 0%,
    rgba(16, 65, 75, 0.3) 50%,
    rgba(16, 65, 75, 0.8) 100%
  );
  border-image-slice: 1;
}
//没数据样式
.noData {
  width: 100%;
  flex: 1;
  font-size: 28px;
  display: flex;
  align-items: center;
  justify-content: center;
}
//标题统一样式
.modelItem {
  width: 100%;
  display: flex;
  flex-direction: column;
  .tipStyle {
    width: 100%;
    height: 3.059vh;
    padding-right: 13px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    .tipLeft {
      width: 221px;
      height: 100%;
      font-size: 16px;
      font-weight: bold;
      color: #fff;
      letter-spacing: 2px;
      padding-right: 45px;
      background: url('~@/assets/images/home/tipBox.png') no-repeat;
      background-size: 100% 100%;
      display: flex;
      align-items: center;
      justify-content: center;
    }
    span {
      cursor: pointer;
    }
  }
}
span,
div {
  white-space: nowrap;
}
@keyframes hideBorder {
  from {
    border-width: 2px;
  }
  to {
    border-width: 0;
  }
}
</style>
<style lang="scss">
.iconBtm {
  width: 66px;
  height: 44px;
  font-size: 14px;
  color: #fff;
  background: url('~@/assets/images/home/toolBox.png') no-repeat !important;
  background-size: 100% 100%;
  padding: 0 0 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  .popper__arrow {
    display: none !important;
  }
}
.iconInfo {
  width: 221px;
  height: 8.805vh;
  padding: 0 12px;
  border-radius: 0;
  background: #07222c !important;
  border: solid 1px #1bccc1;
  .popper__arrow {
    display: none !important;
  }
  .infoItem {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: space-evenly;
    div {
      width: 100%;
      display: flex;
      align-items: center;
      span:first-child {
        width: 85px;
        font-size: 14px;
        color: rgba($color: #fff, $alpha: 0.85);
      }
      span:last-child {
        max-width: 100px;
        font-size: 14px;
        color: #2fc7c8;
        overflow: hidden;
        text-overflow: ellipsis;
      }
    }
  }
  &::after {
    content: '';
    position: absolute;
    right: 6px;
    top: -4px;
    width: 6px;
    height: 6px;
    background: #07222c !important;
    border-top: 1px solid #1bccc1;
    border-left: 1px solid #1bccc1;
    transform: rotate(45deg);
  }
}

//历史检测维养轮播动画
.cityRemove {
  animation: historyOut 1s linear forwards;
}
@keyframes historyOut {
  from {
    top: 0;
  }
  to {
    top: -4.63vh;
  }
}
//文字溢出滚动
@keyframes textOverRoll {
  from {
    transform: translateX(0);
  }
  to {
    transform: translateX(-100%);
  }
}
</style>
