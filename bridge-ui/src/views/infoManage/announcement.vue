<template>
  <div
    class="y-backg-ann animate__animated animate__fadeIn"
    id="song-back-ano"
    style="padding: 20px"
  >
    <div class="y-condition-dow_us">
      <div class="col-md-12 column">
        <div class="y-condition-dow_us_top el-row">
          <div>
            <el-button v-if="addOpt" type="primary" @click="addAnnoIns = true"
              >创建公告</el-button
            >
          </div>
          <div class="searchclass" style="display: flex">
            <el-input
              placeholder="关键词"
              v-model="keyword"
              clearable
              class="y-el-select"
              :maxlength="30"
            ></el-input>
            <el-select
              class="y-el-select"
              v-model="status"
              clearable
              placeholder="状态"
            >
              <el-option
                v-for="item in inspectionDepartments"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              ></el-option>
            </el-select>
            <el-button
              type="primary"
              class="search-buttonCl"
              @click="tableDataInit"
              >查询</el-button
            >
          </div>
        </div>
        <br />

        <!-- 新建模态框 -->
        <el-dialog
          class="add"
          id="addCl"
          title="创建公告"
          :visible.sync="addAnnoIns"
          width="28%"
          :before-close="dialogClose"
          :modal-append-to-body="false"
          :close-on-click-modal="false"
        >
          <el-form
            ref="form"
            :model="addObj"
            :rules="announceRules"
            label-width="125px"
            style="padding-right: 40px"
          >
            <el-form-item label="公告标题:" prop="title">
              <el-input
                v-model.trim="addObj.title"
                :maxlength="30"
                placeholder="30字以内"
              ></el-input>
            </el-form-item>

            <el-form-item label="生效时间:" prop="effectTime">
              <el-date-picker
                v-model="addObj.effectTime"
                type="datetime"
                :picker-options="pickerOptions"
                value-format="yyyy-MM-dd HH:mm:ss"
                placeholder="选择时间"
                class="buildTimeCl"
              ></el-date-picker>
            </el-form-item>

            <el-form-item label="失效时间:" prop="expiresTime">
              <el-date-picker
                v-model="addObj.expiresTime"
                type="datetime"
                :picker-options="pickerOptions"
                value-format="yyyy-MM-dd HH:mm:ss"
                placeholder="选择时间"
                class="buildTimeCl"
              ></el-date-picker>
            </el-form-item>

            <el-form-item label="公告内容:" prop="content">
              <el-input
                type="textarea"
                v-model.trim="addObj.content"
                class="annCl"
                style="width: 100%"
                :maxlength="125"
                placeholder="125字以内"
              ></el-input>
            </el-form-item>
          </el-form>
          <div class="btn">
            <el-button type="primary" @click="addAnnoSubmit('form')"
              >保存</el-button
            >
            <el-button @click="addAnnoIns = false">取消</el-button>
          </div>
        </el-dialog>

        <!-- 修改模态框  -->
        <el-dialog
          class="add"
          id="addClX"
          title="修改公告"
          :visible.sync="AnnoUpdateModel"
          width="28%"
          :before-close="dialogClose"
          :modal-append-to-body="false"
          :close-on-click-modal="false"
        >
          <el-form
            ref="updateform"
            :model="updObj"
            :rules="announceRules"
            label-width="125px"
            style="padding-right: 40px"
          >
            <el-form-item label="公告标题:" prop="title">
              <el-input
                v-model="updObj.title"
                :maxlength="30"
                placeholder="30字以内"
              ></el-input>
            </el-form-item>

            <el-form-item label="生效时间:" prop="effectTime">
              <el-date-picker
                v-model="updObj.effectTime"
                type="datetime"
                value-format="yyyy-MM-dd HH:mm:ss"
                :picker-options="pickerOptions"
                placeholder="选择时间"
                class="buildTimeCl"
              ></el-date-picker>
            </el-form-item>

            <el-form-item label="失效时间:" prop="expiresTime">
              <el-date-picker
                v-model="updObj.expiresTime"
                :picker-options="pickerOptions"
                type="datetime"
                value-format="yyyy-MM-dd HH:mm:ss"
                placeholder="选择时间"
                class="buildTimeCl"
              ></el-date-picker>
            </el-form-item>

            <el-form-item label="公告内容:" prop="content">
              <el-input
                type="textarea"
                v-model="updObj.content"
                style="width: 100%"
                :maxlength="125"
                placeholder="125字以内"
              ></el-input>
            </el-form-item>
          </el-form>
          <div class="btn">
            <el-button type="primary" @click="updateAnnoSubmit('updateform')"
              >保存</el-button
            >
            <el-button @click="AnnoUpdateModel = false">取消</el-button>
          </div>
        </el-dialog>

        <!-- 详情模态框  -->
        <el-dialog
          class="add"
          id="GgXq"
          title="公告详情"
          :visible.sync="AnnoDetialModel"
          width="28%"
          :modal-append-to-body="false"
          :close-on-click-modal="false"
        >
          <el-form
            ref="form"
            :model="DetialObj"
            label-width="125px"
            style="padding-right: 40px"
            disabled
          >
            <el-form-item label="编号:" required>
              <el-input v-model="DetialObj.id"></el-input>
            </el-form-item>

            <el-form-item label="公告标题:" required>
              <el-input v-model="DetialObj.title"></el-input>
            </el-form-item>

            <el-form-item label="创建时间:" required>
              <el-date-picker
                v-model="DetialObj.createTime"
                type="datetime"
                value-format="yyyy-MM-dd HH:mm:ss"
                style="width: 100%"
              ></el-date-picker>
            </el-form-item>

            <el-form-item label="创建者:" required>
              <el-input v-model="DetialObj.creator"></el-input>
            </el-form-item>

            <el-form-item label="生效时间:" required>
              <el-date-picker
                v-model="DetialObj.effectTime"
                :picker-options="pickerOptions"
                type="datetime"
                value-format="yyyy-MM-dd HH:mm:ss"
                style="width: 100%"
              ></el-date-picker>
            </el-form-item>

            <el-form-item label="失效时间:" required>
              <el-date-picker
                v-model="DetialObj.expiresTime"
                :picker-options="pickerOptions"
                type="datetime"
                value-format="yyyy-MM-dd HH:mm:ss"
                style="width: 100%"
              ></el-date-picker>
            </el-form-item>

            <el-form-item label="公告时间:" required>
              <el-input v-model="DetialObj.bulletinTime"></el-input>
            </el-form-item>

            <el-form-item label="状态:" required>
              <el-input v-model="DetialObj.statusName"></el-input>
            </el-form-item>

            <el-form-item label="公告内容:" required>
              <el-input
                type="textarea"
                class="el-disabled-textarea__inner"
                v-model="DetialObj.content"
                style="width: 100%"
              ></el-input>
            </el-form-item>
          </el-form>
          <div class="btn">
            <el-button
              type="primary"
              :disabled="!updateOpt || DetialObj.status != 0"
              @click="anoPublishSubmit"
              >发布</el-button
            >
            <el-button @click="AnnoDetialModel = false">取消</el-button>
          </div>
        </el-dialog>

        <!-- 数据展示区 -->
        <el-row>
          <el-col :span="24">
            <div class="grid-content bg-purple-dark song-table-u">
              <normalTable
                :titleList="titleList"
                :tableData="tableData"
                :opList="opList"
                :pageNum="page"
                @tableClick="tableClick"
              ></normalTable>
            </div>
          </el-col>
        </el-row>

        <!-- 分页器 -->
        <el-row>
          <el-col :span="24">
            <div
              id="anfen"
              class="grid-content bg-purple-dark y-page"
              style="text-align: center"
            >
              <el-pagination
                class="pageNation"
                background
                @current-change="handleCurrentChange"
                :current-page.sync="page"
                :page-size="10"
                layout="total, prev, pager, next"
                :total="dataTotal"
              ></el-pagination>
            </div>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
