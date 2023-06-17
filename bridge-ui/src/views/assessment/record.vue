<template>
  <div class="record animate__animated animate__fadeIn">
    <div class="recordLeft">
      <div class="reLeTop">检测列表</div>
      <div class="reLeMid">
        <el-select
          class="seleProject"
          v-model="projectId"
          placeholder="请选择项目"
          @change="projectChange"
          clearable
        >
          <el-option
            v-for="item in projectList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          >
          </el-option>
        </el-select>
        <div class="midTool">
          <el-select
            class="seleStructure"
            v-model="structureId"
            placeholder="请选择结构物"
            @change="getRecordList(false)"
            clearable
          >
            <el-option
              v-for="item in structureList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
            <template slot="empty">
              <div class="searchDiv">{{ structureTitle }}</div>
            </template>
          </el-select>
          <!-- <div class="midSelect" @click="getRecordList(false)">查询</div> -->
        </div>

        <!-- <div class="midItem">
          <el-select
            class="seleLevel"
            v-model="levelVal"
            placeholder="评定等级"
            clearable
          >
            <el-option
              v-for="item in levelList"
              :key="item.id"
              :label="item.label"
              :value="item.label"
            >
            </el-option>
          </el-select>
        </div>
        <div class="midItem">
          <el-date-picker
            v-model="timeVal"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            value-format="yyyy-MM-dd"
            :picker-options="pickerOptions"
            clearable
          >
          </el-date-picker>
          <div class="midSelect" @click="getRecordList(false)">查询</div>
        </div> -->
      </div>
      <div class="reLeBtm">
        <div class="btmTop">
          <span class="itemOne">结构物</span>
          <span class="itemTwo">检测完成时间</span>
          <span class="itemThree">评定等级</span>
          <span class="itemFour"></span>
        </div>
        <div ref="scrollDiv" class="btmBtm">
          <div
            v-for="(item, index) in bridgeList"
            :ref="activeIndex == index ? 'activeItem' : ''"
            :key="index"
            class="btmItem"
            :class="{ activeClass: activeIndex == index }"
            @click="bridgeClick(index, item)"
          >
            <span class="itemOne" :title="item.structureName">{{
              item.structureName
            }}</span>
            <span class="itemTwo" :title="item.time">{{ item.time }}</span>
            <span class="itemThree">{{ item.ratingLevel }}</span>
            <span v-if="item.original == null" class="delIcon" @click="bridgeDel(item)"
              ><i class="el-icon-delete"></i
            ></span>
          </div>
          <div v-if="bridgeList.length == 0" class="noData">暂无数据</div>
        </div>
      </div>
    </div>
    <div class="recordRight">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane class="tabItem" label="桥梁概况" name="survey">
          <survey
            v-if="activeName == 'survey'"
            :structureManage="structureManage"
          ></survey>
        </el-tab-pane>
        <el-tab-pane class="tabItem" label="桥梁病害" name="disease">
          <disease
            v-if="activeName == 'disease'"
            :structureManage="structureManage"
            :key="keyItem"
          ></disease>
        </el-tab-pane>
        <el-tab-pane class="tabItem" label="BCI评价" name="BCIeva">
          <bcieva
            v-if="activeName == 'BCIeva'"
            @reflash="reflash"
            :structureManage="structureManage"
          ></bcieva>
        </el-tab-pane>
        <el-tab-pane class="tabItem" label="原始记录" name="original">
          <original
            v-if="activeName == 'original'"
            :structureManage="structureManage"
            @reflash="reflash"
          ></original>
        </el-tab-pane>
        <el-tab-pane class="tabItem" label="检测报告" name="report">
          <report
            v-if="activeName == 'report'"
            :structureManage="structureManage"
          ></report>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>
