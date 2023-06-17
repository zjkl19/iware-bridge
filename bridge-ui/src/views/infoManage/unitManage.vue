<template>
  <div class="unitManage animate__animated animate__fadeIn">
    <div class="unitTop">
      <div>
        <el-button v-if="addOpt" type="primary" @click="showAddUnit"
          >新增单位</el-button
        >
      </div>
      <div class="unitTopRight">
        <el-input
          class="keyInput"
          placeholder="关键词"
          v-model="keyValue"
          clearable
          :maxlength="30"
        ></el-input>
        <el-select v-model="roleValue" clearable placeholder="选择单位类型">
          <el-option
            v-for="item in typeSelectRole"
            :key="item.roleId"
            :label="item.roleName"
            :value="item.roleId"
          ></el-option>
        </el-select>
        <el-button type="primary" @click="selectUnitInfo"> 查询 </el-button>
      </div>
    </div>
    <br />
    <div class="unitBtm">
      <normalTable
        :tableData="tableData"
        :titleList="titleList"
        :opList="opList"
        :opWidth="'200'"
        :pageNum="currentPage"
        @tableClick="tableClick"
      ></normalTable>
      <el-pagination
        class="pageNation"
        background
        :current-page.sync="currentPage"
        :page-size="pageSize"
        :pager-count="5"
        layout="total, prev, pager, next"
        :total="total"
        @current-change="handleCurrentChange"
      >
      </el-pagination>
    </div>
    <!-- 用户新增、修改弹框 -->
    <el-dialog
      class="unitDialog"
      :title="dialogTitle"
      :visible.sync="showUnitDialog"
      width="42vw"
      :modal-append-to-body="false"
      @closed="dialogClose"
      :close-on-click-modal="false"
    >
      <el-form
        v-if="showUnitDialog"
        class="unitContent"
        ref="unitForm"
        :model="unitForm"
        :rules="unitRules"
        label-width="120px"
      >
        <el-form-item label="单位名称：" prop="name">
          <el-input
            v-model="unitForm.name"
            placeholder="请输入用户名"
            maxlength="20"
            clearable
            show-word-limit
          ></el-input>
        </el-form-item>
        <el-form-item label="单位类型：" prop="roleId">
          <el-select
            v-model="unitForm.roleId"
            @change="roleChange"
            style="width: 100%"
            :disabled="dialogTitle == '修改单位'"
          >
            <el-option
              v-for="item in typeRoleList"
              :key="item.roleId"
              :label="item.roleName"
              :value="item.roleId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="分配权限">
          <div class="dialogAuth">
            <div class="authItem" v-for="item in authList" :key="item.id">
              <el-scrollbar style="height: 100%">
                <el-tree
                  :ref="item.ref"
                  :data="item.treeData"
                  show-checkbox
                  node-key="id"
                  :default-expand-all="false"
                  :expand-on-click-node="true"
                  :check-on-click-node="true"
                  :default-checked-keys="unitCheckedKeys"
                  :default-expanded-keys="[1, 3, 30, 49, 68, 85, 91]"
                  :props="defaultProps"
                  @check="handleCheckChange"
                ></el-tree>
              </el-scrollbar>
            </div>
          </div>
        </el-form-item>
        <el-form-item class="dialogBtn">
          <el-button type="primary" @click="unitSubmit('unitForm')"
            >确定</el-button
          >
          <el-button @click="showUnitDialog = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
