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
                        >&nbsp;&nbsp;标注信息
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
                                    <th>图片地址</th>
                                    <th>标记地址</th>
                                    <th>创建时间</th>
                                    <th>状态</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                                <!-- 每页7条数据 -->
                                <tr v-for="record of records" :key="record.id">
                                    <td>{{ record.id }}</td>
                                    <td>{{ record.userId }}</td>
                                    <td><a :href="record.photo">下载</a></td>
                                    <td><a :href="record.mark">下载</a></td>
                                    <td>{{ record.createtime }}</td>
                                    <td>{{ record.state }}</td>
                                    <td>
                                        <button type="button" class="btn btn-warning" data-bs-toggle="modal" :data-bs-target="'#update-bot-modal-' + record.id" style="margin: 0 2px;">审核</button>
                                        <!-- 模态框 -->
                                        <div class="modal fade modal-xl" :id="'update-bot-modal-' + record.id" tabindex="-1">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h1 class="modal-title fs-5">审核【ID:{{ record.id }}】</h1>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <div class="mb-3">
                                                            <label for="add-bot-title" class="form-label">审核结果</label>
                                                            <input v-model="record.state" type="text" class="form-control" id="update-bot-title">
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <div class="error-message">{{ record.error_message }}</div>
                                                        <button type="button" class="btn btn-primary" @click="update_record(record)">提交</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <button type="button" data-bs-toggle="modal" :data-bs-target="'#download-bot-modal-' + record.id" class="btn btn-info" style="margin: 0 2px;">导出</button>
                                        <!-- 模态框 -->
                                        <div class="modal fade modal-xl" :id="'download-bot-modal-' + record.id" tabindex="-1">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h1 class="modal-title fs-5">导出【ID:{{ record.id }}】</h1>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <div class="mb-3">
                                                            <label for="add-bot-title" class="form-label">数据集序号</label>
                                                            <input v-model="fileName" type="text" class="form-control" id="update-bot-title">
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <div class="error-message">{{ record.error_message }}</div>
                                                        <button type="button" class="btn btn-primary" @click="export_record(record)">提交</button>
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
import vkbeautify from 'vkbeautify'
import x2js from 'x2js'

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
        const fileName = ref('');
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
                url: "https://app4460.acapp.acwing.com.cn/api/manage/search/mark/all/",
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
            $.ajax({
                url: "https://app4460.acapp.acwing.com.cn/api/manage/update/mark/",
                data: {
                    id: record.id,
                    state: record.state,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                type: "post",
                success(resp) {
                    console.log(resp);
                    if (resp.error_message === "success") {
                        Modal.getInstance("#update-bot-modal-" + record.id).hide();
                        make_notice(record.userId, "您的标注信息已审核，请及时查看");
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

        const export_record = (record) => {
            if (fileName.value.length === 0) {
                alert("请输入序号！");
                return;
            }
            let split = record.label.split("&");
            let height = split[1];
            split = split[2].split("#");
            let width = split[0];
            split = split[1].split("@");
            let t = split[0];
            let xmin = split[1];
            let ymin = split[2];
            let xmax = split[3];
            let ymax = split[4];
            let obj = {
                annotation: {
                    folder: "JPEGImages",
                    filename: "000721.jpg",
                    path: 'C:\\Users\\Echo\\Desktop\\cs\\JPEGImages\\' + fileName.value + '.jpg',
                    source: {
                        database: "Unknown",
                    },
                    size: {
                        width: width,
                        height: height,
                        depth: 3,
                    },
                    segmented: 0,
                    object: {
                        name: t,
                        pose: "Unspecified",
                        truncated: "0",
                        difficult: "0",
                        bndbox: {
                            xmin: xmin,
                            ymin: ymin,
                            xmax: xmax,
                            ymax: ymax,
                        }
                    },
                },
            };
            let xml = new x2js().js2xml(obj);
            xml = `<?xml version='1.0' encoding='utf-8'?>` + xml;
            xml = vkbeautify.xml(xml);

            //压缩数据
            let url = window.URL.createObjectURL(
                new Blob([xml], { type: "text/xml" })
            );
            
            // 然后就可以创建a标签 最后下载下来了
            let linkXml = document.createElement("a");
            linkXml.style.display = "none";
            linkXml.href = url;
            linkXml.setAttribute("download",fileName.value);
            document.body.appendChild(linkXml);
            linkXml.click();
            Modal.getInstance("#download-bot-modal-" + record.id).hide();
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
            fileName,
            click_page,
            pull_page,
            update_record,
            export_record,
        }
    },
}
</script>

<style scoped>

</style>