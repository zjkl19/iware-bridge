<template>
  <div class="mainAna">
    <div>
      <el-select
        class="select"
        v-model="projectId"
        @change="getStructureListByModel"
        filterable
        placeholder="请选择项目"
      >
        <el-option
          v-for="item in projectList"
          :key="item.id"
          :label="item.name"
          :value="item.id"
        ></el-option>
      </el-select>
      <el-select
        class="select"
        placeholder="搜索结构物"
        filterable
        v-model="structureId"
      >
        <el-option
          v-for="item in structureList"
          :key="item.id"
          :label="item.name"
          :value="item.id"
        ></el-option>
      </el-select>

      <el-button
        class="select"
        type="primary"
        @click="getEcharts"
        :disabled="structureName == ''"
        >查询</el-button
      >
    </div>

    <div class="middle animate__animated animate__fadeInDown" id="weixiuxiao">
      <div
        v-loading.lock="loading.echart3"
        class="y-elcol-3 boxShadow"
        id="echart-stu3Cl"
      >
        <div class="echart-stu3"></div>
        <tb-empty :title="title.echart3" v-if="empty.echart3"></tb-empty>
        <div class="y-elcol-div" v-else id="echart-stu3-id"></div>
      </div>

      <div v-loading.lock="loading.echart4" class="y-elcol-echart4 boxShadow">
        <div class="echart-stu4"></div>
        <tb-empty :title="title.echart4" v-if="empty.echart4"></tb-empty>
        <div class="y-elcol-div" v-else id="echart-stu4-id"></div>
      </div>
    </div>

    <div class="middle animate__animated animate__fadeInUp">
      <div v-loading.lock="loading.echart5" class="y-elcol-echart5 boxShadow">
        <div
          class="echart-stu3"
          style="
            font-size: 24px;
            color: #464f60;
            position: absolute;
            left: 13%;
            top: 37%;
          "
        ></div>
        <tb-empty
          :title="title.echart5"
          v-if="empty.echart5"
          style="margin-top: 16px; margin-left: 10px"
        ></tb-empty>
        <div class="y-elcol-div" v-else id="echart-stu5"></div>
      </div>

      <div class="y-elcol-echart6 boxShadow">
        <div
          style="height: 106%"
          class="y-elcol-div"
          id="echart-stu6Cl"
          v-loading.lock="loading.echart6"
        >
          <tb-empty title="维修构件占比" v-if="sum_main == 0"></tb-empty>
          <div
            v-if="sum_main > 0"
            class="c-div-title"
            style="font-weight: bold"
          >
            维修构件占比
          </div>
          <div style="width: 100%; display: flex">
            <div v-if="sum_main > 0" style="width: 50%; height: 80%">
              <div
                v-if="structureType == 1"
                style="
                  width: 100%;
                  height: 100%;
                  float: left;
                  display: inline-block;
                "
              >
                <el-progress
                  :text-inside="true"
                  :stroke-width="15"
                  :percentage="maintenanceRatio.c1_1"
                  class="c-el-progress"
                ></el-progress>
                <div class="c-row-div">车行道</div>
                <el-progress
                  :text-inside="true"
                  :stroke-width="15"
                  :percentage="maintenanceRatio.c1_2"
                  class="c-el-progress"
                ></el-progress>
                <div class="c-row-div">人行道</div>
                <el-progress
                  :text-inside="true"
                  :stroke-width="15"
                  :percentage="maintenanceRatio.c1_3"
                  class="c-el-progress"
                ></el-progress>
                <div class="c-row-div">泄水孔</div>
                <el-progress
                  :text-inside="true"
                  :stroke-width="15"
                  :percentage="maintenanceRatio.c1_4"
                  class="c-el-progress"
                ></el-progress>
                <div class="c-row-div">栏杆</div>
                <el-progress
                  :text-inside="true"
                  :stroke-width="15"
                  :percentage="maintenanceRatio.c1_5"
                  class="c-el-progress"
                ></el-progress>
                <div class="c-row-div">伸缩缝装置</div>
                <el-progress
                  :text-inside="true"
                  :stroke-width="15"
                  :percentage="maintenanceRatio.c1_6"
                  class="c-el-progress"
                ></el-progress>
                <div class="c-row-div">桥、桥区施工</div>
              </div>
              <div
                v-else
                style="
                  width: 100%;
                  height: 100%;
                  float: left;
                  display: inline-block;
                "
              >
                <el-progress
                  :text-inside="true"
                  :stroke-width="15"
                  :percentage="maintenanceRatio.c1_1"
                  class="c-el-progress"
                ></el-progress>
                <div class="c-row-div">洞口</div>
                <el-progress
                  :text-inside="true"
                  :stroke-width="15"
                  :percentage="maintenanceRatio.c1_2"
                  class="c-el-progress"
                ></el-progress>
                <div class="c-row-div">衬砌</div>
                <el-progress
                  :text-inside="true"
                  :stroke-width="15"
                  :percentage="maintenanceRatio.c1_3"
                  class="c-el-progress"
                ></el-progress>
                <div class="c-row-div">检修道</div>
                <el-progress
                  :text-inside="true"
                  :stroke-width="15"
                  :percentage="maintenanceRatio.c1_4"
                  class="c-el-progress"
                ></el-progress>
                <div class="c-row-div">吊顶及各种预埋件</div>
                <el-progress
                  :text-inside="true"
                  :stroke-width="15"
                  :percentage="maintenanceRatio.c1_5"
                  class="c-el-progress"
                ></el-progress>
                <div class="c-row-div">各种标志标线轮廓标</div>
              </div>
            </div>
            <div v-if="sum_main > 0" style="width: 50%; height: 80%">
              <div
                v-if="structureType == 1"
                style="
                  width: 100%;
                  height: 100%;
                  float: left;
                  display: inline-block;
                "
              >
                <el-progress
                  :text-inside="true"
                  :stroke-width="15"
                  :percentage="maintenanceRatio.c2_1"
                  class="c-el-progress"
                ></el-progress>
                <div class="c-row-div">上部结构</div>
                <el-progress
                  :text-inside="true"
                  :stroke-width="15"
                  :percentage="maintenanceRatio.c2_2"
                  class="c-el-progress"
                ></el-progress>
                <div class="c-row-div">限载牌</div>
                <el-progress
                  :text-inside="true"
                  :stroke-width="15"
                  :percentage="maintenanceRatio.c2_3"
                  class="c-el-progress"
                ></el-progress>
                <div class="c-row-div">桥名牌</div>
                <el-progress
                  :text-inside="true"
                  :stroke-width="15"
                  :percentage="maintenanceRatio.c2_4"
                  class="c-el-progress"
                ></el-progress>
                <div class="c-row-div">桥墩、桥台、附属物</div>
                <el-progress
                  :text-inside="true"
                  :stroke-width="15"
                  :percentage="maintenanceRatio.c2_5"
                  class="c-el-progress"
                ></el-progress>
                <div class="c-row-div">
                  其他危及行车、行船、行人安全的病害因素
                </div>
                <el-progress
                  :text-inside="true"
                  :stroke-width="15"
                  :percentage="maintenanceRatio.c2_6"
                  class="c-el-progress"
                ></el-progress>
                <div class="c-row-div">其他说明</div>
              </div>
              <div
                v-else
                style="
                  width: 100%;
                  height: 100%;
                  float: left;
                  display: inline-block;
                "
              >
                <el-progress
                  :text-inside="true"
                  :stroke-width="15"
                  :percentage="maintenanceRatio.c2_1"
                  class="c-el-progress"
                ></el-progress>
                <div class="c-row-div">洞门</div>
                <el-progress
                  :text-inside="true"
                  :stroke-width="15"
                  :percentage="maintenanceRatio.c2_2"
                  class="c-el-progress"
                ></el-progress>
                <div class="c-row-div">路面</div>
                <el-progress
                  :text-inside="true"
                  :stroke-width="15"
                  :percentage="maintenanceRatio.c2_3"
                  class="c-el-progress"
                ></el-progress>
                <div class="c-row-div">排水设施</div>
                <el-progress
                  :text-inside="true"
                  :stroke-width="15"
                  :percentage="maintenanceRatio.c2_4"
                  class="c-el-progress"
                ></el-progress>
                <div class="c-row-div">内饰</div>
                <el-progress
                  :text-inside="true"
                  :stroke-width="15"
                  :percentage="maintenanceRatio.c2_5"
                  class="c-el-progress"
                ></el-progress>
                <div class="c-row-div">其它说明</div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div
        v-loading.lock="loading.echart7"
        class="y-elcol-echart7 boxShadow"
        style="border-radius: 8px"
      >
        <div
          class="echart-stu7"
          style="
            font-size: 24px;
            color: #464f60;
            position: absolute;
            left: 13%;
            top: 37%;
          "
        ></div>
        <tb-empty
          :title="title.echart7"
          v-if="empty.echart7"
          style="margin-top: 8px"
        ></tb-empty>
        <div class="y-elcol-div-stu7" v-else id="echart-stu7-id"></div>
      </div>
    </div>
  </div>
