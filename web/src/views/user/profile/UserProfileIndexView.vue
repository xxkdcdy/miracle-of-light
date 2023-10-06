<template>
    <div class="container">
        <div class="row">
            <!-- 头像 -->
            <div class="col-sm-4 col-md-3">
                <div class="card" style="margin-top: 20px;">
                    <div class="card-body">
                        <img :src="$store.state.user.photo" alt="" style="width : 100%;">
                        <hr>
                        <div align="center">
                            <div class="input-group" style="display: none;">
                                <input type="file" class="form-control" id="upLoadPhotoInput" accept="image/png" @change="upload_photo">
                            </div>
                            <button v-if="!isMobile" type="button" class="btn btn-primary" @click="upload_input_click">
                                更新头像
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <!-- 右侧导航 -->
            <div class="col-sm-8 col-md-9">
                <div class="card" style="margin-top: 20px;">
                    <div class="card-header">
                        <span style="font-size: 130%;">个人信息</span>
                        <button type="button" class="btn btn-primary float-end" @click="update_detail">
                            更新信息
                        </button>
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
                    </div>
                    <div class="card-body">
                        <div class="row" style="padding-top: 1em">
                            <div class="offset-1 col-2">
                                <label for="user-nickname" class="col-form-label" style="font-weight: 600; float: right">昵称:</label>
                            </div>
                            <div class="col-6">
                                <input v-model="nickname" type="text" id="user-nickname" class="form-control">
                            </div>
                        </div>
                        <div class="row" style="padding-top: 1em">
                            <div class="offset-1 col-2">
                                <label for="user-address" class="col-form-label" style="font-weight: 600; float: right">邮寄地址:</label>
                            </div>
                            <div class="col-6">
                                <input v-model="address" type="text" id="user-address" class="form-control">
                                <div id="addressHelp" class="form-text">我们不会透露您的地址给其他人。</div>
                            </div>
                        </div>
                        <div class="row" style="padding-top: 1em">
                            <div class="offset-1 col-2">
                                <label for="user-description" class="col-form-label" style="font-weight: 600; float: right">个人简介:</label>
                            </div>
                            <div class="col-6">
                                <textarea v-model="description" id="user-description" class="form-control"></textarea>
                            </div>
                        </div>
                        <div class="row" style="padding-top: 1em">
                            <div class="offset-1 col-2">
                                <label for="user-organization" class="col-form-label" style="font-weight: 600; float: right">组织:</label>
                            </div>
                            <div class="col-6">
                                <input v-model="organization" type="text" id="user-organization" class="form-control">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- 账号安全 -->
            <div class="offset-sm-4 offset-md-3 col-sm-8 col-md-9">
                <div class="card" style="margin-top: 20px;">
                    <div class="card-header">
                        <span style="font-size: 130%;">账号安全</span>
                    </div>
                    <div class="card-body">
                        <table class="table">
                            <tbody>
                                <tr>
                                    <th scope="row" class ="td-info">
                                        <img class="img" src="https://cdn.acwing.com/static/web/img/user/profile/security/password.jpg" alt="密码">
                                        密码
                                        </th>
                                    <td class ="td-info">已设置</td>
                                    <td class="td-router">
                                        <router-link :to="{name: 'user_security_changepwd'}">
                                            修改密码
                                        </router-link>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row" class ="td-info">
                                        <img class="img" src="https://cdn.acwing.com/static/web/img/user/profile/security/identity.png" alt="实名认证">
                                        实名认证
                                        </th>
                                    <td class ="td-info" v-if="$store.state.user.name === null">尚未实名认证</td>
                                    <td class ="td-info" v-else>{{ $store.state.user.name }}</td>
                                    <td class="td-router">
                                        <router-link :to="{name: 'user_security_identification'}">
                                            查看详情
                                        </router-link>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row" class ="td-info">
                                        <img class="img" src="https://miracle-of-light.oss-cn-shanghai.aliyuncs.com/user/profile/email.png" alt="邮箱认证">
                                        邮箱认证
                                        </th>
                                    <td class ="td-info" v-if="$store.state.user.email === null">尚未邮箱认证</td>
                                    <td class ="td-info" v-else>{{ $store.state.user.email }}</td>
                                    <td class="td-router">
                                        <router-link :to="{name: 'user_security_email'}">
                                            绑定邮箱
                                        </router-link>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { useStore } from 'vuex'
