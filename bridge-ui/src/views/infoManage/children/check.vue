<template>
  <div class="inspectio">
    <!-- 桥梁名称 -->
    <div class="title">{{ structureName }}</div>
    <!-- 数据表格 -->
    <div class="table">
      <el-table :data="tableData" :row-class-name="tableRowClassName">
        <el-table-column type="index" label="序号" width="100" align="center">
          <template slot-scope="scope">
            {{ (pageNum - 1) * 10 + scope.$index + 1 }}
          </template>
        </el-table-column>
        <el-table-column prop="structureName" label="结构物名称" align="center">
        </el-table-column>
        <el-table-column prop="typeName" label="检测类型" align="center">
        </el-table-column>
        <el-table-column
          prop="endTime"
          label="检测完成时间"
          align="center"
          width="200"
        >
        </el-table-column>
        <el-table-column prop="ratingLevel" align="center" label="评定等级">
          <template slot-scope="scope">
            <div>{{ scope.row.ratingLevel || '未评价' }}</div>
          </template>
        </el-table-column>
        <el-table-column label="BCI评分" align="center">
          <template slot-scope="scope">
            <div>{{ scope.row.bridgeConditionIndex || '未评价' }}</div>
          </template>
        </el-table-column>
        <el-table-column prop="operation" label="BCI评价" align="center">
          <template slot-scope="scope">
            <el-button
              @click="handleClick(scope.row, 'BCIeva')"
              type="text"
              size="small"
              >详情</el-button
            >
          </template>
        </el-table-column>
        <el-table-column prop="operation" label="结构病害" align="center">
          <template slot-scope="scope">
            <el-button
              @click="handleClick(scope.row, 'disease')"
              type="text"
              size="small"
              >详情</el-button
            >
          </template>
        </el-table-column>
        <el-table-column prop="operation" label="原始记录" align="center">
          <template slot-scope="scope">
            <el-button
              @click="handleClick(scope.row, 'original')"
              type="text"
              size="small"
              >详情</el-button
            >
          </template>
        </el-table-column>
        <el-table-column prop="operation" label="检测报告" align="center">
          <template slot-scope="scope">
            <el-button
              @click="handleClick(scope.row, 'report')"
              type="text"
              size="small"
              >详情</el-button
            >
          </template>
        </el-table-column>
        <el-table-column prop="operation" label="操作" align="center">
          <template slot-scope="scope">
            <el-button
              @click="handleClick(scope.row, 'survey')"
              type="text"
              size="small"
              >查看概况</el-button
            >
            <!-- <el-button
                  @click="handleClose(scope.row)"
                  type="text"
                  size="small"
                  >删除</el-button
                > -->
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 分页 -->
    <div class="page">
      <el-pagination
        class="pageNation"
        background
        :page-size="10"
        :current-page="pageNum"
        @current-change="handleCurrentChange"
        layout="total,prev, pager, next"
        :total="total"
      >
      </el-pagination>
    </div>
  </div>
</template>

<script>
import { getMonitorRecord } from '@/api/infomanage/bridgeDetail';
export default {
  inject: ['params'],
  data() {
    return {
      addOpt: this.params().addOpt,
      updateOpt: this.params().updateOpt,
      deleteOpt: this.params().deleteOpt,
      structureId: this.params().structureId,
      structureName: this.params().structureName,
      tableData: [],
      pageNum: 1,
      total: 0
    };
  },
  mounted() {
    this.getMonitorRecord();
  },
  methods: {
    // 获取列表
    async getMonitorRecord() {
      let { code, data } = await getMonitorRecord(
        this.structureId,
        this.pageNum
      );
      if (code == '0000') {
        data.list.map((item) => {
          item.endTime = item.endTime.split(' ')[0];
        });
        this.tableData = data.list;
        this.total = data.total;
      }
    },
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex % 2 == 1) {
        return 'first-row';
      } else {
        return 'second-row';
      }
    },
    // 分页
    handleCurrentChange(val) {
      this.pageNum = val;
      this.getMonitorRecord();
    },
    // 跳转详情
    handleClick(obj, activeName) {
      this.$store.dispatch('asyncUpdateActiveIndex', 69);
      this.$router.push({
        name: '检测记录',
        params: {
          structureInfoId: obj.structureInfoId,
          monitorStructureId: obj.monitorStructureId,
          activeName: activeName,
          endTime: obj.endTime,
          firstNav: 'evaluation',
          projectId: obj.projectId
        }
      });
    }
  }
};
</script>

<style lang="scss" scoped>
.inspectio {
  height: 55vh;
  font-size: 0.729165vw;
  .title {
    width: 100%;
    font-size: 16px;
    padding-bottom: 20px;
    font-weight: bold;
    display: flex;
    align-items: center;
    justify-content: center;
  }

  .table {
    width: 100%;
    /deep/ .el-table th {
      padding: 0.88vh 0;
      color: #333;
    }
    /deep/ .el-table td {
      padding: 0.88vh 0;
      &:first-child {
        border-top-left-radius: 5px;
        border-bottom-left-radius: 5px;
      }
      &:last-child {
        border-top-right-radius: 5px;
        border-bottom-right-radius: 5px;
      }
    }
    /deep/ .el-table__empty-block {
      border-top: 1px solid #ebeef5;
    }
  }
  &-btn {
    padding: 0 0 1vw;
  }
}

//斑马纹样式
/deep/ .first-row {
  background: #ffffff;
}
/deep/ .second-row {
  background: #f2f4f6;
}

.page {
  display: flex;
  justify-content: center;
  padding: 1vw 0;
}
.pageNation {
  text-align: center;
  /deep/ button {
    background: transparent;
    border: 1px solid #d9d9d9;
    border-radius: 2px;
    background: none !important;
  }
  /deep/ .el-pager li {
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
</style>
