<template>
  <div class="briefReport">
    <div class="briefReportBox">
      <div class="briefReportTop">
        <div class="left">
          <span @click="backPage">{{ parentName }}</span
          ><span> / 维修简报</span>
        </div>
        <div class="right" @click="backPage">
          <span><i class="iconfont icon-back"></i></span>
          <span>返回</span>
        </div>
      </div>
      <div class="briefReportBtmBox">
        <div v-if="dataInfo" class="briefReportBtm boxShadow">
          <div class="reportBoxOut">
            <div class="reportBox reportTop">
              <el-button
                v-if="roleId === 0 || roleId === 1"
                type="primary"
                @click="dialogVisible = true"
                >图片上传</el-button
              >
              <el-button type="primary" @click="downLoad">下载</el-button>
            </div>
            <div class="reportBox reportMid">
              <div class="first">桥梁隧道维修养护简报</div>
              <div class="second">
                <div>
                  <div>
                    <span class="txt">管理单位：</span
                    ><span>{{ dataInfo.manageDepartment }}</span>
                  </div>
                  <div>
                    <span class="txt">维修人员：</span
                    ><span>{{ dataInfo.creator }}</span>
                  </div>
                </div>
                <div>
                  <div>
                    <span class="txt">维养单位：</span
                    ><span>{{ dataInfo.maintenanceUnit }}</span>
                  </div>
                  <div>
                    <span class="txt">联系方式：</span
                    ><span>{{ dataInfo.phone || '/' }}</span>
                  </div>
                </div>
              </div>
              <div class="third">
                <div class="thirdItem">
                  <span class="left">项目名称</span
                  ><span class="right">{{ dataInfo.projectName }}</span>
                </div>
                <div class="thirdItem bgColor">
                  <span class="left">结构物名称</span
                  ><span class="right">{{ dataInfo.structureName }}</span>
                </div>
                <div class="thirdItem">
                  <span class="left">维修日期</span
                  ><span class="right">{{ dataInfo.maintainTime }}</span>
                </div>
                <div class="thirdItem bgColor">
                  <span class="left">工程量</span
                  ><span class="right">{{ dataInfo.quantities }}</span>
                </div>
                <div class="thirdItem">
                  <span class="left">维修项</span
                  ><span class="right">{{ dataInfo.name }}</span>
                </div>
                <div class="thirdItem bgColor">
                  <span class="left">维修内容</span
                  ><span class="right">{{ dataInfo.content }}</span>
                </div>
                <div class="thirdItem">
                  <span class="left">病害缺陷</span
                  ><span class="right">{{
                    dataInfo.diseaseName + dataInfo.diseaseRemark
                  }}</span>
                </div>
                <div class="thirdItem bgColor">
                  <span class="left">维修方法</span>
                  <span class="right">{{ dataInfo.method }}</span>
                </div>
                <!-- <div class="thirdItemMeth">
                  <span class="left">维养方法</span>
                  <div class="methList">
                    <span v-for="item in dataInfo.methodList" :key="item.id">{{
                      item.text
                    }}</span>
                  </div>
                </div> -->
                <div class="thirdItem bgColor">
                  <span class="left">维修结果</span
                  ><span class="right">{{ dataInfo.result }}</span>
                </div>
                <!-- <div class="thirdItem">
                  <span class="left">备注</span
                  ><span class="right">{{ dataInfo.remark }}</span>
                </div> -->
              </div>
              <!-- <div class="fourth">
                <div class="fourthItemPer">
                  <span>维养人员：</span>
                  <span
                    v-for="item in dataInfo.personList"
                    :key="item"
                    class="nameTxt"
                    >{{ item }}</span
                  >
                </div>
                <span class="fourthItem">{{ dataInfo.compony }}</span>
                <span class="fourthItem">{{ dataInfo.department }}</span>
                <span class="fourthItem">{{ dataInfo.editDate }}</span>
              </div> -->
            </div>
            <div class="reportBox reportBtm">
              <div class="title">附件：</div>
              <div class="picItem">
                <div class="txt">一、维修前状况</div>
                <div class="picList">
                  <div
                    v-for="item in photoBeforeList"
                    :key="item.id"
                    class="photoItem"
                  >
                    <el-image
                      :src="item.path"
                      alt=""
                      :preview-src-list="pathArr[0]"
                    >
                    </el-image>
                    <!-- <img :src="item.path" alt="" /> -->
                    <!-- <span>{{ item.name }}</span> -->
                  </div>
                </div>
              </div>
              <div class="picItem">
                <div class="txt">二、维修过程</div>
                <div class="picList">
                  <div
                    v-for="item in photoWorkList"
                    :key="item.id"
                    class="photoItem"
                  >
                    <el-image
                      :src="item.path"
                      alt=""
                      :preview-src-list="pathArr[1]"
                    >
                    </el-image>
                    <!-- <img :src="item.path" alt="" /> -->
                    <!-- <span>{{ item.name }}</span> -->
                  </div>
                </div>
              </div>
              <div class="picItem">
                <div class="txt">三、维修后状况</div>
                <div class="picList">
                  <div
                    v-for="item in photoAfterList"
                    :key="item.id"
                    class="photoItem"
                  >
                    <el-image
                      :src="item.path"
                      alt=""
                      :preview-src-list="pathArr[2]"
                    >
                    </el-image>
                    <!-- <img :src="item.path" alt="" /> -->
                    <!-- <span>{{ item.name }}</span> -->
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div v-else class="briefReportEmpty boxShadow">暂无简报数据</div>
      </div>
    </div>
    <el-dialog
      class="uploadDialog"
      title="上传图片"
      :visible.sync="dialogVisible"
      :before-close="handleClose"
      :append-to-body="false"
      :modal-append-to-body="false"
    >
      <div class="radioCheck">
        <span class="label">状况选择：</span>
        <el-radio-group
          class="radioGroup"
          v-model="photoType"
          @change="radioChange"
        >
          <el-radio :label="6">维修前状况</el-radio>
          <el-radio :label="7">维修过程</el-radio>
          <el-radio :label="8">维修后状况</el-radio>
        </el-radio-group>
      </div>
      <div class="photoCheck">
        <span class="label">图片：</span>
        <el-upload
          v-if="limit > 0"
          ref="uploadPic"
          class="photoUl"
          :action="`/maintain/record/upload/${reportId}/${photoType}`"
          :auto-upload="false"
          :limit="limit"
          multiple
          list-type="picture-card"
          accept=".jpg,.jpeg,.png,.gif"
          :on-change="handleChange"
          :on-exceed="handleExceed"
          :http-request="uploadFile"
          :file-list="fileList"
          :class="{
            hideUpload: fileList.length > limit - 1
          }"
        >
          <i slot="default" class="el-icon-plus"></i>
          <div class="upItem" slot="file" slot-scope="{ file }">
            <el-image
              ref="previewImg"
              :src="file.url"
              :preview-src-list="imgList"
            >
            </el-image>
            <span class="el-upload-list__item-actions">
              <span
                class="el-upload-list__item-preview"
                @click="handleLook(file)"
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
        <div v-else class="disUp">已存在6张图片！</div>
      </div>
      <div class="btn">
        <el-button type="primary" @click="uploadPhoto">提 交</el-button>
        <el-button @click="handleClose">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { downLoadRecord, getDetail, uploadPhoto } from '@/api/maintain/record';
