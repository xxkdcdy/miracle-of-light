<template>
    <ContentField v-if="!$store.state.user.pulling_info">
        <div class="container justify-content-md-center">
            <div class="row">
                <div class="col-lg-6 col-md-12 login">
                    <div class="page-header">
                        <h3>用户登录</h3>
                    </div>
                    <form @submit.prevent="login">
                        <div class="input-group flex-nowrap">
                            <span class="input-group-text" id="addon-wrapping"></span>
                            <input type="text" v-model="username" class="form-control" placeholder="用户名">
                        </div>
                        <div class="input-group flex-nowrap">
                            <span class="input-group-text" id="addon-wrapping"></span>
                            <input type="password" v-model="password" class="form-control" placeholder="密码">
                        </div>
                        <button type="submit" class="btn btn-success">登录</button>
                    </form>
                </div>
                <div class="col-lg-6 col-md-12">
                    <div class="page-header">
                        <h3>欢迎使用林业昆虫智能识别系统</h3>
                    </div>
                    <div class="alert alert-success" v-if="$store.state.user.registered">注册成功！请使用账号密码登录。</div>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item">请使用注册账号登录，并及时绑定邮箱。</li>
                        <li class="list-group-item">如您忘记密码，请使用绑定邮箱
                                        <router-link class="td-router" :to="{name: 'user_security_findpwd'}">
                                            找回密码
                                        </router-link>。</li>
                        <li class="list-group-item error-message">{{ error_message }}</li>
                    </ul>
                </div>
            </div>
        </div>
    </ContentField>
</template>

<script>
import ContentField from '../../../components/ContentField.vue'
import { useStore } from 'vuex'
import { ref } from 'vue'
import router from '../../../router/index'

export default {
    components: {
        ContentField
    },
    setup() {
        const store = useStore();
        let username = ref('');
        let password = ref('');
        let error_message = ref('');

        const login = () => {
            error_message.value = "";
            store.dispatch("login", {
                username: username.value,
                password: password.value,
                success() {
                    store.dispatch('getinfo', {
                        success() {
                            store.dispatch("getdetail", {
                                success() {
                                },
                                error() {
                                }
                            })
                            router.push({ name: 'home'});
                        }
                    })
                },
                error() {
                    error_message.value = "用户名或密码错误";
                }
            })
        }

        return {
            username,
            password,
            error_message,
            login,
        }
    },
    unmounted() {
        const store = useStore();
        store.commit("updateRegistered", false);
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
div.login {
    background-color: rgb(250, 250, 250);
    border: 1px solid #d5d5d5;
    padding-bottom: 20px;
    padding: 1em;
}
li.error-message {
    color: red;
}
li.td-router {
    color: blue;
    font-weight: 600;
    text-decoration: underline;
}
</style>
