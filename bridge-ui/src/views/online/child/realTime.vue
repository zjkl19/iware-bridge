<template>
  <div class="realTime">
    <div v-if="sensorList.length == 0" class="realTimeBox noData">
      请先选择测点
    </div>
    <div v-else class="realTimeBox">
      <div
        v-for="item in dataList"
        :key="item.id"
        class="realItem"
        :style="{ height: dataList.length > 1 ? '50%' : '100%' }"
      >
        <div class="codeName">{{ item.codeName }}</div>
        <dataLine
          v-if="item.sensorTypeId != 7"
          :ref="'realEchart' + item.echartData.id"
          :echartData="item.echartData"
        ></dataLine>
        <dataScatter
          v-else
          :ref="'realEchart' + item.echartData.id"
          :echartData="item.echartData"
        ></dataScatter>
      </div>
    </div>
  </div>
</template>

<script>
import { getLastTen } from '@/api/online/warnDetails';
import dataLine from './components/dataLine';
import dataScatter from './components/dataScatter';
export default {
  name: 'realTime-dzl',
  components: { dataLine, dataScatter },
  props: {
    sensorList: {
      type: Array,
      default: () => []
    },
    carList: {
      type: Array,
      default: () => []
    }
  },
  watch: {
    sensorList() {
      this.getRealList();
    }
  },
  data() {
    return {
      dataList: [],
      unDestory: true,
      socket: null
    };
  },
  methods: {
    getRealList() {
      if (this.sensorList.length == 0) {
        if (this.socket != null) {
          this.socket.close();
          this.socket.onclose = this.close();
        }
        this.dataList = [];
        return;
      }
      for (let i = 0; i < this.sensorList.length; i++) {
        if (
          this.sensorList[i].companyId == 1 &&
          this.sensorList[i].sensorTypeId != 7
        ) {
          this.$message({
            message: '基康设备' + this.sensorList[i].name + '暂无实时数据！',
            type: 'warning',
            showClose: true,
            duration: 2000
          });
          return;
        }
        if (
          this.sensorList[i].companyId == 3 &&
          this.sensorList[i].sensorTypeId != 7
        ) {
          this.$message({
            message: '智博联设备' + this.sensorList[i].name + '暂无实时数据！',
            type: 'warning',
            showClose: true,
            duration: 2000
          });
          return;
        }
      }
      //关闭websoket
      if (this.socket != null) {
        this.socket.close();
        this.socket.onclose = this.close();
      }
      let arry = [];
      this.sensorList.map((item) => {
        let obj = {
          id: item.sensorCoding,
          codeName: item.name + '(' + item.sensorCoding + ')',
          sensorTypeId: item.sensorTypeId,
          echartData: {
            id: item.sensorCoding,
            unit: item.unit,
            data: [],
            date: []
          }
        };
        arry.push(obj);
      });
      this.dataList = arry;
      this.$nextTick(() => {
        if (arry[0].sensorTypeId == 7) {
          this.getLastTen();
        } else {
          arry.map((item) => {
            let name = 'realEchart' + item.id;
            this.$refs[name][0].setOption();
          });
          this.initWebSocket();
        }
      });
    },
    //获取称重传感器前十条数据
    async getLastTen() {
      let { code, data } = await getLastTen(this.sensorList);
      if (code == '0000') {
        this.dataList.map((item) => {
          for (let key in data) {
            if (item.id == key) {
              let dateList = [];
              let dataList = [];
              data[key].map((child) => {
                child.licensePlate = child.licensePlate.trim();
                child.value = [child.samplingTime, child.value];
                dateList.push(child.samplingTime);
                dataList.push(child);
              });
              item.echartData.data = dataList;
              item.echartData.date = dateList;
            }
          }
          let name = 'realEchart' + item.id;
          this.$refs[name][0].setOption();
        });
        this.$nextTick(() => {
          this.initWebSocket();
        });
      }
    },
    // 初始化方法，在此处建立websocket连接
    initWebSocket() {
      let protocol = window.location.protocol == 'http:' ? 'ws://' : 'wss://'; //window.location.host
      let path =
        protocol +
        window.location.host +
        '/bridge/websocket/' +
        this.$store.getters.getUserInfo.id;
      console.log(path);
      if (typeof WebSocket === 'undefined') {
        this.$message.warning('您的浏览器不支持socket');
      } else {
        // 实例化socket
        this.socket = new WebSocket(path);
        // 监听socket连接
        this.socket.onopen = this.open;
        // 监听socket错误信息
        this.socket.onerror = this.error;
        // 监听socket消息
        this.socket.onmessage = this.getMessage;
      }
    },
    open: function () {
      console.log('socket连接成功');
      let params = [];
      this.sensorList.map((item) => {
        params.push(item.sensorCoding);
      });
      this.socket.send(JSON.stringify(params));
    },
    error: function () {
      console.log('连接错误');
    },
    getMessage: function (msg) {
      //接收数据
      let _this = this;
      let data = JSON.parse(msg.data);
      // console.log(data);
      this.dataList.map((item) => {
        if (item.id == data.sensorCodeing || item.id == data.sensorCoding) {
          if (data.id && data.id == 7) {
            //称重传感器
            data.totalWeight = Number((data.totalWeight / 1000).toFixed(2));
            item.echartData.unit = 't';
            item.echartData.date = item.echartData.date.concat([
              data.samplingTime
            ]);
            let obj = {
              samplingTime: data.samplingTime,
              value: [data.samplingTime, data.totalWeight],
              sensorCoding: data.sensorCoding,
              licensePlate: data.licensePlate.trim(),
              singleShaftNuber: data.singleShaftNuber,
              modelName: this.getCarInfo(data.models)
            };
            item.echartData.data = item.echartData.data.concat([obj]);
            this.$nextTick(() => {
              let name = 'realEchart' + item.id;
              _this.$refs[name][0].setOptionPush();
            });
          } else {
            item.echartData.date = item.echartData.date.concat(
              data.samplingTime
            );
            item.echartData.data = item.echartData.data.concat(data.actual);
            this.$nextTick(() => {
              let name = 'realEchart' + item.id;
              _this.$refs[name][0].setOptionPush();
            });
          }
        }
      });
    },
    send: function () {
      this.socket.send('');
    },
    close: function () {
      console.log('socket已经关闭');
    },
    //获取轴数、车型
    getCarInfo(modelId) {
      let modelName = '';
      this.carList.map((item) => {
        if (item.id === modelId) {
          modelName = item.name;
        }
      });
      return modelName;
    }
  },
  mounted() {
    this.getRealList();
  },
  beforeDestroy() {
    if (this.socket != null) {
      this.socket.close();
      this.socket.onclose = this.close();
    }
    this.unDestory = false;
  }
};
</script>
<style lang="scss" scoped>
.realTime {
  // height: 70.834vh;
  height: 73.834vh;
  width: 100%;
  .realTimeBox {
    height: 100%;
    width: 100%;
    overflow: auto;
    .realItem {
      position: relative;
      .codeName {
        position: absolute;
        left: 0;
        top: 0;
        font-size: 16px;
        color: #000;
      }
    }
    &::-webkit-scrollbar {
      width: 8px;
    }
    &::-webkit-scrollbar-thumb {
      border-radius: 8px;
      background: #c4c4c4;
    }
  }
}
.noData {
  width: 100%;
  height: 100%;
  color: #333;
  font-size: 32px;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
