<template>
  <div class="song-back-tunnl animate__animated animate__fadeIn">
    <!-- <el-row class="y-condition-dow"> -->
    <div class="song-conditun-dotun">
      <div>
        <el-button
          v-if="addOpt && roleID > 1"
          type="primary"
          @click="openAddEditDialog(1)"
          >新增隧道</el-button
        >
      </div>

      <div class="searchclass">
        <el-input
          placeholder="关键词"
          v-model="keyword"
          clearable
          class="song-el-select"
          :maxlength="30"
        ></el-input>

        <el-button type="primary" @click="getList"> 搜索 </el-button>
      </div>
    </div>
    <br />
    <!-- 数据展示区 -->
    <el-row>
      <el-col :span="24">
        <div class="sysTable">
          <normalTable
            :titleList="titleList"
            :tableData="tableData"
            :opList="opList"
            :pageNum="page"
            @tableClick="tableClick"
          ></normalTable>
        </div>
      </el-col>
    </el-row>

    <el-pagination
      class="pageNation"
      background
      :current-page.sync="page"
      :page-size="10"
      :pager-count="5"
      layout="total, prev, pager, next"
      :total="dataTotal"
      @current-change="handleCurrentChange"
    >
    </el-pagination>

    <!-- 新建模态框-->
    <el-dialog
      class="add"
      id="addCLfTu"
      :title="addEditDialogTitle"
      :visible.sync="addEditDialogShow"
      :before-close="closeAddEditDialog"
      width="48%"
      :modal-append-to-body="false"
      :close-on-click-modal="false"
    >
      <el-form
        v-if="addEditDialogShow"
        ref="tunnelForm"
        :rules="tunnelRule"
        :model="tunnelModel"
        label-width="125px"
        style="padding-right: 40px"
      >
        <el-col :span="12">
          <el-form-item label="隧道代码:" prop="code">
            <el-input
              v-model.trim="tunnelModel.code"
              :maxlength="30"
              placeholder="30字以内"
            ></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="隧道名称:" prop="name">
            <el-input
              v-model.trim="tunnelModel.name"
              :maxlength="30"
              placeholder="30字以内"
            ></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="隧道编号:" prop="number">
            <el-input
              v-model.trim="tunnelModel.number"
              :maxlength="30"
              placeholder="30字以内"
            ></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="所属区域:" required>
            <el-cascader
              ref="cascaderSel"
              v-model="areaValue"
              class="roadHierCl"
              :options="areaList"
              clearable
              :props="areaProps"
              separator="-"
            ></el-cascader>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="相关责任人:">
            <el-input
              v-model.trim="tunnelModel.chargeName"
              :maxlength="30"
              placeholder="30字以内"
            ></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="所在路名:">
            <el-input
              v-model.trim="tunnelModel.roadName"
              :maxlength="30"
              placeholder="30字以内"
            ></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="建成年月:" prop="buildTime">
            <el-date-picker
              v-model="tunnelModel.buildTime"
              type="month"
              value-format="yyyy-MM"
              placeholder="选择年月"
              class="buildTimeCl"
            ></el-date-picker>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="养护单位:" prop="maintainDepartment">
            <el-input
              v-model.trim="tunnelModel.maintainDepartment"
              :maxlength="30"
              placeholder="30字以内"
            ></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="设计荷载:" prop="designLoad">
            <el-input
              v-model.trim="tunnelModel.designLoad"
              :maxlength="30"
              placeholder="30字以内"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="隧道等级:" prop="spanType">
            <el-select
              v-model="tunnelModel.spanType"
              placeholder="隧道等级"
              class="roadHierCl"
            >
              <el-option
                v-for="item in roadHierarchySelect"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="经度:" prop="longitude">
            <el-input
              v-model="tunnelModel.longitude"
              :maxlength="30"
              placeholder="30字以内"
            ></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="纬度:" prop="latitude">
            <el-input
              v-model="tunnelModel.latitude"
              :maxlength="30"
              placeholder="30字以内"
            ></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="技术状况:" prop="technology">
            <el-select
              v-model="tunnelModel.technology"
              class="roadHierCl"
              placeholder="技术状况"
            >
              <el-option
                v-for="item in technologyList"
                :key="item.value"
                :label="item.label"
                :value="item.label"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="状况等级:">
            <el-select
              v-model="tunnelModel.grade"
              class="roadHierCl"
              placeholder="状况等级"
              clearable
            >
              <el-option
                v-for="item in gradeList"
                :key="item.value"
                :label="item.label"
                :value="item.label"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="所属单位:" prop="unitId" v-if="showProprietors">
            <el-select
              v-model="tunnelModel.unitId"
              class="rodeCl"
              placeholder="所属单位"
              :disabled="addEditDialogTitle == '修改隧道'"
              style="width: 100%"
            >
              <el-option
                v-for="item in inProprietors"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-form>
      <div class="submitBri">
        <el-button type="primary" @click="tunnelSubmit('tunnelForm')"
          >确定</el-button
        >
        <el-button @click="closeAddEditDialog">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  delTunnel,
  getTunnelList,
  addTunnel,
  updTunnel
} from '@/api/infomanage/tunnel';
import { getUnitByRole } from '@/api/infomanage/unitManage';
import normalTable from '@/components/table/normalTable';
import areaData from '@/assets/json/area.json';
export default {
  name: 'tunnelManage',
  components: { normalTable },
  data() {
    //经度校验
    var validateLon = (rule, value, callback) => {
      var regExp =
        /^-?((0|1?[0-7]?[0-9]?)(([.][0-9]{1,6})?)|180(([.][0]{1,6})?))$/;
      if (!regExp.test(value)) {
        callback(new Error('经度整数部分为-180~180,小数部分为0-6位！'));
      } else {
        callback();
      }
    };
    //维度校验
    var validateLat = (rule, value, callback) => {
      var regExp =
        /^-?((0|[1-8]?[0-9]?)(([.][0-9]{1,6})?)|90(([.][0]{1,6})?))$/;
      if (!regExp.test(value)) {
        callback(new Error('纬度整数部分为-90~90,小数部分为0-6位！'));
      } else {
        callback();
      }
    };
    return {
      selParams: {
        keyword: '',
        maintainDepartment: '',
        pageNum: 1,
        pageSize: 10
      },

      userInfo: {
        startDate: '',
        endDate: ''
      },
      roleID: this.$store.getters.getRoleInfo.id,

      xgg: true,
      xxql: true,
      sc: true,
      qd: true,

      //展示业主
      showProprietors: true,

      loading: true,

      //道路等级（隧道等级）下拉框
      roadHierarchySelect: [
        { value: '特长隧道', label: '特长隧道' },
        { value: '长隧道', label: '长隧道' },
        { value: '中隧道', label: '中隧道' },
        { value: '短隧道', label: '短隧道' }
      ],
      search: {
        id: '',
        key: ''
      },
      latitude: '',
      longitude: '',
      //添加记录的变量
      tunnelModel: {
        code: '',
        name: '',
        roadName: '',
        buildTime: '',
        maintainDepartment: '',
        designLoad: '',
        spanType: '',
        number: '',
        chargeName: '',
        latitude: '',
        longitude: '',
        unitId: '',

        createUserId: '',

        technology: '',
        grade: ''
      },

      //添加隧道信息校验
      tunnelRule: {
        code: [{ required: true, message: '请输入隧道代码', trigger: 'blur' }],
        name: [{ required: true, message: '请输入隧道名称', trigger: 'blur' }],
        number: [
          { required: true, message: '请输入隧道编号', trigger: 'blur' }
        ],
        buildTime: [
          {
            type: 'string',
            required: true,
            message: '请选择建成年月',
            trigger: 'change'
          }
        ],
        maintainDepartment: [
          { required: true, message: '请输入养护单位', trigger: 'blur' }
        ],
        designLoad: [
          { required: true, message: '请输入设计荷载', trigger: 'blur' }
        ],
        spanType: [
          { required: true, message: '请选择隧道等级', trigger: 'change' }
        ],
        latitude: [
          { required: true, message: '请输入维度', trigger: 'blur' },
          { validator: validateLat, trigger: 'blur' }
        ],
        longitude: [
          { required: true, message: '请输入经度', trigger: 'blur' },
          { validator: validateLon, trigger: 'blur' }
        ],

        technology: [
          { required: true, message: '请选择技术状况', trigger: 'change' }
        ],
        grade: [
          { required: true, message: '请选择状况等级', trigger: 'change' }
        ],
        unitId: [
          { required: true, message: '请选择所属单位', trigger: 'change' }
        ]
      },

      areaList: areaData,
      areaProps: {
        value: 'id',
        label: 'name'
      }, //所属区域动态加载
      areaValue: '', //所属区域

      //表格数据
      titleList: [
        { id: 2, prop: 'code', label: '隧道代码', width: '' },
        { id: 3, prop: 'name', label: '隧道名称', width: '' },
        { id: 4, prop: 'number', label: '隧道编号', width: '' },
        { id: 5, prop: 'roadName', label: '所在路名', width: '' },
        { id: 6, prop: 'buildTime', label: '建成年月', width: '' },
        { id: 7, prop: 'maintainDepartment', label: '养护单位', width: '' },
        { id: 8, prop: 'spanType', label: '跨径分类', width: '' }
      ],
      tableData: [
        // {
        //   id: 1,
        //   code: '350902SD001',
        //   name: '福宁一号隧道',
        //   number: '000001',
        //   roadName: '福宁路',
        //   buildTime: '2014-09',
        //   maintainDepartment: '福建省建筑工程质量检测中心有限公司',
        //   spanType: '短隧道'
        // }
      ],
      opList: [
        { id: 1, name: '详情', opShow: true, disabled: '', type: 'checkOpt' },
        { id: 2, name: '修改', opShow: true, disabled: '', type: 'updateOpt' },
        { id: 3, name: '删除', opShow: true, disabled: '', type: 'deleteOpt' }
      ],
      id: '',

      //记录总数
      dataTotal: 0,
      //当前页
      page: 1,

      //一页的条数
      size: '10',
      //选择隧道
      name: '',
      //编号
      number: '',
      //关键字
      keyword: '',

      //添加记录的模态框值
      addEditDialogShow: false,
      addEditDialogTitle: '',
      //养护单位下拉框
      maintainDepartment: '',
      structureNameList: [],

      //所属单位下拉框
      proprietor: '',
      inProprietors: [{ id: 1, username: 'admin' }],
      username: '',

      roleName: '',
      userID: '',
      //桥隧技术状况
      technologyList: [
        { value: 1, label: '公路桥隧技术状况' },
        { value: 2, label: '城市桥隧技术状况' }
      ],
      gradeList: [
        { value: 1, label: '1类' },
        { value: 2, label: '2类' },
        { value: 3, label: '3类' },
        { value: 4, label: '4类' },
        { value: 5, label: '5类' }
      ],
      needRefresh: true //是否需要刷新
    };
  },
  beforeMount() {
    this.$utils.getAuthPage(this); //获取权限
  },
  beforeRouteLeave(to, from, next) {
    if (to.name === '隧道信信息详情') {
      this.needRefresh = false;
    } else {
      this.needRefresh = true;
    }
    next();
  },
  activated() {
    if (this.needRefresh) {
      this.getList();
    }
  },
  mounted() {
    this.showProprietors = this.roleID == 3;
    this.getList();
    if (this.$store.getters.getRoleInfo.id < 2) {
      this.opList[1].opShow = false;
      this.opList[2].opShow = false;
    }
    this.$nextTick(async () => {
      if (this.showProprietors) {
        let { data } = await getUnitByRole(2);
        this.inProprietors = data;
      }
    });
  },
  methods: {
    //加载列表
    async getList() {
      let params = {
        keyword: this.keyword,
        maintainDepartment: this.maintainDepartment,
        pageNum: 1,
        pageSize: 10
      };
      //请求后台数据
      let { code, data } = await getTunnelList(params);
      if (code == '0000') {
        this.page = 1;
        this.tableData = data.list;
        this.dataTotal = data.total;
        this.selParams = params;
      }
    },
    async getList2() {
      //请求后台数据
      let { code, data } = await getTunnelList(this.selParams);
      if (code == '0000') {
        this.tableData = data.list;
        this.dataTotal = data.total;
      }
    },
    //查看桥梁详情
    tunnelDetails(data) {
      this.$router.push({
        path: '/infoManage/tunnelManageDetial',
        query: { id: data.id, structureName: data.name, name: '隧道信息管理' }
      });
    },
    // 添加\修改隧道
    tunnelSubmit(formName) {
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          if (this.areaValue.length == 0) {
            this.$message({
              message: '请选择所属区域！',
              type: 'warning',
              showClose: true,
              duration: 2000
            });
          }
          this.tunnelModel.provinceId = this.areaValue[0]; //省
          this.tunnelModel.cityId = this.areaValue[1] || ''; //市
          this.tunnelModel.countyId = this.areaValue[2] || ''; //区
          if (this.addEditDialogTitle == '新增隧道') {
            // if (!this.showProprietors) {
            //   this.tunnelModel.unitId = this.userID;
            // }
            let { code } = await addTunnel(this.tunnelModel);
            if (code == '0000') {
              this.$message({
                type: 'success',
                message: '添加隧道成功!',
                showClose: true
              });
              this.closeAddEditDialog();
              await this.getList2();
            }
          } else {
            let { code } = await updTunnel(this.tunnelModel);
            if (code == '0000') {
              this.$message({
                type: 'success',
                message: '修改隧道成功!',
                showClose: true
              });
              this.closeAddEditDialog();
              await this.getList2();
            }
          }
        }
      });
    },
    //删除点击事件
    tunnelDelete(data) {
      //删除框
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        let { code } = await delTunnel(data.id);
        if (code == '0000') {
          this.$message({
            type: 'success',
            message: '删除成功!',
            showClose: true,
            duration: 2000
          });
          await this.getList2();
        }
      });
    },
    //表格点击事件
    tableClick(index, data) {
      if (index == 1) {
        this.tunnelDetails(data);
      } else if (index == 2) {
        this.tunnelUpdate(data);
      } else {
        this.tunnelDelete(data);
      }
    },
    //修改隧道基础信息弹出
    tunnelUpdate(data) {
      this.tunnelModel = JSON.parse(JSON.stringify(data));
      this.areaValue = [
        this.tunnelModel.provinceId,
        this.tunnelModel.cityId,
        this.tunnelModel.countyId
      ];
      this.openAddEditDialog(2);

      // if (this.tunnelModel.technology == '公路桥隧技术状况') {
      //   this.gradeList = [
      //     { value: 1, label: '1类' },
      //     { value: 2, label: '2类' },
      //     { value: 3, label: '3类' },
      //     { value: 4, label: '4类' },
      //     { value: 5, label: '5类' }
      //   ];
      // } else if (this.tunnelModel.technology == '城市桥隧技术状况') {
      //   this.gradeList = [
      //     { value: 1, label: 'A级' },
      //     { value: 2, label: 'B级' },
      //     { value: 3, label: 'C级' },
      //     { value: 4, label: 'D级' }
      //   ];
      // } else {
      //   this.gradeList = [];
      // }
    },
    gradeChange() {
      this.tunnelModel.grade = '';
      if (this.tunnelModel.technology == '公路桥隧技术状况') {
        this.gradeList = [
          { value: 1, label: '1类' },
          { value: 2, label: '2类' },
          { value: 3, label: '3类' },
          { value: 4, label: '4类' },
          { value: 5, label: '5类' }
        ];
      } else if (this.tunnelModel.technology == '城市桥隧技术状况') {
        this.gradeList = [
          { value: 1, label: 'A级' },
          { value: 2, label: 'B级' },
          { value: 3, label: 'C级' },
          { value: 4, label: 'D级' }
        ];
      } else {
        this.gradeList = [];
      }
    },
    async handleCurrentChange(val) {
      //重置当前页
      this.page = val;
      this.selParams.pageNum = val;
      //请求数据
      await this.getList2();
    },
    //新增、修改dialog打开 1:新增 2：修改
    openAddEditDialog(type) {
      if (type == 1) {
        this.addEditDialogTitle = '新增隧道';
      } else {
        this.addEditDialogTitle = '修改隧道';
      }
      this.addEditDialogShow = true;
    },
    //新增、修改dialog关闭
    closeAddEditDialog() {
      this.tunnelModel.id = null;
      this.tunnelModel.code = '';
      this.tunnelModel.name = '';
      this.tunnelModel.roadName = '';
      this.tunnelModel.buildTime = null;
      this.tunnelModel.maintainDepartment = '';
      this.tunnelModel.designLoad = '';
      this.tunnelModel.spanType = '';
      this.tunnelModel.number = '';
      this.tunnelModel.chargeName = '';
      this.tunnelModel.longitude = '';
      this.tunnelModel.latitude = '';
      this.tunnelModel.tunnelType = '';
      this.tunnelModel.unitId = '';
      this.tunnelModel.createUserId = null;
      this.tunnelModel.technology = '';
      this.tunnelModel.grade = '';
      this.$refs.cascaderSel.$refs.panel.clearCheckedNodes();
      this.$nextTick(() => {
        this.addEditDialogShow = false;
      });
    }
  }
};
</script>
<style lang="scss" scoped>
.search-buttontunne {
  margin-right: 64px;

  background-color: #419aff;
  border-radius: 4px;
  color: #fafafb;
}

