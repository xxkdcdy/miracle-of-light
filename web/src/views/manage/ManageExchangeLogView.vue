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
                        >&nbsp;&nbsp;发货信息
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12" style="background-color:rgb(240,242,245);height: 530px;padding: 20px">
                        <!-- 下面是具体内容 -->
                        <table class="table table-striped" style="text-align: center;">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>用户ID</th>
                                    <th>产品ID</th>
                                    <th>下单时间</th>
                                    <th>状态</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                                <!-- 每页7条数据 -->
                                <tr v-for="record of records" :key="record.exchangeLog.exchangeLog.id">
                                    <td>{{ record.exchangeLog.exchangeLog.id }}</td>
                                    <td>{{ record.exchangeLog.exchangeLog.userId }}</td>
                                    <td>{{ record.exchangeLog.exchangeLog.exchangeId }}</td>
                                    <td>{{ record.exchangeLog.exchangeLog.createTime }}</td>
                                    <td>{{ record.exchangeLog.exchangeLog.state }}</td>
                                    <td>
                                        <button type="button" v-if="record.exchangeLog.exchangeLog.state === '待发货'" class="btn btn-warning" data-bs-toggle="modal" :data-bs-target="'#update-bot-modal-' + record.exchangeLog.exchangeLog.id" style="margin: 0 2px;">发货</button>
                                        <!-- 模态框 -->
                                        <div class="modal fade modal-xl" :id="'update-bot-modal-' + record.exchangeLog.exchangeLog.id" tabindex="-1">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h1 class="modal-title fs-5">发货【ID:{{ record.exchangeLog.exchangeLog.id }}】</h1>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <div class="mb-3">
                                                            <label for="add-bot-title" class="form-label">订单号</label>
                                                            <input v-model="record.expCode" type="text" class="form-control" id="update-bot-title">
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <div class="error-message">{{ record.error_message }}</div>
                                                        <button type="button" class="btn btn-primary" @click="update_record(record)">发货</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <button type="button" class="btn btn-info" data-bs-toggle="modal" :data-bs-target="'#notice-bot-modal-' + record.exchangeLog.exchangeLog.id" style="margin: 0 2px;">信息</button>
                                        <!-- 模态框 -->
                                        <div class="modal fade modal-xl" :id="'notice-bot-modal-' + record.exchangeLog.exchangeLog.id" tabindex="-1">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h1 class="modal-title fs-5">发货【ID:{{ record.exchangeLog.exchangeLog.id }}】</h1>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <div class="mb-3">
                                                            <label for="add-bot-title" class="form-label">姓名</label>
                                                            <input disabled v-model="record.name.name" type="text" class="form-control" id="update-bot-title">
                                                        </div>
                                                        <div class="mb-3">
                                                            <label for="add-bot-title" class="form-label">地址</label>
                                                            <input disabled v-model="record.address.address" type="text" class="form-control" id="update-bot-title">
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <div class="error-message">{{ record.error_message }}</div>
                                                        <button type="button" class="btn btn-primary" @click="make_notice(record.exchangeLog.exchangeLog.userId, '商品即将发货，请及时填写完整信息！')">提醒</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        
                                    </td>
                                </tr>
                            </tbody>
                        </table>
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
    </ContentField>
</template>

<script>
import ContentField from '../../components/ContentField.vue'
import MenuBar from '../../components/MenuBar.vue'
import { ref } from 'vue'
import $ from 'jquery'
import { useStore } from 'vuex'
import { Modal } from 'bootstrap/dist/js/bootstrap'

export default {
    components: {
        ContentField,
        MenuBar,
    },
    setup() {
        let current_page = ref(1);
        let records = ref([]);
        let total_records = 0;
        let pages = ref([]);
        const store = useStore();
        const click_page = page => {
            if (page === -2) page = current_page.value - 1;
            else if (page === -1) page = current_page.value + 1;
            let max_pages = parseInt(Math.ceil(total_records / 7));
            if (page >= 1 && page <= max_pages) {
                pull_page(page);
            }
        }

        const update_pages = () => {
            let max_pages = parseInt(Math.ceil(total_records / 7));
            let new_pages = [];
            for (let i = current_page.value - 2; i <= current_page.value + 2; i ++) {
                if (i >= 1 && i <= max_pages) {
                    new_pages.push({
                        number: i,
                        is_active: i === current_page.value ? "active" : "",
                    });
                }
            }
            pages.value = new_pages;
        }

        const pull_page = page => {
            current_page.value = page;
            $.ajax({
                url: "https://app4460.acapp.acwing.com.cn/api/manage/search/exchange/log/all/",
                data: {
                    page,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                type: "get",
                success(resp) {
                    console.log(resp);
                    if (resp.error_message === "success") {
                        records.value = resp.records;
                        total_records = resp.records_count;
                        update_pages();
                    } else {
                        console.log(resp);
                    }
                },
                error(resp) {
                    console.log(resp);
                }
            })
        }

        const update_record = (record) => {
            $.ajax({
                url: "https://app4460.acapp.acwing.com.cn/api/manage/update/exchange/log/",
                data: {
                    id: record.exchangeLog.exchangeLog.id,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                type: "post",
                success(resp) {
                    console.log(resp);
                    if (resp.error_message === "success") {
                        Modal.getInstance("#update-bot-modal-" + record.exchangeLog.exchangeLog.id).hide();
                        make_notice(record.exchangeLog.exchangeLog.userId, "您的订单已发货，订单号为" + record.expCode);
                        pull_page(current_page.value);
                    } else {
                        console.log(resp);
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

        pull_page(current_page.value);

        return {
            records,
            pages,
            current_page,
            click_page,
            pull_page,
            update_record,
            make_notice,
        }
    },
}
</script>

<style scoped>

</style>