<template>
  <div class="dailyCheckAnalysis">
    <div class="top">
      <el-select
        placeholder="请选择项目"
        v-model="projectId"
        class="daily-select-light"
        @change="getStructureList"
      >
        <el-option
          v-for="item in projectList"
          :key="item.id"
          :value="item.id"
          :label="item.name"
        />
      </el-select>
      <el-select
        placeholder="请选择结构物"
        v-model="structureId"
        class="daily-select-light"
      >
        <el-option
          v-for="item in structureList"
          :key="item.id"
          :value="item.id"
          :label="item.name"
        />
      </el-select>
      <el-button
        type="primary"
        :disabled="structureId == ''"
        @click="getEcharts"
        >查 询</el-button
      >
    </div>

    <div class="first-row animate__animated animate__fadeInDown">
      <div class="itemBox boxShadow">
        <tb-card title="病害类型统计">
          <div class="echart" ref="chart0" v-show="!this.empty.c0"></div>
          <tb-empty-info v-if="this.empty.c0" />
        </tb-card>
      </div>
      <div class="itemBox boxShadow">
        <tb-card title="构件病害数排行榜">
          <div class="echart" ref="chart1" v-show="!this.empty.c1"></div>
          <tb-empty-info v-if="this.empty.c1" />
        </tb-card>
      </div>
      <div class="itemBox boxShadow">
        <tb-card title="构件病害月频率">
          <!-- 一颗特殊的select 太小 -->
          <el-select
            class="daily__checkAnalyisis__spc-select"
            popper-class="daily__popper"
            v-model="month"
            @change="getDiseaseMonthFrequency"
            style="position: absolute"
          >
            <el-option
              v-for="item in monthList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            ></el-option>
          </el-select>
          <div class="echart" ref="chart2" v-show="!this.empty.c2"></div>
          <tb-empty-info v-if="this.empty.c2" />
        </tb-card>
      </div>
    </div>

    <div
      class="second-row animate__animated animate__fadeInUp"
      id="central-labels"
    >
      <div class="itemBox boxShadow">
        <tb-card title="构件病害报修情况">
          <div class="echart" ref="chart3" v-show="!this.empty.c3"></div>
          <tb-empty-info v-if="this.empty.c3" />
        </tb-card>
      </div>
      <div class="itemBox2 boxShadow">
        <tb-card title="近五年构件病害数对比">
          <div class="echart" ref="chart4" v-show="!this.empty.c4"></div>
          <tb-empty-info v-if="this.empty.c4" />
        </tb-card>
      </div>
    </div>
  </div>
</template>