import {
  delAnnouncement,
  getAnnouncementList,
  addAnnoIns,
  updateAnnouncement,
  annoPublish
} from '@/api/infomanage/announcement';
import normalTable from '@/components/table/normalTable';
export default {
  components: { normalTable },
  data() {
    return {
      selParams: {
        pageNum: 1,
        pageSize: 10,
        keyword: '',
        status: ''
      },
      creationTime: '',
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() < Date.now() - 8.64e7;
        }
      }, // 只能选取当前之后时间

      search: {
        id: '',
        key: ''
      },

      //添加公告记录的变量
      addObj: {
        title: '',
        content: '',
        effectTime: '',
        expiresTime: ''
      },
      announceRules: {
        title: [{ required: true, message: '请输入公告标题', trigger: 'blur' }],
        effectTime: [
          {
            type: 'string',
            required: true,
            message: '请选择生效时间',
            trigger: 'change'
          },
          {
            required: true,
            message: '请选择生效时间',
            trigger: 'blur'
          }
        ],
        expiresTime: [
          {
            type: 'string',
            required: true,
            message: '请选择失效时间',
            trigger: 'change'
          },
          {
            required: true,
            message: '请选择失效时间',
            trigger: 'blur'
          }
        ],
        content: [
          { required: true, message: '请输入公告内容', trigger: 'blur' }
        ]
      },

      //修改公告信息的变量
      updObj: {
        id: '',
        title: '',
        effectTime: '',
        expiresTime: '',
        content: '',
        bulletinTime: ''
      },

      //公告详细信息的变量
      DetialObj: {
        id: '',
        title: '',
        effectTime: '',
        expiresTime: '',
        content: '',
        creator: '',
        createTime: '',
        status: '',
        bulletinTime: ''
      },

      //表格数据
      titleList: [
        // { id: 1, prop: 'id', label: '编号', width: '100' },
        { id: 2, prop: 'title', label: '公告标题', width: '' },
        { id: 3, prop: 'createTime', label: '创建时间', width: '' },
        { id: 4, prop: 'creator', label: '创建者', width: '' },
        { id: 5, prop: 'bulletinTime', label: '公告时间', width: '320' },
        { id: 6, prop: 'statusName', label: '状态', width: '' }
      ],
      tableData: [
        // {
        //   id: 1,
        //   title: '关于日常巡查和维修养护的建议',
        //   createTime: '2020/01/20 11:32:08',
        //   creator: 'admin',
        //   bulletinTime: '2020-01-20-2021-01-01',
        //   status: '未发布'
        // },
      ],
      opList: [
        { id: 1, name: '详情', opShow: false, disabled: '', type: 'checkOpt' },
        { id: 2, name: '修改', opShow: false, disabled: '', type: 'updateOpt' },
        { id: 3, name: '删除', opShow: false, disabled: '', type: 'deleteOpt' }
      ],
      //详情表格
      id: '',

      //记录总数
      dataTotal: 0,
      //当前页
      page: 1,

      //一页的条数
      size: 10,
      //关键字
      keyword: '',

      //添加记录的模态框值
      addAnnoIns: false,
      //修改记录的模态框值
      AnnoUpdateModel: false,

      //查看公告详细信息的模态框值
      AnnoDetialModel: false,
      //养护单位下拉框
      status: '',
      inspectionDepartments: [
        { id: 0, name: '未发布' },
        { id: 1, name: '已发布' },
        { id: 2, name: '已失效' }
      ]
      //  YY_BOX_MASK:YYstyles.YY_BOX_MASK,
    };
  },
  watch: {
    //  关闭添加模态框时清空值
    addAnnoIns(val) {
      if (val) {
        this.addObj.title = '';
        this.addObj.content = '';
        this.addObj.effectTime = '';
        this.addObj.expiresTime = '';
      }
    }
  },
  beforeMount() {
    this.$utils.getAuthPage(this); //获取权限
  },
  //初始化
  mounted() {
    this.tableDataInit();
  },
  methods: {
    //获取列表
    async tableDataInit() {
      let params = {
        keyword: this.keyword,
        status: this.status,
        pageNum: 1,
        pageSize: 10
      };
      let { code, data } = await getAnnouncementList(params);
      if (code == '0000') {
        data.list.map((item) => {
          if (item.status == 0) item.statusName = '未发布';
          if (item.status == 1) item.statusName = '已发布';
          if (new Date() > new Date(item.expiresTime))
            item.statusName = '已失效';
          item['bulletinTime'] = item.effectTime + '~' + item.expiresTime;
        });
        this.page = 1;
        this.tableData = data.list;
        this.dataTotal = data.total;
        this.selParams = params;
      }
    },
    async tableDataInit2() {
      let { code, data } = await getAnnouncementList(this.selParams);
      if (code == '0000') {
        data.list.map((item) => {
          if (item.status == 0) item.statusName = '未发布';
          if (item.status == 1) item.statusName = '已发布';
          if (new Date() > new Date(item.expiresTime))
            item.statusName = '已失效';
          item['bulletinTime'] = item.effectTime + '~' + item.expiresTime;
        });
        this.tableData = data.list;
        this.dataTotal = data.total;
      }
    },

    //详细公告信息查看事件
    announcementDetial(data) {
      this.DetialObj = JSON.parse(JSON.stringify(data));
      this.DetialObj.bulletinTime =
        this.DetialObj.effectTime + '~' + this.DetialObj.expiresTime;
      //详情框
      this.AnnoDetialModel = true;
    },

    //修改公告信息弹出
    updateAnnouncement(data) {
      this.updObj = JSON.parse(JSON.stringify(data));
      //修改框
      this.AnnoUpdateModel = true;
    },

    //删除点击事件
    bridgeDelete(data) {
      //删除框
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        showClose: true
      })
        .then(async () => {
          let { code } = await delAnnouncement(data.id);
          if (code == '0000') {
            this.$message({
              type: 'success',
              message: '删除成功!',
              showClose: true,
              duration: 2000
            });
            await this.tableDataInit2();
            this.$nextTick(() => {
              this.$store.dispatch('asyncUpdateNoticeState', data.id); //更新公告状态
            });
          }
        })
        .catch(() => {});
    },
    //表格点击事件
    tableClick(index, data) {
      if (index == 1) {
        this.announcementDetial(data);
      } else if (index == 2) {
        this.updateAnnouncement(data);
      } else if (index == 3) {
        this.bridgeDelete(data);
      }
    },

    // 创建公告提交事件
    addAnnoSubmit(formName) {
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          if (this.addObj.expiresTime <= this.addObj.effectTime) {
            this.$message({
              message: '失效时间应大于生效时间',
              type: 'warning',
              showClose: true
            });
            return false;
          }
          let { code } = await addAnnoIns(this.addObj);
          if (code == '0000') {
            this.$message({
              type: 'success',
              message: '公告创建成功!',
              showClose: true
            });
            this.dialogClose();
            await this.tableDataInit2();
            this.$nextTick(() => {
              this.$store.dispatch('asyncUpdateNoticeState', Math.random()); //更新公告状态
            });
          }
        }
      });
    },

    //编辑公告提交事件
    updateAnnoSubmit(formName) {
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          if (this.updObj.expiresTime <= this.updObj.effectTime) {
            this.$message({
              message: '失效时间应大于生效时间',
              type: 'warning',
              showClose: true
            });
            return false;
          }
          let { code } = await updateAnnouncement(this.updObj);
          if (code == '0000') {
            this.AnnoUpdateModel = false;
            this.$message({
              type: 'success',
              message: '公告修改成功!',
              showClose: true
            });
            this.dialogClose();
            await this.tableDataInit2();
            this.$nextTick(() => {
              this.$store.dispatch('asyncUpdateNoticeState', Math.random()); //更新公告状态
            });
          }
        }
      });
    },

    //公告信息发布
    async anoPublishSubmit() {
      //1发布，0取消发布
      let { code } = await annoPublish(this.DetialObj.id, 1);
      if (code == '0000') {
        this.$message({
          type: 'success',
          message: '公告发布成功！',
          showClose: true,
          duration: 2000
        });
        this.AnnoDetialModel = false;
        this.$store.dispatch('asyncUpdateNoticeState', this.DetialObj.id); //更新公告状态
        this.$nextTick(async () => {
          await this.tableDataInit2();
        });
      }
    },
    //关闭弹框
    dialogClose() {
      this.addAnnoIns = false;
      this.AnnoUpdateModel = false;
      //添加公告记录的变量
      this.addObj = {
        title: '',
        content: '',
        effectTime: '',
        expiresTime: ''
      };
      //修改公告信息的变量
      this.updObj = {
        id: '',
        title: '',
        effectTime: '',
        expiresTime: '',
        content: '',
        bulletinTime: ''
      };
      this.$nextTick(() => {
        this.$refs.form.clearValidate();
        this.$refs.updateform.clearValidate();
      });
    },
    //分页器事件
    async handleCurrentChange(val) {
      //重置当前页
      this.page = val;
      this.selParams.pageNum = val;
      await this.tableDataInit2();
    }
  }
};
</script>
<style lang="scss" scoped>
.y-condition-dow {
  padding-top: 20px;
  padding-bottom: 20px;
}

