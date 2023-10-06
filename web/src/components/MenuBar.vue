<template>
    <div class="col-md-3" style="background-color:green;">
        <div class="row">
            <div class="col-md-12" style="height: 70px;line-height: 70px;font-size: 20px;color: #9acfea;font-weight: bold; background-color: rgb(0,40,77);text-align: center">
                <img src="https://miracle-of-light.oss-cn-shanghai.aliyuncs.com/user/profile/Menu.jpeg" style="width: 30px;height: 30px;margin-right: 8px">管理系统
            </div>
        </div>

        <div class="row">
            <div class="col-md-12 menu-tab">
                <router-link :class="route_name == 'manage_mark' ? 'show-active' : ''" :to="{name: 'manage_mark'}">标注信息</router-link>
            </div>
        </div>

        <div class="row">
            <div class="col-md-12 menu-tab">
                <router-link :class="route_name == 'manage_insect' ? 'show-active' : ''" :to="{name: 'manage_insect'}">昆虫信息</router-link>
            </div>
        </div>

        <div class="row">
            <div class="col-md-12 menu-tab">
                <router-link :class="route_name == 'manage_exchange' ? 'show-active' : ''" :to="{name: 'manage_exchange'}">兑换信息</router-link>
            </div>
        </div>

        <div class="row">
            <div class="col-md-12 menu-tab">
                <router-link :class="route_name == 'manage_user' ? 'show-active' : ''" :to="{name: 'manage_user'}">用户信息</router-link>
            </div>
        </div>

        <div class="row">
            <div class="col-md-12 menu-tab">
                <router-link :class="route_name == 'manage_version' ? 'show-active' : ''" :to="{name: 'manage_version'}">版本信息</router-link>
            </div>
        </div>

        <div class="row">
            <div class="col-md-12 menu-tab">
                <router-link :class="route_name == 'manage_exchange_log' ? 'show-active' : ''" :to="{name: 'manage_exchange_log'}">发货信息</router-link>
            </div>
        </div>

        <div class="row">
            <div class="col-md-12 menu-tab">
                <router-link :class="route_name == 'manage_index' ? 'show-active' : ''" :to="{name: 'manage_index'}">积分发放</router-link>
            </div>
        </div>
    </div>
</template>

<script>
import { useRoute } from 'vue-router'
import { computed } from 'vue'

export default {
    setup() {
        const route = useRoute();
        let route_name = computed(() => route.name)
        return {
            route_name,
        }
    }
}
</script>


<style scoped>
a:link{
    font-size: 20px;
    color:#fff;
    text-decoration: none;
}
a:visited{
    font-size: 20px;
    color:#fff;
}
a:hover{
    font-size: 20px;
    color: blue;
    text-decoration: none;
}
.menu-tab {
    text-align: center;
    padding: 15px 0px 15px 0px;
}
.show-active {
    border-style: outset;
}
</style>