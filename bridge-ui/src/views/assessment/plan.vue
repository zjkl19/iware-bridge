<template>
  <div class="plan boxShadow animate__animated animate__fadeIn">
    <div class="flex">
      <div>
        <el-button v-if="addOpt" type="primary" @click="addPlan"
          >新增计划</el-button
        >
      </div>
      <div class="flex" style="width: 55%">
        <div>
          <el-select
            id="button1"
            v-model="projectPlanId"
            clearable
            placeholder="全部项目"
            class="border_select"
            @change="getStructureListByModel"
          >
            <el-option
              v-for="item in projectPlanList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </div>
        <div>
          <el-select
            v-model="structurePlanId"
            clearable
            placeholder="全部结构物"
            class="border_select2"
          >
            <el-option
              v-for="item in structurePlanList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </div>
        <div>
          <el-date-picker
            class="border_picker"
            v-model="time"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            value-format="yyyy-MM-dd"
          >
          </el-date-picker>
        </div>
        <div>
          <el-button type="primary" @click="getPlanList">查询 </el-button>
        </div>
      </div>
    </div>

    <div class="planTable">
      <normalTable
        :tableData="tableData"
        :titleList="titleList"
        :opList="opList"
        :opWidth="'200'"
        :pageNum="currentPage"
        :tableName="'检测计划'"
        @tableClick="tableClick"
      ></normalTable>
    </div>

    <!-- 分页器 -->
    <div id="pager">
      <el-pagination
        class="pageNation"
        background
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage"
        :page-size="pageSize"
        layout="total, prev, pager, next"
        :total="dataTotal"
      ></el-pagination>
    </div>

    <!-- 新增遮罩 -->
    <el-dialog
      :visible.sync="addPlanDialog"
      :title="addPlanDialogTitle"
      class="addClass"
      :before-close="dialogClose"
      :modal-append-to-body="false"
      :close-on-click-modal="false"
    >
      <el-form
        class="planForm"
        ref="planForm"
        :model="addPlanData"
        :rules="addPlanRules"
      >
        <el-form-item
          class="add_dialog_form planSpan"
          label="检测类型:"
          prop="type"
        >
          <el-select
            class="add_dialog_select"
            v-model="addPlanData.type"
            clearable
            placeholder="请选择"
          >
            <el-option
              v-for="item in addType"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option> </el-select
        ></el-form-item>

        <el-form-item
          class="add_dialog_form2 planSpan"
          label=" 选择项目:"
          prop="projectInfoId"
        >
          <el-select
            class="add_dialog_select2"
            v-model="addPlanData.projectInfoId"
            clearable
            placeholder="请选择"
            @change="getStructureList2"
          >
            <el-option
              v-for="item in projectList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option> </el-select
        ></el-form-item>

        <el-form-item
          class="add_dialog_form3 planSpan"
          label=" 计划开始日期:"
          prop="startTime"
        >
          <el-date-picker
            class="add_dialog_select3"
            v-model="addPlanData.startTime"
            type="date"
            placeholder="选择日期"
            value-format="yyyy-MM-dd"
            :picker-options="pickerOptions"
            @change="changeTime"
          >
          </el-date-picker>
        </el-form-item>

        <el-form-item
          class="add_dialog_form4 planSpan"
          label=" 计划结束日期:"
          prop="endTime"
        >
          <el-date-picker
            class="add_dialog_picker4"
            v-model="addPlanData.endTime"
            type="date"
            placeholder="选择日期"
            value-format="yyyy-MM-dd"
            :picker-options="pickerOptions"
            @change="changeTime"
          >
          </el-date-picker>
        </el-form-item>

        <el-form-item
          class="add_dialog_form5 planSpan"
          label=" 选择结构物:"
          prop="structureId"
        >
          <el-select
            class="add_dialog_select5"
            v-model="addPlanData.structureId"
            @change="addSelect"
            multiple
            filterable
            collapse-tags
            placeholder="请选择"
          >
            <el-option
              v-for="item in addStructure"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div class="add_table_div">
        <el-table
          :data="addPlanData.structureRelList"
          :row-class-name="tableRowClassName"
        >
          <el-table-column type="index" label="序号" width="80">
          </el-table-column>
          <el-table-column
            min-width="30%"
            prop="structureName"
            label="结构物名称"
            :show-overflow-tooltip="true"
          >
          </el-table-column>
          <el-table-column
            min-width="35%"
            prop="fileName"
            label="检测方案"
            :show-overflow-tooltip="true"
          >
          </el-table-column>
          <el-table-column
            min-width="20%"
            prop="option"
            label="操作"
            :show-overflow-tooltip="true"
          >
            <template slot-scope="scope">
              <el-upload
                class="upload-demo"
                action=""
                :on-change="fileStatusChange"
                :auto-upload="false"
                :show-file-list="false"
                :file-list="scope.row.fileList"
                accept=".doc,.docx,.pdf"
              >
                <span
                  v-if="scope.row.fileName == '-'"
                  class="add_table_button"
                  @click="addUploadButton(scope.row)"
                  >上传方案</span
                >

                <span
                  v-else
                  class="add_table_button"
                  @click="addUploadButton(scope.row)"
                  >重新上传</span
                >
              </el-upload>
            </template>
          </el-table-column>
        </el-table>
        <span class="care"
          ><p style="color: #f56c6c">*</p>
          提示：上传文件名称命名为“结构物名称-检测方案”</span
        >
        <el-upload
          ref="uploadTable"
          action="/bridge/evaluation/plan"
          :http-request="uploadFile"
          :auto-upload="false"
          :show-file-list="false"
          :file-list="fileList"
          list-type="picture-card"
          accept=".doc,.docx,.pdf"
          :headers="fileUploadHeaders"
          style="display: none"
        ></el-upload>
      </div>
      <div class="btn">
        <span class="keep" @click="addPlanOk('planForm')">确定</span>
        <span class="cancel" @click="dialogClose">取消</span>
      </div>
    </el-dialog>

    <!--  详情遮罩 -->
    <el-dialog
      :visible.sync="detailPlanDialog"
      title="检测计划详情"
      class="detail_dialog"
      width="70%"
      id="detailClass"
      :modal-append-to-body="false"
      :close-on-click-modal="false"
    >
      <el-form>
        <el-row>
          <el-col :span="12">
            <el-form-item
              class="detail_dialog_form planSpan"
              label="检测类型:"
              required
            >
              <el-input
                v-model="detailPlanData.typeName"
                class="detail_dialog_input"
                readonly="readonly"
              ></el-input
            ></el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item
              class="detail_dialog_form2 planSpan"
              label=" 选择项目:"
              required
            >
              <el-input
                v-model="detailPlanData.projectName"
                class="detail_dialog_input2"
                readonly="readonly"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item
              class="detail_dialog_form3 planSpan"
              label=" 计划开始日期:"
              required
            >
              <el-input
                v-model="detailPlanData.startTime"
                class="detail_dialog_input3"
                readonly="readonly"
              ></el-input> </el-form-item
          ></el-col>
          <el-col :span="12">
            <el-form-item
              class="detail_dialog_form4 planSpan"
              label=" 计划结束日期:"
              required
            >
              <el-input
                v-model="detailPlanData.endTime"
                class="detail_dialog_picker4"
                readonly="readonly"
              ></el-input> </el-form-item
          ></el-col>
        </el-row>

        <el-form-item class="detail_table_div">
          <el-table :data="detailTable" :row-class-name="tableRowClassName">
            <el-table-column
              prop="id"
              min-width="15%"
              label="序号"
              :show-overflow-tooltip="true"
            >
            </el-table-column>
            <el-table-column
              min-width="30%"
              prop="structureName"
              label="结构物名称"
              :show-overflow-tooltip="true"
            >
            </el-table-column>
            <el-table-column
              min-width="35%"
              prop="fileName"
              label="检测方案"
              :show-overflow-tooltip="true"
            >
            </el-table-column>
            <el-table-column
              min-width="20%"
              prop="option"
              label="操作"
              :show-overflow-tooltip="true"
            >
              <template slot-scope="scope">
                <!-- <el-button
                  style="width: 2vw; height: 4vh; font-size: 1.4vh"
                  type="text"
                  @click="checkPlan(scope.row)"
                  >删除</el-button
                > -->
                <span
                  class="detail_table_button"
                  type="text"
                  @click="downloadPlan(scope.row)"
                  >下载</span
                >
              </template>
            </el-table-column>
          </el-table>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
