<template>
  <div id="RC">
    <div class="border_title boxShadow">
      <div class="border_div">
        <span class="border_span" @click="backTwo"> 桥梁病害 </span>
        <span class="border_span2">/</span>
        <span class="border_span" @click="back"> 回收站 </span>
        <span class="border_span2">/</span>
        <span class="border_span2">记录详情</span>
      </div>
    </div>
    <div class="border_div2 boxShadow">
      <div>
        <el-button type="primary" @click="back" class="border_div3"
          >返回</el-button
        >
      </div>
      <el-row class="border_row">
        <el-col :span="11">
          <span class="border_span3">桥梁名称：{{ bridgeName }}</span>
        </el-col>
        <el-col :span="6">
          <span class="border_span3">检测记录员：{{ name }}</span>
        </el-col>
        <el-col :span="7">
          <span class="border_span4">检测日期：{{ time }}</span>
        </el-col>
      </el-row>
      <div class="border_div4">
        <el-table
          :data="tableData"
          :span-method="objectSpanMethod"
          border
          height="67vh"
          id="disTable"
        >
          <el-table-column prop="value1" label="线路" min-width="65%">
          </el-table-column>
          <el-table-column prop="value2" label="桥跨" min-width="65%">
          </el-table-column>
          <el-table-column prop="value3" label="部位" min-width="65%">
          </el-table-column>
          <el-table-column prop="value4" label="构件" min-width="65%">
          </el-table-column>
          <el-table-column prop="value5" label="病害类型" min-width="85%">
          </el-table-column>
          <el-table-column prop="value6" label="病害状况" min-width="85%">
            <template slot-scope="scope">
              <p v-if="scope.row.value6">
                {{ scope.row.value6 }}
              </p>
              <p v-else></p>
            </template>
          </el-table-column>
          <el-table-column prop="value7" label="病害编号" min-width="65%">
          </el-table-column>
          <el-table-column prop="value8" label="备注" min-width="270%">
          </el-table-column>
          <el-table-column prop="value9" label="检测人员" min-width="65%">
          </el-table-column>
          <el-table-column prop="photo" label="现场照片" min-width="65%">
            <template slot-scope="scope">
              <span v-if="scope.row.photo == 1" @click="clickPhoto(scope.row)">
                <i class="border_i el-icon-picture-outline"></i>
              </span>
              <span v-else>-</span>
            </template>
          </el-table-column>
        </el-table>

        <!-- 照片遮罩 -->

        <el-dialog
          :visible.sync="photoDialog"
          :modal-append-to-body="false"
          title="病害图片"
          id="photoClass"
          width="50vw"
          :close-on-click-modal="false"
        >
          <div class="photoClass">
            <div class="photoLeft">
              <el-image
                v-if="photoUrlArr.length != 0"
                style="width: 50vh; height: 50vh"
                :src="url.path"
              ></el-image>
              <p
                v-else-if="photoUrlArr.length == 0"
                style="font-size: 2vw; line-height: 44vh; text-align: center"
              >
                暂无图片
              </p>

              <el-checkbox
                v-if="photoUrlArr.length != 0 && isPhotoButton == false"
                v-model="checked"
                style="display: block"
                @change="clickPhotoCheck"
                >检测报告图片</el-checkbox
              >
            </div>
            <div class="photoRight">
              <div>
                <div style="height: 2vh; font-size: 1.4vh">
                  病害位置:{{ bridgeName }}+{{ photoManage.value1 }}->{{
                    photoManage.value2
                  }}->{{ photoManage.value3 }}->{{ photoManage.value4 }}
                </div>
                <div style="height: 2vh; font-size: 1.4vh">
                  病害编号:{{ photoManage.value7 }}
                </div>
                <div style="height: 2vh; font-size: 1.4vh">
                  病害类型:{{ photoManage.value5 }}
                </div>
                <div
                  v-if="
                    photoManage.value6 == undefined || photoManage.value6 == '-'
                  "
                  style="height: 2vh; font-size: 1.4vh"
                >
                  病害状况:无
                </div>
                <div
                  v-else-if="
                    photoManage.value6 != undefined && photoManage.value6 != '-'
                  "
                  style="height: 2vh; font-size: 1.4vh"
                >
                  病害状况:{{ photoManage.value6 }}
                </div>

                <div
                  v-if="
                    photoManage.value8 == undefined || photoManage.value8 == '-'
                  "
                  style="height: 2vh; font-size: 1.4vh"
                >
                  备注:无
                </div>
                <div
                  v-else-if="
                    photoManage.value8 != undefined && photoManage.value8 != '-'
                  "
                  style="font-size: 1.4vh"
                >
                  备注:{{ photoManage.value8 }}
                </div>
                <div class="photo">
                  <div v-for="url in photoUrlArr" :key="url.path">
                    <el-image
                      style="
                        width: 10vh;
                        height: 10vh;
                        margin-right: 0.5vw;
                        cursor: pointer;
                        margin-top: 1vh;
                      "
                      :src="url.path"
                      fit="scale-down"
                      @click="amplification(url)"
                    ></el-image>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </el-dialog>
      </div>
    </div>
  </div>
