<template>
  <div class="planM animate__animated animate__fadeIn">
    <!-- <p class="border_p base-font">维养计划</p> -->
    <div class="border_div2">
      <div>
        <el-button v-if="addOpt" type="primary" @click="addPlab"
          >添加计划</el-button
        >
      </div>
      <div>
        <el-select
          v-model="projectPlanId"
          placeholder="请选择项目"
          class="my_select1"
          clearable
        >
          <el-option
            v-for="item in projectPlanList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          >
          </el-option>
        </el-select>
        <el-date-picker
          class="my_select1"
          v-model="time"
          type="monthrange"
          clearable
          align="center"
          value-format="yyyy-MM"
          range-separator="至"
          start-placeholder="开始月份"
          end-placeholder="结束月份"
        >
        </el-date-picker>
        <el-select
          v-model="status"
          class="my_select2"
          placeholder="执行状态"
          clearable
        >
          <el-option
            v-for="item in statusList"
            :key="item.id"
            :label="item.label"
            :value="item.id"
          >
          </el-option>
        </el-select>
        <el-button type="primary" class="border_button" @click="getPlanList"
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
        <el-table-column prop="id" label="序号" width="100" align="center">
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
        <el-table-column prop="planTime" label="维修月份" align="center">
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
        <el-table-column prop="count" label="维修项数量" align="center">
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
        >
          <template slot-scope="scope">
            <div class="textOF">{{ scope.row.realName || '-' }}</div>
          </template>
        </el-table-column>
        <el-table-column
          prop="createTime"
          label="创建时间"
          align="center"
          width="160"
          show-overflow-tooltip
        >
        </el-table-column>
        <el-table-column label="操作" align="center" width="280">
          <template slot-scope="scope">
            <div class="tableOpt">
              <div @click="detailPlan(scope.row)">计划详情</div>
              <div @click="exportPlan(scope.row)">导出计划</div>
              <div
                v-if="updateOpt"
                :class="{ disabled: scope.row.status != 0 }"
                @click="updatePlan(scope.row, scope.row.status)"
              >
                修改
              </div>
              <div
                v-if="deleteOpt"
                :class="{ disabled: scope.row.status != 0 }"
                @click="deleteItem(scope.row, scope.row.status)"
              >
                删除
              </div>
            </div>
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

    <!-- 添加、修改计划弹框 -->
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
        <el-form-item label="预计金额：" prop="budget">
          <el-input
            v-model="planForm.budget"
            placeholder="输入金额"
            maxlength="8"
            clearable
          ></el-input>
        </el-form-item>
        <el-form-item label="维修月份：" prop="planTime">
          <el-date-picker
            v-model="planForm.planTime"
            type="month"
            :clearable="false"
            :picker-options="pickerOptions"
            placeholder="请选择年月"
            value-format="yyyy-MM"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="实际金额：" prop="expenditure">
          <el-input
            v-model="planForm.expenditure"
            placeholder="输入金额"
            maxlength="8"
            clearable
          ></el-input>
        </el-form-item>
      </el-form>
      <div class="repairItems">
        <div>
          <el-button
            v-if="addOpt"
            type="primary"
            @click="showAddItems('planForm')"
            >添加维修项</el-button
          >
        </div>
        <div class="repairTable">
          <el-table
            :data="planForm.itemList"
            :row-class-name="tableRowClassName"
          >
            <el-table-column prop="id" label="序号" width="70" align="center">
              <template slot-scope="scope">
                {{ scope.$index + 1 }}
              </template>
            </el-table-column>
            <el-table-column label="结构物名称" align="center">
              <template slot-scope="scope">
                <div class="textOF">
                  {{ getStructureName(scope.row.structureId) }}
                </div>
              </template>
            </el-table-column>
            <el-table-column
              prop="name"
              label="维修项名称"
              align="center"
              show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
              prop="proposedTime"
              label="拟定维修日期"
              align="center"
            >
            </el-table-column>
            <el-table-column label="维修类型" align="center">
              <template slot-scope="scope">
                <div>
                  {{
                    scope.row.type == 0
                      ? '日常保养'
                      : scope.row.type == 1
                      ? '小修'
                      : scope.row.type == 2
                      ? '中修'
                      : '大修'
                  }}
                </div>
              </template>
            </el-table-column>
            <el-table-column label="病害类型" align="center">
              <template slot-scope="scope">
                <div>{{ scope.row.damageType || '-' }}</div>
              </template>
            </el-table-column>
            <el-table-column
              label="病害程度/异常部位"
              align="center"
              width="145"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <div class="textOF">
                  {{ scope.row.showItem || '-' }}
                </div>
              </template>
            </el-table-column>
            <el-table-column
              label="病害或异常说明"
              align="center"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <div class="textOF">
                  {{ scope.row.remarks || scope.row.diseaseRemark || '-' }}
                </div>
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center" width="80">
              <template slot-scope="scope">
                <div class="tableOpt">
                  <div
                    v-if="deleteOpt"
                    @click="remove(scope.$index, scope.row)"
                  >
                    移除
                  </div>
                </div>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>
      <div class="btn">
        <span class="keep" @click="formSubmit('planForm')">确定</span>
        <span class="cancel" @click="dialogClose">取消</span>
      </div>
    </el-dialog>

    <!-- 添加维修项弹框 -->
    <el-dialog
      class="itemsDialog"
      title="添加维修项"
      :visible.sync="showItemsDialog"
      :before-close="itemsDialogClose"
      append-to-body
      modal-append-to-body
      :close-on-click-modal="false"
    >
      <el-form
        class="itemsBox"
        ref="itemsForm"
        :model="itemsForm"
        :rules="itemsRules"
      >
        <el-form-item label="选择结构物：" prop="structureId">
          <el-select v-model="itemsForm.structureId" placeholder="请选择结构物">
            <el-option
              v-for="item in structureList"
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
        <el-form-item label="维修项名称：" prop="name">
          <el-input
            v-model="itemsForm.name"
            placeholder="输入维修项名称"
            maxlength="15"
            show-word-limit
            clearable
          ></el-input>
        </el-form-item>

        <el-form-item label="拟定维修日期：" prop="proposedTime">
          <el-date-picker
            v-model="itemsForm.proposedTime"
            type="date"
            :clearable="false"
            :picker-options="pickerPlanList"
            placeholder="请选择日期"
            :default-value="
              (showPlanDialog ? planForm.planTime : detailObj.planTime) + '-01'
            "
            value-format="yyyy-MM-dd"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="维修类型：" prop="type">
          <el-select v-model="itemsForm.type" placeholder="请选择维修类型">
            <el-option
              v-for="item in typeList"
              :key="item.id"
              :label="item.label"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div class="btn">
        <span class="add" @click="showDisease('itemsForm')">选择病害</span>
        <span class="keep" @click="addItems('itemsForm')">直接保存</span>
      </div>
    </el-dialog>

    <!-- 添加病害弹框 -->
    <el-dialog
      class="diseaseDialog"
      title="选择病害"
      :visible.sync="showDiseaseDialog"
      append-to-body
      modal-append-to-body
      :close-on-click-modal="false"
    >
      <div class="top">
        <span class="title"
          >请选择病害，若维修项不在病害列表中，请直接点击保存</span
        >
        <div class="condition">
          <el-input
            class="keyword"
            v-model="keyWord"
            placeholder="关键词搜索"
            clearable
          ></el-input>
          <el-button type="primary" @click="getLastInspectionDisease"
            >查询</el-button
          >
        </div>
      </div>
      <div v-if="diseaseNum == 1" class="diseaseTable">
        <el-table
          ref="diseaseTable"
          :data="diseaseTableData"
          :row-class-name="tableRowClassName"
          @selection-change="handleSelectionChange"
          @select-all="handleSelectionChangeAll"
        >
          <el-table-column type="selection" width="50" align="center">
          </el-table-column>
          <el-table-column
            prop="structureName"
            label="结构物"
            align="center"
            show-overflow-tooltip
          >
          </el-table-column>
          <el-table-column
            label="病害类型"
            align="center"
            show-overflow-tooltip
          >
            <template slot-scope="scope">
              <div class="textOF">{{ scope.row.damageType || '-' }}</div>
            </template>
          </el-table-column>
          <el-table-column
            label="病害程度/异常部位"
            align="center"
            width="145"
            show-overflow-tooltip
          >
            <template slot-scope="scope">
              <div class="textOF">
                {{ scope.row.showItem || '-' }}
              </div>
            </template>
          </el-table-column>
          <el-table-column
            label="病害或异常说明"
            align="center"
            show-overflow-tooltip
          >
            <template slot-scope="scope">
              <div class="textOF">{{ scope.row.remarks || '-' }}</div>
            </template>
          </el-table-column>
          <el-table-column
            prop="createTime"
            label="巡查时间"
            align="center"
            width="160"
          >
          </el-table-column>
          <el-table-column label="病害状态" align="center">
            <template>
              <div style="color: #ff5f5f">待修复</div>
            </template>
          </el-table-column>
          <el-table-column label="病害照片" align="center">
            <template slot-scope="scope">
              <div class="tableOpt">
                <div
                  v-if="scope.row.photo.length > 0"
                  @click="seePhoto(scope.row)"
                >
                  查看
                </div>
                <span v-else>-</span>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div v-else class="diseaseTable">
        <el-table
          ref="diseaseTable"
          :data="diseaseTableData"
          :row-class-name="tableRowClassName"
          @selection-change="handleSelectionChange"
          @select-all="handleSelectionChangeAll"
        >
          <el-table-column type="selection" width="50" align="center">
          </el-table-column>
          <el-table-column
            label="病害类型"
            align="center"
            show-overflow-tooltip
          >
            <template slot-scope="scope">
              <div class="textOF">{{ scope.row.damageType || '-' }}</div>
            </template>
          </el-table-column>
          <el-table-column
            label="异常位置"
            align="center"
            width="145"
            show-overflow-tooltip
          >
            <template slot-scope="scope">
              <div class="textOF">
                {{ scope.row.showItem || '-' }}
              </div>
            </template>
          </el-table-column>
          <el-table-column
            label="异常描述"
            align="center"
            show-overflow-tooltip
          >
            <template slot-scope="scope">
              <div class="textOF">{{ scope.row.remarks || '-' }}</div>
            </template>
          </el-table-column>
          <el-table-column label="判定" align="center">
            <template slot-scope="scope">
              <div>
                {{
                  scope.row.exceptionType == 1
                    ? '一般异常'
                    : scope.row.exceptionType == 2
                    ? '严重异常'
                    : '-'
                }}
              </div>
            </template>
          </el-table-column>
          <el-table-column label="养护措施" align="center">
            <template>
              <div style="color: #ff5f5f">维修处置</div>
            </template>
          </el-table-column>
          <el-table-column label="病害照片" align="center">
            <template slot-scope="scope">
              <div class="tableOpt">
                <div
                  v-if="scope.row.photo.length > 0"
                  @click="seePhoto(scope.row)"
                >
                  查看
                </div>
                <span v-else>-</span>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="btn">
        <span class="keep" @click="keepDisease">保存</span>
        <span class="cancel" @click="closeDiseaseDialog">取消</span>
      </div>
    </el-dialog>

    <!-- 计划详情弹框 -->
    <el-dialog
      class="planDetail"
      :title="dialogTitle"
      :visible.sync="showDetailDialog"
      :before-close="detailDialogClose"
      append-to-body
      modal-append-to-body
      :close-on-click-modal="false"
    >
      <div class="detail_dialog_div">
        <div>
          <el-button v-if="addOpt" type="primary" @click="addDetail"
            >添加细项</el-button
          >
        </div>
        <div class="selItem">
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
            value-format="yyyy-MM-dd"
            range-separator="至"
            start-placeholder="开始月份"
            end-placeholder="结束月份"
          >
          </el-date-picker>
          <el-select
            v-model="detailObj.state"
            class="my_select2"
            placeholder="完成状态"
            clearable
          >
            <el-option
              v-for="item in statusList2"
              :key="item.id"
              :label="item.label"
              :value="item.id"
            >
            </el-option>
          </el-select>
          <el-button
            type="primary"
            class="detail_dialog_button"
            @click="getPlanDetailList"
            >查询</el-button
          >
        </div>
      </div>
      <div class="detailTable">
        <el-table
          class="tableBox"
          :data="tablePlanData"
          :row-class-name="tableRowClassName"
        >
          <el-table-column type="index" label="序号" width="80" align="center">
          </el-table-column>
          <el-table-column label="结构物名称" align="center">
            <template slot-scope="scope">
              <div class="textOF">
                {{ getStructureName(scope.row.structureId) }}
              </div>
            </template>
          </el-table-column>
          <el-table-column
            label="维修项名称"
            align="center"
            show-overflow-tooltip
          >
            <template slot-scope="scope">
              <div class="textOF">{{ scope.row.name || '-' }}</div>
            </template>
          </el-table-column>
          <el-table-column
            prop="proposedTime"
            label="拟定完成时间"
            align="center"
            width="120"
          >
          </el-table-column>
          <el-table-column prop="type" label="维修类型" align="center">
            <template slot-scope="scope">
              <div>
                {{
                  scope.row.type == 0
                    ? '日常保养'
                    : scope.row.type == 1
                    ? '小修'
                    : scope.row.type == 2
                    ? '中修'
                    : '大修'
                }}
              </div>
            </template>
          </el-table-column>
          <el-table-column
            label="病害类型"
            align="center"
            show-overflow-tooltip
          >
            <template slot-scope="scope">
              <div class="textOF">{{ scope.row.damageType || '-' }}</div>
            </template>
          </el-table-column>
          <el-table-column
            label="病害程度/异常部位"
            align="center"
            width="145"
            show-overflow-tooltip
          >
            <template slot-scope="scope">
              <div class="textOF">
                {{ scope.row.showItem || '-' }}
              </div>
            </template>
          </el-table-column>
          <el-table-column
            label="病害或异常说明"
            align="center"
            width="120"
            show-overflow-tooltip
          >
            <template slot-scope="scope">
              <div class="textOF">{{ scope.row.diseaseRemark || '-' }}</div>
            </template>
          </el-table-column>
          <el-table-column label="完成状态" align="center">
            <template slot-scope="scope">
              <div
                :class="{
                  working: scope.row.status == 0,
                  success: scope.row.status == 1,
                  danger: scope.row.status == 2
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
          <el-table-column v-if="deleteOpt" label="操作" align="center">
            <template slot-scope="scope">
              <div class="tableOpt">
                <div
                  v-if="scope.row.status == 0"
                  @click="delOneDatail(scope.row)"
                >
                  删除
                </div>
                <div v-else>-</div>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="detailBottom">
        <div class="btmItem">
          <span>预计金额：</span
          ><el-input
            class="btmInput"
            v-model.number="detailObj.budget"
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
            v-model.number="detailObj.expenditure"
            placeholder="-"
            maxlength="8"
            :disabled="!changeMoney"
          >
            <template slot="append">元</template></el-input
          >
        </div>
        <div v-if="detailObj.status == 2" class="btmBtn">
          <span v-if="!changeMoney" class="change" @click="changeMoney = true"
            >修改金额</span
          >
          <span v-else class="change" @click="updatePlanAmount">保存</span>
        </div>
      </div>
    </el-dialog>

    <!-- 查看图片弹框 -->
    <el-dialog
      class="dialogImage"
      title="病害图片"
      :visible.sync="dialogImgShow"
      append-to-body
      modal-append-to-body
      :close-on-click-modal="false"
    >
      <el-carousel
        class="diseasePhoto"
        :interval="4000"
        trigger="click"
        type="card"
      >
        <el-carousel-item v-for="item in dialogImageUrlList" :key="item">
          <el-image :src="item" :preview-src-list="dialogImageUrlList">
          </el-image>
        </el-carousel-item>
      </el-carousel>
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
  getLastInspectionDisease,
  exportPlan,
  getreceiveTime,
  addOneDatail,
  delOneDatail,
  removeDisease
} from '@/api/maintain/plan';
import { updatePlanAmount } from '@/api/normal/plan';
import {
  getProjectListByOnTime,
  getProjectListByModel,
  getStructureList
} from '@/api/common';
export default {
  components: {},
  props: {},
  data() {
    const _this = this;
    return {
      //筛选条件
      selParams: {
        projectId: '',
        beginTime: '',
        endTime: '',
        status: '',
        pageSize: 10,
        pageNum: 1,
        type: 4
      },
      projectId: '',
      projectList: [],
      projectPlanList: [],
      projectPlanId: '',
      time: '',
      status: '',
      statusList: [
        { id: 0, label: '未执行' },
        { id: 1, label: '执行中' },
        { id: 2, label: '已完成' }
      ],
      currentPage: 1,
      total: 0,
      tableData: [],
      //添加、修改弹框
      planDialogTitle: '',
      showPlanDialog: false,
      planRules: {
        projectId: [
          { required: true, message: '请选择项目', trigger: 'change' }
        ],
        planTime: [
          {
            required: true,
            message: '请选择日期',
            trigger: 'change'
          }
        ],
        budget: [
          // { required: true, message: '请输入手机号', trigger: 'blur' },
          { pattern: /^\d+(\.\d+)?$/, message: '请输入正确的数字' }
        ],
        expenditure: [
          // { required: true, message: '请输入手机号', trigger: 'blur' },
          { pattern: /^\d+(\.\d+)?$/, message: '请输入正确的数字' }
        ]
      },
      planForm: {
        projectId: '',
        budget: '',
        planTime: '',
        expenditure: '',
        type: 4,
        itemList: []
      },
      typeList: [
        { id: 0, label: '日常保养' },
        { id: 1, label: '小修' },
        { id: 2, label: '中修' },
        { id: 3, label: '大修' }
      ],
      userList: [],
      structureTitle: '请选择结构物！',
      structureList: [],
      repairTableData: [],
      //添加维修项
      showItemsDialog: false,
      itemsForm: {},
      itemsRules: {
        name: [
          { required: true, message: '请输入维修项名称', trigger: 'blur' }
        ],
        type: [
          { required: true, message: '请选择维修类型', trigger: 'change' }
        ],
        proposedTime: [
          {
            required: true,
            message: '请选择拟定维修日期',
            trigger: 'change'
          }
        ],
        structureId: [
          { required: true, message: '请选择结构物', trigger: 'change' }
        ]
      },
      pickerPlanList: {
        disabledDate(time) {
          let disBegin =
            (_this.showPlanDialog
              ? _this.planForm.planTime
              : _this.detailObj.planTime) + '-01';
          let disEnd = _this.showPlanDialog
            ? _this.$utils.getMonthDay(_this.planForm.planTime, true)
            : _this.$utils.getMonthDay(_this.detailObj.planTime, true); //true 表示返回最后一天日期
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
      //添加病害
      keyWord: '',
      showDiseaseDialog: false,
      diseaseTableData: [],
      diseaseNum: '',
      diseaseItem: {}, //选择的病害列表
      removeDiseaseList: [], //修改移除的病害
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
              time.getTime() < Date.now() - 24 * 3600 * 1000 ||
              time.getTime() < beginTime ||
              time.getTime() > lastTime
            );
          } else {
            return time.getTime() < Date.now() - 24 * 3600 * 1000;
          }
        }
      },
      //计划详情弹框
      dialogTitle: '',
      showDetailDialog: false,
      tablePlanData: [],
      detailObj: {
        structureId: '',
        time: '',
        state: '',
        planTime: ''
      },
      changeMoney: false,
      statusList2: [
        { id: 0, label: '未完成' },
        { id: 1, label: '已完成' },
        { id: 2, label: '已超时' }
      ],
      //查看图片弹框
      dialogImgShow: false,
      dialogImageUrlList: [],
      // 指派时间段最后的时间
      startreceiveTime: null,
      endreceiveTime: null,
      sameStructureId: false
    };
  },
  computed: {
    getStructureName() {
      return function (id) {
        for (let index = 0; index < this.structureList.length; index++) {
          const element = this.structureList[index];
          if (id == element.id) {
            return element.name;
          }
        }
      };
    }
  },
  beforeMount() {
    this.$utils.getAuthPage(this); //获取权限
  },
  mounted() {
    this.getPlanList(); //获取计划列表
    this.getProjectListByOnTime(); //获取项目列表
    this.getProjectListByModel(); //获取有计划的项目列表
    // this.getreceiveTime();
  },
  methods: {
    //获取项目列表
    async getProjectListByOnTime() {
      let id = this.$store.getters.getActiveIndex;
      let { code, data } = await getProjectListByOnTime(id);
      if (code == '0000') {
        this.projectList = data;
      }
    },
    //获取有计划的项目列表
    async getProjectListByModel() {
      let id = this.$store.getters.getActiveIndex;
      let { code, data } = await getProjectListByModel(id);
      if (code == '0000') {
        this.projectPlanList = data;
      }
    },
    //获取计划列表
    async getPlanList() {
      let params = {
        projectId: this.projectPlanId,
        beginTime: !!this.time ? this.time[0] : '',
        endTime: !!this.time ? this.time[1] : '',
        status: this.status,
        pageSize: 10,
        pageNum: 1,
        type: 2
      };
      let { code, data } = await getPlanList(params);
      if (code == '0000') {
        this.currentPage = 1;
        this.selParams = params;
        this.tableData = data.list;
        this.total = data.total;
      }
    },
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
        planId: this.detailObj.id,
        startTime: !!this.detailObj.time
          ? this.detailObj.time[0] + ' 00:00:00'
          : '',
        endTime: !!this.detailObj.time
          ? this.detailObj.time[1] + ' 23:59:59'
          : '',
        structureId: this.detailObj.structureId,
        status: this.detailObj.state,
        type: 2
      };
      let { code, data } = await getPlanDetailList(params);
      if (code == '0000') {
        let statusList = data.list.map((item) => {
          return item.status;
        });
        if (statusList.includes(0)) {
          this.detailObj.status = 0;
        } else {
          this.detailObj.status = 2;
        }
        this.tablePlanData = data.list;
      }
    },
    //计划详情修改金额
    async updatePlanAmount() {
      let params = {
        budget: this.detailObj.budget,
        expenditure: this.detailObj.expenditure,
        planId: this.detailObj.id
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
    //获取结构物列表
    async getStructureList(id) {
      let params = {
        powerId: this.$store.getters.getActiveIndex,
        projectId: id || this.planForm.projectId
      };
      let { code, data } = await getStructureList(params);
      if (code == '0000') {
        this.structureList = data;
      }
    },
    //获取桥梁最后一次巡查病害
    async getLastInspectionDisease() {
      let params = {
        keyWord: this.keyWord,
        id: this.itemsForm.structureId ? this.itemsForm.structureId : 0
      };
      let { code, data } = await getLastInspectionDisease(params);
      if (code == '0000') {
        data.map((item) => {
          if (!!item.photo) {
            item.photo.map((child) => {
              child.path = this.$basePath + child.path;
            });
          }
        });
        let arry = JSON.parse(JSON.stringify(data));
        if (arry.length > 0) {
          for (let i = arry.length - 1; i >= 0; i--) {
            this.planForm.itemList.map((item) => {
              if (item.id == arry[i].id) {
                arry.splice(i, 1);
              }
            });
          }
        }
        this.diseaseTableData = arry;
      }
    },
    //添加单条细项
    async addOneDatail(params) {
      let { code } = await addOneDatail(params);
      if (code == '0000') {
        this.$message({
          message: '添加成功！',
          type: 'success',
          showClose: true,
          duration: 2000
        });
        await this.getPlanDetailList();
        await this.getPlanList();
      }
    },
    //获取指派时间段
    async getreceiveTime(projectId) {
      let id = projectId ? projectId : this.planForm.projectId;
      let { code, data } = await getreceiveTime(id, 0);
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
        // this.startreceiveTime = !!data ? data[0].beginTime : null;
        // this.endreceiveTime = data ? data[0].endTime : null;
      }
    },
    //选择项目
    projectChange(id) {
      this.planForm.structureId = '';
      this.planForm.itemList = [];
      if (id != '') {
        this.structureTitle = '暂无数据';
        this.getreceiveTime(id);
      } else {
        this.structureTitle = '请先选择项目';
      }
      this.$nextTick(() => {
        this.getStructureList();
      });
    },
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex % 2 == 1) {
        return 'first-row';
      } else {
        return 'second-row';
      }
    },
    //分页
    handleCurrentChange(val) {
      this.selParams.pageNum = val;
      this.currentPage = val;
      this.getPlanList2();
    },

    //点击添加计划
    addPlab() {
      this.planDialogTitle = '添加计划';
      this.showPlanDialog = true;
    },
    //点击添加维修项
    showAddItems(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.showItemsDialog = true;
        } else {
          return false;
        }
      });
    },
    //点击添加病害
    showDisease(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.structureList.map((item) => {
            if (item.id == this.itemsForm.structureId) {
              if (item.structureType == 1) {
                this.diseaseNum = 1;
              } else {
                this.diseaseNum = 2;
              }
            }
          });
          this.getLastInspectionDisease();
          this.showDiseaseDialog = true;
        } else {
          return false;
        }
      });
    },
    //保存病害
    keepDisease() {
      let items = { ...this.itemsForm, ...this.diseaseItem };
      items.diseaseInstanceId = this.diseaseItem.id || '';
      if (this.showPlanDialog) {
        this.planForm.itemList.push(items);
        this.sortDataArray(this.planForm.itemList);
      }
      if (this.showDetailDialog) {
        // console.log('添加细项', items);
        items.planId = this.detailObj.id;
        this.addOneDatail(items);
      }
      this.$nextTick(() => {
        this.closeDiseaseDialog();
        this.itemsDialogClose();
      });
    },
    //点击计划详情
    detailPlan(data) {
      this.dialogTitle = data.name;
      this.showDetailDialog = true;
      this.detailObj = JSON.parse(JSON.stringify(data));
      this.getStructureList(data.projectId);
      this.getreceiveTime(data.projectId);
      this.getPlanDetailList();
    },
    //点击实际金额
    realMoney(date) {},
    //点击导出计划
    exportPlan(data) {
      exportPlan(data.id)
        .then((res) => {
          let fileName = data.name + '.xls';
          this.$utils.downloadBlob(res, fileName);
        })
        .catch();
    },
    //点击修改
    updatePlan(data, status) {
      if (status != 0) {
        return;
      }
      this.getStructureList(data.projectId);
      this.getreceiveTime(data.projectId);
      this.planForm = JSON.parse(JSON.stringify(data));
      this.planDialogTitle = '修改计划';
      this.showPlanDialog = true;
    },
    //点击删除
    deleteItem(item, state) {
      if (state != 0) {
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
    //添加细项
    addDetail() {
      this.showItemsDialog = true;
    },
    //删除细项
    delOneDatail(item) {
      this.$confirm('此操作将永久删除该细项, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        showClose: true
      })
        .then(async () => {
          let { code } = await delOneDatail(item.id);
          if (code == '0000') {
            this.$message({
              type: 'success',
              message: '删除成功!',
              showClose: true,
              duration: 2000
            });
            await this.getPlanList2();
            await this.getPlanDetailList();
          }
        })
        .catch(() => {});
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
          if (this.planForm.itemList.length == 0) {
            this.$message({
              type: 'warning',
              message: '请添加维修项！',
              showClose: true,
              duration: 2000
            });
            return;
          }
          this.planForm.budget = Number(this.planForm.budget);
          this.planForm.expenditure = Number(this.planForm.expenditure);
          let typeName = '维养计划';
          this.planForm.name =
            this.planForm.planTime.split('-')[0] +
            '年' +
            this.planForm.planTime.split('-')[1] +
            '月' +
            typeName;
          if (this.planDialogTitle == '添加计划') {
            let { code } = await addPlan(this.planForm);
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
            }
          } else {
            let { code } = await updPlan(this.planForm);
            if (code == '0000') {
              this.$message({
                type: 'success',
                message: '修改成功',
                showClose: true,
                duration: 2000
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
    //保存添加维修项
    addItems(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let items = { ...this.itemsForm, ...this.diseaseItem };
          items.diseaseInstanceId = this.diseaseItem.id || '';
          if (this.showPlanDialog) {
            this.planForm.itemList.push(items);
            this.sortDataArray(this.planForm.itemList);
          }
          if (this.showDetailDialog) {
            // console.log('添加细项', items);
            items.planId = this.detailObj.id;
            this.addOneDatail(items);
          }
          this.itemsDialogClose();
        } else {
          return false;
        }
      });
    },
    //对数组根据日期进行排序
    sortDataArray(dataArray) {
      return dataArray.sort(function (a, b) {
        return (
          Date.parse(a.proposedTime.replace(/-/g, '/')) -
          Date.parse(b.proposedTime.replace(/-/g, '/'))
        );
      });
    },
    //查看病害图片
    seePhoto(data) {
      if (data.photo.length == 0) {
        this.$message({
          message: '暂无图片可查看！',
          type: 'warning',
          showClose: true,
          duration: 2000
        });
        return;
      }
      let arry = [];
      data.photo.map((item) => {
        arry.push(item.path);
      });
      this.dialogImageUrlList = arry;
      this.dialogImgShow = true;
    },
    //表格多选
    handleSelectionChange(list) {
      if (list.length > 1) {
        this.$refs.diseaseTable.clearSelection();
        this.$refs.diseaseTable.toggleRowSelection(list[list.length - 1]);
      }
      this.diseaseItem = list[list.length - 1];
    },
    //表格勾选全部
    handleSelectionChangeAll(list) {
      if (list.length > 1) {
        this.$refs.diseaseTable.clearSelection();
        this.$refs.diseaseTable.toggleRowSelection(list[list.length - 1]);
      }
      this.diseaseItem = list[list.length - 1];
    },
    //关闭弹框
    dialogClose() {
      this.showPlanDialog = false;
      this.addDetailDialog = false;
      this.planForm = {
        projectId: '',
        budget: '',
        structureId: [],
        planTime: '',
        expenditure: ''
      };
      this.detailObj = {
        structureId: '',
        time: '',
        planTime: '',
        state: ''
      };
      this.startreceiveTime = null;
      this.endreceiveTime = null;
      this.diseaseItem = {}; //选择的病害列表
      this.structureList = [];
      this.planForm.itemList = [];
      // this.repairTableData = [];
      this.itemsForm = {};
    },
    itemsDialogClose() {
      this.showItemsDialog = false;
      this.sameStructureId = false;
      this.itemsForm = {};
      this.diseaseItem = {};
      this.$nextTick(() => {
        this.$refs.itemsForm.clearValidate();
      });
    },
    // 移除功能
    remove(index, data) {
      this.$confirm('确定移除该维修项?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        showClose: true
      })
        .then(() => {
          this.planForm.itemList.splice(index, 1);
          if (this.planDialogTitle == '修改计划') {
            removeDisease(data.diseaseInstanceId).then().catch();
          }
        })
        .catch(() => {});
    },
    // 点击取消，不选择病害
    closeDiseaseDialog() {
      // 让表格勾选为空
      this.diseaseItem = {};
      this.showDiseaseDialog = false;
    },
    //计划详情关闭
    detailDialogClose() {
      this.showDetailDialog = false;
      this.detailObj = {
        structureId: '',
        time: '',
        planTime: '',
        state: ''
      };
      this.startreceiveTime = null;
      this.endreceiveTime = null;
      this.tablePlanData = [];
      this.structureList = [];
      this.changeMoney = false;
    }
  }
};
</script>
<style lang="scss" scoped>
.planM {
  padding: 24px;
  display: flex;
  flex-direction: column;
  .border_p {
    font-size: 16px;
  }
  .border_div2 {
    padding-bottom: 20px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    .my_select1 {
      width: 316px;
      height: 40px;
      margin-right: 20px;
    }
    .my_select2 {
      width: 128px;
      height: 40px;
      margin-right: 20px;
    }
    /deep/ .el-date-editor .el-range-separator {
      width: unset;
    }
  }
  .table_div {
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
    /deep/ .el-table__empty-block {
      border-top: 1px solid #ebeef5;
    }
  }
}

