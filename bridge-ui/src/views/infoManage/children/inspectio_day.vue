<template>
  <div class="inspectio">
    <!-- 桥梁名称 -->
    <div class="title">{{ structureName }}</div>
    <!-- 数据表格 -->
    <div class="table">
      <normalTable
        :tableData="tableData"
        :titleList="titleList"
        :opList="opList"
        :pageNum="pageNum"
        @tableClick="tableClick"
      ></normalTable>
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
import { getInspectionRecord } from '@/api/infomanage/bridgeDetail';
import normalTable from '@/components/table/normalTable';
export default {
  components: { normalTable },
  inject: ['params'],
  data() {
    return {
      addOpt: this.params().addOpt,
      updateOpt: this.params().updateOpt,
      deleteOpt: this.params().deleteOpt,
      structureId: this.params().structureId,
      structureName: this.params().structureName,
      //表格数据
      titleList: [
        { id: 2, prop: 'inspectionTime', label: '巡查时间', width: '' },
        { id: 3, prop: 'structureName', label: '结构物名称', width: '' },
        { id: 4, prop: 'inspectionPlanType', label: '巡查类型', width: '' },
        { id: 5, prop: 'inspector', label: '巡查人员', width: '' },
        { id: 6, prop: 'diseaseCount', label: '巡查病害数', width: '' }
      ],
      tableData: [],
      opList: [{ id: 1, name: '巡查简报', opShow: true, type: 'checkOpt' }],
      pageNum: 1,
      total: 0
    };
  },
  mounted() {
    this.getInspectionRecord();
  },
  methods: {
    // 获取列表
    async getInspectionRecord() {
      let { code, data } = await getInspectionRecord(
        this.structureId,
        this.pageNum
      );
      if (code == '0000') {
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
    tableClick(index, data) {
      let item = { ...data };
      item.type = 1;
      this.$emit('briefingChange', item);
      // this.$router.push({
      //   path: '/normal/bridgeBriefing',
      //   query: {
      //     id: data.id,
      //     parentName: '桥梁详情'
      //   }
      // });
    },
    // 分页
    handleCurrentChange(val) {
      this.pageNum = val;
      this.getInspectionRecord();
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
