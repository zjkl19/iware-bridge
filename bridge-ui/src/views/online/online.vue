<template>
  <div class="online">
    <!-- top -->
    <div class="topBox">
      <!-- 数据统计 -->
      <div class="topLeft">
        <!-- 监测数据 -->
        <div class="numPad animate__animated animate__fadeInDown">
          <!-- 监测桥隧数 -->
          <div class="monitoringbox bgColor-blue">
            <div class="text">
              <div class="textSize-title">监测桥隧数</div>
              <div class="textSize-num">{{ bridgeCount }}</div>
            </div>
            <span class="onlineFrame">
              <i class="iconfont icon-qiao"></i>
            </span>
          </div>
          <!-- 传感器数 -->
          <div class="monitoringbox bgColor-orange">
            <div class="text">
              <div class="textSize-title">传感器数</div>
              <div class="textSize-num">{{ sensorCount }}</div>
            </div>
            <div class="onlineFrame">
              <i class="iconfont icon-chuanganqi"></i>
            </div>
          </div>
          <!-- 摄像头 -->
          <div class="monitoringbox bgColor-green">
            <div class="text">
              <div class="textSize-title">摄像头数</div>
              <div class="textSize-num">{{ videoCount }}</div>
            </div>
            <div class="onlineFrame">
              <i class="iconfont icon-shexiangtou"></i>
            </div>
          </div>
          <!-- 预警数 -->
          <div class="monitoringbox bgColor-yellow">
            <div class="text">
              <div class="textSize-title">当日预警数</div>
              <div class="textSize-num">
                <span>{{ todayWarning }}</span>
                <span v-if="warningTend > 0" class="up">
                  <i class="iconfont icon-shangsheng"></i>
                </span>
                <span v-else-if="warningTend < 0" class="down">
                  <i class="iconfont icon-shangsheng"></i>
                </span>
              </div>
            </div>
            <div class="onlineFrame">
              <i class="iconfont icon-gaojing"></i>
            </div>
          </div>
        </div>

        <!-- 预警统计 -->
        <div
          class="earlyWarning boxShadow animate__animated animate__fadeInLeft"
        >
          <div class="earyTop">
            <div>
              <div>预警统计</div>
            </div>
            <div style="display: flex">
              <div
                class="earlyWarning-btn"
                :class="{ activeBg: selectTime == 1 }"
                @click="getStatistics(1)"
              >
                今日
              </div>
              <div
                class="earlyWarning-btn"
                :class="{ activeBg: selectTime == 2 }"
                @click="getStatistics(2)"
              >
                本月
              </div>
              <div
                class="earlyWarning-btn"
                :class="{ activeBg: selectTime == 3 }"
                @click="getStatistics(3)"
              >
                今年
              </div>
              <div
                class="earlyWarning-btn"
                :class="{ activeBg: selectTime == 0 }"
                @click="getStatistics(0)"
              >
                全部
              </div>
            </div>
          </div>
          <div class="middle">
            <monitorPie ref="monitorPie" :echartData="warnData"></monitorPie>
          </div>
        </div>
      </div>
      <!-- 地图 -->
      <div class="topRight animate__animated animate__fadeInRight">
        <div class="mapStyle">
          <div class="mapSelect">
            <el-select
              v-model="structureId"
              placeholder="请选择结构物"
              :popper-append-to-body="false"
              filterable
              clearable
              @change="selectBridge"
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
          <Amap
            ref="Amap"
            :zoom.sync="map.zoom"
            :center="map.center"
            :viewMode="'3D'"
            :skyColor="'#419aff'"
            pitchEnable
            rotateEnable
            :animateEnable="false"
            :defaultCursor="defaultCursor"
            @mousedown="defaultCursor = 'grabbing'"
            @mouseup="defaultCursor = 'grab'"
            @zoomend="syncCenterAndZoom"
            @map-complete="onMapComplete"
          >
            <!-- 桥梁、隧道图标 -->
            <!-- <AmapMarker
              v-for="item in mapDataList"
              :key="item.id"
              :position="[item.longitude, item.latitude]"
              :icon="markIcon"
              :label="{ content: item.name, direction: 'bottom' }"
              clickable
              :offset="[-15, -30]"
              @click="clickMarker(item)"
            ></AmapMarker> -->
            <AmapMarkerCluster
              key="custom-all"
              :data="structureList"
              :grid-size="60"
              :average-center="true"
            >
              <template v-slot:marker="point">
                <div class="markBox" @click="clickMarker(point)">
                  <img class="iconImg" :src="getPointIcon(point)" alt="" />
                  <div v-if="map.zoom >= 15" class="markLabel">
                    {{ point.name }}
                  </div>
                </div>
              </template>
              <template v-slot:cluster="context">
                <div class="clusterBox" :style="getClusterStyle2(context)">
                  <div
                    class="ring"
                    :class="getClusterStyle3(context)"
                    :style="getClusterStyle4(context)"
                  ></div>
                  <div
                    :style="getClusterStyle(context)"
                    @click="clusterClick(context)"
                  >
                    {{ context.count }}
                  </div>
                </div>
              </template>
            </AmapMarkerCluster>
          </Amap>
        </div>
      </div>
    </div>
    <!-- bottom -->
    <div class="bottomBox animate__animated animate__fadeInUp">
      <!-- 传感器统计 -->
      <div class="sensor boxShadow">
        <div>传感器统计</div>
        <div class="sensorEcahrt">
          <span v-if="sensorData.data.length == 0" class="noData"
            >暂无数据</span
          >
          <onlinePie ref="onlinePie" :echartData="sensorData"></onlinePie>
        </div>
      </div>
      <!-- 监测评分排行 -->
      <div class="scoreRanking boxShadow">
        <div class="scoreRanking-box">
          <div>监测概况</div>
          <el-select
            v-model="projectId"
            placeholder="请选择项目"
            clearable
            @change="getScoreRank"
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
        <el-table
          class="onlineTable"
          :data="tableData"
          size="mini"
          height="25vh"
          :row-class-name="tableRowClassName"
          @row-click="clickRow"
        >
          <el-table-column type="index" label="序号" width="100" align="center">
          </el-table-column>
          <el-table-column
            prop="structureName"
            label="结构物名称"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="sensorCount"
            label="传感器数量"
            sortable
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="onlineSensorCount"
            label="在线传感器数"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="todayWarningCount"
            label="当日预警数"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="percentageWarning"
            label="当日处警率"
            align="center"
          >
          </el-table-column>
        </el-table>
      </div>
    </div>
  </div>
