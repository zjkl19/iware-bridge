<template>
  <div class="my-map">
    <Amap
      ref="Amap"
      :zoom.sync="map.zoom"
      :center.sync="map.center"
      :mapStyle="map.mapStyle"
      :viewMode="'3D'"
      pitchEnable
      rotateEnable
      :animateEnable="false"
      :defaultCursor="defaultCursor"
      @mousedown="defaultCursor = 'grabbing'"
      @mouseup="defaultCursor = 'grab'"
      @zoomend="syncCenterAndZoom"
      @map-complete="onMapComplete"
    >
      <!-- 桥梁、隧道图标 -->
      <!-- <AmapMarker
        v-for="item in mapDataList"
        :key="item.id"
        :position="[item.longitude, item.latitude]"
        :icon="item.structureType == 1 ? markIcon : markIcon2"
        :label="{ content: item.name, direction: 'bottom' }"
        clickable
        :offset="[-15, -30]"
        @click="onMarkerClick(item)"
      ></AmapMarker> -->

      <AmapMarkerCluster
        key="custom-all"
        :data="structureList"
        :grid-size="60"
        :average-center="true"
      >
        <template v-slot:marker="point">
          <div
            class="markBox"
            @click="onMarkerClick(point)"
            style="width: 28px"
          >
            <img class="iconImg" :src="getPointIcon(point)" alt="" />
            <div v-if="map.zoom >= 15" class="markLabel">{{ point.name }}</div>
          </div>
        </template>
        <template v-slot:cluster="context">
          <div class="clusterBox" :style="getClusterStyle2(context)">
            <div
              class="ring"
              :class="getClusterStyle3(context)"
              :style="getClusterStyle4(context)"
            ></div>
            <div
              :style="getClusterStyle(context)"
              @click="clusterClick(context)"
            >
              {{ context.count }}
            </div>
          </div>
        </template>
      </AmapMarkerCluster>

      <AmapInfoWindow
        class="sample"
        :position="infoPosition"
        :visible="showinfoWindow"
        :offset="[5, -43]"
        auto-move
        isCustom
      >
        <div class="overlayBox">
          <div class="overlayTop">
            <span class="bridgeName">{{ contentInfo.name }}</span>
            <div class="bridgeOpt">
              <span
                v-if="contentInfo.structureType == 1"
                class="detail"
                @click="details"
                >详情</span
              >
              <span @click="showinfoWindow = false"
                ><i class="el-icon-close"></i
              ></span>
            </div>
          </div>
          <div class="overlayMid">
            <el-carousel
              v-if="bridgePhotoList.length > 0"
              :interval="4000"
              :autoplay="false"
              type="card"
              height="22.243vh"
            >
              <el-carousel-item v-for="item in bridgePhotoList" :key="item.id">
                <img :src="item.path" alt="" />
              </el-carousel-item>
            </el-carousel>
            <div v-else class="noData">
              <span><i class="el-icon-picture-outline"></i></span>
              <span>暂无图片</span>
            </div>
          </div>
          <div class="overlayBtm">
            <div class="overlayItem">
              <span class="title">{{
                contentInfo.structureType == 1 ? '桥梁类型' : '隧道类型'
              }}</span>
              <div class="text">
                <span
                  :class="{
                    textOver:
                      contentInfo.bridgeTypeName &&
                      contentInfo.bridgeTypeName.length > 6
                  }"
                  :title="contentInfo.bridgeTypeName"
                  >{{ contentInfo.bridgeTypeName || '-' }}</span
                >
              </div>
            </div>
            <div class="overlayItem">
              <span class="title">{{
                contentInfo.structureType == 1 ? '桥梁分类' : '隧道分类'
              }}</span
              ><span class="text">{{ contentInfo.spanType || '-' }}</span>
            </div>
            <div class="overlayItem">
              <span class="title">技术状况评级</span
              ><span class="text" :title="contentInfo.grade">{{
                contentInfo.grade || '-'
              }}</span>
            </div>
            <div class="overlayItem">
              <span class="title">管理单位</span>
              <div class="text">
                <span
                  :class="{
                    textOver:
                      contentInfo.runningDepartment &&
                      contentInfo.runningDepartment.length > 6
                  }"
                  :title="contentInfo.runningDepartment"
                  >{{ contentInfo.runningDepartment || '-' }}</span
                >
              </div>
            </div>
          </div>
        </div></AmapInfoWindow
      >
      <!-- 行政区域遮罩 -->
      <AmapdistrictProvince
        :opacity="0.2"
        :depth="2"
        :adcode="[350000]"
        :zooms="[3, 16.7]"
        :styles="province.styles1"
      ></AmapdistrictProvince>
      <AmapdistrictProvince
        v-if="map.zoom < 14"
        ref="layerProvince"
        :opacity="0.2"
        :depth="2"
        :adcode="adcode"
        :zooms="[3, 16.7]"
        :styles="province.styles2"
      ></AmapdistrictProvince>
    </Amap>
  </div>