<script>
import {
  getPlanList,
  addPlan,
  updPlan,
  delPlan,
  getPlanStructure,
  downloadPlan,
  getAppointTime
} from '@/api/assessment/plan';
import {
  getProjectListByOnTime,
  getProjectListByModel,
  getStructureList,
  getStructureListByModel
} from '@/api/common';
import normalTable from '@/components/table/normalTable';
export default {
  components: { normalTable },
  data() {
    const _this = this;
    return {
      selParams: {
        projectId: '',
        structureId: '',
        startTime: '',
        endTime: '',
        pageNum: 1,
        pageSize: 10
      },
      projectPlanList: [],
      projectPlanId: '',
      structurePlanList: [],
      structurePlanId: '',
      //项目搜索框
      projectList: [],
      //项目搜索框的值
      projectId: '',
      //结构物搜索框
      structureList: [],
      //结构物搜索框的值
      structureId: '',
      //时间搜索框的值
      time: '',

      titleList: [
        { id: 2, prop: 'projectName', label: '项目名称', width: '' },
        { id: 3, prop: 'typeName', label: '检测类型', width: '' },
        { id: 4, prop: 'startTime', label: '计划开始时间', width: '' },
        { id: 5, prop: 'endTime', label: '计划结束时间', width: '' }
      ],
      //表格数据
      tableData: [],
      opList: [
        { id: 1, name: '详情', opShow: true, disabled: '' },
        {
          id: 2,
          name: '修改',
          opShow: true,
          disabled: ''
        },
        {
          id: 3,
          name: '删除',
          opShow: true,
          disabled: ''
        }
      ],
      pickerOptions: {
        disabledDate(time) {
          let beginTime = !!_this.startreceiveTime
            ? new Date(_this.startreceiveTime).getTime() - 24 * 3600 * 1000
            : Date.now();
          let endTime = !!_this.endreceiveTime
            ? new Date(_this.endreceiveTime).getTime()
            : Date.now();
          return (
            time.getTime() < Date.now() - 24 * 3600 * 1000 ||
            time.getTime() < beginTime ||
            time.getTime() > endTime
          );
        }
      },
      startreceiveTime: null,
      endreceiveTime: null,
      //当前页
      currentPage: 1,
      //总页数
      dataTotal: 1,
      //每页数量
      pageSize: 10,
      //新增遮罩框
      addPlanDialog: false,
      addPlanDialogTitle: '',
      //新增的数据
      addPlanData: { structureId: [], structureRelList: [] },
      //新增数据规则
      addPlanRules: {
        type: [
          { required: true, message: '请选择检测类型', trigger: 'change' }
        ],
        projectInfoId: [
          { required: true, message: '请选择项目', trigger: 'change' }
        ],
        startTime: [
          {
            required: true,
            message: '请选择开始日期',
            trigger: 'change'
          }
        ],
        endTime: [
          {
            required: true,
            message: '请选择结束日期',
            trigger: 'change'
          }
        ],
        structureId: [
          { required: true, message: '请选择结构', trigger: 'change' }
        ]
      },
      //新增的检测类型
      addType: [
        {
          value: 1,
          label: '定期检测'
        },
        {
          value: 2,
          label: '特殊检测'
        },
        {
          value: 3,
          label: '静载检测'
        }
      ],
      //新增的选择结构物
      addStructure: [],
      //新增的表格
      addTable: [],
      //新增计数
      addCount: 0,
      addUploadData: {},
      fileUploadHeaders: {
        'X-AUTH-TOKEN': this.$store.getters.getToken,
        'X-ROUTER-URL': '/assessment/plan'
      },
      uploadItem: {},
      fileList: [],
      uploadList: [],

      //详情遮罩
      detailPlanDialog: false,
      //详情表格
      detailTable: [],
      //详情计数
      detailCount: 0,
      //详情数据
      detailPlanData: {},
      //分页的结构物id
      selectPageStructure: 0,
      //分页的项目id
      selectPageProject: 0,
      //分页的开始时间
      selectPageStartTime: '',
      //分页的结束时间
      selectPageEndTime: ''
    };
  },
  beforeMount() {
    this.$utils.getAuthPage(this); //获取权限
  },
  mounted() {
    this.getProjectListByOnTime(); //获取项目列表
    this.getProjectListByModel(); //获取有计划的项目列表
    this.getStructureListByModel(); //获取有计划的结构物列表
    this.getPlanList(); //获取计划列表
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
    //获取项目的指派时间
    async getAppointTime(projectId) {
      let id = projectId ? projectId : this.addPlanData.projectId;
      let { code, data } = await getAppointTime(id);
      if (code == '0000') {
        let beginTime = null;
        let endTime = null;
        if (!!data && data.length > 0) {
          beginTime = data[0].beginTime;
          for (let i = 0; i < data.length; i++) {
            if (
              data[i + 1] &&
              data[i].endTime.split('-')[1] ==
                data[i + 1].beginTime.split('-')[1]
            ) {
              endTime = data[i + 1].endTime;
            } else {
              endTime = data[i].endTime;
            }
          }
        }
        this.startreceiveTime = beginTime;
        this.endreceiveTime = endTime;
      }
    },
    //获取结构物列表
    async getStructureList() {
      let params = {
        projectId: this.projectId,
        powerId: this.$store.getters.getActiveIndex
      };
      let { code, data } = await getStructureList(params);
      if (code == '0000') {
        this.structureList = data;
      }
    },
    //获取有计划的结构物列表
    async getStructureListByModel() {
      let params = {
        projectId: this.projectPlanId,
        powerId: this.$store.getters.getActiveIndex
      };
      let { code, data } = await getStructureListByModel(params);
      if (code == '0000') {
        this.structurePlanId = '';
        this.structurePlanList = data;
      }
    },
    async getStructureList2(id) {
      this.addStructure = [];
      this.addPlanData.structureId = [];
      this.addSelect([]);
      this.getAppointTime(id);
      let params = {
        projectId: this.addPlanData.projectInfoId,
        powerId: this.$store.getters.getActiveIndex
      };
      let { code, data } = await getStructureList(params);
      if (code == '0000') {
        this.addStructure = data;
      }
    },
    async getStructureList3(projectId) {
      let params = {
        projectId,
        powerId: this.$store.getters.getActiveIndex
      };
      let { code, data } = await getStructureList(params);
      if (code == '0000') {
        this.addStructure = data;
      }
    },
    //获取计划列表
    async getPlanList() {
      let params = {
        projectId: this.projectPlanId,
        structureId: this.structurePlanId,
        startTime: !!this.time ? this.time[0] : '',
        endTime: !!this.time ? this.time[1] : '',
        pageNum: 1
      };
      let { code, data } = await getPlanList(params);
      if (code == '0000') {
        data.list.map((item) => {
          if (item.status != 1) {
            item.hideName = ['修改', '删除'];
          }
        });
        this.currentPage = 1;
        this.tableData = data.list;
        this.dataTotal = data.total;
      }
    },
    async getPlanList2() {
      let { code, data } = await getPlanList(this.selParams);
      if (code == '0000') {
        data.list.map((item) => {
          if (item.status != 1) {
            item.hideName = ['修改', '删除'];
          }
        });
        this.tableData = data.list;
        this.dataTotal = data.total;
      }
    },
    tableClick(index, data) {
      if (index == 1) this.getPlanStructure(data);
      else if (index == 2) this.update(data);
      else if (index == 3) this.deletes(data);
    },
    //分页
    handleCurrentChange(val) {
      this.currentPage = val;
      this.selParams.pageNum = val;
      this.getPlanList2();
    },
    //详情、修改
    async getPlanStructure(item) {
      // this.detailPlanDialog = true;
      let { code, data } = await getPlanStructure(item.id);
      if (code == '0000') {
        this.detailPlanData = JSON.parse(JSON.stringify(item));
        this.detailTable = data;
        this.detailPlanDialog = true;
      }
    },
    //详情下载
    downloadPlan(item) {
      if (item.fileName == '-') {
        this.$message({
          type: 'warning',
          message: '请先上传方案！',
          duration: 2000,
          showClose: true
        });
        return;
      }
      downloadPlan(item.id)
        .then((res) => {
          if (res.code) {
            this.$message({
              type: 'error',
              message: res.msg,
              duration: 2000,
              showClose: true
            });
          } else {
            this.$utils.downloadBlob(res, item.fileName);
          }
        })
        .catch();
    },
    //删除
    deletes(row) {
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        showClose: true
      })
        .then(async () => {
          let { code } = await delPlan(row.id);
          if (code == '0000') {
            this.$message({
              message: '删除成功！',
              showClose: true,
              type: 'success',
              duration: 2000
            });
            await this.getPlanList2();
          }
        })
        .catch(() => {});
    },
    //新增计划
    addPlan() {
      this.addTable = [];
      this.addPlanData = { structureId: [], structureRelList: [] };
      this.addPlanDialog = true;
      this.addPlanDialogTitle = '新增计划';
    },
    //提交新增计划
    addPlanOk(formName) {
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          this.fileData = new FormData();
          this.$refs.uploadTable.submit();
          // this.uploadList.map((item) => {
          //   this.fileData.append('files', item);
          // });
          this.fileData.append('params', JSON.stringify(this.addPlanData));
          if (this.addPlanDialogTitle == '新增计划') {
            let { code } = await addPlan(this.fileData);
            if (code == '0000') {
              this.$message({
                message: '添加成功！',
                type: 'success',
                showClose: true,
                duration: 2000
              });
              await this.dialogClose();
              await this.getProjectListByModel();
              await this.getPlanList2();
            }
          } else {
            let { code } = await updPlan(this.fileData, this.addPlanData.id);
            if (code == '0000') {
              this.$message({
                message: '修改成功！',
                type: 'success',
                showClose: true,
                duration: 2000
              });
              await this.dialogClose();
              await this.getPlanList2();
            }
          }
        } else {
          return false;
        }
      });
    },
    //新增点击上传
    addUploadButton(item) {
      this.uploadItem = item;
    },
    //选择日期
    changeTime() {
      if (this.addPlanData.startTime != '' && this.addPlanData.endTime != '') {
        if (this.addPlanData.startTime > this.addPlanData.endTime) {
          let time = this.addPlanData.endTime;
          this.addPlanData.endTime = this.addPlanData.startTime;
          this.addPlanData.startTime = time;
        }
      }
    },
    //文件选中(包含校验)
    fileStatusChange(file, fileList) {
      let fileArry = file.name.split('-');
      let fileStrutureName =
        fileArry.length > 2
          ? fileArry.slice(0, fileArry.length - 1).join('-')
          : fileArry[0];
      let structureName = this.uploadItem.structureName;
      if (structureName != fileStrutureName) {
        this.$message({
          message: '上传名称格式不正确！',
          type: 'warning',
          showClose: true,
          duration: 2000
        });
        fileList.splice(1, 1);
        return;
      }
      if (fileList.length > 1) {
        fileList.splice(0, 1);
      }
      let arry = [];
      this.addPlanData.structureRelList.map((item, i) => {
        if (item.structureInfoId == this.uploadItem.structureInfoId) {
          item.fileList[0] = file;
          item.fileName = file.name;
          item.path = file.uid + '.' + file.name.split('.')[1];
          this.$set(this.addPlanData.structureRelList, i, item);
        }
        if (item.fileList.length > 0) {
          arry.push(item.fileList[0]);
        }
      });
      console.log(arry);
      this.fileList = arry;
    },
    //上传文件
    uploadFile(file) {
      // this.uploadList.push(file.file);
      this.fileData.append('files', file.file);
    },
    //新增的表格内容添加
    addSelect(valList) {
      let arry = [];
      this.addStructure.map((item) => {
        valList.map((id) => {
          if (item.id == id) {
            let obj = {
              structureInfoId: id,
              structureName: item.name,
              fileName: '-',
              fileList: []
            };
            arry.push(obj);
          }
        });
      });

      if (this.addPlanData.structureRelList.length > 0) {
        let addTable = this.addPlanData.structureRelList.concat();
        let ids = [];
        addTable.map((item, index) => {
          if (!valList.includes(item.structureInfoId)) {
            this.addPlanData.structureRelList.splice(index, 1);
          } else {
            ids.push(item.structureInfoId);
          }
        });
        let addTable2 = this.addPlanData.structureRelList.concat();
        ids = [...new Set(ids)];
        arry.map((item) => {
          if (!ids.includes(item.structureInfoId)) {
            addTable2.push(item);
          }
        });
        this.addPlanData.structureRelList = addTable2;
      } else {
        this.addPlanData.structureRelList = arry;
      }
    },
    //修改
    async update(row) {
      this.getAppointTime(row.projectInfoId);
      let { code, data } = await getPlanStructure(row.id);
      if (code == '0000') {
        this.getStructureList3(row.projectInfoId);
        let arry = [];
        let obj = {};
        data.map((item, i) => {
          arry.push(item.structureInfoId);
          obj = { ...item };
          data[i].fileList = [obj];
        });
        this.addPlanData = JSON.parse(JSON.stringify(row));
        this.addPlanData.structureRelList = data;
        this.addPlanData.structureId = arry;
        this.addPlanDialogTitle = '修改计划';
        this.addPlanDialog = true;
      }
    },
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex % 2 == 1) {
        return 'first-row';
      } else {
        return 'second-row';
      }
    },
    //关闭弹框
    dialogClose() {
      this.addPlanDialog = false;
      this.addPlanData = { structureId: [], structureRelList: [] };
      this.fileList = [];
      this.startreceiveTime = null;
      this.endreceiveTime = null;
      this.$nextTick(() => {
        this.$refs.planForm.clearValidate();
      });
    }
  }
};
</script>

