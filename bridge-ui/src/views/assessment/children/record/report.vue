<template>
  <div class="report">
    <el-table :data="tableData">
      <el-table-column
        type="index"
        label="序号"
        align="center"
        width="80"
      ></el-table-column>
      <el-table-column
        v-for="item in tableTitleList"
        :key="item.id"
        :prop="item.prop"
        :label="item.label"
        :width="item.width"
        align="center"
      >
      </el-table-column>
      <el-table-column label="BCI评价" align="center">
        <template slot-scope="scope">
          <div v-if="scope.row.bridgeConditionIndex === 0" class="option">
            <span class="dotRed"></span><span>未完成</span>
          </div>
          <div v-else class="option">
            <span class="dotBlue"></span><span>已完成</span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <div class="option">
            <div
              :class="{ disabled: scope.row.bridgeConditionIndex == 0 }"
              @click="generate(scope.row)"
            >
              生成报告
            </div>
            <div
              :class="{
                disabled:
                  scope.row.reportPath === null ||
                  scope.row.bridgeConditionIndex == 0
              }"
              @click="downLoad(scope.row)"
            >
              下载报告
            </div>
          </div>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog
      class="genReport"
      title="生成报告"
      width="25.521vw"
      :visible.sync="showDialog"
      :modal-append-to-body="false"
      @close="dialogClose"
      :close-on-click-modal="false"
    >
      <div class="diaItem">
        <span>
          <p style="color: #ff5f5f">*</p>
          检测依据：
        </span>
        <el-select v-model="basisValue" placeholder="请选择">
          <el-option
            v-for="item in basisList"
            :key="item.id"
            :label="item.label"
            :value="item.label"
          >
          </el-option>
        </el-select>
      </div>
      <div class="diaItem">
        <span>
          <p style="color: #ff5f5f">*</p>
          养护类别：
        </span>
        <el-select v-model="levelValue" placeholder="请选择">
          <el-option
            v-for="item in levelList"
            :key="item.id"
            :label="item.label"
            :value="item.label"
          >
          </el-option>
        </el-select>
      </div>
      <div class="diaBtn">
        <div class="accept" @click="generateReport">确认生成</div>
        <div class="cancel" @click="showDialog = false">取消</div>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import {
  getRoadList,
  generateReport,
  downloadTestReport
} from '@/api/assessment/record';
export default {
  props: {
    structureManage: {
      type: Object,
      default: () => {
        return { id: '', structureName: '' };
      }
    }
  },
  inject: ['authOpt'],
  watch: {
    structureManage() {
      if (this.structureManage.id) {
        this.getRoadList();
      }
    }
  },
  data() {
    return {
      tableTitleList: [
        { id: 1, label: '线路', prop: 'name' },
        { id: 2, label: '检测类型', prop: 'type' }
      ],
      tableData: [
        // { id: 1, name: '朝芸桥', type: '定期检测', bridgeConditionIndex: 1 }
      ],
      disClick: true,
      showDialog: false,
      basisValue: '',
      levelValue: '',
      basisList: [
        { id: 1, label: '《混凝土结构现场检测技术标准》（GB/T 50784-2013）' },
        { id: 2, label: '《城市桥梁养护技术标准》(CJJ 99-2017)' }
      ],
      levelList: [
        { id: 1, label: 'Ⅰ类养护' },
        { id: 2, label: 'Ⅱ类养护' },
        { id: 3, label: 'Ⅲ类养护' },
        { id: 4, label: 'Ⅳ类养护' },
        { id: 5, label: 'Ⅴ类养护' }
      ],
      generItem: {} //生成报告参数
    };
  },
  methods: {
    //获取检测报告表格数据
    async getRoadList() {
      let { code, data } = await getRoadList(this.structureManage.id);
      if (code == '0000') {
        this.tableData = data;
      }
    },
    //点击生成报告
    generate(data) {
      if (data.bridgeConditionIndex === 0) {
        return;
      }
      this.generItem = data;
      this.showDialog = true;
    },
    //确认生成报告
    async generateReport() {
      if (this.basisValue == '') {
        this.$message({
          message: '请选择检测依据！',
          showClose: true,
          type: 'warning',
          duration: 3000
        });
        return;
      }
      if (this.levelValue == '') {
        this.$message({
          message: '请选择养护类别！',
          showClose: true,
          type: 'warning',
          duration: 3000
        });
        return;
      }
      let loading = this.$loading({
        lock: true,
        text: '生成报告中，请稍等...',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      });
      let params = {
        id: this.generItem.id,
        structureName: this.structureManage.structureName,
        roadName: this.generItem.name,
        detectionBase: this.basisValue,
        detectionType: this.generItem.type,
        bridgeLevel: this.levelValue
      };
      try {
        let { code, data, msg } = await generateReport(params);
        if (code == '0000') {
          loading.close();
          if (data == 1) {
            this.$message({
              message: '报告已生成！',
              showClose: true,
              type: 'success',
              duration: 3000
            });
            this.$nextTick(() => {
              this.showDialog = false;
              this.getRoadList();
            });
          } else {
            this.$message({
              message: msg,
              showClose: true,
              type: 'error',
              duration: 3000
            });
          }
        }
      } catch (error) {
        loading.close();
      }
    },
    //下载报告
    async downLoad(file) {
      // console.log(data);
      // return;
      if (file.bridgeConditionIndex === 0) {
        return;
      }
      // let path = '/static/files/assessment/pdf1.pdf';
      // let name = '朝芸桥2020.pdf';
      let loading = this.$loading({
        lock: true,
        text: '文件较大，正在获取资源，请稍等...',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      });
      downloadTestReport(file.id)
        .then((res) => {
          loading.close();
          this.$utils.downloadBlob(res, file.reportPath);
        })
        .catch();
    },
    //关闭dialog
    dialogClose() {
      this.basisValue = '';
      this.levelValue = '';
    }
  },
  mounted() {
    if (this.structureManage.id) {
      this.getRoadList();
    }
  }
};
</script>
<style lang="scss" scoped>
.report {
  height: 100%;
  width: 100%;
  padding-top: 2.5vh;
  .option {
    display: flex;
    align-items: center;
    justify-content: center;
    div {
      color: #419aff;
      font-size: 0.7vw;
      padding: 0 1.2vw;
      cursor: pointer;
      &:hover {
        color: rgba(65, 154, 255, 0.8);
      }
    }
    .disabled {
      cursor: not-allowed;
      opacity: 0.5;
      &:hover {
        color: #419aff;
      }
    }
    .dotBlue {
      width: 0.417vw;
      height: 0.417vw;
      border-radius: 50%;
      background: #419aff;
      margin-right: 0.4vw;
    }
    .dotRed {
      width: 0.417vw;
      height: 0.417vw;
      border-radius: 50%;
      background: #ff4343;
      margin-right: 0.4vw;
    }
  }
  /deep/ .el-table {
    height: 100%;
  }
  /deep/ .el-table__body-wrapper {
    // max-height: 93%;
    height: 93%;
    overflow-y: auto;
  }
  /deep/ .el-table th {
    color: #333;
  }
  /deep/ .el-table td {
    height: 4.45vh;
    font-size: 0.7vw;
    color: #333;
    padding: 0;
  }
}
//dialog样式
.genReport {
  display: flex;
  align-items: center;
  justify-content: center;
  .diaItem {
    font-size: 0.7vw;
    color: #333;
    padding: 0 23px;
    margin-bottom: 1.2vw;
    display: flex;
    align-items: center;
    justify-content: flex-end;
    span {
      display: flex;
      align-items: center;
    }
    /deep/ .el-select {
      flex: 1;
    }
  }
  .diaBtn {
    font-size: 0.7vw;
    color: #333;
    padding: 0 23px;
    display: flex;
    align-items: center;
    justify-content: flex-end;
    .accept {
      width: 96px;
      height: 40px;
      background: #419aff;
      color: #fff;
      border-radius: 4px;
      display: flex;
      align-items: center;
      justify-content: center;
      cursor: pointer;
      &:hover {
        background: rgba(65, 154, 255, 0.8);
      }
    }
    .cancel {
      width: 68px;
      height: 40px;
      background: #ffffff;
      border: 1px solid #dcdfe6;
      border-radius: 4px;
      margin-left: 1.2vw;
      display: flex;
      align-items: center;
      justify-content: center;
      cursor: pointer;
      &:hover {
        color: rgba(0, 0, 0, 0.6);
      }
    }
  }
}
</style>