import { ref } from 'vue'
import $ from 'jquery'
import { Toast } from 'bootstrap/dist/js/bootstrap'

export default {
    setup() {
        const isMobile = navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i);
        const store = useStore();
        let nickname = ref('');
        let address = ref('');
        let description = ref('');
        let organization = ref('');
        let name = ref('');
        let email = ref('');
        let error_message = ref('');

        store.dispatch("getdetail", {
            success(resp) {
                console.log(resp);
                nickname.value = resp.nickname;
                address.value = resp.address;
                description.value = resp.description;
                organization.value = resp.organization;
                name.value = resp.name;
                email.value = resp.email;
            },
            error(resp) {
                nickname.value = store.state.user.username;
                console.log(resp);
            }
        })

        const update_detail = () => {
            $.ajax({
                url: "https://app4460.acapp.acwing.com.cn/api/user/detail/update/",
                type: "post",
                data: {
                    nickname: nickname.value,
                    address: address.value,
                    description: description.value,
                    organization: organization.value
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        store.dispatch("getdetail", {
                            success(resp) {
                                console.log(resp);
                            },
                            error(resp) {
                                console.log(resp);
                            }
                        })
                        error_message.value = "个人信息保存成功！";
                        const toastLiveExample = document.getElementById('liveToast');
                        const toast = new Toast(toastLiveExample);
                        toast.show();
                    } else {
                        error_message.value = resp.error_message;
                        const toastLiveExample = document.getElementById('liveToast');
                        const toast = new Toast(toastLiveExample);
                        toast.show();
                    }
                }
            })
        }

        const upload_input_click = () => {
            const upLoadPhotoInput = document.getElementById('upLoadPhotoInput');
            upLoadPhotoInput.click();
        }

        const upload_photo = () => {
            const upLoadPhotoInput = document.getElementById('upLoadPhotoInput');
            const reader = new FileReader();
            reader.readAsDataURL(upLoadPhotoInput.files[0]);
            reader.onload = () => {
                $.ajax({
                url: "https://app4460.acapp.acwing.com.cn/api/user/account/photo/",
                type: "post",
                data: {
                    photo: reader.result
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        error_message.value = "头像保存成功！";
                        const toastLiveExample = document.getElementById('liveToast');
                        const toast = new Toast(toastLiveExample);
                        toast.show();
                        store.commit("updatePullingInfo", true);
                        store.dispatch("getinfo", {
                            success() {
                                store.commit("updatePullingInfo", false);
                            },
                            error() {
                                store.commit("updatePullingInfo", false);
                            }
                        })
                    } else {
                        error_message.value = resp.error_message;
                        const toastLiveExample = document.getElementById('liveToast');
                        const toast = new Toast(toastLiveExample);
                        toast.show();
                    }
                }
            })
            }
        }

        return {
            nickname,
            address,
            description,
            organization,
            name,
            email,
            error_message,
            isMobile,
            update_detail,
            upload_input_click,
            upload_photo,
        }
    }
}
</script>

<style scoped>
label.btn-file {
    border: 1px solid rgb(13, 110, 253);
}
.card {
    border: 1px solid rgb(219, 207, 207);
    padding: 12px;
    border-radius: 12px;
    box-shadow: 3px 3px 6px #888;
}
th > img {
    width: 18px;
}
tr {
    padding-top: 1em;
}
td.td-router {
    color: blue;
    font-weight: 600;
    text-decoration: underline;
}
</style>
