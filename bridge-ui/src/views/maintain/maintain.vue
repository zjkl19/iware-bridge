<template>
  <div class="maintain">
    <div class="above animate__animated animate__fadeInDown">
      <div class="border_background above_div1">
        <div class="left">
          <span class="my_p">维修桥梁数</span>
          <span class="my_p2">
            {{ structureCount }}
          </span>
        </div>
        <div class="right">
          <span><i class="iconfont icon-qiao"></i></span>
        </div>
      </div>

      <div class="border_background2 above_div1">
        <div class="left">
          <span class="my_p">待处理病害数</span>
          <span class="my_p2">
            {{ pendingCount }}
          </span>
        </div>
        <div class="right">
          <span><i class="iconfont icon-daichuli"></i></span>
        </div>
      </div>

      <div class="border_background3 above_div1">
        <div class="left">
          <span class="my_p">已处理病害数</span>
          <span class="my_p2">
            {{ ProcessedCount }}
          </span>
        </div>
        <div class="right">
          <span><i class="iconfont icon-yichuli"></i></span>
        </div>
      </div>

      <div class="border_background4 above_div1">
        <div class="left">
          <span class="my_p">年度维养总金额</span>
          <span v-if="totalAmount > 10000" class="my_p2">
            {{ Math.round(totalAmount / 100) / 100 }}<span>W</span></span
          >
          <span v-else class="my_p2"> {{ totalAmount }}<span>元</span></span>
        </div>
        <div class="right">
          <span><i class="iconfont icon-jine"></i></span>
        </div>
      </div>
    </div>

    <div class="follow">
      <div class="boxShadow left animate__animated animate__fadeInLeft">
        <div class="my_top">
          <span class="my_span"> 日历工作记录表 </span>
          <div class="my_btn">
            <el-select
              v-model="years"
              placeholder="请选择"
              @change="returnDate"
              class="my_select"
            >
              <el-option
                v-for="item in yearsOptions"
                :key="item.id"
                :label="item.label"
                :value="item.id"
              >
              </el-option>
            </el-select>
            <el-select
              v-model="months"
              placeholder="请选择"
              @change="returnDate"
              class="my_select"
            >
              <el-option
                v-for="item in monthOptions"
                :key="item.id"
                :label="item.label"
                :value="item.id"
              >
              </el-option>
            </el-select>
          </div>
        </div>

        <el-calendar v-model="dateValue" class="my_calendar">
          <template slot="dateCell" slot-scope="{ data }">
            <div
              class="my_div"
              @click="calendarClick(data)"
              :class="{
                select: calendarDate == data.day,
                caldata: calendarButton.includes(data.day)
              }"
            >
              <span class="my_p">
                {{ data.day.split('-').slice(2).join('-') }}
              </span>
              <span v-if="calendarButton.includes(data.day)" class="my_img">
                <i class="iconfont icon-maintain"></i>
              </span>
            </div>
          </template>
        </el-calendar>
        <div class="my_table2">
          <el-table
            class="tableBox"
            :data="tableData"
            :row-class-name="tableRowClassName"
            header-row-class-name="table_th"
            cell-class-name="table_tr"
            height="26.67vh"
          >
            <el-table-column
              prop="name"
              label="维修项"
              min-width="40%"
              show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column prop="maintainTime" label="时间" min-width="30%">
            </el-table-column>
            <el-table-column
              prop="maintainTypeName"
              label="维修类型"
              min-width="30%"
            >
            </el-table-column>
            <el-table-column label="操作" prop="url" min-width="15%">
              <template slot-scope="scope">
                <el-link type="primary" @click="goReport(scope.row.id)"
                  >简报</el-link
                ></template
              ></el-table-column
            >
          </el-table>
        </div>
      </div>
      <div class="mapStyle boxShadow middle animate__animated animate__fadeIn">
        <div class="my_btn">
          <el-cascader
            ref="cascaderHandle"
            class="baidu_select_one my_select"
            v-model="areaValue"
            :options="areaList"
            placeholder="全部地区"
            :props="areaProps"
            :show-all-levels="false"
            @change="selectBridge(1)"
            clearable
          ></el-cascader>

          <el-select
            v-if="thisUserId != 2"
            v-model="userId"
            placeholder="全部业主"
            @change="selectBridge"
            class="baidu_select_two my_select"
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

          <el-select
            v-if="thisUserId != 1 && thisUserId != 0"
            v-model="undertakeId"
            placeholder="全部承接单位"
            @change="selectBridge"
            class="baidu_select_three my_select"
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

          <el-select
            v-model="projectId"
            placeholder="全部项目"
            @change="selectBridge"
            class="baidu_select_four my_select"
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
          <el-select
            v-model="structureItem"
            :popper-append-to-body="false"
            placeholder="选择结构物"
            class="baidu_select_five my_select"
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
        <Amap
          ref="Amap"
          :zoom.sync="map.zoom"
          :center.sync="map.center"
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
            v-for="(item, index) in mapDataList"
            :key="index"
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
      <div class="right animate__animated animate__fadeInRight">
        <div class="boxShadow my_top">
          <span class="my_span">维修病害数</span>
          <div class="border_ec">
            <span v-if="situationData.length == 0" class="noData"
              >暂无数据</span
            >
            <div id="situationEchart"></div>
          </div>
        </div>
        <div class="boxShadow my_middle">
          <span class="my_span">维修类型分布</span>
          <div class="border_ec">
            <span v-if="typeData.length == 0" class="noData">暂无数据</span>
            <div id="typeEchart"></div>
          </div>
        </div>

        <div class="boxShadow my_follow">
          <span class="my_span">维修费用趋势</span>
          <div class="border_ec">
            <span v-if="costData.x.length == 0" class="noData">暂无数据</span>
            <div id="costEchart"></div>
          </div>
        </div>
      </div>
    </div>

    <div v-if="showBriefing">
      <briefReport
        :reportId="reportId"
        :parentName="parentName"
        @closed="showBriefing = false"
      ></briefReport>
    </div>
  </div>
