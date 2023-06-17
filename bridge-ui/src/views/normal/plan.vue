<template>
  <div class="plane animate__animated animate__fadeIn">
    <!-- <p class="border_title_span base-font">巡查计划</p> -->
    <div class="planTop">
      <div>
        <el-button v-if="addOpt" type="primary" @click="addPlab"
          >添加计划</el-button
        >
      </div>

      <div>
        <el-select
          v-model="projectId"
          placeholder="请选择项目"
          class="planTop_select_one my_select1"
          clearable
        >
          <el-option
            v-for="item in modelProjectList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          >
          </el-option>
        </el-select>
        <el-date-picker
          class="planTop_select_one my_select1"
          v-model="time"
          type="monthrange"
          clearable
          align="center"
          value-format="yyyy-MM"
          range-separator="至"
          start-placeholder="开始月份"
          end-placeholder="结束月份"
          :picker-options="pickerMonth"
        >
        </el-date-picker>
        <el-select
          v-model="inspectTypeId"
          class="planTop_select_one my_select2"
          placeholder="巡查类型"
          clearable
        >
          <el-option
            v-for="item in typeList"
            :key="item.id"
            :label="item.label"
            :value="item.id"
          >
          </el-option>
        </el-select>

        <el-select
          v-model="status"
          class="planTop_select_one my_select2"
          placeholder="执行状态"
          clearable
        >
          <el-option
            v-for="item in stateList"
            :key="item.id"
            :label="item.label"
            :value="item.id"
          >
          </el-option>
        </el-select>
        <el-button type="primary" class="planTop_button" @click="getPlanList"
          >查询</el-button
        >
      </div>
    </div>
    <div class="table_div">
      <el-table
        class="tableBox"
        :data="tableData"
        :row-class-name="tableRowClassName"
      >
        <el-table-column type="index" label="序号" width="100" align="center">
          <template slot-scope="scope">
            {{ (currentPage - 1) * 10 + scope.$index + 1 }}
          </template>
        </el-table-column>
        <el-table-column
          prop="name"
          label="计划名称"
          align="center"
          show-overflow-tooltip
        >
        </el-table-column>
        <el-table-column
          prop="projectName"
          label="项目名称"
          align="center"
          show-overflow-tooltip
        >
        </el-table-column>
        <el-table-column prop="type" label="巡查类型" align="center">
          <template slot-scope="scope">
            <!-- 1:日常巡查 2:经常检查 3:特殊检查 4:维修养护 -->
            <div>
              {{
                scope.row.type == 1
                  ? '日常巡查'
                  : scope.row.type == 2
                  ? '经常检查'
                  : scope.row.type == 3
                  ? '特殊检查'
                  : '维修养护'
              }}
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="planTime" label="巡查月份" align="center">
        </el-table-column>
        <el-table-column label="执行状态" align="center">
          <template slot-scope="scope">
            <div
              :class="{
                danger: scope.row.status == 0,
                working: scope.row.status == 1,
                success: scope.row.status == 2
              }"
            >
              {{
                scope.row.status == 0
                  ? '未执行'
                  : scope.row.status == 1
                  ? '执行中'
                  : '已完成'
              }}
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="count" label="巡查次数" align="center">
          <template slot-scope="scope">
            <div>{{ scope.row.count || '-' }}</div>
          </template>
        </el-table-column>
        <!-- <el-table-column prop="budget" label="预计金额（元）" align="center">
          <template slot-scope="scope">
            <div>{{ scope.row.budget || '-' }}</div>
          </template>
        </el-table-column>
        <el-table-column
          prop="expenditure"
          label="实际金额（元）"
          align="center"
        >
          <template slot-scope="scope">
            <div>{{ scope.row.expenditure || '-' }}</div>
          </template>
        </el-table-column> -->
        <el-table-column
          prop="realName"
          label="创建人员"
          align="center"
          show-overflow-tooltip
        ></el-table-column>
        <el-table-column
          prop="createTime"
          label="创建时间"
          align="center"
          width="180"
          show-overflow-tooltip
        ></el-table-column>
        <el-table-column label="操作" align="center" width="200">
          <template slot-scope="scope">
            <el-link v-if="checkOpt" type="primary" @click="detail(scope.row)"
              >计划详情</el-link
            >
            <el-link
              v-if="updateOpt"
              type="primary"
              class="table_link"
              :class="{ disabled: scope.row.status != 0 }"
              @click="updatePlan(scope.row, scope.row.status)"
              >修改</el-link
            >
            <el-link
              v-if="deleteOpt"
              type="primary"
              class="table_link"
              :class="{ disabled: scope.row.status != 0 }"
              @click="deleteItem(scope.row, scope.row.status)"
              >删除</el-link
            >
          </template>
        </el-table-column>
      </el-table>
    </div>

    <el-pagination
      class="pageNation"
      background
      :current-page.sync="currentPage"
      :page-size="10"
      :pager-count="5"
      layout="total, prev, pager, next"
      :total="total"
      @current-change="handleCurrentChange"
    >
    </el-pagination>

    <!-- 添加、修改计划弹窗 -->
    <el-dialog
      class="planDialog"
      :title="planDialogTitle"
      :visible.sync="showPlanDialog"
      :before-close="dialogClose"
      append-to-body
      modal-append-to-body
      :close-on-click-modal="false"
    >
      <el-form
        v-if="showPlanDialog"
        class="planBox"
        ref="planForm"
        :model="planForm"
        :rules="planRules"
      >
        <el-form-item label="选择项目：" prop="projectId">
          <el-select
            v-model="planForm.projectId"
            placeholder="请选择项目"
            @change="projectChange"
          >
            <el-option
              v-for="item in projectList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="巡查类型：" prop="type">
          <el-select v-model="planForm.type" placeholder="请选择巡查类型">
            <el-option
              v-for="item in typeList"
              :key="item.id"
              :label="item.label"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="选择结构物：" prop="structureId">
          <el-select
            v-model="planForm.structureId"
            multiple
            clearable
            collapse-tags
            placeholder="请选择结构物"
            @change="createList"
          >
            <el-option
              v-for="item in structureList2"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
            <template slot="empty">
              <div class="emptySelect">{{ structureTitle }}</div>
            </template>
          </el-select>
        </el-form-item>
        <el-form-item label="预计金额：" prop="budget">
          <el-input
            v-model="planForm.budget"
            placeholder="输入金额"
            clearable
            maxlength="8"
          ></el-input>
        </el-form-item>
        <el-form-item label="巡查月份：" prop="planTime">
          <el-date-picker
            v-model="planForm.planTime"
            type="month"
            :clearable="false"
            :picker-options="pickerOptions"
            placeholder="请选择年月"
            value-format="yyyy-MM"
            @change="createList(2)"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="实际金额：" prop="expenditure">
          <el-input
            v-model="planForm.expenditure"
            placeholder="输入金额"
            clearable
            maxlength="8"
          ></el-input>
        </el-form-item>
      </el-form>
      <div class="planTable">
        <div class="head">
          <span>序号</span>
          <span>结构物</span>
          <span>本月首次巡查日期</span>
        </div>
        <div
          v-if="
            !!planForm.planDetailList &&
            planForm.planDetailList.length > 0 &&
            planForm.planDetailList[0]
          "
          class="content"
          :class="{ scrollBar: planForm.planDetailList.length > 5 }"
        >
          <div
            v-for="(item, index) in planForm.planDetailList"
            :key="item.id"
            class="planItem"
          >
            <span class="text">{{ index + 1 }}</span>
            <div class="text">
              <el-tooltip effect="dark" :content="item.name" placement="top">
                <span>{{ item.name }}</span>
              </el-tooltip>
            </div>
            <el-date-picker
              class="text"
              v-model="item.inspectionTime"
              type="date"
              placeholder="选择日期"
              value-format="yyyy-MM-dd"
              :picker-options="pickerPlanList"
              :clearable="false"
            >
            </el-date-picker>
          </div>
        </div>
        <div v-else class="noData">
          <div>请选择结构物和日期</div>
        </div>
      </div>
      <div class="btn">
        <span class="keep" @click="formSubmit('planForm')">确定</span>
        <span class="cancel" @click="dialogClose">取消</span>
      </div>
    </el-dialog>

    <!-- 计划详情 -->
    <el-dialog
      class="planDetail"
      :title="detailDialogTitle + '计划详情'"
      :visible.sync="detailDialog"
      :before-close="detailDialogClose"
      append-to-body
      modal-append-to-body
      :close-on-click-modal="false"
    >
      <div class="detail_dialog_div">
        <div>
          <el-button
            v-if="addOpt && optStatus"
            class="btnAdd"
            type="primary"
            @click="addDetail"
            >添加细项</el-button
          >
        </div>
        <el-select
          v-model="detailObj.structureId"
          class="my_select1"
          placeholder="请选择结构物"
          clearable
        >
          <el-option
            v-for="item in structureList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          >
          </el-option>
        </el-select>

        <el-date-picker
          class="my_select1"
          v-model="detailObj.time"
          type="daterange"
          clearable
          align="center"
          value-format="yyyy-MM-dd"
          range-separator="至"
          start-placeholder="开始月份"
          end-placeholder="结束月份"
          :picker-options="pickerDetailMonth"
        >
        </el-date-picker>

        <el-select
          v-model="detailObj.status"
          class="my_select2"
          placeholder="请选择状态"
          clearable
        >
          <el-option
            v-for="item in stateList2"
            :key="item.id"
            :label="item.label"
            :value="item.id"
          >
          </el-option>
        </el-select>

        <el-button type="primary" @click="getPlanDetailList">查询</el-button>
      </div>
      <div class="detailTable">
        <el-table
          class="tableBox"
          :data="tableData2"
          :row-class-name="tableRowClassName"
        >
          <el-table-column type="index" label="序号" width="100" align="center">
          </el-table-column>
          <el-table-column
            prop="structureName"
            label="结构物名称"
            align="center"
            show-overflow-tooltip
          >
          </el-table-column>
          <el-table-column
            prop="inspectionTime"
            label="巡查日期"
            align="center"
          >
          </el-table-column>
          <el-table-column label="完成状态" align="center">
            <template slot-scope="scope">
              <div
                :class="{
                  danger: scope.row.status == -1,
                  working: scope.row.status == 0,
                  success: scope.row.status == 1
                }"
              >
                {{
                  scope.row.status == 0
                    ? '未完成'
                    : scope.row.status == 1
                    ? '已完成'
                    : '已超时'
                }}
              </div>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center">
            <template slot-scope="scope">
              <el-link
                v-if="updateOpt"
                type="primary"
                class="table_link"
                :class="{ disabled: scope.row.status != 0 }"
                @click="updateDetail(scope.row, scope.row.status)"
                >修改</el-link
              >
              <el-link
                v-if="deleteOpt && optStatus"
                type="primary"
                class="table_link"
                :class="{ disabled: scope.row.status != 0 }"
                @click="delPlanDetail(scope.row.id, scope.row.status)"
                >删除</el-link
              >
              <div v-if="scope.row.status != 0">-</div>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="detailBottom">
        <div class="btmItem">
          <span>预计金额：</span
          ><el-input
            class="btmInput"
            v-model.number="detailBudget"
            placeholder="-"
            maxlength="8"
            :disabled="!changeMoney"
            ><template slot="append">元</template></el-input
          >
        </div>
        <div class="btmItem">
          <span>实际金额：</span
          ><el-input
            class="btmInput"
            v-model.number="detailExpenditure"
            placeholder="-"
            maxlength="8"
            :disabled="!changeMoney"
          >
            <template slot="append">元</template></el-input
          >
        </div>
        <div v-if="detailObj.changeMoney" class="btmBtn">
          <span v-if="!changeMoney" class="change" @click="changeMoney = true"
            >修改金额</span
          >
          <span v-else class="change" @click="updatePlanAmount">保存</span>
        </div>
      </div>
    </el-dialog>

    <!-- 添加细项 -->
    <el-dialog
      class="addDetail"
      :title="addDetailTitle"
      :visible.sync="addDetailDialog"
      :before-close="addDetailDialogClose"
      append-to-body
      modal-append-to-body
      :close-on-click-modal="false"
    >
      <el-form
        v-if="addDetailDialog"
        class="addDetailBox"
        ref="addDetailForm"
        :model="addDetailForm"
        :rules="addDetailRules"
      >
        <el-form-item label="选择结构物：" prop="structureId">
          <el-select v-model="addDetailForm.structureId" placeholder="请选择">
            <el-option
              v-for="item in structureList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="巡查日期：" prop="inspectionTime">
          <el-date-picker
            v-model="addDetailForm.inspectionTime"
            type="date"
            :clearable="false"
            placeholder="请选择"
            value-format="yyyy-MM-dd"
            :default-value="defaultValue"
            :picker-options="pickerDetailMonth2"
          >
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div class="btn">
        <span class="keep" @click="addDetailSubmit('addDetailForm')">确定</span>
        <span class="cancel" @click="addDetailDialogClose">取消</span>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  getPlanList,
  addPlan,
  updPlan,
  delPlan,
  getPlanDetailList,
  addPlanDetail,
  updPlanDetail,
  delPlanDetail,
  getfirstDate,
  updatePlanAmount
} from '@/api/normal/plan';
import { getreceiveTime } from '@/api/maintain/plan';
import {
  getProjectListByOnTime,
  getStructureList,
  getProjectListByModel
} from '@/api/common';
export default {
  components: {},
  props: {},
  data() {
    const _this = this;
    return {
      selParams: {
        projectId: '',
        beginTime: '',
        endTime: '',
        inspectType: '',
        status: '',
        pageSize: 10,
        pageNum: 1
      }, //用来保存查询的字段
      //计划列表赛选条件
      projectId: '',
      projectList: [],
      modelProjectList: [],
      inspectTypeId: '',
      typeList: [
        { id: 1, label: '日常巡查' } //巡查类型列表
      ],
      status: '',
      stateList: [
        { id: 0, label: '未执行' },
        { id: 1, label: '执行中' },
        { id: 2, label: '已完成' }
      ],
      stateList2: [
        { id: 0, label: '未完成' },
        { id: 1, label: '已完成' },
        { id: -1, label: '已超时' }
      ],
      time: '',
      // 结构物
      structureList: [],
      structureList2: [],
      //添加、修改弹框
      planDialogTitle: '',
      showPlanDialog: false,
      planRules: {
        projectId: [
          { required: true, message: '请选择项目', trigger: 'change' }
        ],
        type: [
          { required: true, message: '请选择巡查类型', trigger: 'change' }
        ],
        structureId: [
          { required: true, message: '请选择结构物', trigger: 'change' }
        ],
        planTime: [
          {
            required: true,
            message: '请选择年月',
            trigger: 'change'
          }
        ],
        budget: [
          // { required: true, message: '请输入金额', trigger: 'blur' },
          { pattern: /^\d+(\.\d+)?$/, message: '请输入正确的数字' }
        ],
        expenditure: [
          // { required: true, message: '请输入金额', trigger: 'blur' },
          { pattern: /^\d+(\.\d+)?$/, message: '请输入正确的数字' }
        ]
      },
      planForm: {
        projectId: '',
        type: '',
        structureId: [],
        budget: '',
        planTime: '',
        expenditure: '',
        planDetailList: []
      },
      //计划详情弹框
      detailDialogTitle: '',
      detailDialog: false,
      detailObj: {
        planId: '',
        structureId: '',
        time: '',
        status: ''
      },
      detailBudget: '',
      detailExpenditure: '',
      optStatus: false,
      changeMoney: false,
      //添加细项弹框
      addDetailTitle: '',
      addDetailDialog: false,
      addDetailRules: {
        structureId: [
          { required: true, message: '请选择结构物', trigger: 'change' }
        ],
        inspectionTime: [
          {
            required: true,
            message: '请选择年月',
            trigger: 'change'
          }
        ]
      },
      addDetailForm: {
        structureId: '',
        inspectionTime: '',
        id: ''
      },
      //日期规则
      pickerOptions: {
        disabledDate(time) {
          let beginTime = !!_this.startreceiveTime
            ? new Date(_this.startreceiveTime).getTime()
            : Date.now();
          let lastTime = !!_this.endreceiveTime
            ? new Date(_this.endreceiveTime).getTime()
            : Date.now();
          if (_this.$store.getters.getRoleInfo.id == 2) {
            lastTime = null;
          }
          if (!!lastTime) {
            return (
              time.getTime() < Date.now() - 24.36 * 1000 ||
              time.getTime() < beginTime ||
              time.getTime() > lastTime
            );
          } else {
            return time.getTime() < Date.now() - 24 * 3600 * 1000;
          }
        }
      },
      pickerMonth: {
        shortcuts: [
          {
            text: '本月',
            onClick(picker) {
              picker.$emit('pick', [new Date(), new Date()]);
            }
          },
          {
            text: '今年至今',
            onClick(picker) {
              const end = new Date();
              const start = new Date(new Date().getFullYear(), 0);
              picker.$emit('pick', [start, end]);
            }
          },
          {
            text: '最近六个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setMonth(start.getMonth() - 6);
              picker.$emit('pick', [start, end]);
            }
          },
          {
            text: '最近一年',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setFullYear(start.getFullYear() - 1);
              picker.$emit('pick', [start, end]);
            }
          }
        ]
        // disabledDate(time) {
        //   return time.getTime() > Date.now();
        // }
      },
      pickerDetailMonth: {
        shortcuts: [
          {
            text: '本月',
            onClick(picker) {
              picker.$emit('pick', [new Date(), new Date()]);
            }
          },
          {
            text: '今年至今',
            onClick(picker) {
              const end = new Date();
              const start = new Date(new Date().getFullYear(), 0);
              picker.$emit('pick', [start, end]);
            }
          },
          {
            text: '最近六个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setMonth(start.getMonth() - 6);
              picker.$emit('pick', [start, end]);
            }
          },
          {
            text: '最近一年',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setFullYear(start.getFullYear() - 1);
              picker.$emit('pick', [start, end]);
            }
          }
        ],
        disabledDate(time) {
          let disBegin = _this.detailStartTime
            ? _this.detailStartTime + '-01'
            : '';
          let disEnd = _this.detailStartTime
            ? _this.$utils.getMonthDay(_this.detailStartTime, true)
            : ''; //true 表示返回最后一天日期
          return (
            time.getTime() < new Date(disBegin).getTime() - 24 * 3600 * 1000 ||
            time.getTime() > new Date(disEnd).getTime()
          );
        }
      },
      defaultValue: '',
      pickerDetailMonth2: {
        disabledDate(time) {
          let disBegin = _this.detailStartTime
            ? _this.detailStartTime + '-01'
            : '';
          let disEnd = _this.detailStartTime
            ? _this.$utils.getMonthDay(_this.detailStartTime, true)
            : ''; //true 表示返回最后一天日期
          let beginTime = !!_this.startreceiveTime
            ? new Date(_this.startreceiveTime)
            : new Date(disBegin).getTime() - 24 * 3600 * 1000;
          let lastTime = !!_this.endreceiveTime
            ? new Date(_this.endreceiveTime)
            : disEnd;
          return (
            time.getTime() < new Date(disBegin).getTime() - 24 * 3600 * 1000 ||
            time.getTime() > new Date(disEnd).getTime() ||
            time.getTime() < new Date(beginTime).getTime() ||
            time.getTime() > new Date(lastTime).getTime()
          );
        }
      },
      pickerPlanList: {
        disabledDate(time) {
          let disBegin = _this.planForm.planTime + '-01';
          let disEnd = _this.$utils.getMonthDay(_this.planForm.planTime, true); //true 表示返回最后一天日期
          let beginTime = !!_this.startreceiveTime
            ? new Date(_this.startreceiveTime)
            : new Date(disBegin).getTime() - 24 * 3600 * 1000;
          let lastTime = !!_this.endreceiveTime
            ? new Date(_this.endreceiveTime)
            : disEnd;
          return (
            time.getTime() < new Date(disBegin).getTime() - 24 * 3600 * 1000 ||
            time.getTime() > new Date(disEnd).getTime() ||
            time.getTime() < new Date(beginTime).getTime() ||
            time.getTime() > new Date(lastTime).getTime()
          );
        }
      },
      //表格数据
      currentPage: 1,
      total: 0,
      tableData: [],
      tableData2: [],
      structureTitle: '请先选择项目',
      startreceiveTime: null,
      endreceiveTime: null,
      firstDate: {}, //当月首次巡查时间
      detailStartTime: undefined, //计划详情的开始时间
      detailPageSize: 35
    };
  },
  beforeMount() {
    this.$utils.getAuthPage(this); //获取权限
  },
  mounted() {
    this.getPlanList(); //获取计划列表
    this.getProjectListByOnTime(); //获取项目列表
    this.getProjectListByModel(); //获取相应权限的项目列表
    // this.getreceiveTime();
  },
  methods: {
    //获取相应权限的项目列表
    async getProjectListByModel() {
      let id = this.$store.getters.getActiveIndex;
      let { code, data } = await getProjectListByModel(id);
      if (code == '0000') {
        this.modelProjectList = data;
      }
    },
    //获取项目列表
    async getProjectListByOnTime() {
      let id = this.$store.getters.getActiveIndex;
      let { code, data } = await getProjectListByOnTime(id);
      if (code == '0000') {
        this.projectList = data;
      }
    },
    //获取计划列表
    async getPlanList() {
      let params = {
        projectId: this.projectId,
        beginTime: !!this.time ? this.time[0] : '',
        endTime: !!this.time ? this.time[1] : '',
        inspectType: this.inspectTypeId,
        status: this.status,
        pageSize: 10,
        pageNum: 1,
        type: 1
      };
      let { code, data } = await getPlanList(params);
      if (code == '0000') {
        this.currentPage = 1;
        this.selParams = params;
        this.tableData = data.list;
        this.total = data.total;
      }
    },
    // 分页
    async getPlanList2() {
      let { code, data } = await getPlanList(this.selParams);
      if (code == '0000') {
        this.tableData = data.list;
        this.total = data.total;
      }
    },
    //获取计划详情列表
    async getPlanDetailList() {
      let params = {
        planId: this.detailObj.planId,
        startTime:
          !!this.detailObj.time && this.detailObj.time.length > 0
            ? this.detailObj.time[0] + ' 00:00:00'
            : '',
        endTime:
          !!this.detailObj.time && this.detailObj.time.length > 0
            ? this.detailObj.time[1] + ' 23:59:59'
            : '',
        structureId: this.detailObj.structureId,
        status: this.detailObj.status,
        pageSize: this.detailPageSize
      };
      let { code, data } = await getPlanDetailList(params);
      if (code == '0000') {
        let statusList = data.list.map((item) => {
          return item.status;
        });
        if (statusList.includes(0)) {
          this.detailObj.changeMoney = false;
        } else {
          this.detailObj.changeMoney = true;
        }
        this.tableData2 = data.list;
      }
    },
    //获取结构物列表
    async getStructureList(id) {
      let params = {
        powerId: this.$store.getters.getActiveIndex,
        projectId: id || this.planForm.projectId
      };
      let { code, data } = await getStructureList(params);
      if (code == '0000') {
        let obj = {
          name: '全选',
          id: -1
        };
        let arry = JSON.parse(JSON.stringify(data));
        arry.unshift(obj);
        this.structureList = data;
        this.structureList2 = arry;
      }
    },
    //获取指派时间段
    async getreceiveTime(projectId) {
      let id = projectId ? projectId : this.planForm.projectId;
      let { code, data } = await getreceiveTime(id, 1);
      if (code == '0000') {
        let beginTime = null;
        let endTime = null;
        if (!!data) {
          beginTime = data[0].beginTime;
          for (let i = 0; i < data.length; i++) {
            let date1 =
              data[i].endTime.split('-')[0] +
              '-' +
              data[i].endTime.split('-')[1];
            let date2 = data[i + 1]
              ? data[i + 1].beginTime.split('-')[0] +
                '-' +
                data[i + 1].beginTime.split('-')[1]
              : '';
            if (data[i + 1] && date1 == date2) {
              endTime = data[i + 1].endTime;
            } else {
              endTime = data[i].endTime;
              break;
            }
          }
        }
        this.startreceiveTime = beginTime;
        this.endreceiveTime = endTime;
      }
    },
    //获取当月首次巡查时间
    async getfirstDate() {
      let params = {
        month: this.planForm.planTime,
        structureId: this.planForm.structureId,
        projectId: this.planForm.projectId
      };
      let { code, data } = await getfirstDate(params);
      if (code == '0000') {
        this.firstDate = data;
        let arry = [];
        let arry1 = this.planForm.structureId.concat();
        arry1.map((id) => {
          for (let key in data) {
            if (id == key) {
              this.structureList.map((item) => {
                if (item.id == id) {
                  let obj = {
                    inspectionTime: data[key],
                    structureId: item.id,
                    name: item.name
                  };
                  arry.push(obj);
                }
              });
            }
          }
        });
        this.planForm.planDetailList = arry;
      }
    },

    //table 样式
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex % 2 == 1) {
        return 'first-row';
      } else {
        return 'second-row';
      }
    },
    //点击添加计划
    addPlab() {
      this.planDialogTitle = '添加计划';
      this.showPlanDialog = true;
    },
    //点击修改计划
    updatePlan(data, status) {
      if (status != 0) {
        return;
      }
      this.getreceiveTime(data.projectId);
      this.$nextTick(async () => {
        await this.getStructureList(data.projectId);
        let arry = [];
        !!data.planDetailList &&
          data.planDetailList.map((item) => {
            if (item) {
              arry.push(item.structureId);
              this.structureList.map((item2) => {
                if (item.structureId == item2.id) {
                  item.name = item2.name;
                }
              });
            }
          });
        let structureList = [...new Set(arry)];
        if (structureList.length > 0) {
          data.structureId = structureList;
        }
        this.planForm = JSON.parse(JSON.stringify(data));
        this.planDialogTitle = '修改计划';
        this.showPlanDialog = true;
      });
    },
    //删除计划
    deleteItem(item, status) {
      if (status != 0) {
        return;
      }
      //删除框
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        showClose: true
      })
        .then(async () => {
          let { code } = await delPlan(item.id);
          if (code == '0000') {
            this.$message({
              type: 'success',
              message: '删除成功!',
              showClose: true,
              duration: 2000
            });
            await this.getPlanList();
          }
        })
        .catch(() => {});
    },
    //点击计划详情
    detail(data) {
      let date =
        (data.planTime ? data.planTime.split('-')[0] : '') +
        '年' +
        (data.planTime ? data.planTime.split('-')[1] : '') +
        '月';
      this.detailPageSize = data.count;
      this.detailDialogTitle = date;
      this.detailStartTime = data.planTime;
      this.detailBudget = data.budget;
      this.detailExpenditure = data.expenditure;
      this.detailObj.changeMoney = data.status == 2 ? true : false;
      this.detailObj.planId = data.id;
      this.detailObj.time = data.planTime
        ? [data.planTime + '-01', this.$utils.getMonthDay(data.planTime, true)]
        : [];
      if (data.status == 0 || data.status == 1) {
        this.optStatus = true;
      } else {
        this.optStatus = false;
      }
      this.detailDialog = true;
      this.getStructureList(data.projectId);
      this.getreceiveTime(data.projectId);
      this.getPlanDetailList();
    },

    //创建计划列表
    createList(index) {
      console.log(index);
      //判断是否点击全选
      if (this.planForm.structureId.includes(-1) && !this.selectAll) {
        let arry = [];
        this.structureList2.map((item) => {
          arry.push(item.id);
        });
        this.planForm.structureId = arry;
        this.selectAll = true;
      }
      if (
        this.planForm.structureId.length == this.structureList2.length - 1 &&
        this.selectAll &&
        !this.planForm.structureId.includes(-1)
      ) {
        this.planForm.structureId = [];
        this.selectAll = false;
      }
      //判断是否全选
      if (
        this.structureList2.length != this.planForm.structureId.length &&
        this.planForm.structureId.includes(-1)
      ) {
        this.planForm.structureId.splice(
          this.planForm.structureId.findIndex((id) => id == -1),
          1
        );
        this.selectAll = false;
      }
      if (
        this.structureList2.length - 1 == this.planForm.structureId.length &&
        !this.planForm.structureId.includes(-1)
      ) {
        this.planForm.structureId.unshift(-1);
        this.selectAll = true;
      }
      //查询本月首次巡查日期
      if (this.planForm.structureId.length > 0 && !!this.planForm.planTime) {
        this.getfirstDate();
      } else {
        this.planForm.planDetailList = [];
      }
    },
    //详情修改金额
    async updatePlanAmount() {
      let params = {
        budget: this.detailBudget,
        expenditure: this.detailExpenditure,
        planId: this.detailObj.planId
      };
      let { code } = await updatePlanAmount(params);
      if (code == '0000') {
        this.$message({
          type: 'success',
          message: '修改成功!',
          showClose: true,
          duration: 2000
        });
        this.changeMoney = false;
        await this.getPlanList2();
      }
    },
    //提交、修改计划
    formSubmit(formName) {
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          let reg = /^\d+(\.\d+)?$/;
          if (this.planForm.budget != '' && !reg.test(this.planForm.budget)) {
            this.$message({
              type: 'warning',
              message: '请输入正确的预计金额！',
              showClose: true,
              duration: 2000
            });
            return;
          }
          if (
            this.planForm.expenditure != '' &&
            !reg.test(this.planForm.expenditure)
          ) {
            this.$message({
              type: 'warning',
              message: '请输入正确的实际金额！',
              showClose: true,
              duration: 2000
            });
            return;
          }
          for (let i = 0; i < this.planForm.planDetailList.length; i++) {
            if (
              this.planForm.planDetailList[i].inspectionTime == '' ||
              this.planForm.planDetailList[i].inspectionTime == null
            ) {
              this.$message({
                type: 'warning',
                message: '首次巡查日期不能为空！',
                showClose: true,
                duration: 2000
              });
              return;
            }
          }
          this.planForm.budget = Number(this.planForm.budget);
          this.planForm.expenditure = Number(this.planForm.expenditure);
          let typeName = '';
          this.typeList.map((item) => {
            if (item.id == this.planForm.type) {
              typeName = item.label;
            }
          });
          this.planForm.name =
            this.planForm.planTime.split('-')[0] +
            '年' +
            this.planForm.planTime.split('-')[1] +
            '月' +
            typeName;
          if (this.planDialogTitle == '添加计划') {
            let { code, data } = await addPlan(this.planForm);
            if (code == '0000') {
              this.$message({
                type: 'success',
                message: '添加成功！',
                showClose: true,
                duration: 2000
              });
              await this.dialogClose();
              await this.getProjectListByModel();
              await this.getPlanList();
            } else if (code == '6039') {
              this.$message({
                type: 'warning',
                dangerouslyUseHTMLString: true,
                message: `<div style="padding-right:10px;white-space: break-spaces;">${data}</div>`,
                showClose: true,
                duration: 5000
              });
              await this.dialogClose();
              await this.getPlanList();
            }
          } else {
            let { code, data } = await updPlan(this.planForm);
            if (code == '0000') {
              this.$message({
                type: 'success',
                message: '修改成功',
                showClose: true,
                duration: 2000
              });
              await this.dialogClose();
              await this.getPlanList();
            } else if (code == '6039') {
              this.$message({
                type: 'warning',
                dangerouslyUseHTMLString: true,
                message: `<div style="padding-right:10px;white-space: break-spaces;">${data}</div>`,
                showClose: true,
                duration: 5000
              });
              await this.dialogClose();
              await this.getPlanList();
            }
          }
        } else {
          return false;
        }
      });
    },
    //点击添加细项
    addDetail() {
      this.defaultValue = this.detailStartTime + '-01';
      this.addDetailDialog = true;
      this.addDetailTitle = '添加细项';
    },
    //点击修改细项
    updateDetail(data, status) {
      if (status != 0) {
        return;
      }
      this.addDetailForm.structureId = data.structureId;
      this.addDetailForm.id = data.id;
      this.addDetailForm.inspectionTime = data.inspectionTime;
      this.addDetailDialog = true;
      this.addDetailTitle = '修改细项';
    },

    //添加、修改细项
    addDetailSubmit(formName) {
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          //先判断是否是不同的结构物
          //在判断添加修改的日期是否已经存在
          for (let index = 0; index < this.tableData2.length; index++) {
            const element = this.tableData2[index];
            if (element.structureId == this.addDetailForm.structureId) {
              if (element.inspectionTime == this.addDetailForm.inspectionTime) {
                this.$message({
                  type: 'warning',
                  message: '选择的日期已经存在！',
                  showClose: true,
                  duration: 2000
                });
                return;
              }
            }
          }
          this.$nextTick(async () => {
            let params = {
              planId: this.detailObj.planId,
              id: this.addDetailForm.id,
              structureId: this.addDetailForm.structureId,
              inspectionTime: this.addDetailForm.inspectionTime
            };

            if (this.addDetailTitle == '添加细项') {
              let { code, data } = await addPlanDetail(params);
              console.log(code);
              if (code == '0000') {
                this.$message({
                  type: 'success',
                  message: '添加成功！',
                  showClose: true,
                  duration: 2000
                });
                await this.addDetailDialogClose();
                await this.getPlanDetailList();
                await this.getPlanList();
              }
            } else {
              let { code } = await updPlanDetail(params);
              if (code == '0000') {
                this.$message({
                  type: 'success',
                  message: '修改成功！',
                  showClose: true,
                  duration: 200
                });
                await this.addDetailDialogClose();
                await this.getPlanDetailList();
                await this.getPlanList();
              }
            }
          });
        } else {
          return false;
        }
      });
    },

    //删除细项
    delPlanDetail(id, status) {
      if (status != 0) {
        return;
      }
      //删除框
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        showClose: true
      })
        .then(async () => {
          let { code } = await delPlanDetail(id);
          if (code == '0000') {
            this.$message({
              type: 'success',
              message: '删除成功!',
              showClose: true,
              duration: 2000
            });
            await this.getPlanDetailList();
            await this.getPlanList();
          }
        })
        .catch(() => {});
    },

    //选择项目
    projectChange(id) {
      this.planForm.structureId = [];
      this.structureList = [];
      if (id != '') {
        this.structureTitle = '暂无数据';
        this.getreceiveTime();
      } else {
        this.structureTitle = '请先选择项目';
        this.startreceiveTime = null;
        this.endreceiveTime = null;
      }
      this.$nextTick(() => {
        this.getStructureList();
      });
    },
    //关闭弹框
    dialogClose() {
      this.showPlanDialog = false;
      this.addDetailDialog = false;
      this.planForm = {
        projectId: '',
        type: '',
        budget: '',
        planTime: '',
        structureId: [],
        expenditure: '',
        planDetailList: []
      };
      this.startreceiveTime = null;
      this.endreceiveTime = null;
      this.structureList = [];
      this.planForm.planDetailList = [];
    },
    detailDialogClose() {
      this.detailDialog = false;
      this.detailObj = {
        planId: '',
        structureId: '',
        time: '',
        status: ''
      };
      this.startreceiveTime = null;
      this.endreceiveTime = null;
      this.tableData2 = [];
      this.structureList = [];
      this.changeMoney = false;
    },
    //关闭细项弹框
    addDetailDialogClose() {
      this.addDetailDialog = false;
      this.addDetailForm = {
        structureId: '',
        inspectionTime: '',
        id: ''
      };
    },
    //分页
    handleCurrentChange(val) {
      this.selParams.pageNum = val;
      this.currentPage = val;
      this.$nextTick(async () => {
        await this.getPlanList2();
      });
    }
  }
};
</script>
<style lang="scss" scoped>
.plane {
  padding: 24px;
  display: flex;
  flex-direction: column;
  .border_title_span {
    font-size: 18px;
    color: #262626;
  }
  .planTop {
    padding-bottom: 20px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    .planTop_select_one {
      margin-right: 20px;
    }
    .my_select1 {
      width: 282px;
    }
    .my_select2 {
      width: 128px;
    }
  }
  .table_div {
    .danger {
      width: 56px;
      height: 24px;
      font-size: 14px;
      color: #fff;
      background: #eb5757;
      border-radius: 4px;
      margin: 0 auto;
      display: flex;
      align-items: center;
      justify-content: center;
    }
    .working {
      width: 56px;
      height: 24px;
      font-size: 14px;
      color: #fff;
      background: #419aff;
      border-radius: 4px;
      margin: 0 auto;
      display: flex;
      align-items: center;
      justify-content: center;
    }
    .success {
      width: 56px;
      height: 24px;
      font-size: 14px;
      color: #fff;
      background: #27ae60;
      border-radius: 4px;
      margin: 0 auto;
      display: flex;
      align-items: center;
      justify-content: center;
    }
    /deep/ .el-table th {
      font-size: 0.7vw;
      font-weight: bold;
      color: #333;
      padding: 1.067vh 0;
      border: 0;
    }
    /deep/ .el-table td {
      font-size: 0.7vw;
      color: #333;
      padding: 1.715vh 0;
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
  }
}

.plane .pageNation {
  text-align: center;
  padding: 1vw 0;
  /deep/ button {
    background: transparent;
    border: 1px solid #d9d9d9;
    border-radius: 4px;
    background: none !important;
  }
  /deep/ .el-pager li {
    background: transparent;
    border: 1px solid #d9d9d9;
    border-radius: 4px;
  }
  /deep/ .el-pager .active {
    background: none !important;
    color: #1890ff !important;
    border: 1px solid #1890ff;
  }
}

.plane .table-button2 {
  width: 2.91666vw;
  height: 2.222vh;
  .table-span {
    position: absolute;
    left: 1.3vw;
    top: 1.8vh;
  }
}

.table_link {
  margin-left: 1.04166vw;
}
.disabled {
  display: none;
  opacity: 0.5;
  cursor: not-allowed;
}
// 计划弹框
.planDialog {
  .planBox {
    padding: 0 48px;
    display: flex;
    flex-wrap: wrap;
    /deep/ .el-form-item {
      width: 50%;
      // margin-bottom: 1.853vh;
      margin-bottom: 20px;
      display: flex;
      align-items: center;
      justify-content: flex-end;
    }
    /deep/ .el-form-item__label {
      padding-right: 5px;
    }
    /deep/ .el-select,
    /deep/ .el-input {
      width: 316px;
    }
    /deep/ .el-tag {
      max-width: 80%;
      overflow: hidden;
      text-overflow: ellipsis;
    }
    // /deep/ .el-input__inner {
    //   background-color: transparent;
    //   color: #333;
    // }
  }
  .planTable {
    width: 100%;
    padding: 2.594vh 28px 4.445vh 38px;
    display: flex;
    flex-direction: column;
    .head {
      padding-right: 20px;
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
          width: 180px;
          border-left: 1px solid #d9d9d9;
        }
      }
    }
    .content {
      position: relative;
      max-height: 180px;
      padding-right: 20px;
      overflow: auto;
      .planItem {
        display: flex;
        align-items: center;
        .text {
          width: 359px;
          height: 36px;
          font-size: 14px;
          color: #333;
          border-right: 1px solid #d9d9d9;
          border-bottom: 1px solid #d9d9d9;
          display: flex;
          align-items: center;
          justify-content: center;
          overflow: hidden;
          span {
            width: 100%;
            padding: 0 20px;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
          }
          &:first-child {
            width: 180px;
            border-left: 1px solid #d9d9d9;
          }
          /deep/ .el-input__inner {
            height: 100%;
            background-color: transparent;
            color: #333;
            line-height: unset;
            text-align: center;
            border: 0;
          }
        }
      }
      &::-webkit-scrollbar {
        position: absolute;
        right: 0;
        width: 8px;
      }
      &::-webkit-scrollbar-thumb {
        background: #c4c4c4;
        border-radius: 8px;
      }
    }
    .scrollBar {
      padding-right: 12px;
    }
    .noData {
      width: 100%;
      padding-right: 20px;
      div {
        height: 36px;
        line-height: 36px;
        font-size: 14px;
        color: #c0c4cc;
        border: 1px solid #d9d9d9;
        border-top: 0;
        text-align: center;
      }
    }
  }
  .btn {
    font-size: 14px;
    padding: 0 68px;
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
    width: 960px;
  }
  /deep/ .el-dialog__body {
    padding: 0 0 4.484vh;
    display: flex;
    flex-direction: column;
    align-items: center;
  }
}