.song-el-select {
  margin-right: 20px;
}

.el-select-dropdown__list {
  list-style: none;
  padding: 6px 0;
  margin: 0;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  background-color: #192436;
}

.y-condition-dow {
  padding-top: 20px;
  padding-bottom: 20px;
}
.y-col-1 {
  float: right;
  margin-right: 64px;
}
.y-col-0 {
  margin-left: 64px;
}

.song-table-tum {
  width: 100%;
  // padding-right: 64px;
  // padding-left: 64px;
  border: 0px;
  // margin-top: -14px !important;
}
.song-back-tunnl {
  height: 100%;
  padding: 20px;
}
.y-page {
  // margin-top: 15px;
}

.buildTimeCl {
  width: 100% !important;
}

/*分页器直接跳转界面*/
.el-input__inner {
  background-color: #192231;
  border: 1px solid #606266;
}

.el-input__inner {
  background-color: #141e30 !important;
  border: solid 1px #1a263d !important;
}

.submitTun {
  float: right;
}
</style>
<style lang="scss" scoped>
#addCLfTu .el-dialog__body {
  height: 600px;
}

#tunFy .el-pagination {
  margin-top: 10px;
}

#tunFy .el-tooltip__popper {
  width: 600px;
}
.add {
  .submitBri {
    padding-top: 10px;
    text-align: center;
  }
  /deep/ .el-dialog__body {
    display: flex;
    flex-direction: column;
  }
  /deep/ .el-form {
    display: flex;
    flex-wrap: wrap;
  }
}
</style>