</template>
<script>
import {
  getCount,
  getWorkRecord,
  getdiseaseCount,
  gettypeRatio,
  accountTrend
} from '@/api/maintain/maintain';
import { getDateRecord } from '@/api/maintain/record';
import {
  getListByRoleAndPower,
  getProjectListByModel,
  getStructureListByModel,
  getAreaByPowerId
} from '@/api/common';
import * as echarts from 'echarts';
import { Amap, Marker, MarkerCluster } from '@amap/amap-vue';
import briefReport from './briefReport';
export default {
  components: {
    Amap,
    AmapMarker: Marker,
    AmapMarkerCluster: MarkerCluster,
    briefReport
  },
  data() {
    const _this = this;
    return {
      thisUserId: this.$store.getters.getRoleInfo.id,
      map: {
        zoom: 7,
        center: [119.306239, 26.075302]
      },
      defaultCursor: 'grab',
      markIcon: require('@/assets/images/home/icon_bridge.png'),

      diolog: {}, // 暂时无用
      show: false,
      years: new Date().getFullYear(),
      yearsOptions: [],
      months: new Date().getMonth() + 1,
      monthOptions: [],
      //巡查报告数据
      tableData: [],
      //全部地区
      areaValue: '',
      areaList: [],
      areaProps: {
        value: 'id',
        label: 'name',
        checkStrictly: true
      },
      //全部业务
      userId: '',
      userList: [],
      //全部承接单位
      undertakeId: '',
      undertakeList: [],
      // 全部项目
      projectId: '',
      projectList: [],
      // 结构物
      structureItem: '',
      structureList: [],
      structureCount: 0, //维养数目
      pendingCount: 0, //待处理病害数
      ProcessedCount: 0, //已处理病害数
      totalAmount: 0, //年度维养总额

      dateValue: new Date(), //日历
      calendarDate: _this.$utils.Dateformat1(new Date()),
      calendarButton: [], //有维养按钮的日期
      situationEcharts: [], //维养情况ec图
      situationData: [],
      costEcharts: [], //维养费用趋势ec图
      costData: {
        x: [],
        y1: [],
        y2: []
        // x: ['1', '2', '3', '4', '5', '6'],
        // y1: ['10', '20', '30', '40', '50', '60'],
        // y2: ['20', '30', '10', '50', '70', '80']
      },

      typeEcharts: [], //维修类型分布ec图
      typeData: [],
      multiple: window.innerHeight / 70 / 7,
      showMapLabel: false,
      showBriefing: false, //桥梁、隧道简报显示
      reportId: '',
      parentName: ''
    };
  },
  beforeMount() {
    this.$utils.getAuthPage(this); //获取权限
  },
  mounted() {
    this.getRoleIdList();
    this.getAreaByPowerId();
    this.getCount(); // 查询维养桥隧数/待处理病害数/已处理病害数/年度维养总金额
    this.getWorkRecord(); //日历工作记录表
    this.getdiseaseCount(); //维修病害数
    this.gettypeRatio(); //维修类型分布
    this.accountTrend(); // 维修费用趋势
    this.getProjectListByModel(); // 获取项目
    this.selectBridge(); // 获取结构物
    this.getAreaByPowerId(); // 获取地区

    this.yearsOption();
    this.monthOption();
    window.addEventListener('resize', () => {
      this.situationData.length > 0 && this.situationEcharts.resize();
      this.costData.x.length > 0 && this.costEcharts.resize();
      this.typeData.length > 0 && this.typeEcharts.resize();
    });
  },
  methods: {
    //获取用户列表
    async getListByRoleAndPower(roleId) {
      let id = this.$store.getters.getActiveIndex;
      let { code, data } = await getListByRoleAndPower(roleId, id);
      if (code == '0000') {
        if (roleId == 1) {
          this.undertakeList = data;
        }
        if (roleId == 2) {
          this.userList = data;
        }
      }
    },
    // 获取地区列表
    async getAreaByPowerId() {
      let id = this.$store.getters.getActiveIndex;
      let { code, data } = await getAreaByPowerId(id);
      if (code == '0000') {
        this.areaList = data;
      }
    },
    // 根据用户id获取列表
    getRoleIdList() {
      if (this.thisUserId == 3) {
        this.getListByRoleAndPower(1);
        this.getListByRoleAndPower(2);
      } else if (this.thisUserId == 2) {
        this.getListByRoleAndPower(1);
      } else {
        this.getListByRoleAndPower(2);
      }
    },

    //获取项目
    async getProjectListByModel() {
      let id = this.$store.getters.getActiveIndex;
      let { code, data } = await getProjectListByModel(id);
      if (code == '0000') {
        this.projectList = data;
      }
    },
    // selectBridge() {
    //   this.baiduOptions.map((text, index) => {
    //     if (text.id == this.baiduValue) {
    //       this.center.lng = text.lng;
    //       this.center.lat = text.lat;
    //       this.zoom = text.zoom;
    //       return;
    //     }
    //   });
    // },
    //获取结构物
    async selectBridge(index) {
      if (index && index == 1) {
        this.$refs.cascaderHandle.dropDownVisible = false; //监听值发生变化就关闭它
      }
      let params = {
        provinceId: this.areaValue[0] || '',
        cityId: this.areaValue[1] || '',
        countyId: this.areaValue[2] || '',
        unitId: this.userId,
        undertakeId: this.undertakeId,
        projectId: this.projectId,
        powerId: this.$store.getters.getActiveIndex
      };
      let { code, data } = await getStructureListByModel(params);
      if (code == '0000') {
        data.map((item) => {
          item.longitude = !!item.longitude ? item.longitude : 0;
          item.latitude = !!item.latitude ? item.latitude : 0;
          item.lnglat = [item.longitude, item.latitude];
        });
        this.structureList = data;
        if (this.structureItem != '') {
          this.structureItem = '';
        }
      }
    },
    // 选择结构物
    selectStructure(id) {
      if (id == '') {
        this.map.center = [119.306239, 26.075302];
        this.map.zoom = 7;
      } else {
        this.structureList.map((item) => {
          if (item.id == id) {
            this.map.center = [item.longitude, item.latitude];
            this.map.zoom = 15;
          }
        });
      }
      this.$nextTick(() => {
        this.queryInRect();
      });
    },

    // 查询维养桥隧数/待处理病害数/已处理病害数/年度维养总金额
    async getCount() {
      let { code, data } = await getCount();
      if (code == '0000') {
        this.structureCount = !!data.structureCount ? data.structureCount : 0;
        this.pendingCount = !!data.pendingCount ? data.pendingCount : 0;
        this.ProcessedCount = !!data.ProcessedCount ? data.ProcessedCount : 0;
        this.totalAmount = !!data.totalAmount ? data.totalAmount : 0;
      }
    },

    // 日历工作记录表
    async getWorkRecord() {
      let { code, data } = await getWorkRecord(this.years, this.months);
      if (code == '0000') {
        this.calendarButton = [];
        data.map((time) => {
          this.calendarButton.push(time.split(' ')[0]);
        });
        this.calendarButton = [...new Set(this.calendarButton)];
        let day = this.years + '-' + this.months + '-' + new Date().getDate();
        let obj = {
          day:
            this.calendarButton.length > 0
              ? this.calendarButton[this.calendarButton.length - 1]
              : this.$utils.Dateformat1(new Date(day))
        };
        this.calendarClick(obj);
      }
    },

    // 获取当天工作记录
    async getDateRecord(date) {
      let { code, data } = await getDateRecord(date);
      if (code == '0000') {
        this.tableData = data;
      }
    },

    //维修病害数
    async getdiseaseCount() {
      let { code, data } = await getdiseaseCount();
      if (code == '0000') {
        this.situationData = [
          {
            name: '已修',
            value: data.ProcessedCount
          },
          {
            name: '未修',
            value: data.pendingCount
          }
        ];
        this.$nextTick(() => {
          this.situationEchartPoint(this.situationData);
        });
      }
    },

    //维修类型分布
    async gettypeRatio() {
      let { code, data } = await gettypeRatio();
      if (code == '0000') {
        let arry = [];
        data.map((item) => {
          let obj = {
            name: item.type,
            value: item.count
          };
          arry.push(obj);
        });
        this.typeData = arry;
        this.$nextTick(() => {
          if (arry.length > 0) this.typeEchartPoint(this.typeData);
        });
      }
    },

    // 维修费用趋势
    async accountTrend() {
      let { code, data } = await accountTrend(this.years, this.months);
      if (code == '0000') {
        let x = [],
          y1 = [],
          y2 = [];
        data.map((item) => {
          x.push(item.monthOfYear.split('-')[1]);
          y1.push(item.budget);
          y2.push(item.expenditure);
        });
        this.costData.x = x;
        this.costData.y1 = y1;
        this.costData.y2 = y2;
        this.$nextTick(() => {
          if (x.length > 0) this.costEchartPoints(this.costData);
        });
      }
    },

    returnDate() {
      this.dateValue = this.years + '-' + this.months;
      this.getWorkRecord();
    },

    // 日历点击事件
    calendarClick(data) {
      this.calendarDate = data.day;
      let month = parseInt(data.day.split('-')[1]);
      let year = parseInt(data.day.split('-')[0]);
      this.years = year;
      this.months = month;
      let is = false;
      this.calendarButton.map((text, index) => {
        if (text == data.day) {
          is = true;
        }
      });
      if (is == true) {
        this.getDateRecord(data.day);
      } else {
        this.tableData = [];
      }
    },

    yearsOption() {
      let nowYear = new Date().getFullYear();
      for (let index = nowYear; index > nowYear - 20; index--) {
        let model = {
          id: index,
          label: index + '年'
        };
        this.yearsOptions.push(model);
      }
    },
    monthOption() {
      for (let index = 1; index <= 12; index++) {
        let model = {
          id: index,
          label: index + '月'
        };
        this.monthOptions.push(model);
      }
    },

    // 地图加载完成后执行
    onMapComplete(map) {
      map.setFitView();
    },
    // 地图缩放停止时触发
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
    costEchartPoints(costData) {
      if (document.getElementById('costEchart')) {
        this.costEcharts = echarts.init(document.getElementById('costEchart'));
        let option = {
          tooltip: {
            trigger: 'axis',
            confine: true,
            axisPointer: {
              type: 'cross',
              label: {
                backgroundColor: '#6a7985'
              }
            }
          },
          legend: {
            // show: showed,
            data: ['计划', '实际'],
            left: '55%',
            itemGap: 10 * this.multiple,
            itemWidth: 16 * this.multiple,
            itemHeight: 8
          },
          grid: {
            left: '2%',
            right: '2%',
            bottom: '0%',
            top: '20%',
            containLabel: true
          },
          xAxis: [
            {
              type: 'category',
              boundaryGap: false,
              splitLine: { show: false },
              axisLabel: { color: '#595959', fontSize: 6 * this.multiple },
              axisTick: { inside: true, lineStyle: { color: '#BFBFBF' } },
              axisLine: { lineStyle: { color: '#BFBFBF' } },
              splitArea: { show: false },
              data: costData.x
            }
          ],
          yAxis: [
            {
              type: 'value',
              splitLine: {
                lineStyle: { width: 1, type: 'dashed', color: '#dedede' }
              },
              axisLabel: { color: '#595959', fontSize: 7 * this.multiple },
              axisTick: { show: false },
              axisLine: { show: false },
              splitArea: { show: false }
            }
          ],
          color: ['#FF5F5F', '#419AFF'],
          series: [
            {
              name: '计划',
              type: 'line',
              areaStyle: {
                color: 'rgba(255, 95, 95, 0.25)'
              },
              emphasis: {
                focus: 'series'
              },
              data: costData.y1
            },
            {
              name: '实际',
              type: 'line',
              areaStyle: {
                color: 'rgba(65, 154, 255, 0.1)'
              },
              emphasis: {
                focus: 'series'
              },
              data: costData.y2
            }
          ]
        };
        this.costEcharts.setOption(option);
      }
    },
    situationEchartPoint(situationData) {
      let showed = situationData.length > 0 ? true : false;
      if (showed && document.getElementById('situationEchart')) {
        let total = 0;
        let value = 0;
        let result = 0;
        situationData.map((text, index) => {
          if (text.name == '已修') {
            value = text.value;
          }
          total += text.value;
        });
        result = total == 0 ? 0 + '%' : Math.round((value / total) * 100) + '%';
        this.situationEcharts = echarts.init(
          document.getElementById('situationEchart')
        );
        let option = {
          tooltip: {
            trigger: 'item'
          },
          legend: {
            top: 'center',
            left: '55%',
            orient: 'vertical',
            icon: 'circle',
            itemGap: 10 * this.multiple,
            itemWidth: 8,
            itemHeight: 8,
            formatter: function (name) {
              let target;
              situationData.map((text, index) => {
                if (name == text.name) {
                  target = text.value;
                }
              });
              let arr = ['{left|' + name + '}{right| ' + target + '}'];
              return arr.join();
            },
            textStyle: {
              rich: {
                right: {
                  fontSize: 7 * this.multiple,
                  color: '#595959',
                  with: 50
                },
                left: {
                  fontSize: 7 * this.multiple,
                  color: '#595959',
                  padding: [0, 5],
                  width: 40 * this.multiple
                }
              }
            }
          },
          color: ['#419AFF', '#FF5F5F'],
          series: [
            {
              name: '维修病害数',
              type: 'pie',
              center: ['25%', '50%'],
              radius: ['70%', '80%'],
              // hoverAnimation: false,
              label: {
                show: false,
                position: 'center'
              },
              itemStyle: {
                normal: {
                  borderWidth: 2,
                  borderColor: '#fff'
                }
              },
              //圆环中间样式
              label: {
                normal: {
                  show: true,
                  position: 'center',
                  fontSize: '14',
                  color: 'black',
                  fontStyle: 'normal',
                  fontWeight: 'normal',
                  padding: [10, 0, 0, 0],
                  formatter: function (parms) {
                    let arr = [
                      '{left|' + '维养率' + '}\n{right| ' + result + '}'
                    ];
                    return arr.join();
                  },
                  textStyle: {
                    rich: {
                      right: {
                        fontSize: 16 * this.multiple,
                        color: '#333333;'
                      },
                      left: {
                        fontSize: 7 * this.multiple,
                        color: '#595959',
                        padding: [10, 0, 0, 0]
                      }
                    }
                  }
                }
              },
              labelLine: {
                show: false
              },
              data: this.situationData
            }
          ]
        };
        // 使用刚指定的配置项和数据显示图表。
        this.situationEcharts.setOption(option);
      }
    },
    typeEchartPoint(typeData) {
      // let showed = typeData.length ? true : false;
      if (document.getElementById('typeEchart')) {
        let total = 0;
        typeData.map((text, index) => {
          total += text.value;
        });

        this.typeEcharts = echarts.init(document.getElementById('typeEchart'));
        let option = {
          tooltip: {
            trigger: 'item'
          },
          legend: {
            // show: showed,
            top: 'center',
            left: '55%',
            orient: 'vertical',
            icon: 'circle',
            itemGap: 10 * this.multiple,
            itemWidth: 8,
            itemHeight: 8,
            formatter: function (name) {
              let target;
              typeData.map((text, index) => {
                if (name == text.name) {
                  target = text.value;
                }
              });
              let arr = ['{left|' + name + '}{right| ' + target + '}'];
              return arr.join();
            },
            textStyle: {
              rich: {
                right: {
                  fontSize: 7 * this.multiple,
                  color: '#595959'
                },
                left: {
                  fontSize: 7 * this.multiple,
                  color: '#595959',
                  padding: [0, 5],
                  width: 40 * this.multiple
                }
              }
            }
          },
          color: ['#419AFF', '#38ECD9', '#FFD45F', '#FF5F5F'],
          series: [
            {
              name: '维修类型分布',
              type: 'pie',
              center: ['25%', '50%'],
              radius: ['70%', '80%'],
              // hoverAnimation: false,
              label: {
                show: false,
                position: 'center'
              },
              itemStyle: {
                normal: {
                  borderWidth: 2,
                  borderColor: '#fff'
                }
              },
              //圆环中间样式
              label: {
                normal: {
                  show: true,
                  position: 'center',
                  fontSize: '14',
                  color: 'black',
                  fontStyle: 'normal',
                  fontWeight: 'normal',
                  padding: [10, 10, 0, 0],
                  formatter: function (parms) {
                    let arr = [
                      '{left|' + '维养数' + '}\n{right| ' + total + '}'
                    ];
                    return arr.join();
                  },
                  textStyle: {
                    rich: {
                      right: {
                        fontSize: 16 * this.multiple,
                        color: '#333333;'
                      },
                      left: {
                        fontSize: 7 * this.multiple,
                        color: '#595959',
                        padding: [10, -10, 0, 0]
                      }
                    }
                  }
                }
              },
              labelLine: {
                show: false
              },
              data: this.typeData
            }
          ]
        };
        // 使用刚指定的配置项和数据显示图表。
        this.typeEcharts.setOption(option);
      }
    },

    goReport(id) {
      this.showBriefing = true;
      this.reportId = id;
      this.parentName = '维养任务总览';
      // this.$router.push({
      //   path: '/maintain/briefReport',
      //   query: { id, parentName: '维养任务总览' }
      // });
    },

    clickMarker(item) {
      // let name = '维养记录';
      // this.$store.dispatch('asyncUpdateActiveIndex', 19);
      // this.$router.push({
      //   name
      // });
    },
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex % 2 === 0) {
        return 'warning-row';
      } else {
        return 'success-row';
      }
    }
  }
};
</script>
<style lang="scss" scoped>
.maintain {
  background: transparent !important;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
.maintain /deep/.el-calendar__title {
  font-size: 0.83vw;
}
.maintain /deep/.el-calendar-table thead th {
  font-size: 0.83vw;
}
.maintain /deep/.el-calendar-table .el-calendar-day {
  height: 4.4444vh;
  width: 63px;
  padding: 0;
}
.maintain /deep/.el-calendar__body {
  padding: 0;
  margin: 0;
}
.maintain .anchorBL {
  display: none;
}
.maintain /deep/.el-calendar__header {
  display: none;
}
.maintain /deep/.el-table .warning-row {
  background: #f2f4f6;
  padding: 0 !important;
  height: 4.074vh;
}

.maintain /deep/.el-table .success-row {
  background: #ffffff;
  padding: 0 !important;
  height: 4.074vh;
}
.maintain /deep/ .el-table thead {
  color: #333;
}
.maintain .el-table th > .cell {
  font-family: Microsoft JhengHei UI;
  font-style: normal;
  font-weight: bold;
  font-size: 0.729vw;
}
.maintain /deep/.table_th {
  padding: 0 !important;
  font-family: Microsoft JhengHei UI;
  font-style: normal;
  font-weight: bold;
  border: 0;
}
.maintain /deep/ .table_tr {
  font-family: Microsoft JhengHei UI;
  font-style: normal;
  font-weight: normal;
  padding: 0 !important;
  height: 4.074vh;
  line-height: 4.074vh;
  border: 0;
}

.maintain .above {
  display: flex;
  justify-content: space-between;
  height: 11.853vh;
  .above_div1 {
    display: flex;
    justify-content: space-between;
    width: 451px;
    border-radius: 0.739vh;
    padding: 2.41vh 44px 2.22vh 52px;
    .left {
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      .my_p {
        font-size: 16px;
        color: #fafafb;
      }
      .my_p2 {
        font-size: 44px;
        color: #fafafb;
        span {
          font-size: 32px;
        }
      }
    }
    .right {
      border-radius: 0.739vh;
      background: rgba(255, 255, 255, 0.12);
      border-radius: 50%;
      width: 64px;
      height: 64px;
      display: flex;
      align-items: center;
      justify-content: center;
      i {
        color: #ffffff;
        font-size: 38px;
      }
    }
  }
}
.maintain .follow {
  width: 100%;
  display: flex;
  justify-content: space-between;
  .left {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    width: 25.83vw;
    height: 70.927vh;
    background: #fff;
    border-radius: 0.739vh;
    padding: 2.2vh 24px;
    .my_top {
      display: flex;
      justify-content: space-between;
      width: 100%;
      height: 3.333vh;
      .my_span {
        font-family: PingFang SC;
        font-style: normal;
        font-weight: normal;
        font-size: 0.83vw;
        color: #262626;
      }
      .my_btn {
        display: flex;
        justify-content: space-between;
        align-items: center;
        width: 260px;
        height: 3.33vh;
        .my_select {
          width: 6.25vw;
        }
      }
    }

    .my_calendar {
      width: 100%;
      height: 32.78vh;
      .my_div {
        position: relative;
        width: 100%;
        height: 100%;
        // padding: 0.4167vw;
        .my_p {
          position: absolute;
          top: 0.74vh;
          left: 8px;
          font-size: 0.729vw;
        }
        .my_img {
          display: flex;
          justify-content: center;
          align-items: center;
          width: 16px;
          height: 1.48vh;
          position: absolute;
          bottom: 0.74vh;
          right: 8px;
          color: #419aff;
        }
      }
      .caldata {
        background: #f7fbff;
      }
      .select {
        background: #419aff;
        .my_p,
        .my_img {
          color: #fff;
        }
      }
      /deep/ .el-calendar-table td.is-today {
        background: #e3efff;
      }
      .el-calendar-table .el-calendar-day:hover {
        background: transparent;
      }
    }
    .my_table2 {
      width: 23.4375vw;
    }
  }
  .middle {
    position: relative;
    width: 832px;
    height: 70.927vh;
    padding: 16px;
    background: #fff;
    border-radius: 0.739vh;
    .my_btn {
      position: absolute;
      top: 2.96vh;
      z-index: 10;
      display: flex;
      justify-content: space-around;
      width: 800px;
      height: 3.7vh;
    }
  }
  .right {
    width: 25.83vw;
    height: 70.927vh;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    .my_span {
      display: block;
      font-family: PingFang SC;
      font-style: normal;
      font-weight: normal;
      font-size: 0.83vw;
    }
    .my_top {
      width: 100%;
      height: 22.223vh;
      padding: 1.85vh 20px;
      display: flex;
      flex-direction: column;
    }
    .my_middle {
      width: 100%;
      height: 22.223vh;
      padding: 1.85vh 20px;
      display: flex;
      flex-direction: column;
    }
    .my_follow {
      width: 100%;
      height: 22.778vh;
      padding: 1.85vh 20px;
      display: flex;
      flex-direction: column;
      .my_div2 {
        height: 7.5vh;
        width: 100%;
        display: flex;
      }
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

.mapStyle {
  z-index: 10;
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
.maintain {
  box-shadow: none;
  background: transparent;
}
.border_background {
  background: linear-gradient(90deg, #4180ff 0%, #41a8ff 100%);
  box-shadow: 0px 12px 24px rgba(65, 154, 255, 0.25);
}
.border_background2 {
  background: linear-gradient(90deg, #ff7f2f 0%, #ff7353 100%) !important;
  box-shadow: 0px 12px 24px rgba(255, 123, 60, 0.25);
}
.border_background3 {
  background: linear-gradient(90deg, #10da74 0%, #27d6b7 100%) !important;
  box-shadow: 0px 12px 24px rgba(26, 217, 144, 0.25);
}
.border_background4 {
  background: linear-gradient(90deg, #4180ff 0%, #41a8ff 100%) !important;
  box-shadow: 0px 12px 24px rgba(65, 154, 255, 0.25);
}

.baidu_select_one {
  width: 120px;
  height: 100%;
}
.baidu_select_two {
  width: 120px;
  height: 100%;
}
.baidu_select_three {
  width: 160px;
  height: 100%;
}
.baidu_select_four {
  width: 184px;
  height: 100%;
}
.baidu_select_five {
  width: 120px;
  height: 100%;
  /deep/ .el-select-dropdown {
    left: 0 !important;
    max-width: 200px;
  }
}
.border_ec {
  width: 100%;
  height: 100%;
  flex: 1;
  overflow: hidden;
}
.border_ec2 {
  width: 100%;
  height: 19.852vh;
}
#situationEchart {
  width: 100%;
  height: 100%;
}
#typeEchart {
  width: 100%;
  height: 100%;
}
#costEchart {
  width: 100%;
  height: 100%;
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
