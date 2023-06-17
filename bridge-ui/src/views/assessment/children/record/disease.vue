<template>
  <div id="BD">
    <div class="my_div">
      <div class="title_span">{{ structureManage.structureName }}</div>
      <div class="border_div">
        <el-input
          v-model="keyWord"
          placeholder="请输入关键字"
          class="border_input"
        ></el-input>
        <el-button
          size="medium"
          type="primary"
          class="border_button"
          @click="selectKeyWord"
          >查询</el-button
        >
        <el-button
          size="medium"
          type="primary"
          class="border_button"
          @click="toRecycling"
          >回收站</el-button
        >
      </div>
    </div>

    <div class="diseaseTable">
      <el-table
        :key="keyItem"
        :data="tableData"
        :span-method="objectSpanMethod"
        height="67vh"
        id="disTable"
        border
        class="table_div"
      >
        <el-table-column prop="value1" label="线路" min-width="50%">
        </el-table-column>
        <el-table-column prop="value2" label="桥跨" min-width="50%">
        </el-table-column>
        <el-table-column prop="value3" label="部位" min-width="55%">
        </el-table-column>
        <el-table-column prop="value4" label="构件" min-width="65%">
        </el-table-column>
        <el-table-column prop="value5" label="病害类型" min-width="100%">
        </el-table-column>
        <el-table-column prop="value6" label="病害状况" min-width="80%">
          <template slot-scope="scope">
            <p v-if="scope.row.value6 == undefined">-</p>
            <p v-else-if="scope.row.value6 != undefined">
              {{ scope.row.value6 }}
            </p>
          </template>
        </el-table-column>
        <el-table-column prop="value7" label="病害编号" min-width="50%">
        </el-table-column>
        <el-table-column prop="value8" label="备注" min-width="120%">
          <template slot-scope="scope">
            <p v-if="scope.row.value8 == undefined">-</p>
            <p v-else-if="scope.row.value8 != undefined">
              {{ scope.row.value8 }}
            </p>
          </template>
        </el-table-column>
        <el-table-column prop="value9" label="检测人员" min-width="50%">
        </el-table-column>
        <el-table-column prop="photo" label="现场照片" min-width="50%">
          <template slot-scope="scope">
            <div v-if="scope.row.photo == 0">-</div>
            <span v-else @click="clickPhoto(scope.row)">
              <i class="border_i el-icon-picture-outline"></i>
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="option" label="修改记录" min-width="50%">
          <template slot-scope="scope">
            <span
              v-if="scope.row.value10 != 0"
              @click="record(scope.row)"
              class="border_span"
            >
              查看记录
            </span>
            <span v-if="scope.row.value10 == 0">-</span>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 查看记录遮罩 -->
    <el-dialog
      :visible.sync="recordDialog"
      title="病害修改记录"
      class="record_dialog_div"
      id="detailClass"
      :modal-append-to-body="false"
      :close-on-click-modal="false"
    >
      <el-form>
        <el-form-item class="record_dialog_form">
          <el-table
            :data="dialogTable"
            border
            id="detailTable"
            class="record_dialog_table"
          >
            <el-table-column
              type="index"
              label="序号"
              width="50"
              align="center"
            >
              <template slot-scope="scope">
                {{ (currentPage - 1) * 10 + scope.$index + 1 }}
              </template>
            </el-table-column>
            <el-table-column prop="name" label="病害类型" align="center">
            </el-table-column>
            <el-table-column prop="degree" label="程度" align="center">
            </el-table-column>
            <el-table-column prop="code" label="病害编号" align="center">
            </el-table-column>
            <el-table-column
              prop="createTime"
              label="修改时间"
              min-width="100%"
              align="center"
            >
            </el-table-column>
            <el-table-column prop="creator" label="修改人员" align="center">
            </el-table-column>
          </el-table>

          <!-- 分页器 -->
          <div class="record_dialog_page" id="pager">
            <el-pagination
              @current-change="handleCurrentChange"
              :current-page.sync="currentPage"
              :page-size="pageSize"
              layout="prev, pager, next, total"
              :total="dataTotal"
            ></el-pagination>
          </div>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 照片遮罩 -->
    <el-dialog
      :visible.sync="photoDialog"
      append-to-body
      title="病害图片"
      class="photoClassBox"
      :close-on-click-modal="false"
    >
      <div class="photoClass">
        <div class="photoLeft">
          <div class="imgBox">
            <el-image
              v-if="photoUrlArr.length != 0"
              :src="url.path"
              :preview-src-list="[url.path]"
            ></el-image>
          </div>
          <div class="imgBtm">
            <el-checkbox
              v-if="photoUrlArr.length != 0 && isPhotoButton == false"
              v-model="checked"
              style="display: block"
              @change="clickPhotoCheck"
              >检测报告图片</el-checkbox
            >
            <div class="btmRight">
              <span>重命名：</span>
              <el-input
                class="imgInput"
                type="textarea"
                v-model="url.fileName"
                maxlength="50"
                clearable
                autosize
                @blur="changeFileName(url)"
              ></el-input>
            </div>
          </div>
        </div>
        <div class="photoRight">
          <div class="text">
            <div>
              病害位置:{{ bridgeName }}+{{ photoManage.value1 }}->{{
                photoManage.value2
              }}->{{ photoManage.value3 }}->{{ photoManage.value4 }}
            </div>
            <div>病害编号:{{ photoManage.value7 }}</div>
            <div>病害类型:{{ photoManage.value5 }}</div>
            <div
              v-if="
                photoManage.value6 == undefined || photoManage.value6 == '-'
              "
            >
              病害状况:无
            </div>
            <div
              v-else-if="
                photoManage.value6 != undefined && photoManage.value6 != '-'
              "
            >
              病害状况:{{ photoManage.value6 }}
            </div>
            <div
              v-if="
                photoManage.value8 == undefined || photoManage.value8 == '-'
              "
            >
              备注:无
            </div>
            <div
              v-else-if="
                photoManage.value8 != undefined && photoManage.value8 != '-'
              "
            >
              备注:{{ photoManage.value8 }}
            </div>
            <div class="photo">
              <div v-for="url in photoUrlArr" :key="url.path">
                <el-image
                  class="imgItem"
                  :src="url.path"
                  fit="scale-down"
                  @click="amplification(url)"
                ></el-image>
                <div style="text-align: center">
                  <i
                    v-if="isPhotoButton == false"
                    class="el-icon-delete"
                    style="font-size: 1.4vh; cursor: pointer; z-index: 999"
                    @click="deletePhoto(url)"
                  ></i>
                </div>
              </div>
            </div>
          </div>
          <div class="btn">
            <el-upload
              class="upload-demo"
              ref="upload"
              action="/bridge/evaluation/record/uploadPhotoUrl"
              :headers="headers"
              :data="uploadData"
              :on-change="handleChange"
              :on-success="successUpload"
              :show-file-list="false"
              :disabled="photoDisabled"
              accept=".png,.jpeg,.jpg,.bmp"
              :before-upload="beforeUpload"
            >
              <el-button
                v-if="isPhotoButton == false"
                slot="trigger"
                type="primary"
                @click="resetUpload"
                style="margin-right: 20px"
                >重新上传</el-button
              >
            </el-upload>
            <el-button @click="photoDialog = false">取消</el-button>
          </div>
        </div>
      </div>
    </el-dialog>
    <recycling
      :structureManage="structureManage"
      v-if="showRecycling"
      class="recycling"
      @backDisease="backDisease"
    ></recycling>
  </div>
