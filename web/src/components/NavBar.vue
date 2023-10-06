<!-- 组件命名必须要两个字母大写，否则报错 -->
<!-- Bootstrap：美工工具 -->
<template>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <div class="container">
    <!-- router-link与a类似，能够实现不刷新跳转 -->
    <router-link class="navbar-brand" :to="{name: 'home'}">林业昆虫智能识别系统</router-link>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarText">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item" v-if="!isMobile">
          <router-link :class="route_name == 'predict_show_index' ? 'nav-link active' : 'nav-link'" :to="{name: 'predict_show_index'}">模型预测</router-link>
        </li>
        <li class="nav-item" v-if="!isMobile">
          <router-link :class="route_name == 'predict_show_mark' ? 'nav-link active' : 'nav-link'" :to="{name: 'predict_show_mark'}">昆虫标注</router-link>
        </li>
        <li class="nav-item">
          <router-link :class="route_name == 'predict_data_index' ? 'nav-link active' : 'nav-link'" :to="{name: 'predict_data_index'}">昆虫数据</router-link>
        </li>
        <li class="nav-item">
          <router-link :class="route_name == 'user_score_index' ? 'nav-link active' : 'nav-link'" :to="{name: 'user_score_index'}">积分平台</router-link>
        </li>
        <li class="nav-item">
          <router-link :class="route_name == 'user_score_ranklist' ? 'nav-link active' : 'nav-link'" :to="{name: 'user_score_ranklist'}">排行榜</router-link>
        </li>
      </ul>
      <ul class="navbar-nav" v-if="$store.state.user.is_login">
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            <img class="nav-photo" :src="$store.state.user.photo" alt="">
            {{ $store.state.user.username }}
          </a>
          <ul class="dropdown-menu">
            <li><router-link class="dropdown-item" :to="{name: 'user_profile_notification'}">站内通知&nbsp;<span class="badge text-bg-info">{{ $store.state.user.notification }}</span></router-link></li>
            <li><router-link class="dropdown-item" :to="{name: 'user_profile_index'}">个人信息</router-link></li>
            <li><router-link class="dropdown-item" :to="{name: 'predict_show_record'}">提交记录</router-link></li>
            <li>
              <form method="post" action="https://support.qq.com/product/381208">
                <input type="hidden" name="openid" :value="$store.state.user.id">
                <input type="hidden" name="nickname" :value="$store.state.user.nickname">
                <input type="hidden" name="avatar" :value="$store.state.user.photo">
                <button class="dropdown-item" type="submit">交流反馈</button>
              </form>
            </li>
            <div v-if="$store.state.user.flag === '2'">
              <li><hr class="dropdown-divider"></li>
              <li><router-link class="dropdown-item" :to="{name: 'manage_mark'}">管理</router-link></li>
            </div>
            <li><hr class="dropdown-divider"></li>
            <li><a class="dropdown-item" href="#" @click="logout">退出</a></li>
          </ul>
        </li>
      </ul>
      <ul class="navbar-nav" v-else-if="!$store.state.user.pulling_info">
        <li class="nav-item">
          <router-link class="nav-link" :to="{ name: 'user_account_login' }" role="button">
            登录
          </router-link>
        </li>
        <li class="nav-item">
          <router-link class="nav-link" :to="{ name: 'user_account_register' }" role="button">
            注册
          </router-link>
        </li>
      </ul>
    </div>
  </div>
</nav>
</template>

<script>
import { useRoute } from 'vue-router'
import { computed } from 'vue'
import { useStore } from 'vuex'

export default {
  setup() {
    const store = useStore();
    const route = useRoute();
    let route_name = computed(() => route.name)
    const isMobile = navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i);
    const logout = () => {
      store.dispatch('logout');
    }

    return {
      route_name,
      isMobile,
      logout
    }
  }
}
</script>

<!-- scoped就是生成F12看到的乱码，这样就不会影响到其他的组件 -->
<style scoped>
img.nav-photo {
  width: 4vh;
  border-radius: 50%;
}
</style>