.planM .tableBox {
  .danger {
    width: 56px;
    height: 2.225vh;
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
    height: 2.225vh;
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
    height: 2.225vh;
    font-size: 14px;
    color: #fff;
    background: #27ae60;
    border-radius: 4px;
    margin: 0 auto;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  .tableOpt {
    display: flex;
    align-items: center;
    justify-content: space-evenly;
    color: #419aff;
    font-size: 14px;
    div {
      cursor: pointer;
    }
    .disabled {
      display: none;
      opacity: 0.5;
      cursor: not-allowed;
    }
  }
  th {
    padding: 0 !important;
    font-family: Microsoft JhengHei UI;
    font-style: normal;
    font-weight: bold;
    height: 4.444vh;
    line-height: 4.444vh;
    color: #333333;
    border: 0;
  }
  td {
    padding: 0 !important;
    font-family: Microsoft JhengHei UI;
    font-style: normal;
    height: 5.562vh;
    line-height: 5.562vh;
    color: #333333;
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
.pageNation {
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

//斑马纹样式
/deep/ .first-row {
  background: #ffffff;
}
/deep/ .second-row {
  background: #f2f4f6;
}

//下拉框没数据
.emptySelect {
  padding: 10px 0;
  margin: 0;
  text-align: center;
  color: #999;
  font-size: 0.7vw;
}

// 计划弹框
.planDialog {
  .planBox {
    padding: 0 48px;
    display: flex;
    flex-wrap: wrap;
    .el-form-item {
      width: 50%;
      margin-bottom: 20px;
      display: flex;
      align-items: center;
      justify-content: flex-end;
      /deep/ .el-form-item__label {
        padding-right: 5px;
      }
      /deep/ .el-select,
      /deep/ .el-date-editor,
      /deep/ .el-input {
        width: 316px;
      }
    }
  }
  .repairItems {
    width: 100%;
    padding: 0 28px;
    margin-bottom: 20px;
    display: flex;
    flex-direction: column;
    .repairTable {
      .tableOpt {
        color: #419aff;
        cursor: pointer;
      }
      /deep/ .el-table__body-wrapper {
        max-height: 230px;
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
      /deep/ .el-table__empty-block {
        border-top: 1px solid #ebeef5;
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
    width: 1092px;
  }
  /deep/ .el-dialog__body {
    padding: 0 0 4.484vh;
    display: flex;
    flex-direction: column;
    align-items: center;
  }
}

//添加维修项弹框
.itemsDialog {
  .itemsBox {
    padding-right: 20px;
    display: flex;
    flex-direction: column;
    .el-form-item {
      width: 100%;
      margin-bottom: 20px;
      display: flex;
      align-items: center;
      justify-content: flex-end;
      /deep/ .el-form-item__label {
        padding-right: 5px;
      }
      /deep/ .el-select,
      /deep/ .el-date-editor,
      /deep/ .el-input {
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
      width: 96px;
      height: 40px;
      color: #fff;
      background: #419aff;
      border-radius: 4px;
      margin-left: 20px;
      display: flex;
      align-items: center;
      justify-content: center;
      cursor: pointer;
    }
    .add {
      width: 96px;
      height: 40px;
      color: #419aff;
      border: 1px solid #419aff;
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
    padding: 3.705vh 20px 4.484vh;
    display: flex;
    flex-direction: column;
    align-items: center;
  }
}

//添加病害弹框
.diseaseDialog {
  .top {
    width: 100%;
    margin-bottom: 20px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    .title {
      color: #595959;
    }
    .condition {
      display: flex;
      align-items: center;
      .keyword {
        width: 282px;
        margin-right: 20px;
      }
    }
  }
  .diseaseTable {
    width: 100%;
    margin-bottom: 20px;
    .tableOpt {
      color: #419aff;
      cursor: pointer;
    }
    /deep/ .el-table__header-wrapper .el-checkbox {
      //找到表头那一行，然后把里面的复选框隐藏掉
      display: none;
    }
    /deep/ .el-table__body-wrapper {
      max-height: 460px;
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
    /deep/ .el-table__empty-block {
      border-top: 1px solid #ebeef5;
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
    width: 1092px;
  }
  /deep/ .el-dialog__body {
    padding: 3.705vh 36px 4.484vh;
    display: flex;
    flex-direction: column;
    align-items: center;
  }
}

//维养计划详情弹框
.planDetail {
  .detail_dialog_div {
    width: 100%;
    padding-bottom: 26px;
    display: flex;
    align-items: center;
    // justify-content: flex-end;
    justify-content: space-between;
    .selItem {
      display: flex;
      align-items: center;
      justify-content: flex-end;
    }
    .my_select1 {
      width: 280px;
      margin-right: 20px;
    }
    .my_select2 {
      width: 126px;
      margin-right: 20px;
    }
    /deep/ .el-date-editor .el-range-separator {
      width: unset;
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
    .tableOpt {
      display: flex;
      align-items: center;
      justify-content: space-evenly;
      color: #419aff;
      font-size: 14px;
      div {
        cursor: pointer;
      }
      .disabled {
        display: none;
        opacity: 0.5;
        cursor: not-allowed;
      }
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
    /deep/ .el-table__empty-block {
      border-top: 1px solid #ebeef5;
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
    width: 1092px;
  }
  /deep/ .el-dialog__body {
    padding: 3.705vh 36px 4.484vh;
    display: flex;
    flex-direction: column;
    align-items: center;
  }
}
//查看图片弹框
.dialogImage {
  .diseasePhoto {
    height: 360px;
    /deep/ .el-carousel__container {
      height: 333px;
    }
    /deep/ .el-carousel__item {
      display: flex;
      align-items: center;
      justify-content: center;
    }
    /deep/ .el-image__inner {
      max-height: 360px;
      max-width: 360px;
    }
  }
  .empty {
    height: 360px;
    background: #e5e5e5;
    display: flex;
    align-items: center;
    flex-direction: column;
    justify-content: center;
    .icon {
      font-size: 38px;
    }
    .text {
      color: #333;
      font-size: 24px;
    }
  }
  /deep/ .el-dialog {
    margin: 0;
    width: 768px;
  }
  /deep/ .el-dialog__body {
    padding: 1.2vw !important;
  }
}
.textOF {
  width: 100%;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>
