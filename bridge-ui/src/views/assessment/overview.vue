<template>
  <div class="overview">
    <div class="left">
      <div class="left_top animate__animated animate__fadeInDown">
        <div class="left_top_left">
          <div class="left_top_left_top">
            <div class="left_top_left_top_div bgColor1">
              <div class="text">
                <p class="my_p">检测项目数</p>
                <p class="my_p2">
                  {{ projectNumber }}
                </p>
              </div>
              <span>
                <i class="iconfont icon-xiangmu"></i>
              </span>
            </div>

            <div class="left_top_left_top_div bgColor2">
              <div class="text">
                <p class="my_p">检测桥隧数</p>
                <p class="my_p2">
                  {{ bridgeNumber }}
                </p>
              </div>
              <span>
                <i class="iconfont icon-shuliang"></i>
              </span>
            </div>
          </div>
          <div class="boxShadow left_top_left_follow">
            <div class="tool">
              <span class="my_span">构件类型病害统计</span>
              <div>
                <el-select
                  v-model="bridgeTypeId"
                  placeholder="全部桥型"
                  class="my_select"
                  clearable
                  @change="getComponent(true)"
                >
                  <el-option
                    v-for="item in bridgeTypeList"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                  >
                  </el-option>
                </el-select>
                <el-select
                  v-model="componentId"
                  placeholder="全部构件"
                  class="my_select"
                  clearable
                  @change="getDiseaseCount"
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
            </div>
            <span v-if="diseaseTypeData.x.length == 0" class="noData">
              暂无数据
            </span>
            <div v-else class="border_ec" id="diseaseTypeEchart"></div>
          </div>
        </div>

        <div class="left_top_right animate__animated animate__fadeInDown">
          <div class="boxShadow left_top_right_div">
            <span class="my_span2">城市桥隧技术状况统计</span>
            <div v-if="cityData.length == 0" class="noData">暂无数据</div>
            <div v-else class="border_ec2" id="cityEchart"></div>
          </div>
          <div class="boxShadow left_top_right_div">
            <span class="my_span2">公路桥隧技术状况统计</span>
            <div v-if="roadData.length == 0" class="noData">暂无数据</div>
            <div v-else class="border_ec2" id="roadEchart"></div>
          </div>
        </div>
      </div>

      <div class="boxShadow left_follow animate__animated animate__fadeInUp">
        <div class="tool">
          <span class="my_span3">桥隧评分排行</span>
          <div>
            <el-select
              v-model="bridgeLevel"
              placeholder="桥梁等级"
              class="my_select3"
              clearable
              @change="getListBridgeRank"
            >
              <el-option
                v-for="item in bridgeLevelList"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              >
              </el-option>
            </el-select>
            <el-select
              v-model="year"
              placeholder="选择年份"
              class="my_select3"
              clearable
              @change="getListBridgeRank"
            >
              <el-option
                v-for="item in yearList"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              >
              </el-option>
            </el-select>
          </div>
        </div>

        <div class="table_div">
          <el-table
            class="tableBox"
            :data="tableData"
            :row-class-name="tableRowClassName"
            @row-click="clickTable"
          >
            <el-table-column
              type="index"
              label="序号"
              width="50"
              align="center"
            >
            </el-table-column>
            <el-table-column
              prop="structureName"
              label="结构物名称"
              align="center"
              show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column prop="ratingLevel" label="桥梁评级" align="center">
            </el-table-column>
            <el-table-column
              prop="evaluationTime"
              label="检测时间"
              align="center"
            >
            </el-table-column>
            <el-table-column
              label="BCI评分"
              prop="bridgeConditionIndex"
              align="center"
              sortable
            >
              <template slot-scope="scope">
                <div
                  v-if="scope.row.bridgeConditionIndex != '/'"
                  class="score"
                  :class="{
                    success: scope.row.bridgeConditionIndex >= 80,
                    warning: scope.row.bridgeConditionIndex >= 70,
                    warning: scope.row.bridgeConditionIndex >= 60,
                    danger: scope.row.bridgeConditionIndex < 60
                  }"
                >
                  {{ scope.row.bridgeConditionIndex }}
                </div>
                <div v-else>/</div>
              </template>
            </el-table-column>
            <el-table-column prop="method" label="处置对策" align="center">
            </el-table-column>
            <el-table-column label="操作" align="center">
              <template slot-scope="scope">
                <el-link type="primary" @click="routerGo(scope.row)"
                  >详情</el-link
                >
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>
    </div>
    <div class="boxShadow right animate__animated animate__fadeIn">
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
          v-if="roleId != 2"
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
          v-if="roleId != 1 && roleId != 0"
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
      <div class="right_tips">
        <el-checkbox-group v-model="checkedCities">
          <el-checkbox
            v-for="city in cities"
            :label="city.name"
            :key="city.name"
            class="right_tips_checkbox"
          >
            <span class="right_tips_checkbox_span">{{ city.name }}</span
            ><span class="right_tips_checkbox_span2">{{
              city.value
            }}</span></el-checkbox
          >
        </el-checkbox-group>
      </div>
      <div class="mapStyle">
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
</template>

