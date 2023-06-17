<template>
  <div class="animate__animated animate__fadeIn">
    <div class="footer-wrapper">
      <!-- <div class="title">维护日志</div> -->
      <div class="f-top">
        <div class="left">
          <el-button v-if="addOpt" type="primary" @click="addBtn"
            >新增日志</el-button
          >
        </div>
        <div class="right">
          <el-select
            v-model="projectId"
            class="m-r-10"
            placeholder="请选择项目"
            clearable
          >
            <el-option
              v-for="item in projectList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            ></el-option>
          </el-select>
          <el-date-picker
            class="m-r-10"
            v-model="startTime"
            type="date"
            placeholder="选择时间"
            clearable
            value-format="yyyy-MM-dd"
          ></el-date-picker>
          <el-input
            class="m-r-10"
            v-model="keyword"
            placeholder="关键词"
            clearable
          />
          <el-button type="primary" @click="getSensorLog">搜索</el-button>
        </div>
      </div>
      <div class="f-bottom">
        <el-row>
          <!-- 维护日志 -->
          <normalTable
            :tableData="tableLogList"
            :titleList="titleList"
            :opList="opList"
            :align="'left'"
            :opWidth="'220'"
            :pageNum="currentPage"
            @tableClick="tableClick"
          ></normalTable>
          <!-- 分页器 -->
          <div class="diaryPage">
            <el-pagination
              class="pageNation"
              background
              @current-change="handleCurrentChange"
              :current-page.sync="currentPage"
              :pager-count="5"
              :page-size="10"
              layout="total, prev, pager, next"
              :total="dataTotal"
            ></el-pagination>
          </div>
        </el-row>
      </div>
    </div>
    <voperation
      :dialog-visible="operation.visible"
      :dialog-state="operation.state"
      :edit-id="operation.id"
      :birdge-item="birdgeItem"
      @cancelBtn="operation.visible = false"
      @saveBtn="saveBtn"
    />
  </div>
