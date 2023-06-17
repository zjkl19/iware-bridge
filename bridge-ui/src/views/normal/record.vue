<template>
  <div class="record animate__animated animate__fadeIn">
    <div class="border_title">
      <p class="border_p base-font">巡查记录</p>
    </div>
    <div class="btm">
      <div class="left_div">
        <div class="left_div1">
          <div class="selItem">
            <el-select
              v-model="projectId"
              placeholder="请选择项目"
              class="left_div1_select"
              @change="getRecordList(true)"
            >
              <el-option
                v-for="item in projectList"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              >
              </el-option>
            </el-select>

            <el-select
              v-model="yearValue"
              placeholder="年份"
              class="left_div1_select2"
              clearable
              @change="getRecordList(true)"
            >
              <el-option
                v-for="item in yearList"
                :key="item.id"
                :label="item.label"
                :value="item.id"
              >
              </el-option>
            </el-select>
          </div>

          <div class="left_div2" :class="{ initTree: onInit, checkOne }">
            <el-tree
              ref="recordTree"
              :data="treeData"
              :props="defaultProps"
              node-key="treeId"
              @node-click="handleNodeClick"
              :default-checked-keys="defaultCheckedKeys"
              :default-expanded-keys="defaultExpandedKeys"
            ></el-tree>
          </div>
        </div>
        <div class="left_div4">
          <div class="left_div5">
            <div>
              <el-button type="primary" class="export" @click="exportExcel"
                >批量导出</el-button
              >
              <el-button
                type="primary"
                class="export"
                @click="importDialog = true"
                >批量导入</el-button
              >
              <el-button type="primary" @click="showDieaseList()"
                >病害列表</el-button
              >
            </div>

            <div>
              <el-date-picker
                class="left_div5_select"
                v-model="timeValue"
                type="daterange"
                clearable
                align="center"
                value-format="yyyy-MM-dd"
                range-separator="至"
                start-placeholder="开始月份"
                end-placeholder="结束月份"
                :picker-options="pickerMonth"
              >
              </el-date-picker>
              <el-input
                class="left_div5_select2"
                v-model="username"
                placeholder="巡查人员"
                clearable
              />
              <el-select
                v-model="typeId"
                placeholder="请选择类型"
                clearable
                class="left_div5_select2"
              >
                <el-option label="日常巡查" :value="1"> </el-option>
              </el-select>
              <el-button
                type="primary"
                class="left_div5_button"
                @click="getDetailList"
                >查询</el-button
              >
            </div>
          </div>
          <div class="table_div">
            <el-table
              class="tableBox"
              :data="tableData"
              :row-class-name="tableRowClassName"
              @selection-change="handleSelectionChange"
              @select-all="handleSelectionChangeAll"
            >
              <el-table-column type="selection" width="50" align="center">
              </el-table-column>
              <el-table-column
                type="index"
                label="序号"
                width="100"
                align="center"
              ></el-table-column>
              <el-table-column
                prop="inspectionTime"
                label="巡查时间"
                align="center"
              >
              </el-table-column>
              <el-table-column
                prop="structureName"
                label="结构物名称"
                align="center"
                show-overflow-tooltip
              >
              </el-table-column>
              <el-table-column
                prop="inspectionPlanType"
                label="巡查类型"
                align="center"
              >
              </el-table-column>
              <el-table-column prop="inspector" label="巡查人员" align="center">
              </el-table-column>
              <el-table-column
                prop="diseaseCount"
                label="巡查病害数"
                align="center"
              >
              </el-table-column>
              <el-table-column label="操作" min-width="120%" align="center">
                <template slot-scope="scope">
                  <el-link
                    type="primary"
                    class="table_link"
                    @click="showDetail(scope.row)"
                    >病害情况</el-link
                  >
                  <el-link
                    type="primary"
                    class="table_link"
                    @click="getReport(scope.row)"
                    >巡查简报</el-link
                  >
                </template>
              </el-table-column>
            </el-table>
          </div>
        </div>
      </div>
    </div>

    <!-- 巡查简报 -->
    <div v-if="showBriefing == 1">
      <bridgeBriefing
        :reportId="reportId"
        :structureId="structureId"
        :parentName="parentName"
        @closed="showBriefing = 0"
      ></bridgeBriefing>
    </div>
    <div v-if="showBriefing == 2">
      <tunnelBriefing
        :reportId="reportId"
        :structureId="structureId"
        :parentName="parentName"
        @closed="showBriefing = 0"
      ></tunnelBriefing>
    </div>

    <!-- 病害情况 -->
    <el-dialog
      class="detailDialog"
      :title="detailTitle + '病害情况'"
      :visible.sync="detailDialog"
      :modal-append-to-body="false"
      :close-on-click-modal="false"
    >
      <div class="detail_dialog_div">
        <el-input
          class="detail_dialog_select my_select2"
          v-model="detailObj.keyword"
          placeholder="关键词搜索"
          clearable
        ></el-input>
        <el-button type="primary" @click="getDiseaseList">查询</el-button>
      </div>
      <div v-if="diseaseNum == 1" class="tableBox">
        <el-table :data="tableData2" :row-class-name="tableRowClassName">
          <el-table-column type="index" label="序号" width="100" align="center">
          </el-table-column>
          <el-table-column
            prop="damageType"
            label="病害类型"
            align="center"
            show-overflow-tooltip
          >
          </el-table-column>
          <el-table-column
            label="病害程度/异常部位"
            align="center"
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
                {{ scope.row.remarks || '-' }}
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="strategy" label="处置对策" align="center">
            <template slot-scope="scope">
              <p>
                {{
                  scope.row.strategy == 1
                    ? '观察'
                    : scope.row.strategy == 2
                    ? '报修'
                    : scope.row.strategy == 3
                    ? '监测'
                    : scope.row.strategy == 4
                    ? '即修'
                    : scope.row.strategy == 5
                    ? '更换'
                    : scope.row.strategy == 6
                    ? '增设'
                    : '-'
                }}
              </p>
            </template>
          </el-table-column>
          <el-table-column label="病害照片" align="center">
            <template slot-scope="scope">
              <el-link
                v-if="scope.row.photo.length > 0"
                type="primary"
                @click="checkPhotos(scope.row)"
                >查看</el-link
              >
              <span v-else>-</span>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div v-else class="tableBox">
        <el-table :data="tableData2" :row-class-name="tableRowClassName">
          <el-table-column type="index" label="序号" width="100" align="center">
          </el-table-column>
          <el-table-column
            prop="damageType"
            label="病害类型"
            align="center"
            show-overflow-tooltip
          >
          </el-table-column>
          <!-- <el-table-column prop="diseaseName" label="病害名"> </el-table-column> -->
          <el-table-column
            label="异常位置"
            align="center"
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
              <div class="textOF">
                {{ scope.row.remarks || '-' }}
              </div>
            </template>
          </el-table-column>
          <el-table-column label="判定" align="center" show-overflow-tooltip>
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
            <template slot-scope="scope">
              <span>
                {{
                  scope.row.strategy == 7
                    ? '跟踪监测'
                    : scope.row.strategy == 8
                    ? '维修处置'
                    : '定期或专项检查'
                }}
              </span>
            </template>
          </el-table-column>
          <el-table-column label="病害照片" align="center">
            <template slot-scope="scope">
              <el-link
                v-if="scope.row.photo.length > 0"
                type="primary"
                @click="checkPhotos(scope.row)"
                >查看</el-link
              >
              <span v-else>-</span>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-dialog>
    <!-- 查看图片弹框 -->
    <el-dialog
      class="dialogImage"
      title="病害图片"
      :visible.sync="dialogImgShow"
      :modal-append-to-body="false"
      :close-on-click-modal="false"
    >
      <!-- <div class="diseasePhoto"> -->
      <el-carousel
        v-if="dialogImageUrlList.length > 0"
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
      <!-- </div> -->
    </el-dialog>
    <!-- 病害列表 -->
    <el-dialog
      class="diseaseListDialog"
      :title="diseaseListTitle + '病害列表'"
      :visible.sync="diseaseListDialog"
      :modal-append-to-body="false"
      :close-on-click-modal="false"
    >
      <div class="detail_dialog_div">
        <div class="topLeft">
          <div
            @click="typeChange(1)"
            :class="{ activeClass: activeIndex == 1 }"
          >
            桥梁
          </div>
          <div
            @click="typeChange(2)"
            :class="{ activeClass: activeIndex == 2 }"
          >
            隧道
          </div>
        </div>

        <div>
          <el-input
            class="detail_dialog_select my_select2"
            v-model="dieaseKeyword"
            placeholder="关键词搜索"
            clearable
          ></el-input>
          <el-button type="primary" @click="showDieaseList">查询</el-button>
          <el-button type="primary" @click="exportDisease">导出</el-button>
        </div>
      </div>
      <div v-if="activeIndex == 1" class="tableBox">
        <el-table :data="diseaseTable" :row-class-name="tableRowClassName">
          <el-table-column type="index" label="序号" width="80" align="center">
          </el-table-column>
          <el-table-column
            prop="structureName"
            label="结构物"
          ></el-table-column>
          <el-table-column
            prop="damageType"
            label="病害类型"
            show-overflow-tooltip
          >
          </el-table-column>
          <el-table-column label="病害程度/异常部位" show-overflow-tooltip>
            <template slot-scope="scope">
              <div class="textOF">
                {{ scope.row.showItem || '-' }}
              </div>
            </template>
          </el-table-column>

          <el-table-column label="病害或异常说明" show-overflow-tooltip>
            <template slot-scope="scope">
              <div class="textOF">
                {{ scope.row.remarks || '-' }}
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="strategy" label="处置对策"> </el-table-column>
          <el-table-column
            prop="inspectionTime"
            label="巡查时间"
          ></el-table-column>
          <el-table-column label="维修时间">
            <template slot-scope="scope">
              <span>
                {{ scope.row.maintainTime || '-' }}
              </span>
            </template>
          </el-table-column>
          <el-table-column label="病害状态" width="80" align="center">
            <template slot-scope="scope">
              <span>
                {{ scope.row.status == 2 ? '已修' : '待修' }}
              </span>
            </template>
          </el-table-column>
          <el-table-column label="病害照片" width="80" align="center">
            <template slot-scope="scope">
              <el-link
                v-if="scope.row.photo.length > 0"
                type="primary"
                @click="checkPhotos(scope.row)"
                >查看</el-link
              >
              <span v-else>-</span>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div v-else class="tableBox">
        <el-table :data="diseaseTable" :row-class-name="tableRowClassName">
          <el-table-column type="index" label="序号" width="50" align="center">
          </el-table-column>
          <el-table-column
            prop="structureName"
            label="结构物"
          ></el-table-column>
          <el-table-column
            prop="damageType"
            label="病害类型"
            show-overflow-tooltip
          >
          </el-table-column>
          <!-- <el-table-column prop="diseaseName" label="病害名"> </el-table-column> -->
          <el-table-column label="异常位置" show-overflow-tooltip>
            <template slot-scope="scope">
              <div class="textOF">
                {{ scope.row.showItem || '-' }}
              </div>
            </template>
          </el-table-column>
          <el-table-column label="异常描述" show-overflow-tooltip>
            <template slot-scope="scope">
              <div class="textOF">
                {{ scope.row.remarks || '-' }}
              </div>
            </template>
          </el-table-column>
          <el-table-column
            prop="exceptionType"
            label="判定"
            show-overflow-tooltip
          >
            <!-- <template slot-scope="scope">
              <div>
                {{
                  scope.row.exceptionType == 1
                    ? '一般异常'
                    : scope.row.exceptionType == 2
                    ? '严重异常'
                    : '-'
                }}
              </div>
            </template> -->
          </el-table-column>
          <el-table-column prop="strategy" label="养护措施">
            <template slot-scope="scope">
              <div>
                {{ scope.row.strategy }}
              </div>
            </template>
          </el-table-column>
          <el-table-column
            prop="inspectionTime"
            label="巡查时间"
          ></el-table-column>
          <el-table-column label="维修时间">
            <template slot-scope="scope">
              <span>
                {{ scope.row.maintainTime || '-' }}
              </span>
            </template>
          </el-table-column>
          <el-table-column label="病害状态" width="80" align="center">
            <template slot-scope="scope">
              <span>
                {{ scope.row.status == 2 ? '已修' : '待修' }}
              </span>
            </template>
          </el-table-column>
          <el-table-column label="病害照片" width="80" align="center">
            <template slot-scope="scope">
              <el-link
                v-if="scope.row.photo.length > 0"
                type="primary"
                @click="checkPhotos(scope.row)"
                >查看</el-link
              >
              <span v-else>-</span>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-dialog>
    <!-- 批量导入 -->
    <el-dialog
      class="importDialog"
      title="批量导入"
      :visible.sync="importDialog"
      :before-close="importDialogClose"
      :modal-append-to-body="false"
      :close-on-click-modal="false"
    >
      <el-form class="fileBox" label-width="120px">
        <el-form-item label="第一步：">
          <el-button class="fileBtn" @click="downloadTemplate"
            >下载模板</el-button
          >
          <div class="notice">下载模板文件，根据模板填写信息。</div>
        </el-form-item>
        <el-form-item label="第二步：" required>
          <el-upload
            ref="uploadFile"
            class="reportUpload"
            :action="actionUrl"
            :headers="headers"
            :auto-upload="false"
            :limit="1"
            accept=".xlsx"
            :on-change="handleChange"
            :on-exceed="handleExceed"
            :on-remove="handleRemove"
            :http-request="uploadFile"
            :file-list="fileList"
          >
            <el-button v-if="fileList.length == 0" class="fileBtn"
              >点击上传</el-button
            >
          </el-upload>
          <div class="notice">
            <span>1.表格首行为字段名，不能删除、修改。</span>
            <span
              >2.表头字段标记<span style="color: #f56c6c">*</span
              >为必填项，请务必填写。</span
            >
            <span>3.仅支持.xlsx格式。</span>
          </div>
        </el-form-item>
      </el-form>
      <div class="btn">
        <el-button type="primary" @click="importSubmit('reportForm')"
          >确定</el-button
        >
        <el-button @click="importDialogClose">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  getRecordList,
  getDetailList,
  getDiseaseList,
  exportExcel,
  importInsExcel,
  exportDisease
} from '@/api/normal/record';
import { getProjectListByModel } from '@/api/common';
import { Dateformat1 } from '@/utils/utils';
import bridgeBriefing from './bridgeBriefing';
import tunnelBriefing from './tunnelBriefing';
export default {
  name: 'record',
  components: { bridgeBriefing, tunnelBriefing },
  data() {
    const _this = this;
    return {
      //左侧查询条件
      projectId: '',
      projectList: [],
      yearValue: '',
      yearList: [],
      //病害详细弹框
      detailTitle: '',
      detailDialog: false,
      tableData2: [],
      detailObj: {
        status: '',
        keyword: ''
      },
      //病害列表
      activeIndex: 1,
      dieaseKeyword: '',
      diseaseListTitle: '',
      diseaseTable: [],
      diseaseListDialog: false,
      statusList: [],
      detailStructureId: '',
      tableData: [],
      tableSelectList: [], //批量导出列表
      diseaseNum: '',
      //批量导入数据
      importDialog: false,
      actionUrl: '/bridge/inspection/record/importInsExcel',
      headers: {
        'X-ROUTER-TOKEN': this.$store.getters.getToken,
        'X-ROUTER-URL': '/normal/record'
      },
      fileList: [],
      fileData: '',
      // detailItem: {},
      //右侧查询条件
      timeValue: '',
      disabledDate: [],
      pickerMonth: {
        disabledDate(time) {
          return (
            time.getTime() <
              new Date(_this.disabledDate[0]).getTime() - 24 * 3600 * 1000 ||
            time.getTime() > new Date(_this.disabledDate[1]).getTime()
          );
        }
      },
      username: '',
      typeId: '',
      onInit: false,
      checkOne: true,
      treeData: [],
      defaultProps: {
        children: 'structureList',
        label: 'name'
      },
      currentNodeKey: '',
      defaultCheckedKeys: [],
      defaultExpandedKeys: [],
      //查看图片弹框
      dialogImageUrlList: [],
      dialogImgShow: false,
      idForSearch: undefined, //* 保存要用于弹窗查询的id,
      detailStructureId: '',
      showBriefing: 0, //桥梁、隧道简报显示
      reportId: '',
      parentName: '',
      structureId: ''
    };
  },
  beforeMount() {
    this.$utils.getAuthPage(this); //获取权限
  },
  mounted() {
    this.getProjectListByModel(); //查询项目列表
    let nowYear = new Date().getFullYear();
    for (let index = nowYear; index > nowYear - 20; index--) {
      let model = {
        id: index,
        label: index + '年'
      };
      this.yearList.push(model);
    }
  },
  methods: {
    //获取巡查列表
    async getRecordList(state) {
      this.projectList.map((item) => {
        if (item.id == this.projectId) {
          this.diseaseListTitle = item.name;
        }
      });
      let params = {
        projectId: this.projectId,
        year: !!this.yearValue ? this.yearValue : 0
      };
      let { code, data } = await getRecordList(params);
      if (code == '0000') {
        let queryInfo = this.$route.query; //路由信息
        let treeName = ''; //巡查月份的名字
        this.tableData = [];
        if (data.length > 0) {
          this.defaultExpandedKeys = [data[0].name];
          let checke = false; //为了选中第一个有数据的tree结构
          data.map((item) => {
            item.treeId = item.name + item.id;
            item.structureList.map((child) => {
              child.treeId = item.name + child.id;
              if (
                item.projectId == queryInfo.projectId &&
                child.id == queryInfo.structureId &&
                !checke &&
                !state
              ) {
                checke = true;
                treeName = item.name;
                this.defaultExpandedKeys = [item.name];
                this.timeValue = [
                  item.planTime + '-01',
                  this.$utils.getMonthDay(item.planTime, true)
                ];
                this.disabledDate = [
                  item.planTime + '-01',
                  this.$utils.getMonthDay(item.planTime, true)
                ];
              }
              child.planTime = item.planTime;
            });
          });
          //默认查询记录列表时间范围
          if (state) {
            this.timeValue = [
              data[0].planTime + '-01',
              this.$utils.getMonthDay(data[0].planTime, true)
            ];
            this.disabledDate = [
              data[0].planTime + '-01',
              this.$utils.getMonthDay(data[0].planTime, true)
            ];
          }
        } else {
          this.timeValue = '';
          this.disabledDate = [];
          this.defaultCheckedKeys = [];
          this.defaultExpandedKeys = [];
        }
        this.treeData = data;
        this.$nextTick(async () => {
          if (this.treeData.length > 0) {
            if (queryInfo.structureId && !state) {
              this.onInit = true;
              this.checkOne = false;
              this.detailStructureId = queryInfo.structureId;
              this.defaultCheckedKeys = [treeName + queryInfo.structureId];
            } else {
              this.defaultCheckedKeys = [data[0].structureList[0].treeId];
              this.detailStructureId = data[0].structureList[0].id;
            }
            await this.getDetailList();
          }
        });
      }
    },
    //获取项目列表
    async getProjectListByModel() {
      let id = this.$store.getters.getActiveIndex;
      let { code, data } = await getProjectListByModel(id);
      if (code == '0000') {
        this.projectList = data;
        let queryInfo = this.$route.query;
        if (queryInfo.projectId) {
          data.map((item) => {
            if (item.id == queryInfo.projectId) {
              this.diseaseListTitle = item.name;
            }
          });
          this.projectId = Number(queryInfo.projectId);
          await this.getRecordList();
        } else {
          if (data.length > 0) {
            this.projectId = data[0].id;
            this.diseaseListTitle = data[0].name;
            await this.getRecordList(true);
          }
        }
      }
    },
    //获取巡查记录列表
    async getDetailList() {
      let params = {
        startTime: !!this.timeValue ? this.timeValue[0] : '',
        endTime: !!this.timeValue ? this.timeValue[1] : '',
        username: this.username,
        type: this.typeId,
        projectId: this.projectId,
        structureId: this.detailStructureId
      };
      let { code, data } = await getDetailList(params);
      if (code == '0000') {
        this.tableData = data;
        this.exportParams = JSON.stringify(params);
      }
    },
    //获取病害情况
    async getDiseaseList() {
      let params = {
        keyword: this.detailObj.keyword
      };
      let { code, data } = await getDiseaseList(this.idForSearch, params);
      if (code == '0000') {
        this.tableData2 = data;
      }
    },
    //批量导出
    async exportExcel() {
      if (this.tableSelectList.length == 0) {
        this.$message({
          message: '请选择要导出的数据！',
          showClose: true,
          type: 'warning',
          duration: 2000
        });
        return;
      }
      let ids = [];
      this.tableSelectList.map((item) => {
        ids.push(item.id);
      });
      let params = {
        exportAll: 0,
        filter: JSON.parse(this.exportParams),
        ids
      };
      exportExcel(params)
        .then((res) => {
          let fileName = '巡查记录' + Dateformat1(new Date()) + '.docx';
          this.$utils.downloadBlob(res, fileName);
        })
        .catch();
    },
    //列表选择
    handleNodeClick(data) {
      if (data && data.structureList) {
        return;
      }
      this.onInit = false;
      this.checkOne = false;
      if (!!data.planTime) {
        this.timeValue = [
          data.planTime + '-01',
          this.$utils.getMonthDay(data.planTime, true)
        ];
        this.disabledDate = [
          data.planTime + '-01',
          this.$utils.getMonthDay(data.planTime, true)
        ];
        this.detailStructureId = data.id;
        this.$nextTick(async () => {
          await this.getDetailList();
        });
      }
    },
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex % 2 == 1) {
        return 'first-row';
      } else {
        return 'second-row';
      }
    },
    //表格多选
    handleSelectionChange(list) {
      this.tableSelectList = list;
    },
    //表格勾选全部
    handleSelectionChangeAll(list) {
      this.tableSelectList = list;
    },
    //病害情况
    showDetail(data) {
      if (data.structureType == 1) {
        this.diseaseNum = 1;
      } else {
        this.diseaseNum = 2;
      }
      let time = !!data.inspectionTime && data.inspectionTime.split(' ')[0];
      let name =
        data.structureName +
        time.split('-')[0] +
        '年' +
        time.split('-')[1] +
        '月' +
        time.split('-')[2] +
        '日';
      this.detailTitle = name;
      this.idForSearch = data.id; //* 保存要用于弹窗查询的id
      this.getDiseaseList();
      this.detailDialog = true;
    },
    //病害列表
    async showDieaseList() {
      let params = {
        keyword: this.dieaseKeyword,
        projectId: this.projectId,
        structureType: this.activeIndex
      };
      let { code, data } = await getDiseaseList(0, params);
      if (code == '0000') {
        data.map((item) => {
          item.strategy =
            item.strategy == 1
              ? '观察'
              : item.strategy == 2
              ? '报修'
              : item.strategy == 3
              ? '监测'
              : item.strategy == 4
              ? '即修'
              : item.strategy == 5
              ? '更换'
              : item.strategy == 6
              ? '增设'
              : item.strategy == 7
              ? '跟踪监测'
              : item.strategy == 8
              ? '维修处置'
              : item.strategy == 9
              ? '定期或专项检查'
              : '-';
          item.exceptionType =
            item.exceptionType == 1
              ? '一般异常'
              : item.exceptionType == 2
              ? '严重异常'
              : '-';
          item.inspectionTime = !!item.inspectionTime
            ? item.inspectionTime.split(' ')[0]
            : '-';
          item.maintainTime = !!item.maintainTime
            ? item.maintainTime.split(' ')[0]
            : '-';
        });
        this.diseaseTable = data;
        this.$nextTick(() => {
          this.diseaseListDialog = true;
        });
      }
    },
    //导出病害列表
    exportDisease() {
      let params = {
        keyword: this.dieaseKeyword,
        projectId: this.projectId,
        structureType: this.activeIndex
      };
      exportDisease(params)
        .then((res) => {
          let fileName = '病害列表' + Dateformat1(new Date()) + '.xls';
          this.$utils.downloadBlob(res, fileName);
        })
        .catch();
    },
    //病害列表桥隧切换
    typeChange(index) {
      this.activeIndex = index;
      this.showDieaseList();
    },
    //巡查简报
    getReport(data) {
      this.reportId = data.id;
      this.structureId = data.structureId;
      this.parentName = '巡查记录';
      if (data.structureType == 1) {
        this.showBriefing = 1;
        // this.$router.push({
        //   path: '/normal/bridgeBriefing',
        //   query: {
        //     id: data.id,
        //     parentName: '巡查记录'
        //   }
        // });
      } else {
        this.showBriefing = 2;
        // this.$router.push({
        //   path: '/normal/tunnelBriefing',
        //   query: {
        //     id: data.id,
        //     parentName: '巡查记录'
        //   }
        // });
      }
    },
    //查看病害图片
    checkPhotos(data) {
      let arry = JSON.parse(JSON.stringify(data));
      let photoList = [];
      arry.photo.map((item) => {
        item.path = this.$basePath + item.path;
        photoList.push(item.path);
      });
      this.dialogImageUrlList = photoList;
      this.dialogImgShow = true;
    },
    //改变文件
    handleChange(file, fileList) {
      let arry = file.name.split('.');
      let name = arry[arry.length - 1];
      let typeList = ['xlsx'];
      if (!typeList.includes(name)) {
        this.$message.warning('请上传正确的格式');
        this.$refs.uploadFile.clearFiles();
        return;
      }
      this.fileList = fileList;
    },
    //验证文件
    handleExceed(file, fileList) {
      this.$message.warning(
        `当前限制选择 1 个文件，本次选择了 ${file.length} 个文件，共选择了 ${
          file.length + fileList.length
        } 个文件`
      );
    },
    //移除文件
    handleRemove(file, fileList) {
      this.fileList = fileList;
    },
    //上传文件
    uploadFile(file) {
      this.fileData.append('excel', file.file); // append增加数据
    },
    //下载模板
    downloadTemplate() {
      let path = './static/file/normal.xlsx';
      this.$utils.downloadFile(path, '巡查记录数据模板.xlsx');
    },
    //上传批量导入文件
    async importSubmit() {
      if (this.fileList.length == 0) {
        this.$message({
          type: 'warning',
          message: '请上传文件!',
          showClose: true,
          duration: 2000
        });
        return;
      }
      let loading = this.$loading({
        lock: true,
        text: '正在上传解析中，请稍等...',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      });
      this.fileData = new FormData();
      this.$refs.uploadFile.submit();
      try {
        let { code, msg } = await importInsExcel(this.fileData);
        loading.close();
        if (code == '0000') {
          this.$message({
            type: 'success',
            message: msg,
            showClose: true,
            duration: 2000
          });
          await this.getRecordList(true);
          await this.importDialogClose();
          // await this.getDetailList();
        } else if (code == '6039') {
          // let html = msg.split('\n').join('</br>');
          this.$message({
            type: 'error',
            dangerouslyUseHTMLString: true,
            message: `<div style="padding-right:10px;white-space: break-spaces;">${msg}</div>`,
            showClose: true,
            duration: 5000
          });
        }
      } catch (error) {
        loading.close();
      }
    },
    importDialogClose() {
      this.importDialog = false;
      this.fileList = [];
    }
  }
};
</script>
<style lang="scss" scoped>
.record {
  padding: 24px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  .border_title {
    font-size: 16px;
    color: #262626;
    line-height: 22px;
  }
  .btm {
    width: 100%;
    height: 76.482vh;
    .left_div {
      height: 100%;
      display: flex;
      .left_div1 {
        width: 348px;
        height: 100%;
        border-right: 1px solid #e8e8e8;
        padding-right: 24px;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        .selItem {
          margin-bottom: 20px;
          display: flex;
          justify-content: space-between;
        }
        .left_div1_select {
          width: 208px;
        }
        .left_div1_select2 {
          width: 100px;
        }
        .left_div2 {
          height: 71.668vh;
          overflow: auto;
          /deep/ .el-tree-node__children .is-current > .el-tree-node__content {
            color: #fff;
            background: #419aff !important;
          }
        }
        //初始化默认选中样式(有传参)
        .initTree {
          /deep/ .el-tree-node__children .is-checked .el-tree-node__content {
            color: #fff;
            background: #419aff !important;
          }
        }
        //初始化默认选中样式(无传参)
        .checkOne {
          /deep/
            .el-tree-node:first-child
            .el-tree-node__children
            .el-tree-node:first-child
            > .el-tree-node__content {
            color: #fff;
            background: #419aff !important;
          }
        }
      }
      .left_div4 {
        width: 1472px;
        height: 100%;
        padding-left: 24px;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        .left_div5 {
          padding-bottom: 26px;
          display: flex;
          align-items: center;
          justify-content: space-between;
          .export {
            color: #419aff;
            background: transparent;
            border: 1px solid #419aff;
          }
          .left_div5_select {
            width: 282px;
            height: 40px;
            margin-right: 1.04166vw;
          }
          .left_div5_select2 {
            width: 128px;
            height: 3.7037vh;
            margin-right: 1.04166vw;
          }
          /deep/ .el-date-editor .el-range-separator {
            width: unset;
          }
        }
        .table_div {
          flex: 1;
          /deep/ .el-table__body-wrapper {
            max-height: 65vh;
            overflow: auto;
            &:hover {
              &::-webkit-scrollbar {
                width: 8px;
              }
              &::-webkit-scrollbar-thumb {
                background: #c4c4c4;
                border-radius: 8px;
              }
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
          .table_link {
            margin-left: 1.04166vw;
          }
          /deep/ .el-table__empty-block {
            border-top: 1px solid #ebeef5;
          }
        }
      }
    }
  }
}
.record .base-font {
  font-family: PingFang SC;
  font-style: normal;
  font-weight: normal;
}
.record .el-divider--vertical {
  height: 76.2222vh;
}
// .record /deep/.el-tree-node__content {
//   height: 3.7037vh;
// }
.el-table .warning-row {
  background: #ffffff;
}

.el-table .success-row {
  background: #f2f4f6;
}
.record .my_white {
  color: white;
  border-left: 1px solid rgb(222, 222, 222);
  border-top: 1px solid rgb(222, 222, 222);
  .my_p {
    text-align: center;
    font-weight: 400;
    color: #262626;
    font-size: 0.8333vw;
  }
}
.record .my_gray {
  color: white;
  border-left: 1px solid rgb(222, 222, 222);
  border-top: 1px solid rgb(222, 222, 222);
  background: rgb(244, 244, 244);
  .my_p {
    text-align: center;
    font-weight: 400;
    color: #262626;
    font-size: 0.8333vw;
  }
}

.record .table-button2 {
  width: 2.91667vw;
  height: 2.222vh;
  .table-span {
    position: absolute;
    left: 1.3vw;
    top: 1.8vh;
  }
}

//病害情况弹框
.detailDialog {
  .detail_dialog_div {
    width: 100%;
    padding-bottom: 24px;
    display: flex;
    align-items: center;
    justify-content: flex-end;
    .topLeft {
      display: flex;
      div {
        width: 88px;
        height: 32px;
        font-size: 14px;
        color: #000;
        display: flex;
        border: 1px solid #d9d9d9;
        align-items: center;
        justify-content: center;
        cursor: pointer;
        &:first-child {
          // border-left: 0;
          border-right: 0;
        }
        &:last-child {
          border-left: 0;
          // border-right: 0;
        }
      }
      .activeClass {
        color: #2f80ed;
        border: 1px solid #2f80ed !important;
      }
    }
    .detail_dialog_select {
      margin-right: 20px;
    }
    .my_select1 {
      width: 128px;
    }
    .my_select2 {
      width: 282px;
    }
  }
  .tableBox {
    width: 100%;
    .danger {
      width: 56px;
      height: 2.225vh;
      font-size: 14px;
      color: #fff;
      background: #eb5757;
      border-radius: 2px;
      margin: 0 auto;
    }
    .working {
      width: 56px;
      height: 2.225vh;
      font-size: 14px;
      color: #fff;
      background: #419aff;
      border-radius: 2px;
      margin: 0 auto;
    }
    .success {
      width: 56px;
      height: 2.225vh;
      font-size: 14px;
      color: #fff;
      background: #27ae60;
      border-radius: 2px;
      margin: 0 auto;
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
    /deep/ .el-table__empty-block {
      border-top: 1px solid #ebeef5;
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

//病害列表弹框
.diseaseListDialog {
  .detail_dialog_div {
    width: 100%;
    padding-bottom: 24px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    .topLeft {
      display: flex;
      div {
        width: 88px;
        height: 32px;
        font-size: 14px;
        color: #000;
        display: flex;
        border: 1px solid #d9d9d9;
        align-items: center;
        justify-content: center;
        cursor: pointer;
        &:first-child {
          // border-left: 0;
          border-right: 0;
        }
        &:last-child {
          border-left: 0;
          // border-right: 0;
        }
      }
      .activeClass {
        color: #2f80ed;
        border: 1px solid #2f80ed !important;
      }
    }
    .detail_dialog_select {
      margin-right: 20px;
    }
    .my_select1 {
      width: 128px;
    }
    .my_select2 {
      width: 282px;
    }
  }
  .tableBox {
    width: 100%;
    .danger {
      width: 56px;
      height: 2.225vh;
      font-size: 14px;
      color: #fff;
      background: #eb5757;
      border-radius: 2px;
      margin: 0 auto;
    }
    .working {
      width: 56px;
      height: 2.225vh;
      font-size: 14px;
      color: #fff;
      background: #419aff;
      border-radius: 2px;
      margin: 0 auto;
    }
    .success {
      width: 56px;
      height: 2.225vh;
      font-size: 14px;
      color: #fff;
      background: #27ae60;
      border-radius: 2px;
      margin: 0 auto;
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
    /deep/ .el-table__empty-block {
      border-top: 1px solid #ebeef5;
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
    /deep/ .el-table .cell.el-tooltip {
      width: 100% !important;
    }
  }
  /deep/ .el-dialog {
    width: 1280px;
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
  // .diseasePhoto {
  //   height: 360px;
  //   display: flex;
  //   justify-content: center;
  // }
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
  /deep/ .el-dialog {
    margin: 0;
    width: 768px;
  }
  /deep/ .el-dialog__body {
    padding: 12px 24px;
  }
}

//批量导入弹框
.importDialog {
  .fileBox {
    // padding-right: 50px;
    .fileBtn {
      padding: 8px 15px;
      border: 1px solid #419aff;
      color: #419aff;
    }
    .notice {
      margin-top: 10px;
      line-height: 20px;
      color: #999;
      display: flex;
      flex-direction: column;
      span {
        line-height: 20px;
      }
    }
    .reportUpload {
      display: flex;
      /deep/ .el-upload-list__item-name {
        max-width: 195px;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
      }
    }
  }
  .btn {
    padding-top: 10px;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  /deep/ .el-dialog {
    margin: 0;
    width: 450px;
  }
  /deep/ .el-dialog__body {
    padding: 16px 24px !important;
  }
}

.textOF {
  width: 100%;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

//斑马纹样式
/deep/ .first-row {
  background: #ffffff;
}
/deep/ .second-row {
  background: #f2f4f6;
}
</style>
