<template>
  <div class="original">
    <div class="haveData">
      <div class="oriLeft">
        <div class="title">{{ structureManage.structureName }}</div>
        <div class="leftMid">
          <div class="midItem">
            <span class="tips">检测仪器：</span>
            <el-input
              v-model="oriInfo.instrumentation"
              clearable
              :disabled="haveData || emptyData"
            ></el-input>
          </div>
          <div class="midItem">
            <span class="tips">
              <p style="color: #ff5f5f">*</p>
              工程地点：
            </span>
            <el-input
              v-model="oriInfo.projectLocation"
              clearable
              :disabled="haveData || emptyData"
              :title="oriInfo.projectLocation"
            ></el-input>
          </div>
          <div class="midItem">
            <span class="tips">检测依据：</span>
            <el-input
              v-model="oriInfo.testBasis"
              clearable
              :disabled="haveData || emptyData"
            ></el-input>
          </div>
          <div class="midItem">
            <span class="tips">记录编号：</span>
            <el-input
              v-model="oriInfo.recordNumber"
              clearable
              :disabled="haveData || emptyData"
            ></el-input>
          </div>
        </div>
        <div v-if="haveData" class="expBtn" @click="exportRecord">导出记录</div>
        <el-button
          v-else
          class="expBtn"
          type="primary"
          :disabled="emptyData"
          @click="insertRecord"
          >生成记录</el-button
        >
      </div>
      <div v-if="haveData" class="oriRight">
        <div class="title">检测记录表</div>
        <div class="tableBox">
          <div class="tableTop">
            <div class="tbTopItem">
              <span>工程名称</span>
              <!-- <span style="border-right: 1px solid #666666" 
                >{{ structureManage.projectName }}-{{
                  structureManage.structureName
                }}</span
              > -->
              <span style="border-right: 1px solid #666666"
                >{{ oriInfo.projectName }}-{{ oriInfo.structureName }}</span
              >
            </div>
            <div class="tbTopItem">
              <span>工程地点</span>
              <span>{{ oriInfo.projectLocation }}</span>
            </div>
          </div>
          <div class="tableBtm">
            <div v-for="(item, index) in oriList" :key="index" class="oriItem">
              <div class="itemTop">
                <span>{{ item.roadName }}</span> > <span>{{ item.type }}</span>
              </div>
              <div v-if="item.photoUrls.length > 0" class="itemMid">
                <span class="picNum">共{{ item.photoUrls.length }}张图片</span>
                <el-image
                  :src="item.photoUrls[0]"
                  :preview-src-list="item.photoUrls"
                >
                  <div slot="error" class="image-slot">
                    <i class="el-icon-picture-outline"></i>
                    <span>加载失败</span>
                  </div>
                </el-image>
                <!-- <img :src="item.imgUrl"
                     alt=""> -->
              </div>
              <div class="itemBtm">
                <div v-for="child in item.list" :key="child.id">
                  {{ child.text }}
                </div>
              </div>
            </div>
            <div v-if="oriList.length == 0" class="emptyOri">
              暂无病害数据！
            </div>
          </div>
        </div>
      </div>
      <div v-else class="noData">
        <img src="@/assets/images/assessment/noRecord.png" alt="" />
        <span>暂未生成记录</span>
      </div>
    </div>
  </div>
