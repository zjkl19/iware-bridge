<template>
  <div class="warnSet animate__animated animate__fadeIn">
    <div class="boxTop">
      <div>
        <el-button v-if="addOpt" type="primary" @click="showDialog(1)">
          新建传感器
        </el-button>
      </div>
      <div>
        <!-- 选择结构物类型 -->
        <el-select
          class="pad"
          v-model="structureId"
          placeholder="结构物"
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
        <!-- 选择传感器类型 -->
        <el-select
          class="pad"
          v-model="sensorTypeId"
          placeholder="传感器类型"
          clearable
        >
          <el-option
            v-for="item in sensorTypeList"
            :key="item.id"
            :label="item.label"
            :value="item.id"
          >
          </el-option>
        </el-select>
        <!-- 选择检测位置 -->
        <el-select
          class="pad"
          v-model="componentId"
          placeholder="监测位置"
          clearable
        >
          <el-option
            v-for="item in componentList"
            :key="item.id"
            :label="item.label"
            :value="item.id"
          >
          </el-option>
        </el-select>
        <el-button class="pad" type="primary" @click="getSensorList"
          >查询</el-button
        >
      </div>
    </div>
    <div>
      <!-- 数据展示区 -->
      <el-row>
        <el-col :span="24">
          <div class="grid-content bg-purple-dark song-table-1">
            <normalTable
              :tableData="tableData"
              :titleList="titleList"
              :tableName="'传感器设置'"
              :opList="opList"
              :opWidth="'220'"
              :pageNum="pageNum"
              @tableClick="tableClick"
            ></normalTable>
          </div>
        </el-col>
      </el-row>

      <!-- 分页器 -->
      <el-row>
        <el-col :span="24">
          <div
            id="userPage"
            class="grid-content bg-purple-dark songu-page"
            style="text-align: center"
          >
            <el-pagination
              class="pageNation"
              background
              @current-change="handleCurrentChange"
              :current-page.sync="pageNum"
              :page-size="10"
              layout="total, prev, pager, next"
              :total="dataTotal"
            ></el-pagination>
          </div>
        </el-col>
      </el-row>
    </div>
    <!-- 新建预警弹框 -->
    <el-dialog
      class="sensorDialog"
      :title="sensorDialogTitle"
      :visible.sync="showSensorDialog"
      :before-close="sensorDialigClose"
      :close-on-click-modal="false"
      append-to-body
      modal-append-to-body
    >
      <el-form class="sensorFormBox" :model="sensorForm">
        <el-form-item class="point" label="测点信息">
          <el-form
            class="pointBox"
            ref="pointObj"
            :model="sensorForm.pointObj"
            :rules="pointrRules"
          >
            <el-form-item
              v-for="item in pointList"
              :key="item.id"
              :label="item.lebel"
              :prop="item.prop"
            >
              <el-input
                class="formInput"
                v-if="item.type == 1"
                v-model="sensorForm.pointObj[item.prop]"
                placeholder="请输入"
                maxlength="30"
                clearable
              ></el-input>
              <el-select
                v-else-if="item.type == 2"
                class="formInput"
                v-model="sensorForm.pointObj[item.prop]"
                placeholder="请选择"
              >
                <el-option
                  v-for="child in item.list"
                  :key="child.id"
                  :label="child.label"
                  :value="child.id"
                >
                </el-option>
              </el-select>
              <el-date-picker
                v-else-if="item.type == 3"
                class="formTime"
                v-model="sensorForm.pointObj[item.prop]"
                type="date"
                placeholder="选择部署日期"
                value-format="yyyy-MM-dd"
              >
              </el-date-picker>
              <el-select
                v-else-if="item.type == 5"
                class="formInput"
                v-model="sensorForm.pointObj[item.prop]"
                placeholder="请选择"
                multiple
                @change="getDetailList(item.id)"
              >
                <el-option
                  v-for="child in item.list"
                  :key="child.id"
                  :label="child.label"
                  :value="child.id"
                >
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="XYZ坐标：" required>
              <div class="position">
                <el-input
                  class="poInput"
                  v-model="sensorForm.pointObj.xAxis"
                  placeholder="X坐标"
                  maxlength="30"
                  clearable
                ></el-input>
                <el-input
                  class="poInput"
                  v-model="sensorForm.pointObj.yAxis"
                  placeholder="Y坐标"
                  maxlength="30"
                  clearable
                ></el-input>
                <el-input
                  class="poInput"
                  v-model="sensorForm.pointObj.zAxis"
                  placeholder="Z坐标"
                  maxlength="30"
                  clearable
                ></el-input>
              </div>
            </el-form-item>
          </el-form>
        </el-form-item>

        <el-form-item class="sensor" label="传感器信息">
          <el-form
            class="sensorBox"
            ref="sensorObj"
            :model="sensorForm.sensorObj"
            :rules="sensorRules"
          >
            <el-form-item
              v-for="item in sensorList"
              :key="item.id"
              :label="item.lebel"
              :prop="item.prop"
            >
              <el-input
                class="formInput"
                v-if="item.type == 1"
                v-model="sensorForm.sensorObj[item.prop]"
                placeholder="请输入"
                maxlength="30"
                clearable
                ><template v-if="item.id == 4" slot="append"
                  >Hz</template
                ></el-input
              >
              <el-select
                v-else-if="item.type == 2"
                class="formInput"
                v-model="sensorForm.sensorObj[item.prop]"
                placeholder="请选择"
                @change="typeChange(item.id)"
              >
                <el-option
                  v-for="child in item.list"
                  :key="child.id"
                  :label="child.label"
                  :value="child.id"
                >
                </el-option>
              </el-select>
              <el-date-picker
                v-else-if="item.type == 4"
                class="formTime"
                v-model="sensorForm.sensorObj[item.prop]"
                type="date"
                placeholder="选择部署日期"
                value-format="yyyy-MM-dd"
                :picker-options="pickerOptions"
              >
              </el-date-picker>
              <el-switch
                class="formInput"
                v-if="item.type == 3"
                v-model="sensorForm.sensorObj[item.prop]"
                :active-value="2"
                :inactive-value="sensorForm.sensorObj.statusValue"
                active-color="#419aff"
                active-text="是"
              >
              </el-switch>
            </el-form-item>
            <el-form-item
              v-if="formItemNum == 1 || formItemNum == 3"
              class="slFormItem"
              label="参考基频："
              prop="referenceFrequency"
            >
              <el-input
                class="formInput slItem1"
                type="number"
                v-model="sensorForm.sensorObj.referenceFrequency"
                placeholder="请输入"
                maxlength="30"
                clearable
                ><template slot="append">Hz</template></el-input
              >
              <div style="padding: 0 5px">±</div>
              <el-input
                class="formInput slItem1"
                type="number"
                v-model="sensorForm.sensorObj.floatRange"
                placeholder="请输入"
                maxlength="30"
                clearable
                ><template slot="append">%</template></el-input
              >
            </el-form-item>
            <el-form-item
              v-if="formItemNum == 3"
              label="索长L："
              prop="cableLength"
            >
              <el-input
                class="formInput"
                type="number"
                v-model="sensorForm.sensorObj.cableLength"
                placeholder="请输入"
                maxlength="30"
                clearable
                ><template slot="append">m</template></el-input
              >
            </el-form-item>
            <el-form-item
              v-if="formItemNum == 3"
              label="单位索长："
              prop="unitCableLength"
            >
              <el-input
                class="formInput"
                type="number"
                v-model="sensorForm.sensorObj.unitCableLength"
                placeholder="请输入"
                maxlength="30"
                clearable
                ><template slot="append">kg/m</template></el-input
              >
            </el-form-item>
            <el-form-item label="传感器照片：" style="width: 100%">
              <div class="upload">
                <el-upload
                  ref="uploadPic"
                  action="/bridge/online/sensor"
                  :auto-upload="false"
                  :limit="1"
                  list-type="picture-card"
                  accept=".jpg,.jpeg,.png,.gif"
                  :on-change="handleChange"
                  :on-exceed="handleExceed"
                  :http-request="uploadFile"
                  :file-list="fileList"
                  :class="{
                    hideUpload: fileList.length != 0
                  }"
                >
                  <i slot="default" class="el-icon-plus"></i>
                  <div class="picItem" slot="file" slot-scope="{ file }">
                    <img :src="file.url" alt="" />
                    <span class="el-upload-list__item-actions">
                      <span
                        class="el-upload-list__item-preview"
                        @click="handleLook(file)"
                      >
                        <i class="el-icon-zoom-in"></i>
                      </span>
                      <span
                        class="el-upload-list__item-delete"
                        @click="handleRemove(file)"
                      >
                        <i class="el-icon-delete"></i>
                      </span>
                    </span>
                  </div>
                </el-upload>
              </div>
            </el-form-item>
          </el-form>
        </el-form-item>

        <el-form-item class="warn" label="预警信息">
          <warnSerTable
            :tableData="warnData"
            :titleList="warnTitleList"
            :sensorPrincipleList="sensorPrincipleList"
            @removeSensorDetail="removeSensorDetail"
          ></warnSerTable>
        </el-form-item>
      </el-form>
      <div class="btn">
        <el-button type="primary" @click="sensorSubmit">确定</el-button>
        <el-button @click="sensorDialigClose">取消</el-button>
      </div>
    </el-dialog>
    <!-- 查看图片弹框 -->
    <el-dialog
      class="dialogImage"
      title="传感器图片"
      :visible.sync="dialogImgShow"
      :modal-append-to-body="false"
      :close-on-click-modal="false"
    >
      <div style="max-height: 35vw; overflow: auto">
        <img :src="dialogImageUrl" alt="" />
      </div>
    </el-dialog>
    <!-- 维护记录弹框 -->
    <el-dialog
      class="dialogRecord"
      :title="recordDialogTitle"
      :visible.sync="showRecordDialog"
      append-to-body
      modal-append-to-body
      :close-on-click-modal="false"
    >
      <div class="condition">
        <div>
          <el-button v-if="addOpt" type="primary" @click="showAddDialog(1)"
            >新建记录</el-button
          >
        </div>
        <div class="right">
          <el-date-picker
            class="recordTime"
            v-model="recordTimeValue"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            value-format="yyyy-MM-dd"
            clearable
          >
          </el-date-picker>
          <el-button type="primary" @click="getRecord">查询</el-button>
        </div>
      </div>
      <div class="recordTable">
        <normalTable
          :tableData="recordTableData"
          :titleList="recordTitleList"
          :opList="recordOpList"
          @tableClick="recordTableClick"
        ></normalTable>
      </div>
      <el-pagination
        class="pageNation"
        background
        :current-page.sync="recordNum"
        :page-size="10"
        :pager-count="5"
        layout="total, prev, pager, next"
        :total="recordTotal"
        @current-change="recordHandleCurrentChange"
      ></el-pagination>
    </el-dialog>
    <!-- 新建维护记录弹框 -->
    <el-dialog
      class="addRecordDialog"
      :title="addRecordDialogTitle"
      :visible.sync="showAddRecordDialog"
      :before-close="addRecordDialogClosed"
      append-to-body
      modal-append-to-body
      :close-on-click-modal="false"
    >
      <el-form
        class="recordForm"
        ref="recordForm"
        :model="recordForm"
        :rules="addRecordRules"
      >
        <el-form-item label="操作日期：" prop="operateTime">
          <el-date-picker
            class="formTime"
            v-model="recordForm.operateTime"
            type="datetime"
            placeholder="选择日期"
            value-format="yyyy-MM-dd HH:mm:ss"
            :picker-options="pickerOptions2"
            clearable
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="操作类型：" prop="type">
          <el-input
            class="formInput"
            v-model.trim="recordForm.type"
            placeholder="请输入操作类型"
            clearable
            maxlength="10"
            show-word-limit
          ></el-input>
        </el-form-item>
        <el-form-item label="操作原因：" prop="reason">
          <el-input
            class="formInput"
            v-model.trim="recordForm.reason"
            placeholder="请输入操作原因"
            clearable
            maxlength="10"
            show-word-limit
          ></el-input>
        </el-form-item>
        <el-form-item label="操作内容：" prop="content">
          <el-input
            class="formInput"
            type="textarea"
            v-model.trim="recordForm.content"
            placeholder="请输入操作内容"
            maxlength="250"
            show-word-limit
          ></el-input>
        </el-form-item>
      </el-form>
      <div class="btn">
        <el-button type="primary" @click="addRecordSubmit('recordForm')"
          >确定</el-button
        >
        <el-button @click="showAddRecordDialog = false">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  getSelList,
  getSensorList,
  addSensor,
  updSensor,
  delSensor,
  getRecord,
  addRecord,
  updRecord,
  delRecord
} from '@/api/online/warnSet';
import { getOnlineNotice } from '@/api/login/login';
import { getStructureList, getStructureListByModel } from '@/api/common';
import normalTable from '@/components/table/normalTable';
import warnSerTable from './components/warnSetTable';
export default {
  name: 'warningSetting',
  components: { normalTable, warnSerTable },
  data() {
    const _this = this;
    const validateNumber = (rule, value, callback) => {
      let reg = /^(([1-9]{1}\d*)|(0{1}))(\.\d{0,2})$/;
      let floatRange = String(this.sensorForm.sensorObj.floatRange) || '';
      let val = String(value);
      if (!val) {
        callback(new Error('请输入参考基频'));
      } else if (val.indexOf('.') != -1 && val.split('.').length > 2) {
        callback(new Error('请输入正确的小数格式')); //防止输入多个小数点
      } else if (val.indexOf('.') != -1 && !reg.test(val)) {
        callback(new Error('参考基频只允许保留两位小数')); //小数点后两位
      } else if (floatRange === '') {
        callback(new Error('请输入频率浮动范围'));
      } else if (floatRange.indexOf('-') != -1) {
        callback(new Error('频率浮动范围不允许输入负号'));
      } else if (
        floatRange.indexOf('.') != -1 &&
        floatRange.split('.')[1].length > 2 &&
        _this.formItemNum == 3
      ) {
        callback(new Error('频率浮动范围只允许保留两位小数')); //小数点后两位
      } else {
        callback();
      }
    };
    const validateNumber2 = (rule, value, callback) => {
      let reg = /^(([1-9]{1}\d*)|(0{1}))(\.\d{0,4})$/;
      let val = String(value);
      if (!val) {
        callback(new Error('请输入数值'));
      } else if (val.indexOf('.') != -1 && val.split('.').length > 2) {
        callback(new Error('请输入正确的小数格式')); //防止输入多个小数点
      } else if (val.indexOf('.') != -1 && !reg.test(val)) {
        callback(new Error('请保留四位小数')); //小数点后两位
      } else {
        callback();
      }
    };
    const validateFrequency = (rule, value, callback) => {
      let regular = /^[0-9]+(\/[0-9]+)?$/;
      if (value && !regular.test(value)) {
        callback(new Error('输入格式不正确')); //小数点后两位
      } else if (
        value &&
        value.indexOf('/') != -1 &&
        value.split('/')[0] != 1
      ) {
        callback(new Error('"/"前只能是数字1')); //小数点后两位
      } else {
        callback();
      }
    };
    return {
      selParams: {},
      sensorTypeId: '',
      structureId: '',
      componentId: '',
      sensorTypeList: [],
      structureList: [],
      componentList: [],
      pageNum: 1,
      dataTotal: 0,
      titleList: [
        { id: 2, prop: 'structureName', label: '结构物', width: '' },
        { id: 3, prop: 'name', label: '测点编号', width: '' },
        { id: 4, prop: 'sensorTypeName', label: '传感器类型', width: '' },
        { id: 5, prop: 'componentName', label: '监测位置', width: '' }
        // { id: 6, prop: 'unit', label: '单位', width: '' }
      ],
      tableData: [], // 传感器设置列表
      opList: [
        { id: 1, name: '修改', opShow: false, type: 'updateOpt' },
        {
          id: 2,
          name: '维护记录',
          opShow: false,
          type: 'checkOpt'
        },
        {
          id: 3,
          name: '删除',
          opShow: false,
          type: 'deleteOpt'
        }
      ],
      //新增、修改弹框
      sensorDialogTitle: '',
      showSensorDialog: false,
      pointrRules: {
        structureId: [
          { required: true, message: '请选择结构物', trigger: 'change' }
        ],
        name: [{ required: true, message: '请输入测点编号', trigger: 'blur' }],
        describe: [
          { required: true, message: '请输入测点编号说明', trigger: 'blur' }
        ],
        deploymentDate: [
          {
            required: true,
            message: '请选择部署日期',
            trigger: 'change'
          }
        ],
        componentId: [
          { required: true, message: '请选择结构位置', trigger: 'change' }
        ],
        sectionPosition: [
          { required: true, message: '请输入截面位置', trigger: 'blur' }
        ],
        collectorInformation: [
          { required: true, message: '请输入采集器信息', trigger: 'blur' }
        ],
        sensorDetailsId: [
          { required: true, message: '请选择传感器细项', trigger: 'change' }
        ]
        // sensorCoding: [
        //   { required: true, message: '请输入数据读取编码', trigger: 'blur' }
        // ]
        // value9: [{ required: true, message: '请输入坐标', trigger: 'blur' }]
      },
      warnRules: {
        unit: [{ required: true, message: '请输入单位', trigger: 'blur' }],
        initialValue: [
          { required: true, message: '请输入初始值', trigger: 'blur' }
        ],
        warningInterval: [
          { required: true, message: '请输入预警间隔', trigger: 'blur' }
        ],
        firstWarningUpper: [
          { required: true, message: '请输入一级预警上限', trigger: 'blur' }
        ],
        secondWarningUpper: [
          { required: true, message: '请输入二级预警上限', trigger: 'blur' }
        ],
        thirdWarningUpper: [
          { required: true, message: '请输入三级预警上限', trigger: 'blur' }
        ],
        firstWarningLower: [
          { required: true, message: '请输入一级预警下限', trigger: 'blur' }
        ],
        secondWarningLower: [
          { required: true, message: '请输入二级预警下限', trigger: 'blur' }
        ],
        thirdWarningLower: [
          { required: true, message: '请输入三级预警下限', trigger: 'blur' }
        ]
      },
      sensorRules: {
        companyId: [
          { required: true, message: '请选择传感器厂商', trigger: 'change' }
        ],
        sensorTypeId: [
          { required: true, message: '请选择传感器类型', trigger: 'change' }
        ],
        samplingFrequency: [
          {
            // required: true,
            validator: validateFrequency,
            // message: '请输入采样频率',
            trigger: 'blur'
          }
        ],
        productionDate: [
          { required: true, message: '请选择出厂日期', trigger: 'change' }
        ],
        productionCoding: [
          { required: true, message: '请输入出厂编号', trigger: 'blur' }
        ],
        status: [{ required: true, message: '请选择', trigger: 'change' }],
        referenceFrequency: [
          {
            required: true,
            // message: '请输入参考基频',
            trigger: 'blur',
            validator: validateNumber
          }
        ],
        cableLength: [
          {
            required: true,
            // message: '请输入索长L',
            trigger: 'blur',
            validator: validateNumber2
          }
        ],
        unitCableLength: [
          {
            required: true,
            // message: '请输入单位索长',
            trigger: 'blur',
            validator: validateNumber2
          }
        ]
      },
      sensorForm: {
        pointObj: {
          sensorDetailsId: []
        },
        sensorObj: {
          statusValue: 0,
          status: 0,
          floatRange: 0
        }
      },
      pointList: [
        //type: 1、输入框 2、下拉框 3、多个下拉框
        {
          id: 1,
          lebel: '结构物：',
          prop: 'structureId',
          type: 2,
          list: []
        },
        { id: 2, lebel: '测点编号：', prop: 'name', type: 1 },
        { id: 3, lebel: '测点编号说明：', prop: 'describe', type: 1 },
        { id: 4, lebel: '部署日期：', prop: 'deploymentDate', type: 3 },
        {
          id: 5,
          lebel: '监测位置：',
          prop: 'componentId',
          type: 2,
          list: []
        },
        { id: 6, lebel: '截面位置：', prop: 'sectionPosition', type: 1 },
        { id: 7, lebel: '采集器信息：', prop: 'collectorInformation', type: 1 },

        {
          id: 8,
          lebel: '传感器细项：',
          prop: 'sensorDetailsId',
          type: 5,
          list: []
        }
        // { id: 8, lebel: '数据读取编码：', prop: 'sensorCoding', type: 1 }
        // { id: 9, lebel: 'XYZ坐标：', prop: 'value9', type: 3 }
      ],
      sensorList: [
        //type: 1、输入框 2、下拉框 3、数字输入框
        {
          id: 1,
          lebel: '传感器厂商：',
          prop: 'companyId',
          type: 2,
          list: []
        },
        {
          id: 2,
          lebel: '传感器类型：',
          prop: 'sensorTypeId',
          type: 2,
          list: []
        },
        { id: 4, lebel: '采样频率：', prop: 'samplingFrequency', type: 1 },
        {
          id: 5,
          lebel: '出厂日期：',
          prop: 'productionDate',
          type: 4
        },
        { id: 6, lebel: '出厂编号：', prop: 'productionCoding', type: 1 },
        // {
        //   id: 7,
        //   lebel: '传感器原理：',
        //   prop: 'sensorPrincipleId',
        //   type: 2,
        //   list: []
        // },
        {
          id: 8,
          lebel: '是否故障：',
          prop: 'status',
          type: 3,
          list: []
        }
      ],
      warnTitleList: [
        { id: 1, label: '细项', prop: 'name', type: 1, disabled: true },
        { id: 2, label: '单位', prop: 'unit', type: 1, disabled: true },
        { id: 3, label: '默认精度', prop: 'precision', type: 2, maxlangth: 8 },
        { id: 4, label: '初始值', prop: 'initialValue', type: 2, maxlangth: 8 },
        {
          id: 5,
          label: '预警间隔',
          prop: 'warningInterval',
          type: 2,
          maxlangth: 8
        },
        { id: 6, label: '数据读取编码', prop: 'sensorCoding', type: 1 },
        { id: 7, label: '传感器原理', prop: 'sensorPrincipleId', type: 3 }
      ],
      formItemNum: '',
      warnData: [],
      warnOriData: [],
      sensorPrincipleList: [], //传感器原理列表
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        }
      },
      pickerOptions2: {
        disabledDate(time) {
          return time.getTime() < Date.now() - 24 * 3600 * 1000;
        }
      },
      fileList: [],
      headers: {
        'X-AUTH-TOKEN': this.$store.getters.getToken,
        'X-ROUTER-URL': '/infoManage/bridgeManage'
      },
      //查看图片弹框
      dialogImageUrl: '',
      dialogImgShow: false,
      //维护记录弹框
      recordDialogTitle: '',
      showRecordDialog: false,
      recordTitleList: [
        { id: 2, prop: 'operateTime', label: '操作日期', width: '' },
        { id: 3, prop: 'type', label: '操作类型', width: '' },
        { id: 4, prop: 'reason', label: '操作原因', width: '' },
        { id: 5, prop: 'content', label: '操作内容', width: '' }
      ],
      recordTableData: [],
      recordOpList: [
        {
          id: 1,
          name: '修改',
          opShow: false,
          type: 'updateOpt'
        },
        {
          id: 2,
          name: '删除',
          opShow: false,
          type: 'deleteOpt'
        }
      ],
      recordNum: 1,
      recordTotal: 0,
      recordTimeValue: '',
      //新建维护记录弹框
      addRecordDialogTitle: '',
      showAddRecordDialog: false,
      recordForm: {
        meansPointId: '',
        operateTime: '',
        type: '',
        reason: '',
        content: ''
      },
      addRecordRules: {
        operateTime: {
          required: true,
          message: '请选择操作日期',
          trigger: 'change'
        },
        type: { required: true, message: '请输入操作类型', trigger: 'blur' },
        reason: { required: true, message: '请输入操作原因', trigger: 'blur' },
        content: { required: true, message: '请输入操作内容', trigger: 'blur' }
      }
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
    if (this.updateOpt) {
      this.recordOpList[0].opShow = true;
    }
    if (this.deleteOpt) {
      this.recordOpList[1].opShow = true;
    }
    this.getStructureList(); //获取结构物
    this.getStructureListByModel(); //获取有传感器的结构物
    this.getSelList(); //获取各选择列表
    this.getSensorList(); //获取传感器列表
    this.getOnlineNotice();
  },
  methods: {
    //获取各选择列表
    async getSelList() {
      let { code, data } = await getSelList();
      if (code == '0000') {
        data.companyList.map((item) => {
          item.label = item.company;
        });
        data.componentInfoList.map((item) => {
          item.label = item.name;
        });
        data.sensorPrincipleList.map((item) => {
          item.label = item.principle;
        });
        data.sensorTypeList.map((item) => {
          item.label = item.name;
        });
        data.sensorDetailsList.map((item) => {
          item.label = item.detailName;
        });
        this.componentList = data.componentInfoList;
        this.sensorTypeList = data.sensorTypeList;
        this.pointList[4].list = data.componentInfoList;
        this.pointList[7].list = data.sensorDetailsList;
        this.sensorList[1].list = data.sensorTypeList;
        this.sensorList[0].list = data.companyList;
        // this.sensorList[6].list = data.sensorPrincipleList;
        this.sensorPrincipleList = data.sensorPrincipleList;
      }
    },
    //根据传感器类型获取细项
    async getDetailList() {
      let arry = [];
      let ids = this.sensorForm.pointObj.sensorDetailsId;
      this.pointList[7].list.map((item) => {
        ids.map((val) => {
          if (item.id == val) {
            let obj = {
              sensorDetailsId: val,
              name: item.detailName,
              unit: item.unit,
              precision: item.precision,
              initialValue: '',
              warningInterval: '',
              sensorCoding: '',
              sensorPrincipleId: '',
              firstWarningUpper: '',
              firstWarningLower: '',
              secondWarningUpper: '',
              secondWarningLower: '',
              thirdWarningUpper: '',
              thirdWarningLower: '',
              submit: true
            };
            arry.push(obj);
          }
        });
      });
      arry.map((item, i) => {
        this.warnData.map((child) => {
          if (item.sensorDetailsId === child.sensorDetailsId) {
            this.$set(arry, i, { ...child });
          }
        });
        this.warnOriData.map((child) => {
          if (item.sensorDetailsId === child.sensorDetailsId) {
            this.$set(arry, i, { ...child });
          }
        });
      });
      this.warnData = arry;
    },
    //移除细项
    removeSensorDetail(id) {
      let ids = this.sensorForm.pointObj.sensorDetailsId;
      ids.splice(
        ids.findIndex((vId) => vId == id),
        1
      );
    },
    //获取结构物列表
    async getStructureList() {
      let params = { powerId: this.$store.getters.getActiveIndex };
      let { code, data } = await getStructureList(params);
      if (code == '0000') {
        data.map((item) => {
          item.label = item.name;
        });
        // this.structureList = data;
        this.pointList[0].list = data;
      }
    },
    //获取有传感器的结构物
    async getStructureListByModel() {
      let params = { powerId: this.$store.getters.getActiveIndex };
      let { code, data } = await getStructureListByModel(params);
      if (code == '0000') {
        this.structureList = data;
      }
    },
    //获取传感器列表
    async getSensorList() {
      let params = {
        sensorTypeId: this.sensorTypeId,
        structureId: this.structureId,
        componentId: this.componentId,
        pageNum: 1,
        pageSize: 10
      };
      let { code, data } = await getSensorList(params);
      if (code == '0000') {
        this.pageNum = 1;
        this.tableData = data.list;
        this.dataTotal = data.total;
        this.selParams = params;
      }
    },
    async getSensorList2() {
      let { code, data } = await getSensorList(this.selParams);
      if (code == '0000') {
        this.tableData = data.list;
        this.dataTotal = data.total;
      }
    },
    //获取维护记录
    async getRecord() {
      let params = {
        meansPointId: this.recordForm.meansPointId,
        startTime: !!this.recordTimeValue
          ? this.recordTimeValue[0] + ' 00:00:00'
          : '',
        endTime: !!this.recordTimeValue
          ? this.recordTimeValue[1] + ' 23:59:59'
          : '',
        pageNum: this.recordNum
      };
      let { code, data } = await getRecord(params);
      if (code == '0000') {
        this.recordParams = { ...params };
        this.recordTableData = data.list;
        this.recordTotal = data.total;
        this.showRecordDialog = true;
      }
    },
    async getRecord2() {
      let { code, data } = await getRecord(this.recordParams);
      if (code == '0000') {
        this.recordTableData = data.list;
        this.recordTotal = data.total;
      }
    },
    //打开新建、修改预警弹框
    showDialog(index) {
      if (index == 1) {
        this.sensorDialogTitle = '新建传感器';
      } else {
        this.sensorDialogTitle = '修改传感器';
      }
      this.showSensorDialog = true;
    },
    //打开新建、修改维修记录弹框
    showAddDialog(index) {
      if (index == 1) {
        this.addRecordDialogTitle = '新建维修记录';
      } else {
        this.addRecordDialogTitle = '修改维修记录';
      }
      this.showAddRecordDialog = true;
    },
    //关闭新建、修改预警弹弹框
    sensorDialigClose() {
      this.showSensorDialog = false;
      this.sensorForm = {
        pointObj: { sensorDetailsId: [] },
        sensorObj: { statusValue: 0, status: 0, floatRange: 0 }
      };
      this.warnData = [];
      this.fileList = [];
      this.warnOriData = [];
      this.$nextTick(() => {
        this.$refs.pointObj.clearValidate();
        // this.$refs.warnObj.clearValidate();
        this.$refs.sensorObj.clearValidate();
      });
    },
    //关闭新建、修改维修记录弹框
    addRecordDialogClosed() {
      let meansPointId = this.recordForm.meansPointId;
      this.recordForm = {
        meansPointId,
        operateTime: '',
        type: '',
        reason: '',
        content: ''
      };
      this.$nextTick(() => {
        this.$refs.recordForm.clearValidate();
        this.showAddRecordDialog = false;
      });
    },
    //提交新建、修改预警
    async sensorSubmit() {
      let reg = /^(\-?)\d+(\.\d+)?$/; //验证坐标
      let formNameList = ['pointObj', 'sensorObj'];
      let falseNum = 0;
      // console.log(this.sensorForm.sensorObj, 1111111111);
      for (let i = 0; i < formNameList.length; i++) {
        if (!this.confirmForm(formNameList[i])) {
          falseNum++;
        }
      }
      if (falseNum >= 1) {
        return;
      }
      if (!reg.test(this.sensorForm.pointObj.xAxis)) {
        this.$message({
          message: '请输入正确的X坐标',
          type: 'warning',
          showClose: true,
          duration: 2000
        });
        return;
      }
      if (!reg.test(this.sensorForm.pointObj.yAxis)) {
        this.$message({
          message: '请输入正确的Y坐标',
          type: 'warning',
          showClose: true,
          duration: 2000
        });
        return;
      }
      if (!reg.test(this.sensorForm.pointObj.zAxis)) {
        this.$message({
          message: '请输入正确的Z坐标',
          type: 'warning',
          showClose: true,
          duration: 2000
        });
        return;
      }
      if (!this.warnConfirm()) {
        return;
      }
      this.warnData.map((item) => {
        //预警信息判断读取编码是否重复
        this.warnOriData.map((child) => {
          if (!item.id && item.sensorCoding == child.sensorCoding) {
            item.id = child.id;
            item.meansPointId = child.meansPointId;
          }
        });
      });
      let params = {
        ...this.sensorForm.pointObj,
        ...this.sensorForm.sensorObj,
        sensorVOList: this.warnData
      };
      this.fileData = new FormData();
      this.$refs.uploadPic.submit();
      this.fileData.append('params', JSON.stringify(params));
      if (this.sensorDialogTitle == '新建传感器') {
        let { code } = await addSensor(this.fileData);
        if (code == '0000') {
          this.$message({
            message: '添加成功！',
            type: 'success',
            showClose: true,
            duration: 2000
          });
          await this.sensorDialigClose();
          await this.getStructureListByModel();
          await this.getSensorList();
        }
      } else {
        let { code } = await updSensor(this.fileData, params.id);
        if (code == '0000') {
          this.$message({
            message: '修改成功！',
            type: 'success',
            showClose: true,
            duration: 2000
          });
          await this.sensorDialigClose();
          await this.getSensorList2();
          await this.getOnlineNotice();
        }
      }
    },
    //表单验证
    confirmForm(formName) {
      let state = true;
      this.$refs[formName].validate((valid) => {
        if (valid) {
          state = true;
        } else {
          state = false;
        }
      });
      return state;
    },
    //预警信息验证
    warnConfirm() {
      let state = true;
      this.warnData.map((item) => {
        if (!item.submit) {
          state = false;
          return;
        }
        if (item.initialValue === '') {
          this.$message({
            message: `${item.name}的初始值不能为空！`,
            type: 'warning',
            showClose: true,
            duration: 2000
          });
          state = false;
        } else if (item.unit === '') {
          this.$message({
            message: `${item.name}的单位不能为空！`,
            type: 'warning',
            showClose: true,
            duration: 2000
          });
          state = false;
        } else if (item.precision === '' || item.precision === null) {
          this.$message({
            message: `${item.name}的默认精度不能为空！`,
            type: 'warning',
            showClose: true,
            duration: 2000
          });
          state = false;
        } else if (item.warningInterval === '') {
          this.$message({
            message: `${item.name}的预警间隔不能为空！`,
            type: 'warning',
            showClose: true,
            duration: 2000
          });
          state = false;
        } else if (item.sensorCoding === '') {
          this.$message({
            message: `${item.name}的数据读取编码不能为空！`,
            type: 'warning',
            showClose: true,
            duration: 2000
          });
          state = false;
        } else if (item.sensorPrincipleId === '') {
          this.$message({
            message: `${item.name}的传感器原理不能为空！`,
            type: 'warning',
            showClose: true,
            duration: 2000
          });
          state = false;
        }

        if (
          item.firstWarningUpper !== '' &&
          item.firstWarningUpper !== null &&
          item.firstWarningLower !== '' &&
          item.firstWarningLower !== null &&
          item.firstWarningUpper <= item.firstWarningLower
        ) {
          this.$message({
            message: `${item.name}的一级预警上限要大于下限！`,
            type: 'warning',
            showClose: true,
            duration: 2000
          });
          state = false;
        }
        if (
          item.secondWarningUpper !== '' &&
          item.secondWarningUpper !== null &&
          item.secondWarningLower !== '' &&
          item.secondWarningLower !== null &&
          item.secondWarningUpper <= item.secondWarningLower
        ) {
          this.$message({
            message: `${item.name}的二级预警上限要大于下限！`,
            type: 'warning',
            showClose: true,
            duration: 2000
          });
          state = false;
        }
        if (
          item.thirdWarningUpper !== '' &&
          item.thirdWarningUpper !== null &&
          item.thirdWarningLower !== '' &&
          item.thirdWarningLower !== null &&
          item.thirdWarningUpper <= item.thirdWarningLower
        ) {
          this.$message({
            message: `${item.name}的三级预警上限要大于下限！`,
            type: 'warning',
            showClose: true,
            duration: 2000
          });
          state = false;
        }

        if (
          item.firstWarningUpper !== '' &&
          item.firstWarningUpper !== null &&
          item.secondWarningUpper !== '' &&
          item.secondWarningUpper !== null &&
          item.thirdWarningUpper !== '' &&
          item.thirdWarningUpper !== null
        ) {
          if (
            item.firstWarningUpper < item.secondWarningUpper ||
            item.secondWarningUpper < item.thirdWarningUpper
          ) {
            this.$message({
              message: `${item.name}的预警上限应为一级>二级>三级！`,
              type: 'warning',
              showClose: true,
              duration: 2000
            });
            state = false;
          }
        }

        if (
          item.firstWarningLower !== '' &&
          item.firstWarningLower !== null &&
          item.secondWarningLower !== '' &&
          item.secondWarningLower !== null &&
          item.thirdWarningLower !== '' &&
          item.thirdWarningLower !== null
        ) {
          if (
            item.firstWarningLower > item.secondWarningLower ||
            item.secondWarningLower > item.thirdWarningLower
          ) {
            this.$message({
              message: `${item.name}的预警下限应为一级<二级<三级！`,
              type: 'warning',
              showClose: true,
              duration: 2000
            });
            state = false;
          }
        }
      });
      return state;
    },
    //提交新建维护记录
    addRecordSubmit(formName) {
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          if (this.addRecordDialogTitle == '新建维修记录') {
            let { code } = await addRecord(this.recordForm);
            if (code == '0000') {
              this.$message({
                message: '新增记录成功！',
                type: 'success',
                showClose: true,
                duration: 2000
              });
              this.addRecordDialogClosed();
              await this.getRecord2();
            }
          } else {
            let { code } = await updRecord(this.recordForm);
            if (code == '0000') {
              this.$message({
                message: '修改记录成功！',
                type: 'success',
                showClose: true,
                duration: 2000
              });
              this.addRecordDialogClosed();
              await this.getRecord2();
            }
          }
        } else {
          return;
        }
      });
    },
    //传感器类型选择
    typeChange(index) {
      if (index == 2) {
        this.formItemNum = this.sensorForm.sensorObj.sensorTypeId;
      }
    },
    //改变文件
    handleChange(file, fileList) {
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
    //查看图片
    handleLook(file) {
      this.dialogImageUrl = file.url;
      this.dialogImgShow = true;
    },
    //移除文件
    handleRemove(file, fileList) {
      this.fileList = [];
    },
    //上传文件
    uploadFile(file) {
      this.fileData.append('file', file.file); // append增加数据
    },
    //传感器列表点击
    tableClick(index, data) {
      if (index == 1) {
        let obj1 = {};
        let obj2 = {};
        let obj3 = {};
        for (let item in data) {
          for (let key in this.pointrRules) {
            if (item == key) obj1[key] = data[key];
          }
          for (let key in this.warnRules) {
            if (item == key) obj2[key] = data[key];
          }
          for (let key in this.sensorRules) {
            if (item == key) obj3[key] = data[key];
          }
        }
        let arry = [];
        data.sensorVOList.map((item) => {
          item.submit = true;
          arry.push(item.sensorDetailsId);
        });
        obj1.xAxis = data.xAxis;
        obj1.yAxis = data.yAxis;
        obj1.zAxis = data.zAxis;
        obj1.sensorDetailsId = arry;
        obj3.statusValue = data.status == 1 ? 1 : 0;
        obj3.floatRange = data.floatRange;
        obj3.photoUrl = data.photoUrl;
        this.sensorForm = {
          pointObj: { ...obj1 },
          sensorObj: { ...obj3 }
        };
        this.warnData = JSON.parse(JSON.stringify(data.sensorVOList));
        this.warnOriData = JSON.parse(JSON.stringify(data.sensorVOList));
        this.sensorForm.pointObj.id = data.id;
        this.fileList = !!data.photoUrl
          ? [{ url: this.$basePath + data.photoUrl }]
          : [];
        this.sensorDialogTitle = '修改传感器';
        this.formItemNum = data.sensorTypeId;
        this.showSensorDialog = true;
      } else if (index == 2) {
        this.recordForm.meansPointId = data.id;
        this.recordDialogTitle = data.sensorTypeName + '-' + data.name;
        this.getRecord(data); //获取维护记录
        this.showRecordDialog = true;
      } else if (index == 3) {
        this.deleteItem(data.id);
      }
    },
    //维护记录点击
    recordTableClick(index, data) {
      if (index == 1) {
        this.showAddDialog(2);
        this.recordForm = { ...this.recordForm, ...data };
      } else if (index == 2) {
        this.deleteRecord(data.id);
      }
    },
    //删除传感器
    deleteItem(id) {
      //删除框
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        showClose: true
      })
        .then(async () => {
          let { code } = await delSensor(id);
          if (code == '0000') {
            this.$message({
              type: 'success',
              message: '删除成功!',
              showClose: true,
              duration: 2000
            });
            await this.getSensorList2();
            await this.getOnlineNotice();
          }
        })
        .catch(() => {
          return false;
        });
    },
    //获取预警数量、传感器数量
    async getOnlineNotice() {
      let { code, data } = await getOnlineNotice();
      if (code == '0000') {
        this.$store.dispatch('asyncUpdateDotWarn', data.warningCount); //预警管理红点
        this.$store.dispatch('asyncUpdateDotSensor', data.offlineCount); //传感器设置红点
      }
    },
    //删除维护记录
    deleteRecord(id) {
      //删除框
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        showClose: true
      })
        .then(async () => {
          let { code } = await delRecord(id);
          if (code == '0000') {
            this.$message({
              type: 'success',
              message: '删除成功!',
              showClose: true,
              duration: 2000
            });
            await this.getRecord2();
          }
        })
        .catch(() => {
          return false;
        });
    },
    handleCurrentChange(val) {
      this.pageNum = val;
      this.selParams.pageNum = val;
      this.getSensorList2();
    },
    recordHandleCurrentChange(val) {
      this.recordNum = val;
      this.recordParams.pageNum = val;
      this.getRecord2();
    }
  }
};
</script>

