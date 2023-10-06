<template>
  <div class="container">
    <div class="row">
      <div class="card" style="margin-top: 20px;">
        <div class="card-header">
          <span style="font-size: 130%;">模型预测</span>
          <div class="dropdown float-end">
            <button class="btn btn-primary dropdown-toggle" style="margin: 0 10px;" type="button" data-bs-toggle="dropdown" aria-expanded="false">
              操作
            </button>
            <ul class="dropdown-menu">
              <a class="dropdown-item" href="#" @click="save_record">提交数据</a>
              <a class="dropdown-item" href="#" @click="export_xlsx">导出结果</a>
            </ul>
          </div>
            <div class="btn-group float-end" role="group" aria-label="Basic radio toggle button group">
                <input type="radio" class="btn-check" name="btnradio" value="1" id="btnradio1" autocomplete="off">
                <label class="btn btn-outline-success" for="btnradio1">使用超分辨率</label>

                <input type="radio" class="btn-check" name="btnradio" value="0" id="btnradio2" autocomplete="off" checked>
                <label class="btn btn-outline-danger" for="btnradio2">不使用超分辨率</label>
            </div>
        </div>
        <div class="card-body">
            <div class="row">
                <div class="col text-center">
                    <div class="input-group" style="display: none;">
                        <input type="file" class="form-control" id="upLoadPhotoInput" accept="image/jpeg" @change="reset_inputSrc">
                    </div>
                    <img :src="imgSrc" @click="upload_input_click" id="inputImg" class="img-fluid img-thumbnail" alt="" style="height: 300px">
                </div>
            </div>
            <div class="row">
                <div class="col text-center">
                    <button type="button" @click="predict" class="btn btn-info mt-5" style="width: 150px;">预测</button>
                </div>
            </div>
            <div class="row">
                <div class="col form-floating mb-3">
                    <textarea rows="3" v-model="$store.state.predictor.result" readonly class="form-control-plaintext" id="floatingPlaintextInput"></textarea>
                    <label for="floatingPlaintextInput">预测结果</label>
                </div>
            </div>
            <div class="row">
                <a class="btn btn-primary" data-bs-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
                    详细结果
                </a>
                <div class="collapse" id="collapseExample">
                    <table class="table table-striped" id="res-table">
                        <thead>
                            <tr>
                            <th scope="col">#</th>
                            <th scope="col">名称</th>
                            <th scope="col">置信度</th>
                            <th scope="col">xmin</th>
                            <th scope="col">ymin</th>
                            <th scope="col">xmax</th>
                            <th scope="col">ymax</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="res in result" :key="res.id">
                                <th scope="row">{{ res.id }}</th>
                                <td>{{ res.category }}</td>
                                <td>{{ res.score }}</td>
                                <td>{{ res.bbox[0] }}</td>
                                <td>{{ res.bbox[1] }}</td>
                                <td>{{ res.bbox[2] }}</td>
                                <td>{{ res.bbox[3] }}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
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
</template>

<script>
import { ref } from 'vue'
import { useStore } from 'vuex'
import $ from 'jquery'
import * as XLSX from 'xlsx/xlsx.mjs'
import { Toast } from 'bootstrap/dist/js/bootstrap'

