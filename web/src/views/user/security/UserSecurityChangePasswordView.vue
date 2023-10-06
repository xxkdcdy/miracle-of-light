<template>
    <ContentField>
        <div class="container justify-content-md-center text-center">
            <h3>密码修改</h3>
            <div class="row">
                <div class="input-group mt-3 offset-3 col-6" style="width: 50%">
                    <span class="input-group-text" id="basic-addon1">原始密码</span>
                    <input v-model="pwd" type="password" class="form-control" placeholder="原密码" aria-label="Username" aria-describedby="basic-addon1">
                </div>
                <div class="input-group mt-3 offset-3 col-6" style="width: 50%">
                    <span class="input-group-text" id="basic-addon1">修改密码</span>
                    <input v-model="newPwd" type="password" class="form-control" placeholder="新密码" aria-label="Username" aria-describedby="basic-addon1">
                </div>
                <div class="input-group mt-3 offset-3 col-6" style="width: 50%">
                    <span class="input-group-text" id="basic-addon1">重复密码</span>
                    <input v-model="repeatPwd" type="password" class="form-control" placeholder="重复新密码" aria-label="Username" aria-describedby="basic-addon1">
                </div>
                <button type="button" @click="verify_changePwd" class="btn btn-success col-2 offset-5 mt-5">修改</button>
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
        let pwd = ref('');
        let newPwd = ref('');
        let repeatPwd = ref('');
        let error_message = ref('');
        const store = useStore();

        const verify_changePwd = () => {
                $.ajax({
                    url: "https://app4460.acapp.acwing.com.cn/api/user/security/changepassword/",
                    type: "post",
                    data: {
                        pwd: pwd.value,
                        new_pwd: newPwd.value,
                        repeat_pwd: repeatPwd.value
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
            pwd,
            newPwd,
            repeatPwd,
            error_message,
            verify_changePwd,
        }
    }
}
</script>

<style scoped>
</style>
