<template>
  <div class="analysisData">
    <div class="analysisDataBox">
      <div class="analysisTop">
        <div class="topLeft">
          <div
            v-for="item in analysisList"
            :key="item.id"
            class="analysisItem"
            :class="{ activeItem: activeIndex == item.id }"
            @click="itemChange(item.id)"
          >
            {{ item.name }}
          </div>
        </div>
        <div class="topRight">
          <div class="topOne">
            <el-select
              v-model="structureId"
              placeholder="请选择结构物"
              @change="getSensorWeightCarNo"
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
          <div class="topTwo">
            <el-date-picker
              v-model="time"
              type="datetimerange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              value-format="yyyy-MM-dd HH:mm:ss"
              :default-time="['00:00:00', '23:59:59']"
              :picker-options="pickerOptions"
              @change="timeChange"
            >
            </el-date-picker>
          </div>
          <div v-if="activeIndex == 1" class="topThree">
            <el-select v-model="lane" placeholder="请选择车道">
              <el-option
                v-for="item in laneList"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              >
              </el-option>
            </el-select>
          </div>
          <el-button type="primary" @click="getData">查询</el-button>
        </div>
      </div>
      <div class="analysisDataContent">
        <statisticsData
          v-if="activeIndex == 1"
          ref="analysisOne"
          :structureId="structureId"
          :lane="lane"
          :time="time"
        ></statisticsData>

        <spectrum
          v-else-if="activeIndex == 2"
          ref="analysisTwo"
          :structureId="structureId"
          :time="time"
        ></spectrum>

        <cableForce
          v-else-if="activeIndex == 3"
          ref="analysisThree"
          :structureId="structureId"
          :time="time"
        ></cableForce>

        <relevance
          v-else-if="activeIndex == 4"
          ref="analysisFour"
          :structureId="structureId"
          :time="time"
        ></relevance>

        <boxLine
          v-else-if="activeIndex == 5"
          ref="analysisFive"
          :structureId="structureId"
          :time="time"
        ></boxLine>
      </div>
    </div>
  </div>
</template>

