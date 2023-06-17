<template>
  <div class="tableManage animate__animated animate__fadeIn">
    <!-- <div class="title">监测报表</div> -->
    <div class="tableManageTop">
      <div class="topLeft">
        <div @click="typeChange(1)" :class="{ activeClass: activeIndex == 1 }">
          每日简报
        </div>
        <div @click="typeChange(2)" :class="{ activeClass: activeIndex == 2 }">
          月度简报
        </div>
        <div @click="typeChange(3)" :class="{ activeClass: activeIndex == 3 }">
          年度分析报告
        </div>
      </div>
      <div class="topRight">
        <el-select
          class="select"
          v-model="projectId"
          placeholder="请选择项目"
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
        <el-input
          class="select"
          v-model="keyword"
          placeholder="关键字搜索"
          clearable
        ></el-input>
        <el-button type="primary" @click="getReportList">查询</el-button>
        <el-button v-if="addOpt" type="primary" @click="showDialog(1)"
          >上传报表</el-button
        >
      </div>
    </div>
    <div class="tableManageBtm">
      <normalTable
        :tableData="tableData"
        :titleList="titleList"
        :opList="opList"
        :opWidth="'200'"
        :pageNum="pageNum"
        @tableClick="tableClick"
      ></normalTable>
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
    <!-- 上传报表 -->
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
            style="width: 100%"
          >
            <el-option
              v-for="item in projectAddList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="报表名称：" prop="name">
          <el-input
            v-model="reportForm.name"
            placeholder="请输入报表名称"
            maxlength="25"
            clearable
            show-word-limit
          ></el-input>
        </el-form-item>
        <el-form-item label="报表类型：" prop="specific">
          <el-select
            v-model="reportForm.specific"
            placeholder="请选择报表类型"
            style="width: 100%"
          >
            <el-option
              v-for="item in typeList"
              :key="item.id"
              :label="item.label"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="上传报告：" required>
          <el-upload
            ref="uploadFile"
            class="reportUpload"
            :action="actionUrl"
            :headers="headers"
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
  getReportList,
  addReport,
  updReport,
  delReport,
  downLoadReport,
  getProjectListByPowerId,
  getProjectListByOnTime
} from '@/api/common';
import normalTable from '@/components/table/normalTable';
export default {
  name: 'tableManage',
  components: { normalTable },
  props: {},
  data() {
    return {
      selParams: {
        projectId: '',
        name: '',
        specific: '',
        pageSize: 10,
        pageNum: 1
      },
      projectList: [],
      projectAddList: [],
      projectId: '',
      keyword: '',
      activeIndex: 1,
      pageNum: 1,
      total: 10,
      titleList: [
        { id: 1, prop: 'projectName', label: '项目名称' },
        { id: 2, prop: 'name', label: '报表名称', width: '' },
        { id: 3, prop: 'specificName', label: '报表类型', width: '' },
        { id: 4, prop: 'username', label: '上传人员', width: '' },
        { id: 5, prop: 'modifyTime', label: '上传时间', width: '' }
      ],
      tableData: [],
      opList: [
        { id: 1, name: '修改', opShow: false, type: 'updateOpt' },
        {
          id: 2,
          name: '删除',
          opShow: false,
          type: 'deleteOpt'
        },
        {
          id: 3,
          name: '下载',
          opShow: false,
          type: 'checkOpt'
        }
      ],
      //上传报表
      reportDialogTitle: '',
      showUploadReport: false,
      typeList: [
        { id: 1, label: '日报' },
        { id: 2, label: '月报' },
        { id: 3, label: '年报' }
      ],
      reportRules: {
        name: [{ required: true, message: '请输入报表名称', trigger: 'blur' }],
        projectId: [
          { required: true, message: '请选择项目', trigger: 'change' }
        ],
        specific: [
          { required: true, message: '请请选择报表类型', trigger: 'change' }
        ]
      },
      reportForm: {
        projectId: '',
        name: '',
        specific: ''
      },
      //上传文件数据
      actionUrl: '',
      headers: {
        'X-ROUTER-TOKEN': this.$store.getters.getToken,
        'X-ROUTER-URL': '/online/tableManage'
      },
      fileList: [],
      fileData: ''
    };
  },
  beforeMount() {
    this.$utils.getAuthPage(this); //获取权限
  },
  mounted() {
    this.getProjectListByPowerId(); //获取项目列表
    this.getProjectListByOnTime(); //获取添加项目列表
    this.getReportList(); //获取报告列表
  },
  methods: {
    //获取项目列表
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
    //获取报告列表
    async getReportList() {
      let params = {
        projectId: this.projectId,
        name: this.keyword,
        specific: this.activeIndex,
        pageSize: 10,
        pageNum: 1,
        type: 1
      };
      let { code, data } = await getReportList(params);
      if (code == '0000') {
        data.list.map((item) => {
          if (item.specific == 1) {
            item.specificName = '每日简报';
          } else if (item.specific == 2) {
            item.specificName = '月度简报';
          } else {
            item.specificName = '年度分析报告';
          }
        });
        this.pageNum = 1;
        this.tableData = data.list;
        this.total = data.total;
        this.selParams = params;
      }
    },
    async getReportList2() {
      let params = { ...this.selParams };
      params.specific = this.activeIndex;
      let { code, data } = await getReportList(params);
      if (code == '0000') {
        data.list.map((item) => {
          if (item.specific == 1) {
            item.specificName = '每日简报';
          } else if (item.specific == 2) {
            item.specificName = '月度简报';
          } else {
            item.specificName = '年度分析报告';
          }
        });
        this.tableData = data.list;
        this.total = data.total;
      }
    },
    //上传报表
    async addReport() {
      let loading = this.$loading({
        lock: true,
        text: '上传报告中，请稍等...',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      });
      this.fileData = new FormData();
      this.$refs.uploadFile.submit();
      this.fileData.append('params', JSON.stringify(this.reportForm));
      try {
        let { code } = await addReport(this.fileData, 1);
        if (code == '0000') {
          loading.close();
          this.$message({
            type: 'success',
            message: '上传成功!',
            showClose: true,
            duration: 2000
          });
          await this.reportDialigClose();
          await this.getReportList2();
        }
      } catch (error) {
        loading.close();
      }
    },
    //修改报表
    async updReport() {
      let loading = this.$loading({
        lock: true,
        text: '上传报告中，请稍等...',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      });
      this.fileData = new FormData();
      this.$refs.uploadFile.submit();
      this.fileData.append('params', JSON.stringify(this.reportForm));
      let { code } = await updReport(this.fileData, this.reportForm.id);
      if (code == '0000') {
        loading.close();
        this.$message({
          type: 'success',
          message: '修改成功!',
          showClose: true,
          duration: 2000
        });
        await this.reportDialigClose();
        await this.getReportList2();
      }
    },
    //删除报表
    delReport(data) {
      this.$confirm(`确定永久删除：${data.name}?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        showClose: true
      })
        .then(async () => {
          let { code } = await delReport(data.id);
          if (code == '0000') {
            this.$message({
              type: 'success',
              message: '删除成功!',
              showClose: true,
              duration: 2000
            });
            await this.getReportList2();
          }
        })
        .catch(() => {});
    },
    //下载报表
    downLoadReport(data) {
      this.$confirm(`确定下载：${data.name}?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        showClose: true
      })
        .then(() => {
          downLoadReport(data.id)
            .then((res) => {
              let fileName = data.name + '.' + data.path.split('.')[1];
              this.$utils.downloadBlob(res, fileName);
            })
            .catch();
        })
        .catch(() => {});
    },
    tableClick(index, data) {
      if (index == 1) {
        this.reportForm = JSON.parse(JSON.stringify(data));
        this.fileList.push({ name: data.name });
        this.showDialog(2, data.id);
      } else if (index == 2) {
        this.delReport(data);
      } else if (index == 3) {
        this.downLoadReport(data);
      }
    },
    typeChange(index) {
      this.activeIndex = index;
      this.getReportList2();
    },
    //打开新增、修改弹框
    showDialog(index, id) {
      if (index == 1) {
        this.reportDialogTitle = '上传报表';
        this.actionUrl = '/bridge/report/1';
      } else {
        this.reportDialogTitle = '修改报表';
        this.actionUrl = `/bridge/report/${id}`;
      }
      this.showUploadReport = true;
    },
    //关闭上传报表弹框
    reportDialigClose() {
      this.showUploadReport = false;
      this.reportForm = {
        projectId: '',
        name: '',
        specific: ''
      };
      this.fileList = [];
      this.$nextTick(() => {
        this.$refs.reportForm.clearValidate();
      });
    },
    //改变文件
    handleChange(file, fileList) {
      let arry = file.name.split('.');
      let name = arry[arry.length - 1];
      let typeList = ['doc', 'docx', 'pdf'];
      if (!typeList.includes(name)) {
        this.$message.warning('请上传正确的格式（doc、docx、pdf）');
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
    //上传报表
    reportSubmit(formName) {
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          if (this.fileList.length == 0) {
            this.$message({
              type: 'warning',
              message: '请上传报告文件!',
              showClose: true,
              duration: 2000
            });
            return;
          }
          if (this.reportDialogTitle == '上传报表') {
            this.addReport();
          } else {
            this.updReport();
          }
        } else {
          return false;
        }
      });
    },
    handleCurrentChange(val) {
      this.pageNum = val;
      this.selParams.pageNum = val;
      this.getReportList2();
    }
  }
};
</script>
<style lang="scss" scoped>
.tableManage {
  padding: 24px;
  display: flex;
  flex-direction: column;
  .title {
    font-size: 16px;
    color: #262626;
  }
  .tableManageTop {
    // padding: 12px 0;
    padding-bottom: 20px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    .topLeft {
      display: flex;
      div {
        width: 88px;
        height: 32px;
        font-size: 14px;
        color: #000;
        display: flex;
        border: 1px solid #d9d9d9;
        align-items: center;
        justify-content: center;
        cursor: pointer;
        &:nth-child(2) {
          border-left: 0;
          border-right: 0;
        }
        &:last-child {
          width: 116px;
        }
      }
      .activeClass {
        color: #2f80ed;
        border: 1px solid #2f80ed !important;
      }
    }
    .topRight {
      display: flex;
      align-items: center;
      .select {
        width: 282px;
        height: 40px;
        margin-right: 20px;
      }
      // /deep/ .el-select {
      //   height: 40px;
      // }
      // /deep/ .el-button {
      //   margin-left: 20px;
      // }
    }
  }
  .tableManageBtm {
    .pageNation {
      padding: 1vw 0;
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
  }
}
//上传报表弹框
.reportDialog {
  .reportBox {
    padding-right: 50px;
    .reportUpload {
      display: flex;
      /deep/ .el-upload-list__item-name {
        max-width: 325px;
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
}
</style>