<script>
import {
  getUnitList,
  addUnit,
  editUnit,
  delUnit,
  unitPowerList,
  getUnitPowers,
  getRoleDefaultPower,
  getPagePowerIds
} from '@/api/infomanage/unitManage';
import md5 from 'js-md5';
import normalTable from '@/components/table/normalTable';
export default {
  name: 'unitManage',
  components: {
    normalTable
  },
  data() {
    var validataPass = (rule, value, callback) => {
      if (value !== this.changePwdForm.newPassword) {
        callback(new Error('密码不一致！'));
      } else {
        callback();
      }
    };
    return {
      selParams: {
        pageNum: 1,
        pageSize: 10,
        keyword: '',
        roleId: '',
        unitId: ''
      },
      typeSelectRole: [],
      typeRoleList: [],
      keyValue: '',
      roleValue: '',
      titleList: [
        { id: 1, prop: 'name', label: '单位名称' },
        { id: 2, prop: 'unitType', label: '单位类型' },
        { id: 3, prop: 'creator', label: '创建者' },
        { id: 4, prop: 'createTime', label: '创建时间' }
      ],
      tableData: [],
      opList: [
        { id: 1, name: '修改', opShow: false, type: 'updateOpt' },
        { id: 2, name: '删除', opShow: false, type: 'deleteOpt' }
      ],
      total: 0,
      currentPage: 1,
      pageSize: 10,
      //新增、修改弹框
      dialogTitle: '',
      showUnitDialog: false,
      unitForm: {
        name: '',
        roleId: '',
        authids: []
      },
      authList: [
        {
          id: 'home',
          ref: 'home',
          treeData: []
        },
        {
          id: 'online',
          ref: 'online',
          treeData: []
        },
        {
          id: 'normal',
          ref: 'normal',
          treeData: []
        },
        {
          id: 'maintain',
          ref: 'maintain',
          treeData: []
        },
        {
          id: 'assessment',
          ref: 'assessment',
          treeData: []
        },
        // {
        //   id: "video",
        //   ref: "video",
        //   treeData: []
        // },
        {
          id: 'infoManage',
          ref: 'infoManage',
          treeData: []
        }
      ],
      unitCheckedKeys: [],
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      unitRules: {
        name: [{ required: true, message: '输入单位名称', trigger: 'blur' }],
        roleId: [
          { required: true, message: '请选择单位类型', trigger: 'change' }
        ]
      }
    };
  },
  beforeMount() {
    this.$utils.getAuthPage(this); //获取权限
  },
  mounted() {
    this.selectUnitInfo();
    this.getPagePowerIds(); //获取页面模块ids
    this.unitPowerList(); //获取权限勾选列表
    this.getRoleDefaultPower(1); //条件筛选角色列表
    this.getRoleDefaultPower(2); //新增、修改角色列表
    //只有admin有删除功能
    // if (this.$store.getters.getRoleInfo.id != 3) {
    //   this.opList[1].opShow = false;
    // }
  },
  methods: {
    //查询列表
    async selectUnitInfo() {
      let roleId = this.$store.getters.getRoleInfo.id;
      let unitId = this.$store.getters.getUserInfo.unitId;
      let params = {
        pageNum: 1,
        pageSize: this.pageSize,
        keyword: this.keyValue,
        roleId: this.roleValue,
        unitId
      };
      let { code, data } = await getUnitList(params);
      if (code == '0000') {
        let arry = data.list;
        arry.map((item) => {
          if (item.id == unitId) {
            item['updateOpt'] = true;
            item['deleteOpt'] = true;
          }
          if (roleId == 3 && item.roleId == 0) {
            item['updateOpt'] = true;
          }
        });
        this.currentPage = 1;
        this.tableData = arry;
        this.total = data.total;
        this.selParams = params;
      }
    },
    async selectUnitInfo2() {
      let roleId = this.$store.getters.getRoleInfo.id;
      let { code, data } = await getUnitList(this.selParams);
      if (code == '0000') {
        let arry = data.list;
        arry.map((item) => {
          if (item.id == this.selParams.unitId) {
            item['updateOpt '] = true;
            item['deleteOpt'] = true;
          }
          if (roleId == 3 && item.roleId == 0) {
            item['updateOpt'] = true;
          }
        });
        this.tableData = arry;
        this.total = data.total;
      }
    },
    //获取页面模块ids
    async getPagePowerIds() {
      let { code, data } = await getPagePowerIds();
      if (code == '0000') {
        this.pageList = data;
      }
    },
    //获取权限勾选列表
    async unitPowerList() {
      let { code, data } = await unitPowerList();
      if (code == '0000') {
        this.authAllList = data;
        let arry = JSON.parse(JSON.stringify(data));
        this.authList[0].treeData = [arry[0], arry[5]]; //首页
        this.authList[1].treeData = [arry[1]]; //在线监测
        this.authList[2].treeData = [arry[2]]; //日常巡查
        this.authList[3].treeData = [arry[3]]; //维修养护
        this.authList[4].treeData = [arry[4]]; //检测评估
        this.authList[5].treeData = [arry[6]]; //信息管理
        this.normalList = JSON.parse(JSON.stringify(this.authList));
        let arry2 = JSON.parse(JSON.stringify(this.authList)); //业主权限
        let list2 = [118];
        let forArry2 = arry2[5].treeData[0].children;

        let arry3 = JSON.parse(JSON.stringify(this.authList)); //承接单位主用户权限
        let list3 = [94, 95, 96, 99, 100, 101, 114, 115, 116];
        this.getRoleList(arry3[5].treeData[0].children, list3);

        let arry4 = JSON.parse(JSON.stringify(this.authList)); //承接单位普通用户权限
        let list4 = [118, 94, 95, 96, 99, 100, 101, 114, 115, 116];
        this.getRoleList(arry4[5].treeData[0].children, list4);

        for (let i = forArry2.length - 1; i >= 0; i--) {
          if (list2.includes(forArry2[i].id)) {
            arry2[5].treeData[0].children.splice(i, 1);
          }
        }
        this.limitList2 = arry2;
        this.limitList3 = arry3;
        this.limitList4 = arry4;
      }
    },
    //查询单位类型列表
    async getRoleDefaultPower(type) {
      let { code, data } = await getRoleDefaultPower(type);
      if (code == '0000') {
        if (type == 1) {
          this.typeSelectRole = data;
        } else {
          this.typeRoleList = data;
        }
      }
    },
    //获取各单位类型权限列表
    getRoleList(arry, limitList) {
      for (let i = arry.length - 1; i >= 0; i--) {
        if (limitList.includes(arry[i].id)) {
          arry.splice(i, 1);
        } else if (arry[i].children.length > 0) {
          this.getRoleList(arry[i].children, limitList);
        }
      }
    },
    //点击新增用户
    showAddUnit() {
      this.authList = JSON.parse(JSON.stringify(this.normalList));
      this.dialogTitle = '新增单位';
      this.showUnitDialog = true;
    },
    //表格操作点击
    tableClick(index, data) {
      if (index == 1) {
        if (data.roleId == 2) {
          this.authList = JSON.parse(JSON.stringify(this.limitList2));
        } else if (data.roleId == 1) {
          this.authList = JSON.parse(JSON.stringify(this.limitList3));
        } else if (data.roleId == 0) {
          this.authList = JSON.parse(JSON.stringify(this.limitList4));
        } else {
          this.authList = JSON.parse(JSON.stringify(this.normalList));
        }
        this.unitForm = JSON.parse(JSON.stringify(data));
        getUnitPowers(data.id).then((res) => {
          let arry = this.getPowerIds(res.data);
          this.unitForm.powerIds = arry;
          this.realPowerIds = res.data;
          this.unitCheckedKeys = arry;
        });
        this.dialogTitle = '修改单位';
        this.showUnitDialog = true;
      } else {
        this.$confirm('确定删除该用户？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(async () => {
            let { code } = await delUnit(data.id);
            if (code == '0000') {
              this.$message({
                type: 'success',
                message: '用户已删除!',
                showClose: true,
                duration: 2000
              });
              await this.selectUnitInfo2();
            }
          })
          .catch();
      }
    },
    //选择单位类型
    roleChange(id) {
      if (id == 2) {
        this.authList = JSON.parse(JSON.stringify(this.limitList2));
      } else if (id == 1) {
        this.authList = JSON.parse(JSON.stringify(this.limitList3));
      } else if (id == 0) {
        this.authList = JSON.parse(JSON.stringify(this.limitList4));
      } else {
        this.authList = JSON.parse(JSON.stringify(this.normalList));
      }
      // if (id == 2 || id == 0) {
      //   this.authList = JSON.parse(JSON.stringify(this.limitList2));
      // } else {
      //   this.authList = JSON.parse(JSON.stringify(this.normalList));
      // }
      this.typeRoleList.map((item) => {
        if (item.roleId === id) {
          let arry = this.getPowerIds(item.powerList.concat());
          this.unitCheckedKeys = arry;
          this.unitForm.powerIds = arry;
        }
      });
      let _this = this;
      _this.$nextTick(function () {
        let list = [];
        _this.authList.map((item) => {
          let ids = _this.$refs[item.ref][0]
            .getCheckedKeys()
            .concat(_this.$refs[item.ref][0].getHalfCheckedKeys());
          list.push(...ids);
        });
        _this.realPowerIds = list;
      });
    },
    //筛选去除父级id
    getPowerIds(list) {
      let result = [];
      for (var i = 0; i < list.length; i++) {
        let flag = false;
        for (var j = 0; j < this.pageList.length; j++) {
          if (list[i] == this.pageList[j]) {
            flag = true;
            break;
          }
        }
        if (!flag) result.push(list[i]);
      }
      return result;
    },
    //勾选用户权限
    handleCheckChange(val) {
      let list = [];
      let result = [];
      this.authList.map((item) => {
        let ids = this.$refs[item.ref][0].getCheckedKeys();
        let resultItems = this.$refs[item.ref][0]
          .getCheckedKeys()
          .concat(this.$refs[item.ref][0].getHalfCheckedKeys());
        list.push(...ids);
        result.push(...resultItems);
      });
      this.unitForm.powerIds = list;
      this.realPowerIds = result;
    },
    //新增、修改弹框点击确定
    unitSubmit(formName) {
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          if (this.unitForm.powerIds.length == 0) {
            this.$message({
              message: '请勾选权限！',
              showClose: true,
              type: 'warning',
              duration: 2000
            });
            return;
          } else {
            this.unitForm.powerIds = this.realPowerIds;
            if (this.dialogTitle == '新增单位') {
              let { code } = await addUnit(this.unitForm);
              if (code == '0000') {
                this.showUnitDialog = false;
                this.$message({
                  message: '新增用户成功！',
                  showClose: true,
                  type: 'success',
                  duration: 2000
                });
                await this.selectUnitInfo2();
              }
            }
            if (this.dialogTitle == '修改单位') {
              let { code } = await editUnit(this.unitForm);
              if (code == '0000') {
                this.showUnitDialog = false;
                this.$message({
                  message: '修改单位成功！',
                  showClose: true,
                  type: 'success',
                  duration: 2000
                });
                await this.selectUnitInfo2();
              }
            }
          }
        } else {
          return false;
        }
      });
    },
    //关闭弹框
    dialogClose() {
      this.unitForm = {
        name: '',
        roleId: '',
        authids: []
      };
      this.unitCheckedKeys = [];
    },
    //分页功能
    async handleCurrentChange(val) {
      this.currentPage = val;
      this.selParams.pageNum = val;
      await this.selectUnitInfo2();
    }
  }
};
</script>

<style lang="scss" scoped>
.unitManage {
  height: 100%;
  padding: 20px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  .unitTop {
    height: 40px;
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;
    .unitTopRight {
      display: flex;
      align-items: center;
      .keyInput {
        width: 7.813vw;
      }
      /deep/ .el-select {
        width: 11.9795vw;
        margin: 0 1.042vw;
      }
    }
  }
  .unitBtm {
    height: 75.865vh;
    .pageNation {
      text-align: center;
      padding: 1vw 0;
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
}
//新增、修改弹框样式
.unitDialog {
  .dialogAuth {
    display: flex;
    flex-wrap: wrap;
    .authItem {
      width: 25%;
      max-height: 20.39vh;
    }
  }
  .dialogBtn {
    margin: 0;
    /deep/ .el-form-item__content {
      margin: 0 !important;
      text-align: center;
    }
  }
  /deep/ .el-dialog__body {
    padding-right: 2.4vw;
  }
}
//修改密码弹框样式
.changPwdDialog {
  .dialogBtn {
    margin: 0;
    /deep/ .el-form-item__content {
      margin: 0 !important;
      text-align: center;
    }
  }
  /deep/ .el-dialog__body {
    padding-right: 2.4vw;
  }
}
</style>
