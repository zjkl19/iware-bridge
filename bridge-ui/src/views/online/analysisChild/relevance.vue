<template>
  <div class="relevance">
    <div class="boxLeft">
      <div class="contentTop">传感器数据</div>
      <div class="contentBox">
        <treeList
          ref="tree"
          :structureId="structureId"
          :maxLength="2"
          @treeChange="treeChange"
        ></treeList>
      </div>
      <div class="contentBtn" @click="getRelevance">查看相关性</div>
    </div>
    <div class="boxMid">
      <div class="contentTop">
        <div class="title">
          <el-tooltip
            :disabled="transverse.length <= 15"
            :content="transverse"
            placement="top"
          >
            <div>横轴：{{ transverse }}</div>
          </el-tooltip>
          <span class="and">&</span>
          <el-tooltip
            :disabled="portrait.length <= 15"
            :content="portrait"
            placement="top"
          >
            <div>纵轴：{{ portrait }}</div>
          </el-tooltip>
        </div>
        <div class="exchange" @click="exchange">
          <i class="el-icon-sort"></i>切换
        </div>
      </div>
      <div class="contentBox">
        <div v-if="echartData.yData1.length == 0" class="noData">
          {{ midMsg }}
        </div>
        <relevanceLine
          v-else
          ref="relevanceLine"
          :echartData="echartData"
        ></relevanceLine>
      </div>
    </div>
    <div class="boxRight">
      <div class="contentTop">相关信息</div>
      <div class="contentBox">
        <div class="contentItem">
          <span class="txt">相关系数：</span
          ><span class="val">{{ correlationCoefficient }}</span>
        </div>
        <div class="contentItem">
          <span class="txt">离差平方和：</span
          ><span class="val">{{ dispersion }}</span>
        </div>
        <div class="contentItem">
          <span class="txt">回归方程：</span
          ><span class="val">{{ regressionEquation }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getCorrelationAnalysis } from '@/api/online/analysisData';
