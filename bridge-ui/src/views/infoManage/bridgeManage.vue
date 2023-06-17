<template>
  <div class="s-back-a animate__animated animate__fadeIn">
    <!-- <el-row class="song-condition-dow"> -->

    <div class="song-conditi-do">
      <div>
        <el-button
          v-if="addOpt && roleID > 1"
          type="primary"
          @click="showDialog(1)"
          >新增桥梁</el-button
        >
      </div>

      <div class="searchclass">
        <el-input
          placeholder="关键词"
          v-model="keyword"
          clearable
          class="y-el-select"
          :maxlength="30"
        ></el-input>
        <el-button type="primary" @click="getList">搜索</el-button>
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

    <!-- 分页器 -->
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

    <!-- 新增模态框 -->
    <el-dialog
      class="add"
      :title="brdgeDialogTitle"
      :visible.sync="addBridgeIns"
      width="48%"
      height="100%"
      :modal-append-to-body="false"
      :before-close="dialogClose"
      :close-on-click-modal="false"
    >
      <el-form
        v-if="addBridgeIns"
        ref="addform"
        :model="addObj"
        :rules="bridgeRule"
        class="add-model"
      >
        <el-form-item label="桥梁代码:" prop="code">
          <el-input
            class="formItem"
            v-model="addObj.code"
            :maxlength="30"
            placeholder="30字以内"
          ></el-input>
        </el-form-item>

        <el-form-item label="桥梁名称:" prop="name">
          <el-input
            class="formItem"
            v-model="addObj.name"
            :maxlength="30"
            placeholder="30字以内"
          ></el-input>
        </el-form-item>

        <el-form-item label="桥梁编号:" prop="number">
          <el-input
            class="formItem"
            v-model="addObj.number"
            :maxlength="30"
            placeholder="30字以内"
          ></el-input>
        </el-form-item>

        <el-form-item label="所属区域:" required>
          <el-cascader
            ref="cascaderSel"
            v-model="areaValue"
            class="rodeCl"
            :options="areaList"
            :props="areaProps"
            separator="-"
            @change="areaChange"
          ></el-cascader>
        </el-form-item>

        <el-form-item label="相关责任人:">
          <el-input
            class="formItem"
            v-model="addObj.chargeName"
            :maxlength="30"
            placeholder="30字以内"
          ></el-input>
        </el-form-item>

        <el-form-item label="责任人电话:">
          <el-input
            class="formItem"
            v-model="addObj.chargePhone"
            :maxlength="13"
            placeholder="请输入责任人电话"
          ></el-input>
        </el-form-item>

        <el-form-item label="所在路名:">
          <el-input
            class="formItem"
            v-model="addObj.roadName"
            :maxlength="30"
            placeholder="30字以内"
          ></el-input>
        </el-form-item>

        <el-form-item label="建成年月:">
          <el-date-picker
            v-model="addObj.buildTime"
            type="month"
            value-format="yyyy-MM"
            placeholder="选择年月"
            class="formItem"
          ></el-date-picker>
        </el-form-item>

        <el-form-item label="养护单位:" prop="maintainDepartment">
          <el-input
            class="formItem"
            v-model="addObj.maintainDepartment"
            :maxlength="30"
            placeholder="30字以内"
          ></el-input>
        </el-form-item>

        <el-form-item label="养护单位电话:">
          <el-input
            class="formItem"
            v-model="addObj.maintainDepartPhone"
            placeholder="请输入养护单位电话"
            :maxlength="13"
          ></el-input>
        </el-form-item>

        <el-form-item label="养护等级:" prop="maintainGrade">
          <el-select
            v-model="addObj.maintainGrade"
            class="rodeCl"
            placeholder="请选择"
          >
            <el-option
              v-for="item in maintainGradeList"
              :key="item.id"
              :label="item.label"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="养护类别:" prop="maintainCategory">
          <el-select
            v-model="addObj.maintainCategory"
            class="rodeCl"
            placeholder="请选择"
          >
            <el-option
              v-for="item in maintainCategoryList"
              :key="item.id"
              :label="item.label"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="设计荷载:" prop="designLoad">
          <el-input
            class="formItem"
            v-model="addObj.designLoad"
            :maxlength="30"
            placeholder="30字以内"
          ></el-input>
        </el-form-item>

        <el-form-item label="单孔跨径分类:" prop="spanType">
          <el-select
            v-model="addObj.spanType"
            class="rodeCl"
            placeholder="请选择"
          >
            <el-option
              v-for="item in roadHierarchySelect"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="经度:" prop="longitude">
          <el-input
            class="formItem"
            v-model="addObj.longitude"
            :maxlength="30"
            placeholder="30字以内"
          ></el-input>
        </el-form-item>

        <el-form-item label="纬度:" prop="latitude">
          <el-input
            class="formItem"
            v-model="addObj.latitude"
            :maxlength="30"
            placeholder="30字以内"
          ></el-input>
        </el-form-item>

        <el-form-item label="桥梁类型:" prop="bridgeType">
          <el-select
            v-model="addObj.bridgeType"
            class="rodeCl"
            placeholder="请选择"
          >
            <el-option
              v-for="item in bridgeTypeSelect"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>

        <!-- <el-form-item label="管理单位:">
          <el-input
            class="formItem"
            v-model="addObj.runningDepartment"
            :maxlength="30"
            placeholder="30字以内"
          ></el-input>
        </el-form-item> -->

        <!-- <el-form-item label="监理单位:" prop="supervisionDepartment">
          <el-input
            class="formItem"
            v-model="addObj.supervisionDepartment"
            :maxlength="30"
            placeholder="30字以内"
          ></el-input>
        </el-form-item>

        <el-form-item label="建设单位:" prop="buildingDepartment">
          <el-input
            class="formItem"
            v-model="addObj.buildingDepartment"
            :maxlength="30"
            placeholder="30字以内"
          ></el-input>
        </el-form-item>

        <el-form-item label="施工单位:" prop="constructionDepartment">
          <el-input
            class="formItem"
            v-model="addObj.constructionDepartment"
            :maxlength="30"
            placeholder="30字以内"
          ></el-input>
        </el-form-item> -->

        <el-form-item label="技术状况:" prop="technology">
          <el-select
            v-model="addObj.technology"
            class="rodeCl"
            @change="addGradeChange()"
            placeholder="请选择"
          >
            <el-option
              v-for="item in technologyList"
              :key="item.value"
              :label="item.label"
              :value="item.label"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="状况等级:">
          <el-select
            v-model="addObj.grade"
            class="rodeCl"
            placeholder="请选择"
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

        <el-form-item v-if="roleID == 3" label="所属单位:" prop="unitId">
          <el-select
            v-model="addObj.unitId"
            class="rodeCl"
            clearable
            :disabled="brdgeDialogTitle == '修改桥梁'"
            placeholder="请选择"
          >
            <el-option
              v-for="item in inProprietors"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div class="submitBri">
        <el-button type="primary" @click="addSubmit('addform')">确定</el-button>
        <el-button @click="dialogClose">取消</el-button>
        <!-- <el-button type="primary" @click="addBriDetial(this.addObj.id)">新增桥梁详情</el-button> -->
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  delBridge,
  getBridgeList,
  addBridge,
  updbridge
} from '@/api/infomanage/bridgeManage';
import { getUnitByRole } from '@/api/infomanage/unitManage';
import normalTable from '@/components/table/normalTable';
import areaData from '@/assets/json/area.json';
export default {
  name: 'bridgeManage',
  components: { normalTable },
  data() {
    //经度校验
    var validateLon = (rule, value, callback) => {
      var regExp =
        /^(\-|\+)?(((\d|[1-9]\d|1[0-7]\d|0{1,3})\.\d{0,15})|(\d|[1-9]\d|1[0-7]\d|0{1,3})|180\.0{0,15}|180)$/;
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
    //电话校验
    var checkPhone = (rule, value, callback) => {
      let phoneReg = /^1[3|4|5|7|8][0-9]{9}$/;
      // Number.isInteger是es6验证数字是否为整数的方法,但是我实际用的时候输入的数字总是识别成字符串
      // 所以我就在前面加了一个+实现隐式转换

      if (!Number.isInteger(+value)) {
        callback(new Error('请输入数字值'));
      } else {
        if (phoneReg.test(value)) {
          callback();
        } else {
          callback(new Error('电话号码格式不正确'));
        }
      }
    };
    return {
      selParams: {
        keyword: '',
        maintainDepartment: '',
        pageNum: 1,
        pageSize: 10
      },
      roleID: this.$store.getters.getRoleInfo.id,
      //添加桥梁信息校验
      bridgeRule: {
        code: [{ required: true, message: '请输入桥梁代码', trigger: 'blur' }],
        name: [{ required: true, message: '请输入桥梁名称', trigger: 'blur' }],
        number: [
          { required: true, message: '请输入桥梁编号', trigger: 'blur' }
        ],
        // buildTime: [
        //   {
        //     type: 'string',
        //     required: true,
        //     message: '请选择建成年月',
        //     trigger: 'change'
        //   }
        // ],
        maintainDepartment: [
          { required: true, message: '请输入养护单位', trigger: 'blur' }
        ],
        designLoad: [
          { required: true, message: '请输入设计荷载', trigger: 'blur' }
        ],
        spanType: [
          { required: true, message: '请选择桥梁等级', trigger: 'change' }
        ],
        areaValue: [
          { required: true, message: '请选择所属区域', trigger: 'change' }
        ],
        latitude: [
          { required: true, message: '请输入维度', trigger: 'blur' },
          { validator: validateLat, trigger: 'blur' }
        ],
        longitude: [
          { required: true, message: '请输入经度', trigger: 'blur' },
          { validator: validateLon, trigger: 'blur' }
        ],
        bridgeType: [
          { required: true, message: '请选择桥梁类型', trigger: 'change' }
        ],
        // runningDepartment: [
        //   { required: true, message: '请输入管理单位', trigger: 'blur' }
        // ],
        // supervisionDepartment: [
        //   { required: true, message: '请输入监理单位', trigger: 'blur' }
        // ],
        // buildingDepartment: [
        //   { required: true, message: '请输入建设单位', trigger: 'blur' }
        // ],
        // constructionDepartment: [
        //   { required: true, message: '请输入施工单位', trigger: 'blur' }
        // ],
        technology: [
          { required: true, message: '请选择技术状况', trigger: 'change' }
        ],
        // grade: [
        //   { required: true, message: '请选择状况等级', trigger: 'change' }
        // ],
        unitId: [
          { required: true, message: '请选择所属单位', trigger: 'change' }
        ],
        // chargePhone: [{ validator: checkPhone, trigger: 'blur' }],
        // maintainDepartPhone: [{ validator: checkPhone, trigger: 'blur' }],
        maintainGrade: [
          { required: true, message: '请选择养护等级', trigger: 'change' }
        ],
        maintainCategory: [
          { required: true, message: '请选择养护类别', trigger: 'change' }
        ]
      },
      areaList: areaData,
      areaProps: {
        value: 'id',
        label: 'name'
      }, //所属区域动态加载
      areaValue: [], //所属区域
      maintainGradeList: [
        { id: 1, label: 'Ⅰ等养护' },
        { id: 2, label: 'Ⅱ等养护' },
        { id: 3, label: 'Ⅲ等养护' }
      ], //养护等级
      maintainCategoryList: [
        { id: 1, label: 'Ⅰ类养护' },
        { id: 2, label: 'Ⅱ类养护' },
        { id: 3, label: 'Ⅲ类养护' },
        { id: 4, label: 'Ⅳ类养护' },
        { id: 5, label: 'Ⅴ类养护' }
      ], //养护类别
      //两个联动下拉框
      goodsCircle: {
        title: '',
        authorInfo: {},
        author: '',
        authorImg: '',
        content: '',
        favoritesId: null,
        numIids: []
      },
      favorites: [],
      localFavorites: [],
      listQuery: {
        liilType: 2, //1,为系统基础配置,不可调节;2暂定为朋友圈选品库,不传查询全部
        enableStatus: 1, //：启用状态（默认查询启用）；-1废弃
        authorStatus: 1 //1,有效的发布者
      },

      xxql: true, // 新增
      sc: true,
      qd: true,
      xgg: true,

      powerId: '',
      powerName: '',
      routerUrl: '',

      //展示业主
      showProprietors: true,
      // disabled :false,
      //加载遮盖框
      loading: true,

      //道路等级下拉框
      roadHierarchySelect: [
        { value: '特大桥', label: '特大桥' },
        { value: '大桥', label: '大桥' },
        { value: '中桥', label: '中桥' },
        { value: '小桥', label: '小桥' }
      ],

      //桥梁类型下拉框
      bridgeTypeSelect: [
        { value: 1, label: '梁桥' },
        { value: 2, label: '拱桥' },
        { value: 3, label: '刚架拱桥' },
        { value: 4, label: '悬索桥' },
        { value: 5, label: '斜拉桥 ' },
        { value: 6, label: '钢管混凝土拱桥' }
      ],

      number: '',
      search: {
        id: '',
        key: ''
      },
      buildTime: '',
      latitude: '',
      longitude: '',
      //添加记录的变量
      addObj: {
        bridgeType: '',
        buildTime: '',
        buildingDepartment: '',
        chargeName: '',
        chargePhone: '',
        city: '',
        code: '',
        constructionDepartment: '',
        county: '',
        createTime: '',
        createUserId: '',
        designLoad: '',
        grade: '',
        id: '',
        latitude: '',
        longitude: '',
        maintainDepartPhone: '',
        maintainDepartment: '',
        maintainGrade: '',
        modifyTime: '',
        name: '',
        number: '',
        projectId: '',
        province: '',
        roadName: '',
        runningDepartment: '',
        spanType: '',
        status: '',
        structureType: '',
        supervisionDepartment: '',
        technology: '',
        unitId: ''
      },

      //表格数据
      titleList: [
        { id: 2, prop: 'code', label: '桥梁代码', width: '' },
        { id: 3, prop: 'name', label: '桥梁名称', width: '' },
        { id: 4, prop: 'number', label: '桥梁编号', width: '' },
        { id: 5, prop: 'roadName', label: '所在路名', width: '' },
        { id: 6, prop: 'buildTime', label: '建成年月', width: '' },
        { id: 7, prop: 'maintainDepartment', label: '养护单位', width: '' },
        { id: 8, prop: 'spanType', label: '跨径分类', width: '' }
      ],
      tableData: [
        // {
        //   id: 1,
        //   code: '350982LC002',
        //   name: '新桐山大桥',
        //   number: '002',
        //   roadName: '古城东路',
        //   buildTime: '2003-12',
        //   maintainDepartment: '福建省建筑科学研究院有限公司',
        //   spanType: '大桥'
        // }
      ],
      opList: [
        { id: 1, name: '详情', opShow: false, disabled: '', type: 'checkOpt' },
        { id: 2, name: '修改', opShow: false, disabled: '', type: 'updateOpt' },
        { id: 3, name: '删除', opShow: false, disabled: '', type: 'deleteOpt' }
      ],
      id: '',

      // jurisdictionContent:[],
      //记录总数
      dataTotal: 0,
      //当前页
      page: 1,

      //一页的条数
      size: '10',
      //选择桥梁
      name: '',
      // code: "",
      //关键字
      keyword: '',

      brdgeDialogTitle: '',
      //添加记录的模态框值
      addBridgeIns: false,
      //养护单位下拉框
      maintainDepartment: '',

      //所属单位下拉框
      proprietor: '',
      inProprietors: [],
      username: '',

      //桥梁名称下拉框
      structureNameList: [],

      roleName: '',

      //桥隧技术状况
      technologyList: [
        { value: 1, label: '公路桥隧技术状况' },
        { value: 2, label: '城市桥隧技术状况' }
      ],
      gradeList: [],

      userInfo: {
        startDate: '',
        endDate: ''
      },
      yaddPower: false,
      yupdatePower: false,
      yselPower: false,

      // YY_BOX_MASK:YYstyles.YY_BOX_MASK,
      needRefresh: true //页面是否需要刷新
    };
  },

  computed: {
    contentShortLength() {
      return this.goodsCircle.content.length;
    }
  },
  beforeMount() {
    this.$utils.getAuthPage(this); //获取权限
  },
  beforeRouteLeave(to, from, next) {
    if (to.name === '桥梁信息详情') {
      this.needRefresh = false;
    } else {
      this.needRefresh = true;
    }
    next();
  },
  activated() {
    if (this.needRefresh) {
      this.getList();
      this.$nextTick(async () => {
        if (this.showProprietors) {
          let { data } = await getUnitByRole(2);
          this.inProprietors = data;
        }
      });
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
      let { code, data } = await getBridgeList(params);
      if (code == '0000') {
        this.page = 1;
        this.tableData = data.list;
        this.dataTotal = data.total;
        this.selParams = params;
      }
    },
    async getList2() {
      let { code, data } = await getBridgeList(this.selParams);
      if (code == '0000') {
        this.tableData = data.list;
        this.dataTotal = data.total;
      }
    },
    showDialog(index, data) {
      if (index == 1) {
        this.brdgeDialogTitle = '新增桥梁';
      } else {
        this.brdgeDialogTitle = '修改桥梁';
        this.addObj = JSON.parse(JSON.stringify(data));
        this.areaValue = [
          this.addObj.provinceId,
          this.addObj.cityId,
          this.addObj.countyId
        ];
      }
      this.addBridgeIns = true;
    },
    dialogClose() {
      this.$refs.cascaderSel.$refs.panel.clearCheckedNodes();
      this.addObj = {
        bridgeType: '',
        buildTime: '',
        buildingDepartment: '',
        chargeName: '',
        chargePhone: '',
        city: '',
        code: '',
        constructionDepartment: '',
        county: '',
        createTime: '',
        createUserId: '',
        designLoad: '',
        grade: '',
        id: '',
        latitude: '',
        longitude: '',
        maintainDepartPhone: '',
        maintainDepartment: '',
        maintainGrade: '',
        modifyTime: '',
        name: '',
        number: '',
        projectId: '',
        province: '',
        roadName: '',
        runningDepartment: '',
        spanType: '',
        status: '',
        structureType: '',
        supervisionDepartment: '',
        technology: '',
        unitId: ''
      };
      this.$nextTick(() => {
        this.addBridgeIns = false;
      });
    },
    //选择所属区域
    areaChange(list) {
      // console.log(list);
    },
    //新增技术状况选择
    addGradeChange(state) {
      if (!state) {
        this.addObj.grade = '';
      }
      if (this.addObj.technology == '公路桥隧技术状况') {
        this.gradeList = [
          { value: 1, label: '1类' },
          { value: 2, label: '2类' },
          { value: 3, label: '3类' },
          { value: 4, label: '4类' },
          { value: 5, label: '5类' }
        ];
      } else if (this.addObj.technology == '城市桥隧技术状况') {
        this.gradeList = [
          { value: 1, label: 'A级' },
          { value: 2, label: 'B级' },
          { value: 3, label: 'C级' },
          { value: 4, label: 'D级' },
          { value: 5, label: 'E级' },
          { value: 6, label: '合格级' },
          { value: 7, label: '不合格级' }
        ];
      } else {
        this.gradeList = [];
      }
    },
    //查看桥梁详情
    bridgeDetails(data) {
      this.$router.push({
        path: '/infoManage/bridgeManageDetial',
        query: { id: data.id, structureName: data.name, name: '桥梁信息管理' }
      });
    },
    // 新增提交桥梁
    addSubmit(formName) {
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          let phoneReg = /^1[3|4|5|7|8][0-9]{9}$/;
          let phoneReg2 = /^(0[0-9]{2,3}\-)([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/;
          if (!!this.addObj.chargePhone) {
            if (
              (!Number.isInteger(+this.addObj.chargePhone) ||
                !phoneReg.test(this.addObj.chargePhone)) &&
              this.addObj.chargePhone.length <= 11
            ) {
              this.$message({
                message: '责任人手机号码格式不正确！',
                type: 'warning',
                showClose: true,
                duration: 2000
              });
              return;
            } else if (
              !phoneReg2.test(this.addObj.chargePhone) &&
              this.addObj.chargePhone.length > 11
            ) {
              this.$message({
                message: '责任人座机格式不正确！',
                type: 'warning',
                showClose: true,
                duration: 2000
              });
              return;
            }
          }
          if (!!this.addObj.maintainDepartPhone) {
            if (
              (!Number.isInteger(+this.addObj.maintainDepartPhone) ||
                !phoneReg.test(this.addObj.maintainDepartPhone)) &&
              this.addObj.maintainDepartPhone.length <= 11
            ) {
              this.$message({
                message: '养护单位手机格式不正确！',
                type: 'warning',
                showClose: true,
                duration: 2000
              });
              return;
            } else if (
              !phoneReg2.test(this.addObj.maintainDepartPhone) &&
              this.addObj.maintainDepartPhone.length > 11
            ) {
              this.$message({
                message: '养护单位座机格式不正确！',
                type: 'warning',
                showClose: true,
                duration: 2000
              });
              return;
            }
          }
          if (this.areaValue.length == 0) {
            this.$message({
              message: '请选择所属区域！',
              type: 'warning',
              showClose: true,
              duration: 2000
            });
            return;
          }
          this.addObj.provinceId = this.areaValue[0]; //省
          this.addObj.cityId = this.areaValue[1] || ''; //市
          this.addObj.countyId = this.areaValue[2] || ''; //区
          if (this.brdgeDialogTitle == '新增桥梁') {
            let { code } = await addBridge(this.addObj);
            if (code == '0000') {
              this.$message({
                message: '新增桥梁成功！',
                type: 'success',
                showClose: true
              });
              this.dialogClose();
              await this.getList2();
            }
          } else {
            let { code } = await updbridge(this.addObj);
            if (code == '0000') {
              this.$message({
                message: '修改桥梁成功！',
                type: 'success',
                showClose: true
              });
              this.dialogClose();
              await this.getList2();
            }
          }
        }
      });
    },
    //删除点击事件
    bridgeDelete(data) {
      this.$confirm(
        '此操作将永久删除该桥梁以及所有与其相关的信息, 是否继续?',
        '提示',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }
      ).then(async () => {
        let id = data.id;
        let { code } = await delBridge(id);
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
        this.bridgeDetails(data);
      } else if (index == 2) {
        this.showDialog(2, data);
        this.addGradeChange(true);
      } else if (index == 3) {
        this.bridgeDelete(data);
      }
    },
    //分页器事件
    async handleCurrentChange(val) {
      //重置当前页
      this.page = val;
      this.selParams.pageNum = val;
      //请求数据
      await this.getList2();
    }
  }
};
</script>
<style lang="scss" scoped>
.s-back-a {
  height: 100%;
  padding: 20px;
  display: flex;
  flex-direction: column;
  .song-conditi-do {
    display: flex;
    justify-content: space-between;
    .searchclass {
      display: flex;
      .y-el-select {
        margin-right: 20px;
      }
    }
  }
}

