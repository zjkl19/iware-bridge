<template>
  <div class="photo">
    <div class="photoTop">
      <el-button
        v-if="addOpt && roleId > 1 && tableData.length < 6"
        type="primary"
        @click="showPhotoDialog = true"
        >添加图片</el-button
      >
      <!-- 桥梁名称 -->
      <div class="title">{{ structureName }}</div>
    </div>
    <div class="photoBtm">
      <el-table
        class="photoTable"
        :data="tableData"
        :row-class-name="tableRowClassName"
      >
        <el-table-column type="index" label="序号" width="100" align="center">
          <template slot-scope="scope">
            {{ (pageInfo.page - 1) * 10 + scope.$index + 1 }}
          </template>
        </el-table-column>
        <el-table-column label="图片" align="center" width="200">
          <template slot-scope="scope">
            <div class="photoItem" @click="lookPhoto(scope.row)">
              <img :src="scope.row.path" alt="" />
            </div>
          </template>
        </el-table-column>
        <el-table-column
          prop="remarks"
          label="备注"
          align="center"
          show-overflow-tooltip
        >
        </el-table-column>
        <el-table-column label="操作" align="center" width="200">
          <template slot-scope="scope">
            <div class="opItem" @click="deltPhoto(scope.row.id)">删除</div>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        class="pageNation"
        @current-change="handleCurrentChange"
        :current-page.sync="pageInfo.page"
        :page-size="10"
        layout="total, prev, pager, next"
        :total="pageInfo.total"
      ></el-pagination>
    </div>
    <!-- 点击查看大图 -->
    <el-dialog
      class="photoDialog seePic"
      title="查看图片"
      :visible.sync="showLookDialog"
      :modal-append-to-body="false"
      :close-on-click-modal="false"
    >
      <img :src="lookUrl" alt="" />
    </el-dialog>
    <!-- 添加图片弹框 -->
    <el-dialog
      class="photoDialog"
      title="添加图片"
      :visible.sync="showPhotoDialog"
      :modal-append-to-body="false"
      width="35vw"
      @closed="cannelUpload"
      :close-on-click-modal="false"
    >
      <div class="photo-Dev">
        <!-- 图片展示 -->
        <div class="photo-up flex flex-start">
          <p>图片上传:</p>
          <!-- 图片上传显示 -->
          <el-upload
            class="photoUpload"
            :action="actionUrl"
            list-type="picture-card"
            :file-list="fileList"
            accept="image/png,image/jpg,image/jpeg"
            :headers="headers"
            :auto-upload="false"
            :on-change="fileStatusChange"
            :on-exceed="onExceed"
            :on-success="handleUploadSuccess"
            :http-request="httpRequest"
            :multiple="true"
            :limit="allowUploadNum"
            ref="upload"
          >
            <i slot="default" class="el-icon-plus"></i>
            <div class="picItem" slot="file" slot-scope="{ file }">
              <img :src="file.url" alt="" />
              <span class="el-upload-list__item-actions">
                <span
                  class="el-upload-list__item-preview"
                  @click="handlePictureCardPreview(file, fileList)"
                >
                  <i class="el-icon-zoom-in"></i>
                </span>

                <span
                  class="el-upload-list__item-delete"
                  @click="handleRemove(file)"
                >
                  <i class="el-icon-delete"></i>
                </span>
              </span>
            </div>
          </el-upload>
          <el-dialog
            :visible.sync="dialogVisible"
            append-to-body
            :close-on-click-modal="false"
          >
            <img width="100%" :src="dialogImageUrl" alt="" />
          </el-dialog>
        </div>
        <!-- 备注 -->
        <div class="photo-remarks flex flex-start">
          <p>备注:</p>
          <div class="remarkBox">
            <el-input
              type="textarea"
              placeholder="请输入内容"
              v-model="model.remarks"
              maxlength="250"
              show-word-limit
              size="mini"
              resize="none"
            >
            </el-input>
          </div>
        </div>
        <!-- 按钮 -->
        <div class="photo-btn">
          <el-button type="primary" @click="uploadFileList">保存</el-button>
          <el-button @click="cannelUpload()">取消</el-button>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listByPage, deletePhoto, uploadFiles } from '@/api/infomanage/photo';
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
      pageInfo: {
        total: 0,
        page: 1,
        pageCount: 1
      }, //分页参数
      allowUploadNum: 0, //允许上传图片数
      dialogImageUrl: '',
      showLookDialog: false,
      showPhotoDialog: false,
      dialogVisible: false,
      tableData: [],
      lookUrl: '',
      fileList: [],
      model: {
        type: 1,
        targetId: this.params().structureId,
        remarks: ''
      },
      fileData: '',
      type: 1,
      actionUrl: '',
      headers: {
        'X-ROUTER-URL': '/infoManage/bridgeManage'
      }
    };
  },
  mounted() {
    this.actionUrl = '/bridge/photo/upload/' + this.type;
    this.getList();
  },
  methods: {
    //取消dialog
    cannelUpload() {
      this.model = {
        type: 1,
        targetId: this.params().structureId,
        remarks: ''
      };
      this.showPhotoDialog = false;
      this.fileList = [];
      this.$refs.upload.clearFiles();
    },
    //获取列表
    async getList() {
      let type = 1;
      let pageInfo = {
        pageNum: this.pageInfo.page,
        pageSize: this.pageInfo.limit
      };
      if (this.structureId != null) {
        let { code, data } = await listByPage(pageInfo, this.structureId, type);
        if (code == '0000') {
          data.list.map((item) => {
            item.path = this.$basePath + item.path;
          });
          this.tableData = data.list;
          this.allowUploadNum = 6 - data.list.length;
          this.pageInfo = {
            total: data.total,
            page: data.pageNum,
            pageCount: data.pages,
            limit: data.pageSize
          };
        }
      }
    },
    //上传图片预览
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    //上传图片移除
    handleRemove(file) {
      let fileList = this.$refs.upload.uploadFiles;
      let index = this.removeFile(file, fileList);
      this.fileList.splice(index, 1);
    },

    //文件超出个数
    onExceed() {
      this.$message({
        message: `单个结构物仅允许上传6张图片,当前只能上传${this.allowUploadNum}张！`,
        type: 'error',
        showClose: true
      });
    },

    //文件选中(包含校验)
    fileStatusChange(file, fileList) {
      let isPIC = false;
      let typeList = ['image/jpeg', 'image/jpg', 'image/png'];
      if (typeList.includes(file.raw.type)) isPIC = true;
      // let isLt2M = file.size / 1024 / 1024 < 2;
      if (!isPIC) {
        this.$message({
          showClose: true,
          message: '上传文件格式必须jpg或png',
          type: 'error'
        });
        this.removeFile(file, fileList);
        return false;
      }
      // if (!isLt2M) {
      //   this.$message({
      //     showClose: true,
      //     message: '上传图片大小不能超过 2MB!',
      //     type: 'error'
      //   });
      //   this.removeFile(file, fileList);
      //   return false;
      // }
      if (this.checkIsSame(file)) {
        this.$message({
          showClose: true,
          message: '上传图片名称重复!',
          type: 'error'
        });
        this.removeFile(file, fileList);
        return false;
      }
      if (file.status == 'ready') {
        this.fileList = [];
        fileList.map((item) => this.fileList.push(item));
      }
    },

    //校验上传图片是否重复
    checkIsSame(file) {
      for (let i = 0; i < this.fileList.length; i++) {
        if (file.name == this.fileList[i].name) {
          return true;
        }
      }
      return false;
    },

    //移除文件
    removeFile(file, fileList) {
      for (let i = 0; i < fileList.length; i++) {
        if (file.uid == fileList[i].uid) {
          fileList.splice(i, 1);
          return i;
        }
      }
    },

    httpRequest(file) {
      this.fileData.append('files', file.file); // append增加数据
    },

    //提交上传
    uploadFileList() {
      if (this.fileList.length == 0) {
        this.$message({
          message: '请选择文件！',
          showClose: true,
          type: 'warning'
        });
        return;
      }
      let loading = this.$loading({
        lock: true,
        text: '正在上传中...',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      });
      this.fileData = new FormData();
      this.$refs.upload.submit();
      this.fileData.append('params', JSON.stringify(this.model));
      uploadFiles(this.fileData, 1).then((res) => {
        loading.close();
        this.cannelUpload();
        this.$message({
          message: '上传成功',
          showClose: true,
          type: 'success'
        });
        this.getList();
      });
    },
    //上传成功回调
    handleUploadSuccess(res, file) {
      // console.log(res);
    },

    //放大查看图片
    lookPhoto(data) {
      this.lookUrl = data.path;
      this.showLookDialog = true;
    },
    //删除图片
    deltPhoto(id) {
      this.$confirm('此操作将永久删除照片, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          deletePhoto(id).then((res) => {
            this.$message({
              message: '删除成功',
              type: 'success'
            });
            this.getList();
          });
        })
        .catch((e) => {
          this.$message({
            message: '取消删除',
            type: 'info'
          });
        });
    },
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex % 2 == 1) {
        return 'first-row';
      } else {
        return 'second-row';
      }
    },
    //分页功能
    handleCurrentChange(val) {
      this.pageInfo.page = val;
      this.getList();
    }
  }
};
</script>