</template>

<script>
import { getRecordDetailList, getPhotoUrl } from '@/api/assessment/disease.js';
export default {
  props: {
    clickPlan: Object
  },
  data() {
    return {
      //桥名
      bridgeName: '',
      //检测日期
      time: '',
      //检测记录员
      name: '',
      //图片地址
      photoUrlArr: [],
      //当前大图片地址
      url: '',
      //照片遮罩
      photoDialog: false,
      //点击照片当前列的信息
      photoManage: {},
      //存储表格的合并数据
      maps: [],
      //表格数据
      tableData: [
        // { value1: 1 }
      ]
    };
  },
  mounted() {
    //初始化
    this.init();
  },
  methods: {
    async init() {
      this.bridgeName = this.clickPlan.structureName;
      this.time = this.clickPlan.createTime;
      this.name = this.clickPlan.creator;
      let params = {
        id: this.clickPlan.id,
        creator: this.clickPlan.creator,
        createTime: this.clickPlan.createTime
      };
      let { code, data } = await getRecordDetailList(params);
      if (code == '0000') {
        this.tableData = data;
        this.mapData();
      }
    },
    //返回到回收站
    back() {
      this.$emit('backRcyclig');
    },
    //返回到桥梁病害
    backTwo() {
      this.$emit('backDisease');
    },
    mapData() {
      var map = [];
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
          var my = {
            lable: this.tableData[index].value1,
            children: [],
            value1: this.tableData[index].value1,
            value2: this.tableData[index].value2,
            value3: this.tableData[index].value3,
            value4: this.tableData[index].value4,
            value5: this.tableData[index].value5,
            value6: this.tableData[index].value6
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
              var my = {
                lable: this.tableData[index].value2,
                children: [],
                value1: this.tableData[index].value1,
                value2: this.tableData[index].value2,
                value3: this.tableData[index].value3,
                value4: this.tableData[index].value4,
                value5: this.tableData[index].value5,
                value6: this.tableData[index].value6
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
                var my = {
                  lable: this.tableData[index].value3,
                  children: [],
                  value1: this.tableData[index].value1,
                  value2: this.tableData[index].value2,
                  value3: this.tableData[index].value3,
                  value4: this.tableData[index].value4,
                  value5: this.tableData[index].value5,
                  value6: this.tableData[index].value6
                };
                map[index2].children[index3].children.push(my);
              }
            }
          }
        }
      }
      //4
      for (let index = 0; index < this.tableData.length; index++) {
        for (let index2 = 0; index2 < map.length; index2++) {
          for (let index3 = 0; index3 < map[index2].children.length; index3++) {
            for (
              let index4 = 0;
              index4 < map[index2].children[index3].children.length;
              index4++
            ) {
              if (
                map[index2].lable == this.tableData[index].value1 &&
                map[index2].children[index3].lable ==
                  this.tableData[index].value2 &&
                map[index2].children[index3].children[index4].lable ==
                  this.tableData[index].value3
              ) {
                let is = false;
                for (
                  let index5 = 0;
                  index5 <
                  map[index2].children[index3].children[index4].children.length;
                  index5++
                ) {
                  if (
                    map[index2].children[index3].children[index4].children[
                      index5
                    ].lable == this.tableData[index].value4
                  ) {
                    is = true;
                    break;
                  }
                }
                if (is == false) {
                  var my = {
                    lable: this.tableData[index].value4,
                    children: [],
                    value1: this.tableData[index].value1,
                    value2: this.tableData[index].value2,
                    value3: this.tableData[index].value3,
                    value4: this.tableData[index].value4,
                    value5: this.tableData[index].value5,
                    value6: this.tableData[index].value6
                  };
                  map[index2].children[index3].children[index4].children.push(
                    my
                  );
                }
              }
            }
          }
        }
      }
      //5
      for (let index = 0; index < this.tableData.length; index++) {
        for (let index2 = 0; index2 < map.length; index2++) {
          for (let index3 = 0; index3 < map[index2].children.length; index3++) {
            for (
              let index4 = 0;
              index4 < map[index2].children[index3].children.length;
              index4++
            ) {
              for (
                let index5 = 0;
                index5 <
                map[index2].children[index3].children[index4].children.length;
                index5++
              ) {
                if (
                  map[index2].lable == this.tableData[index].value1 &&
                  map[index2].children[index3].lable ==
                    this.tableData[index].value2 &&
                  map[index2].children[index3].children[index4].lable ==
                    this.tableData[index].value3 &&
                  map[index2].children[index3].children[index4].children[index5]
                    .lable == this.tableData[index].value4
                ) {
                  var my = {
                    lable: this.tableData[index].value5,
                    children: [],
                    value1: this.tableData[index].value1,
                    value2: this.tableData[index].value2,
                    value3: this.tableData[index].value3,
                    value4: this.tableData[index].value4,
                    value5: this.tableData[index].value5,
                    value6: this.tableData[index].value6
                  };
                  map[index2].children[index3].children[index4].children[
                    index5
                  ].children.push(my);
                }
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
            text3.children.map((text4, index4) => {
              text4.children.map((text5, index5) => {
                row1++;
              });
            });
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
            text3.children.map((text4, index4) => {
              text4.children.map((text5, index5) => {
                if (text.value1 == text5.value1) {
                  row2++;
                }
              });
            });
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
      //3
      let row3 = 0;
      let row3Arr = [];
      map.map((text, index) => {
        text.children.map((text2, index2) => {
          text2.children.map((text3, index3) => {
            row3 = 0;
            text3.children.map((text4, index4) => {
              text4.children.map((text5, index5) => {
                if (
                  text.value1 == text5.value1 &&
                  text2.value2 == text5.value2
                ) {
                  row3++;
                }
              });
            });
            row3Arr.push(row3);
          });
        });
      });
      this.maps.row3 = [];
      for (let index = 0; index < row3Arr.length; index++) {
        for (let index2 = 0; index2 < row3Arr[index]; index2++) {
          if (index2 == 0) {
            this.maps.row3.push(row3Arr[index]);
          } else {
            this.maps.row3.push(0);
          }
        }
      }
      //4
      let row4 = 0;
      let row4Arr = [];
      map.map((text, index) => {
        text.children.map((text2, index2) => {
          text2.children.map((text3, index3) => {
            text3.children.map((text4, index4) => {
              row4 = 0;
              text4.children.map((text5, index5) => {
                if (
                  text.value1 == text5.value1 &&
                  text2.value2 == text5.value2 &&
                  text3.value3 == text5.value3
                ) {
                  row4++;
                }
              });
              row4Arr.push(row4);
            });
          });
        });
      });
      this.maps.row4 = [];
      for (let index = 0; index < row4Arr.length; index++) {
        for (let index2 = 0; index2 < row4Arr[index]; index2++) {
          if (index2 == 0) {
            this.maps.row4.push(row4Arr[index]);
          } else {
            this.maps.row4.push(0);
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
      if (column.property === 'value3') {
        let _row = this.maps.row3[rowIndex];
        let _col = _row > 0 ? 1 : 0;
        return [_row, _col];
      }
      if (column.property === 'value4') {
        let _row = this.maps.row4[rowIndex];
        let _col = _row > 0 ? 1 : 0;
        return [_row, _col];
      }
    },
    //暂时无用
    mergeAction(val, rowIndex, colData) {
      let _row = this.rowMergeArrs[val].rowArr[rowIndex];
      let _col = _row > 0 ? 1 : 0;
      return [_row, _col];
    },
    //暂时无用
    rowMergeHandle(arr, data) {
      if (!Array.isArray(arr) && !arr.length) return false;
      if (!Array.isArray(data) && !data.length) return false;
      let needMerge = {};
      arr.forEach((i) => {
        needMerge[i] = {
          rowArr: [],
          rowMergeNum: 0
        };
        data.forEach((item, index) => {
          if (index === 0) {
            needMerge[i].rowArr.push(1);
            needMerge[i].rowMergeNum = 0;
          } else {
            if (item[i] === data[index - 1][i]) {
              needMerge[i].rowArr[needMerge[i].rowMergeNum] += 1;
              needMerge[i].rowArr.push(0);
            } else {
              needMerge[i].rowArr.push(1);
              needMerge[i].rowMergeNum = index;
            }
          }
        });
      });
      return needMerge;
    },
    //点击照片
    async clickPhoto(row) {
      let { code, data } = await getPhotoUrl(row.id);
      if (code == '0000') {
        this.photoDialog = true;
        this.photoManage = row;
        this.photoUrlArr = data;
        if (data != '') {
          let url = {};
          url.path = data[0].path;
          url.id = data[0].id;
          this.url = url;
        }
      }
    },
    //点击照片放大
    amplification(url) {
      this.url = url;
    }
  }
};
</script>

<style lang="scss" scoped>
#RC #disTable .el-table__header tr,
#RC #disTable .el-table__header th {
  padding: 0;
  height: 48px;
  text-align: center;
}
#RC #disTable .el-table__body tr,
#RC #disTable .el-table__body td {
  padding: 0;
  height: 48px;
  text-align: center;
}
#RC #disTable .el-table__header tr,
#RC #disTable .el-table__header th,
#RC #disTable .el-table__body tr,
#RC #disTable .el-table__body td {
  border-top: 1px solid #ebeef5;
  border-left: 1px solid #ebeef5;
  border-right: none;
  border-bottom: none;
}

#RC #disTable .el-table__body tr,
#RC #disTable .el-table__body td {
  background: white;
}

#RC #disTable .el-table__header tr,
#RC #disTable .el-table__header th {
  background: rgb(242, 244, 246);
}