//计划详情弹框
.planDetail {
  .detail_dialog_div {
    width: 100%;
    padding-bottom: 26px;
    display: flex;
    align-items: center;
    justify-content: flex-end;
    .btnAdd {
      width: 98px;
      margin-right: 20px;
    }
    .my_select1 {
      width: 280px;
      margin-right: 20px;
    }
    .my_select2 {
      width: 126px;
      margin-right: 20px;
    }
  }
  .detailTable {
    width: 100%;
    .danger {
      width: 56px;
      height: 24px;
      font-size: 14px;
      color: #fff;
      background: #eb5757;
      border-radius: 4px;
      margin: 0 auto;
      display: flex;
      align-items: center;
      justify-content: center;
    }
    .working {
      width: 56px;
      height: 24px;
      font-size: 14px;
      color: #fff;
      background: #419aff;
      border-radius: 4px;
      margin: 0 auto;
      display: flex;
      align-items: center;
      justify-content: center;
    }
    .success {
      width: 56px;
      height: 24px;
      font-size: 14px;
      color: #fff;
      background: #27ae60;
      border-radius: 4px;
      margin: 0 auto;
      display: flex;
      align-items: center;
      justify-content: center;
    }
    /deep/ .el-table__body-wrapper {
      max-height: 480px;
      overflow: auto;
      &::-webkit-scrollbar {
        width: 8px;
      }
      &::-webkit-scrollbar-thumb {
        background: #c4c4c4;
        border-radius: 8px;
      }
    }
    /deep/ .el-table th {
      font-size: 0.7vw;
      font-weight: bold;
      color: #333;
      // padding: 1.067vh 0;
      border: 0;
    }
    /deep/ .el-table td {
      font-size: 0.7vw;
      color: #333;
      // padding: 1.715vh 0;
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
  }
  .detailBottom {
    width: 100%;
    margin-top: 24px;
    display: flex;
    .btmItem {
      margin-right: 20px;
      .btmInput {
        width: 150px;
        height: 40px;
      }
      /deep/ .el-input-group__append {
        height: 100%;
        background: transparent;
        display: flex;
        align-items: center;
        justify-content: center;
      }
      /deep/ .el-input.is-disabled .el-input__inner {
        color: #606266;
        background: transparent;
      }
    }
    .btmBtn {
      .change,
      .keep {
        padding: 8px 12px;
        font-size: 14px;
        color: #fff;
        margin-right: 10px;
        background: #419aff;
        border-radius: 4px;
      }
      .cancel {
        padding: 8px 12px;
        font-size: 14px;
        color: #333;
        border: 1px solid #dcdfe6;
        border-radius: 4px;
      }
      span {
        cursor: pointer;
      }
    }
    div {
      display: flex;
      align-items: center;
      justify-content: center;
      span {
        white-space: nowrap;
      }
    }
  }
  /deep/ .el-dialog {
    width: 986px;
  }
  /deep/ .el-dialog__body {
    padding: 3.705vh 36px 4.484vh;
    display: flex;
    flex-direction: column;
    align-items: center;
  }
}

//添加细项弹框
.addDetail {
  .addDetailBox {
    width: 100%;
    padding-right: 12px;
    display: flex;
    flex-direction: column;
    .el-form-item {
      display: flex;
      align-items: center;
      justify-content: flex-end;
      /deep/ .el-form-item__label {
        padding-right: 5px;
      }
      /deep/ .el-select,
      /deep/ .el-date-editor {
        width: 316px;
      }
    }
  }
  .btn {
    font-size: 14px;
    padding: 0 68px;
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
    width: 490px;
  }
  /deep/ .el-dialog__body {
    padding: 3.705vh 30px 4.484vh;
    display: flex;
    flex-direction: column;
    align-items: center;
  }
}

//下拉框没数据
.emptySelect {
  padding: 10px 0;
  margin: 0;
  text-align: center;
  color: #999;
  font-size: 0.7vw;
}

//斑马纹样式
/deep/ .first-row {
  background: #ffffff;
}
/deep/ .second-row {
  background: #f2f4f6;
}
//月份范围选择样式
/deep/ .el-date-editor .el-range-separator {
  width: unset;
}
//表格空数据样式
/deep/ .el-table__empty-block {
  border-top: 1px solid #ebeef5;
}
</style>
