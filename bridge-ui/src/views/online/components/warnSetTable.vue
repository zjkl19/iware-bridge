<template>
  <div class="warnSerTable">
    <el-table :data="tableData">
      <!-- <el-table-column type="index" label="序号" width="80" align="center">
        <template slot-scope="scope">
          <div class="rowBox rowIndex">{{ scope.$index + 1 }}</div>
        </template>
      </el-table-column> -->
      <el-table-column
        v-for="item in titleList"
        :key="item.id"
        :prop="item.prop"
        :label="item.label"
        :width="item.width"
        align="center"
      >
        <template slot="header">
          <div class="headItem">
            <span v-if="!item.disabled" class="require">*</span>
            <span>{{ item.label }}</span>
          </div>
        </template>
        <template slot-scope="scope">
          <div class="rowBox">
            <el-input
              v-if="item.type == 1"
              class="rowItem rowItemInput"
              v-model="scope.row[item.prop]"
              placeholder="请填写"
              :disabled="item.disabled"
            ></el-input>
            <el-input
              v-else-if="item.type == 2"
              class="rowItem rowItemInput"
              v-model="scope.row[item.prop]"
              placeholder="请填写"
              :maxlength="item.maxlangth"
              @blur="valueToNumber(1, scope.row, item.prop, item)"
            ></el-input>
            <el-select
              v-else-if="item.type == 3"
              class="rowItem"
              v-model="scope.row[item.prop]"
              placeholder="请选择"
            >
              <el-option
                v-for="child in sensorPrincipleList"
                :key="child.id"
                :label="child.label"
                :value="child.id"
              >
              </el-option>
            </el-select>
          </div>
        </template>
      </el-table-column>
      <!-- 预警等级列表 -->
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
            <div class="rowBox">
              <el-input
                class="rowItem rowItemInput"
                v-model="scope.row[child.prop]"
                placeholder="请填写"
                maxlength="8"
                @blur="valueToNumber(2, scope.row, child.prop, item, child)"
              ></el-input>
            </div>
          </template>
        </el-table-column>
      </el-table-column>
      <el-table-column label="操作" width="80" align="center">
        <template slot-scope="scope">
          <div class="opItem">
            <div @click="removeItem(scope.row, scope.$index)">移除</div>
          </div>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  name: 'warnSerTable-dzl',
  components: {},
  props: {
    //数据
    tableData: {
      type: Array,
      default: () => []
    },
    //表头数组
    titleList: {
      type: Array,
      default: () => []
    },
    //传感器原理列表
    sensorPrincipleList: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
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
      ]
    };
  },
  methods: {
    //移除细项
    removeItem(item, index) {
      this.$confirm(`确定移除${item.name}细项?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        showClose: true
      })
        .then(() => {
          this.tableData.splice(index, 1);
          this.$emit('removeSensorDetail', item.sensorDetailsId);
        })
        .catch(() => {
          return false;
        });
    },
    //转化成数字型
    valueToNumber(index, data, prop, item, child) {
      let regular = /^[-+]?[0-9]+(\.[0-9]+)?$/;
      if (data[prop] != '') {
        if (!regular.test(data[prop])) {
          let msg = '';
          if (index == 1) {
            msg = `${data.name}的${item.label}格式不正确！`;
          } else {
            msg = `${data.name}的${item.label}${child.label}格式不正确！`;
          }
          this.$message({
            message: msg,
            type: 'warning',
            showClose: true,
            duration: 2000
          });
          data[prop] = '';
          data['submit'] = false;
        } else {
          data[prop] = Number(data[prop]);
          data['submit'] = true;
        }
      }
    },
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex % 2 == 1) {
        return 'first-row';
      } else {
        return 'second-row';
      }
    }
  },
  mounted() {}
};
</script>
<style lang="scss" scoped>
.warnSerTable {
  padding: 10px 0;
  .headItem {
    display: flex;
    align-items: center;
    justify-content: center;
    .require {
      color: #f56c6c;
      margin-right: 5px;
    }
  }
  .rowIndex {
    width: 80px;
  }
  .rowBox {
    position: absolute;
    top: 0;
    left: 0;
    height: 3.705vh;
    padding: 5px;
    border-right: 1px solid #ebeef5;
    display: flex;
    align-items: center;
    justify-content: center;
    .rowItem {
      height: 100%;
      width: 100%;
      /deep/ .el-input {
        height: 100%;
        width: 100%;
      }
      /deep/ .el-input__inner {
        height: 100%;
        width: 100%;
        line-height: unset;
        border: 0;
        text-align: center;
        overflow: hidden;
        text-overflow: ellipsis;
      }
      /deep/ .el-input__suffix {
        display: flex;
        align-items: center;
      }
    }
    .rowItemInput {
      /deep/.el-input__inner {
        padding: 0;
      }
    }
  }

  // 通用操作
  .opItem {
    color: #419aff;
    font-size: 0.7vw;
    display: flex;
    align-items: center;
    justify-content: space-evenly;
    .disabled {
      opacity: 0.5;
      cursor: not-allowed;
    }
    .txtHide {
      opacity: 0;
      cursor: default;
    }
    .noshow {
      display: none;
    }
    div {
      cursor: pointer;
    }
  }

  /deep/ .el-table th {
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
  /deep/ .el-table__empty-block {
    // border-top: 1px solid #ebeef5;
    min-height: 3.705vh;
    .el-table__empty-text {
      line-height: 3.705vh;
    }
  }
  /deep/ .el-table td {
    height: 3.705vh;
    font-size: 0.7vw;
    color: #333;
    // padding: 1.067vh 0;
    padding: 0;
    border: 0;
    border-bottom: 1px solid #ebeef5;
  }
  /deep/ .el-input.is-disabled .el-input__inner {
    color: #333;
    background: transparent;
  }

  //斑马纹样式
  /deep/ .first-row {
    background: #ffffff;
  }
  /deep/ .second-row {
    background: #f2f4f6;
  }
}
</style>
