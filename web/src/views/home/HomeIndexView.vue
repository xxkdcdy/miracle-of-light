<template>
    <ContentField>
        <div class="row align-items-lg-center">
          <div class="col-8 mx-auto col-md-4 order-md-2 col-lg-5">
            <svg xmlns="http://www.w3.org/2000/svg" width="600" height="533" class="img-fluid mb-md-0" role="img" viewBox="0 0 900 800">
              <title>Miracle Of Light</title>
              <image href="data:image/png;base64,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" xlink:href="data:image/png;base64,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" id="svg_1" height="533" width="600" y="33.5" x="150"/>
            </svg>
            <!-- <img src="https://miracle-of-light.oss-cn-shanghai.aliyuncs.com/user/profile/icon_default.jpeg" class="img-responsive" alt=""> -->
          </div>
          <div class="col-md-8 order-md-1 col-lg-7 text-center text-md-start">
            <h1 class="bd-text-purple-bright index_top_h1">使用Miracle_Of_Light认识昆虫！</h1>
            <p class="lead index_top_p mt-4" style="font-style: oblique;font-size: 1.01rem !important">
              一个伟大的林业昆虫智能识别管理系统。 </p>
            <p class="lead index_top_p mb-4"> Miracle_Of_Light 是一套用于 Web端、移动端 和 接口平台
              的多平台系统，基于离线和在线识别功能，以及一系列社区化功能，提供认识昆虫，认识环境的平台，您能够通过平台工具了解到保护环境的重要性 。 </p>

            <div class="d-flex flex-column flex-md-row">
              <a :href="versionUrl" class="get-start btn btn-lg btn-bd-primary mb-1 w-100 me-1">下载 虫博士App</a>
              <button class="btn btn-lg btn-outline-secondary mb-1 w-100" @click="register">注册账户</button>
            </div>
            <div class="mt-1 d-block text-center">
              当前版本：{{ versionCode }} | 软件更新于：{{ createTime }}<br>版本信息：{{ versionDescription }}
              <p style="font-style:italic;">开发工作：xxkdcdy[dychan2000@163.com]</p>
            </div>
          </div>
        </div>

        <section class="row pb-md-4 align-items-center">
        <div class="col-md-5">
          <h2 class="display-5 fw-normal">昆虫识别接口接入</h2>
          <p class="lead fw-normal">
            向管理员获取参数和appKey，使用接口将昆虫识别功能接入自己的项目。
          </p>
        </div>
        <div class="code-container col-md-7 ps-md-5">
          <div class="bd-clipboard"></div>
          <button type="button" class="btn-clipboard" @click="clip">
            复制这段代码
          </button>
          <div class="highlight">
            <pre class="chroma"><code class="language-sh" id="codeText" data-lang="sh">https://app4460.acapp.acwing.com.cn/model/healthcheck/</code></pre>
          </div>
        </div>
      </section>
    </ContentField>
</template>

<script>
import ContentField from '../../components/ContentField.vue'
import { useStore } from 'vuex'
import router from '../../router/index'
import $ from 'jquery'
import { ref } from 'vue'

export default {
    components: {
        ContentField
    },
    setup() {
      const store = useStore();
      const jwt_token = localStorage.getItem("jwt_token");
      let versionCode = ref("");
      let versionUrl = ref("");
      let createTime = ref("");
      let versionDescription = ref("");
      if(jwt_token) {
        store.commit("updateToken", jwt_token);
        store.dispatch("getinfo", {
          success() {
            store.dispatch("getdetail", {
              success() {
              },
              error() {
              }
            })
            router.push({ name: "home" });
            store.commit("updatePullingInfo", false);
          },
          error() {
            store.commit("updatePullingInfo", false);
          }
        })
      } else {
          store.commit("updatePullingInfo", false);
      }

      const register = () => {
        store.dispatch('logout');
        router.push({ name: "user_account_login" });
      }

      const clip = () => {
        const codeText = document.getElementById("codeText");
        // text是复制文本
        // 创建input元素
        const el = document.createElement('input')
        // 给input元素赋值需要复制的文本
        el.setAttribute('value', codeText.innerHTML);
        // 将input元素插入页面
        document.body.appendChild(el)
        // 选中input元素的文本
        el.select()
        // 复制内容到剪贴板
        document.execCommand('copy')
        // 删除input元素
        document.body.removeChild(el)
        alert('复制成功')
      }

      $.ajax({
        url: "https://app4460.acapp.acwing.com.cn/api/version/latest/",
        type: "get",
        success(resp) {
          if (resp.error_message === "success") {
            versionCode.value = resp.version_message.versionCode;
            versionUrl.value = resp.version_message.versionUrl;
            createTime.value = resp.version_message.createTime;
            versionDescription.value = resp.version_message.versionDescription;
          }
        },
        error(resp) {
          console.log(resp);
        }
      })
      return {
        versionCode,
        versionUrl,
        createTime,
        versionDescription,
        register,
        clip,
      }
    }
}
</script>

<style scoped>
.get-start {
    background-color: green;
    color: #fff;
    font-weight: 600;
}
.code-container {
    background-color: #f8f9fa;
}
.btn-clipboard {
    width: 6rem;
    top: 0;
    right: 0.65rem;
    z-index: 10;
    display: block;
    padding: 0.25rem 0.5rem;
    font-size: .65em;
    color: #0d6efd;
    background-color: #fff;
    border: 1px solid;
    border-radius: 0.25rem;
}
.btn-clipboard:hover {
    color: #fff;
    background-color: #0d6efd;
}
</style>
