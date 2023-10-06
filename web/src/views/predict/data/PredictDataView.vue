<template>
    <div class="container">
        <div class="row">
            <div>
                <div class="card" style="margin-top: 20px;">
                    <div class="card-header">
                        <div class="hstack gap-3">
                            <span style="font-size: 130%">昆虫数据</span>
                            <div class="bg-body-tertiary border ms-auto">
                                <div class="input-group">
                                    <input type="text" v-model="insect" list="list">
                                    <datalist id="list" v-show="isshow">
                                        <option v-for="item in selectInsects" :key="item">{{ item }}</option>
                                    </datalist>
                                    <button class="btn btn-outline-secondary" :disabled="isDisabled" data-bs-toggle="modal" data-bs-target="#msgModal" type="button" id="button-addon2" @click="search_insect">昆虫查询</button>
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
                    </div>
                    <div class="card-body">
                        <div class="row row-cols-1 row-cols-md-4 g-4">
                            <div class="col" v-for="record of records" :key="record.insectId">
                                <div class="card h-100">
                                    <a target="_blank" :href="'https://baike.baidu.com/item/' + record.name">
                                        <img :src="record.url" class="card-img-top" alt="..." style="height: 175px;">
                                    </a>
                                    <div class="card-header">
                                        <h5 class="card-title">{{ record.name }}</h5>
                                    </div>
                                    <div class="card-body">
                                        <div class="hstack gap-1">
                                            <div class="bg-body-tertiary border ms-auto">{{ record.insectOrder }}</div>
                                            <div class="vr"></div>
                                            <div class="bg-body-tertiary border">{{ record.family }}</div>
                                            <div class="vr"></div>
                                            <div class="bg-body-tertiary border">{{ record.genus }}</div>
                                        </div>
                                        <div class="collapse" :id="'collapseExample' + record.insectId">
                                            <div class="card card-body">
                                                <div class="bg-body-tertiary border">
                                                    <span class="fw-bold">生活环境</span>
                                                    {{ record.environment }}
                                                </div>
                                                <div class="bg-body-tertiary border">
                                                    <span class="fw-bold">分布区域</span>
                                                    {{ record.region }}
                                                </div>
                                                <div class="bg-body-tertiary border">
                                                    <span class="fw-bold">防治建议</span>
                                                    {{ record.suggest }}
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="card-footer">
                                        <button class="btn btn-primary" style="width: 100%;" type="button" data-bs-toggle="collapse" :data-bs-target="'#collapseExample' + record.insectId">
                                            查看介绍
                                        </button>
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
                    <!-- Modal -->
                    <div class="modal fade modal-xl" id="msgModal" tabindex="-1" role="dialog">
                        <div class="modal-dialog modal-lg" ><!-- style="display: inline-block; width: auto;" -->
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h4 class="modal-title float-start" id="modalLabel">{{ insectMsg.name }}</h4>
                                    <h4 class="modal-title ms-auto" id="modalLabel">{{ insectMsg.insectOrder }}-{{ insectMsg.family }}-{{ insectMsg.genus }}</h4>
                                    <button type="button" class="btn-close float-end" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <img :src="insectMsg.url" alt="" style="width: 300px;">
                                    <div class="bg-body-tertiary border text-left">
                                        <span class="fw-bold">拉丁名称</span>
                                        {{ insectMsg.nameLatin }}
                                    </div>
                                    <div class="bg-body-tertiary border text-left">
                                        <span class="fw-bold">生活环境</span>
                                        {{ insectMsg.environment }}
                                    </div>
                                    <div class="bg-body-tertiary border text-left">
                                        <span class="fw-bold">分布区域</span>
                                        {{ insectMsg.region }}
                                    </div>
                                    <div class="bg-body-tertiary border text-left">
                                        <span class="fw-bold">防治建议</span>
                                        {{ insectMsg.suggest }}
                                    </div>
                                </div>
                            </div>
                        </div>
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

