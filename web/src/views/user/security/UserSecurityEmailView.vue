<template>
    <ContentField>
        <div class="container justify-content-md-center text-center">
            <h3>邮箱认证</h3>
            <div class="row">
                <div class="input-group mt-3 offset-3 col-6" style="width: 50%">
                    <input v-model="email" type="text" class="form-control" placeholder="邮箱">
                    <button class="btn btn-outline-secondary" @click="send_email" type="button" id="button-addon2">发送验证码</button>
                </div>
                <div class="input-group mt-3 offset-3 col-6" style="width: 50%">
                    <span class="input-group-text" id="basic-addon1">验证码</span>
                    <input v-model="code" type="text" class="form-control" placeholder="请输入邮箱中收到的验证码" aria-label="Username" aria-describedby="basic-addon1">
                </div>
                <button type="button" v-if="!$store.state.user.emailing" @click="verify_email" class="btn btn-success col-2 offset-5 mt-5">认证</button>
                <div v-else class="spinner-border text-primary col-2 offset-6 mt-5"></div>
            </div>
        </div>
        <!-- 更新Toast -->
        <div class="toast-container position-fixed bottom-0 end-0 p-3">
            <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                <div class="toast-header">
                    <strong class="me-auto">消息通知</strong>
                    <small>几秒前</small>
                    <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                </div>
                <div class="toast-body">
                    {{ error_message }}
                </div>
            </div>
        </div>
    </ContentField>
</template>

<script>
import ContentField from '../../../components/ContentField.vue'
import { ref } from 'vue'
import { useStore } from 'vuex';
import $ from 'jquery'
import { Toast } from 'bootstrap/dist/js/bootstrap'
import router from '../../../router/index'

export default {
    components: {
        ContentField
    },
    setup() {
        let email = ref('');
        let code = ref('');
        let error_message = ref('');
        const store = useStore();

        const send_email = () => {
            store.commit("updateEmailing", true);
            $.ajax({
                url: "https://app4460.acapp.acwing.com.cn/api/user/detail/sendmail/",
                type: "post",
                data: {
                    email: email.value
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        error_message.value = "验证码发送成功！";
                        const toastLiveExample = document.getElementById('liveToast');
                        const toast = new Toast(toastLiveExample);
                        toast.show();
                    } else {
                        error_message.value = resp.error_message;
                        const toastLiveExample = document.getElementById('liveToast');
                        const toast = new Toast(toastLiveExample);
                        toast.show();
                    }
                    store.commit("updateEmailing", false);
                },
                error(resp) {
                    console.log(resp);
                    store.commit("updateEmailing", false);
                }
            })
        }
        const verify_email = () => {
            $.ajax({
                url: "https://app4460.acapp.acwing.com.cn/api/user/security/verify/email/",
                type: "post",
                data: {
                    code: code.value
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        router.push({ name: 'user_profile_index'});
                    } else {
                        error_message.value = resp.error_message;
                        const toastLiveExample = document.getElementById('liveToast');
                        const toast = new Toast(toastLiveExample);
                        toast.show();
                    }
                },
                error(resp) {
                  console.log(resp);
                }
            })
        }
        return {
            email,
            code,
            error_message,
            send_email,
            verify_email,
        }
    }
}
</script>

<style scoped>
</style>
