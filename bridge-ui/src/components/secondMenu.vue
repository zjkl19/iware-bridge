<template>
  <div class="secondBox">
    <div v-if="secondMenuList.length > 0" class="secondMenu">
      <el-tabs
        v-model="secondName"
        class="secondTabs"
        @tab-click="secondRouterGo"
      >
        <el-tab-pane
          v-for="item in secondMenuList"
          :key="item.id"
          :label="item.name"
          :name="item.name"
        >
          <span slot="label">
            <el-badge
              v-if="item.name == '预警管理'"
              :is-dot="dotWarn > 0"
              class="dot"
              >{{ item.name }}</el-badge
            >
            <el-badge
              v-else-if="item.name == '传感器设置'"
              :is-dot="dotSensor > 0"
              class="dot"
              >{{ item.name }}</el-badge
            >
            <span v-else>{{ item.name }}</span>
          </span>
        </el-tab-pane>
      </el-tabs>
    </div>
    <div class="contentSecond">
      <keep-alive :include="aliveList">
        <router-view class="contentInfoBox boxShadow" />
      </keep-alive>
    </div>
  </div>
</template>

<script>
export default {
  name: 'secondMenu-dzl',
  components: {},
  data() {
    return {
      secondName: '',
      aliveList: ['bridgeManage', 'tunnelManage', 'projectManage']
    };
  },
  computed: {
    secondMenuList() {
      return this.$store.getters.getSecondMenuList;
    },
    activeName() {
      return this.secondName;
    },
    routerPath() {
      return this.$route.path;
    },
    dotWarn() {
      return this.$store.getters.getDotWarn;
    },
    dotSensor() {
      return this.$store.getters.getDotSensor;
    }
  },
  watch: {
    secondMenuList() {
      this.routerChange();
    },
    routerPath() {
      this.routerChange();
    }
  },
  methods: {
    //二级路由跳转
    secondRouterGo() {
      this.$router.push({ name: this.activeName });
    },
    //路由切换
    routerChange() {
      this.secondMenuList.map((item) => {
        if (item.routerUrl == this.routerPath) {
          this.secondName = item.name;
        }
      });
    }
  },
  mounted() {
    let path = this.$route.path;
    this.secondMenuList.map((item) => {
      if (item.routerUrl == path) {
        this.secondName = item.name;
      }
    });
  }
};
</script>
<style lang="scss" scoped>
.secondBox {
  display: flex;
  flex-direction: column;
  .secondMenu {
    height: 5.19vh;
    width: 100%;
    padding: 0 28px;
    .secondTabs {
      height: 100%;
      .dot /deep/ .el-badge__content {
        top: 10px;
        left: -15px;
      }
      /deep/ div {
        height: 100%;
        display: flex;
        align-items: center;
      }
      /deep/ .el-tabs__content {
        height: 0;
      }
      /deep/ .el-tabs__active-bar {
        height: 2px;
        bottom: 1px;
      }
      /deep/ .el-tabs__nav {
        display: flex;
        align-items: center;
      }
      /deep/ .el-tabs__header {
        margin: 0;
      }
      /deep/ .el-tabs__item {
        font-size: 16px;
      }
      /deep/ .el-tabs__nav-wrap::after {
        background: transparent;
      }
      /deep/ .el-tabs__item:focus.is-active.is-focus:not(:active) {
        box-shadow: none;
      }
    }
  }
  .contentSecond {
    // position: relative;
    flex: 1;
    padding: 1.855vh 28px;
    background: #f6f7fb;
    overflow: hidden;
    z-index: 2;
    .contentInfoBox {
      width: 100%;
      height: 100%;
      background: #fff;
      border-radius: 8px;
    }
  }
}
</style>
