<template>
  <div class="bciSummary">
    <div class="bread">
      <span @click="backPage">BCI评价 / </span><span>病害汇总表</span>
    </div>
    <div class="contentBox">
      <div class="content">
        <div class="contentTop">
          <span class="back" @click="backPage">返回</span>
        </div>
        <div class="sumTable">
          <el-table :data="tableData" :span-method="objectSpanMethod">
            <el-table-column
              v-for="item in tableTitleList"
              :key="item.id"
              :prop="item.prop"
              :label="item.label"
              :min-width="item.minWidth"
              align="center"
            >
            </el-table-column>
          </el-table>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { getDiseaseSummaryList } from '@/api/assessment/bciEvaluation';
export default {
  props: {
    bridgeRoadId: Number
  },
  data() {
    return {
      //存储表格的合并数据
      maps: [],
      //统计不同数量
      firstArr: [],
      secondArr: [],
      //转换成适合合并的数组
      firstRst: [],
      secondRst: [],
      tableTitleList: [
        { id: 1, label: '部位', prop: 'value1', minWidth: '40%' },
        { id: 2, label: '部件', prop: 'value2', minWidth: '40%' },
        { id: 3, label: '病害类型', prop: 'value3', minWidth: '60%' },
        { id: 4, label: '累计工程量', prop: 'value4', minWidth: '200%' }
      ],
      tableData: [
        // {
        //   area: 4.0,
        //   depth: 0.0,
        //   number: 0.0,
        //   seamLength: 0.0,
        //   seamWidth: 0.0,
        //   value1: '桥面系',
        //   value2: '桥面铺装',
        //   value3: '波浪及车辙',
        //   value4: '累计面积：3㎡'
        // },
        // {
        //   area: 4.0,
        //   depth: 0.0,
        //   number: 0.0,
        //   seamLength: 0.0,
        //   seamWidth: 0.0,
        //   value1: '桥面系',
        //   value2: '桥面铺装',
        //   value3: '坑槽',
        //   value4: '累计面积：0.49㎡；最大深度：50mm'
        // },
        // {
        //   area: 4.0,
        //   depth: 0.0,
        //   number: 0.0,
        //   seamLength: 0.0,
        //   seamWidth: 0.0,
        //   value1: '桥面系',
        //   value2: '桥面铺装',
        //   value3: '碎裂活破裂',
        //   value4: '累计面积：0.18㎡'
        // },
        // {
        //   area: 4.0,
        //   depth: 0.0,
        //   number: 0.0,
        //   seamLength: 0.0,
        //   seamWidth: 0.0,
        //   value1: '桥面系',
        //   value2: '排水系统',
        //   value3: '泄水管堵塞',
        //   value4: '累计数量：11个'
        // },
        // {
        //   area: 4.0,
        //   depth: 0.0,
        //   number: 0.0,
        //   seamLength: 0.0,
        //   seamWidth: 0.0,
        //   value1: '上部结构',
        //   value2: '主梁',
        //   value3: '表面裂缝',
        //   value4: '累计面积：0.18㎡'
        // },
        // {
        //   area: 4.0,
        //   depth: 0.0,
        //   number: 0.0,
        //   seamLength: 0.0,
        //   seamWidth: 0.0,
        //   value1: '下部结构',
        //   value2: '墩身',
        //   value3: '露筋锈蚀',
        //   value4: '累计面积：0.18㎡'
        // }
      ]
    };
  },
  methods: {
    async init() {
      let { code, data } = await getDiseaseSummaryList(this.bridgeRoadId);
      if (code == '0000') {
        this.tableData = data;
        this.mapData();
      }
    },
    backPage() {
      this.$emit('hideDetail');
    },
    mapData() {
      //思路：先变成层级结构：一列为一层
      //再筛选出同类的数据变成[3,0,0,2,0]代表这一列有5个数据前3个合并在2个合并
      let map = [];
      //1
      for (let index = 0; index < this.tableData.length; index++) {
        let is = false;
        for (let index2 = 0; index2 < map.length; index2++) {
          if (map[index2].lable == this.tableData[index].value1) {
            is = true;
            break;
          }
        }
        if (is == false) {
          let my = {
            lable: this.tableData[index].value1,
            children: [],
            value1: this.tableData[index].value1,
            value2: this.tableData[index].value2,
            value3: this.tableData[index].value3,
            value4: this.tableData[index].value4
          };
          map.push(my);
        }
      }
      //2
      for (let index = 0; index < this.tableData.length; index++) {
        for (let index2 = 0; index2 < map.length; index2++) {
          if (map[index2].lable == this.tableData[index].value1) {
            let is = false;
            for (
              let index3 = 0;
              index3 < map[index2].children.length;
              index3++
            ) {
              if (
                map[index2].children[index3].lable ==
                this.tableData[index].value2
              ) {
                is = true;
                break;
              }
            }
            if (is == false) {
              let my = {
                lable: this.tableData[index].value2,
                children: [],
                value1: this.tableData[index].value1,
                value2: this.tableData[index].value2,
                value3: this.tableData[index].value3,
                value4: this.tableData[index].value4
              };
              map[index2].children.push(my);
            }
          }
        }
      }
      //3
      for (let index = 0; index < this.tableData.length; index++) {
        for (let index2 = 0; index2 < map.length; index2++) {
          for (let index3 = 0; index3 < map[index2].children.length; index3++) {
            if (
              map[index2].lable == this.tableData[index].value1 &&
              map[index2].children[index3].lable == this.tableData[index].value2
            ) {
              let is = false;
              for (
                let index4 = 0;
                index4 < map[index2].children[index3].children.length;
                index4++
              ) {
                if (
                  map[index2].children[index3].children[index4].lable ==
                  this.tableData[index].value3
                ) {
                  is = true;
                  break;
                }
              }
              if (is == false) {
                let my = {
                  lable: this.tableData[index].value3,
                  children: [],
                  value1: this.tableData[index].value1,
                  value2: this.tableData[index].value2,
                  value3: this.tableData[index].value3,
                  value4: this.tableData[index].value4
                };
                map[index2].children[index3].children.push(my);
              }
            }
          }
        }
      }
      //1
      let row1 = 0;
      let row1Arr = [];
      map.map((text, index) => {
        row1 = 0;
        text.children.map((text2, index2) => {
          text2.children.map((text3, index3) => {
            row1++;
          });
        });
        row1Arr.push(row1);
      });
      this.maps.row1 = [];
      for (let index = 0; index < row1Arr.length; index++) {
        for (let index2 = 0; index2 < row1Arr[index]; index2++) {
          if (index2 == 0) {
            this.maps.row1.push(row1Arr[index]);
          } else {
            this.maps.row1.push(0);
          }
        }
      }
      //2
      let row2 = 0;
      let row2Arr = [];
      map.map((text, index) => {
        text.children.map((text2, index2) => {
          row2 = 0;
          text2.children.map((text3, index3) => {
            if (text.value1 == text3.value1) {
              row2++;
            }
          });
          row2Arr.push(row2);
        });
      });
      this.maps.row2 = [];
      for (let index = 0; index < row2Arr.length; index++) {
        for (let index2 = 0; index2 < row2Arr[index]; index2++) {
          if (index2 == 0) {
            this.maps.row2.push(row2Arr[index]);
          } else {
            this.maps.row2.push(0);
          }
        }
      }
    },
    //添加需要合并的列
    objectSpanMethod({ row, column, rowIndex, columnIndex }) {
      if (column.property === 'value1') {
        let _row = this.maps.row1[rowIndex];
        let _col = _row > 0 ? 1 : 0;
        return [_row, _col];
      }
      if (column.property === 'value2') {
        let _row = this.maps.row2[rowIndex];
        let _col = _row > 0 ? 1 : 0;
        return [_row, _col];
      }
    }
    // // 合并单元格
    // objectSpanMethod({ rowIndex, columnIndex }) {
    //   if (columnIndex === 1) {
    //     if (this.secondRst[rowIndex]) {
    //       return [this.secondRst[rowIndex], 1];
    //     } else {
    //       return [0, 0];
    //     }
    //   }
    //   if (columnIndex === 0) {
    //     if (this.firstRst[rowIndex]) {
    //       return [this.firstRst[rowIndex], 1];
    //     } else {
    //       return [0, 0];
    //     }
    //   }
    // },
    // //获取适合合并表格的数组
    // getResultArr(obj, rst) {
    //   let index = 0,
    //     sum = 0;
    //   for (let i = 0; i < this.tableData.length; i++) {
    //     if (sum == i) {
    //       sum += obj[index];
    //       rst.push(obj[index]);
    //       index++;
    //     } else {
    //       rst.push(0);
    //     }
    //   }
    // },
    // productArr(obj) {
    //   let firstIndex = 0,
    //     secondIndex = 0;
    //   let firstTemp = obj[0].value,
    //     secondTemp = obj[0].value1;
    //   for (let i = 0; i < obj.length; i++) {
    //     //统计不同类型
    //     if (firstTemp == obj[i].value) {
    //       firstIndex++;
    //       if (obj.length == i + 1) {
    //         this.firstArr.push(firstIndex);
    //       }
    //       if (secondTemp == obj[i].value1) {
    //         secondIndex++;
    //         if (obj.length == i + 1) {
    //           this.secondArr.push(secondIndex);
    //         }
    //       } else {
    //         if (obj.length == i + 1) {
    //           this.secondArr.push(secondIndex);
    //           this.secondArr.push(1);
    //         } else {
    //           this.secondArr.push(secondIndex);
    //           secondTemp = obj[i].value1;
    //           secondIndex = 1;
    //         }
    //       }
    //     } else {
    //       if (obj.length == i + 1) {
    //         this.firstArr.push(firstIndex);
    //         this.firstArr.push(1);
    //       } else {
    //         this.firstArr.push(firstIndex);
    //         firstTemp = obj[i].value;
    //         firstIndex = 1;
    //       }
    //       this.secondArr.push(secondIndex);
    //       this.secondArr.push(1);
    //     }

    //     // if (secondTemp == obj[i].value1) {
    //     //     secondIndex++;
    //     //     if (obj.length == i + 1) {
    //     //         this.secondArr.push(secondIndex);
    //     //     }
    //     // } else {
    //     //     if (obj.length == i + 1) {
    //     //         this.secondArr.push(secondIndex);
    //     //         this.secondArr.push(1);
    //     //     } else {
    //     //         this.secondArr.push(secondIndex);
    //     //         secondTemp = obj[i].value1;
    //     //         secondIndex = 1;
    //     //     }
    //     // }
    //   }
    // },
  },
  mounted() {
    this.init();
    // this.productArr(this.tableData);
    // this.getResultArr(this.firstArr, this.firstRst);
    // this.getResultArr(this.secondArr, this.secondRst);
  }
};
</script>
<style lang="scss" scoped>
.bciSummary {
  display: flex;
  flex-direction: column;
  .bread {
    height: 5.24vh;
    background: #fff;
    padding: 0 28px;
    display: flex;
    align-items: center;
    span {
      font-size: 0.8vw;
      color: #262626;
      white-space: pre-wrap;
      &:first-child {
        font-weight: bold;
        cursor: pointer;
      }
    }
  }
  .contentBox {
    padding: 1.855vh 28px;
    flex: 1;
    background: #f2f4f6;
    .content {
      width: 100%;
      height: 100%;
      background: #fff;
      border-radius: 8px;
      padding: 1.2vw;
      display: flex;
      flex-direction: column;
      .contentTop {
        height: 6.303vh;
        width: 100%;
        display: flex;
        .back {
          width: 3.125vw;
          height: 3.708vh;
          color: #fff;
          background: #419aff;
          border-radius: 4px;
          font-size: 0.7vw;
          display: flex;
          align-items: center;
          justify-content: center;
          cursor: pointer;
          &:hover {
            background: rgba(65, 154, 255, 0.8);
          }
        }
      }
      .sumTable {
        height: 100%;
        width: 100%;
        /deep/ .el-table {
          height: 100%;
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
        /deep/ .el-input__inner {
          font-size: 0.7vw;
          color: #333;
          text-align: center;
        }
        /deep/ .el-table--enable-row-hover .el-table__body tr:hover > td {
          background: #fff;
        }
      }
    }
  }
}
</style>
