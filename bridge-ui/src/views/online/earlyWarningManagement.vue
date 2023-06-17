<template>
  <!-- 预警管理 -->
  <div class="inspectio boxShadow animate__animated animate__fadeIn">
    <div class="condition">
      <!-- 选择桥梁 -->
      <div class="btnInterval">
        <el-select v-model="structureId" placeholder="请选择结构物" clearable>
          <el-option
            v-for="item in structureList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          >
          </el-option>
        </el-select>
      </div>
      <!-- 选择处理状态 -->
      <div class="btnInterval">
        <el-select
          class="selItem2"
          v-model="status"
          placeholder="处理状态"
          clearable
        >
          <el-option label="未处理" :value="0"> </el-option>
          <el-option label="已处理" :value="1"> </el-option>
        </el-select>
      </div>
      <!-- 选择预警等级 -->
      <div class="btnInterval">
        <el-select
          class="selItem2"
          v-model="level"
          placeholder="预警等级"
          clearable
        >
          <el-option
            v-for="item in levelList"
            :key="item.id"
            :label="item.label"
            :value="item.id"
          >
          </el-option>
        </el-select>
      </div>
      <!-- 选择日期 -->
      <div class="btnInterval">
        <el-date-picker
          v-model="timeValue"
          align="center"
          type="datetimerange"
          value-format="yyyy-MM-dd HH:mm:ss"
          :default-time="['00:00:00', '23:59:59']"
          :picker-options="pickerOptions"
          clearable
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        >
        </el-date-picker>
      </div>
      <!-- 测点编号 -->
      <div class="btnInterval">
        <el-input
          v-model="sensorName"
          placeholder="请输入测点编号"
          clearable
        ></el-input>
      </div>
      <!-- 查询 -->
      <div class="btnInterval">
        <el-button type="primary" @click="getWarnList">查询</el-button>
      </div>
      <!-- 批量处理 -->
      <div v-if="updateOpt" class="btnInterval">
        <el-button class="deal" @click="openbatch()">批量处理</el-button>
      </div>
    </div>
    <!-- 数据表格 -->
    <div class="table">
      <el-row>
        <el-col :span="24">
          <normalTable
            :tableData="tableData"
            :titleList="titleList"
            :opList="opList"
            :opWidth="'150'"
            :tableName="'预警管理'"
            :pageNum="pageNum"
            @tableClick="tableClick"
          ></normalTable>
        </el-col>
      </el-row>
    </div>
    <!-- 分页 -->
    <div class="page">
      <el-pagination
        class="pageNation"
        background
        :page-size="10"
        :pager-count="5"
        :current-page="pageNum"
        @current-change="handleCurrentChange"
        layout="total,prev, pager, next"
        :total="total"
      >
      </el-pagination>
    </div>
    <!-- 预警处理窗口 -->
    <el-dialog
      title="预警处理"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose"
      :modal-append-to-body="false"
      :close-on-click-modal="false"
    >
      <el-form ref="form" label-position="left">
        <el-form-item label="处理措施:">
          <el-input
            type="textarea"
            v-model="form.name"
            placeholder="请输入"
          ></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible = false"
          >确 定</el-button
        >
      </span>
    </el-dialog>
    <!-- 批量处理窗口 -->
    <el-dialog
      class="dealAll"
      title="批量处理"
      :visible.sync="dialogVisible2"
      :before-close="batchClose"
      :modal-append-to-body="false"
      :close-on-click-modal="false"
    >
      <el-form
        ref="dealForm"
        label-position="left"
        :model="proForm"
        :rules="dealRules"
      >
        <el-form-item label="选择结构物：" prop="structureId">
          <el-select v-model="proForm.structureId" placeholder="请选择结构物">
            <el-option
              v-for="item in structureList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="批量选择：" prop="levelList">
          <el-checkbox-group v-model="proForm.levelList">
            <el-checkbox :label="1">一级预警</el-checkbox>
            <el-checkbox :label="2">二级预警</el-checkbox>
            <el-checkbox :label="3">三级预警</el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="处理措施：" prop="measures">
          <el-input
            type="textarea"
            v-model="proForm.measures"
            rows="5"
            maxlength="250"
            show-word-limit
            placeholder="请输入"
          ></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer"
        ><el-button type="primary" @click="warnProcessing('dealForm')"
          >确定</el-button
        >
        <el-button @click="dialogVisible2 = false">取消</el-button>
      </span>
    </el-dialog>
    <!-- 查看窗口 -->
    <el-dialog
      class="warnSee"
      title="查看"
      :visible.sync="dialogVisible3"
      :before-close="seeClose"
      :modal-append-to-body="false"
      :close-on-click-modal="false"
    >
      <div class="seeTable">
        <div v-for="item in seeArr" :key="item.id" class="tableItem">
          <span class="tableTitle">{{ item.name }}</span>
          <div
            class="tableValue"
            :title="
              seeObj[item.value] != null && seeObj[item.value].length > 9
                ? seeObj[item.value]
                : ''
            "
          >
            {{ seeObj[item.value] != null ? seeObj[item.value] : '-' }}
          </div>
        </div>
      </div>
      <div class="tap">
        <span :class="{ activeItem: warnIndex == 1 }" @click="warnClick(1)"
          >时程图</span
        >
        <span :class="{ activeItem: warnIndex == 2 }" @click="warnClick(2)"
          >数据列表</span
        >
      </div>
      <div v-if="warnIndex == 1" class="echart">
        <warnLine
          v-if="
            warnData.data.length > 0 && seeObj.sensorTypeName != '称重传感器'
          "
          ref="warnLine"
          :echartData="warnData"
        ></warnLine>
        <warnScatter
          v-else-if="
            warnData.data.length > 0 && seeObj.sensorTypeName == '称重传感器'
          "
          ref="warnLine"
          :echartData="warnData"
        ></warnScatter>
        <div v-else class="empty">
          <span>暂无数据</span>
        </div>
      </div>
      <div v-else class="dataTable">
        <div class="head">
          <span>序号</span>
          <span>预警时间</span>
          <span>实测数值</span>
        </div>
        <div v-if="warnList.length > 0" class="content">
          <div
            v-for="(child, index) in warnList"
            :key="child.id"
            class="warnItem"
          >
            <span>{{ index + 1 }}</span>
            <span>{{ child.time }}</span>
            <span :style="{ color: getColor(child.value) }">{{
              child.value
            }}</span>
          </div>
        </div>
        <div v-else class="empty">
          <span>暂无数据</span>
        </div>
      </div>
      <div class="dealmethods">
        <div class="title">
          <span style="color: #ff5f5f">*</span><span>处理措施：</span>
        </div>
        <el-input
          type="textarea"
          :rows="5"
          maxlength="250"
          clearable
          show-word-limit
          placeholder="请输入"
          v-model="singleForm.measures"
          :disabled="seeObj.status == 1"
        >
        </el-input>
      </div>
      <div class="dealMan">
        <span>处理人：{{ seeObj.username || '-' }}</span>
        <span>处理时间：{{ seeObj.handlerTime || '-' }}</span>
      </div>
      <div v-if="seeObj.status == 0" class="btn">
        <div class="keep" @click="warnProcessing2">保存</div>
        <div class="cancel" @click="dialogVisible3 = false">取消</div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  getWarnList,
  warnProcessing,
  getSensorData
} from '@/api/online/warnManage';
import { getOnlineNotice } from '@/api/login/login';
import { getStructureListByModel } from '@/api/common';
import normalTable from '@/components/table/normalTable';
import warnLine from './components/warnLine';
import warnScatter from './components/warnScatter';
export default {
  components: { normalTable, warnLine, warnScatter },
  data() {
    return {
      selParams: {},
      structureManage: {}, //结构物对象
      titleList: [
        { id: 1, prop: 'structureName', label: '结构物名称', width: '' },
        { id: 2, prop: 'warningTime', label: '预警时间', width: '' },
        { id: 3, prop: 'sensorName', label: '测点编号', width: '' },
        { id: 4, prop: 'componentName', label: '预警事项', width: '' }
        // { id: 6, prop: "level", label: "预警等级", width: "" }
      ],
      tableData: [],
      opList: [
        {
          id: 1,
          name: '预警处理',
          opShow: false,
          disabled: '',
          type: 'updateOpt'
        },
        {
          id: 2,
          name: '查看',
          opShow: false,
          disabled: '',
          type: 'checkOpt'
        }
      ],
      //时程图数据
      warnData: {
        date: [],
        data: []
      },
      warnList: [
        // { id: 1, time: '2021-06-16  10:30:10', value: 760.65 },
        // { id: 2, time: '2021-06-16  10:30:10', value: 869.23 },
        // { id: 3, time: '2021-06-16  10:30:10', value: 766.87 },
        // { id: 4, time: '2021-06-16  10:30:10', value: 795.46 },
        // { id: 5, time: '2021-06-16  10:30:10', value: 760.65 },
        // { id: 6, time: '2021-06-16  10:30:10', value: 760.65 },
        // { id: 7, time: '2021-06-16  10:30:10', value: 760.65 },
        // { id: 8, time: '2021-06-16  10:30:10', value: 760.65 }
      ],
      textareaVal: '',

      //批量处理
      proForm: {
        structureId: '',
        levelList: [],
        measures: ''
      },
      singleForm: {
        warningRecordId: '',
        measures: ''
      },
      dealRules: {
        structureId: [
          { required: true, message: '请选择结构物', trigger: 'change' }
        ],
        levelList: [
          {
            required: true,
            message: '请选择处理等级',
            trigger: 'change'
          }
        ],
        measures: [
          { required: true, message: '请输入处理措施', trigger: 'blur' }
        ]
      },

      structureId: '',
      status: '',
      level: '',
      timeValue: '',
      sensorName: '',
      structureList: [],
      options: [{ value: 1, label: '台江大桥' }],
      options2: [],
      pickerOptions: {
        shortcuts: [
          {
            text: '最近一周',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', [start, end]);
            }
          },
          {
            text: '最近一个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit('pick', [start, end]);
            }
          },
          {
            text: '最近三个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit('pick', [start, end]);
            }
          },
          {
            text: '最近一年',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 365);
              picker.$emit('pick', [start, end]);
            }
          }
        ]
      },
      pageNum: 1,
      total: 0,
      dialogVisible: false, //预警处理窗口显示判断
      dialogVisible2: false, //批量处理窗口显示判断
      dialogVisible3: false, //查看窗口显示判断
      earlyObj: {}, //预警处理对象
      form: {}, //批量处理对象
      seeObj: {}, //查看对象
      levelList: [
        { id: 1, label: '一级预警' },
        { id: 2, label: '二级预警' },
        { id: 3, label: '三级预警' }
      ], //批量处理预警等级
      seeArr: [
        {
          id: 1,
          name: '预警时间',
          value: 'warningTime'
        },
        {
          id: 2,
          name: '测点编号',
          value: 'sensorName'
        },
        {
          id: 3,
          name: '传感器类型',
          value: 'sensorTypeName'
        },
        {
          id: 4,
          name: '监测位置',
          value: 'componentName'
        },
        {
          id: 5,
          name: '结构物',
          value: 'structureName'
        },
        {
          id: 6,
          name: '单位',
          value: 'unit'
        },
        {
          id: 7,
          name: '一级预警值',
          value: 'firstWarningUpper'
        },
        {
          id: 8,
          name: '二级预警值',
          value: 'secondWarningUpper'
        },
        {
          id: 9,
          name: '三级预警值',
          value: 'thirdWarningUpper'
        },
        {
          id: 10,
          name: '实测数值',
          value: 'value'
        }
      ], //查看
      warnIndex: 1
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
    this.getWarnList();
    this.getStructureListByModel();
  },
  methods: {
    //获取传感器列表
    async getWarnList() {
      let params = {
        structureId: this.structureId,
        status: this.status,
        level: this.level,
        startTime: !!this.timeValue ? this.timeValue[0] : '',
        endTime: !!this.timeValue ? this.timeValue[1] : '',
        sensorName: this.sensorName,
        pageNum: 1,
        pageSize: 10
      };
      let { code, data } = await getWarnList(params);
      if (code == '0000') {
        this.tableData = data.list;
        this.total = data.total;
        data.list.map((item) => {
          item.value = item.value.toFixed(2);
          if (item.status == 0) {
            item.hideName = ['查看'];
          }
          if (item.status == 1) {
            item.hideName = ['预警处理'];
          }
        });
        this.pageNum = 1;
        this.selParams = params;
        await this.getOnlineNotice();
      }
    },
    async getWarnList2() {
      let { code, data } = await getWarnList(this.selParams);
      if (code == '0000') {
        this.tableData = data.list;
        this.total = data.total;
        data.list.map((item) => {
          if (item.status == 0) {
            item.hideName = ['查看'];
          }
          if (item.status == 1) {
            item.hideName = ['预警处理'];
          }
        });
        await this.getOnlineNotice();
      }
    },
    //获取结构物列表
    async getStructureListByModel() {
      let params = { powerId: this.$store.getters.getActiveIndex };
      let { code, data } = await getStructureListByModel(params);
      if (code == '0000') {
        this.structureList = data;
      }
    },
    //批量处理
    warnProcessing(formName) {
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          let { code } = await warnProcessing(this.proForm);
          if (code == '0000') {
            this.$message({
              message: '处理成功！',
              type: 'success',
              showClose: true,
              duration: 2000
            });
            let num = this.$store.getters.getDotWarn;
            this.$store.dispatch('asyncUpdateDotWarn', --num); //预警管理红点
            this.batchClose();
            await this.getWarnList2();
            await this.getOnlineNotice();
          }
        } else {
          return;
        }
      });
    },
    //单条处理
    async warnProcessing2() {
      if (this.singleForm.measures == '' || this.singleForm.measures == null) {
        this.$message({
          message: '请填写处理措施！',
          type: 'warning',
          showClose: true,
          duration: 2000
        });
        return;
      }
      let { code } = await warnProcessing(this.singleForm);
      if (code == '0000') {
        this.$message({
          message: '处理成功！',
          type: 'success',
          showClose: true,
          duration: 2000
        });
        this.seeClose();
        await this.getWarnList2();
        await this.getOnlineNotice();
      }
    },
    //获取预警数量、传感器数量
    async getOnlineNotice() {
      let { code, data } = await getOnlineNotice();
      if (code == '0000') {
        this.$store.dispatch('asyncUpdateDotWarn', data.warningCount); //预警管理红点
        this.$store.dispatch('asyncUpdateDotSensor', data.offlineCount); //传感器设置红点
      }
    },
    //获取查看数据
    async getSensorData(row) {
      let params = {
        samplingTime: row.warningTime,
        sensorCoding: row.sensorCoding,
        sensorDetailsId: row.sensorDetailsId,
        sensorId: row.sensorId
      };
      let { code, data } = await getSensorData(params);
      if (code == '0000') {
        let dateList = [];
        let dataList = [];
        let list = [];
        if (!!data) {
          dateList = data.times.split(',');
          dataList = data.datas.split(',');
          dataList.map((item, index) => {
            let obj = {
              id: index + 1,
              time: dateList[index],
              value: item
            };
            list.push(obj);
          });
        }
        this.warnData = {
          unit: row.unit,
          date: dateList,
          data: dataList,
          upOne: row.firstWarningUpper,
          upTwo: row.secondWarningUpper,
          upThree: row.thirdWarningUpper,
          downOne: row.firstWarningLower,
          downTwo: row.secondWarningLower,
          downThree: row.thirdWarningLower
        };
        this.warnList = list;
        this.dialogVisible3 = true;
        this.$nextTick(() => {
          if (this.warnIndex == 1 && dateList.length > 0) {
            this.$refs.warnLine.setOption();
          }
        });
      }
    },
    //表格点击事件
    tableClick(index, item) {
      console.log(item);
      // if (index == 1) {
      let data = { ...item };
      this.warnIndex = 1;
      this.getSensorData(item);
      this.singleForm.warningRecordId = data.id;
      this.singleForm.measures = data.measures;
      if (data.firstWarningLower == null && data.firstWarningUpper == null) {
        data.firstWarningUpper = '-';
      } else {
        data.firstWarningUpper =
          '{ ' +
          (!!data.firstWarningLower ? data.firstWarningLower : '-') +
          '，' +
          (!!data.firstWarningUpper ? data.firstWarningUpper : '-') +
          ' }';
      }
      if (data.secondWarningLower == null && data.secondWarningUpper == null) {
        data.secondWarningUpper = '';
      } else {
        data.secondWarningUpper =
          '{ ' +
          (!!data.secondWarningLower ? data.secondWarningLower : '-') +
          '，' +
          (!!data.secondWarningUpper ? data.secondWarningUpper : '-') +
          ' }';
      }
      if (data.thirdWarningLower == null && data.thirdWarningUpper == null) {
        data.thirdWarningUpper = '-';
      } else {
        data.thirdWarningUpper =
          '{ ' +
          (!!data.thirdWarningLower ? data.thirdWarningLower : '-') +
          '，' +
          (!!data.thirdWarningUpper ? data.thirdWarningUpper : '-') +
          ' }';
      }
      this.seeObj = { ...data };
      // } else {
      //   this.openEarlyWarning();
      // }
    },
    // 分页
    handleCurrentChange(val) {
      this.pageNum = val;
      this.selParams.pageNum = val;
      this.getWarnList2();
    },
    // 打开预警处理窗口
    openEarlyWarning(row) {
      this.dialogVisible = true;
    },
    // 关闭预警处理窗口
    handleClose() {
      this.dialogVisible = false;
    },
    // 打开批量处理窗口
    openbatch(row) {
      this.dialogVisible2 = true;
    },
    // 关闭批量处理窗口
    batchClose() {
      this.dialogVisible2 = false;
      this.proForm = {
        structureId: '',
        levelList: [],
        measures: ''
      };
      this.$refs.dealForm.resetFields();
    },
    //查看时程图、数据列表选择
    warnClick(index) {
      this.warnIndex = index;
      if (index == 1 && this.warnData.data.length > 0) {
        this.$nextTick(() => {
          this.$refs.warnLine.setOption();
        });
      }
    },
    //获取数据列表测值颜色
    getColor(value) {
      if (
        (!!this.warnData.upOne && value >= this.warnData.upOne) ||
        (!!this.warnData.downOne && value <= this.warnData.downOne)
      ) {
        return '#EB5757';
      }
      if (
        (!!this.warnData.upTwo && value >= this.warnData.upTwo) ||
        (!!this.warnData.downTwo && value <= this.warnData.downTwo)
      ) {
        return '#F2C94C';
      }
      if (
        (!!this.warnData.upThree && value >= this.warnData.upThree) ||
        (!!this.warnData.downThree && value <= this.warnData.downThree)
      ) {
        return '#419AFF';
      }
    },
    // 关闭查看窗口
    seeClose() {
      this.dialogVisible3 = false;
      this.seeObj = {};
    }
  }
};
</script>

