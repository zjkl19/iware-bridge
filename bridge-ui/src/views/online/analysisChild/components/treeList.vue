<template>
  <div class="treeList">
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

            <el-form-item
              v-if="
                sensorForm.sensorTypeId == 1 || sensorForm.sensorTypeId == 3
              "
              class="slFormItem"
              label="参考基频："
              disabled
            >
              <el-input
                class="formInput slItem1"
                v-model="sensorForm.referenceFrequency"
                placeholder="/"
                maxlength="30"
                clearable
                ><template slot="append">Hz</template></el-input
              >
              <div style="padding: 0 5px">±</div>
              <el-input
                class="formInput slItem1"
                v-model="sensorForm.floatRange"
                placeholder="/"
                maxlength="30"
                clearable
                ><template slot="append">%</template></el-input
              >
            </el-form-item>
            <el-form-item
              v-if="sensorForm.sensorTypeId == 3"
              label="索长L："
              disabled
            >
              <el-input
                class="formInput"
                v-model="sensorForm.cableLength"
                placeholder="/"
                maxlength="30"
                clearable
                ><template slot="append">m</template></el-input
              >
            </el-form-item>
            <el-form-item
              v-if="sensorForm.sensorTypeId == 3"
              label="单位索长："
              disabled
            >
              <el-input
                class="formInput"
                v-model="sensorForm.unitCableLength"
                placeholder="/"
                maxlength="30"
                clearable
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
  </div>
</template>

<script>
import { getListByType } from '@/api/online/warnDetails';
export default {
  name: 'treeList-dzl',
  components: {},
  props: ['structureId', 'maxLength', 'all'],
  data() {
    return {
      treeData: [],
      defaultProps: {
        //树形结构配置
        children: 'sensorVOList',
        label: 'name'
      },
      sensorCheckList: [],
      defaultExpandedKeys: [],
      activeType: 0,
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
      filePath: ''
    };
  },
  methods: {
    // 获取树形结构数据
    async getTree(index, id) {
      let structureId = id || this.structureId;
      this.activeType = index;
      let { code, data } = await getListByType(structureId, index, this.all);
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
    handleCheckChange(obj) {
      // console.log(obj, list);
      let limit = false;
      if (obj.sensorList) {
        let array = [];
        obj.sensorList.map((item) => {
          array.push(item.id);
        });
        this.$refs.treeData.setCheckedKeys(array.slice(0, 2));
      }
      let arry = this.$refs.treeData.getCheckedNodes(true);
      if (this.maxLength > 0 && arry.length > this.maxLength) {
        limit = true;
        this.$message({
          message: `最多选择${this.maxLength}个传感器！`,
          type: 'warning',
          showClose: true,
          duration: 2000
        });
        arry.map((item, i) => {
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
        if (keys.length > 0) this.$refs.treeData.setCheckedKeys(keys);
        if (!limit) this.$emit('treeChange', arry);
      });
    },
    //查看传感器详情
    async showDetail(data) {
      console.log(data);
      data.deploymentDate = data.deploymentDate.split(' ')[0];
      data.productionDate = data.productionDate.split(' ')[0];
      this.sensorForm = { ...data };
      this.filePath = !!data.photoUrl ? this.$basePath + data.photoUrl : '';
      this.warnData = data.sensorVOList;
      this.showSensorDialog = true;
    }
  },
  mounted() {
    this.getTree(0);
  }
};
</script>
<style lang="scss" scoped>
.treeList {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  .box-btn {
    height: 2.964vh;
    margin-bottom: 1.855vh;
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
    overflow: overlay;
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
      /deep/ .el-form-item__content {
        display: flex;
        justify-content: space-between;
      }
    }
    .upload {
      display: flex;
      align-items: center;
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
    /deep/ .el-input.is-disabled .el-input__inner {
      background: transparent;
      color: #333;
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
</style>
