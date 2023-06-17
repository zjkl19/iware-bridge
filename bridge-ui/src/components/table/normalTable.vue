<template>
  <div class="normalTable">
    <el-table :data="tableData" :row-class-name="tableRowClassName">
      <el-table-column type="index" label="序号" width="100" align="center">
        <template slot-scope="scope">
          {{ (pageNum - 1) * 10 + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column
        v-for="item in titleList"
        :key="item.id"
        :prop="item.prop"
        :label="item.label"
        :width="item.width"
        :align="align"
        show-overflow-tooltip
      ></el-table-column>
      <!-- 预警管理 -->
      <el-table-column
        v-if="tableName == '预警管理'"
        label="预警等级"
        align="center"
      >
        <template slot-scope="scope">
          <div
            :class="
              scope.row.level == 1
                ? 'red'
                : scope.row.level == 2
                ? 'yellow'
                : scope.row.level == 3
                ? 'blue'
                : ''
            "
          >
            {{
              scope.row.level == 1
                ? '一级预警'
                : scope.row.level == 2
                ? '二级预警'
                : scope.row.level == 3
                ? '三级预警'
                : '-'
            }}
          </div>
        </template>
      </el-table-column>
      <el-table-column
        v-if="tableName == '预警管理'"
        label="处理状态"
        align="center"
      >
        <template slot-scope="scope">
          <div v-if="scope.row.status == 0" class="danger">未处理</div>
          <div v-else-if="scope.row.status == 1" class="success">已处理</div>
        </template>
      </el-table-column>
      <!-- 传感器设置 -->
      <el-table-column
        v-if="tableName == '传感器设置'"
        label="传感器状态"
        align="center"
      >
        <template slot-scope="scope">
          <div v-if="scope.row.status == 0" class="outline">离线</div>
          <div v-else-if="scope.row.status == 1" class="success">正常</div>
          <div v-else class="danger">故障</div>
        </template>
      </el-table-column>
      <!-- 检测计划 -->
      <el-table-column
        v-if="tableName == '检测计划'"
        label="执行状态"
        align="center"
      >
        <template slot-scope="scope">
          <div v-if="scope.row.status == 1" class="danger">未执行</div>
          <div v-else-if="scope.row.status == 2" class="working">执行中</div>
          <div v-else-if="scope.row.status == 3" class="success">已完成</div>
          <div v-else class="danger">已超时</div>
        </template>
      </el-table-column>
      <!-- 用户管理 -->
      <el-table-column
        v-if="tableName == '用户管理' && opList[0].opShow"
        label="用户状态"
        align="center"
      >
        <template slot-scope="scope">
          <el-switch
            class="statusChange"
            :value="scope.row.status"
            :active-text="scope.row.status == 0 ? '禁用' : '启用'"
            :active-value="1"
            :inactive-value="0"
            :disabled="scope.row.updateOpt"
            @change="switchChange(5, scope.row)"
          >
          </el-switch>
        </template>
      </el-table-column>
      <!-- 通用操作 -->
      <el-table-column
        v-if="showOpt"
        label="操作"
        :width="opWidth"
        align="center"
      >
        <template slot-scope="scope">
          <div class="opItem">
            <template v-for="item in opList">
              <div
                v-if="item.opShow"
                :key="item.id"
                :class="{
                  disabled:
                    scope.row[item.type] ||
                    (scope.row.opacityName &&
                      scope.row.opacityName.includes(item.name)),
                  txtHide:
                    scope.row.disName && scope.row.disName.includes(item.name),
                  noshow:
                    scope.row.hideName && scope.row.hideName.includes(item.name)
                }"
                @click="
                  tableClick(
                    item.id,
                    scope.row,
                    scope.row[item.type],
                    item.name
                  )
                "
              >
                <span>{{ item.name }}</span>
              </div>
            </template>
          </div>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script>
export default {
  name: 'normalTable',
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
    //哪个模块使用的表（模块名称）
    tableName: {
      type: String,
      default: ''
    },
    //页数
    pageNum: {
      type: Number,
      default: 1
    },
    //操作选项列表
    opList: {
      type: Array,
      default: () => []
    },
    //操作列宽度
    opWidth: {
      type: String,
      default: ''
    },
    //对齐方式
    align: {
      type: String,
      default: 'center'
    }
  },
  data() {
    return {
      detail: false,
      change: false,
      dataDelete: false,
      examination: false,
      offline: false,
      online: false,
      showOpt: true
    };
  },
  methods: {
    //开关切换
    switchChange(index, data) {
      this.$confirm('确定修改用户状态？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          data.status = data.status == 0 ? 1 : 0;
          this.$emit('tableClick', index, data);
        })
        .catch(() => {
          return;
        });
    },
    //操作
    tableClick(index, data, disabled, disName) {
      // console.log(index, data, disabled, disName);
      // index: 1是查看、2是修改、3是删除
      // data: 数据
      // disabled: 判断查看、修改、删除是否可点击
      // disName: 操作名称
      if (
        disabled ||
        (data.opacityName && data.opacityName == disName) ||
        (data.disName && disName == data.disName)
      ) {
        return;
      }
      this.$emit('tableClick', index, data);
    },
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex % 2 == 1) {
        return 'first-row';
      } else {
        return 'second-row';
      }
    },
    //是否显示操作栏
    checkOptList() {
      let result = false;
      this.opList.map((item) => {
        if (item.opShow) {
          result = true;
        }
      });
      return result;
    }
  },
  mounted() {
    if (!this.checkOptList() || this.opList.length == 0) {
      this.showOpt = false;
    }
  }
};
</script>
<style lang="scss" scoped>
.normalTable {
  // height: 95%;
  // 预警管理
  .red {
    color: #f5222d;
    font-weight: bold;
  }
  .orange {
    color: #fa5a16;
    font-weight: bold;
  }
  .yellow {
    color: #f39836;
    font-weight: bold;
  }
  .blue {
    color: #419aff;
    font-weight: bold;
  }
  .danger {
    width: 56px;
    height: 24px;
    // height: 2.225vh;
    font-size: 14px;
    color: #fff;
    background: #eb5757;
    border-radius: 2px;
    margin: 0 auto;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  .working {
    width: 56px;
    height: 24px;
    // height: 2.225vh;
    font-size: 14px;
    color: #fff;
    background: #419aff;
    border-radius: 2px;
    margin: 0 auto;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  .outline {
    width: 56px;
    height: 24px;
    // height: 2.225vh;
    font-size: 14px;
    color: #fff;
    background: grey;
    border-radius: 2px;
    margin: 0 auto;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  .success {
    width: 56px;
    height: 24px;
    // height: 2.225vh;
    font-size: 14px;
    color: #fff;
    background: #27ae60;
    border-radius: 2px;
    margin: 0 auto;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  .statusChange {
    /deep/ .el-switch__label.is-active {
      color: #333;
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

  //斑马纹样式
  /deep/ .first-row {
    background: #ffffff;
  }
  /deep/ .second-row {
    background: #f2f4f6;
  }

  /deep/ .el-table {
    height: 100%;
    &::after {
      background: #f2f4f6;
    }
  }
  /deep/ .el-table th {
    font-size: 0.7vw;
    font-weight: bold;
    color: #333;
    padding: 1.067vh 0;
    border: 0;
    // border-bottom: 1px solid #ebeef5;
  }
  /deep/ .el-table__body-wrapper {
    height: 92%;
    overflow-y: auto;
    &::-webkit-scrollbar {
      height: 0.4vw;
    }
  }
  /deep/ .el-table__empty-block {
    border-top: 1px solid #ebeef5;
  }
  /deep/ .el-table td {
    font-size: 0.7vw;
    color: #333;
    // padding: 1.067vh 0;
    padding: 1.76vh 0;
    border: 0;
    &:first-child {
      border-top-left-radius: 5px;
      border-bottom-left-radius: 5px;
    }
    &:last-child {
      border-top-right-radius: 5px;
      border-bottom-right-radius: 5px;
    }
  }
  /deep/ .el-table .cell.el-tooltip {
    width: unset !important;
    line-height: unset;
  }
  /deep/ .el-table--striped .el-table__body tr.el-table__row--striped td {
    background: #f2f4f6;
  }
}
</style>