import { listByPage } from '@/api/common';
import earlyWarningManagement from '../online/earlyWarningManagement.vue';
export default {
  components: { earlyWarningManagement },
  name: 'maintainBrief',
  props: ['reportId', 'parentName'],
  data() {
    return {
      roleId: this.$store.getters.getRoleInfo.id,
      // parentName: '',
      dataInfo: {},
      photoBeforeList: [],
      photoWorkList: [],
      photoAfterList: [],
      pathArr: [[], [], []],
      dialogVisible: false,
      photoType: 6,
      fileList: [],
      limit: 1,
      fileData: null,
      imgList: []
    };
  },
  beforeMount() {
    this.$utils.getAuthPage(this, 58); //获取权限
  },
  mounted() {
    // this.reportId = this.$route.query.id;
    // this.parentName = this.$route.query.parentName;
    this.getDetail();
  },
  methods: {
    //查询单条细项
    async getDetail() {
      let { code, data } = await getDetail(this.reportId);
      if (code == '0000') {
        if (data.diseaseName == null) {
          data.diseaseName = '';
        }
        if (data.diseaseRemark == null) {
          data.diseaseRemark = '';
        } else {
          data.diseaseRemark = '：' + data.diseaseRemark;
        }
        this.dataInfo = data;
        this.$nextTick(() => {
          this.listByPage(6);
          this.listByPage(7);
          this.listByPage(8);
        });
      }
    },
    //获取照片
    async listByPage(type) {
      let params = {
        pageSize: 10,
        pageNum: 1,
        id: this.reportId,
        type
      };
      let { code, data } = await listByPage(params);
      if (code == '0000') {
        data.list.map((item) => {
          item.path = this.$basePath + item.path;
        });
        if (type == 6) {
          this.photoBeforeList = data.list;
          this.pathArr[0] = data.list.map((item) => {
            return item.path;
          });
          this.limit = 6 - this.pathArr[0].length;
        }
        if (type == 7) {
          this.photoWorkList = data.list;
          this.pathArr[1] = data.list.map((item) => {
            return item.path;
          });
        }
        if (type == 8) {
          this.photoAfterList = data.list;
          this.pathArr[2] = data.list.map((item) => {
            return item.path;
          });
        }
      }
    },
    //上传图片
    async uploadPhoto() {
      if (this.fileList.length == 0) {
        this.$message({
          message: '请先上传图片！',
          type: 'warning',
          showClose: true,
          duration: 2000
        });
        return;
      }
      let loading = this.$loading({
        lock: true,
        text: '正在上传中，请稍等...',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      });
      this.fileData = new FormData();
      this.$refs.uploadPic.submit();
      let params = {
        targetId: this.reportId,
        type: this.photoType
      };
      this.fileData.append('params', JSON.stringify(params));
      try {
        let { code } = await uploadPhoto(this.fileData);
        if (code == '0000') {
          this.$message({
            message: '上传成功！',
            type: 'success',
            showClose: true,
            duration: 2000
          });
          loading.close();
          await this.listByPage(this.photoType);
          await this.handleClose();
        }
      } catch (error) {
        loading.close();
      }
    },
    //下载
    async downLoad() {
      downLoadRecord(this.reportId)
        .then((res) => {
          let fileName = '桥梁隧道维修养护简报.docx';
          this.$utils.downloadBlob(res, fileName);
        })
        .catch();
    },
    //返回
    backPage() {
      // this.$router.go(-1);
      this.$emit('closed');
    },
    radioChange(e) {
      if (e === 6) {
        this.limit = 6 - this.pathArr[0].length;
      } else if (e === 7) {
        this.limit = 6 - this.pathArr[1].length;
      } else {
        this.limit = 6 - this.pathArr[2].length;
      }
      this.photoType = e;
      this.fileList = [];
    },
    handleClose() {
      this.photoType = 6;
      this.fileList = [];
      this.limit = 6 - this.pathArr[0].length;
      this.dialogVisible = false;
    },
    //改变文件
    handleChange(file, fileList) {
      let arry = [];
      fileList.map((item) => {
        arry.push(item.url);
      });
      this.fileList = fileList;
      this.imgList = arry;
    },
    //验证文件
    handleExceed(file, fileList) {
      this.$message.warning(
        `当前限制选择 ${this.limit} 个文件，本次选择了 ${
          file.length
        } 个文件，共选择了 ${file.length + fileList.length} 个文件`
      );
    },
    //查看图片
    handleLook() {
      this.$refs.previewImg.showViewer = true;
    },
    //移除文件
    handleRemove(file) {
      this.fileList.map((item, i) => {
        if (item.uid === file.uid) this.fileList.splice(i, 1);
      });
    },
    //上传文件
    uploadFile(file) {
      this.fileData.append('files', file.file); // append增加数据
    }
  }
};
</script>

