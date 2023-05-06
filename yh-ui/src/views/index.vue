<template>
  <div class="app-container home">
    <el-row :gutter="20">
      <el-col :sm="24" :lg="24">
        <blockquote class="text-warning" style="font-size: 14px">
          【腾讯云】云服务器、云数据库、COS、CDN、短信等云产品特惠热卖中
          <br />
          <el-link
            href="https://cloud.tencent.com/act/cps/redirect?redirect=2446&cps_key=133a9ecf06a80ddc0d58e464fe465055&from=console"
            type="primary"
            target="_blank"
            >https://cloud.tencent.com/act/cps/redirect?redirect=2446&cps_key=133a9ecf06a80ddc0d58e464fe465055&from=console</el-link
          >
          <br />
          【阿里云】阿里云服务器折扣区
          <br />
          <el-link
            href="https://www.aliyun.com/minisite/goods?userCode=aeq6zy8g"
            type="primary"
            target="_blank"
            >https://www.aliyun.com/minisite/goods?userCode=aeq6zy8g</el-link
          >
          <br />
          【阿里云】学生福利
          <br />
          <el-link
            href="https://www.aliyun.com/daily-act/ecs/activity_share?userCode=aeq6zy8g"
            type="primary"
            target="_blank"
            >https://www.aliyun.com/daily-act/ecs/activity_share?userCode=aeq6zy8g</el-link
          >
          <h4 class="text-danger">
            云产品通用红包，可叠加官网常规优惠使用。(仅限新用户)
          </h4>
        </blockquote>

        <hr />
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :sm="24" :lg="12" style="padding-left: 20px">
        <h2>yh-qqrobot开放平台</h2>
        <p>
          基于若依和go-cqhttp实现的机器人，因为喜欢研究一下比较有趣的东西，然后这种机器人暂时没见到使用java开发，所以就写了一个来玩
        </p>
        <p>
          <el-tag type="danger">&yen;免费开源</el-tag>
        </p>
        <p>
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-cloudy"
            plain
            @click="goTarget('https://github.com/nssnail/yh-qqrobot-basics')"
            >访问github</el-button
          >
          <el-button
            size="mini"
            icon="el-icon-document"
            plain
            @click="goTarget('https://docs.yh-qqrobot.com/')"
            >参考文档</el-button
          >
        </p>
      </el-col>

      <!-- <el-col :sm="24" :lg="12" style="padding-left: 50px">
        <el-row>
          <el-col :span="12">
            <h2>技术选型</h2>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <h4>后端技术</h4>
            <ul>
              <li>SpringBoot</li>
              <li>Spring Security</li>
              <li>JWT</li>
              <li>MyBatis</li>
              <li>Druid</li>
              <li>Fastjson</li>
              <li>...</li>
            </ul>
          </el-col>
          <el-col :span="6">
            <h4>前端技术</h4>
            <ul>
              <li>Vue</li>
              <li>Vuex</li>
              <li>Element-ui</li>
              <li>Axios</li>
              <li>Sass</li>
              <li>Quill</li>
              <li>...</li>
            </ul>
          </el-col>
        </el-row>
      </el-col> -->
    </el-row>
    <el-divider />
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>联系信息</span>
          </div>
          <div class="body">
            <p>
              <i class="el-icon-message"></i> 联系邮箱：q1191986647@163.com<br />
              <i class="el-icon-user-solid"></i> QQ号：2844089416
              (问题请输入'qq'验证)<br />
              <i class="el-icon-user-solid"></i> QQ群：742321295
            </p>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>捐赠支持</span>
          </div>
          <div class="body">
            <img
              src="https://base-1300681170.cos.ap-nanjing.myqcloud.com//docs/qqrobotimage-20230506100141459.png"
              alt="donate"
              width="100%"
            />
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "Index",
  data() {
    return {
      // 版本号
      version: "3.8.5",
    };
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    },
  },
};
</script>

<style scoped lang="scss">
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }
  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }
  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
}
</style>