<script>
import { getRecordList, delMonitorStructure } from '@/api/assessment/record';
import { getProjectListByModel, getStructureListByModel } from '@/api/common';
import survey from './children/record/survey';
import disease from './children/record/disease';
import bcieva from './children/record/BCIeva';
import original from './children/record/original';
import report from './children/record/report';
export default {
  name: 'Record-dzl',
  components: {
    survey,
    disease,
    bcieva,
    original,
    report
  },
  data() {
    return {
      projectId: '',
      activeName: 'survey',
      projectVal: '',
      structureId: '',
      structureTitle: '请先选择项目',
      levelVal: '',
      projectList: [],
      structureList: [],
      levelList: [
        { id: 1, label: 'A' },
        { id: 2, label: 'B' },
        { id: 3, label: 'C' },
        { id: 4, label: 'D' },
        { id: 5, label: 'E' }
      ],
      timeVal: '',
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now() + 24 * 60 * 60 * 1000;
        },
        shortcuts: [
          {
            text: '最近一周',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', [start, end]);
            }
          },
          {
            text: '最近一个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit('pick', [start, end]);
            }
          },
          {
            text: '最近三个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit('pick', [start, end]);
            }
          }
        ]
      },
      activeIndex: 0,
      bridgeList: [
        // {
        //   id: 11,
        //   structureName: '朝芸桥',
        //   time: '2020-03-20',
        //   ratingLevel: '合格级'
        // },
        // {
        //   id: 1,
        //   structureName: '解放大桥',
        //   time: '2019-07-02',
        //   ratingLevel: '合格级'
        // },
        // {
        //   id: 2,
        //   structureName: '闽江四桥',
        //   time: '2019-06-17',
        //   ratingLevel: '合格级'
        // },
        // {
        //   id: 3,
        //   structureName: '尤溪洲大桥',
        //   time: '2019-06-18',
        //   ratingLevel: '合格级'
        // },
        // {
        //   id: 4,
        //   structureName: '螺洲大桥',
        //   time: '2019-07-02',
        //   ratingLevel: '合格级'
        // },
        // {
        //   id: 5,
        //   structureName: '福马路上下三环匝道桥',
        //   time: '2019-07-03',
        //   ratingLevel: 'A'
        // },
        // {
        //   id: 6,
        //   structureName: '横屿路网纵五路K0+037桥',
        //   time: '2019-05-03',
        //   ratingLevel: 'A'
        // },
        // {
        //   id: 7,
        //   structureName: '南二环白湖亭高架桥',
        //   time: '2019-05-15',
        //   ratingLevel: 'A'
        // },
        // {
        //   id: 8,
        //   structureName: '奥体路K0+054桥',
        //   time: '2019-05-23',
        //   ratingLevel: 'A'
        // },
        // {
        //   id: 9,
        //   structureName: '马洲二支河-天水路-桥',
        //   time: '2019-06-07',
        //   ratingLevel: 'A'
        // },
        // {
        //   id: 10,
        //   structureName: '马洲河-仁山路-桥',
        //   time: '2019-06-10',
        //   ratingLevel: 'A'
        // }
      ],
      structureManage: {},
      oldName: '',
      keyItem: 0
    };
  },
  provide() {
    return {
      authOpt: () => {
        return {
          addOpt: this.addOpt,
          checkOpt: this.checkOpt,
          updateOpt: this.updateOpt,
          deleteOpt: this.deleteOpt
        };
      }
    };
  },
  beforeMount() {
    this.$utils.getAuthPage(this); //获取权限
  },
  mounted() {
    this.getProjectListByModel();
    // this.getStructureListByModel();
    let routeInfo = this.$route.params;
    if (routeInfo.activeName) {
      let obj = {
        structureInfoId: routeInfo.structureInfoId,
        time: routeInfo.endTime,
        roadId: routeInfo.roadId,
        monitorStructureId: routeInfo.monitorStructureId,
        activeName: routeInfo.activeName
      };
      this.structureId = routeInfo.structureInfoId || '';
      this.projectId = routeInfo.projectId || '';
      // this.activeName = routeInfo.activeName;
      this.getStructureListByModel();
      this.getRecordList(true, obj); //获取检测列表
    } else {
      this.getRecordList(false); //获取检测列表
    }
  },
  methods: {
    //获取有计划的项目列表
    async getProjectListByModel() {
      let id = this.$store.getters.getActiveIndex;
      let { code, data } = await getProjectListByModel(id);
      if (code == '0000') {
        this.projectList = data;
      }
    },
    //获取有计划的结构物列表
    async getStructureListByModel() {
      let params = {
        projectId: this.projectId,
        powerId: this.$store.getters.getActiveIndex
      };
      let { code, data } = await getStructureListByModel(params);
      if (code == '0000') {
        this.structureList = data;
      }
    },
    //查询检测列表
    async getRecordList(route, object) {
      let params = {
        projectInfoId: this.projectId,
        structureInfoId: this.structureId
      };
      let { code, data } = await getRecordList(params);
      if (code == '0000') {
        if (route) {
          data.map((item, index) => {
            if (object.monitorStructureId == item.id) {
              this.activeIndex = index;
              this.structureManage = item;
              if (object.roadId) {
                this.structureManage.roadId = object.roadId;
              }
              this.activeName = object.activeName;
            }
          });
        } else {
          this.structureManage = data[0];
          this.activeIndex = 0;
        }
        this.bridgeList = data;
        this.$nextTick(() => {
          if (data.length > 0) {
            //滚动到指定位置
            this.$refs.scrollDiv.scrollTop =
              this.$refs.activeItem[0].offsetTop -
              this.$refs.scrollDiv.offsetTop;
          }
        });
      }
    },
    //选择项目
    projectChange(val) {
      this.structureId = '';
      this.structureList = [];
      if (val == '') {
        this.structureTitle = '请先选择项目';
      } else {
        this.structureTitle = '暂无结构物';
        this.getStructureListByModel();
      }
      this.getRecordList(false);
    },
    //bci评价刷新列表
    reflash(activeName) {
      this.getRecordList(true, {
        monitorStructureId: this.structureManage.id,
        activeName
      });
    },
    //切换标签页
    handleClick() {},
    //选择检测列表
    bridgeClick(index, item) {
      this.structureManage = item;
      this.activeIndex = index;
    },
    //删除检测列表项目
    bridgeDel(data) {
      this.$confirm('确定删除这条记录?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async () => {
          let { code } = await delMonitorStructure(data.id);
          if (code && code == '0000') {
            this.$message({
              type: 'success',
              message: '删除成功!',
              showClose: true,
              duration: 2000
            });
            await this.getRecordList(false);
          }
        })
        .catch();
    }
  }
};
</script>
<style lang="scss" scoped>
.record {
  // height: 100%;
  background: #fff;
  border-radius: 0.4vw;
  padding: 1vw 1.2vw;
  display: flex;
  align-items: center;
  .recordLeft {
    width: 21.667vw;
    height: 100%;
    border-right: 1px solid #e8e8e8;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    .reLeTop {
      font-weight: bold;
      font-size: 0.8vw;
      line-height: 24px;
      color: #333;
    }
    .reLeMid {
      // height: 14.088vh;
      // height: 96px;
      padding-right: 1.2vw;
      display: flex;
      // align-items: center;
      // justify-content: space-between;
      // flex-direction: column;
      // justify-content: space-between;
      .midTool {
        // display: flex;
        align-items: center;
        justify-content: space-between;
        // .seleStructure {
        //   width: 316px;
        // }
        .midSelect {
          width: 3.125vw;
          height: 100%;
          font-size: 0.7vw;
          color: #fff;
          background: #419aff;
          border-radius: 4px;
          display: flex;
          align-items: center;
          justify-content: center;
          cursor: pointer;
          &:hover {
            background: rgba(65, 154, 255, 0.8);
          }
        }
      }
      .seleProject {
        width: 10.063vw;
        padding-right: 0.5vw;
      }
      .midItem {
        display: flex;
        align-items: center;
        justify-content: space-between;
        .seleStructure {
          width: 10.063vw;
        }
        .seleLevel {
          width: 5.521vw;
        }
        .midSelect {
          width: 3.125vw;
          height: 100%;
          font-size: 0.7vw;
          color: #fff;
          background: #419aff;
          border-radius: 4px;
          display: flex;
          align-items: center;
          justify-content: center;
          cursor: pointer;
          &:hover {
            background: rgba(65, 154, 255, 0.8);
          }
        }
      }
      /deep/ .el-date-editor {
        width: 16.459vw;
      }
      /deep/ .el-date-editor .el-range-separator {
        padding: 0;
        display: flex;
        align-items: center;
      }
      /deep/ .el-input__inner {
        height: 3.708vh;
        font-size: 0.7vw;
        color: #000;
      }
      /deep/ .el-input__suffix {
        display: flex;
        align-items: center;
      }
    }
    .reLeBtm {
      // height: 62.535vh;
      height: 70vh;
      padding-right: 0.4vw;
      display: flex;
      flex-direction: column;
      .btmTop {
        height: 4.635vh;
        padding-right: 0.8vw;
        display: flex;
        .itemOne {
          padding-left: 0.8vw;
          //   width: 9.271vw;
          width: 35%;
          justify-content: flex-start;
        }
        .itemTwo {
          //   width: 7.396vw;
          width: 35%;
          justify-content: center;
        }
        .itemThree {
          //   flex: 1;
          width: 20%;
          justify-content: flex-end;
        }
        .itemFour {
          flex: 1;
        }
        span {
          font-weight: bold;
          font-size: 0.7vw;
          color: #333;
          border-bottom: 1px solid #e8e8e8;
          display: flex;
          align-items: center;
        }
      }
      .btmBtm {
        // height: 58vh;
        height: 68vh;
        padding-right: 0.4vw;
        display: flex;
        flex-direction: column;
        // flex: 1;
        overflow-y: auto;
        .btmItem {
          position: relative;
          width: 100%;
          height: 48px;
          padding: 0.8vw;
          font-size: 0.7vw;
          color: #000;
          border-bottom: 1px solid #e8e8e8;
          display: flex;
          align-items: center;
          cursor: pointer;
          .itemOne {
            // width: 8.438vw;
            width: 35%;
            text-align: left;
          }
          .itemTwo {
            // width: 7.396vw;
            width: 35%;
            text-align: center;
          }
          .itemThree {
            // flex: 1;
            width: 20%;
            text-align: right;
          }
          .delIcon {
            position: absolute;
            right: 0.6vw;
            // top: 0.65vw;
            opacity: 0;
            font-size: 0.8vw;
            height: 100%;
            display: flex;
            align-items: center;
          }
          span {
            white-space: nowrap;
            text-overflow: ellipsis;
            overflow: hidden;
          }
          &:hover {
            background: #f5f7fa;
            .delIcon {
              opacity: 1;
              color: red;
            }
          }
        }
        .activeClass {
          background: #419aff;
          border-bottom: 1px solid #419aff;
          color: #fff;
          border-radius: 4px;
          &:hover {
            background: #419aff;
            .delIcon {
              color: #fff !important;
            }
          }
        }
        .noData {
          width: 100%;
          height: 100%;
          font-size: 0.7vw;
          display: flex;
          align-items: center;
          justify-content: center;
        }
        &::-webkit-scrollbar {
          width: 8px;
          background: transparent;
        }
        &::-webkit-scrollbar-thumb {
          background: #c4c4c4;
          border-radius: 4px;
        }
      }
    }
  }
  .recordRight {
    height: 100%;
    padding-left: 1.2vw;
    flex: 1;
    .tabItem {
      height: 100%;
    }
    /deep/ .el-tabs {
      height: 100%;
      display: flex;
      flex-direction: column;
    }
    /deep/ .el-tabs__header {
      margin: 0;
    }
    /deep/ .el-tabs__content {
      height: 100%;
      position: unset;
    }
    /deep/ .el-tabs__item:focus.is-active.is-focus:not(:active) {
      box-shadow: none;
    }
    /deep/ .el-tabs__nav-wrap:after {
      height: 1px;
      background-color: #e5e5e5;
    }
  }
}
//下拉框没数据
.searchDiv {
  padding: 10px 0;
  margin: 0;
  text-align: center;
  color: #999;
  font-size: 0.7vw;
}
</style>
