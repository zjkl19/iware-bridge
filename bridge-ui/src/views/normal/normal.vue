<template>
  <div class="normal">
    <div class="above animate__animated animate__fadeInDown">
      <div class="border_one above_div1">
        <div class="left">
          <span class="my_p">总巡查次数</span>
          <span class="my_p2">
            {{ totalCount }}
          </span>
        </div>
        <div class="right">
          <span><i class="iconfont icon-jihua"></i></span>
        </div>
      </div>

      <div class="border_two above_div1">
        <div class="left">
          <span class="my_p">当月巡查次数</span>
          <span class="my_p2">
            {{ monthCount }}
          </span>
        </div>
        <div class="right">
          <span><i class="iconfont icon-xuncha"></i></span>
        </div>
      </div>

      <div class="border_three above_div1">
        <div class="left">
          <span class="my_p">巡查病害数</span>
          <span class="my_p2">
            {{ diseaseCount }}
          </span>
        </div>
        <div class="right">
          <span><i class="iconfont icon-maintain"></i></span>
        </div>
      </div>

      <div class="border_four above_div1">
        <div class="left">
          <span class="my_p">巡查桥隧数</span>
          <span class="my_p2">
            {{ structureCount }}
          </span>
        </div>
        <div class="right">
          <span><i class="iconfont icon-qiao"></i></span>
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
              placeholder="请选择年份"
              @change="selectCardDate"
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
              placeholder="请选择月份"
              @change="selectCardDate"
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
                <i class="iconfont icon-shoudian"></i>
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
            <template slot="empty">
              <div>当天无数据</div>
            </template>
            <el-table-column
              label="巡查细项"
              min-width="60%"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <div class="txtHide">
                  {{
                    scope.row.structureName +
                    scope.row.inspectionTime +
                    scope.row.inspectionPlanType
                  }}
                </div>
              </template>
            </el-table-column>
            <el-table-column
              prop="inspectionPlanType"
              label="巡查类型"
              min-width="25%"
            >
            </el-table-column>
            <el-table-column label="操作" prop="url" min-width="15%">
              <template slot-scope="scope">
                <el-link type="primary" @click="routerGo(scope.row)"
                  >查看</el-link
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
            @change="getStructureListByModel(1)"
            clearable
          ></el-cascader>

          <el-select
            v-if="thisUserId != 2"
            v-model="userId"
            :popper-append-to-body="false"
            placeholder="全部业主"
            class="baidu_select_two"
            @change="getStructureListByModel"
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
            :popper-append-to-body="false"
            placeholder="全部承接单位"
            class="baidu_select_three"
            @change="getStructureListByModel"
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
            :popper-append-to-body="false"
            placeholder="全部项目"
            class="baidu_select_four"
            @change="getStructureListByModel"
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
            class="baidu_select_five"
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
            :icon="item.structureType == 1 ? markIcon : markIcon2"
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
                  <div class="ring" :class="getClusterStyle3(context)"
              :style="getClusterStyle4(context)"></div>
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
          <span class="my_span">主要病害统计</span>
          <div class="border_ec">
            <span v-if="mainDiseaseData.length == 0" class="noData"
              >暂无数据</span
            >
            <div id="mainDiseaseEchart"></div>
          </div>
        </div>

        <div class="boxShadow my_middle">
          <span class="my_span">养护等级统计</span>
          <div class="border_ec">
            <span v-if="levelData.length == 0" class="noData">暂无数据</span>
            <div id="levelEchart"></div>
          </div>
        </div>

        <div class="boxShadow my_follow">
          <div class="my_div2">
            <span class="my_span">巡查病害数变化</span>
            <div class="my_right">
              <el-select
                v-model="changeYear"
                placeholder="年份"
                @change="changeSelect"
                class="my_div2_select"
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
                v-model="changeMonth"
                placeholder="月份"
                @change="changeSelect"
                class="my_div2_select2"
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
          <div class="border_long_ec">
            <span v-if="changeData.x.length == 0" class="noData">暂无数据</span>
            <div id="changeEchart"></div>
          </div>
        </div>
      </div>
    </div>

    <div v-if="showBriefing == 1">
      <bridgeBriefing
        :reportId="reportId"
        :parentName="parentName"
        :structureId="briefingId"
        @closed="showBriefing = 0"
      ></bridgeBriefing>
    </div>
    <div v-if="showBriefing == 2">
      <tunnelBriefing
        :reportId="reportId"
        :parentName="parentName"
        :structureId="briefingId"
        @closed="showBriefing = 0"
      ></tunnelBriefing>
    </div>
  </div>
