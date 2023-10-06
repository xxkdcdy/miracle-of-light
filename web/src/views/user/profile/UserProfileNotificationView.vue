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
                            <p style="font-weight: 600;">总积分：{{ $store.state.user.scoreTotal }}</p>
                        </div>
                    </div>
                </div>
            </div>
            <!-- 右侧导航 -->
            <div class="col-sm-8 col-md-9">
                <div class="card" style="margin-top: 20px;">
                    <div class="card-header">
                        <span style="font-size: 130%;">站内通知</span>
                        <button type="button" class="btn btn-primary float-end" @click="read_all_notification">
                            全部已读
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
                        <div class="row" style="padding-top: 1em" v-for="no in notification" :key="no.id">
                            <div class="card text-bg-light">
                                <div class="card-body">
                                    <h5 class="card-title">{{ no.title }} &nbsp;<span v-if="no.notice === 0" class="badge bg-danger">New</span></h5>
                                    <p class="card-text">{{ no.content }}</p>
                                    <a href="#" class="card-link" @click="read_notification(no.id)" v-if="no.notice === 0">标为已读</a>
                                    <a href="#" class="card-link" @click="remove_notification(no.id)">删除通知</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- 分页组件 -->
                    <nav aria-label="...">
                        <ul class="pagination" style="float: right;">
                            <li class="page-item" @click="click_page(-2)">
                                <a class="page-link" href="#">前一页</a>
                            </li>
                            <li :class="'page-item ' + page.is_active" v-for="page in pages" :key="page.number" @click="click_page(page.number)">
                                <a class="page-link" href="#">{{ page.number }}</a>
                            </li>
                            <li class="page-item" @click="click_page(-1)">
                                <a class="page-link" href="#">后一页</a>
                            </li>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { useStore } from 'vuex'
import { ref } from 'vue'
import $ from 'jquery'

export default {
    setup() {
        let notification = ref([]);
        let current_page = 1;
        let total_records = 0;
        let pages = ref([]);
        const error_message = ref("");
        const store = useStore();

        const refresh_notification = page => {
            current_page = page;
            $.ajax({
                url: "https://app4460.acapp.acwing.com.cn/api/user/detail/notification/getlist/",
                type: "get",
                data: {
                    page: page,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    notification.value = resp.notification;
                    total_records = resp.records_count;
                    update_pages();
                    console.log(notification.value);
                }
            })
        }

        const read_notification = (id) => {
            $.ajax({
                url: "https://app4460.acapp.acwing.com.cn/api/user/detail/notification/update/",
                type: "post",
                data: {
                    id: id,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        refresh_notification(current_page);
                        store.dispatch("getinfo", {
                            success() {
                                store.commit("updatePullingInfo", false);
                            },
                            error() {
                                store.commit("updatePullingInfo", false);
                            }
                        })
                    } else {
                        console.log(resp);
                    }
                }
            })
        }

        const read_all_notification = () => {
            for (let i = 0; i < notification.value.length; i ++) {
                let no = notification.value[i];
                if (no.notice === 0) {
                    read_notification(no.id);
                }
            }
        }

        const remove_notification = (id) => {
            $.ajax({
                url: "https://app4460.acapp.acwing.com.cn/api/user/detail/notification/remove/",
                type: "post",
                data: {
                    id: id,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        refresh_notification(current_page);
                        store.dispatch("getinfo", {
                            success() {
                                store.commit("updatePullingInfo", false);
                            },
                            error() {
                                store.commit("updatePullingInfo", false);
                            }
                        })
                    } else {
                        console.log(resp);
                    }
                }
            })
        }

        refresh_notification(current_page);

        const click_page = page => {
            if (page === -2) page = current_page - 1;
            else if (page === -1) page = current_page + 1;
            let max_pages = parseInt(Math.ceil(total_records / 3));
            if (page >= 1 && page <= max_pages) {
                refresh_notification(page);
            }
        }

        const update_pages = () => {
            let max_pages = parseInt(Math.ceil(total_records / 3));
            let new_pages = [];
            for (let i = current_page - 2; i <= current_page + 2; i ++) {
                if (i >= 1 && i <= max_pages) {
                    new_pages.push({
                        number: i,
                        is_active: i === current_page ? "active" : "",
                    });
                }
            }
            pages.value = new_pages;
        }


        return {
            notification,
            pages,
            error_message,
            read_notification,
            read_all_notification,
            remove_notification,
            click_page,
            update_pages,
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