</template>

<script>
import {
  getCount,
  getStatistics,
  getScoreRank,
  getSensorList
} from '@/api/online/online';
import { getProjectListByModel, getStructureListByModel } from '@/api/common';
import { Amap, Marker, MarkerCluster } from '@amap/amap-vue';
import monitorPie from './components/monitorPie';
import onlinePie from './components/onlinePie';
export default {
  components: {
    Amap,
    AmapMarker: Marker,
    AmapMarkerCluster: MarkerCluster,
    monitorPie,
    onlinePie
  },
  data() {
    return {
      //筛选项
      projectList: [],
      projectId: '',
      structureList: [],
      structureId: '',
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
      //传感器统计图数据
      sensorData: {
        data: []
      },
      tableData: [],
      //百度地图选项值
      map: {
        zoom: 7,
        center: [119.306239, 26.075302]
      },
      markIcon: require('@/assets/images/home/icon_bridge.png'),
      defaultCursor: 'grab',
      //传感器类型ec图数据
      sensorTypeData: [
        {
          name: '倾角传感器',
          value: 18
        },
        {
          name: '加速器传感器',
          value: 25
        },
        {
          name: '称重传感器',
          value: 12
        },
        {
          name: '位移传感器',
          value: 8
        },
        {
          name: '索力传感器',
          value: 20
        },
        {
          name: '应变传感器',
          value: 295
        },
        {
          name: '温度传感器',
          value: 4
        }
      ],
      //基础信息
      bridgeCount: 0,
      sensorCount: 0,
      videoCount: 0,
      todayWarning: 0,
      warningTend: 1,
      selectTime: 1
    };
  },
  beforeMount() {
    this.$utils.getAuthPage(this); //获取权限
  },
  mounted() {
    this.getCount(); //基础信息
    this.getStatistics(1); //预警统计
    this.getSensorList(); //传感器统计
    this.getScoreRank(); //监测评分排行
    this.getProjectListByModel(); //获取项目
    this.getStructureListByModel(); //获取结构物
  },
  methods: {
    //获取桥隧数、传感器数、摄像头、当日预警数
    async getCount() {
      let params = {};
      let { code, data } = await getCount(params);
      if (code == '0000') {
        this.bridgeCount = data.bridgeCount;
        this.sensorCount = data.sensorCount;
        this.videoCount = data.videoCount;
        this.todayWarning = data.todayWarning;
        this.warningTend = data.warningTend;
      }
    },
    // 预警统计
    async getStatistics(type) {
      this.selectTime = type;
      let { code, data } = await getStatistics(type);
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
    //传感器统计
    async getSensorList() {
      let { code, data } = await getSensorList();
      if (code == '0000') {
        let arry = [];
        data.map((item) => {
          if (!!item.name) {
            let obj = {
              name: item.name,
              value: item.count
            };
            arry.push(obj);
          }
        });
        this.sensorData.data = arry;
        this.$nextTick(() => {
          this.$refs.onlinePie.setOption();
        });
      }
    },
    //监测评分排行
    async getScoreRank() {
      let id = !!this.projectId ? this.projectId : -1;
      let { code, data } = await getScoreRank(id);
      if (code == '0000') {
        this.tableData = data;
      }
    },
    //获取项目列表
    async getProjectListByModel() {
      let id = this.$store.getters.getActiveIndex;
      let { code, data } = await getProjectListByModel(id);
      if (code == '0000') {
        this.projectList = data;
      }
    },
    //获取结构物
    async getStructureListByModel() {
      let params = { powerId: this.$store.getters.getActiveIndex };
      let { code, data } = await getStructureListByModel(params);
      if (code == '0000') {
        data.map((item) => {
          item.longitude = !!item.longitude ? item.longitude : 0;
          item.latitude = !!item.latitude ? item.latitude : 0;
          item.lnglat = [item.longitude, item.latitude];
        });
        this.structureList = data;
      }
    },
    //表格点击单行
    clickRow(row, column, event) {
      this.structureList.map((item) => {
        if (item.id == row.structureId) {
          this.map.center = [item.longitude, item.latitude];
          this.map.zoom = 15;
        }
      });
    },
    onMapComplete(map) {
      map.setFitView();
    },
    syncCenterAndZoom(e) {
      this.map.zoom = e.target.getZoom();
    },
    //聚合操作
    getPointIcon(data) {
      // console.log(data);
      let icon;
      if (data.structureType == 1)
        icon = require('@/assets/images/home/icon_bridge.png');
      else icon = require('@/assets/images/home/icon_tunnel.png');
      return icon;
    },
    //聚合操作（聚合样式）
    getClusterStyle(context) {
      let size;
      if (context.count <= 100) {
        size = 36;
      } else if (context.count <= 200) {
        size = 42;
      } else {
        size = 48;
      }
      return {
        backgroundColor: `#419aff`,
        width: `${size}px`,
        height: `${size}px`,
        lineHeight: `${size}px`,
        borderRadius: `50%`,
        color: `#fff`,
        fontSize: '16px',
        fontFamily: 'Roboto',
        fontWeight: '500',
        textAlign: 'center'
      };
    },
    //聚合操作（位置）
    getClusterStyle2(context) {
      let size;
      if (context.count <= 100) {
        size = 36;
      } else if (context.count <= 200) {
        size = 42;
      } else {
        size = 48;
      }
      return {
        left: `-${size / 2}px`,
        top: `-${size / 2}px`
      };
    },
    //聚合操作(外圈动画)
    getClusterStyle3(context) {
      if (context.count <= 100) {
        return 'ringSize1';
      } else if (context.count <= 200) {
        return 'ringSize2';
      } else {
        return 'ringSize3';
      }
    },
    //聚合操作(外圈动画)
    getClusterStyle4(context) {
      let size;
      if (context.count <= 100) {
        size = 36;
      } else if (context.count <= 200) {
        size = 42;
      } else {
        size = 48;
      }
      return {
        width: `${size}px`,
        height: `${size}px`
      };
    },
    //聚合操作
    clusterClick(context) {
      this.map.center = [
        context.clusterData[0].lnglat.lng,
        context.clusterData[0].lnglat.lat
      ];
      this.map.zoom += 1;
    },
    clickMarker(data) {
      // this.$router.push({
      //   path: '/online/onebridgeMonitoring',
      //   query: { id: data.id }
      // });
      this.$router.push({
        name: '结构物监测',
        params: { id: data.id }
      });
    },
    // 点击地图搜索
    selectBridge(val) {
      if (val != '') {
        this.structureList.map((item, index) => {
          if (item.id == val) {
            // console.log(item);
            this.map.center = [item.longitude, item.latitude];
            this.map.zoom = 14;
            return;
          }
        });
      } else {
        this.map.center = [119.306239, 26.075302];
        this.map.zoom = 7;
      }
      this.$nextTick(() => {
        this.queryInRect();
      });
    },
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex % 2 == 1) {
        return 'first-row';
      } else {
        return 'second-row';
      }
    }
  }
};
</script>

