<template>
  <div
    class="project-board animate__animated animate__fadeIn"
    style="padding: 20px"
  >
    <!-- dialog部分 -->
    <!-- 项目【新增】【编辑】 -->
    <el-dialog
      class="proDialog"
      :visible.sync="editAddDialogVisible"
      :title="addOrEditProjectTitle"
      width="40%"
      @closed="projectDialogClose"
      append-to-body
      modal-append-to-body
      :close-on-click-modal="false"
    >
      <div slot="title" style="font-size: 16px; line-height: 18px">
        {{ addOrEditProjectTitle }}
      </div>
      <div class="projectEditForm-body" v-if="editAddDialogVisible">
        <el-form
          :model="projectEditForm"
          :rules="projectRules"
          ref="projectForm"
        >
          <el-form-item
            label="项目名称："
            label-position="right"
            label-width="108px"
            prop="name"
          >
            <el-input
              placeholder="请输入项目名称"
              maxlength="18"
              clearable
              show-word-limit
              class="projectEditForm-input"
              v-model.trim="projectEditForm.name"
              :disabled="isMainUser || isCommonUser"
            ></el-input>
          </el-form-item>

          <el-form-item
            label="项目联系人："
            label-position="right"
            label-width="108px"
            prop="chargePerson"
          >
            <el-input
              placeholder="请输入项目联系人"
              maxlength="18"
              clearable
              show-word-limit
              class="projectEditForm-input"
              v-model.trim="projectEditForm.chargePerson"
              :disabled="isMainUser || isCommonUser"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="所属业主："
            label-position="right"
            label-width="108px"
            v-if="isAdmin"
            prop="unitId"
          >
            <el-select
              v-model="projectEditForm.unitId"
              clearable
              placeholder="所属业主"
              v-bind:disabled="isMainUser || isCommonUser"
              @change="handleProprietorChange"
            >
              <el-option
                v-for="item in inProprietors"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item
            label="项目简介："
            label-position="right"
            label-width="108px"
            id="projectArea"
          >
            <el-input
              placeholder="请输入项目简介"
              maxlength="125"
              type="textarea"
              clearable
              show-word-limit
              :autosize="{ minRows: 2 }"
              class="projectEditForm-input"
              v-model.trim="projectEditForm.synopsis"
              v-bind:disabled="isMainUser || isCommonUser"
            ></el-input>
          </el-form-item>

          <el-form-item
            label="添加结构物："
            label-position="right"
            label-width="108px"
            prop="structureIds"
          >
            <el-select
              placeholder="输入结构物(可选)"
              v-model="projectEditForm.structureIds"
              class="projectEditForm-select"
              multiple
              @change="changePhotoList"
              :disabled="isMainUser || isCommonUser"
            >
              <el-option
                v-for="item in ownerStructureList"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>

          <el-form-item
            class="photoRow"
            label="项目照片："
            label-position="right"
            label-width="108px"
            v-show="showImgObj"
          >
            <div v-for="item in imgList" :key="item.id" class="photoItem">
              <el-image
                :src="item.path"
                :fit="fit"
                @click="showImage(item.path)"
              ></el-image>
              <span>{{ item.bridgeName }}</span>
            </div>
          </el-form-item>
        </el-form>
      </div>
      <div class="edit-project-dialog-foot">
        <el-button
          type="primary"
          @click="editOrSaveProject('projectForm')"
          :disabled="isMainUser || isCommonUser"
          >确定</el-button
        >
        <el-button @click="editAddDialogVisible = false">取消</el-button>
      </div>
    </el-dialog>
    <el-dialog
      :visible.sync="imgDialogVisible"
      append-to-body
      modal-append-to-body
      :close-on-click-modal="false"
    >
      <el-row>
        <el-col :span="24">
          <div>
            <el-image
              style="width: 100%; height: 600px"
              :src="showImgUrl"
              :fit="fit"
            ></el-image>
          </div>
        </el-col>
      </el-row>
    </el-dialog>

    <!-- 项目查看所属结构物 -->
    <el-dialog
      class="infoDialog"
      :visible.sync="showStructureListVisible"
      :title="showStructureListTitle"
      @closed="closeLookStructureDialog"
      append-to-body
      modal-append-to-body
      :close-on-click-modal="false"
    >
      <div class="infoTable">
        <el-table
          :data="projectStructureList"
          :row-class-name="tableRowClassName"
        >
          <el-table-column
            type="index"
            width="80"
            label="序号"
            align="center"
          ></el-table-column>
          <el-table-column prop="name" label="结构物名称" align="center">
          </el-table-column>
          <el-table-column
            prop="structureType"
            label="结构物类型"
            align="center"
          >
            <template slot-scope="scope">
              <div class="tableItem">
                <span class="dot"></span
                ><span>{{
                  scope.row.structureType == 1 ? '桥梁' : '隧道'
                }}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            prop="chargeName"
            label="项目联系人"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="maintainDepartment"
            label="养护单位"
            align="center"
          ></el-table-column>
          <el-table-column label="操作" align="center">
            <template slot-scope="scope">
              <div class="btn" @click="goToStructureDetailPaage(scope.row)">
                查看详情
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-dialog>

    <!-- 一.项目管理【头部】 -->
    <div class="project-manage-header">
      <div class="add-project-btn">
        <el-button
          v-if="addOpt && roleID > 1"
          type="primary"
          @click="addNewProject"
          >新增项目</el-button
        >
      </div>
      <div class="project-search-bar">
        <div class="project-keyword-search">
          <el-input
            clearable
            placeholder="关键词"
            v-model="projectSearchBar"
          ></el-input>
        </div>
        <el-button type="primary" @click="getList">查询</el-button>
      </div>
    </div>

    <!-- 二.项目管理【主体】 -->
    <div class="project-manage-body">
      <el-row>
        <el-col :span="24">
          <div class="grid-content bg-purple-dark song-table-u">
            <normalTable
              :titleList="titleList"
              :tableData="tableData"
              :opList="opList"
              :pageNum="currentPage"
              @tableClick="tableClick"
            ></normalTable>
          </div>
        </el-col>
      </el-row>
    </div>
    <!-- 三.项目管理【尾部】 -->
    <div class="project-manage-foot">
      <div class="project-manage-foot_pagination">
        <el-pagination
          class="pageNation"
          background
          :page-size="10"
          :total="totalProjects"
          @current-change="listProject"
          layout="total, prev, pager, next"
          :current-page.sync="currentPage"
        ></el-pagination>
      </div>
    </div>

    <!-- 项目指派table -->
    <el-dialog
      title="指派记录"
      :visible.sync="appointSyn"
      width="60%"
      class="appointDialog"
      append-to-body
      modal-append-to-body
      :close-on-click-modal="false"
    >
      <el-button
        v-if="appointOpt"
        type="primary"
        @click="openOperationClick()"
        style="float: right"
        :disabled="roleID == 3"
        >项目指派</el-button
      >
      <el-table :data="tableDataOperation" style="margin-top: 24px">
        <el-table-column
          prop="projectName"
          show-overflow-tooltip
          label="项目"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="appointUnitName"
          show-overflow-tooltip
          label="指派人"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="receiveUnitName"
          show-overflow-tooltip
          label="承接单位"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="businessNames"
          show-overflow-tooltip
          label="指派业务"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="startTime"
          label="开始时间"
          align="center"
          show-overflow-tooltip
        ></el-table-column>
        <el-table-column
          prop="endTime"
          label="结束时间"
          align="center"
          show-overflow-tooltip
        ></el-table-column>

        <el-table-column
          v-if="appointOpt"
          label="操作"
          align="center"
          width="70"
        >
          <template slot-scope="scope">
            <el-button
              @click="delAppoint(scope.row)"
              type="text"
              size="small"
              :disabled="canDeleteAppoint(scope.row)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页器 -->
      <el-row>
        <el-col :span="24">
          <el-pagination
            class="pageNation"
            @current-change="handleInnerTableChange"
            :current-page.sync="page"
            :page-size="10"
            layout="total, prev, pager, next"
            :total="dataTotal"
          ></el-pagination>
        </el-col>
      </el-row>
    </el-dialog>

    <el-dialog
      class="projectAppointDialog"
      title="项目指派"
      :visible.sync="openOperation"
      id="dis"
      @closed="appointClose"
      append-to-body
      modal-append-to-body
      :close-on-click-modal="false"
    >
      <el-form
        class="appointForm"
        v-if="openOperation"
        ref="appointForm"
        :model="userProjectRecord"
        :rules="recordRules"
      >
        <el-form-item label="指派给:" prop="receiveUnitId">
          <el-select
            v-model="userProjectRecord.receiveUnitId"
            filterable
            placeholder="请选择被指派人"
            class="y-el-select"
          >
            <el-option
              v-for="item in userList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item
          v-if="roleID != 1"
          label="项目指派的开始时间:"
          prop="startTime"
        >
          <el-date-picker
            class="y-el-select"
            v-model="userProjectRecord.startTime"
            value-format="yyyy-MM-dd"
            type="date"
            placeholder="选择日期 "
            :picker-options="pickerOptions"
          ></el-date-picker>
        </el-form-item>

        <el-form-item
          v-if="roleID != 1"
          label="项目指派的结束时间:"
          prop="endTime"
        >
          <el-date-picker
            class="y-el-select"
            v-model="userProjectRecord.endTime"
            value-format="yyyy-MM-dd"
            type="date"
            placeholder="选择日期 "
            :picker-options="pickerOptions"
          ></el-date-picker>
        </el-form-item>

        <el-form-item label="指派业务:" prop="business">
          <el-checkbox-group
            class="y-el-select"
            v-model="userProjectRecord.business"
          >
            <el-checkbox
              v-for="item in business"
              :label="item.id"
              :key="item.id"
              >{{ item.name }}</el-checkbox
            >
          </el-checkbox-group>
        </el-form-item>
      </el-form>
      <div class="btn">
        <el-button type="primary" @click="appointSave('appointForm')"
          >保存</el-button
        >
        <el-button @click="openOperation = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listStructureByUnit,
  listProjectByPage,
  addProject,
  deleteProject,
  delAppoint,
  updateProject,
  addAppoint,
  getAppointList,
  getBusiness,
  getAppointItem
} from '@/api/infomanage/projectInfo';
import { getUnitByRole } from '@/api/infomanage/unitManage';
import { getBridgeListByProjectId } from '@/api/infomanage/bridgeManage';
import normalTable from '@/components/table/normalTable';
export default {
  name: 'projectManage',
  components: { normalTable },
  data() {
    return {
      selParams: {
        pageNum: 1,
        pageSize: 10,
        keyword: ''
      },
      fit: 'contain',
      imgDialogVisible: false,
      showImgUrl: '',
      chargePerson: '',

      name: '',
      //记录总数
      dataTotal: 0,
      //当前页
      page: 1,

      //一页的条数
      size: '10',
      appointProjectItem: '',
      projectId: '',
      userProjectRecord: {
        projectId: '',
        business: [],
        childId: '',
        receiveUnitId: '',
        mainUndertakingUnitUserid: '',
        //项目指派的开始时间
        startTime: '',
        //项目指派的结束时间
        endTime: '',
        username: '',
        //项目指派时间
        projectTime: ''
      },
      tableDataOperation: [],
      openOperation: false,
      appointSyn: false,
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() < Date.now() - 8.64e7;
        }
      }, // 只能选取当前之后时间

      titleList: [
        { id: 2, prop: 'name', label: '项目名称', width: '' },
        { id: 3, prop: 'unitName', label: '业主单位', width: '' },
        { id: 4, prop: 'structureNames', label: '结构物', width: '' },
        { id: 5, prop: 'createTime', label: '创建时间', width: '' }
      ],
      tableData: [],
      opList: [
        { id: 1, name: '修改', opShow: false, disabled: '', type: 'updateOpt' },
        { id: 2, name: '删除', opShow: false, disabled: '', type: 'deleteOpt' },
        {
          id: 3,
          name: '查看结构物信息',
          opShow: false,
          disabled: '',
          type: 'checkOpt'
        },
        { id: 4, name: '指派', opShow: false, disabled: '', type: 'appointOpt' }
      ],

      //展示承接单位主用户
      showMainUnit: false,
      userList: [], //接收单位列表
      //添加项目校验
      projectRules: {
        name: [{ required: true, message: '请输入项目名', trigger: 'blur' }],
        chargePerson: [
          { required: true, message: '请输入项目联系人', trigger: 'blur' }
        ],
        unitId: [{ required: true, message: '请选择业主', trigger: 'change' }],
        structureIds: [
          { required: true, message: '请选择结构物', trigger: 'blur' }
        ]
      },
      //添加指派记录校验
      recordRules: {
        receiveUnitId: [
          { required: true, message: '请选择被指派人', trigger: 'change' }
        ],
        startTime: [
          {
            required: true,
            message: '请选择开始时间',
            trigger: 'change'
          }
        ],
        endTime: [
          {
            required: true,
            message: '请选择结束时间',
            trigger: 'change'
          }
        ],
        business: [
          {
            required: true,
            message: '请至少选择一项业务',
            trigger: 'change'
          }
        ]
      },
      projectTimes: [],
      roleName: '',
      roleID: this.$store.getters.getRoleInfo.id,

      projectStructureList: [],
      showStructureListVisible: false,
      showStructureListTitle: '结构物列表查看',
      totalProjects: 0,

      projectEditForm: {
        name: '',
        synopsis: '',
        addNewStructure: [],
        inProprietors: [],
        unitId: '',
        createUserId: '',
        username: '',
        userName: '',
        structureIds: [],

        mainUndertakingUnitUserids: [],
        mainUndertakingUnitUserid: '',
        projectStartTime: '',
        projectEndTime: '',

        mainUndertakingUnitUseridsPT: [],
        structureNames: '',
        chargePerson: ''
      },
      imgList: [],

      //所属业主下拉框
      inProprietors: [],
      ownerStructureList: [],
      projectSearchBar: '',
      addOrEditProjectTitle: '',
      editAddDialogVisible: false,
      currentPage: 1,
      showImgObj: false,
      isAdmin: false,
      isOwner: false,
      isMainUser: false,
      isCommonUser: false,
      needRefresh: true //页面是否需要刷新
    };
  },
  computed: {
    //指派是否可删除
    canDeleteAppoint() {
      return function (item) {
        return new Date(item.endTime) < new Date();
      };
    }
  },
  beforeRouteLeave(to, from, next) {
    if (to.name === '桥梁信息详情' || to.name === '隧道信信息详情') {
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
  beforeMount() {
    this.appointOpt = false;
    this.$utils.getAuthPage(this); //获取权限
    if (this.$store.getters.getRoleInfo.id == 3) {
      this.opList[3].opShow = true;
    }
  },
  mounted() {
    this.init();
    this.getList();
    if (this.$store.getters.getRoleInfo.id < 2) {
      this.opList[0].opShow = false;
      this.opList[1].opShow = false;
    }
    this.$nextTick(async () => {
      if (this.isAdmin) {
        let { code, data } = await getUnitByRole(2);
        if (code == '0000') {
          this.inProprietors = data;
        }
      }
    });
  },

  methods: {
    init() {
      if (this.roleID == 3) this.isAdmin = true;
      if (this.roleID == 2) this.isOwner = true;
      if (this.roleID == 1) this.isMainUser = true;
      if (this.roleID == 0) this.isCommonUser = true;
    },
    // 查询获取项目
    async getList() {
      let params = {
        keyword: this.projectSearchBar,
        pageNum: 1,
        pageSize: 10
      };
      let { code, data } = await listProjectByPage(params);
      if (code == '0000') {
        this.currentPage = 1;
        this.tableData = data.list;
        this.totalProjects = data.total;
        this.selParams = params;
      }
    },
    async getList2() {
      let { code, data } = await listProjectByPage(this.selParams);
      if (code == '0000') {
        this.tableData = data.list;
        this.totalProjects = data.total;
      }
    },
    // 获取可用结构物
    async getStructureByUser(add) {
      // let model = {
      //   id: add ? '' : this.projectEditForm.id,
      //   unitId:
      //     this.projectEditForm.unitId || this.$store.getters.getUserInfo.id
      // };
      let unitId = add
        ? this.$store.getters.getUserInfo.unitId
        : this.projectEditForm.unitId;
      let { code, data } = await listStructureByUnit(unitId);
      if (code == '0000') {
        this.ownerStructureList = data;
        // if (this.editDialogFlag) {
        //   let arry = [];
        //   data.map((item) => {
        //     item.photoList.map((photo) => {
        //       photo.path = this.$basePath + photo.path;
        //       photo.bridgeName = item.name;
        //       photo.structureId = item.id;
        //     });
        //     arry = [...arry, ...item.photoList];
        //   });
        //   this.imgList = arry;
        // }
      }
    },
    //获取指派列表
    async getAppointList() {
      this.projectId = this.appointProjectItem.id;
      let pageInfo = {
        pageNum: this.page,
        pageSize: 10
      };
      //请求后台数据
      let { code, data } = await getAppointList(this.projectId, pageInfo);
      if (code == '0000') {
        this.tableDataOperation = data.list;
        this.dataTotal = data.total;
      }
    },
    //删除指派
    delAppoint(item) {
      this.$confirm('是否确认删除该指派?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async () => {
          let { code } = await delAppoint(item.id);
          if (code == '0000') {
            this.$message({
              type: 'success',
              message: '删除成功',
              showClose: true
            });
            await this.getAppointList();
          }
        })
        .catch(() => {});
    },
    //选择业主下拉框change
    handleProprietorChange() {
      if (this.projectEditForm.unitId != '') {
        this.getStructureByUser();
      } else {
        this.ownerStructureList = [];
        this.projectEditForm.structureIds = [];
      }
    },
    //展示图片
    showImage(img) {
      this.showImgUrl = img;
      this.imgDialogVisible = true;
    },

    //搜索点击事件
    selectBridgeIns() {
      this.page = 1;
      this.handleCurrentChange(this.page);
    },

    //表格点击事件
    tableClick(index, data) {
      if (index == 1) {
        this.editProject(data);
      } else if (index == 2) {
        this.deletProject(data);
      } else if (index == 3) {
        this.showStructureList(data);
      } else {
        this.appointProject(data);
      }
    },

    //修改项目，打开dialog
    editProject(project) {
      this.imgList = [];
      this.projectEditForm.id = project.id;
      this.projectEditForm.name = project.name;
      this.projectEditForm.chargePerson = project.chargePerson;
      this.projectEditForm.unitId = project.unitId;
      this.projectEditForm.synopsis = project.synopsis;
      this.projectEditForm.structureIds = project.structureIds;
      this.preSelectStructureIds = project.structureIds;

      this.addOrEditProjectTitle = '修改项目';
      this.editAddDialogVisible = true;
      this.editDialogFlag = true;
      this.showImgObj = false;
      this.getStructureByUser();
    },
    //添加项目，打开dialog
    addNewProject() {
      this.preSelectStructureIds = [];
      this.imgList = [];
      if (!this.isAdmin) {
        this.getStructureByUser(true);
      }

      this.addOrEditProjectTitle = '新增项目';

      this.editAddDialogVisible = true;
      this.showImgObj = false;
    },
    //点击指派按钮
    appointProject(item) {
      if (item.structureNames == null || item.structureNames == '') {
        this.$message({
          type: 'warning',
          message: '当前项目下无结构物，不可指派！',
          showClose: true
        });
      }
      this.appointSyn = true;
      this.appointProjectItem = item;
      this.userProjectRecord.projectId = item.id;
      this.getAppointList();
    },
    //查看结构物列表,打开dialog
    showStructureList(data) {
      this.showStructureListTitle = data.name;
      this.showStructureListVisible = true;

      // 根据结构物名称获取结构物
      getBridgeListByProjectId(data.id).then((res) => {
        this.projectStructureList = res.data;
      });
    },
    //删除点击事件
    deletProject(data) {
      this.$confirm('是否确认删除项目' + data.name + '?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        let { code } = await deleteProject(data.id);
        if (code == '0000') {
          this.$message({
            type: 'success',
            message: '删除成功',
            showClose: true
          });
          await this.getList2();
        }
      });
    },

    //分页器事件
    async handleInnerTableChange(val) {
      this.page = val;
      //重置当前页
    },

    // 添加或修改项目，点击确定后
    editOrSaveProject(formName) {
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          for (let index = 0; index < this.inProprietors.length; index++) {
            const element = this.inProprietors[index];
            if (element.id === this.projectEditForm.unitId) {
              this.projectEditForm.username = element.username;
              break;
            }
          }
          if (this.addOrEditProjectTitle == '新增项目') {
            let { code } = await addProject(this.projectEditForm);
            if (code == '0000') {
              this.editAddDialogVisible = false;
              this.$message({
                type: 'success',
                message: '添加成功',
                showClose: true
              });
              await this.getList2();
            }
          } else {
            let { code, msg } = await updateProject(this.projectEditForm);
            if (code == '0000') {
              if (msg != 'repeatName') {
                this.editAddDialogVisible = false;
                this.$message({
                  type: 'success',
                  message: '更新成功',
                  showClose: true
                });
                await this.getList2();
              } else {
                this.$message({
                  type: 'warning',
                  message: '存在相同项目名',
                  showClose: true
                });
              }
            }
          }
        }
      });
    },

    //保存项目指派信息
    appointSave(formName) {
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          if (
            this.userProjectRecord.endTime < this.userProjectRecord.startTime
          ) {
            this.$message({
              message: '项目结束时间应大于项目开始时间',
              type: 'warning',
              showClose: true
            });
            return false;
          }
          this.userProjectRecord.startTime =
            this.userProjectRecord.startTime + ' 00:00:00';
          this.userProjectRecord.endTime =
            this.userProjectRecord.endTime + ' 23:59:59';
          //指派
          let { code } = await addAppoint(this.userProjectRecord);
          if (code == '0000') {
            this.openOperation = false;
            //刷新页面
            await this.getAppointList();
            this.$message({
              type: 'success',
              message: '项目指派成功!',
              showClose: true
            });
          }
        }
      });
    },

    //点击 “项目指派”  按钮后弹出框的字段值初始化
    async openOperationClick() {
      if (this.roleID == 2) {
        //业主获取承接单位住用户
        await this.getUnitByRole(1);
        await this.getBusiness(); //获取业务
        this.openOperation = true;
      } else if (this.roleID == 1) {
        // this.recordLimit.startTime = null;
        // this.recordLimit.endTime = null;
        await this.getUnitByRole(0);
        await this.getAppointItem(this.userProjectRecord.projectId);
      }
    },

    //根据单位角色获取单位
    async getUnitByRole(id) {
      if (id == 0) {
        let unitId = this.$store.getters.getUserInfo.unitId;
        let { code, data } = await getUnitByRole(id, unitId);
        if (code == '0000') {
          this.userList = data;
        }
      } else {
        let { code, data } = await getUnitByRole(id);
        if (code == '0000') {
          this.userList = data;
        }
      }
    },

    //承接单位主用户获取指派信息
    async getAppointItem(projectId) {
      let { code, data } = await getAppointItem(projectId);
      if (code == '0000') {
        if (data != null) {
          this.business = data;
          this.userProjectRecord.startTime = this.$utils.Dateformat1(
            new Date()
          );
          this.userProjectRecord.endTime = this.$utils.Dateformat1(new Date());
          this.openOperation = true;
        } else {
          this.$message({
            type: 'warning',
            message: '当前项目拥有权限已过期，无权指派！',
            showClose: true
          });
        }
      }
    },

    //获取指派业务
    async getBusiness() {
      let { code, data } = await getBusiness();
      if (code == '0000') {
        this.business = data;
      }
    },

    // 查询获取项目
    async listProject(val) {
      this.currentPage = val;
      this.selParams.pageNum = val;
      await this.getList2();
    },
    //查看结构物列表,打开dialog
    goToStructureDetailPaage(data) {
      this.showStructureListVisible = false;
      if (data.structureType == 1) {
        this.$router.push({
          path: '/infoManage/bridgeManageDetial',
          query: { id: data.id, name: '项目管理' }
        });
      } else {
        this.$router.push({
          path: '/infoManage/tunnelManageDetial',
          query: { id: data.id, name: '项目管理' }
        });
      }
    },
    //照片列表修改
    changePhotoList(currentList) {
      // console.log(currentList);
      // if (this.addOrEditProjectTitle === '修改项目') {
      //   let imgList = [];
      //   this.ownerStructureList.map((item) => {
      //     currentList.map((id) => {
      //       console.log(item, id);
      //       if (item.id == id) {
      //         imgList.concat(item.photoList);
      //       }
      //     });
      //   });
      //   this.imgList = imgList;
      // }
    },
    //取数组差集
    difference(a, b) {
      let set1 = new Set(a),
        set2 = new Set(b);
      return [
        ...new Set([...set1].filter((x) => !set2.has(x))),
        ...new Set([...set2].filter((x) => !set1.has(x)))
      ];
    },
    //关闭项目dialog
    projectDialogClose() {
      this.projectEditForm.name = '';
      this.projectEditForm.chargePerson = '';
      this.projectEditForm.userName = '';
      this.projectEditForm.unitId = '';
      this.projectEditForm.synopsis = '';
      this.projectEditForm.structureIds = [];
      this.ownerStructureList = [];
      this.editDialogFlag = false;
    },
    //项目指派关闭
    appointClose() {
      this.userProjectRecord.appointUser = '';
      this.userProjectRecord.receiveUnitId = '';
      this.userProjectRecord.startTime = '';
      this.userProjectRecord.endTime = '';
      this.userProjectRecord.business = [];
    },
    //关闭查看详细信息dialog
    closeLookStructureDialog() {
      this.showStructureListVisible = false;
      this.projectStructureList = [];
    },
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex % 2 == 1) {
        return 'first-row';
      } else {
        return 'second-row';
      }
    }
  }
};
</script>