<style lang="scss" scoped>
.photo {
  height: 36vw;
  width: 100%;
  display: flex;
  flex-direction: column;
  // justify-content: space-between;
  .photoTop {
    position: relative;
    width: 100%;
    padding-bottom: 20px;
    display: flex;
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
    /deep/ .el-button {
      z-index: 10;
    }
  }
  .photoBtm {
    height: 90%;
    display: flex;
    flex-direction: column;
    .photoTable {
      flex: unset;
      .photoItem {
        position: absolute;
        top: 0;
        height: 100%;
        width: 100%;
        display: flex;
        align-items: center;
        justify-content: center;
        cursor: pointer;
        img {
          height: 3.645vh;
        }
      }
      .opItem {
        color: #1890ff;
        cursor: pointer;
      }
      .el-table--striped .el-table__body tr.el-table__row--striped td {
        background: #fff;
      }
    }
    .pageNation {
      padding: 1vw 0;
      text-align: center;
      /deep/ button {
        background: transparent;
        border: 1px solid #d9d9d9;
        border-radius: 2px;
      }
      /deep/ .el-pager {
        padding: 0 0.41665vw;
      }
      /deep/ .el-pager li {
        background: transparent;
        border: 1px solid #d9d9d9;
        border-radius: 2px;
      }
      /deep/ .el-pager .active {
        color: #1890ff;
        border: 1px solid #1890ff;
      }
    }
    /deep/ .el-table th {
      font-size: 14px;
      color: #333;
      padding: 0.88vh 0;
      font-weight: bold;
    }
    /deep/ .el-table td {
      font-size: 14px;
      color: #333;
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
}

//斑马纹样式
/deep/ .first-row {
  background: #ffffff;
}
/deep/ .second-row {
  background: #f2f4f6;
}

.lookDialog {
}

.photoDialog {
  .title {
    padding: 1.3021vw 0 2.969vw;
    font-weight: 600;
  }
  .photo-up {
    padding: 1vw 0;
    display: flex;
    p {
      width: 78px;
      padding-right: 10px;
      text-align: right;
    }
    .photoUpload {
      width: 515px;
    }
    .picItem {
      height: 100%;
      display: flex;
      align-items: center;
    }
  }
  .photo-remarks {
    padding: 1vw 0;
    display: flex;
    justify-content: flex-end;
    p {
      width: 78px;
      padding-right: 10px;
      text-align: right;
    }
    .remarkBox {
      width: 515px;
    }
  }
  .photo-btn {
    text-align: center;
    padding: 2vw 4.1vw;
  }
  /deep/ .el-dialog__body {
    padding-right: 60px;
  }
}
.seePic {
  img {
    // max-width: 1120px;
    max-height: 58.337vh;
  }
  /deep/ .el-dialog__body {
    padding: 16px 32px;
    display: flex;
    align-items: center;
    justify-content: center;
  }
}

.flex {
  display: flex;
  &-between {
    justify-content: space-between;
  }
  &-align {
    align-items: center;
  }
  &-column {
    flex-direction: column;
  }
  &-start {
    justify-content: start;
  }
}

/deep/ .el-upload-list--picture-card .el-upload-list__item {
  width: 6.25vw;
  height: 5vw;
}
/deep/ .el-upload--picture-card {
  width: 6.25vw;
  height: 5vw;
  line-height: 5.5vw;
}
/deep/ .el-textarea__inner {
  height: 10.418vw !important;
}
</style>
