<template>
  <div class="five">
    <!-- 桥梁名称 -->
    <div class="title">{{ structureName }}</div>
    <!-- 表格 -->
    <div class="table">
      <el-table :data="tableData" :row-class-name="tableRowClassName">
        <el-table-column type="index" label="序号" width="100" align="center">
          <template slot-scope="scope">
            {{ (pageNum - 1) * 10 + scope.$index + 1 }}
          </template>
        </el-table-column>
        <el-table-column
          prop="maintainPlanName"
          label="维养计划"
          align="center"
        >
        </el-table-column>
        <el-table-column prop="structureName" label="结构物名称" align="center">
        </el-table-column>
        <el-table-column prop="name" label="维修项目" align="center">
        </el-table-column>
        <el-table-column
          prop="maintainTypeName"
          label="维修类型"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="proposedTime"
          label="计划维修时间"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="maintainTime"
          label="实际维修时间"
          align="center"
        >
        </el-table-column>
        <el-table-column prop="creator" label="维修人员" align="center">
        </el-table-column>
        <el-table-column prop="operation" label="操作" align="center">
          <template slot-scope="scope">
            <div class="btn" @click="routerGo(scope.row)">维修简报</div>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 分页 -->
    <div class="page">
      <el-pagination
        class="pageNation"
        background
        :page-size="pageSize"
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
import { getMaintainRecord } from '@/api/infomanage/bridgeDetail';
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
      pageSize: 5,
      total: 0
    };
  },
  mounted() {
    this.getMaintainRecord();
  },
  methods: {
    // 获取列表
    async getMaintainRecord() {
      let { code, data } = await getMaintainRecord(
        this.structureId,
        this.pageNum
      );
      if (code == '0000') {
        this.tableData = data.list;
        this.total = data.total;
      }
    },
    routerGo(data) {
      let item = { ...data };
      item.type = 2;
      this.$emit('briefingChange', item);
      // this.$router.push({
      //   path: '/maintain/briefReport',
      //   query: { id, parentName: '桥梁详情' }
      // });
    },
    // 分页
    handleCurrentChange(val) {
      this.pageNum = val;
      this.getMaintainRecord();
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
.five {
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
    .btn {
      color: #419aff;
      cursor: pointer;
    }
    /deep/ .el-table th {
      font-size: 0.7vw;
      font-weight: bold;
      color: #333;
      padding: 1.067vh 0;
      border: 0;
      // border-bottom: 1px solid #ebeef5;
    }
    /deep/ .el-table td {
      font-size: 0.7vw;
      color: #333;
      // padding: 1.067vh 0;
      padding: 1.76vh 0;
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