</template>

<script>
import {
  getDiseaseList,
  getRecordList,
  // getRecordCount,
  getPhotoUrl,
  deletePhoto,
  updatePhoto,
  isOriginalRecordByStructure
} from '@/api/assessment/disease';
import recycling from './components/recycling';
export default {
  components: {
    recycling
  },
  inject: ['authOpt'],
  props: {
    structureManage: {
      type: Object,
      default: () => {
        return { id: '', structureName: '' };
      }
    }
  },
  data() {
    return {
      keyItem: 0,
      //回收站跳转
      showRecycling: false,
      //检测报告图片选择按钮
      checked: false,
      //关键字内容
      keyWord: '',
      //点击照片当前列的信息
      photoManage: {},
      //查询的关键字
      sendKeyWord: '',
      //跳转所需要携带的信息
      returnManage: '',
      //桥名
      bridgeName: '',
      //图片地址
      photoUrlArr: [{ path: require('@/assets/images/disease22.png') }],
      //当前大图片信息
      url: { path: require('@/assets/images/disease22.png') },
      //照片遮罩
      photoDialog: false,
      //当前页数
      currentPage: 1,
      //总页数
      dataTotal: 100,
      //每页数量
      pageSize: 10,
      //查看记录的信息
      rowManage: {},
      //记录遮罩里的表格
      dialogTable: [],
      //记录遮罩
      recordDialog: false,
      //当前选择的标签
      activeName: 'second',
      //存储表格的合并数据
      maps: [],
      //表格数据
      tableData: [],
      //发送的关键字
      sendKeyword: '',
      //上传附带的数据
      uploadData: { dirName: 'recordPhoto' },
      //控制是否可以上传
      photoDisabled: true,
      //判断照片里的按钮是否需要隐藏
      isPhotoButton: false,
      headers: {
        'X-AUTH-TOKEN': this.$store.getters.getToken,
        'X-ROUTER-URL': '/assessment/record'
      }
      // needMergeArr: ["value1", "value2", "value3", "value4"], // 有合并项的列
      // rowMergeArrs: {}, // 包含需要一个或多个合并项信息的对象
    };
  },
  watch: {
    structureManage() {
      if (this.structureManage.id) {
        this.init();
      }
    }
  },
  mounted() {
    this.mapData();
    //初始化
    if (this.structureManage.id) {
      this.init();
      this.bridgeName = this.structureManage.structureName;
    }
    // this.rowMergeArrs = this.rowMergeHandle(this.needMergeArr, this.tableData);
  },
  methods: {
    demo(row) {},
    //初始化
    async init() {
      //判断是否需要隐藏按钮
      let obj1 = await isOriginalRecordByStructure(this.structureManage.id);
      if (obj1.code == '0000') {
        this.isPhotoButton = obj1.data;
      }

      let params = {
        id: this.structureManage.id,
        keyword: this.sendKeyword
      };
      let obj2 = await getDiseaseList(params);
      if (obj2.code == '0000') {
        this.tableData = obj2.data;
        this.mapData();
        //刷新
        this.keyItem = Math.random();
      }
    },
    //返回到桥梁病害
    async backDisease() {
      this.showRecycling = false;
      this.recordDialog = false;
      let params = {
        id: this.structureManage.id,
        keyword: this.sendKeyword
      };
      let { code, data } = await getDiseaseList(params);
      if (code == '0000') {
        this.tableData = data;
        this.mapData();
        this.keyItem = Math.random();
      }
    },
    //查询
    async selectKeyWord() {
      this.sendKeyWord = this.keyWord;
      let params = {
        id: this.structureManage.id,
        keyword: this.keyWord
      };
      let { code, data } = await getDiseaseList(params);
      if (code == '0000') {
        this.tableData = data;
        this.mapData();
        this.keyItem = Math.random();
      }
    },
    //查看记录遮罩
    async record(row) {
      this.rowManage = row;
      console.log(this.rowManage);
      let params = {
        currentPage: this.currentPage,
        pageSize: this.pageSize,
        targetId: this.rowManage.targetId,
        partType: this.rowManage.partType,
        diseaseId: this.rowManage.diesaseId,
        id: this.rowManage.id,
        creator: this.rowManage.value9
      };
      let { code, data } = await getRecordList(params);
      if (code == '0000') {
        this.recordDialog = true;
        this.dialogTable = data.list;
        this.dataTotal = data.total;
      }
    },
    //分页
    async handleCurrentChange(page) {
      this.currentPage = page;
      let params = {
        currentPage: this.currentPage,
        pageSize: this.pageSize,
        targetId: this.rowManage.targetId,
        partType: this.rowManage.partType,
        diseaseId: this.rowManage.diesaseId
      };
      let { code, data } = await getRecordList(params);
      if (code == '0000') {
        this.dialogTable = data.list;
        this.dataTotal = data.total;
      }
    },
    //重新上传
    resetUpload() {
      if (this.photoUrlArr.length >= 6) {
        this.$message({
          message: '限制最多上传6张图片，请删除后上传',
          type: 'warning'
        });
        this.photoDisabled = true;
        return;
      } else {
        this.photoDisabled = false;
      }
    },
    //设定检测报告图片
    async clickPhotoCheck() {
      //选择检测照片
      if (this.checked) {
        let params = {
          targetId: this.photoManage.id,
          id: this.url.id,
          status: 2
        };
        let { code } = await updatePhoto(params);
        if (code == '0000') {
          this.photoUrlArr.map((text) => {
            if (this.url.id == text.id) {
              text.status = 2;
            }
          });
        }
      } else {
        let params = {
          targetId: this.photoManage.id,
          id: this.url.id,
          status: 1
        };
        let { code } = await updatePhoto(params);
        if (code == '0000') {
          this.photoUrlArr.map((text) => {
            if (this.url.id == text.id) {
              text.status = 1;
            }
          });
        }
      }
    },
    //重命名
    changeFileName(item) {
      let nameList = item.name.split('.');
      let orlName = nameList.slice(0, nameList.length - 1).join('');
      if (item.fileName == orlName) {
        return;
      }
      this.$confirm('是否重命名图片？', '确认信息', {
        distinguishCancelAndClose: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      })
        .then(async () => {
          let arry = item.name.split('.');
          let name = item.fileName + '.' + arry[arry.length - 1];
          let params = {
            id: item.id,
            name
          };
          let { code } = await updatePhoto(params);
          if (code == '0000') {
            this.$message({
              type: 'success',
              message: '修改成功！',
              showClose: true,
              duration: 2000
            });
            this.photoUrlArr.map((text) => {
              if (this.url.id == text.id) {
                text.name = name;
              }
            });
          }
        })
        .catch(() => {
          item.fileName = orlName;
        });
    },
    //点击照片
    async clickPhoto(row) {
      this.uploadData.partType = row.partType;
      this.uploadData.status = 1;
      this.uploadData.type = 5;
      this.uploadData.targetId = row.id;
      this.photoUrlArr = [];
      this.url = '';
      let { code, data } = await getPhotoUrl(row.id);
      if (code == '0000') {
        this.checked = false;
        this.photoDialog = true;
        this.photoManage = row;
        this.photoUrlArr = data;
        if (data != '') {
          if (data[0].status == 2) {
            this.checked = true;
          } else {
            this.checked = false;
          }
          let nameList = data[0].name.split('.');
          data[0].fileName = nameList.slice(0, nameList.length - 1).join('');
          this.url = { ...data[0] };
        }
      }
    },
    //点击照片放大
    amplification(url) {
      //设定选中的检测照片
      if (url.status == 1) {
        this.checked = false;
      } else if (url.status == 2) {
        this.checked = true;
      }
      let nameList = url.name.split('.');
      url.fileName = nameList.slice(0, nameList.length - 1).join('');
      this.url = { ...url };
    },
    //删除图片
    deletePhoto(url) {
      this.$confirm('确定删除删吗', '确认信息', {
        distinguishCancelAndClose: true,
        confirmButtonText: '删除',
        cancelButtonText: '取消'
      })
        .then(async () => {
          let deleteObj = await deletePhoto(url.id, 0);
          if (deleteObj.code == '0000') {
            this.$message({
              type: 'success',
              message: '删除成功！',
              showClose: true,
              duration: 2000
            });
            let urlObj = await getPhotoUrl(url.targetId);
            if (urlObj.code == '0000') {
              this.photoUrlArr = urlObj.data;
              if (urlObj.data != '') {
                let nameList = urlObj.data[0].name.split('.');
                urlObj.data[0].fileName = nameList
                  .slice(0, nameList.length - 1)
                  .join('');
                this.url = { ...urlObj.data[0] };
              }
            }
          }
        })
        .catch((action) => {});
    },
    //上传文件成功后
    async successUpload() {
      let { code, data } = await getPhotoUrl(this.uploadData.targetId);
      if (code == '0000') {
        this.photoUrlArr = data;
        if (data != '') {
          let nameList = data[0].name.split('.');
          data[0].fileName = nameList.slice(0, nameList.length - 1).join('');
          this.url = { ...data[0] };
        }
      }
    },
    //上传文件前
    beforeUpload(file, fileList) {
      const testmsg = file.name.substring(file.name.lastIndexOf('.') + 1);
      const isLt10M = file.size / 1024 / 1024 <= 10;
      const extension = testmsg === 'bmp';
      const extension2 = testmsg === 'jpeg';
      const extension3 = testmsg === 'jpg';
      const extension4 = testmsg === 'png';
      if (!extension && !extension2 && !extension3 && !extension4) {
        this.$message({
          message: '上传文件只能是 bmp、jpeg、jpg、png格式!',
          type: 'warning'
        });
      }
      if (!isLt10M) {
        this.$message({
          message: '上传文件限制在10M以内',
          type: 'warning'
        });
      }
      return (extension || extension2 || extension3 || extension4) && isLt10M;
    },
    //回收站
    toRecycling() {
      this.showRecycling = true;
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
              let my = {
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
                let my = {
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
                  let my = {
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
                  let my = {
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
        // return this.mergeAction("value1", rowIndex, column);
        let _row = this.maps.row1[rowIndex];
        let _col = _row > 0 ? 1 : 0;
        return [_row, _col];
      }
      if (column.property === 'value2') {
        // return this.mergeAction("value2", rowIndex, column);
        let _row = this.maps.row2[rowIndex];
        let _col = _row > 0 ? 1 : 0;
        return [_row, _col];
      }
      if (column.property === 'value3') {
        // return this.mergeAction("value3", rowIndex, column);
        let _row = this.maps.row3[rowIndex];
        let _col = _row > 0 ? 1 : 0;
        return [_row, _col];
      }
      if (column.property === 'value4') {
        // return this.mergeAction("value4", rowIndex, column);
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
    handleChange(file) {
      // console.log(file);
    }
  }
};
</script>
<style lang="scss" scoped>
.diseaseTable {
  margin-top: 2vh;
  margin-left: 1.5%;
  margin-right: 1.5%;
  height: 67vh;
  /deep/ .el-table th {
    color: #333;
    text-align: center;
  }
  /deep/ .el-table td {
    text-align: center;
  }
}
</style>
<style lang="scss" scoped>
#BD #disTable .el-table__header tr,
#BD #disTable .el-table__header th {
  font-size: 1.4vh;
  padding: 0;
  height: 4.8vh;
  text-align: center;
}
#BD #disTable .el-table__body tr,
#BD #disTable .el-table__body td {
  font-size: 1.4vh;
  padding: 0;
  height: 4.8vh;
  text-align: center;
}
#BD #disTable .el-table__header tr,
#BD #disTable .el-table__header th,
#BD #disTable .el-table__body tr,
#BD #disTable .el-table__body td {
  border-top: 1px solid #ebeef5;
  border-left: 1px solid #ebeef5;
  border-right: none;
  border-bottom: none;
}

#BD #disTable .el-table__body tr,
#BD #disTable .el-table__body td {
  background: white;
}

#BD #disTable .el-table__header tr,
#BD #disTable .el-table__header th {
  background: rgb(242, 244, 246);
}