.y-condition-dow_us {
  padding-top: 0px;
  padding-bottom: 0px;
  .y-condition-dow_us_top {
    display: flex;
    align-items: center;
    justify-content: space-between;
    &::before,
    &::after {
      content: unset;
    }
  }
}

.y-col-1 {
  float: right;
  margin-right: 64px;
}
.y-col-0 {
  margin-left: 64px;
}

.song-table-u {
  width: 100%;
  height: 95%;
  /* padding-right: 64px; */
  /* padding-left: 64px; */
  border: 0px;
  /* margin-top: -14px !important; */
}
.y-page {
  .pageNation {
    padding: 1vw 0;
    text-align: center;
    /deep/ button {
      background: transparent;
      border: 1px solid #d9d9d9;
      border-radius: 2px;
    }
    /deep/ .el-pager li {
      background: transparent !important;
      border: 1px solid #d9d9d9;
      border-radius: 2px;
    }
    /deep/ .el-pager .active {
      color: #1890ff !important;
      border: 1px solid #1890ff;
    }
  }
}
</style>
<style scoped>
.el-col-20 {
  width: 85.77333%;
}

.y-el-select {
  width: 150px;
  margin-right: 20px;
}

.y-backg-ann .el-button--primary {
  /* background-color: #3874ecd5 !important;
  border-color: #3874ecd5 !important; */

  background-color: #419aff;
  border-radius: 4px;
  color: #fafafb;
}

