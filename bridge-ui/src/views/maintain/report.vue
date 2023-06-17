<template>
  <div class="report animate__animated animate__fadeIn">
    <div class="border_div">
      <!-- <p class="border_p base-font">维养报告</p> -->
      <div class="border_div2">
        <el-select
          v-model="projectPlanId"
          placeholder="请选择项目"
          class="border_select my_select1"
          clearable
        >
          <el-option
            v-for="item in projectPlanList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          >
          </el-option>
        </el-select>

        <el-input
          class="border_select my_select1"
          v-model="name"
          placeholder="关键词搜索"
          clearable
        ></el-input>

        <el-button type="primary" class="border_button" @click="getReportList"
          >查询</el-button
        >
        <el-button
          v-if="addOpt"
          type="primary"
          class="border_button"
          @click="showDialog(1)"
          >上传报告</el-button
        >
      </div>

      <div class="border_div3">
        <el-table
          class="tableBox"
          :data="tableData"
          :row-class-name="tableRowClassName"
        >
          <el-table-column type="index" label="序号" width="100" align="center">
            <template slot-scope="scope">
              {{ (currentPage - 1) * 10 + scope.$index + 1 }}
            </template>
          </el-table-column>
          <el-table-column
            prop="projectName"
            label="项目名称"
            align="center"
            show-overflow-tooltip
          >
          </el-table-column>
          <el-table-column
            prop="name"
            label="报告名称"
            align="center"
            show-overflow-tooltip
          >
          </el-table-column>
          <el-table-column prop="username" label="上传人员" align="center">
          </el-table-column>
          <el-table-column prop="createTime" label="上传时间" align="center">
          </el-table-column>
          <el-table-column label="操作" align="center" width="180">
            <template slot-scope="scope">
              <el-link
                v-if="updateOpt"
                type="primary"
                @click="showDialog(2, scope.row)"
                >修改</el-link
              >

              <el-link
                v-if="deleteOpt"
                type="primary"
                class="border_link"
                @click="deleteItem(scope.row)"
                >删除</el-link
              >
              <el-link
                type="primary"
                class="border_link"
                @click="download(scope.row)"
                >下载</el-link
              >
            </template>
          </el-table-column>
        </el-table>
      </div>
      <el-pagination
        class="pageNation"
        background
        :current-page.sync="currentPage"
        :page-size="pageSize"
        :pager-count="5"
        layout="total, prev, pager, next"
        :total="total"
        @current-change="handleCurrentChange"
      >
      </el-pagination>
    </div>

    <!-- 上传报告 -->
    <el-dialog
      class="reportDialog"
      :title="reportDialogTitle"
      :visible.sync="showUploadReport"
      :before-close="reportDialigClose"
      :modal-append-to-body="false"
      width="30vw"
      :close-on-click-modal="false"
    >
      <el-form
        v-if="showUploadReport"
        class="reportBox"
        ref="reportForm"
        :model="reportForm"
        :rules="reportRules"
        label-width="120px"
      >
        <el-form-item label="选择项目：" prop="projectId">
          <el-select
            v-model="reportForm.projectId"
            placeholder="请选择项目"
            :disabled="reportDialogTitle == '修改报告'"
            style="width: 100%"
          >
            <el-option
              v-for="item in projectList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="报告名称：" prop="name">
          <el-input
            class="reportName"
            v-model="reportForm.name"
            placeholder="请输入报告名称"
            maxlength="25"
            clearable
            show-word-limit
          ></el-input>
        </el-form-item>
        <el-form-item label="上传报告：" required>
          <el-upload
            ref="uploadFile"
            class="reportUpload"
            action="/bridge/report/3"
            :auto-upload="false"
            :limit="1"
            accept=".doc,.docx,.pdf"
            :on-change="handleChange"
            :on-exceed="handleExceed"
            :on-remove="handleRemove"
            :http-request="uploadFile"
            :file-list="fileList"
          >
            <el-button v-if="fileList.length == 0" size="small" type="primary"
              >点击上传</el-button
            >
          </el-upload>
        </el-form-item>
      </el-form>
      <div class="btn">
        <el-button type="primary" @click="reportSubmit('reportForm')"
          >确定</el-button
        >
        <el-button @click="reportDialigClose">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  getProjectListByOnTime,
  getProjectListByModel,
  getReportList,
  addReport,
  updReport,
  delReport,
  downLoadReport
} from '@/api/common';
export default {
  components: {},
  props: {},
  data() {
    return {
      //查询条件
      selParams: {
        name: '',
        projectId: '',
        pageSize: 10,
        pageNum: 1,
        type: 3
      },
      projectPlanList: [],
      projectPlanId: '',
      projectList: [],
      projectId: '',
      name: '',

      currentPage: 1,
      pageSize: 10,
      total: 1,
      tableData: [],

      //上传报告
      reportDialogTitle: '',
      showUploadReport: false,
      typeList: [{ id: 1, label: '张三' }],
      reportRules: {
        name: [{ required: true, message: '请输入报告名称', trigger: 'blur' }],
        projectId: [
          { required: true, message: '请选择项目', trigger: 'change' }
        ]
        // typeId: [{ required: true, message: '请选择', trigger: 'change' }]
      },
      reportForm: {
        projectId: '',
        name: ''
      },
      fileList: []
    };
  },
  beforeMount() {
    this.$utils.getAuthPage(this); //获取权限
  },
  mounted() {
    this.getReportList(); //获取报告列表
    this.getProjectListByOnTime(); //获取项目列表
    this.getProjectListByModel(); //获取有计划的项目列表
  },
  methods: {
    //获取报告列表
    async getReportList() {
      let params = {
        name: this.name,
        projectId: this.projectPlanId,
        pageSize: 10,
        pageNum: 1,
        type: 3
      };
      let { code, data } = await getReportList(params);
      if (code == '0000') {
        this.currentPage = 1;
        this.selParams = params;
        this.tableData = data.list;
        this.total = data.total;
      }
    },
    // 分页
    async getReportList2() {
      let { code, data } = await getReportList(this.selParams);
      if (code == '0000') {
        this.tableData = data.list;
        this.total = data.total;
      }
    },
    //获取项目列表
    async getProjectListByOnTime() {
      let id = this.$store.getters.getActiveIndex;
      let { code, data } = await getProjectListByOnTime(id);
      if (code == '0000') {
        this.projectList = data;
      }
    },
    //获取有计划的项目列表
    async getProjectListByModel() {
      let id = this.$store.getters.getActiveIndex;
      let { code, data } = await getProjectListByModel(id);
      if (code == '0000') {
        this.projectPlanList = data;
      }
    },
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex % 2 == 1) {
        return 'first-row';
      } else {
        return 'second-row';
      }
    },
    //打开新增、修改弹框
    showDialog(index, data) {
      if (index == 1) {
        this.reportDialogTitle = '上传报告';
      } else {
        this.reportDialogTitle = '修改报告';
        let fileArry = data.path.split('.');
        this.fileList = [
          { name: data.name + '.' + fileArry[fileArry.length - 1] }
        ];
        this.reportForm = JSON.parse(JSON.stringify(data));
      }
      this.showUploadReport = true;
    },
    //关闭上传报告弹框
    reportDialigClose() {
      this.reportForm = {
        projectId: '',
        name: ''
      };
      this.fileList = [];
      this.$nextTick(() => {
        this.showUploadReport = false;
      });
    },
    //改变文件
    handleChange(file, fileList) {
      let fileArry = file.name.split('.');
      let name = fileArry[fileArry.length - 1];
      let arryList = ['doc', 'docx', 'pdf'];
      if (!arryList.includes(name)) {
        this.$message({
          message: '文件格式不正确！',
          showClose: true,
          type: 'warning',
          duration: 2000
        });
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
      this.fileData.append('file', file.file); // append增加数据
    },
    //上传报告
    reportSubmit(formName) {
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          if (this.fileList.length == 0) {
            this.$message({
              message: '请上传报告！',
              showClose: true,
              type: 'warning',
              duration: 2000
            });
            return;
          }
          this.fileData = new FormData();
          this.$refs.uploadFile.submit();
          this.fileData.append('params', JSON.stringify(this.reportForm));
          if (this.reportDialogTitle == '上传报告') {
            let { code } = await addReport(this.fileData, 3);
            if (code == '0000') {
              this.$message({
                message: '添加成功！',
                showClose: true,
                type: 'success',
                duration: 2000
              });
              await this.reportDialigClose();
              await this.getProjectListByModel();
              await this.getReportList();
            }
          } else {
            let { code } = await updReport(this.fileData, this.reportForm.id);
            if (code == '0000') {
              this.$message({
                message: '修改成功！',
                showClose: true,
                type: 'success',
                duration: 2000
              });
              await this.reportDialigClose();
              await this.getReportList();
            }
          }
        } else {
          return false;
        }
      });
    },
    //分页功能
    handleCurrentChange(val) {
      this.selParams.pageNum = val;
      this.currentPage = val;
      this.getReportList2();
    },
    //删除报告
    deleteItem(data) {
      //删除框
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        showClose: true
      })
        .then(async () => {
          let { code } = await delReport(data.id);
          if (code == '0000') {
            this.$message({
              message: '删除成功！',
              showClose: true,
              type: 'success',
              duration: 2000
            });
            await this.getReportList();
          }
        })
        .catch(() => {});
    },
    //下载报告
    download(data) {
      this.$confirm('确定下载该维养报告?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'success',
        showClose: true
      })
        .then(async () => {
          downLoadReport(data.id)
            .then((res) => {
              let fileName = data.name + '.' + data.path.split('.')[1];
              this.$utils.downloadBlob(res, fileName);
            })
            .catch();
        })
        .catch(() => {});
    }
  }
};
</script>
<style lang="scss" scoped>
.report {
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
    justify-content: flex-end;
    .my_select1 {
      width: 280px;
      height: 3.7037vh;
    }
    .border_select {
      margin-left: 1.04166vw;
    }
    .border_button {
      margin-left: 1.04166vw;
    }
  }
  .border_div3 {
    .border_link {
      margin-left: 1.041666vw;
    }
    /deep/ .el-table__empty-block {
      border-top: 1px solid #ebeef5;
    }
    /deep/ .el-table th {
      font-size: 0.7vw;
      font-weight: bold;
      color: #333;
      // padding: 1.067vh 0;
      border: 0;
    }
    /deep/ .el-table td {
      font-size: 0.7vw;
      color: #333;
      // padding: 1.715vh 0;
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
  }
}

.report .pageNation {
  text-align: center;
  padding: 1vw 0;
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

//上传报告弹框
.reportDialog {
  .reportBox {
    padding-right: 50px;
    .reportName {
      /deep/ .el-input__inner {
        padding-right: 75px;
      }
    }
    .reportUpload {
      display: flex;
    }
    /deep/ .el-upload-list {
      max-width: 100%;
    }
  }
  .btn {
    padding-top: 10px;
    display: flex;
    align-items: center;
    justify-content: center;
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