</template>
<script>
import {
  getCount,
  getDiseaseRatio,
  getDiseaseTrend,
  getGradeRatio,
  getWorkRecord
} from '@/api/normal/normal';
import { getDateRecord } from '@/api/normal/record';
import {
  getProjectListByModel,
  getStructureListByModel,
  getListByRoleAndPower,
  getAreaByPowerId
} from '@/api/common';
import * as echarts from 'echarts';
import { Amap, Marker, MarkerCluster  } from '@amap/amap-vue';
import bridgeBriefing from './bridgeBriefing';
import tunnelBriefing from './tunnelBriefing';
export default {
  name: 'normalPage',
  components: {
    Amap,
    AmapMarker: Marker,
    AmapMarkerCluster: MarkerCluster,
    bridgeBriefing,
    tunnelBriefing
  },
  data() {
    const _this = this;
    return {
      thisUserId: this.$store.getters.getRoleInfo.id,
      //地图数据
      map: {
        zoom: 7,
        center: [119.306239, 26.075302]
      },
      defaultCursor: 'grab',
      markIcon: require('@/assets/images/home/icon_bridge.png'),
      markIcon2: require('@/assets/images/home/icon_tunnel.png'),
      //查询总巡查次数/当月巡查次数/巡查病害数/巡查桥隧数
      structureCount: 0,
      diseaseCount: 0,
      totalCount: 0,
      monthCount: 0,

      //年月下拉选列表
      years: new Date().getFullYear(),
      yearsOptions: [],
      months: new Date().getMonth() + 1,
      monthOptions: [],
      changeYear: new Date().getFullYear(),
      changeMonth: new Date().getMonth() + 1,
      //高德地图选项
      areaValue: [],
      areaList: [],
      areaProps: {
        value: 'id',
        label: 'name',
        checkStrictly: true
      }, //所属区域动态加载
      userId: '',
      userList: [],
      undertakeId: '',
      undertakeList: [],
      projectId: '',
      projectList: [],
      structureItem: '',
      structureList: [], //  结构物列表

      //日历工作记录表
      tableData: [],

      levelEcharts: [], //养护等级ec图
      levelData: [], //养护等级数据

      changeEcharts: [],
      changeData: {
        x: [],
        y: []
      },

      dateValue: _this.$utils.Dateformat1(new Date()), //日历
      calendarDate: _this.$utils.Dateformat1(new Date()),
      calendarButton: [], //有维养按钮的日期
      mainDiseaseEcharts: [], //主要病害占比ec图
      mainDiseaseData: [],
      showMapLabel: false,
      multiple: window.innerHeight / 540,
      showBriefing: 0, //桥梁、隧道简报显示
      reportId: '',
      parentName: '',
      briefingId: ''
    };
  },
  beforeMount() {
    this.$utils.getAuthPage(this); //获取权限
  },
  mounted() {
    this.getRoleIdList();
    this.yearsOption();
    this.monthOption();

    this.getProjectListByModel(); //获取项目
    this.getStructureListByModel(); //获取结构物
    this.getCount(); //查询总巡查次数/当月巡查次数/巡查病害数/巡查桥隧数
    this.getDiseaseRatio(); //主要病害统计
    this.getDiseaseTrend(); //巡查病害数变化
    this.getGradeRatio(); //养护等级统计
    this.getWorkRecord(); //日历工作记录表
    this.getAreaByPowerId(); //获取地区
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
          let arr = [];
          data.map((item) => {
            arr.push(item);
          });
          this.userList = arr;
        }
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
    // 获取地区列表
    async getAreaByPowerId() {
      let id = this.$store.getters.getActiveIndex;
      let { code, data } = await getAreaByPowerId(id);
      if (code == '0000') {
        this.areaList = data;
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
    //获取结构物
    async getStructureListByModel(index) {
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
    //查询总巡查次数/当月巡查次数/巡查病害数/巡查桥隧数
    async getCount() {
      let { code, data } = await getCount();
      if (code == '0000') {
        this.structureCount = !!data.structureCount ? data.structureCount : 0;
        this.diseaseCount = !!data.diseaseCount ? data.diseaseCount : 0;
        this.totalCount = !!data.totalCount ? data.totalCount : 0;
        this.monthCount = !!data.monthCount ? data.monthCount : 0;
      }
    },
    //主要病害统计
    async getDiseaseRatio() {
      let { code, data } = await getDiseaseRatio();
      if (code == '0000') {
        let arr = [];
        data.map((item) => {
          let obj = {
            name: item.diseasePart,
            value: item.count
          };
          arr.push(obj);
        });
        this.mainDiseaseData = arr;
        this.$nextTick(() => {
          if (arr.length > 0) this.mainDiseaseEchartPoint(this.mainDiseaseData);
        });
      }
    },
    //巡查病害数统计
    async getDiseaseTrend() {
      let { code, data } = await getDiseaseTrend(
        this.changeYear,
        this.changeMonth
      );
      if (code == '0000') {
        let x = [];
        let y = [];
        data.map((item) => {
          x.push(item.dayOfMonth);
          y.push(item.count);
        });
        this.changeData = { x, y };
        this.$nextTick(() => {
          if (this.changeData.x.length) {
            this.changeEchartPoint(this.changeData);
          }
        });
      }
    },
    //养护等级占比
    async getGradeRatio() {
      let { code, data } = await getGradeRatio();
      if (code == '0000') {
        let arr = [];
        data.map((item) => {
          let obj = {
            name: item.gradeName,
            value: item.count
          };
          arr.push(obj);
        });
        this.levelData = arr;
        this.$nextTick(() => {
          this.levelData.length > 0 && this.levelEchartPoint(this.levelData);
        });
      }
    },
    //日历工作记录表
    async getWorkRecord() {
      let { code, data } = await getWorkRecord(this.years, this.months);
      if (code == '0000') {
        this.calendarButton = [];
        data.map((item) => {
          let time = item.split(' ');
          this.calendarButton.push(time[0]);
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

    //巡查病害数变化选择日期
    changeSelect() {
      this.getDiseaseTrend();
    },
    //日历工作记录表选择日期
    selectCardDate() {
      this.dateValue = this.years + '-' + this.months;
      this.getWorkRecord();
    },
    //获取近20年
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
    //获取月份
    monthOption() {
      for (let index = 1; index <= 12; index++) {
        let model = {
          id: index,
          label: index + '月'
        };
        this.monthOptions.push(model);
      }
    },
    //获取城市名
    // getCityName(arry, valList, i) {
    //   let cityName = '';
    //   arry.map((item) => {
    //     if (item.id == valList[i]) {
    //       cityName = item.name;
    //       if (valList[i + 1]) {
    //         let index = i + 1;
    //         cityName = this.getCityName(item.children, valList, index);
    //       }
    //     }
    //   });
    //   return cityName;
    // },
    selectStructure(id) {
      //选择结构物
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
    },
    // 地图图块加载完成后触发事件 暂无
    onMapComplete(map) {
      map.setFitView();
    },
    // 地图停止缩放时触发
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
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex % 2 === 0) {
        return 'warning-row';
      } else {
        return 'success-row';
      }
    },
    changeEchartPoint(changeData) {
      if (document.getElementById('changeEchart')) {
        this.changeEcharts = echarts.init(
          document.getElementById('changeEchart')
        );
        let option = {
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'none'
            }
          },
          xAxis: {
            name: '日',
            type: 'category',
            boundaryGap: false,
            nameLocation: 'end',
            nameGap: 5,
            nameTextStyle: { color: '#595959' },
            splitLine: { show: false },
            axisLabel: { color: '#595959', fontSize: 6 * this.multiple },
            axisTick: { inside: true, lineStyle: { color: '#BFBFBF' } },
            axisLine: { lineStyle: { color: '#BFBFBF' } },
            splitArea: { show: false },
            data: changeData.x
          },
          color: '#419AFF',
          yAxis: {
            name: '个',
            type: 'value',
            nameTextStyle: { color: '#595959' },
            splitLine: {
              lineStyle: { width: 1, type: 'dashed', color: '#dedede' }
            },
            axisLabel: { color: '#595959', fontSize: 7 * this.multiple },
            axisTick: { show: false },
            axisLine: { show: false },
            splitArea: { show: false }
          },
          grid: {
            left: '2%',
            right: '4%',
            bottom: '0%',
            top: '25%',
            containLabel: true
          },
          series: [
            {
              top: '10%',
              data: changeData.y,
              type: 'line',
              smooth: true,
              areaStyle: {
                color: '#ECF5FF'
              }
            }
          ]
        };
        // 使用刚指定的配置项和数据显示图表。
        this.changeEcharts.setOption(option);

        window.addEventListener('resize', () => {
          this.changeEcharts.resize();
        });
      }
    },
    levelEchartPoint(levelData) {
      // console.log(levelData);
      if (document.getElementById('levelEchart')) {
        let showed = levelData.length ? true : false;
        let total = 0;
        levelData.map((text, index) => {
          total += text.value;
        });
        this.levelEcharts = echarts.init(
          document.getElementById('levelEchart')
        );
        let option = {
          tooltip: {
            trigger: 'item'
          },
          legend: {
            show: showed,
            type: 'scroll',
            top: 'center',
            left: '55%',
            orient: 'vertical',
            icon: 'circle',
            itemGap: 10 * this.multiple,
            itemWidth: 8,
            itemHeight: 8,
            formatter: function (name) {
              let target;
              levelData.map((text, index) => {
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
                  width: 120
                }
              }
            }
          },
          color: ['#FF5F5F', '#419AFF', '#38ECD9'],
          series: [
            {
              name: '养护等级统计',
              type: 'pie',
              center: ['25%', '50%'],
              radius: ['70%', '80%'],
              // hoverAnimation: false,
              // avoidLabelOverlap: false,
              // silent: true,
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
                  position: 'center',
                  fontSize: '14',
                  color: '#000',
                  padding: [10, 0, 0, 0],
                  formatter: function (parms) {
                    let arr = [
                      '{left|' + '桥梁数' + '}\n{right| ' + total + '}'
                    ];
                    return arr.join();
                  },
                  textStyle: {
                    rich: {
                      right: {
                        fontSize: 16 * this.multiple,
                        color: '#333333;',
                        padding: [0, 10, 0, 0]
                      },
                      left: {
                        fontSize: 7 * this.multiple,
                        color: '#595959',
                        padding: [10, 0, 0, 0]
                      }
                    }
                  }
                },
                emphasis: {
                  show: false,
                  textStyle: {
                    fontSize: '30',
                    fontWeight: 'bold'
                  }
                }
              },
              labelLine: {
                show: false
              },
              data: this.levelData
            }
          ]
        };
        // 使用刚指定的配置项和数据显示图表。
        this.levelEcharts.setOption(option);

        window.addEventListener('resize', () => {
          this.levelEcharts.resize();
        });
      }
    },
    mainDiseaseEchartPoint(mainDiseaseData) {
      if (document.getElementById('mainDiseaseEchart')) {
        let showed = mainDiseaseData.length ? true : false;
        let total = 0;
        mainDiseaseData.map((text, index) => {
          total += text.value;
        });
        this.mainDiseaseEcharts = echarts.init(
          document.getElementById('mainDiseaseEchart')
        );
        let option = {
          tooltip: {
            trigger: 'item',
            confine: true
          },
          legend: {
            show: showed,
            type: 'scroll',
            pageButtonGap: 4 * this.multiple,
            pageIconSize: 6 * this.multiple,
            top: '10%',
            left: '55%',
            orient: 'vertical',
            icon: 'circle',
            itemGap: 6 * this.multiple,
            itemWidth: 8,
            itemHeight: 8,
            formatter: function (name) {
              let target;
              let newName = name;
              if (newName.length > 8) {
                newName = newName.substring(0, 8) + '...';
              }
              // let nameArry = [];
              // while (newName) {
              //   nameArry.push(newName.substr(0, 8));
              //   newName = newName.substr(8);
              // }
              // nameArry = nameArry.join('\n');
              mainDiseaseData.map((text) => {
                if (name == text.name) {
                  target = text.value;
                }
              });
              let arr = ['{left|' + newName + '}{right| ' + target + '}'];
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
                  width: 120
                }
              }
            }
          },
          color: [
            '#FF5F5F',
            '#F6903D',
            '#419AFF',
            '#F08BB4',
            '#61DDAA',
            '#F6BD16',
            '#7262fd'
          ],
          series: [
            {
              name: '主要病害统计',
              type: 'pie',
              center: ['25%', '50%'],
              radius: ['70%', '80%'],
              // hoverAnimation: false,
              // avoidLabelOverlap: false,
              // silent: true,
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
                  color: '#000',
                  padding: [10, 0, 0, 0],
                  formatter: function (parms) {
                    let arr = [
                      '{left|' + '病害数' + '}\n{right| ' + total + '}'
                    ];
                    return arr.join();
                  },
                  textStyle: {
                    rich: {
                      right: {
                        fontSize: 16 * this.multiple,
                        color: '#333333;',
                        padding: [0, 10, 0, 0]
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
              data: this.mainDiseaseData
            }
          ]
        };
        // 使用刚指定的配置项和数据显示图表。
        this.mainDiseaseEcharts.setOption(option);

        window.addEventListener('resize', () => {
          this.mainDiseaseEcharts.resize();
        });
      }
    },
    //日历点击事件
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
    routerGo(data) {
      this.reportId = data.id;
      this.parentName = '巡查任务总览';
      this.briefingId = data.structureId;
      if (data.structureType == 1) {
        this.showBriefing = 1;
      } else {
        this.showBriefing = 2;
      }
    },

    clickMarker(item) {
      // console.log(item);
      // this.$router.push({
      //   path: '/normal/record',
      //   query: {
      //     projectId: item.projectId,
      //     structureId: item.id
      //   }
      // });
    }
  }
};
</script>
<style lang="scss" scoped>
.normal {
  background: transparent !important;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
.normal /deep/.el-calendar__title {
  font-size: 0.83vw;
}
.normal /deep/.el-calendar-table thead th {
  font-size: 0.83vw;
}
.normal /deep/.el-calendar-table .el-calendar-day {
  width: 63px;
  height: 4.4444vh;
  padding: 0;
}
.normal /deep/.el-calendar__body {
  padding: 0;
  margin: 0;
}

.normal .el-table th > .cell {
  font-family: Microsoft JhengHei UI;
  font-style: normal;
  font-weight: bold;
  font-size: 0.729vw;
}
.normal /deep/ .el-table thead {
  color: #333;
}
.normal /deep/.el-table .warning-row {
  padding: 0 !important;
  height: 4.074vh;
  background: #f2f4f6;
}

.normal /deep/.el-table .success-row {
  padding: 0 !important;
  height: 4.074vh;
  background: #ffffff;
}
.normal /deep/ .table_th {
  padding: 0 !important;
  font-family: Microsoft JhengHei UI;
  font-style: normal;
  font-weight: bold;
  border: 0;
}
.normal /deep/ .table_tr {
  font-family: Microsoft JhengHei UI;
  font-style: normal;
  font-weight: normal;
  padding: 0 !important;
  height: 4.074vh;
  line-height: 4.074vh;
  border: 0;
}
.normal .anchorBL {
  display: none;
}
.normal /deep/.el-calendar__header {
  display: none;
}
.normal .above {
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
        font-family: Microsoft JhengHei UI;
        font-style: normal;
        font-weight: normal;
        font-size: 0.83vw;
        color: #fafafb;
      }
      .my_p2 {
        font-family: PingFang SC;
        font-style: normal;
        font-size: 2.29vw;
        color: #fafafb;
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
.normal .follow {
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
        // border-bottom: 1px solid #ebeef5;
        // border-right: 1px solid #ebeef5;
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
      width: 100%;
      .txtHide {
        overflow: hidden;
        text-overflow: ellipsis;
      }
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
      justify-content: space-evenly;
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
        width: 100%;
        display: flex;
        justify-content: space-between;
        .my_right {
          display: flex;
          align-items: center;
          // justify-content: space-between;
          // width: 172px;
          .my_div2_select {
            width: 100px;
            margin-right: 20px;
          }
          .my_div2_select2 {
            width: 80px;
          }
        }
      }
    }
    .my_span {
      font-family: PingFang SC;
      font-style: normal;
      font-weight: normal;
      font-size: 0.83vw;
      color: #262626;
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
.normal {
  box-shadow: none;
  background: transparent;
}
.border_one {
  background: linear-gradient(90deg, #4180ff 0%, #41a8ff 100%);
  box-shadow: 0px 12px 24px rgba(65, 154, 255, 0.25);
}
.border_two {
  background: linear-gradient(90deg, #ff7f2f 0%, #ff7353 100%) !important;
  box-shadow: 0px 12px 24px rgba(255, 123, 60, 0.25);
}
.border_three {
  background: linear-gradient(90deg, #10da74 0%, #27d6b7 100%) !important;
  box-shadow: 0px 12px 24px rgba(26, 217, 144, 0.25);
}
.border_four {
  background: linear-gradient(90deg, #4180ff 0%, #41a8ff 100%) !important;
  box-shadow: 0px 12px 24px rgba(65, 154, 255, 0.25);
}

.baidu_select_one {
  width: 120px;
  height: 100%;
  // left: 32px;
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
  flex: 1;
  width: 100%;
  height: 100%;
  overflow: hidden;
}
.border_long_ec {
  flex: 1;
  width: 100%;
  height: 100%;
  overflow: hidden;
}
#levelEchart {
  width: 100%;
  height: 100%;
}
#mainDiseaseEchart {
  width: 100%;
  height: 100%;
}
#changeEchart {
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
