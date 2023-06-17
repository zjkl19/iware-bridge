<template>
  <div class="bridgeManageDetial">
    <div class="bridgeDetailTop">
      <span @click="routerGo">{{ routerBackName }}</span
      ><span> / 桥梁详情</span>
    </div>
    <div class="bridgeDetailBtm">
      <div class="ass-input boxShadow">
        <el-tabs
          v-model="activeName"
          class="briDetailTabs"
          @tab-click="tabCheckout()"
        >
          <el-tab-pane
            class="briDetailPane"
            label="桥梁卡片资料"
            name="cardinfo"
          >
            <CarfInfoTab
              v-if="activeName == 'cardinfo'"
              class="paneItem"
            ></CarfInfoTab>
          </el-tab-pane>
          <el-tab-pane class="briDetailPane" label="结构详细数据" name="struct">
            <StrunctTab
              class="paneItem"
              v-if="activeName == 'struct'"
            ></StrunctTab>
          </el-tab-pane>
          <el-tab-pane class="briDetailPane" label="图片管理" name="photo">
            <photo class="paneItem" v-if="activeName == 'photo'"> </photo>
          </el-tab-pane>
          <el-tab-pane class="briDetailPane" label="模型上传" name="models">
            <models class="paneItem" v-if="activeName == 'models'"></models>
          </el-tab-pane>
          <el-tab-pane
            class="briDetailPane"
            label="日常巡查记录"
            name="inspectio_day"
          >
            <inspectio-day
              class="paneItem"
              v-if="activeName == 'inspectio_day'"
              @briefingChange="briefingChange"
            ></inspectio-day>
          </el-tab-pane>
          <el-tab-pane class="briDetailPane" label="维修养护记录" name="five">
            <five
              class="paneItem"
              v-if="activeName == 'five'"
              @briefingChange="briefingChange"
            ></five>
          </el-tab-pane>
          <el-tab-pane class="briDetailPane" label="检测记录" name="check">
            <check class="paneItem" v-if="activeName == 'check'"></check>
          </el-tab-pane>
          <el-tab-pane
            class="briDetailPane"
            label="电子档案资料"
            name="electronicArchives"
          >
            <electronicArchives
              class="paneItem"
              v-if="activeName == 'electronicArchives'"
            ></electronicArchives>
          </el-tab-pane>
        </el-tabs>
      </div>
    </div>

    <!-- 巡查简报 -->
    <div v-if="showBriefing == 1">
      <bridgeBriefing
        :reportId="reportId"
        :parentName="parentName"
        :structureId="structureId"
        @closed="showBriefing = 0"
        style="top: 0"
      ></bridgeBriefing>
    </div>
    <div v-if="showBriefing == 2">
      <briefReport
        :reportId="reportId"
        :parentName="parentName"
        :structureId="structureId"
        @closed="showBriefing = 0"
        style="top: 0"
      ></briefReport>
    </div>
  </div>
</template>

<script>
import CarfInfoTab from './children/CardInfoTab';
import StrunctTab from './children/StrunctTab';
import photo from './children/photo';
import models from './children/models';
import inspectioDay from './children/inspectio_day';
import five from './children/five';
import check from './children/check';
import electronicArchives from './children/electronicArchives';
import bridgeBriefing from '../normal/bridgeBriefing';
import briefReport from '../maintain/briefReport';
export default {
  components: {
    CarfInfoTab,
    StrunctTab,
    photo,
    models,
    inspectioDay,
    five,
    check,
    electronicArchives,
    bridgeBriefing,
    briefReport
  },
  data() {
    return {
      routeId: 0,
      activeName: 'cardinfo',
      fgnKeyId: this.$route.query.id,
      // structureName:"",
      structureId: '',
      structureName: '',
      routerBackName: '',
      reportId: '',
      parentName: '',
      showBriefing: 0
    };
  },
  provide() {
    return {
      params: () => {
        return {
          addOpt: this.addOpt,
          updateOpt: this.updateOpt,
          deleteOpt: this.deleteOpt,
          structureId: this.structureId,
          structureName: this.structureName
        };
      }
    };
  },
  beforeMount() {
    this.$utils.getAuthPage(this, 92); //获取权限
    this.structureId = this.$route.query.id;
    this.structureName = this.$route.query.structureName;
    this.routerBackName = this.$route.query.name;
    if (this.$route.query.type) {
      this.activeName = this.$route.query.type;
    }
  },
  beforeRouteEnter: function (to, from, next) {
    if (from.path == '/normal/bridgeBriefing') {
      next((vm) => {
        vm.activeName = 'inspectio_day';
      });
    } else if (from.path == '/maintain/briefReport') {
      next((vm) => {
        vm.activeName = 'five';
      });
    } else if (from.path == '/assessment/record') {
      next((vm) => {
        vm.activeName = 'check';
      });
    } else {
      next();
    }
  },
  methods: {
    tabCheckout(tab, event) {},
    handleClick() {
      let _this = this;
      getInfoBriManDetailById(this.fgnKeyId).then((resp) => {
        if (resp.code == '0000') {
          _this.structureManage = resp.data[0];
        }
      });
    },
    //巡查日报
    briefingChange(data) {
      this.reportId = data.id;
      this.parentName = '桥梁详情';
      this.showBriefing = data.type;
    },
    //路由回退
    routerGo() {
      this.$router.go(-1);
    }
  }
};
</script>

<style scoped lang="scss">
.bridgeManageDetial {
  position: absolute;
  top: 6.448vh;
  left: 0;
  height: 93.552vh !important;
  display: flex;
  flex-direction: column;
  .bridgeDetailTop {
    height: 5.24vh;
    background: #fff;
    padding: 0 28px;
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
  .bridgeDetailBtm {
    flex: 1;
    padding: 1.855vh 28px;
    background: #f6f7fb;
    overflow: hidden;
    .briDetailTabs {
      height: 100%;
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      .briDetailPane {
        height: 100%;
        .paneItem {
          height: 100%;
        }
      }
      /deep/ .el-tabs__header {
        margin: 0;
      }
      /deep/ .el-tabs__content {
        height: 93%;
      }
    }
  }
}
.assessment {
  display: flex;
  justify-content: flex-end;
  // margin-top: 20px;
  .ass-top {
    border: solid 1px #3873ec;
    font-family: SourceHanSansCN-Regular;
    font-size: 14px;
    color: #3873ec;
    background: #141e30;
  }
}
.ass-input {
  padding: 24px;
  width: 100%;
  height: 100%;
  /deep/ .el-tabs__item:focus.is-active.is-focus:not(:active) {
    box-shadow: none;
  }
  /deep/ .el-tabs__nav-wrap::after {
    height: 1px;
  }
}
</style>
