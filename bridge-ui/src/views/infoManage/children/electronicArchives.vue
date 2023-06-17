<template>
  <div class="models">
    <div class="models-btn">
      <div>
        <el-button
          v-if="addOpt && roleId > 1"
          type="primary"
          class="search-button-Fj"
          @click="openDialog(1)"
          >添加</el-button
        >
      </div>

      <div class="right">
        <el-select
          v-model="type"
          class="select"
          placeholder="文件类型"
          clearable
        >
          <el-option
            v-for="item in typeList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          >
          </el-option>
        </el-select>
        <el-input
          v-model="fileName"
          class="select"
          placeholder="文件名"
          clearable
        ></el-input>
        <el-button class="select" type="primary" @click="getElectronicList"
          >查询</el-button
        >
      </div>
    </div>
    <!-- 表格 -->
    <div class="table">
      <normalTable
        :tableData="tableData"
        :titleList="titleList"
        :opList="opList"
        :pageNum="page"
        @tableClick="tableClick"
      ></normalTable>
    </div>
    <!-- 分页 -->
    <div class="page">
      <el-pagination
        class="pageNation"
        background
        layout="total,prev, pager, next"
        :total="dataTotal"
        @current-change="handleCurrentChange"
        :page-size="10"
        :current-page="page"
      >
      </el-pagination>
    </div>

    <!-- 添加附件弹框 -->
    <el-dialog
      class="electronic"
      :title="title"
      :visible.sync="starAnnexModal"
      :modal-append-to-body="false"
      :before-close="dialogClose"
      :close-on-click-modal="false"
    >
      <el-form
        class="dataForm"
        ref="dataForm"
        :model="dataForm"
        :rules="addRules"
        :disabled="title == '电子档案详情'"
      >
        <el-form-item label="文件编号：" prop="code">
          <el-input
            class="formInput"
            v-model.trim="dataForm.code"
            placeholder="请输入文件编号"
            clearable
            maxlength="30"
            show-word-limit
          ></el-input>
        </el-form-item>
        <el-form-item label="文件名称：" prop="fileName">
          <el-input
            class="formInput"
            v-model.trim="dataForm.fileName"
            placeholder="请输入文件名称"
            clearable
            maxlength="30"
            show-word-limit
          ></el-input>
        </el-form-item>
        <el-form-item label="文件类型：" prop="type">
          <el-select
            v-model="dataForm.type"
            class="formInput"
            placeholder="请选择文件类型"
          >
            <el-option
              v-for="item in typeList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="文件摘要：">
          <el-input
            class="formInput"
            type="textarea"
            v-model.trim="dataForm.summary"
            placeholder="请输入文件摘要"
            maxlength="100"
            autosize
          ></el-input>
        </el-form-item>
        <el-form-item label="文件内容：" required>
          <el-upload
            ref="uploadFile"
            class="dataUpload"
            :action="actionUrl"
            :headers="headers"
            :auto-upload="false"
            :limit="1"
            accept=".doc,.docx,.pdf"
            :on-change="handleChange"
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
      <div v-if="title != '电子档案详情'" class="btn">
        <el-button type="primary" @click="addSubmit('dataForm')"
          >确定</el-button
        >
        <el-button @click="dialogClose">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  getElectronicList,
  addElectronic,
  updElectronic,
  delElectronic,
  downloadElectronic
} from '@/api/infomanage/electronicArchives';
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
      roleId: this.$store.getters.getRoleInfo.id,
      selParams: {
        pageNum: 1,
        pageSize: 10,
        fileName: '',
        structureId: '',
        type: ''
      },
      title: '上传附件',
      fileName: '',
      type: '',
      typeList: [
        { id: 1, name: '设计档案' },
        { id: 2, name: '施工文件' },
        { id: 3, name: '合同文件' },
        { id: 4, name: '基本资料' },
        { id: 5, name: '养护档案' },
        { id: 6, name: '其他' }
      ],

      //表格数据
      titleList: [
        { id: 2, prop: 'code', label: '文件编号', width: '' },
        { id: 3, prop: 'fileName', label: '文件名称', width: '' },
        { id: 4, prop: 'typeName', label: '文件类别', width: '' },
        { id: 5, prop: 'summary', label: '文件摘要', width: '' },
        { id: 6, prop: 'createTime', label: '创建时间', width: '' },
        { id: 7, prop: 'username', label: '创建人员', width: '' }
      ],
      tableData: [], // 传感器设置列表
      opList: [
        { id: 1, name: '详情', opShow: true, type: 'checkOpt' },
        {
          id: 2,
          name: '修改',
          opShow: this.params().updateOpt,
          type: 'updateOpt'
        },
        {
          id: 3,
          name: '删除',
          opShow: this.params().deleteOpt,
          type: 'deleteOpt'
        },
        {
          id: 4,
          name: '下载',
          opShow: true,
          type: 'checkOpt'
        }
      ],
      //记录总数
      dataTotal: 0,
      //当前页
      page: 1,
      //添加记录的模态框值
      dataForm: {
        code: '',
        type: '',
        fileName: '',
        summary: ''
      },
      addRules: {
        code: [{ required: true, message: '请输入文件编号', trigger: 'blur' }],
        fileName: [
          { required: true, message: '请输入文件名称', trigger: 'blur' }
        ],
        type: [{ required: true, message: '请选择文件类型', trigger: 'change' }]
      },
      starAnnexModal: false, //添加附件模态框照片
      actionUrl: '',
      headers: {
        'X-ROUTER-TOKEN': this.$store.getters.getToken,
        'X-ROUTER-URL': '/infoManage/bridgeManage'
      },
      fileList: []
    };
  },
  mounted() {
    this.getElectronicList(); //获取电子档案列表
  },
  methods: {
    //获取电子档案列表
    async getElectronicList() {
      let params = {
        structureId: this.structureId,
        type: this.type,
        fileName: this.fileName,
        pageNum: this.page
      };
      let { code, data } = await getElectronicList(params);
      if (code == '0000') {
        this.tableData = data.list;
        this.dataTotal = data.total;
        this.selParams = params;
      }
    },
    async getElectronicList2() {
      let { code, data } = await getElectronicList(this.selParams);
      if (code == '0000') {
        this.tableData = data.list;
        this.dataTotal = data.total;
      }
    },
    //提交上传
    addSubmit(formName) {
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          if (this.fileList.length == 0) {
            this.$message({
              type: 'warning',
              message: '请上传文件!',
              showClose: true,
              duration: 2000
            });
            return;
          }
          this.fileData = new FormData();
          this.$refs.uploadFile.submit();
          this.dataForm.structureId = this.structureId;
          this.fileData.append('params', JSON.stringify(this.dataForm));
          if (this.title == '添加电子档案') {
            let { code } = await addElectronic(this.fileData);
            if (code == '0000') {
              this.$message({
                type: 'success',
                showClose: true,
                message: '添加成功!',
                duration: 2000
              });
              await this.dialogClose();
              await this.getElectronicList2();
            }
          } else {
            let { code } = await updElectronic(this.fileData, this.dataForm.id);
            if (code == '0000') {
              this.$message({
                type: 'success',
                showClose: true,
                message: '修改成功!',
                duration: 2000
              });
              await this.dialogClose();
              await this.getElectronicList2();
            }
          }
        } else {
          return false;
        }
      });
    },
    // 删除文件
    delElectronic(item) {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async () => {
          let { code } = await delElectronic(item.id);
          if (code == '0000') {
            this.$message({
              type: 'success',
              showClose: true,
              message: '删除成功!',
              duration: 2000
            });
            await this.dialogClose();
            await this.getElectronicList2();
          }
        })
        .catch(() => {});
    },
    // 下载文件
    downloadElectronic(data) {
      this.$confirm(`确定下载：${data.fileName}?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        showClose: true
      })
        .then(() => {
          downloadElectronic(data.id)
            .then((res) => {
              let fileName = data.fileName + '.' + data.path.split('.')[1];
              this.$utils.downloadBlob(res, fileName);
            })
            .catch();
        })
        .catch(() => {});
    },
    //模型修改
    openDialog(index, data) {
      if (index == 1) {
        this.title = '添加电子档案';
        this.actionUrl = '/bridge/electronic';
      } else {
        this.title = '修改电子档案';
        this.actionUrl = `/bridge/electronic/${data.id}`;
      }
      this.starAnnexModal = true;
    },

    tableRowClassName({ row, rowIndex }) {
      if (rowIndex % 2 == 1) {
        return 'first-row';
      } else {
        return 'second-row';
      }
    },
    //表格点击
    tableClick(index, data) {
      if (index == 1) {
        this.title = '电子档案详情';
        let obj = { name: data.fileName + '.' + data.path.split('.')[1] };
        this.fileList.push(obj);
        this.dataForm = { ...this.dataForm, ...data };
        this.starAnnexModal = true;
      } else if (index == 2) {
        let obj = { name: data.fileName + '.' + data.path.split('.')[1] };
        this.fileList.push(obj);
        this.dataForm = { ...data };
        this.openDialog(index, data);
      } else if (index == 3) {
        this.delElectronic(data);
      } else if (index == 4) {
        this.downloadElectronic(data);
      }
    },

    //改变文件
    handleChange(file, fileList) {
      let fileArry = file.name.split('.');
      let name = fileArry[fileArry.length - 1];
      let typeList = ['doc', 'docx', 'pdf'];
      if (!typeList.includes(name)) {
        this.$message.warning('请上传正确的格式（doc、docx、pdf）');
        this.$refs.uploadFile.clearFiles();
        return;
      }
      this.fileList = fileList;
    },
    //移除文件
    handleRemove(file, fileList) {
      this.fileList = fileList;
    },
    //上传文件
    uploadFile(file) {
      this.fileData.append('file', file.file); // append增加数据
    },

    //关闭弹框
    dialogClose() {
      this.starAnnexModal = false;
      this.dataForm = {
        code: '',
        type: '',
        fileName: '',
        summary: ''
      };
      this.fileList = [];
      this.$nextTick(() => {
        this.$refs.dataForm.clearValidate();
      });
    },

    //分页器事件
    handleCurrentChange(val) {
      //重置当前页
      this.page = val;
      this.selParams.pageNum = val;
      //更新展示
      this.getElectronicList2();
    }
  }
};
</script>
<style lang="scss" scoped>
.models {
  font-size: 1vw;
  .title {
    padding-bottom: 1vw;
  }
  .table {
  }
  .models-btn {
    display: flex;
    align-items: center;
    justify-content: space-between;
    .right {
      display: flex;
      align-items: center;
      .select {
        margin-right: 20px;
      }
    }
  }
}

//电子档案
.electronic {
  .dataForm {
    display: flex;
    flex-direction: column;
    .formTime,
    .formInput {
      width: 316px;
    }
    .dataUpload {
      width: 316px;
      display: flex;
      /deep/ .el-upload-list {
        width: 100%;
      }
    }
    /deep/ .el-form-item {
      padding-right: 36px;
      display: flex;
      justify-content: flex-end;
    }
    /deep/ .el-textarea__inner {
      min-height: 50px !important;
    }
  }
  .btn {
    padding-top: 10px;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  /deep/ .el-dialog {
    width: 520px;
  }
  /deep/ .el-dialog__body {
    padding: 20px;
    display: flex;
    flex-direction: column;
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
  padding: 1vw 0;
  display: flex;
  justify-content: center;
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
