<template>
    <ContentField v-if="!$store.state.user.pulling_info">
        <div class="container justify-content-md-center">
            <div class="row">
                <div class="col-lg-6 col-md-12 register">
                    <div class="page-header">
                        <h3>用户注册</h3>
                    </div>
                    <form @submit.prevent="register">
                        <div class="input-group flex-nowrap">
                            <span class="input-group-text" id="addon-wrapping"></span>
                            <input type="text" v-model="username" class="form-control" placeholder="用户名">
                        </div>
                        <div class="input-group flex-nowrap">
                            <span class="input-group-text" id="addon-wrapping"></span>
                            <input type="password" v-model="password" class="form-control" placeholder="密码">
                        </div>
                        <div class="input-group flex-nowrap">
                            <span class="input-group-text" id="addon-wrapping"></span>
                            <input type="password" v-model="confirmedPassword" class="form-control" placeholder="确认密码">
                        </div>
                        <button type="submit" class="btn btn-success">注册</button>
                    </form>
                </div>
                <div class="col-lg-6 col-md-12">
                    <div class="page-header">
                        <h3>欢迎使用林业昆虫智能识别系统</h3>
                    </div>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item">请填写注册信息。</li>
                        <li class="list-group-item">系统会判断重复用户名，遇到重复用户名时，请尝试更换用户名。</li>
                        <li class="list-group-item error-message">{{ error_message }}</li>
                    </ul>
                </div>
            </div>
        </div>
    </ContentField>
</template>

<script>
import ContentField from '../../../components/ContentField.vue'
import { ref } from 'vue'
import router from '../../../router/index'
import $ from 'jquery'
import { useStore } from 'vuex'


export default {
    components: {
        ContentField
    },
    setup() {
        const store = useStore();
        let username = ref('');
        let password = ref('');
        let confirmedPassword = ref('');
        let error_message = ref('');

        const register = () => {
            error_message.value = '';
            $.ajax({
                url: "https://app4460.acapp.acwing.com.cn/api/user/account/register/",
                type: "post",
                data: {
                    username: username.value,
                    password: password.value,
                    confirmedPassword: confirmedPassword.value,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        store.commit("updateRegistered", true);
                        router.push({ name: "user_account_login" })
                    } else {
                        error_message.value = resp.error_message;
                    }
                },
            })
        }

        return {
            username,
            password,
            confirmedPassword,
            error_message,
            register,
        }
    }
}
</script>

<style scoped>
button {
    width: 100%;
    margin-top: 1em;
}
div.row {
    padding: 1em;
}
div.input-group {
    margin-top: 1em;
    margin-bottom: 1em;
}
div.register {
    background-color: rgb(250, 250, 250);
    border: 1px solid #d5d5d5;
    padding-bottom: 20px;
    padding: 1em;
}
li.error-message {
    color: red;
}
</style>
