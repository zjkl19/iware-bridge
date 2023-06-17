<template>
  <div class="bcieva">
    <div class="bciTable">
      <el-table :data="tableData">
        <el-table-column
          type="index"
          label="序号"
          width="80"
          align="center"
        ></el-table-column>
        <el-table-column
          v-for="item in titleList"
          :key="item.id"
          :prop="item.prop"
          :label="item.label"
          :width="item.width"
          align="center"
          show-overflow-tooltip
        ></el-table-column>
        <el-table-column label="BCI评价" align="center">
          <template slot-scope="scope">
            <div
              class="tableItem"
              @click="goEvaluate(scope.row.bridgeRoadId, scope.row.is)"
            >
              <div v-if="scope.row.is">详情</div>
              <div v-else>开始评价</div>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="扣分病害" align="center">
          <template slot-scope="scope">
            <div
              class="tableItem"
              @click="goDetail(scope.row.bridgeRoadId)"
              v-if="scope.row.is"
            >
              详情
            </div>
            <div v-else>-</div>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <BCIdetail
      v-if="showDetail"
      class="bciContent"
      :bridgeRoadId="bridgeRoadId"
      @hideEvaluate="hideEvaluate"
      :isEvaluate="isEvaluate"
      :score="structureManage.score"
      :state="state"
    ></BCIdetail>
    <BCIsummary
      v-if="showSummary"
      class="bciContent"
      :bridgeRoadId="bridgeRoadId"
      @hideDetail="hideDetail"
    ></BCIsummary>
  </div>
</template>
<script>
import BCIdetail from './components/BCIdetail';
import BCIsummary from './components/BCIsummary';
import { getBCIEvaluationList } from '@/api/assessment/bciEvaluation';
export default {
  props: {
    structureManage: {
      type: Object,
      default: () => {
        return { id: '', structureName: '' };
      }
    }
  },
  components: {
    BCIdetail,
    BCIsummary
  },
  inject: ['authOpt'],
  watch: {
    structureManage() {
      if (this.structureManage.id) {
        this.getBCIEvaluationList();
      }
    }
  },
  data() {
    return {
      titleList: [
        { id: 1, label: '线路名称', prop: 'roadName' },
        { id: 2, label: '桥跨数量', prop: 'bridgeSpanNum' },
        { id: 3, label: 'BCI评分', prop: 'bCIScore' }
      ],
      tableData: [],
      showDetail: false,
      showSummary: false,
      bridgeRoadId: '',
      isEvaluate: false,
      state: 1
    };
  },
  methods: {
    //查询BCI评价列表
    async getBCIEvaluationList() {
      let params = {
        structureInfoId: this.structureManage.id
      };
      let { code, data } = await getBCIEvaluationList(params);
      if (code == '0000') {
        this.tableData = data;
        if (this.structureManage.roadId) {
          this.goEvaluate(this.structureManage.roadId, false);
        }
      }
    },
    //点击开始评价(isEvaluate: true:详情，false:开始评价)
    goEvaluate(bridgeRoadId, isEvaluate) {
      this.showDetail = true;
      this.bridgeRoadId = bridgeRoadId;
      this.isEvaluate = isEvaluate;
      if (isEvaluate) {
        this.state = 2;
      } else {
        this.state = 1;
      }
    },
    hideEvaluate() {
      this.showDetail = false;
      this.$emit('reflash', 'BCIeva');
      this.getBCIEvaluationList();
    },
    //点击详情
    goDetail(bridgeRoadId) {
      this.showSummary = true;
      this.bridgeRoadId = bridgeRoadId;
    },
    hideDetail() {
      this.showSummary = false;
    }
  },
  mounted() {
    if (this.structureManage.id) {
      this.getBCIEvaluationList();
    }
  }
};
</script>
<style lang="scss" scoped>
.bcieva {
  height: 100%;
  width: 100%;
  padding-top: 2.5vh;
  display: flex;
  flex-direction: column;
  .bciTable {
    height: 100%;
    .tableItem {
      font-size: 0.7vw;
      color: #419aff;
      cursor: pointer;
      &:hover {
        color: rgba(65, 154, 255, 0.8);
      }
    }
    /deep/ .el-table {
      height: 100%;
    }
    /deep/ .el-table__body-wrapper {
      height: 93%;
      overflow-y: auto;
    }
    /deep/ .el-table th {
      color: #333;
    }
    /deep/ .el-table td {
      height: 4.45vh;
      color: #333;
      padding: 0;
    }
  }
  .bciContent {
    position: absolute;
    top: 6.488vh;
    left: 0;
    width: 100%;
    height: 93.513vh;
    // padding-top: 6.488vh;
    // background: #E5E5E5;
    z-index: 4;
  }
}
</style>