<style lang="scss" scoped>
.warnSet {
  padding: 24px;
  display: flex;
  flex-direction: column;
  .title {
    font-size: 16px;
    color: #262626;
  }
  .boxTop {
    padding-bottom: 20px;
    display: flex;
    justify-content: space-between;

    .pad {
      margin-left: 20px;
    }
  }
  .song-table-1 {
    /deep/ .el-table th {
      color: #333;
      font-weight: bold;
    }
    /deep/ .el-table td {
      padding: 1.668vh 0;
    }
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
}
//预警弹框
.sensorDialog {
  .point,
  .warn,
  .sensor {
    margin: 0;
    display: flex;
    flex-direction: column;
    /deep/ .el-form-item__label {
      color: #419aff;
      text-align: left;
      line-height: 20px;
    }
  }
  .warn {
    /deep/ .el-table__body-wrapper {
      max-height: 18.519vh;
      overflow-y: auto;
      &::-webkit-scrollbar {
        width: 6px;
      }
      &:hover {
        &::-webkit-scrollbar-thumb {
          background: #c4c4c4;
          border-radius: 6px;
        }
      }
    }
  }
  .pointBox,
  .warnBox,
  .sensorBox {
    padding: 0 20px;
    display: flex;
    flex-wrap: wrap;
    .position {
      width: 316px;
      display: flex;
      align-items: center;
      justify-content: space-between;
      /deep/ .el-input {
        width: 30%;
      }
      /deep/ .el-input__inner {
        width: 100%;
      }
    }
    .slFormItem {
      /deep/ .el-form-item__content {
        display: flex;
        justify-content: space-between;
      }
    }
    .upload {
      .hideUpload {
        /deep/ .el-upload--picture-card {
          display: none;
        }
      }
      img {
        max-height: 120px;
      }
      .picItem,
      /deep/ .el-upload-list {
        display: flex;
        align-items: center;
        justify-content: center;
      }
      div:first-child {
        height: 100%;
        width: 100%;
        display: flex;
        align-items: center;
      }
      /deep/ .el-upload-list__item {
        height: 100%;
        margin: 0;
      }
      /deep/ .el-upload--picture-card {
        width: 155px;
        height: 88px;
        background: transparent;
        border: 1px dashed rgba(0, 0, 0, 0.15);
        border-radius: 4px;
        display: flex;
        align-items: center;
        justify-content: center;
      }
    }
    /deep/ .el-form-item {
      width: 33.33%;
      padding: 1.112vh 0;
      display: flex;
      align-items: center;
    }
    /deep/ .el-form-item__label {
      width: 125px;
      color: #333;
      text-align: right;
    }
    /deep/ .el-form-item__content {
      line-height: unset;
    }
    /deep/ .el-input-group__append {
      padding: 0 10px;
      color: #000;
      background: transparent;
    }
  }
  .formTime,
  .formInput {
    width: 316px;
    // height: 3.705vh;
    /deep/ .el-input {
      // height: 100%;
      display: flex;
      align-items: center;
    }
    /deep/ .el-input__inner {
      // height: 100%;
      line-height: unset;
    }
    /deep/ .el-input__suffix {
      display: flex;
      align-items: center;
    }
  }
  .slItem1 {
    width: 148px;
  }
  .poInput {
    height: 3.705vh;
    /deep/ .el-input__inner {
      height: 100%;
      line-height: unset;
    }
  }
  .btn {
    padding-top: 10px;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  /deep/ .el-dialog {
    width: 1442px;
  }
  /deep/ .el-dialog__body {
    padding: 20px;
    padding-top: 0;
    display: flex;
    flex-direction: column;
  }
}
//查看图片弹框
.dialogImage {
  img {
    height: 100%;
    width: 100%;
  }
  /deep/ .el-dialog {
    margin: 0;
  }
  /deep/ .el-dialog__body {
    padding: 1.2vw !important;
  }
}
//维护记录弹框
.dialogRecord {
  .condition {
    display: flex;
    justify-content: space-between;
    .right {
      display: flex;
      .recordTime {
        width: 282px;
        margin-right: 20px;
      }
      /deep/ .el-date-editor .el-range-separator {
        width: unset;
      }
    }
  }
  .recordTable {
    padding: 20px 0;
    /deep/ .el-table td {
      padding: 1.019vh 0;
    }
  }
  .pageNation {
    text-align: center;
    padding-top: 0;
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
  /deep/ .el-dialog {
    width: 1092px;
  }
  /deep/ .el-dialog__body {
    padding: 20px;
    display: flex;
    flex-direction: column;
  }
}
//新建维护记录弹框
.addRecordDialog {
  .recordForm {
    display: flex;
    flex-direction: column;
    .formTime,
    .formInput {
      width: 316px;
    }
    /deep/ .el-form-item {
      display: flex;
      justify-content: center;
    }
    /deep/ .el-textarea__inner {
      min-height: 50px !important;
    }
  }
  .btn {
    padding-top: 20px;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  /deep/ .el-dialog {
    width: 520px;
  }
  /deep/ .el-dialog__body {
    padding: 20px;
    display: flex;
    flex-direction: column;
  }
}
</style>