export default {
    data() {
        let insects = [];
        let that = this;
        $.ajax({
            url: "https://app4460.acapp.acwing.com.cn/api/predict/data/getall/",
            type: "get",
            headers: {
                Authorization: "Bearer " + that.$store.state.user.token,
            },
            success(resp) {
                for (let item of resp.records) {
                    insects.unshift(item.name);
                    if (item.url === null) item.url = "https://miracle-of-light.oss-cn-shanghai.aliyuncs.com/user/profile/icon_default.jpeg";
                    that.insectMsgs.unshift(item);
                }
            },
            error(resp) {
                console.log(resp);
            }
        })
        return {
            isshow: true,
            insect: "",
            insectMsg: {
                            "insectId": 1,
                            "insectOrder": "半翅目",
                            "family": "蝉科",
                            "familyCode": "C15204000000",
                            "genus": "蚱蝉属",
                            "genusCode": "C15204005000",
                            "name": "黑蚱蝉",
                            "nameCode": "C15204005005",
                            "nameLatin": "Cryptotympana atrata Fabricius",
                            "environment": "杨、柳、榆、女贞、竹、苦楝、水杉、悬铃木...",
                            "region": "惠山区、滨湖区；赣榆区、连云区；泰兴、靖...",
                            "suggest": "喷布20%氰戊菊酯（速灭杀丁）乳油200...",
                            "url": "https://miracle-of-light.oss-cn-shanghai.aliyuncs.com/user/profile/icon_default.jpeg"
                        },
            insectMsgs: [],
            insects: insects,
            isDisabled: true,
            selectInsects: [],
        }
    },
    watch: {
        insect: function(val) {
            let that = this;
            let isvalid = false;
            for (let item of that.insects) {
                if (item === that.insect) {
                    isvalid = true;
                }
            }
            that.isDisabled = !isvalid;
            if (val.length === 0) {
                this.isshow = false;
            } else {
                this.isshow = true;
                let new_insects = [];
                for (let item of this.insects) {
                    if (item.indexOf(val) >= 0) new_insects.unshift(item);
                }
                this.selectInsects = new_insects;
            }
        }
    },
    methods: {
        search_insect() {
            let that = this;
            let isvalid = false;
            for (let item of that.insects) {
                if (item === that.insect) {
                    isvalid = true;
                }
            }
            if (isvalid) {
                // 检查到输入有效，数据库中存在数据，查询一下结果
                for (let item of that.insectMsgs) {
                    if (item.name === that.insect) {
                        that.insectMsg = item;
                        break;
                    }
                }
                console.log(that.insectMsg);
            }
        }
    },
    setup() {
        let records = ref([]);
        let current_page = 1;
        let total_records = 0;
        let pages = ref([]);
        const store = useStore();
        let error_message = "";

        const refresh_records = page => {
            current_page = page;
            $.ajax({
                url: "https://app4460.acapp.acwing.com.cn/api/predict/data/getlist/",
                type: "get",
                data: {
                    page: page,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    records.value = resp.records;
                    total_records = resp.records_count;
                    for (let record of records.value) {
                        let maxSize = 20;
                        if (record.environment.length > maxSize) record.environment = record.environment.substring(0,maxSize) + "...";
                        if (record.region.length > maxSize) record.region = record.region.substring(0,maxSize) + "...";
                        if (record.suggest.length > maxSize) record.suggest = record.suggest.substring(0,maxSize) + "...";
                        if (record.url === null) record.url = "https://miracle-of-light.oss-cn-shanghai.aliyuncs.com/user/profile/icon_default.jpeg";
                    }
                    console.log(records.value);
                    update_pages();
                }
            })
        }

        refresh_records(current_page);

        const click_page = page => {
            if (page === -2) page = current_page - 1;
            else if (page === -1) page = current_page + 1;
            let max_pages = parseInt(Math.ceil(total_records / 4));
            if (page >= 1 && page <= max_pages) {
                refresh_records(page);
            }
        }

        const update_pages = () => {
            let max_pages = parseInt(Math.ceil(total_records / 4));
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
            records,
            pages,
            error_message,
            click_page,
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