</template>
<script>
import {
  getAccountTrend,
  getRepairTrend,
  getItemRepeatAnalyse,
  getMaintainRatio,
  getMaintainItemStatistics
} from '@/api/maintain/checkAnalysis';
import { getProjectListByModel, getStructureListByModel } from '@/api/common';
import echarts from 'echarts';
import tbEmpty from './components/tbEmpty.vue';
export default {
  components: {
    tbEmpty
  },
  data() {
    return {
      mounted: false,
      //赛选条件
      projectList: [],
      projectId: '',
      structureList: [],
      structureId: '',

      sum_main: 0,
      projectList: [{ id: 1, projectName: '宁德东侨区桥隧维养项目' }], //项目列表
      selectProject: 1, //选中的桥
      selectProjectName: '',
      //标题
      title: {
        echart3: '近五年维修费用变化趋势',
        echart4: '近五年维修趋势',
        echart5: '重复维修项占比',
        echart6: '维修构件占比',
        echart7: '维修项变化'
      },
      //空数据
      empty: {
        echart3: true,
        echart4: true,
        echart5: true,
        echart6: true,
        echart7: true
      },
      //加载框
      loading: {
        echart3: false,
        echart4: false,
        echart5: false,
        echart6: false,
        echart7: false
      },
      maintenanceRatio: {
        //重大维修构建占比
        c1_1: 0,
        c1_2: 0,
        c1_3: 0,
        c1_4: 0,
        c1_5: 0,
        c1_6: 0,
        c2_1: 0,
        c2_2: 0,
        c2_3: 0,
        c2_4: 0,
        c2_5: 0,
        c2_6: 0
      },
      statistical3: null,
      statistical4: null,
      statistical5: null,
      statistical7: null,
      structureInfo: [{ structureName: '宁德汇绿桥', id: 1 }],
      structureName: 1
    };
  },
  beforeMount() {
    this.$utils.getAuthPage(this); //获取权限
  },
  mounted() {
    this.getProjectListByModel(); //获取项目列表
  },
  methods: {
    //获取项目列表
    async getProjectListByModel() {
      let id = this.$store.getters.getActiveIndex;
      let { code, data } = await getProjectListByModel(id);
      if (code == '0000') {
        this.projectList = data;
        this.projectId = data.length > 0 ? data[0].id : '';
        this.$nextTick(async () => {
          if (data.length > 0) {
            await this.getStructureListByModel();
          }
        });
      }
    },
    //获取结构物列表
    async getStructureListByModel() {
      let params = {
        projectId: this.projectId,
        powerId: this.$store.getters.getActiveIndex
      };
      let { code, data } = await getStructureListByModel(params);
      if (code == '0000') {
        this.structureList = data;
        this.structureId = data.length > 0 ? data[0].id : '';
        if (!this.mounted) {
          this.getEcharts();
          this.mounted = true;
        }
      }
    },
    //查询数据
    getEcharts() {
      this.getAccountTrend();
      this.getRepairTrend();
      this.getItemRepeatAnalyse();
      this.getMaintainRatio();
      this.getMaintainItemStatistics();
    },
    //项目近五年维修费用变化趋势
    async getAccountTrend() {
      let { code, data } = await getAccountTrend(this.structureId);
      if (code == '0000') {
        let month = [];
        let val = [];
        for (let key in data) {
          month.push(key);
          val.push(data[key] || 0);
        }
        if (month.length > 0) {
          this.empty.echart3 = false;
        } else {
          this.empty.echart3 = true;
        }
        this.$nextTick(() => {
          if (month.length > 0) {
            this.echart3(month, val);
          }
        });
      }
    },
    //近五年维修趋势
    async getRepairTrend() {
      let { code, data } = await getRepairTrend(this.structureId);
      if (code == '0000') {
        let dataList = [];
        let nameList = [];
        for (let key in data) {
          data[key].map((item) => {
            if (item.diseasePart && !!item.diseasePart) {
              nameList.push(item.diseasePart);
            }
          });
        }
        nameList = [...new Set(nameList)];
        for (let key in data) {
          let arry = [];
          for (let i = 0; i < nameList.length; i++) {
            arry.push(0);
          }
          data[key].map((item) => {
            nameList.map((name, j) => {
              if (name == item.diseasePart) {
                arry[j] = item.count;
              }
            });
          });
          let obj = {
            name: key,
            data: arry
          };
          dataList.push(obj);
        }
        if (nameList.length > 0) {
          this.empty.echart4 = false;
        } else {
          this.empty.echart4 = true;
        }
        this.$nextTick(() => {
          if (nameList.length > 0) {
            this.echart4(nameList, dataList);
          }
        });
      }
    },
    //重复维修项占比
    async getItemRepeatAnalyse() {
      let { code, data } = await getItemRepeatAnalyse(this.structureId);
      if (code == '0000') {
        let nameList = [];
        let dataList1 = [];
        let dataList2 = [];
        data.map((item) => {
          if (item.method && !!item.method) {
            nameList.push(item.method);
            dataList1.push(item.rate);
            dataList2.push(item.cycle);
          }
        });
        if (nameList.length > 0) {
          this.empty.echart5 = false;
        } else {
          this.empty.echart5 = true;
        }
        this.$nextTick(() => {
          if (nameList.length > 0) {
            this.echart5(nameList, dataList1, dataList2);
          }
        });
      }
    },
    //当年维修占比
    async getMaintainRatio() {
      let { code, data } = await getMaintainRatio(this.structureId);
      if (code == '0000') {
        this.structureList.map((item) => {
          if (item.id == this.structureId) {
            this.structureType = item.structureType;
            this.echart6(data, item.structureType);
          }
        });
      }
    },
    //近五年维修项目统计
    async getMaintainItemStatistics() {
      let { code, data } = await getMaintainItemStatistics(this.structureId);
      if (code == '0000') {
        let nameList = [];
        let dataList = [];
        for (let key in data) {
          nameList.push(key);
          dataList.push(data[key]);
        }
        if (nameList.length > 0) {
          this.empty.echart7 = false;
        } else {
          this.empty.echart7 = true;
        }
        this.$nextTick(() => {
          if (nameList.length > 0) {
            this.echart7(nameList, dataList);
          }
        });
      }
    },
    echart3(month, val) {
      let option = {
        title: {
          text: '项目近五年维修费用变化趋势',
          top: '3',
          left: '5',
          textStyle: {
            // color: this.YY_ECHARTS_1TITLE,
            fontSize: 16
          }
        },
        tooltip: {
          trigger: 'axis'
        },
        xAxis: {
          type: 'category',
          data: month,
          boundaryGap: false,
          axisLabel: { color: '#595959' },
          nameTextStyle: { color: '#595959' },
          axisTick: { inside: true, lineStyle: { color: '#BFBFBF' } },
          axisLine: { lineStyle: { color: '#BFBFBF' } },
          splitArea: { show: false }
        },
        yAxis: {
          name: '万元',
          type: 'value',
          axisLabel: { color: '#595959' },
          nameTextStyle: {
            color: '#595959'
          },
          splitLine: {
            lineStyle: { width: 1, type: 'dashed', color: '#dedede' }
          },
          axisTick: { show: false },
          axisLine: { show: false },
          splitArea: { show: false }
        },
        grid: {
          left: 20,
          right: 20,
          top: 70,
          bottom: 10,
          containLabel: true
        },
        series: [
          {
            data: val,
            type: 'line',
            lineStyle: {
              width: 2
            },
            areaStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                {
                  offset: 0,
                  color: 'rgba(53,151,253,.25)'
                },
                {
                  offset: 1,
                  color: 'transparent'
                }
              ])
            },
            color: '#3597fd',
            symbol: 'circle',
            symbolSize: 6
          }
        ]
      };
      this.statistical3 = echarts.init(
        document.getElementById('echart-stu3-id')
      );
      this.statistical3.setOption(option);
      window.onresize = function () {
        this.statistical3.resize();
      };
    },
    echart4(nameList, dataList) {
      let option4 = {
        // backgroundColor: "#192436",
        title: {
          text: '近五年维修趋势',
          top: '3',
          left: '5',
          textStyle: {
            // color: this.YY_ECHARTS_1TITLE,
            fontSize: 16
          }
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            // 坐标轴指示器，坐标轴触发有效
            type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
          }
        },
        legend: {
          // show:true,
          top: 30,
          icon: 'circle',
          itemWidth: 8,
          itemHeight: 8,
          textStyle: {
            color: '#4d586d'
          }
        },
        grid: {
          top: '20%',
          left: '3%',
          right: '6%',
          bottom: '5%',
          containLabel: true
        },
        dataZoom: [
          {
            show: true,
            type: 'inside',
            realtime: true,
            startValue: 0,
            endValue: 4,
            zoomLock: true
          }
        ],
        xAxis: [
          {
            type: 'category',
            name: '维修项',
            axisLabel: {
              show: true,
              interval: 0,
              formatter: function (params) {
                var newParamsName = '';
                var paramsNameNumber = params.length;
                var provideNumber = 5; //一行显示几个字
                var rowNumber = Math.ceil(paramsNameNumber / provideNumber);
                if (paramsNameNumber > provideNumber) {
                  for (var p = 0; p < rowNumber; p++) {
                    var tempStr = '';
                    var start = p * provideNumber;
                    var end = start + provideNumber;
                    if (p == rowNumber - 1) {
                      tempStr = params.substring(start, paramsNameNumber);
                    } else {
                      tempStr = params.substring(start, end) + '\n';
                    }
                    newParamsName += tempStr;
                  }
                } else {
                  newParamsName = params;
                }
                return newParamsName;
              },
              textStyle: {
                color: '#595959',
                fontSize: 12
              }
            },
            nameTextStyle: { color: '#595959' },
            axisTick: { inside: true, lineStyle: { color: '#BFBFBF' } },
            axisLine: { lineStyle: { color: '#BFBFBF' } },
            splitArea: { show: false },
            data: nameList
          }
        ],
        yAxis: [
          {
            type: 'value',
            name: '维修数',
            axisLabel: {
              formatter: '{value}',
              color: '#595959',
              fontSize: 12
            },
            nameTextStyle: {
              color: '#595959'
            },
            splitLine: {
              lineStyle: { width: 1, type: 'dashed', color: '#dedede' }
            },
            axisTick: { show: false },
            axisLine: { show: false },
            splitArea: { show: false }
          }
        ],
        series: [
          {
            name: dataList[0].name,
            type: 'bar',
            barWidth: 16, //柱子宽度
            barGap: '10%', //柱子之间间距
            itemStyle: {
              shadowBlur: 1,
              normal: {
                // color: this.YY_ECHARTS_PIE1
                // barBorderRadius: 10,
              }
            },
            data: dataList[0].data
          },
          {
            name: dataList[1].name,
            type: 'bar',
            barWidth: 16, //柱子宽度
            barGap: '10%', //柱子之间间距
            itemStyle: {
              shadowBlur: 1,
              normal: {
                // color: this.YY_ECHARTS_PIE2
                // barBorderRadius: 10,
              }
            },
            data: dataList[1].data
          },
          {
            name: dataList[2].name,
            type: 'bar',
            barWidth: 16, //柱子宽度
            barGap: '10%', //柱子之间间距
            itemStyle: {
              shadowBlur: 1,
              normal: {
                // color: this.YY_ECHARTS_PIE3
                // barBorderRadius: 10,
              }
            },
            data: dataList[2].data
          },
          {
            name: dataList[3].name,
            type: 'bar',
            barWidth: 16, //柱子宽度
            barGap: '10%', //柱子之间间距
            itemStyle: {
              shadowBlur: 1,
              normal: {
                // color: this.YY_ECHARTS_PIE4
                // barBorderRadius: 10,
              }
            },
            data: dataList[3].data
          },
          {
            name: dataList[4].name,
            type: 'bar',
            barWidth: 16, //柱子宽度
            barGap: '10%', //柱子之间间距
            itemStyle: {
              shadowBlur: 1,
              normal: {
                // color: this.YY_ECHARTS_PIE5
                // barBorderRadius: 10,
              }
            },
            data: dataList[4].data
          }
        ]
      };
      this.statistical4 = echarts.init(
        document.getElementById('echart-stu4-id')
      );
      this.statistical4.setOption(option4);
      window.onresize = function () {
        this.statistical4.resize();
      };
    },
    echart5(nameList, dataList1, dataList2) {
      let total = 0;
      dataList1.filter((item) => {
        total += item;
      });
      let option5 = {
        title: {
          text: '重复维修项占比及周期分析',
          top: '5',
          left: '5',
          textStyle: {
            // color: this.YY_ECHARTS_1TITLE,
            fontSize: 16
          }
        },
        tooltip: {
          trigger: 'axis',
          confine: true,
          axisPointer: {
            type: 'none'
          },
          formatter: function (params) {
            let html = '<div style="padding:2px 5px">';
            html += '<div>' + params[0].name + '</div>';
            html +=
              '<div style="display:flex;align-items:center"><span style="width:8px;height:8px;border-radius:4px;background:#FF5F5F;margin-right:5px"></span>' +
              params[0].seriesName +
              '：' +
              Math.round((params[0].data / total) * 100) +
              '%</div>';
            html +=
              '<div style="display:flex;align-items:center"><span style="width:8px;height:8px;border-radius:4px;background:#419AFF;margin-right:5px"></span>' +
              params[1].seriesName +
              '：' +
              params[1].data +
              '天</div>';
            html += '</div>';
            return html;
          }
        },
        grid: {
          right: '15%',
          top: '20%',
          bottom: '15%',
          left: '13%'
        },
        // legend: {
        //   data: ['占比', '周期'],
        //   top: 5,
        //   left: '68%',
        //   textStyle: {
        //     color: '#4d586d'
        //   }
        // },
        dataZoom: [
          {
            show: true,
            type: 'inside',
            realtime: true,
            startValue: 0,
            endValue: 4,
            zoomLock: true
          }
        ],
        xAxis: [
          {
            type: 'category',
            axisLabel: {
              show: true,
              interval: 0,
              formatter: function (params) {
                var newParamsName = '';
                var paramsNameNumber = params.length;
                var provideNumber = 3; //一行显示几个字
                var rowNumber = Math.ceil(paramsNameNumber / provideNumber);
                if (paramsNameNumber > provideNumber) {
                  for (var p = 0; p < rowNumber; p++) {
                    var tempStr = '';
                    var start = p * provideNumber;
                    var end = start + provideNumber;
                    if (p == rowNumber - 1) {
                      tempStr = params.substring(start, paramsNameNumber);
                    } else {
                      tempStr = params.substring(start, end) + '\n';
                    }
                    newParamsName += tempStr;
                  }
                } else {
                  newParamsName = params;
                }
                return newParamsName;
              },
              textStyle: {
                color: '#595959',
                fontSize: 11
              }
            },
            nameTextStyle: { color: '#595959' },
            axisTick: { inside: true, lineStyle: { color: '#BFBFBF' } },
            axisLine: { lineStyle: { color: '#BFBFBF' } },
            splitArea: { show: false },
            data: nameList
          }
        ],
        yAxis: [
          {
            type: 'value',
            name: '占比',
            min: 0,
            max: 100,
            // position: 'right',
            // offset: 80,
            nameTextStyle: {
              color: '#595959'
            },
            splitLine: {
              lineStyle: { width: 1, type: 'dashed', color: '#dedede' }
            },
            axisTick: { show: false },
            axisLine: { show: false },
            splitArea: { show: false },
            axisLabel: {
              formatter: '{value}',
              color: '#595959'
            }
          }
          // {
          //   type: 'value',
          //   name: '周期(天)',
          //   // min: 0,
          //   // max: 250,
          //   position: 'right',
          //   nameTextStyle: {
          //     color: '#595959'
          //   },
          //   splitLine: {
          //     lineStyle: { width: 1, type: 'dashed', color: '#dedede' }
          //   },
          //   axisTick: { show: false },
          //   axisLine: { show: false },
          //   splitArea: { show: false },
          //   axisLabel: {
          //     formatter: '{value}',
          //     color: '#595959'
          //   }
          // }
        ],
        series: [
          {
            name: '占比',
            type: 'bar',
            barWidth: 16,
            data: dataList1
          }
          // {
          //   name: '周期',
          //   type: 'bar',
          //   yAxisIndex: 1,
          //   barWidth: 16,
          //   data: dataList2
          // }
        ]
      };
      this.statistical5 = echarts.init(document.getElementById('echart-stu5'));
      this.statistical5.setOption(option5);
      window.onresize = function () {
        this.statistical5.resize();
      };
    },
    echart6(dataList, type) {
      //重大维系占比
      this.sum_main = 0;
      dataList.map((item) => {
        if (item.diseasePart != null) {
          this.sum_main += item.count;
        }
      });
      if (type == 1) {
        this.maintenanceRatio = {
          c1_1: 0,
          c1_2: 0,
          c1_3: 0,
          c1_4: 0,
          c1_5: 0,
          c1_6: 0,
          c2_1: 0,
          c2_2: 0,
          c2_3: 0,
          c2_4: 0,
          c2_5: 0,
          c2_6: 0
        };
        dataList.map((item) => {
          if (item.diseasePart == '桥梁保护区域内施工') {
            this.maintenanceRatio.c2_6 = Number(
              (
                this.maintenanceRatio.c2_6 +
                (item.count / this.sum_main) * 100
              ).toFixed(1)
            ); //保留一位小数，在转成浮点型
            if (this.maintenanceRatio.c2_6 > 100) {
              this.maintenanceRatio.c2_6 = 100;
            }
          } else if (item.diseasePart == '车行道') {
            this.maintenanceRatio.c1_1 = item.rate;
          } else if (item.diseasePart == '人行道') {
            this.maintenanceRatio.c1_2 = item.rate;
          } else if (item.diseasePart == '泄水孔') {
            this.maintenanceRatio.c1_3 = item.rate;
          } else if (item.diseasePart == '栏杆') {
            this.maintenanceRatio.c1_4 = item.rate;
          } else if (item.diseasePart == '伸缩缝装置') {
            this.maintenanceRatio.c1_5 = item.rate;
          } else if (item.diseasePart == '桥、桥区施工') {
            this.maintenanceRatio.c1_6 = item.rate;
          } else if (item.diseasePart == '上部结构') {
            this.maintenanceRatio.c2_1 = item.rate;
          } else if (item.diseasePart == '限载牌') {
            this.maintenanceRatio.c2_2 = item.rate;
          } else if (item.diseasePart == '桥名牌') {
            this.maintenanceRatio.c2_3 = item.rate;
          } else if (item.diseasePart == '桥墩、桥台、附属物') {
            this.maintenanceRatio.c2_4 = item.rate;
          } else if (
            item.diseasePart == '其他危及行车、行船、行人安全的病害因素'
          ) {
            this.maintenanceRatio.c2_5 = item.rate;
          } else if (item.diseasePart != null) {
            if (item.count != null) {
              this.maintenanceRatio.c2_6 = Number(
                (
                  ((this.maintenanceRatio.c2_6 + item.count) / this.sum_main) *
                  100
                ).toFixed(1)
              );
              if (this.maintenanceRatio.c2_6 > 100) {
                this.maintenanceRatio.c2_6 = 100;
              }
            }
          }
        });
      } else {
        this.maintenanceRatio = {
          c1_1: 0,
          c1_2: 0,
          c1_3: 0,
          c1_4: 0,
          c1_5: 0,
          c2_1: 0,
          c2_2: 0,
          c2_3: 0,
          c2_4: 0,
          c2_5: 0
        };
        dataList.map((item) => {
          if (item.diseasePart == '洞口') {
            this.maintenanceRatio.c1_1 = item.rate;
          } else if (item.diseasePart == '衬砌') {
            this.maintenanceRatio.c1_2 = item.rate;
          } else if (item.diseasePart == '检修道') {
            this.maintenanceRatio.c1_3 = item.rate;
          } else if (item.diseasePart == '吊顶及各种预埋件') {
            this.maintenanceRatio.c1_4 = item.rate;
          } else if (item.diseasePart == '各种标志标线轮廓标') {
            this.maintenanceRatio.c1_5 = item.rate;
          } else if (item.diseasePart == '洞门') {
            this.maintenanceRatio.c2_1 = item.rate;
          } else if (item.diseasePart == '路面') {
            this.maintenanceRatio.c2_2 = item.rate;
          } else if (item.diseasePart == '排水设施') {
            this.maintenanceRatio.c2_3 = item.rate;
          } else if (item.diseasePart == '内饰') {
            this.maintenanceRatio.c2_4 = item.rate;
          } else if (item.diseasePart == '其它说明') {
            this.maintenanceRatio.c2_5 = item.rate;
          }
        });
      }
    },
    echart7(nameList, dataList) {
      let option = {
        title: {
          text: '维修项变化',
          top: '3',
          left: '5',
          textStyle: {
            // color: "#fafafb",
            fontSize: 16
          }
        },
        tooltip: {
          trigger: 'axis'
        },
        xAxis: {
          type: 'category',
          data: nameList,
          boundaryGap: false,
          axisLabel: {
            margin: 12,
            color: '#595959'
          },
          nameTextStyle: { color: '#595959' },
          axisTick: { inside: true, lineStyle: { color: '#BFBFBF' } },
          axisLine: { lineStyle: { color: '#BFBFBF' } },
          splitArea: { show: false }
        },
        yAxis: {
          name: '维修数',
          type: 'value',
          nameTextStyle: {
            color: '#595959'
          },
          splitLine: {
            lineStyle: { width: 1, type: 'dashed', color: '#dedede' }
          },
          axisTick: { show: false },
          axisLine: { show: false },
          splitArea: { show: false },
          axisLabel: {
            margin: 20,
            color: '#595959'
          }
        },
        grid: {
          left: 15,
          right: 20,
          top: 70,
          bottom: 10,
          containLabel: true
        },
        series: [
          {
            data: dataList,
            type: 'line',
            lineStyle: {
              width: 2
            },
            areaStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                {
                  offset: 0,
                  color: 'rgba(53,151,253,.25)'
                },
                {
                  offset: 1,
                  color: 'transparent'
                }
              ])
            },
            color: '#3597fd',
            symbol: 'circle',
            symbolSize: 6
          }
        ]
      };
      this.statistical7 = echarts.init(
        document.getElementById('echart-stu7-id')
      );
      this.statistical7.setOption(option);
      window.onresize = function () {
        this.statistical7.resize();
      };
    }
  }
};
</script>