<style lang="scss" scoped>
.online {
  box-shadow: none;
  background: transparent !important;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  .topBox {
    height: 49.353vh;
    display: flex;
    align-items: center;
    justify-content: space-between;
    .topLeft {
      height: 100%;
      width: 584px;
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      .numPad {
        height: 21.853vh;
        display: flex;
        flex-wrap: wrap;
        justify-content: space-between;
        align-content: space-between;
        .monitoringbox {
          width: 282px;
          height: 10vh;
          border-radius: 8px;
          padding: 22px 24px;
          color: #fff;
          display: flex;
          align-items: center;
          justify-content: space-between;
          .text {
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            .textSize-num {
              display: flex;
              align-items: baseline;
              .up {
                color: #ff5f5f;
                margin-left: 5px;
                display: flex;
                i {
                  font-size: 24px;
                }
              }
              .down {
                color: #27ae60;
                margin-left: 5px;
                display: flex;
                transform: rotate(180deg);
                i {
                  font-size: 24px;
                }
              }
            }
          }
          .onlineFrame {
            width: 64px;
            height: 64px;
            background: rgba(255, 255, 255, 0.12);
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            i {
              font-size: 36px;
            }
          }
        }
      }
      .earlyWarning {
        height: 25.557vh;
        padding: 22px 24px;
        display: flex;
        flex-direction: column;
        .earyTop {
          display: flex;
          justify-content: space-between;
        }
        .middle {
          width: 100%;
          flex: 1;
        }
      }
    }
    .topRight {
      width: 1260px;
      height: 100%;
    }
  }
  .bottomBox {
    height: 33.334vh;
    display: flex;
    align-items: center;
    justify-content: space-between;
    .sensor {
      height: 100%;
      width: 584px;
      padding: 22px 24px;
      display: flex;
      flex-direction: column;
      .sensorEcahrt {
        flex: 1;
        width: 100%;
      }
    }
    .scoreRanking {
      height: 100%;
      width: 1260px;
      padding: 22px 24px;
      display: flex;
      flex-direction: column;
      .scoreRanking-box {
        padding-bottom: 12px;
        display: flex;
        justify-content: space-between;
      }
      /deep/ .el-table__empty-block {
        border-top: 1px solid #ebeef5;
      }
    }
  }
}

