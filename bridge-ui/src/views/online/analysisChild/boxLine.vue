<template>
  <div class="boxLine">
    <div class="boxLeft">
      <div class="contentTop">传感器数据</div>
      <div class="contentBox">
        <treeList
          ref="tree"
          :structureId="structureId"
          :maxLength="10"
          :all="1"
          @treeChange="treeChange"
        ></treeList>
      </div>
    </div>
    <div class="boxRight">
      <div v-if="echartData.xData.length == 0" class="noData">暂无数据！</div>
      <dataBox v-else ref="boxEchart" :echartData="echartData"></dataBox>
    </div>
  </div>
</template>

<script>
import { boxAnalyse } from '@/api/online/warnDetails';
import treeList from './components/treeList';
import dataBox from './components/dataBox';
export default {
  name: 'boxLine-dzl',
  components: { treeList, dataBox },
  props: ['structureId', 'time'],
  data() {
    return {
      //tree数据
      sensorList: [],
      //箱线图数据
      echartData: {
        xData: [],
        data: [
          // [2, 3, 4, 5, 6],
          // [5, 6, 7, 8, 9],
          // [3, 4, 5, 6, 7]
        ],
        warnData: [
          // [0, 1, '异常'],
          // [0, 9, '异常'],
          // [1, 1, '异常'],
          // [1, 11, '异常'],
          // [1, 3, '异常']
        ],
        maxValList: [],
        minValList: []
      }
    };
  },
  methods: {
    getTreeData(id) {
      this.$refs.tree.getTree(0, id);
    },
    treeChange(arry) {
      // console.log(arry);
      this.sensorList = arry;
      this.$nextTick(() => {
        this.boxAnalyse();
      });
    },
    //箱型图分析
    async boxAnalyse() {
      let loading = this.$loading({
        lock: true,
        text: '数据加载中，请稍等...',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      });
      let params = {
        sensorList: this.sensorList,
        startTime: this.time[0],
        endTime: this.time[1]
      };
      let { code, data } = await boxAnalyse(params);
      if (code == '0000') {
        let xData = [];
        let dataList = [];
        let warnData = [];
        let maxValList = [];
        let minValList = [];
        data.map((item, index) => {
          xData.push(item.name);
          dataList.push(item.calculate);
          minValList.push(item.calculate[0]);
          maxValList.push(item.calculate[4]);
          item.warningList.map((val) => {
            let warnList = [index, val, '异常'];
            warnData.push(warnList);
            minValList.push(val);
            maxValList.push(val);
          });
        });
        this.echartData.xData = xData;
        this.echartData.data = dataList;
        this.echartData.warnData = warnData;
        this.echartData.maxValList = maxValList;
        this.echartData.minValList = minValList;
        this.$nextTick(() => {
          if (xData.length > 0) this.$refs.boxEchart.setOption();
          loading.close();
        });
      }
    }
  }
};
</script>
<style lang="scss" scoped>
.boxLine {
  height: 79.075vh;
  margin-top: 1.855vh;
  background: #fff;
  border-radius: 8px;
  display: flex;
  box-sizing: border-box;
  .boxLeft {
    width: 400px;
    border-right: 1px solid #e8e8e8;
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
  .boxRight {
    width: 1392px;
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
