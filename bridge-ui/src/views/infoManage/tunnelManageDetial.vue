<template>
  <div class="tunnelManageDetial">
    <div class="tunnelDetailTop">
      <span @click="routerGo">{{ routerBackName }}</span
      ><span> / 隧道详情</span>
    </div>
    <div class="tunnelDetailBtm">
      <div class="ass-input boxShadow">
        <el-tabs
          v-model="activeName"
          class="briDetailTabs"
          @tab-click="tabCheckout()"
        >
          <el-tab-pane
            class="briDetailPane"
            label="隧道卡片资料"
            name="cardinfo"
          >
            <CarfInfoTab class="paneItem" :routeId="routeId"> </CarfInfoTab>
          </el-tab-pane>
          <el-tab-pane class="briDetailPane" label="图片管理" name="photo">
            <photo
              class="paneItem"
              :routeId="routeId"
              v-if="activeName == 'photo'"
            >
            </photo>
          </el-tab-pane>
          <el-tab-pane class="briDetailPane" label="模型上传" name="models">
            <models
              class="paneItem"
              :routeId="routeId"
              v-if="activeName == 'models'"
            ></models>
          </el-tab-pane>
        </el-tabs>
      </div>
    </div>
  </div>
</template>

<script>
import CarfInfoTab from './children/tunnel/CardInfoTab';
import photo from './children/tunnel/photo';
import models from './children/tunnel/models';
export default {
  components: {
    CarfInfoTab,
    photo,
    models
  },
  data() {
    return {
      routeId: 0,
      activeName: 'cardinfo',
      fgnKeyId: this.$route.query.id,
      structureName: '',
      structureId: '',
      routerBackName: ''
    };
  },
  provide() {
    return {
      params: () => {
        return {
          addOpt: this.addOpt,
          updateOpt: this.updateOpt,
          deleteOpt: this.deleteOpt,
          structureId: this.structureId
        };
      }
    };
  },
  beforeMount() {
    this.$utils.getAuthPage(this, 97); //获取权限
    this.structureId = this.$route.query.id;
    this.structureName = this.$route.query.structureName;
    this.routerBackName = this.$route.query.name;
    if (this.$route.query.type) {
      this.activeName = this.$route.query.type;
    }
  },
  beforeRouteEnter: function (to, from, next) {
    let queryId = to.query.id;

    next((vm) => {
      vm.routeId = parseInt(queryId);
    });
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
    //路由回退
    routerGo() {
      this.$router.go(-1);
    }
  }
};
</script>

<style scoped lang="scss">
.tunnelManageDetial {
  position: absolute;
  top: 6.448vh;
  left: 0;
  height: 93.552vh !important;
  display: flex;
  flex-direction: column;
  .tunnelDetailTop {
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
  .tunnelDetailBtm {
    flex: 1;
    padding: 1.855vh 3.3333vw;
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
  padding: 15px 15px 15px 15px;
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
