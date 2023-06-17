<template>
  <div class="videoManage animate__animated animate__fadeIn" id="videoObserve">
    <el-row class="videoBox">
      <el-col class="videoLeft">
        <span class="title">视频观察</span>
        <div class="grid-mid">
          <el-select
            v-model="videoProjectValue"
            placeholder="请选择"
            @change="changeProjectItem(1)"
            filterable
            style="width: 100%"
          >
            <el-option
              v-for="item in videoProjectList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            ></el-option>
          </el-select>
        </div>
        <div class="grid-content">
          <el-tree
            ref="videoTree"
            :data="treeData"
            show-checkbox
            node-key="id"
            default-expand-all
            :expand-on-click-node="true"
            :check-on-click-node="true"
            :default-checked-keys="videoCheckedKeys"
            :props="defaultProps"
            @check="handleCheckChange"
          ></el-tree>
        </div>
        <el-button
          type="primary"
          :class="{ selDisabled: videoCheckList.length == 0 }"
          @click="selectVideo"
          >查看视频</el-button
        >
      </el-col>
      <el-col class="videoRight">
        <div class="contentTop">
          <div>
            <el-button v-if="addOpt" type="primary" @click="addVideoClick"
              >新增视频</el-button
            >
          </div>
          <el-pagination
            class="pageNation"
            background
            :current-page.sync="currentPage"
            :page-size="4"
            :pager-count="5"
            layout="total, prev, pager, next"
            :total="total"
            @current-change="handleCurrentChange"
          >
          </el-pagination>
        </div>
        <div v-if="videoList.length > 0" class="content">
          <div v-for="item in videoList" :key="item.id" class="videoItem">
            <div class="videoPlay">
              <EZUIKit
                v-if="item.ezopenUrl !== null"
                :id="item.id"
                :videoUrl="item.ezopenUrl"
                :accessToken="item.accessToken"
              ></EZUIKit>
              <div v-else class="noVideo">
                <span><i class="el-icon-video-camera"></i></span
                ><span>暂无视频</span>
              </div>
            </div>
            <div class="videoDetail">
              <span>{{ item.structureName }}-{{ item.monitorName }}</span>
              <div class="opation">
                <div>
                  <span v-if="updateOpt" @click="videoUpdata(item)">
                    <i class="el-icon-edit-outline"></i>
                  </span>
                </div>
                <div>
                  <span v-if="deleteOpt" @click="videoDelete(item)">
                    <i class="el-icon-delete"></i>
                  </span>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div v-else class="noVideo">
          <img src="@/assets/images/video.png" />
          <span>请选择要查看的视频</span>
        </div>
      </el-col>
    </el-row>
    <!-- 新增视频弹框 -->
    <el-dialog
      class="addVideo"
      :title="dialogTitle"
      :visible.sync="showVideoDialog"
      width="30vw"
      :modal-append-to-body="false"
      @close="addDialogClose"
      :close-on-click-modal="false"
    >
      <el-form
        v-if="showVideoDialog"
        class="addContent"
        ref="videoForm"
        :model="formVideo"
        :rules="rules"
        label-width="120px"
      >
        <el-form-item label="项目名称" prop="projectId">
          <el-select
            v-model="formVideo.projectId"
            placeholder="请选择项目"
            filterable
            @change="changeProjectItem(2)"
            style="width: 100%"
          >
            <el-option
              v-for="item in projectList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="结构物名称" prop="structureId">
          <el-select
            v-model="formVideo.structureId"
            placeholder="请选择结构物"
            filterable
            style="width: 100%"
          >
            <el-option
              v-for="item in straList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            ></el-option>
            <template slot="empty">
              <div class="searchDiv">请先选择项目</div>
            </template>
          </el-select>
        </el-form-item>
        <el-form-item label="监控视角名称" prop="monitorName">
          <el-input
            v-model.trim="formVideo.monitorName"
            placeholder="请输入监控视角名称"
            maxlength="20"
            clearable
            show-word-limit
          ></el-input>
        </el-form-item>
        <el-form-item label="视频坐标" required>
          <div class="position">
            <el-input
              class="posItem"
              v-model="formVideo.xAxis"
              placeholder="x坐标"
              maxlength="10"
              clearable
              show-word-limit
            ></el-input>
            <el-input
              class="posItem"
              v-model="formVideo.yAxis"
              placeholder="y坐标"
              maxlength="10"
              clearable
              show-word-limit
            ></el-input>
            <el-input
              class="posItem"
              v-model="formVideo.zAxis"
              placeholder="z坐标"
              maxlength="10"
              clearable
              show-word-limit
            ></el-input>
          </div>
        </el-form-item>
        <el-form-item label="设备类型：">
          <el-select
            v-model="formVideo.type"
            @change="codeChange"
            style="width: 100%"
          >
            <el-option
              v-for="item in codeList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item
          v-if="formVideo.type == 1"
          label="视频地址"
          prop="ezopenUrl"
        >
          <el-input
            v-model="formVideo.ezopenUrl"
            placeholder="请输入视频地址"
            maxlength="60"
            clearable
            show-word-limit
          ></el-input>
        </el-form-item>
        <el-form-item
          v-if="formVideo.codeValue == 2"
          label="监控IP地址"
          prop="ipAddress"
        >
          <el-input
            v-model="formVideo.ipAddress"
            placeholder="请输入监控IP地址"
            maxlength="20"
            show-word-limit
          ></el-input>
        </el-form-item>
        <el-form-item
          v-if="formVideo.codeValue == 2"
          label="监控端口"
          prop="videoPort"
        >
          <el-input
            v-model="formVideo.videoPort"
            placeholder="请输入监控端口"
            maxlength="8"
            show-word-limit
          ></el-input>
        </el-form-item>
        <el-form-item
          v-if="formVideo.codeValue == 2"
          label="监控appkey"
          prop="videoAccount"
        >
          <el-input
            v-model="formVideo.videoAccount"
            placeholder="请输入监控secret"
            maxlength="20"
            show-word-limit
          ></el-input>
        </el-form-item>
        <el-form-item
          v-if="formVideo.codeValue == 2"
          label="监控密码"
          prop="videoPassword"
        >
          <el-input
            v-model="formVideo.videoPassword"
            placeholder="请输入监控密码"
            maxlength="20"
            show-word-limit
          ></el-input>
        </el-form-item>
        <el-form-item
          v-if="formVideo.codeValue == 2"
          label="监控视频码"
          prop="videoCode"
        >
          <el-input
            v-model="formVideo.videoCode"
            placeholder="请输入监控视频码"
            maxlength="30"
            show-word-limit
          ></el-input>
        </el-form-item>
        <el-form-item class="addBtn">
          <el-button type="primary" @click="videoSubmit('videoForm')"
            >确定</el-button
          >
          <el-button @click="showVideoDialog = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import {
  getVideoStructureList,
  getDetail,
  saveVideo,
  videoEdit,
  videoDelete,
  getYingshiyunAccessToken
} from '@/api/video/video';
import {
  getProjectListByPowerId,
  getProjectListByModel,
  getStructureList
} from '@/api/common';
import EZUIKit from '@/components/video/EZUIKit';
export default {
  name: 'Video-dzl',
  components: { EZUIKit },
  data() {
    return {
      projectList: [
        // {
        //   id: 1,
        //   name: '项目1'
        // }
      ],
      videoProjectValue: '',
      videoProjectList: [],
      straList: [
        // {
        //   id: 1,
        //   label: '结构物1'
        // }
      ],
      projectTimer: null,
      videoCheckedKeys: [],
      treeData: [
        // {
        //   id: 1,
        //   name: '福鼎大桥',
        //   videoList: [
        //     {
        //       id: 8,
        //       name: '左侧车道1',
        //       projectId: 1, //项目id
        //       structureId: 1, //结构物id
        //       structureName: '福鼎大桥',
        //       codeValue: 1, //1、萤石云 2、海康
        //       videoUrl: 'ezopen://open.ys7.com/D75652535/1.hd.live' //视频地址
        //     },
        //     {
        //       id: 4,
        //       name: '左侧车道2',
        //       projectId: 1, //项目id
        //       structureId: 1, //结构物id
        //       structureName: '福鼎大桥',
        //       codeValue: 1, //1、萤石云 2、海康
        //       videoUrl: 'ezopen://open.ys7.com/D75652535/1.hd.live'
        //     }
        //   ]
        // }
      ],
      defaultProps: {
        children: 'videoList',
        label: (data) => {
          return data.name || data.monitorName;
        }
      },
      currentPage: 1,
      total: 0,
      videoCheckList: [],
      videoList: [
        // {
        //   id: 1, //视频id
        //   projectValue: 1, //项目id
        //   straValue: 1, //结构物id
        //   videoName: '台江大桥-左侧车道1',
        //   codeValue: 1, //1、萤石云 2、海康
        //   videoUrl: 'ezopen://open.ys7.com/D11667720/1.hd.live' //视频地址
        // },
        // {
        //   id: 2,
        //   projectValue: 2,
        //   straValue: 3,
        //   videoName: '台江大桥-左侧车道2',
        //   codeValue: 1,
        //   videoUrl: 'ezopen://open.ys7.com/D11667720/1.hd.live'
        // }
        // {
        //   id: 3,
        //   projectValue: 3,
        //   straValue: 2,
        //   videoName: '台江大桥2-左侧车道3',
        //   codeValue: 1,
        //   videoUrl: 'ezopen://open.ys7.com/D11667720/1.hd.live'
        // },
        // {
        //   id: 4,
        //   projectValue: 1,
        //   straValue: 3,
        //   videoName: '台江大桥2-左侧车道4',
        //   codeValue: 1,
        //   videoUrl: 'ezopen://open.ys7.com/D11667720/1.hd.live'
        // }
      ],
      codeList: [
        {
          id: 1,
          name: '萤石云'
        }
        // {
        //   id: 2,
        //   name: '海康威视'
        // }
      ],
      showVideoDialog: false,
      dialogTitle: '',
      formVideo: {
        projectId: '',
        structureId: '',
        monitorName: '',
        type: 1,
        ezopenUrl: '',
        xAxis: '',
        yAxis: '',
        zAxis: ''
      },
      rules: {
        projectId: [
          { required: true, message: '请选项目名称', trigger: 'change' }
        ],
        structureId: [
          { required: true, message: '请选择结构物名称', trigger: 'change' }
        ],
        monitorName: [
          { required: true, message: '请输入监控视角名称', trigger: 'blur' }
        ],
        ezopenUrl: [
          { required: true, message: '请输入视频地址', trigger: 'blur' }
        ]
        // ipAddress: [
        //   { required: true, message: '请输入监控IP地址', trigger: 'blur' }
        // ],
        // videoPort: [
        //   { required: true, message: '请输入监控端口', trigger: 'blur' }
        // ],
        // videoAccount: [
        //   { required: true, message: '请输入监控账号', trigger: 'blur' }
        // ],
        // videoPassword: [
        //   { required: true, message: '请输入监控密码', trigger: 'blur' }
        // ],
        // videoCode: [
        //   { required: true, message: '请输入监控视频码', trigger: 'blur' }
        // ]
      },
      accessToken: '', //萤石云token
      VideoState: false //视频加载状态
      // videoAuth: {   //海康配置
      //   appkey: '25981144',
      //   ip: '172.16.6.17',
      //   port: 443,
      //   secret: 'MLUFETLtEHJZxNksrtaQ',
      //   snapDir: '/home/SnapDir',
      //   videoDir: '/home/VideoDir'
      // }
    };
  },
  beforeMount() {
    this.$utils.getAuthPage(this); //获取权限
  },
  mounted() {
    this.getProjectListByPowerId(); //查询新增、修改项目列表
    this.getProjectListByModel(); //查询项目下拉选列表
  },
  beforeRouteLeave(to, from, next) {
    window.addEventListener('message', () => {});
    next();
  },
  methods: {
    //查询项目下拉选列表
    async getProjectListByModel() {
      let id = this.$store.getters.getActiveIndex;
      let { code, data } = await getProjectListByModel(id);
      if (code == '0000') {
        // this.videoCheckList = [];
        this.videoProjectList = data;
        if (data.length > 0 && this.videoProjectValue == '') {
          this.videoProjectValue = data[0].id;
          await this.getVideoStructureList(data[0].id);
        }
        if (data.length > 0 && this.videoProjectValue != '') {
          let ids = [];
          data.map((item) => {
            ids.push(item.id);
          });
          if (!ids.includes(this.videoProjectValue) && data.length > 0) {
            this.videoProjectValue = data[0].id;
            await this.getVideoStructureList(data[0].id);
          }
        }
      }
    },
    //查询新增、修改项目列表
    async getProjectListByPowerId() {
      let id = this.$store.getters.getActiveIndex;
      let { code, data } = await getProjectListByPowerId(id);
      if (code == '0000') {
        this.projectList = data;
      }
    },
    //查询视频结构物列表
    async getVideoStructureList(id) {
      let { code, data } = await getVideoStructureList(id);
      if (code == '0000') {
        data.map((item) => {
          item.id = item.structureId;
        });
        this.treeData = data;
        if (this.videoProjectList.length == 0) {
          this.videoProjectValue = '';
        }
      }
    },
    //查询新增、修改结构物列表
    async getStructureList(projectId) {
      let params = { projectId };
      let { code, data } = await getStructureList(params);
      if (code == '0000') {
        this.straList = data;
      }
    },
    //获取视频列表
    async getDetail() {
      // console.log(this.videoCheckList);
      let list = JSON.parse(JSON.stringify(this.videoCheckList));
      list = list.slice(
        (this.currentPage - 1) * 4,
        (this.currentPage - 1) * 4 + 4
      );
      if (this.currentPage > 1 && list.length == 0) {
        this.currentPage--;
        list = JSON.parse(JSON.stringify(this.videoCheckList));
        list = list.slice(
          (this.currentPage - 1) * 4,
          (this.currentPage - 1) * 4 + 4
        );
      }
      this.total = this.videoCheckList.length;
      let { code, data } = await getYingshiyunAccessToken();
      if (code == '0000') {
        list.map((item) => {
          item.accessToken = data;
        });
        this.videoList = list;
      }
    },
    //新增视频
    async saveVideo() {
      let { code } = await saveVideo(this.formVideo);
      if (code == '0000') {
        this.$message({
          message: '新增成功！',
          type: 'success',
          showClose: true,
          duration: 2000
        });
        let treeKeys = this.$refs.videoTree.getCheckedKeys().concat();
        let newTreeKeys = [];
        this.videoCheckList.map((item) => {
          if (treeKeys.includes(item.id)) {
            newTreeKeys.push(item.id);
          }
        });
        this.showVideoDialog = false;
        await this.getDetail();
        await this.getProjectListByModel();
        await this.getVideoStructureList(this.videoProjectValue);
        this.$nextTick(() => {
          this.$refs.videoTree.setCheckedKeys(newTreeKeys);
        });
      }
    },
    //编辑视频
    async videoEdit() {
      let { code } = await videoEdit(this.formVideo);
      if (code == '0000') {
        this.$message({
          message: '修改成功！',
          type: 'success',
          showClose: true,
          duration: 2000
        });
        this.videoCheckList.map((item, index) => {
          if (item.id == this.formVideo.id) item = this.formVideo;
          this.$set(this.videoCheckList, index, item);
        });
        this.showVideoDialog = false;
        await this.getDetail();
        let treeKeys = this.$refs.videoTree.getCheckedKeys();
        await this.getVideoStructureList(this.videoProjectValue);
        let newTreeKeys = [];
        this.videoCheckList.map((item) => {
          if (treeKeys.includes(item.id)) {
            newTreeKeys.push(item.id);
          }
        });
        this.$refs.videoTree.setCheckedKeys(newTreeKeys);
      }
    },
    //选择项目
    changeProjectItem(index) {
      //防抖（防止多次选择）
      if (this.projectTimer != null) {
        clearTimeout(this.projectTimer);
        this.projectTimer = null;
      }
      this.projectTimer = setTimeout(async () => {
        //查询结构物
        if (index == 1) {
          this.videoCheckList = [];
          await this.getVideoStructureList(this.videoProjectValue);
        } else {
          this.straList = [];
          await this.getStructureList(this.formVideo.projectId);
        }
      }, 500);
    },
    //选择设备类型
    codeChange() {
      this.formVideo.ezopenUrl = '';
      // this.formVideo.ipAddress = '';
      // this.formVideo.videoPort = '';
      // this.formVideo.videoAccount = '';
      // this.formVideo.videoPassword = '';
      // this.formVideo.videoCode = '';
    },
    //视频点选择
    handleCheckChange() {
      this.videoCheckList = this.$refs.videoTree.getCheckedNodes(true);
    },
    //查看视频
    async selectVideo() {
      if (this.videoCheckList.length == 0) {
        this.$message({
          message: '请选择监控视角！',
          type: 'warning',
          showClose: true,
          duration: 2000
        });
        return;
      }
      this.currentPage = 1;
      await this.getDetail();
    },
    //点击新增视频
    addVideoClick() {
      this.dialogTitle = '新增视频';
      this.showVideoDialog = true;
    },
    //提交视频
    videoSubmit(formName) {
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          let reg = /^(\-?)\d+(\.\d+)?$/; //验证坐标
          // if (
          //   this.formVideo.xAxis == '' ||
          //   this.formVideo.yAxis == '' ||
          //   this.formVideo.zAxis == ''
          // ) {
          //   console.log(
          //     this.formVideo.xAxis,
          //     this.formVideo.yAxis,
          //     this.formVideo.zAxis
          //   );
          //   this.$message({
          //     message: '坐标不能为空',
          //     type: 'warning',
          //     showClose: true,
          //     duration: 2000
          //   });
          //   return;
          // }
          if (!reg.test(this.formVideo.xAxis)) {
            this.$message({
              message: '请输入正确的X坐标',
              type: 'warning',
              showClose: true,
              duration: 2000
            });
            return;
          }
          if (!reg.test(this.formVideo.yAxis)) {
            this.$message({
              message: '请输入正确的y坐标',
              type: 'warning',
              showClose: true,
              duration: 2000
            });
            return;
          }
          if (!reg.test(this.formVideo.zAxis)) {
            this.$message({
              message: '请输入正确的z坐标',
              type: 'warning',
              showClose: true,
              duration: 2000
            });
            return;
          }
          if (this.dialogTitle == '新增视频') await this.saveVideo();
          if (this.dialogTitle == '修改视频') await this.videoEdit();
        } else {
          return false;
        }
      });
    },
    //视频修改
    async videoUpdata(data) {
      this.dialogTitle = '修改视频';
      this.formVideo = JSON.parse(JSON.stringify(data));
      this.showVideoDialog = true;
      this.editVideoList = JSON.parse(JSON.stringify(this.videoCheckList));
      await this.getStructureList(data.projectId);
    },
    //删除视频
    videoDelete(data) {
      this.$confirm('确定删除该视频?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async () => {
          let { code } = await videoDelete(data.id);
          if (code == '0000') {
            this.$message({
              message: '删除成功！',
              type: 'success',
              showClose: true,
              duration: 2000
            });
            let arry = JSON.parse(JSON.stringify(this.videoCheckList));
            for (let i = arry.length - 1; i >= 0; i--) {
              if (arry[i].id == data.id) this.videoCheckList.splice(i, 1);
            }
            let treeKeys = this.$refs.videoTree.getCheckedKeys().concat();
            treeKeys.splice(
              treeKeys.findIndex((id) => id === data.id),
              1
            );
            await this.getDetail();
            await this.getProjectListByModel();
            await this.getVideoStructureList(this.videoProjectValue);
            this.$refs.videoTree.setCheckedKeys(treeKeys);
          }
        })
        .catch();
    },
    //关闭新增视频弹框
    addDialogClose() {
      this.formVideo = {
        projectId: '',
        structureId: '',
        monitorName: '',
        type: 1,
        ezopenUrl: '',
        xAxis: '',
        yAxis: '',
        zAxis: ''
      };
      this.straList = [];
    },
    videoError(state) {
      this.VideoState = state;
      if (!state) {
        if (confirm('是否现在安装插件？')) {
          window.open('/static/hkJS/VideoWebPlugin.exe');
        } else {
          return;
        }
      }
    },
    //分页功能
    async handleCurrentChange(val) {
      this.videoList = [];
      this.currentPage = val;
      await this.getDetail();
    }
  }
};
</script>