<style lang="scss" scoped>
/* 分页样式 */
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

//表格样式
.planTable {
  text-align: center;
  margin-top: 3.5vh;
  margin-top: 1%;
  width: 100%;
  .el-table .cell {
    height: unset !important;
    line-height: unset !important;
  }
}

//新增遮罩
.addClass {
  .planForm {
    padding: 0 24px 0 0;
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
      /deep/ .el-tag {
        max-width: 80%;
        overflow: hidden;
        text-overflow: ellipsis;
      }
    }
  }
  .add_table_div {
    width: 100%;
    padding: 0 30px;
    margin-bottom: 20px;
    .care {
      display: flex;
      padding-top: 10px;
    }
    .add_table_button {
      color: #419aff;
      cursor: pointer;
    }
    /deep/ .el-table__body-wrapper {
      max-height: 210px;
      overflow: auto;
      &:hover {
        &::-webkit-scrollbar {
          width: 6px;
        }
        &::-webkit-scrollbar-thumb {
          background: #c4c4c4;
          border-radius: 8px;
        }
      }
    }
    /deep/ .el-table__empty-block {
      border-top: 1px solid #ebeef5;
    }
    /deep/ .el-table th {
      font-size: 14px;
      font-weight: bold;
      color: #333;
      padding: 0.464vh 0;
      border: 0;
    }
    /deep/ .el-table td {
      font-size: 14px;
      color: #333;
      padding: 0.927vh 0;
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
    padding: 3.705vh 20px 4.484vh;
    display: flex;
    flex-direction: column;
    align-items: center;
  }
}