<style scoped lang="scss">
.mainAna {
  background: transparent !important;
  box-shadow: unset;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  .select {
    margin-right: 20px;
    height: 40px;
  }
  .middle {
    width: 100%;
    height: 38.149vh;
    display: flex;
    align-items: center;
    justify-content: space-between;
    .y-elcol-echart4 {
      width: 1234px;
      height: 100%;
      margin: 0;
      padding: 12px;
      display: flex;
      flex-direction: column;
      .echart-stu4 {
        font-size: 24px;
        color: #464f60;
      }
    }
    .y-elcol-3,
    .y-elcol-echart5,
    .y-elcol-echart6,
    .y-elcol-echart7 {
      width: 605px;
      height: 100%;
      margin: 0;
      padding: 12px;
      display: flex;
      flex-direction: column;
    }
    /deep/ .empty .title {
      font-weight: bold;
    }
  }
}

.y-elcol-div {
  height: 100%;
  width: 100%;
  border-radius: 8px;
}

.y-elcol-div-stu7 {
  height: 100%;
  width: 100%;
  border-radius: 8px;
}

.c-row-div {
  font-size: 12px;
  line-height: 17px;
  letter-spacing: 0px;
  color: #4d586d;
  padding-left: 15px;
  padding-bottom: 3px;
}
.c-el-progress {
  padding: 10px 20px 5px 20px !important;
  /deep/ .el-progress-bar__innerText {
    color: #000;
    height: 100%;
    vertical-align: super;
  }
}
.c-div-title {
  font-size: 16px;
  line-height: 17px;
  letter-spacing: 0px;
  width: 100%;
  height: 10%;
  float: left;
  display: inline-block;
  padding: 10px 5px 10px 15px;
}
</style>