.search-button-mj {
  /* width: 88px; */
  height: 40px;
  background-color: #3773ec;
  border: solid 1px #3873ec;
  /* box-shadow: 0px 4px 12px 0px rgba(33, 164, 150, 0.4); */
}
#bridgeManage .el-dialog {
  background-color: #101929 !important;
}

.buildTimeCl {
  width: 100% !important;
}

.y-col-1[data-v-e392610e] {
  float: right;
  margin-right: -40px;
}

.chaCl {
  margin-left: 0px;
}

.search-button2[data-v-5ea6b57c] {
  /* margin-left: 64px; */
}

.search-button2 {
  /*  width: 88px; */
  height: 40px;
  background-color: #3773ec;
  border: solid 1px #3873ec;
  /* box-shadow: 0px 4px 12px 0px rgba(33, 164, 150, 0.4); */
  /* margin-left: 64px; */
}

.search-button_st[data-v-5ea6b57c] {
  /* margin-right: 66px; */
}

.search-button_st {
  /* width: 72px; */
  /* height: 38px; */
  background-color: #3874ecd5;
  /* box-shadow: 0px 4px 12px 0px rgba(6, 175, 133, 0.36); */
  border-radius: 4px;
  font-size: 14px;
  color: #ffffff;
}

.search-buttonCl {
  /* margin-right: 64px; */
  background-color: #419aff;
  border-radius: 4px;
  color: #fafafb;
}
.btn {
  padding-top: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>

<style lang="scss">
.y-backg-ann {
  height: 100%;
}
#addCl .el-dialog__title {
  font-size: 16px;
}