<style lang="scss" scoped>
.inspectio {
  height: 36vw;
  font-size: 0.729165vw;
  padding: 24px;
  .condition {
    padding-bottom: 20px;
    display: flex;
    justify-content: flex-end;
  }

  .table {
    width: 100%;
    /deep/ .el-table td {
      padding: 1.668vh 0;
    }
  }
  &-btn {
    padding: 0 0 1vw;
  }
}

//查看弹框
.warnSee {
  .seeTable {
    padding: 0 30px;
    display: flex;
    justify-content: center;
    flex-wrap: wrap;
    .tableItem {
      width: 20%;
      height: 6.668vh;
      display: flex;
      flex-direction: column;
      span {
        width: 100%;
        height: 50%;
        display: flex;
        align-items: center;
        justify-content: center;
      }
      &:nth-child(1),
      &:nth-child(6) {
        border-left: 1px solid #d9d9d9;
      }
      &:nth-child(n + 6) .tableValue {
        border-bottom: 1px solid #d9d9d9;
      }
    }
    .tableTitle {
      font-weight: bold;
      background-color: rgba(242, 244, 246, 1);
      border: 1px solid #d9d9d9;
      border-left: 0;
    }
    .tableValue {
      width: 100%;
      height: 50%;
      line-height: 3.334vh;
      padding: 0 10px;
      text-align: center;
      border-right: 1px solid #d9d9d9;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
    }
  }
  .tap {
    padding: 2.669vh 30px 2vh;
    display: flex;
    .activeItem {
      color: #2f80ed;
      border: 1px solid #2f80ed !important;
    }
    span {
      font-size: 14px;
      color: #000;
      padding: 8px 16px;
      border: 1px solid #d9d9d9;
      cursor: pointer;
      &:last-child {
        border-left: 0;
      }
    }
  }
  .echart {
    width: 100%;
    height: 30.31vh;
    padding: 0 30px;
    .empty {
      height: 100%;
      width: 100%;
      padding-bottom: 5.337vh;
      span {
        height: 100%;
        width: 100%;
        font-size: 28px;
        color: #333;
        background: #e5e5e5;
        display: flex;
        align-items: center;
        justify-content: center;
      }
    }
  }
  .dataTable {
    padding-left: 30px;
    padding-right: 10px;
    padding-bottom: 5.337vh;
    display: flex;
    flex-direction: column;
    .head {
      padding-right: 10px;
      display: flex;
      align-items: center;
      span {
        width: 359px;
        height: 36px;
        font-size: 14px;
        color: #333;
        font-weight: bold;
        background: #f2f4f6;
        border: 1px solid #d9d9d9;
        border-left: 0;
        display: flex;
        align-items: center;
        justify-content: center;
        &:first-child {
          width: 170px;
          border-left: 1px solid #d9d9d9;
        }
      }
    }
    .content {
      height: 180px;
      padding-right: 10px;
      overflow: auto;
      .warnItem {
        display: flex;
        align-items: center;
        span {
          width: 359px;
          height: 36px;
          font-size: 14px;
          color: #333;
          border-right: 1px solid #d9d9d9;
          border-bottom: 1px solid #d9d9d9;
          display: flex;
          align-items: center;
          justify-content: center;
          &:first-child {
            width: 170px;
            border-left: 1px solid #d9d9d9;
          }
        }
      }
      &::-webkit-scrollbar {
        width: 8px;
      }
      &::-webkit-scrollbar-thumb {
        background: #c4c4c4;
        border-radius: 8px;
      }
    }
    .empty {
      width: 100%;
      height: 180px;
      padding-right: 20px;
      span {
        width: 100%;
        height: 100%;
        font-size: 28px;
        color: #333;
        border: 1px solid #d9d9d9;
        border-top: 0;
        display: flex;
        align-items: center;
        justify-content: center;
      }
    }
  }
  .dealmethods {
    padding: 0 20px;
    display: flex;
    .title {
      display: flex;
    }
    span {
      font-size: 14px;
      color: #333;
    }
    /deep/ .el-textarea {
      width: 680px;
    }
    /deep/ .el-textarea__inner {
      font-size: 14px;
      color: #333;
      border: 1px solid rgba(0, 0, 0, 0.15);
      border-radius: 4px;
      &::placeholder {
        color: #595959;
        font-size: 14px;
      }
    }
  }
  .dealMan {
    padding: 2.669vh 30px;
    display: flex;
    flex-direction: column;
    span {
      line-height: 20px;
    }
  }
  .btn {
    font-size: 14px;
    padding: 2.455vh 30px;
    display: flex;
    align-items: center;
    justify-content: center;
    .keep {
      width: 68px;
      height: 40px;
      color: #fff;
      background: #419aff;
      border-radius: 4px;
      margin-right: 20px;
      display: flex;
      align-items: center;
      justify-content: center;
      cursor: pointer;
    }
    .cancel {
      width: 68px;
      height: 40px;
      color: #727477;
      border: 1px solid #dcdfe6;
      border-radius: 4px;
      display: flex;
      align-items: center;
      justify-content: center;
      cursor: pointer;
    }
  }
  /deep/ .el-dialog {
    width: 948px;
  }
  /deep/ .el-dialog__body {
    padding: 20px 0;
    padding-top: 10px;
    display: flex;
    flex-direction: column;
  }
}

//批量处理弹框
.dealAll {
  /deep/ .el-dialog {
    width: 520px;
  }
  /deep/ .el-dialog__body {
    padding: 30px 0 0;
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  /deep/ .el-form-item {
    display: flex;
  }
  /deep/ .el-form-item__label {
    width: 100px;
    padding: 0;
    text-align: right;
    margin-right: 5px;
  }
  /deep/ .el-checkbox__label {
    color: #262626;
  }
  /deep/ .el-select,
  /deep/ .el-textarea,
  /deep/ .el-checkbox-group {
    width: 316px;
  }
}

.page {
  display: flex;
  justify-content: center;
  padding: 1vw 0;
}
// 间距
.btnInterval {
  margin-left: 20px;
  .deal {
    color: #419aff;
    border: 1px solid #419aff;
  }
  .selItem2 {
    width: 128px;
  }
  /deep/ .el-range-editor.el-input__inner {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
  /deep/ .el-date-editor .el-range-separator {
    width: unset;
  }
}
/deep/ .el-dialog__footer {
  text-align: center;
}
.pageNation {
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
</style>