.earlyWarning {
  width: 585.594px;
  &-box {
    border-radius: 8px;
    background: #fff;
    height: 26vh;
    padding: 24px;
  }
  &-btn {
    width: 60px;
    height: 32px;
    border: 1px solid #d9d9d9;
    border-left: 0;
    cursor: pointer;
    font-size: 14px;
    text-align: center;
    // line-height: 2.1;
    display: flex;
    align-items: center;
    justify-content: center;
    &:first-child {
      border-left: 1px solid #d9d9d9;
    }
  }
  // &-btn:hover {
  //   border: 1px solid rgba(47, 128, 237, 1);
  //   color: rgba(47, 128, 237, 1);
  // }
}
.activeBg {
  color: #1890ff;
  border: 1px solid #1890ff !important;
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

// 图表大小
.echart2 {
  height: 24.211vh;
}
.i {
  width: 8px;
  height: 0.855vh;
  border-radius: 20px;
  margin: 0 4px;
}

// 监测评分排行
.scoreRanking {
  .onlineTable {
    &::before {
      height: 0;
    }
    /deep/ .el-table__body-wrapper:hover {
      &::-webkit-scrollbar {
        width: 6px;
      }
      &::-webkit-scrollbar-thumb {
        background: #c4c4c4;
        border-radius: 4px;
      }
    }
  }
  //斑马纹样式
  /deep/ .first-row {
    background: #ffffff;
  }
  /deep/ .second-row {
    background: #f2f4f6;
  }
  /deep/ .el-table th {
    border: 0;
    color: #333;
    font-weight: bold;
  }
  /deep/ .el-table td {
    border: 0;
    &:first-child {
      border-top-left-radius: 5px;
      border-bottom-left-radius: 5px;
    }
    &:last-child {
      border-top-right-radius: 5px;
      border-bottom-right-radius: 5px;
    }
    cursor: pointer;
  }
}

.text {
  &Size {
    &-title {
      font-size: 16px;
    }
    &-num {
      font-size: 32px;
    }
  }
}

.bgColor {
  &-blue {
    background-color: rgba(65, 128, 255, 1); /* 不支持线性的时候显示 */
    background-image: linear-gradient(
      to right,
      rgba(65, 128, 255, 1),
      rgba(65, 168, 255, 1)
    );
    box-shadow: 0px 12px 24px rgba(65, 154, 255, 0.25);
  }
  &-orange {
    background-color: rgba(255, 127, 47, 1); /* 不支持线性的时候显示 */
    background-image: linear-gradient(
      to right,
      rgba(255, 127, 47, 1),
      rgba(255, 115, 83, 1)
    );
    box-shadow: 0px 12px 24px rgba(255, 123, 60, 0.25);
  }
  &-green {
    background-color: rgba(16, 218, 116, 1); /* 不支持线性的时候显示 */
    background-image: linear-gradient(
      to right,
      rgba(16, 218, 116, 1),
      rgba(39, 214, 183, 1)
    );
    box-shadow: 0px 12px 24px rgba(26, 217, 144, 0.25);
  }
  &-yellow {
    background-color: rgba(242, 153, 74, 1); /* 不支持线性的时候显示 */
    background-image: linear-gradient(
      to right,
      rgba(242, 153, 74, 1),
      rgba(242, 201, 76, 1)
    );
    box-shadow: 0px 12px 24px rgba(242, 174, 75, 0.25);
  }
  &-red {
    background-color: rgba(255, 95, 95, 1); /* 不支持线性的时候显示 */
    background-image: linear-gradient(
      to right,
      rgba(255, 95, 95, 1),
      rgba(255, 95, 95, 1)
    );
  }
}

/deep/ .el-tag--dark {
  border: 0;
}
//地图
.mapStyle {
  position: relative;
  height: 100%;
  width: 100%;
  background-color: #fff;
  padding: 16px;
  border-radius: 8px;
  box-shadow: 0 6px 6px #dbdfed;
  .mapSelect {
    position: absolute;
    right: 30px;
    top: 30px;
    z-index: 10;
    /deep/ .el-select {
      width: 220px;
    }
    /deep/ .el-select-dropdown {
      left: 0 !important;
      max-width: 220px;
    }
  }
  .markBox {
    position: relative;
    left: -6px;
    // width: 28px;
    display: flex;
    flex-direction: column;
    align-items: center;
    .markLabel {
      font-size: 14px;
      color: #fff;
      white-space: nowrap;
    }
  }
  .clusterBox {
    position: relative;
    .ring {
      position: absolute;
      left: 0;
      top: 0;
      border-radius: 50%;
      z-index: -1;
    }
    .ringSize1 {
      animation: rings 1.5s linear 1s infinite;
    }
    .ringSize2 {
      animation: rings 1.5s linear 0.5s infinite;
    }
    .ringSize3 {
      animation: rings 1.5s linear infinite;
    }
  }
  .iconImg {
    width: 28px;
    z-index: 10;
  }
  .markTextHide {
    /deep/ .amap-marker-label {
      display: none;
    }
  }
  /deep/ .amap-marker-label {
    color: rgb(53, 96, 255);
    font-size: 14px;
    font-weight: bold;
    border: 0;
    text-shadow: 1px 1px #fff, -1px -1px #fff, -1px 1px #fff, 1px -1px #fff;
    background-color: transparent;
  }
  /deep/ .amap-logo,
  /deep/ .amap-copyright {
    display: none !important;
  }
}
//圈扩散
@keyframes rings {
  from {
    background: rgba(65, 154, 255, 1);
    transform: scale(1);
  }
  to {
    background: rgba(65, 154, 255, 0);
    transform: scale(1.4);
  }
}
</style>
