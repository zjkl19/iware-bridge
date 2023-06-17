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
        <div v-else style="display: flex">
          <el-button type="primary" @click="getPreserve"> 保存 </el-button>
          <el-button @click="cancel"> 取消 </el-button>
        </div>
        <!-- <el-button> 导出 </el-button> -->
      </el-col>
      <el-col class="title">
        <p style="padding: 0 40px">隧道名称：{{ structureInfo.name }}</p>
        <p>所在路名：{{ structureInfo.roadName }}</p>
      </el-col>
    </el-row>
    <!-- 表格数据 -->
    <div class="tableBox">
      <el-row class="tableRow">
        <el-col :span="8">
          <el-row style="display: flex; align-items: center">
            <el-col class="head" :span="8">
              <div style="height: 100%">一般资料</div>
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
              <div style="height: 25%">一般资料</div>
              <div style="height: 75%">详情资料</div>
            </el-col>
            <el-col :span="16">
              <div class="content" v-for="item in data2" :key="item.id">
                <label for="i">{{ item.name }}</label>
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
          <el-row style="display: flex; align-items: center">
            <el-col class="head" :span="8">
              <div style="height: 100%">详情资料</div>
            </el-col>
            <el-col :span="16">
              <div class="content" v-for="item in data3" :key="item.id">
                <label for="i">{{ item.name }}</label>
                <div class="textInput">
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
      </el-row>
    </div>
  </div>
</template>

