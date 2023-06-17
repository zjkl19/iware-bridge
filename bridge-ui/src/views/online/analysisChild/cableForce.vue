<template>
  <div class="cableForce">
    <div class="cableForceItem">
      <div class="itemLeft">
        <div class="selItem">
          <span class="title">{{ title1 }}</span>
          <div class="myRadio">
            <el-radio-group v-model="radioValue1" @change="radioChange1">
              <el-radio :label="1">频率</el-radio>
              <el-radio :label="2">索力</el-radio>
            </el-radio-group>
          </div>
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
    <div class="cableForceItem">
      <div class="itemLeft">
        <div class="selItem">
          <span class="title">{{ title2 }}</span>
          <div class="myRadio">
            <el-radio-group v-model="radioValue2" @change="radioChange2">
              <el-radio :label="1">频率</el-radio>
              <el-radio :label="2">索力</el-radio>
            </el-radio-group>
          </div>
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
  name: 'cableForce-dzl',
  components: { analysisLine, analysisLineArea },
  props: ['structureId', 'time'],
  data() {
    return {
      title1: '频率图',
      title2: '频率图',
      radioValue1: 1,
      radioValue2: 1,
      selValue: '',
      selList: [],
      selValue1: '',
      selValue2: '',
      //第一行echarts数据
      areaData1: {
        id: 20,
        name: '频率值',
        unit: '频率（Hz）',
        xData: [],
        yData: [],
        yData2: []
      },
      lineData1: {
        id: 1,
        name: '',
        unit: 'Hz',
        xData: [],
        yData: []
      },
      //第二行echarts数据
      areaData2: {
        id: 2,
        name: '频率值',
        unit: '频率（Hz）',
        xData: [],
        yData: [],
        yData2: []
      },
      lineData2: {
        id: 3,
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
      let { code, data } = await getSensorWeightCarNo(structureId, 3);
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
      if (index == 1) {
        this.radioValue1 = 1;
        this.title1 = '频率图';
        this.areaData1.unit = '频率（Hz）';
      } else {
        this.radioValue2 = 1;
        this.title2 = '频率图';
        this.areaData2.unit = '频率（Hz）';
      }
      let params = {
        id: index == 1 ? this.selValue1 : this.selValue2,
        type: 3,
        startTime: this.time[0],
        endTime: this.time[1]
      };
      let { code, data } = await getFrequency(params);
      if (code == '0000') {
        let xData = [];
        let yData = [];
        let yData2 = [];
        data.map((item) => {
          xData.push(item.calculateTime);
          yData.push(item.frequency);
          yData2.push(item.cableForce);
        });
        if (index == 1) {
          this.lineData1.xData = []; //  清空频谱图数据
          this.areaData1.xData = xData;
          this.areaData1.yData = yData;
          this.areaData1.yData2 = yData2;
        } else {
          this.lineData2.xData = []; //  清空频谱图数据
          this.areaData2.xData = xData;
          this.areaData2.yData = yData;
          this.areaData2.yData2 = yData2;
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
      // console.log(typeIndex);
      let params = {
        id: typeIndex == 1 ? this.selValue1 : this.selValue2,
        time,
        type: 3
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
    },
    //单选切换
    radioChange1(e) {
      if (e === 1) {
        this.title1 = '频率图';
        this.areaData1.unit = '频率（Hz）';
      } else {
        this.title1 = '索力图';
        this.areaData1.unit = '索力（KN）';
      }
      let yData = this.areaData1.yData;
      this.areaData1.yData = this.areaData1.yData2;
      this.areaData1.yData2 = yData;
      this.$nextTick(() => {
        this.$refs.anaLineArea1.setOption();
      });
    },
    radioChange2(e) {
      if (e === 1) {
        this.title2 = '频率图';
        this.areaData2.unit = '频率（Hz）';
      } else {
        this.title2 = '索力图';
        this.areaData2.unit = '索力（KN）';
      }
      let yData = this.areaData2.yData;
      this.areaData2.yData = this.areaData2.yData2;
      this.areaData2.yData2 = yData;
      this.$nextTick(() => {
        this.$refs.anaLineArea2.setOption();
      });
    }
  }
};
</script>
<style lang="scss" scoped>
.cableForce {
  height: 79.075vh;
  margin-top: 1.855vh;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  box-sizing: border-box;
  .cableForceItem {
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
