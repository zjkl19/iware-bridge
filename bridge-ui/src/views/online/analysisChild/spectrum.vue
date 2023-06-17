<template>
  <div class="spectrum">
    <div class="spectrumItem">
      <div class="itemLeft">
        <div class="selItem">
          <span class="title">频率图</span>
          <div class="mySelect">
            <el-select
              v-model="selValue1"
              placeholder="请选择"
              @change="getFrequency(1)"
            >
              <el-option
                v-for="item in selList"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              >
              </el-option>
            </el-select>
          </div>
        </div>
        <div class="echartsBox">
          <div v-if="areaData1.xData.length == 0" class="noData">
            暂无数据！
          </div>
          <analysisLineArea
            v-else
            ref="anaLineArea1"
            :typeIndex="1"
            :echartData="areaData1"
            @getFrequencyPoint="getFrequencyPoint"
          ></analysisLineArea>
        </div>
      </div>
      <div class="itemRight">
        <div class="selItem">
          <span class="title">频谱图</span>
        </div>
        <div class="echartsBox">
          <div v-if="lineData1.xData.length == 0" class="noData">
            暂无数据！
          </div>
          <analysisLine
            v-else
            ref="anaLine1"
            :echartData="lineData1"
          ></analysisLine>
        </div>
      </div>
    </div>
    <div class="spectrumItem">
      <div class="itemLeft">
        <div class="selItem">
          <span class="title">频率图</span>
          <div class="mySelect">
            <el-select
              v-model="selValue2"
              placeholder="请选择"
              @change="getFrequency(2)"
            >
              <el-option
                v-for="item in selList"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              >
              </el-option>
            </el-select>
          </div>
        </div>
        <div class="echartsBox">
          <div v-if="areaData2.xData.length == 0" class="noData">
            暂无数据！
          </div>
          <analysisLineArea
            v-else
            ref="anaLineArea2"
            :typeIndex="2"
            :echartData="areaData2"
            @getFrequencyPoint="getFrequencyPoint"
          ></analysisLineArea>
        </div>
      </div>
      <div class="itemRight">
        <div class="selItem">
          <span class="title">频谱图</span>
        </div>
        <div class="echartsBox">
          <div v-if="lineData2.xData.length == 0" class="noData">
            暂无数据！
          </div>
          <analysisLine
            v-else
            ref="anaLine2"
            :echartData="lineData2"
          ></analysisLine>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {
  getSensorWeightCarNo,
  getFrequency,
  getFrequencyPoint
} from '@/api/online/analysisData';
import analysisLine from './components/analysisLine';
import analysisLineArea from './components/analysisLineArea';
export default {
  name: 'spectrum-dzl',
  components: { analysisLine, analysisLineArea },
  props: ['structureId', 'time'],
  data() {
    return {
      selList: [],
      selValue1: '',
      selValue2: '',
      //第一行echarts数据
      areaData1: {
        id: 1,
        name: '频率值',
        unit: '频率（Hz）',
        xData: [],
        yData: []
      },
      lineData1: {
        id: 2,
        name: '',
        unit: 'Hz',
        xData: [],
        yData: []
      },
      //第二行echarts数据
      areaData2: {
        id: 3,
        name: '频率值',
        unit: '频率（Hz）',
        xData: [],
        yData: []
      },
      lineData2: {
        id: 4,
        name: '',
        unit: 'Hz',
        xData: [],
        yData: []
      }
    };
  },
  mounted() {
    this.getSensorWeightCarNo(this.structureId);
  },
  methods: {
    getData() {
      this.getFrequency(1);
      this.getFrequency(2);
    },
    //获取测点列表
    async getSensorWeightCarNo(structureId) {
      let { code, data } = await getSensorWeightCarNo(structureId, 2);
      if (code == '0000') {
        this.selList = data;
        this.$nextTick(() => {
          if (data.length >= 2) {
            this.selValue1 = data[0].id;
            this.selValue2 = data[1].id;
            this.getFrequency(1);
            this.getFrequency(2);
          } else if (data.length == 1) {
            this.selValue1 = data[0].id;
            this.selValue2 = data[0].id;
            this.getFrequency(1);
            this.getFrequency(2);
          }
        });
      }
    },
    //获取频率图数据
    async getFrequency(index) {
      let params = {
        id: index == 1 ? this.selValue1 : this.selValue2,
        type: 2,
        startTime: this.time[0],
        endTime: this.time[1]
      };
      let { code, data } = await getFrequency(params);
      if (code == '0000') {
        let xData = [];
        let yData = [];
        data.map((item) => {
          xData.push(item.calculateTime);
          yData.push(item.frequency);
        });
        if (index == 1) {
          this.lineData1.xData = []; //  清空频谱图数据
          this.areaData1.xData = xData;
          this.areaData1.yData = yData;
        } else {
          this.lineData2.xData = []; //  清空频谱图数据
          this.areaData2.xData = xData;
          this.areaData2.yData = yData;
        }
        this.$nextTick(() => {
          if (index == 1) {
            if (xData.length > 0) this.$refs.anaLineArea1.setOption();
          } else {
            if (xData.length > 0) this.$refs.anaLineArea2.setOption();
          }
        });
      }
    },
    //获取频谱图
    async getFrequencyPoint(time, typeIndex) {
      let params = {
        id: typeIndex == 1 ? this.selValue1 : this.selValue2,
        time,
        type: 2
      };
      let { code, data } = await getFrequencyPoint(params);
      if (code == '0000') {
        let xData = [];
        let yData = [];
        data.map((item) => {
          xData.push(item.frequency);
          yData.push(item.amplitude);
        });
        if (typeIndex == 1) {
          this.lineData1.name = time;
          this.lineData1.xData = xData;
          this.lineData1.yData = yData;
        } else {
          this.lineData2.name = time;
          this.lineData2.xData = xData;
          this.lineData2.yData = yData;
        }
        if (xData.length == 0) {
          this.$message({
            message: `暂无频谱数据！`,
            type: 'warning',
            showClose: true,
            duration: 2000
          });
        }
        this.$nextTick(() => {
          if (xData.length > 0) {
            if (typeIndex == 1) this.$refs.anaLine1.setOption();
            else this.$refs.anaLine2.setOption();
          }
        });
      }
    }
  }
};
</script>
<style lang="scss" scoped>
.spectrum {
  height: 79.075vh;
  margin-top: 1.855vh;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  box-sizing: border-box;
  .spectrumItem {
    height: 38.612vh;
    width: 100%;
    background: #ffffff;
    // padding: 1.855vh 24px;
    box-shadow: 0px 12px 24px #dbdfed;
    border-radius: 8px;
    display: flex;
    .itemLeft,
    .itemRight {
      height: 100%;
      padding: 1.855vh 24px;
      display: flex;
      flex-direction: column;
      box-sizing: border-box;
      .selItem {
        height: 2.964vh;
        display: flex;
        align-items: center;
        justify-content: space-between;
        .mySelect {
          height: 100%;
        }
      }
      .echartsBox {
        flex: 1;
      }
    }
    .itemLeft {
      width: 1056px;
      border-right: 1px solid #e5e5e5;
    }
    .itemRight {
      width: 736px;
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
