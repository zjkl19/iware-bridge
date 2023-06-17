<template>
  <div class="CardInfoTab">
    <el-row class="top">
      <el-col :span="3" class="topTool">
        <el-button
          type="primary"
          v-if="updateOpt && isModify && roleId > 1"
          @click="getModify"
        >
          修改
        </el-button>
        <div v-else-if="updateOpt" style="display: flex">
          <el-button type="primary" @click="getPreserve"> 保存 </el-button>
          <el-button @click="cancel"> 取消 </el-button>
        </div>
        <!-- <el-button> 导出 </el-button> -->
      </el-col>
      <el-col class="title">
        <p style="padding: 0 40px">桥梁名称：{{ structureInfo.name }}</p>
        <p>所在路名：{{ structureInfo.roadName }}</p>
      </el-col>
    </el-row>
    <!-- 表格数据 -->
    <div class="tableBox">
      <el-row class="tableRow">
        <el-col :span="8">
          <el-row style="display: flex; align-items: center">
            <el-col class="head" :span="8">
              <div style="line-height: 70; height: 100%">一般资料</div>
            </el-col>
            <el-col :span="16">
              <div class="content" v-for="item in data1" :key="item.id">
                <label for="i"
                  ><span v-if="item.prop">*</span>{{ item.name }}</label
                >
                <el-select
                  v-if="item.type == 2"
                  class="elSelect"
                  v-model="item.value"
                  placeholder="/"
                  :disabled="isDisabled"
                >
                  <el-option
                    v-for="child in item.list"
                    :key="child.id"
                    :label="child.label"
                    :value="child.id"
                  ></el-option>
                </el-select>
                <el-date-picker
                  v-else-if="item.type == 3"
                  class="timeSel"
                  v-model="item.value"
                  type="month"
                  value-format="yyyy-MM"
                  placeholder="/"
                  :disabled="isDisabled"
                ></el-date-picker>
                <div v-else-if="item.type == -1"></div>
                <div v-else class="textInput">
                  <el-input
                    :type="item.type == 1 ? 'text' : 'number'"
                    class="elInput"
                    placeholder="/"
                    :title="item.value"
                    v-model="item.value"
                    maxlength="30"
                    show-word-limit
                    :disabled="isDisabled"
                  >
                    <template v-if="item.append" slot="append">{{
                      item.append
                    }}</template></el-input
                  >
                </div>
              </div>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="8">
          <el-row>
            <el-col class="head" :span="8">
              <div style="height: 65.4%; line-height: 50">上部结构</div>
              <div style="height: 34.6%; line-height: 25">附属工程</div>
            </el-col>
            <el-col :span="16">
              <div class="content" v-for="item in data2" :key="item.id">
                <label for="i">{{ item.name }}</label>
                <div class="textInput">
                  <el-input
                    class="elInput"
                    type="text"
                    placeholder="/"
                    :title="item.value"
                    v-model="item.value"
                    maxlength="30"
                    :disabled="isDisabled"
                  >
                    <template v-if="item.append" slot="append">{{
                      item.append
                    }}</template></el-input
                  >
                </div>
              </div>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="8">
          <el-row style="display: flex; align-items: center">
            <el-col class="head" :span="8">
              <div style="height: 30.75%; line-height: 25">下部结构-桥墩</div>
              <div style="height: 42.3%; line-height: 35">下部结构-桥台</div>
              <div style="height: 27%; line-height: 20">附挂管线</div>
            </el-col>
            <el-col :span="16">
              <div class="content" v-for="item in data3" :key="item.id">
                <label for="i">{{ item.name }}</label>
                <div class="textInput">
                  <el-input
                    class="elInput"
                    type="text"
                    placeholder="/"
                    :title="item.value"
                    v-model="item.value"
                    maxlength="30"
                    :disabled="isDisabled"
                  >
                    <template v-if="item.append" slot="append">{{
                      item.append
                    }}</template></el-input
                  >
                </div>
              </div>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import {
  getBridgeDetailById,
  updateBridgeDetail
} from '@/api/infomanage/bridgeManage';
export default {
  inject: ['params'],
  data() {
    return {
      data1: [
        // {
        //   id: 1,
        //   name: '管理单位',
        //   type: 1,
        //   code: 'runningDepartment',
        //   value: ''
        // },
        {
          id: 2,
          name: '养护单位',
          type: 1,
          code: 'maintainDepartment',
          value: '',
          prop: true
        },
        {
          id: 3,
          name: '建设单位',
          type: 1,
          code: 'buildingDepartment',
          value: ''
        },
        {
          id: 4,
          name: '设计单位',
          type: 1,
          code: 'designDepartment',
          value: ''
        },
        {
          id: 5,
          name: '监理单位',
          type: 1,
          code: 'supervisionDepartment',
          value: ''
        },
        {
          id: 6,
          name: '施工单位',
          type: 1,
          code: 'constructionDepartment',
          value: ''
        },
        {
          id: 7,
          name: '建成年月',
          type: 3,
          code: 'buildTime',
          value: ''
        },
        {
          id: 8,
          name: '总造价',
          type: 4,
          code: 'cost',
          value: '',
          append: '元'
        },
        {
          id: 9,
          name: '养护类别',
          type: 2,
          code: 'maintainCategory',
          value: '',
          list: [
            { id: 1, label: 'Ⅰ类养护' },
            { id: 2, label: 'Ⅱ类养护' },
            { id: 3, label: 'Ⅲ类养护' },
            { id: 4, label: 'Ⅳ类养护' },
            { id: 5, label: 'Ⅴ类养护' }
          ],
          prop: true
        },
        {
          id: 10,
          name: '养护等级',
          type: 2,
          code: 'maintainGrade',
          value: '',
          list: [
            { id: 1, label: 'Ⅰ等养护' },
            { id: 2, label: 'Ⅱ等养护' },
            { id: 3, label: 'Ⅲ等养护' }
          ],
          prop: true
        },
        {
          id: 11,
          name: '道路等级',
          type: 1,
          code: 'roadGrade',
          value: ''
        },
        {
          id: 12,
          name: '结构类型',
          code: 'bridgeType',
          value: '',
          type: 2,
          list: [
            { id: 1, label: '梁桥' },
            { id: 2, label: '拱桥' },
            { id: 3, label: '刚架拱桥' },
            { id: 4, label: '悬索桥' },
            { id: 5, label: '斜拉桥 ' },
            { id: 6, label: '钢管混凝土拱桥' }
          ],
          prop: true
        },
        {
          id: 13,
          name: '设计荷载',
          type: 1,
          code: 'designLoad',
          value: ''
        },
        {
          id: 14,
          name: '限载标准',
          type: 1,
          code: 'postingStandard',
          value: ''
        },
        {
          id: 15,
          name: '抗震烈度',
          type: 1,
          code: 'quakeIntensity',
          value: ''
        },
        {
          id: 16,
          name: '正斜交角',
          type: 1,
          code: 'skewAngle',
          value: ''
        },
        {
          id: 17,
          name: '桥梁跨数',
          type: 5,
          code: 'spanNum',
          value: ''
        },
        {
          id: 18,
          name: '跨径组合',
          type: 1,
          code: 'spanCombination',
          value: ''
        },
        {
          id: 19,
          name: '桥面面积',
          type: 4,
          code: 'area',
          value: '',
          append: '㎡'
        },
        {
          id: 20,
          name: '桥梁总长',
          type: 4,
          code: 'totalLength',
          value: '',
          append: 'm'
        },
        {
          id: 21,
          name: '桥梁总宽',
          type: 4,
          code: 'totalWidth',
          value: '',
          append: 'm'
        },
        {
          id: 22,
          name: '车行道净宽',
          type: 4,
          code: 'roadwayWidth',
          value: '',
          append: 'm'
        },
        {
          id: 23,
          name: '人行道净宽',
          type: 4,
          code: 'sidewalkWidth',
          value: '',
          append: 'm'
        },
        {
          id: 24,
          name: '河道等级',
          type: 2,
          code: 'streamwayGrade',
          value: '',
          list: [
            { id: 1, label: '1级' },
            { id: 2, label: '2级' },
            { id: 3, label: '3级' },
            { id: 4, label: '4级' },
            { id: 5, label: '5级' }
          ]
        },
        {
          id: 25,
          name: '最高水位',
          type: 4,
          code: 'highestStage',
          value: '',
          append: 'm'
        },
        {
          id: 26,
          name: '常水位',
          type: 4,
          code: 'usualStage',
          value: '',
          append: 'm'
        },
        {
          id: 27,
          name: '',
          type: -1,
          code: '',
          value: ''
        }
      ], //一般资料
      data2: [
        {
          id: 1,
          name: '主梁形式',
          type: 1,
          code: 'upMainBeamForm',
          value: ''
        },
        {
          id: 2,
          name: '主梁尺寸',
          type: 1,
          code: 'upMainBeamSize',
          value: ''
        },
        {
          id: 3,
          name: '主梁数量',
          type: 5,
          code: 'upMainBeamQuantity',
          value: ''
        },
        {
          id: 4,
          name: '横梁形式',
          type: 1,
          code: 'upCrossBeamForm',
          value: ''
        },
        {
          id: 5,
          name: '主跨桥下净空',
          type: 4,
          code: 'clearanceSpan',
          value: '',
          append: 'm'
        },
        {
          id: 6,
          name: '桥下限高',
          type: 4,
          code: 'lowerLimit',
          value: '',
          append: 'm'
        },
        {
          id: 7,
          name: '拱桥矢跨比',
          type: 1,
          code: 'upRiseSpan',
          value: ''
        },
        {
          id: 8,
          name: '支座型式',
          type: 1,
          code: 'upBearingForm',
          value: ''
        },
        {
          id: 9,
          name: '支座数量',
          type: 5,
          code: 'upBearingNum',
          value: ''
        },
        {
          id: 10,
          name: '桥面结构',
          type: 1,
          code: 'upDeckComposition',
          value: ''
        },
        {
          id: 11,
          name: '桥面铺装厚度',
          type: 4,
          code: 'upPavementLand',
          value: '',
          append: 'm'
        },
        {
          id: 12,
          name: '伸缩缝型式',
          type: 1,
          code: 'upExpansionForm',
          value: ''
        },
        {
          id: 13,
          name: '伸缩缝数量',
          type: 5,
          code: 'upExpansionQuantity',
          value: ''
        },
        {
          id: 14,
          name: '主桥纵坡',
          type: 4,
          code: 'upMainLongitudinalSlope',
          value: '',
          append: '%'
        },
        {
          id: 15,
          name: '主桥横坡',
          type: 4,
          code: 'upMainCrossSlope',
          value: '',
          append: '%'
        },
        {
          id: 16,
          name: '引桥纵坡',
          type: 4,
          code: 'upApproachLongitudinalSlope',
          value: '',
          append: '%'
        },
        {
          id: 17,
          name: '引桥横坡',
          type: 4,
          code: 'upApproachCrossSlope',
          value: '',
          append: '%'
        },
        {
          id: 18,
          name: '集水口尺寸',
          type: 1,
          code: 'auxiliaryGulleySize',
          value: ''
        },
        {
          id: 19,
          name: '集水口数量',
          type: 5,
          code: 'auxiliaryGulleyNum',
          value: ''
        },
        {
          id: 20,
          name: '泄水管尺寸',
          type: 1,
          code: 'waterDrainPipeSize',
          value: ''
        },
        {
          id: 21,
          name: '泄水管长度',
          type: 4,
          code: 'waterDrainPipeLength',
          value: '',
          append: 'm'
        },
        {
          id: 22,
          name: '栏杆总长',
          type: 4,
          code: 'auxiliaryRailLength',
          value: '',
          append: 'm'
        },
        {
          id: 23,
          name: '栏杆结构',
          type: 1,
          code: 'auxiliaryRailStruction',
          value: ''
        },
        {
          id: 24,
          name: '端柱尺寸',
          type: 1,
          code: 'auxiliaryBoundarySize',
          value: ''
        },
        {
          id: 25,
          name: '护岸类型',
          type: 1,
          code: 'auxiliaryRevetmentType',
          value: ''
        },
        {
          id: 26,
          name: '引坡挡墙类型',
          type: 1,
          code: 'auxiliaryBarricadeType',
          value: ''
        }
      ], //上部结构and附属工程
      data3: [
        {
          id: 1,
          name: '形式',
          type: 1,
          code: 'pierForm',
          value: ''
        },
        {
          id: 2,
          name: '桥墩数量',
          type: 5,
          code: 'pierNum',
          value: ''
        },
        {
          id: 3,
          name: '桥墩标高',
          type: 4,
          code: 'pierElevation',
          value: '',
          append: 'm'
        },
        {
          id: 4,
          name: '盖梁尺寸',
          type: 1,
          code: 'pierCapSize',
          value: ''
        },
        {
          id: 5,
          name: '基底标高',
          type: 4,
          code: 'pierBaseElevation',
          value: '',
          append: 'm'
        },
        {
          id: 6,
          name: '底板尺寸',
          type: 1,
          code: 'pierFloorSize',
          value: ''
        },
        {
          id: 7,
          name: '基桩尺寸',
          type: 1,
          code: 'pierPileSize',
          value: ''
        },
        {
          id: 8,
          name: '基桩根数',
          type: 5,
          code: 'pierPileNum',
          value: ''
        },
        {
          id: 9,
          name: '形式',
          type: 1,
          code: 'abutmentForm',
          value: ''
        },
        {
          id: 10,
          name: '桥台数量',
          type: 5,
          code: 'abutmentNum',
          value: ''
        },
        {
          id: 11,
          name: '桥台标高',
          type: 4,
          code: 'abutmentElevation',
          value: '',
          append: 'm'
        },
        {
          id: 12,
          name: '基底标高 ',
          type: 4,
          code: 'abutmentBaseElevation',
          value: '',
          append: 'm'
        },
        {
          id: 13,
          name: '台帽尺寸',
          type: 1,
          code: 'abutmentCapSize',
          value: ''
        },
        {
          id: 14,
          name: '底板尺寸',
          type: 4,
          code: 'abutmentBaseboardSize',
          value: '',
          append: 'm'
        },
        {
          id: 15,
          name: '基桩尺寸',
          type: 1,
          code: 'abutmentPileSize',
          value: ''
        },
        {
          id: 16,
          name: '基桩根数',
          type: 5,
          code: 'abutmentPileNum',
          value: ''
        },
        {
          id: 17,
          name: '挡土板厚度',
          type: 4,
          code: 'abutmentRetainThick',
          value: '',
          append: 'm'
        },
        {
          id: 18,
          name: '翼墙形式',
          type: 1,
          code: 'abutmentWingWallForm',
          value: ''
        },
        {
          id: 19,
          name: '翼墙长度',
          type: 4,
          code: 'abutmentWingWallLength',
          value: '',
          append: 'm'
        },
        {
          id: 20,
          name: '给水管',
          type: 1,
          code: 'pipelineWater',
          value: ''
        },
        {
          id: 21,
          name: '燃气管',
          type: 1,
          code: 'pipelineGas',
          value: ''
        },
        {
          id: 22,
          name: '电力缆',
          type: 1,
          code: 'pipelineElectricity',
          value: ''
        },
        {
          id: 23,
          name: '通信电缆',
          type: 1,
          code: 'pipelineCable',
          value: ''
        },
        {
          id: 24,
          name: ' ',
          type: 1,
          code: '', //
          value: ''
        },
        {
          id: 25,
          name: ' ',
          type: 1,
          code: '', //
          value: ''
        },
        {
          id: 26,
          name: ' ',
          type: 1,
          code: '', //
          value: ''
        }
      ], //下部结构and附挂管线
      isDisabled: true, //修改判断
      isModify: true, //保存判断
      updateOpt: this.params().updateOpt,
      structureId: this.params().structureId,
      roleId: this.$store.getters.getRoleInfo.id,
      structureInfo: {},
      bridgeInfo: {}
    };
  },
  methods: {
    //获取桥梁详情
    async getBridgeDetailById() {
      let { code, data } = await getBridgeDetailById(this.structureId);
      if (code == '0000') {
        this.structureInfo = data;
        let bridgeInfo = { ...data.bridgeInfo, ...data };
        bridgeInfo.bridgeInfo = null;
        this.data1.map((item) => {
          for (let key in bridgeInfo) {
            if (key == item.code) {
              item.value = bridgeInfo[key];
            }
          }
        });
        this.data2.map((item) => {
          for (let key in bridgeInfo) {
            if (key == item.code) {
              item.value = bridgeInfo[key];
            }
          }
        });
        this.data3.map((item) => {
          for (let key in bridgeInfo) {
            if (key == item.code) {
              item.value = bridgeInfo[key];
            }
          }
        });
      }
    },
    // 修改
    getModify() {
      this.dataOri1 = JSON.parse(JSON.stringify(this.data1));
      this.dataOri2 = JSON.parse(JSON.stringify(this.data2));
      this.dataOri3 = JSON.parse(JSON.stringify(this.data3));
      this.isDisabled = this.isModify = false;
    },
    cancel() {
      this.data1 = this.dataOri1;
      this.data2 = this.dataOri2;
      this.data3 = this.dataOri3;
      this.isDisabled = this.isModify = true;
    },
    // 保存
    async getPreserve() {
      if (!this.confirm()) {
        return;
      }
      let infoObj = JSON.parse(JSON.stringify(this.structureInfo));
      this.data1.map((item) => {
        for (let key in infoObj) {
          if (key == item.code) {
            if ((item.type == 4 || item.type == 5) && !!item.value) {
              infoObj[key] = Number(item.value);
            } else {
              infoObj[key] = item.value;
            }
          }
        }
        for (let key in infoObj.bridgeInfo) {
          if (key == item.code) {
            if ((item.type == 4 || item.type == 5) && !!item.value) {
              infoObj.bridgeInfo[key] = Number(item.value);
            } else {
              infoObj.bridgeInfo[key] = item.value;
            }
          }
        }
      });
      this.data2.map((item) => {
        for (let key in infoObj.bridgeInfo) {
          if (key == item.code) {
            if ((item.type == 4 || item.type == 5) && !!item.value) {
              infoObj.bridgeInfo[key] = Number(item.value);
            } else {
              infoObj.bridgeInfo[key] = item.value;
            }
          }
        }
      });
      this.data3.map((item) => {
        for (let key in infoObj.bridgeInfo) {
          if (key == item.code) {
            if ((item.type == 4 || item.type == 5) && !!item.value) {
              infoObj.bridgeInfo[key] = Number(item.value);
            } else {
              infoObj.bridgeInfo[key] = item.value;
            }
          }
        }
      });

      let { code } = await updateBridgeDetail(infoObj);
      if (code == '0000') {
        this.$message({
          message: '保存成功！',
          type: 'success',
          showClose: true,
          duration: 2000
        });
        this.isDisabled = this.isModify = true;
        await this.getBridgeDetailById();
      }
    },
    //修改提交验证
    confirm() {
      let result = true;
      let regular = /^\d+(\.\d{1,2})?$/; //验证小数点后两位
      let regular2 = /^[1-9]\d*$/; //验证正整数
      this.data1.map((item) => {
        if (item.prop && (item.value == '' || item.value == null)) {
          this.$message({
            message: item.name + '不能为空！',
            type: 'warning',
            showClose: true,
            duration: 2000
          });
          result = false;
        }
        if (item.type == 4 && !!item.value) {
          if (!regular.test(item.value)) {
            this.$message({
              message: item.name + '为正数且保留两位小数！',
              type: 'warning',
              showClose: true,
              duration: 2000
            });
            result = false;
          }
        }
        if (item.type == 5 && !!item.value) {
          if (!regular2.test(item.value)) {
            this.$message({
              message: item.name + '要为正整数！',
              type: 'warning',
              showClose: true,
              duration: 2000
            });
            result = false;
          }
        }
      });
      this.data2.map((item) => {
        if (item.type == 4 && !!item.value) {
          if (!regular.test(item.value)) {
            this.$message({
              message: item.name + '为正数且保留两位小数！',
              type: 'warning',
              showClose: true,
              duration: 2000
            });
            result = false;
          }
        }
        if (item.type == 5 && !!item.value) {
          if (!regular2.test(item.value)) {
            this.$message({
              message: item.name + '要为正整数！',
              type: 'warning',
              showClose: true,
              duration: 2000
            });
            result = false;
          }
        }
      });
      this.data3.map((item) => {
        if (item.type == 4 && !!item.value) {
          if (!regular.test(item.value)) {
            this.$message({
              message: item.name + '为正数且保留两位小数！',
              type: 'warning',
              showClose: true,
              duration: 2000
            });
            result = false;
          }
        }
        if (item.type == 5 && !!item.value) {
          if (!regular2.test(item.value)) {
            this.$message({
              message: item.name + '要为正整数！',
              type: 'warning',
              showClose: true,
              duration: 2000
            });
            result = false;
          }
        }
      });
      return result;
    }
  },
  mounted() {
    this.getBridgeDetailById();
  }
};
</script>