<style lang="scss" scoped>
.videoManage {
  height: 100%;
  // padding: 1vw 3.2vw;
  .videoBox {
    height: 100%;
    width: 100%;
    border-radius: 8px;
    display: flex;
    .videoLeft {
      width: 18.75vw;
      border-right: 1px solid #e8e8e8;
      padding: 1vw 1.2vw !important;
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      .title {
        font-size: 16px;
        color: #333;
        font-weight: bold;
        line-height: 24px;
      }
      .grid-content {
        height: 72vh;
        overflow-y: auto;
        /deep/ .el-tree-node__label {
          width: 100%;
          overflow: hidden;
          text-overflow: ellipsis;
        }
      }
      .selDisabled {
        opacity: 0.5;
        cursor: not-allowed;
      }
    }
    .videoRight {
      flex: 1;
      padding: 1vw 2.4vw !important;
      display: flex;
      flex-direction: column;
      .contentTop {
        height: 40px;
        margin-bottom: 1.2vw;
        display: flex;
        align-items: center;
        justify-content: space-between;
        .pageNation {
          /deep/ button {
            background: transparent;
            border: 1px solid #d9d9d9;
            border-radius: 2px;
          }
          /deep/ .el-pager li {
            background: transparent;
            border: 1px solid #d9d9d9;
            border-radius: 2px;
          }
          /deep/ .el-pager .active {
            color: #1890ff;
            border: 1px solid #1890ff;
          }
        }
      }
      .content {
        flex: 1;
        display: flex;
        flex-wrap: wrap;
        justify-content: space-between;
        .videoItem {
          width: 680px;
          display: flex;
          flex-direction: column;
          .videoPlay {
            height: 33.334vh;
            .noVideo {
              width: 100%;
              height: 100%;
              background: #000;
              display: flex;
              flex-direction: column;
              justify-content: center;
              span {
                margin: 0;
                color: #fff;
                font-size: 40px;
                &:last-child {
                  font-size: 20px;
                  margin-top: 10px;
                }
              }
            }
          }
          .videoDetail {
            padding-top: 8px;
            display: flex;
            align-items: center;
            justify-content: space-between;
            span {
              font-size: 14px;
              line-height: 18px;
              color: #000;
            }
            .opation {
              display: flex;
              align-items: center;
              justify-content: flex-end;
              span {
                color: #419aff;
                font-size: 16px;
                cursor: pointer;
                &:first-child {
                  margin-right: 12px;
                }
              }
            }
          }
        }
      }
      .noVideo {
        flex: 1;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        span {
          margin: 15px 0;
          font-size: 0.8vw;
          color: #595959;
        }
      }
    }
  }
}
//新增视频弹框
.addVideo {
  .addContent {
    .position {
      display: flex;
      align-items: center;
      justify-content: space-between;
      .posItem {
        width: 120px;
      }
    }
  }
  .addBtn {
    display: flex;
    justify-content: flex-end;
  }
  /deep/ .el-dialog__body {
    padding-right: 2.4vw;
  }
}
//下拉框没数据
.searchDiv {
  padding: 10px 0;
  margin: 0;
  text-align: center;
  color: #999;
  font-size: 0.7vw;
}
::-webkit-scrollbar {
  width: 0.3vw;
  height: 0.3vw;
}
::-webkit-scrollbar-thumb {
  background: #1890ff;
  border-radius: 0.5vw;
}
</style>
