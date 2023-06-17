<template>
  <div class="userManage animate__animated animate__fadeIn">
    <div class="userTop">
      <div>
        <el-button v-if="addOpt" type="primary" @click="showAddUser"
          >新增用户</el-button
        >
      </div>
      <div class="userTopRight">
        <el-input
          class="keyInput"
          placeholder="关键词"
          v-model="keyValue"
          clearable
          :maxlength="30"
        ></el-input>
        <el-button type="primary" @click="selectUserInfo"> 查询 </el-button>
      </div>
    </div>
    <br />
    <div class="userBtm">
      <normalTable
        :tableData="tableData"
        :titleList="titleList"
        :opList="opList"
        :pageNum="currentPage"
        :tableName="'用户管理'"
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
      class="userDialog"
      :title="dialogTitle"
      :visible.sync="showUserDialog"
      :modal-append-to-body="false"
      @closed="dialogClose"
      :close-on-click-modal="false"
    >
      <el-form
        v-if="showUserDialog"
        class="userContent"
        ref="userForm"
        :model="userForm"
        :rules="userRules"
        label-width="120px"
      >
        <el-form-item label="用户名：" prop="username">
          <el-input
            v-model="userForm.username"
            placeholder="请输入用户名"
            maxlength="20"
            clearable
            show-word-limit
          ></el-input>
        </el-form-item>
        <el-form-item
          v-if="dialogTitle == '新增用户'"
          label="密码："
          prop="password"
        >
          <el-input
            v-model="userForm.password"
            auto-complete="new-password"
            placeholder="请输入密码"
            type="password"
            maxlength="20"
            clearable
            show-password
            show-word-limit
          ></el-input>
        </el-form-item>
        <el-form-item label="名称：" prop="realName">
          <el-input
            v-model="userForm.realName"
            placeholder="请输入名称"
            maxlength="20"
            clearable
            show-word-limit
          ></el-input>
        </el-form-item>
        <el-form-item label="选择单位：" prop="unitId">
          <el-select
            v-model="userForm.unitId"
            @change="setUnitRoleId"
            style="width: 100%"
            :disabled="dialogTitle == '修改用户'"
          >
            <el-option
              v-for="item in typeRoleList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-if="unitRoleId != 2" label="选择身份：">
          <el-checkbox-group v-model="userForm.appRoleList" style="width: 100%">
            <el-checkbox :label="1">巡查人员</el-checkbox>
            <el-checkbox :label="2">维养人员</el-checkbox>
            <el-checkbox :label="3">检测人员</el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="手机号：">
          <el-input
            v-model="userForm.phone"
            placeholder="请输入手机号"
            maxlength="11"
            clearable
            show-word-limit
          ></el-input>
        </el-form-item>
        <el-form-item label="邮箱：">
          <el-input
            v-model="userForm.email"
            placeholder="请输入邮箱"
            maxlength="30"
            clearable
            show-word-limit
          ></el-input>
        </el-form-item>
      </el-form>
      <div class="dialogBtn">
        <el-button type="primary" @click="userSubmit('userForm')"
          >确定</el-button
        >
        <el-button @click="showUserDialog = false">取消</el-button>
      </div>
    </el-dialog>
    <!-- 修改密码弹框 -->
    <el-dialog
      class="changPwdDialog"
      title="修改密码"
      :visible.sync="showChangePwdDialog"
      append-to-body
      @closed="dialogClose"
      :close-on-click-modal="false"
    >
      <el-form
        v-if="showChangePwdDialog"
        class="changePwdContent"
        ref="changePwdForm"
        :model="changePwdForm"
        :rules="changePwdRules"
        label-width="120px"
      >
        <el-form-item label="旧密码" prop="oldPassword">
          <el-input
            v-model="changePwdForm.oldPassword"
            placeholder="请输入密码"
            type="password"
            maxlength="20"
            clearable
            show-password
            show-word-limit
            auto-complete="new-password"
          ></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input
            v-model="changePwdForm.newPassword"
            placeholder="请输入密码"
            type="password"
            maxlength="20"
            clearable
            show-password
            show-word-limit
            auto-complete="new-password"
          ></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input
            v-model="changePwdForm.confirmPassword"
            placeholder="请输入密码"
            type="password"
            maxlength="20"
            clearable
            show-password
            show-word-limit
            auto-complete="new-password"
          ></el-input>
        </el-form-item>
        <el-form-item label="">
          <el-button class="reset" @click="resetPassword">重置密码</el-button>
        </el-form-item>
      </el-form>
      <div class="dialogBtn">
        <el-button type="primary" @click="changePwdSubmit('changePwdForm')"
          >确定</el-button
        >
        <el-button @click="showChangePwdDialog = false">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import {
  getUserList,
  addUser,
  editUser,
  delUser,
  editUserStatus,
  changePassword,
  getUserUnitList,
  removeBindDevice
} from '@/api/infomanage/userManage';
import md5 from 'js-md5';
import normalTable from '@/components/table/normalTable';
export default {
  name: 'userManage',
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
        userId: this.$store.getters.getUserInfo.id
      },
      typeSelectRole: [],
      typeRoleList: [],
      keyValue: '',
      roleValue: '',
      titleList: [
        { id: 1, prop: 'username', label: '用户名' },
        { id: 2, prop: 'realName', label: '名称' },
        { id: 3, prop: 'unitName', label: '所属单位' },
        { id: 5, prop: 'creator', label: '创建者' },
        { id: 6, prop: 'createTime', label: '创建时间' }
      ],
      tableData: [],
      opList: [
        { id: 1, name: '修改', opShow: false, type: 'updateOpt' },
        {
          id: 2,
          name: '修改密码',
          opShow: false,
          type: 'updateOpt'
        },
        {
          id: 3,
          name: '删除',
          opShow: false,
          type: 'deleteOpt'
        },
        { id: 4, name: '解除绑定', opShow: false, type: 'updateOpt' }
      ],
      total: 0,
      currentPage: 1,
      pageSize: 10,
      //新增、修改弹框
      dialogTitle: '',
      showUserDialog: false,
      unitRoleId: '', //选择单位选中的角色id
      userForm: {
        username: '',
        realName: '',
        appRoleList: [],
        password: '',
        phone: '',
        email: '',
        unitId: ''
      },
      userRules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, message: '用户名最少3位', trigger: 'blur' },
          {
            pattern: /^[A-Za-z0-9]+$/,
            message: '不允许输入中文或特殊符号'
          }
        ],
        realName: [{ required: true, message: '请输入名称', trigger: 'blur' }],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 8, message: '密码最少8位', trigger: 'blur' },
          {
            pattern: /^[a-z0-9~!@#$%^&*._-]+$/,
            message: '密码仅包含字母、数字及常用特殊符号(~!@#$%^&*._-)'
          }
        ],
        unitId: [{ required: true, message: '请选择单位', trigger: 'change' }]
      },
      //修改密码弹框
      showChangePwdDialog: false,
      changePwdForm: {
        oldPassword: '',
        newPassword: '',
        confirmPassword: ''
      },
      changePwdRules: {
        oldPassword: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, message: '密码最少6位', trigger: 'blur' }
        ],
        newPassword: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, message: '密码最少6位', trigger: 'blur' },
          {
            pattern: /^[a-z0-9~!@#$%^&*._-]+$/,
            message: '密码包含字母、数字及常用特殊符号(~!@#$%^&*._-)'
          }
        ],
        confirmPassword: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, message: '密码最少6位', trigger: 'blur' },
          {
            pattern: /^[a-z0-9~!@#$%^&*._-]+$/,
            message: '密码包含字母、数字及常用特殊符号(~!@#$%^&*._-)'
          },
          { validator: validataPass, trigger: 'blur' }
        ]
      }
    };
  },
  beforeMount() {
    this.$utils.getAuthPage(this); //获取权限
  },
  mounted() {
    this.selectUserInfo();
    this.getUserUnitList();
  },
  methods: {
    //查询列表
    async selectUserInfo() {
      let roleId = this.$store.getters.getRoleInfo.id;
      let userId = this.$store.getters.getUserInfo.id;
      let params = {
        pageNum: 1,
        pageSize: this.pageSize,
        keyword: this.keyValue,
        roleId: this.roleValue,
        userId
      };
      let { code, data } = await getUserList(params);
      if (code == '0000') {
        let arry = data.list;
        arry.map((item) => {
          if (item.deviceId == null) {
            item.opacityName = '解除绑定';
          }
          if (item.id == userId) {
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
    async selectUserInfo2() {
      let roleId = this.$store.getters.getRoleInfo.id;
      let { code, data } = await getUserList(this.selParams);
      if (code == '0000') {
        let arry = data.list;
        arry.map((item) => {
          if (item.deviceId == null) {
            item.opacityName = '解除绑定';
          }
          if (item.id == this.selParams.userId) {
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
    //获取选择单位列表
    async getUserUnitList() {
      let { code, data } = await getUserUnitList();
      if (code == '0000') {
        this.typeRoleList = data;
      }
    },
    //点击新增用户
    showAddUser() {
      this.dialogTitle = '新增用户';
      this.showUserDialog = true;
    },
    //表格操作点击
    tableClick(index, data) {
      if (index == 1) {
        this.unitRoleId = data.roleId;
        this.userForm = JSON.parse(JSON.stringify(data));
        this.userForm.appRoleList = data.appRoleList || [];
        this.dialogTitle = '修改用户';
        this.showUserDialog = true;
      } else if (index == 2) {
        this.changePwdForm.userId = data.id;
        this.changePwdForm.realPassword = data.password;
        this.showChangePwdDialog = true;
      } else if (index == 3) {
        this.$confirm('确定删除该用户？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(async () => {
            let { code } = await delUser(data.id);
            if (code == '0000') {
              this.$message({
                type: 'success',
                message: '用户已删除!',
                showClose: true,
                duration: 2000
              });
              await this.selectUserInfo2();
            }
          })
          .catch();
      } else if (index == 4) {
        this.$confirm('确定解绑该用户？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(async () => {
            let { code } = await removeBindDevice(data.id);
            if (code == '0000') {
              this.$message({
                type: 'success',
                message: '解除绑定成功！',
                showClose: true,
                duration: 2000
              });
              await this.selectUserInfo2();
            }
          })
          .catch();
      } else {
        editUserStatus(data.id, data.status)
          .then((res) => {
            if (res.code == '0000') {
              this.$message({
                type: 'success',
                message: '用户状态更改成功！',
                showClose: true,
                duration: 2000
              });
              this.selectUserInfo2();
            }
          })
          .catch();
      }
    },
    //重置密码
    resetPassword() {
      this.$confirm(`确定重置密码？重置后密码为：12345678`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async () => {
          let password = `12345678`;
          let model = {
            id: this.changePwdForm.userId,
            password: md5(password)
          };
          let { code } = await changePassword(model);
          if (code == '0000') {
            this.$message({
              type: 'success',
              message: '密码重置成功!',
              showClose: true,
              duration: 2000
            });
            this.showChangePwdDialog = false;
            await this.selectUserInfo2();
          }
        })
        .catch();
    },
    //新增、修改弹框点击确定
    userSubmit(formName) {
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          let phoneReg = /^1[3|4|5|7|8][0-9]{9}$/;
          let emailReg = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
          console.log(this.userForm.appRoleList, this.userForm.phone);
          if (
            this.userForm.appRoleList != null &&
            this.userForm.appRoleList.length > 0 &&
            this.userForm.phone.trim() == ''
          ) {
            this.$message({
              message: '选择身份后手机号不能为空！',
              type: 'warning',
              showClose: true,
              duration: 2000
            });
            return;
          }
          if (
            this.userForm.phone.length > 0 &&
            !phoneReg.test(this.userForm.phone)
          ) {
            this.$message({
              message: '手机号码格式不正确！',
              type: 'warning',
              showClose: true,
              duration: 2000
            });
            return;
          }
          if (
            this.userForm.email != null &&
            this.userForm.email.length > 0 &&
            !emailReg.test(this.userForm.email)
          ) {
            this.$message({
              message: '邮箱格式不正确！',
              type: 'warning',
              showClose: true,
              duration: 2000
            });
            return;
          }
          let roleId = '';
          this.typeRoleList.map((item) => {
            if (item.id == this.userForm.unitId) {
              roleId = item.roleId;
            }
          });
          let model = {
            id: this.userForm.id,
            username: this.userForm.username,
            realName: this.userForm.realName,
            password:
              this.dialogTitle == '新增用户'
                ? md5(this.userForm.password)
                : null,
            phone: this.userForm.phone != '' ? Number(this.userForm.phone) : '',
            appRoleList: this.userForm.appRoleList,
            unitId: this.userForm.unitId,
            email: this.userForm.email,
            roleId
          };
          if (this.dialogTitle == '新增用户') {
            let { code } = await addUser(model);
            if (code == '0000') {
              this.showUserDialog = false;
              this.$message({
                message: '新增用户成功！',
                showClose: true,
                type: 'success',
                duration: 2000
              });
              await this.selectUserInfo2();
            }
          }
          if (this.dialogTitle == '修改用户') {
            let { code } = await editUser(model);
            if (code == '0000') {
              this.showUserDialog = false;
              this.$message({
                message: '修改用户成功！',
                showClose: true,
                type: 'success',
                duration: 2000
              });
              await this.selectUserInfo2();
            }
          }
        } else {
          return false;
        }
      });
    },
    //修改密码弹框点击确定
    changePwdSubmit(formName) {
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          if (
            this.changePwdForm.realPassword !=
            md5(this.changePwdForm.oldPassword)
          ) {
            console.log(
              this.changePwdForm.realPassword,
              md5(this.changePwdForm.oldPassword)
            );
            this.$message({
              message: '旧密码错误！',
              showClose: true,
              type: 'error',
              duration: 2000
            });
            return false;
          }
          let params = {
            id: this.changePwdForm.userId,
            password: md5(this.changePwdForm.newPassword)
          };
          let { code } = await changePassword(params);
          if (code == '0000') {
            this.$message({
              message: '修改密码成功！',
              showClose: true,
              type: 'success',
              duration: 2000
            });
            this.showChangePwdDialog = false;
            this.changePwdForm.oldPassword = '';
            this.changePwdForm.newPassword = '';
            this.changePwdForm.realPassword = '';
            await this.selectUserInfo2();
          }
        } else {
          return false;
        }
      });
    },
    //设置选择单位的角色id
    setUnitRoleId(id) {
      this.userForm.appRoleList = [];
      this.typeRoleList.map((item) => {
        if (item.id === id) this.unitRoleId = item.roleId;
      });
    },
    //关闭弹框
    dialogClose() {
      this.userForm = {
        username: '',
        realName: '',
        appRoleList: [],
        password: '',
        phone: '',
        email: '',
        unitId: ''
      };
      this.changePwdForm = {
        oldPassword: '',
        newPassword: '',
        confirmPassword: ''
      };
    },
    //分页功能
    async handleCurrentChange(val) {
      this.currentPage = val;
      this.selParams.pageNum = val;
      await this.selectUserInfo2();
    }
  }
};
</script>

<style lang="scss" scoped>
.userManage {
  height: 100%;
  padding: 20px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  .userTop {
    height: 40px;
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;
    .userTopRight {
      display: flex;
      align-items: center;
      .keyInput {
        width: 7.813vw;
        margin-right: 20px;
      }
      /deep/ .el-select {
        width: 11.9795vw;
        margin: 0 1.042vw;
      }
    }
  }
  .userBtm {
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
.userDialog {
  .dialogAuth {
    display: flex;
    flex-wrap: wrap;
    .authItem {
      width: 25%;
      max-height: 20.39vh;
    }
  }
  .dialogBtn {
    width: 100%;
    text-align: center;
  }
  /deep/ .el-dialog {
    width: 520px;
  }
  /deep/ .el-form-item__content {
    // width: 320px;
    padding-right: 32px;
  }
  /deep/ .el-dialog__body {
    padding: 24px;
    display: flex;
    flex-direction: column;
  }
}
//修改密码弹框样式
.changPwdDialog {
  .reset {
    padding: 8px 15px;
    border: 1px solid #419aff;
    color: #419aff;
  }
  .dialogBtn {
    width: 100%;
    text-align: center;
  }
  /deep/ .el-dialog {
    width: 520px;
  }
  /deep/ .el-form-item__content {
    width: 320px;
  }
  /deep/ .el-dialog__body {
    padding: 24px;
    display: flex;
    flex-direction: column;
  }
}
</style>
