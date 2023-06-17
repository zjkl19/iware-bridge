<template>
  <div class="models">
    <div class="models-btn">
      <!-- <el-upload
        class="upload-demo"
        action="/bridge/Upload/modelUpload"
        multiple
        :limit="1"
        :before-upload="beforeAvatarUpload"
      >
        <el-button type="primary">添加附件</el-button>
      </el-upload> -->
      <el-button
        v-if="addOpt && tableData.length == 0 && roleId > 1"
        type="primary"
        class="search-button-Fj"
        @click="showAddAnnexModel(1)"
        >添加附件</el-button
      >
      <div class="title">{{ structureName }}</div>
    </div>
    <!-- 表格 -->
    <div class="table">
      <el-row>
        <el-col :span="24">
          <el-table :data="tableData" :row-class-name="tableRowClassName">
            <el-table-column
              type="index"
              label="序号"
              width="100"
              align="center"
            >
              <template slot-scope="scope">
                {{ (page - 1) * 10 + scope.$index + 1 }}
              </template>
            </el-table-column>
            <el-table-column prop="targetId" label="桥梁ID" align="center">
            </el-table-column>
            <el-table-column prop="name" label="文件名" align="center">
            </el-table-column>
            <el-table-column
              prop="remarks"
              label="备注"
              align="center"
              show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
              prop="operation"
              label="操作"
              align="center"
              width="220"
            >
              <template slot-scope="scope">
                <div class="optBox">
                  <div
                    v-if="updateOpt"
                    class="optItem"
                    @click="handleUpdateClick(scope.row)"
                  >
                    修改
                  </div>
                  <div
                    v-if="deleteOpt"
                    class="optItem"
                    @click="handelClose(scope.row)"
                  >
                    删除
                  </div>
                  <div class="optItem" @click="downloadZip(scope.row)">
                    文件下载
                  </div>
                </div>
              </template>
            </el-table-column>
          </el-table>
        </el-col>
      </el-row>
    </div>
    <!-- 分页 -->
    <div class="page">
      <el-pagination
        class="pageNation"
        background
        layout=" total, prev, pager, next"
        :total="dataTotal"
        @current-change="handleCurrentChange"
        :page-size="10"
        :current-page="page"
      >
      </el-pagination>
    </div>

    <!-- 添加附件弹框 -->
    <el-row>
      <el-col :span="24">
        <el-dialog
          class="photo"
          :title="title"
          :visible.sync="starAnnexModal"
          width="30%"
          :modal-append-to-body="false"
          :close-on-click-modal="false"
        >
          <div class="fileHeader">
            <el-upload
              class="upload-demo"
              ref="upload"
              :action="actionUrl"
              :headers="headers"
              :auto-upload="false"
              :http-request="httpRequest"
              :on-change="fileStatusChange"
              :on-remove="handleRemove"
              :multiple="false"
              accept=".zip"
              :limit="1"
              :file-list="fileList"
            >
              <el-button slot="trigger" size="small" type="primary"
                >选取文件</el-button
              >
              <div slot="tip" class="el-upload__tip" style="margin: 10px 10px">
                上传zip压缩包，obj和mtl的名称需和压缩包名称相同
              </div>
            </el-upload>
            <el-radio-group
              v-model="fileData.terrain"
              style="margin: 10px 10px"
            >
              <el-radio :label="1">默认地形</el-radio>
              <el-radio :label="2">无地形</el-radio>
            </el-radio-group>
            <div class="fileDown">
              <span>备注：</span>
              <el-input
                class="el-textarea__inner"
                type="textarea"
                v-model="fileData.remarks"
                :maxlength="200"
                placeholder="200字以内"
              ></el-input>
            </div>
          </div>
          <div class="btn">
            <el-button
              size="small"
              type="primary"
              @click="submitAnnexUpload"
              class="submitUploadCl"
              >确定</el-button
            >
            <el-button
              size="small"
              @click="starAnnexModal = false"
              class="cannelUploadCl"
              >取消</el-button
            >
          </div>
        </el-dialog>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { listByPage, deletePhoto, downloadFile } from '@/api/infomanage/photo';