</template>
<script>
import {
  getSensorLog,
  addSensorLog,
  updSensorLog,
  delSensorLog
} from '@/api/online/diary';
import { getProjectListByPowerId, getProjectListByOnTime } from '@/api/common';
import normalTable from '@/components/table/normalTable';
import Voperation from './components/operation';
export default {
  components: {
    Voperation,
    normalTable
  },
  data() {
    return {
      selParams: {
        projectId: '',
        startTime: '',
        keyword: '',
        pageNum: 1,
        pageSize: 10
      },
      projectList: [],
      projectAddList: [],
      projectId: '',
      currentPage: 1,
      dataTotal: 7,
      startTime: '',
      keyword: '',
      titleList: [
        { id: 1, prop: 'projectName', label: '项目名称', width: '180' },
        { id: 2, prop: 'createTime', label: '创建时间', width: '180' },
        { id: 3, prop: 'describe', label: '日志描述', width: '' },
        { id: 4, prop: 'solution', label: '解决方案', width: '' },
        { id: 5, prop: 'remarks', label: '备注', width: '' }
      ],
      tableLogList: [], // 维护日志列表
      opList: [
        { id: 1, name: '查看', opShow: true },
        {
          id: 2,
          name: '修改',
          opShow: true
        },
        {
          id: 3,
          name: '删除',
          opShow: true
        }
      ],
      operation: {
        visible: false,
        state: 'add',
        id: 0
      },
      birdgeItem: {
        inspectionDate: '',
        remarks: '',
        structureType: '',
        structureName: '',
        inspectionDepartment: '',
        inspectorName: ''
      }
    };
  },
  beforeMount() {
    this.$utils.getAuthPage(this); //获取权限
  },
  mounted() {
    this.getSensorLog(); //获取日志列表
    this.getProjectListByPowerId(); //查询项目列表
    this.getProjectListByOnTime(); //获取添加项目列表
  },
  methods: {
    //查询项目列表
    async getProjectListByPowerId() {
      let id = this.$store.getters.getActiveIndex;
      let { code, data } = await getProjectListByPowerId(id);
      if (code == '0000') {
        this.projectList = data;
      }
    },
    //获取添加项目列表
    async getProjectListByOnTime() {
      let id = this.$store.getters.getActiveIndex;
      let { code, data } = await getProjectListByOnTime(id);
      if (code == '0000') {
        this.projectAddList = data;
      }
    },
    //获取日志列表
    async getSensorLog() {
      var params = {
        projectId: this.projectId,
        startTime: !!this.startTime ? this.startTime : '',
        endTime: !!this.startTime ? this.startTime + ' 23:59:59' : '',
        keyword: this.keyword,
        pageNum: 1,
        pageSize: 10
      };
      let { code, data } = await getSensorLog(params);
      if (code == '0000') {
        this.currentPage = 1;
        this.tableLogList = data.list;
        this.dataTotal = data.total;
        this.selParams = params;
      }
    },
    async getSensorLog2() {
      let { code, data } = await getSensorLog(this.selParams);
      if (code == '0000') {
        this.tableLogList = data.list;
        this.dataTotal = data.total;
      }
    },
    async saveBtn(params) {
      if (this.operation.state == 'add') {
        let { code } = await addSensorLog(params);
        if (code == '0000') {
          this.$message({
            type: 'success',
            message: '添加成功!',
            showClose: true,
            duration: 2000
          });
          await this.getSensorLog2();
          this.operation.visible = false;
        }
      } else if (this.operation.state == 'edit') {
        let { code } = await updSensorLog(params);
        if (code == '0000') {
          this.$message({
            type: 'success',
            message: '修改成功!',
            showClose: true,
            duration: 2000
          });
          await this.getSensorLog2();
          this.operation.visible = false;
        }
      } else {
        this.operation.visible = false;
      }
    },
    handleCurrentChange(val) {
      //重置当前页
      this.currentPage = val;
      this.selParams.pageNum = val;
      //请求数据
      //更新展示
      this.getSensorLog2();
    },
    // 添加功能
    addBtn() {
      this.operation.id = 0;
      this.operation.state = 'add';
      this.operation.visible = true;
    },
    tableClick(index, data) {
      if (index == 1) this.lookBtn(data);
      else if (index == 2) this.editBtn(data);
      else if (index == 3) this.deleteItem(data);
    },
    lookBtn(item) {
      if (item.remarks == '') {
        item.remarks = ' ';
      }
      this.birdgeItem = { ...item };
      this.operation.id = item.id;
      this.operation.state = 'read';
      this.operation.visible = true;
    },
    // 编辑功能
    editBtn(item) {
      this.birdgeItem = { ...item };
      this.operation.id = item.id;
      this.operation.state = 'edit';
      this.operation.visible = true;
    },
    deleteItem(data) {
      //删除框
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        showClose: true
      })
        .then(async () => {
          let { code } = await delSensorLog(data.id);
          if (code == '0000') {
            this.$message({
              type: 'success',
              message: '删除成功!',
              showClose: true,
              duration: 2000
            });
            await this.getSensorLog2();
          }
        })
        .catch(() => {});
    }
  }
};
</script>
<style lang="scss" scoped>
/deep/.el-table td {
  padding: 0;
}
.footer-wrapper {
  padding: 20px;
  border-radius: 4px;
  .f-title {
    font-size: 16px;
  }
  .f-top {
    padding-bottom: 20px;
    display: flex;
    justify-content: space-between;
    .left {
    }
    .right {
      display: flex;
      .m-r-10 {
        width: 240px !important;
        margin-right: 20px;
      }
    }
  }
  .f-bottom {
    /deep/ .el-table th {
      font-weight: bold;
      color: #333;
    }
  }
}
</style>
<style lang="scss">
#ty .el-col {
  border-radius: 4px;
}

