<template>
  <div class="bciDetail">
    <div class="bread">
      <span @click="backPage">BCI评价 / </span><span>BCI评价详情</span>
    </div>
    <div class="contentBox">
      <div class="content">
        <div class="contentTop">
          <span class="back" @click="backPage">返回</span>
          <span v-if="!evaluateFlag" class="recal" @click="countBCIShow(1)"
            >重新计算</span
          >
        </div>
        <div class="contentMid">
          <span class="title">BCI评价结果</span>
          <div class="result">
            <div v-for="item in resList" :key="item.id" class="resultItem">
              <span class="label">{{ item.label }}</span>
              <span class="value">{{ item.value }}</span>
            </div>
          </div>
        </div>
        <div class="contentBtm">
          <div v-for="item in tableList" :key="item.id" class="tableClass">
            <span class="title">{{ item.title }}</span>
            <div class="resultTable">
              <resTable
                :ref="'table' + item.id"
                :tableTitleList="item.tableTitleList"
                :tableData="item.tableData"
                :tableName="item.title"
              ></resTable>
            </div>
          </div>
        </div>
        <div class="explain">
          <span class="text">评分说明：</span>
          <span
            >1、BCI评价详情是根据已建立的桥梁结构和录入的桥梁病害，参考城市桥梁养护技术标准《CJJ99-2017》自动计算出建议权重值和各项评分。
            自动评分后检测员可根据实际情况对构件权重和评分进行调整。</span
          >
          <span
            >2、"重新计算"按钮：表示系统可根据规范自动评分和检测员调整后，系统支持重新计算出最终评分结果。</span
          >
          <span
            >3、扣分值为*：表示该病害存在单项直接控制指标，可作为直接评定为D级桥的依据。
            该构件扣分值按80分计算桥面系、上部结构、下部结构的BCI、BSI，最后计算全桥BCI值，但该桥的评定等级不得高于D级。参见
            城市桥梁养护技术标准《CJJ99-2017》4.5.1。</span
          >
          <span
            >4、实际权重：根据规范第108页，当存在某座桥梁没有设置某些构件时，将此缺失构件的权重值分配给其他部件。分配方法
            采用将缺失部件权重值按照既有部件权重在全部既有部件权重中所占比例进行分配的方法。</span
          >
          <span
            >5、标准第9页4.1.7：当上部结构和下部结构技术状况等级为3类、桥面系技术状况等级为4类，
            且桥梁总体技术状况评分为40≤Dr＜60时，桥梁总体技术状况等级应评定为3类。</span
          >
          <span>
            6、标准第9页4.1.8：全桥总体技术状况等级评定时，当主要部件评分达到4类或5类且影响桥梁安全时，
            可按照桥梁主要部件最差的缺损状况评定。</span
          >
        </div>
      </div>
    </div>
    <!-- 重新计算弹框 -->
    <el-dialog
      class="recalculate"
      :title="dialogResetTitle"
      :visible.sync="dialogResetShow"
      :modal-append-to-body="false"
      :close-on-click-modal="false"
    >
      <div class="resetTable">
        <div v-for="item in resTableList" :key="item.id" class="tableClass">
          <span class="title">{{ item.title }}</span>
          <div class="resultTable">
            <resTable
              :ref="'table2' + item.id"
              :tableTitleList="item.tableTitleList"
              :tableData="item.tableData"
              :tableName="item.title"
              :disabled="false"
            ></resTable>
          </div>
        </div>
      </div>
      <div class="btn">
        <el-button type="primary" @click="countBCI">{{
          dialogResetTitle
        }}</el-button>
        <el-button @click="dialogResetShow = false">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import {
  selBCIEvaluationDetail,
  countBCI,
  calculateDeduct
} from '@/api/assessment/bciEvaluation';
import resTable from './BCItable';
export default {
  components: {
    resTable
  },
  props: {
    bridgeRoadId: Number,
    isEvaluate: Boolean,
    state: Number,
    score: Number
  },
  data() {
    return {
      //评价结果
      resList: [
        {
          id: 1,
          label: '项目名称：',
          value: '',
          code: 'projectName'
        },
        {
          id: 2,
          label: '业主单位：',
          value: '',
          code: 'ownerUnit'
        },
        {
          id: 3,
          label: '检测时间：',
          value: '',
          code: 'testingTime'
        },
        {
          id: 4,
          label: '评价日期：',
          value: '',
          code: 'evaluationTime'
        },
        { id: 5, label: '评价单位：', value: '', code: 'evaluationUnit' },
        { id: 6, label: '评价人员：', value: '', code: 'evaluator' },
        { id: 7, label: '线路名称：', value: '', code: 'roadName' },
        { id: 8, label: '跨径数量：', value: '', code: 'spanNumber' },
        { id: 9, label: '评价等级：', value: '', code: 'ratingLevel' },
        { id: 10, label: '桥面系BCIm：', value: '', code: 'bridgeBCIm' },
        {
          id: 11,
          label: '上部结构BCIs：',
          value: '',
          code: 'upperStructureBCIs'
        },
        {
          id: 12,
          label: '下部结构BCIx：',
          value: '',
          code: 'lowerStructureBCIx'
        },
        { id: 13, label: 'BCI评分：', value: '', code: 'roadScore' },
        // {
        //   id: 17,
        //   label: '全桥BCI：',
        //   value: '',
        //   code: 'score'
        // },
        {
          id: 14,
          label: '桥面系BSIm：',
          value: '',
          code: 'bridgeBSIm'
        },
        {
          id: 15,
          label: '上部结构BSIs：',
          value: '',
          code: 'upperStructureBSIs'
        },
        {
          id: 16,
          label: '下部结构BSIx：',
          value: '',
          code: 'lowerStructureBSIx'
        }
      ],
      //表数据列表
      tableList: [
        {
          id: 'One',
          title: '桥面系',
          tableTitleList: [
            { id: 1, label: '桥面系要素', prop: 'artifactsName' },
            { id: 2, label: '原始权重', prop: 'initialWeight' },
            { id: 3, label: '实际权重', prop: 'actualWeight' },
            { id: 4, label: '各评价要素扣分', prop: 'deduct' },
            { id: 5, label: '各评价要素评分', prop: 'score' },
            { id: 6, label: 'BCIm', prop: 'valueX' },
            { id: 7, label: 'BSIm', prop: 'valueY' }
          ],
          tableData: []
        },
        {
          id: 'Two',
          title: '上部结构',
          tableTitleList: [
            { id: 1, label: '跨号', prop: 'spanCode' },
            { id: 9, label: '上部结构要素', prop: 'artifactsName' },
            { id: 2, label: '原始权重', prop: 'initialWeight' },
            { id: 3, label: '实际权重', prop: 'actualWeight' },
            { id: 4, label: '各评价要素扣分', prop: 'deduct' },
            { id: 5, label: '各评价要素评分', prop: 'score' },
            { id: 6, label: 'BCIsi', prop: 'valueZ' },
            { id: 7, label: 'BCIs', prop: 'valueX' },
            { id: 8, label: 'BSIs', prop: 'valueY' }
          ],
          tableData: []
        },
        {
          id: 'Three',
          title: '下部结构',
          tableTitleList: [
            { id: 1, label: '墩台号', prop: 'spanCode' },
            { id: 9, label: '下部结构要素', prop: 'artifactsName' },
            { id: 2, label: '原始权重', prop: 'initialWeight' },
            { id: 3, label: '实际权重', prop: 'actualWeight' },
            { id: 4, label: '各评价要素扣分', prop: 'deduct' },
            { id: 5, label: '各评价要素评分', prop: 'score' },
            { id: 6, label: 'BCIxj', prop: 'valueZ' },
            { id: 7, label: 'BCIx', prop: 'valueX' },
            { id: 8, label: 'BSIx', prop: 'valueY' }
          ],
          tableData: []
        }
      ],
      //重新计算弹框
      dialogResetTitle: '',
      dialogResetShow: false,
      evaluateFlag: false,
      resTableList: [
        {
          id: 'One',
          title: '桥面系',
          tableTitleList: [
            { id: 1, label: '桥面系要素', prop: 'artifactsName' },
            { id: 2, label: '原始权重', prop: 'initialWeight' },
            { id: 3, label: '实际权重', prop: 'actualWeight' },
            { id: 4, label: '各评价要素扣分', prop: 'deduct' }
          ],
          tableData: []
        },
        {
          id: 'Two',
          title: '上部结构',
          tableTitleList: [
            { id: 1, label: '跨号', prop: 'spanCode' },
            { id: 9, label: '上部结构要素', prop: 'artifactsName' },
            { id: 2, label: '原始权重', prop: 'initialWeight' },
            { id: 3, label: '实际权重', prop: 'actualWeight' },
            { id: 4, label: '各评价要素扣分', prop: 'deduct' }
          ],
          tableData: []
        },
        {
          id: 'Three',
          title: '下部结构',
          tableTitleList: [
            { id: 1, label: '墩台号', prop: 'spanCode' },
            { id: 9, label: '下部结构要素', prop: 'artifactsName' },
            { id: 2, label: '原始权重', prop: 'initialWeight' },
            { id: 3, label: '实际权重', prop: 'actualWeight' },
            { id: 4, label: '各评价要素扣分', prop: 'deduct' }
          ],
          tableData: []
        }
      ],
      openAuto: this.state
    };
  },
  watch: {
    dialogResetShow(e) {
      if (!e && this.openAuto == 1) {
        this.backPage();
      }
      if (e && this.openAuto == 1) this.countBCIShow(2);
    }
  },
  methods: {
    //查询BCI开始评价
    async selBCIEvaluationDetail() {
      let params = {
        roadId: this.bridgeRoadId
      };
      let { code, data } = await selBCIEvaluationDetail(params);
      if (code == '0000') {
        this.analysisData(data);
        this.evaluateFlag = data.evaluateFlag;
      }
    },
    //重新计算
    async countBCI() {
      //上部结构变量
      let acrossUpDTOList = [];
      let upperList = this.resTableList[1].tableData;
      //下部结构变量
      let acrossLowDTOList = [];
      let lowerList = this.resTableList[2].tableData;

      //处理上部结构数据
      if (upperList.length > 0) {
        let spanUpCode = upperList[0].spanCode;
        let spanUpId = upperList[0].spanId;
        let scoreUpId = upperList[0].scoreId;
        let acrossUpObj = {};
        let acrossUpList = [];

        upperList.map((item) => {
          if (item.spanCode != spanUpCode) {
            acrossUpObj['spanCode'] = spanUpCode;
            acrossUpObj['spanId'] = spanUpId;
            acrossUpObj['scoreId'] = scoreUpId;
            acrossUpObj['artifactsDTOList'] = acrossUpList;
            acrossUpDTOList.push(JSON.parse(JSON.stringify(acrossUpObj)));

            spanUpCode = item.spanCode;
            spanUpId = item.spanId;
            scoreUpId = item.scoreId;
            acrossUpList = [item];
          } else {
            acrossUpList.push(item);
          }
        });
        acrossUpObj['spanCode'] = spanUpCode;
        acrossUpObj['spanId'] = spanUpId;
        acrossUpObj['scoreId'] = scoreUpId;
        acrossUpObj['artifactsDTOList'] = acrossUpList;
        acrossUpDTOList.push(JSON.parse(JSON.stringify(acrossUpObj)));
      }

      //处理下部结构数据
      if (lowerList.length > 0) {
        let spanLowCode = lowerList[0].spanCode;
        let spanLowId = lowerList[0].spanId;
        let scoreLowId = lowerList[0].scoreId;
        let acrossLowObj = {};
        let acrossLowList = [];

        lowerList.map((item) => {
          if (item.spanCode != spanLowCode) {
            acrossLowObj['spanCode'] = spanLowCode;
            acrossLowObj['spanId'] = spanLowId;
            acrossLowObj['scoreId'] = scoreLowId;
            acrossLowObj['artifactsDTOList'] = acrossLowList;
            acrossLowDTOList.push(JSON.parse(JSON.stringify(acrossLowObj)));

            spanLowCode = item.spanCode;
            spanLowId = item.spanId;
            scoreLowId = item.scoreId;
            acrossLowList = [item];
          } else {
            acrossLowList.push(item);
          }
        });
        acrossLowObj['spanCode'] = spanLowCode;
        acrossLowObj['spanId'] = spanLowId;
        acrossLowObj['scoreId'] = scoreLowId;
        acrossLowObj['artifactsDTOList'] = acrossLowList;
        acrossLowDTOList.push(JSON.parse(JSON.stringify(acrossLowObj)));
      }

      //重新计算参数
      let params = {
        roadId: this.bridgeRoadId,
        bridgeTypeId: this.bridgeTypeId,
        floorSystemDTO: { artifactsDTOList: this.resTableList[0].tableData },
        upperStructureDTO: { acrossDTOList: acrossUpDTOList },
        lowerStructureDTO: { acrossDTOList: acrossLowDTOList },
        bridgeBCIm: Number(this.resList[9].value),
        upperStructureBCIs: Number(this.resList[10].value),
        lowerStructureBCIx: Number(this.resList[11].value),
        bridgeBSIm: Number(this.resList[13].value),
        upperStructureBSIs: Number(this.resList[14].value),
        lowerStructureBSIx: Number(this.resList[15].value)
      };
      let actualWeight = 0;
      let lowerStructureDTOIs = false;
      let upperStructureDTOIs = false;
      params.floorSystemDTO.artifactsDTOList.map((text, index) => {
        actualWeight += (text.actualWeight * 100) / 100;
      });

      if (
        Math.round(actualWeight * 100) / 100 != 1 &&
        params.floorSystemDTO.artifactsDTOList.length != 0
      ) {
        this.$message({
          message: '桥面系的权重相加需要等于1',
          type: 'warning'
        });
        return;
      }
      params.lowerStructureDTO.acrossDTOList.map((text, index) => {
        actualWeight = 0;
        text.artifactsDTOList.map((text2, index2) => {
          actualWeight += text2.actualWeight;
        });

        if (Math.round(actualWeight * 100) / 100 != 1) {
          lowerStructureDTOIs = true;
          return;
        }
      });
      if (lowerStructureDTOIs) {
        this.$message({
          message: '下部结构的每一跨的权重相加需要等于1',
          type: 'warning'
        });
        return;
      }

      params.upperStructureDTO.acrossDTOList.map((text, index) => {
        actualWeight = 0;
        text.artifactsDTOList.map((text2, index2) => {
          actualWeight += text2.actualWeight;
        });

        if (Math.round(actualWeight * 100) / 100 != 1) {
          upperStructureDTOIs = true;
          return;
        }
      });

      if (upperStructureDTOIs) {
        this.$message({
          message: '上部结构的每一跨的权重相加需要等于1',
          type: 'warning'
        });
        return;
      }
      // console.log(1111, this.resList);
      // return;
      let { code, data } = await countBCI(params);
      if (code == '0000') {
        if (code == '0000') {
          this.$message({
            message: '重新计算成功！',
            showClose: true,
            type: 'success',
            duration: 3000
          });
          this.openAuto = 2;
          this.dialogResetShow = false;
          this.analysisData(data);
        }
      }
    },
    //显示重新计算弹框
    countBCIShow(index) {
      this.tableList.map((item) => {
        this.resTableList.map((child) => {
          if (item.id == child.id) {
            child.tableData = JSON.parse(JSON.stringify(item.tableData));
          }
        });
      });
      if (index == 1) {
        this.dialogResetTitle = '重新计算';
      } else {
        this.dialogResetTitle = '开始计算';
      }
      this.dialogResetShow = true;
      this.$nextTick(() => {
        this.$refs.table2One[0].tableInit();
        this.$refs.table2Two[0].tableInit();
        this.$refs.table2Three[0].tableInit();
      });
    },
    //解析BCI评价数据
    analysisData(data) {
      let _this = this;
      if (data.bridgeTypeId) {
        this.bridgeTypeId = data.bridgeTypeId;
      }
      //BCI评价结果
      this.resList.map((item) => {
        item.value = data.bciEvaluationResultDTO
          ? data.bciEvaluationResultDTO[item.code]
          : '';
      });
      //桥面系
      let floorSystemList = data.floorSystemDTO.artifactsDTOList;
      floorSystemList.map((item) => {
        item['valueX'] = _this.FixedTwo(data.floorSystemDTO.bCIm);
        item['valueY'] = _this.FixedTwo(data.floorSystemDTO.bSIm);
      });
      this.tableList[0].tableData = floorSystemList;

      //上部结构
      let upperList = data.upperStructureDTO.acrossDTOList;
      let upperStructureList = [];
      upperList.map((itemList) => {
        if (itemList.artifactsDTOList.length > 0) {
          itemList.artifactsDTOList.map((child) => {
            child['spanCode'] = itemList.spanCode;
            child['spanId'] = itemList.spanId;
            child['scoreId'] = itemList.scoreId;
            child['valueX'] = _this.FixedTwo(data.upperStructureDTO.bCIs);
            child['valueY'] = _this.FixedTwo(data.upperStructureDTO.bSIs);
            child['valueZ'] = _this.FixedTwo(itemList.bCIsi);
            upperStructureList.push(child);
          });
        }
      });
      this.tableList[1].tableData = upperStructureList;

      //下部结构
      let lowerList = data.lowerStructureDTO.acrossDTOList;
      let lowerStructureList = [];
      lowerList.map((itemList) => {
        if (itemList.artifactsDTOList.length > 0) {
          itemList.artifactsDTOList.map((child) => {
            child['spanCode'] = itemList.spanCode;
            child['spanId'] = itemList.spanId;
            child['scoreId'] = itemList.scoreId;
            child['valueX'] = _this.FixedTwo(data.lowerStructureDTO.bCIx);
            child['valueY'] = _this.FixedTwo(data.lowerStructureDTO.bSIx);
            child['valueZ'] = _this.FixedTwo(itemList.bCIxj);
            lowerStructureList.push(child);
          });
        }
      });
      this.tableList[2].tableData = lowerStructureList;

      // this.resList[12].value = this.score || '-';
      this.$nextTick(() => {
        _this.$refs.tableOne[0].tableInit();
        _this.$refs.tableTwo[0].tableInit();
        _this.$refs.tableThree[0].tableInit();
      });
    },
    //返回
    backPage() {
      this.$emit('hideEvaluate');
    },
    FixedTwo(str) {
      if (str != null && str != undefined) {
        str = str.toFixed(2);
      }
      return str;
    }
  },
  mounted() {
    if (this.isEvaluate) {
      this.selBCIEvaluationDetail();
    } else {
      calculateDeduct(this.bridgeRoadId).then((res) => {
        this.selBCIEvaluationDetail();
        if (this.openAuto == 1) {
          let p = this.selBCIEvaluationDetail();
          Promise.all([p]).then((res) => {
            this.dialogResetShow = true;
          });
        }
      });
    }
  }
};
</script>
<style lang="scss" scoped>
.bciDetail {
  display: flex;
  flex-direction: column;
  .bread {
    height: 5.24vh;
    // height: 56px;
    background: #fff;
    padding: 0 28px;
    margin-bottom: 1.855vh;
    display: flex;
    align-items: center;
    span {
      font-size: 0.8vw;
      color: #262626;
      white-space: pre-wrap;
      &:first-child {
        font-weight: bold;
        cursor: pointer;
      }
    }
  }
  .contentBox {
    padding: 0 28px 1.855vh;
    flex: 1;
    overflow: auto;
    .content {
      width: 100%;
      background: #fff;
      border-radius: 8px;
      padding: 1.2vw;
      display: flex;
      flex-direction: column;
      .contentTop {
        height: 6.303vh;
        width: 100%;
        display: flex;
        .back {
          width: 3.125vw;
          height: 3.708vh;
          color: #fff;
          background: #419aff;
          border-radius: 4px;
          margin-right: 1vw;
          &:hover {
            background: rgba(65, 154, 255, 0.8);
          }
        }
        .recal {
          width: 4.584vw;
          height: 3.708vh;
          color: #419aff;
          border: 1px solid #419aff;
          &:hover {
            color: rgba(65, 154, 255, 0.8);
            border: 1px solid rgba(65, 154, 255, 0.8);
          }
        }
        span {
          font-size: 0.7vw;
          border-radius: 4px;
          display: flex;
          align-items: center;
          justify-content: center;
          cursor: pointer;
        }
      }
      .contentMid {
        height: 28.731vh;
        width: 100%;
        .title {
          font-size: 0.7vw;
          color: #000;
          font-weight: bold;
        }
        .result {
          width: 100%;
          height: 23.541vh;
          padding-top: 14px;
          display: flex;
          flex-wrap: wrap;
          .resultItem {
            width: 25%;
            height: 25%;
            display: flex;
            align-items: center;
            justify-content: flex-end;
            .label {
              font-size: 0.7vw;
              color: #333;
            }
            .value {
              height: 40px;
              width: 16.667vw;
              font-size: 0.7vw;
              padding: 0 0.8vw;
              border: 1px solid rgba(0, 0, 0, 0.15);
              border-radius: 4px;
              color: #000;
              display: flex;
              align-items: center;
            }
          }
        }
      }
      .contentBtm {
        .tableClass {
          padding-bottom: 20px;
          display: flex;
          flex-direction: column;
          .title {
            font-size: 0.7vw;
            color: #000;
            font-weight: bold;
            padding-bottom: 1.2vw;
          }
        }
      }
      .explain {
        display: flex;
        flex-direction: column;
        .text {
          font-size: 16px;
          margin-bottom: 10px;
        }
        span {
          color: #333;
          font-size: 14px;
        }
      }
    }
  }
}
//重新计算弹框
.recalculate {
  .resetTable {
    height: 60.002vh;
    padding-right: 12px;
    overflow: auto;
    .tableClass {
      padding-bottom: 20px;
      display: flex;
      flex-direction: column;
      .title {
        font-size: 0.7vw;
        color: #000;
        font-weight: bold;
        padding-bottom: 10px;
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
  .btn {
    padding-top: 20px;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  /deep/ .el-dialog {
    width: 1092px;
  }
  /deep/ .el-dialog__body {
    padding: 20px 30px;
    display: flex;
    flex-direction: column;
  }
}
</style>
