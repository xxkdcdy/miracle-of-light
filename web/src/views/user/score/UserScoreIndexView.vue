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
                            <p style="font-weight: 600;">可用积分：{{ $store.state.user.scoreAvailable }}</p>
                        </div>
                    </div>
                </div>
            </div>
            <!-- 右侧导航 -->
            <div class="col-sm-8 col-md-9">
                <div class="card" style="margin-top: 20px;">
                    <div class="card-header">
                        <span style="font-size: 130%;">积分平台</span>
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
                        <div class="row row-cols-1 row-cols-md-3 g-4">
                            <div class="col" v-for="no of notification" :key="no.id">
                                <div class="card h-100">
                                    <img :src="no.photo" class="card-img-top" alt="...">
                                    <div class="card-body">
                                        <h5 class="card-title">{{ no.title }}</h5>
                                        <p class="card-text">【积分:{{ no.score }}】{{ no.description }}</p>
                                    </div>
                                    <div class="card-footer">
                                        <button type="button" @click="buy_exchange(no.id)" class="btn btn-primary" style="width: 100%;">兑换</button>
                                    </div>
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
import { Toast } from 'bootstrap/dist/js/bootstrap'

export default {
    setup() {
        let notification = ref([]);
        let current_page = 1;
        let total_records = 0;
        let pages = ref([]);
        const store = useStore();
        let error_message = ref("");

        const refresh_notification = page => {
            current_page = page;
            $.ajax({
                url: "https://app4460.acapp.acwing.com.cn/api/user/score/exchange/getlist/",
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

        const buy_exchange = (exchangeId) => {
            $.ajax({
                url: "https://app4460.acapp.acwing.com.cn/api/user/score/exchange/buy/",
                type: "post",
                data: {
                    exchangeId: exchangeId,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        error_message.value = "兑换成功";
                        const toastLiveExample = document.getElementById('liveToast');
                        const toast = new Toast(toastLiveExample);
                        toast.show();
                        // 重新获取一次信息，加载上提示
                        store.dispatch("getinfo", {
                            success() {
                                store.dispatch("getdetail", {
                                    success() {
                                    },
                                    error() {
                                    }
                                })
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

        return {
            notification,
            pages,
            error_message,
            click_page,
            update_pages,
            buy_exchange,
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