<script>
import {
  getCount,
  getDiseaseCount,
  getListBridgeRank,
  getListTechnologyStatus,
  getListBridgeType,
  getComponent
} from '@/api/assessment/overview';
import {
  getProjectListByModel,
  getStructureListByModel,
  getListByRoleAndPower,
  getArea
} from '@/api/common';
import * as echarts from 'echarts';
import { Amap, Marker, MarkerCluster } from '@amap/amap-vue';
export default {
  name: 'ovweview',
  components: {
    Amap,
    AmapMarker: Marker,
    AmapMarkerCluster: MarkerCluster
  },
  props: {},
  data() {
    return {
      roleId: this.$store.getters.getRoleInfo.id,
      //地图参数
      map: {
        zoom: 7,
        center: [119.306239, 26.075302]
      },
      markIcon: require('@/assets/images/home/icon_bridge.png'),
      defaultCursor: 'grab',
      tableData: [],
      bridgeNumber: 0,
      projectNumber: 0,
      checkedCities: ['今年已检测', '今年待检测', '今年已超期'],
      cities: [
        {
          name: '今年已检测',
          value: 26
        },
        {
          name: '今年待检测',
          value: 0
        },
        {
          name: '今年已超期',
          value: 0
        }
      ],
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

      currentPage: 1,
      pageSize: 5,
      dataTotal: 5,
      //构件类型病害统计
      bridgeTypeId: '',
      bridgeTypeList: [],
      componentId: '',
      componentList: [],

      //桥隧评分排行
      bridgeLevel: '',
      bridgeLevelList: [
        { id: 'A', name: 'A级' },
        { id: 'B', name: 'B级' },
        { id: 'C', name: 'C级' },
        { id: 'D', name: 'D级' },
        { id: 'E', name: 'E级' }
      ],
      year: '',
      yearList: [],

      diseaseTypeEcharts: [],
      diseaseTypeData: {
        x: [],
        y: []
      },
      cityEcharts: null,
      cityData: [],
      roadEcharts: null,
      roadData: [],
      multiple: window.innerHeight / 70 / 7
    };
  },
  beforeMount() {
    this.$utils.getAuthPage(this); //获取权限
  },
  mounted() {
    this.getProjectListByModel(); //获取项目列表
    this.getStructureListByModel(); //获取结构物列表
    this.getComponent(); //获取构件列表
    this.getCount(); //获取检测项目数和检测桥隧数
    this.getDiseaseCount(); //获取构建病害类型统计
    this.getListBridgeRank(); //获取桥隧评分排名
    this.getListTechnologyStatus(); //列出桥隧技术状况统计
    this.getListBridgeType(); //获取病害筛选条件
    this.getArea(); //获取区域
    this.getListByRoleAndPower(2); //获取业主
    this.getListByRoleAndPower(1); //承接单位列表
    let nowYear = new Date().getFullYear();
    for (let index = nowYear; index > nowYear - 20; index--) {
      let model = {
        id: index,
        name: index + '年'
      };
      this.yearList.push(model);
    }
  },
  methods: {
    //获取项目列表
    async getProjectListByModel() {
      let id = this.$store.getters.getActiveIndex;
      let { code, data } = await getProjectListByModel(id);
      if (code == '0000') {
        this.projectList = data;
      }
    },
    //获取构建病害数排行列表
    async getComponent(state) {
      if (state) {
        this.getDiseaseCount();
      }
      let bridgeTypeId = this.bridgeTypeId || 0;
      let { code, data } = await getComponent(bridgeTypeId);
      if (code == '0000') {
        this.componentList = data;
      }
    },
    //获取区域
    async getArea() {
      let powerId = this.$store.getters.getActiveIndex;
      let { code, data } = await getArea(powerId);
      if (code == '0000') {
        this.areaList = data;
      }
    },
    //获取业主、承接单位列表
    async getListByRoleAndPower(index) {
      if (index == this.roleId) {
        return;
      }
      let powerId = this.$store.getters.getActiveIndex;
      let { code, data } = await getListByRoleAndPower(index, powerId);
      if (code == '0000') {
        if (index == 2) {
          this.userList = data;
        } else {
          this.undertakeList = data;
        }
      }
    },
    //获取检测项目数和检测桥隧数
    async getCount() {
      let { code, data } = await getCount();
      if (code == '0000') {
        this.projectNumber = data.project || 0;
        this.bridgeNumber = data.structure || 0;
      }
    },
    //获取构建病害类型统计
    async getDiseaseCount() {
      let params = {
        bridgeTypeId: this.bridgeTypeId,
        componentId: this.componentId
      };
      let { code, data } = await getDiseaseCount(params);
      if (code == '0000') {
        let xData = [];
        let yData = [];
        data.map((item) => {
          xData.push(item.name);
          yData.push(item.count);
        });
        this.diseaseTypeData.x = xData;
        this.diseaseTypeData.y = yData;
        this.$nextTick(() => {
          if (xData.length > 0) {
            this.diseaseTypeEchartPoint(this.diseaseTypeData);
          }
        });
      }
    },
    //获取桥隧评分排名
    async getListBridgeRank() {
      let params = {
        pageSize: 1000,
        pageNum: 1,
        year: this.year,
        bridgeLevel: this.bridgeLevel
      };
      let { code, data } = await getListBridgeRank(params);
      if (code == '0000') {
        data.list.map((item) => {
          if (item.ratingLevel == 'A') {
            item.method = '日常保养';
          } else if (item.ratingLevel == 'B') {
            item.method = '保养小修';
          } else if (item.ratingLevel == 'C') {
            item.method = '针对性小修';
          } else if (item.ratingLevel == 'D') {
            item.method = '局部中修';
          } else if (item.ratingLevel == 'E') {
            item.method = '大修或加固';
          } else {
            item.method = '/';
          }
        });
        this.tableData = data.list;
      }
    },
    //列出桥隧技术状况统计
    async getListTechnologyStatus() {
      let { code, data } = await getListTechnologyStatus();
      if (code == '0000') {
        let city = [];
        let road = [];
        if (data.city) {
          data.city.map((item) => {
            let obj = {
              value: item.count,
              name: item.grade
            };
            city.push(obj);
          });
        }
        if (data.road) {
          data.road.map((item) => {
            let obj = {
              value: item.count,
              name: item.grade
            };
            road.push(obj);
          });
        }
        this.cityData = city;
        this.roadData = road;
        this.$nextTick(() => {
          if (city.length > 0) {
            this.cityEchartPoint(city);
          }
          if (road.length > 0) {
            this.roadEchartPoint(road);
          }
        });
      }
    },
    //获取结构物列表
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
      }
    },
    //获取病害筛选条件
    async getListBridgeType() {
      let { code, data } = await getListBridgeType();
      if (code == '0000') {
        this.bridgeTypeList = data;
      }
    },
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
      this.$nextTick(() => {
        this.queryInRect();
      });
    },
    routerGo(data) {
      this.$router.push({
        name: '检测记录',
        params: {
          structureInfoId: data.structureId,
          monitorStructureId: data.monitorStructureId,
          activeName: 'BCIeva',
          endTime: data.endTime,
          firstNav: 'evaluation',
          projectId: data.projectId
        }
      });
    },
    clickMarker(item) {
      this.$router.push({
        path: '/assessment/detection',
        query: {
          structureId: item.id
        }
      });
    },
    selectBridge() {
      this.baiduOptions.map((text, index) => {
        if (text.id == this.baiduValue) {
          this.center.lng = text.lng;
          this.center.lat = text.lat;
          this.zoom = text.zoom;
          return;
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
    diseaseTypeEchartPoint(diseaseTypeData) {
      this.diseaseTypeEcharts = echarts.init(
        document.getElementById('diseaseTypeEchart')
      );
      this.diseaseTypeEcharts.clear();
      let option = {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'none'
          }
        },
        grid: {
          left: '5%',
          right: '5%',
          bottom: '5%',
          top: '15%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          splitLine: { show: false },
          axisLabel: { color: '#595959', fontSize: 6 * this.size },
          axisTick: { show: false },
          axisLine: { lineStyle: { color: '#BFBFBF' } },
          splitArea: { show: false },
          data: diseaseTypeData.x
        },
        color: '#58AFFF',
        yAxis: {
          type: 'value',
          splitLine: {
            lineStyle: { width: 1, type: 'dashed', color: '#BFBFBF' }
          },
          axisLabel: { color: '#595959', fontSize: 7 * this.size },
          axisTick: { show: false },
          axisLine: { show: false },
          splitArea: { show: false }
        },
        series: [
          {
            barWidth: 20,
            data: diseaseTypeData.y,
            type: 'bar',
            label: {
              show: true,
              position: 'top'
            },
            barWidth: 20
          }
        ]
      };
      this.diseaseTypeEcharts.setOption(option);
      window.addEventListener('resize', () => {
        this.diseaseTypeEcharts.resize();
      });
    },

    cityEchartPoint(cityData) {
      let total = 0;
      cityData.map((item) => {
        total += item.value;
      });
      this.cityEcharts = echarts.init(document.getElementById('cityEchart'));
      let option = {
        tooltip: {
          //鼠标停放显示数据
          trigger: 'item',
          formatter: function (params) {
            if (params.value > 0) {
              let percent = ((params.data.value / total) * 100).toFixed(0);
              return (
                params.seriesName +
                '<br/>' +
                params.data.name +
                '： ' +
                params.data.value +
                ' (' +
                percent +
                '%)'
              );
            } else {
              return '';
            }
          }
        },
        legend: {
          top: 'center',
          left: '55%',
          orient: 'vertical',
          icon: 'circle',
          itemGap: 4 * this.multiple,
          itemWidth: 4 * this.multiple,
          itemHeight: 4 * this.multiple,
          formatter: function (name) {
            let target;
            cityData.map((text, index) => {
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
                width: 40 * this.multiple
              }
            }
          }
        },
        color: [
          '#FFD45F',
          '#FF9B5F',
          '#FF5F5F',
          '#4d38ec',
          '#3873ec',
          '#38bbec',
          '#38ecd9'
        ],
        series: [
          {
            name: '城市桥隧技术状况统计',
            type: 'pie',
            center: ['25%', '50%'],
            radius: ['60%', '70%'],
            label: {
              normal: {
                position: 'center',
                formatter: '{text1|桥隧数}\n{text2|' + total + '}',
                rich: {
                  text1: {
                    fontSize: 7 * this.multiple,
                    color: '#595959',
                    fontWeight: '100',
                    padding: [7, 0]
                  },
                  text2: {
                    fontSize: 16 * this.multiple,
                    fontWeight: '400',
                    color: '#333'
                  }
                }
              }
            },
            labelLine: {
              show: false
            },
            data: this.cityData
          }
        ]
      };
      this.cityEcharts.setOption(option);
      window.addEventListener('resize', () => {
        this.cityEcharts.resize();
      });
    },

    roadEchartPoint(roadData) {
      let total = 0;
      roadData.map((item) => {
        total += item.value;
      });
      this.roadEcharts = echarts.init(document.getElementById('roadEchart'));
      let option = {
        tooltip: {
          //鼠标停放显示数据
          trigger: 'item',
          formatter: function (params) {
            if (params.value > 0) {
              let percent = ((params.data.value / total) * 100).toFixed(0);
              return (
                params.seriesName +
                '<br/>' +
                params.data.name +
                '： ' +
                params.data.value +
                ' (' +
                percent +
                '%)'
              );
            } else {
              return '';
            }
          }
        },
        legend: {
          top: 'center',
          left: '55%',
          orient: 'vertical',
          icon: 'circle',
          itemGap: 6 * this.multiple,
          itemWidth: 4 * this.multiple,
          itemHeight: 4 * this.multiple,
          formatter: function (name) {
            let target;
            roadData.map((text, index) => {
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
                width: 40 * this.multiple
              }
            }
          }
        },
        color: ['#FFD45F', '#FF9B5F', '#FF5F5F', '#419AFF', '#38ECD9'],
        series: [
          {
            name: '公路桥隧技术状况统计',
            type: 'pie',
            center: ['25%', '50%'],
            radius: ['60%', '70%'],
            label: {
              normal: {
                position: 'center',
                formatter: '{text1|桥隧数}\n{text2|' + total + '}',
                rich: {
                  text1: {
                    fontSize: 7 * this.multiple,
                    color: '#595959',
                    fontWeight: '100',
                    padding: [7, 0]
                  },
                  text2: {
                    fontSize: 16 * this.multiple,
                    fontWeight: '400',
                    color: '#333'
                  }
                }
              }
            },
            labelLine: {
              show: false
            },
            data: this.roadData
          }
        ]
      };
      this.roadEcharts.setOption(option);
      window.addEventListener('resize', () => {
        this.roadEcharts.resize();
      });
    },
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex % 2 == 1) {
        return 'first-row';
      } else {
        return 'second-row';
      }
    },
    clickTable(row, column, event) {
      this.map.center = [row.lng, row.lat];
      this.map.zoom = row.zoom;
    }
  }
};
</script>
<style lang="scss" scoped>
.overview {
  box-shadow: none;
  background: transparent !important;
  display: flex;
  align-items: center;
  justify-content: space-between;
  .left {
    width: 1012px;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    .left_top {
      height: 44.445vh;
      width: 100%;
      display: flex;
      justify-content: space-between;
      .left_top_left {
        height: 100%;
        width: 592px;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        .left_top_left_top {
          height: 8.8889vh;
          width: 100%;
          display: flex;
          align-items: center;
          justify-content: space-between;
          .left_top_left_top_div {
            width: 286px;
            height: 100%;
            padding: 16px 32px;
            border-radius: 8px;
            display: flex;
            align-items: center;
            justify-content: space-between;
            .text {
              color: #fff;
              display: flex;
              flex-direction: column;
              .my_p {
                font-size: 16px;
              }
              .my_p2 {
                font-size: 32px;
              }
            }
            span {
              color: #fff;
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
          .bgColor1 {
            background: linear-gradient(90deg, #4180ff 0%, #41a8ff 100%);
            box-shadow: 0px 12px 24px rgba(65, 154, 255, 0.25);
          }
          .bgColor2 {
            background: linear-gradient(
              90deg,
              #ff7f2f 0%,
              #ff7353 100%
            ) !important;
            box-shadow: 0px 12px 24px rgba(255, 123, 60, 0.25);
          }
        }
        .left_top_left_follow {
          height: 33.705vh;
          padding: 24px;
          width: 100%;
          display: flex;
          flex-direction: column;
          .tool {
            display: flex;
            justify-content: space-between;
            div {
              display: flex;
              align-items: center;
              .my_select {
                width: 160px;
                margin-left: 20px;
              }
            }
          }
          .border_ec {
            flex: 1;
          }
        }
      }
      .left_top_right {
        height: 100%;
        width: 400px;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        .left_top_right_div {
          width: 100%;
          height: 21.297vh;
          padding: 24px 24px 0;
          display: flex;
          flex-direction: column;
          .border_ec2 {
            flex: 1;
          }
        }
      }
    }
    .left_follow {
      height: 38.334vh;
      width: 100%;
      padding: 24px 24px;
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      .tool {
        display: flex;
        justify-content: space-between;
        .my_select3 {
          width: 160px;
          margin-left: 20px;
        }
      }
      .table_div {
        height: 29.168vh;
        .score {
          width: 56px;
          height: 2.223vh;
          border-radius: 4px;
          margin: 0 auto;
          display: flex;
          align-items: center;
          justify-content: center;
        }
        .danger {
          color: #fff;
          background: #f5222d;
        }
        .warning {
          color: #fff;
          background: #fa5a16;
        }
        .yellow {
          color: #fff;
          background: #f39836;
        }
        .success {
          color: #fff;
          background: #27ae60;
        }
        /deep/ .el-table__body-wrapper {
          max-height: 25vh;
          overflow: auto;
          &:hover {
            &::-webkit-scrollbar {
              width: 6px;
            }
            &::-webkit-scrollbar-thumb {
              background: #c4c4c4;
              border-radius: 8px;
            }
          }
        }
        /deep/ .el-table__empty-block {
          border-top: 1px solid #ebeef5;
        }
        /deep/ .el-table th {
          font-size: 14px;
          font-weight: bold;
          color: #333;
          padding: 0.464vh 0;
          border: 0;
        }
        /deep/ .el-table td {
          font-size: 14px;
          color: #333;
          padding: 0.927vh 0;
          border: 0;
          &:first-child {
            border-top-left-radius: 5px;
            border-bottom-left-radius: 5px;
          }
          &:last-child {
            border-top-right-radius: 5px;
            border-bottom-right-radius: 5px;
          }
        }
        /deep/ .el-table .cell {
          line-height: unset;
        }
      }
    }
  }
  .right {
    position: relative;
    width: 832px;
    height: 100%;
    padding: 16px;
    .my_btn {
      position: absolute;
      top: 31px;
      width: 800px;
      display: flex;
      justify-content: space-evenly;
      align-items: center;
      z-index: 10;
      .my_select {
        width: 6.25vw;
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
          max-width: 150px;
        }
      }
    }
    .mapStyle {
      width: 100%;
      height: 100%;
      .markTextHide {
        /deep/ .amap-marker-label {
          display: none;
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
    .right_select {
      width: 8.333vw;
      height: 3.7037vh;
      position: absolute;
      right: 36px;
      top: 36px;
      z-index: 10;
    }
    .right_tips {
      display: none;
      width: 10.4166vw;
      height: 14.8148vh;
      background: white;
      position: absolute;
      z-index: 999;
      margin-top: 65vh;
      margin-left: 26.4583vw;
      .right_tips_checkbox {
        margin-top: 2.2222vh;
        margin-left: 1.25vw;
        .right_tips_checkbox_span {
          color: #595959;
          font-weight: normal;
          font-size: 0.8333vw;
        }
        .right_tips_checkbox_span2 {
          font-weight: bold;
          font-size: 0.8333vw;
          color: #333333;
          margin-left: 1.77083vw;
        }
      }
    }
  }
}

.overview .pageNation {
  text-align: center;
  padding: 0;
  // padding-bottom: 0.927vh;
  /deep/ button {
    margin: 0 0.2604vw;
    background: transparent;
    border: 1px solid #d9d9d9;
    border-radius: 2px;
    background: none !important;
  }
  /deep/ .el-pager li {
    margin: 0 0.2604vw;
    background: transparent;
    border: 1px solid #d9d9d9;
    border-radius: 2px;
  }
  /deep/ .el-pager .active {
    background: none !important;
    color: #1890ff !important;
    border: 1px solid #1890ff;
  }
}

.overview .bm-view {
  width: 37.916vw;
  height: 81.6667vh;
  margin: 1.481vh 0.8333vw 1.481vh 0.8333vw;
}
.overview .anchorBL {
  display: none;
}

.overview .table_button {
  width: 2.775vw;
  height: 2.3222vh;
  .table_span {
    position: absolute;
    left: 1vw;
    top: 1.5vh;
  }
}

//空数据样式
.noData {
  width: 100%;
  flex: 1;
  font-size: 28px;
  color: #333;
  display: flex;
  align-items: center;
  justify-content: center;
}

//斑马纹样式
/deep/ .first-row {
  background: #ffffff;
}
/deep/ .second-row {
  background: #f2f4f6;
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
