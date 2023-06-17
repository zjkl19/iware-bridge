<template>
  <div class="briefReport">
    <div class="briefReportBox">
      <div class="briefReportTop">
        <div class="left">
          <span @click="backPage">{{ parentName }}</span
          ><span> / 巡查简报</span>
        </div>
        <div class="right" @click="backPage">
          <span><i class="iconfont icon-back"></i></span>
          <span>返回</span>
        </div>
      </div>
      <div class="briefReportBtmBox">
        <div v-if="dataInfo" class="briefReportBtm boxShadow">
          <div class="reportBoxOut">
            <div class="reportBox reportTop">
              <div class="reportTitle">
                <span>{{ reportTitle }}</span>
                <el-button class="btn" type="primary" @click="download"
                  >下载</el-button
                >
              </div>
              <div class="reportInfo">
                <div
                  v-for="item in reportInfoList"
                  :key="item.id"
                  class="infoItem"
                >
                  <span class="text">{{ item.text }}</span
                  ><span class="value">{{ item.value }}</span>
                </div>
                <div class="locationItem" @click="getMap">
                  <span class="location"
                    ><i class="el-icon-map-location"></i
                  ></span>
                  <span class="location">查看定位</span>
                </div>
              </div>
            </div>
            <div class="reportBox reportMid">
              <div class="midTop">
                <div class="checkTop">
                  <div class="left">
                    <span class="text checkPane">检查项</span>
                    <div class="split">
                      <span class="checkPane">结构名称</span>
                      <!-- <span class="checkPane"></span> -->
                      <span class="checkPane">检查内容</span>
                    </div>
                  </div>
                  <div class="right">
                    <span class="checkTitle checkPane">病害情况</span>
                    <span class="checkTitle checkPane">损害程度(数量)</span>
                    <span class="checkTitle2 checkPane">病害或异常说明</span>
                    <span class="checkTitle checkPane">备注</span>
                    <span class="checkTitle checkPane">病害图片</span>
                  </div>
                </div>
                <div class="checkBtm">
                  <!-- 桥铭牌、交通标志 -->
                  <div
                    v-for="(item, index) in checkList1"
                    :key="item.id"
                    class="checkRow"
                  >
                    <div class="rowOneLeft checkPane bgColor">
                      {{ item.damageType }}
                    </div>
                    <div
                      class="rowRight"
                      :class="{ bgColor: (index + 1) % 2 == 1 }"
                    >
                      <span
                        class="checkPane"
                        :class="{
                          txtRed: !norTxtList.includes(item.optionName)
                        }"
                        >{{ item.optionName }}</span
                      >
                      <span class="checkPane">{{
                        item.quantity != ''
                          ? item.quantity + ' (' + item.unit + ')'
                          : '-'
                      }}</span>
                      <span class="checkDes checkPane">{{
                        item.remarks || '-'
                      }}</span>
                      <span class="checkPane">{{
                        item.strategy || '观察'
                      }}</span>
                      <span
                        v-if="item.photo && item.photo.length > 0"
                        class="checkPane"
                        @click="showImage(item)"
                        ><i class="el-icon-picture-outline"></i
                      ></span>
                      <span v-else class="checkPane">-</span>
                    </div>
                  </div>
                  <!-- 桥面系 -->
                  <div class="checkRow" v-if="checkList2.length > 0">
                    <span class="rowTwo checkPane bgColor">桥面系</span>
                    <div class="checkTwoRight">
                      <div
                        v-for="(item, index) in checkList2"
                        :key="item.id"
                        class="twoItem"
                      >
                        <div v-if="item.list" class="twoItemBox">
                          <span
                            class="checkPane bgColor"
                            style="height: auto"
                            >{{ item.damageType }}</span
                          >
                          <div class="rowTwoRight">
                            <div
                              v-for="(child, childIndex) in item.list"
                              :key="child.id"
                              class="rowRight"
                              :class="{ bgColor: (childIndex + 1) % 2 == 1 }"
                            >
                              <span class="checkPane">{{
                                child.damageType
                              }}</span>
                              <span
                                class="checkPane"
                                :class="{
                                  txtRed: !norTxtList.includes(child.optionName)
                                }"
                                >{{ child.optionName }}</span
                              >
                              <span class="checkPane">{{
                                child.quantity != ''
                                  ? child.quantity + ' (' + child.unit + ')'
                                  : '-'
                              }}</span>
                              <span class="checkDes checkPane">{{
                                child.remarks || '-'
                              }}</span>
                              <span class="checkPane">{{
                                child.strategy || '观察'
                              }}</span>
                              <span
                                v-if="child.photo && child.photo.length > 0"
                                class="checkPane"
                                @click="showImage(child)"
                                ><i class="el-icon-picture-outline"></i
                              ></span>
                              <span v-else class="checkPane">-</span>
                            </div>
                          </div>
                        </div>
                        <div v-else class="twoItemBox">
                          <span class="checkPane bgColor single">{{
                            item.damageType
                          }}</span>
                          <div
                            class="rowRight"
                            :class="{ bgColor: (index + 1) % 2 == 1 }"
                          >
                            <span
                              class="checkPane"
                              :class="{
                                txtRed: !norTxtList.includes(item.optionName)
                              }"
                              >{{ item.optionName }}</span
                            >
                            <span class="checkPane">{{
                              item.quantity != ''
                                ? item.quantity + ' (' + item.unit + ')'
                                : '-'
                            }}</span>
                            <span class="checkDes checkPane">{{
                              item.remarks || '-'
                            }}</span>
                            <span class="checkPane">{{
                              item.strategy || '观察'
                            }}</span>
                            <span
                              v-if="item.photo && item.photo.length > 0"
                              class="checkPane"
                              @click="showImage(item)"
                              ><i class="el-icon-picture-outline"></i
                            ></span>
                            <span v-else class="checkPane">-</span>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <!-- 上部结构、桥墩桥台 -->
                  <!-- <div class="checkRow2">
                    <div
                      v-for="(item, index) in checkList3"
                      :key="item.id"
                      class="threeItem"
                    >
                      <span
                        class="checkPane bgColor single"
                        style="height: auto"
                        >{{ item.damageType }}</span
                      >
                      <div class="threeRight">
                        <div
                          v-for="(child, childIndex) in item.list"
                          :key="child.id"
                          class="rowRight"
                          :class="{
                            bgColor:
                              index == 0
                                ? (childIndex + 1) % 2 == 1
                                : (childIndex + 2) % 2 == 1
                          }"
                        >
                          <span class="checkPane">{{ child.damageType }}</span>
                          <span
                            class="checkPane"
                            :class="{
                              txtRed:
                                child.optionName != '无' && !!child.optionName
                            }"
                            >{{ child.optionName || '无' }}</span
                          >
                          <span class="checkPane">{{ child.quantity }}</span>
                          <span class="checkDes checkPane">{{
                            child.remarks
                          }}</span>
                          <span class="checkPane">{{ child.strategy }}</span>
                        </div>
                      </div>
                    </div>
                  </div> -->
                </div>
              </div>
              <div class="midTop">
                <div class="checkTop">
                  <div class="left">
                    <span class="text checkPane">检查项</span>
                    <div class="split">
                      <span class="checkPane">结构名称</span>
                      <span class="checkPane">检查内容</span>
                    </div>
                  </div>
                  <div class="right">
                    <span class="checkTitle checkPane">有无明显异常</span>
                    <span class="checkTitle checkPane">异常部位</span>
                    <span class="checkTitle2 checkPane">病害或异常说明</span>
                    <span class="checkTitle checkPane">备注</span>
                    <span class="checkTitle checkPane">病害图片</span>
                  </div>
                </div>
                <div class="checkBtm">
                  <!-- 上部结构、桥墩桥台 -->
                  <div class="checkRow2">
                    <div
                      v-for="(item, index) in checkList3"
                      :key="item.id"
                      class="threeItem"
                    >
                      <span
                        class="checkPane bgColor single"
                        style="height: auto"
                        >{{ item.damageType }}</span
                      >
                      <div class="threeRight">
                        <div
                          v-for="(child, childIndex) in item.list"
                          :key="child.id"
                          class="rowRight"
                          :class="{
                            bgColor:
                              index == 0
                                ? (childIndex + 1) % 2 == 1
                                : (childIndex + 2) % 2 == 1
                          }"
                        >
                          <span class="checkPane">{{ child.damageType }}</span>
                          <span
                            class="checkPane"
                            :class="{
                              txtRed: !norTxtList.includes(child.optionName)
                            }"
                            >{{ child.optionName }}</span
                          >
                          <span class="checkPane">{{
                            child.exceptionPart || '-'
                          }}</span>
                          <span class="checkDes checkPane">{{
                            child.remarks || '-'
                          }}</span>
                          <span class="checkPane">{{
                            child.strategy || '观察'
                          }}</span>
                          <span
                            v-if="child.photo && child.photo.length > 0"
                            class="checkPane"
                            @click="showImage(child)"
                            ><i class="el-icon-picture-outline"></i
                          ></span>
                          <span v-else class="checkPane">-</span>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="midBtm">
                <div class="checkItem">
                  <span class="left checkPane">检查项</span
                  ><span class="right checkPane">病害描述</span>
                </div>
                <div
                  v-for="(item, index) in checkInfo"
                  :key="item.id"
                  class="checkItem"
                  :class="{ bgColor: (index + 1) % 2 == 1 }"
                >
                  <span class="left checkPane">{{ item.damageType }}</span>
                  <span v-if="index != 1" class="right checkPane">{{
                    item.remarks || '-'
                  }}</span>
                  <span v-else class="right checkPane">{{
                    item.optionName || '-'
                  }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div v-else class="briefReportEmpty boxShadow">暂无巡查报告数据</div>
      </div>
    </div>
    <!-- 查看定位 -->
    <el-dialog
      class="mapView"
      title="查看定位"
      :visible.sync="showMap"
      :modal-append-to-body="false"
      @close="mapClose"
      width="40vw"
      :close-on-click-modal="false"
    >
      <!-- <div class="location">
        <span>位置：</span>
        <span>{{ location }}</span>
      </div> -->
      <div class="mapStyle">
        <Amap
          :class="{ markTextHide: map.zoom < 14 }"
          :zoom.sync="map.zoom"
          :center.sync="map.center"
          :viewMode="'3D'"
          :skyColor="'#419aff'"
          pitchEnable
          rotateEnable
          :defaultCursor="defaultCursor"
          @mousedown="defaultCursor = 'grabbing'"
          @mouseup="defaultCursor = 'grab'"
          @zoomend="syncCenterAndZoom"
          @map-complete="onMapComplete"
        >
          <!-- 桥梁、隧道图标 -->
          <AmapMarker
            :position="map.position"
            :icon="markIcon"
            :label="{
              content: location,
              direction: 'bottom'
            }"
            :offset="[0, 0]"
            clickable
          ></AmapMarker>
        </Amap>
      </div>
    </el-dialog>
    <!-- 查看图片弹框 -->
    <el-dialog
      class="dialogImage"
      title="病害照片"
      :visible.sync="dialogImgShow"
      :modal-append-to-body="false"
      :close-on-click-modal="false"
    >
      <el-carousel
        class="diseasePhoto"
        :interval="4000"
        trigger="click"
        type="card"
      >
        <el-carousel-item v-for="item in dialogImageUrlList" :key="item.id">
          <img :src="item.path" alt="" />
        </el-carousel-item>
      </el-carousel>
    </el-dialog>
  </div>
