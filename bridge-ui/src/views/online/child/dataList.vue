<template>
  <div class="dataList">
    <div class="tool">
      <span>历史值时间段：</span>
      <el-date-picker
        class="time"
        v-model="timeValue"
        type="datetimerange"
        :picker-options="pickerOptions"
        range-separator="至"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
        value-format="yyyy-MM-dd HH:mm:ss"
        :default-time="['00:00:00', '23:59:59']"
        align="center"
        :clearable="false"
      >
      </el-date-picker>
      <el-button type="primary" @click="getList">查询</el-button>
    </div>
    <div class="dlTable">
      <normalTable
        :tableData="tableData"
        :titleList="sensorTypeId == 7 ? titleList2 : titleList"
        :pageNum="currentPage"
      ></normalTable>
    </div>

    <el-pagination
      class="pageNation"
      background
      :current-page.sync="currentPage"
      :page-size="10"
      :pager-count="5"
      layout="total, prev, pager, next"
      :total="total"
      @current-change="handleCurrentChange"
    >
    </el-pagination>
  </div>
</template>

<script>
import { getList, exportExcell } from '@/api/online/warnDetails';
import normalTable from '@/components/table/normalTable';
export default {
  name: 'dataList-dzl',
  components: { normalTable },
  props: {
    sensorList: {
      type: Array,
      default: () => []
    }
  },
  watch: {
    sensorList() {
      this.getList();
    }
  },
  data() {
    return {
      pickerOptions: {
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
      timeValue: [
        this.$utils.Dateformat1(new Date()) + ' 00:00:00',
        this.$utils.Dateformat1(new Date()) + ' 23:59:59'
      ],
      currentPage: 1,
      total: 0,
      titleList: [
        { id: 1, prop: 'sensorCoding', label: '传感器编号' },
        { id: 2, prop: 'samplingTime', label: '采样时间', width: '' },
        { id: 3, prop: 'value', label: '实测值', width: '' }
      ],
      titleList2: [
        { id: 1, prop: 'sensorCoding', label: '传感器编号' },
        { id: 2, prop: 'samplingTime', label: '采样时间', width: '' },
        { id: 3, prop: 'value', label: '总重（t）', width: '' },
        { id: 4, prop: 'singleShaftNuber', label: '轴数', width: '' },
        { id: 5, prop: 'modelName', label: '车型', width: '' },
        { id: 6, prop: 'licensePlate', label: '车牌号', width: '' }
      ],
      sensorTypeId: '',
      tableData: [],
      params: {}
    };
  },
  methods: {
    //获取数据列表
    async getList() {
      if (this.sensorList.length == 0) {
        this.$message({
          message: '请选择传感器！',
          type: 'warning',
          showClose: true,
          duration: 2000
        });
        return;
      }
      this.sensorTypeId = this.sensorList[0].sensorTypeId;
      let params = {
        sensorList: this.sensorList,
        startTime: this.timeValue[0],
        endTime: this.timeValue[1],
        pageNum: this.currentPage
      };
      let { code, data } = await getList(params);
      if (code == '0000') {
        data.list.map((item) => {
          item.singleShaftNuber = item.singleShaftNuber || '-';
          item.modelName = item.modelName || '-';
          item.licensePlate =
            !!item.licensePlate && item.licensePlate.trim() != ''
              ? item.licensePlate.trim()
              : '-';
        });
        this.tableData = data.list;
        this.total = data.total;
        this.params = { ...params };
      }
    },
    //数据导出
    exportExcell() {
      if (this.tableData.length == 0) {
        this.$message({
          message: '暂无数据可导出！',
          type: 'warning',
          showClose: true,
          duration: 2000
        });
        return;
      }
      exportExcell(this.params)
        .then((res) => {
          let fileName =
            '数据列表 ' + this.$utils.Dateformat1(new Date()) + '.csv';
          this.$utils.downloadBlob(res, fileName);
        })
        .catch();
    },
    //分页功能
    async handleCurrentChange(val) {
      this.currentPage = val;
      await this.getList();
    }
  },
  mounted() {
    this.getList();
  }
};
</script>
<style lang="scss" scoped>
.dataList {
  height: 100%;
  width: 100%;
  display: flex;
  flex-direction: column;
  // justify-content: space-between;
  .tool {
    height: 3.705vh;
    display: flex;
    align-items: center;
    .time {
      // height: 100%;
      margin: 0 20px;
      display: flex;
      align-items: center;
      justify-content: space-between;
      /deep/ .el-range-separator {
        display: flex;
        align-items: center;
        justify-content: center;
      }
    }
    /deep/ .el-button {
      height: 100%;
      display: flex;
      align-items: center;
      justify-content: center;
    }
  }
  .dlTable {
    padding: 1.853vh 0 0.926vh;
    /deep/ .el-table td {
      // padding: 1.945vh 0;
    }
  }
  .pageNation {
    text-align: center;
    /deep/ button {
      background: transparent;
      border: 1px solid #d9d9d9;
      border-radius: 2px;
    }
    /deep/ .el-pager li {
      background: transparent;
      border: 1px solid #d9d9d9;
      border-radius: 2px;
    }
    /deep/ .el-pager .active {
      color: #1890ff;
      border: 1px solid #1890ff;
    }
  }
}
</style>
