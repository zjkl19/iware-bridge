<template>
  <div class="warningDetails animate__animated animate__fadeIn">
    <!-- 左侧 -->
    <div class="box-left">
      <div>传感器数据</div>
      <el-select
        class="leftSel"
        v-model="structureId"
        placeholder="请选择结构物"
        @change="getTree(activeType)"
      >
        <el-option
          v-for="item in structureList"
          :key="item.id"
          :label="item.name"
          :value="item.id"
        >
        </el-option>
      </el-select>
      <!-- 树形结构分类按钮 -->
      <div class="box-btn">
        <span :class="{ activeItem: activeType == 0 }" @click="getTree(0)"
          >按类型</span
        >
        <span :class="{ activeItem: activeType == 1 }" @click="getTree(1)"
          >按位置</span
        >
        <span :class="{ activeItem: activeType == 2 }" @click="getTree(2)"
          >全部</span
        >
      </div>
      <!-- 树 -->
      <div class="box-tree">
        <el-tree
          ref="treeData"
          :data="treeData"
          node-key="id"
          :props="defaultProps"
          :default-expanded-keys="defaultExpandedKeys"
          @check="handleCheckChange"
          show-checkbox
        >
          <div
            class="custom-tree-node"
            slot-scope="{ node, data }"
            :style="
              data.sensorTypeId && data.sensorVOList ? 'padding-left:0' : ''
            "
          >
            <span :class="{ textOF: data.sensorVOList }" :title="node.label">{{
              node.label
            }}</span>
            <div v-if="data.sensorTypeId && data.sensorVOList" class="treeItem">
              <span
                :class="
                  data.status == 0
                    ? 'outline'
                    : data.status == 1
                    ? 'success'
                    : 'danger'
                "
                >{{
                  data.status == 0 ? '离线' : data.status == 1 ? '在线' : '故障'
                }}</span
              >
              <span class="detail" @click.stop="showDetail(data)">详情</span>
            </div>
          </div>
        </el-tree>
      </div>
    </div>
    <!-- 右侧 -->
    <div class="box-right">
      <!-- 导航栏 -->
      <div class="box-menu">
        <el-menu
          :default-active="activeIndex"
          mode="horizontal"
          @select="handleSelect"
        >
          <el-menu-item index="realTime">实时值</el-menu-item>
          <el-menu-item index="history"> 历史值 </el-menu-item>
          <!-- <el-menu-item index="boxLineDiagram">箱线图分析</el-menu-item> -->
          <el-menu-item index="dataList">数据列表</el-menu-item>
        </el-menu>
        <el-button
          v-if="activeIndex == 'dataList'"
          type="primary"
          class="exportBtn"
          @click="exportExcell"
        >
          数据导出
        </el-button>
      </div>
      <!-- 实时值 -->
      <div class="menuItem" v-if="activeIndex == 'realTime'">
        <!-- 实时图表 -->
        <real-time :sensorList="sensorCheckList" :carList="carList"></real-time>
      </div>
      <!-- 历史值 -->
      <div class="menuItem" v-else-if="activeIndex == 'history'">
        <!-- 选择时间 -->
        <history :sensorList="sensorCheckList"></history>
      </div>
      <!-- 箱线图分析 -->
      <!-- <div class="menuItem" v-else-if="activeIndex == 'boxLineDiagram'">
        <box-line-diagram :sensorList="sensorCheckList"></box-line-diagram>
      </div> -->
      <!-- 数据列表 -->
      <div class="menuItem" v-else>
        <data-list
          ref="sensorDataList"
          :sensorList="sensorCheckList"
        ></data-list>
      </div>
    </div>

    <!-- 传感器详情弹框 -->
    <el-dialog
      class="sensorDialog"
      title="传感器详情"
      :visible.sync="showSensorDialog"
      :modal-append-to-body="false"
      :close-on-click-modal="false"
    >
      <el-form class="sensorFormBox" :model="sensorForm">
        <el-form-item class="point" label="测点信息">
          <el-form class="pointBox" :model="sensorForm" disabled>
            <el-form-item
              v-for="item in pointList"
              :key="item.id"
              :label="item.lebel"
            >
              <el-input
                class="formInput"
                v-model="sensorForm[item.prop]"
                placeholder="/"
                clearable
              ></el-input>
            </el-form-item>
            <el-form-item label="XYZ坐标：" required>
              <div class="position">
                <el-input
                  class="poInput"
                  v-model="sensorForm.xAxis"
                  placeholder="/"
                  clearable
                ></el-input>
                <el-input
                  class="poInput"
                  v-model="sensorForm.yAxis"
                  placeholder="/"
                  clearable
                ></el-input>
                <el-input
                  class="poInput"
                  v-model="sensorForm.zAxis"
                  placeholder="/"
                  clearable
                ></el-input>
              </div>
            </el-form-item>
          </el-form>
        </el-form-item>

        <el-form-item class="sensor" label="传感器信息">
          <el-form class="sensorBox" :model="sensorForm" disabled>
            <el-form-item
              v-for="item in sensorList"
              :key="item.id"
              :label="item.lebel"
            >
              <el-input
                class="formInput"
                v-model="sensorForm[item.prop]"
                placeholder="/"
                clearable
                ><template v-if="item.id == 4" slot="append"
                  >Hz</template
                ></el-input
              >
            </el-form-item>
            <el-form-item label="是否故障：">
              <el-switch
                class="formInput"
                v-model="sensorForm.status"
                :active-value="2"
                :inactive-value="sensorForm.statusValue"
                active-color="#419aff"
                active-text="是"
              >
              </el-switch>
            </el-form-item>
            <el-form-item
              v-if="
                sensorForm.sensorTypeId == 1 || sensorForm.sensorTypeId == 3
              "
              class="slFormItem"
              label="参考基频："
            >
              <el-input
                class="formInput slItem1"
                v-model="sensorForm.referenceFrequency"
                placeholder="请输入"
                ><template slot="append">Hz</template></el-input
              >
              <div style="padding: 0 5px">±</div>
              <el-input
                class="formInput slItem1"
                v-model="sensorForm.floatRange"
                placeholder="请输入"
                ><template slot="append">%</template></el-input
              >
            </el-form-item>
            <el-form-item v-if="sensorForm.sensorTypeId == 3" label="索长L：">
              <el-input
                class="formInput"
                v-model="sensorForm.cableLength"
                placeholder="请输入"
                ><template slot="append">m</template></el-input
              >
            </el-form-item>
            <el-form-item
              v-if="sensorForm.sensorTypeId == 3"
              label="单位索长："
            >
              <el-input
                class="formInput"
                type="number"
                v-model="sensorForm.unitCableLength"
                placeholder="请输入"
                ><template slot="append">kg/m</template></el-input
              >
            </el-form-item>
            <el-form-item label="传感器照片：" style="width: 100%">
              <div class="upload">
                <div v-if="filePath == ''">暂无图片</div>
                <el-image v-else :src="filePath" :preview-src-list="[filePath]">
                </el-image>
              </div>
            </el-form-item>
          </el-form>
        </el-form-item>

        <el-form-item class="warnSerTable" label="预警信息">
          <el-table :data="warnData">
            <el-table-column
              v-for="item in warnTitleList"
              :key="item.id"
              :prop="item.prop"
              :label="item.label"
              :width="item.width"
              align="center"
            >
              <template slot-scope="scope">
                <div class="rowBox">{{ scope.row[item.prop] || '/' }}</div>
              </template>
            </el-table-column>
            <el-table-column
              v-for="item in warnLevelList"
              :key="item.id"
              :prop="item.prop"
              :label="item.label"
              align="center"
            >
              <el-table-column
                v-for="child in item.list"
                :key="child.id"
                :prop="child.prop"
                :label="child.label"
                width="90"
                align="center"
              >
                <template slot-scope="scope">
                  <div class="rowBox">{{ scope.row[child.prop] || '/' }}</div>
                </template>
              </el-table-column>
            </el-table-column>
          </el-table>
        </el-form-item>
      </el-form>
    </el-dialog>
    <!-- 查看图片弹框 -->
    <el-dialog
      class="dialogImage"
      title="传感器图片"
      :visible.sync="dialogImgShow"
      :modal-append-to-body="false"
      :close-on-click-modal="false"
    >
      <div style="max-height: 35vw; overflow: auto">
        <img :src="dialogImageUrl" alt="" />
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getListByType, getAxleType } from '@/api/online/warnDetails';
import { getStructureListByModel } from '@/api/common';
import realTime from './child/realTime';
import history from './child/history';
// import boxLineDiagram from './child/boxLineDiagram';
import dataList from './child/dataList';
export default {
  components: {
    realTime,
    history,
    // boxLineDiagram,
    dataList
  },
  data() {
    return {
      structureList: [],
      structureId: '',
      carList: [], //车型列表
      activeIndex: 'realTime', //导航栏选项
      activeType: 0,
      treeData: [], //树形结构数据
      defaultProps: {
        children: 'sensorVOList',
        label: 'name'
      }, //树形结构配置
      defaultExpandedKeys: [],
      sensorCheckList: [],
      //传感器详细
      showSensorDialog: false,
      sensorForm: {},
      pointList: [
        //type: 1、输入框 2、下拉框 3、多个下拉框
        {
          id: 1,
          lebel: '结构物：',
          prop: 'structureName',
          type: 2,
          list: []
        },
        { id: 2, lebel: '测点编号：', prop: 'name', type: 1 },
        { id: 3, lebel: '测点编号说明：', prop: 'describe', type: 1 },
        { id: 4, lebel: '部署日期：', prop: 'deploymentDate', type: 3 },
        {
          id: 5,
          lebel: '监测位置：',
          prop: 'componentName',
          type: 2,
          list: []
        },
        { id: 6, lebel: '截面位置：', prop: 'sectionPosition', type: 1 },
        { id: 7, lebel: '采集器信息：', prop: 'collectorInformation', type: 1 }
        // { id: 9, lebel: 'XYZ坐标：', prop: 'value9', type: 3 }
      ],
      sensorList: [
        //type: 1、输入框 2、下拉框 3、数字输入框
        {
          id: 1,
          lebel: '传感器厂商：',
          prop: 'companyName',
          type: 2,
          list: []
        },
        {
          id: 2,
          lebel: '传感器类型：',
          prop: 'sensorTypeName',
          type: 2,
          list: []
        },
        { id: 4, lebel: '采样频率：', prop: 'samplingFrequency', type: 1 },
        {
          id: 5,
          lebel: '出厂日期：',
          prop: 'productionDate',
          type: 4
        },
        { id: 6, lebel: '出厂编号：', prop: 'productionCoding', type: 1 }
      ],
      warnData: [],
      warnTitleList: [
        { id: 1, label: '细项', prop: 'name' },
        { id: 2, label: '单位', prop: 'unit' },
        { id: 3, label: '默认精度', prop: 'precision' },
        { id: 4, label: '初始值', prop: 'initialValue' },
        {
          id: 5,
          label: '预警间隔',
          prop: 'warningInterval'
        },
        { id: 6, label: '数据读取编码', prop: 'sensorCoding' },
        { id: 7, label: '传感器原理', prop: 'sensorPrincipleName' }
      ],
      warnLevelList: [
        {
          id: 16,
          label: '一级预警',
          list: [
            { id: 1, label: '上限', prop: 'firstWarningUpper' },
            { id: 2, label: '下限', prop: 'firstWarningLower' }
          ]
        },
        {
          id: 17,
          label: '二级预警',
          list: [
            { id: 1, label: '上限', prop: 'secondWarningUpper' },
            { id: 2, label: '下限', prop: 'secondWarningLower' }
          ]
        },
        {
          id: 18,
          label: '三级预警',
          list: [
            { id: 1, label: '上限', prop: 'thirdWarningUpper' },
            { id: 2, label: '下限', prop: 'thirdWarningLower' }
          ]
        }
      ],
      filePath: '',
      //查看图片弹框
      dialogImageUrl: '',
      dialogImgShow: false
    };
  },
  beforeMount() {
    this.$utils.getAuthPage(this); //获取权限
  },
  mounted() {
    let params = this.$route.params;
    if (params.id) {
      this.structureId = params.id;
    }
    this.getStructureListByModel(); //获取结构物
    this.getAxleType(); //获取车型列表
  },
  beforeRouteLeave(to, from, next) {
    if (to.name === '结构物监测') {
      sessionStorage.setItem('structureId', this.structureId);
    } else {
      if (sessionStorage.getItem('structureId')) {
        sessionStorage.removeItem('structureId');
      }
    }
    next();
  },
  beforeRouteEnter(to, from, next) {
    if (from.name === '结构物监测') {
      let structureId = sessionStorage.getItem('structureId');
      next((vm) => {
        if (structureId) {
          console.log(structureId);
          vm.structureId = Number(structureId);
        }
      });
    } else {
      next();
    }
  },
  methods: {
    //获取结构物
    async getStructureListByModel() {
      let params = { powerId: this.$store.getters.getActiveIndex };
      let { code, data } = await getStructureListByModel(params);
      if (code == '0000') {
        this.structureList = data;
        if (this.structureId == '') {
          this.structureId = data[0] ? data[0].id : '';
        }
        this.$nextTick(() => {
          this.getTree(this.activeType);
        });
      }
    },
    //获取车型列表
    async getAxleType() {
      let { code, data } = await getAxleType();
      if (code == '0000') {
        this.carList = data;
      }
    },
    // 获取树形结构数据
    async getTree(index) {
      this.activeType = index;
      let { code, data } = await getListByType(this.structureId, index);
      if (code == '0000') {
        // let dataList = this.getDisabled(data);
        let arry = [];
        data.map((item, index) => {
          if (item.id == null) {
            item.id = index + 1;
          }
          arry.push(item.id);
        });
        this.treeData = data;
        this.defaultExpandedKeys = arry;
        if (this.sensorCheckList.length > 0) {
          this.sensorCheckList = [];
        }
        this.$nextTick(() => {
          let arry = this.$refs.treeData.getCheckedKeys();
          if (arry.length > 0) {
            this.$refs.treeData.setCheckedKeys([]);
          }
        });
      }
    },
    //查看传感器详情
    async showDetail(data) {
      // console.log(data);
      data.deploymentDate = data.deploymentDate.split(' ')[0];
      data.productionDate = data.productionDate.split(' ')[0];
      this.sensorForm = { ...data };
      this.sensorForm.statusValue = data.status == 1 ? 1 : 0;
      this.filePath = !!data.photoUrl ? this.$basePath + data.photoUrl : '';
      this.warnData = data.sensorVOList;
      this.showSensorDialog = true;
    },
    //筛选基康传感器
    getDisabled(list) {
      list.map((item) => {
        if (item.sensorList && item.sensorList.length > 0) {
          let dataList = this.getDisabled(item.sensorList);
          return dataList;
        } else {
          if (item.companyId == 1 && item.sensorTypeId != 7) {
            item.disabled = true;
          }
        }
      });
      return list;
    },
    // 点击导航栏
    handleSelect(key, keyPath) {
      this.activeIndex = key;
      // this.sensorCheckList = [];
    },
    handleCheckChange(obj, list) {
      // console.log(obj, list);
      if (obj.sensorList) {
        let array = [];
        obj.sensorList.map((item) => {
          array.push(item.id);
        });
        this.$refs.treeData.setCheckedKeys(array.slice(0, 2));
      }
      let arry = this.$refs.treeData.getCheckedNodes(true);
      if (arry.length > 1) {
        let sensorTypeId = this.sensorCheckList[0].sensorTypeId;
        arry.map((item, i) => {
          if (item.sensorTypeId != sensorTypeId) {
            this.$message({
              message: '只能选择同类型的传感器！',
              type: 'warning',
              showClose: true,
              duration: 2000
            });
            arry.splice(i, 1);
          }
        });
      }
      if (arry.length > 2) {
        this.$message({
          message: '最多选择两个传感器！',
          type: 'warning',
          showClose: true,
          duration: 2000
        });
        arry.map((item, i) => {
          if (item.sensorTypeId == 1 || item.sensorTypeId == 3) {
            item.noFrequency = true;
          }
          if (item.id == obj.id) {
            arry.splice(i, 1);
          }
        });
      }
      let keys = [];
      arry.map((item) => {
        keys.push(item.id);
      });
      this.sensorCheckList = arry;
      this.$nextTick(() => {
        if (keys.length > 0) {
          this.$refs.treeData.setCheckedKeys(keys);
        }
      });
    },
    //数据导出
    exportExcell() {
      this.$refs.sensorDataList.exportExcell();
    },
    tabClick() {},
    //查看图片
    handleLook(file) {
      this.dialogImageUrl = file.url;
      this.dialogImgShow = true;
    }
  }
};
</script>