<style scoped lang="scss">
.y-span-1 {
  // color: $YY_SPAN_DIVTITLE;
}
.proDialog {
  .photoRow {
    .photoItem {
      padding: 0 5px;
      display: flex;
      flex-direction: column;
      align-items: center;
      /deep/ .el-image {
        max-height: 88px;
        max-width: 100px;
      }
    }
    /deep/ .el-form-item__content {
      max-height: 250px;
      display: flex;
      align-items: center;
      flex-wrap: wrap;
      overflow: auto;
      &::-webkit-scrollbar {
        width: 6px;
      }
      &:hover {
        &::-webkit-scrollbar-thumb {
          background: #d7dae2;
          border-radius: 6px;
        }
      }
    }
  }
  .edit-project-dialog-foot {
    display: flex;
    justify-content: center;
  }
  .projectEditForm-select {
    // width: 80%;
    /deep/ .el-select__tags {
      max-height: 18.519vh;
      overflow: auto;
      &::-webkit-scrollbar {
        width: 6px;
      }
      &::-webkit-scrollbar-thumb {
        background: #c4c4c4;
        border-radius: 6px;
      }
    }
    /deep/ .el-input__inner {
    }
  }
  /deep/ .el-dialog__body {
    padding: 1.5625vw 3.0417vw;
  }
  /deep/ .el-select {
    width: 100%;
  }
}
.appointDialog {
  /deep/ .el-table th {
    font-size: 14px;
    color: #333;
    font-weight: bold;
  }
  /deep/ .el-table td {
    font-size: 14px;
    color: #333;
  }
  .pageNation {
    text-align: center;
    padding: 1vw 0;
    /deep/ button {
      background: transparent;
      border: 1px solid #d9d9d9;
      border-radius: 2px;
    }
    /deep/ .el-pager li {
      margin: 0 0.2604vw;
      background: transparent;
      border: 1px solid #d9d9d9;
      border-radius: 2px;
    }
    /deep/ .el-pager .active {
      color: #1890ff;
      border: 1px solid #1890ff;
    }
  }
  /deep/ .el-dialog__body {
    padding: 24px 36px;
    padding-top: 12px;
  }
}
.project-board {
  width: 100%;

  .el-pager {
    // background-color: $YY_BACKGROUND;
  }
  .el-pagination.is-background .btn-next,
  .el-pagination.is-background .btn-prev,
  .el-pagination.is-background .el-pager li {
    // margin: 0 5px;
    // background-color: $YY_BACKGROUND;
    color: #a8b2c4;
    min-width: 30px;
    border-radius: 4px;
  }
  .el-pagination.is-background .el-pager li:not(.disabled).active {
    background-color: #419aff;
    // color: $YY_SPAN_IMGTITLE;
  }
  .structure-info-box {
    max-height: 500px;
    overflow: scroll;
    width: 100%;
    .structure-info-table {
      .structure-info-table_header {
        font-size: 14px;
        text-align: left;
        color: #65728a;
        padding-top: 10px;
        padding-bottom: 10px;
        // background-color: $YY_DIV_BACKGROUND;
      }
    }
    .structure-info-table_body {
      width: 100%;
      text-align: left;
      // color: $YY_SPAN_DIVTITLE;
      .structure-info-table_body-font {
        font-size: 14px;
        width: 90%;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        .structure-type-point_B {
          color: #1265fd;
        }
        .structure-type-point_T {
          color: #40db62;
        }
        .structure-type-a {
          color: #419aff;
          cursor: pointer;
        }
      }
      .structure-info-table_line-even {
        padding-top: 12px;
        padding-bottom: 12px;
        // background: $YY_TABLE_ZEBRA;
        padding-left: 8px;
      }
      .structure-info-table_body-odd {
        padding-top: 12px;
        padding-bottom: 12px;
        // background-color: $YY_DIV_BACKGROUND;
        padding-left: 8px;
      }
    }
  }
  .projectEditForm-body {
    // margin-left: 30px;
    .overfloaInputStructure {
      max-height: 100px;
      overflow: scroll;
    }
    .projectEditForm-show-structure-box {
      width: 80%;
      height: 200px;
      overflow: scroll;
      padding: 12px;
      border: 1px solid #2c3d5e;

      .structure-tags {
        float: left;
        // margin-right: 8px;
      }
    }
  }
  .edit-project-dialog-foot {
    // margin-left: 75%;
    text-align: center;
  }
  .project-manage-header {
    position: relative;
    width: 100%;
    height: 60px;
    .add-project-btn {
      // margin-left: 64px;
      float: left;
    }
    .el-button--primary {
      background-color: #419aff;
      // box-shadow: 0 4px 12px 0 rgba(33, 164, 150, 0.4);
    }
    .el-button--primary:hover {
      background-color: #3873ec;
    }
    .project-search-bar {
      float: right;
      display: flex;
      // margin-right: 64px;
      .project-keyword-search {
        width: 250px;
        margin-right: 20px;
      }
      // .project-structure-search
      // {
      //   margin-right: 8px;
      // }
    }
  }
  .project-manage-body {
    // padding-left: 64px;
    // padding-right: 64px;

    .project-show-list {
      font-size: 14px;
      .project-show-list_header {
        // background: $YY_DIV_BACKGROUND;
        padding-top: 16px;
        padding-bottom: 16px;
        padding-left: 8px;
        .project-show-list_header-font {
          color: #65728a;
          line-height: 18px;
        }
      }
      .project-show-list_body {
        font-size: 14px;

        .project-show-list_body-fontx {
          width: 100px;
          white-space: nowrap;
          text-overflow: ellipsis;
          overflow: hidden;
        }
        .project-show-list_odd-line {
          padding-top: 20px;
          padding-bottom: 20px;
          line-height: 18px;
          padding-left: 8px;
          // background-color: $YY_TABLE_ZEBRA;
        }
        .project-show-list_even-line {
          padding-top: 18px;
          padding-bottom: 18px;
          line-height: 18px;
          padding-left: 8px;
          // background: $YY_DIV_BACKGROUND;
        }
        .project-show-list-operation {
          .project-show-list-a {
            color: #419aff;
          }
          .project-show-list-a:hover {
            color: #86c9f0;
          }
        }
      }
    }
    /deep/ .el-table th {
      font-weight: bold;
      color: #333;
    }
    /deep/ .el-table td {
      color: #333;
    }
  }
  .project-manage-foot {
    width: 100%;
    text-align: center;
    .pageNation {
      text-align: center;
      padding: 1vw 0;
      /deep/ button {
        background: transparent;
        border: 1px solid #d9d9d9;
        border-radius: 2px;
      }
      /deep/ .el-pager li {
        background: transparent;
        border: 1px solid #d9d9d9;
        border-radius: 2px;
      }
      /deep/ .el-pager .active {
        color: #1890ff;
        border: 1px solid #1890ff;
      }
    }
  }
}
.infoDialog {
  .infoTable {
    width: 100%;
    .tableItem {
      display: flex;
      align-items: center;
      justify-content: center;
      .dot {
        width: 8px;
        height: 8px;
        border-radius: 50%;
        background: #419aff;
        margin-right: 5px;
      }
    }
    .btn {
      color: #419aff;
      cursor: pointer;
    }
    /deep/ .el-table th {
      font-weight: bold;
      color: #333;
      border: 0;
    }
    /deep/ .el-table__body-wrapper {
      max-height: 470px;
      overflow-y: auto;
      &::-webkit-scrollbar {
        height: 8px;
      }
    }
    /deep/ .el-table__empty-block {
      border-top: 1px solid #ebeef5;
    }
    /deep/ .el-table td {
      color: #333;
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
.projectAppointDialog {
  .appointForm {
    width: 100%;
    display: flex;
    flex-direction: column;
    .y-el-select {
      width: 240px;
    }
    .btn {
      width: 100%;
      display: flex;
      align-items: center;
      justify-content: center;
    }
    /deep/ .el-form-item {
      display: flex;
      justify-content: flex-end;
    }
  }
  /deep/ .el-dialog {
    width: 480px;
  }
  /deep/ .el-dialog__body {
    padding: 20px 36px;
    display: flex;
    flex-direction: column;
    align-items: center;
  }
}
//斑马纹样式
/deep/ .first-row {
  background: #ffffff;
}
/deep/ .second-row {
  background: #f2f4f6;
}
.empty_div {
  line-height: 60px;
  width: 50%;
  color: #4d586d;
  min-height: 60px;
  text-align: center;
  width: 100%;
  display: flex;
  -webkit-box-pack: center;
  justify-content: center;
  -webkit-box-align: center;
  align-items: center;
}
</style>

<style>
#projectArea .el-textarea__inner {
  min-height: 132px;
  max-height: 132px;
}

#dis .el-input.is-disabled .el-input__inner {
  background-color: #1e2c46;
}
</style>