<style lang="scss" scoped>
.search-buttontun {
  margin-right: 64px;

  background-color: #419aff;
  border-radius: 4px;
  color: #fafafb;
}

.search-button_tun[data-v-5ea6b57c] {
  margin-right: 66px;
}

.search-button_tun {
  width: 72px;
  height: 38px;
  background-color: #3874ecd5;
  box-shadow: 0px 4px 12px 0px rgba(6, 175, 133, 0.36);
  border-radius: 4px;
  font-size: 14px;
  color: #ffffff;
}

.search-buttontun[data-v-5ea6b57c] {
  /* margin-left:64px; */
}

.search-buttontun {
  /*  width: 88px; */
  height: 40px;
  background-color: #3773ec;
  border: solid 1px #3873ec;
  box-shadow: 0px 4px 12px 0px rgba(33, 164, 150, 0.4);
  /* margin-left:64px; */
}

.song-conditun-dotun {
  display: flex;
  justify-content: space-between;
  .searchclass {
    display: flex;
  }
}

.topCl {
  float: left;
  padding-left: 62px;
  width: 100% !important;
}

.rightCl {
  float: right;
  padding-right: 4%;
}

.addTunnStCl {
  float: left;
  /* padding-right:457px; */

  /* padding-right:730px; */
}

.roadHierCl {
  width: 100% !important;
}

.el-col-20 {
  width: 99.77333%;
}
.pageNation {
  padding: 1vw 0;
  text-align: center;
  /deep/ button {
    background: transparent;
    border: 1px solid #d9d9d9;
    border-radius: 2px;
    background: none !important;
  }
  /deep/ .el-pager li {
    background: transparent;
    border: 1px solid #d9d9d9;
    border-radius: 2px;
  }
  /deep/ .el-pager .active {
    background: none !important;
    color: #1890ff !important;
    border: 1px solid #1890ff;
  }
}

.sysTable {
  //   height: 95%;
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
    div {
      cursor: pointer;
    }
  }
  /deep/ .el-table {
    height: 100%;
  }
  /deep/ .el-table th {
    font-size: 0.7vw;
    font-weight: bold;
    color: #333;
    padding: 1.669vh 0;
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
  /deep/ .el-table td {
    font-size: 0.7vw;
    color: #333;
    padding: 1.669vh 0;
  }
  /deep/ .el-table .cell.el-tooltip {
    width: unset !important;
  }
}
</style>