</template>
<script>
import {
  insertRecord,
  exportRecord,
  getOriginalRecord,
  getExportRecord
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
        this.getOriginalRecord();
      }
    }
  },
  data() {
    return {
      oriInfo: {
        instrumentation: '',
        projectLocation: '',
        testBasis: '',
        recordNumber: ''
      },
      oriList: [
        // {
        //   spanName: 1,
        //   roadName: 'A桥左幅',
        //   type: '桥面系1',
        //   photoUrls: [require('@/assets/images/or.png')],
        //   list: [
        //     {
        //       id: 1,
        //       text: '编号：14-1，1#跨伸缩装置，缝内沉积物阻塞，程度:严重，位置：（8.02，14.14）'
        //     },
        //     {
        //       id: 2,
        //       text: '编号：122-3，1#跨桥头平顺，其他病害，备注：桥头跳车严重，位置：（7.83，10.28）'
        //     }
        //   ]
        // }
      ],
      haveData: false,
      emptyData: false
    };
  },
  methods: {
    //获取导出数据
    async getExportRecord() {
      let { code, data } = await getExportRecord(this.structureManage.id);
      if (code == '0000') {
        //处理图片数组
        data.map((item) => {
          let arry = [];
          if (item.photoUrls.length > 0) {
            item.photoUrls.map((child) => {
              arry.push(child.path);
            });
          }
          item.photoUrls = arry;
        });
        this.oriList = data;
      }
    },
    //获取结构物原始记录
    async getOriginalRecord() {
      let { code, data } = await getOriginalRecord(this.structureManage.id);
      if (code == '0000') {
        if (!!data) {
          this.oriInfo = data;
          this.haveData = true;
          await this.getExportRecord();
        } else {
          this.oriInfo = {
            instrumentation: '',
            projectLocation: '',
            testBasis: '',
            recordNumber: ''
          };
          this.haveData = false;
        }
      }
    },
    //生成记录
    async insertRecord() {
      let reg = /[\u4e00-\u9fa5]/;
      if (this.oriInfo.projectLocation == '') {
        this.$message({
          message: '工程地点不能为空！',
          showClose: true,
          type: 'warning',
          duration: 3000
        });
        return;
      }
      if (
        this.oriInfo.recordNumber != '' &&
        reg.test(this.oriInfo.recordNumber)
      ) {
        this.$message({
          message: '记录编号不能为汉字！',
          showClose: true,
          type: 'warning',
          duration: 3000
        });
        return;
      }
      let params = this.oriInfo;
      params.id = this.structureManage.id;
      let { code } = await insertRecord(params);
      if (code == '0000') {
        this.$message({
          message: '生成记录成功！',
          showClose: true,
          type: 'success',
          duration: 3000
        });
        this.haveData = true;
        this.$emit('reflash', 'original');
        await this.getOriginalRecord();
      }
    },
    //导出记录
    async exportRecord() {
      let params = {
        id: this.structureManage.id
      };
      let { code, data } = await exportRecord(params);
      if (code == '0000') {
        if (!!data) {
          window.open(data);
        }
      }
    }
  },
  mounted() {
    if (this.structureManage.id) {
      this.getOriginalRecord();
    } else {
      this.emptyData = true;
    }
  }
};
</script>
<style lang="scss" scoped>
.original {
  width: 100%;
  height: 100%;
  .haveData {
    width: 100%;
    height: 100%;
    display: flex;
    .oriLeft {
      width: 15.834vw;
      height: 100%;
      padding: 2.5vh 0;
      border-right: 1px solid #e8e8e8;
      .title {
        font-size: 0.7vw;
        color: #333;
        font-weight: bold;
        padding-bottom: 0.7vw;
      }
      .leftMid {
        width: 100%;
        height: 32.624vh;
        padding-right: 1.2vw;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        .midItem {
          height: 25%;
          display: flex;
          flex-direction: column;
          justify-content: center;
          .tips {
            font-size: 0.7vw;
            color: #333;
            padding-bottom: 8px;
            display: flex;
            align-items: center;
          }
        }
      }
      .expBtn {
        width: 4.584vw;
        height: 3.708vh;
        background: #419aff;
        border-radius: 4px;
        color: #fff;
        font-size: 0.7vw;
        margin-top: 0.7vw;
        display: flex;
        align-items: center;
        justify-content: center;
        cursor: pointer;
        &:hover {
          background: rgba(65, 154, 255, 0.8);
        }
      }
      /deep/ .el-button--primary.is-disabled {
        border: 0;
        background: rgba(65, 154, 255, 0.8);
        cursor: not-allowed;
      }
    }
    .oriRight {
      // padding: 1.2vw 1.2vw 0;
      // padding: 1.2vw 0 0 1.2vw;
      padding-left: 1.2vw;
      display: flex;
      flex-direction: column;
      flex: 1;
      .title {
        // height: 6.674vh;
        font-size: 1vw;
        color: #333;
        font-weight: bold;
        display: flex;
        align-items: center;
        justify-content: center;
        flex: 1;
      }
      .tableBox {
        width: 100%;
        height: 68.583vh;
        border: 1px solid #666666;
        display: flex;
        flex-direction: column;
        .tableTop {
          width: 100%;
          // height: 5.123vh;
          display: flex;
          align-items: center;
          .tbTopItem {
            width: 50%;
            height: 100%;
            border-bottom: 1px solid #666666;
            display: flex;
            align-items: center;
            span {
              width: 28%;
              height: 100%;
              font-size: 0.7vw;
              font-weight: bold;
              display: flex;
              align-items: center;
              justify-content: center;
              overflow-wrap: anywhere;
              &:last-child {
                width: 72%;
                font-weight: normal;
                color: #333;
                padding: 10px;
                border-left: 1px solid #666666;
              }
            }
          }
        }
        .tableBtm {
          width: 100%;
          height: 63.949vh;
          display: flex;
          flex-direction: column;
          overflow: auto;
          .oriItem {
            width: 100%;
            padding: 1.2vw;
            display: flex;
            flex-direction: column;
            .itemTop {
              font-size: 0.7vw;
              color: #333;
              font-weight: bold;
              line-height: 2.595vh;
            }
            .itemMid {
              position: relative;
              width: 100%;
              // height: 53.476vh;
              padding: 1vw;
              display: flex;
              align-items: center;
              justify-content: center;
              .picNum {
                position: absolute;
                left: 0;
                top: 0;
                font-size: 0.7vw;
                line-height: 2.595vh;
                color: #333;
              }
              /deep/ .el-image__inner {
                width: unset;
                max-height: 49.398vh;
              }
              /deep/ .image-slot {
                width: 13.021vw;
                height: 16.01vh;
                background: #f5f7fa;
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: center;
                span {
                  font-size: 0.7vw;
                  color: #c0c4cc;
                  padding-top: 0.3vw;
                }
                i {
                  font-size: 2vw;
                  color: #909399;
                }
              }
            }
            .itemBtm {
              font-size: 0.7vw;
              color: #333;
              line-height: 2.595vh;
            }
          }
          .emptyOri {
            width: 100%;
            height: 100%;
            font-size: 24px;
            display: flex;
            align-items: center;
            justify-content: center;
          }
          &::-webkit-scrollbar {
            width: 4px;
            background: transparent;
          }
          &::-webkit-scrollbar-thumb {
            background: #c4c4c4;
            border-radius: 4px;
          }
        }
      }
    }
  }
  .noData {
    // width: 100%;
    // height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    flex: 1;
    span {
      font-size: 14px;
      color: #595959;
      margin-top: 12px;
    }
  }
}
</style>
