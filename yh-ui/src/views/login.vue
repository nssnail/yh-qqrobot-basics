<template>
  <div class="login">
    <div class="login-form-box">
      <div class="login-form-left">
        <div class="tips-title">欢迎使用yh-qqrobot平台</div>
        <div class="tips-contain">
          <ul>
            <li>spring boot</li>
            <li>redis</li>
            <li>mysql</li>
            <!-- <li>rocketmq</li> -->
            <li>quartz</li>
            <li>腾讯云oss</li>
          </ul>
        </div>
      </div>
      <div class="login-form-right">
        <el-form
          ref="loginForm"
          :model="loginForm"
          :rules="loginRules"
          class="login-form"
        >
          <h3 class="title">登录</h3>
          <el-form-item prop="username">
            <el-input
              v-model="loginForm.username"
              type="text"
              auto-complete="off"
              placeholder="账号"
            >
              <svg-icon
                slot="prefix"
                icon-class="user"
                class="el-input__icon input-icon"
              />
            </el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input
              v-model="loginForm.password"
              type="password"
              auto-complete="off"
              placeholder="密码"
              @keyup.enter.native="handleLogin"
            >
              <svg-icon
                slot="prefix"
                icon-class="password"
                class="el-input__icon input-icon"
              />
            </el-input>
          </el-form-item>
          <el-form-item class="captcha-input" prop="code" v-if="captchaEnabled">
            <el-input
              v-model="loginForm.code"
              auto-complete="off"
              placeholder="验证码"
              @keyup.enter.native="handleLogin"
            >
              <svg-icon
                slot="prefix"
                icon-class="validCode"
                class="el-input__icon input-icon"
              />
            </el-input>
            <div class="login-code-box">
              <img :src="codeUrl" @click="getCode" class="login-code-img" />
            </div>
          </el-form-item>
          <!-- <el-checkbox
          v-model="loginForm.rememberMe"
          style="margin: 0px 0px 25px 0px;color:#FFF"
          >记住密码</el-checkbox
        > -->
          <el-form-item style="width: 100%">
            <el-button
              class="login-btn"
              :loading="loading"
              size="medium"
              type="primary"
              style="width: 100%"
              @click.native.prevent="handleLogin"
            >
              <span v-if="!loading">登 录</span>
              <span v-else>登 录 中...</span>
            </el-button>
            <div style="float: right" v-if="register">
              <router-link class="link-type" :to="'/register'"
                >立即注册</router-link
              >
            </div>
          </el-form-item>
        </el-form>
      </div>
    </div>
    <!--  底部  -->
    <div class="el-login-footer">
      <div>Copyright © 2023-2023 | yh-qqrobot平台</div>
    </div>
  </div>
</template>

<script>
import { getCodeImg } from "@/api/login";
import Cookies from "js-cookie";
import { encrypt, decrypt } from "@/utils/jsencrypt";

export default {
  name: "Login",
  data() {
    return {
      codeUrl: "",
      loginForm: {
        username: "",
        password: "",
        rememberMe: false,
        code: "",
        uuid: "",
      },
      loginRules: {
        username: [
          { required: true, trigger: "blur", message: "请输入您的账号" },
        ],
        password: [
          { required: true, trigger: "blur", message: "请输入您的密码" },
        ],
        code: [{ required: true, trigger: "change", message: "请输入验证码" }],
      },
      loading: false,
      // 验证码开关
      captchaEnabled: true,
      // 注册开关
      register: false,
      redirect: undefined,
    };
  },
  watch: {
    $route: {
      handler: function (route) {
        this.redirect = route.query && route.query.redirect;
      },
      immediate: true,
    },
  },
  created() {
    this.getCode();
    this.getCookie();
  },
  methods: {
    getCode() {
      getCodeImg().then((res) => {
        this.captchaEnabled =
          res.captchaEnabled === undefined ? true : res.captchaEnabled;
        if (this.captchaEnabled) {
          this.codeUrl = "data:image/gif;base64," + res.img;
          this.loginForm.uuid = res.uuid;
        }
      });
    },
    getCookie() {
      const username = Cookies.get("username");
      const password = Cookies.get("password");
      const rememberMe = Cookies.get("rememberMe");
      this.loginForm = {
        username: username === undefined ? this.loginForm.username : username,
        password:
          password === undefined ? this.loginForm.password : decrypt(password),
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe),
      };
    },
    handleLogin() {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          this.loading = true;
          if (this.loginForm.rememberMe) {
            Cookies.set("username", this.loginForm.username, { expires: 30 });
            Cookies.set("password", encrypt(this.loginForm.password), {
              expires: 30,
            });
            Cookies.set("rememberMe", this.loginForm.rememberMe, {
              expires: 30,
            });
          } else {
            Cookies.remove("username");
            Cookies.remove("password");
            Cookies.remove("rememberMe");
          }
          this.$store
            .dispatch("Login", this.loginForm)
            .then(() => {
              this.$router.push({ path: this.redirect || "/" }).catch(() => {});
            })
            .catch(() => {
              this.loading = false;
              if (this.captchaEnabled) {
                this.getCode();
              }
            });
        }
      });
    },
  },
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-image: url("../assets/images/bg.jpg");
  background-size: cover;
}
.title {
  margin: 0px auto 30px auto;
  text-align: center;
  color: #7fdfdf;
  letter-spacing: 5px;
  font-family: "STKaiti";
  font-weight: bold;
  font-size: 20px;
}
.login-form-box {
  // background-image: url("../assets/images/bg.jpg");
  // background-size: cover;
  display: flex;
  // align-items: center;
  // background: #7fdfdf;
  background: rgba($color: #7fdfdf, $alpha: 0.7);
  border-radius: 6px;
  box-shadow: 0 4px 12px 0 rgba(0, 0, 0, 0.1);
}
.login-form {
  width: 100%;
  .el-input {
    height: 38px;
    input {
      height: 38px;
    }
  }
  .input-icon {
    height: 39px;
    width: 14px;
    margin-left: 2px;
  }
}
.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}
.login-code-box {
  cursor: pointer;
  width: 100px;
  height: 40px;
  right: 0;
  top: 0;
  position: absolute;
}
.login-code {
  width: 100px;
  height: 40px;
  img {
    cursor: pointer;
    vertical-align: middle;
  }
}
.el-login-footer {
  // height: 40px;
  // line-height: 40px;
  display: flex;
  // justify-content: center;
  flex-direction: column;
  align-items: center;
  position: fixed;
  bottom: 20px;
  width: 100%;
  // text-align: center;
  color: #fff;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
}
.login-code-img {
  height: 38px;
}
.login-btn {
  background: #7fdfdf;
  color: #fff;
  border: 0;
}
.login-form-left {
  // background: #000;
  font-family: Arial;
  color: #fff;
  width: 300px;
  padding: 25px;
}
.login-form-right {
  display: flex;
  align-items: center;
  // background: #fff;
  border-radius: 6px;
  // padding: 25px 30px;
  height: 400px;
  background: #fff;
  width: 400px;
  padding: 25px 25px 5px 25px;
}
::v-deep .el-input input {
  border-radius: 0;
  border: 0;
  border-bottom: 1px solid#7fdfdf;
  height: 40px;
}
.captcha-input {
  position: relative;
}
.tips-title {
  font-size: 20px;
  font-weight: bold;
  letter-spacing: 5px;
}
.tips-contain ul {
  padding: 10px;
}
</style>