</template>

<script>
import { Amap, Marker } from '@amap/amap-vue';
import { getOnePlanDetail } from '@/api/normal/plan';
import {
  getDiseaseList,
  download,
  getInspectionDisease
} from '@/api/normal/record';
export default {
  name: 'normalBrief',
  components: {
    Amap,
    AmapMarker: Marker
  },
  props: ['reportId', 'structureId', 'parentName'],
  data() {
    return {
      reportTitle: '',
      // parentName: '',
      location: '',
      dataInfo: {},
      //报告信息列表
      reportInfoList: [
        { id: 1, text: '桥梁名称：', value: '' },
        { id: 2, text: '巡检日期：', value: '' },
        { id: 3, text: '天气：', value: '' },
        {
          id: 4,
          text: '巡检单位：',
          value: ''
        },
        { id: 5, text: '巡检人：', value: '' }
      ],
      //检查项、损坏描述列表
      checkList1: [
        // {
        //   id: 1,
        //   damageType: '桥名牌',
        //   optionName: '完整',
        //   quantity: '',
        //   remarks: '',
        //   strategy: '',
        //   unit: '块'
        // },
        // {
        //   id: 2,
        //   damageType: '限载牌',
        //   optionName: '完整',
        //   quantity: '',
        //   remarks: '',
        //   strategy: '',
        //   unit: '块'
        // }
      ],
      checkList2: [
        // {
        //   id: 1,
        //   damageType: '车行道',
        //   list: [
        //     {
        //       id: 11,
        //       damageType: '碎裂',
        //       optionName: '完好',
        //       quantity: '',
        //       remarks: '',
        //       strategy: '',
        //       unit: '㎡'
        //     },
        //     {
        //       id: 12,
        //       damageType: '局部坑槽',
        //       optionName: '完好',
        //       quantity: '',
        //       remarks: '',
        //       strategy: '',
        //       unit: '㎡'
        //     },
        //     {
        //       id: 13,
        //       damageType: '拥包',
        //       optionName: '平整',
        //       quantity: '',
        //       remarks: '',
        //       strategy: '',
        //       unit: '处'
        //     },
        //     {
        //       id: 14,
        //       damageType: '车辙',
        //       optionName: '平整',
        //       quantity: '',
        //       remarks: '',
        //       strategy: '',
        //       unit: '处'
        //     },
        //     {
        //       id: 15,
        //       damageType: '桥头跳车',
        //       optionName: '平顺',
        //       quantity: '',
        //       remarks: '',
        //       strategy: '',
        //       unit: '处'
        //     },
        //     {
        //       id: 16,
        //       damageType: '隔离栏',
        //       optionName: '完整',
        //       quantity: '',
        //       remarks: '',
        //       strategy: '',
        //       unit: 'm'
        //     }
        //   ]
        // },
        // {
        //   id: 2,
        //   damageType: '人行道',
        //   list: [
        //     {
        //       id: 21,
        //       damageType: '板件',
        //       optionName: '完整',
        //       quantity: '',
        //       remarks: '',
        //       strategy: '',
        //       unit: '㎡'
        //     },
        //     {
        //       id: 22,
        //       damageType: '铺装(砖)',
        //       optionName: '平整',
        //       quantity: '',
        //       remarks: '',
        //       strategy: '',
        //       unit: '㎡'
        //     }
        //   ]
        // },
        // {
        //   id: 3,
        //   damageType: '栏杆',
        //   list: [
        //     {
        //       id: 31,
        //       damageType: '栏杆扶手',
        //       optionName: '完整',
        //       quantity: '',
        //       remarks: '',
        //       strategy: '',
        //       unit: 'm'
        //     },
        //     {
        //       id: 32,
        //       damageType: '栏杆立柱',
        //       optionName: '完整',
        //       quantity: '',
        //       remarks: '',
        //       strategy: '',
        //       unit: '只'
        //     }
        //   ]
        // },
        // {
        //   id: 4,
        //   damageType: '伸缩缝装置',
        //   list: [
        //     {
        //       id: 41,
        //       damageType: '缺损',
        //       optionName: '完整',
        //       quantity: '',
        //       remarks: '',
        //       strategy: '',
        //       unit: 'm'
        //     },
        //     {
        //       id: 42,
        //       damageType: '阻塞',
        //       optionName: '正常',
        //       quantity: '',
        //       remarks: '',
        //       strategy: '',
        //       unit: '只'
        //     }
        //   ]
        // },
        // {
        //   id: 5,
        //   damageType: '泄水孔',
        //   optionName: '畅通',
        //   quantity: '',
        //   remarks: '',
        //   strategy: '',
        //   unit: '只'
        // }
      ],
      checkList3: [
        // {
        //   id: 1,
        //   damageType: '上部结构',
        //   list: [
        //     {
        //       id: 1,
        //       damageType: '主梁(整体完好情况、振动)',
        //       optionName: '正常',
        //       quantity: '',
        //       remarks: '',
        //       strategy: ''
        //     },
        //     {
        //       id: 2,
        //       damageType: '横向联系',
        //       optionName: '正常',
        //       quantity: '',
        //       remarks: '',
        //       strategy: ''
        //     },
        //     {
        //       id: 3,
        //       damageType: '拱肋(拱脚区、钢管节点)',
        //       optionName: '正常',
        //       quantity: '',
        //       remarks: '',
        //       strategy: ''
        //     },
        //     {
        //       id: 4,
        //       damageType: '吊杆(上下锚头、振动)',
        //       optionName: '正常',
        //       quantity: '',
        //       remarks: '',
        //       strategy: ''
        //     },
        //     {
        //       id: 5,
        //       damageType: '上部其他变异',
        //       optionName: '正常',
        //       quantity: '',
        //       remarks: '',
        //       strategy: ''
        //     }
        //   ]
        // },
        // {
        //   id: 2,
        //   damageType: '桥墩、桥台、附属物',
        //   list: [
        //     {
        //       id: 1,
        //       damageType: '桥墩/桥台',
        //       optionName: '正常',
        //       quantity: '',
        //       remarks: '',
        //       strategy: ''
        //     },
        //     {
        //       id: 2,
        //       damageType: '锥坡',
        //       optionName: '正常',
        //       quantity: '',
        //       remarks: '',
        //       strategy: ''
        //     },
        //     {
        //       id: 3,
        //       damageType: '下部其他变异',
        //       optionName: '正常',
        //       quantity: '',
        //       remarks: '',
        //       strategy: ''
        //     }
        //   ]
        // }
      ],
      //检查项、病害描述
      checkInfo: [
        {
          id: 1,
          damageType: '桥、桥区施工',
          optionName: '',
          quantity: '',
          remarks: '',
          strategy: ''
        },
        {
          id: 2,
          damageType: '其他危及行车、行船、行人安全的病害因素',
          optionName: '',
          quantity: '',
          remarks: '',
          strategy: ''
        },
        {
          id: 3,
          damageType: '其他说明',
          optionName: '',
          quantity: '',
          remarks: '',
          strategy: ''
        }
      ],
      norTxtList: ['完好', '完整', '平整', '平顺', '通畅', '正常'],
      //查看定位
      map: {
        zoom: 17,
        center: [],
        position: []
      },
      defaultCursor: 'grab',
      markIcon: require('@/assets/images/home/location.png'),
      showMap: false,
      //异常图片弹框
      dialogImgShow: false,
      dialogImageUrlList: []
    };
  },
  beforeMount() {
    this.$utils.getAuthPage(this, 58); //获取权限
    // this.reportId = this.$route.query.id;
    // this.parentName = this.$route.query.parentName;
  },
  mounted() {
    // this.dataInfo = dataObject[this.reportId];
    this.getInspectionDisease();
    // this.getOnePlanDetail();
    // this.getDiseaseList();
  },
  methods: {
    //获取病害列表
    async getInspectionDisease() {
      let { code, data } = await getInspectionDisease(this.structureId);
      if (code == '0000') {
        let checkList1 = [];
        let checkList2 = [];
        let checkList3 = [];
        let checkInfo = [];
        //桥名牌、限载牌
        for (let i = 0; i < data.list1.length; i++) {
          let obj = {
            id: data.list1[i].list[0].list[0].id,
            damageType: data.list1[i].list[0].list[0].damageType,
            optionName: data.list1[i].list[0].list[0].optionName,
            quantity: '',
            remarks: '',
            strategy: '',
            unit: data.list1[i].list[0].list[0].unit
          };
          checkList1.push(obj);
        }
        this.checkList1 = checkList1;
        //桥面系
        let list2 = data.list2;
        let check2Obj = {
          id: list2[0].list[0].id,
          damageType: list2[0].list[0].damageType,
          list: []
        };
        for (let i = 0; i < list2.length; i++) {
          if (i + 1 < list2.length) {
            for (let j = 0; j < list2[i].list[0].list.length; j++) {
              let obj = {
                id: list2[i].list[0].list[j].id,
                damageType: list2[i].list[0].list[j].damageType,
                optionName: list2[i].list[0].list[j].optionName,
                quantity: '',
                remarks: '',
                strategy: '',
                unit: list2[i].list[0].list[j].unit
              };
              check2Obj.list.push(obj);
            }
            if (
              list2[i].list[0].damageType != list2[i + 1].list[0].damageType
            ) {
              checkList2.push(check2Obj);
              check2Obj = {
                id: list2[i + 1].list[0].id,
                damageType: list2[i + 1].list[0].damageType,
                list: []
              };
            }
          } else {
            if (list2[i].list[0].damageType == '泄水孔') {
              check2Obj = {
                id: list2[i].list[0].list[0].id,
                damageType: list2[i].list[0].list[0].damageType,
                optionName: list2[i].list[0].list[0].optionName,
                quantity: '',
                remarks: '',
                strategy: '',
                unit: list2[i].list[0].list[0].unit
              };
            } else {
              for (let j = 0; j < list2[i].list[0].list.length; j++) {
                let obj = {
                  id: list2[i].list[0].list[j].id,
                  damageType: list2[i].list[0].list[j].damageType,
                  optionName: list2[i].list[0].list[j].optionName,
                  quantity: '',
                  remarks: '',
                  strategy: '',
                  unit: list2[i].list[0].list[j].unit
                };
                check2Obj.list.push(obj);
              }
            }

            checkList2.push(check2Obj);
          }
        }
        this.checkList2 = checkList2;
        //上部结构、下部结构
        let list3 = data.list3;
        let check3Obj = {
          id: list3[0].list[0].id,
          damageType: list3[0].list[0].damageType,
          list: []
        };
        for (let i = 0; i < list3.length; i++) {
          if (i + 1 < list3.length) {
            for (let j = 0; j < list3[i].list[0].list.length; j++) {
              let obj = {
                id: list3[i].list[0].list[j].id,
                damageType: list3[i].list[0].list[j].damageType,
                optionName: list3[i].list[0].list[j].optionName,
                quantity: '',
                remarks: '',
                strategy: '',
                unit: list3[i].list[0].list[j].unit
              };
              check3Obj.list.push(obj);
            }
            if (
              list3[i].list[0].damageType != list3[i + 1].list[0].damageType
            ) {
              checkList3.push(check3Obj);
              check3Obj = {
                id: list3[i + 1].list[0].id,
                damageType: list3[i + 1].list[0].damageType,
                list: []
              };
            }
          } else {
            for (let j = 0; j < list3[i].list[0].list.length; j++) {
              let obj = {
                id: list3[i].list[0].list[j].id,
                damageType: list3[i].list[0].list[j].damageType,
                optionName: list3[i].list[0].list[j].optionName,
                quantity: '',
                remarks: '',
                strategy: '',
                unit: list3[i].list[0].list[j].unit
              };
              check3Obj.list.push(obj);
            }
            checkList3.push(check3Obj);
          }
        }
        this.checkList3 = checkList3;
        //检查项、病害描述
        for (let i = 0; i < data.list4.length; i++) {
          let obj = {
            id: data.list4[i].list[0].list[0].id,
            damageType: data.list4[i].list[0].list[0].damageType,
            optionName: data.list4[i].list[0].list[0].optionName,
            quantity: '',
            remarks: '',
            strategy: '',
            unit: data.list4[i].list[0].list[0].unit
          };
          checkInfo.push(obj);
        }
        this.checkInfo = checkInfo;
        this.$nextTick(() => {
          this.getOnePlanDetail();
          this.getDiseaseList();
        });
      }
    },
    // 获取单个巡查计划细项
    async getOnePlanDetail() {
      let { code, data } = await getOnePlanDetail(this.reportId);
      if (code == '0000') {
        let time = data.inspectionTime.split(' ')[0];
        this.reportInfoList[0].value = data.structureName || '/';
        this.reportInfoList[1].value = time || '/';
        this.reportInfoList[2].value = data.weather || '/';
        this.reportInfoList[3].value = data.inspectionUnit || '/';
        this.reportInfoList[4].value = data.inspector || '/';
        this.checkInfo.map((item) => {
          if (item.damageType == '其他说明')
            item.remarks = data.otherRemark || '';
        });
        this.reportTitle = data.structureName + time + '日常巡查日报表';
        this.location = data.location || '/';
        this.map.center = [data.longitude, data.latitude];
        this.map.position = [data.longitude, data.latitude];
      }
    },
    // 获取病害情况
    async getDiseaseList() {
      let { code, data } = await getDiseaseList(this.reportId, {});
      if (code == '0000') {
        data.map((item) => {
          this.updDiseList(this.checkList1, item);
          this.updDiseList(this.checkList2, item);
          this.updDiseList(this.checkList3, item);
        });
        this.updDiseList2(data);
      }
    },
    updDiseList(arr, obj) {
      let nameList = ['', '观察', '报修', '监测', '即修', '更换', '增设'];
      arr.map((item, i) => {
        if (!item.list) {
          if (item.damageType == obj.damageType) {
            // console.log(obj, item);
            // item = { ...obj };
            obj.strategy = nameList[obj.strategy];
            this.$set(arr, i, obj);
          }
        } else {
          item.list.map((item2, j) => {
            if (
              item2.damageType == obj.damageType &&
              item.damageType == obj.checkItem
            ) {
              // item2 = { ...obj };
              obj.strategy = nameList[obj.strategy];
              this.$set(arr[i].list, j, obj);
            }
          });
        }
      });
      // console.log(arr);
    },
    updDiseList2(arr) {
      let optionNameList = [];
      arr.map((item) => {
        if (item.damageType == '桥、桥区施工') {
          this.$set(this.checkInfo, 0, item);
        }
        if (item.damageType == '其他危及行车、行船、行人安全的病害因素') {
          optionNameList.push(item.optionName);
        }
      });
      this.checkInfo[1].optionName = optionNameList.join(',');
      // console.log(arr);
    },
    //下载
    download() {
      download(this.reportId)
        .then((res) => {
          let fileName = this.reportTitle + '.docx';
          this.$utils.downloadBlob(res, fileName);
        })
        .cath(() => {});
    },
    //返回
    backPage() {
      // this.$router.go(-1);
      this.$emit('closed');
    },
    //查看定位
    getMap() {
      this.showMap = true;
    },
    //查看图片
    showImage(item) {
      if (item.photo.length == 0) {
        this.$message({
          message: '暂无病害图片！',
          type: 'warning',
          showClose: true,
          duration: 2000
        });
        return;
      }
      item.photo.map((child) => {
        child.path = this.$basePath + child.path;
      });
      this.dialogImageUrlList = item.photo;
      this.dialogImgShow = true;
    },
    //关闭地图
    mapClose() {
      this.showMap = false;
    },
    onMapComplete(map) {
      map.setFitView();
    },
    syncCenterAndZoom(e) {
      this.map.zoom = e.target.getZoom();
    }
  }
};
</script>