<script>
import {
  getCompDiseaseRank,
  getDiseaseMonthFrequency,
  getDiseaseRepair,
  getDiseaseYearRank,
  getDiseaseType
} from '@/api/normal/checkAnalysis';
import { getProjectListByModel, getStructureList } from '@/api/common';
import echarts from 'echarts';
import tbEmptyInfo from './compoents/tbEmptyInfo';
import tbCard from './compoents/tbCard';
import echartsConfigs from './echartsConfigs/checkAnalysis';
//缓存echarts实例
// let echartsInstances = Array(5).fill(null);
export default {
  components: { tbEmptyInfo, tbCard },
  data() {
    return {
      echartsInstances: [],
      //赛选条件
      projectList: [],
      projectId: '',
      structureList: [],
      structureId: '',

      empty: {
        c0: true,
        c1: true,
        c2: true,
        c3: true,
        c4: true
      },
      optionsBridge: {
        sp: [
          '桥、桥区施工',
          '其他危及行车、行船、行人安全的病害因素',
          '其它说明'
        ]
      },
      options: {
        checkItem: [],
        checkItemShort: [],
        sp: [] //特殊检查项，不会显示在4图里
      },
      month: new Date().getMonth() + 1,
      monthList: []
    };
  },
  beforeMount() {
    this.mounted = true;
    this.$utils.getAuthPage(this); //获取权限
  },
  mounted() {
    this.getProjectListByModel(); //获取项目列表
    this.echartsInstances = Array(5).fill(null);
    for (let month = 1; month <= 12; month++) {
      let obj = {
        id: month,
        name: month + '月'
      };
      this.monthList.push(obj);
    }
  },
  destroyed() {
    this.echartsInstances.forEach((e) => e && e.dispose());
    this.echartsInstances = null;
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
          await this.getStructureList();
        });
      }
    },
    //获取结构物列表
    async getStructureList() {
      let params = { projectId: this.projectId };
      let { code, data } = await getStructureList(params);
      if (code == '0000') {
        this.structureList = data;
        this.structureId = data.length > 0 ? data[0].id : '';
        this.$nextTick(() => {
          if (this.mounted) {
            this.mounted = false;
            this.getEcharts();
          }
        });
      }
    },
    //查询数据
    getEcharts() {
      this.getDiseaseType(); //病害类型统计
      this.getCompDiseaseRank(); //构件病害数排行榜
      this.getDiseaseMonthFrequency(); //构件病害月频率
      this.getDiseaseRepair(); //构件病害报修情况
      this.getDiseaseYearRank(); //近五年构件病害数对比
    },
    //病害类型统计
    async getDiseaseType() {
      let { code, data } = await getDiseaseType(this.structureId);
      if (code == '0000') {
        let rankPieChartRes = [];
        data.map((item) => {
          let obj = {
            analyzeCount: item.count,
            damageType: item.type
          };
          rankPieChartRes.push(obj);
        });
        this.$nextTick(() => {
          this.DiseaseType(rankPieChartRes);
        });
      }
    },
    //构件病害数排行榜
    async getCompDiseaseRank() {
      let { code, data } = await getCompDiseaseRank(this.structureId);
      if (code == '0000') {
        let rankNumberChartRes = [];
        data.map((item) => {
          let obj = {
            analyzeCount: item.count,
            checkItemShort: item.diseasePart
          };
          rankNumberChartRes.push(obj);
        });
        this.$nextTick(() => {
          this.CompDiseaseRank(rankNumberChartRes);
        });
      }
    },
    //构件病害月频率
    async getDiseaseMonthFrequency() {
      let { code, data } = await getDiseaseMonthFrequency(
        this.structureId,
        this.month
      );
      if (code == '0000') {
        let rankMonthChartRes = [];
        data.map((item) => {
          let obj = {
            analyzeCount: item.count,
            checkItem: item.diseasePart
          };
          rankMonthChartRes.push(obj);
        });
        this.$nextTick(() => {
          this.DiseaseMonthFrequency(rankMonthChartRes);
        });
      }
    },
    //构件病害报修情况
    async getDiseaseRepair() {
      let { code, data } = await getDiseaseRepair(this.structureId);
      if (code == '0000') {
        let rankRepairChartRes = {
          addList: [],
          fixList: [],
          observeList: [],
          repairList: [],
          replaceList: [],
          watchList: [],
          followList: [],
          manageList: [],
          checkList: []
        };
        let rankRepairChartRes2 = {
          followList: [],
          manageList: [],
          checkList: []
        };
        data.map((item) => {
          let obj = {
            analyzeCount: item.count,
            checkItem: item.part
          };
          switch (item.strategy) {
            case 1:
              rankRepairChartRes.observeList.push(obj);
              break;
            case 2:
              rankRepairChartRes.repairList.push(obj);
              break;
            case 3:
              rankRepairChartRes.watchList.push(obj);
              break;
            case 4:
              rankRepairChartRes.fixList.push(obj);
              break;
            case 5:
              rankRepairChartRes.replaceList.push(obj);
              break;
            case 6:
              rankRepairChartRes.addList.push(obj);
              break;
            case 7:
              rankRepairChartRes2.followList.push(obj);
              break;
            case 8:
              rankRepairChartRes2.manageList.push(obj);
              break;
            case 9:
              rankRepairChartRes2.checkList.push(obj);
              break;
            default:
              break;
          }
        });
        let structureType = '';
        this.structureList.map((item) => {
          if (item.id == this.structureId) {
            structureType = item.structureType;
          }
        });
        this.$nextTick(() => {
          if (structureType == 1) this.DiseaseRepair(rankRepairChartRes);
          else this.DiseaseRepair2(rankRepairChartRes2);
        });
      }
    },
    //近五年构件病害数对比
    async getDiseaseYearRank() {
      let { code, data } = await getDiseaseYearRank(this.structureId);
      if (code == '0000') {
        let rankYearsChartRes = {};
        Object.keys(data).map((item) => {
          rankYearsChartRes[item] = [];
          let items = data[item];
          items.map((item2) => {
            let obj = {
              analyzeCount: item2.count,
              checkItem: item2.diseasePart
            };
            rankYearsChartRes[item].push(obj);
          });
        });

        this.$nextTick(() => {
          this.DiseaseYearRank(rankYearsChartRes);
        });
      }
    },
    DiseaseType(rankPieChartRes) {
      //1
      const data = rankPieChartRes.map(({ damageType, analyzeCount }) => [
        damageType.split(/[(（]/)[0],
        analyzeCount
      ]);
      const count = data.length;
      this.handleEchartsMethods(0, count, data);
    },
    CompDiseaseRank(rankNumberChartRes) {
      //2
      let count = 0;
      const chartDataMap = new Map(
        rankNumberChartRes.map((v) => [v.checkItemShort, 0])
      );
      rankNumberChartRes.forEach(({ checkItemShort, analyzeCount }) => {
        if (chartDataMap.has(checkItemShort)) {
          count += analyzeCount;
          chartDataMap.set(
            checkItemShort,
            chartDataMap.get(checkItemShort) + analyzeCount
          );
        }
      });
      const data = [
        ['undefined chart title', '病害数'],
        ...[...chartDataMap.entries()]
          .map((v) => [v[0], v[1]])
          .sort((a, b) => {
            //此处排序
            return b[1] - a[1];
          })
      ];

      this.handleEchartsMethods(1, count, data);
    },
    DiseaseMonthFrequency(rankMonthChartRes) {
      let count = 0;
      const chartDataMap = new Map(
        rankMonthChartRes.map((v) => [v.checkItem, 0])
      );
      rankMonthChartRes.forEach(({ checkItem, analyzeCount }) => {
        if (chartDataMap.has(checkItem)) {
          //判断是否有这个key
          count += analyzeCount;
          chartDataMap.set(
            checkItem,
            chartDataMap.get(checkItem) + analyzeCount
          );
        }
      });
      const data = [
        ['undefined chart title', '次数'],
        ...[...chartDataMap.entries()].map((v) => [v[0], v[1]])
      ];
      this.handleEchartsMethods(2, count, data);
    },
    //构件病害报修情况（桥）
    DiseaseRepair(rankRepairChartRes) {
      let checkItem = [];
      Object.keys(rankRepairChartRes).map((key) => {
        rankRepairChartRes[key].map((item2) => {
          checkItem.push(item2.checkItem);
        });
      });
      checkItem = [...new Set(checkItem)];
      const repairTypes = [
        'observeList',
        'repairList', //baoxiu
        'watchList',
        'fixList', //jixiu
        'replaceList',
        'addList'
      ];
      const repairTypesCN = ['观察', '报修', '监测', '即修', '更换', '增设'];
      let count = 0;
      let chartDataMap;
      chartDataMap = new Map(
        checkItem
          .filter((item) => this.optionsBridge.sp.indexOf(item) < 0)
          .map((str) => {
            let t = {};
            repairTypes.forEach((item) => (t[item] = 0));
            return [str, t];
          })
      );
      repairTypes.forEach((type) => {
        rankRepairChartRes[type].forEach((item) => {
          if (chartDataMap.has(item.checkItem)) {
            chartDataMap.get(item.checkItem)[type] = item.analyzeCount;
            count += item.analyzeCount;
          }
        });
      });
      const data = [
        ['undefined chart title', ...repairTypesCN],
        ...[...chartDataMap.entries()].map((v, index) => [
          checkItem[index],
          ...repairTypes.map((type) => v[1][type])
        ])
      ];
      this.handleEchartsMethods(3, count, data, 6);
    },
    //构件病害报修情况（隧）
    DiseaseRepair2(rankRepairChartRes) {
      let checkItem = [];
      Object.keys(rankRepairChartRes).map((key) => {
        rankRepairChartRes[key].map((item2) => {
          checkItem.push(item2.checkItem);
        });
      });
      checkItem = [...new Set(checkItem)];
      const repairTypes = ['followList', 'manageList', 'checkList'];
      const repairTypesCN = ['跟踪监测', '维修处置', '定期或专项检查'];
      let count = 0;
      let chartDataMap;
      chartDataMap = new Map(
        checkItem
          .filter((item) => this.optionsBridge.sp.indexOf(item) < 0)
          .map((str) => {
            let t = {};
            repairTypes.forEach((item) => (t[item] = 0));
            return [str, t];
          })
      );
      repairTypes.forEach((type) => {
        rankRepairChartRes[type].forEach((item) => {
          if (chartDataMap.has(item.checkItem)) {
            chartDataMap.get(item.checkItem)[type] = item.analyzeCount;
            count += item.analyzeCount;
          }
        });
      });
      const data = [
        ['undefined chart title', ...repairTypesCN],
        ...[...chartDataMap.entries()].map((v, index) => [
          checkItem[index],
          ...repairTypes.map((type) => v[1][type])
        ])
      ];
      this.handleEchartsMethods(3, count, data, 3);
    },
    DiseaseYearRank(rankYearsChartRes) {
      let checkItem = [];
      Object.keys(rankYearsChartRes).map((key) => {
        rankYearsChartRes[key].map((item2) => {
          checkItem.push(item2.checkItem);
        });
      });
      checkItem = [...new Set(checkItem)];
      const year = new Date().getFullYear();
      let count = 0;

      let chartDataTitle = ['undefined chart title'];
      Object.keys(rankYearsChartRes).map((item) => {
        chartDataTitle.push(item);
      });

      let resDataArr = [];
      Object.keys(rankYearsChartRes).map((item) => {
        resDataArr.push(rankYearsChartRes[item]);
      });
      //根据名称能获取该行的数据
      const chartDataMap = new Map(checkItem.map((v) => [v, [0, 0, 0, 0, 0]]));
      //对每年 的 每项 数据 遍历
      //只有在本页定义的checkItem能被计算，其他值不予录入
      resDataArr.forEach((yearData, yearIndex) => {
        yearData.forEach(({ checkItem, analyzeCount }) => {
          if (chartDataMap.has(checkItem)) {
            chartDataMap.get(checkItem)[yearIndex] += analyzeCount;
            count += analyzeCount;
          }
        });
      });
      const data = [
        chartDataTitle,
        ...[...chartDataMap.entries()].map((v, index) => [
          checkItem[index],
          ...v[1]
        ]) //把checkItem插入到数据
      ];

      this.handleEchartsMethods(4, count, data);
    },

    /** 代理echarts的挂载和清空 */
    handleEchartsMethods(index, count, ...data) {
      //check empty
      if (count === 0) {
        this.empty['c' + index] = true;
        //empty then clear chart( unnessary )
        this.$nextTick(() => {
          //下一次渲染调用（渲染完成调用）
          if (
            this.echartsInstances instanceof Array &&
            this.echartsInstances[index]
          ) {
            this.echartsInstances[index].clear();
          }
        });
        return;
      } else {
        this.empty['c' + index] = false;

        this.$nextTick((_) => {
          //check dom
          if (!this.$refs['chart' + index]) return;
          //echart register & mount
          if (!this.echartsInstances[index]) {
            this.echartsInstances[index] = echarts.init(
              this.$refs['chart' + index]
            );
          }
          this.echartsInstances[index].clear();

          this.echartsInstances[index].setOption(
            echartsConfigs[index](...data)
          );
        });
      }
    }
  }
};
</script>

<style scoped lang="scss">
.dailyCheckAnalysis {
  background: transparent !important;
  box-shadow: unset;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  .top {
    display: flex;
    align-items: center;
  }
  .first-row,
  .second-row {
    height: 38.149vh;
    display: flex;
    align-items: center;
    justify-content: space-between;
    .itemBox {
      width: 605px;
      height: 100%;
      padding: 12px;
      display: flex;
      flex-direction: column;
      .daily__checkAnalyisis__spc-select {
        position: absolute;
        right: 0;
        width: 88px;
      }
    }
    .itemBox2 {
      width: 1234px;
      height: 100%;
      padding: 12px;
      display: flex;
      flex-direction: column;
    }
    .echart {
      flex: 1;
    }
    /deep/ .tb-card {
      position: relative;
      padding: 0;
      border-radius: 0;
      display: flex;
      flex-direction: column;
      .title {
        position: unset;
      }
    }
  }
}

.first-row > div {
  height: 33.365vh;
}

.boxShadow {
  background: #fff;
  border-radius: 8px;
}

.daily-select-light {
  margin-right: 10px;
}
</style>
