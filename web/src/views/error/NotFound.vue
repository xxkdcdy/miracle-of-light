<template>
    <ContentField>
        <div class="container">
            <div class="row">
                <div class="col text-center">
                    <img src="https://cdn.acwing.com/static/web/img/404.jpg" alt="">
                </div>
                <div class="col error_info">
                    <h2><strong>找不到页面</strong></h2>
                    <p style="font-size: 25px">抱歉，我们找不到您想要的页面……</p>
                    <br>
                    <router-link class="btn btn-default" :to="{name: 'home'}" style="border-radius: 5px" >
                        <button type="button" class="btn btn-primary" style="border-radius: 5px">返回主页</button>
                    </router-link>
                </div>
            </div>
        </div>
    </ContentField>
</template>

<script>
import ContentField from '../../components/ContentField.vue'

export default {
    components: {
        ContentField
    }
}
</script>

<style scoped>
div.error_info {
    margin-top: 1vh;
}
</style>