#addCl .el-dialog__header {
  padding: 20px 20px 10px;
  color: #808a96;
}

#anCL .el-textarea__inner {
  /* background-color: #141E30; */
  min-height: 91px !important;
}

#ggxn .el-textarea__inner {
  /* background-color: #141E30; */
  min-height: 91px !important;
}

#song-back-ano .el-input.is-disabled .el-input__inner {
  border: none;
}

#song-back-ano .el-textarea.is-disabled .el-textarea__inner {
  // background-color: $YY_DIV_BACKGROUND;
  cursor: not-allowed;
  min-height: 91px !important;
}

#song-back-ano .el-textarea__inner {
  min-height: 91px !important;
}

#song-back-ano .el-table .cell {
  /* width: 42px;
	height: 14px;
	font-family: SourceHanSansCN-Regular;*/
  font-size: 14px;
  font-stretch: normal;
  letter-spacing: 0px;
}

#song-back-ano .el-button--text {
  /* width: 42px;
	height: 14px;
	font-family: SourceHanSansCN-Regular;*/
  font-size: 14px;
  font-weight: normal;
  font-stretch: normal;
  letter-spacing: 0px;
  color: #419aff;
}
#song-back-ano .el-textarea.is-disabled .el-textarea__inner {
  background-color: #f5f7fa;
}
</style>