.plan #updateClass .el-dialog__title {
  line-height: 4vh;
  margin-left: 0.5vw;
}
.plan #updateClass .el-dialog__header {
  height: 4vh;
}
.plan #updateClass .el-form-item__label {
  line-height: 4vh;
}
.plan #updateClass .el-form-item {
  margin-bottom: 2.4vh;
}
.plan #updateClass .el-tag.el-tag--info {
  width: 7vw;
  height: 2.5vh;
  line-height: 2.5vh;
  font-size: 1vh;
}
.plan #updateClass .el-dialog__headerbtn .el-dialog__close {
  width: 0.8vw;
  height: 1.4vh;
  font-size: 1.4vh;
  line-height: 1.4vh;
}
.plan #updateClass .el-tag--small {
  margin-left: 0.5vw;
}

.plan #detailClass .el-dialog__title {
  line-height: 4vh;
  margin-left: 0.5vw;
}
.plan #detailClass .el-dialog__header {
  height: 4vh;
}
.plan #detailClass .el-form-item__label {
  line-height: 4vh;
}
.plan #detailClass .el-form-item {
  margin-bottom: 2.4vh;
}
.plan #detailClass .el-tag.el-tag--info {
  width: 7vw;
  height: 2.5vh;
  line-height: 2.5vh;
  font-size: 1vh;
}
.plan #detailClass .el-dialog__headerbtn .el-dialog__close {
  width: 0.8vw;
  height: 1.4vh;
  font-size: 1.4vh;
  line-height: 1.4vh;
}
.plan #detailClass .el-tag--small {
  margin-left: 0.5vw;
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
.plan .el-button--mini,
.plan .el-button--small {
  font-size: 1.2vh;
  height: 3.2vh;
  width: 3vw;
}
/deep/.el-date-editor .el-range-separator {
  width: unset;
}
.el-message-box__content {
  padding: 1vh 1.5vh;
}
.plan {
  padding: 24px;
}
.border_button_span {
  font-size: 1.4vh;
  font-family: Microsoft YaHei;
  text-align: center;
}
.border_select {
  width: 15vw !important;
}
.border_select2 {
  width: 15vw !important;
}
.border_picker {
  width: 15vw;
  font-family: PingFang SC;
}

