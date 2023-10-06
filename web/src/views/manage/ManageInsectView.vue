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
                        >&nbsp;&nbsp;昆虫信息
                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#add-bot-modal" style="margin-left: 700px;">新增昆虫</button>
                        <!-- 模态框 -->
                        <div class="modal fade modal-xl" id="add-bot-modal" tabindex="-1">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h1 class="modal-title fs-5">新增昆虫</h1>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <div class="mb-3">
                                            <label for="add-bot-title" class="form-label">目</label>
                                            <input v-model="insectOrder" type="text" class="form-control" id="update-bot-title">
                                        </div>
                                        <div class="mb-3">
                                            <label for="add-bot-title" class="form-label">科</label>
                                            <input v-model="family" type="text" class="form-control" id="update-bot-title">
                                        </div>
                                        <div class="mb-3">
                                            <label for="add-bot-title" class="form-label">科代码</label>
                                            <input v-model="familyCode" type="text" class="form-control" id="update-bot-title">
                                        </div>
                                        <div class="mb-3">
                                            <label for="add-bot-title" class="form-label">属</label>
                                            <input v-model="genus" type="text" class="form-control" id="update-bot-title">
                                        </div>
                                        <div class="mb-3">
                                            <label for="add-bot-title" class="form-label">属代码</label>
                                            <input v-model="genusCode" type="text" class="form-control" id="update-bot-title">
                                        </div>
                                        <div class="mb-3">
                                            <label for="add-bot-title" class="form-label">昆虫名称</label>
                                            <input v-model="name" type="text" class="form-control" id="update-bot-title">
                                        </div>
                                        <div class="mb-3">
                                            <label for="add-bot-title" class="form-label">昆虫代码</label>
                                            <input v-model="nameCode" type="text" class="form-control" id="update-bot-title">
                                        </div>
                                        <div class="mb-3">
                                            <label for="add-bot-title" class="form-label">拉丁名称</label>
                                            <input v-model="nameLatin" type="text" class="form-control" id="update-bot-title">
                                        </div>
                                        <div class="mb-3">
                                            <label for="add-bot-title" class="form-label">生活环境</label>
                                            <input v-model="environment" type="text" class="form-control" id="update-bot-title">
                                        </div>
                                        <div class="mb-3">
                                            <label for="add-bot-title" class="form-label">生活区域</label>
                                            <input v-model="region" type="text" class="form-control" id="update-bot-title">
                                        </div>
                                        <div class="mb-3">
                                            <label for="add-bot-title" class="form-label">防治建议</label>
                                            <input v-model="suggest" type="text" class="form-control" id="update-bot-title">
                                        </div>
                                        <div class="mb-3">
                                            <label for="formFile" class="form-label">图片</label>
                                            <input class="form-control" @change="load_image_normal" type="file" accept="image/jpeg" id="upLoadPhotoInput">
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <div class="error-message">{{ error_message }}</div>
                                        <button type="button" class="btn btn-primary" @click="insert_record">提交</button>
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"  @click="pull_page(current_page)">取消</button>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12" style="background-color:rgb(240,242,245);height: 530px;padding: 20px">
                        <!-- 下面是具体内容 -->
                        <table class="table table-striped" style="text-align: center;">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>名称</th>
                                    <th>目</th>
                                    <th>科</th>
                                    <th>属</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                                <!-- 每页7条数据 -->
                                <tr v-for="record of records" :key="record.insectId">
                                    <td>{{ record.insectId }}</td>
                                    <td>{{ record.name }}</td>
                                    <td>{{ record.insectOrder }}</td>
                                    <td>{{ record.family }}</td>
                                    <td>{{ record.genus }}</td>
                                    <td>
                                        <button type="button" class="btn btn-warning" data-bs-toggle="modal" :data-bs-target="'#update-bot-modal-' + record.insectId" style="margin: 0 2px;">修改</button>
                                        <!-- 模态框 -->
                                        <div class="modal fade modal-xl" :id="'update-bot-modal-' + record.insectId" tabindex="-1">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h1 class="modal-title fs-5">修改昆虫信息【ID:{{ record.insectId }}】</h1>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <div class="mb-3">
                                                            <label for="add-bot-title" class="form-label">目</label>
                                                            <input v-model="record.insectOrder" type="text" class="form-control" id="update-bot-title">
                                                        </div>
                                                        <div class="mb-3">
                                                            <label for="add-bot-title" class="form-label">科</label>
                                                            <input v-model="record.family" type="text" class="form-control" id="update-bot-title">
                                                        </div>
                                                        <div class="mb-3">
                                                            <label for="add-bot-title" class="form-label">科代码</label>
                                                            <input v-model="record.familyCode" type="text" class="form-control" id="update-bot-title">
                                                        </div>
                                                        <div class="mb-3">
                                                            <label for="add-bot-title" class="form-label">属</label>
                                                            <input v-model="record.genus" type="text" class="form-control" id="update-bot-title">
                                                        </div>
                                                        <div class="mb-3">
                                                            <label for="add-bot-title" class="form-label">属代码</label>
                                                            <input v-model="record.genusCode" type="text" class="form-control" id="update-bot-title">
                                                        </div>
                                                        <div class="mb-3">
                                                            <label for="add-bot-title" class="form-label">昆虫名称</label>
                                                            <input v-model="record.name" type="text" class="form-control" id="update-bot-title">
                                                        </div>
                                                        <div class="mb-3">
                                                            <label for="add-bot-title" class="form-label">昆虫代码</label>
                                                            <input v-model="record.nameCode" type="text" class="form-control" id="update-bot-title">
                                                        </div>
                                                        <div class="mb-3">
                                                            <label for="add-bot-title" class="form-label">拉丁名称</label>
                                                            <input v-model="record.nameLatin" type="text" class="form-control" id="update-bot-title">
                                                        </div>
                                                        <div class="mb-3">
                                                            <label for="add-bot-title" class="form-label">生活环境</label>
                                                            <input v-model="record.environment" type="text" class="form-control" id="update-bot-title">
                                                        </div>
                                                        <div class="mb-3">
                                                            <label for="add-bot-title" class="form-label">生活区域</label>
                                                            <input v-model="record.region" type="text" class="form-control" id="update-bot-title">
                                                        </div>
                                                        <div class="mb-3">
                                                            <label for="add-bot-title" class="form-label">防治建议</label>
                                                            <input v-model="record.suggest" type="text" class="form-control" id="update-bot-title">
                                                        </div>
                                                        <div class="mb-3">
                                                            <label for="add-bot-title" class="form-label">图片地址</label>
                                                            <input v-model="record.url" disabled type="text" class="form-control" id="update-bot-title">
                                                        </div>
                                                        <div class="mb-3">
                                                            <label for="formFile" class="form-label">更换图片</label>
                                                            <input class="form-control" @change="load_image(record)" type="file" accept="image/jpeg" :id="'upLoadPhotoInput-' + record.insectId">
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

                                        <button type="button" @click="remove_record(record.insectId)" class="btn btn-danger" style="margin: 0 2px;">删除</button>
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
        const insectOrder = ref('');
        const family = ref('');
        const familyCode = ref('');
        const genus = ref('');
        const genusCode = ref('');
        const name = ref('');
        const nameCode = ref('');
        const nameLatin = ref('');
        const environment = ref('');
        const region = ref('');
        const suggest = ref('');
        const imgSrc = ref('');
        const error_message = ref('');

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
                url: "https://app4460.acapp.acwing.com.cn/api/manage/search/insect/all/",
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

        const load_image = (record) => {
            const upLoadPhotoInput = document.getElementById('upLoadPhotoInput-' + record.insectId);
            const reader = new FileReader();
            reader.readAsDataURL(upLoadPhotoInput.files[0]);
            reader.onload = () => {
                record.img = reader.result;
            }
        }

        const load_image_normal = () => {
            const upLoadPhotoInput = document.getElementById('upLoadPhotoInput');
            const reader = new FileReader();
            reader.readAsDataURL(upLoadPhotoInput.files[0]);
            reader.onload = () => {
                imgSrc.value = reader.result;
            }
        }

        const update_record = (record) => {
            record.error_message = "";
            $.ajax({
                url: "https://app4460.acapp.acwing.com.cn/api/manage/update/insect/",
                data: {
                    id: record.insectId,
                    insectOrder: record.insectOrder,
                    family: record.family,
                    familyCode: record.familyCode,
                    genus: record.genus,
                    genusCode: record.genusCode,
                    name: record.name,
                    nameCode: record.nameCode,
                    nameLatin: record.nameLatin,
                    environment: record.environment,
                    region: record.region,
                    suggest: record.suggest,
                    photo: record.img,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                type: "post",
                success(resp) {
                    if (resp.error_message === "success") {
                        Modal.getInstance("#update-bot-modal-" + record.insectId).hide();
                        pull_page(current_page.value);
                    } else {
                        error_message.value = resp.error_message;
                        console.log(resp);
                    }
                },
                error(resp) {
                    console.log(resp);
                }
            })
        }

        const insert_record = () => {
            error_message.value = "";
            $.ajax({
                url: "https://app4460.acapp.acwing.com.cn/api/manage/insert/insect/",
                data: {
                    insectOrder: insectOrder.value,
                    family: family.value,
                    familyCode: familyCode.value,
                    genus: genus.value,
                    genusCode: genusCode.value,
                    name: name.value,
                    nameCode: nameCode.value,
                    nameLatin: nameLatin.value,
                    environment: environment.value,
                    region: region.value,
                    suggest: suggest.value,
                    photo: imgSrc.value,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                type: "post",
                success(resp) {
                    if (resp.error_message === "success") {
                        Modal.getInstance("#add-bot-modal").hide();
                        insectOrder.value = "";
                        family.value = "";
                        familyCode.value = "";
                        genus.value = "";
                        genusCode.value = "";
                        name.value = "";
                        nameCode.value = "";
                        nameLatin.value = "";
                        environment.value = "";
                        region.value = "";
                        suggest.value = "";
                        imgSrc.value = "";
                        error_message.value = "";
                        pull_page(current_page.value);
                    } else {
                        error_message.value = resp.error_message;
                        console.log(resp);
                    }
                },
                error(resp) {
                    console.log(resp);
                }
            })
        }

        const remove_record = (id) => {
            $.ajax({
                url: "https://app4460.acapp.acwing.com.cn/api/manage/delete/insect/",
                data: {
                    id: id,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                type: "post",
                success(resp) {
                    if (resp.error_message === "success") {
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

        pull_page(current_page.value);

        return {
            records,
            pages,
            current_page,
            insectOrder,
            family,
            familyCode,
            genus,
            genusCode,
            name,
            nameCode,
            nameLatin,
            environment,
            region,
            suggest,
            imgSrc,
            error_message,
            click_page,
            pull_page,
            load_image,
            load_image_normal,
            insert_record,
            update_record,
            remove_record,
        }
    },
}
</script>

<style scoped>

</style>