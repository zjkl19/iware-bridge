<template>
  <div class="strunctTab">
    <div class="strunctLeft">
      <div class="span">{{ structureName }}</div>
      <div class="tree">
        <el-tree
          :data="surveyList"
          :props="defaultProps"
          @node-click="handleNodeClick"
          :default-expand-all="true"
        ></el-tree>
      </div>
    </div>
    <div class="strunctRight">
      <div class="strunctMsg">
        <span class="title">结构信息</span>
        <div class="msgBox">
          <div v-for="item in strunctMsgList" :key="item.id" class="msgItem">
            <span class="txt">{{ item.name }}</span>
            <el-input class="valInput" v-model="item.value" disabled>
              <template v-if="item.unit" slot="append">{{
                item.unit
              }}</template>
            </el-input>
          </div>
        </div>
      </div>
      <div class="strunctPhoto">
        <span class="title">结构照片</span>
        <div class="strunctImgbox">
          <div v-for="(item, index) in imgArr" :key="item.id" class="imgItem">
            <el-image
              :src="item.path"
              class="elImg"
              :preview-src-list="pathArr[index]"
            >
            </el-image>
            <span>{{ item.name }}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {
  getBridgeSurveyDetail,
  getDiseaseDetail
} from '@/api/assessment/survey';
export default {
  components: {},
  inject: ['params'],
  data() {
    return {
      addOpt: this.params().addOpt,
      updateOpt: this.params().updateOpt,
      deleteOpt: this.params().deleteOpt,
      structureId: this.params().structureId,
      structureName: this.params().structureName,
      surveyList: [], //树形结构数组
      defaultProps: {
        label: 'name',
        children: 'children'
      },
      strunctMsgList: [],
      partType: '',
      imgId: '', //图片ID
      moniPlanStructId: '',
      pathArr: [], //大图显示
      imgArr: []
    };
  },
  mounted() {},
  created() {
    this.getTree();
  },
  methods: {
    // 获取树形结构
    async getTree() {
      let params = {
        bid: this.structureId
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
      this.pathArr = [];
      let params = {
        id: this.imgId,
        type: this.partType,
        moniPlanStructId: this.moniPlanStructId
      };
      let { code, data } = await getDiseaseDetail(params);
      if (code == '0000') {
        data.list.map((item) => {
          item.value = item.value == 'null' ? '-' : item.value;
        });
        this.strunctMsgList = data.list;
        this.imgArr = data.photo;
        data.photo.map((item) => {
          this.pathArr.push([item.path]);
        });
      }
    },
    // 点击切换
    async handleNodeClick(obj, node) {
      this.partType = obj.type;
      this.imgId = obj.id;
      await this.getDiseaseDetail();
    }
  }
};
</script>
<style scoped lang="scss">
.strunctTab {
  display: flex;
  .strunctLeft {
    width: 360px;
    border-right: 1px solid #e4e7ed;
    display: flex;
    flex-direction: column;
    .span {
      font-weight: bold;
    }
    .tree {
      height: 71.76vh;
      padding: 1.852vh 20px 1.852vh 0;
      overflow: auto;
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
  .strunctRight {
    flex: 1;
    padding-left: 20px;
    .strunctMsg {
      width: 100%;
      padding-bottom: 1.852vh;
      display: flex;
      flex-direction: column;
      .title {
        font-weight: bold;
      }
      .msgBox {
        width: 100%;
        display: flex;
        flex-wrap: wrap;
        .msgItem {
          width: 25%;
          margin-top: 1.852vh;
          display: flex;
          align-items: center;
          justify-content: flex-end;
          .txt {
            font-size: 14px;
            padding-right: 8px;
          }
          .valInput {
            width: 240px;
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
    .strunctPhoto {
      width: 100%;
      display: flex;
      flex-direction: column;
      .title {
        font-weight: bold;
        padding-bottom: 1.852vh;
      }
      .strunctImgbox {
        display: flex;
        flex-wrap: wrap;
        justify-content: space-between;
        .imgItem {
          width: 344px;
          margin-right: 20px;
          margin-bottom: 1.852vh;
          border-radius: 4px;
          display: flex;
          flex-direction: column;
          .elImg {
            width: 100%;
            height: 17.686vh;
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
    }
  }
}
</style>
