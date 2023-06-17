<template>
  <div class="survey">
    <!-- 概况树形图 -->
    <div class="survey-left">
      <div class="title">
        <span v-if="structureManage.structureName">{{
          structureManage.structureName
        }}</span>
      </div>
      <div class="tree">
        <el-tree
          :data="surveyList"
          :props="defaultProps"
          @node-click="handleNodeClick"
          :default-expand-all="true"
        ></el-tree>
      </div>
    </div>
    <!-- 概况显示图片 -->
    <div class="survey-right">
      <div class="surveyMsg">
        <span class="title">结构信息</span>
        <div class="msgBox">
          <div v-for="item in surveyMsgList" :key="item.id" class="msgItem">
            <span class="txt">{{ item.name }}</span>
            <el-input class="valInput" v-model="item.value" disabled>
              <template v-if="item.unit" slot="append">{{
                item.unit
              }}</template>
            </el-input>
          </div>
        </div>
      </div>
      <div class="surveyPhoto">
        <div class="surveyBtn">
          <span class="title">结构照片：</span>
          <el-button class="down" type="primary" @click="downLoad"
            >下载</el-button
          >
          <el-upload
            v-if="!isUpload"
            action="/bridge/brigeResource/uploadPicture"
            :before-upload="beforeAvatarUpload"
            :http-request="onRequest"
            :on-change="handleChange"
            :show-file-list="false"
            auto-upload
            multiple
            :limit="6"
            :file-list="fileList"
            ref="upload"
          >
            <el-button type="primary">上传</el-button>
          </el-upload>
        </div>
        <div class="survey-imgbox">
          <div v-for="(item, index) in imgArr" :key="item.id" class="imgItem">
            <el-image
              :src="item.path"
              alt=""
              class="elImg"
              :preview-src-list="pathArr"
            >
            </el-image>
            <div class="flex flex-between flex-align">
              <div class="flex">
                <el-checkbox
                  v-model="checkList[index]"
                  @change="changeValue(index)"
                  >{{ item.name }}</el-checkbox
                >
              </div>
              <div class="icon flex" v-if="item.isCanDel == 1">
                <i class="el-icon-delete" @click="clickImgRemove(item)"></i>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import {
  getDiseaseDetail, //获取图片列表
  getUpLoad, //上传图片
  deleteImg, //删除图片
  downLoadPhoto, //下载结构图片
  selectDefaultPicture, //选择默认图片
  getBridgeSurveyDetail //获取树形结构
} from '@/api/assessment/survey';
import { isOriginalRecordByStructure } from '@/api/assessment/disease';
export default {
  props: {
    structureManage: {
      type: Object,
      default: () => {
        return { id: '', structureName: '' };
      }
    }
  },
  inject: ['authOpt'],
  data() {
    return {
      title: '解放大桥',
      surveyList: [],
      surveyMsgList: [],
      defaultProps: {
        //树形结构设置
        label: 'name',
        children: 'children'
      },
      imgArr: [], //图片数组
      radio: -1,
      bid: 0, //桥梁ID
      mp_srel_id: 0,
      List: {},
      partType: '', //
      type: 1, //
      formData: [], //表单列表
      file: {}, //文件对象
      fileList: [], //文件列表
      imgId: '', //图片ID
      pathArr: [], //大图显示
      isUpload: false, //上传按钮显示
      checkList: [],
      checked1: []
    };
  },
  watch: {
    structureManage() {
      if (this.structureManage.id) {
        this.getTreeList();
        this.isOriginalRecordByStructure();
      }
    },
    imgArr: {
      handler(newName, oldName) {
        let list = [];
        this.checkList = [];
        newName.forEach((el, index) => {
          if (el.status == 2) {
            list.push(index);
            this.checkList.push(true);
          } else if (el.status == 1) {
            this.checkList.push(false);
          }
        });
        this.checked1 = list;
        5;
      },
      deep: true,
      immediate: true
    }
  },
  mounted() {
    if (this.structureManage.id) {
      this.getTreeList();
      this.isOriginalRecordByStructure();
    }
  },
  methods: {
    // 获取树形结构
    async getTreeList() {
      let params = {
        bid: null,
        mpSrelId: this.structureManage.id
      };
      let { code, data } = await getBridgeSurveyDetail(params);
      if (code == '0000') {
        this.surveyList = data;
        if (data.length != 0) {
          this.imgId = data[0].id;
          this.partType = 1;
          await this.getDiseaseDetail();
        } else {
          this.imgArr = [];
        }
      }
    },
    // 获取图片
    async getDiseaseDetail() {
      // return;
      this.pathArr = [];
      let params = {
        id: this.imgId,
        type: this.partType,
        moniPlanStructId: this.structureManage.id
      };
      let { code, data } = await getDiseaseDetail(params);
      if (code == '0000') {
        data.list.map((item) => {
          item.value = item.value == 'null' ? '' : item.value;
        });
        this.surveyMsgList = data.list;
        this.imgArr = data.photo;
        data.photo.map((item) => {
          this.pathArr.push(item.path);
        });
      }
    },
    // 更新原始记录图片
    async changeValue(index) {
      let _this = this;
      let model = {
        id: this.imgArr[index].id,
        status: this.checkList[index] ? 2 : 1
      };
      let { code, msg } = await selectDefaultPicture(model);
      if (code != '0000') {
        _this.$message({
          type: 'error',
          message: msg
        });
      }
    },
    // 获取原始记录是否生成
    async isOriginalRecordByStructure() {
      let { code, data } = await isOriginalRecordByStructure(
        this.structureManage.id
      );
      if (code == '0000') {
        this.isUpload = data;
      }
    },
    // 获取图片列表
    async handleNodeClick(obj) {
      this.partType = obj.type;
      this.imgId = obj.id;
      await this.getDiseaseDetail();
    },
    // 查看文件
    handleChange(file, fileList) {
      this.file = file;
      this.fileList = fileList;
    },
    // 上传图片
    async onRequest() {
      let _this = this;
      let uploadObj = await getUpLoad(this.formData);
      if (uploadObj.code == '0000') {
        _this.$message({
          type: 'success',
          message: '上传成功!'
        });
        await this.getDiseaseDetail();
      } else {
        this.$message.error(uploadObj.msg);
      }
    },
    // 删除图片
    clickImgRemove(value) {
      this.$confirm('是否确认永久删除该图片?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async () => {
          let { code } = await deleteImg(value.id);
          if (code == '0000') {
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
            await this.getDiseaseDetail();
          }
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
    },
    // 图片格式 添加图片参数
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg' || file.type === 'image/png';
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isJPG) {
        this.$message.error('上传图片只能是 JPG / PNG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传图片大小不能超过 2MB!');
      }
      this.formData = new FormData();
      this.formData.append('file', file);
      this.formData.append('partType', this.partType);
      this.formData.append('type', this.type);
      this.formData.append('targetId', this.imgId);
      return isJPG && isLt2M;
    },
    // 下载结构图片
    async downLoad() {
      let _this = this;
      let params = {
        status: [1, 2],
        targetId: this.imgId,
        type: this.type,
        partType: this.partType
      };
      if (_this.imgArr.length == 0) {
        this.$message({
          type: 'info',
          message: '下载图片为空'
        });
        return;
      }
      let { code, data } = await downLoadPhoto(params);
      if (code == '0000') {
        let href = 'http://' + window.location.host + data;
        this.$confirm('是否下载该结构图片?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            window.open(href, '_blank');
          })
          .catch(() => {});
      } else {
        this.$message.error(msg);
      }
    }
  }
};
</script>
<style lang="scss" scoped>
.survey {
  height: 100%;
  display: flex;
  &-left {
    width: 304px;
    border-right: 1px solid rgb(229, 229, 229);
    padding: 1.852vh 20px 0 0;
    .title {
      padding: 0 0 0.5vw;
    }
    .tree {
      height: 70.371vh;
      overflow-y: auto;
      /deep/ .el-tree {
        height: 100%;
      }
      /deep/ .el-tree-node__children .is-current > .el-tree-node__content {
        color: #fff;
        background: #419aff;
        border-radius: 4px;
        &:hover {
          background: #419aff;
        }
      }
    }
  }
  &-imgbox {
    // height: 49.26vh;
    // flex: 1;
    padding-right: 14px;
    display: flex;
    flex-wrap: wrap;
    // overflow-y: auto;
    justify-content: space-between;
    .imgItem {
      width: 250px;
      margin-right: 20px;
      margin-bottom: 1.852vh;
      border-radius: 4px;
      display: flex;
      flex-direction: column;
      .elImg {
        width: 100%;
        height: 12.964vh;
        margin-bottom: 5px;
        background: rgba($color: #e5e5e5, $alpha: 0.3);
        display: flex;
        align-items: center;
        justify-content: center;
        /deep/ .el-image__inner {
          width: unset;
          height: unset;
          max-width: 100%;
          max-height: 100%;
          border-radius: 4px;
        }
      }
      .btm {
        /deep/ .el-radio__label {
          color: #000;
        }
        /deep/ .el-radio__inner {
          border: 1px solid #419aff;
          background: transparent;
          &::after {
            width: 8px;
            height: 8px;
            background: #419aff;
          }
        }
      }
      span {
        word-break: break-all;
      }
      &:nth-child(4n) {
        margin-right: 0;
      }
      /deep/ .el-checkbox,
      /deep/.el-checkbox__input {
        display: flex;
        align-items: center;
      }
      /deep/ .el-checkbox__label {
        max-width: 200px;
        overflow: hidden;
        text-overflow: ellipsis;
      }
    }
    &::after {
      content: '';
      flex: 1;
    }
    &::-webkit-scrollbar {
      width: 8px;
    }
    &::-webkit-scrollbar-thumb {
      background: #c4c4c4;
      border-radius: 4px;
    }
  }

  &-right {
    width: 100%;
    height: 100%;
    padding: 1.852vh 24px 1.852vh 24px;
    display: flex;
    flex-direction: column;
    .surveyMsg {
      width: 100%;
      padding-bottom: 1.852vh;
      display: flex;
      flex-direction: column;
      .title {
        font-weight: bold;
      }
      .msgBox {
        width: 100%;
        // padding-top: 1.852vh;
        display: flex;
        flex-wrap: wrap;
        // justify-content: space-between;
        .msgItem {
          width: 33%;
          // padding-right: 24px;
          margin-top: 1.852vh;
          display: flex;
          align-items: center;
          justify-content: flex-end;
          .txt {
            font-size: 14px;
            padding-right: 8px;
          }
          .valInput {
            width: 260px;
          }
          /deep/ .el-input__inner {
            cursor: default;
          }
          /deep/ .el-input.is-disabled .el-input__inner {
            background: transparent;
            border: 1px solid #dcdfe6;
            color: #606266;
          }
          /deep/ .el-input-group__append {
            color: #606266;
            padding: 0 16px;
            background: transparent;
          }
        }
      }
    }
    .surveyPhoto {
      width: 100%;
      display: flex;
      flex-direction: column;
      .title {
        font-weight: bold;
        margin-right: 16px;
      }
    }
  }
}
.surveyBtn {
  padding-bottom: 1.852vh;
  display: flex;
  align-items: center;
  .down {
    margin-right: 20px;
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
}

.icon {
  color: #419aff;
  cursor: pointer;
  img {
    width: 100%;
    height: 100%;
  }
}
.text {
  font-size: 0.729165vw;
}

/deep/ .el-message-box__wrapper > .el-message-box {
  width: 40vw !important;
}
</style>