.el-select > .el-input {
  display: block;
  padding-left: 0px;
}

.addBridgCl {
  float: left;
  margin-left: 0px;
}

.addBridgStCl {
  float: left;
}

.addBridgDaoCl {
  float: left;
}

.el-col-20 {
  width: 99.77333%;
}

.topCl {
  float: left;
  padding-left: 62px;
  width: 100% !important;
}

.rightCl {
  float: right;
  padding-right: 3.8%;
}

.song-el-select {
  width: 150px;
}

.song-condition-dow {
  padding-top: 20px;
  padding-bottom: 20px;
}
/* .y-col-1 {
  float: right;
  margin-right: 64px;
} */
/* .y-col-0 {
  margin-left: 64px;
} */

.song-table-1 {
  width: 100%;
  // padding-right: 64px;
  //padding-left: 64px;
  border: 0px;
  // margin-top: -14px !important;
}

.song-page {
  margin-top: 15px;
}

.search-buttonbri[data-v-5ea6b57c] {
  // margin-left: 64px;
}

.search-buttonbri {
  height: 40px;
  // background-color: $YY_BUTTON_YES;
  border: solid 1px;
  box-shadow: 0px 4px 12px 0px rgba(33, 164, 150, 0.4);
  //margin-left: 64px;
}

.search-button_bri[data-v-5ea6b57c] {
  margin-right: 66px;
}

.search-button_bri {
  width: 72px;
  height: 38px;
  // background-color: $YY_BUTTON_YES;
  box-shadow: 0px 4px 12px 0px rgba(6, 175, 133, 0.36);
  border-radius: 4px;
  font-size: 14px;
  color: #ffffff;
}

.search-buttonbr {
  margin-right: 64px;
  background-color: #419aff;
  border-radius: 4px;
  color: #fafafb;
}

.submitBri {
  padding-top: 10px;
  text-align: center;
}
</style>

<style lang="scss" scoped>
#briFy .el-tooltip__popper {
  width: 600px;
}

.pageNation {
  text-align: center;
  padding: 1vw 0;
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
.add {
  .add-model {
    padding-right: 20px;
    display: flex;
    flex-wrap: wrap;
    .formItem,
    .rodeCl {
      width: 316px;
    }
  }
  /deep/ .el-form-item {
    width: 50%;
    display: flex;
    align-items: center;
    justify-content: flex-end;
  }
  /deep/ .el-form-item__content {
    margin-left: 0;
  }
  /deep/ .el-dialog__body {
    padding: 24px;
    display: flex;
    flex-direction: column;
  }
}
</style>