import treeList from './components/treeList';
import relevanceLine from './components/relevanceLine';
export default {
  name: 'relevance-dzl',
  components: { treeList, relevanceLine },
  props: ['structureId', 'time'],
  data() {
    return {
      midMsg: '请选择传感器！',
      //传感器数据
      sensorList: [],
      //图数据
      transverse: '-', //横向
      portrait: '-', //纵向
      echartData: {
        name1: '',
        name2: '',
        xValueList: [], //x轴数值数组
        yValueList: [], //y轴数值数组
        //实测值
        yData1: [],
        //拟合值
        yData2: [],
        //3δ上限
        yData3: [],
        //3δ下限
        yData4: []
      },
      //相关信息
      correlationCoefficient: '',
      dispersion: '',
      regressionEquation: ''
    };
  },
  methods: {
    getTreeData(id) {
      this.$refs.tree.getTree(0, id);
    },
    treeChange(arry) {
      this.sensorList = arry;
    },
    //查看相关性
    async getRelevance() {
      if (this.sensorList.length < 2) {
        this.$message({
          message: `请选择两个传感器！`,
          type: 'warning',
          showClose: true,
          duration: 2000
        });
        return;
      }
      this.getCorrelationAnalysis();
    },
    //相关性分析
    async getCorrelationAnalysis() {
      let loading = this.$loading({
        lock: true,
        text: '正在分析中，请稍等...',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      });
      let params = {
        sensorList: this.sensorList,
        startTime: this.time[0],
        endTime: this.time[1]
      };
      try {
        let { code, data } = await getCorrelationAnalysis(params);
        if (code == '0000') {
          loading.close();
          if (data.allX.length == 0) {
            this.$message({
              message: `暂无相关性数据！`,
              type: 'warning',
              showClose: true,
              duration: 2000
            });
          }
          this.transverse = this.sensorList[0].sensorName;
          this.portrait = this.sensorList[1].sensorName;
          this.echartData.name1 =
            this.sensorList[0].sensorName +
            ' (' +
            this.sensorList[0].unit +
            ')';
          this.echartData.name2 =
            this.sensorList[1].sensorName +
            ' (' +
            this.sensorList[1].unit +
            ')';
          let xValueList = []; //x轴数值数组
          let yValueList = []; //y轴数值数组
          let yData1 = [];
          data.allX.map((num, i) => {
            xValueList.push(num);
            yValueList.push(data.allY[i]);
            yData1.push([num, data.allY[i]]);
          });
          let yData2 = [
            { value: [data.middleFirstX, data.middleFirstY] },
            { value: [data.middleSecondX, data.middleSecondY] }
          ];
          let yData3 = [
            { value: [data.upFirstX, data.upFirstY] },
            { value: [data.upSecondX, data.upSecondY] }
          ];
          let yData4 = [
            { value: [data.downFirstX, data.downFirstY] },
            { value: [data.downSecondX, data.downSecondY] }
          ];
          xValueList = xValueList.concat([
            data.middleFirstX,
            data.middleSecondX,
            data.upFirstX,
            data.upSecondX,
            data.downFirstX,
            data.downSecondX
          ]);
          yValueList = yValueList.concat([
            data.middleFirstY,
            data.middleSecondY,
            data.upFirstY,
            data.upSecondY,
            data.downFirstY,
            data.downSecondY
          ]);
          this.echartData.yData1 = yData1;
          this.echartData.yData2 = yData2;
          this.echartData.yData3 = yData3;
          this.echartData.yData4 = yData4;
          this.echartData.xValueList = xValueList;
          this.echartData.yValueList = yValueList;
          this.correlationCoefficient = data.correlationCoefficient;
          this.dispersion = data.dispersion;
          this.regressionEquation = data.regressionEquation;
          this.$nextTick(() => {
            if (yData1.length > 0) this.$refs.relevanceLine.setOption();
            else this.midMsg = '暂无数据！';
          });
        }
      } catch (error) {
        loading.close();
      }
    },
    //横轴坐标对换
    exchange() {
      let transverse = this.transverse;
      this.transverse = this.portrait;
      this.portrait = transverse;
      let name = this.echartData.name1;
      this.echartData.name1 = this.echartData.name2;
      this.echartData.name2 = name;
      let xValueList = this.echartData.xValueList;
      this.echartData.xValueList = this.echartData.yValueList;
      this.echartData.yValueList = xValueList;
      this.echartData.yData1.map((item) => {
        let value = item[0];
        item[0] = item[1];
        item[1] = value;
      });
      this.echartData.yData2.map((item) => {
        let value = item.value[0];
        item.value[0] = item.value[1];
        item.value[1] = value;
      });
      this.echartData.yData3.map((item) => {
        let value = item.value[0];
        item.value[0] = item.value[1];
        item.value[1] = value;
      });
      this.echartData.yData4.map((item) => {
        let value = item.value[0];
        item.value[0] = item.value[1];
        item.value[1] = value;
      });
      this.$nextTick(() => {
        this.$refs.relevanceLine.setOption();
      });
    }
  }
};
</script>
<style lang="scss" scoped>
.relevance {
  height: 79.075vh;
  margin-top: 1.855vh;
  background: #fff;
  border-radius: 8px;
  display: flex;
  box-sizing: border-box;
  .boxLeft,
  .boxMid,
  .boxRight {
    padding: 1.855vh 24px;
    display: flex;
    flex-direction: column;
    .contentTop {
      font-size: 16px;
      color: #262626;
    }
    .contentBox {
      width: 100%;
      flex: 1;
      margin-top: 16px;
    }
  }
  .boxLeft {
    width: 400px;
    border-right: 1px solid #e8e8e8;
    /deep/ .box-tree {
      height: 62.038vh;
    }
    .contentBtn {
      height: 3.705vh;
      font-size: 14px;
      background: #419aff;
      color: #fff;
      border-radius: 4px;
      display: flex;
      align-items: center;
      justify-content: center;
      cursor: pointer;
    }
  }
  .boxMid {
    width: 972px;
    border-right: 1px solid #e8e8e8;
    .contentTop {
      position: relative;
      height: 2.964vh;
      text-align: center;
      .title {
        line-height: 2.964vh;
        font-size: 16px;
        color: #262626;
        display: flex;
        align-items: center;
        justify-content: center;
        .and {
          padding: 0 16px;
        }
        /deep/ .el-tooltip {
          max-width: 310px;
          font-size: 16px;
          color: #262626;
          white-space: nowrap;
          overflow: hidden;
          text-overflow: ellipsis;
        }
      }
      .exchange {
        position: absolute;
        right: 0;
        top: 0;
        height: 2.964vh;
        width: 72px;
        border: 1px solid #419aff;
        color: #419aff;
        border-radius: 4px;
        display: flex;
        align-items: center;
        justify-content: center;
        box-sizing: border-box;
        cursor: pointer;
        i {
          font-size: 12px;
          margin-right: 6px;
          transform: rotate(90deg);
        }
      }
    }
  }
  .boxRight {
    width: 420px;
    .contentBox {
      margin-top: 24px;
      display: flex;
      flex-direction: column;
      .contentItem {
        width: 100%;
        height: 68px;
        margin-bottom: 20px;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        .txt {
          font-size: 14px;
          line-height: 20px;
          color: #333;
        }
        .val {
          height: 40px;
          font-size: 14px;
          color: #000;
          padding: 0 16px;
          border: 1px solid rgba($color: #000, $alpha: 0.15);
          border-radius: 4px;
          box-sizing: border-box;
          display: flex;
          align-items: center;
        }
      }
    }
  }
}
.noData {
  height: 100%;
  width: 100%;
  font-size: 32px;
  color: #333;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