</template>

<script>
import { getPhotoList } from '@/api/home/home';
import {
  Amap,
  Marker,
  MarkerCluster,
  infoWindow,
  districtLayerProvince
} from '@amap/amap-vue';
export default {
  name: 'homeMap-dzl',
  components: {
    Amap,
    AmapMarker: Marker,
    AmapInfoWindow: infoWindow,
    AmapMarkerCluster: MarkerCluster,
    AmapdistrictProvince: districtLayerProvince
  },
  props: {
    structureList: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      map: {
        zoom: 8,
        center: [119.306239, 26.075302],
        mapStyle: 'amap://styles/90f9e6c039e16c0d1ad7f6e77cb2b383'
      },
      defaultCursor: 'grab',
      mapDataList: [],
      showinfoWindow: false,
      infoPosition: [120.221378, 27.323578],
      contentInfo: {},
      bridgeTypeList: [
        { value: 1, label: '梁桥' },
        { value: 2, label: '拱桥' },
        { value: 3, label: '刚架拱桥' },
        { value: 4, label: '悬索桥' },
        { value: 5, label: '斜拉桥 ' },
        { value: 6, label: '钢管混凝土拱桥' }
      ],
      markIcon: require('@/assets/images/home/icon_bridge.png'),
      markIcon2: require('@/assets/images/home/icon_tunnel.png'),
      province: {
        styles1: {
          fill: '#419aff',
          'city-stroke': '#03a9f2' // 中国地级市边界
        },
        styles2: {
          fill: '#6eeeff',
          'county-stroke': '#6eeeff', // 中国区县边界
          'city-stroke': '#6eeeff' // 中国地级市边界
        }
      },
      adcode: [],
      bridgePhotoList: [],
      cityProvinceList: []
    };
  },
  watch: {
    structureList(val) {
      this.showinfoWindow = false;
    }
  },
  methods: {
    onMapComplete(map) {
      map.setFitView();
    },
    syncCenterAndZoom(e) {
      this.map.zoom = e.target.getZoom();
      // this.queryInRect();
    },
    //聚合操作
    getPointIcon(data) {
      // console.log(data);
      let icon;
      if (data.structureType == 1)
        icon = require('@/assets/images/home/icon_bridge.png');
      else icon = require('@/assets/images/home/icon_tunnel.png');
      return icon;
    },
    //聚合操作（聚合样式）
    getClusterStyle(context) {
      let size;
      if (context.count <= 100) {
        size = 36;
      } else if (context.count <= 200) {
        size = 42;
      } else {
        size = 48;
      }
      return {
        backgroundColor: `#419aff`,
        width: `${size}px`,
        height: `${size}px`,
        lineHeight: `${size}px`,
        borderRadius: `50%`,
        color: `#fff`,
        fontSize: '16px',
        fontFamily: 'Roboto',
        fontWeight: '500',
        textAlign: 'center'
      };
    },
    //聚合操作（位置）
    getClusterStyle2(context) {
      let size;
      if (context.count <= 100) {
        size = 36;
      } else if (context.count <= 200) {
        size = 42;
      } else {
        size = 48;
      }
      return {
        left: `-${size / 2}px`,
        top: `-${size / 2}px`
      };
    },
    //聚合操作(外圈动画)
    getClusterStyle3(context) {
      if (context.count <= 100) {
        return 'ringSize1';
      } else if (context.count <= 200) {
        return 'ringSize2';
      } else {
        return 'ringSize3';
      }
    },
    //聚合操作(外圈动画)
    getClusterStyle4(context) {
      let size;
      if (context.count <= 100) {
        size = 36;
      } else if (context.count <= 200) {
        size = 42;
      } else {
        size = 48;
      }
      return {
        width: `${size}px`,
        height: `${size}px`
      };
    },
    //聚合操作
    clusterClick(context) {
      this.map.center = [
        context.clusterData[0].lnglat.lng,
        context.clusterData[0].lnglat.lat
      ];
      this.map.zoom += 1;
    },
    //地图操作（移动、缩放）
    queryInRect() {
      // if (this.map.zoom > 13) {
      let bounds = this.$refs.Amap.$map.getBounds();
      let ne = bounds.northEast; //右上
      let sw = bounds.southWest; //左下
      let arry = [];
      this.structureList.map((item) => {
        if (
          item.longitude < ne.lng &&
          item.longitude > sw.lng &&
          item.latitude < ne.lat &&
          item.latitude > sw.lat
        ) {
          arry.push(item);
        }
      });
      // console.log(arry);
      this.mapDataList = arry.slice(0, 100);
      // }
    },
    onMarkerClick(data) {
      this.showinfoWindow = false;
      this.map.center = [data.longitude, data.latitude + 0.00015];
      this.map.zoom = 20;
      // if (data.structureType == '桥梁') {
      this.bridgeTypeList.map((item) => {
        if (item.id == data.bridgeType) {
          data.bridgeTypeName = item.label;
        }
      });
      this.contentInfo = { ...data };
      if (data.structureType == 1) {
        this.contentInfo.bridgeTypeName = this.bridgeTypeList.filter(
          (item) => item.value == data.bridgeType
        )[0].label;
      } else {
        this.contentInfo.bridgeTypeName = '-';
      }

      this.infoPosition = [data.longitude, data.latitude];
      this.getPhotoList(data.id, data.structureType);
      setTimeout(() => {
        // this.queryInRect();
        this.showinfoWindow = true;
      }, 1000);
    },
    //获取图片列表
    async getPhotoList(id, type) {
      let { code, data } = await getPhotoList(id, type);
      if (code == '0000') {
        data.list.map((item) => {
          item.path = this.$basePath + item.path;
        });
        this.bridgePhotoList = data.list;
      }
    },
    //点击详情
    details() {
      this.$router.push({
        path: '/bridgeModel',
        query: {
          id: this.contentInfo.id,
          bridgeName: this.contentInfo.name
        }
      });
    },
    cityGo(name, level) {
      this.cityProvinceList.map((item) => {
        if (item.name == name) {
          let position = item.center.split(',');
          this.map.center = [position[0], position[1]];
          this.adcode = [Number(item.adcode)];
        } else {
          item.districts.map((child) => {
            if (child.name == name) {
              let position = child.center.split(',');
              this.map.center = [position[0], position[1]];
              this.adcode = [Number(child.adcode)];
            } else {
              child.districts.map((son) => {
                if (son.name == name) {
                  let position = son.center.split(',');
                  this.map.center = [position[0], position[1]];
                  this.adcode = [Number(son.adcode)];
                }
              });
            }
          });
        }
      });
      if (level) {
        this.map.zoom = level;
      }
    },
    cityBack() {
      this.adcode = [];
      this.map.center = [119.306239, 26.075302];
      this.map.zoom = 8;
    },
    loadList(arry) {
      let list = JSON.parse(JSON.stringify(arry));
      this.mapDataList = list.slice(0, 100);
      // this.queryInRect();
    }
  },
  mounted() {
    // let list = JSON.parse(JSON.stringify(this.structureList));
    // this.mapDataList = list.slice(0, 100);
    let params = {
      key: 'b5b020ce5380b304261c2405c3143367',
      keywords: '福建',
      subdistrict: 3
    };
    this.$http({
      methods: 'get',
      url: 'https://restapi.amap.com/v3/config/district',
      params
    }).then((res) => {
      this.cityProvinceList = res.data.districts[0].districts;
    });
  }
};
</script>
<style lang="scss" scoped>
.my-map {
  height: 100%;
  width: 100%;
  .overlayBox {
    position: absolute;
    bottom: 0;
    width: 100%;
    height: 40.594vh;
    background-color: #1a3d67;
    padding: 1.669vh 24px;
    box-shadow: 0px 2px 8px 0px rgba(15, 15, 15, 0.36);
    border-radius: 8px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    &::after {
      position: absolute;
      left: 47.7%;
      bottom: -18px;
      content: '';
      border-width: 10px;
      border-style: solid dashed dashed dashed;
      border-color: #1a3d67 transparent transparent transparent;
    }
  }
  .overlayTop {
    height: 2.595vh;
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;
    .bridgeName {
      font-size: 16px;
      color: #fff;
    }
    .bridgeOpt {
      height: 100%;
      display: flex;
      align-items: center;
      .detail {
        height: 100%;
        width: 56px;
        font-size: 14px;
        color: #03a9f2;
        margin-right: 20px;
        border-radius: 2.595vh;
        border: solid 1px #03a9f2;
        display: flex;
        align-items: center;
        justify-content: center;
        cursor: pointer;
      }
      span:last-child {
        font-size: 24px;
        color: #a0aabc;
        cursor: pointer;
      }
    }
  }
  .overlayMid {
    // height: 26.692vh;
    height: 27.341vh;
    width: 100%;
    border-bottom: 1px solid #2f4970;
    .noData {
      height: 100%;
      width: 100%;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      span {
        color: #fff;
        font-size: 16px;
        &:first-child {
          font-size: 40px;
          padding: 5px 0;
        }
      }
    }
    /deep/ .el-carousel {
      width: 100%;
      height: 100%;
      display: flex;
      align-items: center;
      justify-content: space-evenly;
      flex-direction: column;
    }
    /deep/ .el-carousel__container {
      width: 100%;
      img {
        max-width: 100% !important;
        max-height: 100% !important;
      }
    }
    /deep/ .el-carousel__item--card.is-active {
      width: 80%;
      left: -55px;
    }
    /deep/ .el-carousel__item:nth-child(2n) {
      background-color: transparent;
      display: flex;
      align-items: center;
      justify-content: center;
    }
    /deep/ .el-carousel__item:nth-child(2n + 1) {
      background-color: transparent;
      display: flex;
      align-items: center;
      justify-content: center;
    }
    /deep/ .el-carousel__mask {
      background: transparent;
    }
    /deep/ .el-carousel__button {
      width: 8px;
      height: 8px;
      border-radius: 4px;
      opacity: 1;
    }
    /deep/ .el-carousel__indicators--outside button {
      background-color: #5a76a0;
    }
    /deep/ .el-carousel__indicator.is-active button {
      background-color: #419aff;
    }
  }
  .overlayBtm {
    height: 4.45vh;
    width: 100%;
    display: flex;
    align-items: center;
    .overlayItem {
      width: 25%;
      height: 100%;
      font-size: 14px;
      padding: 0 5px;
      border-right: 1px solid #2f4970;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: space-evenly;
      .title {
        color: rgba(255, 255, 255, 0.4);
      }
      .text {
        width: 100%;
        color: #fff;
        overflow: hidden;
        text-align: center;
        span {
          position: relative;
          white-space: nowrap;
        }
        .textOver {
          display: inline-block;
          padding-left: 100%;
          animation: textOverRoll 5s linear infinite;
        }
      }
      &:last-child {
        border: none;
      }
    }
  }
  .markBox {
    position: relative;
    // left: -6px;
    // width: 28px;
    display: flex;
    flex-direction: column;
    align-items: center;
    .markLabel {
      font-size: 14px;
      color: #fff;
      white-space: nowrap;
    }
  }
  .clusterBox {
    position: relative;
    .ring {
      position: absolute;
      left: 0;
      top: 0;
      border-radius: 50%;
      z-index: -1;
    }
    .ringSize1 {
      animation: rings 1.5s linear 1s infinite;
    }
    .ringSize2 {
      animation: rings 1.5s linear 0.5s infinite;
    }
    .ringSize3 {
      animation: rings 1.5s linear infinite;
    }
  }
  .iconImg {
    width: 28px;
    z-index: 10;
  }
  .markTextHide {
    /deep/ .amap-marker-label {
      display: none;
    }
  }
  /deep/ .amap-icon {
    width: 28px;
    height: 32px;
  }
  /deep/ .amap-info-contentContainer {
    width: 440px;
    height: 438px;
  }
  /deep/ .amap-marker-label {
    background: transparent;
    color: #fff;
    border: 0;
    font-size: 14px;
  }
  /deep/ .amap-logo,
  /deep/ .amap-copyright {
    display: none !important;
  }
}
//文字溢出滚动
@keyframes textOverRoll {
  from {
    transform: translateX(0);
  }
  to {
    transform: translateX(-100%);
  }
}
//圈扩散
@keyframes rings {
  from {
    background: rgba(65, 154, 255, 1);
    transform: scale(1);
  }
  to {
    background: rgba(65, 154, 255, 0);
    transform: scale(1.4);
  }
}
</style>