import { uploadModel, updateModel } from '@/api/infomanage/models';
export default {
  inject: ['params'],
  data() {
    return {
      addOpt: this.params().addOpt,
      updateOpt: this.params().updateOpt,
      deleteOpt: this.params().deleteOpt,
      structureId: this.params().structureId,
      structureName: this.params().structureName,
      roleId: this.$store.getters.getRoleInfo.id,

      title: '',
      headers: {
        'X-AUTH-TOKEN': this.$store.getters.getToken,
        'X-ROUTER-URL': '/infoManage/bridgeManage'
      },
      actionUrl: '',

      //附件信息
      starAnnexModal: false, //添加附件模态框照片
      //附件数据
      fileData: {
        id: null,
        targetId: this.structureId,
        type: 3,
        remarks: '',
        terrain: 1
      },

      fileList: [],
      //表格数据
      tableData: [],
      //记录总数
      dataTotal: 0,
      //当前页
      page: 1,

      //一页的条数
      size: 5
    };
  },

  watch: {
    starAnnexModal(value) {
      if (value === false) {
        this.fileList = [];
        this.fileData.remarks = '';
      }
    }
  },

  mounted() {
    this.getList();
  },
  methods: {
    //获取列表
    async getList() {
      let pageInfo = {
        pageNum: this.page,
        pageSize: 10
      };
      if (this.structureId != null) {
        let { code, data } = await listByPage(pageInfo, this.structureId, 3);
        if (code == '0000') {
          this.tableData = data.list;
          this.dataTotal = data.total;
        }
      }
    },
    //模型修改
    handleUpdateClick(row) {
      this.fileData = {
        id: row.id,
        type: row.type,
        targetId: row.targetId,
        remarks: row.remarks,
        terrain: row.terrain
      };
      this.showAddAnnexModel(2);
    },

    //弹出添加附件的弹框 1:添加 2：修改
    showAddAnnexModel(type) {
      if (type == 1) {
        this.title = '上传附件';
        this.actionUrl = '/bridge/photo/model/upload/3';
        this.fileData.targetId = this.structureId;
      } else {
        this.title = '修改模型';
        this.actionUrl = '/bridge/photo/model/update/' + this.fileData.id;
      }
      this.starAnnexModal = true;
    },

    //关闭弹窗
    closeDialog() {
      this.starAnnexModal = false;
      this.fileList = [];
      this.fileData.id = null;
      this.fileData.targetId = null;
      this.fileData.type = 3;
      this.fileData.remarks = '';
      this.fileData.terrain = 1;
    },
    //附件提交
    async submitAnnexUpload() {
      if (this.fileList.length == 0) {
        this.$message({
          message: '请选择文件！',
          showClose: true,
          type: 'warning'
        });
        return;
      }
      this.formData = new FormData();
      this.$refs.upload.submit();
      this.formData.append('attach', JSON.stringify(this.fileData));
      if (this.title == '上传附件') {
        let { code } = await uploadModel(this.formData, 3);
        if (code == '0000') {
          this.$message({
            message: '上传成功',
            showClose: true,
            type: 'success'
          });
          this.closeDialog();
          this.getList();
        }
      } else {
        let { code } = await updateModel(this.formData, this.fileData.id);
        if (code == '0000') {
          this.$message({
            message: '修改成功',
            showClose: true,
            type: 'success'
          });
          this.closeDialog();
          this.getList();
        }
      }
    },
    httpRequest(file) {
      this.formData.append('file', file.file); // append增加数据
    },
    //文件选中(包含校验)
    fileStatusChange(file, fileList) {
      let isZIP = file.name.split('.')[1] === 'zip';
      let isLt30M = file.size / 1024 / 1024 < 30;
      if (!isZIP) {
        this.$message({
          showClose: true,
          message: '上传文件格式必须是ZIP格式',
          type: 'error'
        });
        this.$refs.upload.clearFiles();
        return false;
      }
      // if (!isLt30M) {
      //   this.$message({
      //     showClose: true,
      //     message: '上传模型大小不能超过 30MB!',
      //     type: 'error'
      //   });
      //   this.$refs.upload.clearFiles();
      //   return false;
      // }

      if (file.status == 'ready') {
        this.fileList = fileList;
      }
    },
    handleRemove(file, fileList) {
      this.fileList = [];
    },

    // 删除文件
    handelClose(obj) {
      let _this = this;
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          deletePhoto(obj.id).then((resp) => {
            if (resp.code == '0000') {
              _this.$message({
                type: 'success',
                message: '删除成功!'
              });
              _this.getList();
            }
          });
        })
        .catch(() => {});
    },

    //文件下载
    downloadZip(row) {
      downloadFile(row.id)
        .then((res) => {
          this.$utils.downloadBlob(res, row.name);
        })
        .catch((err) => console.log(err));
    },

    tableRowClassName({ row, rowIndex }) {
      if (rowIndex % 2 == 1) {
        return 'first-row';
      } else {
        return 'second-row';
      }
    },

    //分页器事件
    handleCurrentChange(val) {
      //重置当前页
      this.page = val;
      //更新展示
      this.getList();
    }
  }
};
</script>
<style lang="scss" scoped>
.models {
  height: 55vh;
  font-size: 1vw;
  .title {
    position: absolute;
    width: 100%;
    height: 100%;
    font-size: 16px;
    font-weight: bold;
    display: flex;
    align-items: center;
    justify-content: center;
  }

  .table {
    width: 100%;
    .optBox {
      width: 100%;
      display: flex;
      align-items: center;
      justify-content: space-evenly;
      .optItem {
        font-size: 14px;
        color: #419aff;
        cursor: pointer;
      }
    }
    /deep/ .el-table th {
      color: #333;
      padding: 0.88vh 0;
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
  }
  &-btn {
    position: relative;
    display: flex;
    padding-bottom: 20px;
  }
  /deep/ .el-button {
    z-index: 10;
  }
}
.photo {
  .fileHeader {
    padding: 0 20px;
    .fileDown {
      display: flex;
      /deep/ .el-textarea {
        flex: 1;
        border: none;
        margin-bottom: 10px;
      }
    }
  }
  .btn {
    width: 100%;
    padding-top: 10px;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  /deep/ .el-dialog__body {
    padding: 12px 24px;
  }
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

//斑马纹样式
/deep/ .first-row {
  background: #ffffff;
}
/deep/ .second-row {
  background: #f2f4f6;
}
</style>
