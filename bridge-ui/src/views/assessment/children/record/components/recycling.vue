<template>
  <div id="recycling">
    <div class="border_title boxShadow">
      <div class="border_div">
        <span class="border_span" @click="back"> 桥梁病害 </span>
        <span class="border_span2">/</span>
        <span class="border_span2">回收站</span>
      </div>
    </div>
    <div class="table_div boxShadow">
      <div>
        <el-button type="primary" @click="back" class="table_button"
          >返回</el-button
        >
      </div>
      <div class="table_div2">
        <el-table :data="tableData" stripe id="totalTable">
          <el-table-column type="index" label="序号" align="center">
          </el-table-column>
          <el-table-column prop="creator" label="检测人员" align="center">
          </el-table-column>
          <el-table-column
            prop="createTime"
            label="上传日期"
            min-width="40%"
            align="center"
          >
          </el-table-column>
          <el-table-column prop="isDelete" label="回收原因" align="center">
            <template slot-scope="scope">
              <span v-if="scope.row.isDelete != '1'">多人检测同一部位</span>
              <span v-else>病害被删除</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="option"
            label="操作"
            align="center"
            width="300"
          >
            <template slot-scope="scope">
              <el-button
                @click="viewDetail(scope.row)"
                type="text"
                size="small"
                class="table_button2"
                >查看记录详情</el-button
              >
              <el-button
                v-if="!isOriginalRecord"
                @click="replaceRecord(scope.row)"
                class="table_button3"
                type="text"
                size="small"
                >{{
                  scope.row.isDelete == '1' ? '恢复记录' : '替换当前记录'
                }}</el-button
              >
            </template>
          </el-table-column>
        </el-table>
        <!-- 分页器 -->
        <div class="table_page" id="pager">
          <el-pagination
            @current-change="handleCurrentChange"
            :current-page.sync="currentPage"
            :page-size="pageSize"
            layout="prev, pager, next, total"
            :total="dataTotal"
          ></el-pagination>
        </div>
      </div>
    </div>

    <recordDetail
      v-if="showRecordDetail"
      :clickPlan="clickPlan"
      class="recordDetail"
      @backRcyclig="backRcyclig"
      @backDisease="backDisease"
    ></recordDetail>
  </div>
</template>
<script>
import {
  getRecyclingList,
  getRecyclingCount,
  replaceRecord
} from '@/api/assessment/disease';
import recordDetail from './recordDetail';
import { isOriginalRecordByStructure } from '@/api/assessment/record';
export default {
  components: {
    recordDetail
  },
  props: {
    structureManage: Object
  },
  data() {
    return {
      //选择的计划id
      clickPlan: {},
      //查看记录详情
      showRecordDetail: false,
      isOriginalRecord: false,
      //当前页数
      currentPage: 1,
      //总页数
      dataTotal: 0,
      //每页数量
      pageSize: 10,
      //跳转所需要携带的信息
      returnManage: '',
      //表格数据
      tableData: [
        // { id: 1 }
      ]
    };
  },
  mounted() {
    //初始化
    if (this.structureManage.id != undefined) {
      this.init();
    }
    this.isOriginalRecordByStructure();
  },
  methods: {
    //是否有上传记录
    async isOriginalRecordByStructure() {
      let { code, data } = await isOriginalRecordByStructure(
        this.structureManage.id
      );
      if (code == '0000') {
        this.isOriginalRecord = data;
      }
    },
    //返回到回收站
    backRcyclig() {
      this.init();
      this.showRecordDetail = false;
    },
    //返回到桥梁病害
    backDisease() {
      this.showRecordDetail = false;
      this.$emit('backDisease');
    },
    async init() {
      let params = {
        id: this.structureManage.id,
        currentPage: this.currentPage,
        pageSize: this.pageSize
      };
      let { code, data } = await getRecyclingList(params);
      if (code == '0000') {
        this.dataTotal = data.total;
        this.tableData = data.list.map((text, index) => {
          let arr = {
            id: (this.currentPage - 1) * 10 + index + 1,
            creator: text.creator,
            createTime: text.createTime,
            isDelete: text.isDelete
          };
          return arr;
        });
      }
    },
    //返回
    back() {
      this.$emit('backDisease');
    },
    //查看记录详情
    viewDetail(row) {
      this.showRecordDetail = true;
      this.clickPlan = row;
      this.clickPlan.structureName = this.structureManage.structureName;
      this.clickPlan.id = this.structureManage.id;
    },
    //替换当前记录详情
    replaceRecord(row) {
      this.$confirm('是否确定当前操作？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async () => {
          let recObj = await replaceRecord(row.creator, row.createTime);
          if (recObj.code == '0000') {
            let params = {
              id: this.structureManage.id,
              currentPage: this.currentPage,
              pageSize: this.pageSize
            };
            let RecyclingObj = await getRecyclingList(params);
            if (RecyclingObj.code == '0000') {
              //判断是否需要退页
              if (RecyclingObj.data.length == 0 && this.currentPage != 1) {
                this.currentPage = this.currentPage - 1;
                //获取数据
                let params1 = {
                  id: this.structureManage.id,
                  currentPage: this.currentPage,
                  pageSize: this.pageSize
                };
                let RecyclingObj2 = await getRecyclingList(params1);
                if (RecyclingObj2.code == '0000') {
                  this.tableData = RecyclingObj2.data.list.map(
                    (text, index) => {
                      this.dataTotal = RecyclingObj2.data.total;
                      let arr = {
                        id: (this.currentPage - 1) * 10 + index + 1,
                        creator: text.creator,
                        createTime: text.createTime,
                        isDelete: text.isDelete
                      };
                      return arr;
                    }
                  );
                  this.$message({
                    message: '操作成功!',
                    type: 'success',
                    showClose: true,
                    duration: 2000
                  });
                }
              } else {
                this.dataTotal = RecyclingObj.data.total;
                this.tableData = RecyclingObj.data.list.map((text, index) => {
                  let arr = {
                    id: (this.currentPage - 1) * 10 + index + 1,
                    creator: text.creator,
                    createTime: text.createTime,
                    isDelete: text.isDelete
                  };
                  return arr;
                });
                this.$message({
                  message: '操作成功!',
                  type: 'success',
                  showClose: true,
                  duration: 2000
                });
              }
            }
          }
        })
        .catch(() => {});
    },
    //分页
    async handleCurrentChange(page) {
      this.currentPage = page;
      let params = {
        id: this.structureManage.id,
        currentPage: this.currentPage,
        pageSize: this.pageSize
      };
      //获取数据
      let { code, data } = await getRecyclingList(params);
      if (code == '0000') {
        this.tableData = data.list.map((text, index) => {
          let arr = {
            id: (this.currentPage - 1) * 10 + index + 1,
            creator: text.creator,
            createTime: text.createTime
          };
          return arr;
        });
      }
    }
  }
};
</script>
<style lang="scss" scoped>
#recycling #totalTable .el-table__header tr,
#recycling #totalTable .el-table__header th {
  padding: 0;
  height: 4.8vh;
  //   text-align: center;
}
#recycling #totalTable .el-table__body tr,
#recycling #totalTable .el-table__body td {
  padding: 0;
  height: 4.8vh;
  //   text-align: center;
}