<style></style>

<style lang="scss" scoped>
.briefReport {
  position: absolute;
  top: 6.488vh;
  left: 0;
  width: 100%;
  height: 93.513vh;
  // padding-top: 6.488vh;
  background: #f6f7fb;
  z-index: 99;
  .briefReportBox {
    width: 100%;
    // padding: 1.855vh 0;
    display: flex;
    flex-direction: column;
    .briefReportTop {
      height: 5.186vh;
      // margin-bottom: 3.71vh;
      padding: 0 36px 0 28px;
      background: #fff;
      display: flex;
      align-items: center;
      justify-content: space-between;
      .left {
        display: flex;
        align-items: center;
        span {
          font-size: 0.8vw;
          color: #262626;
          white-space: pre-wrap;
          &:first-child {
            font-weight: bold;
            cursor: pointer;
            &:hover {
              color: #419aff;
            }
          }
        }
      }
      .right {
        color: #419aff;
        display: flex;
        align-items: center;
        cursor: pointer;
        span {
          margin-left: 5px;
        }
      }
    }
    .briefReportBtmBox {
      width: 100%;
      height: 88.334vh;
      padding: 1.855vh 28px;
      overflow: hidden;
      .briefReportBtm {
        padding: 32px;
        height: 100%;
        overflow: hidden;
        .reportBoxOut {
          height: 100%;
          display: flex;
          align-items: center;
          flex-direction: column;
          overflow-y: auto;
          .reportBox {
            // width: 1280px;
            width: 100%;
          }
        }
      }
      .briefReportEmpty {
        width: 100%;
        height: 83.513vh;
        color: #333;
        display: flex;
        align-items: center;
        justify-content: center;
      }
    }
  }
}
.briefReportBtm {
  .reportTop {
    margin-bottom: 32px;
    display: flex;
    flex-direction: column;
    .reportTitle {
      position: relative;
      height: 40px;
      font-size: 24px;
      color: #262626;
      font-weight: bold;
      margin-bottom: 32px;
      display: flex;
      align-items: center;
      justify-content: center;
      .btn {
        position: absolute;
        top: 0;
        right: 0;
        display: flex;
      }
    }
    .reportInfo {
      width: 100%;
      font-size: 16px;
      display: flex;
      align-items: center;
      justify-content: space-between;
      .text {
        color: #262626;
      }
      .value {
        color: #999;
      }
      .locationItem {
        cursor: pointer;
        span {
          color: #419aff;
          margin: 0 4px;
        }
      }
    }
  }
  .reportMid {
    direction: flex;
    flex-direction: column;
    .midTop {
      border-top: 1px solid #d9d9d9;
      border-left: 1px solid #d9d9d9;
      margin-bottom: 32px;
      display: flex;
      flex-direction: column;
      .checkTop {
        width: 100%;
        display: flex;
        .left {
          width: 495px;
          display: flex;
          flex-direction: column;
          .text {
            width: 100%;
            height: 5.186vh;
          }
          .split {
            width: 100%;
            display: flex;
            span {
              width: 33.33%;
              height: 5.186vh;
              &:first-child {
                width: 66.66%;
              }
            }
          }
        }
        .right {
          flex: 1;
          display: flex;
          .checkTitle {
            width: 165px;
          }
          .checkTitle2 {
            flex: 1;
          }
          span {
            height: 10.371vh;
            // height: 5.186vh;
          }
        }
      }
      .checkBtm {
        display: flex;
        flex-direction: column;
        .checkRow {
          display: flex;
          .rowOneLeft {
            width: 495px;
          }
          .rowTwo {
            height: auto;
          }
          .checkTwoRight {
            flex: 1;
            display: flex;
            flex-direction: column;
            .twoItem {
              width: 100%;
              .twoItemBox {
                width: 100%;
                display: flex;
              }
              .rowTwoRight {
                flex: 1;
                display: flex;
                flex-direction: column;
              }
            }
          }
          .rowRight {
            flex: 1;
            display: flex;
          }
          .checkDes {
            flex: 1;
          }
          .txtRed {
            color: #ff5f5f;
          }
          i {
            font-size: 24px;
            cursor: pointer;
          }
        }
        .checkRow2 {
          display: flex;
          flex-direction: column;
          .threeItem {
            display: flex;
            // .checkPane {
            //   // width: 330px;
            // }
            .threeRight {
              flex: 1;
              display: flex;
              flex-direction: column;
            }
          }
          .rowRight {
            flex: 1;
            display: flex;
          }
          .checkDes {
            flex: 1;
          }
          .txtRed {
            color: #ff5f5f;
          }
          i {
            font-size: 24px;
            cursor: pointer;
          }
        }
      }
    }
    .midBtm {
      border-top: 1px solid #d9d9d9;
      border-left: 1px solid #d9d9d9;
      display: flex;
      flex-direction: column;
      .checkItem {
        display: flex;
        .left {
          width: 660px;
        }
        .right {
          flex: 1;
        }
      }
    }
    .bgColor {
      background: #f4f4f4;
    }
  }
}

