<template>
  <div class="container">
    <div class="row">
      <div class="card" style="margin-top: 20px;">
        <div class="card-header">
          <span style="font-size: 130%;">提交记录</span>
          <div class="dropdown float-end">
            <button class="btn btn-primary dropdown-toggle" style="margin: 0 10px;" type="button" data-bs-toggle="dropdown" aria-expanded="false">
              {{ data }}
            </button>
            <ul class="dropdown-menu">
              <a class="dropdown-item" href="#" @click="change_data('标注数据')">标注数据</a>
              <a class="dropdown-item" href="#" @click="change_data('识别数据')">识别数据</a>
            </ul>
          </div>
        </div>
        <div class="card-body">
          <table class="table table-hover table-striped" style="text-align: center;" v-if="data === '标注数据'">
            <thead>
                <tr>
                    <th>图像</th>
                    <th>标签</th>
                    <th>提交时间</th>
                    <th>状态</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="record in records" :key="record.id">
                  <td><img data-bs-toggle="modal" :data-bs-target="'#photoModal' + record.id" :src="record.photo" style="width: 80px; height: 80px;"></td>
                  <td><img data-bs-toggle="modal" :data-bs-target="'#markModal' + record.id" :src="record.mark" style="width: 80px; height: 80px;"></td>
                  <td>{{ record.createtime }}</td>
                  <td>{{ record.state }}</td>
                  <td><button @click="remove_item(record.id)" type="button" class="btn btn-danger">删除</button></td>
                  <!-- photo显示大图部分 -->
                  <div class="modal fade bs-example-modal-lg text-center" :id="'photoModal' + record.id" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" >
                    <div class="modal-dialog modal-lg" ><!-- style="display: inline-block; width: auto;" -->
                      <div class="modal-content">
                        <div class="modal-header">
                          <h4 class="modal-title float-start" id="modalLabel">{{ record.createtime }}</h4>
                          <button type="button" class="btn-close float-end" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                          <img :src="record.photo" alt="" style="width: 600px;">
                        </div>
                      </div>
                    </div>
                  </div>
                  <!-- mark显示大图部分 -->
                  <div class="modal fade bs-example-modal-lg text-center" :id="'markModal' + record.id" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" >
                    <div class="modal-dialog modal-lg" ><!-- style="display: inline-block; width: auto;" -->
                      <div class="modal-content">
                        <div class="modal-header">
                          <h4 class="modal-title float-start" id="modalLabel">{{ record.createtime }}</h4>
                          <button type="button" class="btn-close float-end" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                          <img :src="record.mark" alt="" style="width: 600px;">
                        </div>
                      </div>
                    </div>
                  </div>
                </tr>
            </tbody>
          </table>
          <table class="table table-hover table-striped" style="text-align: center;" v-else>
            <thead>
              <tr>
                <th>标签</th>
                <th>提交时间</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="record in records" :key="record.id">
                <td><img data-bs-toggle="modal" :data-bs-target="'#imgModal' + record.id" :src="record.classify" style="width: 80px; height: 80px;"></td>
                <td>{{ record.createtime }}</td>
                <td><button @click="remove_item(record.id)" type="button" class="btn btn-danger">删除</button></td>
                <!-- 显示大图部分 -->
                <div class="modal fade bs-example-modal-lg text-center" :id="'imgModal' + record.id" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" >
                  <div class="modal-dialog modal-lg" ><!-- style="display: inline-block; width: auto;" -->
                    <div class="modal-content">
                      <div class="modal-header">
                        <h4 class="modal-title float-start" id="modalLabel">{{ record.createtime }}</h4>
                        <button type="button" class="btn-close float-end" data-bs-dismiss="modal" aria-label="Close"></button>
                      </div>
                      <div class="modal-body">
                        <img :src="record.classify" alt="" style="width: 600px;">
                      </div>
                    </div>
                  </div>
                </div>
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
</template>

<script>
import { useStore } from 'vuex'
import { ref } from 'vue'
import $ from 'jquery'

export default {
  setup() {
    const data = ref('标注数据');
    const store = useStore();
    let current_page = 1;
    let records = ref([]);
    let total_records = 0;
    let pages = ref([]);

    const click_page = page => {
      if (page === -2) page = current_page - 1;
      else if (page === -1) page = current_page + 1;
      let max_pages = parseInt(Math.ceil(total_records / 5));
      if (page >= 1 && page <= max_pages) {
          pull_page(page);
      }
    }

    const update_pages = () => {
      let max_pages = parseInt(Math.ceil(total_records / 5));
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

    const pull_page = page => {
      current_page = page;
      if (data.value === "识别数据") {
        $.ajax({
          url: "https://app4460.acapp.acwing.com.cn/api/predict/show/record/classify/getlist/",
          data: {
            page,
          },
          type: "get",
          headers: {
            Authorization: "Bearer " + store.state.user.token,
          },
          success(resp) {
            if (resp.error_message === "success") {
              records.value = resp.records;
              total_records = resp.records_count;
              update_pages();
            } else {
              records.value = [];
              total_records = 0;
              update_pages();
            }
          },
          error(resp) {
            console.log(resp);
          }
        })
      } else {
        $.ajax({
          url: "https://app4460.acapp.acwing.com.cn/api/predict/show/record/mark/getlist/",
          data: {
            page,
          },
          type: "get",
          headers: {
            Authorization: "Bearer " + store.state.user.token,
          },
          success(resp) {
            if (resp.error_message === "success") {
              records.value = resp.records;
              total_records = resp.records_count;
              update_pages();
            } else {
              records.value = [];
              total_records = 0;
              update_pages();
            }
          },
          error(resp) {
            console.log(resp);
          }
        })       
      }
    }

    const remove_item = (id) => {
      if (data.value === "识别数据") {
        $.ajax({
          url: "https://app4460.acapp.acwing.com.cn/api/predict/show/record/classify/remove/",
          data: {
            id: id,
          },
          type: "post",
          headers: {
            Authorization: "Bearer " + store.state.user.token,
          },
          success(resp) {
            if (resp.error_message === "success") {
              total_records --;
              if (total_records % 5 === 0) current_page --;
              pull_page(current_page);
            } else {
              console.log(resp);
            }
          },
          error(resp) {
            console.log(resp);
          }
        })
      } else {
        $.ajax({
          url: "https://app4460.acapp.acwing.com.cn/api/predict/show/record/mark/remove/",
          data: {
            id: id,
          },
          type: "post",
          headers: {
            Authorization: "Bearer " + store.state.user.token,
          },
          success(resp) {
            if (resp.error_message === "success") {
              total_records --;
              if (total_records % 5 === 0) current_page --;
              pull_page(current_page);
            } else {
              console.log(resp);
            }
          },
          error(resp) {
            console.log(resp);
          }
        })
      }
    }

    pull_page(current_page);

    const change_data = (d) => {
      data.value = d;
      current_page = 1;
      pull_page(current_page);
    }

    return {
      records,
      pages,
      data,
      change_data,
      click_page,
      remove_item,
    }
  }
}
</script>

<style scoped>
.card {
    border: 1px solid rgb(219, 207, 207);
    padding: 12px;
    border-radius: 12px;
    box-shadow: 3px 3px 6px #888;
}
img.record-user-photo {
    width: 8vh;
}

</style>
