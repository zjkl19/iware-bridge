<template>
  <div class="table">
    <el-table :data="tableData" :span-method="objectSpanMethod">
      <el-table-column
        v-for="item in tableTitleList"
        :key="item.id"
        :prop="item.prop"
        :label="item.label"
        :width="item.width"
        align="center"
      >
        <template slot-scope="scope">
          <el-input
            v-if="item.prop == 'actualWeight' && !disabled"
            v-model="scope.row.actualWeight"
            onkeyup="this.value = this.value.replace(/[^\d^\.]+/g, '').replace('.', '$#$').replace(/\./g, '').replace('$#$', '.');"
            @blur="setValue(scope.row.actualWeight, scope, 'actualWeight')"
            @change="
              valueConfirm(scope.row.actualWeight, scope, 'actualWeight')
            "
            :disabled="disabled"
          ></el-input>
          <el-input
            v-else-if="item.prop == 'deduct' && !disabled"
            v-model="scope.row.deduct"
            onkeyup="this.value = this.value.replace(/[^\d^\.]+/g, '').replace('.', '$#$').replace(/\./g, '').replace('$#$', '.');"
            @blur="setValue(scope.row.deduct, scope, 'deduct')"
            @change="valueConfirm(scope.row.deduct, scope, 'deduct')"
            :disabled="disabled"
          ></el-input>
          <div v-else>
            <span
              v-if="scope.row.serious == 1 && item.prop == 'deduct'"
              class="serious"
              >*</span
            >
            {{ scope.row[item.prop] !== null ? scope.row[item.prop] : '-' }}
          </div>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      //统计不同数量
      firstArr: [],
      secondArr: [],
      thirdArr: [],
      fourArr: [],
      //转换成适合合并的数组
      firstRst: [],
      secondRst: [],
      thirdRst: [],
      fourRst: [],
      oldVal: ''
    };
  },
  props: {
    tableData: {
      type: Array,
      default: () => []
    },
    tableTitleList: {
      type: Array,
      default: () => []
    },
    tableName: {
      type: String,
      default: ''
    },
    disabled: {
      type: Boolean,
      default: true
    }
  },
  methods: {
    // 合并单元格
    objectSpanMethod({ rowIndex, columnIndex, column }) {
      if (this.tableName == '桥面系') {
        if (columnIndex === 6 && column.property == 'valueY') {
          if (this.secondRst[rowIndex]) {
            return [this.secondRst[rowIndex], 1];
          } else {
            return [0, 0];
          }
        }
        if (columnIndex === 5 && column.property == 'valueX') {
          if (this.firstRst[rowIndex]) {
            return [this.firstRst[rowIndex], 1];
          } else {
            return [0, 0];
          }
        }
      } else {
        if (columnIndex === 6 && column.property == 'valueZ') {
          if (this.thirdRst[rowIndex]) {
            return [this.thirdRst[rowIndex], 1];
          } else {
            return [0, 0];
          }
        }
        if (columnIndex === 7 && column.property == 'valueX') {
          if (this.secondRst[rowIndex]) {
            return [this.secondRst[rowIndex], 1];
          } else {
            return [0, 0];
          }
        }
        if (columnIndex === 8 && column.property == 'valueY') {
          if (this.firstRst[rowIndex]) {
            return [this.firstRst[rowIndex], 1];
          } else {
            return [0, 0];
          }
        }
        if (columnIndex === 0 && column.property == 'spanCode') {
          if (this.fourRst[rowIndex]) {
            return [this.fourRst[rowIndex], 1];
          } else {
            return [0, 0];
          }
        }
      }
    },
    //获取适合合并表格的数组
    getResultArr(obj, rst) {
      let index = 0,
        sum = 0;
      for (let i = 0; i < this.tableData.length; i++) {
        if (sum == i) {
          sum += obj[index];
          rst.push(obj[index]);
          index++;
        } else {
          rst.push(0);
        }
      }
    },
    productArr(obj) {
      let firstIndex = 0,
        secondIndex = 0,
        thirdIndex = 0,
        fourIndex = 0;
      let firstTemp = obj[0].valueX,
        secondTemp = obj[0].valueY,
        thirdTemp = obj[0].valueZ,
        fourTemp = obj[0].spanCode;
      for (let i = 0; i < obj.length; i++) {
        //统计不同类型
        //统计跨号、墩台号
        if (fourTemp == obj[i].spanCode) {
          fourIndex++;
          if (obj.length == i + 1) {
            this.fourArr.push(fourIndex);
          }
          for (let j = 0; j < obj.length; j++) {
            //统计BCIxj、BCIsi
            if (thirdTemp == obj[j].valueZ) {
              thirdIndex++;
              if (obj.length == j + 1) {
                this.thirdArr.push(thirdIndex);
              }
            } else {
              if (obj.length == j + 1) {
                this.thirdArr.push(thirdIndex);
                this.thirdArr.push(1);
              } else {
                this.thirdArr.push(thirdIndex);
                thirdTemp = obj[j].valueZ;
                thirdIndex = 1;
              }
            }
          }
        } else {
          if (obj.length == i + 1) {
            this.fourArr.push(fourIndex);
            this.fourArr.push(1);
          } else {
            this.fourArr.push(fourIndex);
            fourTemp = obj[i].spanCode;
            fourIndex = 1;
          }
        }

        //统计BCIx、BCIs
        if (firstTemp == obj[i].valueX) {
          firstIndex++;
          if (obj.length == i + 1) {
            this.firstArr.push(firstIndex);
          }
        } else {
          if (obj.length == i + 1) {
            this.firstArr.push(firstIndex);
            this.firstArr.push(1);
          } else {
            this.firstArr.push(firstIndex);
            firstTemp = obj[i].valueX;
            firstIndex = 1;
          }
        }

        //统计BSIx、BSIs
        if (secondTemp == obj[i].valueY) {
          secondIndex++;
          if (obj.length == i + 1) {
            this.secondArr.push(secondIndex);
          }
        } else {
          if (obj.length == i + 1) {
            this.secondArr.push(secondIndex);
            this.secondArr.push(1);
          } else {
            this.secondArr.push(secondIndex);
            secondTemp = obj[i].valueY;
            secondIndex = 1;
          }
        }
      }
    },
    //输入验证
    valueConfirm(val, scope, name) {
      //   let regular = /^[-+]?[0-9]+(\.[0-9]+)?$/
      if (val[0] === '.') {
        this.tableData[scope.$index][name] = Number('0' + val);
      }
      this.tableData[scope.$index][name] = Number(
        this.tableData[scope.$index][name]
      ).toFixed(2);
    },
    //确保输入的值没错
    setValue(val, scope, name) {
      if (!!val) {
        let value = val;
        if (this.tableData[scope.$index][name] !== value) {
          value = value
            .replace(/[^\d^\.]+/g, '')
            .replace('.', '$#$')
            .replace(/\./g, '')
            .replace('$#$', '.');
        }
        if (value > 100) {
          value = 100;
        }
        this.tableData[scope.$index][name] = Number(value);
      }
    },
    tableInit() {
      if (this.tableData.length > 0) {
        this.productArr(this.tableData);
        this.getResultArr(this.firstArr, this.firstRst);
        this.getResultArr(this.secondArr, this.secondRst);
        this.getResultArr(this.thirdArr, this.thirdRst);
        this.getResultArr(this.fourArr, this.fourRst);
      }
    }
  },
  mounted() {
    this.tableInit();
  }
};
</script>

<style lang="scss" scoped>
.table {
  height: 100%;
  width: 100%;
  .serious {
    color: #f5222d;
  }
  /deep/ .el-table {
    border-top: 1px solid #ebeef5;
    border-left: 1px solid #ebeef5;
  }
  /deep/ .el-table th {
    font-size: 0.7vw;
    color: #333;
    background: #f2f4f6;
    border-right: 1px solid #ebeef5;
  }
  /deep/ .el-table td {
    height: 4.45vh;
    font-size: 0.7vw;
    color: #333;
    padding: 0;
    background: transparent;
    border-right: 1px solid #ebeef5;
  }
  /deep/ .el-table__empty-block {
    border-right: 1px solid #ebeef5;
    border-bottom: 1px solid #ebeef5;
  }
  /deep/ .el-input__inner {
    font-size: 0.7vw;
    color: #333;
    text-align: center;
  }
  /deep/ .el-table--enable-row-hover .el-table__body tr:hover > td {
    background: #fff;
  }
}
</style>