#BD #detailTable .el-table__header tr,
#BD #detailTable .el-table__header th {
  font-size: 1.4vh;
  padding: 0;
  height: 4.8vh;
  text-align: center;
}
#BD #detailTable .el-table__body tr,
#BD #detailTable .el-table__body td {
  font-size: 1.4vh;
  padding: 0;
  height: 4.8vh;
  text-align: center;
}
#BD #detailTable .el-table__header tr,
#BD #detailTable .el-table__header th,
#BD #detailTable .el-table__body tr,
#BD #detailTable .el-table__body td {
  border: 1px solid #ebeef5;
}

#BD #detailTable .el-table__body tr,
#BD #detailTable .el-table__body td {
  background: white;
}

#BD #detailTable .el-table__header tr,
#BD #detailTable .el-table__header th {
  background: rgb(242, 244, 246);
}
#BD .cell .el-tooltip,
#BD .cell {
  height: 4.8vh;
  line-height: 4.8vh;
  text-align: center;
}

#BD .el-table__row .cell {
  font-weight: 500;
}
/* 分页样式 */
#BD #pager .el-dialog,
#BD #pager .el-pager li {
  // background: #141e30 !important;
  color: #808a96 !important;
}

#BD #pager .el-pagination button:disabled {
  color: #808a96 !important;
  // background-color: #141e30 !important;
  cursor: not-allowed;
}