.add_table_div,
.update_table_div,
.detail_table_div {
  width: 100%;
  padding: 0 30px;
  .detail_table_button {
    color: #419aff;
    cursor: pointer;
  }
  /deep/ .el-table__empty-block {
    border-top: 1px solid #ebeef5;
  }
  /deep/ .el-table th {
    font-size: 14px;
    font-weight: bold;
    color: #333;
    padding: 0.464vh 0;
    border: 0;
  }
  /deep/ .el-table td {
    font-size: 14px;
    color: #333;
    padding: 0.927vh 0;
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

.add_dialog_form6 {
  margin-left: 35%;
  .add_dialog_button6 {
    width: 4vw;
    height: 4vh;
    font-size: 1.4vh;
  }
}

.update_dialog_form {
  width: 100%;
  margin-left: 2.5vw;
  margin-top: 2.8vh;
  .update_dialog_select {
    width: 62%;
  }
}

.update_dialog_form2 {
  width: 90%;
  margin-left: 3vw;
  margin-top: 2.8vh;
  .update_dialog_select2 {
    width: 69%;
  }
}

.update_dialog_form3 {
  width: 100%;
  margin-left: 0.9vw;
  .update_dialog_select3 {
    width: 62%;
  }
}

.update_dialog_form4 {
  width: 90%;
  margin-left: 1.5vw;
  .update_dialog_picker4 {
    width: 69.5%;
  }
}

.update_dialog_form5 {
  margin-left: 1.5vw;
  .update_dialog_select5 {
    width: 85%;
  }
}

.update_dialog_form6 {
  margin-left: 40%;
  .update_dialog_button6 {
    width: 4vw;
    height: 4vh;
    font-size: 1.4vh;
  }
}
.detail_dialog {
  height: 80vh;
  .detail_dialog_form {
    width: 35.5vw;
    margin-left: 2.5vw;
    margin-top: 2.8vh;
    .detail_dialog_input {
      width: 22vw;
    }
  }

  .detail_dialog_form2 {
    width: 32vw;
    margin-left: 3vw;
    margin-top: 2.8vh;
    .detail_dialog_input2 {
      width: 24.5vw;
    }
  }

  .detail_dialog_form3 {
    width: 35.5vw;
    margin-left: 0.9vw;
    .detail_dialog_input3 {
      width: 22vw;
    }
  }

  .detail_dialog_form4 {
    width: 32vw;
    margin-left: 1.5vw;
    .detail_dialog_picker4 {
      width: 24.5vw;
    }
  }
}
//斑马纹样式
/deep/ .first-row {
  background: #ffffff;
}
/deep/ .second-row {
  background: #f2f4f6;
}
.flex {
  display: flex;
  justify-content: space-between;
}
</style>
