<template>
  <div class="header">
    <div class="firstMenu">
      <div class="firstMenuLeft">
        <img src="@/assets/images/logo2.png" alt="" />
        <span>城市桥隧智慧管养云平台</span>
      </div>
      <div class="firstMenuRoute">
        <div
          v-for="item in firstMenuList"
          :key="item.id"
          class="routerItem"
          :class="{ routerActive: activeIndex == item.id }"
          @click="firstRouterGo(item)"
        >
          <span
            ><i class="iconfont" :class="'icon-' + item.routerName"></i></span
          ><span>{{ item.name }}</span>
        </div>
      </div>
      <div class="firstMenuRight">
        <el-dropdown class="message">
          <el-badge :value="msgNum" :hidden="msgNum == 0" class="item">
            <span class="bell"><i class="el-icon-bell"></i></span>
          </el-badge>
          <el-dropdown-menu slot="dropdown" class="headDrop">
            <div class="title">公告 ({{ msgOriginalList.length }})</div>
            <div class="msgBox" v-infinite-scroll="msgLoadMore">
              <div
                v-for="item in msgList"
                :key="item.id"
                class="msgItem"
                @click="msgClick(item)"
              >
                <div class="titleBox">
                  <span v-if="item.status == 0" class="dot"></span>
                  <span class="txt">{{ item.title }}</span>
                </div>
                <span class="time"
                  >{{ item.creator }}发布于 {{ item.effectTime }}</span
                >
              </div>
              <div v-if="msgLoading" class="btmTitle">
                加载中<i class="el-icon-loading"></i>
              </div>
              <div v-if="msgNoMore && msgList.length == 0" class="btmTitle">
                暂无公告
              </div>
              <div v-if="msgNoMore && msgList.length > 5" class="btmTitle">
                已加载全部
              </div>
            </div>
          </el-dropdown-menu>
        </el-dropdown>
        <!-- <span class="message"><i class="el-icon-bell"></i></span> -->
        <span class="version" @click="showVersion"
          ><i class="iconfont icon-version"></i></span
        ><span class="username">{{ realName }}</span
        ><span class="logout" @click="logout"
          ><i class="iconfont icon-logout"></i
        ></span>
      </div>
    </div>
    <router-view class="contentFirst" />

    <!-- 公告弹窗 -->
    <el-dialog
      class="msgDialog"
      :visible.sync="showMsgDialog"
      title="公告详情"
      width="40vw"
      :modal-append-to-body="false"
      :close-on-click-modal="false"
    >
      <div class="diaBox">
        <div class="header">{{ msgForm.title }}</div>
        <div class="context">{{ msgForm.content }}</div>
        <div class="footer">
          {{ msgForm.creator }}发布于 {{ msgForm.effectTime }}
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  logout,
  getOnlineNotice,
  getNoticeList,
  updNoticeStatus
} from '@/api/login/login';
export default {
  name: 'Header-dzl',
  components: {},
  props: {},
  data() {
    return {
      menuList: [],
      firstMenuList: [],
      msgNum: 0, //公告数
      msgList: [], //公告列表
      msgOriginalList: [], //公告总列表
      msgPage: 1,
      msgLoading: false, //显示加载中
      msgNoMore: false, //是否加载全部公告
      showMsgDialog: false, //是否显示公告弹窗
      msgForm: {},
      realName: ''
    };
  },
  computed: {
    activeIndex() {
      return this.$store.getters.getActiveIndex;
    },
    activeName() {
      return this.secondName;
    },
    routerPath() {
      return this.$route.path;
    },
    noticeState() {
      return this.$store.getters.getNoticeState;
    }
  },
  watch: {
    activeIndex(val) {
      let arry = [];
      this.menuList.map((item) => {
        if (item.parentId == val && (item.active == 0 || item.active == null)) {
          arry.push(item);
        }
      });
      this.$store.dispatch('asyncUpdateSecondMenuList', arry);
    },
    routerPath(val) {
      this.menuList.map((item) => {
        let name = val.split('/')[1];
        if (item.routerName == name && item.parentId == 0) {
          this.$store.dispatch('asyncUpdateActiveIndex', item.id);
        }
      });
    },
    noticeState() {
      this.msgPage = 1;
      this.msgNoMore = false;
      this.getNoticeList();
    }
  },
  methods: {
    //获取公告列表
    async getNoticeList() {
      let { code, data } = await getNoticeList();
      if (code == '0000') {
        this.msgNum = data.unread;
        this.msgOriginalList = data.announcementList;
        this.msgList = [];
        this.getMsgList();
      }
    },
    //获取预警数量、传感器数量
    async getOnlineNotice() {
      let { code, data } = await getOnlineNotice();
      if (code == '0000') {
        this.$store.dispatch('asyncUpdateDotWarn', data.warningCount); //预警管理红点
        this.$store.dispatch('asyncUpdateDotSensor', data.offlineCount); //传感器设置红点
      }
    },
    //一级跳转路由
    firstRouterGo(data) {
      // this.$store.dispatch("asyncUpdateActiveIndex", data.id);
      // this.activeIndex = data.id;
      // let secondArry = [];
      // menuList.map((item) => {
      //   if (item.parentId == data.id && item.is_active == 0) {
      //     secondArry.push(item);
      //   }
      // });
      // if (secondArry.length > 0) {
      //   this.secondName = secondArry[0].name;
      //   this.secondMenuList = secondArry;
      // }
      this.$store.dispatch('asyncUpdateActiveIndex', data.id);
      this.$router.push(data.routerUrl);
    },
    //退出登录
    logout() {
      this.$confirm('确定退出？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        let { code } = await logout();
        if (code == '0000') {
          this.$message({
            message: '退出成功！',
            type: 'success',
            showClose: true,
            duration: 2000
          });
          this.$store.dispatch('asyncUpdateUserInfo', {});
          this.$store.dispatch('asyncUpdateRouterList', []);
          this.$store.dispatch('asyncUpdateActiveIndex', null);
          this.$router.push('/login');
        }
      });
    },
    //显示当前版本
    showVersion() {
      let div = this.$createElement;
      this.$msgbox({
        title: '版本信息',
        message: div('div', { class: 'versionBox' }, [
          div('span', null, '当前版本：AN-IWARE-BRIDGE_1.4.0 (2022-03-03)'),
          div('span', null, '版权所有：福建省建筑科学研究院')
        ]),
        showCancelButton: false,
        showConfirmButton: false
      })
        .then(() => {})
        .catch(() => {});
    },
    //获取到当前公告列表
    getMsgList() {
      let arry = JSON.parse(JSON.stringify(this.msgOriginalList));
      let newArry = arry.splice((this.msgPage - 1) * 5, 5);
      if (newArry.length < 5) {
        this.msgNoMore = true;
      }
      this.msgList = [...this.msgList, ...newArry];
      this.msgLoading = false;
      this.msgPage++;
    },
    //加载更多公告
    msgLoadMore() {
      if (this.msgLoading || this.msgNoMore) {
        return;
      }
      this.msgLoading = true;
      setTimeout(() => {
        this.getMsgList();
      }, 2000);
    },
    //点击查看公告
    async msgClick(item) {
      let { code } = await updNoticeStatus(item.id, 1);
      if (code == '0000') {
        this.msgForm = { ...item };
        this.showMsgDialog = true;
        this.$nextTick(() => {
          this.$store.dispatch('asyncUpdateNoticeState', Math.random()); //更新公告状态
        });
      }
    }
  },
  mounted() {
    let menuList = this.$store.getters.getRouterList;
    this.menuList = menuList;
    let firstArry = [];
    menuList.map((item) => {
      if (item.parentId == 0) {
        item['routerName'] = item.routerUrl.split('/')[1];
        firstArry.push(item);
      }
    });
    this.firstMenuList = firstArry;
    let defaultIndex = this.$store.getters.getActiveIndex;
    if (defaultIndex !== null) {
      let arry = [];
      menuList.map((item) => {
        if (
          item.parentId == defaultIndex &&
          (item.active == 0 || item.active == null)
        ) {
          arry.push(item);
        }
      });
      this.$store.dispatch('asyncUpdateSecondMenuList', arry);
    }
    this.realName = this.$store.getters.getUserInfo.realName;
    if (
      this.$route.name == '首页总览' ||
      window.performance.navigation.type == 1
    ) {
      this.getNoticeList(); //获取公告列表
      this.getOnlineNotice(); //获取预警数量、传感器数量
    }
  }
};
</script>
<style lang="scss" scoped>
.header {
  height: 100%;
  width: 100%;
  display: flex;
  flex-direction: column;
  .firstMenu {
    height: 6.488vh;
    width: 100%;
    color: #fff;
    background: #031429;
    padding: 0 28px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    .firstMenuLeft {
      display: flex;
      align-items: center;
      span {
        font-size: 32px;
        margin-left: 16px;
      }
    }
    .firstMenuRoute {
      height: 100%;
      display: flex;
      align-items: center;
      .routerItem {
        height: 100%;
        width: 128px;
        font-size: 20px;
        color: rgba(255, 255, 255, 0.85);
        text-decoration: none;
        display: flex;
        align-items: center;
        justify-content: center;
        cursor: pointer;
        span:first-child {
          margin-right: 4px;
        }
      }
      .routerActive {
        background: #419aff;
        color: #fff;
      }
    }
    .firstMenuRight {
      display: flex;
      align-items: center;
      .message {
        .bell {
          font-size: 20px;
          color: #9aa1a9;
          cursor: pointer;
        }
        /deep/ .el-badge__content {
          height: 16px;
          line-height: 16px;
          font-size: 12px;
          color: #fff;
          text-align: center;
          border: 0;
          border-radius: 8px;
          background: #ff3c0f;
        }
      }
      .version {
        font-size: 16px;
        margin-left: 18px;
        color: #9aa1a9;
        cursor: pointer;
      }
      .username {
        font-size: 16px;
        color: #fafafb;
        margin: 0 12px 0 18px;
      }
      .logout {
        width: 20px;
        height: 20px;
        font-size: 20px;
        color: #9aa1a9;
        display: flex;
        align-items: center;
        justify-content: center;
        cursor: pointer;
      }
    }
  }
  .contentFirst {
    flex: 1;
    overflow: hidden;
  }
}
//公告提示样式
.headDrop {
  // height: 28.334vh;
  width: 336px;
  right: 16px !important;
  left: unset !important;
  padding: 0;
  border: 0;
  background-color: #1a3d67;
  box-shadow: 0px 2px 16px 0px rgba(15, 15, 15, 0.36);
  display: flex;
  flex-direction: column;
  .title {
    height: 4.075vh;
    font-size: 14px;
    color: rgba($color: #fff, $alpha: 0.85);
    border-bottom: 1px solid #2f4970;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  .btmTitle {
    height: 4.075vh;
    font-size: 14px;
    color: rgba($color: #fff, $alpha: 0.85);
    display: flex;
    align-items: center;
    justify-content: center;
  }
  .msgBox {
    max-height: 24.168vh;
    overflow-y: auto;
    .msgItem {
      height: 8.057vh;
      width: 100%;
      padding: 0 24px;
      border-bottom: 1px solid #2f4970;
      display: flex;
      flex-direction: column;
      justify-content: space-evenly;
      cursor: pointer;
      .titleBox {
        display: flex;
        align-items: center;
        .dot {
          width: 6px;
          height: 6px;
          border-radius: 50%;
          margin-right: 5px;
          background: #ff5f5f;
        }
        .txt {
          flex: 1;
          font-size: 14px;
          color: rgba($color: #fff, $alpha: 0.85);
          white-space: nowrap;
          overflow: hidden;
          text-overflow: ellipsis;
        }
      }
      .time {
        font-size: 12px;
        color: rgba($color: #fff, $alpha: 0.4);
      }
      &:hover {
        background: #0e2f57;
      }
    }
    &::-webkit-scrollbar {
      width: 6px;
    }
    &::-webkit-scrollbar-thumb {
      background: #419aff;
      border-radius: 4px;
    }
  }
  /deep/ .popper__arrow {
    display: none;
  }
  // &::after {
  //   position: absolute;
  //   top: -16px;
  //   right: 146px;
  //   content: '';
  //   border-width: 8px;
  //   border-style: dashed dashed solid dashed;
  //   border-color: transparent transparent #1a3d67 transparent;
  // }
}
//公告弹窗
.msgDialog {
  .diaBox {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    .header {
      font-size: 24px;
      color: #fff;
      text-align: center;
    }
    .context {
      font-size: 18px;
      color: rgba($color: #fff, $alpha: 0.85);
      text-indent: 2em;
      margin: 20px 0;
    }
    .footer {
      font-size: 14px;
      color: rgba($color: #fff, $alpha: 0.4);
    }
  }
  /deep/ .el-dialog {
    background: #1a3d67;
  }
  /deep/ .el-dialog__title {
    color: #fff;
  }
  /deep/ .el-dialog__body {
    padding: 16px 24px 24px;
  }
}
//版本信息
.versionBox {
  display: flex;
  flex-direction: column;
  span {
    line-height: 24px;
  }
}
</style>
