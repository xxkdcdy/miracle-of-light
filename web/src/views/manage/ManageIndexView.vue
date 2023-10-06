<template>
    <ContentField>
        <div class="row">
            <!--导航-->
            <MenuBar></MenuBar>
            <!--右侧内容-->
            <div class="col-md-9" style="border: 1px solid gray;height: 600px">
                <div class="row">
                    <!--x显示位置-->
                    <div class="col-md-12" style="height: 70px;font-size: 18px; color: rgb(109,109,109);line-height: 70px;padding-left:20px;">
                        >&nbsp;&nbsp;积分发放
                    </div>
                </div>
                <!-- 下面是具体内容 -->
                <div class="row">
                    <div class="col-md-12" style="background-color:rgb(240,242,245);height: 530px;padding: 20px">
                        <div class="row">
                            <div class="col-md-4 offset-4" style="margin-top: 50px">
                                <label>用户ID</label>
                                <input v-model="userId" type="text" class="form-control">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-4 offset-4" style="margin-top: 50px">
                                <label>积分数额</label>
                                <input v-model="score" type="text" class="form-control">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-4 offset-4" style="margin-top: 50px">
                                <label>发放事由</label>
                                <input v-model="reason" type="text" class="form-control">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-4 offset-4" style="margin-top: 30px; text-align: center">
                                <button class="btn btn-success" @click="update_score" type="reset">发放</button>
                                <span class="error-msg">{{ error_message }}</span>
                            </div>    
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </ContentField>
</template>

<script>
import ContentField from '../../components/ContentField.vue'
import MenuBar from '../../components/MenuBar.vue'
import { ref } from 'vue'
import $ from 'jquery'
import { useStore } from 'vuex'


export default {
    components: {
        ContentField,
        MenuBar,
    },
    setup() {
        const store = useStore();
        const userId = ref(0);
        const score = ref(0);
        const reason = ref('');
        const error_message = ref('');

        const update_score = () => {
            $.ajax({
                url: "https://app4460.acapp.acwing.com.cn/api/manage/update/score/",
                type: "post",
                data: {
                    userId: userId.value,
                    score: score.value,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        error_message.value = "发放成功！";
                        make_notice(userId.value, "获得积分：" + score.value + ", 事由：" + reason.value);
                    } else {
                        error_message.value = resp.error_message;
                    }
                },
                error(resp) {
                    console.log(resp);
                }
            })
        }

        const make_notice = (userId, content) => {
            $.ajax({
                url: "https://app4460.acapp.acwing.com.cn/api/manage/notice/make/",
                data: {
                    userId: userId,
                    content: content,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                type: "post",
                success(resp) {
                    console.log(resp);
                    if (resp.error_message === "success") {
                        alert("发送成功！");
                    } else {
                        console.log(resp);
                    }
                },
                error(resp) {
                    console.log(resp);
                }
            })
        }

        return  {
            userId,
            score,
            reason,
            error_message,
            update_score,
        }
    },
}
</script>

<style scoped>
.error-msg {
    color: red;
}
</style>