<style lang="scss" scoped>
.warningDetails {
  padding: 24px;
  display: flex;
  .box-left {
    height: 100%;
    width: 336px;
    padding-right: 8px;
    border-right: 1px solid #e8e8e8;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    .leftSel {
      height: 3.705vh;
      padding-right: 16px;
    }
    .box-btn {
      height: 2.964vh;
      display: flex;
      align-items: center;
      justify-content: center;
      span {
        height: 100%;
        width: 60px;
        font-size: 14px;
        color: #000;
        border: 1px solid #d9d9d9;
        border-left: 0;
        display: flex;
        align-items: center;
        justify-content: center;
        cursor: pointer;
        &:first-child {
          border-left: 1px solid #d9d9d9;
        }
      }
      .activeItem {
        color: #2f80ed;
        border: 1px solid #2f80ed !important;
      }
    }
    .box-tree {
      height: 65.927vh;
      padding-right: 10px;
      overflow: scroll;
      .custom-tree-node {
        width: 100%;
        display: flex;
        align-items: center;
        justify-content: space-between;
        .treeItem {
          font-size: 12px;
          display: flex;
          align-items: center;
          cursor: auto;
          .outline {
            width: 36px;
            height: 20px;
            color: #999;
            background: rgba(0, 0, 0, 0.1);
            border: 1px solid rgba(0, 0, 0, 0.25);
            border-radius: 2px;
            display: flex;
            align-items: center;
            justify-content: center;
          }
          .success {
            width: 36px;
            height: 20px;
            color: #2f80ed;
            background: rgba(24, 144, 255, 0.15);
            border: 1px solid #2f80ed;
            border-radius: 2px;
            display: flex;
            align-items: center;
            justify-content: center;
          }
          .danger {
            width: 36px;
            height: 20px;
            color: #eb5757;
            background: rgba(235, 87, 87, 0.15);
            border: 1px solid #eb5757;
            border-radius: 2px;
            display: flex;
            align-items: center;
            justify-content: center;
          }
        }
        .textOF {
          width: 180px;
          overflow: hidden;
          text-overflow: ellipsis;
        }
        .detail {
          padding: 0 10px;
          color: #419aff;
          cursor: pointer;
          opacity: 0;
        }
        &:hover {
          .detail {
            opacity: 1;
          }
        }
      }
      /deep/ .el-tree-node__content > label.el-checkbox {
        margin: 0;
      }
      /deep/ .el-tree-node__children .custom-tree-node {
        padding-left: 8px;
      }
      // 父节点不显示复选框
      /deep/.el-tree .el-tree-node .is-leaf + .el-checkbox .el-checkbox__inner {
        display: inline-block;
      }
      /deep/.el-tree .el-tree-node .el-checkbox .el-checkbox__inner {
        display: none;
      }

      &::-webkit-scrollbar {
        width: 6px;
      }
      &:hover {
        &::-webkit-scrollbar-thumb {
          background: #d7dae2;
          border-radius: 6px;
        }
      }
    }
  }
  .box-right {
    flex: 1;
    padding-left: 24px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    .box-menu {
      position: relative;
      .exportBtn {
        position: absolute;
        right: 0;
        bottom: 6px;
      }
      /deep/ .el-menu {
        border-bottom: solid 1px #e8e8e8;
      }
      /deep/ .el-menu-item {
        height: 3.705vh;
        line-height: unset;
        color: #262626;
        font-size: 16px;
        &:hover {
          color: #419aff;
        }
      }
      /deep/ .el-menu--horizontal > .el-menu-item.is-active {
        color: #419aff;
      }
    }
    .menuItem {
      // flex: 1;
      height: 74.075vh;
      width: 100%;
      // padding: 1.853vh 0 0;
      display: flex;
      flex-direction: column;
    }
  }
}
//传感器详情弹框
.sensorDialog {
  .point,
  .warn,
  .sensor {
    margin: 0;
    display: flex;
    flex-direction: column;
    /deep/ .el-form-item__label {
      color: #419aff;
      text-align: left;
    }
  }
  .pointBox,
  .warnBox,
  .sensorBox {
    padding: 0 20px;
    display: flex;
    flex-wrap: wrap;
    .position {
      width: 316px;
      display: flex;
      align-items: center;
      justify-content: space-between;
      /deep/ .el-input {
        width: 30%;
      }
      /deep/ .el-input__inner {
        width: 100%;
      }
    }
    .slFormItem {
      .slItem1 {
        width: 148px;
      }
      /deep/ .el-form-item__content {
        display: flex;
        align-items: center;
        justify-content: space-between;
      }
      /deep/ .el-input__inner {
        width: 100%;
      }
    }
    .upload {
      /deep/ img {
        max-height: 120px;
      }
      /deep/ .el-image__error {
        width: 106px;
        height: 60px;
      }
    }
    /deep/ .el-form-item {
      width: 33.33%;
      padding: 12px 0;
      display: flex;
      align-items: center;
    }
    /deep/ .el-form-item__label {
      width: 125px;
      color: #333;
      text-align: right;
    }
    /deep/ .el-input__inner {
      width: 100%;
    }
    /deep/ .el-form-item__content {
      display: flex;
    }
    /deep/ .el-input.is-disabled .el-input__inner {
      background: transparent;
      color: #333;
    }
  }

  .warnSerTable {
    padding: 10px 0;
    /deep/ .el-form-item__label {
      color: #419aff;
    }
    /deep/ .el-table__body-wrapper {
      max-height: 18.519vh;
      overflow-y: auto;
      &::-webkit-scrollbar {
        width: 6px;
      }
      &:hover {
        &::-webkit-scrollbar-thumb {
          background: #c4c4c4;
          border-radius: 6px;
        }
      }
    }
    /deep/.el-table th {
      font-size: 0.7vw;
      font-weight: bold;
      color: #333;
      background: transparent;
      line-height: unset;
      padding: 0;
      border: 0;
      border-bottom: 1px solid #ebeef5;
      border-right: 1px solid #ebeef5;
      .cell {
        padding: 0;
      }
    }
    /deep/.el-table__empty-block {
      // border-top: 1px solid #ebeef5;
      min-height: 3.705vh;
      .el-table__empty-text {
        line-height: 3.705vh;
      }
    }
    /deep/.el-table td {
      height: 3.705vh;
      font-size: 0.7vw;
      color: #333;
      // padding: 1.067vh 0;
      padding: 0;
      border: 0;
      border-bottom: 1px solid #ebeef5;
      border-right: 1px solid #ebeef5;
    }
    /deep/.el-input.is-disabled .el-input__inner {
      color: #333;
      background: transparent;
    }
  }
  .btn {
    padding-top: 30px;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  /deep/ .el-dialog {
    width: 1442px;
  }
  /deep/ .el-dialog__body {
    padding: 20px;
    padding-top: 0;
    display: flex;
    flex-direction: column;
  }
}
.sensorDialog {
  .point,
  .warn,
  .sensor {
    margin: 0;
    display: flex;
    flex-direction: column;
    /deep/ .el-form-item__label {
      color: #419aff;
      text-align: left;
      line-height: 20px;
    }
  }
  .warn {
    /deep/ .el-table__body-wrapper {
      max-height: 18.519vh;
      overflow-y: auto;
      &::-webkit-scrollbar {
        width: 6px;
      }
      &:hover {
        &::-webkit-scrollbar-thumb {
          background: #c4c4c4;
          border-radius: 6px;
        }
      }
    }
  }
  .pointBox,
  .warnBox,
  .sensorBox {
    padding: 0 20px;
    display: flex;
    flex-wrap: wrap;
    .position {
      width: 316px;
      display: flex;
      align-items: center;
      justify-content: space-between;
      /deep/ .el-input {
        width: 30%;
      }
      /deep/ .el-input__inner {
        width: 100%;
      }
    }
    .slFormItem {
      /deep/ .el-form-item__content {
        display: flex;
        justify-content: space-between;
      }
    }
    /deep/ .el-form-item {
      width: 33.33%;
      padding: 1.112vh 0;
      display: flex;
      align-items: center;
    }
    /deep/ .el-form-item__label {
      width: 125px;
      color: #333;
      text-align: right;
    }
    /deep/ .el-input-group__append {
      padding: 0 10px;
      color: #000;
      background: transparent;
    }
  }
  .formTime,
  .formInput {
    width: 316px;
    height: 3.705vh;
    /deep/ .el-input {
      height: 100%;
      display: flex;
      align-items: center;
    }
    /deep/ .el-input__inner {
      height: 100%;
      line-height: unset;
    }
    /deep/ .el-input__suffix {
      display: flex;
      align-items: center;
    }
  }
  .slItem1 {
    width: 148px;
  }
  .poInput {
    height: 3.705vh;
    /deep/ .el-input__inner {
      height: 100%;
      line-height: unset;
    }
  }
  .btn {
    padding-top: 10px;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  /deep/ .el-dialog {
    width: 1442px;
  }
  /deep/ .el-dialog__body {
    padding: 20px;
    padding-top: 0;
    display: flex;
    flex-direction: column;
  }
}
//查看图片弹框
.dialogImage {
  img {
    height: 100%;
    width: 100%;
  }
  /deep/ .el-dialog {
    margin: 0;
  }
  /deep/ .el-dialog__body {
    padding: 1.2vw !important;
  }
}
</style>