#recycling #totalTable .el-table__header tr,
#recycling #totalTable .el-table__header th {
  background: white !important;
}
/* 分页样式 */
#recycling #pager .el-dialog,
#recycling #pager .el-pager li {
  // background: #141e30 !important;
  color: #808a96 !important;
}

#recycling #pager .el-pagination button:disabled {
  color: #808a96 !important;
  // background-color: #141e30 !important;
  cursor: not-allowed;
}

#recycling #pager .el-pagination .btn-next {
  padding-left: 12px;
  // background-color: #141e30 !important;
  color: #808a96 !important;
}

#recycling #pager .el-pagination .btn-prev {
  padding-right: 12px;
  color: #808a96 !important;
  // background-color: #141e30 !important;
}

#recycling #pager .el-pager li.active {
  cursor: default;
  color: #aec0d8 !important;
  background-color: #419aff !important;
  border-radius: 4px;
}
#recycling .recordDetail {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgb(246, 247, 251);
  z-index: 10;
}
#recycling #totalTable th {
  background: rgb(244, 244, 244) !important;
  font-family: Microsoft YaHei;
}
#recycling #totalTable td {
  // text-align: center !important;
  font-family: Microsoft JhengHei UI;
}
#recycling #totalTable tr,
#recycling #totalTable td {
  font-size: 1.4vh;
  padding: 0;
  height: 6vh;
}
.el-message-box {
  width: 22vw;
  height: 15.6vh;
}
.el-message-box__message {
  font-size: 1.4vh;
}
.el-message-box__title {
  font-size: 1.8vh;
  line-height: 1.8vh;
  margin-left: 0.5vw;
  margin-top: 0.5vh;
}
.el-message-box__headerbtn {
  top: 1.5vh;
  right: 1vw;
  font-size: 1.6vh;
}
.el-message-box__header {
  padding: 1.5vh 1.5vh 1vw;
  height: 3.6vh;
}
.el-message-box__status + .el-message-box__message {
  margin-top: 1.5vh;
  padding-left: 2vw;
  padding-right: 1vw;
}
.el-message-box__status.el-icon-warning {
  font-size: 3vh !important;
}
.el-message-box__message p {
  height: 3vh;
  line-height: 3vh;
}
#recycling .el-button--mini,
#recycling .el-button--small {
  font-size: 1.2vh;
  height: 3.2vh;
  width: 3vw;
}
.el-message-box__content {
  padding: 1vh 1.5vh;
}
#recycling {
  background: rgb(245, 245, 245);
}
.border_title {
  width: 100%;
  height: 5.6vh;
  background: white;
  .border_div {
    margin-left: 2.8vw;
    .border_span {
      line-height: 5.6vh;
      font-weight: bold;
      font-size: 1.4vh;
      cursor: pointer;
    }
    .border_span2 {
      line-height: 5.6vh;
      font-size: 1.4vh;
    }
  }
}
.table_div {
  margin-left: 2.8vw;
  margin-right: 2.8vw;
  background: white;
  margin-top: 2vh;
  border-radius: 8px;
  height: 83.8vh;
  .table_button {
    margin-top: 3.5vh;
    margin-left: 1.5vw;
    width: 5vw;
    height: 4vh;
    font-size: 1.4vh;
  }
  .table_div2 {
    margin-top: 1.5%;
    margin-left: 1.5vw;
    margin-right: 1.5vw;
    .table_button2 {
      width: 2vw;
      height: 4vh;
      font-size: 1.4vh;
    }
    .table_button3 {
      width: 2vw;
      height: 4vh;
      font-size: 1.4vh;
      margin-left: 4vw;
    }
    .table_page {
      text-align: center;
    }
  }
}
</style>
