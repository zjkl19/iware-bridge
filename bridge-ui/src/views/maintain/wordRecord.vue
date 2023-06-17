<template>
  <div class="planM animate__animated animate__fadeIn">
    <div class="border_div">
      <!-- <p class="border_p base-font">维养记录</p> -->
      <div class="border_div2">
        <div>
          <el-button class="export" type="primary" @click="exportFile"
            >批量导出</el-button
          ><el-button class="export" type="primary" @click="importDialog = true"
            >批量导入</el-button
          >
        </div>

        <div>
          <el-select
            v-model="projectId"
            placeholder="请选择项目"
            class="my_select1"
            @change="getStructureListByModel(true)"
            clearable
          >
            <el-option
              v-for="item in projectList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>

          <el-select
            v-model="structureId"
            placeholder="请选择结构物"
            class="my_select1"
            clearable
          >
            <el-option
              v-for="item in structureList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>

          <el-date-picker
            class="my_select1"
            v-model="timeValue"
            type="daterange"
            clearable
            align="center"
            value-format="yyyy-MM-dd"
            range-separator="至"
            start-placeholder="开始月份"
            end-placeholder="结束月份"
          >
          </el-date-picker>

          <el-input
            class="my_select2"
            v-model="username"
            placeholder="维修人员"
            clearable
          />

          <el-select
            v-model="type"
            class="my_select2"
            placeholder="维修类型"
            clearable
          >
            <el-option
              v-for="item in typeList"
              :key="item.id"
              :label="item.label"
              :value="item.id"
            >
            </el-option>
          </el-select>
          <!-- <el-select
            v-model="status"
            class="my_select2"
            placeholder="执行状态"
            clearable
          >
            <el-option
              v-for="item in statusList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select> -->
          <el-button type="primary" class="border_button" @click="getRecordList"
            >查询</el-button
          >
        </div>
      </div>

      <div class="table_div">
        <el-table
          ref="multipleTable"
          class="tableBox"
          :data="tableData"
          :row-class-name="tableRowClassName"
          @select="handleSelection"
          @select-all="handleSelectionChangeAll"
        >
          <el-table-column type="selection" width="50" align="center">
          </el-table-column>
          <el-table-column type="index" label="序号" width="100" align="center">
            <template slot-scope="scope">
              {{ (currentPage - 1) * 10 + scope.$index + 1 }}
            </template>
          </el-table-column>
          <el-table-column
            prop="maintainPlanName"
            label="维养计划"
            align="center"
            show-overflow-tooltip
          >
          </el-table-column>
          <el-table-column
            prop="structureName"
            label="结构物名称"
            align="center"
            show-overflow-tooltip
          >
          </el-table-column>
          <el-table-column
            prop="name"
            label="维修项目"
            align="center"
            show-overflow-tooltip
          >
          </el-table-column>
          <el-table-column
            prop="maintainTypeName"
            label="维修类型"
            align="center"
            show-overflow-tooltip
          >
          </el-table-column>
          <el-table-column
            prop="quantities"
            label="工作量"
            align="center"
            show-overflow-tooltip
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

          <el-table-column label="操作" align="center">
            <template slot-scope="scope">
              <el-link type="primary" @click="goReport(scope.row.id)"
                >维修简报</el-link
              >
            </template>
          </el-table-column>
        </el-table>
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

    <div v-if="showBriefing">
      <briefReport
        :reportId="reportId"
        :parentName="parentName"
        @closed="showBriefing = false"
      ></briefReport>
    </div>

    <!-- 批量导入 -->
    <el-dialog
      class="importDialog"
      title="批量导入"
      :visible.sync="importDialog"
      :before-close="importDialogClose"
      :modal-append-to-body="false"
      :close-on-click-modal="false"
    >
      <el-form class="fileBox" label-width="120px">
        <el-form-item label="第一步：">
          <el-button class="fileBtn" @click="downloadTemplate"
            >下载模板</el-button
          >
          <div class="notice">下载模板文件，根据模板填写信息。</div>
        </el-form-item>
        <el-form-item label="第二步：" required>
          <el-upload
            ref="uploadFile"
            class="reportUpload"
            :action="actionUrl"
            :headers="headers"
            :auto-upload="false"
            :limit="1"
            accept=".xlsx"
            :on-change="handleChange"
            :on-exceed="handleExceed"
            :on-remove="handleRemove"
            :http-request="uploadFile"
            :file-list="fileList"
          >
            <el-button v-if="fileList.length == 0" class="fileBtn"
              >点击上传</el-button
            >
          </el-upload>
          <div class="notice">
            <span>1.表格首行为字段名，不能删除、修改。</span>
            <span
              >2.表头字段标记<span style="color: #f56c6c">*</span
              >为必填项，请务必填写。</span
            >
            <span>3.仅支持.xlsx格式。</span>
          </div>
        </el-form-item>
      </el-form>
      <div class="btn">
        <el-button type="primary" @click="importSubmit('reportForm')"
          >确定</el-button
        >
        <el-button @click="importDialogClose">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  getRecordList,
  exportRecord,
  importMaiExcel
} from '@/api/maintain/record';
import { getProjectListByModel, getStructureListByModel } from '@/api/common';
import briefReport from './briefReport';
export default {
  components: { briefReport },
  data() {
    return {
      //赛选条件
      selParams: {
        projectId: '',
        structureId: '',
        username: '',
        status: '',
        type: '',
        startTime: '',
        entTime: '',
        pageSize: 10,
        pageNum: 1
      },
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        }
      }, // 只能选取当前之后时间
      projectList: [],
      projectId: '',
      structureList: [],
      structureId: '',
      timeValue: '',
      username: '',
      statusList: [
        { id: 0, name: '未完成' },
        { id: 1, name: '已完成' },
        { id: 2, name: '已超时' }
      ],
      status: '',
      typeList: [
        { id: 0, label: '日常保养' },
        { id: 1, label: '小修' },
        { id: 2, label: '中修' },
        { id: 3, label: '大修' }
      ],
      type: '',

      currentPage: 1,
      total: 0,
      tableData: [],
      tableSelectIdList: [],

      options: [],
      exportAll: 0,

      showBriefing: false, //桥梁、隧道简报显示
      reportId: '',
      parentName: '',
      //批量导入数据
      importDialog: false,
      actionUrl: '',
      headers: {
        'X-ROUTER-TOKEN': this.$store.getters.getToken,
        'X-ROUTER-URL': '/normal/record'
      },
      fileList: [],
      fileData: ''
    };
  },
  beforeMount() {
    this.addOpt = false;
    this.$utils.getAuthPage(this); //获取权限
    let params = this.$route.params;
    if (params.id) {
      this.projectId = Number(params.projectId);
      this.structureId = Number(params.id);
    }
  },
  mounted() {
    this.getProjectListByModel(); //获取项目列表
    this.getStructureListByModel(); //获取结构物列表
    this.getRecordList(); //获取记录列表
  },
  methods: {
    //获取项目列表
    async getProjectListByModel() {
      let id = this.$store.getters.getActiveIndex;
      let { code, data } = await getProjectListByModel(id);
      if (code == '0000') {
        this.projectList = data;
      }
    },
    //获取结构物列表
    async getStructureListByModel(clear) {
      let params = {
        projectId: this.projectId,
        powerId: this.$store.getters.getActiveIndex
      };
      let { code, data } = await getStructureListByModel(params);
      if (code == '0000') {
        this.structureList = data;
        if (clear) {
          this.structureId = '';
        }
      }
    },
    //获取记录列表
    async getRecordList() {
      let params = {
        projectId: this.projectId,
        structureId: this.structureId,
        username: this.username,
        status: this.status,
        type: this.type,
        startTime: !!this.timeValue ? this.timeValue[0] : '',
        endTime: !!this.timeValue ? this.timeValue[1] : '',
        pageSize: 10,
        pageNum: 1
      };
      let { code, data } = await getRecordList(params);
      if (code == '0000') {
        this.currentPage = 1;
        this.selParams = params;
        this.tableData = data.list;
        this.total = data.total;
        if (this.exportAll == 1) {
          this.$refs.multipleTable.toggleAllSelection();
        }
        this.exportParams = JSON.stringify(params);
        this.$nextTick(() => {
          if (this.exportAll == 0) {
            let arry = [];
            this.tableData.map((item) => {
              this.tableSelectIdList.map((id) => {
                if (item.id == id) {
                  arry.push(item);
                }
              });
            });
            this.toggleSelection(arry);
          }
        });
      }
    },
    //分页
    async getRecordList2() {
      let { code, data } = await getRecordList(this.selParams);
      if (code == '0000') {
        this.tableData = data.list;
        this.total = data.total;
        if (this.exportAll == 1) {
          this.$refs.multipleTable.toggleAllSelection();
        }
        this.exportParams = JSON.stringify(this.selParams);
        this.$nextTick(() => {
          if (this.exportAll == 0) {
            let arry = [];
            this.tableData.map((item) => {
              this.tableSelectIdList.map((id) => {
                if (item.id == id) {
                  arry.push(item);
                }
              });
            });
            this.toggleSelection(arry);
          }
        });
      }
    },
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex % 2 == 1) {
        return 'first-row';
      } else {
        return 'second-row';
      }
    },
    //表格勾选
    handleSelection(list, row) {
      if (this.exportAll == 1) {
        if (this.tableSelectIdList.includes(row.id)) {
          this.tableSelectIdList.splice(
            this.tableSelectIdList.findIndex((item) => item === row.id),
            1
          );
        } else {
          this.tableSelectIdList.push(row.id);
        }
      } else {
        if (this.tableSelectIdList.includes(row.id)) {
          this.tableSelectIdList.splice(
            this.tableSelectIdList.findIndex((item) => item === row.id),
            1
          );
        } else {
          let idList = [];
          list.map((item) => {
            idList.push(item.id);
          });
          this.tableSelectIdList = this.tableSelectIdList.concat(idList);
        }
      }
      this.tableSelectIdList = [...new Set(this.tableSelectIdList)];
    },
    //表格勾选全部
    handleSelectionChangeAll(list) {
      if (list.length != 0) this.exportAll = 1;
      else this.exportAll = 0;
      this.tableSelectIdList = [];
    },
    //默认勾选
    toggleSelection(rows) {
      if (rows) {
        rows.forEach((row) => {
          this.$refs.multipleTable.toggleRowSelection(row);
        });
      }
    },
    //批量导出
    async exportFile() {
      if (this.exportAll == 0 && this.tableSelectIdList.length == 0) {
        this.$message({
          message: '请选择要导出的数据！',
          showClose: true,
          type: 'warning',
          duration: 2000
        });
        return;
      }
      let params = {
        exportAll: this.exportAll,
        ids: [...new Set(this.tableSelectIdList)],
        filter: JSON.parse(this.exportParams)
      };
      exportRecord(params)
        .then((res) => {
          let fileName =
            '维修记录卡 ' + this.$utils.Dateformat1(new Date()) + '.xls';
          this.$utils.downloadBlob(res, fileName);
        })
        .catch((e) => {
          console.log(e);
        });
    },
    handleCurrentChange(val) {
      this.selParams.pageNum = val;
      this.currentPage = val;
      this.getRecordList2();
    },
    goReport(id) {
      this.showBriefing = true;
      this.reportId = id;
      this.parentName = '维养记录';
      // this.$router.push({
      //   path: '/maintain/briefReport',
      //   query: { id, parentName: '维养记录' }
      // });
    },
    //改变文件
    handleChange(file, fileList) {
      let arry = file.name.split('.');
      let name = arry[arry.length - 1];
      let typeList = ['xlsx'];
      if (!typeList.includes(name)) {
        this.$message.warning('请上传正确的格式');
        this.$refs.uploadFile.clearFiles();
        return;
      }
      this.fileList = fileList;
    },
    //验证文件
    handleExceed(file, fileList) {
      this.$message.warning(
        `当前限制选择 1 个文件，本次选择了 ${file.length} 个文件，共选择了 ${
          file.length + fileList.length
        } 个文件`
      );
    },
    //移除文件
    handleRemove(file, fileList) {
      this.fileList = fileList;
    },
    //上传文件
    uploadFile(file) {
      this.fileData.append('excel', file.file); // append增加数据
    },
    //下载模板
    downloadTemplate() {
      let path = './static/file/maintain.xlsx';
      this.$utils.downloadFile(path, '维养记录数据模板.xlsx');
    },
    //上传批量导入文件
    async importSubmit() {
      if (this.fileList.length == 0) {
        this.$message({
          type: 'warning',
          message: '请上传文件!',
          showClose: true,
          duration: 2000
        });
        return;
      }
      let loading = this.$loading({
        lock: true,
        text: '正在上传解析中，请稍等...',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      });
      this.fileData = new FormData();
      this.$refs.uploadFile.submit();
      try {
        let { code, msg } = await importMaiExcel(this.fileData);
        loading.close();
        if (code == '0000') {
          this.$message({
            type: 'success',
            message: msg,
            showClose: true,
            duration: 2000
          });
          await this.importDialogClose();
          await this.getDetailList();
        } else if (code == '6039') {
          let html = msg.split('\n').join('；');
          this.$message({
            type: 'error',
            dangerouslyUseHTMLString: true,
            message: `<div style="padding-right:10px">${html}</div>`,
            showClose: true,
            duration: 20000
          });
        }
      } catch (error) {
        loading.close();
      }
    },
    importDialogClose() {
      this.importDialog = false;
      this.fileList = [];
    }
  }
};
</script>
<style lang="scss" scoped>
.planM {
  padding: 24px;
  display: flex;
  flex-direction: column;
  .border_p {
    font-size: 0.8333vw;
  }
  .border_div2 {
    padding-bottom: 20px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    .export {
      color: #419aff;
      background: transparent;
      border: 1px solid #419aff;
    }
    .my_select1 {
      width: 280px;
      height: 40px;
      margin-right: 20px;
      /deep/ .el-range-separator {
        width: unset;
      }
    }
    .my_select2 {
      width: 128px;
      height: 40px;
      margin-right: 20px;
    }
  }
  .table_div {
    /deep/ .el-table th {
      font-size: 0.7vw;
      font-weight: bold;
      color: #333;
      padding: 1.067vh 0;
      border: 0;
    }
    /deep/ .el-table td {
      font-size: 0.7vw;
      color: #333;
      padding: 1.715vh 0;
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
}

.pageNation {
  text-align: center;
  padding: 1vw 0;
  /deep/ button {
    background: transparent;
    border: 1px solid #d9d9d9;
    border-radius: 4px;
    background: none !important;
  }
  /deep/ .el-pager li {
    background: transparent;
    border: 1px solid #d9d9d9;
    border-radius: 4px;
  }
  /deep/ .el-pager .active {
    background: none !important;
    color: #1890ff !important;
    border: 1px solid #1890ff;
  }
}

//批量导入弹框
.importDialog {
  .fileBox {
    // padding-right: 50px;
    .fileBtn {
      padding: 8px 15px;
      border: 1px solid #419aff;
      color: #419aff;
    }
    .notice {
      margin-top: 10px;
      line-height: 20px;
      color: #999;
      display: flex;
      flex-direction: column;
      span {
        line-height: 20px;
      }
    }
    .reportUpload {
      display: flex;
      /deep/ .el-upload-list__item-name {
        max-width: 195px;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
      }
    }
  }
  .btn {
    padding-top: 10px;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  /deep/ .el-dialog {
    margin: 0;
    width: 450px;
  }
  /deep/ .el-dialog__body {
    padding: 16px 24px !important;
  }
}

//斑马纹样式
/deep/ .first-row {
  background: #ffffff;
}
/deep/ .second-row {
  background: #f2f4f6;
}
</style>