<style></style>

<style lang="scss" scoped>
.briefReport {
  position: absolute;
  top: 6.488vh;
  left: 0;
  width: 100%;
  height: 93.513vh;
  // padding-top: 6.488vh;
  background: #f6f7fb;
  z-index: 99;
  .briefReportBox {
    width: 100%;
    // padding: 1.855vh 0;
    display: flex;
    flex-direction: column;
    .briefReportTop {
      height: 5.186vh;
      // margin-bottom: 3.71vh;
      padding: 0 36px 0 28px;
      background: #fff;
      display: flex;
      align-items: center;
      justify-content: space-between;
      .left {
        display: flex;
        align-items: center;
        span {
          font-size: 0.8vw;
          color: #262626;
          white-space: pre-wrap;
          &:first-child {
            font-weight: bold;
            cursor: pointer;
            &:hover {
              color: #419aff;
            }
          }
        }
      }
      .right {
        color: #419aff;
        display: flex;
        align-items: center;
        cursor: pointer;
        span {
          margin-left: 5px;
        }
      }
    }
    .briefReportBtmBox {
      width: 100%;
      height: 88.334vh;
      padding: 1.855vh 28px;
      overflow: hidden;
      .briefReportBtm {
        padding: 32px;
        height: 100%;
        overflow: hidden;
        .reportBoxOut {
          height: 100%;
          display: flex;
          align-items: center;
          flex-direction: column;
          overflow-y: auto;
          .reportBox {
            width: 1280px;
          }
        }
      }
      .briefReportEmpty {
        width: 100%;
        height: 83.513vh;
        color: #333;
        display: flex;
        align-items: center;
        justify-content: center;
      }
    }
  }
}
.briefReportBtm {
  .reportTop {
    display: flex;
    align-items: center;
    justify-content: flex-end;
  }
  .reportMid {
    direction: flex;
    flex-direction: column;
    .first {
      font-size: 24px;
      color: #262626;
      font-weight: bold;
      text-align: center;
      margin-bottom: 32px;
    }
    .second {
      padding-bottom: 10px;
      .txt {
        font-size: 16px;
        color: #7d8696;
      }
      div {
        height: 40px;
        display: flex;
        align-items: center;
      }
    }
    .third {
      border-top: 1px solid #d9d9d9;
      border-left: 1px solid #d9d9d9;
      display: flex;
      flex-direction: column;
      .thirdItem {
        height: 77px;
        width: 100%;
        display: flex;
        .left {
          width: 120px;
          color: #7d8696;
        }
        .right {
          padding: 0 10px;
          color: #333;
          text-indent: 2em;
          justify-content: flex-start;
          flex: 1;
        }
        span {
          height: 100%;
          font-size: 16px;
          border-right: 1px solid #d9d9d9;
          border-bottom: 1px solid #d9d9d9;
          display: flex;
          align-items: center;
          justify-content: center;
        }
      }
      .thirdItemMeth {
        width: 100%;
        display: flex;
        .left {
          width: 120px;
          color: #7d8696;
        }
        .methList {
          padding: 10px;
          flex: 1;
          display: flex;
          flex-direction: column;
          span {
            width: 100%;
            color: #333;
            text-indent: 2em;
            border: 0;
            line-height: 32px;
            justify-content: flex-start;
          }
        }
        div {
          border-right: 1px solid #d9d9d9;
          border-bottom: 1px solid #d9d9d9;
        }
        span {
          height: auto;
          font-size: 16px;
          border-right: 1px solid #d9d9d9;
          border-bottom: 1px solid #d9d9d9;
          display: flex;
          align-items: center;
          justify-content: center;
        }
      }
      .bgColor {
        background: #f4f4f4;
      }
    }
    .fourth {
      padding: 10px 0;
      display: flex;
      flex-direction: column;
      .fourthItemPer {
        padding-bottom: 40px;
        .nameTxt {
          padding-right: 15px;
        }
      }
      .fourthItem {
        width: 100%;
        text-align: end;
      }
      span {
        font-size: 16px;
        color: #333;
        line-height: 32px;
      }
    }
    div {
      width: 100%;
    }
  }
  .reportBtm {
    display: flex;
    flex-direction: column;
    .title {
      font-size: 24px;
      color: #333;
      font-weight: 400;
      padding: 32px 0;
    }
    .picItem {
      font-size: 16px;
      color: #333;
      padding-bottom: 20px;
      display: flex;
      flex-direction: column;
      .txt {
        padding-bottom: 10px;
      }
      .picList {
        width: 100%;
        display: flex;
        flex-wrap: wrap;
        .photoItem {
          width: 16%;
          border: 1px solid #d9d9d9;
          padding: 8px;
          margin-left: 0.3%;
          margin-bottom: 10px;
          display: flex;
          flex-direction: column;
          align-items: center;
          justify-content: center;
          img {
            width: 100%;
            max-height: 332px;
          }
          span {
            padding-top: 8px;
          }
        }
      }
    }
  }
}
.uploadDialog {
  .radioCheck {
    display: flex;
    align-items: center;
    justify-content: flex-end;
    .radioGroup {
      flex: 1;
    }
    .label {
      width: 100px;
      padding-right: 10px;
      text-align: right;
    }
  }
  .photoCheck {
    margin: 20px 0;
    display: flex;
    justify-content: flex-end;
    .label {
      width: 100px;
      padding-right: 10px;
      text-align: right;
    }
    .photoUl {
      flex: 1;
      display: flex;
      align-items: center;
    }
    .disUp {
      flex: 1;
      color: #000;
      font-weight: bold;
    }
  }
  .btn {
    display: flex;
    align-items: center;
    justify-content: center;
  }
  .photoUl {
    .upItem {
      height: 100%;
      width: 100%;
      display: flex;
      align-items: center;
      justify-content: center;
    }
    img {
      max-height: 60px;
    }
    /deep/ .el-upload-list {
      display: flex;
      align-items: center;
    }
    /deep/ .el-upload--picture-card {
      width: 60px;
      height: 60px;
      display: flex;
      align-items: center;
      justify-content: center;
    }
    /deep/ .el-upload-list__item {
      width: 60px;
      height: 60px;
      margin: 0;
      margin-right: 10px;
      background: transparent;
      display: flex;
      align-items: center;
      justify-content: center;
    }
    /deep/ .el-upload-list__item-actions {
      display: flex;
      align-items: center;
      justify-content: space-evenly;
      span {
        margin: 0;
      }
    }
  }
  .hideUpload {
    /deep/ .el-upload--picture-card {
      display: none;
    }
  }
  /deep/ .el-dialog {
    width: 580px;
  }
  /deep/ .el-dialog__body {
    padding: 20px;
    display: flex;
    flex-direction: column;
  }
}
</style>