<script>
import {
  getTunnelDetailById,
  updateTunnelDetail
} from '@/api/infomanage/tunnel';
export default {
  inject: ['params'],
  data() {
    return {
      data1: [
        {
          id: 1,
          name: '隧道名称',
          type: 1,
          code: 'name',
          value: '',
          prop: true
        },
        {
          id: 2,
          name: '相关责任人',
          type: 1,
          code: 'chargeName',
          value: ''
        },
        {
          id: 3,
          name: '隧道编号',
          type: 1,
          code: 'number',
          value: '',
          prop: true
        },
        {
          id: 4,
          name: '隧道代码',
          type: 1,
          code: 'code',
          value: '',
          prop: true
        },
        {
          id: 5,
          name: '设计荷载',
          type: 1,
          code: 'designLoad',
          value: '',
          prop: true
        },
        {
          id: 6,
          name: '所在路名',
          type: 1,
          code: 'roadName',
          value: ''
        },
        {
          id: 7,
          name: '建成年月',
          type: 3,
          code: 'buildTime',
          value: '',
          prop: true
        },
        {
          id: 8,
          name: '养护单位',
          type: 1,
          code: 'maintainDepartment',
          value: '',
          prop: true
        },
        {
          id: 9,
          name: '结构类型',
          type: 2,
          code: 'structureType',
          value: '',
          list: [
            { id: 1, label: '桥梁' },
            { id: 2, label: '隧道' }
          ]
        },
        {
          id: 10,
          name: '隧道等级',
          type: 2,
          code: 'spanType',
          value: '',
          list: [
            { id: '特长隧道', label: '特长隧道' },
            { id: '长隧道', label: '长隧道' },
            { id: '中隧道', label: '中隧道' },
            { id: '短隧道', label: '短隧道' }
          ],
          prop: true
        },
        {
          id: 11,
          name: '经度',
          type: 5,
          code: 'longitude',
          value: '',
          prop: true
        },
        {
          id: 12,
          name: '纬度',
          code: 'latitude',
          value: '',
          type: 5,
          prop: true
        }
      ], //一般资料
      data2: [
        {
          id: 1,
          name: '技术状况',
          type: 2,
          code: 'technology',
          value: '',
          list: [
            { id: 1, label: '公路桥隧技术状况' },
            { id: 2, label: '城市桥隧技术状况' }
          ],
          prop: true
        },
        {
          id: 2,
          name: '状况等级',
          type: 1,
          code: 'grade',
          value: '',
          list: [
            { id: 1, label: '1类' },
            { id: 2, label: '2类' },
            { id: 3, label: '3类' },
            { id: 4, label: '4类' },
            { id: 5, label: '5类' }
          ]
        },
        {
          id: 3,
          name: '所属业主',
          type: 1,
          code: 'userId',
          value: '',
          prop: true
        },
        {
          id: 4,
          name: '路线编号',
          type: 1,
          code: 'roadCode',
          value: ''
        },
        {
          id: 5,
          name: '路线等级',
          type: 1,
          code: 'roadGrade',
          value: ''
        },
        {
          id: 6,
          name: '中心桩号',
          type: 1,
          code: 'centerMileage',
          value: ''
        },
        {
          id: 7,
          name: '长度',
          type: 4,
          code: 'length',
          value: '',
          append: 'm'
        },
        {
          id: 8,
          name: '净宽',
          type: 4,
          code: 'width',
          value: '',
          append: 'm'
        },
        {
          id: 9,
          name: '路面宽度',
          type: 4,
          code: 'roadWidth',
          value: '',
          append: 'm'
        },
        {
          id: 10,
          name: '净高',
          type: 4,
          code: 'height',
          value: '',
          append: 'm'
        },
        {
          id: 11,
          name: '岩层地质',
          type: 1,
          code: 'stratumGeology',
          value: ''
        },
        {
          id: 12,
          name: '围岩分类',
          type: 1,
          code: 'wallRockTypes',
          value: ''
        }
      ], //上部结构and附属工程
      data3: [
        {
          id: 1,
          name: '衬砌类型',
          type: 1,
          code: 'liningType',
          value: ''
        },
        {
          id: 2,
          name: '衬砌厚度',
          type: 4,
          code: 'liningThick',
          value: '',
          append: 'm'
        },
        {
          id: 3,
          name: '洞门型式',
          type: 1,
          code: 'portalForm',
          value: ''
        },
        {
          id: 4,
          name: '路面类型',
          type: 1,
          code: 'roadType',
          value: ''
        },
        {
          id: 5,
          name: '照明设施',
          type: 1,
          code: 'lightingInstallation',
          value: ''
        },
        {
          id: 6,
          name: '通风设施',
          type: 1,
          code: 'airInstallation',
          value: ''
        },
        {
          id: 7,
          name: '消防与救援设施',
          type: 1,
          code: 'fireInstallation',
          value: ''
        },
        {
          id: 8,
          name: '监控设施',
          type: 1,
          code: 'watchInstallation',
          value: ''
        },
        {
          id: 9,
          name: '供配电设施',
          type: 1,
          code: 'electricInstallation',
          value: ''
        },
        {
          id: 10,
          name: '洞内纵坡',
          type: 1,
          code: 'tunnelLongitudinalSlope',
          value: ''
        },
        {
          id: 11,
          name: ' ',
          type: 1,
          code: '', //
          value: ''
        },
        {
          id: 12,
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
      tunnelInfo: {}
    };
  },
  methods: {
    //获取桥梁详情
    async getTunnelDetailById() {
      let { code, data } = await getTunnelDetailById(this.structureId);
      if (code == '0000') {
        this.structureInfo = data;
        let tunnelInfo = { ...data.tunnelInfo, ...data };
        tunnelInfo.tunnelInfo = null;
        this.data1.map((item) => {
          for (let key in tunnelInfo) {
            if (key == item.code) {
              item.value = tunnelInfo[key];
            }
          }
        });
        this.data2.map((item) => {
          for (let key in tunnelInfo) {
            if (key == item.code) {
              item.value = tunnelInfo[key];
            }
          }
        });
        this.data3.map((item) => {
          for (let key in tunnelInfo) {
            if (key == item.code) {
              item.value = tunnelInfo[key];
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
        for (let key in infoObj.tunnelInfo) {
          if (key == item.code) {
            if ((item.type == 4 || item.type == 5) && !!item.value) {
              infoObj.tunnelInfo[key] = Number(item.value);
            } else {
              infoObj.tunnelInfo[key] = item.value;
            }
          }
        }
      });
      this.data2.map((item) => {
        for (let key in infoObj.tunnelInfo) {
          if (key == item.code) {
            if ((item.type == 4 || item.type == 5) && !!item.value) {
              infoObj.tunnelInfo[key] = Number(item.value);
            } else {
              infoObj.tunnelInfo[key] = item.value;
            }
          }
        }
      });
      this.data3.map((item) => {
        for (let key in infoObj.tunnelInfo) {
          if (key == item.code) {
            if ((item.type == 4 || item.type == 5) && !!item.value) {
              infoObj.tunnelInfo[key] = Number(item.value);
            } else {
              infoObj.tunnelInfo[key] = item.value;
            }
          }
        }
      });

      let { code } = await updateTunnelDetail(infoObj);
      if (code == '0000') {
        this.$message({
          message: '保存成功！',
          type: 'success',
          showClose: true,
          duration: 2000
        });
        this.isDisabled = this.isModify = true;
        await this.getTunnelDetailById();
      }
    },
    //修改提交验证
    confirm() {
      let result = true;
      let regular = /^\d+(\.\d{1,2})?$/; //验证小数点后两位
      let regExp1 =
        /^-?((0|1?[0-7]?[0-9]?)(([.][0-9]{1,6})?)|180(([.][0]{1,6})?))$/; //验证经度
      let regExp2 =
        /^-?((0|[1-8]?[0-9]?)(([.][0-9]{1,6})?)|90(([.][0]{1,6})?))$/; //验证纬度
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
          if (item.name == '经度') {
            if (!regExp1.test(item.value)) {
              this.$message({
                message: '经度整数部分为-180~180,小数部分为0-6位！',
                type: 'warning',
                showClose: true,
                duration: 2000
              });
              result = false;
            }
          }
          if (item.name == '纬度') {
            if (!regExp2.test(item.value)) {
              this.$message({
                message: '纬度整数部分为-90~90,小数部分为0-6位！',
                type: 'warning',
                showClose: true,
                duration: 2000
              });
              result = false;
            }
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
      });
      return result;
    }
  },
  mounted() {
    this.getTunnelDetailById();
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
  .tableRow {
    height: 100%;
    border-top: 1px solid rgba(217, 217, 217, 1);
    border-left: 1px solid rgba(217, 217, 217, 1);
    /deep/ .el-col-8 {
      height: 100%;
      .el-row {
        height: 100%;
        .el-col-16 {
          height: 100%;
        }
      }
    }
  }
}
.head {
  text-align: center;
  height: 100%;
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
  height: 8.33%;
  display: flex;
  text-align: center;
  label {
    width: 140px;
    height: 100%;
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
  .timeSel {
    height: 100%;
    width: 326px !important;
  }
  .textInput {
    height: 100%;
    width: 16.9792vw;
    border: 0;
  }
  /deep/ .el-select {
    height: 100%;
  }
  /deep/ .el-input {
    height: 100%;
    width: 100%;
  }
  /deep/ .el-input-group__append {
    width: 40px;
    color: #333;
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
