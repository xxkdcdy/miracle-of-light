<template>
    <ContentField>
        <div class="container justify-content-md-center text-center">
            <h3>身份认证</h3>
            <div class="row" v-if="$store.state.user.identification === null || $store.state.user.name === null">
                <div class="input-group mt-3 offset-3 col-6" style="width: 50%">
                    <span class="input-group-text" id="basic-addon1">身份证号</span>
                    <input v-model="idCard" type="text" class="form-control" placeholder="身份证号" aria-label="Username" aria-describedby="basic-addon1">
                </div>
                <div class="input-group mt-3 offset-3 col-6" style="width: 50%">
                    <span class="input-group-text" id="basic-addon1">真实姓名</span>
                    <input v-model="name" type="text" class="form-control" placeholder="真实姓名" aria-label="Username" aria-describedby="basic-addon1">
                </div>
                <button type="button" @click="verify_id" class="btn btn-success col-2 offset-5 mt-5">认证</button>
            </div>
            <div class="row" v-else>
                <div class="input-group mt-3 offset-3 col-6" style="width: 50%">
                    <span class="input-group-text" id="basic-addon1">身份证号</span>
                    <input v-model="idCard" type="text" class="form-control" :placeholder="$store.state.user.identification" aria-label="Username" aria-describedby="basic-addon1" disabled>
                </div>
                <div class="input-group mt-3 offset-3 col-6" style="width: 50%">
                    <span class="input-group-text" id="basic-addon1">真实姓名</span>
                    <input v-model="name" type="text" class="form-control" :placeholder="$store.state.user.name" aria-label="Username" aria-describedby="basic-addon1" disabled>
                </div>
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
        let idCard = ref('');
        let name = ref('');
        let error_message = ref('');
        const store = useStore();

        const verify_id = () => {
            $.ajax({
                url: "https://app4460.acapp.acwing.com.cn/api/user/security/verify/id/",
                type: "post",
                data: {
                    idCard: idCard.value,
                    name: name.value
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
            idCard,
            name,
            error_message,
            verify_id,
        }
    }
}
</script>

<style scoped>
</style>