#BD #pager .el-pagination .btn-next {
  padding-left: 12px;
  // background-color: #141e30 !important;
  color: #808a96 !important;
}

#BD #pager .el-pagination .btn-prev {
  padding-right: 12px;
  color: #808a96 !important;
  // background-color: #141e30 !important;
}

#BD #pager .el-pager li.active {
  cursor: default;
  color: #aec0d8 !important;
  background-color: #419aff !important;
  border-radius: 4px;
}
#BD #page .el-pager .li {
  height: 2.8vh;
  width: 2vw;
  font-size: 1.4vh;
}
#BD #pager .el-pager li {
  width: 2vw;
  height: 2.8vh;
  font-size: 1.4vh;
  line-height: 2.8vh;
}
#BD #pager .el-pagination__total {
  font-size: 1.4vh !important;
  line-height: 2.8vh !important;
}
#BD #pager .el-pagination .btn-next .el-icon,
#BD #pager .el-pagination .btn-prev .el-icon {
  font-size: 1.4vh;
  line-height: 2.8vh;
}
#BD .recycling {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 93.4%;
  background: rgb(246, 247, 251);
  z-index: 5;
  margin-top: 6.5556vh;
}
#BD .el-input__inner {
  // height: 4vh !important;
  font-size: 1.4vh;
}

