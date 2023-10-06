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
                        >&nbsp;&nbsp;用户信息
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12" style="background-color:rgb(240,242,245);height: 530px;padding: 20px">
                        <!-- 下面是具体内容 -->
                        <table class="table table-striped" style="text-align: center;">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>用户名</th>
                                    <th>昵称</th>
                                    <th>标记</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                                <!-- 每页7条数据 -->
                                <tr v-for="record of records" :key="record.id">
                                    <td>{{ record.id }}</td>
                                    <td>{{ record.username }}</td>
                                    <td>{{ record.nickname }}</td>
                                    <td>{{ record.flag }}</td>
                                    <td>
                                        <button type="button" class="btn btn-warning" data-bs-toggle="modal" :data-bs-target="'#update-bot-modal-' + record.id" style="margin: 0 2px;">修改</button>

                                        <!-- 模态框 -->
                                        <div class="modal fade modal-xl" :id="'update-bot-modal-' + record.id" tabindex="-1">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h1 class="modal-title fs-5">修改信息【ID:{{ record.id }}】</h1>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <div class="mb-3">
                                                            <label for="add-bot-title" class="form-label">标签</label>
                                                            <input v-model="record.flag" type="email" class="form-control" id="update-bot-title">
                                                        </div>
                                                        <div class="mb-3">
                                                            <label for="add-bot-title" class="form-label">昵称</label>
                                                            <input v-model="record.nickname" type="email" class="form-control" id="update-bot-title">
                                                        </div>
                                                        <div class="mb-3">
                                                            <label for="add-bot-title" class="form-label">姓名</label>
                                                            <input v-model="record.name" type="email" class="form-control" id="update-bot-title">
                                                        </div>
                                                        <div class="mb-3">
                                                            <label for="add-bot-title" class="form-label">地址</label>
                                                            <input v-model="record.address" type="email" class="form-control" id="update-bot-title">
                                                        </div>
                                                        <div class="mb-3">
                                                            <label for="add-bot-title" class="form-label">描述</label>
                                                            <input v-model="record.description" type="email" class="form-control" id="update-bot-title">
                                                        </div>
                                                        <div class="mb-3">
                                                            <label for="add-bot-title" class="form-label">邮箱</label>
                                                            <input v-model="record.email" type="email" class="form-control" id="update-bot-title">
                                                        </div>
                                                        <div class="mb-3">
                                                            <label for="add-bot-title" class="form-label">机构</label>
                                                            <input v-model="record.organization" type="email" class="form-control" id="update-bot-title">
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <div class="error-message">{{ record.error_message }}</div>
                                                        <button type="button" class="btn btn-primary" @click="update_record(record)">修改</button>
                                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"  @click="pull_page(current_page)">取消</button>
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
                url: "https://app4460.acapp.acwing.com.cn/api/manage/search/user/all/",
                data: {
                    page,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                type: "get",
                success(resp) {
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
            record.error_message = "";
            $.ajax({
                url: "https://app4460.acapp.acwing.com.cn/api/manage/update/user/",
                data: {
                    id: record.id,
                    flag: record.flag,
                    nickname: record.nickname,
                    name: record.name,
                    address: record.address,
                    description: record.description,
                    email: record.email,
                    organization: record.organization,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                type: "post",
                success(resp) {
                    if (resp.error_message === "success") {
                        Modal.getInstance("#update-bot-modal-" + record.id).hide();
                        pull_page(current_page.value);
                    } else {
                        record.error_message = resp.error_message;
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
        }
    },
}
</script>

<style scoped>

</style>