#RC #photoClass .el-dialog__title {
  line-height: 4vh;
  margin-left: 1.5%;
  font-size: 1.8vh;
}
#RC #photoClass .el-dialog__header {
  height: 4vh;
}
#RC #photoClass .el-dialog__headerbtn .el-dialog__close {
  width: 0.8vw;
  height: 1.4vh;
  font-size: 1.4vh;
  line-height: 1.4vh;
}
img {
  image-rendering: -moz-crisp-edges; /* Firefox */
  image-rendering: -o-crisp-edges; /* Opera */
  image-rendering: -webkit-optimize-contrast; /*Webkit (non-standard naming) */
  image-rendering: crisp-edges;
  -ms-interpolation-mode: nearest-neighbor; /* IE (non-standard property) */
}

.border_title {
  width: 100%;
  height: 5.6vh;
  background: white;
  .border_div {
    margin-left: 2.8vw;
    .border_span {
      line-height: 5.6vh;
      font-weight: bold;
      font-size: 1.4vh;
      cursor: pointer;
    }
    .border_span2 {
      line-height: 5.6vh;
      font-size: 1.4vh;
    }
  }
}
.border_div2 {
  margin-left: 2.8vw;
  margin-right: 2.8vw;
  background: white;
  margin-top: 2vh;
  border-radius: 8px;
  height: 83.8vh;
  .border_div3 {
    margin-top: 3.5vh;
    margin-left: 1.5vw;
    width: 5vw;
    height: 4vh;
    font-size: 1.4vh;
  }
  .border_row {
    margin-top: 1.5%;
    margin-left: 1.5vw;
    margin-right: 1.5vw;
    .border_span3 {
      font-family: Microsoft YaHei UI;
      font-weight: bold;
      font-size: 1.4vh;
    }
    .border_span4 {
      font-family: Microsoft YaHei UI;
      font-weight: bold;
      float: right;
      font-size: 1.4vh;
    }
  }
  .border_div4 {
    margin-top: 1.5%;
    margin-left: 1.5vw;
    margin-right: 1.5vw;
    /deep/ .el-table th {
      color: #333;
      text-align: center;
    }
    /deep/ .el-table td {
      text-align: center;
    }
    .border_i {
      cursor: pointer;
    }
  }
}
.photoClass {
  display: flex;
  .photoLeft {
    width: 26vw;
    height: 50vh;
    border-right: 1px solid;
    padding-right: 1vw;
  }
  .photoRight {
    width: 22vw;
    padding-left: 1vw;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
  }
  .btn {
    display: flex;
    justify-content: flex-end;
  }
  .photo {
    display: flex;
    flex-wrap: wrap;
  }
}
</style>
