<template>
  <el-dialog
    class="diaryDialog"
    :title="dialogTitle"
    :visible.sync="dialogShow"
    :append-to-body="true"
    center
    :close-on-click-modal="false"
  >
    <el-form ref="model" :model="model" :rules="rules" :disabled="isRead">
      <el-form-item label="项目名称：" prop="projectId">
        <el-select v-model="model.projectId" placeholder="请选择项目">
          <el-option
            v-for="item in projectList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="维护时间：" prop="maintainTime">
        <el-date-picker
          type="date"
          placeholder="选择时间"
          v-model="model.maintainTime"
          value-format="yyyy-MM-dd"
        ></el-date-picker>
      </el-form-item>

      <el-form-item label="日志表述：" prop="describe">
        <el-input
          type="textarea"
          :rows="2"
          v-model="model.describe"
          :maxlength="250"
          placeholder="250字以内"
        ></el-input>
      </el-form-item>

      <el-form-item label="解决方案：" prop="solution">
        <el-input
          type="textarea"
          :rows="2"
          v-model="model.solution"
          :maxlength="250"
          placeholder="250字以内"
        ></el-input>
      </el-form-item>

      <el-form-item label="备注：">
        <el-input
          type="textarea"
          :rows="2"
          v-model="model.remarks"
          :maxlength="250"
          placeholder="250字以内"
        ></el-input>
      </el-form-item>
    </el-form>
    <div v-show="!isRead" class="btn">
      <span class="keep" @click="confirmBtn('model')">确定</span>
      <span class="cancel" @click="cancelBtn">取消</span>
    </div>
  </el-dialog>
</template>
<script>
import { getProjectListByPowerId } from '@/api/common';
export default {
  props: {
    dialogVisible: {
      type: Boolean,
      default: false
    }, //模态框显示隐藏
    birdgeItem: {
      type: Object,
      default: function () {
        return {}; // 编辑页面详情
      }
    },
    dialogState: {
      type: String,
      default: ''
    },
    editId: {
      type: Number,
      default: 0
    }
  },
  data() {
    return {
      dialogShow: this.dialogVisible,
      model: {
        projectId: '',
        maintainTime: '', //时间
        describe: '', //日志表述
        solution: '', //解决方案
        remarks: '' //备注
      },
      projectList: [],
      isRead: false,
      // 表单验证
      rules: {
        maintainTime: [
          {
            required: true,
            message: '时间不能为空',
            trigger: 'change'
          }
        ],
        projectId: [
          {
            required: true,
            message: '项目不能为空',
            trigger: 'change'
          }
        ],
        describe: [
          { required: true, message: '日志表述不能为空', trigger: 'blur' }
        ],
        solution: [
          { required: true, message: '解决方案不能为空', trigger: 'blur' }
        ]
      }
    };
  },
  computed: {
    //标题判断
    dialogTitle() {
      if (this.dialogState === 'add') {
        return '新增日志';
      } else if (this.dialogState === 'edit') {
        return '修改日志';
      } else {
        return '查看日志';
      }
    }
  },
  //监听进入为添加页面还是编辑页面，是编辑页面就渲染数据，添加页面就初始化
  watch: {
    dialogVisible() {
      if (this.dialogVisible) {
        if (this.dialogState === 'add') {
          this.model = {
            projectId: '',
            maintainTime: '', //时间
            describe: '', //日志表述
            solution: '', //解决方案
            remarks: '' //备注
          };
        } else {
          this.model = {
            id: this.birdgeItem.id,
            maintainTime: this.birdgeItem.maintainTime.substring(0, 10), //时间
            describe: this.birdgeItem.describe, //日志表述
            // inspectionDate: [this.birdgeItem.inspectionDate,this.birdgeItem.remarks], //检查时间
            solution: this.birdgeItem.solution, //解决方案
            remarks: this.birdgeItem.remarks, //备注
            projectId: this.birdgeItem.projectId
          };
          if (this.dialogState === 'read') {
            this.isRead = true;
          }
        }
        //移除表单项的校验结果
        this.$nextTick(() => {
          this.$refs.model.clearValidate();
        });
      }
      this.dialogShow = this.dialogVisible;
    },
    dialogShow() {
      if (!this.dialogShow) {
        this.isRead = false;
        this.$emit('cancelBtn');
      }
    }
  },
  mounted() {
    this.getProjectListByPowerId(); //查询新增、修改项目列表
  },
  methods: {
    //查询新增、修改项目列表
    async getProjectListByPowerId() {
      let id = this.$store.getters.getActiveIndex;
      let { code, data } = await getProjectListByPowerId(id);
      if (code == '0000') {
        this.projectList = data;
      }
    },
    cancelBtn() {
      this.dialogShow = false;
    },
    // 保存按钮
    confirmBtn(formName) {
      //对整个表单进行验证
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.saveData();
        } else {
          return false;
        }
      });
    },
    // 保存数据
    saveData() {
      var flag = true;
      var reg = /^(?! +$).*$/;
      if (reg.test(this.model.solution) == false) {
        this.$message({
          type: 'warning',
          message: '解决方案不能全是空格',
          showClose: true
        });
        flag = false;
        return;
      }
      if (flag) {
        let saveData = {
          id: this.model.id || '',
          maintainTime: this.model.maintainTime,
          describe: this.model.describe,
          solution: this.model.solution,
          remarks: this.model.remarks,
          projectId: this.model.projectId
          // inspectorName: this.model.inspectorName
        };

        this.$emit('saveBtn', saveData);
      }
    }
  }
};
</script>
<style scoped lang="scss">
/* .el-date-editor.el-input, .el-date-editor.el-input__inner {
    width: 100%;
} */
.diaryDialog {
  .el-form-item {
    display: flex;
    align-items: center;
    justify-content: flex-end;
    /deep/ .el-form-item__label {
      padding-right: 5px;
    }
    /deep/ .el-textarea,
    /deep/ .el-date-editor,
    /deep/ .el-input {
      width: 316px;
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
</style>