<script>
import {
  getSensorStructure,
  getSensorWeightCarNo
} from '@/api/online/analysisData';
import statisticsData from './analysisChild/statisticsData';
import spectrum from './analysisChild/spectrum';
import cableForce from './analysisChild/cableForce';
import relevance from './analysisChild/relevance';
import boxLine from './analysisChild/boxLine';
export default {
  name: 'analysisData',
  components: {
    statisticsData,
    spectrum,
    cableForce,
    relevance,
    boxLine
  },
  props: {},
  data() {
    return {
      //筛选条件
      pickerOptions1: {
        shortcuts: [
          {
            text: '最近一周',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', [start, end]);
            }
          },
          {
            text: '最近一个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit('pick', [start, end]);
            }
          },
          {
            text: '最近三个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit('pick', [start, end]);
            }
          },
          {
            text: '最近半年',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 180);
              picker.$emit('pick', [start, end]);
            }
          },
          {
            text: '最近一年',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 365);
              picker.$emit('pick', [start, end]);
            }
          }
        ],
        disabledDate(time) {
          return time.getTime() > Date.now();
        }
      },
      pickerOptions2: {
        shortcuts: [
          {
            text: '最近一周',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', [start, end]);
            }
          },
          {
            text: '最近一个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit('pick', [start, end]);
            }
          },
          {
            text: '最近三个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit('pick', [start, end]);
            }
          }
        ],
        disabledDate(time) {
          return time.getTime() > Date.now();
        }
      },
      structureList: [],
      structureId: '',
      time: [
        this.$utils.Dateformat1(new Date()) + ' 00:00:00',
        this.$utils.Dateformat1(new Date()) + ' 23:59:59'
      ],
      laneList: [],
      lane: '',
      analysisList: [
        { id: 1, name: '车辆荷载分析' },
        { id: 2, name: '振动频谱分析' },
        { id: 3, name: '拉索索力分析' },
        { id: 4, name: '相关性分析' },
        { id: 5, name: '箱线图分析' }
      ],
      activeIndex: 1
    };
  },
  computed: {
    pickerOptions() {
      if (this.activeIndex != 5) return this.pickerOptions1;
      else return this.pickerOptions2;
    }
  },
  beforeMount() {
    this.$utils.getAuthPage(this); //获取权限
  },
  mounted() {
    this.getSensorStructure(1); //获取结构物
  },
  methods: {
    //获取结构物
    async getSensorStructure(type) {
      let { code, data } = await getSensorStructure(type);
      if (code == '0000') {
        this.structureList = data;
        if (data.length > 0) {
          this.structureId = data[0].id;
          this.$nextTick(() => {
            if (this.activeIndex === 1) this.getSensorWeightCarNo();
            else this.getSensorWeightCarNo(data[0].id);
          });
        }
      }
    },
    //查询称重传感器结构物下的车道
    async getSensorWeightCarNo(id) {
      if (this.activeIndex == 1) {
        let { code, data } = await getSensorWeightCarNo(this.structureId, 1);
        if (code == '0000') {
          let arry = [];
          data.map((item) => {
            let obj = {
              id: item.id,
              name: item.name
            };
            arry.push(obj);
          });
          arry.unshift({ id: '', name: '全部车道' });
          this.laneList = arry;
          this.$nextTick(() => {
            if (!id) {
              this.getData();
            }
          });
        }
      } else if (this.activeIndex == 2) {
        this.$refs.analysisTwo.getSensorWeightCarNo(id);
      } else if (this.activeIndex == 3) {
        this.$refs.analysisThree.getSensorWeightCarNo(id);
      } else if (this.activeIndex == 4) {
        this.$refs.analysisFour.getTreeData(id);
      } else {
        this.$refs.analysisFive.getTreeData(id);
      }
    },
    getData() {
      if (this.activeIndex == 1) {
        this.$refs.analysisOne.getData();
      } else if (this.activeIndex == 2) {
        this.$refs.analysisTwo.getData();
      } else if (this.activeIndex == 3) {
        this.$refs.analysisThree.getData();
      } else if (this.activeIndex == 4) {
        this.$refs.analysisFour.getRelevance();
      } else {
        this.$refs.analysisFive.boxAnalyse();
      }
    },
    itemChange(index) {
      this.activeIndex = index;
      this.getSensorStructure(index);
    },
    //时间选择
    timeChange(time) {
      if (this.activeIndex == 5) {
        let begin = new Date(time[0]).getTime();
        let end = new Date(time[1]).getTime();
        if (end - begin > 90 * 24 * 3600 * 1000) {
          this.$message({
            message: `日期范围不能超过三个月！`,
            type: 'warning',
            showClose: true,
            duration: 2000
          });
          time[0] = this.$utils.Dateformat(
            new Date(end - 90 * 24 * 3600 * 1000)
          );
        }
      }
    }
  }
};
</script>
<style lang="scss" scoped>
.analysisData {
  position: absolute;
  top: 11.771vh;
  left: 0;
  height: 88.242vh !important;
  // height: unset;
  padding: 1.855vh 0;
  background: transparent !important;
  box-shadow: none;
  z-index: 10;
  overflow: auto;
  .analysisDataBox {
    width: 100%;
  }
  .analysisTop {
    // height: 3.708vh;
    height: 3.705vh;
    padding: 0 64px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    .topLeft,
    .topRight {
      height: 100%;
      display: flex;
      align-items: center;
    }
    .analysisItem {
      line-height: 3.705vh;
      padding: 0 16px;
      font-size: 14px;
      color: #000;
      background: #fff;
      border: 1px solid #d9d9d9;
      border-right: 0;
      cursor: pointer;
      &:last-child {
        border-right: 1px solid #d9d9d9;
      }
    }
    .activeItem {
      color: #2f80ed;
      border: 1px solid #1890ff !important;
    }
    .topOne {
      width: 250px;
      height: 100%;
      margin-right: 20px;
    }
    .topTwo {
      width: 357px;
      height: 100%;
      margin-right: 20px;
    }
    .topThree {
      width: 160px;
      height: 100%;
      margin-right: 20px;
    }
    /deep/ .el-select,
    /deep/ .el-input,
    /deep/ .el-input__inner {
      width: 100%;
      height: 100%;
    }
    /deep/ .el-button {
      width: 60px;
      height: 100%;
      padding: 0;
    }
    /deep/ .el-range-editor.el-input__inner {
      display: flex;
      align-items: center;
      justify-content: space-between;
    }
    /deep/ .el-date-editor .el-range-separator {
      width: unset;
      display: flex;
      align-items: center;
      justify-content: center;
    }
    /deep/ .el-input__icon {
      display: flex;
      align-items: center;
      justify-content: center;
    }
  }
  .analysisDataContent {
    // height: 88.323vh !important;
    // height: unset;
    padding: 0 64px;
    box-shadow: none;
    z-index: 10;
  }
}
</style>