//表格样式
.checkPane {
  width: 165px;
  // height: 5.186vh;
  font-size: 16px;
  padding: 1.575vh 12px;
  color: #333;
  text-align: center;
  border-right: 1px solid #d9d9d9;
  border-bottom: 1px solid #d9d9d9;
  display: flex;
  align-items: center;
  justify-content: center;
  overflow-wrap: anywhere;
}
.single {
  width: 330px;
}

//查看定位
.mapView {
  .location {
    display: flex;
    align-items: center;
  }
  .mapStyle {
    width: 100%;
    height: 66.407vh;
    margin: 10px 0 20px;
    .markTextHide {
      /deep/ .amap-marker-label {
        display: none;
      }
    }
    /deep/ .amap-marker-label {
      color: rgb(53, 96, 255);
      font-size: 14px;
      font-weight: bold;
      border: 0;
      text-shadow: 1px 1px #fff, -1px -1px #fff, -1px 1px #fff, 1px -1px #fff;
      background-color: transparent;
    }
    /deep/ .amap-logo,
    /deep/ .amap-copyright {
      display: none !important;
    }
  }
  /deep/ .el-dialog__body {
    padding: 0 24px;
  }
}
//查看图片弹框
.dialogImage {
  .diseasePhoto {
    height: 360px;
    /deep/ .el-carousel__container {
      height: 333px;
    }
    /deep/ .el-carousel__item {
      display: flex;
      align-items: center;
      justify-content: center;
    }
    img {
      max-height: 333px;
      max-width: 360px;
    }
  }
  .empty {
    height: 360px;
    background: #e5e5e5;
    display: flex;
    align-items: center;
    flex-direction: column;
    justify-content: center;
    .icon {
      font-size: 38px;
    }
    .text {
      color: #333;
      font-size: 24px;
    }
  }
  /deep/ .el-dialog {
    margin: 0;
    width: 768px;
  }
  /deep/ .el-dialog__body {
    padding: 1.2vw !important;
  }
}
</style>