#BD #detailClass .el-dialog__title {
  line-height: 4vh;
  margin-left: 1.5%;
  font-size: 1.8vh;
}
#BD #detailClass .el-dialog__header {
  height: 4vh;
}
#BD #detailClass .el-dialog__headerbtn .el-dialog__close {
  width: 0.8vw;
  height: 1.4vh;
  font-size: 1.4vh;
  line-height: 1.4vh;
}
.el-message-box {
  width: 22vw;
  height: 15.6vh;
}
.el-message-box__message {
  font-size: 1.4vh;
}
.el-message-box__title {
  font-size: 1.8vh;
  line-height: 1.8vh;
  margin-left: 0.5vw;
  margin-top: 0.5vh;
}
.el-message-box__headerbtn {
  top: 1.5vh;
  right: 1vw;
  font-size: 1.6vh;
}
.el-message-box__header {
  padding: 1.5vh 1.5vh 1vw;
  height: 3.6vh;
}
.el-message-box__status + .el-message-box__message {
  margin-top: 1.5vh;
  padding-left: 2vw;
  padding-right: 1vw;
}
.el-message-box__status.el-icon-warning {
  font-size: 3vh !important;
}
.el-message-box__message p {
  height: 3vh;
  line-height: 3vh;
}
#BD .el-button--mini,
#BD .el-button--small {
  font-size: 1.2vh;
  height: 3.2vh;
  width: 3vw;
}
.el-message-box__content {
  padding: 1vh 1.5vh;
}
img {
  image-rendering: -moz-crisp-edges; /* Firefox */
  image-rendering: -o-crisp-edges; /* Opera */
  image-rendering: -webkit-optimize-contrast; /*Webkit (non-standard naming) */
  image-rendering: crisp-edges;
  -ms-interpolation-mode: nearest-neighbor; /* IE (non-standard property) */
}
#BD .my_div {
  display: flex;
  justify-content: space-between;
  padding: 1vw 1.2vw 0;
  align-items: center;
}
#BD .title_span {
  font-weight: bold;
  // line-height: 4vh;
  font-size: 1.4vh;
}
#BD .photo_diolog_photo {
  width: 55%;
  display: inline-flex;
  height: 55vh;
  margin: 0 1% 0 0.8vw;
  .el-image {
    width: 640px;
    height: 40.742vh;
  }
  .photo_diolog_check {
    display: block;
  }
}
#BD .photo_diolog_inf {
  width: 40%;
  display: inline-flex;
  margin-left: 1%;
  .photo_diolog_inf_div {
    margin-top: -56vh;
    position: fixed;
    .photo_diolog_span {
      height: 2vh;
      font-size: 1.4vh;
    }
    .photo_diolog_div {
      display: inline-flex;
    }
  }
}
#BD .photo_diolog_img {
  width: 10vh;
  height: 10vh;
  margin-right: 0.5vw;
  cursor: pointer;
  margin-top: 1vh;
}
#BD .photo_diolog_deleteI {
  font-size: 1.4vh;
  margin-left: 10%;
  margin-top: 11vh;
  margin-left: -50%;
  cursor: pointer;
  z-index: 999;
}