export default {
    setup() {
        const imgSrc = ref("https://miracle-of-light.oss-cn-shanghai.aliyuncs.com/predict/show/%E7%82%B9%E5%87%BB%E9%80%89%E6%8B%A9.png");
        const labels = ref([]);
        const store = useStore();
        const result = ref([]);
        const error_message = ref("");
        store.commit("updateResult", "");
        const predict = async () => {
            const inputImg = document.getElementById('inputImg');
            const radio = document.getElementsByName('btnradio');
            let sr = "-1";
            for (let r of radio) {
                if (r.checked) sr = r.value;
            }
            console.log(sr);
            if (inputImg.src.indexOf("https://miracle-of-light.oss-cn-shanghai.aliyuncs.com") !== -1) {
                return;
            }
            $.ajax({
                // 3001
                url: "https://app4460.acapp.acwing.com.cn/model/predict/",
                type: "post",
                data: {
                    img: imgSrc.value,
                    appKey: "iuaena0516",
                    use_sr: sr,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        console.log(resp);
                        resp.result.sort((a, b) => {
                            return b.score - a.score;
                        })
                        if (resp.result.length === 0) {
                            store.commit("updateResult", "无结果");
                        } else {
                            result.value = resp.result;
                            console.log("---result---", result.value);
                            let id = 1;
                            for (let res of result.value) {
                                res.id = id;
                                for (let label of labels.value) {
                                    if (label.pinyin === res.category) {
                                        res.category = label.name;
                                        break;
                                    }
                                }
                                for (let i = 0; i < 4; i ++ ) {
                                    res.bbox[i] = Math.round(res.bbox[i]);
                                }
                                id ++;
                            }
                            store.commit("updateResult", "(Top1) " + labels.value[resp.result[0].category_id].name + resp.result[0].score);
                        }
                        imgSrc.value = resp.img;
                    } else {
                        console.log(resp);
                    }
                },
                error(resp) {
                    console.log(resp);
                }
            })
        }
        const upload_input_click = () => {
            const upLoadPhotoInput = document.getElementById('upLoadPhotoInput');
            upLoadPhotoInput.click();
        }
        const reset_inputSrc = () => {
            const upLoadPhotoInput = document.getElementById('upLoadPhotoInput');
            const reader = new FileReader();
            reader.readAsDataURL(upLoadPhotoInput.files[0]);
            reader.onload = () => {
                imgSrc.value = reader.result;
            }
        }
        const export_xlsx = () => {
            //写入Excel
            //设置标签内容
            const table_elt = document.getElementById("res-table");
            const WorkBook = XLSX.utils.table_to_book(table_elt);
            //写入Excel
            const wbout = XLSX.write(WorkBook, {bookType: 'xlsx', bookSST: false, type: 'binary', compression: true});
            //压缩数据
            const s2ab =((s) => {
                let buf = new ArrayBuffer(s.length);
                let view = new Uint8Array(buf);
                for (let i = 0; i < s.length; i++) view[i] = s.charCodeAt(i) & 0xFF;
                return buf;
            });
            const blob = new Blob([s2ab(wbout)],{
                type: 'application/octet-steam'
            });
            // 下载
            let url = null
            if(typeof blob === 'object' && blob instanceof Blob) url = URL.createObjectURL(blob);
            let pom = document.createElement('a');
            pom.setAttribute('href', url);
            pom.setAttribute('download', 'res.xlsx');
            pom.click();
        }
        const save_record = () => {
            const inputImg = document.getElementById('inputImg');
            if (inputImg.src.indexOf("https://miracle-of-light.oss-cn-shanghai.aliyuncs.com") !== -1) {
                return;
            }
            $.ajax({
                url: "https://app4460.acapp.acwing.com.cn/api/predict/show/record/classify/add/",
                type: "post",
                data: {
                    classify: inputImg.src,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        error_message.value = "保存成功！";
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
        $.ajax({
            // 这里移动端记得跟着改
            url: "https://miracle-of-light.oss-cn-shanghai.aliyuncs.com/predict/show/labels1.json",
            type: "get",
            success(resp) {
                console.log(resp);
                labels.value = resp;
            },
            error(resp) {
                console.log(resp);
            }
        })
        return {
            imgSrc,
            result,
            error_message,
            predict,
            upload_input_click,
            reset_inputSrc,
            export_xlsx,
            save_record,
        }
    }
}
</script>

<style scoped>
div.error_info {
    margin-top: 1vh;
}
.card {
    border: 1px solid rgb(219, 207, 207);
    padding: 12px;
    border-radius: 12px;
    box-shadow: 3px 3px 6px #888;
}
</style>