<style lang="scss" scoped>
.CardInfoTab {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  .top {
    position: relative;
    height: 40px;
    .topTool {
      position: absolute;
      left: 0;
      z-index: 2;
      display: flex;
      align-items: center;
      /deep/ .el-button {
        margin: 0;
        margin-right: 10px;
      }
    }
    .title {
      width: 100%;
      height: 100%;
      display: flex;
      align-items: center;
      justify-content: center;
    }
  }
}
.tableBox {
  height: 93%;
  overflow-y: scroll;
  .tableRow {
    border-top: 1px solid rgba(217, 217, 217, 1);
    border-left: 1px solid rgba(217, 217, 217, 1);
  }
}
.head {
  text-align: center;
  height: 124.8vh;
  background-color: rgba(242, 244, 246, 1);
  font-weight: 700;
  div {
    width: 100%;
    font-size: 14px;
    font-weight: bold;
    color: #333;
    border-right: 1px solid rgba(217, 217, 217, 1);
    border-bottom: 1px solid rgba(217, 217, 217, 1);
    display: flex;
    align-items: center;
    justify-content: center;
  }
}

.content {
  display: flex;
  text-align: center;
  label {
    width: 140px;
    height: 4.8vh;
    line-height: 2.5;
    font-size: 14px;
    color: #333;
    // padding: 11px 0;
    border-right: 1px solid rgba(217, 217, 217, 1);
    border-bottom: 1px solid rgba(217, 217, 217, 1);
    display: flex;
    align-items: center;
    justify-content: center;
    span {
      color: #ff5f5f;
    }
  }
  div {
    width: 326px;
    border-right: 1px solid rgba(217, 217, 217, 1);
    border-bottom: 1px solid rgba(217, 217, 217, 1);
    // padding: 11px;
    input {
      color: #333;
      font-size: 14px;
      border: 0;
      width: 100%;
      height: 100%;
      text-align: center;
      background: #fff;
      overflow: hidden;
      text-overflow: ellipsis;
    }
  }
  .textInput {
    height: 4.8vh;
    width: 16.9792vw;
    border: 0;
  }
  .timeSel {
    height: 4.8vh;
    width: 326px;
  }
  .elInput {
    height: 100%;
    width: 100%;
  }
  .elSelect {
    height: 4.8vh;
    /deep/ .el-input {
      height: 100%;
    }
  }
  /deep/ .el-input-group__append {
    width: 40px;
    color: #999;
    background: transparent;
    border: 0;
    padding: 0;
    border-left: 1px solid rgba(217, 217, 217, 1);
  }
  /deep/ .el-input__inner {
    color: #333;
    font-size: 14px;
    height: 100%;
    border: 0;
    text-align: center;
  }
  /deep/ .el-input.is-disabled .el-input__inner {
    background: #fff;
    color: #333;
    cursor: unset;
  }
}
</style>