#ty .bg-purple {
  /* min-height: 180px; */
  height: 108px;
  width: 282px;
}

#ty .grid-content {
  /* min-height: 380px; */
  min-height: 240px;
}

#ty .grid-contentmap {
  height: 502px;
  padding: 18px;
}

#ty .detection-num {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 44px;
  font-family: SourceHanSansCN-Medium;
}
#ty .center {
  text-align: center;

  font-family: SourceHanSansCN-Medium;
  font-size: 48px;
  font-weight: normal;
  font-stretch: normal;
  line-height: 19px;
  letter-spacing: 0px;
  color: #fafafb;
}
#ty .font6 {
  font-size: 16px;
  font-family: SourceHanSansCN-Regul;
  color: #aec0b6;
  position: relative;
}
#ty .font {
  font-size: 16px;
  font-family: SourceHanSansCN-Regul;
  font-weight: normal;
  font-stretch: normal;
  line-height: 19px;
  letter-spacing: 0px;
  position: relative;
  top: 13px;
  left: 13px;
}
#ty .font-Y {
  font-size: 16px;
  font-family: SourceHanSansCN-Regul;
  position: relative;
  top: 13px;
  left: 13px;
}

#ty #yuanjiao {
  font-family: Arial;
  border-radius: 8px;
}
#ty .lin-purple-1 {
  background-image: linear-gradient(90deg, #4180ff 0%, #41a8ff 100%),
    linear-gradient(#192436, #192436);
  background-blend-mode: normal, normal;
  border-radius: 8px;
}
#ty .lin-purple-2 {
  background-image: linear-gradient(90deg, #ff7f2f 0%, #ff7353 100%),
    linear-gradient(#192436, #192436);
  background-blend-mode: normal, normal;
  border-radius: 8px;
}
#ty .lin-purple-3 {
  background-image: linear-gradient(90deg, #10da74 0%, #27d6b7 100%),
    linear-gradient(#192436, #192436);
  background-blend-mode: normal, normal;
  border-radius: 8px;
}
#ty .lin-purple-4 {
  background-image: linear-gradient(90deg, #f2994a 0%, #f2c94c 100%),
    linear-gradient(#192436, #192436);
  background-blend-mode: normal, normal;
  border-radius: 8px;
}

/* 地图样式 */
#ty .bm-view {
  width: 100%;
  height: 100%;
}

/* 地图的桥梁搜索框的样式 */
#ty .L-map-topDiv {
  width: 240px;
  height: 40px;
  top: 30px;
  right: 40px;
  position: absolute;
  z-index: 9999;
  background-color: rgba(04, 09, 14, 0.3);
  border-radius: 4px;
}

.warning-level-info-box {
  border-radius: 8px;
  height: 250px;
  position: relative;
}

.warning-level-info-box .warning-level-info-box_title {
  position: absolute;
  font-size: 16px;
  left: 24px;
  top: 24px;
}

.warning-level-info-box_charts-box {
  width: 100%;
  position: absolute;
  top: 56px;
}

.warning-level-info-box_charts-footer {
  width: 100%;
  position: absolute;
  bottom: 52px;
  text-align: center;
}

.warning-level-footer_info-point_fixed {
  font-size: 24px;
  color: #328ff1;
}

.warning-level-footer_info-point_unfix {
  font-size: 24px;
  color: rgb(255, 67, 67);
}

.warning-level-footer_info-font {
  font-size: 16px;
  color: #808a96;
  margin-right: 8px;
}
.diaryPage {
  .pageNation {
    text-align: center;
    padding: 1vw 0;
    button {
      background: transparent;
      border: 1px solid #d9d9d9;
      border-radius: 2px;
      background: none !important;
    }
    .el-pager li {
      background: transparent;
      border: 1px solid #d9d9d9;
      border-radius: 2px;
    }
    .el-pager .active {
      background: none !important;
      color: #1890ff !important;
      border: 1px solid #1890ff;
    }
  }
}
</style>