#BD .optBtn {
  .el-form-item__content {
    display: flex;
    align-items: center;
    justify-content: flex-end;
  }
  .optBtn_upload {
    display: grid;
  }
  .optBtn_button {
    color: #727477;
  }
}
#BD .photo_diolog_button {
  float: right;
  background: white;
  width: 5vw;
  height: 4vh;
  font-size: 1.4vh;
}
#BD .photo_diolog_button2 {
  float: right;
  margin-right: 1vw;
  width: 5vw;
  height: 4vh;
  font-size: 1.4vh;
}

.border_div {
  float: right;
}
.border_input {
  width: 10vw;
  margin-right: 1vw;
}
.border_button {
  font-size: 1.4vh;
}
.table_div {
  min-width: 800px;
}
.border_i {
  cursor: pointer;
}
.border_span {
  color: #419aff;
  cursor: pointer;
}
.record_dialog_div {
  font-weight: bold;
  .record_dialog_form {
    margin-left: 1.5%;
    margin-right: 1.5%;
    .record_dialog_table {
      width: 100%;
      margin-top: 2vh;
    }
    .record_dialog_page {
      text-align: center;
    }
  }
}
.photoClassBox {
  .photoClass {
    display: flex;
    .photoLeft {
      width: 660px;
      border-right: 1px solid;
      padding-right: 20px;
      display: flex;
      flex-direction: column;
      .imgBox {
        height: 38.427vh;
        width: 100%;
        /deep/ .el-image {
          height: 100%;
          width: 100%;
          display: flex;
          align-items: center;
          justify-content: center;
        }
        /deep/ .el-image__inner {
          max-height: 100%;
          max-width: 100%;
          height: unset;
          width: unset;
        }
      }
      .imgBtm {
        padding-top: 20px;
        display: flex;
        // align-items: center;
        justify-content: space-between;
        .btmRight {
          display: flex;
        }
        .imgInput {
          width: 220px;
        }
      }
    }
    .photoRight {
      flex: 1;
      padding-left: 20px;
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      .text div {
        line-height: 20px;
      }
      .photo {
        display: flex;
        flex-wrap: wrap;
        .imgItem {
          width: 80px;
          height: 80px;
          margin: 10px 5px 0;
          display: flex;
          align-items: center;
          justify-content: center;
          /deep/ .el-image__inner {
            max-height: 100%;
            max-width: 100%;
            height: unset;
            width: unset;
          }
        }
      }
      .btn {
        display: flex;
        align-items: flex-end;
        justify-content: flex-end;
      }
    }
  }
  /deep/ .el-dialog {
    width: 1083px;
  }
  /deep/ .el-dialog__body {
    padding: 20px;
    padding-top: 0;
    display: flex;
    flex-direction: column;
  }
}
</style>
